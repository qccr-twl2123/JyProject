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
	<div style="text-align:center;line-height:40px;color:#fff">聊天信息（6）</div>
</nav>

<div class="liaotianxx-list clearfix">
	<ul>
		<li class="clearfix">
			<div class="ltxx-img"><img src="/imgmem/20130518113434876(1).jpg"></div>
			<p>小明</p>
		</li>
		<li class="clearfix">
			<div class="ltxx-img"><img src="/imgmem/20130518113434876(1).jpg"></div>
			<p>小明</p>
		</li>
		<li class="clearfix">
			<div class="ltxx-img"><img src="/imgmem/20130518113434876(1).jpg"></div>
			<p>小明</p>
		</li>
		<li class="clearfix">
			<div class="ltxx-img"><img src="/imgmem/20130518113434876(1).jpg"></div>
			<p>小明</p>
		</li>
		<li class="clearfix">
			<div class="ltxx-img"><img src="/imgmem/20130518113434876(1).jpg"></div>
			<p>小明</p>
		</li>
		<li>
			<a href="###" class="ltxx-add"><img src="/imgmem/add_03.jpg"></a>
		</li>
		<li>
			<a href="###"  class="ltxx-add"><img style="margin-top:28px;" src="/imgmem/add_06.jpg"></a>
		</li>
	</ul>
	
	
</div>

<article class="rm-list tj-list my-list">
	<ul>
		<li><a href="../jsp/htmlmember/shdz.jsp">群聊名称<b class="z-arrow"></b><span class="fr">我们都是一家人</span></a></li>
		<li><a href="../jsp/htmlmember/shoucang.jsp">群公告<b class="z-arrow"></b></a></li>
	</ul>
</article>

<input type="button" class="lt-delte" value="删除并退出" />

</body>
</html>
