<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" type="text/css" href="css/htmlmember/style.css">
<link rel="stylesheet" href="css/htmlmember/styles.css" type="text/css">
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9"></script>
<script src="js/jquery-1.8.0.min.js"></script>
</head>
<body style="background:#fff;">
<nav class="top">
	<a href="html_member/gouhtmlLogin.do"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">忘记密码</div>
</nav>
 <ul class="register-list">
	<li>
		<input class="land-phone" type="text"  id="phone" style="text-align:left;padding-left:10px;"  placeholder="请输入手机号" maxlength="11"   onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
		<input type="button" value="重新获取" class="register-anniu code" onclick="getDxCode()">
	</li>
	<li>
		<input class="land-phone" type="text" id="dxCode" style="text-align:left;padding-left:10px;"  placeholder="请输入短信中的验证码"/>
	</li>
	<li><a onclick="sureLogin()"><input type="button" style="width:60%;" class="Login-button" value="确定" /></a></li>
</ul>
 <script type="text/javascript">
 $(function(){
 		var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(r){
		   		if(this.getStatus() == BMAP_STATUS_SUCCESS){
		   			window.lng=r.point.lng;
		   			window.lat=r.point.lat;
	 	   			$.ajax({
		   	         	type:"post",
		   	         	url:"http://api.map.baidu.com/geocoder/v2/?ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&callback=renderReverse&location="+lat+","+lng+"&output=json&pois=1", 
		   		        dataType:"jsonp",
		   		        success: function(data){
		   		        	if(data.status == "0"){
		   		        		var result=data.result;
		   		        		window.addressComponent=result.addressComponent;
		   		        		window.province_name=addressComponent.province;
		   		        		window.city_name=addressComponent.city;
		   		        		window.area_name=addressComponent.district;
		   		        		window.street=addressComponent.street;
			   		        }
		   		        }
		   		   });
	 	   		}else {
		   			alert('failed'+this.getStatus());
		   		}        
		 },{enableHighAccuracy: true})
	});
var validCode=true;
//获取验证码
function getDxCode(){
	if( $("#phone").val() == "" ){
		return;
	}
	if (validCode) {
		$.ajax({
	        type:"post",
	        url:'<%=basePath%>app_member/getCode.do', 
	  	 	data:{"phone":$("#phone").val(),"type":"2","user_type":"2"},                
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
			       			},1000)
			       	 }else{
		       			 alert(data.message);
		       	 	}
	         }
	    });
	}
}

//完成登陆
function sureLogin(){
	if(newCode != $("#dxCode").val()){
		alert("验证码错误");
		return;
	}
	if("" == $("#phone").val()){
		alert("手机号码不能为空");
		return;
	}
	$.ajax({
	        type:"post",
	        url:'<%=basePath%>app_member/SMSlogin.do?', 
	  	 	data:{
	  	 		"phone": $("#phone").val()
		  	},                
	        dataType:"json",
	        success: function(data){
	        	 if(data.result == "1"){
	        		 window.location.href="<%=basePath%>html_member/gouShouYe.do?province_name="+province_name+"&area_name="+area_name+"&city_name="+city_name+"&address="+city_name+area_name+street+"&longitude="+lng+"&latitude="+lat+"&member_id="+data.data.member_id;	 	 
			      }else{
		       		 alert(data.message);
		       	 }
	         }
	 });
}

</script>
</body>
</html>
