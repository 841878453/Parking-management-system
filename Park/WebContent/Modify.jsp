<html>
<head>
<meta charset="utf-8">
<title>密码修改</title>
<link rel="stylesheet" href="css/login_Style.css">
<link rel="stylesheet" href="css/xcConfirm.css" />
<link href="css/flickerplate.css" rel="stylesheet">
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
if (session.getAttribute("message") != null) {
	message = (String) session.getAttribute("message");
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
		<div class="login">
			<h1>密码修改</h1>
			<form method="post" action="Judge_Modify">
				<p>
					<input type="text" name="username" placeholder="账号">
				</p>
				<p>
					<input type="password" name="password" placeholder="原密码">
				</p>
				<p>
					<input type="password" name="new_password" placeholder="新密码">
				</p>
				<p>
					<input type="password" name="c_new_password" placeholder="确认新密码">
				</p>
				<p class="submit">
					<input type="submit" name="commit" value="修改">
					<input type="button" onclick="javascript:window.location.href='index.jsp'"  value="返回">
				</p>
					
			</form>
		</div>
	</section>
	<script type="text/javascript">
	var message=  "<%=message%>";
		if (message == "修改密码成功!")
			window.wxc.xcConfirm(message, window.wxc.xcConfirm.typeEnum.success);
		if (message == "修改密码出错!") {
			window.wxc.xcConfirm(message, window.wxc.xcConfirm.typeEnum.error);
		}
	</script>
</body>
<%
	session.removeAttribute("message");
%>
</html>
