<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
	<title>会员注册</title>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<link rel="stylesheet" href="css/htmlmember/normalize.min.css">
	<link rel="stylesheet" href="css/htmlmember/hyzc.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<style>
		.btn-zhuce{
			margin: 0 0 8px 0;
			line-height: 2;
		}
	</style>
</head>
<body>
	<div class="view">
		<div class="input-view">
			<div class="input-box">
				<input type="text" placeholder="请输入手机号"  maxlength='11' id="be_phone"  onchange="getTuiJianList(this)"  onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" >
				<select class="land-phone" id="recommended"  >
					<option value="0@0">不填写，直接注册</option>
				</select>
				<input type="text" placeholder="图片验证码"  maxlength='4' id="txCode"  oninput="changeButton(this,'code',4)">
				 <img  id="imgObj" alt="点击更换" src="verifyCodeServlet" onclick="changeImg()"/>
				<input type="text"  placeholder="验证码"  maxlength='4' id="dxCode"  >
				<div class="btn-huoqu code" onclick="getDxCode()">
					获取验证码
				</div>
				<input type="password"  placeholder="登录密码" id="password" maxlength="16"   oninput="changeButton(this,'btn-zhuce',8)">
			</div>
				<div class="btn-zhuce" onclick="codeflag()">
					免费注册九鱼网会员
				</div>
				<div class="btn-zhuce" style="background:rgb(255,78,78);box-shadow:0 0.1rem rgb(208,8,8);color:#fff;" onclick="goLogin()">
					已有帐号 立即登录
				</div>
 		</div>
	</div>
	<script type="text/javascript">
					function goLogin(){
						window.location.href="<%=basePath%>html_member/toLogin.do";
					}
 	
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
		          		//更改按钮的颜色
		          		function changeButton(obj,bq_class,min_length){
		          			if($(obj).val() != null && $(obj).val().length >= min_length){
		          				$("."+bq_class).css("background"," #ffa31b");
		          			}
 		          		}
	</script>
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
 			        url:'<%=basePath%>html_member/getTuijianPhone.do', 
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
  		}
		
	var validCode=true;
	// getDxCode();
	//获取验证码
	function getDxCode(){
		if($("#be_phone").val() == null || $("#be_phone").val() == "" ){
			alert("手机号码不能为空");
			return;
		}else if($("#txCode")[0].value== "" ){
			alert("图形验证码不能为空");
			return;
		}
 		
 		if (validCode) {
			$.ajax({
		        type:"post",
		        url:'<%=basePath%>html_member/findPhoneByZhuCe.do', 
		  	 	data:{"phone":$("#be_phone").val() , "tuxingcode":$("#txCode").val()},                
		        dataType:"json",
		        success: function(data){
		        	 if(data.result == "1"){
  				       	 		var time=60;
				       			var code=$(".code");
 			       				validCode=false;
			       				code.addClass("msgs1");
			       				code.attr("onclick","");
			       				var t=setInterval(function() {
				       				time--;
				       				code.html(time+"秒");
				       				if (time==0) {
				       					clearInterval(t);
				       					code.html("重新获取");
				       					validCode=true;
				       					code.removeClass("msgs1");
				       					code.attr("onclick","getDxCode()");
		 		       				}
				       			},1000);
 			       	 }else{
 			       		 alert(data.message);
 			       	 }
		         }
		    });
		}
 	}
	
	//完成注册
	function sureSaveMember(){
		var word=$("#password").val()
		var zzcs=new RegExp(/[\W]/g);    /*字母和数字*/
		if(word == ""){
				alert("密码不能为空");
				return;
			}else if(word.length  < 8){
					alert("长度至少8位");
					return;
			}else if(zzcs.test(word)){
				alert("密码只能有数字和字母组成");
			}
			
	  		var strs= new Array(); //定义一数组 
			//推荐人的id以及类型
			strs=$("#recommended").val().split("@"); 
			$.ajax({
		        type:"post",
		        url:'<%=basePath%>html_member/register.do?', 
		  	 	data:{
		  	 		"phone":$("#be_phone").val(),
		  	 		"recommended":strs[0],
		  	 		"recommended_type":strs[1],
		  	 		"password":$("#password").val(),
		  	 		"wxopen_id":"${wxopen_id}", 
		  	 		"zhuce_shebei":"3"
	 	  	 	},                
		        dataType:"json",
		        success: function(data){
		        	 if(data.result == "1"){
		        		 window.location.href="<%=basePath%>html_member/toLogin.do";	 
				      }else{
			       		 alert(data.message);
			       	 }
		         }
		    });
 	}
  	
	//判断验证码是否正确
	function codeflag(){
		$.ajax({
	        type:"post",
	        url:'<%=basePath%>html_member/IsOKMessageCode.do', 
	  	 	data:{
	  	 		"messagecode":$("#dxCode").val(),
 	  	 		"codetype":"1"
 	  	 	},    
 	  	    async: false,
	        dataType:"json",
	        success: function(data){
	        	 if(data.result == "1"){
	        		 sureSaveMember();
			      }else{
		       		 alert(data.message);
		       		 return;
		       	 }
	         }
	    });
 		 
  	}
	
	
</script>
</body>
</html>