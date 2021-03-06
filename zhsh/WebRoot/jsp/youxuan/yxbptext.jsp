<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>爆品说明</title>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="css/youxuan/normalize.min.css">
    <link rel="stylesheet" href="css/youxuan/hsd_qgsm.css">
</head>
<body>
	<c:if test="${pd.type eq '2' }">
		<div class="title">
	        <a href="javascript:window.history.back();" class="fanhui">‹</a>
	        <h6>抢购说明</h6>
	    </div>
	</c:if>
     <div class="gouwuche marg">
        <img src="img/qg_title_02.png" alt="">
    </div>
    <div class="cont">
        <p>优选爆品是九鱼网推出的重要优惠模块之一，通过精选商品+深度折扣+限时限量抢购的模式，旨在帮助消费者获得更实惠的消费、更佳的购物体验和抢购的乐趣。</p>
        <img src="img/banner_03.jpg" alt="">
    </div>
    <div class="gouwuche">
        <img src="img/qg_title_03.png" alt="">
        <p>添加到购物车的商品必须在30分钟之内完成支付，逾期将自动清空购物车，清空后库存将进行释放，其他会员均有机会再次抢购。</p>
    </div>
    <div class="gouwuche">
        <img src="img/qg_title_06.png" alt="">
        <p>成功下载“九鱼网”APP并免费注册九鱼网会员后，在APP内完成购买和支付。</p>
    </div>
    <div class="gouwuche">
        <img src="img/qg_title_08.png" alt="">
        <p>支持九鱼积分、九鱼钱包余额、支付宝、微信支付等购买方式。</p>
    </div>
    <div class="gouwuche">
        <img src="img/qg_title_10.png" alt="">
        <p>在APP上支付成功后，将生成一张提货券，凭提货券到商家指定的地点进行提货。</p>
    </div>
    <div class="gouwuche">
        <img src="img/qg_title_12.png" alt="">
        <p>完成购买后，会员须到实体店提货，提货前，请务必验视商品或服务质量，如需退货，可当场向商家提出申请，由商家在APP上操作退款，一旦进行提货操作，系统将不支持退货操作。如需更换商品或服务，请与商家协商解决。</p>
    </div>
    <div class="gouwuche">
        <img src="img/qg_title_15.png" alt="">
        <p class="tuikuan">退款成功后，退款金额在九鱼钱包余额中查看，余额可用于再次消费使用，也可提现到支付宝或银行卡。</p>
    </div>
</body>
</html>