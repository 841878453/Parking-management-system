
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>停车管理</title>
<link rel="stylesheet" href="css/function.css">
<link rel="stylesheet" href="css/flipTimer.css">
<link rel="stylesheet" href="css/Park_Manage.css">
<link rel="stylesheet" href="css/combo.select.css">
<link rel="stylesheet" href="css/xcConfirm.css"/>
</head>
<%@page import="BaseBean.*" import="java.util.*"%>
<%
	String userName = "";
	String msg_success = "";
	String msg_delete = "";
	String Cost_delete = "";
	if (session.getAttribute("msg_success") != null) {
		msg_success = (String) session.getAttribute("msg_success");
	}
	if (session.getAttribute("msg_delete") != null) {
		msg_delete = (String) session.getAttribute("msg_delete");
	}
	if (session.getAttribute("Cost_delete") != null) {
		Cost_delete = (String) session.getAttribute("Cost_delete").toString();
	}
	if (session.getAttribute("userbean") == null) {
		response.sendRedirect("index.jsp");
	} else {
		UserBean userbean = new UserBean();
		userbean = (UserBean) session.getAttribute("userbean");
		userName = userbean.getUserName();
	}
%>
<%
	java.util.Date currentTime = new java.util.Date();//得到当前系统时间
	int month = currentTime.getMonth() + 1;
	int year = currentTime.getYear() + 1900;
	int date = currentTime.getDate();
	int day = currentTime.getDay();
	String str_day = "";
	switch (day) {
		case 1 :
			str_day = "一";
			break;
		case 2 :
			str_day = "二";
			break;
		case 3 :
			str_day = "三";
			break;
		case 4 :
			str_day = "四";
			break;
		case 5 :
			str_day = "五";
			break;
		case 6 :
			str_day = "六";
			break;
		case 0 :
			str_day = "日";
			break;
	}
	String date_CN = year + "年" + month + "月" + date + "日 星期" + str_day;
%>
<%!float pec = 0;
	String pec_str = "";%>
<%
	ArrayList<String> s = (ArrayList<String>) session.getAttribute("car_place");
	ArrayList<CarBean> car_place_number = (ArrayList<CarBean>) session.getAttribute("car_place_number");
	float pec = (float) car_place_number.size() / 100;
	String pec_str = "'" + car_place_number.size() + "/100'";
%>
<body>

	<div class="wrapper jsc-sidebar-content jsc-sidebar-pulled xx"
		style="height: 800px">
		<div>
			<a href="#" class="icon-menu link-menu jsc-sidebar-trigger"
				style="font-family: '黑体';">三</a>
		</div>
		<div class="date_CN">
			<p><%=date_CN%></p>
		</div>
		<div class="time">
			<div class="hours"></div>
			<div class="minutes"></div>
			<div class="seconds"></div>
		</div>
		<div class="bubble_pos">
			<canvas id="waterbubble"></canvas>
		</div>

		<section class="Approach_Container">
			<div class="login">
				<h1>进场管理</h1>
				<form method="post" action="Insert">
					<p>
						<select name="car_Type">
							<option value="">车辆类型</option>
							<option value="四座车辆">四座车辆</option>
							<option value="六座车辆">六座车辆</option>
							<option value="十座车辆">十座车辆</option>
							<option value="卡车">卡车</option>
							<option value="特殊车辆">特殊车辆</option>
						</select>
					</p>

					<p>
						<input type="text" name="car_Number" placeholder="车牌号">
					</p>
					<p>
						<select name="car_Place">
							<option value="">车位</option>
							<optgroup label="A区">
								<%
									if (!s.isEmpty())
										for (int i = 0; i < s.size(); i++) {
											if (s.get(i).toString().indexOf("A") != -1)
												out.print("<option value='" + s.get(i).toString() + "'>" + s.get(i).toString() + "</option>");
										}
								%>
							</optgroup>
							<optgroup label="B区">
								<%
									if (!s.isEmpty())
										for (int i = 0; i < s.size(); i++) {
											if (s.get(i).toString().indexOf("B") != -1)
												out.print("<option value='" + s.get(i).toString() + "'>" + s.get(i).toString() + "</option>");
										}
								%>
							</optgroup>
							<optgroup label="C区">
								<%
									if (!s.isEmpty())
										for (int i = 0; i < s.size(); i++) {
											if (s.get(i).toString().indexOf("C") != -1)
												out.print("<option value='" + s.get(i).toString() + "'>" + s.get(i).toString() + "</option>");
										}
								%>
							</optgroup>
							<optgroup label="D区">
								<%
									if (!s.isEmpty())
										for (int i = 0; i < s.size(); i++) {
											if (s.get(i).toString().indexOf("D") != -1)
												out.print("<option value='" + s.get(i).toString() + "'>" + s.get(i).toString() + "</option>");
										}
								%>
							</optgroup>
							<optgroup label="E区">
								<%
									if (!s.isEmpty())
										for (int i = 0; i < s.size(); i++) {
											if (s.get(i).toString().indexOf("E") != -1)
												out.print("<option value='" + s.get(i).toString() + "'>" + s.get(i).toString() + "</option>");
										}
								%>
							</optgroup>
						</select>
					</p>
					<p class="submit">
						<input type="submit" name="commit" value="进场">
					</p>
				</form>
			</div>

			<div class="login" id="leaving">
				<h1>出场管理</h1>
				<form method="post" action="Delete">
					<p>
						<select name="Number">
							<option value="">车牌号</option>
							<%
								for (int i = 0; i < car_place_number.size(); i++) {
									out.print("<option value='" + car_place_number.get(i).getCar_Number() + "'>"
											+ car_place_number.get(i).getCar_Number() + "</option>");
								}
							%>
						</select>
					</p>

					<p class="submit">
						<input type="submit" name="commit1" value="出场">
					</p>
				</form>
			</div>
		</section>

		<div class="Park_Infor">
			<h1>停车场具体车位信息图</h1>
		</div>
		<div class="Park_Table">
			<table width="300" height="30">
				<%
					boolean flag = false;
					String p = "";
					for (int i = 0; i < 20; i++) {
						out.print("<tr>");
						for (int j = 0; j < 5; j++) {
							if ((i + 1) < 10) {
								p = "0" + (i + 1);
							} else {
								p = "" + (i + 1);
							}
							switch (j) {
								case 0 :
									p = "A" + p;
									break;
								case 1 :
									p = "B" + p;
									break;
								case 2 :
									p = "C" + p;
									break;
								case 3 :
									p = "D" + p;
									break;
								case 4 :
									p = "E" + p;
									break;
							}
							flag = false;
							for (int u = 0; u < car_place_number.size(); u++) {
								if (car_place_number.get(u).getCar_Place().equals(p)) {
									out.print("<td>" + p + "：" + car_place_number.get(u).getCar_Number() + "</td>");
									flag = true;
									break;
								}
							}
							if (!flag) {
								out.print("<td>" + p
										+ "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
										+ "</td>");
							}
						}
						out.print("</tr>");
					}
				%>
			</table>
		</div>
	</div>

	<div class="sidebar jsc-sidebar" id="jsi-nav" data-sidebar-options="">
		<ul class="sidebar-list">
			<li>&nbsp&nbsp用户ID:&nbsp&nbsp<%=userName%></li>
			<li><a href="Park_Manage.jsp" class="current" target="_self">停车管理</a></li>
			<li><a href="Query_Now.jsp" target="_self">当前信息查询</a></li>
			<li><a href="Query.jsp" target="_self">历史信息查询</a></li>
			<li><a href="Analysis.jsp" target="_self">数据统计</a></li>
			<li><a href="Price_Modify.jsp" target="_self">价格修改</a></li>
			<li id="logout"><a href="Logout" target="_self">注销登录</a></li>
		</ul>

	</div>

	<script src="js/jquery-1.8.3.min.js"></script>
	<script src="js/sidebar.js"></script>
	<script src="js/jquery.flipTimer.js"></script>
	<script src="js/waterbubble.js"></script>
	<script src="js/jquery.combo.select.js"></script>
	<script src="js/xcConfirm.js" ></script>
	<script>
		$(function() {
			$('.time').flipTimer({
				seconds : true
			});
			$('#waterbubble').waterbubble({
				data :<%=pec%>,
				txt :<%=pec_str%>,
				textColor : 'rgba(0, 0, 0, 0.8)',
				font : 'bold 20px arial',
				radius : 80
			});

			$('select').comboSelect();
		});
		$('#jsi-nav').sidebar({
			trigger : '.jsc-sidebar-trigger'
		});
		var msg_success=  "<%=msg_success%>";
		if(msg_success=="入场成功!"){
		window.wxc.xcConfirm(msg_success, window.wxc.xcConfirm.typeEnum.success);
		}
		else if(msg_success=="入场失败!"){
			window.wxc.xcConfirm(msg_success, window.wxc.xcConfirm.typeEnum.error);
		}
		var msg_delete=  "<%=msg_delete%>";
		var Cost_delete=  "<%=Cost_delete%>";
		if(msg_delete=="出场成功!"){
		window.wxc.xcConfirm(msg_delete+"<br>停车费用："+Cost_delete+"元", window.wxc.xcConfirm.typeEnum.success);
		}
		else if(msg_delete=="出场失败!"){
			window.wxc.xcConfirm(msg_delete, window.wxc.xcConfirm.typeEnum.error);
		}
	</script>


</body>
<%session.removeAttribute("msg_success");
session.removeAttribute("msg_delete");
session.removeAttribute("Cost_delete");
%>
</html>