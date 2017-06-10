<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>九鱼销链</title>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<link rel="shortcut icon" href="favicon.ico" >
    <link rel="Bookmark" href="favicon.ico">
   	<link rel="icon" type="image/gif" href="animated_favicon1.gif" >
	<link rel="stylesheet" href="css/zhihui/normalize.min.css">
	<link rel="stylesheet" href="css/zhihui/hsd_load.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框-->
</head>
<body>
	
		<div class="cont">
			<div class="tiaozhuan"><a onclick="denglu();" ><img src="img/ok.png" alt=""></a></div>
			<form action="login_login.do" method="post" name="loginForm" id="loginForm" onsubmit="return check();">
				<div class="load">
					<div class="load_box">
						<ul>
							<li>
								<span>用户名：</span>
								<input type="text" name="loginname" id="loginname" value="${loginname }" placeholder="请输入用户名">
							</li>
							<li>
								<span>密&nbsp;&nbsp;&nbsp;&nbsp;码：</span>
								<input type="password" name="password" id="password" placeholder="请输入密码" value="${password }">
							</li>
							<li>
								<span>验证码：</span>
								<input type="text" class="yzm" name="code" id="code" class="login_code">
								<img src="" alt="" class="yzm_img" id="codeImg">
							</li>
						</ul>
					</div>
	 			</div>
			</form>
			<span class="inf">[浙] ICP备16025718号-2  本站发布所有内容，未经许可，不得转载
		</div>
	
<script type="text/javascript">
		var errInfo = "${errInfo}";
		$(document).ready(function(){
			changeCode();
			$("#codeImg").bind("click",changeCode);
			if(errInfo!=""){
				if(errInfo.indexOf("验证码")>-1){
					
					$("#code").tips({
						side:1,
			            msg:errInfo,
			            bg:'#FF5080',
			            time:5
			        });
					
					$("#code").focus();
				}else{
					$("#loginname").tips({
						side:1,
			            msg:errInfo,
			            bg:'#FF5080',
			            time:5
			        });
				}
			}
			$("#loginname").focus();
		});
		
		$(document).keyup(function(event){
			  if(event.keyCode ==13){
				  denglu();
			  }
		 });
	
		function genTimestamp(){
			var time = new Date();
			return time.getTime();
		}
		//更换验证码
		function changeCode(){
			$("#codeImg").attr("src","code.do?t="+genTimestamp());
		}
		
		
		//登录
		function check(){
 			if($("#loginname").val()==""){
 				$("#loginname").tips({
					side:2,
		            msg:'用户名不得为空',
		            bg:'#AE81FF',
		            time:3
		        });
				
				$("#loginname").focus();
				return false;
			}else{
				$("#loginname").val(jQuery.trim($('#loginname').val()));
			}
			
			if($("#password").val()==""){

				$("#password").tips({
					side:2,
		            msg:'密码不得为空',
		            bg:'#AE81FF',
		            time:3
		        });
				
				$("#password").focus();
				return false;
			}
			if($("#code").val()==""){

				$("#code").tips({
					side:1,
		            msg:'验证码不得为空',
		            bg:'#AE81FF',
		            time:3
		        });

				$("#code").focus();
				return false;
			}
 			return true;
		}
		
		//登录
		function denglu(){
			$("#loginForm").submit();
		}
		
		$(document).keyup(function(event){
			  if(event.keyCode ==13){
			    $("#to-recover").trigger("click");
			  }
		});
		
		</script>		
		<script>
		//TOCMAT重启之后 点击左侧列表跳转登录首页 
		if (window != top) {
			top.location.href = location.href; 
		}
	  </script>

</body>
</html>