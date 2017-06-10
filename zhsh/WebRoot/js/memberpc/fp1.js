$(function(){
	//下一步点击事件
	$(".qr_button").click(function(e){
		var jphone_y=$("#jphone_y").val();
		if(jphone_y == ""){
			alert("请填写九鱼账户");
			return;
		}
		if($("#txcd_lu").val() == ""){
			alert("图形码不能为空");
			return;
		}
 		$.ajax({
			  url:base_inf.base_href+"memberpc/yZLN.do",
			  data:{"jphone_y":jphone_y,"txcd":$("#txcd_lu").val()},
			  type:"post",
			  dataType:"json",
			  success:function(data){
				  if(data.result == "1"){
					  $("#Form").submit();
				  }else{
					  alert(data.message);
				  }
	 		  } 
		});
		
		
	});
});

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
	