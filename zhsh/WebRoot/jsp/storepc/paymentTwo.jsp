<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>服务续费</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/zhxx_fwxf.css">
	<style type="text/css">
	.wx_ewm{
		position: absolute;
		width: 30%;
		height: 30vw;
		border:1px solid #117dd0;
		right:0;
		left: 0;
		bottom: 0;
		top: 0;
		margin: auto;overflow: hidden;
	}
	.ewm_title{
		position: absolute;bottom: 0;
		text-align: center;
		width: 100%;
		height: 34px;
		line-height: 34px;
		color: #fff;
		background: #117dd0;
		font-size: 87.25%;
	}
	.ewm_box{
		position: fixed;
		width: 100%;
		height: 100%;
		background: rgba(120,120,120,0.7);
		z-index: 1;
		display:none;
	}
	.ewm_box .img_box{
		width: 100%;
		height: 90%;
		text-align: center;
		background: #fff;
	}
	.ewm_box .img_box canvas{
		width: 70%;
		height: 70%;
		padding-top: 10%;
	}
	.ewm_box .img_box span{
		width: 100%;
		display: inline-block;
		line-height: 2.5;
	}
  	</style>
</head>
<body>
  <c:if test="${storeqx.look eq '1'}">
  	 <c:if test="${pd.isopen_points eq '0' }">
		  	 <div class="ewm_box">
					<div class="wx_ewm">
						<div class="img_box ewm">
		 					
						</div>
						<div class="ewm_title">
							 
						</div>
					</div>
				</div>
		 	<ul class="xf">   <!-- 续费页 -->
				<li style="text-align:right;border-bottom: 1px solid #119dd0;line-height:2.5;" class="clf">
					<c:if test="${storeqx.add eq '1'}"><span class="flr col-9 padd_lr cp xf_sqfp" onclick="goAddFaPiao()">索取发票</span></c:if>  
					<a  onclick="shuaxin()"  class="flr col-blue padd_lr cp">刷新</a>
					<span class="flr padd_lr">服务到期时间：<span class="col-r">${pd.endtime}</span></span>
				</li>
				<c:if test="${pd.day < 30 }">
					<li style="line-height:2.5;padding-left:5%;">
						<span>你的服务费将在<span class="col-red">${pd.day}</span>天到期，到期后所有服务将不能正常使用，请及时续费</span>
					</li>
				</c:if>
				<li style="padding-top:20px;margin:0 5%;">
					<ul>
						<c:forEach items="${feeList}" var="var">
							<li class="list-item" onclick="surethis(this,'${var.purchase_price}','${var.city_file_fee_id}','${var.allmonth}')">		<!-- item -->
								<img src="img/active.png" alt="" class="list_img">
								<ul>
									<li>
										
										<span style="font-size:190%">
											  <c:if test="${var.buying_time eq '3' }">季度服务</c:if>
											  <c:if test="${var.buying_time eq '6' }">半年服务</c:if>
											  <c:if test="${var.buying_time eq '12' }">年度服务</c:if>
											  <c:if test="${var.buying_time eq '24' }">两年服务</c:if>
										</span>
										<span class="font-s">已赠送<span class="col-red">${var.present_time}</span>个月</span>
									</li>
									<li>
										<span class="col-red price" style="font-size:190%">${var.purchase_price}</span>
										<span >元/<span class="col-blue month">${var.allmonth}</span>个月</span>
									</li>
								</ul>
							</li>
						</c:forEach>
					</ul>
				</li>
				<li style="line-height:2.5;padding-left:5%;">
					<span>续费后的到期时间：</span>
					<span class="col-red"  id="endtime"></span>
				</li>
				<li style="line-height:2.5;padding-left:5%;">
					<span>应付金额：<span class="col-red payMoney"></span>元</span>
				</li>
				<li style="line-height:2.5;padding-left:5%;">
					<span style="vertical-align: top;" >支付方式：</span>
					<span class="act_box" act="0" onclick="wap_pay('alipay_pc_direct')" >
						<img src="img/apay.png" alt="">
						<img src="img/active.png" alt="" class="act_img">
					</span>
					<span class="act_box" act="0" onclick="wap_pay('wx_pub_qr')">
						<img src="img/wechatpay.png" alt="">
						<img src="img/active.png" alt="" class="act_img">
					</span>
				</li>
				<li style="line-height:2.5;padding-left:5%;">
					<span class="check_item">
						<input type="checkbox" name="hetong" id="choose" checked>
						<span>我已经阅读并同意<a href="jsp/storepc/hts.html">《九鱼销链软件销售合同书》</a></span>
					</span>
				</li>
				<li style="text-align:center;">
					<span class="anniu-l" onclick="sure_pay()">确定</span>
				</li>
			</ul>
			<input name="city_file_fee_id" class="city_file_fee_id" type="hidden" />
			<input name="starttime" class="starttime" type="hidden" value="" />
			<input name="city_file_fee_id" class="city_file_fee_id" type="hidden" />
  	 </c:if>
	 <c:if test="${pd.isopen_points eq '1' }">
	 	<ul class="xf">   
 				<li style="padding-top:20px;margin:0 5%;">
					<ul>
						<li class="list-item">	 
								<img src="img/active.png" alt="" class="list_img">
								<ul>
									<li>
  										<span style="font-size:190%"> 当前交易扣点：<span class="col-red">${pd.transaction_points}</span>% </span>
 									</li>
 								</ul>
							</li>
					</ul>
				</li>
 			</ul>
	 </c:if>
</c:if>
</body>
<script src="js/jquery-1.12.4.min.js"></script>
<script src="js/ping/pingpp.js" type="text/javascript"></script>
<script src="js/jquery.qrcode.min.js"></script>
<script>
$(function(){
	var box=$(".act_box")
	box.click(function(e){
		box.css({"border":"1px solid #999"})
		$(".act_img").css({"display":"none"})
		for (var i = 0; i < box.length; i++) {
			$(box[i]).attr("act","0")
		};
		$(e.target).parent().attr("act","1")
		if ($(box[1]).attr("act")==0) {
			$(box[0]).css({"border":"1px solid rgb(255,108,0)"})
			$($(".act_img")[0]).css({"display":"block"})
		}else{
			$(box[1]).css({"border":"1px solid rgb(255,108,0)"})
			$($(".act_img")[1]).css({"display":"block"})
		}
	})
	$(".act_img")[0].click()   /*预置初始状态为支付宝*/




	/*服务选择*/
	var list=$(".list-item")
	list.click(function(e){
		$(".list_img").css("display","none")
		$(".list-item").css("border","2px solid #117dd0")
		$(e.target).parents(".list-item").children("img").css("display","block")
		$(e.target).parents(".list-item").css("border","2px solid rgb(255,108,0)")
	})
 	$(".list_img")[0].click()    /*初始化  默认第一个  选中 */  
});
				//前往申请发票页面
				function goAddFaPiao(){
					window.location.href="<%=basePath%>fapiao/goAddFaPiao.do?store_id=${storepd.store_id}"; 
				}
	
				//刷新当前页面
				function shuaxin(){
					window.location.reload();  
				}
 			    
			    //更新支付方式
		    	var channel="alipay_pc_direct";
				function wap_pay(value) {
					channel=value;
				}
			    
			    
               //确认服务
               function surethis(obj,number,city_file_fee_id,month){
            	   $(".payMoney").html(number);
            	   $(".city_file_fee_id").val(city_file_fee_id);
                   var d=dateAdd(new Date("${pd.endtime}".replace(/-/,"/")) ,"m",parseInt(month));
             	   var datestr=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
            	   $("#endtime").html(datestr);
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

     	
	
     	//ping++支付
    	function sure_pay() {
	    		var paymoney=$(".payMoney").html();
	     		if(paymoney == ""){
	     			alert("请先选择一种年费");
	     			return;
	     		}
		        if(channel == ""){
					alert("请选择一种支付方式");
					return;
				}
  	     		if($("#choose").is(":checked")){
	     			var url='<%=basePath%>storepc/goStore.do';
	          		//获取charge
	        		$.ajax({
	    				type:"post",
	    					url:'<%=basePath%>storepc_pay/store_payEarnest_money.do',
	    					data:"in_jiqi=4&profit_type=10&starttime="+$(".starttime").val()+"&endtime="+$("#endtime").html()+"&next_city_file_fee_id="+$(".city_file_fee_id").val()+"&money="+paymoney+"&pay_way="+channel+"&store_id="+"${storepd.store_id}"+"&store_operator_id="+"${storepd.oprator_id}"+"&url="+url,
	    					dataType:"json",
	    					success:function(data){
	    						var charge=data.data;
	    						var waterrecord_id=charge.orderNo;
	    						if(charge == ""){
	    							alert("支付渠道未开通");
	    						}else{
	    							if(channel == "alipay_qr"){
	    								var credential=charge.credential;
	    								var alipay_qr=credential.alipay_qr;
	    								 
	    							}else if(channel == "wx_pub_qr" ){
	    								var credential=charge.credential;
	    								var wx_pub_qr =credential.wx_pub_qr ;
	    								$(".ewm_box").show();
	    								$(".ewm").empty();
	    								//生成二维码：商家ID以及zhuohao.生成的二维码下载，图片尺寸为5*6CM；
	    		 				        jQuery($(".ewm")).qrcode({ text: wx_pub_qr }); 
	    		 				        $(".ewm").append("<span>微信支付二维码</span>");
	    								//设置定时器
	     								var time=30;
	     	 		       				window.t=setInterval(function() {
	    	 			       				time--;
	     	 			       				if(time == 0){
	    	 			       					shuaxin();
	    	 			       				}else{
	    	 			       					isOverPay(waterrecord_id);
	    	 			       					$(".ewm_title").html(time+"秒后将会自动刷新页面，请尽快支付");
	    	 			       				}
	     	 			       			},1000);
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
	     		}else{
	     			alert("请先同意，勾选");
	     			return;
	     		}
           }
     	
    	//定时器
        function isOverPay(waterrecord_id){
        	$.ajax({
				type:"post",
					url:"<%=basePath%>storepc/isPayThisOrder.do",
					data:{"waterrecord_id":waterrecord_id},
					dataType:"json",
					success:function(data){ 
						if(data.result =="1"){
							$(".ewm_title").html("支付成功，即将刷新页面");
							window.location.reload(); //刷新当前页面
  						}
   					}
			});  
        }
     	 
      
	
</script>
</html>