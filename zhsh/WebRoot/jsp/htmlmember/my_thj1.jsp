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
        <base href="<%=basePath%>">
 		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="css/htmlmember/style.css">
		<link rel="stylesheet" href="css/htmlmember/styles.css" type="text/css">
</head>
<body style="background:#ededed;">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">提货券</div>
</nav>
<c:forEach items="${tihuoList }" var="var">
	<a href="html_me/tihuoByOrderId.do?order_id=${var.order_id}">
		<div class="my-thj-list clearfix"> 
			<c:if test="${var.tihuo_status eq '0' }">
				<span class="fr red fourteen-px">待提货</span>
			</c:if>
			<c:if test="${var.tihuo_status eq '1' }">
				<span class="fr red fourteen-px">已提货</span>
			</c:if>
			<c:if test="${var.tihuo_status eq '99' }">
				<span class="fr red fourteen-px">已退款</span>
			</c:if>
 			<span class="my-top-tx fl"><img src="${var.pictrue_url }" ></span>
			<div class="my-top-text my-thj-text">
				<p>${var.store_name }</p>
				<p>有效期：${var.enddate }</p>
				<p>总价：${var.sale_money }</p>
			</div>
		</div>
	</a>
</c:forEach>

</body>
</html>
