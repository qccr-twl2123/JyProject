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
			<span class="fshb-left">红包个数</span>
			<input type="text" class="fshb-input" placeholder="填写赠送积分额"/>
			<span class="fshb-right">分</span>
		</li>
		<li>
			本群共8个人
		</li>
		<li>
			<span class="fshb-left">总积分<span class="pin">拼</span></span>
			<input type="text" class="fshb-input" placeholder="填写赠送积分额"/>
			<span class="fshb-right">分</span>
		</li>
		<li>
			当前为拼手气红包，<span class="blue">改为普通红包</span>
		</li>
		<li>
			<input type="text" style="text-align:left"  class="fshb-input" placeholder="恭喜发财，大吉大利！"/>
		</li>
	</ul>
</div>

	<b class="fshb-number">1250分</b>
	<input type="button" class="fshb-anniu" value="发送红包" />
	<p class="fshb-p">积分1分=1元，在商户消费时抵等值现金</p>

<div class="bj"></div>
<div class="fsjf-forms">
	<div class="close">×</div>
	<p>积分余额不足</p>
	<input type="button" class="fshb-button" value="立即充值" />
</div>
</body>
</html>
