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
        <meta charset="utf-8">
 		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/style.css">
		<link rel="stylesheet" href="<%=basePath%>css/htmlmember/styles.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery.min.js"></script>
		<script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
</head>
<body style="background:#ededed">
<nav class="top">
	<a href="<%=basePath%>html_me/textDesc.do?type=1" class="fr" style="margin-right:10px;"><i class="order-two"></i>余额说明</a>
	<a href="<%=basePath%>html_me/goMe.do"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">钱包</div>

</nav>
<div class="wallet">
	<i><img src="../imgmem/wallet_icon_03.png"></i>
	<p>当前余额:   <span style="color:#ff0600"><fmt:formatNumber type="number" value="${pg.now_money}" pattern="0.00" maxFractionDigits="2"/></span></p>
	<div class="wallet-button-div">
		 <a href="<%=basePath%>html_member/gochongzhi.do?member_id=${pd.member_id}" class="wallet-button">充值</a>
		 <a href="<%=basePath%>html_me/gotixian.do?member_id=${pd.member_id}"  class="wallet-button fr">提现</a>
	</div>
</div>
<div class="wallet-content clearfix">
	<p class="wallet-content-title">最近30天收支明细</p>
	<ul>
		 <c:forEach  items="${varList}" var="var" varStatus="vs" >
			<li class="clearfix">
				<div class="fl">
					<p class="fourteen-px">订单类型</p>
					<p>${var.createtime }</p>
				</div>
				<div class="fr">
					<c:if test="${var.consume_type eq '1' }">
						<p class="red fourteen-px">收入+${var.number }</p>
						<p>余额：${var.now_money }</p>
					</c:if>
					<c:if test="${var.consume_type eq '2' }">
						<p class="red fourteen-px">支出${var.number }</p>
						<p>余额：${var.now_money }</p>
					</c:if>
				</div>
			</li>
		</c:forEach>
		<!-- <li class="clearfix">
			<div class="fl">
				<p class="fourteen-px">订单退款</p>
				<p>2016-01-15      17:35:12</p>
			</div>
			<div class="fr">
				<p class="blue fourteen-px">收入+20.30</p>
				<p>余额：52.80</p>
			</div>
		</li> -->
	</ul>
</div>


</body>
</html>
