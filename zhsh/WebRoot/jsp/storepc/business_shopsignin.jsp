<%@page import="java.awt.Color"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="IE=9" >
    <meta name="renderer" content="webkit">
    <title>商家登录</title>
    <meta charset="utf-8">
     <base href="<%=basePath%>">
    <link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
    <link rel="stylesheet" href="css/pcstore/hsd_load.css">
    <link rel="stylesheet" href="css/pcstore/bootstrap.min.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<script src="js/jquery.cookie.js"></script>
</head>
<body onkeydown="BindEnter(event)">
<!--头部-->
    <header>
        <div class="head_cont">
            <a href="<%=basePath%>"><img src="img/storelogo.png" alt="" class="logo"></a>
            <div>•九鱼销链商家中心</div>
        </div>
    </header>
<!--内容-->
 <form action="<%=basePath%>storepc/goXuanBanCiZhouHao.do" id="From" name="Form" method="post"> 
 <input type="hidden" name="type" id="type" value="1"/><!-- 1商家，2操作员 -->
 <input type="hidden" name="store_id" id="store_id" value=""/>
<input type="hidden" name="gologin_id" id="gologin_id" value=""/>
<section>
    <div class="cont">
        <div class="denglu">
            <h6>商家登录</h6>
            <div class="yhm"><!--用户名-->
                <div class="rowborder">
                    <img src="img/denglukuang/user.png" alt="">
                </div>
                <input type="text" placeholder="仅限于ID登陆" name="registertel_phone" id="registertel_phone"  oninput="isStore(this)"  onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
            </div>
            <div class="mm"><!--密码-->
                <div class="rowborder">
                    <img src="img/denglukuang/lock.png" alt="">
                </div>
                <input type="password" placeholder="请输入登录密码" maxlength="16" name="login_password" id="login_password" onclick="changeText()" >
            </div>
            <!-- style="display:none;"  -->
            <div class="txcode" style="display:none;">
	            <div class="yzm" >
	                 <div class="rowborder">
	                    <img src="img/denglukuang/coad.png" alt="">
	                </div>
	                <input type="text" placeholder="验证码" maxlength="6" name="code" id="code" onclick="changeText()">
	             </div>
	              <img class="yzm_img" src="verifyCodeServlet" alt="" id="imgObj" onclick="changeImg()"> 
             </div>
             <div class="zhcz"><!--账号操作-->
                <input type="checkbox" id="saveid">
                <span>记住登录帐号</span>
              	<!--   <a href="">忘记密码？</a> -->
            </div>
            <div class="button" id="login">登录</div>
        </div>
    </div>
</section>
</form>
<!--底部-->
<footer>
    <div class="footcont">
        <ul>
            <li><a href="<%=path %>/storepc/goRegister.do" class="noborder">我要开店</a></li>
            <li><a href="<%=basePath%>jsp/storepc/gyjy.html" target="_blank">关于九鱼</a></li>
            <li><a href="">加入我们</a></li>
            <li><a href="">合作流程</a></li>
            <li><a href="">常见问题</a></li>
        </ul>
    </div>
    <div class="beian">[浙] ICP备16025718号-2 本站发布所有内容，未经许可，不得转载 </div>
</footer>
<script type="text/javascript">
  //使用document.getElementById获取到按钮对象
    	function BindEnter(event){
    		var login = document.getElementById("login");
    		if(event.keyCode == 13){
    			login.click();
    			event.returnValue = false;
    		}
    	}
  
   //改变登录的文本
  	function changeText(){
  		$("#login").html("登录");
  		$("#login").css("background","#15acf2");
   	}
  
   
  	 //判断当前商家是否审核通过
    function isStore(obj){
	 	   var registertel_phone=$("#registertel_phone").val();
	 	   if(registertel_phone.length == 8 || registertel_phone.length == 12 ){
		 		 $.ajax({  
		 	         type : "POST",  
		 	         url : '<%=path%>/storepc/isStore.do',  
		 	         data :{
		 	        	 	registertel_phone:registertel_phone
		  	      	 },  
		 	         dataType:"json",
		 	         success : function(data) {  
		 		 	       	 if(data.result=="0"){
		 		 			     alert(data.message);
		 			         } 
		 	   	 	      	if(data.open_txcode == "1"){
		 		 	        	 $(".txcode").show();
		 		 	        	 changeImg();
		 			        	 $("#code").val("")
		 		 	        }else{
		 		 	        	 $(".txcode").hide();
		 		 	        }
		 		   	 	    $("#login").html("登录");
		 		   	  		$("#login").css("background","#15acf2");
		 	        } 
		 	   	}); 
	 	   }else{
	 		  	$("#login").html("登录");
	  	  		$("#login").css("background","#15acf2");
	 	   }
    }
   
  	 
  
     //登录
    $("#login").click(function(){
    	if($("#registertel_phone").val().trim() == ""){
    		alert("登录账号不能为空");
    		return;
    	}
    	if($("#login_password").val().trim() == ""){
    		alert("密码不能为空");
    		return;
    	}
	    $.ajax({  
	         type : "POST",  
	         url : '<%=path%>/storepc/pcLogin.do',  
	         data :{
	        	 	registertel_phone:$("#registertel_phone").val().trim(),
	        	 	password:$("#login_password").val()
	        	 	,code:$("#code").val()
	         },  
	         dataType:"json",
	         success : function(data) { 
	        	 $("#login").html(data.message);
	 	         if(data.result=="1"){
		 	        	if($("#saveid").attr("checked")){
		 					$.cookie('loginname', $("#registertel_phone").val(), { expires: 7 });
		 					$.cookie('password', $("#login_password").val(), { expires: 7 });
		 				}
	 	        	    $("#type").val(data.type);
	 	        	    $("#store_id").val(data.store_id);
	 	        	    $("#gologin_id").val(data.gologin_id);
	 			        $("#From").submit();
		         }else{
 		        	 $("#login").css("background","#e01616");
		         }
	 	         if(data.open_txcode == "1"){
	 	        	 $(".txcode").show();
	 	        	 changeImg();
		        	 $("#code").val("")
	 	         }else{
	 	        	$(".txcode").hide();
	 	         }
 	          } 
	        }); 
    })
    /*   //更换验证码(无背景验证码)
    $(document).ready(function(){
			changeCode();
			$("#codeImg").bind("click",changeCode);
 			 
   	});
  	//验证码图片
    function changeCode(){
			$("#codeImg").attr("src","code.do?t="+genTimestamp());
	}
    function genTimestamp(){
		var time = new Date();
		return time.getTime();
	} */
  //TOCMAT重启之后 点击左侧列表跳转登录首页 
	if (window != top) {
		top.location.href = location.href; 
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
		
		
		//TOCMAT重启之后 点击左侧列表跳转登录首页 
		if (window != top) {
			top.location.href = location.href; 
		}
    </script>
</body>
</html>