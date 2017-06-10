 	document.onkeydown=function(e){
        var ev=e||window.event
        var code=ev.keyCode;   //获取键码
        console.log(code)
        if(code==13){
			var login = document.getElementById("login");//指定事件的id
			login.click();
        }
    }
 	     
    //登录
    $("#login").click(function(){
		    if($("#phone").val().trim() == ""){
		    	alert("登录账号不能为空");
		    	return;
		    }
		    if($("#password").val().trim() == ""){
		    	alert("登录密码不能为空");
		    	return;
		    }
	      $.ajax({  
	         type : "POST",  
	         url : base_inf.base_href+"memberpc/pcLogin.do",  
	         data :{
	        	 	phone:$("#phone").val(),
	        	 	loginpassword:$("#password").val(),
	        	 	code:$("#code").val()
	        	 },  
	         dataType:"json",
	         success : function(data) { 
	        	 $("#login").html(data.message);
	 	         if(data.result=="1"){
	 	        	if($("#saveid").attr("checked")){
	 					$.cookie('loginname', $("#phone").val(), { expires: 7 });
	 					$.cookie('password', $("#password").val(), { expires: 7 });
	 				}
	 			    window.location.href=base_inf.base_href;
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
 	          },  
	         error : function() {  
	            alert("失败");  
	         }  
	        }); 
    });
    

    //改变登录的文本
   	function changeText(){
   		$("#login").html("登录");
   		$("#login").css("background","#15acf2");
    }
   
    
    
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
    