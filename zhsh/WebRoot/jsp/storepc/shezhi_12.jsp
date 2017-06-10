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
    <title>九鱼销链，欢迎您</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/pcstore/hsd_tips.css">
	 <script src="js/storepc/jquery-1.8.0.min.js"></script>
</head>
<body  onkeydown="BindEnter(event)"> 
<div class="bg">
<header>
    <div class="head_cont">
        <img src="img/storelogo.png" alt="" class="logo">
        <div class="title">•  九鱼销链，欢迎您~ </div>
        <div class="one"></div>
    </div>
</header>
<section>
    <div class="sec_cont">
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;设置完成，以上信息可在商家后台根据实际需要进行调整，点击”进入商家后台“即可。</p>
    </div>
</section>
<footer>
    <div class="font_cont">
        <div class="next" onclick="nowstart()" id="nowstart" style="width: 29%; float: right; " >
            <img src="img/page/jiantou.png" alt=""><img src="img/page/sjht_03.png" alt="">
        </div>
        
    </div>
</footer>
</div>
<script type="text/javascript"> 
//使用document.getElementById获取到按钮对象
		function BindEnter(event){
			var nowstart = document.getElementById("nowstart");
			if(event.keyCode == 13){
				nowstart.click();
				event.returnValue = false;
			}
		}

        //结束设置
     	function nowstart(){
     		window.location.href='<%=basePath%>storepc/goStore.do';
      	}
</script>
</body>
</html>