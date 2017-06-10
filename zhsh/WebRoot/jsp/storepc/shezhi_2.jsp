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
    <title>商家信息</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
    <link rel="stylesheet" href="css/pcstore/hsd_information.css">
	<script src="js/jquery-1.8.0.min.js"></script>
</head>
<body onkeydown="BindEnter(event)">
<div class="bg">
<header>
    <div class="head_cont">
        <img src="img/page/business_inf/inf_logo.png" alt="" class="logo">
        <div class="title">•  商家信息 </div>
        <div class="one"></div>
    </div>
</header>
<form action="<%=basePath%>storepc_StoreManageController/editStore.do" method="post" name="Form" id="Form">
<input type="hidden" name="jichushezhi" value="${pd.jichushezhi}">
<input type="hidden" name="store_id" value="${pd.store_id }">
<section>
    <ul class="inf_list">
        <li class="phone">
            <div class="tit"><span>*</span><p>电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话：</p></div>
            <input value="${pd.phone_bymemeber}" type="text"  name="phone_bymemeber"  maxlength="16"><p>注：该手机号码为门店服务号码，将展示给会员。</p>
        </li>
        <li class="address">
            <div class="tit"><span>*</span><p>详细地址：</p></div>
            <input  value="${pd.address}" type="text" name="address">
			<div class="tit"><p>楼层：</p></div>
			<input value="${pd.store_storey}" type="text" name="store_storey" >
        </li>
        <li class="keywords">
            <div class="tit"><span>关&nbsp;&nbsp;&nbsp;键&nbsp;&nbsp;&nbsp;字</span><p>:</p></div>
            <input type="text" name="keyword">
            </li>
        <li class="range">
            <div class="tit"><span>*</span><p>经营范围：</p></div>
            <textarea rows="3" cols="20" required name="management_projects_desc" >${pd.management_projects_desc }</textarea>
            </li>
        <li class="information">
            <div class="tit"><p>公告信息：</p></div>
            <textarea rows="3" cols="20" name="notice_information"  >${pd.notice_information }</textarea>
            </li>
        <li class="introduce">
            <div class="tit"><span>*</span><p>商家介绍：</p></div>
            <textarea rows="3" cols="20" required name="store_introduce" >${pd.store_introduce }</textarea></li>
        <li class="notice">
            <div class="tit"><p>官方网址：</p></div>
            <input type="text" name="website_address"></li>
    </ul>
</section>
</form>
<footer>
    <div class="next" onclick="subm()" id="subm">
        <img src="img/page/next.png" alt="">
    </div>
</footer>
</div>
<script type="text/javascript">
	function subm() {
		$("#Form").submit();
	}
	
	//使用document.getElementById获取到按钮对象
	function BindEnter(event){
		var subm = document.getElementById("subm");
		if(event.keyCode == 13){
			subm.click();
			event.returnValue = false;
		}
	}
</script>
</body>
</html>