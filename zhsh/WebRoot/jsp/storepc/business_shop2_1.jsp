 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>test</title>

<base href="<%=basePath%>">

<link rel="stylesheet" href="css/ace.min.css" />
<link href="css/bootstrap.min.css" rel="stylesheet" />


<link rel="stylesheet" href="css/storepc/business_shop2.css">
<script src="js/jquery-1.8.0.min.js"></script>
<script src="js/jquery-1.7.2.js"></script>
<script src="js/storepc/business_shop2.js"></script>


</head>
<body>
	<div class="parent">

		<table border="0" cellspacing="0" cellpadding="0">

			<tr>
				<td>小类编号</td>
				<td>小类名称</td>
				<td>市场价</td>
				<td>零售价</td>
				<td>积分率</td>
				<td>库存量</td>
				<td>已消费量</td>
				<td>商品状态</td>
				<td>单品销售状态</td>
				<td>促销规则</td>
			</tr>
			<tr>
				<td>${good.goods_id}</td>
				<td>${good.goods_name}</td>
				<td>${good.market_money}</td>
				<td>${good.retail_money}</td>
				<td>${good.integral_rate}</td>
				<td>${good.stock_number}</td>
				<td>${good.consumption_number}</td>
				<td>${good.goods_status}</td>
				<td>${good.sales_status}</td>
				<td>${good.promotion_type}</td>				
			</tr>



		</table>
	</div>
</body>
</html>