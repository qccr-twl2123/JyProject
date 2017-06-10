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
<body style="background:#ededed">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">切换地址</div>
</nav>
<article class="rm-list tj-list">
	<ul>
		<li><a href="html_member/goAllCityone.do?area_name=${pd.area_name}&city_name=${pd.city_name}">当前城市：${pd.city_name} ${pd.area_name}<b class="z-arrow"></b><span class="fr" style="color:#4594e6">切换</span></a></li>
	</ul>
</article>
<div class="city clearfix">
	<div class="city-list">
		<ul id="areaList">
			<c:forEach items="${varList}" var="areavar">
 				 <li onclick="goShouye(this,'1')">${areavar.area_name}</li>
			</c:forEach>
 		</ul>
	</div>
</div>
<script type="text/javascript" src="js/htmlmember/jquery.min.js"></script>
<script type="text/javascript">
//前往首页
function goShouye(obj,value){
	var area_name=$(obj).html();
  	window.location.href='<%=basePath%>html_member/gouShouYe.do?area_name='+area_name+'&city_name=${pd.city_name}';
}
 </script>
<p></p>
<br>
<p></p>
<br>
<p></p>
<br>
</body>
</html>
