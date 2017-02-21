package Servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import BaseBean.CarBean;
import BaseBean.Price;
import Dao.SampleDAO;

@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Delete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public int cost(Timestamp t1) {
		try {
			SampleDAO dao = new SampleDAO();
			ArrayList<Price> pc = dao.select_value();

			Timestamp t2 = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = t1;
			Date date2 = t2;

			String s1 = date1.getYear() + "-" + date1.getMonth() + "-" + date1.getDay() + " 8:00:00";
			String s2 = date1.getYear() + "-" + date1.getMonth() + "-" + date1.getDay() + " 20:00:00";
			Date d1_time1 = sdf.parse(s1);
			Date d1_time2 = sdf.parse(s2);

			String s3 = date2.getYear() + "-" + date2.getMonth() + "-" + date2.getDay() + " 8:00:00";
			String s4 = date2.getYear() + "-" + date2.getMonth() + "-" + date2.getDay() + " 20:00:00";
			Date d2_time1 = sdf.parse(s3);
			Date d2_time2 = sdf.parse(s4);

			int hour1 = date1.getHours();

			long diff = date2.getTime() - date1.getTime();
			long minutes = diff / (1000 * 60);

			int spend = 0;
			if (minutes >= 60 * 24) {
				long day = minutes / (60 * 24);
				spend = spend + pc.get(0).getCost() * 4 * (int) day;
				minutes = minutes - (60 * 24) * day;
			}
			if (minutes == 0)
				return spend;

			if (hour1 >= 8 && hour1 < 20) {
				double hour = minutes / 60;
				if (hour1 + hour <= 20) {
					if (minutes <= 15)
						return spend;
					if (minutes > 15 && minutes <= 60)
						return (pc.get(0).getCost() + spend);
					if (minutes > 60 && minutes <= 60 * 4)
						return (pc.get(0).getCost() / 2 * (int) (minutes / 30 + 1) + spend);
					if (minutes > 60 * 4)
						return (pc.get(0).getCost() * 4 + spend);
				}
				if (hour1 + hour <= 32) {
					long df = d1_time2.getTime() - date1.getTime();
					long min = df / (1000 * 60);
					if (min <= 15)
						return (spend + pc.get(1).getCost());
					if (min > 15 && min <= 60)
						return (pc.get(0).getCost() + spend + pc.get(1).getCost());
					if (min > 60 && min <= 60 * 4)
						return (pc.get(0).getCost() / 2 * (int) (min / 30 + 1) + spend + pc.get(1).getCost());
					if (min > 60 * 4)
						return (pc.get(0).getCost() * 4 + spend + pc.get(1).getCost());
				}
				if (hour1 + hour < 44) {
					long df1 = d1_time2.getTime() - date1.getTime();
					long min1 = df1 / (1000 * 60);
					long df2 = date2.getTime() - d2_time1.getTime();
					long min2 = df2 / (1000 * 60);

					long min = min1 + min2;
					if (min <= 15)
						return (spend + pc.get(1).getCost());
					if (min > 15 && min <= 60)
						return (pc.get(0).getCost() + spend + pc.get(1).getCost());
					if (min > 60 && min <= 60 * 4)
						return (pc.get(0).getCost() / 2 * (int) (min / 30 + 1) + spend + pc.get(1).getCost());
					if (min > 60 * 4)
						return (pc.get(0).getCost() * 4 + spend + pc.get(1).getCost());
				}

			}
			if (hour1 >= 20 && hour1 < 24) {
				double hour = minutes / 60;
				if (hour1 + hour <= 32) {
					return spend + pc.get(1).getCost();
				}
				if (hour1 + hour < 44) {
					long df2 = date2.getTime() - d2_time1.getTime();
					long min2 = df2 / (1000 * 60);
					if (min2 <= 15)
						return (spend + pc.get(1).getCost());
					if (min2 > 15 && min2 <= 60)
						return (pc.get(0).getCost() + spend + pc.get(1).getCost());
					if (min2 > 60 && min2 <= 60 * 4)
						return (pc.get(0).getCost() / 2 * (int) (min2 / 30 + 1) + spend + pc.get(1).getCost());
					if (min2 > 60 * 4)
						return (pc.get(0).getCost() * 4 + spend + pc.get(1).getCost());
				}
				if (hour1 + hour < 48) {
					return spend + pc.get(1).getCost() + pc.get(0).getCost() * 4;
				}
			}
			if (hour1 >= 0 && hour1 < 8) {
				double hour = minutes / 60;
				if (hour1 + hour <= 8) {
					return spend + pc.get(1).getCost();
				}
				if (hour1 + hour < 20) {
					long df2 = date2.getTime() - d2_time1.getTime();
					long min2 = df2 / (1000 * 60);
					if (min2 <= 15)
						return (spend + pc.get(1).getCost());
					if (min2 > 15 && min2 <= 60)
						return (pc.get(0).getCost() + spend + pc.get(1).getCost());
					if (min2 > 60 && min2 <= 60 * 4)
						return (pc.get(0).getCost() / 2 * (int) (min2 / 30 + 1) + spend + pc.get(1).getCost());
					if (min2 > 60 * 4)
						return (pc.get(0).getCost() * 4 + spend + pc.get(1).getCost());
				}
				if (hour1 + hour < 32) {
					return spend + pc.get(1).getCost() + pc.get(0).getCost() * 4;
				}
			}
			return spend;
		} catch (Exception ee) {
			ee.printStackTrace();
			return -1;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String msg_delete = null;
		HttpSession session = request.getSession();
		int x=0;
		try {
			SampleDAO dao = new SampleDAO();
			String number = request.getParameter("Number");

			CarBean carbean = dao.select_new(number);

			boolean success = dao.delete_new(number);

			 x = cost(new java.sql.Timestamp(carbean.getApproach_Time().getTime()));
			if (carbean.getCar_Type().equals("卡车") || carbean.getCar_Type().equals("十座车辆")
					|| carbean.getCar_Type().equals("特殊车辆"))
				x = x * 2;
			carbean.setCar_Cost(x);

			boolean s = dao.insert_old(carbean);
			if (success) {
				msg_delete = "出场成功!";
			} else {
				msg_delete = "出场失败!";
			}
		} catch (Exception e) {
			msg_delete = "出场失败!";
			e.printStackTrace();
		}
		session.setAttribute("msg_delete", msg_delete);
		session.setAttribute("Cost_delete", x);
		RequestDispatcher view = request.getRequestDispatcher("Select");
		view.forward(request, response);
	}

}
