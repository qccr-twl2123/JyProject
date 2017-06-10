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
		<style type="text/css">
		#ss{
		        border: none;
			    background: #66d4f5;
			    color: #fff;
			    height: 25px;
			    line-height: 25px;
			    padding: 0 10px;
			    border-radius: 5px;
			    position: absolute;
			    right: 45px;
			    top: 52px;
		}
		</style>
 </head>
<body style="background:#ededed;">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">找商家发红包</div>
</nav>

<div class="searchdiv">
	<input type="text" class="serach" placeholder="输入商家名称" id="store_name"><span id="ss" onclick="searchStore('1')">搜索</span></input>
	
</div>
 <div class="zsjfhb-title">
	我消费过的
</div>
<div class="rm-me-list clearfix">
	<ul id="allStoreList">
		<!-- <li class="clearfix">
			<a href="javascript:;" class="fr input-hb-red">有红包<br>领一个</a>  
			<span class="rm-me-tx"><img src="/imgmem/20130518113434876(1).jpg"></span>
			<div class="rm-me-name">
				<b>曼陀罗</b>
 			</div>
		</li> -->
	</ul>
</div>
<script type="text/javascript">
		$(function(){
 			searchStore("0");
 		});
		//商家列表
		function searchStore(value){
			var store_name=$("#store_name").val();
			var city_name="";
			var url="<%=basePath%>app_store_redpackets/listIsRed.do";
			if(value == "1"){
				city_name="${pd.city_name}";
 			}
  			//验证提交
			$.ajax({
			        type:"post",
			        url:url, 
			  	 	 data:{
			  	 		"store_name":store_name,
			 		 	"member_id":"${pd.member_id}" ,
			 		 	"city_name":city_name ,
			  	 	 },                
			        dataType:"json",
			        success: function(data){
			        	 var allStoreList=data.data;
			        	 $("#allStoreList").empty();
			        	 for (var i = 0; i < allStoreList.length; i++) {
			        		    //判断是否有红包
			        		    var s="";
			        		    if(allStoreList[i].haveRed == "0"){
			        		    	s="<a onclick='sendDX(this)' store_id='"+allStoreList[i].store_id+"' class='fr input-hb-gay'>没有？<br>发一个呗</a>  ";
			        		    }else{
			        		    	s="<a onclick='goStoreGoods(this)' store_id='"+allStoreList[i].store_id+"' class='fr input-hb-red'>有红包<br>领一个</a>  ";
			        		    }
 		 						var str="<li class='clearfix'>"+
											s+
											"<span class='rm-me-tx'><img src='"+allStoreList[i].pictrue_url+"'></span>"+
 											"<div class='rm-me-name' style=' margin-top: 12px; '>"+
												"<b>"+allStoreList[i].store_name+"</b>"+
								 			"</div>"+
										"</li>";
 								$("#allStoreList").append(str);
						}
 				    }
	      });
		}
		
		//发短信
		function sendDX(obj){
			var store_id=$(obj).attr("store_id");
			//发短信
			$.ajax({
			        type:"post",
			        url:'<%=basePath%>app_store_redpackets/messageForStoreRed.do', 
			  	 	 data:{
			  	 		"member_id":"${pd.member_id}" ,
			  	 		"store_id":store_id 
			  	 	 },                
			        dataType:"json",
			        success: function(data){ 
			        	if(data.result == '1'){
			        		alert(data.message);
			        	}
 					}
		  });
		} 
		
		//去商家的商品界面
		function goStoreGoods(obj){
			var store_id=$(obj).attr("store_id");
			window.location.href="<%=basePath%>/html_member/goStoreGoods.do?store_id="+store_id+"&member_id=${pd.member_id}";
		} 
</script>
</body>
</html>
