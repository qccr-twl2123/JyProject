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
	<style type="text/css">
	/*check-code*/
	.checkcode canvas{
		float: right;
		width: 85px;
		height: 36px;
		padding: 3px;
	}
	</style>
	<script type="text/javascript">
	 		var base_inf={
 	            base_herf:"<%=basePath%>" 
 	        };
	</script>
<!-- 	<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&s=1"></script>
 --></head>
<body style="background:#fff;">
	<div class="readyjsp" style="width: 100%;margin: auto;position: absolute;top: 40%; display:none; ">
			<img alt="" src="img/readyjsp.gif"  style=" width: 100%; ">
	</div>
	<div class="onediv" style="display:none">
		<nav class="top">
 			<a href="<%=basePath%>html_member/goRegister.do" class="fr" style="margin-right:5px;">注册</a>  
			<!-- <a href="#"><b class="back-arrow fl"></b></a> -->
			<div style="text-align:center;line-height:40px;color:#fff">登陆</div>
		</nav>
		<div class="land-tx clearfix">
				<i class="land-tx-img"><img src="https://www.jiuyuvip.com/FileSave/File/userFile/moren.png" id="member_image"></i>
 		</div>
		<ul class="land-list">
			<li><input class="land-phone" type="text"  id="phone" placeholder="手机号码" onchange="getImage(this.value)"/></li>
			<li><input class="land-phone" type="password" id="password" placeholder="登陆密码"/></li>
			<li class="checkcode">
				<input class="land-yzm" type="text" id="getCode" placeholder="验证码"  maxlength="4"/>
				<span class="land-yzm-number" >
					<canvas class="J_codeimg" id="myCanvas" onclick="createCode()">
 					</canvas>
 				</span>
			</li>
			<li><input type="button" class="Login-button" style="background-color: #ff0600;" value="登陆"  onclick="submitLogin()"/></li>
			<li><input style="background-color: #66BAFC;" type="button" class="Login-button" value="暂不登陆，先看看"  onclick="goShouYe()"/></li>
			<li><a href="<%=basePath%>jsp/htmlmember/wjmm.jsp" class="fl">忘记密码</a><span class="fr"><a href="<%=basePath%>jsp/htmlmember/dxdl.jsp" >短信快捷登陆</a></span></li>
		</ul>
	</div>
		<script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
		<script src="<%=basePath%>js/wx/jweixin-1.0.0.js"></script>
 		<script src="<%=basePath%>js/wx/zepto.min.js"></script>
 		<script src="<%=basePath%>js/htmlmember/weixindemo.js"></script>
 		<script type="text/javascript">
  		     //微信定位
  		    wxdingwei();
 			createCode();//验证码
   	    	//gogogo2();
  	    	//gogogo1();
			"${pd}"
  	       //普通定位定位开始
    		function gogogo1(){
    			$.ajax({
    	         	type:"post",
    	         	url:"https://api.map.baidu.com/location/ip?ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&coor=bd09ll", 
    		        dataType:"jsonp",
    		        success: function(data){
     		        	var content=data.content;
     		        	var address_detail=content.address_detail;
     		        	window.province_name=address_detail.province;
     		        	window.city_name=address_detail.city;
     		        	window.area_name=address_detail.district;
     		        	window.street=address_detail.street;
     		        	window.street_number=address_detail.street_number;
     		        	window.point=content.point;
     		        	window.lng=point.x;
     		        	window.lat=point.y;
     		        	if("${pd.ok}" == "1"){
   		        			window.location.href="<%=basePath%>html_member/gouShouYe.do?province_name="+province_name+"&area_name="+area_name+"&city_name="+city_name+"&address="+city_name+area_name+street+"&longitude="+lng+"&lat="+latitude;	 
   		        		}else{
   		        			$(".onediv").show();
   		        			$(".readyjsp").hide();
   		        		}
     		        }
    		   });
    		}
  	       
 			
  	        //精确定位定位开始
    		function gogogo2(){
     			$.ajax({
 		         	type:"post",
 		         	url:"https://api.map.baidu.com/highacciploc/v1?qcip=&callback_type=jsonp&qterm=pc&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&coord=bd09ll", 
 			        dataType:"jsonp",
 			        success: function(data){
 	 		        	var result=data.result;
 	 		        	var content=data.content;
 	 		        	if(result.error == 161){
 	 		        		window.lng=content.location.lng;
	   		        		window.lat=content.location.lat;
	   		        	 	console.log(content.location.lat+","+content.location.lng);
 	 		        		pcd(content.location.lng,content.location.lat);
 	 		        	}
 	 		        }
 			   });
    		}
 	    	//精确定位--->获取省市区
 	    	function pcd(lng,lat){
 	    		$.ajax({
 	   	         	type:"post",
 	   	         	url:"https://api.map.baidu.com/geocoder/v2/?ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&callback=renderReverse&location="+lat+","+lng+"&output=json&pois=1", 
 	   		        dataType:"jsonp",
 	   		        success: function(data){
 	   		        	if(data.status == "0"){
 		   		        	var result=data.result;
	 		   		        window.addressComponent=result.addressComponent;
	   		        		window.province_name=addressComponent.province;
	   		        		window.city_name=addressComponent.city;
	   		        		window.area_name=addressComponent.district;
	   		        		window.street=addressComponent.street;
 	 	   		        	if("${pd.ok}" == "1"){
	   		        			window.location.href="<%=basePath%>html_member/gouShouYe.do?province_name="+province_name+"&area_name="+area_name+"&city_name="+city_name+"&address="+city_name+area_name+street+"&longitude="+lng+"&latitude="+lat;	 
	   		        		}else{
	   		        			$(".onediv").show();
	   		        			$(".readyjsp").hide();
 	   		        		}
 	 	   		        }
 	   		        }
 	   		   });
 	    	}
 	    	
 			 
 	    	
 	    	
 	    	
  		/*-----------------------------------------------------------------------------
		* @Description: 验证码 
		* @author: 	xuyihong(xuyihong@163.com)
		* @date		2015.09.24
		* ---------------------------------------------------------------------------*/
		function showCheck(a){/* 显示验证码图片 */
			var c = document.getElementById("myCanvas");
		  	var ctx = c.getContext("2d");
			ctx.clearRect(0,0,1000,1000);
			ctx.font = "80px Arial";
			ctx.fillText(a,0,100);
		}

		var code ; //在全局 定义验证码      
		function createCode(){       
		    code = "";      
		    var codeLength = 4;//验证码的长度
		    var selectChar = new Array(1,2,3,4,5,6,7,8,9,'a','b','c','d','e','f','g','h','j','k','l','m','n','p','q','r','s','t','u','v','w','x','y','z'/* ,'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z' */);      
		    for(var i=0;i<codeLength;i++) {
		       var charIndex = Math.floor(Math.random()*60);      
		       code +=selectChar[charIndex];
		    }      
		    if(code.length != codeLength){      
		      createCode();      
		    }
		    showCheck(code);
		}
		          

		function validate () {
		    var inputCode = document.getElementById("J_codetext").value.toUpperCase();
		    var codeToUp=code.toUpperCase();
		    if(inputCode.length <=0) {
		      document.getElementById("J_codetext").setAttribute("placeholder","Type Here");
		      createCode();
		      return false;
		    }
		    else if(inputCode != codeToUp ){
		      document.getElementById("J_codetext").value="";
		      document.getElementById("J_codetext").setAttribute("placeholder","Error");
		      createCode();
		      return false;
		    }
		    else {
		      window.open(document.getElementById("J_down").getAttribute("data-link"));
		      document.getElementById("J_codetext").value="";
		      createCode();
		      return true;
		    }

		}

		// download
		$(document).ready(function(){
		  $(".J_download").bind("click",function(){
		    $(".J_before").hide(40);
		    $(".J_after").show(200);
		    createCode();
		  });
		  $(".btn-no").bind("click",function(){
		    $(".J_after").hide(40);
		    $(".J_before").show(200);
		  });
		})
		//为确定按钮添加回车事件
		// document.onkeydown=function(event){
//		     var e = event || window.event || arguments.callee.caller.arguments[0];
//		     if(e && e.keyCode==13){ // enter 键
//		         validate();
//		     }
		// }; 
 		 
		//获取头像
		 function getImage(value){
			 $.ajax({
                 type:"post",
                 url:'<%=basePath%>app_member/imgae_urlByPhone.do', 
           	 	 data:{"phone":value },                
                 dataType:"json",
                 success: function(data){
                	 	if(data.result == "1"){
                	 		var image_url=data.image_url;
                	 		$("#member_image").attr("src",image_url);
                	 	}
                  }
             });
		 }
		
		//登陆验证
		function submitLogin(){
			//alert(province_name+"@"+city_name+"@"+area_name+"@"+street+"@"+lng+"@"+lat);
 			if($("#phone").val() == ""){
				alert("电话不能为空");
				return;
			}
			if($("#password").val() == ""){
				alert("密码不能为空");
				return;
			}
			if($("#getCode").val() == ""){
				alert("验证码不能为空");
				return;
			}else{
				if(code != $("#getCode").val()){
	 				$("#getCode").attr("placeholder","验证码错误");
					$("#getCode").val("");
 					return;
				}
			}
			//验证提交
  			$.ajax({
                type:"post",
                url:'<%=basePath%>app_member/login.do', 
          	 	 data:{"phone":$("#phone").val() ,"password":$("#password").val()},                
                dataType:"json",
                success: function(data){
               	 	if(data.result == "1"){
               	 		window.location.href="<%=basePath%>html_member/gouShouYe.do?is_first=0&province_name="+province_name+"&area_name="+area_name+"&city_name="+city_name+"&address="+city_name+area_name+street+"&longitude="+lng+"&latitude="+lat+"&member_id="+data.data.member_id;	 
               	 	}else{
               	 		alert(data.message);
                	}
                 }
            });
		}
		
		//暂不登录前往首页
		function goShouYe(){
			if(province_name != null ){
				window.location.href="<%=basePath%>html_member/gouShouYe.do?province_name="+province_name+"&area_name="+area_name+"&city_name="+city_name+"&address="+city_name+area_name+street+"&longitude="+lng+"&latitude="+lat;	 
 			}
		}
		
  	</script>
</body>
 </html>
