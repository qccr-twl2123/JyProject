<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
    <head>
        <title>档案管理</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/zhihui/dangan17.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/dangan17.js"></script>
        <script src="js/jquery.qrcode.min.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">保证金金额</span>
	          <input type=text onkeyup="value=value.replace(/[^\d]/g,'')" disabled="ture" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"  class="dangan_d1_ipt1" value="${e.earnest_money}" id="amount"/>
 	        </div>
 	        <div  >
 	        	<span class="dangan_d1_sp1">选择支付方式&nbsp;&nbsp;</span>
 	        	<div style="     margin-left: 15%;     margin-bottom: 1%; ">
	                <input  type="radio" checked name="zhifu" value="1" onclick="payway('wx_pub_qr')"/> <span class="sp_img"><img src="img/account_tx1.png" ></span><span class="hands">微信支付</span> 
  	            </div>
  	            <div style="     margin-left: 15%;     margin-bottom: 1%; ">
  	                <input  type="radio" name="zhifu" value="2" onclick="payway('alipay_wap')"/> <span class="sp_img"><img src="img/account_tx2.png" ></span><span class="hands">支付宝支付</span> 
  	            </div>
  	            <div style="     margin-left: 15%;     margin-bottom: 1%; ">
  	                <input  type="radio" name="zhifu" value="3" onclick="payway('unionpay')"/> <span class="sp_img"><img src="img/account_tx3.png" ></span><span class="hands">银联支付</span> 
 	        	</div>
 	        </div>
 	        <div class="dangan_d1 bankinfore" style="display:none;">
 	         		<div style="     margin-left: 15%;     margin-bottom: 1%; ">开户行：</div>
 	         		<div style="     margin-left: 15%;     margin-bottom: 1%; ">开户行：</div>
 	         		<div style="     margin-left: 15%;     margin-bottom: 1%; ">开户行：</div>
 	         </div>
 	        <br>
  	        <div class="dangan_d1">
		        	<c:if test="${qx.edit eq '1'}"> 
			        	<a class="middle_a" onclick="wap_pay('${pd.sp_file_id}')"  target="ifra">
			            <span class="dangan16_sp1 mgleft">确认</span>
			            </a>
		            </c:if>
		            <c:if test="${qx.edit eq '1'}"> 
	 	            <a class="middle_a" href="zhihui_sp_file/goEdit.do?sp_file_id=${pd.sp_file_id}&currentPage=${pd.currentPage}"  target="ifra"> 
		           		<span class="dangan16_sp1">取消</span>
		            </a>
	            </c:if>
	        </div>
	         <div style="width:100%;height:200px;margin:0 auto;" id="erweimapay">
 
 	         </div> 
	        </c:if>
	         <script src="js/ping/pingpp.js" type="text/javascript"></script>
	        <script type="text/javascript">
	      //更新支付方式
	    	var channel="wx_pub_qr";
	    	function payway(value){
	    		channel=value;
	    		if(value == "unionpay"){
	    			$(".bankinfore").show();
	    		}else{
	    			$(".bankinfore").hide();
	    		}
	    	}
	    	
	     	//ping++支付
	    	function wap_pay(value) {
	     		if(channel == "unionpay"){
	     			var url='<%=basePath%>zhihui_sp_file/goEdit.do?sp_file_id=${pd.sp_file_id}&currentPage=${pd.currentPage}';
	     			$.ajax({
						type:"post",
							url:"zhihui_sp_file/addPayCz.do",
							data:"money="+$("#amount").val()+"&pay_way="+channel+"&sp_file_id="+value ,
							dataType:"json",
							success:function(data){
								 if(data.result== "1"){
									 window.location.href=url;
								 }
		   					}
					});  
	     		}else{
	     			var url='<%=basePath%>zhihui_sp_file/goEdit.do?sp_file_id=${pd.sp_file_id}';
	 	      		//获取charge
		    		$.ajax({
						type:"post",
							url:"zhihui_sp_file/sp_PartyCz.do",
							data:"money="+$("#amount").val()+"&pay_way="+channel+"&sp_file_id="+value+"&url="+url,
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
									}else if(channel == "wx_pub_qr" ){
										var credential=charge.credential;
										var wx_pub_qr =credential.wx_pub_qr ;
										$("#erweimapay").empty();
										//生成二维码：商家ID以及zhuohao.生成的二维码下载，图片尺寸为5*6CM；
				 				        jQuery($("#erweimapay")).qrcode({ width: 150, height: 150,  text: wx_pub_qr }); 
		 							}else if(channel == "alipay_wap"  || channel == "wx_pub"){
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
 	          }
	    	
	        
	        </script>
      </body>
</html>