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
	<div style="text-align:center;line-height:40px;color:#fff">发送积分红包</div>
</nav>

<div class="fshb-top">
	可用积分：9999分
</div>

<div class="fshb-list">
	<ul>
		<li>
			<span class="fshb-left">积分</span>
			<input type="text" class="fshb-input" placeholder="填写赠送积分额"/>
			<span class="fshb-right">分</span>
		</li>
		<li>
			<input type="text" style="text-align:left"  class="fshb-input" placeholder="恭喜发财，大吉大利！"/>
		</li>
	</ul>
</div>

	<b class="fshb-number">1250分</b>
	<input type="button" class="fshb-anniu" value="发送红包" />
	<p class="fshb-p">积分1分=1元，在商户消费时抵等值现金</p>
</body>
</html>
