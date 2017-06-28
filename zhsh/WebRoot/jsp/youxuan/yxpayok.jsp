<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
	<title>支付成功</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/youxuan/normalize.min.css">
	<link rel="stylesheet" href="css/youxuan/zfcg.css">
	<script src="js/jquery-1.8.0.min.js"></script>
 </head>
<header>
		<div class="goback" onclick="goBaoPing()">‹</div>
		<div class="title">
			 <div class="djs">
		        <h6>在线支付</h6>
	    	</div>
		</div>
</header>
<body>
<div class="tit">支付成功</div>
<img src="img/qqimg.png" alt="" class="img">
<p> <span class="col">积分支付:￥${pd.jfmoney eq ''?'0':pd.jfmoney}</span></p>
<c:if test="${!empty pd.wxmoney}">
	<p> <span class="col">微信支付:￥${pd.wxmoney}</span></p>
</c:if>
 <div class="link">
	<a onclick="goBaoPing()"><img src="img/jxqg.png" alt=""></a>
	<a href="html_member/payOkDetailOrder.do?orderno=${pd.orderno}&city_name=${pd.city_name}&area_name=${pd.area_name}"><img src="img/ddxq.png" alt=""></a>
	<h5 class="text">提示：到店后，请将本页面出示给商家收银员，作为付款凭证，返回后可到我的-“提货券”查看详情</h5>
</div>
<script type="text/javascript">
function goBaoPing(){
	window.location.href='<%=basePath%>html_member/goMyYouXuan.do?city_name=${pd.city_name}&area_name=${pd.area_name}';
}
</script>
</body>
</html>