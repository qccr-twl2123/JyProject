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
	<div style="text-align:center;line-height:40px;color:#fff">收货地址</div>

</nav>


<div class="clearfix shdz-content">
	<a href="<%=basePath%>html_me/addaddress.do?member_id=${pd.member_id}">
	<span class="xzdz-left shdz-add fl">+</span>
	新增收货地址
	</a>
</div>
<c:forEach items="${addressList }" var="var">
	<div class="clearfix shdz-content">
	 <div>
		<a href="<%=basePath%>html_me/editaddress.do?member_id=${pd.member_id}&member_address_id=${var.member_address_id}" class="fr edit"></a>
		<p style="font-size:16px;color:#333">${var.name }
			<c:if test="${var.sex eq '1'}">男士</c:if>
			<c:if test="${var.sex eq '2'}">女士</c:if>
		 ${var.phone }</p>
		<p>${var.address }</p>
	 </div>
	</div>
</c:forEach>
<!-- <div class="clearfix shdz-content">
	<div>
		<a href="###" class="fr edit"></a>
		<p style="font-size:16px;color:#333">王伟 先生 18525447895</p>
		<p>浙江省杭州市滨江区长河路400号</p>
	</div>
</div> -->


</body>
</html>
