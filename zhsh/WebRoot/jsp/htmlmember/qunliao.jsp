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
</head>
<body style="background:#ededed;">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">群聊</div>
</nav>
<a href="../jsp/htmlmember/xjqz.jsp" class="ql-top">创建一个群！！！</a>
<div class="rm-me-list clearfix">
	<ul>
		<li class="clearfix"> 
			<a href="../jsp/htmlmember/liaotian.jsp">
				<i class="rm-icon3"></i>
				<div class="rm-me-name">
					<p class="ql-text">啦啦啦</p>
				</div>
			</a>
		</li>
		
	</ul>
</div>

<div class="tjsj-forms">
	<div class="close tjsj-close">×</div>
	<div class="tjsj-forms-div">
		<img src="/imgmem/sjtjlxr_bj_07.png">
		<p>推荐短信已发送正在等待好友注册...继续推荐其他朋友吧</p>
	</div>
</div>

</body>
</html>
