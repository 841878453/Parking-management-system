<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>数据统计</title>
<link rel="stylesheet" href="css/function.css">
<link rel="stylesheet" href="css/Analysis.css">
<link rel="stylesheet" href="css/ion.calendar.css">
</head>
<%@page import="BaseBean.UserBean" import="java.util.*"%>
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
	ArrayList<Integer> approach_Number = null;
	ArrayList<Integer> leaving_Number = null;
	ArrayList<String> Analysis_Month_N = null;
	ArrayList<Integer> Analysis_Car_Type = null;
	String Analysis_Date="每日";
	String Analysis_Month_M="";
	String Analysis_Type_Month="";
	if (session.getAttribute("Analysis_Car_Type") != null) {
		Analysis_Car_Type = (ArrayList<Integer>) session.getAttribute("Analysis_Car_Type");
	}
	if (session.getAttribute("approach_Number") != null) {
		approach_Number = (ArrayList<Integer>) session.getAttribute("approach_Number");
	}
	if (session.getAttribute("leaving_Number") != null) {
		leaving_Number = (ArrayList<Integer>) session.getAttribute("leaving_Number");
	}
	if (session.getAttribute("Analysis_Type_Month") != null) {
		Analysis_Type_Month = (String) session.getAttribute("Analysis_Type_Month");
	}
	if (session.getAttribute("Analysis_Date") != null) {
		Analysis_Date = (String) session.getAttribute("Analysis_Date");
	}
	if (session.getAttribute("Analysis_Month_M") != null) {
		Analysis_Month_M = (String) session.getAttribute("Analysis_Month_M")+"月";
	}
	if (session.getAttribute("Analysis_Month") != null) {
		Analysis_Month_N = (ArrayList<String>) session.getAttribute("Analysis_Month");
	}
%>
<body>
	<div class="wrapper jsc-sidebar-content jsc-sidebar-pulled"
		style="height: 1600px">
		<div>
			<a href="#" class="icon-menu link-menu jsc-sidebar-trigger"
				style="font-family: '黑体';">三</a>
		</div>

		<section class="Analysis_Container">
			<div class="login">
				<h1>每日进出车辆数统计</h1>
				<form method="post" action="Analysis_Number">
					<p>
						<input type="text" name="Analysis_Time" class="date"
							placeholder="日期">
					</p>

					<p class="submit">
						<input type="submit" name="commit" value="查询">
					</p>
				</form>
			</div>
		</section>

		<section class="Analysis_Container1">
			<div class="login">
				<h1>月每日总收入统计</h1>
				<form method="post" action="Analysis_Sum_Car_Cost">
					<p>
						<input type="text" name="Analysis_Month" class="date1"
							placeholder="年月">
					</p>

					<p class="submit">
						<input type="submit" name="commit" value="查询">
					</p>
				</form>
			</div>
		</section>

		<section class="Analysis_Container2">
			<div class="login">
				<h1>月车辆类型统计</h1>
				<form method="post" action="Analysis_Sum_Car_Type">
					<p>
						<input type="text" name="Analysis_Type" class="date1"
							placeholder="年月">
					</p>

					<p class="submit">
						<input type="submit" name="commit" value="查询">
					</p>
				</form>
			</div>
		</section>

		<div id="chart1" class="chart1"></div>
		<div id="chart2" class="chart2"></div>
		<div id="chart3" class="chart3"></div>
	</div>
	<div class="sidebar jsc-sidebar" id="jsi-nav" data-sidebar-options="">
		<ul class="sidebar-list">
			<li>&nbsp&nbsp用户ID:&nbsp&nbsp<%=userName%></li>
			<li><a href="Park_Manage.jsp" target="_self">停车管理</a></li>
			<li><a href="Query_Now.jsp" target="_self">当前信息查询</a></li>
			<li><a href="Query.jsp" target="_self">历史信息查询</a></li>
			<li><a href="Analysis.jsp" class="current" target="_self">数据统计</a></li>
			<li><a href="Price_Modify.jsp" target="_self">价格修改</a></li>
			<li id="logout"><a href="Logout" target="_self">注销登录</a></li>
		</ul>
	</div>



	<script src="js/jquery-1.8.3.min.js"></script>
	<script src="js/sidebar.js"></script>
	<script src="js/highcharts.js"></script>
	<script src="js/exporting.js"></script>
	<script src="js/moment.min.js"></script>
	<script src="js/moment.zh-cn.js"></script>
	<script src="js/ion.calendar.min.js"></script>
	<script src="js/highcharts-3d.js"></script>
	<script>
		$('#jsi-nav').sidebar({
			trigger : '.jsc-sidebar-trigger'
		});
		$(function() {
			
			$('.date').each(function() {
				$(this).ionDatePicker({
					lang : 'zh-cn',
					format : 'YYYY-MM-DD'
				});
			});
			$('.date1').each(function() {
				$(this).ionDatePicker({
					lang : 'zh-cn',
					format : 'YYYY-MM'
				});
			});
		});
	</script>
	<script type="text/javascript">
	Highcharts.setOptions({
	    lang:{
	       contextButtonTitle:"图表导出菜单",
	       decimalPoint:".",
	       downloadJPEG:"下载JPEG图片",
	       downloadPDF:"下载PDF文件",
	       downloadPNG:"下载PNG文件",
	       downloadSVG:"下载SVG文件",
	       drillUpText:"返回 {series.name}",
	       loading:"加载中",	   
	       noData:"没有数据",
	       printChart:"打印图表",
	       resetZoom:"恢复缩放",
	       resetZoomTitle:"恢复图表"
	    }
	});
	
	
		$(function() {
			$('#chart1')
					.highcharts(
							{
								chart: {
						            type: 'column'
						        },
								credits:{enabled:false},
								title : {
									text : '<%=Analysis_Date %>进出场车辆数目统计柱状图',
									x : -20
								//center
								},
								xAxis : {
									categories : [ '0-1','1-2','2-3','3-4','4-5','5-6','6-7','7-8','8-9','9-10','10-11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22','22-23','23-24',]
								},
								yAxis : {
									min : 0,
									title : {
										text : '数量'
									},
									plotLines : [ {
										value : 0,
										width : 1,
										color : '#808080'
									} ]
								},
								tooltip : {
									valueSuffix : '辆'
								},
								legend : {
									layout : 'vertical',
									align : 'right',
									verticalAlign : 'middle',
									borderWidth : 0
								},
								series : [
										{
											name : '进场车辆',
											data : [
	<%if (approach_Number != null) {
				for (int i = 0; i < 23; i++) {
					out.print(approach_Number.get(i) + ",");
				}
				out.print(approach_Number.get(23));
			}%>
		]
										},
										{
											name : '出场车辆',
											color: '#ccff66',
											data : [
	<%if (leaving_Number != null) {
				for (int i = 0; i < 23; i++) {
					out.print(leaving_Number.get(i) + ",");
				}
				out.print(leaving_Number.get(23));
			}%>
		]
										} ]
							});
			
			
			
			$('#chart2')
			.highcharts(
					{
						credits:{enabled:false},
						title : {
							text : '<%=Analysis_Month_M %>每日总收入统计折线图',
							x : -20
						//center
						},
						xAxis : {
							categories : ['1' ]
						},
						yAxis : {
							min : 0,
							title : {
								text : '总收入'
							},
							plotLines : [ {
								value : 0,
								width : 1,
								color : '#808080'
							} ]
						},
						tooltip : {
							valueSuffix : '元'
						},
						legend : {
							layout : 'vertical',
							align : 'right',
							verticalAlign : 'middle',
							borderWidth : 0
						},
						series : [
								{
									name : '总收入',
									color: '#ccff66',
									data : [
<%if (Analysis_Month_N != null) {
	String str="";
		for (int i = 0; i <Analysis_Month_N.size() ; i++) {
			str=Analysis_Month_N.get(i);
			if(str==null){
				out.print("0" + ",");
			}else{
				out.print(str + ",");
			}
			
		}
		if(Analysis_Month_N.get(Analysis_Month_N.size()-1)==null){
			out.print("0");
		}else{
			out.print(leaving_Number.get(Analysis_Month_N.size()-1));
		}
	}%>
]
								} ]
					});
			
			
			
			
			$('#chart3')
			.highcharts(
					{
						chart: {
				            type: 'pie',
				            options3d: {
				                enabled: true,
				                alpha: 45,
				                beta: 0
				            }
				        },
						credits:{enabled:false},
						title : {
							text : '<%=Analysis_Type_Month%>月车辆类型统计饼图',
							x : -20
						//center
						},
						 plotOptions: {
					            pie: {
					                allowPointSelect: true,
					                cursor: 'pointer',
					                depth: 35,
					                dataLabels: {
					                    enabled: true,
					                    format: '{point.name}'
					                }
					            }
					        },
					        series: [{
					            type: 'pie',
					            name: '车辆数',
					            data: [
					                  <%
					                  if(Analysis_Car_Type!=null){
					                	  out.print("{name:'四座车辆', y:"+Analysis_Car_Type.get(0)+", sliced: true,selected: true},");
					                	  out.print("['六座车辆',"+Analysis_Car_Type.get(1)+"],");
					                	  out.print("['十座车辆',"+Analysis_Car_Type.get(2)+"],");
					                	  out.print("['特殊车辆',"+Analysis_Car_Type.get(3)+"],");
					                	  out.print("['卡车',"+Analysis_Car_Type.get(4)+"],");  
					                  }
					                  %>       
					            ]
					        }],
						tooltip : {
							pointFormat: '{series.name}: <b>{point.y}<br>所占比重:<b>{point.percentage:.1f}%</b></b>',
							valueSuffix : '辆'
						}
	
						
					});
		});
	</script>
</body>

<%
	session.removeAttribute("approach_Number");
	session.removeAttribute("leaving_Number");
	session.removeAttribute("Analysis_Date");
	session.removeAttribute("Analysis_Month");
	session.removeAttribute("Analysis_Month_M");
	session.removeAttribute("Analysis_Car_Type");
	session.removeAttribute("Analysis_Type_Month");
%>
</html>