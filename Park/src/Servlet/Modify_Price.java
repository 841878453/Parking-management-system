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
import javax.websocket.Session;

import BaseBean.Price;
import Dao.SampleDAO;

@WebServlet("/Modify_Price")
public class Modify_Price extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Modify_Price() {
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
		HttpSession session = request.getSession();
		try {
			String message_Price_Modify = "";
			int x1, x2;
			if (request.getParameter("8-20").equals(""))
				x1 = ((ArrayList<Price>) session.getAttribute("Price")).get(1).getCost();
			else
				x1 = Integer.valueOf(request.getParameter("8-20"));
			if (request.getParameter("20-次日8").equals(""))
				x2 = ((ArrayList<Price>) session.getAttribute("Price")).get(0).getCost();
			else
				x2 = Integer.valueOf(request.getParameter("20-次日8"));

			SampleDAO dao = new SampleDAO();
			Boolean t = dao.update_Price(x1, "8-20");
			Boolean t1 = dao.update_Price(x2, "20-次日8");
			if (t == true && t1 == true) {
				message_Price_Modify = "修改成功!";
				ArrayList<Price> p = dao.select_value();
				session.setAttribute("Price", p);
				session.setAttribute("message_Price_Modify", message_Price_Modify);
				RequestDispatcher view = request.getRequestDispatcher("Price_Modify.jsp");
				view.forward(request, response);
			} else {
				ArrayList<Price> p = dao.select_value();
				session.setAttribute("Price", p);
				message_Price_Modify = "修改失败!";
				session.setAttribute("message_Price_Modify", message_Price_Modify);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
