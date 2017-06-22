<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
		<base href="<%=basePath%>">
        <meta charset="utf-8">
 		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="css/htmlmember/style.css">
		<link rel="stylesheet" href="css/htmlmember/styles.css" type="text/css">
		<script type="text/javascript" src="js/htmlmember/jquery.min.js"></script>
		<script type="text/javascript" src="js/htmlmember/jquery.min.js"></script>
</head>
<body style="background:#ededed">
<nav class="top">
	<a href="html_me/textDesc.do?type=8" class="fr" style="margin-right:10px;"><i class="order-two"></i>积分说明</a>
	<a href="html_me/goMe.do"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">我的积分</div>

</nav>
<div class="wallet">
	<i><img src="imgmem/points_03.png"></i>
	<p>当前积分:   <span style="color:#ff0600">￥<fmt:formatNumber type="number" value="${pg.now_integral}" pattern="0.00" maxFractionDigits="2"/></span>分</p>
	<p class="wdjf-text">1积分=1元钱，平台所有商户均可抵等值现金使用</p>
</div>
<div class="wallet-content clearfix">
	<p class="wallet-content-title">最近30天收支明细</p>
	<ul>
	<c:forEach items="${varList }" var="var">
		<li class="clearfix">
			<div class="fl">
				<p class="fourteen-px">${var.content }</p>
				<p>${var.createtime }</p>
			</div>
			<div class="fr">
				<c:if test="${var.consume_type eq '1' }">
					<p class="red fourteen-px">获赠+${var.number }</p>
				</c:if>
				<c:if test="${var.consume_type eq '2' }">
					<p class="red fourteen-px">支出${var.number }</p>
				</c:if>
			</div>
		</li>
	</c:forEach>
		<!-- <li class="clearfix">
			<div class="fl">
				<p class="fourteen-px">赠送积分给好友[阿明]</p>
				<p>2016-01-15      17:35:12</p>
			</div>
			<div class="fr">
				<p class="blue fourteen-px">支出-17</p>
			</div>
		</li> -->
	</ul>
</div>
</body>
</html>
