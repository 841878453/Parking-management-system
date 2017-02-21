package Servlet;

import java.io.IOException;

import java.util.*;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.SampleDAO;
import BaseBean.CarBean;

@WebServlet("/Insert")
public class Insert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Insert() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		CarBean carbean = new CarBean();
		SampleDAO dao = new SampleDAO();
		String msg_success = null;
		HttpSession session = request.getSession();
		try
		{
			if(request.getParameter("car_Type")!=""&&request.getParameter("car_Number")!=""&&request.getParameter("car_Place")!=""){
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
			carbean.setApproach_Time(new java.util.Date());
			carbean.setCar_Type(request.getParameter("car_Type"));
			carbean.setCar_Number(request.getParameter("car_Number"));
			carbean.setCar_Place(request.getParameter("car_Place"));
			boolean success = dao.insert_new(carbean);
			if(success)
			{
				msg_success = "入场成功!";
			}
			else
			{
				msg_success = "入场失败!";
			}
			}else{
				msg_success = "入场失败!";
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		session.setAttribute("msg_success", msg_success);
		RequestDispatcher view = request.getRequestDispatcher("Select");
		view.forward(request, response);
	}

}
