<%-- <!-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>支付</title>
        <base href="<%=basePath%>">
        <meta charset="utf-8">
        <link rel="stylesheet" href="<%=basePath%>css/storepc/payment.css">
        <script src="<%=basePath%>js/storepc/jquery-1.8.0.min.js"></script>
        <script src="js/jquery.qrcode.min.js"></script>
    </head>
    <body>
       <!-- header -->
       <div class="signin_header">
           <span class="signin_logo">
               <img src="img/logo.png" style="width:70px;height:70px;margin-top:10px;">
           </span>
           <span class="signin_header_sp1"> 
              	九鱼销链商家中心
           </span>
           <p class="header_p1" >${storepd.store_name}<a href="<%=path %>/storepc/loginOut.do"><span>安全退出</span></a></p>
       </div>
       <!-- nav -->
       <!-- <div class="nav"> 
          <dl>
            <dd class=" ddclick" data-id="#homepage"><a href="business_homepage1.html" target="ifra">首页</a></dd>
            <dd class=" ddclick" data-id="#marketing"><a href="business_marketing1.html" target="ifra">营销控台</a></dd>
            <dd class=" ddclick" data-id="#shop"><a href="business_shop1.html" target="ifra">商品管理</a></dd>
            <dd class=" ddclick" data-id="#base"><a href="business_base.html" target="ifra">基础信息</a></dd>
            <dd class=" ddclick" data-id="#account"><a href="business_account.html" target="ifra">账户信息</a></dd>
            <dd id="weixin"><a target="ifra2" href="business_weixin1.html">互动/沟通</a></dd>
            <dt>营业状态 <select>
              <option>正在营业中</option>
              <option>试营业</option>
            </select></dt>
          </dl>
       </div> -->
       <!-- body -->
      
       <div class="signin_body">
          <!-- 首页 -->
          <span class="right_bg">
             <!--  <img src="img/right_bg.png" width="100%"> -->
          </span>
          <div class="signin_body_right">
               <div class="payment_d1">
                  <span class="payment_d1_sp2">支付保证金</span>
                  <span class="payment_d1_sp1">开通九鱼销链保证金/服务费 : <span class="red">${pd.allmoney}</span>  <span style="width: 20px;"></span> 有效期至 : <span class="red">${pd.endtime }</span></span>
               </div>
               <div class="payment_d2 mgtop30"> 
                  <input type="radio" name="pay"  checked onclick="payway('wx_pub_qr')"></input>
                  <span class="payment_d2_sp1"><img src="img/account_tx1.png"></span>
                  <span>微信支付</span>
               </div>
                <div class="payment_d2"> 
                  <input type="radio" name="pay" onclick="payway('alipay_pc_direct')"></input>
                  <span class="payment_d2_sp1"><img src="img/account_tx2.png"></span>
                  <span>支付宝支付</span>
               </div>
                <!-- <div class="payment_d2"> 
                  <input type="radio" name="pay"></input>
                  <span class="payment_d2_sp1"><img src="img/account_tx4.png"></span>
                  <span>银联支付</span>
               </div> -->
               <div style="width:200px;height:200px;float:right;margin-right:30%;" id="erweimapay">
 
 	           </div> 
  	           <c:if test="${pd.allmoneystatus eq '1'}">
	 	           <div class="payment_d2">
	                 <span class="payment_btn" onclick="wap_pay()">支付</span>
	              </div>
 	           </c:if>
                <div class="payment_d2">
                 <span class="payment_btn" onclick="overpay()" style="background-color: #006e98;width:150px;">已支付，前往首页</span>
               </div>
          </div>
       </div>
        <!-- footer -->
       <!-- <div class="signin_footer">
          <div class="footer_d1">
            <span>我要开店</span>
            <span>  关于九鱼 </span>
            <span>  加入我们</span>
          </div>
          <div class="footer_d2">
            [沪]CP备504336号-2 本站发布所有内容，未经许可，不得转载
          </div>
       </div> -->
        <script src="js/ping/pingpp.js" type="text/javascript"></script>
    <script type="text/javascript">
    	
    	//更新支付方式
    	var channel="wx_pub_qr";
    	function payway(value){
    		channel=value;
    	}
    	
     	//ping++支付
    	function wap_pay() {
     		var url='<%=basePath%>storepc/goStore.do';
      		//获取charge
    		$.ajax({
				type:"post",
					url:"<%=basePath%>storepc_pay/store_payEarnest_money.do",
					data:"money=${pd.allmoney}&pay_way="+channel+"&store_id="+"${storepd.store_id}"+"&store_operator_id="+"${storepd.oprator_id}"+"&url="+url,
					dataType:"json",
					success:function(data){
						var charge=data.data;
						if(charge == ""){
							alert("支付渠道未开通");
						}else{
							if(channel == "alipay_qr"){
								var credential=charge.credential;
								var alipay_qr=credential.alipay_qr;
								$("#erweimapay").empty();
								//生成二维码：商家ID以及zhuohao.生成的二维码下载，图片尺寸为5*6CM；
		 				        jQuery($("#erweimapay")).qrcode({ width: 150, height: 150,  text: alipay_qr }); 
		 				        $("#erweimapay").append("<span>支付宝支付二维码</span>");
							}else if(channel == "wx_pub_qr" ){
								var credential=charge.credential;
								var wx_pub_qr =credential.wx_pub_qr ;
								$("#erweimapay").empty();
								//生成二维码：商家ID以及zhuohao.生成的二维码下载，图片尺寸为5*6CM；
		 				        jQuery($("#erweimapay")).qrcode({ width: 150, height: 150,  text: wx_pub_qr }); 
		 				        $("#erweimapay").append("<span>微信支付二维码</span>");
 							}else if(channel == "alipay_wap"  || channel == "wx_pub" || channel == "alipay_pc_direct"){
 								//支付
 								pingpp.createPayment(charge, function(result, err){
 								    console.log(result);
 								    console.log(err.msg);
 								    console.log(err.extra);
 								    if (result == "success") {
 								    	alert("success");
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
   					}
			});  
          }
    	
     	//判断是否已经支付
     	function overpay(){
     		if("${pd.allmoneystatus}" == "0"){
     			window.location.href='<%=basePath%>storepc/goStore.do?store_id=${pd.store_id}';
     		}else{
     			$.ajax({
    				type:"post",
    					url:"<%=basePath%>storepc/isPayBaoZhengJin.do",
    					data:{"store_id":"${pd.store_id}"},
    					dataType:"json",
    					success:function(data){ 
    						if(data.result =="0"){
    							alert(data.message);
    						}else{
    							window.location.href='<%=basePath%>storepc/goStore.do?store_id=${pd.store_id}';
    						}
       					}
    			});  
     		}
      	}
    
    </script>
    </body>
</html> --%>