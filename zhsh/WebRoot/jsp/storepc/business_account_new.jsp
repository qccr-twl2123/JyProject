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
	<title>积分充值</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/zhxx_jfzc.css">
	<style type="text/css">
	li{
		margin: 8px;
	}
	body{
		height:98%;	
	}
	</style>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
	<ul style="line-height:2.5; position: relative;">
		<li>
			<span>可用积分</span>：
			<span>${pd.now_wealth}</span>
		</li>
		<li>
			<span>冻结积分</span>：
			<span>${pd.tixian_money }</span>
		</li>
		<li>
			<span>充值现金</span>：
			<input type="text" class="inp-l" id="amount"  onkeyup="value=value.replace(/[^\.\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\.\d]/g,''))">
		</li>
		<li>
			<span style="vertical-align: top;" >支付方式：</span>
			<span class="act_box"   onclick="payway('alipay_pc_direct','1')">
				<img src="img/apay.png" alt="">
				<img src="img/active.png" alt="" class="act_img">
			</span>
			<span class="act_box"   onclick="payway('wx_pub_qr','2')">
				<img src="img/wechatpay.png" alt="">
				<img src="img/active.png" alt="" class="act_img">
			</span>
		</li>

		<li class="wx_ewm" style="display:none">
			<div class="img_box" id="erweimapay">
			
 				
			</div>
			<div class="ewm_title">
				30秒后将会自动刷新页面，请尽快支付
			</div>
		</li>
		<c:if test="${storeqx.add eq '1'}">
			<li style="text-align:left;">
				<span class="anniu-l" style="margin:20px 0 0 80px;" onclick="wap_pay()">确定</span>
				<span class="anniu-l" style="margin:20px 0 0 80px;" onclick="shuaxin()">刷新</span>
			</li>
		</c:if>
	</ul>
	<div class="alipay">
	
	</div>
</c:if>
</body>
<script src="js/jquery-1.8.0.min.js"></script>
<script src="js/jquery.qrcode.min.js"></script>
<script type="text/javascript">
        if("${ispay}" != null && "${ispay}" != "" && "${ispay}" == "1"){
        	window.top.location="<%=basePath%>storepc/goStore.do";
        }
</script>
<script type="text/javascript">
		//刷新
        function shuaxin(){
        	window.location.href="storepc_wealth/list.do"; 
        }
    	
    	//更新支付方式
    	var channel="alipay_pc_direct";
    	function payway(value,type){
    		channel=value;
    	}
    	
      	//微信支付宝支付
     	function wap_pay() {
      		if($("#amount").val() == "" || $("#amount").val() =="0"){
      			alert("金额不能为空");
      			return;
      		}
      		//清除定时器
      		if(window.t != null ){
      			clearInterval(t);
      		}
       		$.ajax({
					type:"post",
						url:"storepc_paymoney/store_cz.do",
						data:{
							"money":$("#amount").val(),"pay_way":channel
						},
	 					dataType:"json",
						success:function(data){ 
							 if(data.result == "1"){
								 var map=data.data;
								 if(channel == "wx_pub_qr"){
									 var wx_pub_qr =map.code_url ;
									 $(".wx_ewm").show();
									 $("#erweimapay").empty();
									 //生成二维码：商家ID以及zhuohao.生成的二维码下载，图片尺寸为5*6CM；
			 				         jQuery($("#erweimapay")).qrcode({ text: wx_pub_qr }); 
			 				         $("#erweimapay").append("<span>微信支付二维码</span>");
									 //设置定时器
										 var time=30;
			 		       				 window.t=setInterval(function() {
		 			       				time--;
			 			       				if(time == 0){
		 			       					shuaxin();
		 			       				}else{
		 			       					$(".ewm_title").html(time+"秒后将会自动刷新页面，请尽快支付");
		 			       				}
			 			       			},1000);
					 			 }else{
					 				window.open('<%=basePath%>storepc_paymoney/goPayChongZhi.do?total_amount='+$("#amount").val()+'&body=3&out_trade_no='+map);
					 			 }
 							 }else{
								 alert(data.message);
							 }
	   					}
				});  
           }
    	
   
	var box=$(".act_box")
	box.click(function(e){
		box.css({"border":"1px solid #999"});
		$(".act_img").css({"display":"none"});
		$(".act_box").attr("act","0");
		$(e.target).parent().attr("act","1");
 		if ($(box[1]).attr("act") == "0") {
 			$(box[0]).css({"border":"1px solid rgb(255,108,0)"})
			$($(".act_img")[0]).css({"display":"block"})
		}else{
			$(box[1]).css({"border":"1px solid rgb(255,108,0)"})
			$($(".act_img")[1]).css({"display":"block"})
		}
	})
    $(".act_img")[0].click();


</script>
</html>