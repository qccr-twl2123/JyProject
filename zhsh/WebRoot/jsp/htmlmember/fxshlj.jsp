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
	<script type="text/javascript" src="js/htmlmember/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/htmlmember/main.js"></script>
</head>
<body style="background:#ededed;">
<nav class="top">
	<a href="javascript:;" class="fr" style="margin-right:5px;">发送</a>
	<a href="index_rm.jsp"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">分享商户连接</div>
</nav>

<div class="searchdiv">
	<input type="text" class="serach" placeholder="输入商家名称">
</div>

<div class="rm-me-list clearfix">
	<ul>
		<li class="clearfix">
			<span class="fr">
				<i class="gx" style="display:block;float:right;margin-top:0;margin-bottom:18px;"></i>
				<p>2016/03/05</p>
			</span>  
			<span class="my-top-tx fl">
				<img src="/imgmem/20130518113434876(1).jpg">
			</span>
			<div class="fxsh-text">
				<b>牛伯伯潮汕牛肉火锅</b>
				<p>我是你的老同学啊，加我!</p>
			</div>
		</li>
		<li class="clearfix">
			<span class="fr">
				<i class="gx" style="display:block;float:right;margin-top:0;margin-bottom:18px;"></i>
				<p>2016/03/05</p>
			</span>  
			<span class="my-top-tx fl">
				<img src="/imgmem/20130518113434876(1).jpg">
			</span>
			<div class="fxsh-text">
				<b>牛伯伯潮汕牛肉火锅</b>
				<p>我是你的老同学啊，加我!</p>
			</div>
		</li>
		
	</ul>
</div>

</body>
</html>
