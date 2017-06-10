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
<body style="background:#fff;">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">绑定新手机号</div>
</nav>

<ul class="register-list">
	<li>
		<input class="land-phone" type="text"  style="text-align:left;padding-left:10px;"  placeholder="输入新的手机号码"/>
		<input type="button" value="立即验证" class="register-anniu">
	</li>
	<li>
		<input class="land-phone" type="text"  style="text-align:left;padding-left:10px;"  placeholder="请输入短信中的验证码"/>
	</li>
	<li><a href="../jsp/htmlmember/index.jsp"><input type="button" style="width:40%;" class="Login-button" value="绑定" /></a></li>
</ul>
</body>
</html>
