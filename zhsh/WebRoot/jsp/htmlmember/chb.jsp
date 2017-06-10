<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%><!DOCTYPE html>
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
	<div style="text-align:center;line-height:40px;color:#fff">信惠红包</div>
</nav>
<div class="chb-top">
	<span class="my-top-tx"><img src="/imgmem/20130518113434876(1).jpg"></span>
	<p>张三的红包</p>
	<p class="gay">恭喜发财，大吉大利！</p>
	<p><span style="font-size:30px;">0.01</span>分</p>
	<p class="blue">已存入积分</p>
</div>

<p  class="tj-title">已领取3/3个</p>
<div class="rm-me-list clearfix">
	<ul>
		<li class="clearfix">
			<b class="fr">0.01积分</b>  
			<span class="chb-img fl"><img src="/imgmem/tjsjlxr_03.png"></span>
			<div class="rm-me-name">
				<b>曼陀罗</b>
				<p>07-02 22:59</p>
			</div>
		</li>
		<li class="clearfix">
			<b class="fr">0.01积分</b> 
			<span class="chb-img fl"><img src="/imgmem/20130518113434876(1).jpg"></span>
			<div class="rm-me-name">
				<b>曼陀罗</b>
				<p>07-02 22:59</p>
			</div>
		</li>
		<li class="clearfix">
			<b class="fr">0.01积分</b>    
			<span class="chb-img fl"><img src="/imgmem/20130518113434876(1).jpg"></span>
			<div class="rm-me-name">
				<b>曼陀罗</b>
				<p>07-02 22:59</p>
			</div>
		</li>
	</ul>
</div>

</body>
</html>
