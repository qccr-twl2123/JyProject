<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
        <meta charset="utf-8">
 		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/style.css">
		<link rel="stylesheet" href="<%=basePath%>css/htmlmember/styles.css" type="text/css">
		<script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
		<script src="<%=basePath%>js/jquery.form.js"></script>
		<style type="text/css">
			#flpay,#allpay{
			    top: 90px;
	    		position: absolute;
	    		width:100%;
			}
			#allintegral,#allmoney, #flintegral,#flmoney{
				width:110px;
			} 
			.yhm-footer a {
 			    width: 158px;
 			}
 			input{
 			    height: 29px;
 			    font-size: 20px;
 			}
 			.yhmd-input {
    			width: 205px;
     			font-size: 20px;
			}
		</style>
		<script type="text/javascript">
		function qingkong(){
			$("input").each(function(){
				$(this).val("");
			});
		}
   		if(true){
			//验证提交
			$.ajax({
			        type:"post",
			        url:'<%=basePath%>app_goods/allMoneyByOne.do', 
			  	 	 data:{
			  	 		"store_id":"${pd.store_id}",
			 		 	"member_id":"${pd.member_id}",
			 		 	"paymoney":"0" ,
			 		 	"allgoods":"" ,
			 		 	"notmoney":"0" 
			  	 	 },                
			        dataType:"json",
			        success: function(data){
			        	 //优惠明细
			       	 	 var yingxiaoList=data.data.yingxiaoList;
			        	 //刚进来的优惠明细
			       	 	 var yxpd=data.data.yxpd;
			       	  	 if(yxpd != null  ){
			       	  		//优惠明细
					        	var str="";
					        	if(yxpd.mz != ""){
				   		 			str+="<p  class='gay allyx'><span>"+yxpd.mz+"</span></p>";
				   		 		}
					        	if(yxpd.sdyx != ""){
				   		 			str+="<p class='gay allyx'><span>"+yxpd.sdyx+"</span></p>";
				   		 		}
					        	if(yxpd.mj != ""){
				   		 			str+="<p class='gay allyx'><span>"+yxpd.mj+"</span></p>";
				   		 		}
					        	if(yxpd.gmcs != ""){
				   		 			str+="<p class='gay allyx'><span>"+yxpd.gmcs+"</span></p>";
				   		 		}
					        	if(yxpd.mnjn != ""){
				   		 			str+="<p class='gay allyx'><span>"+yxpd.mnjn+"</span></p>";
				   		 		}
					        	if(yxpd.jfsy != ""){
				   		 			str+="<p class='gay allyx'><span>"+yxpd.jfsy+"</span></p>";
				   		 		}
					        	if(yxpd.zk != ""){
				   		 			str+="<p class='gay allyx'><span>"+yxpd.zk+"</span></p>";
				   		 		}
					        	$("#allyhmx").append(str);
			       	  	 }
   			        	 //优惠后的价格
			       	 	 var countpd=data.data.countpd;
			        	 //财富信息
			       	 	 var memberInfor=data.data.memberInfor;
			        	 if(memberInfor != null){
			        		 $("#allnow_integral").html(memberInfor.now_integral);
				        	 $("#allnow_money").html(memberInfor.now_money);
			        	 }
 			        	 //商家名称
			       	 	 var store_name=data.data.store_name;
 			        	 $(".store_name").html(store_name);
			        	 //0-只开通总金额买单，1-只开通类别买单，3-都不开通
			       	 	 var issortjf=data.data.issortjf;
			        	 if(issortjf == "0"){
			        		 $("#allpay").show();
			        		 $(".whwall").css("color","#ff0600");
			        	 }else if(issortjf == "1"){
			        		 $("#flpay").show();
			        		 $(".whwfl").css("color","#ff0600");
			        	 }else{
			        		 $("#show").show();
 			        	 }
			        	 //桌号
			        	 var tableNumberList=data.data.tableNumberList;
			        	 for(var j=0;j<tableNumberList.length;j++){
			        		 $(".storedeskno").append("<option  value='"+tableNumberList[j].table_name+"'>"+tableNumberList[j].table_name+"</oprion>");
			        	 }
			        	 if(tableNumberList.length == 0){
			        		 $(".storedeskno").append("<option  value=''>当前商家无桌号</oprion>");
			        	 }
 			        }
	      });
 		}
		
 		//分类买单
 		if(true){
 			//验证提交
			$.ajax({
			        type:"post",
			        url:'<%=basePath%>app_goods/allMoneyByTwo.do', 
			  	 	 data:{
			  	 		"store_id":"${pd.store_id}",
			 		 	"member_id":"${pd.member_id}",
 			 		 	"allleibie":""   
			  	 	 },                
			        dataType:"json",
			        success: function(data){
			        	 //优惠明细
			       	 	 var yingxiaoList=data.data.yingxiaoList;
			        	 //刚进来的优惠明细
			       	 	 var yxpd=data.data.yxpd;
			        	 if(yxpd != null  ){
			        		//优惠明细
					        	var str="";
					        	if(yxpd.mz != ""){
				   		 			str+="<p  class='gay flyx'><span>"+yxpd.mz+"</span></p>";
				   		 		}
					        	if(yxpd.sdyx != ""){
				   		 			str+="<p class='gay flyx'><span>"+yxpd.sdyx+"</span></p>";
				   		 		}
					        	if(yxpd.mj != ""){
				   		 			str+="<p class='gay flyx'><span>"+yxpd.mj+"</span></p>";
				   		 		}
					        	if(yxpd.gmcs != ""){
				   		 			str+="<p class='gay flyx'><span>"+yxpd.gmcs+"</span></p>";
				   		 		}
					        	if(yxpd.mnjn != ""){
				   		 			str+="<p class='gay flyx'><span>"+yxpd.mnjn+"</span></p>";
				   		 		}
					        	if(yxpd.jfsy != ""){
				   		 			str+="<p class='gay flyx'><span>"+yxpd.jfsy+"</span></p>";
				   		 		}
					        	if(yxpd.zk != ""){
				   		 			str+="<p class='gay flyx'><span>"+yxpd.zk+"</span></p>";
				   		 		}
					        	$("#flyhmx").append(str);
			        	 }
  			        	 //优惠后的价格
			       	 	 var countpd=data.data.countpd;
			        	 //财富信息
			       	 	 var memberInfor=data.data.memberInfor;
			       	  	 if(memberInfor != null){
				       	  	 $("#flnow_integral").html(memberInfor.now_integral);
				        	 $("#flnow_money").html(memberInfor.now_money);
			       	  	 }
 			        	 //商家名称
			       	 	 var store_name=data.data.store_name;
			        	 $(".store_name").val(store_name);
			        	 //类别集合
			        	 $(".flsort").remove();
			       	 	 var sortList=data.data.sortList;
			       	 	 if(sortList != null){
			       	 		for (var i = 0; i < sortList.length; i++) {
								var str="<li class='flsort'>"+
											"<span style='display: inline-block; width: 160px;'><span class='green'>"+sortList[i].name+"</span>消费金额  :</span>"+
											"<span class='red'><input  type='text' id='"+sortList[i].goods_category_id+"' onchange='flPayMoney()' style='width:100px' onkeyup='value=value.replace(/[^\.\d]/g,'')' onbeforepaste='clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))'/></span>"+
											"<span class='fr gay'>积分率："+sortList[i].back_rate+"%</span>"+
											"<input type='hidden' class='goods_category_id'   goods_category_id='"+sortList[i].goods_category_id+"' back_rate='"+sortList[i].back_rate+"' zk_rate='"+sortList[i].zk_rate+"'/>"+
										"</li>";
								$("#flyhmx").before(str);
							}
			       	 	 }
  			        }
	         });
 		}
		</script>
</head>
<body style="background:#fff;">
<nav class="top">
	<a href="<%=basePath%>html_me/textDesc.do?type=7" class="fr" style="margin-right:10px;">优惠说明</a>
	<a href="<%=basePath%>html_member/goStoreDetail.do?store_id=${pd.store_id}&member_id=${pd.member_id}"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">优惠买单</div>
</nav>
<div class="yh-title clearfix">
	<ul>
		<li><a onclick="show('0',this)" class="whwall">按总金额</a></li>
		<li><a onclick="show('1',this)" class="whwfl">按类别金额</a></li>
	</ul>
</div>
<script type="text/javascript">
//展示
function show(value,obj){
	$(".whwall").css("color","#000000");
	$(".whwfl").css("color","#000000");
	$(obj).css("color","#ff0600");
	if(value == "0"){
		if("${pd.issortjf}" == value){
			$("#allpay").show();
			$("#flpay").hide();
			$("#show").hide();
		}else{
			$("#allpay").hide();
			$("#flpay").hide();
			$("#show").show();
		}
	}else if(value == "1"){
		if("${pd.issortjf}" == value){
			$("#allpay").hide();
			$("#flpay").show();
			$("#show").hide();
		}else{
			$("#allpay").hide();
			$("#flpay").hide();
			$("#show").show();
		}
 	}
}
</script>
<div id="showym">
</div>
<div id="allpay" style="display:none">
	<div class="yh-content clearfix">
		<p class="store_name" style="font-size: 24px;"></p>
		<ul>
				<li>
					<span>消费优惠金额：</span>
					<input type="text" class="yhmd-input"   id="allsalemoney" onchange="allPayMoney()" onkeyup="value=value.replace(/[^\.\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
				</li>
				<li>
					<span>不优惠金额：</span>
					<input type="text" class="yhmd-input"   id="allnotmoney"  onchange="allPayMoney()" onkeyup="value=value.replace(/[^\.\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
				</li>
				<li>
					<span>桌号：</span>
  					<select class="yhmd-input storedeskno" id="alldesk_no" >
					
					</select>
				</li>
<script type="text/javascript">
//优惠内容
 	function allPayMoney(){
 		var allsalemoney=$("#allsalemoney").val();
 		if(allsalemoney == "" || allsalemoney == "0"){
 			return;
 		}
 		var allnotmoney=$("#allnotmoney").val();
 		if(allnotmoney == ""){
 			allnotmoney="0";
 		}
 		if(parseFloat(allsalemoney)/2 < parseFloat(allnotmoney) ){
 			alert("不优惠金额不能超过支付金额的50%");
 			$("#allnotmoney").val("0");
 			return;
 		}
 		//验证提交
		$.ajax({
		        type:"post",
		        url:'<%=basePath%>app_goods/allMoneyByOne.do', 
		  	 	 data:{
		  	 		"store_id":"${pd.store_id}",
		 		 	"member_id":"${pd.member_id}",
		 		 	"paymoney":allsalemoney ,
		 		 	"allgoods":"" ,
		 		 	"notmoney":allnotmoney
		  	 	 },                
		        dataType:"json",
		        success: function(data){
		        	 //优惠明细
		        	 $(".allyx").remove();
		       	 	 var yingxiaoList=data.data.yingxiaoList;
 		        	 //刚进来的优惠明细
		       	 	 var yxpd=data.data.yxpd;
		        	 if(yingxiaoList.length == 0){
		        		//优惠明细
				        	var str="";
				        	if(yxpd.mz != ""){
			   		 			str+="<p  class='gay allyx'><span>"+yxpd.mz+"</span></p>";
			   		 		}
				        	if(yxpd.sdyx != ""){
			   		 			str+="<p class='gay allyx'><span>"+yxpd.sdyx+"</span></p>";
			   		 		}
				        	if(yxpd.mj != ""){
			   		 			str+="<p class='gay allyx'><span>"+yxpd.mj+"</span></p>";
			   		 		}
				        	if(yxpd.gmcs != ""){
			   		 			str+="<p class='gay allyx'><span>"+yxpd.gmcs+"</span></p>";
			   		 		}
				        	if(yxpd.mnjn != ""){
			   		 			str+="<p class='gay allyx'><span>"+yxpd.mnjn+"</span></p>";
			   		 		}
				        	if(yxpd.jfsy != ""){
			   		 			str+="<p class='gay allyx'><span>"+yxpd.jfsy+"</span></p>";
			   		 		}
				        	if(yxpd.zk != ""){
			   		 			str+="<p class='gay allyx'><span>"+yxpd.zk+"</span></p>";
			   		 		}
				        	$("#allyhmx").append(str);
		        	 }else{
		        		 var discount_content="";
		        		 for (var i = 0; i < yingxiaoList.length; i++) {
								var str="<p class='gay allyx'><span>"+yingxiaoList[i].content+"</span><span class='blue fr'>"+yingxiaoList[i].number+"</span></p> ";
								var dis=yingxiaoList[i].content+"@"+yingxiaoList[i].id+"@"+yingxiaoList[i].number+"@"+yingxiaoList[i].type;
								discount_content+=dis+",";
								$("#allyhmx").append(str);
		        		 }
		        		 $("#discount_content").val(discount_content);//优惠明细
		        	 }
 			         //优惠后的价格
		       	 	 var countpd=data.data.countpd;
		       	  	$("#sale_money").val(allsalemoney);
		       	  	$("#no_discount_money").val(allnotmoney);
		       	  	$("#discount_money").val(countpd.reducemoney);
		       	  	$("#actual_money").val(countpd.paymoney);
		       	  	$("#get_integral").val(countpd.zengjf);
		       	  	$("#redpackage_id").val(countpd.red_id);
		       	  	$("#store_redpackets_id").val(countpd.store_redpackets_id);
		       	  	$("#pay_sort_type").val("1");
		       	 	$("#allpaymoney").html(countpd.paymoney);
		       	  	$("#allreducemoney").html(countpd.reducemoney);
		       	  	$(".lastallpaymoney").html(countpd.paymoney);
		       	  	//$(".lastallpaymoney").val(countpd.zengjf);
		        	 //财富信息
		       	 	 var memberInfor=data.data.memberInfor;
		        	 $("#allnow_integral").html(memberInfor.now_integral);
		        	 $("#allnow_money").html(memberInfor.now_money);
		        	 //商家名称
		       	 	 var store_name=data.data.store_name;
		        	 $(".store_name").html(store_name);
		        	 //0-只开通总金额买单，1-只开通类别买单，3-都不开通
		       	 	 var issortjf=data.data.issortjf;
 		        }
      });
 	}
 </script>
				<li id="allyhmx" style="font-size: 14px;">
					<p class="fourteen-px">优惠明细：</p>
 					<!-- <p class="gay allyx">
						<span>每满500元9.5折，满1000元9折</span>
						<span class="blue fr">-30.00</span>
					</p> -->
 				</li>
				<li>
					<span class="fr gay">已优惠<span id="allreducemoney" style="color:red"> </span>元</span>
					<div class="fourteen-px center"><b>实付金额：￥<span id="allpaymoney" style="font-size: 20px;color:red"> </span></b></div>    
				</li>
				<li>
					<span class="pay_list_c1 fl">
						<input type="checkbox"   style="width:24px;height:24px">
					</span>
					<span class="height ">
						积分支付：<input id="allintegral"  type="text" onclick="ischeck(this)" onchange="isOK(this,'allnow_integral','lastallpaymoney')" onkeyup="value=value.replace(/[^\.\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
					</span>
					<span class="fr height gay">积分<span id="allnow_integral"> </span>分</span>
				</li>
				<li>
					<span class="pay_list_c1 fl">
						<input type="checkbox" style="width:24px;height:24px">
					</span>
					<span class="height ">
						余额支付：<input  id="allmoney" type="text" onclick="ischeck(this)" onchange="isOK(this,'allnow_money','lastallpaymoney')" onkeyup="value=value.replace(/[^\.\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
					</span>
					<span class="fr height gay">余额<span id="allnow_money"></span>元</span>
				</li>
		</ul>
 	</div>
  	<div class="yhm-footer">
		<a onclick="xianjinpay('1')">
			<p>现金支付<span class="lastallpaymoney"></span></p>
 		</a>
		<a class="fr" onclick="threepay('1')">
			<p>在线支付 <span class="lastallpaymoney"></span></p>
 		</a>
	</div>
</div>
<script type="text/javascript">
//现金支付
function xianjinpay(type){
		//验证提交
 		$("#pay_type").val("1");
		if(type == "1"){
			if($("#allmoney").val() != "" && $("#allmoney").val() != "0"){
				$("#user_balance").val($("#allmoney").val());
			}
			if($("#allintegral").val() != "" && $("#allintegral").val() != "0"){
				$("#user_integral").val($("#allintegral").val());
			}
			if($("#allpaymoney").html() != "" && $("#allpaymoney").html() != "0"){
				$("#actual_money").val($("#allpaymoney").html());
			}
			$("#desk_no").val($("#alldesk_no").val());
 		}else{
 			if($("#flmoney").val() != "" && $("#flmoney").val() != "0"){
				$("#user_balance").val($("#flmoney").val());
			}
			if($("#flintegral").val() != "" && $("#flintegral").val() != "0"){
				$("#user_integral").val($("#flintegral").val());
			}
			if($("#flpaymoney").html() != "" && $("#flpaymoney").html() != "0"){
				$("#actual_money").val($("#flpaymoney").html());
			}
			$("#desk_no").val($("#fldesk_no").val());
 		}
/* 		if($("#desk_no").val() == ""){
			alert("桌号不能为空");
			return;
		} */
		if($("#sale_money").val() == ""){
			alert("购买金额不能为空");
			return;
		}
		if($("#actual_money").val() == ""){
			alert("支付金额不能为空");
			return;
		}
		$("#Form").ajaxSubmit({  
				url : '<%=basePath%>app_pay_history/cashPay.do',
				type: "POST",//提交类型  
				data:{ 
				},
				 dataType:"json",
				 success:function(data){ 
				   			if(data.result == "1"){
				   				alert("支付成功，请等收银员确认订单");
				   				window.location.reload(); //刷新当前页面
				   			}else{
				   				alert(data.message);
				   			}
				} 	     
		});
}
//第三方支付
function threepay(type){
	//验证提交
	$("#pay_type").val("2");
	if(type == "1"){
		if($("#allmoney").val() != "" && $("#allmoney").val() != "0"){
			$("#user_balance").val($("#allmoney").val());
		}
		if($("#allintegral").val() != "" && $("#allintegral").val() != "0"){
			$("#user_integral").val($("#allintegral").val());
		}
		/* if($("#lastallpaymoney").html() != "" && $("#lastallpaymoney").html() != "0"){
			$("#actual_money").val($("#lastallpaymoney").html());
		} */
		$("#desk_no").val($("#alldesk_no").val());
	}else{
			if($("#flmoney").val() != "" && $("#flmoney").val() != "0"){
			$("#user_balance").val($("#flmoney").val());
		}
		if($("#flintegral").val() != "" && $("#flintegral").val() != "0"){
			$("#user_integral").val($("#flintegral").val());
		}
/* 		if($("#lastflpaymoney").html() != "" && $("#lastflpaymoney").html() != "0"){
			$("#actual_money").val($("#lastflpaymoney").html());
		} */
		$("#desk_no").val($("#fldesk_no").val());
	}
/* 	if($("#desk_no").val() == ""){
		alert("桌号不能为空");
		return;
	} */
	if($("#sale_money").val() == ""){
		alert("购买金额不能为空");
		return;
	}
	if($("#actual_money").val() == ""){
		alert("支付金额不能为空");
		return;
	}
	if(parseFloat($("#actual_money").val()) == 0){
		$("#Form").ajaxSubmit({  
				  	url : '<%=basePath%>app_pay_history/thirdPartyPay.do',
			        type: "POST",//提交类型  
			      	data:{ "pay_way":"nowmoney"
			      	},  
			      	dataType:"json",
			   		success:function(data){ 
 			   			if(data.result == "1"){
			   				alert("支付成功");
			   				window.location.reload(); //刷新当前页面
			   			}else{
			   				alert(data.message);
			   			}
			   		} 	     
		});
	}else{
 		$("#Form").submit();
	}
 }
</script>
<!-- 表单提交 -->
<form action="<%=basePath%>html_member/goPay.do" method="post" id="Form" name="Form">
		<input type="hidden" id="sale_money" name="sale_money" value="0"/> 
		<input type="hidden" id="no_discount_money" name="no_discount_money" value="0"/> 
		<input type="hidden" id="desk_no" name="desk_no" value=""/> 
		<input type="hidden" id="discount_money" name="discount_money"  value="0"/> 
		<input type="hidden" id="actual_money" name="actual_money" value="0"/> 
		<input type="hidden" id="user_balance" name="user_balance" value="0"/> 
		<input type="hidden" id="user_integral" name="user_integral" value="0"/> 
		<input type="hidden" id="pay_type" name="pay_type" value="1" /> 
 		<input type="hidden" id="get_integral" value="0" name="get_integral"/> 
		<input type="hidden" id="member_id" value="${pd.member_id}" name="member_id"/> 
		<input type="hidden" id="discount_content" name="discount_content"/><!-- 优惠明细 -->
		<input type="hidden" id="redpackage_id" name="redpackage_id" /><!-- 使用的红包ID-->
		<input type="hidden" id="store_redpackets_id" name="store_redpackets_id"/><!--满赠的赠送红包ID-->
		<input type="hidden" id="store_id" value="${pd.store_id}" name="store_id" /> 
		<input type="hidden" id="allgoodsid" value="" name="allgoodsid" /> 
		<input type="hidden" id="pay_sort_type"  name="pay_sort_type" /> 
</form>
<script type="text/javascript">
//分类购买优惠内容
	function flPayMoney(){
		var allleibie="";
	    $(".goods_category_id").each(function(){
	    	var goods_category_id=$(this).attr("goods_category_id");
	    	var money=$("#"+goods_category_id).val();
	    	var back_rate=$(this).attr("back_rate");
	    	var zk_rate=$(this).attr("zk_rate");
	    	var s=goods_category_id+"@"+money+"@"+back_rate+"@"+zk_rate;
	    	if(money != null && money != "0" &&  money.trim() != ""){
	    		allleibie+=s+",";
	    	}
	    });
 		//验证提交
		$.ajax({
		        type:"post",
		        url:'<%=basePath%>app_goods/allMoneyByTwo.do', 
		  	 	 data:{
		  	 		"store_id":"${pd.store_id}",
		 		 	"member_id":"${pd.member_id}",
			 		 "allleibie":allleibie   //类别ID@金钱@积分率@折扣率
		  	 	 },                
		        dataType:"json",
		        success: function(data){
		        	 //优惠明细
		        	 $(".flyx").remove();
		       	 	 var yingxiaoList=data.data.yingxiaoList;
		        	 //刚进来的优惠明细
		       	 	 var yxpd=data.data.yxpd;
		        	 if(yingxiaoList.length == 0){
		        		//优惠明细
				        	var str="";
				        	if(yxpd.mz != ""){
			   		 			str+="<p  class='gay flyx'><span>"+yxpd.mz+"</span></p>";
			   		 		}
							if(yxpd.sdyx != ""){
			   		 			str+="<p class='gay flyx'><span>"+yxpd.sdyx+"</span></p>";
			   		 		}
							if(yxpd.mj != ""){
			   		 			str+="<p class='gay flyx'><span>"+yxpd.mj+"</span></p>";
			   		 		}
							if(yxpd.gmcs != ""){
			   		 			str+="<p class='gay flyx'><span>"+yxpd.gmcs+"</span></p>";
			   		 		}
							if(yxpd.mnjn != ""){
			   		 			str+="<p class='gay flyx'><span>"+yxpd.mnjn+"</span></p>";
			   		 		}
							if(yxpd.jfsy != ""){
			   		 			str+="<p class='gay flyx'><span>"+yxpd.jfsy+"</span></p>";
			   		 		}
							if(yxpd.zk != ""){
			   		 			str+="<p class='gay flyx'><span>"+yxpd.zk+"</span></p>";
			   		 		}
				        	$("#flyhmx").append(str);
		        	 }else{
		        		 var discount_content="";
		        		 for (var i = 0; i < yingxiaoList.length; i++) {
								var str="<p class='gay flyx'><span>"+yingxiaoList[i].content+"</span><span class='blue fr'>"+yingxiaoList[i].number+"</span></p> ";
								var dis=yingxiaoList[i].content+"@"+yingxiaoList[i].id+"@"+yingxiaoList[i].number+"@"+yingxiaoList[i].type;
								discount_content+=dis+",";
								$("#flyhmx").append(str);
		        		 }
		        		 $("#discount_content").val(discount_content);//优惠明细
		        	 }
 			         //优惠后的价格
		       	 	 var countpd=data.data.countpd;
		       	 	$("#sale_money").val(countpd.allmoney);
		       	  	$("#no_discount_money").val("0");
		       	  	$("#discount_money").val(countpd.reducemoney);
		       	  	$("#actual_money").val(countpd.paymoney);
		       	  	$("#get_integral").val(countpd.zengjf);
		       	  	$("#redpackage_id").val(countpd.red_id);
		       	  	$("#store_redpackets_id").val(countpd.store_redpackets_id);
		       	  	$("#pay_sort_type").val("2");
		       	  	$("#flpaymoney").html(countpd.paymoney);
		       	  	$("#flreducemoney").html(countpd.reducemoney);
		       	  	$(".lastflpaymoney").html(countpd.paymoney);
		        	 //财富信息
		       	 	 var memberInfor=data.data.memberInfor;
		       	 	 $("#flnow_integral").html(memberInfor.now_integral);
		        	 $("#flnow_money").html(memberInfor.now_money);
		        	 //商家名称
		       	 	 var store_name=data.data.store_name;
		        	 $(".store_name").val(store_name);
		        	 //类别集合
 		       	 	 var sortList=data.data.sortList;
			    }
         });
}
</script>
<div id="flpay" style="display:none">
		<div class="yh-content clearfix">
			<p class="store_name" style="font-size: 24px;"></p>
			<ul id="flgoodssort">
				<li>
					<span>桌号：</span>
 					<select class="yhmd-input storedeskno" id="fldesk_no" >
					
					</select>
				</li>
				<!-- <li class="flsort">
					<span><span class="green">生鲜</span>消费金额  :</span>
					<span class="red"><input  type="text" id="flsortmoney" />元</span>
					<span class="fr gay">积分率：2%</span>
					<input type="hidden" class="goods_category_id" back_rate="" zh_rate=""/>
				</li> -->
				
				<li id="flyhmx" style="font-size:14px;">
					<p class="fourteen-px">优惠明细：</p>
					<!-- <p class="gay flyh ">
						<span>满200元可用红包20元</span>
						<span class="blue fr">-20.00</span>
					</p> -->
  				</li>
				<li>
					<span class="fr gay">已优惠<span id="flreducemoney" style="color:red"> </span>元</span>
					<div class="fourteen-px center"><b>实付金额：￥<span id="flpaymoney" style="font-size: 20px;color:red"> </span></b></div>    
				</li>
				<li>
					<span class="pay_list_c1 fl">
						<input type="checkbox"   value="1" style="width:24px;height:24px" >
					</span>
					<span class="height ">
						积分支付：<input type="text" id="flintegral" onclick="ischeck(this)" onchange="isOK(this,'flnow_integral','lastflpaymoney')" onkeyup="value=value.replace(/[^\.\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
					</span>
					<span class="fr height gay">积分<span id="flnow_integral"> </span>分</span>
				</li>
				<li>
					<span class="pay_list_c1 fl">
						<input type="checkbox"   value="1" style="width:24px;height:24px" >
					</span>
					<span class="height ">
						余额支付：<input type="text" id="flmoney" onclick="ischeck(this)" onchange="isOK(this,'flnow_money','lastflpaymoney')" onkeyup="value=value.replace(/[^\.\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
					</span>
					<span class="fr height gay">余额<span id="flnow_money"></span>元</span>
				</li>
			</ul>
 		</div>
 		<div class="yhm-footer">
			<a onclick="xianjinpay('1')">
				<p>现金支付<span class="lastflpaymoney"></span></p>
	 		</a>
			<a class="fr" onclick="threepay('1')">
				<p>在线支付 <span class="lastflpaymoney"></span></p>
	 		</a>
		</div>
</div>
<script type="text/javascript">
 		//选中
 		function ischeck(obj){
 			var a=$(obj).parent().prev().find("input").attr("checked","checked");
 			if($(obj).val() == ""){
 				window.number=parseFloat("0");//当前要修改的金钱
 			}else{
 				window.number=parseFloat($(obj).val());//当前要修改的金钱
 			}
   		}
 		
 		//判断近期是否充足
 		function isOK(obj,id,lessmoney){
 			if($(obj).val() == "" ){
 				$(obj).val("0");
 			}
 			var s1=parseFloat($(obj).val());//选择支付的金钱
 			var s2=parseFloat($("#"+id).val());//当前用户余额
 			var s3=parseFloat($("."+lessmoney).eq(0).html());//需要支付的金钱
 			if(s1 > s2){
 				alert("余额不足");
 				$(obj).val(s2)
 				return;
 			}else{
 				if(s1 > (s3+number)){
 					alert("支付金钱超出支付范围");
 					$(obj).val("0")
 					var n=s3+number;
 					$("."+lessmoney).html(n.toFixed(2));
 					$("#actual_money").val(n.toFixed(2));
  				}else{
 					//alert(number);
 					var n=s3-s1+number;
 					$("."+lessmoney).html(n.toFixed(2));
 					$("#actual_money").val(n.toFixed(2));
   				}
 				//alert($("#actual_money").val());
 			}
 		}
</script>
</body>
</html>
