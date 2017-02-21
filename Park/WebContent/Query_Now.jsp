<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>当前信息查询</title>
<link rel="stylesheet" href="css/function.css">
<link rel="stylesheet" href="css/Query.css">
<link rel="stylesheet" href="css/jquery.dataTables.css">
<link rel="stylesheet" href="css/ion.calendar.css">
<link rel="stylesheet" href="css/combo.select.css">
</head>
<%@page import="BaseBean.*" import="java.util.ArrayList"%>
<%
	String userName = "";
	if (session.getAttribute("userbean") == null) {
		response.sendRedirect("index.jsp");
	} else {
		UserBean userbean = new UserBean();
		userbean = (UserBean) session.getAttribute("userbean");
		userName = userbean.getUserName();
	}
%>

<%
	ArrayList<CarBean> car_All_Now_Infor = (ArrayList<CarBean>) session.getAttribute("car_Query_Now_Type");
	ArrayList<String> car_Query_Now_Record = (ArrayList<String>) session.getAttribute("car_Query_Now_Record");
	String car_Approach_Time = "";
	String car_Number = "";
	String car_Place = "";
	String car_Type = "";
	if (car_Query_Now_Record != null) {
		car_Approach_Time = car_Query_Now_Record.get(0);
		car_Number = car_Query_Now_Record.get(1);
		car_Place = car_Query_Now_Record.get(2);
		car_Type = car_Query_Now_Record.get(3);
	}
%>
<body>

	<div class="wrapper jsc-sidebar-content jsc-sidebar-pulled"
		style="height: 900px">
		<div>
			<a href="#" class="icon-menu link-menu jsc-sidebar-trigger"
				style="font-family: '黑体';">三</a>
		</div>

		<section class="Query_Container">
			<div class="login">
				<h1>当前信息查询</h1>
				<form method="post" action="Select_Now">
					<p>
						<input type="text" name="car_Approach_Time" class="date"
							placeholder="起始时间" value="<%=car_Approach_Time%>">
					</p>
					<p>
						<input type="text" name="car_Number" placeholder="车牌号"
							value="<%=car_Number%>">
					</p>
					<p>
						<select name="car_Place" class="select1">
							<option value="">车位号</option>
							<%
								String p = "";
								for (int i = 0; i < 5; i++) {
									switch (i) {
										case 0 :
											p = "A";
											break;
										case 1 :
											p = "B";
											break;
										case 2 :
											p = "C";
											break;
										case 3 :
											p = "D";
											break;
										case 4 :
											p = "E";
											break;
									}
									out.print("<optgroup label='" + p + "区'>");
									for (int j = 1; j < 21; j++) {
										p = p.substring(0, 1);
										if (j < 10) {
											p += "0" + j;
										} else {
											p += "" + j;
										}
										out.print("<option value='" + p + "'>" + p + "</option>");
									}
									out.print("</optgroup>");
								}
							%>
						</select>
					</p>
					<p>
						<select name="car_Type" class="select1">
							<option value="">车辆类型</option>
							<option value="四座车辆">四座车辆</option>
							<option value="六座车辆">六座车辆</option>
							<option value="十座车辆">十座车辆</option>
							<option value="卡车">卡车</option>
							<option value="特殊车辆">特殊车辆</option>
						</select>
					</p>

					<p class="submit">
						<input type="submit" name="commit" value="查询">
					</p>
				</form>
			</div>
		</section>

		<div class="Query_Table">
			<table id="table_id" class="display">
				<thead>
					<tr>
						<th>车辆类型</th>
						<th>进场时间</th>
						<th>车牌号</th>
						<th>车位号</th>
					</tr>
				</thead>
				<tbody>
					<%
						if (car_All_Now_Infor != null) {
							for (int i = 0; i < car_All_Now_Infor.size(); i++) {
								out.print("<tr>");
								out.print("<td>" + car_All_Now_Infor.get(i).getCar_Type() + "</td>" + "<td>"
										+ car_All_Now_Infor.get(i).getApproach_Time().toString().substring(0, 19) + "</td>" 
										+ "<td>"+ car_All_Now_Infor.get(i).getCar_Number() + "</td>" + "<td>"
										+ car_All_Now_Infor.get(i).getCar_Place() + "</td>" );
								out.print("</tr>");
							}
						}
						session.removeAttribute("car_Query_Now_Type");
					%>
				</tbody>
			</table>
		</div>



	</div>





	</div>

	<div class="sidebar jsc-sidebar" id="jsi-nav" data-sidebar-options="">
		<ul class="sidebar-list">
			<li>&nbsp&nbsp用户ID:&nbsp&nbsp<%=userName%></li>
			<li><a href="Park_Manage.jsp" target="_self">停车管理</a></li>
			<li><a href="Query_Now.jsp" class="current" target="_self">当前信息查询</a></li>
			<li><a href="Query.jsp"  target="_self">历史信息查询</a></li>
			<li><a href="Analysis.jsp" target="_self">数据统计</a></li>
			<li><a href="Price_Modify.jsp" target="_self">价格修改</a></li>
			<li id="logout"><a href="Logout" target="_self">注销登录</a></li>
		</ul>
	</div>

	<script src="js/jquery-1.8.3.min.js"></script>
	<script src="js/sidebar.js"></script>
	<script src="js/jquery.dataTables.js"></script>
	<script src="js/moment.min.js"></script>
	<script src="js/moment.zh-cn.js"></script>
	<script src="js/ion.calendar.min.js"></script>
	<script src="js/jquery.combo.select.js"></script>
	<script>
		$(document).ready(function() {
			$('#table_id').DataTable({
				"lengthMenu" : [ [ 10, 15, 20, 25 ], [ 10, 15, 20, 25 ] ],
				"oLanguage" : {
					"sProcessing" : "处理中...",

					"sLengthMenu" : "显示_MENU_项结果",
					"sZeroRecords" : "没有匹配结果",
					"sInfo" : "显示第_START_至_END_项结果，共_TOTAL_项",
					"sInfoEmpty" : "显示第0至0项结果，共0项",
					"sInfoFiltered" : "(由_MAX_项结果过滤)",
					"sInfoPostFix" : "",
					"sSearch" : "搜索:",
					"sUrl" : "",
					"sEmptyTable" : "无数据",
					"sLoadingRecords" : "载入中...",
					"sInfoThousands" : ",",
					"oPaginate" : {

						"sFirst" : "首页",
						"sPrevious" : "上页",
						"sNext" : "下页",
						"sLast" : "末页"
					},

					"oAria" : {

						"sSortAscending" : ":以升序排列此列",
						"sSortDescending" : ":以降序排列此列"
					}
				}
			});
		});
		$('#jsi-nav').sidebar({
			trigger : '.jsc-sidebar-trigger'
		});
		$(function() {
			$('.select1').comboSelect();
			$('.date').each(function() {
				$(this).ionDatePicker({
					lang : 'zh-cn',
					format : 'YYYY-MM-DD'
				});
			});
		});
	</script>
	<%
		session.removeAttribute("car_Query_Now_Record");
	%>
</body>
</html>