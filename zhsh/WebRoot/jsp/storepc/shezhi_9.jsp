<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>积分充值</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
    <link rel="stylesheet" href="css/pcstore/bootstrap.min.css">
    <link rel="stylesheet" href="css/pcstore/hsd_jfpay.css">
  </head>
<body  onkeydown="BindEnter(event)">
<div class="dask">
    <div class="alertpay alert">
        <div class="close">X</div>
        <div class="alert_tit">
            <img src="img/page/pay/wechatpay2.png" alt=""><span>微信扫一扫付款</span>
        </div>
         <div class="ewm"></div>
    </div>
</div>
<div class="bg">
<header>
    <div class="head_cont">
        <img src="img/page/jfpay.png" alt="" class="logo">
        <div class="title">•  积分充值 </div>
        <div class="one"></div>
    </div>
</header>
<section>
    <div class="sec_cont">
        <p><span>声明：</span>为了您的交易能够顺利达成，您需要进行积分充值，首次充值<span>100</span>元以上，积分不足时会员消费将被中止，充值的积分和会员支付的
            消费款项可在系统里面随时进行提现。</p>

    <ul>
        <li>
            <span>可用积分：</span>
            <span>${pd.now_wealth}</span>
        </li>
        <li>
            <span>冻结积分：</span>
            <span>${pd.tixian_money }</span>
        </li>
        <li>
            <span>现金充值：</span>
 			<input type="text" class="size" id="amount"  onkeyup="value=value.replace(/[^\.\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\.\d]/g,''))"/>
         </li>
        <li>
            <span>支付方式：</span>
            <label class="radio-inline" onclick="payway('wx_pub_qr')">
                <input type="radio" name="inlineRadioOptions" id="inlineRadio2" >
                <img src="img/page/wechat.png" alt="">
                <span>微信支付</span>
            </label>
            <label class="radio-inline" onclick="payway('alipay_pc_direct')">
                <input type="radio" name="inlineRadioOptions" id="inlineRadio3">
                <img src="img/page/apay.png" alt="">
                <span>支付宝支付</span>
            </label>
        </li>
        <li class="checkbutton">
            <div class="button_box" style="display:none;">
                <!-- <div class="butt" onclick="shuaxin()">刷新</div> -->
                <div class="butt" onclick="sure_pay()">确定</div>
            </div>
        </li>
    </ul>
    </div>
</section>
<footer>
    <div class="font_cont">
        <div class="button_box">
            <div class="butt" onclick="gonext()" id="gonext">稍后充值，前往下一步</div>
        </div>
    </div>
</footer>
</div>
<script src="js/jquery-1.8.0.min.js"></script>
<script src="js/ping/pingpp.js" type="text/javascript"></script>
<script src="js/jquery.qrcode.min.js"></script>
<script type="text/javascript">
        function shuaxin(){
        	 window.location.reload(); //刷新当前页面
        }
     	//更新支付方式
    	var channel="wx_pub_qr";
    	function payway(value){
    		channel=value;
    		$(".button_box").show();
    	}
      	//ping++支付
    	function sure_pay() {
      		if($("#amount").val() == "" || $("#amount").val() =="0"){
      			return;
      		}
     		var url='<%=basePath%>storepc/goSheZhiOne.do?jichushezhi=${pd.jichushezhi}';
      		//获取charge
    		$.ajax({
				type:"post",
					url:"storepc_pay/store_PartyCz.do",
					data:"money="+$("#amount").val()+"&pay_way="+channel+"&store_id=${storepd.store_id}&store_operator_id=${storepd.oprator_id}&url="+url,
					dataType:"json",
					success:function(data){
						if(data.result == "0"){
							alert(data.message);
 							return;
						}
						var charge=data.data;
						if(charge == ""){
							alert("支付渠道未开通");
						}else{
							if(channel == "alipay_qr"){
								var credential=charge.credential;
								var alipay_qr=credential.alipay_qr;
								$(".dask").show();
								$(".ewm").empty();
								//生成二维码：商家ID以及zhuohao.生成的二维码下载，图片尺寸为5*6CM；
		 				        jQuery($(".ewm")).qrcode({ width: 180, height: 180,  text: alipay_qr }); 
							}else if(channel == "wx_pub_qr" ){
								var credential=charge.credential;
								var wx_pub_qr =credential.wx_pub_qr ;
								$(".dask").show();
								$(".ewm").empty();
								//生成二维码：商家ID以及zhuohao.生成的二维码下载，图片尺寸为5*6CM；
		 				        jQuery($(".ewm")).qrcode({ width: 180, height: 180,  text: wx_pub_qr }); 
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
   					}
			});  
          }
    	
    	//使用document.getElementById获取到按钮对象
 		function BindEnter(event){
 			var gonext = document.getElementById("gonext");
 			if(event.keyCode == 13){
 				gonext.click();
 				event.returnValue = false;
 			}
 		}
		
    //下一步
	function gonext(){
		var n="${pd.now_wealth}";
		window.location.href='<%=basePath%>storepc/goSheZhiOne.do?store_id=${pd.store_id}&jichushezhi=${pd.jichushezhi}';
		<%-- if(parseFloat(n) < 100){
				alert("至少充值100，若已充值请刷新一下");
				return;
		}else{
			window.location.href='<%=basePath%>storepc/goSheZhiOne.do?store_id=${pd.store_id}&jichushezhi=${pd.jichushezhi}';
		} --%>
	}
	//关闭窗口
	$(".close").click(function(){
		 window.location.reload(); //刷新当前页面
	})
    
</script>
  
</body>
</html>