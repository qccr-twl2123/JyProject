<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
		<title>首页</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		 <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/style.css">
		<link rel="stylesheet" href="<%=basePath%>css/htmlmember/styles.css" type="text/css">
 		<script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery-1.11.3.min.js"></script>
 		<script type="text/javascript" src="<%=basePath%>js/htmlmember/main.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/iscroll.js"></script>
 		<style type="text/css"  >
 		/**
		 *
		 * Pull down styles
		 *
		 */
		 #pullUp {
			background: #fff;
		    height: 40px;
		    line-height: 30px;
		    padding: 5px 80px;  
		    border-bottom: 1px solid #ccc;
		    word-spacing:8px; 
		    font-weight: bold;
		    font-size: 11px;
		    color: #8A8885;
		    text-align: center;
		 }
  		</style> 
		<script type="text/javascript">
 		 //获取首页数据
		 if(true){
 			 $.ajax({
			    	type:"post",
			    	url:'<%=basePath%>app_shouye/kjListGoods.do', 
				 	data:{
				 		 	"province_name":"${pd.province_name}",
				 		 	"city_name":"${pd.city_name}",
				 		 	"area_name":"${pd.area_name}",
				 		 	"longitude":"${pd.longitude}",
				 		 	"latitude":"${pd.latitude}",
				 		 	"member_id":"${pd.member_id}"
			 	 	},                
			    	dataType:"json",
			    	success: function(data){
				   	 	if(data.result == "1"){
				   	 		//城市一级分类的集合
				   	 		var cityList=data.data.cityList;
				   	 		for (var i = 0; i < cityList.length; i++) {
								if(i < 8){
									$("#onecity").append("<a onclick=\"storeList(\'"+cityList[i].city_file_sort_id+"\',\'1\',\'1\',\'0\',\'"+cityList[i].sort_name+"\',\'智能排序\',\'筛选\')\"><i><img src=\""+cityList[i].sort_imageurl+"\" width=\"50px\" height=\"50px\"></i>"+cityList[i].sort_name+"</a>");
								}else if(i >= 8 && i < 16){
									$("#twocity").append("<a onclick=\"storeList(\'"+cityList[i].city_file_sort_id+"\',\'1\',\'1\',\'0\',\'"+cityList[i].sort_name+"\',\'智能排序\',\'筛选\')\"><i><img src=\""+cityList[i].sort_imageurl+"\"></i>"+cityList[i].sort_name+"</a>");
								}/* else if(i >= 16 && i < 24){
									$("#threecity").append("<a onclick=\"storeList(\'"+cityList[i].city_file_sort_id+"\',\'1\',\'1\',\'0\',\'"+cityList[i].sort_name+"\',\'智能排序\',\'筛选\')\"><i><img src=\""+cityList[i].sort_imageurl+"\"></i>"+cityList[i].sort_name+"</a>");
		 						} */else{
		 							//无效
		 						}
		 					}
				   	 		
		 		   	 		//轮播图的集合
				   	 		var advertList=data.data.advertList;
				   	 		for (var i = 0; i < advertList.length; i++) {
				   	 			$("#advertList li").eq(i).append("<a href='"+advertList[i].hyperlink_url+"'><img src='"+advertList[i].image_url+"' alt='' width='100%' height='100'></a>");		 		 
 				   	 		}
				   	 		
		 		   	 		
				   	 		//商家的集合
				   	 		var allstoreList=data.data.allstoreList;
				   	 		for (var i = 0; i < allstoreList.length; i++) {
				   	 				var xing=parseInt(allstoreList[i].comment_score);
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
		 			   	 			var str="<div class='business-list'>"+
					 					 		"<a onclick=\"goStoreDetail(\'"+allstoreList[i].store_id+"\')\" class=\"clearfix\">"+
						 					 		"<div class='index-tx clearfix'>"+
						 					 			"<i><img src='"+allstoreList[i].pictrue_url+"' style='width:80px;height:80px;'></i> <p style='color:#ff271d;font-weight:bold'>"+allstoreList[i].integral_rate+"</p>"+
								   		 				"<p style='color:#ff271d;'>赠送积分率</p>"+
								   		 			"</div>"+
								   		 			"<div>"+
								   		 				"<div class='business-xq'>"+
								   		 					"<div>"+allstoreList[i].store_name+"<span style='float:right;font-size:12px;color:#aeaeae;font-weight:normal'>距离"+allstoreList[i].distance+"千米</span></div>"+
								   		 					xingstr+
								   		 					"<span style='color:#ff4e00'>"+allstoreList[i].comment_score+"分</span> <span style='color:#aeaeae;font-weight:normal;font-size:12px;'> 已售"+allstoreList[i].transaction_times+"单</span>"+
								   		 				"</div>";
			 			   	 		var marketlist=allstoreList[i].marketlist;
						        	for(var m=0;m<marketlist.length ; m++){
						        		var marketing_type=marketlist[m].marketing_type;
						        		var grantrule=marketlist[m].grantrule;
						        		if(marketing_type == "1"){
						        			str+="<p class='yh'><span class='zeng'>增</span>"+grantrule+"</p>";
						        		}else if(marketing_type == "2"){
						        			str+="<p class='yh'><span class='jian'>减</span>"+grantrule+"</p>";
						        		}else if(marketing_type == "3"){
						        			str+="<p class='yh'><span class='shi'>时</span>"+grantrule+"</p>";
						        		}else if(marketing_type == "4"){
						        			str+="<p class='yh'><span class='song'>买</span>"+grantrule+"</p>";
						        		}else if(marketing_type == "5"){
						        			str+="<p class='yh'><span class='song'>买</span>"+grantrule+"</p>";
						        		}else if(marketing_type == "6"){
						        			str+="<p class='yh'><span class='ji'>积</span>"+grantrule+"</p>";
						        		}else if(marketing_type == "7"){
						        			str+="<p class='yh'><span class='zhe'>折</span>"+grantrule+"</p>";
						        		}
						        	}
						   		 	str+=" </div></a></div>";
		 		   	 				$(".storeList").append(str);
		   					}
				   	 		
		 	 	   	 	}else{
				   	 		alert(data.message);
				   	 	}
			     }
			});
		 }
		</script>
</head>
<body>
   <div style="">
	<nav class="top">
		<span class="fr top-right">+
			<div class="top-right-hide">
				<i class="top-san"></i>
				<ul>
					<li><a href="<%=basePath%>html_member/getFuJinRed.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}&member_id=${pd.member_id}"><i class="icon1"></i>抢附近商家红包</a></li>
					<li><a href="<%=basePath%>html_member/gotuijian.do?member_id=${pd.member_id}"><i class="icon2"></i>推荐好友注册</a></li>
 				</ul>
			</div>
		</span>
		<a href="<%=basePath%>html_member/goArea.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}&member_id=${pd.member_id}" class="top-text"><img src="<%=basePath%>images/dw_03.png" alt="">${pd.address}</a>
	</nav>
	<script type="text/javascript">
	$(".top-right").click(function(){
	 	if($(".top-right-hide").css("display") == "none"){
			$(".top-right-hide").show();
		}else{
			$(".top-right-hide").hide();
		}
	});
	
	$(function() {
		// 查询输入框绑定回车事件
		$("#searchTxt").bind("keydown", function(e) {
		if (e.keyCode == 13) {
			    var content = $("#searchTxt").val().trim();
				$.ajax({
					type:"post",
					url:"<%=basePath%>html_member/gouShouYe.do",
					dataType:"json",
	 				data:{
	 						//province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}&member_id=${pd.member_id}
	 						content : content
	 				},
					success:function(data){
						
	 				}
				});
		}
	});
	});
	
	</script>
	<div id="isshow">
	<div class="searchdiv">
		<a href="<%=basePath%>html_member/gouStoreSouSuo.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}&member_id=${pd.member_id}">
			<input value="" class="serach" id = "searchTxt" placeholder="请输入商家名称，地址">
		</a><!-- <span>搜索</span> --> 
	</div>
	<div id="lunbotu">
	    <div class="swipe-wrap" style="margin-bottom: 22px;">    <!--此处class的名称是固定的-->
	        <div id="onecity"> </div>
	        <div id="twocity"> </div>
 	    </div>
	    <ul class="yuandian">
	        <li class="cur"></li>
	        <li></li>
	    </ul>
	</div>
	<script src='<%=basePath%>js/htmlmember/swipe.js'></script>
	<script>
	  	if(true){
			// pure JS
		    var elem = document.getElementById('lunbotu');
		    window.mySwipe = Swipe(elem, {
		        	// startSlide: 4,
		         //auto: 3000,        //每隔3000ms，自动轮播一次
		        // continuous: true,
		        // disableScroll: true,
		        // stopPropagation: true,
	
	//	        回调函数表示没做完一个轮播，就执行下面的内容
		         callback: function(index, element) {
	//	             console.log(index);           //在控制台输出index的值
	
	//	             让自己的li添加cur类，其他的兄弟li移除cur类，也就是一种排他
		             $(".yuandian li").eq(index).addClass("cur").siblings().removeClass("cur");
	
		         }
		        // transitionEnd: function(index, element) {}
		    });
	
	//	    点击小圆点，图片会有一个slide的效果
		    $('.yuandian li').click(function(){
		        mySwipe.slide($(this).index());
		    });
	
		    // with jQuery
		    // window.mySwipe = $('#mySwipe').Swipe().data('Swipe');
		} 
	</script>
	
	<div class="hongbao" >
	<div class="slider">
		<ul id="advertList">
		    <li id="advert0">  </li>
			<li id="advert1"> </li>
			<li id="advert2">  </li> 
 		 </ul>
	</div>
	</div>
	<script type="text/javascript" src="<%=basePath%>js/htmlmember/yxMobileSlider.js"></script>
	<script>
	    $(".slider").yxMobileSlider({width:640,during:3000})
	</script> 
</div>
<article class="index-title clearfix">
	<ul>
		<li onclick="listAllCitySort('sort')">
			<span  id="city_file_sort_id" city_file_sort_id='0' sort_type='0'>全部分类</span>
			<div class="whw triangle-down">    <!--向下的三角--> </div>
			<i></i>
		</li>
		<li onclick="showbiaoti('paixu')">
			<span  id="paixu" paixu='1'>智能排序</span>
			<div class="whw triangle-down">    <!--向下的三角--> </div>
			<i></i>
		</li>
		<li onclick="showbiaoti('shaixuan')">
			<span  id="shaixuan" shaixuan='0'>筛选</span>
			<div class="whw triangle-down">    <!--向下的三角--> </div>
		</li>
	</ul>
<div>
<script type="text/javascript">
		//获取一级二级分类
		function listAllCitySort(value){
				//验证提交
				$.ajax({
				        type:"post",
				        url:'<%=basePath%>app_shouye/listAllCitySort.do', 
				  	 	 data:{
				  	 		"province_name":"${pd.province_name}",
				 		 	"city_name":"${pd.city_name}",
				 		 	"area_name":"${pd.area_name}"
				  	 	 },                
				        dataType:"json",
				        success: function(data){
				       	 	var oneList=data.data;
				       	 	$(".fl").empty();
				       	 	 for (var i = 0; i < oneList.length; i++) {
								$(".fl").append("<li city_file_sort_id='"+oneList[i].city_file_sort_id+"' sort_type='"+oneList[i].sort_type+"' onclick='showTwo(this)'>"+oneList[i].sort_name+"</li>");
								var twoList=oneList[i].twoList;
								for (var j = 0; j< twoList.length; j++) {
									$(".all-left").append("<li class='"+oneList[i].city_file_sort_id+" two' city_file_sort_id='"+twoList[j].city_file_sort_id+"' sort_type='"+twoList[j].sort_type+"' style='display:none' onclick='searchStore(this)' >"+twoList[j].sort_name+"</li>");
				 				}
							}
				        }
		      });
 				//关闭隐藏
				showbiaoti(value);
		}
		
		function showbiaoti(value){
			//关闭隐藏
			if($("."+value).css("display") != "none"){
	 			$("."+value).hide();
	 			$(".whw").removeClass("triangle-down");
	 			$(".whw").removeClass("triangle-up");
	 			$(".whw").addClass("triangle-down");
			}else{
				$(".all").hide();
				$("."+value).show();
				$(".whw").removeClass("triangle-down");
	 			$(".whw").removeClass("triangle-up");
	 			$(".whw").addClass("triangle-down");
 	 			$("#"+value).next().addClass("triangle-up");
			}
		}

 </script>
	<div class="all all-one sort" style="top: 41px;z-index: 1;">
		<ul class="fl"> </ul><!-- 一级分类 -->
		<ul class="all-left" id="" > </ul><!-- 二级分类 -->
	</div>
	<div class="all paixu px" style="top: 39px;z-index: 1;">
		<ul>
			<li onclick="searchpaixu(this,'1')" >
				智能排序
			</li>
			<li onclick="searchpaixu(this,'2')">
				距离从近到远
			</li>
			<li onclick="searchpaixu(this,'3')">
				人气从高到低
			</li>
			<li onclick="searchpaixu(this,'4')">
				积分率从高到低
			</li>
		</ul>
	</div>
	<div class="all shaixuan px" style="top: 41px;z-index: 1;">
		<ul  style="width: 33%; background: #fff;  padding-bottom: 15px;float: right;">
			<li onclick="searchshai(this,'0')">
				筛选
			</li>
			<li onclick="searchshai(this,'1')">
				首单立减
			</li>
			<li onclick="searchshai(this,'2')">
				折扣商品
			</li>
			<li onclick="searchshai(this,'3')">
				满返代金券
			</li>
		</ul>
	</div>
</div>	
</article>
<div id="thelist"  class="storeList" >
			 <!-- 商家集合 -->
</div>
<div id="pullUp" onclick="clickmore()">
		<span class="pullUpLabel moreshuju" style="font-size:1rem;">点击加载更多... </span>
</div>
<!-- 结束 -->
<input type="hidden" name="page" value="1" id="page"/>
<script type="text/javascript">
//点击加载更多
function clickmore(){
 		//设置页数
		var page=parseInt( $("#page").val() )+1;
		$("#page").val(page);
		//开始筛选
		var paixu=$("#paixu").attr("paixu");
		var shaixuan=$("#shaixuan").attr("shaixuan");
		var city_file_sort_id=$("#city_file_sort_id").attr("city_file_sort_id");
		var sort_type=$("#city_file_sort_id").attr("sort_type");
		$(".moreshuju").html("<img src='../../images/jiazai.gif' width='28px;'' />");
		storeListTwo(city_file_sort_id,sort_type,paixu,shaixuan);
 }
	
 //鼠标移入事件显示二级分类
function showTwo(obj){
	var city_file_sort_id=$(obj).attr("city_file_sort_id");
	$(".two").hide();
	$("."+city_file_sort_id).show();
}
///类别筛选商家
function searchStore(obj){
	var city_file_sort_id=$(obj).attr("city_file_sort_id");
	var sort_name=$(obj).html();
	if($(obj).html().trim() == "全部"){
		var parent=$(".fl").find("li").each(function(){
			if($(this).attr("city_file_sort_id") == city_file_sort_id){
				sort_name=$(this).html();
			}
		});
	} 
	
	var sort_type=$(obj).attr("sort_type");
	var paixu=$("#paixu").attr("paixu");
	var paixu_name=$("#paixu").html();
	var shaixuan=$("#shaixuan").attr("shaixuan");
	var shaixuan_name=$("#shaixuan").html();
	storeList(city_file_sort_id,sort_type,paixu,shaixuan,sort_name,paixu_name,shaixuan_name);
}

//筛选
function searchshai(obj,shaixuan){
	var shaixuan_name=$(obj).html();
	var paixu=$("#paixu").attr("paixu");
	var paixu_name=$("#paixu").html();
	var city_file_sort_id=$("#city_file_sort_id").attr("city_file_sort_id");
 	var sort_name=$("#city_file_sort_id").html();
	var sort_type=$("#city_file_sort_id").attr("sort_type");
	storeList(city_file_sort_id,sort_type,paixu,shaixuan,sort_name,paixu_name,shaixuan_name);
}

//排序
function searchpaixu(obj,paixu){
	var paixu_name=$(obj).html();
	var shaixuan=$("#shaixuan").attr("shaixuan");
	var shaixuan_name=$("#shaixuan").html();
	var city_file_sort_id=$("#city_file_sort_id").attr("city_file_sort_id");
	var sort_name=$("#city_file_sort_id").html();
	var sort_type=$("#city_file_sort_id").attr("sort_type");
	storeList(city_file_sort_id,sort_type,paixu,shaixuan,sort_name,paixu_name,shaixuan_name);
 }


var page=1;

//商家筛选
function storeList(city_file_sort_id,sort_type,paixu,shaixuan,sort_name,paixu_name,shaixuan_name){
	window.location.href="<%=basePath%>html_member/gouStoreShaiXuan.do?city_file_sort_id="+city_file_sort_id+"&sort_type="+sort_type+
			"&longitude=${pd.longitude}"+"&latitude=${pd.latitude}&province_name=${pd.province_name}&city_name=${pd.city_name}&area_name=${pd.area_name}&address=${pd.address}"+
			"&paixu="+paixu+"&shaixuan="+shaixuan+"&page="+page+"&sort_name="+sort_name+"&paixu_name="+paixu_name+"&shaixuan_name="+shaixuan_name;
}
//去商家详情页
function goStoreDetail(store_id){
	window.location.href="<%=basePath%>html_member/goStoreDetail.do?store_id="+store_id+"&member_id=${pd.member_id}";
}



//筛选商家
function storeListTwo(city_file_sort_id,sort_type,paixu,shaixuan){
			$.ajax({
		    	type:"post",
		    	url:'<%=basePath%>app_shouye/listAllStore.do', 
			 	data:{
			 		 	"province_name":"${pd.province_name}",
			 		 	"city_name":"${pd.city_name}",
			 		 	"area_name":"${pd.area_name}",
			 		 	"address":"${pd.address}",
			 		 	"longitude":"${pd.longitude}",
			 		 	"latitude":"${pd.latitude}",
			 		 	"city_file_sort_id":city_file_sort_id,
			 		 	"sort_type":sort_type,
			 		 	"paixu":paixu,
			 		 	"shaixuan":shaixuan,
			 		 	"page":$("#page").val()
		 	 	},                
		    	dataType:"json",
		    	success: function(data){
			   	 	if(data.result == "1"){
			   	 		//商家的集合
			   	 		if($("#page").val() == 1){
			   	 			$(".storeList").empty();
			   	 		}
			   	 		$(".all").hide();
			   	 		var allstoreList=data.data;
			   	 		if(allstoreList.length == 0 &&  $("#page").val() != "1"){
			   	 			//设置页数
			   				var page=parseInt( $("#page").val() )-1;
			   				$("#page").val(page);
			   				$(".moreshuju").html("<span style='font-size:1rem;'>已经到顶了</span>");
			   	 		}else{
			   	 			$(".moreshuju").html("<span style='font-size:1rem;'>点击加载更多...</span>");
			   	 		}
			   	 		for (var i = 0; i < allstoreList.length; i++) {
					   	 		var xing=parseInt(allstoreList[i].comment_score);
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
				   	 			var str="<div class='business-list'>"+
				 					 		"<a onclick=\"goStoreDetail(\'"+allstoreList[i].store_id+"\')\" class=\"clearfix\">"+
					 					 		"<div class='index-tx clearfix'>"+
					 					 			"<i><img src='"+allstoreList[i].pictrue_url+"' style='width:80px;height:80px;'></i> <p style='color:#ff271d;font-weight:bold'>"+allstoreList[i].integral_rate+"</p>"+
							   		 				"<p style='color:#ff271d;'>赠送积分率</p>"+
							   		 			"</div>"+
							   		 			"<div>"+
							   		 				"<div class='business-xq'>"+
							   		 					"<div>"+allstoreList[i].store_name+"<span style='float:right;font-size:12px;color:#aeaeae;font-weight:normal'>距离"+allstoreList[i].distance+"千米</span></div>"+
							   		 					xingstr+
							   		 					"<span style='color:#ff4e00'>"+allstoreList[i].comment_score+"分</span> <span style='color:#aeaeae;font-weight:normal;font-size:12px;'> 已售"+allstoreList[i].transaction_times+"单</span>"+
							   		 				"</div>";
					   	 		var marketlist=allstoreList[i].marketlist;
					        	for(var m=0;m<marketlist.length ; m++){
					        		var marketing_type=marketlist[m].marketing_type;
					        		var grantrule=marketlist[m].grantrule;
					        		if(marketing_type == "1"){
					        			str+="<p class='yh'><span class='zeng'>增</span>"+grantrule+"</p>";
					        		}else if(marketing_type == "2"){
					        			str+="<p class='yh'><span class='jian'>减</span>"+grantrule+"</p>";
					        		}else if(marketing_type == "3"){
					        			str+="<p class='yh'><span class='shi'>时</span>"+grantrule+"</p>";
					        		}else if(marketing_type == "4"){
					        			str+="<p class='yh'><span class='song'>买</span>"+grantrule+"</p>";
					        		}else if(marketing_type == "5"){
					        			str+="<p class='yh'><span class='song'>买</span>"+grantrule+"</p>";
					        		}else if(marketing_type == "6"){
					        			str+="<p class='yh'><span class='ji'>积</span>"+grantrule+"</p>";
					        		}else if(marketing_type == "7"){
					        			str+="<p class='yh'><span class='zhe'>折</span>"+grantrule+"</p>";
					        		}
					        	}
					   		 	str+=" </div></a></div>";
				   	 			$(".storeList").append(str);
							}
			   	 		
			 	   	 	}else{
			   	 		 alert(data.message);
			   	 	}
		     }
		});
}
</script>
<p></p>
<br>
<p></p>
<br>
<p></p>
<br>
<footer class="footerdi">
	<ul>
		<li class="f_whole">
			<a style=" color: #e90000; " href="<%=basePath%>html_member/gouShouYe.do?member_id=${pd.member_id}">
				<i class="cur"></i>
				首页
			</a>
		</li>
		<li class="f_jiexiao">
			<a href="<%=basePath%>html_member/goMyYouXuan.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}&member_id=${pd.member_id}">
				<i></i>
				优选爆品
			</a>
		</li>
		<li class="f_car">
			<a href="<%=basePath%>html_member/gouRenMai.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}&member_id=${pd.member_id}">
				<i></i>
				人脉圈
			</a>
		</li>
		<li class="f_personal">
 			<a href="<%=basePath%>html_me/goMe.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}&member_id=${pd.member_id}">
				<i></i>
				我的
			</a>
		</li>
	</ul>
</footer>
</div>
</body>
</html>
 --%>