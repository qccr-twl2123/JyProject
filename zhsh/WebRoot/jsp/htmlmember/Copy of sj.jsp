<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
        <meta charset="utf-8">
        <title>商家详情</title>
 		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
 		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/style.css">
		<link rel="stylesheet" href="<%=basePath%>css/htmlmember/styles.css" type="text/css">
  		<link rel="stylesheet" href="<%=basePath%>css/htmlmember/normalize.min.css">
  		<script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
  		<style type="text/css">
			*{margin:0; padding:0;} 
			.sj-button {
			        background: #ffb900;
				    border: none;
				    color: #fff;
				    border-radius: 5px;
				    text-align: center;
				    font-size: 1px;
				    height: 45px;
				    width: 28%;
				    position: relative;
				    top: -26px;
				    display: inline-block;
				    padding-top: 2%;
			}
			.sj-dress span {
			     border-right: 1px solid #ffffff;  
 			}
 			.tit_bg_box{
 			position: absolute;right: 0.4rem;top: 0.4rem;display: inline;padding-right: 1rem;height: 1.7rem;width: 1.3rem; background-size: 100%;
 			}
		</style>
</head>
<body style="background:#dedede;">
<nav class="top">
	<i class="sp-sc fr" onclick="iscollect(this)"></i>
    <i class="sp-vip fr" onclick="isvip(this)"></i>
	<a href="<%=basePath%>html_member/gouShouYe.do"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">商家详情页</div>
</nav>
<script type="text/javascript">
//是否收藏
function iscollect(obj){
	var collect_id=$(obj).attr("collect");
	if(collect_id == "0"){//新增收藏
		$.ajax({
	        type:"post",
	        url:'<%=basePath%>app_store/iscolloctByMS.do', 
	  	 	data:{
	  	 		"store_id":"${pd.store_id}",
	  	 		"member_id":"${pd.member_id}"
		  	 	},                
	        dataType:"json",
	        success: function(data){
	        	$(obj).attr("collect","1");
	        	 //改变图片
	        	$(".sp-sc").css("background","url(../images/sj_06.png) no-repeat"); 
	         }
	    });
		
	}else{//取消收藏
		$.ajax({
	        type:"post",
	        url:'<%=basePath%>app_store/iscolloctByMS.do', 
	  	 	data:{
	  	 		"store_id":"${pd.store_id}",
	  	 		"member_id":"${pd.member_id}"
 		  	 	},                
	        dataType:"json",
	        success: function(data){
	        	$(obj).attr("collect","0");
	        	//改变图片
	        	$(".sp-sc").css("background","url(../images/sj_05.png) no-repeat");
	         }
	    });
	}
	
}

//是否为vip
function isvip(obj){
 	$.ajax({
        type:"post",
        url:'<%=basePath%>app_store/getVipForStore.do?', 
  	 	data:{
  	 		"store_id":"${pd.store_id}",
  	 		"member_id":"${pd.member_id}"
  	  	 	},                
        dataType:"json",
        success: function(data){
        	 alert(data.message);
         }
    });
}

</script>
<div class="yh-title sj-title clearfix">
	<ul>
		<li  class="sj-current"><a href="<%=basePath%>html_member/goStoreDetail.do?store_id=${pd.store_id}&member_id=${pd.member_id}">商家</a></li>
		<li><a href="<%=basePath%>html_member/goStoreGoods.do?store_id=${pd.store_id}&member_id=${pd.member_id}">商品</a></li>
		<li><a href="<%=basePath%>html_member/goStoreComment.do?store_id=${pd.store_id}&member_id=${pd.member_id}">评价</a></li>
	</ul>
</div>
<div class="clearfix sj-dj" style="    margin-top: 46px;">
	<span class="my-top-tx fl">
		<img src="<%=basePath%>/imgmem/20130518113434876(1).jpg"  id="storeimage"  >
	</span>
	<div style=" position: relative; top: 18px; left: 10px;     width: 250px;">
		<p id="storename"></p>
		<p id="storexing"></p>
 	</div>
 	<a href="http://www.jiuyuvip.com/FileSave/zhihuiPC/business_erweima2.html" class="sj-button fr" >点击下载app即可体验优惠买单</a>
 </div>
<div class="sj-zk clearfix">
	<ul>
		<li>
			<p><b id="storejf"> </b></p>
			<p>赠送积分率</p>
		</li>
		<li>
			<p><b id="storezk"> </b></p>
			<p>折扣</p>
		</li>
		<li>
			<p><b id="storepaytype"></b></p>
			<p>购买方式</p>
		</li>
		<li>
 			<p><b id="storepayway"> </b></p>
			<p>支付方式</p>
		</li>
	</ul>
</div>

<div class="sj-dress">
 	<span id="storeaddress" style="width:90%;"> </span>
 </div>

<div class="sj-conetnt">
	<p class="sj-conetnt-title" style="line-height: 50px;">公告与活动</p>
    <p class="sj-text" id="notice_information"></p> 
	<div class="sj-tj" id="storeyouhui">
		<!--  <p><span class="zhe">折</span>消费满300元9.5折，满500元9折，满1000元8.5折</p> -->
	</div>
   <!--  <div class="sj-list">
		<p class="sj-list-title">商家信息</p>
		<p id="storeinfor"></p>
	</div>  -->
	<article class="rm-list tj-list my-list sj-ul">
		<ul>
			<li><a href="#" id="storesjxq">商家详情图<b class="z-arrow"></b></a></li>
			<li><a href="#" id="storeyyzz">经营执照<b class="z-arrow"></b></a></li>
			<li><a href="#" id="storejyxk">经营许可证<b class="z-arrow"></b></a></li>
		</ul>
	</article>
 </div>
 <!-- 优质商家推荐 -->
	<ul class="ul_list" style="width: 100%;overflow: hidden;padding: 0 0.5rem;background-color: #fff">
 		<li class="li_nav" style="line-height: 2;font-size: 1.7rem;padding-left: 0.4rem;padding-bottom: 0.5rem;">优质商家推荐</li>
		<!--  
		<li class="li_item" style="width: 50%;float: left;padding: 0 0.4rem 1rem 0.4rem;">
			<div class="li_cont" style="width: 100%;border: 1px solid #ccc;padding-bottom: 1rem;">
				<div class="img_box" style="width: 100%;padding-bottom:100%;height:0px">
					<img src="img/aaa.png" alt="" style="width: 100%;padding-bottom:100%;">
				</div>
				<div class="li_tit" style="width: 100%;">
					<div class="inline_box" style="height: 1.6rem;padding: 0.4rem 0.4rem;width: 100%;position: relative;line-height: 1.6rem;font-size: 1.2rem;">
						<span class="title" style="width: 90%;margin: 0;display:block;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">商家A</span>
						<div class="tit_bg_box" style="position: absolute;right: 0.4rem;top: 0.4rem;display: inline;padding-right: 1rem;height: 1.2rem;width: 1.2rem;background: url(img/jf.png);background-size: 100%;">
							
						</div>
					</div>
					<div class="inline_box" style="height: 1.6rem;padding: 0.4rem 0.4rem;width: 100%;position: relative;line-height: 1.6rem;font-size: 1.2rem;">
						<span>经营类别：<span>大型超市</span></span>
					</div>
					<div class="inline_box" style="height: 1.6rem;padding: 0.4rem 0.4rem;width: 100%;position: relative;line-height: 1.6rem;font-size: 1.2rem;">
						<div class="pingji"style="display: inline;"></div>  评级
						<span class="colr" style="color: #f90000;">3.8分</span>
						<span class="flr" style="float: right;padding-right: 0.4rem;">已售<span>12432</span></span>
					</div>
				</div>
			</div>
		</li> -->
		 
 	</ul>
<script type="text/javascript">
		if(true){
			//验证提交
			$.ajax({
			        type:"post",
			        url:'<%=basePath%>app_store/findById.do', 
			  	 	 data:{
			  	 		"store_id":"${pd.store_id}",
			 		 	"member_id":"${pd.member_id}" 
			  	 	 },                
			        dataType:"json",
			        success: function(data){
			       	 	 var storedetail=data.data;
			        	//优惠明细：先公告
 			        	if(storedetail.notice_information != null || storedetail.notice_information != ""){
			        		$("#notice_information").append("公告："+storedetail.notice_information) ;
			        	}
 			        	var str="";
			        	//后营销明细
			        	var marketlist=data.data.marketlist;
			        	for(var m=0;m<marketlist.length ; m++){
			        		var marketing_type=marketlist[m].marketing_type;
			        		var grantrule=marketlist[m].grantrule;
			        		if(marketing_type == "1"){
			        			str+="<p><span class='zeng'>增</span>"+grantrule+"</p>";
			        		}else if(marketing_type == "2"){
			        			str+="<p><span class='jian'>减</span>"+grantrule+"</p>";
			        		}else if(marketing_type == "3"){
			        			str+="<p><span class='shi'>时</span>"+grantrule+"</p>";
			        		}else if(marketing_type == "4"){
			        			str+="<p><span class='song'>买</span>"+grantrule+"</p>";
			        		}else if(marketing_type == "5"){
			        			str+="<p><span class='song'>购</span>"+grantrule+"</p>";
			        		}else if(marketing_type == "6"){
			        			str+="<p><span class='ji'>积</span>"+grantrule+"</p>";
			        		}else if(marketing_type == "7"){
			        			str+="<p><span class='zhe'>折</span>"+grantrule+"</p>";
			        		}
			        	}
   			        	$("#storeyouhui").append(str);
 			        	//详情
			        	if(storedetail.iscollect != "0"){
			        		$(".sp-sc").css("background","url(../images/sj_06.png) no-repeat"); 
			        	}
			        	$(".sp-sc").attr("collect",storedetail.iscollect);
			        	$(".sp-vip").attr("vip",storedetail.vip);
			        	$("#storename").html(storedetail.store_name);
			        	//评论星级
			        	var xing=parseInt(storedetail.comment_score);
	   	 				var xingstr="";
	   	 			    if(xing == 1){
	   	 			    	xingstr="<rating class='grst-detail rating'><svg class='eleme-svg svg-rating-star ng-scope' viewBox='10 0 50 10'><g id='svg-rating-star'><polygon class='svg-rating-star-inactive' points='4.732,0 6.194,2.963 9.463,3.438 7.098,5.745 7.656,9 4.732,7.463 1.807,9 2.366,5.745 0,3.438 3.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,0 16.194,2.963 19.463,3.438 17.098,5.745 17.656,9 14.732,7.463 11.807,9 12.366,5.745 10,3.438 13.269,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='24.732,0 26.194,2.963 29.463,3.438 27.098,5.745 27.656,9 24.732,7.463 21.807,9 22.365,5.745 20,3.438 23.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='34.732,0 36.194,2.963 39.463,3.438 37.098,5.745 37.656,9 34.732,7.463 31.807,9 32.365,5.745 30,3.438 33.27,2.963 '></polygon> <polygon class='svg-rating-star-inactive' points='44.732,0 46.194,2.963 49.463,3.438 47.098,5.745 47.656,9 44.732,7.463 41.807,9 42.365,5.745 40,3.438 43.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,0 56.194,2.963 59.463,3.438 57.098,5.745 57.656,9 54.732,7.463 51.807,9 52.365,5.745 50,3.438 53.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='64.732,0 66.194,2.963 69.463,3.438 67.098,5.745 67.656,9 64.732,7.463 61.807,9 62.365,5.745 60,3.438 63.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,0 76.194,2.963 79.463,3.438 77.098,5.745 77.656,9 74.732,7.463 71.807,9 72.365,5.745 70,3.438 73.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,0 86.194,2.963 89.463,3.438 87.098,5.745 87.656,9 84.732,7.463 81.807,9 82.365,5.745 80,3.438 83.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='94.732,0 96.194,2.963 99.463,3.438 97.098,5.745 97.656,9 94.732,7.462 91.807,9 92.365,5.745 90,3.438 93.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='4.732,10 6.194,12.963 9.463,13.438 7.098,15.745 7.656,19 4.732,17.463 1.807,19 2.366,15.745 0,13.438 3.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,10 16.194,12.963 19.463,13.438 17.098,15.745 17.656,19 14.732,17.463 11.807,19 12.366,15.745 10,13.438 13.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,10 26.194,12.963 29.463,13.438 27.098,15.745 27.656,19 24.732,17.463 21.807,19 22.365,15.745 20,13.438 23.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,10 36.194,12.963 39.463,13.438 37.098,15.745 37.656,19 34.732,17.463 31.807,19 32.365,15.745 30,13.438 33.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='44.732,10 44.732,17.463 41.807,19 42.365,15.745 40,13.438 43.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='44.732,10 46.195,12.963 49.464,13.438 47.099,15.745 47.657,19 44.732,17.463 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,10 56.194,12.963 59.463,13.438 57.098,15.745 57.656,19 54.732,17.463 51.807,19 52.365,15.745 50,13.438 53.27,12.963 '></polygon> <polygon class='svg-rating-star-inactive' points='64.732,10 66.194,12.963 69.463,13.438 67.098,15.745 67.656,19 64.732,17.463 61.807,19 62.365,15.745 60,13.438 63.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,10 76.194,12.963 79.463,13.438 77.098,15.745 77.656,19 74.732,17.463 71.807,19 72.365,15.745 70,13.438 73.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,10 86.194,12.963 89.463,13.438 87.098,15.745 87.656,19 84.732,17.463 81.807,19 82.365,15.745 80,13.438 83.27,12.963 '></polygon></g></svg></rating>";
	   	 			    }else if(xing == 2){
	   	 			    	xingstr="<rating class='grst-detail rating'><svg class='eleme-svg svg-rating-star ng-scope' viewBox='10 0 50 10'><g id='svg-rating-star'><polygon class='svg-rating-star-inactive' points='4.732,0 6.194,2.963 9.463,3.438 7.098,5.745 7.656,9 4.732,7.463 1.807,9 2.366,5.745 0,3.438 3.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,0 16.194,2.963 19.463,3.438 17.098,5.745 17.656,9 14.732,7.463 11.807,9 12.366,5.745 10,3.438 13.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,0 26.194,2.963 29.463,3.438 27.098,5.745 27.656,9 24.732,7.463 21.807,9 22.365,5.745 20,3.438 23.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='34.732,0 36.194,2.963 39.463,3.438 37.098,5.745 37.656,9 34.732,7.463 31.807,9 32.365,5.745 30,3.438 33.27,2.963 '></polygon> <polygon class='svg-rating-star-inactive' points='44.732,0 46.194,2.963 49.463,3.438 47.098,5.745 47.656,9 44.732,7.463 41.807,9 42.365,5.745 40,3.438 43.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,0 56.194,2.963 59.463,3.438 57.098,5.745 57.656,9 54.732,7.463 51.807,9 52.365,5.745 50,3.438 53.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='64.732,0 66.194,2.963 69.463,3.438 67.098,5.745 67.656,9 64.732,7.463 61.807,9 62.365,5.745 60,3.438 63.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,0 76.194,2.963 79.463,3.438 77.098,5.745 77.656,9 74.732,7.463 71.807,9 72.365,5.745 70,3.438 73.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,0 86.194,2.963 89.463,3.438 87.098,5.745 87.656,9 84.732,7.463 81.807,9 82.365,5.745 80,3.438 83.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='94.732,0 96.194,2.963 99.463,3.438 97.098,5.745 97.656,9 94.732,7.462 91.807,9 92.365,5.745 90,3.438 93.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='4.732,10 6.194,12.963 9.463,13.438 7.098,15.745 7.656,19 4.732,17.463 1.807,19 2.366,15.745 0,13.438 3.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,10 16.194,12.963 19.463,13.438 17.098,15.745 17.656,19 14.732,17.463 11.807,19 12.366,15.745 10,13.438 13.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,10 26.194,12.963 29.463,13.438 27.098,15.745 27.656,19 24.732,17.463 21.807,19 22.365,15.745 20,13.438 23.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,10 36.194,12.963 39.463,13.438 37.098,15.745 37.656,19 34.732,17.463 31.807,19 32.365,15.745 30,13.438 33.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='44.732,10 44.732,17.463 41.807,19 42.365,15.745 40,13.438 43.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='44.732,10 46.195,12.963 49.464,13.438 47.099,15.745 47.657,19 44.732,17.463 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,10 56.194,12.963 59.463,13.438 57.098,15.745 57.656,19 54.732,17.463 51.807,19 52.365,15.745 50,13.438 53.27,12.963 '></polygon> <polygon class='svg-rating-star-inactive' points='64.732,10 66.194,12.963 69.463,13.438 67.098,15.745 67.656,19 64.732,17.463 61.807,19 62.365,15.745 60,13.438 63.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,10 76.194,12.963 79.463,13.438 77.098,15.745 77.656,19 74.732,17.463 71.807,19 72.365,15.745 70,13.438 73.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,10 86.194,12.963 89.463,13.438 87.098,15.745 87.656,19 84.732,17.463 81.807,19 82.365,15.745 80,13.438 83.27,12.963 '></polygon></g></svg></rating>";
	   	 			    } else if(xing == 3){
	   	 			    	xingstr="<rating class='grst-detail rating'><svg class='eleme-svg svg-rating-star ng-scope' viewBox='10 0 50 10'><g id='svg-rating-star'><polygon class='svg-rating-star-inactive' points='4.732,0 6.194,2.963 9.463,3.438 7.098,5.745 7.656,9 4.732,7.463 1.807,9 2.366,5.745 0,3.438 3.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,0 16.194,2.963 19.463,3.438 17.098,5.745 17.656,9 14.732,7.463 11.807,9 12.366,5.745 10,3.438 13.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,0 26.194,2.963 29.463,3.438 27.098,5.745 27.656,9 24.732,7.463 21.807,9 22.365,5.745 20,3.438 23.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,0 36.194,2.963 39.463,3.438 37.098,5.745 37.656,9 34.732,7.463 31.807,9 32.365,5.745 30,3.438 33.27,2.963 '></polygon> <polygon class='svg-rating-star-inactive' points='44.732,0 46.194,2.963 49.463,3.438 47.098,5.745 47.656,9 44.732,7.463 41.807,9 42.365,5.745 40,3.438 43.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,0 56.194,2.963 59.463,3.438 57.098,5.745 57.656,9 54.732,7.463 51.807,9 52.365,5.745 50,3.438 53.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='64.732,0 66.194,2.963 69.463,3.438 67.098,5.745 67.656,9 64.732,7.463 61.807,9 62.365,5.745 60,3.438 63.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,0 76.194,2.963 79.463,3.438 77.098,5.745 77.656,9 74.732,7.463 71.807,9 72.365,5.745 70,3.438 73.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,0 86.194,2.963 89.463,3.438 87.098,5.745 87.656,9 84.732,7.463 81.807,9 82.365,5.745 80,3.438 83.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='94.732,0 96.194,2.963 99.463,3.438 97.098,5.745 97.656,9 94.732,7.462 91.807,9 92.365,5.745 90,3.438 93.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='4.732,10 6.194,12.963 9.463,13.438 7.098,15.745 7.656,19 4.732,17.463 1.807,19 2.366,15.745 0,13.438 3.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,10 16.194,12.963 19.463,13.438 17.098,15.745 17.656,19 14.732,17.463 11.807,19 12.366,15.745 10,13.438 13.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,10 26.194,12.963 29.463,13.438 27.098,15.745 27.656,19 24.732,17.463 21.807,19 22.365,15.745 20,13.438 23.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,10 36.194,12.963 39.463,13.438 37.098,15.745 37.656,19 34.732,17.463 31.807,19 32.365,15.745 30,13.438 33.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='44.732,10 44.732,17.463 41.807,19 42.365,15.745 40,13.438 43.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='44.732,10 46.195,12.963 49.464,13.438 47.099,15.745 47.657,19 44.732,17.463 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,10 56.194,12.963 59.463,13.438 57.098,15.745 57.656,19 54.732,17.463 51.807,19 52.365,15.745 50,13.438 53.27,12.963 '></polygon> <polygon class='svg-rating-star-inactive' points='64.732,10 66.194,12.963 69.463,13.438 67.098,15.745 67.656,19 64.732,17.463 61.807,19 62.365,15.745 60,13.438 63.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,10 76.194,12.963 79.463,13.438 77.098,15.745 77.656,19 74.732,17.463 71.807,19 72.365,15.745 70,13.438 73.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,10 86.194,12.963 89.463,13.438 87.098,15.745 87.656,19 84.732,17.463 81.807,19 82.365,15.745 80,13.438 83.27,12.963 '></polygon></g></svg></rating>";
	   	 			    } else if(xing == 4){
	   	 			    	xingstr="<rating class='grst-detail rating'><svg class='eleme-svg svg-rating-star ng-scope' viewBox='10 0 50 10'><g id='svg-rating-star'><polygon class='svg-rating-star-inactive' points='4.732,0 6.194,2.963 9.463,3.438 7.098,5.745 7.656,9 4.732,7.463 1.807,9 2.366,5.745 0,3.438 3.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,0 16.194,2.963 19.463,3.438 17.098,5.745 17.656,9 14.732,7.463 11.807,9 12.366,5.745 10,3.438 13.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,0 26.194,2.963 29.463,3.438 27.098,5.745 27.656,9 24.732,7.463 21.807,9 22.365,5.745 20,3.438 23.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,0 36.194,2.963 39.463,3.438 37.098,5.745 37.656,9 34.732,7.463 31.807,9 32.365,5.745 30,3.438 33.27,2.963 '></polygon> <polygon class='svg-rating-star-active' points='44.732,0 46.194,2.963 49.463,3.438 47.098,5.745 47.656,9 44.732,7.463 41.807,9 42.365,5.745 40,3.438 43.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,0 56.194,2.963 59.463,3.438 57.098,5.745 57.656,9 54.732,7.463 51.807,9 52.365,5.745 50,3.438 53.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='64.732,0 66.194,2.963 69.463,3.438 67.098,5.745 67.656,9 64.732,7.463 61.807,9 62.365,5.745 60,3.438 63.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,0 76.194,2.963 79.463,3.438 77.098,5.745 77.656,9 74.732,7.463 71.807,9 72.365,5.745 70,3.438 73.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,0 86.194,2.963 89.463,3.438 87.098,5.745 87.656,9 84.732,7.463 81.807,9 82.365,5.745 80,3.438 83.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='94.732,0 96.194,2.963 99.463,3.438 97.098,5.745 97.656,9 94.732,7.462 91.807,9 92.365,5.745 90,3.438 93.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='4.732,10 6.194,12.963 9.463,13.438 7.098,15.745 7.656,19 4.732,17.463 1.807,19 2.366,15.745 0,13.438 3.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,10 16.194,12.963 19.463,13.438 17.098,15.745 17.656,19 14.732,17.463 11.807,19 12.366,15.745 10,13.438 13.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,10 26.194,12.963 29.463,13.438 27.098,15.745 27.656,19 24.732,17.463 21.807,19 22.365,15.745 20,13.438 23.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,10 36.194,12.963 39.463,13.438 37.098,15.745 37.656,19 34.732,17.463 31.807,19 32.365,15.745 30,13.438 33.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='44.732,10 44.732,17.463 41.807,19 42.365,15.745 40,13.438 43.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='44.732,10 46.195,12.963 49.464,13.438 47.099,15.745 47.657,19 44.732,17.463 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,10 56.194,12.963 59.463,13.438 57.098,15.745 57.656,19 54.732,17.463 51.807,19 52.365,15.745 50,13.438 53.27,12.963 '></polygon> <polygon class='svg-rating-star-inactive' points='64.732,10 66.194,12.963 69.463,13.438 67.098,15.745 67.656,19 64.732,17.463 61.807,19 62.365,15.745 60,13.438 63.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,10 76.194,12.963 79.463,13.438 77.098,15.745 77.656,19 74.732,17.463 71.807,19 72.365,15.745 70,13.438 73.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,10 86.194,12.963 89.463,13.438 87.098,15.745 87.656,19 84.732,17.463 81.807,19 82.365,15.745 80,13.438 83.27,12.963 '></polygon></g></svg></rating>";
	   	 			    } else if(xing ==5){
	   	 			    	xingstr="<rating class='grst-detail rating'><svg class='eleme-svg svg-rating-star ng-scope' viewBox='10 0 50 10'><g id='svg-rating-star'><polygon class='svg-rating-star-inactive' points='4.732,0 6.194,2.963 9.463,3.438 7.098,5.745 7.656,9 4.732,7.463 1.807,9 2.366,5.745 0,3.438 3.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,0 16.194,2.963 19.463,3.438 17.098,5.745 17.656,9 14.732,7.463 11.807,9 12.366,5.745 10,3.438 13.269,2.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,0 26.194,2.963 29.463,3.438 27.098,5.745 27.656,9 24.732,7.463 21.807,9 22.365,5.745 20,3.438 23.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,0 36.194,2.963 39.463,3.438 37.098,5.745 37.656,9 34.732,7.463 31.807,9 32.365,5.745 30,3.438 33.27,2.963 '></polygon> <polygon class='svg-rating-star-active' points='44.732,0 46.194,2.963 49.463,3.438 47.098,5.745 47.656,9 44.732,7.463 41.807,9 42.365,5.745 40,3.438 43.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='54.732,0 56.194,2.963 59.463,3.438 57.098,5.745 57.656,9 54.732,7.463 51.807,9 52.365,5.745 50,3.438 53.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='64.732,0 66.194,2.963 69.463,3.438 67.098,5.745 67.656,9 64.732,7.463 61.807,9 62.365,5.745 60,3.438 63.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,0 76.194,2.963 79.463,3.438 77.098,5.745 77.656,9 74.732,7.463 71.807,9 72.365,5.745 70,3.438 73.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,0 86.194,2.963 89.463,3.438 87.098,5.745 87.656,9 84.732,7.463 81.807,9 82.365,5.745 80,3.438 83.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='94.732,0 96.194,2.963 99.463,3.438 97.098,5.745 97.656,9 94.732,7.462 91.807,9 92.365,5.745 90,3.438 93.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='4.732,10 6.194,12.963 9.463,13.438 7.098,15.745 7.656,19 4.732,17.463 1.807,19 2.366,15.745 0,13.438 3.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,10 16.194,12.963 19.463,13.438 17.098,15.745 17.656,19 14.732,17.463 11.807,19 12.366,15.745 10,13.438 13.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,10 26.194,12.963 29.463,13.438 27.098,15.745 27.656,19 24.732,17.463 21.807,19 22.365,15.745 20,13.438 23.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,10 36.194,12.963 39.463,13.438 37.098,15.745 37.656,19 34.732,17.463 31.807,19 32.365,15.745 30,13.438 33.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='44.732,10 44.732,17.463 41.807,19 42.365,15.745 40,13.438 43.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='44.732,10 46.195,12.963 49.464,13.438 47.099,15.745 47.657,19 44.732,17.463 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,10 56.194,12.963 59.463,13.438 57.098,15.745 57.656,19 54.732,17.463 51.807,19 52.365,15.745 50,13.438 53.27,12.963 '></polygon> <polygon class='svg-rating-star-inactive' points='64.732,10 66.194,12.963 69.463,13.438 67.098,15.745 67.656,19 64.732,17.463 61.807,19 62.365,15.745 60,13.438 63.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,10 76.194,12.963 79.463,13.438 77.098,15.745 77.656,19 74.732,17.463 71.807,19 72.365,15.745 70,13.438 73.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,10 86.194,12.963 89.463,13.438 87.098,15.745 87.656,19 84.732,17.463 81.807,19 82.365,15.745 80,13.438 83.27,12.963 '></polygon></g></svg></rating>";
	   	 			    }else{
	   	 			    	xingstr="<rating class='grst-detail rating'><svg class='eleme-svg svg-rating-star ng-scope' viewBox='10 0 50 10'><g id='svg-rating-star'><polygon class='svg-rating-star-inactive' points='4.732,0 6.194,2.963 9.463,3.438 7.098,5.745 7.656,9 4.732,7.463 1.807,9 2.366,5.745 0,3.438 3.269,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='14.732,0 16.194,2.963 19.463,3.438 17.098,5.745 17.656,9 14.732,7.463 11.807,9 12.366,5.745 10,3.438 13.269,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='24.732,0 26.194,2.963 29.463,3.438 27.098,5.745 27.656,9 24.732,7.463 21.807,9 22.365,5.745 20,3.438 23.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='34.732,0 36.194,2.963 39.463,3.438 37.098,5.745 37.656,9 34.732,7.463 31.807,9 32.365,5.745 30,3.438 33.27,2.963 '></polygon> <polygon class='svg-rating-star-inactive' points='44.732,0 46.194,2.963 49.463,3.438 47.098,5.745 47.656,9 44.732,7.463 41.807,9 42.365,5.745 40,3.438 43.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,0 56.194,2.963 59.463,3.438 57.098,5.745 57.656,9 54.732,7.463 51.807,9 52.365,5.745 50,3.438 53.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='64.732,0 66.194,2.963 69.463,3.438 67.098,5.745 67.656,9 64.732,7.463 61.807,9 62.365,5.745 60,3.438 63.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,0 76.194,2.963 79.463,3.438 77.098,5.745 77.656,9 74.732,7.463 71.807,9 72.365,5.745 70,3.438 73.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,0 86.194,2.963 89.463,3.438 87.098,5.745 87.656,9 84.732,7.463 81.807,9 82.365,5.745 80,3.438 83.27,2.963 '></polygon><polygon class='svg-rating-star-inactive' points='94.732,0 96.194,2.963 99.463,3.438 97.098,5.745 97.656,9 94.732,7.462 91.807,9 92.365,5.745 90,3.438 93.27,2.963 '></polygon><polygon class='svg-rating-star-active' points='4.732,10 6.194,12.963 9.463,13.438 7.098,15.745 7.656,19 4.732,17.463 1.807,19 2.366,15.745 0,13.438 3.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='14.732,10 16.194,12.963 19.463,13.438 17.098,15.745 17.656,19 14.732,17.463 11.807,19 12.366,15.745 10,13.438 13.269,12.963 '></polygon><polygon class='svg-rating-star-active' points='24.732,10 26.194,12.963 29.463,13.438 27.098,15.745 27.656,19 24.732,17.463 21.807,19 22.365,15.745 20,13.438 23.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='34.732,10 36.194,12.963 39.463,13.438 37.098,15.745 37.656,19 34.732,17.463 31.807,19 32.365,15.745 30,13.438 33.27,12.963 '></polygon><polygon class='svg-rating-star-active' points='44.732,10 44.732,17.463 41.807,19 42.365,15.745 40,13.438 43.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='44.732,10 46.195,12.963 49.464,13.438 47.099,15.745 47.657,19 44.732,17.463 '></polygon><polygon class='svg-rating-star-inactive' points='54.732,10 56.194,12.963 59.463,13.438 57.098,15.745 57.656,19 54.732,17.463 51.807,19 52.365,15.745 50,13.438 53.27,12.963 '></polygon> <polygon class='svg-rating-star-inactive' points='64.732,10 66.194,12.963 69.463,13.438 67.098,15.745 67.656,19 64.732,17.463 61.807,19 62.365,15.745 60,13.438 63.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='74.732,10 76.194,12.963 79.463,13.438 77.098,15.745 77.656,19 74.732,17.463 71.807,19 72.365,15.745 70,13.438 73.27,12.963 '></polygon><polygon class='svg-rating-star-inactive' points='84.732,10 86.194,12.963 89.463,13.438 87.098,15.745 87.656,19 84.732,17.463 81.807,19 82.365,15.745 80,13.438 83.27,12.963 '></polygon></g></svg></rating>";
		   	 			}
			        	$("#storexing").html(xingstr);
			        	$("#storeimage").attr("src",storedetail.pictrue_url);
			        	$("#storejf").html(storedetail.minjfrate);
			        	$("#storezk").html(storedetail.minzkrate);
			        	var way_typeList=storedetail.way_typeList;
 			        	var paytypestr="";
			        	for (var i = 0; i < way_typeList.length; i++) {
							if(way_typeList[i].way_type == "1"){
								paytypestr+="<img src='../img/ddxf.png'style='width:43px' />"
							}else{
								paytypestr+="<img src='../img/wgzt.png' style='width:43px' />"
							}
						}
			        	$("#storepaytype").html(paytypestr);
 			        	var way_statusList=storedetail.way_statusList;
			        	var pay_waystr="";
			        	for (var i = 0; i < way_statusList.length; i++) {
							if(way_statusList[i].way_status == "1"){
								pay_waystr+="<img src='../img/alipayzf.png' style='width:22px' />"
							}else if(way_statusList[i].way_status == "2"){ 
								pay_waystr+="<img src='../img/wxzf.png'  style='width:22px'/>"
							}else if(way_statusList[i].way_status == "3"){ 
								pay_waystr+="<img src='../img/poszf.png'  style='width:22px'/>"
							}else if(way_statusList[i].way_status == "4"){ 
								pay_waystr+="<img src='../img/pgzf.png'  style='width:22px'/>"
							}
						}
 			        	$("#storepayway").html(pay_waystr);
 			        	$("#storeaddress").html("地址："+storedetail.address);
			        	/* $("#storeinfor").html(storedetail.store_introduce); */
			        	$("#storesjxq").attr("href","<%=basePath%>html_member/goStoreInforByH5.do?store_id=${pd.store_id}");
			        	$("#storeyyzz").attr("href","<%=basePath%>html_member/goStoreImage.do?image_one="+storedetail.business_licenses_image_one+"&image_two=&image_three=&image_four=&image_fix=" );
			        	$("#storejyxk").attr("href","<%=basePath%>html_member/goStoreImage.do?image_one="+storedetail.license_image_one+"&image_two="+storedetail.license_image_two+"&image_three="+storedetail.license_image_three+"&image_four="+storedetail.license_image_four+"&image_fix="+storedetail.license_image_fix);
			        	var daoLiuStoreList=storedetail.daoLiuStoreList;
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
				        			onemarkstr+="<span class='zeng tit_bg_box'>增</span>";
				        		}else if(onemark_type == "2"){
				        			onemarkstr+="<span class='jian tit_bg_box' >减</span>";
				        		}else if(onemark_type == "3"){
				        			onemarkstr+="<span class='shi tit_bg_box'   >时</span>";
				        		}else if(onemark_type == "4"){
				        			onemarkstr+="<span class='song tit_bg_box'   >买</span>";
				        		}else if(onemark_type == "5"){
				        			onemarkstr+="<span class='song tit_bg_box'   >购</span>";
				        		}else if(onemark_type == "6"){
				        			onemarkstr+="<span class='ji tit_bg_box'   >积</span>";
 				        		}else if(onemark_type == "7"){
				        			onemarkstr+="<span class='zhe tit_bg_box '   >折</span>";
				        		}
				        	}
				        	var str="<a href='html_member/goStoreDetail.do?member_id=${pd.member_id}&store_id="+onestore.ci_store_id+"&daoliurecord_id="+onestore.daoliurecord_id+"' style='color:#000'><li class='li_item' style='width: 50%;float: left;padding: 0 0.4rem 1rem 0.4rem;'>"+
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
	      });
		}
 		</script>
 </body>
</html>
 --%>