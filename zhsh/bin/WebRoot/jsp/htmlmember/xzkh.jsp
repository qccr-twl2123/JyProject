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
		<script type="text/javascript" src="js/htmlmember/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="js/htmlmember/main.js"></script>
</head>
<body style="background:#ededed;">
<nav class="top">
	<a href="<%=basePath%>html_me/gotixian.do?member_id=${pd.member_id}"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">新增卡号</div>
</nav>
<p class="wdjf-text hb-gq-footer">请绑定持卡本人的银行卡</p>

<div class="xzkh-content clearfix">
	<ul>
		<li><input type="text" placeholder="持卡人" id="name"/></li>
		<li><input type="text" placeholder="开户行" id="account"/></li>
		<li><input type="text" placeholder="卡号" id ="kh"
			onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/></li>
		<li><input type="text" placeholder="预留号码" id="phone" maxlength="11"
			onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/></li>
		<li>
			<input type="text" placeholder="输入验证码" id="code"/>
			<a class="blue xzkh-yzm" onclick="code()">发送验证码</a>
		</li>
	</ul>
</div>

	<input class="recharge-sure" type="button" value="确认新增" onclick="save()"/>

</body>
<script src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
    var newcode="";
	function code(){
		var phone = $("#phone").val().trim();
		$.ajax({
				type:"post",
				url:"<%=basePath%>app_member/getCode.do",
				data:{
					phone:phone,
					type:"7"
					},
				success:function(data){
					newcode=data.data;
				}
		});
	}
	
	function save(){
		var kh = $("#kh").val().trim();
		var name = $("#name").val().trim();
		var phone = $("#phone").val().trim();
		var account = $("#account").val().trim();
		var code = $("#code").val().trim();
		if(name == null || name == ""){
			alert("持卡人不能为空！");
			return ;
		}
		if(account == null || account == ""){
			alert("开户行不能为空！");
			return ;
		}
		if(kh == null || kh == ""){
			alert("卡号不能为空!");
			return ;
		}
		if( kh.length> 19 || kh.length < 12 ){
			alert("请输入正确的银行卡号！");
			return ;
		}
		if(phone == null || phone == ""){
			alert("预留手机号不能为空！");
			return ;
		}
		if(code == null || code == ""){
			alert("验证码不能为空！");
			return ;
		}else{
			if(newcode != code){
				alert("验证码错误！");
				$("#code").val("");
 				return ;
			}
		}
		var member_id="${pd.member_id}";
		if(member_id != null && member_id !=''){
			$.ajax({
				type:"post",
				url:"<%=basePath%>html_me/savebank.do",
				data:{
					phone:phone,
					bank_number:kh,
					member_id:member_id,
					account:account,
					member_name:name,
					code:code
					},
				success:function(data){
						if(data.result == "00"){
							alert(data.message);
		 				}else{
		 					window.location.href="<%=basePath%>html_me/gotixian.do?member_id="+member_id;
		 				}
				}
			});
		}
  	}
</script>
</html>
