package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

@WebServlet("/Select")
public class Select extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Select() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		
		try
		{
			SampleDAO dao = new SampleDAO();
			ArrayList<Price> p = dao.select_value();
			ArrayList<CarBean> s = dao.select_place_number();
			int k=0;
			ArrayList<String> s1 = new ArrayList<String>();
			
			for(char i='A';i<='E';i++)
			{
				for(int j=1;j<=20;j++)
				{
					if(j<10)
					{
						if (s.isEmpty())
						{
							s1.add("" + i + "0" + j);
						} 
						else 
						{
							if(k<s.size() && s.get(k).getCar_Place().equals(""+i+"0"+j))
							{
								k++;
							}
							else
								s1.add("" + i + "0" + j);
						}
					}
					else
					{
						if (s.isEmpty()) {
							s1.add("" + i + j);
						} else {
							if(k<s.size() && s.get(k).getCar_Place().equals(""+i+j))
							{
								k++;
							}
							else
								s1.add("" + i + j);
						}
					}
				}
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("car_place", s1);
			session.setAttribute("car_place_number", s);
			session.setAttribute("Price", p);
			RequestDispatcher view = request.getRequestDispatcher("Park_Manage.jsp");
			view.forward(request, response);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		
	}

}
