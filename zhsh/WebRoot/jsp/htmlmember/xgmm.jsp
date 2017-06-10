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
	<div style="text-align:center;line-height:40px;color:#fff">修改密码</div>
</nav>

<ul class="register-list">
	<li>
		<span class="xgyhm-title">当前密码：</span>
		<input onchange="changeAttr(this)" oninput="mmchange(this)" onblur="minlength(this)" type="text" class="land-phone" maxlength="16"  id ="oldpassword" style="text-align:left;padding-left:80px; width:70%"  />
 	</li>
	<li>
		<span class="xgyhm-title">新密码：</span>
		<input onchange="changeAttr(this)" oninput="mmchange(this)" onblur="minlength(this)" class="land-phone" type="text" maxlength="16" id="xinpass" style="text-align:left;padding-left:80px; width:70%" />
	</li>
	<li>
		<span class="xgyhm-title">确认密码：</span>
		<input onchange="changeAttr(this)" oninput="mmchange(this)" onblur="minlength(this)" class="land-phone" type="text" maxlength="16" id ="quepass" style="text-align:left;padding-left:80px; width:70%" />
	</li>
	<li><a style="width:60%;color:#fff" class="Login-button" onclick="update()">确认提交</a></li>
</ul>
<script type="text/javascript">
//改变样式
function changeAttr($this){
	$this.setAttribute("type", "password");
}

function mmchange(ele){
	var zzcs=new RegExp(/[\W]/g);    /*字母和数字*/
	var word= $(ele).value
	if(zzcs.test(word)){
		$(ele).value="";
		alert("密码只能有数字和字母组成")
	}
}
function minlength(ele){
	var word= $(ele).value
	if (word.length<8) {
		$(ele).value="";
		alert("长度至少8位")
	};
}

//修改密码
	function update(){
		var nowPassword = "${pg.password}";
		var xinpass = $("#oldpassword").val().trim();
		if(nowPassword != xinpass){
			alert("当前密码错误！");
			return;
		}
 		var xinpass = $("#xinpass").val().trim();
   		var quepass = $("#quepass").val().trim();
    	if(xinpass == ""){
   			alert("请输入新密码！");
   			return ;
   		}
   		if(quepass == ""){
   			alert("请确认密码！");
   			return ;
   		}
   		if(xinpass != quepass){
   			alert("两次密码输入不一致！");
   			return ;
   		} 
   		$.ajax({
				type:"post",
				url:'<%=basePath%>html_me/updPass.do',
				data:{
					member_id:"${pd.member_id}",
					password:quepass
				},
				success:function(data){
   					if(data.result == "00"){
						alert("修改失败，请联系管理员！");
	 				}else if(data.result == "01"){
	 					window.location.href="<%=basePath%>html_me/goLand.do";
	 				}
			}
		});
	}
	
</script>
</body>
  </html>

