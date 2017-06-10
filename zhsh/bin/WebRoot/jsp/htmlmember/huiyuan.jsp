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
<body style="background:#ededed;">
<nav class="top">
	<a href="<%=basePath%>html_me/textDesc.do?type=9" class="fr" style="margin-right:5px;">会员卡说明</a>
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">我的会员</div>
</nav>

<c:forEach items="${varList}" var="var" >
	<div class="vip-one" style="color:${font_color}">
			<img src="${var.image_url}">
			<div class="vip-div">
			<p class="vip-title">${var.store_name }</p>
			<div class="vip-text">
				<span class="fl">
					<p>经营品项</p>
					<p>${var.name1 }${var.name2 }${var.name3 }</p>
				</span>
				<span class="fr">
					<p>购买次数：${var.shop_number }</p>
					<p>消费金额：${var.total_money }元</p>
				</span>
			</div>
			<div class="clearfix"></div>
			<p class="vip-dress">${var.address }</p>
		</div>
	</div>
</c:forEach>
<!-- <div class="vip-one">
	<img src="/imgmem/vip_06.png">
	<div class="vip-div vip-two">
		<p class="vip-title">牛伯伯潮汕牛肉火锅</p>
		<div class="vip-text">
			<span class="fl">
				<p>经营品项</p>
				<p>美食 酒店 休闲娱乐</p>
			</span>
			<span class="fr">
				<p>购买次数：132</p>
				<p>消费金额：10000元</p>
			</span>
		</div>
		<div class="clearfix"></div>
		<p class="vip-dress vip-dress-two">地址：浙江省杭州市西湖区文三路马腾路1237号</p>
	</div>
</div>
<div class="vip-one">
	<img src="/imgmem/vip_09.png">
	<div class="vip-div">
		<p class="vip-title">世纪华联</p>
		<div class="vip-text">
			<span class="fl">
				<p>经营品项</p>
				<p>美食 酒店 休闲娱乐</p>
			</span>
			<span class="fr">
				<p>购买次数：132</p>
				<p>消费金额：10000元</p>
			</span>
		</div>
		<div class="clearfix"></div>
	</div>
</div> -->
 </body>
</html>
