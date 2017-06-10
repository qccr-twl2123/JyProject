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
		<style type="text/css">
		.zhxx-dj{
			 width:20px; 
		}
		</style>
 </head>
<body style="background:#ededed;">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">账户信息</div>
</nav>
<div class="shdz-content zhxx-content zhxx-tx-anniu clearfix">
	<span>头像</span>
	<b class="z-arrow" style="margin-top:25px;margin-left:10px;"></b>
	<a class="example2 my-top-tx fr" href="${empty pg.image_url?'../../img/moren.png':pg.image_url }"  style=" width: 80px; ">
		<img src="${empty pg.image_url?'../../img/moren.png':pg.image_url }" />
	</a>
</div>

<article class="rm-list tj-list my-list">
	<ul>
		<c:if test="${pg.vip_level eq '1'}">
			<li style="padding:0 5%">会员等级<i class="zhxx-dj" ></i> </li>
		</c:if>
		<c:if test="${pg.vip_level eq '2'}">
			<li style="padding:0 5%">会员等级<i class="zhxx-dj"></i><i class="zhxx-dj"></i></li>
		</c:if>
		<c:if test="${pg.vip_level eq '3'}">
			<li style="padding:0 5%">会员等级<i class="zhxx-dj"></i><i class="zhxx-dj"></i><i class="zhxx-dj"></i> </li>
		</c:if>
		<c:if test="${pg.vip_level eq '4'}">
			<li style="padding:0 5%">会员等级<i class="zhxx-dj"></i> <i class="zhxx-dj"></i> <i class="zhxx-dj"></i> <i class="zhxx-dj"></i> </li>
		</c:if>
		<c:if test="${pg.vip_level eq '5'}">
			<li style="padding:0 5%">会员等级<i class="zhxx-dj"></i><i class="zhxx-dj"></i><i class="zhxx-dj"></i><i class="zhxx-dj"></i><i class="zhxx-dj"></i> </li>
		</c:if>
 	</ul>
</article>
<article class="rm-list tj-list my-list">
	<ul>
		<li><a href="<%=basePath%>html_me/goMe.do?member_id=${pd.member_id }&type=2">用户名<b class="z-arrow"></b><span class="fr">${pg.name }</span></a></li>
	</ul>
</article>

<article class="rm-list tj-list my-list">
	<ul>
		<li><a href="<%=basePath%>html_me/goMe.do?member_id=${pd.member_id }&type=3">修改账户密码<b class="z-arrow"></b></a></li>
	</ul>
</article>
<article class="rm-list tj-list my-list">
	<ul>
		<li><a href="<%=basePath%>html_me/goMe.do?member_id=${pd.member_id }&type=4">已绑定手机号<b class="z-arrow"></b><span class="fr">${pg.phone }</span></a></li>
	</ul>
</article>
<div class="bj"></div>
<div class="fsjfq-forms">
	<ul>
		<li>
			<a href="javascript:;" class="file">相册
			    <input type="file" name="" id="">
			</a>
		</li>
		<li>
			<a href="javascript:;" class="file" style="border:none">拍照
				<input type="file" accept="image/*" capture="camera">
			</a>
		</li>
		
	</ul>
	<ul style="margin-top:10px;">
		<li>
			<a href="javascript:;"  style="border:none" class="file fsjfq-delte">取消</a>
		</li>
 	</ul>
</div>
<script src="<%=basePath%>js/htmlmember/jquery.min.js"></script>
<script type="/text/javascript" src="<%=basePath%>js/htmlmember/tx.js"></script>
<script src="<%=basePath%>js/htmlmember/jquery.imgbox.pack.js"></script>
<script>
$(function(){
	$("#example1").imgbox();
	$(".example2").imgbox({
		'speedIn'		: 0,
		'speedOut'		: 0,
		'alignment'		: 'center',
		'overlayShow'	: true,
		'allowMultiple'	: false
	});
});
</script>

</body>
</html>
