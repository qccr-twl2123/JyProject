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
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/main.js"></script>
		<script type="text/javascript">
		if(true){
			//验证提交
			$.ajax({
			        type:"post",
			        url:'<%=basePath%>app_friend/tuiJianPhoneList.do', 
			  	 	 data:{
			  	 		"phoneList":"手机通讯录的电话集合",
			 		 	"member_id":"${pd.member_id}" 
			  	 	 },                
			        dataType:"json",
			        success: function(data){
			        	var map=data.data;//获取到的是一个map
			        	 
				    }
	      });
		}
		</script>
</head>
<body style="background:#ededed;">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">推荐手机联系人</div>
</nav>

 
<p class="tj-title">
	A
</p>
<div class="rm-me-list clearfix">
	<ul>
		<li class="clearfix">
			
			<span class="fr tjcg">推荐成功</span>  
			<span class="rm-me-tx"><img src="/imgmem/tjsjlxr_03.png"></span>
			<div class="rm-me-name">
				<b>曼陀罗</b>
				<p>13512345678</p>
			</div>
		</li>
		<li class="clearfix">
			<input type="button" value="推荐" class="input-tj" />  
			<span class="rm-me-tx"><img src="/imgmem/tjsjlxr_03.png"></span>
			<div class="rm-me-name">
				<b>曼陀罗</b>
				<p>13512345678</p>
			</div>
		</li>
		<li class="clearfix">
			<span class="input-bktj">已注册不可推荐</span>
			<span class="rm-me-tx"><img src="/imgmem/tjsjlxr_03.png"></span>
			<div class="rm-me-name">
				<b>曼陀罗</b>
				<p>13512345678</p>
			</div>
		</li>
		<li class="clearfix">
			<input type="button" value="推荐中..." class="input-tjz" />
			<span class="rm-me-tx"><img src="/imgmem/tjsjlxr_03.png"></span>
			<div class="rm-me-name">
				<b>曼陀罗</b>
				<p>13512345678</p>
			</div>
		</li>
	</ul>
</div>

<div class="tjsj-forms">
	<div class="close tjsj-close">×</div>
	<div class="tjsj-forms-div">
		<img src="/imgmem/sjtjlxr_bj_07.png">
		<p>推荐短信已发送正在等待好友注册...继续推荐其他朋友吧</p>
	</div>
</div>

</body>
</html>
