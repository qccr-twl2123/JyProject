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
	<base href="<%=basePath%>">
 	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="css/htmlmember/style.css">
	<link rel="stylesheet" href="css/htmlmember/styles.css" type="text/css">
</head>
<body style="background:#ededed">
<nav class="top">
	<a href="html_member/gouShouYe.do"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">切换地址</div>
</nav>
<div class="searchdiv">
		<input class="serach" type="search" style="border:none"  placeholder="城市/行政区/拼音" oninput="contentSure(this.value)" />
</div>
<div class="index_dress_title">
	<span>当前定位城市</span>
	<span class="dress-border">${pd.city_name}</span>
</div>


<div class="dress1-list">
 	<ul id="cityList">
	
	</ul>
</div>
<!-- <div class="dress1-list">
	<p>B</p>
	<ul>
		<li>鞍山</li>
		<li>安庆</li>
		<li>安阳</li>
	</ul>
</div> -->
<script type="text/javascript" src="js/htmlmember/jquery.min.js"></script>
<script type="text/javascript">
//前往首页
function goArea(obj){
  	window.location.href='<%=basePath%>html_member/goArea.do?city_name='+$(obj).html();
}
	
function cityFangfa(content){
	$.ajax({
    	type:"post",
    	url:'<%=basePath%>html_member/listCity.do', 
	 	data:{"content":content},                
    	dataType:"json",
    	success: function(data){
	   	 	if(data.result == "1"){ 
	   	 		$("#cityList").empty();
	   	 	    var cityList=data.data;
	   	 	    for (var i = 0; i < cityList.length; i++) {
		   	 	    for(var key in cityList[i]) { 
                        //alert("键：" + key + ",值  姓名："+ friendList[i][key].name);
                         if(cityList[i][key].length != 0){
                        	 $("#cityList").append("<li>"+key+"</li>");
	                            for (var j = 0; j < cityList[i][key].length; j++) {
	                            	//alert(cityList[i][key][j].city_name);
	                            	$("#cityList").append("<li onclick=\"goArea(this)\">"+cityList[i][key][j].city_name+"</li>");
							}
                        }
                      }
				}
		   	}else{
	   	 		alert(data.message);
	   	 	}
     	}
	});
}


$(function(){
	cityFangfa("");
}); 

//搜索
function contentSure(value){
 	cityFangfa(value);
}
</script>

<p></p>
<br>
<p></p>
<br>
<p></p>
<br>
</body>
</html>
