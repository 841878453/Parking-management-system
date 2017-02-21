<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>价格修改</title>
<link rel="stylesheet" href="css/function.css">
<link rel="stylesheet" href="css/Price_Modify.css">
<link rel="stylesheet" href="css/flipTimer.css">
<link rel="stylesheet" href="css/xcConfirm.css" />
</head>
<%@page import="BaseBean.*" import="java.util.*"%>

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
ArrayList<Price> pc = (ArrayList<Price>)session.getAttribute("Price");
String message_Price_Modify="";
if (session.getAttribute("message_Price_Modify") != null) {
	message_Price_Modify = (String) session.getAttribute("message_Price_Modify");
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
<body>

	<div class="wrapper jsc-sidebar-content jsc-sidebar-pulled" style="height: 800px" >
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
		<section class="Price_Modify_Container">
			<div class="title">
				<h>收费价目修改</h>
			</div>
			<div class="login">
				<h1>价格修改</h1>
				<form method="post" action="Modify_Price">
					<p>
						<input type="text" name="8-20" placeholder="8点-20点每小时收费单价：<%=pc.get(1).getCost() %>元">
					</p>
					<p>
						<input type="text" name="20-次日8" placeholder="20点-次日8点每次收费单价：<%=pc.get(0).getCost() %>元">
					</p>

					<p class="submit">
						<input type="submit" name="commit" value="修改价格">
					</p>
				</form>
			</div>
		</section>


	</div>

	<div class="sidebar jsc-sidebar" id="jsi-nav" data-sidebar-options="">
		<ul class="sidebar-list">
			<li>&nbsp&nbsp用户ID:&nbsp&nbsp<%=userName%></li>
			<li><a href="Park_Manage.jsp" target="_self">停车管理</a></li>
			<li><a href="Query_Now.jsp" target="_self">当前信息查询</a></li>
			<li><a href="Query.jsp" target="_self">历史信息查询</a></li>
			<li><a href="Analysis.jsp" target="_self">数据统计</a></li>
			<li><a href="Price_Modify.jsp" class="current" target="_self">价格修改</a></li>
			<li id="logout"><a href="Logout" target="_self">注销登录</a></li>
		</ul>
	</div>

	<script src="js/jquery-1.8.3.min.js"></script>
	<script src="js/sidebar.js"></script>
		<script src="js/jquery.flipTimer.js"></script>
		<script src="js/xcConfirm.js" ></script>
	<script>
		$('#jsi-nav').sidebar({
			trigger : '.jsc-sidebar-trigger'
		});
		$(function() {
			$('.time').flipTimer({
				seconds : true
			});
		});
		var message_Price_Modify=  "<%=message_Price_Modify%>";
		if(message_Price_Modify=="修改成功!"){
		window.wxc.xcConfirm(message_Price_Modify, window.wxc.xcConfirm.typeEnum.success);
		}
		else if(message_Price_Modify=="修改失败!"){
			window.wxc.xcConfirm(message_Price_Modify, window.wxc.xcConfirm.typeEnum.error);
		}
	</script>
<%session.removeAttribute("message_Price_Modify");%>
</body>
</html>