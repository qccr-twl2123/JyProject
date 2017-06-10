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
	<div style="text-align:center;line-height:40px;color:#fff">换绑手机号</div>
</nav>

<ul class="register-list">
	<li>
		<input class="land-phone" type="text" id = "phone" style="text-align:left;padding-left:10px;" placeholder="${pg.ph}" maxlength="11"
		onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
		<input type="button" value="立即验证" class="register-anniu code" onclick="code()">
	</li>
	<li>
		<input class="land-phone" type="text"  style="text-align:left;padding-left:10px;"  placeholder="请输入短信中的验证码" id="code"/>
	</li>
	<li><input type="button" style="width:40%;" class="Login-button" value="验证" onclick="updPhone()"/></li>
</ul>
</body>
 <script type="text/javascript">
var validCode=true;
 	function code(){
		var phone = $("#phone").val().trim();
		if(phone == ""){
			alert("手机号码不能为空");
			return;
		}
		if (validCode) {
			$.ajax({
				type:"post",
				url:"<%=basePath%>app_member/getCode.do",
				data:{
					phone:phone,
					type:"6",
					member_id:"${pd.member_id}",
					user_type:"2"
					},
				success:function(data){
					if(data.result == "1"){
						window.newCode=data.data;
			       	 	var time=60;
		       			var code=$(".code");
		       			validCode=false;
	       				code.addClass("msgs1");
	       				code.attr("onclick","");
	       				var t=setInterval(function() {
		       				time--;
		       				code.val(time+"秒");
		       				if (time==0) {
			       					clearInterval(t);
			       					code.val("重新验证");
			       					validCode=true;
			       					code.removeClass("msgs1");
			       					code.attr("onclick","code()");
			       				}
		       			},1000);
					}else{
						
					}
 				}
			});
		}
  	}
	//验证
	function updPhone(){
		var phone = $("#phone").val().trim();
		var code = $("#code").val().trim();
		if(newCode != code){
			alert("验证码错误！！");
			return;
		}
		$.ajax({
				type:"post",
				url:"<%=basePath%>html_me/updPhone.do",
				data:{
					member_id:"${pd.member_id}",
					phone:phone,
					code:code
					},
				success:function(data){
   					if(data.result == "00"){
						alert("当前手机已注册！");
  					}else if(data.result == "02"){
						alert("修改时失败，请联系管理员！");
					}else if(data.result == "01"){
						alert("修改成功！");
					}else if(data.result == "03"){
						alert("验证码不正确！");
					}
			}
		});
	}
</script>
</html>
