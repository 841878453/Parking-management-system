package Servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.SampleDAO;


@WebServlet("/Analysis_Sum_Car_Cost")
public class Analysis_Sum_Car_Cost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Analysis_Sum_Car_Cost() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		ArrayList<String> Sum_Cost = new ArrayList<String>();
		try
		{
			if(!request.getParameter("Analysis_Month").equals(""))
			{
			SampleDAO dao = new SampleDAO();
			
			Calendar rightNow = Calendar.getInstance();
			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM");
			rightNow.setTime(simpleDate.parse(request.getParameter("Analysis_Month"))); 
			int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			Sum_Cost=dao.Analysis_Sum_Car_Cost(days, request.getParameter("Analysis_Month"));
			HttpSession session = request.getSession();
			session.setAttribute("Analysis_Month", Sum_Cost);
			session.setAttribute("Analysis_Month_M", request.getParameter("Analysis_Month"));
			}
			response.sendRedirect("Analysis.jsp?#chart2");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
