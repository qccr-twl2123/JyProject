<!-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>九鱼 欢迎你</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
    <link rel="stylesheet" href="css/pcstore/bootstrap.min.css">
    <link rel="stylesheet" href="css/pcstore/hsd_welcome.css">
</head>
<body onkeydown="BindEnter(event)">
<div class="bg">
	<header>
	    <div class="head_cont">
	        <img src="img/storelogo.png" alt="" class="logo" style="
    width: 100px;
">
	        <div class="title">•  九鱼销链，欢迎您~ </div>
	        <div class="one"></div>
	    </div>
	</header>
	<section>
	    <div class="sec_cont">
	        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢迎您使用九鱼销链，初次登录，请您设置门店的基本信息，以下设置大约需要10-20分钟，设置完成后，将由服务商为您制作门店专属物料，同时您的门店信息将在九鱼网上正式展示，设置的信息后期可在系统中进行修改。</p>
	    </div>
	</section>
	<footer>
	     <div class="font_cont">
	        <div class="button_box">
	            <div class="butt"  onclick="nowstart()" id="nowstart">前往下一步</div>
	            <div class="fl">
	                <img src="img/page/jiantou.png" alt="">
	            </div>
	        </div>
	    </div>
	 </footer>
 </div>
<script src="<%=basePath%>js/storepc/jquery-1.8.0.min.js"></script>
<script type="text/javascript"> 
        //开始设置
     	function nowstart(){
     		window.location.href='<%=basePath%>storepc/goSheZhiOne.do?store_id=${pd.store_id}&jichushezhi=10000000000';
      	}
        
     	//使用document.getElementById获取到按钮对象
    	function BindEnter(event){
    		var nowstart = document.getElementById("nowstart");
    		if(event.keyCode == 13){
    			nowstart.click();
    			event.returnValue = false;
    		}
    	}
     </script>
</body>
</html>