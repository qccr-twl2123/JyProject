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
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">保证金金额</span>
	          <input type=text onkeyup="value=value.replace(/[^\d]/g,'')" disabled="ture" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"  class="dangan_d1_ipt1" value="${e.earnest_money}" id="amount"/>
 	        </div>
 	        <div>
 	        	<span class="dangan_d1_sp1">选择支付方式&nbsp;&nbsp;</span>
                <input  type="radio" checked name="zhifu" value="1" onclick="payway('wx')"/> <span class="sp_img"><img src="img/account_tx1.png" ></span><span class="hands">微信支付</span> 
                <br>
                <span class="dangan_d1_sp1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <input  type="radio" name="zhifu" value="2" onclick="payway('alipay_wap')"/> <span class="sp_img"><img src="img/account_tx2.png" ></span><span class="hands">支付宝支付</span> 
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
	        </c:if>
	         <script src="js/ping/pingpp.js" type="text/javascript"></script>
	        <script type="text/javascript">
	      //更新支付方式
	    	var channel="wx";
	    	function payway(value){
	    		channel=value;
	    	}
	    	
	     	//ping++支付
	    	function wap_pay(value) {
	     		var url='<%=basePath%>zhihui_service_performance/goEdit.do?sp_file_id=${pd.sp_file_id}';
	     		//var url='www.baidu.com';
	      		//获取charge
	    		$.ajax({
					type:"post",
						url:"zhihui_service_performance/partyCz.do",
						data:"money="+$("#amount").val()+"&pay_way="+channel+"&sp_file_id="+value+"&url="+url,
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
				});  
	          }
	    	
	        
	        </script>
      </body>
</html>