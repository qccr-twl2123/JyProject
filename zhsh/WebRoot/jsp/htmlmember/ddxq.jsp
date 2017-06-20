<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" type="text/css" href="css/htmlmember/style.css">
<link rel="stylesheet" href="css/htmlmember/styles.css" type="text/css">
<link rel="stylesheet" href="css/htmlmember/normalize.min.css" type="text/css">
<script type="text/javascript" src="js/htmlmember/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="js/htmlmember/main.js"></script>
<style type="text/css">
	.ddxq-text p{
		line-height: 30px;
		margin-left: 10px;
	}
	.ddxq-thj{

	}
	.my-thj-list p{
		text-align: right;
		line-height: 20px;
	}
	.ddxq-red{
		padding:10px;
		text-align: center;
		font-size: 14px;
	}
	.blod{
		font-weight: bold;
		font-size: 16px;
	}
	.ddxq-list p{
		text-align: left;
	}
	.tit_bg_box{
 		position: absolute;right: 0.4rem;top: 0.4rem;display: inline;padding-right: 1rem;height: 1.7rem;width: 1.3rem; background-size: 100%;
 	}
</style>
</head>
<body style="background:#ededed;">
<nav class="top">
	<a class="back_yemian" ><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">订单详情</div>
</nav>
<div class="clearfix sj-dj" style="margin-top:0">
	<span class="my-top-tx fl">
		<img src=" " id="pictrue_url">
	</span>
	<div class="fl ddxq-text">
		<p class="fourteen-px" id="store_name"></p>
		<p class="red" style="    font-size: 17px;">交易金额￥<span class="sale_money"></span></p>
	</div>
	<span class="fr" style="line-height:30px;display:inline-block;color: #CAC2C2;">交易成功</span>
</div>
<c:if test="${pd.ordertype eq '3' }">
	<div class="my-thj-list clearfix">
 		<span class="fourteen-px">提货券号码</span>
		<div class="fr">
			<p style="font-size: 20px;color:blue;" id="tihuo_id"></p>
			<p>(请凭提货券号码到商家门店提货)</p>
		</div>
	</div>
</c:if>

<div class="wallet-content clearfix">
	<p class="wallet-content-title" style="font-size: 17px;">付款方式</p>
	<ul id="fukaungList">

	</ul>
</div>
<div class="wallet-content clearfix">
	<p class="wallet-content-title" style="font-size: 17px;">优惠明细</p>
	<ul id="discountList">
		 
	</ul>
</div>
<c:if test="${pd.discount_money >= '0.01'  or pd.get_integral >= '0.01'}">
	<div class="my-thj-list clearfix" id="yhmx">
		<span class="fourteen-px">优惠明细</span>
			<div class="fr">
			   <c:if test="${pd.discount_money >= '0.01' }">
			    	<p id="ds">本次消费共优惠了￥<span class="red" id="discount_money"></span></p>
			   </c:if>
			   <c:if test="${pd.get_integral != '0.01' }">
			    	<p id="jf">另赠送积分<span class="red" id="get_integral"></span>积分</p>
			   </c:if>
	 		</div>
	</div>
</c:if>
<div class="wallet-content clearfix">
	<ul>
		<li class="clearfix">
			<div class="fl">
				<p class="fourteen-px">订单号</p>
 			</div>
			<div class="fr">
				<p id="look_number">${pd.look_number }</p>
			</div>
		</li>
		<li class="clearfix">
			<div class="fl">
				<p class="fourteen-px">创建时间</p>
				
			</div>
			<div class="fr">
				<p id="createtime"> </p>
			</div>
		</li>
	</ul>
</div>
<c:if test="${pd.ordertype eq '3' }">
	<p class="red ddxq-red">到店后，请将本页面出示给商家收银员，作为付款凭证 返回后也可以
	到<span class="blod">我的-提货券</span>查看详情</p>
	
	<div class="my-thj-list ddxq-list">
		<p class="fourteen-px">说明</p>
		<p class="gay">1.该界面是在购物车付款成功之后跳转的界面，点击返回后是到商家详情页（注意：现在的版本买单后购物车未清空）；</p>
		<p class="gay">2.如果使用余额支付或积分支付的款项已经足够，则直接跳转到该页面，不需要再跳转到支付宝和微信支付的界面。</p>
	</div>
</c:if>
<!-- 优质商家推荐 -->
	<ul class="ul_list" style="width: 100%;overflow: hidden;padding: 0 0.5rem;background-color: #fff">
 		<li class="li_nav" style="line-height: 2;font-size: 1.7rem;padding-left: 0.4rem;padding-bottom: 0.5rem;">优质商家推荐</li>
 	
 	</ul>
 	<script type="text/javascript">
 	$(function(){
 		//验证提交
		$.ajax({
		        type:"post",
		        url:'app_order/findById.do', 
		  	 	 data:{
 		  	 		"tihuo_id":"${pd.tihuo_id}" ,
 		  	 		"order_id":"${pd.order_id}" 
 		  	 	 },                
		        dataType:"json",
		        success: function(data){
		        	if(data.result == "1"){
		        		var pd=data.data;
	  		        	$("#store_name").html(pd.store_name);
	  		        	$("#tihuo_id").html(pd.tihuo_id);
	  		        	$("#createtime").html(pd.createtime);
			        	$("#pictrue_url").attr("src",pd.pictrue_url);
			        	$(".back_yemian").attr("href","html_member/goStoreDetail.do?sk_shop="+pd.sk_shop);
  			        	$(".sale_money").html(pd.sale_money);
			        	$("#look_number").html( pd.look_number);
			        	$("#discount_money").html( pd.discount_money);
			        	$("#get_integral").html( pd.get_integral);
   			        	//方式集合
	 		        	var fukaungList=pd.fukaungList;
 			        	for (var i = 0; i < fukaungList.length; i++) {
							var str="<li class='clearfix'> <div class='fl'> <p class='fourteen-px' style='font-size: 14px;'>"+fukaungList[i].fsname+"</p> </div> <div class='fr'> <p style='color:red;'>"+fukaungList[i].fsnumber+"</p> </div> </li>";
							$("#fukaungList").append(str);
			        	}
			        	if(parseFloat(pd.discount_money) > 0 ||  parseFloat(pd.get_integral) > 0){
			        		if(parseFloat(pd.discount_money) == 0){
			        			$("#ds").hide();
			        		}
							if(parseFloat(pd.get_integral) == 0){
								$("#jf").hide();
			        		}
			        	}else{
			        		$("#yhmx").hide();
			        	}
			        	//营销情况
 			        	var discountList=pd.discountList;
 			        	for (var i = 0; i < discountList.length; i++) {
 			        		var str="<li class='clearfix'> <div class='fl'> <p class='fourteen-px' style='font-size: 14px;'>"+discountList[i].content+"</p> </div> <div class='fr'> <p style='color:red;'>"+discountList[i].number+"</p> </div> </li>";
							$("#discountList").append(str);
 			        	}
			        	var daoLiuStoreList=pd.daoLiuStoreList;
			        	for (var i = 0; i < daoLiuStoreList.length; i++) {
				        		var onestore=daoLiuStoreList[i];
				        		//评论星级
				   	 			var onexing="";
			   	 			    if(parseInt(onestore.comment_score) == 1){
			   	 			    	onexing="<rating class='grst-detail rating'><svg class='eleme-svg svg-rating-star ng-scope' viewBox='10 0 50 10'><g id='svg-rating-star'><polygon class='svg-rating-star-inactive' points='4.732,0 6.194,2.963 9.463,3.438 7.098,5.745 7.656,9 4.732,7.463 1.807,9 2.366,5.745 0,3.438 3.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,0 16.194,2.963 19.463,3.438 17.098,5.745 17.656,9 14.732,7.463 11.807,9 12.366,5.745 10,3.438 13.269,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='24.732,0 26.194,2.963 29.463,3.438 27.098,5.745 27.656,9 24.732,7.463 21.807,9 22.365,5.745 20,3.438 23.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='34.732,0 36.194,2.963 39.463,3.438 37.098,5.745 37.656,9 34.732,7.463 31.807,9 32.365,5.745 30,3.438 33.27,2.963 '></polygon> <polygon class='svg-rating-star-inactive' points='44.732,0 46.194,2.963 49.463,3.438 47.098,5.745 47.656,9 44.732,7.463 41.807,9 42.365,5.745 40,3.438 43.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,0 56.194,2.963 59.463,3.438 57.098,5.745 57.656,9 54.732,7.463 51.807,9 52.365,5.745 50,3.438 53.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='64.732,0 66.194,2.963 69.463,3.438 67.098,5.745 67.656,9 64.732,7.463 61.807,9 62.365,5.745 60,3.438 63.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,0 76.194,2.963 79.463,3.438 77.098,5.745 77.656,9 74.732,7.463 71.807,9 72.365,5.745 70,3.438 73.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,0 86.194,2.963 89.463,3.438 87.098,5.745 87.656,9 84.732,7.463 81.807,9 82.365,5.745 80,3.438 83.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='94.732,0 96.194,2.963 99.463,3.438 97.098,5.745 97.656,9 94.732,7.462 91.807,9 92.365,5.745 90,3.438 93.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='4.732,10 6.194,12.963 9.463,13.438 7.098,15.745 7.656,19 4.732,17.463 1.807,19 2.366,15.745 0,13.438 3.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,10 16.194,12.963 19.463,13.438 17.098,15.745 17.656,19 14.732,17.463 11.807,19 12.366,15.745 10,13.438 13.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,10 26.194,12.963 29.463,13.438 27.098,15.745 27.656,19 24.732,17.463 21.807,19 22.365,15.745 20,13.438 23.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,10 36.194,12.963 39.463,13.438 37.098,15.745 37.656,19 34.732,17.463 31.807,19 32.365,15.745 30,13.438 33.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='44.732,10 44.732,17.463 41.807,19 42.365,15.745 40,13.438 43.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='44.732,10 46.195,12.963 49.464,13.438 47.099,15.745 47.657,19 44.732,17.463 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,10 56.194,12.963 59.463,13.438 57.098,15.745 57.656,19 54.732,17.463 51.807,19 52.365,15.745 50,13.438 53.27,12.963 '></polygon> <polygon class='svg-rating-star-inactive' points='64.732,10 66.194,12.963 69.463,13.438 67.098,15.745 67.656,19 64.732,17.463 61.807,19 62.365,15.745 60,13.438 63.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,10 76.194,12.963 79.463,13.438 77.098,15.745 77.656,19 74.732,17.463 71.807,19 72.365,15.745 70,13.438 73.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,10 86.194,12.963 89.463,13.438 87.098,15.745 87.656,19 84.732,17.463 81.807,19 82.365,15.745 80,13.438 83.27,12.963 '></polygon></g></svg></rating>";
			   	 			    }else if(parseInt(onestore.comment_score) == 2){
			   	 			    	onexing="<rating class='grst-detail rating'><svg class='eleme-svg svg-rating-star ng-scope' viewBox='10 0 50 10'><g id='svg-rating-star'><polygon class='svg-rating-star-inactive' points='4.732,0 6.194,2.963 9.463,3.438 7.098,5.745 7.656,9 4.732,7.463 1.807,9 2.366,5.745 0,3.438 3.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,0 16.194,2.963 19.463,3.438 17.098,5.745 17.656,9 14.732,7.463 11.807,9 12.366,5.745 10,3.438 13.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,0 26.194,2.963 29.463,3.438 27.098,5.745 27.656,9 24.732,7.463 21.807,9 22.365,5.745 20,3.438 23.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='34.732,0 36.194,2.963 39.463,3.438 37.098,5.745 37.656,9 34.732,7.463 31.807,9 32.365,5.745 30,3.438 33.27,2.963 '></polygon> <polygon class='svg-rating-star-inactive' points='44.732,0 46.194,2.963 49.463,3.438 47.098,5.745 47.656,9 44.732,7.463 41.807,9 42.365,5.745 40,3.438 43.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,0 56.194,2.963 59.463,3.438 57.098,5.745 57.656,9 54.732,7.463 51.807,9 52.365,5.745 50,3.438 53.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='64.732,0 66.194,2.963 69.463,3.438 67.098,5.745 67.656,9 64.732,7.463 61.807,9 62.365,5.745 60,3.438 63.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,0 76.194,2.963 79.463,3.438 77.098,5.745 77.656,9 74.732,7.463 71.807,9 72.365,5.745 70,3.438 73.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,0 86.194,2.963 89.463,3.438 87.098,5.745 87.656,9 84.732,7.463 81.807,9 82.365,5.745 80,3.438 83.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='94.732,0 96.194,2.963 99.463,3.438 97.098,5.745 97.656,9 94.732,7.462 91.807,9 92.365,5.745 90,3.438 93.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='4.732,10 6.194,12.963 9.463,13.438 7.098,15.745 7.656,19 4.732,17.463 1.807,19 2.366,15.745 0,13.438 3.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,10 16.194,12.963 19.463,13.438 17.098,15.745 17.656,19 14.732,17.463 11.807,19 12.366,15.745 10,13.438 13.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,10 26.194,12.963 29.463,13.438 27.098,15.745 27.656,19 24.732,17.463 21.807,19 22.365,15.745 20,13.438 23.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,10 36.194,12.963 39.463,13.438 37.098,15.745 37.656,19 34.732,17.463 31.807,19 32.365,15.745 30,13.438 33.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='44.732,10 44.732,17.463 41.807,19 42.365,15.745 40,13.438 43.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='44.732,10 46.195,12.963 49.464,13.438 47.099,15.745 47.657,19 44.732,17.463 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,10 56.194,12.963 59.463,13.438 57.098,15.745 57.656,19 54.732,17.463 51.807,19 52.365,15.745 50,13.438 53.27,12.963 '></polygon> <polygon class='svg-rating-star-inactive' points='64.732,10 66.194,12.963 69.463,13.438 67.098,15.745 67.656,19 64.732,17.463 61.807,19 62.365,15.745 60,13.438 63.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,10 76.194,12.963 79.463,13.438 77.098,15.745 77.656,19 74.732,17.463 71.807,19 72.365,15.745 70,13.438 73.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,10 86.194,12.963 89.463,13.438 87.098,15.745 87.656,19 84.732,17.463 81.807,19 82.365,15.745 80,13.438 83.27,12.963 '></polygon></g></svg></rating>";
			   	 			    } else if(parseInt(onestore.comment_score) == 3){
			   	 			    	onexing="<rating class='grst-detail rating'><svg class='eleme-svg svg-rating-star ng-scope' viewBox='10 0 50 10'><g id='svg-rating-star'><polygon class='svg-rating-star-inactive' points='4.732,0 6.194,2.963 9.463,3.438 7.098,5.745 7.656,9 4.732,7.463 1.807,9 2.366,5.745 0,3.438 3.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,0 16.194,2.963 19.463,3.438 17.098,5.745 17.656,9 14.732,7.463 11.807,9 12.366,5.745 10,3.438 13.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,0 26.194,2.963 29.463,3.438 27.098,5.745 27.656,9 24.732,7.463 21.807,9 22.365,5.745 20,3.438 23.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,0 36.194,2.963 39.463,3.438 37.098,5.745 37.656,9 34.732,7.463 31.807,9 32.365,5.745 30,3.438 33.27,2.963 '></polygon> <polygon class='svg-rating-star-inactive' points='44.732,0 46.194,2.963 49.463,3.438 47.098,5.745 47.656,9 44.732,7.463 41.807,9 42.365,5.745 40,3.438 43.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,0 56.194,2.963 59.463,3.438 57.098,5.745 57.656,9 54.732,7.463 51.807,9 52.365,5.745 50,3.438 53.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='64.732,0 66.194,2.963 69.463,3.438 67.098,5.745 67.656,9 64.732,7.463 61.807,9 62.365,5.745 60,3.438 63.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,0 76.194,2.963 79.463,3.438 77.098,5.745 77.656,9 74.732,7.463 71.807,9 72.365,5.745 70,3.438 73.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,0 86.194,2.963 89.463,3.438 87.098,5.745 87.656,9 84.732,7.463 81.807,9 82.365,5.745 80,3.438 83.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='94.732,0 96.194,2.963 99.463,3.438 97.098,5.745 97.656,9 94.732,7.462 91.807,9 92.365,5.745 90,3.438 93.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='4.732,10 6.194,12.963 9.463,13.438 7.098,15.745 7.656,19 4.732,17.463 1.807,19 2.366,15.745 0,13.438 3.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,10 16.194,12.963 19.463,13.438 17.098,15.745 17.656,19 14.732,17.463 11.807,19 12.366,15.745 10,13.438 13.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,10 26.194,12.963 29.463,13.438 27.098,15.745 27.656,19 24.732,17.463 21.807,19 22.365,15.745 20,13.438 23.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,10 36.194,12.963 39.463,13.438 37.098,15.745 37.656,19 34.732,17.463 31.807,19 32.365,15.745 30,13.438 33.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='44.732,10 44.732,17.463 41.807,19 42.365,15.745 40,13.438 43.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='44.732,10 46.195,12.963 49.464,13.438 47.099,15.745 47.657,19 44.732,17.463 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,10 56.194,12.963 59.463,13.438 57.098,15.745 57.656,19 54.732,17.463 51.807,19 52.365,15.745 50,13.438 53.27,12.963 '></polygon> <polygon class='svg-rating-star-inactive' points='64.732,10 66.194,12.963 69.463,13.438 67.098,15.745 67.656,19 64.732,17.463 61.807,19 62.365,15.745 60,13.438 63.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,10 76.194,12.963 79.463,13.438 77.098,15.745 77.656,19 74.732,17.463 71.807,19 72.365,15.745 70,13.438 73.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,10 86.194,12.963 89.463,13.438 87.098,15.745 87.656,19 84.732,17.463 81.807,19 82.365,15.745 80,13.438 83.27,12.963 '></polygon></g></svg></rating>";
			   	 			    } else if(parseInt(onestore.comment_score) == 4){
			   	 			    	onexing="<rating class='grst-detail rating'><svg class='eleme-svg svg-rating-star ng-scope' viewBox='10 0 50 10'><g id='svg-rating-star'><polygon class='svg-rating-star-inactive' points='4.732,0 6.194,2.963 9.463,3.438 7.098,5.745 7.656,9 4.732,7.463 1.807,9 2.366,5.745 0,3.438 3.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,0 16.194,2.963 19.463,3.438 17.098,5.745 17.656,9 14.732,7.463 11.807,9 12.366,5.745 10,3.438 13.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,0 26.194,2.963 29.463,3.438 27.098,5.745 27.656,9 24.732,7.463 21.807,9 22.365,5.745 20,3.438 23.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,0 36.194,2.963 39.463,3.438 37.098,5.745 37.656,9 34.732,7.463 31.807,9 32.365,5.745 30,3.438 33.27,2.963 '></polygon> <polygon class='svg-rating-star-active' points='44.732,0 46.194,2.963 49.463,3.438 47.098,5.745 47.656,9 44.732,7.463 41.807,9 42.365,5.745 40,3.438 43.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,0 56.194,2.963 59.463,3.438 57.098,5.745 57.656,9 54.732,7.463 51.807,9 52.365,5.745 50,3.438 53.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='64.732,0 66.194,2.963 69.463,3.438 67.098,5.745 67.656,9 64.732,7.463 61.807,9 62.365,5.745 60,3.438 63.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,0 76.194,2.963 79.463,3.438 77.098,5.745 77.656,9 74.732,7.463 71.807,9 72.365,5.745 70,3.438 73.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,0 86.194,2.963 89.463,3.438 87.098,5.745 87.656,9 84.732,7.463 81.807,9 82.365,5.745 80,3.438 83.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='94.732,0 96.194,2.963 99.463,3.438 97.098,5.745 97.656,9 94.732,7.462 91.807,9 92.365,5.745 90,3.438 93.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='4.732,10 6.194,12.963 9.463,13.438 7.098,15.745 7.656,19 4.732,17.463 1.807,19 2.366,15.745 0,13.438 3.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,10 16.194,12.963 19.463,13.438 17.098,15.745 17.656,19 14.732,17.463 11.807,19 12.366,15.745 10,13.438 13.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,10 26.194,12.963 29.463,13.438 27.098,15.745 27.656,19 24.732,17.463 21.807,19 22.365,15.745 20,13.438 23.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,10 36.194,12.963 39.463,13.438 37.098,15.745 37.656,19 34.732,17.463 31.807,19 32.365,15.745 30,13.438 33.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='44.732,10 44.732,17.463 41.807,19 42.365,15.745 40,13.438 43.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='44.732,10 46.195,12.963 49.464,13.438 47.099,15.745 47.657,19 44.732,17.463 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,10 56.194,12.963 59.463,13.438 57.098,15.745 57.656,19 54.732,17.463 51.807,19 52.365,15.745 50,13.438 53.27,12.963 '></polygon> <polygon class='svg-rating-star-inactive' points='64.732,10 66.194,12.963 69.463,13.438 67.098,15.745 67.656,19 64.732,17.463 61.807,19 62.365,15.745 60,13.438 63.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,10 76.194,12.963 79.463,13.438 77.098,15.745 77.656,19 74.732,17.463 71.807,19 72.365,15.745 70,13.438 73.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,10 86.194,12.963 89.463,13.438 87.098,15.745 87.656,19 84.732,17.463 81.807,19 82.365,15.745 80,13.438 83.27,12.963 '></polygon></g></svg></rating>";
			   	 			    } else if(parseInt(onestore.comment_score) ==5){
			   	 			    	onexing="<rating class='grst-detail rating'><svg class='eleme-svg svg-rating-star ng-scope' viewBox='10 0 50 10'><g id='svg-rating-star'><polygon class='svg-rating-star-inactive' points='4.732,0 6.194,2.963 9.463,3.438 7.098,5.745 7.656,9 4.732,7.463 1.807,9 2.366,5.745 0,3.438 3.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,0 16.194,2.963 19.463,3.438 17.098,5.745 17.656,9 14.732,7.463 11.807,9 12.366,5.745 10,3.438 13.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,0 26.194,2.963 29.463,3.438 27.098,5.745 27.656,9 24.732,7.463 21.807,9 22.365,5.745 20,3.438 23.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,0 36.194,2.963 39.463,3.438 37.098,5.745 37.656,9 34.732,7.463 31.807,9 32.365,5.745 30,3.438 33.27,2.963 '></polygon> <polygon class='svg-rating-star-active' points='44.732,0 46.194,2.963 49.463,3.438 47.098,5.745 47.656,9 44.732,7.463 41.807,9 42.365,5.745 40,3.438 43.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='54.732,0 56.194,2.963 59.463,3.438 57.098,5.745 57.656,9 54.732,7.463 51.807,9 52.365,5.745 50,3.438 53.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='64.732,0 66.194,2.963 69.463,3.438 67.098,5.745 67.656,9 64.732,7.463 61.807,9 62.365,5.745 60,3.438 63.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,0 76.194,2.963 79.463,3.438 77.098,5.745 77.656,9 74.732,7.463 71.807,9 72.365,5.745 70,3.438 73.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,0 86.194,2.963 89.463,3.438 87.098,5.745 87.656,9 84.732,7.463 81.807,9 82.365,5.745 80,3.438 83.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='94.732,0 96.194,2.963 99.463,3.438 97.098,5.745 97.656,9 94.732,7.462 91.807,9 92.365,5.745 90,3.438 93.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='4.732,10 6.194,12.963 9.463,13.438 7.098,15.745 7.656,19 4.732,17.463 1.807,19 2.366,15.745 0,13.438 3.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,10 16.194,12.963 19.463,13.438 17.098,15.745 17.656,19 14.732,17.463 11.807,19 12.366,15.745 10,13.438 13.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,10 26.194,12.963 29.463,13.438 27.098,15.745 27.656,19 24.732,17.463 21.807,19 22.365,15.745 20,13.438 23.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,10 36.194,12.963 39.463,13.438 37.098,15.745 37.656,19 34.732,17.463 31.807,19 32.365,15.745 30,13.438 33.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='44.732,10 44.732,17.463 41.807,19 42.365,15.745 40,13.438 43.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='44.732,10 46.195,12.963 49.464,13.438 47.099,15.745 47.657,19 44.732,17.463 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,10 56.194,12.963 59.463,13.438 57.098,15.745 57.656,19 54.732,17.463 51.807,19 52.365,15.745 50,13.438 53.27,12.963 '></polygon> <polygon class='svg-rating-star-inactive' points='64.732,10 66.194,12.963 69.463,13.438 67.098,15.745 67.656,19 64.732,17.463 61.807,19 62.365,15.745 60,13.438 63.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,10 76.194,12.963 79.463,13.438 77.098,15.745 77.656,19 74.732,17.463 71.807,19 72.365,15.745 70,13.438 73.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,10 86.194,12.963 89.463,13.438 87.098,15.745 87.656,19 84.732,17.463 81.807,19 82.365,15.745 80,13.438 83.27,12.963 '></polygon></g></svg></rating>";
			   	 			    }else{
			   	 			    	onexing="<rating class='grst-detail rating'><svg class='eleme-svg svg-rating-star ng-scope' viewBox='10 0 50 10'><g id='svg-rating-star'><polygon class='svg-rating-star-inactive' points='4.732,0 6.194,2.963 9.463,3.438 7.098,5.745 7.656,9 4.732,7.463 1.807,9 2.366,5.745 0,3.438 3.269,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='14.732,0 16.194,2.963 19.463,3.438 17.098,5.745 17.656,9 14.732,7.463 11.807,9 12.366,5.745 10,3.438 13.269,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='24.732,0 26.194,2.963 29.463,3.438 27.098,5.745 27.656,9 24.732,7.463 21.807,9 22.365,5.745 20,3.438 23.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='34.732,0 36.194,2.963 39.463,3.438 37.098,5.745 37.656,9 34.732,7.463 31.807,9 32.365,5.745 30,3.438 33.27,2.963 '></polygon> <polygon class='svg-rating-star-inactive' points='44.732,0 46.194,2.963 49.463,3.438 47.098,5.745 47.656,9 44.732,7.463 41.807,9 42.365,5.745 40,3.438 43.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,0 56.194,2.963 59.463,3.438 57.098,5.745 57.656,9 54.732,7.463 51.807,9 52.365,5.745 50,3.438 53.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='64.732,0 66.194,2.963 69.463,3.438 67.098,5.745 67.656,9 64.732,7.463 61.807,9 62.365,5.745 60,3.438 63.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,0 76.194,2.963 79.463,3.438 77.098,5.745 77.656,9 74.732,7.463 71.807,9 72.365,5.745 70,3.438 73.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,0 86.194,2.963 89.463,3.438 87.098,5.745 87.656,9 84.732,7.463 81.807,9 82.365,5.745 80,3.438 83.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='94.732,0 96.194,2.963 99.463,3.438 97.098,5.745 97.656,9 94.732,7.462 91.807,9 92.365,5.745 90,3.438 93.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='4.732,10 6.194,12.963 9.463,13.438 7.098,15.745 7.656,19 4.732,17.463 1.807,19 2.366,15.745 0,13.438 3.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,10 16.194,12.963 19.463,13.438 17.098,15.745 17.656,19 14.732,17.463 11.807,19 12.366,15.745 10,13.438 13.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,10 26.194,12.963 29.463,13.438 27.098,15.745 27.656,19 24.732,17.463 21.807,19 22.365,15.745 20,13.438 23.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,10 36.194,12.963 39.463,13.438 37.098,15.745 37.656,19 34.732,17.463 31.807,19 32.365,15.745 30,13.438 33.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='44.732,10 44.732,17.463 41.807,19 42.365,15.745 40,13.438 43.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='44.732,10 46.195,12.963 49.464,13.438 47.099,15.745 47.657,19 44.732,17.463 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,10 56.194,12.963 59.463,13.438 57.098,15.745 57.656,19 54.732,17.463 51.807,19 52.365,15.745 50,13.438 53.27,12.963 '></polygon> <polygon class='svg-rating-star-inactive' points='64.732,10 66.194,12.963 69.463,13.438 67.098,15.745 67.656,19 64.732,17.463 61.807,19 62.365,15.745 60,13.438 63.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,10 76.194,12.963 79.463,13.438 77.098,15.745 77.656,19 74.732,17.463 71.807,19 72.365,15.745 70,13.438 73.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,10 86.194,12.963 89.463,13.438 87.098,15.745 87.656,19 84.732,17.463 81.807,19 82.365,15.745 80,13.438 83.27,12.963 '></polygon></g></svg></rating>";
				   	 			}
			   	 				//后营销明细
					        	var onemark=onestore.marketlist;
					        	var onemarkstr="";
					        	for(var m=0;m<onemark.length ; m++){
					        		var onemark_type=onemark[m].marketing_type;
					        		var onegrantrule=onemark[m].grantrule;
					        		if(onemark_type == "1"){
					        			onemarkstr+="<span class='zeng tit_bg_box'>满增</span>";
					        		}else if(onemark_type == "2"){
					        			onemarkstr+="<span class='jian tit_bg_box' >立减</span>";
					        		}else if(onemark_type == "3"){
					        			onemarkstr+="<span class='shi tit_bg_box'   >时段</span>";
					        		}else if(onemark_type == "4"){
					        			onemarkstr+="<span class='song tit_bg_box'   >立减</span>";
					        		}else if(onemark_type == "5"){
					        			onemarkstr+="<span class='song tit_bg_box'   >累计</span>";
					        		}else if(onemark_type == "6"){
					        			onemarkstr+="<span class='ji tit_bg_box'   >积分</span>";
						        	}else if(onemark_type == "7"){
					        			onemarkstr+="<span class='zhe tit_bg_box '   >折扣</span>";
					        		}
					        	}
								var str="<a href='html_member/goStoreDetail.do?sk_shop="+onestore.sk_shop+"&daoliurecord_id="+onestore.daoliurecord_id+"' style='color:#000'><li class='li_item' style='width: 50%;float: left;padding: 0 0.4rem 1rem 0.4rem;'>"+
										"<div class='li_cont' style='width: 100%;border: 1px solid #ccc;padding-bottom: 1rem;'>"+
									"<div class='img_box' style='    position: relative;width: 100%;padding-bottom:100%;height:0px'>"+
										"<img src='"+onestore.pictrue_url+"' alt='' style='position: absolute;width: 100%;  height: 100%; '>"+
									"</div>"+
									"<div class='li_tit' style='width: 100%;'>"+
										"<div class='inline_box' style='height: 1.6rem;padding: 0.4rem 0.4rem;width: 100%;position: relative;line-height: 1.6rem;font-size: 1.2rem;'>"+
											"<span class='title' style='width: 90%;margin: 0;display:block;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>"+onestore.store_name+"</span>"+
											 onemarkstr+
										"</div>"+
										"<div class='inline_box' style='height: 1.6rem;padding: 0.4rem 0.4rem;width: 100%;position: relative;line-height: 1.6rem;font-size: 1.2rem;'>"+
											"<span>"+onestore.sort_name+"</span>"+
										"</div>"+
										"<div class='inline_box' style='height: 1.6rem;padding: 0.4rem 0.4rem;width: 100%;position: relative;line-height: 1.6rem;font-size: 1.2rem;'>"+
											"<div class='pingji'style='display: inline;'>"+onexing+"</div> "+
											"<span class='colr' style='color: #f90000;'>"+onestore.comment_score+"分</span>"+
											"<span class='flr' style='float: right;padding-right: 0.4rem;'>已售<span>"+onestore.transaction_times+"</span></span>"+
										"</div>"+
									"</div>"+
								"</div>"+
							"</li></a>";
				        		$(".ul_list").append(str);
						}
		        	}
 		        }
      	});
   	});
 	</script>
 </body>
</html>
