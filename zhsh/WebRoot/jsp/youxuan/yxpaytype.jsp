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
	<title>选择支付方式</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/youxuan/zxzf.css">
	<script src="js/jquery-1.8.0.min.js"></script>
</head>
<body>
	<header>
		<div class="goback" onclick="backreturn()">‹</div>
		<div class="title">
			 <div class="djs">
		        <h6>在线支付</h6>
	    	</div>
		</div>
	</header>
	<script type="text/javascript">
	function backreturn(){
		window.history.back();
	}
	</script>
	<div class="cont">
			<div class="tit">
			请选择支付方式
			<span >支付金额：<span class="col">￥${pd.zongjia_money}</span></span>
 	</div>
	<a onclick="payMoney('wx_pub')" class="payway">
		<img src="img/wechattb.png" >
 		<p>微信支付</p>
  	</a>
	<!-- <a href="" class="payway">
		<img src="img/apay.png" alt="">
		<p>支付宝支付</p>
		<p>推荐已安装支付宝客户端的用户使用</p>
	</a> -->
	</div>
	<script src="js/ping/pingpp.js" type="text/javascript"></script>
<script type="text/javascript">
function payMoney(pay_way){
	var pay_name="微信支付";
	var url="";
	$.ajax({
        type:"post",
        url:'<%=basePath%>youxuan/PayOrder.do', 
	  	data:{
  	 		"goodsinfor":"${pd.goodsinfor}", 
   	 		"gopay_type":"${pd.gopay_type}",
   	 		"user_integral":"${pd.user_integral}",
   	 		"pay_way":pay_way,
  	 		"in_jiqi":"5",
  	 		"goods_type":"2"
	  	},                
        dataType:"json",
        async: false,
        success: function(data){
        	if(data.result == "0"){
   				alert(data.message);
   				return
   			}
   			var charge=data.data;
   			var orderno=charge.orderNo;
   			var money=(charge.amount/100).toFixed(2);
   			//支付
			pingpp.createPayment(charge, function(result, err){
			    console.log(result);
			    console.log(err.msg);
			    console.log(err.extra);
			    if (result == "success") {
			    	//alert("支付成功");
			    	window.location.href='<%=basePath%>html_member/payOkGoJsp.do?orderno='+orderno+'&pay_name='+pay_name+'&money='+money;
			        // 只有微信公众账号 wx_pub 支付成功的结果会在这里返回，其他的支付结果都会跳转到 extra 中对应的 URL。
			    } else if (result == "fail") {
			    	alert("支付失败fail");
				        // charge 不正确或者微信公众账号支付失败时会在此处返回
			    } else if (result == "cancel") {
			    	alert("cancel");
			        // 微信公众账号支付取消支付
			    }
			});
		}
    });
}

</script>
</body>
</html>