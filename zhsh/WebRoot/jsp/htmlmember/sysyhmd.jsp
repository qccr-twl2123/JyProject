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
        <title>优惠买单</title>
        <base href="<%=basePath%>">
        <meta charset="utf-8">
 		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="css/htmlmember/style.css">
		<link rel="stylesheet" href="css/htmlmember/styles.css" type="text/css">
 		<style type="text/css">
			#flpay,#allpay{
			    top: 51px;
	    		position: absolute;
	    		width:100%;
	    		display: none;
			}
			input{
 			    height: 18px;
 			    font-size: 13px;
 			    width: 45%;
 			}
 			.blue{
 				color:blue;
 			}
			.yhm-footer {
			    margin: 0 auto;
			}
			.yhmd-input {
			    width: 71%;
			}
			.yhm-footer a {
				padding:2px;
 			    margin-bottom: 10px;
 			    width: inherit;
			}
			.fr_1 {
			    float: none;
			    margin-left: 14%;
			}
			.zhifu {
			        width: 97%;
				    height: 254px;
				    position: fixed;
				    top: 26%;
				    margin: 1%;
				    border: 1px solid #d6d6d6;
				    background-color: #fff;
				    font-size: 20px;
			    	display: none;
			}
			.zhifu_d1{
			    height: 50px;
			    line-height: 50px;
			    border-bottom:1px solid #d6d6d6;
			    text-align: center;
			}
			.zhifu_d1 span{
			    width: 40px;
			    height: 40px;
			    border:1px solid #d6d6d6;
			    text-align: center;
			    line-height: 40px;
			    border-radius: 50%;
			    cursor: pointer;
			    float: right;
			    margin-right: 20px;
			    margin-top: 5px;
			
			}
			.zhifu_d2{
			    width: 90%;
			    height: 50px;
			    margin: 10px 10%;
			    line-height: 50px;
			}
			.zhifu_d2 span{
			     text-align: center;
			     margin-top: 25px;
			}
			
			.zhifu_yes{
			    width: 100px;
			    height: 40px;
			    text-align: center;
			    line-height: 40px;
			    border:1px solid #d6d6d6;
			    border-radius: 5px;
			    background-color: #e4393c;
			    color: #fff;
			    cursor: pointer;
			    margin: 20px  40%; 
			}
			.recharge {
			    background: #fff;
			    width: 90%;
			    border-radius: 5px;
			    height: 40px;
			    line-height: 40px;
			    margin: 10px auto;
 			}
			.recharge1 {
			    background: #fff;
			    width: 50%;
			    border-radius: 5px;
			    height: 40px;
			    line-height: 40px;
			    margin: 10px auto;
 			}
			.recharge span {
			    width: 100%; 
			    display: inline-block;
			    text-align: center;
			}
			.recharge-content ul li {
			    border-bottom: 1px solid #d9d9d9;
			     font-size: 19px;
			    padding: 16px;
			}
			.recharge-sure {
			    background: #ffb900;
			    width: 179px;
			    height: 57px;
		    }
		</style>
    </head>
    <body style="background:#fff;">
			<nav class="top">
				<a href="html_me/textDesc.do?type=7" class="fr" style="margin-right:10px;">优惠说明</a>
				<a  onclick="back_url()"><b class="back-arrow fl"></b></a>
				<div style="text-align:center;line-height:40px;color:#fff">按总金额买单</div>
			</nav>
             <!-- <div class="yh-title clearfix" >
				<ul>
					<li><a onclick="show('0',this)" class="whwall">按总金额</a></li>
					<li><a onclick="show('1',this)" class="whwfl">按类别金额</a></li>
				</ul>
			</div>  -->
			<div id="showym" style="display:none;">
				暂未开通此买单方式
 			</div>
            <!-- 优惠买单新位置 -->
            <div class="tc" >
                  <!-- 按总金额 -->
                  <form action="" name="allbuyForm" id="allbuyForm" method="post">
                    <input type="hidden" name="session_orderid" value="${session_orderid}">
                    <input type="hidden" name="pay_type" value="2"/>
                     <input type="hidden" name="pay_sort_type" value="1"/>
                    <input type="hidden" name="allgoodsid" value=""/>
                    <input type="hidden" name="desk_no" value="${pd.desk_no}"/>
                    <input type="hidden" name="redpackage_id" id="allredpackage_id" value=""/>
                    <input type="hidden" name="store_redpackets_id" id="allstore_redpackets_id" value=""/>
                    <input type="hidden" name="sk_shop" value="${pd.new_store_id}"/>
                    <input type="hidden" name="discount_content" id="alldiscount_content" value=""/>
                    <input type="hidden" name="get_integral" id="allget_integral" value=""/>
                    <input type="hidden" name="discount_money" id="alldiscount_money" value=""/>
                    <input type="hidden" name="discount_after_money" id="alldiscount_after_money" value=""/>
                    <input type="hidden" name="actual_money" id="allactual_money"  value=""/>
                    <div  id="allpay">
	                        <div class="yh-content clearfix">
								<p class="store_name" style="font-size: 24px;"></p>
								<ul>
										<li>
											<span>消费金额：</span>
											<input type="text" class="yhmd-input"    name="sale_money"  id="allsale_money" value=""  oninput="leibieone()" onkeyup="value=value.replace(/[^\d\.]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />
										</li>
										<li>
											<span>不优惠金额：</span>
											<input type="text" class="yhmd-input"   name="no_discount_money"  id="allno_discount_money" value=""  oninput="leibieone()" onkeyup="value=value.replace(/[^\d\.]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"  />
										</li>
										<li>
											 <span>桌号：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${pd.desk_no}</span>
										</li>
										<li id="allyhmx" style="font-size: 14px;">
											<p class="fourteen-px">优惠明细：</p>
											<span class="allyouhui">
				                            	<c:forEach items="${marketlist }" var="var">
				                            		<p class="gay fr_1">
														<span>${var.grantrule}</span>
														<span class="blue fr_1"></span>
													</p>
	 			                            	</c:forEach>
				                            </span>
						 					<!-- <p class="gay allyx">
												<span>每满500元9.5折，满1000元9折</span>
												<span class="blue fr">-30.00</span>
											</p> -->
	  					 				</li>
	 									<li>
											<span class="fr_1 gay">已优惠<span class="alldiscount_money" style="color:red"> </span>元</span>
											<span class="fr_1 gay">赠送积分<span class="allget_integral" style="color:red"> </span>元</span>
 										</li>
	 									<li>
 											<div class="fourteen-px center"><b>优惠后还需支付金额：<span class="alldiscount_after_money" style="font-size: 20px;color:red"> </span>元</b></div>    
										</li>
										<li>
											<span class="height ">
												余额支付：<input type="text" name="user_balance" id="alluser_balance" oninput="isOk('1',this,'all')" value="" onkeyup="value=value.replace(/[^\d\.]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"  />元 
											</span>
											<span class="fr height gay">余额<fmt:formatNumber type="number" value="${mpd.now_money}" pattern="0.00" maxFractionDigits="2"/>元</span>
										</li>
										<li>
											<span class="height ">
												积分支付：
	 											<input type="text" name="user_integral"  id="alluser_integral" oninput="isOk('2',this,'all')" value="" onkeyup="value=value.replace(/[^\d\.]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"  />分 
											</span>
											<span class="fr height gay">积分<fmt:formatNumber type="number" value="${mpd.now_integral}" pattern="0.00" maxFractionDigits="2"/>分</span>
										</li>
										<li>
 											<div class="fourteen-px center"><b>最后还需支付金额：<span class="alllastpay_money" style="font-size: 20px;color:red"> </span>元</b></div>    
										</li>
	 							</ul>
						 	</div>
	                        <div class="yhm-footer">
	 							<a class="fr zeropaymoney" onclick="gopay('1')">
									<p>在线支付</p>
						 		</a>
							</div> 
                      </div>
                    </form>
             </div>
            <!-- 支付界面 -->
             <div class="zhifu">
             		<input type="hidden" name="pay_sort" value="" id="pay_sort"/>
                    <div class="zhifu_d1"> 欢迎会员前来消费^_^ <span>X</span></div>
                     <div class="recharge">
						<span>支付金额:<strong class="zuihouyibu_money" style=" color: red; font-size: 23px; "></strong> (元)</span>
					 </div>
					 <div class="recharge1">
					 	 <i class="weixin"></i>微信支付
					 </div>
 					 <input class="recharge-sure surepay readypaymoney" type="button" value="确认支付"  onclick="readypaymoney()"/>
                  </div> 
            <!-- 支付界面结束 -->
        <!-- 分页需要的js -->
		<script src=" js/jquery-1.8.0.min.js" type="text/javascript"></script>
 		<script src="js/jquery.form.js"  type="text/javascript"></script>
		<script src="js/ping/pingpp.js" type="text/javascript"></script>
        <script type="text/javascript">
            //0-只开通总金额买单，1-只开通类别买单，3-都不开通
           var issortjf="${issortjf}";
       	   if(issortjf == "0" || issortjf == "1"){
       		 $("#allpay").show();
       		 $(".whwall").css("color","#ff0600");
       	   }else{
       		 $("#showym").show();
       		 $(".tc").hide();
           }
         	 
        	//支付关闭窗口
	        $('.zhifu_d1 span').click(function(){
				window.location.reload()
			}); 
    		
	        //前往商家详情
	 		function back_url(){
	 			window.location.href="html_member/goStoreDetail.do?sk_shop=${pd.new_store_id}";
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

   		
    		
    		//获取总金额的优惠信息
   			function leibieone(){
   				if(isNumber($("#allsale_money"))){
   	   				return;
   	   			}
   				if(isNumber($("#allno_discount_money"))){
   	   				return;
   	   			}
   				var sale_money=$("#allsale_money").val();
    			var no_discount_money=$("#allno_discount_money").val();
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
    	  		//获取营销信息
   	 			$.ajax({
   					url:"app_goods/allMoneyByOne.do",
   					 /* url:"app_goods/saoYiSaoShopBuyGoods.do",  */ 
   					type:"post",
   					dataType:"json",
   					data:{
    					"sk_shop":"${pd.new_store_id}",
   						  "allmoney":sale_money,  
   						"paymoney":sale_money,
   						"notmoney":no_discount_money,
   						"redpackage_id":""
   					},
    					success:function(data){ 
   						var countpd=data.data.countpd;
   						var yingxiaoList=data.data.yingxiaoList;
   						if(countpd != null){
   							$(".alldiscount_after_money").html(parseFloat(countpd.paymoney).toFixed(2));
   							$("#alldiscount_after_money").val(parseFloat(countpd.paymoney).toFixed(2));//优惠后支付的金额
   	   	 					$("#allactual_money").val(countpd.paymoney);
   	   	 					$(".alllastpay_money").html(parseFloat(countpd.paymoney).toFixed(2));
   	   						$("#alldiscount_money").val(countpd.reducemoney);
   	   						$(".alldiscount_money").html(parseFloat(countpd.reducemoney).toFixed(2));
   	   	 					$("#allget_integral").val(countpd.zengjf);
   	   						$(".allget_integral").html(parseFloat(countpd.zengjf).toFixed(2));
   	   						$("#allredpackage_id").val(countpd.redpackage_id);
   	   						$("#allstore_redpackets_id").val(countpd.store_redpackets_id);
   	   						$("#alldiscount_content").val(countpd.discount_content);
   						}
   						var dc="";
    	 				$(".allyouhui").empty();
    	  					for(var n=0; n<yingxiaoList.length ; n++){
   	  						var s1=yingxiaoList[n].content;
   	  						var s2=yingxiaoList[n].id;
   	  						var s3=yingxiaoList[n].number;
   	  						var s4=yingxiaoList[n].type;
     	  					var sspan="<p class='gay fr_1 '> <span>"+s1+"</span> <span class='blue fr_1'>"+s3+"</span> </p> ";
      	  					dc+=s1+"@"+s2+"@"+s3+"@"+s4+",";
     	  					$(".allyouhui").append(sspan);
   	  					}
     	  				if($("#alldiscount_content").val() == ""){
    	  					$("#alldiscount_content").val(dc);
    	  				}
    	 			}
   				});
   							
   			}
    		
    		

   	   		
   	   		//选中支付
   	   		function isOk(value,obj,type){
   	   			if(isNumber(obj)){
   	   				return;
   	   			}
   	    		//处理一下该支付的金额
   	   			var alldiscount_after_money=$("#alldiscount_after_money").val();//优惠后支付的金额
   	   			var user_balance=$("#alluser_balance").val();
   	   			var user_integral=$("#alluser_integral").val();
   	   			var now_integral="${mpd.now_integral}";
   	   			var now_money="${mpd.now_money}";
   	   			var user="";
   	    		if(value =="1"){//金额
   	   				user=$(obj).val();
   	    			if(user == ""){
   	    				user="0";
   	    			}
   					//判断金额够不够
   					var n=parseFloat(now_money).toFixed(2) - parseFloat(user).toFixed(2);
   	  				if(n < 0){
   							alert("余额不足");
   							$(obj).val(user.substring(0, user.length-1));
   							return;
   					}
   	   			}else{//积分
   	   				user=$(obj).val();
   	   				if(user == ""){
   	    				user="0";
   	    			}
   					//判断金额够不够
   					var n=parseFloat(now_integral).toFixed(2) - parseFloat(user).toFixed(2);
   	 				if(n <0 ){
   						alert("积分不足");
   						$(obj).val(user.substring(0, user.length-1));
   						return;
   					}
   	   			}
   	    		if(user_balance == ""){
   	    			user_balance="0";
   	    		}
   	    		if(user_integral == ""){
   	    			user_integral="0";
   	    		}
   	    		var lastpay_money=parseFloat(alldiscount_after_money)-parseFloat(user_balance)-parseFloat(user_integral);
   	    		if(lastpay_money < 0){
   	    			alert("支付金额过多，请重新输入");
   	    			$(obj).val(user.substring(0, user.length-1));
   	    			return;
   	    		}
    	   		$("#allactual_money").val(lastpay_money.toFixed(2));//id表示隐藏的
   	   			$(".alllastpay_money").html(lastpay_money.toFixed(2));
   	    	}
   	 	 
   			
   			var flag=true;
   			
   			//表单提交购买
   	   		function gopay(value){
   				if(!flag){
   					return;
   				}
    	   		var allactual_money=parseFloat($("#allactual_money").val());
   	       		$(".zuihouyibu_money").html(allactual_money.toFixed(2));
   	       		$("#pay_sort").val(value);
   	       		if(allactual_money == 0){
   	       			zeropaymoney(value);
   	       		}else{
	   	       		$(".tc").hide();
	   	   			$(".zhifu").show();
   	       		}
     	    }  
   		
   			
   			//订单金额为0的时候支付
   			function zeropaymoney(value){
   				flag=false;
   	   		    $(".zeropaymoney").css("background","#a4a4a4");
 		    	var formup="";
		    	if(value == "1"){
		    	    formup="allbuyForm";
		    	}else{
		    		flag=true;
		   	   		$(".zeropaymoney").css("background","#ffb900");
		    	    return;
		    	}
		    	var url="html_member/toLogin.do";
    	   		$("#"+formup).ajaxSubmit({   
				  	url : 'app_pay_history/thirdPartyPay.do',
			        type: "POST",//提交类型  
			      	data:{ 
			      			"pay_way":"nowpay",
			      			"url":url,
			      			"in_jiqi":"5"
			      	},  
			      	dataType:"json",
			   		success:function(data){ 
				   		if(data.result == "1"){
			   				alert("支付成功");
			   				var order_id=data.data.order_id;
			   				window.location.href='html_member/findById.do?ordertype=2&order_id='+order_id;
			   			}else{
			   				flag=true;
			   				alert(data.message);
 		   		   	   		$(".zeropaymoney").css("background","#ffb900");
 			   	   		    return;
			   			}
			   		} 	     
				});
   		    }
	   			
   			
 			
   		    //开始支付
   		    function readypaymoney(){
	   		    	if(!flag){
	   		    		return;
	   		    	}
   		    		flag=false;
	   	   		    $(".readypaymoney").css("background","#a4a4a4");
   		    	    var sort=$("#pay_sort").val();
   		    	    var formup="";
   		    	    if(sort == "1"){
   		    	    	formup="allbuyForm";
   		    	    }else{
   		    	    	flag=true;
   		   	   		    $(".readypaymoney").css("background","#a4a4a4");
   		    	    	return;
   		    	    }
    		    	var url="html_member/toLogin.do";
 	   	   		    $("#"+formup).ajaxSubmit({  
	   							  	url : 'app_pay_history/thirdPartyPay.do',
	   						        type: "post",//提交类型  
	   						      	data:{ 
	   						      		"pay_way":"wx_pub","url":url,"in_jiqi":"5"
	   						      	},  
	   						      	dataType:"json",
	   						   		success:function(data){ 
	   						   			if(data.result == "0"){
 	   						   				alert(data.message);
 	   						   				flag=true;
 	   		   		   	   		    		$(".readypaymoney").css("background","#e4393c");
	   						   				return;
	   						   			}
	   						   			var order_id=data.data.order_id;
 	   						   			var charge=data.data.charge;
	   						   			//支付
	   									pingpp.createPayment(charge, function(result, err){
	   									    console.log(result);
	   									    console.log(err.msg);
	   									    console.log(err.extra);
	   									    if (result == "success") {
	   									    	//alert("支付成功");
	   									    	window.location.href='html_member/findById.do?ordertype=2&order_id='+order_id;
	   									        // 只有微信公众账号 wx_pub 支付成功的结果会在这里返回，其他的支付结果都会跳转到 extra 中对应的 URL。
	   									    } else if (result == "fail") {
	   									    	alert("支付失败fail");
	   									    	flag=true;
	   					   		   	   		    $(".readypaymoney").css("background","#e4393c");
	   	 								        // charge 不正确或者微信公众账号支付失败时会在此处返回
		   					   		   	   		$(".zeropaymoney").css("background","#ffb900");
 	   									    } else if (result == "cancel") {
 	   									        // 微信公众账号支付取消支付
	   									    	flag=true;
	   					   		   	   		    $(".readypaymoney").css("background","#e4393c");
	   					   		   	   			$(".zeropaymoney").css("background","#ffb900");
 	   									    }
	   									});
	   						   		} 	     
	   				});
     		     }
     			
        </script>
        
     
    </body>
</html> 