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

import Dao.SampleDAO;


@WebServlet("/Analysis_Number")
public class Analysis_Number extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Analysis_Number() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		ArrayList<Integer> number1 = new ArrayList<Integer>();
		ArrayList<Integer> number2 = new ArrayList<Integer>();
		try
		{
			SampleDAO dao = new SampleDAO();
			number1 = dao.Analysis_Number(request.getParameter("Analysis_Time"), "approach_Time");
			number2 = dao.Analysis_Number(request.getParameter("Analysis_Time"), "leaving_Time");
			
			HttpSession session = request.getSession();
			session.setAttribute("approach_Number", number1);
			session.setAttribute("leaving_Number", number2);
			if(request.getParameter("Analysis_Time").equals("")){
				session.setAttribute("Analysis_Date", "√ø»’");
			}else{
			session.setAttribute("Analysis_Date", request.getParameter("Analysis_Time"));
			}
			RequestDispatcher view = request.getRequestDispatcher("Analysis.jsp");
			view.forward(request, response);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
