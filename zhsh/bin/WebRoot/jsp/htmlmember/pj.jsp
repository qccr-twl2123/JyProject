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
		<link href="<%=basePath%>css/htmlmember/lanrenzhijia.css" type="text/css" rel="stylesheet" />
		<%-- <script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery-1.11.3.min.js"></script> --%>
		<%-- <script type="text/javascript" src="<%=basePath%>js/htmlmember/main.js"></script> --%>
		<script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>  
		<style type="text/css">
			.active{
			fill:#ff9f19;
			}
			.inactive{
			fill:#ccc;
			}
		</style>
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
			       	 	if(storedetail.iscollect != "0"){
			        		$(".sp-sc").css("background","url(../images/sj_06.png) no-repeat"); 
			        	}
 			        	//详情
			        	$(".sp-sc").attr("collect",storedetail.iscollect);
			        	$(".sp-vip").attr("vip",storedetail.vip);
 			        }
	      });
		}
		//评价列表
		if(true){
			//验证提交
			$.ajax({
			        type:"post",
			        url:'<%=basePath%>app_comment/listAll.do', 
			  	 	 data:{
			  	 		"store_id":"${pd.store_id}" 
			  	 	 },                
			        dataType:"json",
			        success: function(data){
			        	if(data.result == "1"){
			        		var list=data.data;
 	 			        	$("#storepf").html(list.score);
	 			        	$("#storebfb").html(list.percent);
	 			        	//星星
	 			        	var n=parseInt(list.score);
	 			        	if( n == 1){
	 			        		$("#two").css("fill","#ccc");
	 			        		$("#three").css("fill","#ccc");
	 			        		$("#four").css("fill","#ccc");
	 			        		$("#fix").css("fill","#ccc");
	 			        	}else if( n == 2){
 	 			        		$("#three").css("fill","#ccc");
 	 			        		$("#four").css("fill","#ccc");
	 			        		$("#fix").css("fill","#ccc");
	 			        	}else if( n == 3){
  	 			        		$("#four").css("fill","#ccc");
	 			        		$("#fix").css("fill","#ccc");
	 			        	}else if( n == 4){
   	 			        		$("#fix").css("fill","#ccc");
	 			        	}else if( n == 5){
	 			        		
  	 			        	}
	 			        	//评论循环
	 			        	var commentList=list.commentList;
	 			        	for (var i = 0; i < commentList.length; i++) {
	 			        		var xing=parseInt(commentList[i].star_number);
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
			   	 			    var imgList=commentList[i].imgList;
			   	 			    var imgstr="";
			   	 			    for (var j = 0; j < imgList.length; j++){
			   	 			    	imgstr+="<div class='content'> <a class='example2' href='"+imgList[j].icon_url+"'><img src='"+imgList[j].icon_url+"'/></a></div>";
								}
			   	 			    //imgstr+="</div>";
 								var str="";
								str+="<li>"+
									"<span class='comment-tx'><img src='"+commentList[i].image_url+"'></span>"+
									"<div class='comment-text clearfix'>"+
										"<p class='comment-phone'>"+commentList[i].phone+"<span class='fr'>"+commentList[i].star_time+"</span></p>"+
										"<p>"+xingstr+
 											"<span class='red'>"+commentList[i].star_number+"分</span><span></span>"+
										"</p>"+
										"<div class='comment-text-div'>"+commentList[i].content+"</div>"+
										  imgstr+
									"</div>"+
								"</li>";
								$("#commentList").append(str);
								if(true){
				   	 			    $("#example1").imgbox();
					   	 			$(".example2").imgbox({
					   	 				'speedIn'		: 0,
					   	 				'speedOut'		: 0,
					   	 				'alignment'		: 'center',
					   	 				'overlayShow'	: true,
					   	 				'allowMultiple'	: false
					   	 			});
			   	 			    }
							}
			        	}
  			        }
	      });
		}
		</script>
</head>
<body style="background:#dedede;">
<nav class="top">
	<i class="sp-sc fr" onclick="iscollect(this)"></i>
    <i class="sp-vip fr" onclick="isvip(this)"></i>
	<a href="<%=basePath%>html_member/gouShouYe.do"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">商家评论</div>
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
        	 if(data.result == "1"){
        		
        	 }
         }
    });
}

</script>
<div class="yh-title sj-title clearfix">
	<ul>
		<li  ><a href="<%=basePath%>html_member/goStoreDetail.do?store_id=${pd.store_id}&member_id=${pd.member_id}">商家</a></li>
		<li><a href="<%=basePath%>html_member/goStoreGoods.do?store_id=${pd.store_id}&member_id=${pd.member_id}">商品</a></li>
		<li class="sj-current"><a href="<%=basePath%>html_member/goStoreComment.do?store_id=${pd.store_id}&member_id=${pd.member_id}">评价</a></li>
	</ul>
</div>

<div class="pj-pf clearfix">
	<span class="pj-pf-left">
		<p class="red fourteen-px" id="storepf">4.5</p>
		<p>综合评分</p>
		<p>高于周边商家<span id="storebfb">67.3</span>%</p>
	</span>
	<rating class="grst-detail rating">
					<svg class="eleme-svg svg-rating-star ng-scope" viewBox="10 0 50 10" style="width:160px;height:33px;float:left;margin-left:10px;margin-top:25px">
					    <g id="svg-rating-star">
					    <polygon class="svg-rating-star-active" points="4.732,0 6.194,2.963 9.463,3.438 7.098,5.745 7.656,9 4.732,7.463 1.807,9 2.366,5.745 0,3.438 3.269,2.963 "></polygon>
					    <polygon id="one"  class="active" points="14.732,0 16.194,2.963 19.463,3.438 17.098,5.745 17.656,9 14.732,7.463 11.807,9 12.366,5.745 10,3.438 13.269,2.963 "></polygon>
					    <polygon id="two" class="active" points="24.732,0 26.194,2.963 29.463,3.438 27.098,5.745 27.656,9 24.732,7.463 21.807,9 22.365,5.745 20,3.438 23.27,2.963 "></polygon>
					    <polygon id="three" class="active" points="34.732,0 36.194,2.963 39.463,3.438 37.098,5.745 37.656,9 34.732,7.463 31.807,9 32.365,5.745 30,3.438 33.27,2.963 "></polygon>
					    <polygon id="four" class="active" points="44.732,0 46.194,2.963 49.463,3.438 47.098,5.745 47.656,9 44.732,7.463 41.807,9 42.365,5.745 40,3.438 43.27,2.963 "></polygon>
					    <polygon id="fix" class="active" points="54.732,0 56.194,2.963 59.463,3.438 57.098,5.745 57.656,9 54.732,7.463 51.807,9 52.365,5.745 50,3.438 53.27,2.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="64.732,0 66.194,2.963 69.463,3.438 67.098,5.745 67.656,9 64.732,7.463 61.807,9 62.365,5.745 60,3.438 63.27,2.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="74.732,0 76.194,2.963 79.463,3.438 77.098,5.745 77.656,9 74.732,7.463 71.807,9 72.365,5.745 70,3.438 73.27,2.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="84.732,0 86.194,2.963 89.463,3.438 87.098,5.745 87.656,9 84.732,7.463 81.807,9 82.365,5.745 80,3.438 83.27,2.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="94.732,0 96.194,2.963 99.463,3.438 97.098,5.745 97.656,9 94.732,7.462 91.807,9 92.365,5.745 90,3.438 93.27,2.963 "></polygon>
					    <polygon class="svg-rating-star-active" points="4.732,10 6.194,12.963 9.463,13.438 7.098,15.745 7.656,19 4.732,17.463 1.807,19 2.366,15.745 0,13.438 3.269,12.963 "></polygon>
					    <polygon class="svg-rating-star-active" points="14.732,10 16.194,12.963 19.463,13.438 17.098,15.745 17.656,19 14.732,17.463 11.807,19 12.366,15.745 10,13.438 13.269,12.963 "></polygon>
					    <polygon class="svg-rating-star-active" points="24.732,10 26.194,12.963 29.463,13.438 27.098,15.745 27.656,19 24.732,17.463 21.807,19 22.365,15.745 20,13.438 23.27,12.963 "></polygon>
					    <polygon class="svg-rating-star-active" points="34.732,10 36.194,12.963 39.463,13.438 37.098,15.745 37.656,19 34.732,17.463 31.807,19 32.365,15.745 30,13.438 33.27,12.963 "></polygon>
					    <polygon class="svg-rating-star-active" points="44.732,10 44.732,17.463 41.807,19 42.365,15.745 40,13.438 43.27,12.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="44.732,10 46.195,12.963 49.464,13.438 47.099,15.745 47.657,19 44.732,17.463 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="54.732,10 56.194,12.963 59.463,13.438 57.098,15.745 57.656,19 54.732,17.463 51.807,19 52.365,15.745 50,13.438 53.27,12.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="64.732,10 66.194,12.963 69.463,13.438 67.098,15.745 67.656,19 64.732,17.463 61.807,19 62.365,15.745 60,13.438 63.27,12.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="74.732,10 76.194,12.963 79.463,13.438 77.098,15.745 77.656,19 74.732,17.463 71.807,19 72.365,15.745 70,13.438 73.27,12.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="84.732,10 86.194,12.963 89.463,13.438 87.098,15.745 87.656,19 84.732,17.463 81.807,19 82.365,15.745 80,13.438 83.27,12.963 "></polygon>
					    </g>
				  </svg>
	 </rating>
</div>
<!-- <div class="content">
	<a class="example2" href="images/3793633099_3e1e53e4ac_o.jpg"><img src="images/3793633099_4f9c3e08b3_m.jpg" /></a>
</div> -->
<div class="comment-list">
	<ul id="commentList">
		<!-- <li>
			<span class="comment-tx"><img src="/imgmem/20130518113434876(1).jpg"></span>
			<div class="comment-text">
				<p class="comment-phone">186****6515<span class="fr">2016-01-20  16：30</span></p>
				<p>
					<rating class="grst-detail rating">
					<svg class="eleme-svg svg-rating-star ng-scope" viewBox="10 0 50 10">
					    <g id="svg-rating-star">
					    <polygon class="svg-rating-star-active" points="4.732,0 6.194,2.963 9.463,3.438 7.098,5.745 7.656,9 4.732,7.463 1.807,9 2.366,5.745 0,3.438 3.269,2.963 "></polygon>
					    <polygon class="svg-rating-star-active" points="14.732,0 16.194,2.963 19.463,3.438 17.098,5.745 17.656,9 14.732,7.463 11.807,9 12.366,5.745 10,3.438 13.269,2.963 "></polygon>
					    <polygon class="svg-rating-star-active" points="24.732,0 26.194,2.963 29.463,3.438 27.098,5.745 27.656,9 24.732,7.463 21.807,9 22.365,5.745 20,3.438 23.27,2.963 "></polygon>
					    <polygon class="svg-rating-star-active" points="34.732,0 36.194,2.963 39.463,3.438 37.098,5.745 37.656,9 34.732,7.463 31.807,9 32.365,5.745 30,3.438 33.27,2.963 "></polygon>
					    <polygon class="svg-rating-star-active" points="44.732,0 46.194,2.963 49.463,3.438 47.098,5.745 47.656,9 44.732,7.463 41.807,9 42.365,5.745 40,3.438 43.27,2.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="54.732,0 56.194,2.963 59.463,3.438 57.098,5.745 57.656,9 54.732,7.463 51.807,9 52.365,5.745 50,3.438 53.27,2.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="64.732,0 66.194,2.963 69.463,3.438 67.098,5.745 67.656,9 64.732,7.463 61.807,9 62.365,5.745 60,3.438 63.27,2.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="74.732,0 76.194,2.963 79.463,3.438 77.098,5.745 77.656,9 74.732,7.463 71.807,9 72.365,5.745 70,3.438 73.27,2.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="84.732,0 86.194,2.963 89.463,3.438 87.098,5.745 87.656,9 84.732,7.463 81.807,9 82.365,5.745 80,3.438 83.27,2.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="94.732,0 96.194,2.963 99.463,3.438 97.098,5.745 97.656,9 94.732,7.462 91.807,9 92.365,5.745 90,3.438 93.27,2.963 "></polygon>
					    <polygon class="svg-rating-star-active" points="4.732,10 6.194,12.963 9.463,13.438 7.098,15.745 7.656,19 4.732,17.463 1.807,19 2.366,15.745 0,13.438 3.269,12.963 "></polygon>
					    <polygon class="svg-rating-star-active" points="14.732,10 16.194,12.963 19.463,13.438 17.098,15.745 17.656,19 14.732,17.463 11.807,19 12.366,15.745 10,13.438 13.269,12.963 "></polygon>
					    <polygon class="svg-rating-star-active" points="24.732,10 26.194,12.963 29.463,13.438 27.098,15.745 27.656,19 24.732,17.463 21.807,19 22.365,15.745 20,13.438 23.27,12.963 "></polygon>
					    <polygon class="svg-rating-star-active" points="34.732,10 36.194,12.963 39.463,13.438 37.098,15.745 37.656,19 34.732,17.463 31.807,19 32.365,15.745 30,13.438 33.27,12.963 "></polygon>
					    <polygon class="svg-rating-star-active" points="44.732,10 44.732,17.463 41.807,19 42.365,15.745 40,13.438 43.27,12.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="44.732,10 46.195,12.963 49.464,13.438 47.099,15.745 47.657,19 44.732,17.463 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="54.732,10 56.194,12.963 59.463,13.438 57.098,15.745 57.656,19 54.732,17.463 51.807,19 52.365,15.745 50,13.438 53.27,12.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="64.732,10 66.194,12.963 69.463,13.438 67.098,15.745 67.656,19 64.732,17.463 61.807,19 62.365,15.745 60,13.438 63.27,12.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="74.732,10 76.194,12.963 79.463,13.438 77.098,15.745 77.656,19 74.732,17.463 71.807,19 72.365,15.745 70,13.438 73.27,12.963 "></polygon>
					    <polygon class="svg-rating-star-inactive" points="84.732,10 86.194,12.963 89.463,13.438 87.098,15.745 87.656,19 84.732,17.463 81.807,19 82.365,15.745 80,13.438 83.27,12.963 "></polygon>
					    </g>
					   </svg>
				</rating>
					<span class="red">5分</span><span>30分钟送达</span>
				</p>
				<div class="comment-text-div">按时送货。东西还是以前的好吃按时送货。东西还是以前的好吃按时送货。东西还是以前的好吃按时送货。东西还是以前的好吃按时送货。东西还是以前的好吃</div>
			</div>
		</li> -->
	</ul>
</div>

<script src="<%=basePath%>js/htmlmember/jquery.min.js"></script>
<script src="<%=basePath%>js/htmlmember/jquery.imgbox.pack.js"></script>
 <script>
$(function(){
	
});
</script>

</body>
</html>
