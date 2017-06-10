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
	<a href="../jsp/htmlmember/liaotianxx.jsp" class="fr liaotain-qun"><img src="/imgmem/liaotian_03.png"></a>
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">lalal</div>
</nav>

<div class="liaotian-list">
	<ul>
		<li class="clearfix">
			<span class="rm-tx" style="float:right;margin:0"><img src="/imgmem/20130518113434876(1).jpg"></span>
			<div class="liaotian-text-right fr">
				<i class="liaotian-r-i"></i>
				你知道牛伯伯潮汕牛肉火锅么，我昨天去了，老好吃了。
			</div>
		</li>
		<li>
			<span class="rm-tx" style="margin:0"><img src="/imgmem/20130518113434876(1).jpg"></span>
			<div class="liaotian-text-right liaotian-text-left  fl">
				<i class="liaotian-l-i"></i>
				你知道牛伯伯潮汕牛肉火锅么，我昨天去了，老好吃了。
			</div>
		</li>
	</ul>
</div>



</body>
</html>
