<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
        <meta charset="utf-8">
 		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/style.css">
		<link rel="stylesheet" href="<%=basePath%>css/htmlmember/styles.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/main.js"></script>
</head>
<body style="background:#ededed">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">设置</div>

</nav>

<div class="xzdz-content clearfix">
	<ul>
		<!-- <li class="clearfix">
				<span class="fl">清除图片缓存</span>
				<span class="fr">5.15MB</span>
		</li>
		<li class="clearfix">
				<span class="fl">非Wifi下图片质量</span>
				<span>普通</span>
		</li> -->
		<li class="clearfix">
				<span class="fl">账号安全</span>
		</li>
	</ul>
</div>

<!-- <div class="xzdz-content clearfix">
	<ul>
		<li class="clearfix">
				<span class="fl">关于九鱼</span>
		</li>
	</ul>
</div> -->

<div class="xzdz-content clearfix">
	<ul>
		<li class="clearfix">
				<a href="<%=basePath%>html_me/outLogin.do?member_id=${pd.member_id}&islogin=0&in_jiqi=5" style="text-align:center;color:#ff0600;display:block">退出登录</a>
		</li>
	</ul>
</div>
 </body>
</html>
