<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
	<a href="###" class="fr" style="margin-right:10px;">优惠说明</a>
	<a href="../jsp/htmlmember/index_my.jsp"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">优惠买单</div>
</nav>

<div class="yh-title clearfix">
	<ul>
		<li><a href="../jsp/htmlmember/yhmd.jsp">按总金额</a></li>
		<li><a href="../jsp/htmlmember/yhmd1.jsp" class="yhmd-current">按类别金额</a></li>
	</ul>
</div>

<div class="yh-content clearfix">
	<p>世纪联华超市（滨江店）</p>
	<ul>
		<li>
			<span><span class="green">生鲜</span>消费金额  :</span>
			<span class="red">36.80元</span>
			<span class="fr gay">积分率：2%</span>
		</li>
		<li>
			<span><span class="green">生鲜</span>消费金额  :</span>
			<span class="red">36.80元</span>
			<span class="fr gay">积分率：2%</span>
		</li>
		<li>
			<span><span class="green">生鲜</span>消费金额  :</span>
			<span class="red">36.80元</span>
			<span class="fr gay">积分率：2%</span>
		</li>
		<li>
			<p class="fourteen-px">优惠明细：</p>
			<p class="gay">
				<span>满200元可用红包20元</span>
				<span class="blue fr">-20.00</span>
			</p>
			<p class="gay">
				<span>积分</span>
				<span class="red fr">+48.00</span>
			</p>

		</li>
		<li>
			 <span class="fr gay">已优惠50元</span>
			<div class="fourteen-px center"><b>实付金额：￥682.00</b></div>    
		</li>
		<li>
			<span class="pay_list_c1 fl">
				<input type="radio" checked="checked" name="paylist" value="1" class="radioclass">
			</span>
			<span class="height ">
				积分支付：56.80
			</span>
			<span class="fr height gay">积分余额125.20分</span>
		</li>
	</ul>
	
</div>

<div class="yhm-footer">
	<a href="###">
		<p>现金支付</p>
		<p>0</p>
	</a>
	<a href="###" class="fr">
		<p>在线支付</p>
		<p>0</p>
	</a>
</div>

</body>
</html>
 --%>