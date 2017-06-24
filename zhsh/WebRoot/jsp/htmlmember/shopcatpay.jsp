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
	<base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>购物车买单</title>
     <link rel="stylesheet" href="css/htmlmember/frozen.css">
    <link rel="stylesheet" href="css/htmlmember/newpay.css">
</head>
<body ontouchstart>
<header class="ui-header ui-header-positive ui-border-b bg_ff0600">
    <i class="ui-icon-return" onclick="goStoreDetail()"></i><h1 class="col_f">购物车购买</h1>
</header>
<section class="ui-container">
     <ul class="ui-list ui-border-tb mg_b_10">
        <li class="ui-border-t">
            <div class="ui-list-info"  style="margin: 0 auto;">
                <h4 class="ui-nowrap">${yhmdpd.store_name}</h4>
            </div>
        </li>
    </ul>  
    <ul class="ui-list ui-list-text ui-border-tb  mg_b_10">
    	<c:forEach items="${yhmdpd.goodsList}" var="goodsvar">
	    	<li class="ui-border-t">
	            <div class="ui-list-info">
	                <h4 class="ui-nowrap mx_width dis_in_b">${goodsvar.goods_name}</h4>
	                <span class="list_span">x${goodsvar.shop_number}</span>
	            </div>
	            <div class="ui-list-action col_c9">￥${goodsvar.summoney}元</div>
	        </li>
    	</c:forEach>
     </ul>
     <ul class="ui-list ui-list-text ui-border-tb  mg_b_10">
        <li class="ui-border-t">
            <div class="ui-list-action" style="margin: 0 auto;"><span>总金额：</span><span>${yhmdpd.countpd.allmoney}</span></div>
        </li>
    </ul>
    <ul class="ui-list ui-list-text   mg_b_10">
    	<c:forEach items="${yhmdpd.yingxiaoList}" var="yxsvar">
	    	<li class="ui-border-t"  style="padding:0 15px 0 0 ">
	            <div class="ui-list-icon" style="height: inherit;">
	                <span style="background-color:red;width: 40px;text-align: center;height: initial;color: #ffffff;">${yxsvar.name}</span>
	            </div>
	            <div class="ui-list-info">
	                <h4 class="ui-nowrap mx_width dis_in_b">${yxsvar.content}</h4>
	            </div>
	            <div class="ui-list-action">${yxsvar.number}</div>
	        </li>
    	</c:forEach>
     </ul>
    <ul class="ui-list ui-list-text ui-list-link ui-border-tb mg_b_10">
        <li class="ui-border-t" onclick="goUseRed()" >
            <h4 class="ui-nowrap" >红包</h4>
            <div class="ui-txt-info">${yhmdpd.redMessage}</div>
        </li>
    </ul>
    <ul class="ui-list ui-list-text ui-border-tb  mg_b_10">
        <li class="ui-border-t">
            <div class="ui-list-info">
				<span>优惠金额：</span><span>${yhmdpd.countpd.reducemoney}</span>
            </div>
            <div class="ui-list-action"><span>待支付：</span><span>${yhmdpd.countpd.paymoney}</span></div>
        </li>
    </ul>
    <ul class="ui-list ui-list-text ui-border-tb mg_b_10">
        <li class="ui-border-t">
            <div class="ui-list-info">
                <span>剩余余额：</span>
                <span>${yhmdpd.memberInfor.now_money}</span>
            </div>
            <label class="ui-switch">
                <input type="checkbox"   class="user_balance"  onclick="isOK(this)">
            </label>
        </li>
        <li class="ui-border-t">
            <div class="ui-list-info">
                <span>剩余积分：</span>
                <span>${yhmdpd.memberInfor.now_integral}</span>
            </div>
            <label class="ui-switch">
                <input type="checkbox" class="user_integral" onclick="isOK(this)">
            </label>
        </li>
    </ul>
    <div class="demo-block">
        <div class="ui-form ui-border-t">
        	<form action="" method="post" id="Form" name="Form">
				<input type="hidden" name="session_orderid" value="${session_orderid}">
				<input type="hidden" id="sale_money" value="${yhmdpd.countpd.allmoney }" name="sale_money"/> 
				<input type="hidden" id="no_discount_money" value="${yhmdpd.countpd.notmoney}" name="no_discount_money"/> 
				<input type="hidden" id="desk_no" value="" name="desk_no"/> 
				<input type="hidden" id="discount_money" value="${yhmdpd.countpd.reducemoney}" name="discount_money"/> 
				<input type="hidden" id="actual_money" value="${yhmdpd.countpd.paymoney}" name="actual_money"/> 
				<input type="hidden" id="user_balance" value="0" name="user_balance"/> 
				<input type="hidden" id="user_integral" value="0" name="user_integral"/> 
				<input type="hidden" id="pay_type" value="3" name="pay_type" /> 
				 <input type="hidden" id="get_integral" value="${yhmdpd.countpd.zengjf }" name="get_integral"/> 
			 	<input type="hidden" id="discount_content" value="${yhmdpd.countpd.discount_content }" name="discount_content"/><!-- 优惠明细 -->
				<input type="hidden" id="redpackage_id"  value="${yhmdpd.countpd.redpackage_id }" name="redpackage_id"/><!-- 使用的红包ID-->
				<input type="hidden" id="store_redpackets_id" value="${yhmdpd.countpd.store_redpackets_id }" name="store_redpackets_id"/><!--满赠的赠送红包ID-->
				<input type="hidden" id="sk_shop" value="${yhmdpd.sk_shop}" name="sk_shop"/> 
				<input type="hidden" id="allgoodsid" value="${yhmdpd.allgoodsid }" name="allgoodsid"/> 
				<input type="hidden" id="pay_sort_type" value="1" name="pay_sort_type"/> 
 				<div class="ui-form-item ui-form-item-textarea ui-border-b">
	                <label style="width: inherit;">
	                        	备注：
	                 </label>
	                 <input placeholder="请输入备注" style="padding-left: 60px;"  name="remark"/> 
	            </div>
			</form>
        </div>
    </div>
</section>
<footer class="ui-footer">
    <div class="ui-row-flex ">
        <div class="ui-flex ui-col ui-col-3 ui-flex-pack-end ui-flex  ui-flex-align-center" style="height: 56px;padding-right: 15px">实付款：<span class="col_c9 actual_money"  >￥${yhmdpd.countpd.paymoney}元</span></div>
        <div class="ui-flex ui-col ui-flex-align-center ui-flex-pack-center col_f surepay" style="height: 56px;background:rgb(251,171,44) " onclick="surepay()">确认支付 </div>
    </div>
</footer>
<script type="text/javascript" src="js/htmlmember/jquery-1.11.3.min.js"></script>
<script src="js/jquery.form.js"></script>
<script src="js/ping/pingpp.js" type="text/javascript"></script>
<script type="text/javascript">
	//前往使用红包页面
	function goUseRed(){
 		window.location.href='html_member/goUseRed.do?pay_type=3&sk_shop=${yhmdpd.sk_shop}'; 
	}
	//前往商家页面
	function goStoreDetail(){
 		window.location.href='html_member/goStoreDetail.do?sk_shop=${yhmdpd.sk_shop}'; 
	}

	//判断近期是否充足
	function isOK(obj){//1-使用余额，2-使用积分
  		var allnow_integral="${yhmdpd.memberInfor.now_integral}";
  		var allnow_balance="${yhmdpd.memberInfor.now_money}";
  		var paymoeny=parseFloat("${yhmdpd.countpd.paymoney}");//优惠后的金额：总金额-优惠金额-红包支付金额
   		var objclass=$(obj).attr("class");
   		var double_actual_money=parseFloat($("#actual_money").val());
  		var double_user_balance=parseFloat($("#user_balance").val());
  		var double_user_integral=parseFloat($("#user_integral").val());
  		if(paymoeny == 0){
  			$(obj).removeAttr("checked");
  			return;
  		}
  		if(objclass == "user_balance"){
  			//选中的是余额
  			if ($(obj).is(":checked")){
  				if(double_actual_money == 0){
  		  			$(obj).removeAttr("checked");
  		  			return;
  		  		}
	  			if(parseFloat(allnow_balance)-double_actual_money > 0  ){
 	  				double_user_balance=double_actual_money;
 	  				double_actual_money=0;
	  			}else{//实际支付金额大于余额:支付所有余额
	  				double_actual_money=double_actual_money-parseFloat(allnow_balance);
  					double_user_balance=parseFloat(allnow_balance);
	  			}
		  	}else{
		  			$(obj).removeAttr("checked");
		  			double_actual_money=double_actual_money+double_user_balance;
		  			double_user_balance=0;
		  			if ($(".user_integral").is(":checked")){
			  			if(parseFloat(allnow_integral)-paymoeny  > 0 ){
 	   		  				double_user_integral=paymoeny;
 	   		  				double_actual_money=0;
			  			}else{
			  				double_actual_money=paymoeny-parseFloat(allnow_integral);
			  				double_user_integral=parseFloat(allnow_integral);
			  			}
				  	}
		  	}
  		}else{ 
  			//选中的是积分
  			if ($(obj).is(":checked")){
  				if(double_actual_money == 0){
  		  			$(obj).removeAttr("checked");
  		  			return;
  		  		}
	  			if(parseFloat(allnow_integral)-double_actual_money > 0  ){
 	  				double_user_integral=double_actual_money;
	  				double_actual_money=0;
	  			}else{//实际支付金额大于余额:支付所有余额
	  				double_actual_money=double_actual_money-parseFloat(allnow_integral);
	  				double_user_integral=parseFloat(allnow_integral);
	  			}
		  	}else{
		  			$(obj).removeAttr("checked");
		  			double_actual_money=double_actual_money+double_user_integral;
		  			double_user_integral=0;
		  			if ($(".user_balance").is(":checked")){
			  			if(parseFloat(allnow_balance)-paymoeny  > 0 ){
 	   		  				double_user_balance=paymoeny;
 	   		  				double_actual_money=0;
			  			}else{
			  				double_actual_money=paymoeny-parseFloat(allnow_balance);
			  				double_user_balance=parseFloat(allnow_balance);
			  			}
				  	}
		  	}
  		}
  		$("#actual_money").val(double_actual_money.toFixed(2));
  		$(".actual_money").html("￥"+double_actual_money.toFixed(2)+"元");
  		$("#user_balance").val(double_user_balance.toFixed(2));
  		$("#user_integral").val(double_user_integral.toFixed(2));
 	}
	
	var flag=true;
	//确认支付
	function surepay(){
		if(!flag){
			return;
		}
		flag=false;
		$(".surepay").removeAttr("onclick");
		$(".surepay").css("background","rgb(192, 192, 192)");
 		var double_actual_money=parseFloat($("#actual_money").val());
		var url="<%=basePath%>html_member/toLoginWx.do";
		var pay_way="nowpay";
		if(double_actual_money > 0){
			pay_way="wx_pub";
		} 
	    $("#Form").ajaxSubmit({  
	    	url : 'app_pay_history/thirdPartyPay.do',
	        type: "post",//提交类型  
	      	data:{ 
	      		"pay_way":pay_way,"url":url,"in_jiqi":"5"
	      	},  
	      	dataType:"json",
	   		success:function(data){ 
	   			if(data.result == "0"){
	   				flag=true;
	   				$(".surepay").attr("onclick","surepay()");
	   				$(".surepay").css("background","rgb(251,171,44)");
	   				alert(data.message);
	   				return;
	   			}
	   			var tihuo_id = data.tihuo_id;//订单号
	   			if(double_actual_money > 0){
 		   			var charge=data.data.charge;
 					pingpp.createPayment(charge, function(result, err){
					    console.log(result);
					    console.log(err.msg);
					    console.log(err.extra);
					    if (result == "success") {
 					    	window.location.href='html_member/findById.do?ordertype=3&tihuo_id='+tihuo_id;
					        // 只有微信公众账号 wx_pub 支付成功的结果会在这里返回，其他的支付结果都会跳转到 extra 中对应的 URL。
					    } else if (result == "fail") {
					    	alert("支付失败fail");
					    	flag=true;
					    	$(".surepay").attr("onclick","surepay()");
			   				$(".surepay").css("background","rgb(251,171,44)");
 						    // charge 不正确或者微信公众账号支付失败时会在此处返回
					    } else if (result == "cancel") {
					    	alert("cancel");
					        // 微信公众账号支付取消支付
					        flag=true;
					    	$(".surepay").attr("onclick","surepay()");
			   				$(".surepay").css("background","rgb(251,171,44)");
 					    }
					});
	   			}else{
	   				//在支付成功的状态下跳转订单到订单详情界面
	   				window.location.href='html_member/findById.do?tihuo_id='+tihuo_id; 
	   			}
 	   		} 
	    });
		 
	}
 
</script>
</body>
</html>
