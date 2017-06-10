//获取验证码
function gc(){
	var data = {"password_token":$("#password_token").val()};
	$.post(base_inf.base_href+"memberpc//gFpMc.do", data).success(function(result) {
	     if(result == "1"){
	    	 	$(".hq_button").hide();
             	$(".cxhq_button").show();
  	         	var timenum=60;
	            var time=setInterval(function(){
	                if(timenum<1){
	                    window.clearInterval(time);
	                    $(".time").html("60");
	                    $(".hq_button").show();
	                    $(".cxhq_button").hide();
	                }else{
	                	timenum--;
	                    $(".time").html(timenum);
	                }
	            },1000);
	     }
	});
}
//确认
function sc(){
	if(base_inf.x == ""){
		window.location.href=base_inf.base_href+"memberpc/gfp2.do";
 	}else{
		if($("#mscode").val() == ""){
			alert("验证码不能为空");
			return;
		}
		var data = {"mscode": $("#mscode").val() };
		$.post(base_inf.base_href+"memberpc/yZmC.do", data ).success(function(result) {
		     if(result == "0"){
	 	         	 alert("验证码错误");
		     }else{
		    	 $("#Form").submit();
		     }
		});
	}
 }