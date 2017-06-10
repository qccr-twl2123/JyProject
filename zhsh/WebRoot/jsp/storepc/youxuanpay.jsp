<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>首页</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/storepc/business_account.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/storepc/business_account.js"></script>
        <script src="js/jquery.qrcode.min.js"></script>
        <script type="text/javascript">
        if("${ispay}" != null && "${ispay}" != "" && "${ispay}" == "1"){
        	window.top.location="<%=basePath%>storepc/goStore.do";
        }
        </script>
     </head>
    <body>
           <input type="hidden" name="youxuangoods_id" id="youxuangoods_id" value="${pd.youxuangoods_id}" />
           <div class="d2" style=" margin-top: 2%; ">
                <span>支付金额</span>
                <input value="${pd.money}" type="text" class="d2_ipt1" id="amount"  onkeyup="value=value.replace(/[^\.\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\.\d]/g,''))" readonly="readonly"/>
           </div>
            <div class="d2" id="type" style="width:60%;float:left;">
                <span class="sp_ft">支付方式</span>
                <dl class="d2_dl ">
                   <dd> 
                        <label><input type="radio" checked name="zhifu" value="1" onclick="payway('wx_pub_qr')"></input> <span class="sp_img"><img src="img/account_tx1.png" ></span><span class="hands">微信支付</span></label>
                    </dd> 
                    <dd> 
                        <label><input type="radio" name="zhifu" value="2" onclick="payway('alipay_pc_direct')"></input> <span class="sp_img"><img src="img/account_tx2.png" ></span><span class="hands">支付宝支付</span></label>
                    </dd> 
                </dl>
            </div>
             <div style="width:200px;height:200px;margin-right: 92px;float:right;" id="erweimapay">
 
 	         </div>   
             <span class="tj" onclick="wap_pay()">提交</span> 
       </body>
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
					url:"youxuan/payyouxuan.do",
					data:"in_jiqi=4&money=${pd.money}&pay_way="+channel+"&store_id=${pd.store_id}&store_operator_id=${storepd.oprator_id}&youxuangoods_id=${pd.youxuangoods_id}&url="+url,
					dataType:"json",
					success:function(data){
						if(data.result == "1"){
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
	 							}else if(channel == "alipay_pc_direct"  ||  channel == "alipay_wap"  || channel == "wx_pub"){
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
						}else{
							alert(data.message);
						}
    				}
			});  
          }
    	
    
    </script>
</html>