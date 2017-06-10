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
 		<style type="text/css">
		.radioclass{
			opacity: 1;
		}
		</style>
</head>
<body style="background:#ededed;">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">推荐好友注册</div>
</nav>


<div class="tjhy-top">
	<i class="tjhy-phone"></i>
	<input type="text" placeholder="输入手机号" id = "phone" maxlength="11"
		onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
	<a class="fr tj-anniu"   onclick="tuijian();"><i></i>推荐</a>
</div>

<div class="tjhy-div">
	<p class="tjhy-p">推荐语设置</p>
	<textarea placeholder="一句话告诉朋友推荐理由吧！" id= "content"></textarea>
</div>

<div class="tjhy-div">
	<p class="tjhy-p">默认推荐语</p>
	<ul>
		<li>
			<span class="pay_list_c1 on fl">
				<input type="radio" name="paylist" value="1" class="radioclass" onclick="tuiguang()">
			</span>
			<div>
				<span id = "rad1">我发现一个超级省钱的应用，在实体店消费时只要报上手机号码就能省钱，赶紧注册吧！</span>
			</div>
		</li>
		<li>
			<span class="pay_list_c1 fl">
				<input type="radio"  name="paylist" value="2" class="radioclass" onclick="tuiguang()">
			</span>
			<div>
				<span id = "rad2">哇！这个月的零花钱超级丰盛，只要推荐给好友注册就能获得奖励。你也赶紧来试试吧！</span>
			</div>
		</li>
	</ul>
</div>
</body>
<script type="text/javascript" src="js/htmlmember/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="js/htmlmember/main.js"></script>
<script src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
	function tuijian(){
		var phone = $("#phone").val().trim();
		var content = $("#content").val();
		if(phone == ""){
			alert("手机号码不能为空");
			return;
		}
		$.ajax({
				type:"post",
				url:'<%=basePath%>html_member/ajaxTuiJian.do',
				data:{
 					be_phone:phone,
					content:content
				},
				success:function(data){
   					if(data.result == "1"){
						alert("推荐等待确认中！");
 	 				}else{
	 					alert(data.message);
	 				}
			}
		});
	}
	//推荐的信息
	function tuiguang(){
		var tjy = $('input:radio:checked').val();
		var rad1 = $("#rad1").text();
		var rad2 = $("#rad2").text();
		if(tjy == 1){
			 $("#content").val(rad1);
		}else if(tjy == 2){
			 $("#content").val(rad2);
		}
	}
</script>
</html>
