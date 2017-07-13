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
 	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/style.css">
	<link rel="stylesheet" href="<%=basePath%>css/htmlmember/styles.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/htmlmember/main.js"></script>
	<style type="text/css">
	.radioclass{
			opacity: 1;
	}
	</style>
</head>
<body style="background:#ededed;">
<nav class="top">
	<a href="<%=basePath%>html_me/listPurse.do?member_id=${pd.member_id}"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">充值</div>
</nav>
<div class="recharge">
	<span>金额(元): </span>
	<input type="text" class="recharge-input" placeholder="请输入充值金额" id="money" onkeyup="value=value.replace(/[^\.\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
</div>

<div class="recharge-content clearfix">
	<ul>
		<li>请选择支付方式</li>
	 	<!-- <li>
			<span class="pay_list_c1 on fr">
				<input type="radio"  name="paylist" value="1" class="radioclass" onclick="payway('alipay_wap')">
			</span>
			<i class="qq"></i>
			<div class="recharge-text">
				<p>支付宝支付</p>
 			</div>
 		</li>  -->
		<li>
			<span class="pay_list_c1 fr">
				<input type="radio" checked name="paylist" value="2" class="radioclass" onclick="payway('wx_pub')">
			</span>
			<i class="weixin"></i>
			<div class="recharge-text">
 				<p>微信支付</p>
			</div>
		</li>
	</ul>
</div>
<input class="recharge-sure" type="button" value="确认充值"  onclick="save()"/>
</body>
<script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
 <script src="<%=basePath%>js/ping/pingpp.js" type="text/javascript"></script>
<script type="text/javascript">
 	//更新支付方式
   	var channel="wx_pub";
    function payway(value){
    	channel=value;
    }
   	//充值
	function save(){
		//var num = $('input:radio:checked').val();//获取用户选择的是支付宝支付还是微信支付
		//alert(num);
		
		var money = $("#money").val().trim();
 		if(money == "" || money == null){
			alert("金额不能为空！");
			return ;
		}
 		if("${pd.member_id}" == null){
 			return;
 		}
 		var url='http://www.jiuyuvip.com/html_member/toLogin.do';
 		//var url='https://www.jiuyuvip.com/html_me/goMe.do?type=11';
 		if(channel == "wx_pub"){
 			//获取charge
 	   		$.ajax({
 				type:"post",
 					url:"<%=basePath%>app_pay_history/thirdPartyCz.do",
 					data:"in_jiqi=5&money="+money+"&pay_way="+channel+"&member_id="+"${pd.member_id}"+"&url="+url,
 					dataType:"json",
 					success:function(data){
 						var charge=data.data;
 						if(charge == ""){
 							alert("支付渠道未开通");
 						}else{
 							//支付
 							pingpp.createPayment(charge, function(result, err){
 							    console.log(result);
 							    console.log(err.msg);
 							    console.log(err.extra);
 							    if (result == "success") {
 							    	alert("支付成功");
 							    	window.location.href="<%=basePath%>html_me/goMe.do?type=11";  
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
 	  				}
 			});  
 		}else{//非微信公众号支付
 			window.locahost.href='<%=basePath%>pay.html';
 		}
 	}
</script>
</html>
