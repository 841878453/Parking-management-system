package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import BaseBean.UserBean;

@WebServlet("/Judge")
public class Judge extends HttpServlet {
	private static InitialContext context = null;
	private DataSource dataSource = null;
	private static final String select_SQL = "select * from login";
	private static final long serialVersionUID = 1L;

	public Judge() {
		super();
		try {
			context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/sampleDS");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		UserBean userbean = new UserBean();
		userbean.setUserName(request.getParameter("username"));
		userbean.setPassword(request.getParameter("password"));
		String message = "";
		try {
			boolean login_Success = false;
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(select_SQL);
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {
				if (rst.getString(1).equals(userbean.getUserName())
						&& rst.getString(2).equals(userbean.getPassword())) {
					if (request.getParameter("remember_me") != null
							&& request.getParameter("remember_me").equals("remember")) {
						Cookie cookie = new Cookie("park_Username",
								userbean.getUserName() + "-" + userbean.getPassword());
						cookie.setMaxAge(60 * 60 * 24 * 7);
						response.addCookie(cookie);
					}
					message="";
					login_Success = true;
					HttpSession session = request.getSession();
					session.setAttribute("userbean", userbean);
					RequestDispatcher view = request.getRequestDispatcher("Select");
					view.forward(request, response);
				}
				else
				{
					Cookie cookie = new Cookie("park_Username","");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
			if (!login_Success) {
				message = "’À∫≈ªÚ’ﬂ√‹¬Î¥ÌŒÛ!";
				HttpSession session = request.getSession();
				session.setAttribute("message", message);
				response.sendRedirect("index.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
