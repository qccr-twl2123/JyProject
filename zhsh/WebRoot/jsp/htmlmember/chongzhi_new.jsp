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
	<style type="text/css">
	.radioclass{
			opacity: 1;
	}
	</style>
</head>
<body style="background:#ededed;">
<nav class="top">
	<a href="html_me/goMe.do?type=11"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">充值</div>
</nav>
<div class="recharge">
	<span>金额(元): </span>
	<input type="text" class="recharge-input" placeholder="请输入充值金额" id="money" onkeyup="value=value.replace(/[^\.\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
</div>

<div class="recharge-content clearfix">
	<ul>
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
<script type="text/javascript">
var base_inf={
         base_herf:"<%=basePath%>" 
};
</script>
<script src="js/jquery-1.8.0.min.js"></script>
<script src="js/wx/jweixin-1.0.0.js"></script>
<script src="js/wx/zepto.min.js"></script>
<script src="js/htmlmember/weixindemo.js"></script>
<script type="text/javascript">
   	//充值
	function save(){
 			var money = $("#money").val().trim();
	 		if(money == "" || money == null){
				alert("金额不能为空！");
				return ;
			}
	 		//开始微信支付--充值
	   		$.ajax({
				type:"post",
					url:"html_member/paychongzhi.do",
					data:{
						"in_jiqi":"5",
						"money":money,
						"pay_way":"wx_pub"
 					},
					dataType:"json",
					success:function(data){
						if(data.result == "0"){
							alert(data.message);
						}else{
							var map=data.data;
							if(map.return_msg == "OK"){
								callWxJsPay(map.payment_type,map.appId,map.timeStamp,map.nonceStr,map.package,map.signType,map.sign,map.out_trade_no);
		 		        	 }else{
		 		        		 alert(map.return_msg);
		 		        	 }
 						}
 	  				}
			});  
 	}
</script>
</html>
