package Servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import BaseBean.CarBean;
import Dao.SampleDAO;

@WebServlet("/Select_Now")
public class Select_Now extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Select_Now() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		try {
			String car_Approach_Time = "";
			String car_Number = "";
			String car_Place = "";
			String car_Type = "";
			if (request.getParameter("car_Approach_Time") != null) {
				car_Approach_Time = request.getParameter("car_Approach_Time");
			}
			if (request.getParameter("car_Number") != null) {
				car_Number = request.getParameter("car_Number");
			}
			if (request.getParameter("car_Place") != null) {
				car_Place = request.getParameter("car_Place");
			}
			if (request.getParameter("car_Type") != null) {
				car_Type = request.getParameter("car_Type");
			}

			SampleDAO dao = new SampleDAO();
			ArrayList<CarBean> car_Query_Type = new ArrayList<CarBean>();
			ArrayList<String> car_Query_Record = new ArrayList<String>();
			car_Query_Record.add(car_Approach_Time);
			car_Query_Record.add(car_Number);
			car_Query_Record.add(car_Place);
			car_Query_Record.add(car_Type);
			car_Query_Type = dao.Query_Now(car_Approach_Time, car_Number, car_Place, car_Type);

			HttpSession session = request.getSession();
			session.setAttribute("car_Query_Now_Type", car_Query_Type);
			session.setAttribute("car_Query_Now_Record", car_Query_Record);
			RequestDispatcher view = request.getRequestDispatcher("Query_Now.jsp");
			view.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
