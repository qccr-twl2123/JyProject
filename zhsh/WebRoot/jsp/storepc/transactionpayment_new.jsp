<!-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>交易扣点页面</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="store_favicon.ico" >
     <link rel="Bookmark" href="store_favicon.ico">
     <link rel="icon" type="image/gif" href="store_animated_favicon1.gif" >
    <link rel="stylesheet" href="css/pcstore/bootstrap.min.css">
    <link rel="stylesheet" href="css/pcstore/hsd_pay.css">
    <style type="text/css">
    .h_title2{
    	    font-size: 18px;
    }
    section .sec_cont .item .price {
    	color: red;
    	font-size: 26px;
    	line-height: 1.6;
 	}
    </style>
 </head>
<body onkeydown="BindEnter(event)">
<div class="bg">
<div class="dask">
    <div class="alertpay alert">
        <div class="close">X</div>
        <div class="alert_tit">
            <img src="img/wechatpay2.png" alt=""><span>微信扫一扫付款</span>
        </div>
        <div class="ewm"></div>
    </div>
</div>
<!--头部-->
<header>
    <div class="head_cont">
       <img src="img/page/pay/paylogo.png" alt="" class="logo">
        <div class="title">•    付款</div>
        <div class="timebox">
            <span>服务到期时间：</span><span class="time" id="endtime"></span>
         </div>
        <div class="one"></div>
    </div>
</header>
<!--内容-->
<section>
    <div class="sec_cont">
  		<div class="item"  onclick="surethis(this,'10','','12')">
				 <div class="pricebox" >
	 				  <span  class="price_text">首次充值积分：<span class="price">10</span>元</span>
					  <br/>
	 				  <span  class="price_text">交易扣点：<span class="price">${pd.transaction_points}</span>%</span>
					  <br/>
					  <span  class="price_text">服务时限：<span class="price qujian" style="font-size: 16px;"></span></span>
				 </div>
  				 <img src="img/page/pay/active.png" alt="">
		 </div>
         <div class="allprice">
            <span>应付金额:<span class="pricetext payMoney"></span>元</span>
            <input type="hidden" class="starttime" value="" />
        </div>
        <div class="payway">
            <div class="text">
                <span>请选择支付方式：</span>
            </div>
            <div class="pay" onclick="wap_pay('alipay_pc_direct')">
                <img src="img/page/pay/apay.png" alt="">
                <img src="img/page/pay/active.png" alt="" class="payactive">
            </div>
            <div class="pay" onclick="wap_pay('wx_pub_qr')">
                <img src="img/page/pay/wechatpay.png" alt="">
                <img src="img/page/pay/active.png" alt="" class="payactive">
            </div>
        </div>
        <div class="ht">
            <input type="checkbox" name="hetong" id="choose" checked>
            <span>我已经阅读并同意 <a href="jsp/storepc/hts.html" target="_blank">《九鱼销链软件销售合同书》</a></span>
        </div>
        <div class="assign" >
            <img src="img/page/true.png" alt="" onclick="sure_pay()">
        </div>
        <div class="next" onclick="overpay()" id="overpay" >
            <img src="img/page/next.png" alt="">
        </div>
    </div>
</section>
</div>
</body>
<script src="js/jquery-1.12.4.min.js"></script>
<script src="js/ping/pingpp.js" type="text/javascript"></script>
<script src="js/jquery.qrcode.min.js"></script>
<script>
			    var item=$(".item");
			    var imgs=$(".item").children("img");
  			    item.click(function(){
  			    	var that=$(this).children("img")
 			        item.css({"border": "2px solid #15acf2"});
			        imgs.css({"display":"none"});
			        $(this).css({"border": "2px solid #ff6c00"});
			        that.css({"display":"block"})
 			    })
 			    item[0].click();
 			    
  			    var payway=$(".pay");
			    payway.click(function(){
			        payway.css({"border":"1px solid #b5b5b5"})
			        $(".payactive").css({"display":"none"})
			        $(this).css({"border":"1px solid #ff6c00"})
			        $(this).children("img").css({"display":"block"});
			    })
			    
			    //更新支付方式
		    	var channel="";
				function wap_pay(value) {
					channel=value;
				}
			    
			    
               //确认服务
               function surethis(obj,number,city_file_fee_id,month){
            	   $(".payMoney").html(number);
               	   var thedate = new Date(); 
             	   var datestr=thedate.getFullYear() + '-' +  (parseInt(thedate.getMonth())+1)  + '-' + thedate.getDate();
             	   $(".starttime").val(datestr);
              	   var d=dateAdd(thedate,"m",parseInt(month));
             	   var datestr2=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
            	   $("#endtime").html(datestr2);
            	   $(obj).find(".qujian").html(datestr+"至"+datestr2);
                }
                
               
            //   n个月后
           	function dateAdd(date,strInterval, Number) {  //参数分别为日期对象，增加的类型，增加的数量 
                var dtTmp = date;  
                switch (strInterval) {   
                    case 'second':
                    case 's' :
                        return new Date(Date.parse(dtTmp) + (1000 * Number));  
                    case 'minute':
                    case 'n' :
                        return new Date(Date.parse(dtTmp) + (60000 * Number));  
                    case 'hour':
                    case 'h' :
                        return new Date(Date.parse(dtTmp) + (3600000 * Number)); 
                    case 'day':                           
                    case 'd' :
                        return new Date(Date.parse(dtTmp) + (86400000 * Number)); 
                    case 'week':                           
                    case 'w' :
                        return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));
                    case 'month':
                    case 'm' :
                        return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
                    case 'year':
                    case 'y' :
                        return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
                }  
            }

     	
	
     	//微信支付/支付宝支付
    	function sure_pay() {
		        if(channel == ""){
					alert("请选择一种支付方式");
					return;
				}
	     		var paymoney=$(".payMoney").html();
	     		if(paymoney == ""){
	     			alert("请先选择一种交易扣点");
	     			return;
	     		}
	     		//清除定时器
	      		if(window.t1 != null ){
	      			clearInterval(t1);
	      		}
	     		if($("#choose").is(":checked")){
	     			$.ajax({
     					type:"post",
     						url:"storepc_paymoney/transaction_pointsPay.do",
     						data:{
	     							"money":paymoney,"pay_way":channel
     						},
     	 					dataType:"json",
     						success:function(data){ 
     							 if(data.result == "1"){
     								 var map=data.data;
     								 if(channel == "wx_pub_qr"){
     									 var wx_pub_qr =map.code_url ;
        								 $(".dask").show();
        								 $(".ewm").empty();
        								 //生成二维码：商家ID以及zhuohao.生成的二维码下载，图片尺寸为5*6CM；
        		 				         jQuery($(".ewm")).qrcode({ width: 180, height: 180,  text: wx_pub_qr }); 
        								 //设置定时器
        								 window.t1 = setInterval(isOverPay, 10000);//10秒执行一次
     				     			 }else{
     				     				 window.open('<%=basePath%>storepc_paymoney/goPayChongZhi.do?total_amount='+paymoney+'&body=1&out_trade_no='+map);
     				     			 }
     								
     							 }else{
     								 alert(data.message);
     							 }
     	   					}
     				});  
 	     		}else{
	     			alert("请先同意，勾选");
	     			return;
	     		}
           };
           
        //定时器
        function isOverPay(){
        	$.ajax({
				type:"post",
					url:"storepc/isPayBaoZhengJin.do",
 					dataType:"json",
					success:function(data){ 
						if(data.result =="1"){
							window.location.href='storepc/goSheZhiOne.do?jichushezhi=00000000000';
  						}else{
  							alert(data.message);
  						}
   					}
			});  
        }
           
      	//判断是否已经支付
     	function overpay(type){
     		$.ajax({
				type:"post",
					url:"<%=basePath%>storepc/isPayBaoZhengJin.do",
					data:{"store_id":"${pd.store_id}"},
					dataType:"json",
					success:function(data){ 
						if(data.result =="0"){
 							alert(data.message);
  						}else{
							window.location.href='<%=basePath%>storepc/goSheZhiOne.do?store_id=${pd.store_id}&jichushezhi=00000000000';
						}
   					}
			});  
      	}
      	
     	//使用document.getElementById获取到按钮对象
    	function BindEnter(event){
    		var overpay = document.getElementById("overpay");
    		if(event.keyCode == 13){
    			overpay.click();
    			event.returnValue = false;
    		}
    	}
     	//关闭窗口
    	$(".close").click(function(){
    		 clearInterval(t1);
    		 window.location.reload(); //刷新当前页面
    	});
 </script>
</html>