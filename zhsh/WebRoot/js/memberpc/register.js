//获取推荐人手机号码并判断是否已经注册过
    	function listPhone(){
    		//判断手机格式
			var myreg = /^((13[0-9])|(15[^4,\\D])|(17[2,7-8])|(18[0,5-9]))\\d{8}$/;
			if($("#be_phone").val().length == 11){
				if(!myreg.test($("#be_phone").val())){
					$("#be_phone").tips({
						side:3,
			            msg:'手机号格式不正确',
			            bg:'#AE81FF',
			            time:3
			        });
					$("#be_phone").focus();
					$("#be_phone").val("");
					return false;
		 		} else{
		 			$.ajax({
			             type: "POST",
			             url: base_inf+base_href+"memberpc/TuijianPhone.do",
			             data: {"phone":$("#be_phone").val()},
			             dataType: "json",
			             success: function(data){
			            	$("#phone").empty();
			            	if(data.result == "01"){
		  			             	var strList=data.varList;
		 		             		$("#phone").append("<option  value='0@0@0'>不填写，直接注册</option>");
					             	for(var i=0;i<strList.length;i++){
		 			             		$("#phone").append("<option value='"+strList[i].phone+"@"+strList[i].type+"@"+strList[i].id+"'>"+strList[i].phone+"</option>");
					             	}
			            	}else{
			            		alert("当前号码已注册请前往登陆");
			            		$("#be_phone").val("");
			            		return false;
			            	}
			             	
			             }
			         });
		 		}
			}
 	    }
    	
    	
        var validCode=true;
     	//获取验证码
    	function getCode(){
            if($("#password").val() == ""){
    			alert("密码不能为空");
    			return;
    		}
     		if($("#be_phone").val() == ""){
    			alert("手机号码不能为空");
    			return;
    		} 
     		 if($("#txcode").val() == ""){
     			alert("图形码不能为空");
     			return;
     		}
            if(validCode){
			   $.ajax({
	             		type: "POST",
	             		url: base_inf+base_href+"memberpc/getZhuCeCode.do",
	             		data: {"phone":$("#be_phone").val(),"tuxingcode":$("#txcode").val()},
	             		dataType: "json",
	             		success: function(data){
	             			alert(data.message);
	             			if(data.result == "0"){
 	             				return;
	             			}else{
	             				//重新设置图形验证码
  	 				       		var time=60;
					       		var numbercode=$(".numbercode");
	 			       			validCode=false;		 
				       			numbercode.attr("onclick","");
				       			var t=setInterval(function() {
						       		time--;
						       		numbercode.html(time+"秒");
						       		if (time==0) {
						       			clearInterval(t);
						       			numbercode.html("重新获取");
						       			validCode=true;
						       			numbercode.attr("onclick","getCode()");
				 		       		}
						       	},1000);
	             				
	             			}
	             			
 	            	 		
 	             		}
	         	});


		}
    		
    	}
    	
	   //提交注册
	   function zhuceOK(){
		   if($("#password").val() == ""){
	   			alert("密码不能为空");
	   			return;
	   		}
 	   		if($("#be_phone").val() == ""){
	   			alert("手机号码不能为空");
	   			return;
	   		} 
    		if($("#phonecode").val() == ""){
    			alert("验证码不能为空");
    			return;
    		}
		    $("#Form").ajaxSubmit({  
					  	url : base_inf.base_href+'memberpc/register.do',
				        type: "POST",//提交类型  
				      	data:{},
				      	dataType:"json",
				   		success:function(data){
				   			 alert(data.message);
				   			 if(data.result == "1"){
				   				 window.location.href=base_inf+base_href+"memberpc/goMemberLogin.do";
				   			 } 
 						} 
								  
			});
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
    		
    		