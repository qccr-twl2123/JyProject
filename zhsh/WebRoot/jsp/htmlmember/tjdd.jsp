<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/style.css">
	<link rel="stylesheet" href="<%=basePath%>css/htmlmember/styles.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/htmlmember/main.js"></script>
	<style type="text/css">
			#flpay,#allpay{
			    top: 90px;
	    		position: absolute;
	    		width:100%;
			}
			#allintegral,#allmoney, #flintegral,#flmoney{
				width:110px;
			} 
			.sj-button {
			    background: #ffb900;
			    border: none;
			    color: #fff;
			    width: 137px;
			    height: 43px;
			    /* line-height: 30px; */
			    border-radius: 5px;
			    font-size: 1.3rem;
			    text-align: center;
			}
	</style>
</head>
<body style="background:#fff;">
<!-- 表单提交 -->
<form action="<%=basePath%>html_member/goPay.do" method="post" id="Form" name="Form">
		<input type="hidden" id="sale_money" name="sale_money" value="${pd.paymoney}"/> 
		<input type="hidden" id="session_orderid" name="session_orderid" value="${session_orderid}"/> 
		<input type="hidden" id="no_discount_money" name="no_discount_money" value=""/> 
		<input type="hidden" id="desk_no" name="desk_no" value=""/> 
		<input type="hidden" id="discount_money" name="discount_money"  value=""/> 
		<input type="hidden" id="actual_money" name="actual_money" value=""/> 
		<input type="hidden" id="user_balance" name="user_balance" value=""/> 
		<input type="hidden" id="user_integral" name="user_integral" value=""/> 
		<input type="hidden" id="pay_type" name="pay_type" value="3" /> 
 		<input type="hidden" id="get_integral" value="0" name="get_integral"/> 
 		<input type="hidden" id="discount_content" name="discount_content"/><!-- 优惠明细 -->
		<input type="hidden" id="redpackage_id" name="redpackage_id" /><!-- 使用的红包ID-->
		<input type="hidden" id="store_redpackets_id" name="store_redpackets_id"/><!--满赠的赠送红包ID-->
		<input type="hidden" id="sk_shop" value="${pd.sk_shop}" name="sk_shop" /> 
		<input type="hidden" id="allgoodsid" value="${pd.allgoods}" name="allgoodsid" /> 
		<input type="hidden" id="pay_sort_type"  name="pay_sort_type" value="1" /> 
		<input type="hidden" id="remark"  name="remark" value="" /> 
</form>
<nav class="top">
	<a onclick="backreturn()"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">确认订单</div>
</nav>
<script type="text/javascript">
		function backreturn(){
			window.history.back();
		}
</script>
<div class="tidd-title clearfix store_name" style="font-size:18px;">
	<!-- 啦啦啦啦啦 -->
</div>
<!-- 商品集合 -->
<ul class="tjdd-list" id="shoplist">

</ul>
<!-- 订单详情 -->
<div class="clearfix tjdd-text">
	<h3 class="fl">订单金额</h2>
	<span class="fr" >¥<span style="font-size:18px;color:red"><fmt:formatNumber type="number" value="${pd.paymoney}" pattern="0.00" maxFractionDigits="2"/>  </span>元</span>
</div>
<div class="yh-content clearfix">
	<ul>
		<li id="allyhmx">
			<p class="fourteen-px">优惠明细：</p>
			<!-- <p class="gay">
				<span>整店折扣</span>
				<span class="fr">-30.00</span>
			</p> -->
		</li>
 	</ul>
 </div>

<div class="clearfix tjdd-text">
	<h3 class="fl">还需支付</h2>
	<span class="fr">¥<span class="youhuiaftermoney"  style="font-size:18px;color:red"></span>元</span>
</div>
<div class="clearfix tjdd-text">
	<h3 class="fl">备注</h2>
	<span class="fr"><input type="text" class="remark" /></span>
</div>
<div class="tjdd-text tjdd-zf clearfix" style="font-size:14px">
		<li class="clearfix">
			<!-- <span class="pay_list_c1 fl">
				<input type="checkbox"   style="width:19px;height:19px">
			</span> -->
			<span class="height">
				<span class="fl">余额支付：</span>
				<input class="tjdd-input"   id="allmoney" type="text"   oninput="isOK(this,'1')" onkeyup="value=value.replace(/[^\.\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
 			</span>
			<span class="fr height gay">余额 <span  id="allnow_money" style="color:red;"></span> 元</span>
		</li>
		<li>
			<!-- <span class="pay_list_c1 fl">
				<input type="checkbox"   style="width:19px;height:19px">
			</span> -->
			<span class="height " >
				<span class="fl">积分支付：</span>
				<input id="allintegral"  class="tjdd-input" type="text"  oninput="isOK(this,'2')" onkeyup="value=value.replace(/[^\.\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
 			</span>
			<span class="fr height gay">积分 <span  id="allnow_integral" style="color:red;"></span> 分</span>
		</li>
</div>
<script type="text/javascript">
if(true){
	//商品循环
	<c:forEach items="${shopList}" var="var">
		var shopstr="<li><span class='fl'>${var.goods_name}</span><span class='fr'>${var.allmoney}元</span><span class='fr tjdd-number'>x${var.goods_number}</span></li>";
		$("#shoplist").append(shopstr);
	</c:forEach>
}
if(true){
		if(true){
		//验证提交
		$.ajax({
		        type:"post",
		        url:'<%=basePath%>app_goods/allMoneyByOne.do', 
		  	 	 data:{
		  	 		"sk_shop":"${pd.sk_shop}",
 		 		 	"paymoney":"${pd.paymoney}" ,
		 		 	"allgoods":"${pd.allgoods}" ,
		 		 	"notmoney":"0"
		  	 	 },                
		        dataType:"json",
		        success: function(data){
		        	 	//优惠明细
			       	 	var yingxiaoList=data.data.yingxiaoList;
			        	var discount_content="";
		        		 for (var i = 0; i < yingxiaoList.length; i++) {
								var str="<p class='gay allyx'><span>"+yingxiaoList[i].content+"</span><span class='blue fr'>"+yingxiaoList[i].number+"</span></p>";
								var dis=yingxiaoList[i].content+"@"+yingxiaoList[i].id+"@"+yingxiaoList[i].number+"@"+yingxiaoList[i].type;
								discount_content+=dis+",";
								$("#allyhmx").append(str);
		        		 }
		        		 $("#discount_content").val(discount_content);//优惠明细
	 			         //优惠后的价格
			       	 	var countpd=data.data.countpd;
			       	  	//.$("#sale_money").val("${pd.paymoney}");
			       	  	//$("#no_discount_money").val(allnotmoney);
			       	  	$("#discount_money").val(countpd.reducemoney);
			       	  	$("#actual_money").val(countpd.paymoney);
			       	  	$("#get_integral").val(countpd.zengjf);
			       	  	$("#redpackage_id").val(countpd.red_id);
			       	  	$("#store_redpackets_id").val(countpd.zengid);
			       	  	$("#pay_sort_type").val("1");
			       	 	$("#allpaymoney").html(countpd.paymoney);
			       	  	$("#allreducemoney").html(countpd.reducemoney);
			       	  	$(".lastallpaymoney").html(parseFloat(countpd.paymoney).toFixed(2));
			       	  	$(".youhuiaftermoney").html(parseFloat(countpd.paymoney).toFixed(2));
				        //财富信息
			       	 	 var memberInfor=data.data.memberInfor;
			        	 $("#allnow_integral").html(parseFloat(memberInfor.now_integral).toFixed(2));
			        	 $("#allnow_money").html(parseFloat(memberInfor.now_money).toFixed(2));
			        	 //商家名称
			       	 	 var store_name=data.data.store_name;
			        	 $(".store_name").html(store_name);
 		        }
      });
	}
}
</script>
<script src="<%=basePath%>js/jquery.form.js"></script>
<script type="text/javascript">
		//判断是否为数字
		function isNumber(obj){
			if($(obj).val() == ""){
				return;
			}
			if (isNaN($(obj).val())) { 
				$(obj).val($(obj).val().substring(0, $(obj).val().length-1));
			　　return;
			} 
			if($(obj).val().indexOf("-") >= 0){
				return;
			}
			var xiaoshu=$(obj).val().length - $(obj).val().indexOf(".");
				if($(obj).val().indexOf(".") >0 && xiaoshu > 3){
				$(obj).val($(obj).val().substring(0, $(obj).val().length-1));
			}
		}
	
	//判断近期是否充足
	function isOK(obj,type){
		isNumber(obj);
 		var allnow_integral=$("#allnow_integral").html();
 		var allnow_balance=$("#allnow_balance").html();
  		var user_integral=$("#allintegral").val();//使用的积分
 		var user_balance=$("#allmoney").val();//使用的余额
 		if(user_balance == ""){user_balance="0";}
 		if(user_integral == ""){user_integral="0";}
 		if(type == "1"){
 			var n=parseFloat(allnow_balance).toFixed(2)-parseFloat(user_balance).toFixed(2);
 			if(n < 0){
 				alert("当前余额不足");
 				$(obj).val($(obj).val().substr(0,$(obj).val().length-1));
 				return;
 			}
 		}
 		else if(type == "2"){
 			var n=parseFloat(allnow_integral).toFixed(2)-parseFloat(user_integral).toFixed(2);
 			if(n < 0){
 				alert("当前积分不足");
 				$(obj).val($(obj).val().substr(0,$(obj).val().length-1));
 				return;
 			}
 		}
 		var s4=parseFloat($(".youhuiaftermoney").html()).toFixed(2);//实际支付的金钱
 		var lessmoney=s4-parseFloat(user_balance).toFixed(2)-parseFloat(user_integral).toFixed(2);
 		if(lessmoney < 0){
 			alert("支付金额超出，已帮你自动更改");
 			$(obj).val($(obj).val().substr(0,$(obj).val().length-1));
			return;
		}
 		$(".lastallpaymoney").html(lessmoney.toFixed(2));
		$("#actual_money").val(lessmoney.toFixed(2));
	}
</script>
<div class="tjdd-text">
	<h3 class="fl">实付款：<span class="lastallpaymoney"></span>元</h3>
	<a onclick="threepay()" class="fr sj-button">
		提交订单
	</a>
</div>
<script type="text/javascript">
//第三方支付
function threepay(){
	//验证提交
 	$("#remark").val($(".remark").val());
 	$("#user_balance").val($("#allmoney").val());
	$("#user_integral").val($("#allintegral").val());
	if($("#sale_money").val() == ""){
		alert("购买金额不能为空");
		return;
	}
	if($("#actual_money").val() == ""){
		alert("支付金额不能为空");
		return;
	}
	if(parseFloat($("#actual_money").val()) == 0){
		  $("#Form").ajaxSubmit({  
				  	url : '<%=basePath%>app_pay_history/thirdPartyPay.do',
			        type: "post",//提交类型  
			      	data:{ 
			      		"pay_way":"nowpay","in_jiqi":"5"
			      	},  
			      	dataType:"json",
			   		success:function(data){ 
 			   			if(data.result == "1"){
			   				//alert("支付成功");
			   				var tihuo_id = data.tihuo_id;//订单号
			   				//在支付成功的状态下跳转订单到订单详情界面
			   				window.location.href='<%=basePath%>html_member/findById.do?tihuo_id='+tihuo_id; 
			   			}else{
			   				alert("支付失败，请联系收银员");
			   			}
			   		} 	     
		});
	}else{
 		$("#Form").submit();
	}
 }
</script>
</body>
</html>
