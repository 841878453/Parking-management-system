<html>
<head>
<meta charset="utf-8">
<title>停车管理系统</title>
<link rel="stylesheet" href="css/login_Style.css">
<link href="css/flickerplate.css" rel="stylesheet">
<link rel="stylesheet" href="css/xcConfirm.css" />
<style>
.flicker-pic {
	position: absolute;
	left: 50px;
	top: 90px;
	width: 800px;
	height: 360px;
	margin-left: auto;
	margin-right: auto;
}
</style>
<script src="js/jquery-1.8.3.min.js"></script>
<script src="js/modernizr-custom-v2.7.1.min.js"></script>
<script src="js/jquery-finger-v0.1.0.min.js"></script>
<script src="js/flickerplate.min.js"></script>
<script src="js/xcConfirm.js"></script>
<script>
	$(function() {
		$('.flicker-pic').flicker();
	});
	
</script>
</head>
<%
	String message = "";
	String username = "";
	String password = "";
	String checked = "";
	if (session.getAttribute("message") != null) {
		message = (String) session.getAttribute("message");
	}
	Cookie cookie;
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
			if (cookie.getName().equals("park_Username")) {
				username = cookie.getValue().split("-")[0];
				password = cookie.getValue().split("-")[1];
				checked = "checked";
			}
		}
	}
%>
<body>
	<div class="flicker-pic">
		<ul>
			<li data-background="img/index1.jpg"></li>
			<li data-background="img/index2.jpg"></li>
			<li data-background="img/index3.jpg"></li>
		</ul>
	</div>

	<section class="container">
		<div class="title">
			<h>停车管理系统</h>
		</div>
		<div class="login">
			<h1>用户登录</h1>
			<form method="post" action="Judge">
				<p>
					<input type="text" name="username" value="<%=username%>"
						placeholder="账号">
				</p>
				<p>
					<input type="password" name="password" value="<%=password%>"
						placeholder="密码">
				</p>
				<p class="remember_me">
					<label> <input type="checkbox" name="remember_me"
						value="remember" id="remember_me" <%=checked%>> 记住密码 
				</p>
				<p class="submit">
					<input type="submit" name="commit" value="登录">
				</p>
			</form>
		</div>

		<div class="login-help">
			<p>
				忘记密码? <a href="Modify.jsp">点击修改</a>.
			</p>
		</div>
	</section>
	<script type="text/javascript">
	var message=  "<%=message%>";
		if (message == "账号或者密码错误!") {
			window.wxc.xcConfirm(message, window.wxc.xcConfirm.typeEnum.error);
		}
	</script>
</body>
<%
	session.removeAttribute("message");
%>
</html>
