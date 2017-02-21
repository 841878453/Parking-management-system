package Servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.SampleDAO;


@WebServlet("/Analysis_Sum_Car_Type")
public class Analysis_Sum_Car_Type extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Analysis_Sum_Car_Type() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		ArrayList<Integer> number = new ArrayList<Integer>();
		String Analysis_Type_Month=request.getParameter("Analysis_Type");
		try
		{
			HttpSession session = request.getSession();
			if(!request.getParameter("Analysis_Type").equals(""))
			{
			SampleDAO dao = new SampleDAO();
			
			Calendar rightNow = Calendar.getInstance();
			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM");
			rightNow.setTime(simpleDate.parse(request.getParameter("Analysis_Type"))); 
			int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			number = dao.Analysis_Sum_Car_Type(request.getParameter("Analysis_Type"), days);
			session.setAttribute("Analysis_Car_Type", number);
			}
			session.setAttribute("Analysis_Type_Month", Analysis_Type_Month);
			response.sendRedirect("Analysis.jsp?#chart3");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
