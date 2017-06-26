<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">  
    <title>个人中心</title>
 	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/style.css">
	<link rel="stylesheet" href="<%=basePath%>css/htmlmember/styles.css" type="text/css">
</head>
<body style="background:#ededed;">
<nav class="top">
	<div style="text-align:center;line-height:40px;color:#fff">个人中心</div>
</nav>
<div class="my-top clearfix">
	<a href="<%=basePath%>html_me/goMe.do?type=1">
		<b class="z-arrow my-arrow fr"></b>
		<span class="my-top-tx fl" style=" width: 80px; "><img src="${empty pg.image_url?'../../img/moren.png':pg.image_url}" ></span>
		<div class="my-top-text">
			<p><i class="people-icon"></i>${pg.name }</p>
			<p>${pg.show_lookid}<b></b></p>
		</div>
		
	</a>
</div>
<div class="wallet clearfix">
	<ul>
		<li>
			<a href="<%=basePath%>html_me/goMe.do?type=11">
				<p><span style="color:#ff0600"><fmt:formatNumber type="number" value="${pg.now_money}" pattern="0.00" maxFractionDigits="2"/></span>元</p>
				<p>钱包余额</p>
			</a>
		</li>
		<li>
			<a href="<%=basePath%>html_me/goMe.do?type=13">
				<p><span style="color:#f8b551">${pg.redpackage_number }</span>个</p>
				<p>红包</p>
			</a>
		</li>
		<li>	<a href="<%=basePath%>html_me/goMe.do?type=12">
				<p><span style="color:#556fb5"><fmt:formatNumber type="number" value="${pg.now_integral}" pattern="0.00" maxFractionDigits="2"/></span>分</p>
				<p>积分余额</p>
			</a>
		</li>
	</ul>
</div>
<article class="rm-list tj-list  ">
	<ul>
		<li><a href="<%=basePath%>html_me/goMe.do?type=6"><i class="my-list-one"></i>提货劵<b class="z-arrow"></b></a></li>
		<li><a href="<%=basePath%>html_me/goMe.do?type=7"><i class="my-list-three"></i>我的订单<b class="z-arrow"></b></a></li>
	</ul>
</article>
<article class="rm-list tj-list  ">
	<ul>
		<li><a href="<%=basePath%>html_me/goMe.do?type=8"><i class="my-list-two"></i>我的会员卡<b class="z-arrow"></b></a></li>
	</ul>
</article>
<article class="rm-list tj-list  ">
	<ul>
 		<li><a href="<%=basePath%>html_me/goMe.do?type=14"><i class="my-list-four"></i>收藏<b class="z-arrow"></b></a></li>
	</ul>
</article>
<article class="rm-list tj-list  ">
	<ul>
		<li><a href="<%=basePath%>html_me/goMe.do?type=5"><i class="my-list-five"></i>推广二维码<b class="z-arrow"></b></a></li>
		<!-- <li><a href="#"><i class="my-list-eight"></i>推荐有奖<b class="z-arrow"></b></a></li> -->
		<li><a><i class="my-list-six"></i>魅力值  <span style="color:red;display: inline-block;float: right;">${pg.charm_number}</span></a></li>
	</ul>
</article>
<article class="rm-list tj-list  ">
	<ul>
		<li><a href="<%=basePath%>html_me/goMe.do?type=9"><i class="my-list-shi"></i>我要开店<b class="z-arrow"></b></a></li>
 	</ul>
</article>
<article class="rm-list tj-list ">
	<ul>
		<li><a href="<%=basePath%>html_me/outLogin.do?islogin=0&in_jiqi=5" ><i  class="my-list-seven"></i>解除与当前微信绑定</a></li>
	</ul>
</article> 
<%-- <article class="rm-list tj-list  ">
	<ul>
		<li><a href="<%=basePath%>oneYuan/listAllGoods.do?member_id=${pd.member_id}&type=2"><i class="my-list-five"></i>一元购商品<b class="z-arrow"></b></a></li>
 	</ul>
</article>  --%>
<%-- <article class="rm-list tj-list ">
	<ul>
		<li><a href="<%=basePath%>html_me/goMe.do?type=10"><i  class="my-list-seven"></i>设置<b class="z-arrow"></b></a></li>
	</ul>
</article> --%> 
 <p></p>
<br>
<br>
<br>
<br>
<footer class="footerdi">
	<ul>
		<li class="f_whole">
			<a href="<%=basePath%>html_member/gouShouYe.do">
				<i ></i>
				首页
			</a>
		</li>
		<li class="f_jiexiao">
			<a href="<%=basePath%>html_member/goMyYouXuan.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}">
				<i></i>
				优选爆品
			</a>
		</li>
		<li class="f_car">
			<a href="<%=basePath%>html_member/gouRenMai.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}">
				<i></i>
				人脉圈
			</a>
		</li>
		<li class="f_personal">
 			<a style=" color: #e90000; " href="<%=basePath%>html_me/goMe.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}">
				<i class="cur"></i>
				我的
			</a>
		</li>
	</ul>
</footer>
</body>
</html>
