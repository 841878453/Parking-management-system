package Servlet;

import java.io.IOException;
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


@WebServlet("/Judge_Modify")
public class Judge_Modify extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static InitialContext context = null;
	private DataSource dataSource = null;
	private static final String select_SQL = "select * from login"; 
    
    public Judge_Modify() {
        super();
        try {
			context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/sampleDS");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		
		UserBean userbean = new UserBean();
		userbean.setUserName(request.getParameter("username"));
		userbean.setPassword(request.getParameter("password"));
		HttpSession session = request.getSession();
		String message = "";
		try 
		{
			boolean modify_Success = false;
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(select_SQL);
			ResultSet rst = pstmt.executeQuery();
			while (rst.next())
			{
				if (rst.getString(1).equals(userbean.getUserName())
						&& rst.getString(2).equals(userbean.getPassword()) 
						&& request.getParameter("new_password").equals(request.getParameter("c_new_password"))) 
				{
					pstmt = conn.prepareStatement("update login set password = ? where username = ? ");
					pstmt.setString(1, request.getParameter("new_password"));
					pstmt.setString(2, userbean.getUserName());
					pstmt.executeUpdate();
					message = "修改密码成功!";
					modify_Success=true;
					session.setAttribute("message",message);
					RequestDispatcher view = request.getRequestDispatcher("Modify.jsp");
					view.forward(request, response);
				}
			}
			if (!modify_Success) {
				message = "修改密码出错!";
				session.setAttribute("message", message);
				response.sendRedirect("Modify.jsp");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
