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
		<link rel="stylesheet" href="css/htmlmember/css3.css" media="screen" type="text/css" />

</head>
<body style="background:#dedede;">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">创建群组</div>
</nav>

<div class="khpj-div clearfix">
	<span class="fl">群组名称：</span>
	<input type="text" class="recharge-input" style="float:left" placeholder="请输入群组名称"/>
</div>

<textarea class="khpj-text" >群组描述：</textarea>
<div class="khpj-div">是否公开
<div class="switch demo3">
	<input type="checkbox">
	<label><i></i></label>
</div>
</div>

<div class="khpj-div">是否需要验证
<div class="switch demo3">
	<input type="checkbox">
	<label><i></i></label>
</div>
</div>


<input type="button" class="recharge-sure" value="添加联系人" />
</body>
</html>

