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
	<a href="###" class="fr" style="margin-right:5px;">选择</a>
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">我的红包</div>
</nav>

<div class="hb-list">
	<ul>
		<li>
			<span  class="hb-list-left fl">
				<p>￥<span>8</span></p>
				<p>红包</p>
			</span>
			<div class="hb-list-right fl">
				<span>
					<p>[商家红包]重庆小面武汉路店</p>
					<p><i class="hb-list-time"></i>有效期至2016-01-23</p>
					<p><i class="hb-list-coupon"></i>消费满16元可用，每单限用一张</p>
				</span>
			</div>
		</li>
		<li>
			<span  class="hb-list-left fl">
				<p>￥<span>8</span></p>
				<p>红包</p>
			</span>
			<div class="hb-list-right fl">
				<span>
					<p>[商家红包]重庆小面武汉路店</p>
					<p><i class="hb-list-time"></i>有效期至2016-01-23</p>
					<p><i class="hb-list-coupon"></i>消费满16元可用，每单限用一张</p>
				</span>
			</div>
		</li>
	</ul>
</div>

<p class="hb-gq-footer">没有更多可用劵了丨<a href="../jsp/htmlmember/hb_gq.jsp" class="blue">查看过期红包></a></p>

</body>
</html>
