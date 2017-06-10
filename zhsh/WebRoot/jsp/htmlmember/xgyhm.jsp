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
</head>
<body style="background:#fff;">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">注修改用户名</div>
</nav>

<ul class="register-list">
	<li>
		<span class="xgyhm-title">用户名</span>
		<input class="land-phone" type="text" id = "name" style="text-align:left;padding-left:60px; width:80%"  value="${pg.name }" maxlength="8" />
	</li>
</ul>
<p style="line-height:30px;text-align:center;color:#8a8a8a">以英文字母或汉字开头，限4-16个字符，一个汉字为2个字符</p>
<input type="button" style="width:40%;" class="Login-button" value="确定" onclick="update()"/>
<script type="text/javascript">
	function update(){
		var name = $("#name").val().trim();
		if(name == 0 || name == ""){
			alert("请输入有效数据！");
			return ;
		}
	 	var reg = /^[a-zA-Z\xa0-\xff_][0-9a-zA-Z\xa0-\xff_]{3,15}$/;
	 	if(name.match(reg)){
	 		alert("以英文字母或汉字开头，限4-16个字符");
	 		return;
	 	}else{
	 		$.ajax({
				type:"post",
				url:"<%=basePath%>html_me/updName.do",
				dataType:"json",
					data:{
							member_id:"${pd.member_id}",
							name:name
						},
					success:function(data){
						if(data.result == "1"){
							alert("修改成功");
							window.location.href='<%=basePath%>html_me/goMe.do?member_id='+"${pd.member_id}";
						}
	 				}
		  }); 
	 	}
 		
	}
	
</script>
</body>
 </html>


