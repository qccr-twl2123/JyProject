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
	<title>结算订单页</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/youxuan/normalize.min.css">
	<link rel="stylesheet" href="css/youxuan/qrdd.css">
 </head>
<body>
	<header>
		<div class="goback" onclick="backreturn()">‹</div>
		<div class="title">
			 <div class="djs">
		        <h6>确认订单</h6>
	    	</div>
		</div>
	</header>
 	<div class="shopping">
	</div>
	<div style="height: 6%;position: absolute;bottom: 56px;font-size: 15px;">
		使用积分：<input style="    font-size: 22px; display: inline-block;width: 128px;height: 82%;margin-right: 10px;" type="text" value="" class="memberuser_integral" oninput="jisuanlastmoney(this)"  onkeyup="value=value.replace(/[^\d\.]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"   /><span>
		积分财富：<span class="now_integral" style="color:red;font-size: 18px;"></span></span>
	</div>
	<footer>
		<div class="text">
			<span>合计：</span><span class="zongjia"></span>
		</div>
 		<a  class="foot_tj jiesuan" onclick="goPayMoney()">结算</a>
	</footer>
	<input type="hidden" class="zongpaymoney" value="">
	<input type="hidden" class="lastpaymoney" value="">
</body>
<script type="text/javascript">
var base_inf={
         base_herf:"<%=basePath%>" 
};
</script>
<script src="js/jquery-1.8.0.min.js"></script>
<script src="js/jquery.form.js"></script>
<script src="js/wx/jweixin-1.0.0.js"></script>
<script src="js/wx/zepto.min.js"></script>
<script src="js/htmlmember/weixindemo.js"></script>
	<script type="text/javascript">
	$(function(){
  		$.ajax({
	        type:"post",
	        url:'youxuan/gosurePayOrder.do', 
		  	data:{
	  	 		"goodsinfor":"${pd.goodsinfor}", 
 	  	 		"gopay_type":"${pd.gopay_type}"
		  	},                
	        dataType:"json",
	        async: false,
	        success: function(data){
	        	  var result_status=data.result;
	        	  if(result_status == "0"){
 	        		  $(".jiesuan").css("background","#7D7B7D");
	        		  $(".jiesuan").attr("onclick","");
	        	  }
  		          var gopay_type="${pd.gopay_type}";
		          var map1=data.data;
		          var moneyCount=map1.moneyCount;
 		          $(".zongpaymoney").val(parseFloat(moneyCount).toFixed(2));
 		          $(".lastpaymoney").val(parseFloat(moneyCount).toFixed(2));
		          $(".zongjia").html("￥"+parseFloat(moneyCount).toFixed(2)+"元");
		          window.zongjia_money=parseFloat(moneyCount).toFixed(2);
		          var jfCount=map1.jfCount;
		          var shenMoneyCount=map1.shenMoneyCount;
		          $(".now_integral").html(map1.now_integral);
		          var storeList=map1.storeList;
		          for(var i=0;i<storeList.length;i++){
		        	  var storepd=storeList[i];
		        	  var store_name=storepd.store_name;
		        	  var pictrue_url=storepd.pictrue_url;
		        	  var allmoney=storepd.allmoney;
		        	  var alljifen=storepd.alljifen;
		        	  var allnumber=storepd.allnumber;
		        	  var allshenmoney=storepd.allshenmoney;
		        	  var str="<ul class='dd_sj'> <li class='sj_head'> <img src='"+pictrue_url+"'  > <span> "+store_name+"</span> </li>";
 		        	  var goodsList=storepd.goodsList;
 		        	  for(var j=0;j<goodsList.length;j++){
 		        		 var goodspd=goodsList[j];
 		        		 var str1="<li class='sj_sp'> <img src='"+goodspd.gg_imageurl+"' ><div class='cont'> <p>"+goodspd.brand_name+"|"+goodspd.goods_name+"<p>"+goodspd.gg_miaosu+"</p> <p class='sp_jg'><span>￥"+goodspd.sale_money+"</span> <span>￥"+goodspd.gf_salemoney+"</span> <span class='sj_num' id='"+goodspd.goods_id+"number'>x"+goodspd.goods_number+"</span></p></div></li>";
 		        		 if(gopay_type == "1"){
 		        			str1+="<li class='sp_num only_id'  ><span>购买数量</span><span class='change'  youxuangg_id='"+goodspd.goods_id+"' ><span class='down' onclick='caozuoShop(-1,this)'>-</span><span class='num "+goodspd.goods_id+"number'>"+allnumber+"</span><span class='up' onclick='caozuoShop(1,this)'>+</span></span></li>";
 	 		        	 }
 		        		 str+=str1;
 		        	  }
 		        	  
 		        	  var str3="<li class='sj_jf'><span>赠送积分</span><span class='jf_num' >"+parseFloat(alljifen).toFixed(2)+"</span></li><li class='sj_ysje'><span>已省金额</span><span class='ys_money'>"+parseFloat(allshenmoney).toFixed(2)+"</span></li><li class='sj_xj'><span>共<span>"+allnumber+"</span>件商品</span> <span>小计：</span> <span>￥"+parseFloat(allmoney).toFixed(2)+"</span></li></ul>";
 		        	  str+=str3;
 		        	  $(".shopping").append(str);
		          }
 	        	  if(result_status == "0"){
	        		  alert(data.message);
 	        	  }
 		    }
	    });
	});
	
	//返回
	function backreturn(){
		if("${pd.gopay_type}" == "2"){
			window.location.href="html_member/goMyYouXuanShopCart.do";
 		}else{
 			window.location.href="html_member/goMyYouXuanDetail.do?youxuangoods_id=${pd.goods_id}";
		}
 	}
	
	//操作购买数量
	function caozuoShop(value,obj){
		var nowshopnumber;
		if(value == "-1"){
			nowshopnumber=$(obj).next();
		}else{
			nowshopnumber=$(obj).prev();
		} 
 		if($(nowshopnumber).html() == "1" && value == "-1"){
 			return;
		}else{
			var youxuangg_id=eval($(obj).parent().attr("youxuangg_id"));
			var shopnumber=parseInt($(nowshopnumber).html())+parseInt(value);
			$.ajax({
		        type:"post",
		        url:'youxuan/isOKadd.do', 
		  	 	data:{
		  	 		"goods_id":youxuangg_id,
		  	 		"goods_type":"2",
		  	 		"caozuo_number":shopnumber,
		  	 		"member_id":"${pd.member_id}"
			  	 	},                
		        dataType:"json",
		        success: function(data){
		        	//判断是否还有库存
  		        	if(data.result == "1"){
		        		window.location.href="html_member/goReadyPayOrder.do?goods_id=${pd.goods_id}&gopay_type=${pd.gopay_type}&goodsinfor="+youxuangg_id+"@"+shopnumber;
 	 	       	 	}else{
 	 	       	 		alert(data.message);
 	 	       	 	}
 		         }
		    });
			
		}
	}
	
	
	//去结算页面
	function goPayMoney(){
  		if(parseFloat($(".lastpaymoney").val()) == 0){
			//直接生成订单，不用跳转页面
			payMoney("nowpay","0");
 		}else{
 			payMoney("wx_pub","1");
		}
  	}
	
	//当使用积分支付，总金额为o的时候
	function payMoney(pay_way,change_type){
 		$.ajax({
	        type:"post",
	        url:'html_member/yxPayOrder.do', 
		  	data:{
	  	 		"goodsinfor":"${pd.goodsinfor}", 
 	  	 		"gopay_type":"${pd.gopay_type}",
	  	 		"pay_way":pay_way,
	  	 		"user_integral":$(".memberuser_integral").val(),
	  	 		"in_jiqi":"5",
	  	 		"goods_type":"2"//1-正常商品，2-优选商品
		  	},                
	        dataType:"json",
	        async: false,
	        success: function(data){
	        	if(data.result == "0"){
	   				alert(data.message);
	   				return
	   			}else{
 	   				if(change_type == "0"){
 	   					var orderno=data.data;
	   					window.location.href='html_member/payOkGoJsp.do?orderno='+orderno;
	   				}else{
	   					var map=data.data;
	   					if(map.return_msg == "OK"){
	   						callWxJsPay(map.payment_type,map.appId,map.timeStamp,map.nonceStr,map.package,map.signType,map.sign,map.out_trade_no);
			        	 }else{
			        		 alert(map.return_msg);
			        	 }
	   				}
 	   			}
 			}
	    });
	}
		
	//使用积分支付的时候
	function jisuanlastmoney(obj){
		if(isNumber(obj)){
			return;
		}
		var use=$(obj).val();
		if(use == ""){
			use="0";
		}
		var zongpaymoney=$(".zongpaymoney").val();
		var last_cha=parseFloat(zongpaymoney)-parseFloat(use);
		if(last_cha < 0){
 			$(obj).val(use.substring(0, use.length-1));
 			return;
		}
 		$(".zongjia").html("￥"+last_cha.toFixed(2)+"元");
 		$(".lastpaymoney").val(last_cha.toFixed(2));
	}
	
	
	//判断是否为数字
	function isNumber(obj){
 			if (isNaN($(obj).val())) { 
				$(obj).val($(obj).val().substring(0, $(obj).val().length-1));
			　　return true;
			} 
			if($(obj).val().indexOf("-") >= 0){
				return true;
			}
			var xiaoshu=$(obj).val().length - $(obj).val().indexOf(".");
			if($(obj).val().indexOf(".") >0 && xiaoshu > 3){
				$(obj).val($(obj).val().substring(0, $(obj).val().length-1));
			}
	}

	
	
</script>
</html>