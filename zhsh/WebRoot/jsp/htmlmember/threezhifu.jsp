<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
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
	<script type="text/javascript" src="js/htmlmember/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/htmlmember/main.js"></script>
	<style type="text/css">
	.radioclass{
	    opacity: 1;
	}
	</style>
 </head>
<body style="background:#ededed;">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">支付</div>
</nav>
<div class="recharge">
	<span>金额(元):<fmt:formatNumber type="number" value="${pd.actual_money}" pattern="0.00" maxFractionDigits="2"/>  </span>
 </div>

<div class="recharge-content clearfix">
	<ul>
		<li>请选择支付方式</li>
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
<input class="recharge-sure surepay" type="button" value="确认支付"  onclick="save()"/>


<form action="" method="post" id="Form" name="Form">
	<input type="hidden" name="session_orderid" value="${session_orderid}">
	<input type="hidden" id="sale_money" value="${pd.sale_money }" name="sale_money"/> 
	<input type="hidden" id="no_discount_money" value="${pd.no_discount_money }" name="no_discount_money"/> 
	<input type="hidden" id="desk_no" value="${pd.desk_no }" name="desk_no"/> 
	<input type="hidden" id="discount_money" value="${pd.discount_money }" name="discount_money"/> 
	<input type="hidden" id="actual_money" value="${pd.actual_money }" name="actual_money"/> 
	<input type="hidden" id="user_balance" value="${pd.user_balance }" name="user_balance"/> 
	<input type="hidden" id="user_integral" value="${pd.user_integral }" name="user_integral"/> 
	<input type="hidden" id="pay_type" value="3" name="pay_type" /> 
	 <input type="hidden" id="get_integral" value="${pd.get_integral }" name="get_integral"/> 
 	<input type="hidden" id="discount_content" value="${pd.discount_content }" name="discount_content"/><!-- 优惠明细 -->
	<input type="hidden" id="redpackage_id"  value="${pd.redpackage_id }" name="redpackage_id"/><!-- 使用的红包ID-->
	<input type="hidden" id="store_redpackets_id" value="${pd.store_redpackets_id }" name="store_redpackets_id"/><!--满赠的赠送红包ID-->
	<input type="hidden" id="sk_shop" value="${pd.sk_shop}" name="sk_shop"/> 
	<input type="hidden" id="allgoodsid" value="${pd.allgoodsid }" name="allgoodsid"/> 
	<input type="hidden" id="pay_sort_type" value="${pd.pay_sort_type }" name="pay_sort_type"/> 
	<input type="hidden" id="remark" value="${pd.remark }" name="remark"/> 
</form>
</body>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script src="js/ping/pingpp.js" type="text/javascript"></script>
<script type="text/javascript">

	//更新支付方式
   	var channel="wx_pub";
    function payway(value){
    	channel=value;
    }
   	//第三方支付
	function save(){
   		    $(".surepay").attr("onclick","");
   		    $(".surepay").css("background","#a4a4a4");
   			var url="<%=basePath%>html_member/toLoginWx.do";
   		    $("#Form").ajaxSubmit({  
						  	url : '<%=basePath%>app_pay_history/thirdPartyPay.do',
					        type: "post",//提交类型  
					      	data:{ 
					      		"pay_way":channel,"url":url,"in_jiqi":"5"
					      	},  
					      	dataType:"json",
					   		success:function(data){ 
					   			if(data.result == "0"){
					   				$(".surepay").attr("onclick","save()");
					   				$(".surepay").css("background","#ffb900");
					   				alert(data.message);
					   				return;
					   			}
					   			var tihuo_id = data.tihuo_id;//订单号
					   			var charge=data.data.charge;
					   			//支付
								pingpp.createPayment(charge, function(result, err){
								    console.log(result);
								    console.log(err.msg);
								    console.log(err.extra);
								    if (result == "success") {
								    	alert("支付成功");
								    	window.location.href='<%=basePath%>html_member/findById.do?ordertype=3&tihuo_id='+tihuo_id;
								        // 只有微信公众账号 wx_pub 支付成功的结果会在这里返回，其他的支付结果都会跳转到 extra 中对应的 URL。
								    } else if (result == "fail") {
								    	alert("支付失败fail");
								    	$(".surepay").attr("onclick","save()");
						   				$(".surepay").css("background","#ffb900");
 								        // charge 不正确或者微信公众账号支付失败时会在此处返回
								    } else if (result == "cancel") {
								    	alert("cancel");
								        // 微信公众账号支付取消支付
								    	$(".surepay").attr("onclick","save()");
						   				$(".surepay").css("background","#ffb900");
								    }
								});
					   		} 	     
			});
 		 
  	}
</script>
</html>
 --%>