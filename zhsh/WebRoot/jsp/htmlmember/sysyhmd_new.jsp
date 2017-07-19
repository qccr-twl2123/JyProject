<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
    <title>优惠买单</title>
    <link rel="stylesheet" href="css/htmlmember/frozen.css">
    <link rel="stylesheet" href="css/htmlmember/newpay.css">
</head>
<body ontouchstart>
<header class="ui-header ui-header-positive ui-border-b bg_ff0600">
    <i class="ui-icon-return" onclick="goStoreDetail()"></i><h1 class="col_f">扫一扫优惠买单</h1>
</header>
<section class="ui-container">
    <ul class="ui-list ui-list-text ui-border-tb mg_b_10">
        <li class="ui-border-t">
            <div class="ui-list-info ">
                <h4>店铺</h4>
            </div>
            <div class="ui-list-action">${store_name}</div>
        </li>
        <li class="ui-border-t">
            <div class="ui-list-info">
                <h4>桌号</h4>
            </div>
            <div class="ui-list-action col_c9">${pd.desk_no}</div>
        </li>
     </ul>
    <div class="ui-form ui-border-t mg_b_10">
             <div class="ui-form-item ui-border-b">
                <label> 消费总金额 </label>
            	<input type="number" placeholder="消费总金额"  value="${pd.sale_money}" style='text-align:right;' class="sale_money" oninput="allpayyouhui()">
            </div>
     </div>
    <div class="ui-form ui-border-t mg_b_10">
             <div class="ui-form-item ui-border-b">
                <label> 不优惠金额 </label>
            	<input type="number" placeholder="请询问收银员哦" value="${pd.no_discount_money}" style='text-align:right;' class="no_discount_money" oninput="allpayyouhui()">
            </div>
     </div>

    <ul class="ui-list ui-list-text ui-list-pure ui-border-tb mg_b_10 ">
        <li class="ui-border-t youhuiList" >
            <p><span>买单优惠明细 </span></p>
            <c:forEach items="${marketlist }" var="var">
			    <p  class="oneyouhui" style="overflow:hidden;"><span style='color:#999;float:left;'>${var.grantrule}</span><span style="float:right;"></span></p>
	 		</c:forEach>
          </li>
    </ul>
    <ul class="ui-list ui-list-text ui-list-link ui-border-tb mg_b_10">
        <li class="ui-border-t" onclick="goUseRed()">
            <h4 class="ui-nowrap">红包</h4>
            <div class="ui-txt-info redMessage" >无可用红包</div>
        </li>
    </ul>
    <ul class="ui-list ui-list-text ui-border-tb mg_b_10">
        <li class="ui-border-t">
            <div class="ui-list-info">
                <span>还需实付金额：</span>
                <span class="col_c9 discount_after_money"></span>
            </div>
            <div class="ui-list-action">已优惠<span class="discount_money"></span>元</div>
        </li>
    </ul>

    <ul class="ui-list ui-list-text ui-border-tb mg_b_10">
        <li class="ui-border-t">
            <div class="ui-list-info">
                <span>账户余额：</span>
                <span>${mpd.now_money}</span>
            </div>
            <label class="ui-switch">
                <input type="checkbox" onclick="isOK(this)" class="user_balance">
            </label>
        </li>
        <li class="ui-border-t">
            <div class="ui-list-info">
                <span>积分余额：</span>
                <span>${mpd.now_integral}</span>
            </div>
            <label class="ui-switch">
                <input type="checkbox"  onclick="isOK(this)" class="user_integral">
            </label>
        </li>
    </ul>
    <div class="demo-block">
        <div class="ui-form ui-border-t">
        	<form action="" method="post" id="Form" name="Form">
				<input type="hidden" name="session_orderid" value="${session_orderid}">
				<input type="hidden" id="sale_money" value="" name="sale_money"/> 
				<input type="hidden" id="no_discount_money" value="" name="no_discount_money"/> 
				<input type="hidden" id="desk_no" value="${pd.desk_no}" name="desk_no"/> 
				<input type="hidden" id="discount_money" value="" name="discount_money"/> 
				<input type="hidden" id="discount_after_money" value="" name="discount_after_money"/> 
				<input type="hidden" id="actual_money" value="" name="actual_money"/> 
				<input type="hidden" id="user_balance" value="0" name="user_balance"/> 
				<input type="hidden" id="user_integral" value="0" name="user_integral"/> 
				<input type="hidden" id="pay_type" value="2" name="pay_type" /> 
				 <input type="hidden" id="get_integral" value="" name="get_integral"/> 
			 	<input type="hidden" id="discount_content" value="" name="discount_content"/><!-- 优惠明细 -->
				<input type="hidden" id="redpackage_id"  value="" name="redpackage_id"/><!-- 使用的红包ID-->
				<input type="hidden" id="store_redpackets_id" value="" name="store_redpackets_id"/><!--满赠的赠送红包ID-->
				<input type="hidden" id="sk_shop" value="${pd.sk_shop}" name="sk_shop"/> 
				<input type="hidden" id="allgoodsid" value="" name="allgoodsid"/> 
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
    <div class="ui-btn-wrap">
        <button class="ui-btn-lg actual_money surepay" style='color: #fff;background: #c90000;' onclick="surepay()">
            	 在线支付
        </button>
    </div>
</section>
</body>
<script type="text/javascript" src="js/htmlmember/jquery-1.11.3.min.js"></script>
<script src="js/jquery.form.js"></script>
<script src="js/ping/pingpp.js" type="text/javascript"></script>
<script type="text/javascript">

	//判断是否为数字
	function isNumber(obj){
		if (isNaN($(obj).val())) { 
			$(obj).val($(obj).val().substring(0, $(obj).val().length-1));
		　　return true;
		} 
		if($(obj).val().indexOf("-") >= 0){
			$(obj).val($(obj).val().substring(0, $(obj).val().length-1));
			return true;
		}
		var xiaoshu=$(obj).val().length - $(obj).val().indexOf(".");
		if($(obj).val().indexOf(".") >0 && xiaoshu > 3){
			$(obj).val($(obj).val().substring(0, $(obj).val().length-1));
		}
	}



	//获取总金额的优惠信息
	function allpayyouhui(){
		if(isNumber($(".sale_money"))){
				return;
		}
		if(isNumber($(".no_discount_money"))){
				return;
		}
		var sale_money=$(".sale_money").val();
		var no_discount_money=$(".no_discount_money").val();
		if(sale_money =="" ){
			return;
		}
		if(no_discount_money =="" ){
			no_discount_money="0";
		}
		if(parseFloat(sale_money)/2-parseFloat(no_discount_money) < 0){
			alert("不优惠金额不能大于总金额的50%");
			$("#allno_discount_money").val(no_discount_money.substr(0,no_discount_money.length-1));
			return;
		}
		//重置
		chongzhi_money();
		//获取营销信息
		$.ajax({
 			url:"app_goods/saoYiSaoShopBuyGoods.do",   
			type:"post",
			dataType:"json",
			data:{
				"sk_shop":"${pd.sk_shop}",
				"allmoney":sale_money,  
 				"notmoney":no_discount_money,
				"redpackage_id":"${empty pd.redpackage_id?'':pd.redpackage_id}"
			},
			success:function(data){ 
				$(".redMessage").html(data.data.redMessage);
 				var countpd=data.data.countpd;
				if(countpd != null){
					    $(".discount_after_money").html("￥"+parseFloat(countpd.paymoney).toFixed(2));
 	 					$(".actual_money").html("在线支付"+parseFloat(countpd.paymoney).toFixed(2)+"元");
 						$(".discount_money").html(parseFloat(countpd.reducemoney).toFixed(2));
 						$("#discount_after_money").val(parseFloat(countpd.paymoney).toFixed(2));//优惠后支付的金额
 						$("#sale_money").val(countpd.allmoney);
 						$("#no_discount_money").val(countpd.notmoney);
	 					$("#actual_money").val(countpd.paymoney);
 						$("#discount_money").val(countpd.reducemoney);
	 					$("#get_integral").val(countpd.zengjf);
 						$("#redpackage_id").val(countpd.redpackage_id);
						$("#store_redpackets_id").val(countpd.store_redpackets_id);
						$("#discount_content").val(countpd.discount_content);
				}
				var yingxiaoList=data.data.yingxiaoList;
				$(".oneyouhui").remove();
				for(var n=0; n<yingxiaoList.length ; n++){
						var s1=yingxiaoList[n].content;
						var s2=yingxiaoList[n].id;
						var s3=yingxiaoList[n].number;
						var s4=yingxiaoList[n].type;
						var sspan="<p  class='oneyouhui' style='overflow:hidden;'><span style='color:#999;float:left;'>"+s1+"</span><span style='float:right;'>"+s3+"</span></p>";
						$(".youhuiList").append(sspan);
				}
 			}
		});
					
	}

	//前往使用红包页面
	function goUseRed(){
		if($(".sale_money").val() =="" ){
			return;
		}
 		window.location.href='html_member/goUseRed.do?pay_type=2&sk_shop=${pd.sk_shop}&desk_no=${pd.desk_no}&sale_money='+$(".sale_money").val()+'&no_discount_money='+$(".no_discount_money").val(); 
	}
	//前往商家页面
	function goStoreDetail(){
 		window.location.href='html_member/goStoreDetail.do?sk_shop=${yhmdpd.sk_shop}'; 
	}
	
	//重置使用的积分余额
	function chongzhi_money(){
		$("#actual_money").val($("#discount_after_money").val());
  		$(".actual_money").html("在线支付"+$("#discount_after_money").val()+"元");
  		$("#user_balance").val("0");
  		$("#user_integral").val("0");
  		$(".user_balance").removeAttr("checked");
  		$(".user_integral").removeAttr("checked");
	}
	

	//判断近期是否充足
	function isOK(obj){//1-使用余额，2-使用积分
  		var allnow_integral="${mpd.now_integral}";
  		var allnow_balance="${mpd.now_money}";
  		var paymoeny=parseFloat($("#discount_after_money").val());//优惠后的金额：总金额-优惠金额-红包支付金额
   		var objclass=$(obj).attr("class");
    	var double_actual_money=parseFloat($("#actual_money").val());
  		var double_user_balance=parseFloat($("#user_balance").val());
  		var double_user_integral=parseFloat($("#user_integral").val());
  		if(paymoeny == 0 || $("#sale_money").val() == "" || parseFloat($("#sale_money").val()) <=0    ){
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
  		$(".actual_money").html("在线支付"+double_actual_money.toFixed(2)+"元");
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
 		var pay_way="nowpay";
		if(double_actual_money > 0){
			pay_way="wx_pub";
		}
	    $("#Form").ajaxSubmit({  
	    	url : 'html_member/payorder.do',
	        type: "post",//提交类型  
	      	data:{ 
	      		"pay_way":pay_way,
				"in_jiqi":"5"
	      	},  
	      	dataType:"json",
	   		success:function(data){ 
	   			if(data.result == "0"){
	   				flag=true;
	   				$(".surepay").attr("onclick","surepay()");
	   				$(".surepay").css("background","#c90000");
	   				alert(data.message);
	   				return;
	   			}
	   			var map=data.data;
	   			var order_id = map.order_id;//订单号
	   			if(double_actual_money > 0){
 					 if(map.return_msg == "OK"){
 						callWxJsPay(map.payment_type,map.appId,map.timeStamp,map.nonceStr,map.package,map.signType,map.sign,map.out_trade_no);
		        	 }else{
		        		 alert(map.return_msg);
		        	 }
	   			}else{
	   				//在支付成功的状态下跳转订单到订单详情界面
	   				window.location.href='html_member/findById.do?ordertype=2&order_id='+order_id; 
	   			}
 	   		} 
	    }); 
	}
	
	$(function(){
		if("${pd.isredback}" == "1"){
			allpayyouhui();
		}
	});
	
</script>
</html>