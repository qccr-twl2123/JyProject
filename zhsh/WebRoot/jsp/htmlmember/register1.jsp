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
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="css/htmlmember/style.css">
		<link rel="stylesheet" href="css/htmlmember/styles.css" type="text/css">
		<script src="js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9"></script>
</head>
<body style="background:#fff;">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">注册</div>
</nav>

<ul class="register-list">
 	<li>
		<span class="signin_body_right_d2_img23" >
	             <img  id="imgObj" alt="点击更换" src="verifyCodeServlet" onclick="changeImg()"/>
	    </span>
		<input class="land-phone" type="text" id="txCode" style="text-align:left;padding-left:10px;"  placeholder="图形验证码"/>
  	</li>
	 <script type="text/javascript">
	 	          	//有背景干扰验证码
		          	 function changeImg(){  
		          		  var imgSrc = $("#imgObj");  
		          		  var src = imgSrc.attr("src");  
		          		  imgSrc.attr("src",chgUrl(src));  
		          		}  
		          		//时间戳  
		          		//为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳  
		          		function chgUrl(url){  
		          		  var timestamp = (new Date()).valueOf();  
		          		  url = url.substring(0,17);  
		          		  if((url.indexOf("&")>=0)){  
		          		    url = url + "×tamp=" + timestamp;  
		          		  }else{  
		          		    url = url + "?timestamp=" + timestamp;  
		          		  }  
		          		  return url;  
		          		}  
	              </script>
	<li>
		<input class="land-phone" type="password"  id="password" style="text-align:left;padding-left:10px;"  placeholder="密码"/>
	</li>
	<li>
		<span style="font-size:18px;">当前手机号手机 ${pd.phone}</span>
	</li>
	<li>
		<input class="land-phone" type="text" id="dxCode" style="text-align:left;padding-left:10px;"  placeholder="手机验证码"/>
		<input type="button" value="获取验证码" class="register-anniu code"  onclick="getDxCode()">
 	</li>
 	<li><a onclick="sureSaveMember()"><input type="button" style="width:60%;" class="Login-button" value="完成注册" /></a></li>
	<!-- <li>未收到验证码？拨打<span style="color:#3492e9">免费热线</span>快速获取</li> -->
</ul>
<script type="text/javascript">
	
	var validCode=true;
	// getDxCode();
	//获取验证码
	function getDxCode(){
		if($("#txCode").val() == "" ){
			alert("图形验证码不能为空");
			return;
		}
		if($("#password").val() == ""){
			alert("密码不能为空");
			return;
		}
		if("${pd.phone}" == null || "${pd.phone}" == "" ){
			alert("手机号码不能为空");
			return;
		}
		if (validCode) {
			$.ajax({
		        type:"post",
		        url:'<%=basePath%>app_member/getCode.do', 
		  	 	data:{"phone":"${pd.phone}","type":"1","user_type":"2","tuxingcode":$("#txCode").val()},                
		        dataType:"json",
		        success: function(data){
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
				       					code.val("重新获取");
				       					validCode=true;
				       					code.removeClass("msgs1");
				       					code.attr("onclick","getDxCode()");
		 		       				}
				       			},1000);
 			       	 }else{
 			       		 $("#dxCode").attr("placeholder",data.message);
			       	 }
		         }
		    });
		}
 	}
  	
	//完成注册
	function sureSaveMember(){
 		if($("#password").val() == ""){
			alert("密码不能为空");
			return;
		}
		if(newCode != $("#dxCode").val()){
			alert("验证码错误");
			return;
		}
		$.ajax({
	        type:"post",
	        url:'<%=basePath%>app_member/register.do?', 
	  	 	data:{
	  	 		"phone":"${pd.phone}",
	  	 		"recommended":"${pd.recommended}",
	  	 		"recommended_type":"${pd.recommended_type}",
	  	 		"password":$("#password").val()
 	  	 	},                
	        dataType:"json",
	        success: function(data){
	        	 if(data.result == "1"){
	        		 window.location.href="html_member/toLogin.do";	 
			      }else{
		       		 alert(data.message);
		       	 }
	         }
	    });
 	}
	
	
	
	
</script>
</body>
</html>
