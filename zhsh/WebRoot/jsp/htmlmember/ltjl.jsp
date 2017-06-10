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
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="css/htmlmember/style.css">
	<link rel="stylesheet" href="css/htmlmember/styles.css" type="text/css">
</head>
<body style="background:#ededed;">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">最新聊天记录</div>
</nav>

<div class="rm-me-list clearfix">
	<ul>
		<li class="clearfix">
			
			<span class="fr">14：56</span>  
			<span class="rm-me-tx">
				<img src="/imgmem/20130518113434876(1).jpg">
				<i class="news"></i>
			</span>
			<div class="rm-me-name">
				<b>曼陀罗</b>
				<p>我是你的老同学啊，加我!</p>
			</div>
		</li>
		<li class="clearfix">
			<span class="fr">14：56</span>  
			<span class="rm-me-tx"><img src="/imgmem/20130518113434876(1).jpg"></span>
			<div class="rm-me-name">
				<b>曼陀罗</b>
				<p>我是你的老同学啊，加我!</p>
			</div>
		</li>
		<li class="clearfix">
			<span class="fr">14：56</span>  
			<span class="rm-me-tx"><img src="/imgmem/20130518113434876(1).jpg"></span>
			<div class="rm-me-name">
				<b>曼陀罗</b>
				<p>我是你的老同学啊，加我!</p>
			</div>
		</li>
	</ul>
</div>

</body>
</html>
