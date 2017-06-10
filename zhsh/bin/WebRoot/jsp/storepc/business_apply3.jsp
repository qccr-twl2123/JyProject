<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>门店信息填写</title>
	 <base href="<%=basePath%>">
	 <link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
	<link rel="stylesheet" href="css/pcstore/hsd_mdxx.css">
	<script src="js/jquery-1.8.0.min.js"></script>
    <script src="js/jquery.tips.js"></script>
	<script type="text/javascript">
	        window.history.forward(1);
	</script>
</head>
<body>
	<form action="storepc/save.do" id="From" method="post">
	<input name="registertel_phone" type="hidden" value="${pd.phone}"/>
	<input name="longitude" type="hidden" value="${pd.longitude}"/>
	<input name="latitude" type="hidden" value="${pd.latitude}"/>
	<input name="province_name" type="hidden" value="${pd.province_name}"/>
	<input name="city_name" type="hidden" value="${pd.city_name}"/>
	<input name="area_name" type="hidden" value="${pd.area_name}"/>
 	<!--头部-->
    <header>
        <div class="head_cont">
             <div><a href="<%=basePath%>"><img src="img/storelogo.png" alt="" class="logo"></a>• 填写门店信息</div>
        </div>
    </header>
  	<div class="title">
  		<div class="sec_head">
    		<p>请填写您的门店信息</p>
    		<span>(带*必填，九鱼网绝对不会将您的个人信息透漏给第三方)</span>
    	</div>
  	</div>
    <div class="list">
    	<div class="list_cont">
    		<ul>
    			<li> 
    			<div class="name"><span>*</span>商店名称：</div><input type="text" name="business_licenses_name" id = "business_licenses_name"><span class="zhushi">注：营业执照上的全称</span>
    			</li>
    			<li> 
    			<div class="name"><span>*</span>显示名称：</div><input type="text" name="store_name" id = "store_name"><span class="zhushi">注：显示在网页上和APP上的商店简称</span>
    			</li>
    			<li><div class="name"><span>*</span>商店地址：</div>${pd.address}  <input type="text" name="address" id="address" value=""></li>
    			<li><div class="name"><span>*</span>联系人：</div><input type="text" name="principal" id = "principal"></li>
    			<li><div class="name_pinlei"><span>*</span>经营品类：</div><textarea name="management_projects_desc" id = "management_projects_desc" cols="30" rows="10"></textarea></li>
    			<li><div class="name"><span>*</span>手机号码：</div><input readonly="readonly" type="text"  name="phone" id="phone"  value="${pd.phone}"></li>
    			<li><div class="button"><div class="btnbox" id="search" onclick="sureAddStore()">现在开始</div></div></li>
    		</ul>
    	</div>
    	<div class="list_img">
    		<img src="img/mdxx_img_03.jpg" alt="">
    	</div>
    </div>
     <footer>
    <div class="footcont">
        <ul>
            <li><a href="storepc/goRegister.do" class="noborder">我要开店</a></li>
            <li><a href="jsp/storepc/gyjy.html" target="_blank">关于九鱼</a></li>
            <li><a href="">加入我们</a></li>
            <li><a href="">合作流程</a></li>
            <li><a href="">常见问题</a></li>
        </ul>
    </div>
    <div class="beian">©2016 jiuyuvip.com [浙] ICP备16025718号-2 ,All Rights Reserved.</div>
</footer>
<script type="text/javascript">
	    //注册
	    function sureAddStore(){
	    	    var address = $("#address").val();
	    	    var business_licenses_name = $("#business_licenses_name").val().trim();
	    	    var store_name = $("#store_name").val().trim();
	    	    var principal = $("#principal").val().trim();
	    	    var management_projects_desc = $("#management_projects_desc").val().trim();
	    	    var phone = $("#phone").val().trim();
 	    	    if(business_licenses_name == '' || business_licenses_name == null){
 	    	    	$("#business_licenses_name").tips({
    					side:3,
    		            msg:'商店名称不能为空',
    		            bg:'#AE81FF',
    		            time:1
	    		    });
	    			$("#business_licenses_name").focus();
	    	    	return ;
	    	    }
	    	    if(store_name == '' || store_name == null){
 	    	    	$("#store_name").tips({
    					side:3,
    		            msg:'显示名称不能为空',
    		            bg:'#AE81FF',
    		            time:1
	    		    });
	    			$("#store_name").focus();
	    	    	return ;
	    	    }
	    	    if(address == '' || address == null){
 	    	    	$("#address").tips({
	    					side:3,
	    		            msg:'商店地址不能为空',
	    		            bg:'#AE81FF',
	    		            time:1
	    		    });
	    			$("#address").focus();
	    	    	return ;
	    	    } 
	    	    if(principal == '' || principal == null){
 	    	    	 $("#principal").tips({
	    					side:3,
	    		            msg:'联系人姓名不能为空',
	    		            bg:'#AE81FF',
	    		            time:1
	    		    });
	    			$("#principal").focus();
	    	    	return ;
	    	    }
	    	     if(management_projects_desc == '' || management_projects_desc == null){
	    	    	 $("#management_projects_desc").tips({
	    					side:3,
	    		            msg:'经营品项不能为空',
	    		            bg:'#AE81FF',
	    		            time:1
	    		    });
	    			$("#management_projects_desc").focus();
	    	    	return ;
 	    	    }
 	    	    $("#address").val("${pd.address}"+address);
 	    	    $("#From").submit();
  	    } 
 
     </script>
</body>
</html>