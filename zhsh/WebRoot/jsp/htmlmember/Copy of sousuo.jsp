<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/style.css">
<link rel="stylesheet" href="<%=basePath%>css/htmlmember/styles.css" type="text/css">
<script type="text/javascript" src="<%=basePath%>js/iscroll.js"></script>
<script src="<%=basePath%>js/jquery-1.8.0.min.js"></script> 
<style type="text/css">
	.serch-button{
		position:absolute;
		background: #ffba00;
		color: #fff;
		height: 100%;
		right: 0;
		top: 0;
		border:none;
		width: 50px;
		border-top-right-radius: 5px;
		border-bottom-right-radius: 5px;
		font-family: "微软雅黑"
	}
</style>
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
 </head>
<body>
<nav class="top">
	<a href="<%=basePath%>html_member/gouShouYe.do"><b class="back-arrow fl"></b></a>
	<div class="phone-search" style="margin:5px auto; width:80%">
		<input type="text" class="serach" id="content" style="width:86%;font-family: 微软雅黑"  placeholder="请输入商家名称，地址" oninput="storeListTwo('1')"/>
 	</div>
</nav>
<!-- 上拉，下拉位置 -->
<div id="thelist"  class="storeList" >
			 <!-- 商家集合 -->
</div>
<div id="pullUp" onclick="clickmore()">
		<span class="pullUpLabel moreshuju"   style="font-size:1rem;">点击加载更多.... </span>
</div>
<!-- 结束 -->
<input type="hidden" name="page" value="1" id="page"/>
<!-- 结束 -->
<script type="text/javascript">
//点击加载更多
function clickmore(){
 		//设置页数
		var page=parseInt( $("#page").val() )+1;
		$("#page").val(page);
		$(".moreshuju").html("<img src='../../images/jiazai.gif' width='28px;'' />");
		storeListTwo("2");
}
//筛选商家
function storeListTwo(type){
			if(type == "1"){
				$("#page").val("1");
			}
			var content=$("#content").val();
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
			 		 	"city_file_sort_id":"",
			 		 	"sort_type":"",
			 		 	"content":content,
			 		 	"paixu":"",
			 		 	"shaixuan":"",
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
			   	 		if(allstoreList.length <=2 &&  $("#page").val() != "1"){
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

//去商家详情页
function goStoreDetail(store_id){
	window.location.href="<%=basePath%>html_member/goStoreDetail.do?store_id="+store_id+"&member_id=${pd.member_id}";
}
</script>
</script>


</body>
</html>
 --%>