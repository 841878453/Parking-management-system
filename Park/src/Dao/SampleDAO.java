package Dao;


import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import BaseBean.CarBean;
import BaseBean.Price;

public class SampleDAO {
	private static InitialContext context = null;
	private DataSource dataSource = null;
	private static final String insert_new_SQL = "insert into now_Pcondition values(?,?,?,?)";
	private static final String select_place_SQL = "select car_Place,car_Number from now_Pcondition order by car_Place";
	private static final String select_new_number_SQL = "select car_Number from now_Pcondition";
	private static final String select_SQL = "select * from now_Pcondition where car_Number = ?";
	private static final String delete_new_SQL = "delete from now_Pcondition where car_Number = ?";
	
	private static final String insert_old_SQL = "insert into old_Pcondition values(?,?,?,?,?,?)";
	private static final String select_time_SQL = "select * from old_Pcondition where ?>approach_Time and approach_Time < ?";
	private static final String select_type_SQL = "select * from old_Pcondition where car_Type = ?";
	private static final String select_number_SQL = "select * from old_Pcondition where car_Number = ?";
	
	public SampleDAO()
	{
		try
		{
			if(context==null)
			{
				context=new InitialContext();
			}
			dataSource =(DataSource)context.lookup("java:comp/env/jdbc/sampleDS");
		}
		catch(NamingException e2)
		{
			e2.printStackTrace();
		}
	}
	
	public Connection getConnection()
	{
		Connection conn = null;
		try
		{
			conn = dataSource.getConnection();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return conn;
	}
	//向当前停车表插入一条内容
	public boolean insert_new(CarBean Car)
	{
		Connection conn =null;
		PreparedStatement pstmt = null;
		try
		{
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(insert_new_SQL);
			pstmt.setString(1, Car.getCar_Type());
			pstmt.setTimestamp(2,  new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			pstmt.setString(3, Car.getCar_Number());
			pstmt.setString(4, Car.getCar_Place());
			pstmt.executeUpdate();
			pstmt.close();
			return true;
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			return false;
		}
		finally
		{
			try{conn.close();}
			catch(SQLException e){e.printStackTrace();}
		}
	}
	//遍历车位号
	public ArrayList<CarBean> select_place_number()
	{
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rst =null;
		ArrayList<CarBean> cb = new ArrayList<CarBean>();
		try
		{
			conn = dataSource.getConnection();
			pstmt =conn.prepareStatement(select_place_SQL);
			rst = pstmt.executeQuery();
			while(rst.next())
			{
				CarBean carbean = new CarBean();
				carbean.setCar_Place(rst.getString("car_Place"));
				carbean.setCar_Number(rst.getString("car_Number"));
				
				cb.add(carbean);
			}
			rst.close();
			pstmt.close();
			return cb;
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			return null;
		}
		finally
		{
			try{conn.close();}
			catch(SQLException e){e.printStackTrace();}
		}
		
	}
	//遍历车牌号
	public ArrayList<String> select_new_number()
	{
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rst =null;
		ArrayList<String> s = new ArrayList<String>();
		try
		{
			conn = dataSource.getConnection();
			pstmt =conn.prepareStatement(select_new_number_SQL);
			rst = pstmt.executeQuery();
			if(rst.next())
			{
				s.add(rst.getString("car_Number"));
			}
			rst.close();
			pstmt.close();
			return s;
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			return null;
		}
		finally
		{
			try{conn.close();}
			catch(SQLException e){e.printStackTrace();}
		}
	}
	
	public CarBean select_new(String car_Number)
	{
		Connection conn =null;
		PreparedStatement pstmt = null;
		try
		{
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(select_SQL);
			pstmt.setString(1, car_Number);
			ResultSet rst = pstmt.executeQuery();
			CarBean carbean = new CarBean();
			while(rst.next())
			{
				carbean.setCar_Type(rst.getString("car_Type"));
				carbean.setApproach_Time(rst.getTimestamp("approach_Time"));
				carbean.setCar_Number(rst.getString("car_Number"));
				carbean.setCar_Place(rst.getString("car_Place"));
			}
			pstmt.close();
			return carbean;
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			return null;
		}
		finally
		{
			try{conn.close();}
			catch(SQLException e){e.printStackTrace();}
		}
	}
	
	//从当前停车表删掉一条内容
	public boolean delete_new(String car_Number)
	{
		Connection conn =null;
		PreparedStatement pstmt = null;
		try
		{
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(delete_new_SQL);
			pstmt.setString(1, car_Number);
			pstmt.executeUpdate();
			pstmt.close();
			return true;
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			return false;
		}
		finally
		{
			try{conn.close();}
			catch(SQLException e){e.printStackTrace();}
		}
	}
	//向历史停车表插入一条内容
	public boolean insert_old(CarBean Car)
	{
		Connection conn =null;
		PreparedStatement pstmt = null;
		try
		{
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(insert_old_SQL);
			pstmt.setString(1, Car.getCar_Type());
			
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
			String date_1=df.format(Car.getApproach_Time());
			
			pstmt.setTimestamp(2, new java.sql.Timestamp(Car.getApproach_Time().getTime()));
			pstmt.setTimestamp(3, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			pstmt.setString(4, Car.getCar_Number());
			pstmt.setString(5, Car.getCar_Place());
			pstmt.setInt(6, Car.getCar_Cost());
			pstmt.executeUpdate();
			pstmt.close();
			return true;
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			return false;
		}
		finally
		{
			try{conn.close();}
			catch(SQLException e){e.printStackTrace();}
		}
	}
	//按时间段查询
	public ArrayList<CarBean> select_time(java.sql.Date t1, java.sql.Date t2)
	{
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rst =null;
		CarBean carbean = new CarBean();
		ArrayList<CarBean> Carlist = new ArrayList<CarBean>();
		try
		{
			conn = dataSource.getConnection();
			pstmt =conn.prepareStatement(select_time_SQL);
			pstmt.setDate(1, t1);
			pstmt.setDate(2, t2);
			rst = pstmt.executeQuery();
			while(rst.next())
			{
				carbean.setCar_Type(rst.getString("car_Type"));
				carbean.setApproach_Time(rst.getDate("approach_Time"));
				carbean.setLeaving_Time(rst.getDate("leaving_Time"));
				carbean.setCar_Number(rst.getString("car_Number"));
				carbean.setCar_Place(rst.getString("car_Place"));
				carbean.setCar_Cost(rst.getInt("car_Cost"));
				Carlist.add(carbean);
			}
			rst.close();
			pstmt.close();
			return Carlist;
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			return null;
		}
		finally
		{
			try{conn.close();}
			catch(SQLException e){e.printStackTrace();}
		}
	}
	//按车辆类型查询
	public ArrayList<CarBean> select_type(String car_Type)
	{
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rst =null;
		
		ArrayList<CarBean> Carlist = new ArrayList<CarBean>();
		try
		{
			conn = dataSource.getConnection();
			pstmt =conn.prepareStatement(select_type_SQL);
			pstmt.setString(1, car_Type);
			rst = pstmt.executeQuery();
			while(rst.next())
			{
				CarBean carbean = new CarBean();
				carbean.setCar_Type(rst.getString("car_Type"));
				carbean.setApproach_Time(rst.getTimestamp("approach_Time"));
				carbean.setLeaving_Time(rst.getTimestamp("leaving_Time"));
				carbean.setCar_Number(rst.getString("car_Number"));
				carbean.setCar_Place(rst.getString("car_Place"));
				carbean.setCar_Cost(rst.getInt("car_Cost"));
				Carlist.add(carbean);
			}
			rst.close();
			pstmt.close();
			return Carlist;
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			return null;
		}
		finally
		{
			try{conn.close();}
			catch(SQLException e){e.printStackTrace();}
		}
	}
	//按车牌号查询
	public ArrayList<CarBean> select_number(String car_Number)
	{
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rst =null;
		CarBean carbean = new CarBean();
		ArrayList<CarBean> Carlist = new ArrayList<CarBean>();
		try
		{
			conn = dataSource.getConnection();
			pstmt =conn.prepareStatement(select_time_SQL);
			pstmt.setString(1, car_Number);
			rst = pstmt.executeQuery();
			while(rst.next())
			{
				carbean.setCar_Type(rst.getString("car_Type"));
				carbean.setApproach_Time(rst.getDate("approach_Time"));
				carbean.setLeaving_Time(rst.getDate("leaving_Time"));
				carbean.setCar_Number(rst.getString("car_Number"));
				carbean.setCar_Place(rst.getString("car_Place"));
				carbean.setCar_Cost(rst.getInt("car_Cost"));
				Carlist.add(carbean);
			}
			rst.close();
			pstmt.close();
			return Carlist;
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			return null;
		}
		finally
		{
			try{conn.close();}
			catch(SQLException e){e.printStackTrace();}
		}
	}
	private static final String select_value_SQL = "select * from cost_table";
	//遍历价格表
	public ArrayList<Price> select_value()
		{
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rst =null;
			ArrayList<Price> pc = new ArrayList<Price>();
			try
			{
				conn = dataSource.getConnection();
				pstmt =conn.prepareStatement(select_value_SQL);
				rst = pstmt.executeQuery();
				while(rst.next())
				{
					Price price = new Price();
					price.setTime(rst.getString(1));
					price.setCost(rst.getInt(2));
					
					pc.add(price);
				}
				rst.close();
				pstmt.close();
				return pc;
			}
			catch(SQLException se)
			{
				se.printStackTrace();
				return null;
			}
			finally
			{
				try{conn.close();}
				catch(SQLException e){e.printStackTrace();}
			}
		}

	public ArrayList<CarBean> Query(String car_Approach_Time,String car_Leaving_Time,String car_Number,String car_Place,String car_Type,String car_Cost)
	{
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rst =null;
		
		ArrayList<CarBean> Carlist = new ArrayList<CarBean>();
		boolean flag=false;
		String sql="select * from old_Pcondition where ";
		if(car_Approach_Time!=""){
			sql+=" approach_Time>='"+car_Approach_Time+"' ";
			flag=true;
			}
			if(car_Leaving_Time!=""){
			if(flag){
				sql+=" and ";
			}
			sql+=" leaving_Time<='"+car_Leaving_Time+"' ";
			flag=true;
			}
			if(car_Number!=""){
				if(flag){
					sql+=" and ";
				}
				sql+=" car_Number='"+car_Number+"' ";
				flag=true;
			}
			if(car_Place!=""){
				if(flag){
					sql+=" and ";
				}
				sql+=" car_Place='"+car_Place+"' ";
				flag=true;
			}
			if(car_Type!=""){
				if(flag){
					sql+=" and ";
				}
				sql+=" car_Type='"+car_Type+"' ";
				flag=true;
			}
			if(car_Cost!=""){
				if(flag){
					sql+=" and ";
				}
				if(car_Cost.equals("10元以下")){
				sql+=" car_Cost<'"+car_Cost+"' ";
				}
				if(car_Cost.equals("10-20元")){
					sql+=" car_Cost>='10' and car_Cost<20 ";
					}
				if(car_Cost.equals("20-30元")){
					sql+=" car_Cost>='20' and car_Cost<30 ";
					}
				if(car_Cost.equals("30-40元")){
					sql+=" car_Cost>='30' and car_Cost<40 ";
					}
				if(car_Cost.equals("40元以上")){
					sql+=" car_Cost>='40' ";
					}
				flag=true;
			}
		try
		{
			conn = dataSource.getConnection();
			pstmt =conn.prepareStatement(sql);
			rst = pstmt.executeQuery();
			while(rst.next())
			{
				CarBean carbean = new CarBean();
				carbean.setCar_Type(rst.getString("car_Type"));
				carbean.setApproach_Time(rst.getTimestamp("approach_Time"));
				carbean.setLeaving_Time(rst.getTimestamp("leaving_Time"));
				carbean.setCar_Number(rst.getString("car_Number"));
				carbean.setCar_Place(rst.getString("car_Place"));
				carbean.setCar_Cost(rst.getInt("car_Cost"));
				Carlist.add(carbean);
			}
			rst.close();
			pstmt.close();
			return Carlist;
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			return null;
		}
		finally
		{
			try{conn.close();}
			catch(SQLException e){e.printStackTrace();}
		}
	}

	
	public boolean update_Price(int s1,String s2)
	{
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rst =null;
		ArrayList<Price> pc = new ArrayList<Price>();
		try
		{
			conn = dataSource.getConnection();
			pstmt =conn.prepareStatement("update cost_table set cost = ? where time = ?");
			pstmt.setInt(1, s1);
			pstmt.setString(2, s2);
			pstmt.executeUpdate();
			pstmt.close();
			return true;
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
			return false;
		}
	}
	public ArrayList<CarBean> Query_Now(String car_Approach_Time,String car_Number,String car_Place,String car_Type)
	{
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rst =null;
		
		ArrayList<CarBean> Carlist = new ArrayList<CarBean>();
		boolean flag=false;
		String sql="select * from now_Pcondition where ";
		if(car_Approach_Time!=""){
			sql+=" approach_Time>='"+car_Approach_Time+"' ";
			flag=true;
			}
			if(car_Number!=""){
				if(flag){
					sql+=" and ";
				}
				sql+=" car_Number='"+car_Number+"' ";
				flag=true;
			}
			if(car_Place!=""){
				if(flag){
					sql+=" and ";
				}
				sql+=" car_Place='"+car_Place+"' ";
				flag=true;
			}
			if(car_Type!=""){
				if(flag){
					sql+=" and ";
				}
				sql+=" car_Type='"+car_Type+"' ";
				flag=true;
			}
		try
		{
			conn = dataSource.getConnection();
			pstmt =conn.prepareStatement(sql);
			rst = pstmt.executeQuery();
			while(rst.next())
			{
				CarBean carbean = new CarBean();
				carbean.setCar_Type(rst.getString("car_Type"));
				carbean.setApproach_Time(rst.getTimestamp("approach_Time"));
				carbean.setCar_Number(rst.getString("car_Number"));
				carbean.setCar_Place(rst.getString("car_Place"));
				Carlist.add(carbean);
			}
			rst.close();
			pstmt.close();
			return Carlist;
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			return null;
		}
		finally
		{
			try{conn.close();}
			catch(SQLException e){e.printStackTrace();}
		}
	}
	
	public ArrayList<Integer> Analysis_Number(String time,String method)	
	{
		if(time.equals(""))
			return null;
		
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rst =null;
		ArrayList<Integer> number = new ArrayList<Integer>();
		String sql = "select count(car_Cost) huafei"
					 +" from old_Pcondition"
					 +" where "+method+" between '"+time+" 00:00:00' and '"+time+" 01:00:00' ";
		for(int i=1;i<23;i++)
		{
			if(i<9)
			{
			sql = sql+" UNION ALL"
		 			 +" select count(car_Cost)"
					 +" from old_Pcondition"
					 +" where "+method+" between '"+time+" 0"+i+":00:00' and '"+time+" 0"+(i+1)+":00:00'";
			}
			if(i==9)
			{
				sql = sql+" UNION ALL"
			 			 +" select count(car_Cost)"
						 +" from old_Pcondition"
						 +" where "+method+" between '"+time+" 0"+i+":00:00' and '"+time+" "+(i+1)+":00:00'";
				
			}
			if(i>9)
			{
				sql = sql+" UNION ALL"
			 			 +" select count(car_Cost)"
						 +" from old_Pcondition"
						 +" where "+method+" between '"+time+" "+i+":00:00' and '"+time+" "+(i+1)+":00:00'";
				
			}
		}
		
		sql = sql+" UNION ALL"
	 			 +" select count(car_Cost)"
				 +" from old_Pcondition"
				 +" where "+method+" between '"+time+" 23:00:00' and '"+time+" 23:59:59'";
		
		try
		{
			conn = dataSource.getConnection();
			pstmt =conn.prepareStatement(sql);
			
			rst = pstmt.executeQuery();
			while(rst.next())
			{
				int temp = 0;
				temp = rst.getInt(1);
				number.add(temp);
			}
			pstmt.close();
			return number;
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
			return null;
		}
		
		
	}
	
	public ArrayList<String> Analysis_Sum_Car_Cost(int days,String times)
	{
		
		if(days==0)
			return null;
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rst =null;
		ArrayList<String> number = new ArrayList<String>();
		String sql = "select sum(car_Cost) huafei"
					 +" from old_Pcondition"
					 +" where leaving_Time between '"+times+"-01' and '"+times+"-02' ";
		for(int i=2;i<days;i++)
		{
			if(i<9)
			{
				sql = sql + " union all"
						  +" select sum(car_Cost) "
						  + " from old_Pcondition"
						  + " where leaving_Time between '"+times+"-0"+i+"' and '"+times+"-0"+(i+1)+"'";
			}
			if(i==9)
			{
				sql = sql + " union all"+" select sum(car_Cost) "
						  + " from old_Pcondition"
						  + " where leaving_Time between '"+times+"-0"+i+"' and '"+times+"-"+(i+1)+"'";
			}
			if(i>9)
			{
				sql = sql + " union all"+" select sum(car_Cost) "
						  + " from old_Pcondition"
						  + " where leaving_Time between '"+times+"-"+i+"' and '"+times+"-"+(i+1)+"'";
			}
		}
		try
		{
			conn = dataSource.getConnection();
			pstmt =conn.prepareStatement(sql);
			
			rst = pstmt.executeQuery();
			while(rst.next())
			{
				String temp = "";
				temp = rst.getString(1);
				number.add(temp);
			}
			pstmt.close();
			return number;
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
			return null;
		}	
	}
	
	public ArrayList<Integer> Analysis_Sum_Car_Type(String time,int days)
	{
		if(time.equals(""))
			return null;
		
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rst =null;
		ArrayList<Integer> number = new ArrayList<Integer>();
		String sql = " select count(car_Type)"
					+" from old_Pcondition"
					+" where car_Type='四座车辆' and leaving_Time between '"+time+"-01' and '"+time+"-"+days+"' "
					+" union all"
					+" select count(car_Type)"
					+" from old_Pcondition"
					+" where car_Type='六座车辆' and leaving_Time between '"+time+"-01' and '"+time+"-"+days+"' "
					+" union all"
					+" select count(car_Type)"
					+" from old_Pcondition"
					+" where car_Type='十座车辆' and leaving_Time between '"+time+"-01' and '"+time+"-"+days+"' "
					+" union all"
					+" select count(car_Type)"
					+" from old_Pcondition"
					+" where car_Type='特殊车辆' and leaving_Time between '"+time+"-01' and '"+time+"-"+days+"' "
					+" union all"
					+" select count(car_Type)"
					+" from old_Pcondition"
					+" where car_Type='卡车' and leaving_Time between '"+time+"-01' and '"+time+"-"+days+"' ";
		try
		{
			conn = dataSource.getConnection();
			pstmt =conn.prepareStatement(sql);
			
			rst = pstmt.executeQuery();
			while(rst.next())
			{
				int temp = 0;
				temp = rst.getInt(1);
				number.add(temp);
			}
			pstmt.close();
			return number;
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
			return null;
		}
	}
	
	
}
