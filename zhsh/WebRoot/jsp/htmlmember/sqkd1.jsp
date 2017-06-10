<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
	<base href="<%=basePath%>">
	<title>申请入驻</title>
	<link rel="stylesheet" href="<%=basePath%>css/htmlmember/kd_inf.css" type="text/css">
	<style>
		body,html{height: 100%;margin: 0;overflow-y:scroll; }
	</style>
</head>
<body>
<header>
		<div class="goback" onclick="backreturn()"><img src="https://www.jiuyuvip.com/img/fanhui.png" alt=""></div>
		<div class="title">
			 <div class="djs">
		        <h6 style="font-size: 1rem;">申请入驻</h6>
	    	</div>
		</div>
		<script type="text/javascript">
		function backreturn(){
			window.history.back();
		}
		</script>
</header>
<div style="position: relative;width:100%;height:100%;padding-top: 44px;box-sizing: border-box;">
	<img src="img/loading.png" style="width:100%;height:100%;display:block;">
	<a href="<%=basePath%>html_me/gosqkdtwo.do?member_id=${pd.member_id}" style="display:block;position: absolute;right:0;top:50%;width:35%;"><img src="img/link.png" alt="" style="width:100%;"></a>
</div>
	
</body>
</html>