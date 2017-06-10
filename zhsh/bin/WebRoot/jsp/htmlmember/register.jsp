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
		<style type="text/css">
		body{
			font-family : 微软雅黑,宋体;
 		}
		.land-phone, .land-yzm {
 		    width: 73%;
 		    background: #BDE2ED;
 		}
		.wspen{
			display:inline-block;;
			width:17%;
			color:#000000;
			margin-left: 5%;
			font-size: 15px;
		}
		.wli{
		    border-bottom: 1px solid #c7c2c2;
		}
 		</style>
</head>
<body style="background: #fff;">
 <nav class="top">
	<!-- <a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a> -->
	<div style="text-align:center;line-height:40px;color:#fff">注册</div>
</nav>
<div style=" position: fixed;top: -48px;width: 100%; ">
   <img src="img/zhuce.png" style="width:100%;"/>
</div>
<div style=" display: block; position: fixed; top: 41px; width: 100%; "> 
	<ul class="land-list">
		<li class="wli">
			<span class="wspen">&nbsp;&nbsp;账号</span>
			<input style="font-size: 20px;text-align: left;" onchange="getTuiJianList(this)" class="land-phone" type="text" id="be_phone" placeholder="请输入手机号码" maxlength="11"   onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
		</li>
		<li class="wli">
			<span class="wspen">推荐人</span>
			<select class="land-phone" id="recommended" style="font-size: 19px;font-weight: bold;">
				<option value="0@0">不填写，直接注册</option>
			</select>
		</li>
		<li style="margin-top: 8%;" ><a onclick="goNextZhuCe()" style="background: #ece9e1;color:#ffffff;font-size: 18px;border-radius: 0px;text-align: center;" class="Login-button" >选好了，下一步&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
	</ul>
</div>
 <script type="text/javascript">
		var str="";
		var n=0;
		if("${pd.recommended}" != null && "${pd.recommended}" != ""){
				str=("<option value='${pd.recommended}@${pd.recommended_type}'>${pd.recommended_phone}</option>");
				$("#recommended").find("option").remove();
				$("#recommended").append(str);
				$("#recommended").append("<option value='0@0'>不填写，直接注册</option>");
				n=1;
		}
		//获取推荐人列表
		function getTuiJianList(obj){
			if($(obj).val() == ""){
				return;
			}
 			var value=$(obj).val();
 			if(n==0){
 				$.ajax({
 			        type:"post",
 			        url:'<%=basePath%>app_member/getTuijianPhone.do', 
 			  	 	data:{"be_phone":value},                
 			        dataType:"json",
 			        success: function(data){
 	 		        	//判断是否通过推荐链接进来de
 			        	$("#recommended").find("option").remove();
  	  		       	 	if(data.result == "1"){
 			       	 		 var list=data.data;
 			       	 		 if(list.length == 0){
 			       	 			$("#recommended").append("<option value='0@0'>不填写，直接注册</option>");
 			       	 		 }else{
	 			       	 		for(var i=0 ;i<list.length ; i++){
	 			       	 		 	$("#recommended").append("<option value='"+list[i].id+"@"+list[i].type+"'>"+list[i].phone+"</option>");
	 			       	 		 }
	 			       	 		$("#recommended").append("<option value='0@0'>不填写，直接注册</option>");
 			       	 		 }
  	 		       	 	}
 			         }
 			    });
 			}
 			$(".Login-button").css("background","#ff0600");
 		}
		
		//下一步
		function goNextZhuCe(){
			if($("#be_phone").val()== ""){
				alert("手机号码不能为空");
				return;
			}
			var strs= new Array(); //定义一数组 
			//推荐人的id以及类型
			strs=$("#recommended").val().split("@");;
 			window.location.href="html_member/gouZhuceNext.do?recommended="+strs[0]+"&recommended_type="+strs[1]+"&phone="+$("#be_phone").val();
		}
</script>
</body>
</html>
