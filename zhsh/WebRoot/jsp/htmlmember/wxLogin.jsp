<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <base href="<%=basePath%>">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <link rel="stylesheet" href="css/htmlmember/labary/predefine.css">
    <link rel="stylesheet" href="css/htmlmember/loading.css">
    <script type="text/javascript">
	 		var base_inf={
 	            base_herf:"<%=basePath%>" 
 	        };
	</script>
</head>
<style>
     li{
        padding: 0;
    }
    body{
        position: relative;
    }
    ul{
        width:90%;
        display: block;
        background: #fcdccd;
    }
    .inf_box{
        position: absolute;
        margin: auto;
        top: 7rem;
        bottom: 0;
        right: 0;
        left: 0;
        padding: 6% 12%;
        max-height: 16.2rem;
        border-radius: 4px;
        box-sizing: border-box;
    }
    .bd1_li{
        border-radius: 4px;
        border: 1px solid #bd7200;
    }
    .mg_b{
        margin-bottom: 0.9rem;
    }
    .li_span{
        display: inline-block;
        height: 100%;
        border-right: 1px solid #bd7200;
        width: 2.4rem;;
    }
    .li2{
        width: 63%;
        position: relative;
    }
    .li_div{
        position: absolute;
        right: -60%;
        top:0;

        background: #ffa31b;
        color: #fff;
        line-height: 2.5rem;
        min-width: 4.2rem;
    }
    .li_img{
        position: relative;
        vertical-align: middle;
        height: 80%;
        padding: 10% 0;
        font-size: 0;

    }
</style>
<body style="background-color: #fff">
<div class="neirong"  style="display:none;">
	<img src="img/bangding.jpg" alt="" style="width: 100%">
	<ul class="inf_box">
	    <li class="lh2   bd1_li mg_b">
	        <span class="fwb li_span"><img class="li_img" src="img/shouji.png" style="left: 0.5rem;" alt=""></span>
	        <input type="text" maxlength="13" id="phone" placeholder="请输入手机号" style="background: rgba(0,0,0,0);width: 75%" oninput="lenghtOK(this)">
	    </li>
	    <li class="lh2  bd1_li  mg_b li2">
	        <span class="fwb li_span"><img class="li_img" src="img/yanzhengma.png" style="left: 0.5rem;" alt=""></span>
	        <input type="text"   style="background: rgba(0,0,0,0);width: 50%" maxlength="4" id="messagecode">
	        <div class="li_div bd1_li">
	            <span class="txr  code " >获取验证码</span>
	        </div>
	    </li>
	    <li class="lh2 bd1_li   mg_b">
	        <span class="fwb li_span"><img class="li_img" src="img/tjr.png" style="left: 0.4rem;" alt=""></span>
	         <select name="recommended" id="recommended"    style="width: 75%;">
	        	<option value="0@0">不填写，直接注册</option>
	        </select>
	    </li>
	    <li class="txc lh2 pd2 mg_b">
	        <span class="act btn pd2" onclick="submitLogin()">登录</span>
	    </li>
	</ul>
</div>
<div class="readyjsp" style="width: 100%;margin: auto;position: absolute;top: 40%; display:none; ">
			<img alt="" src="img/readyjsp.gif"  style=" width: 100%; ">
</div>
</body>
<script src="js/htmlmember/library/jquery-1.12.4.min.js"></script>
<script src="js/wx/jweixin-1.0.0.js"></script>
<script src="js/wx/zepto.min.js"></script>
 <script src="js/htmlmember/weixindemo.js"></script>
<script>
"${pd}"
if("${member_id}" != ""){//已经注册过微信账号,直接前往首页
	 document.title='前往首页';
	 $(".readyjsp").show();
 	 //微信定位
	 wxdingwei();
	 //gogogo2();
  } else{
	
	$(".neirong").show();
 	 document.title='绑定手机';
}
//=========================================推荐人===========================================================
var str="";
var n=0;
if("${pd.recommended}" != null && "${pd.recommended}" != ""){
	str=("<option value='${pd.recommended}@${pd.recommended_type}' selected >${pd.recommended_phone}</option>");
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
}
//==============================================================================================================

//判断手机号码的位数
function lenghtOK(obj){
	if($(obj).val().length == 11){
		var val=obj.value;
        if(!(/^1(3|4|5|7|8)\d{9}$/.test(val))){
            alert("手机号码有误，请重填");
            return;
        }
   		$(".code").attr("onclick","getDxCode()");
 		getTuiJianList(obj);
 	}else{
 		$(".code").attr("onclick","");
		$(".code").html("获取验证码");
	}
}

//======================================================================================================

	//获取验证码
	var validCode=true;
 	function getDxCode(){
		if($("#phone").val() == null || $("#phone").val() == "" ){
			alert("手机号码不能为空");
			return;
		} 
  		if (validCode) {
			$.ajax({
		        type:"post",
		        url:"<%=basePath%>html_member/htmlFindCode.do", 
		  	 	data:{"phone":$("#phone").val()},                
		        dataType:"json",
		        success: function(data){
		        	 if(data.result == "1"){
  				       	 		var time=60;
				       			var code=$(".code");
 			       				validCode=false;
 			       				code.attr("onclick","");
			       				var t=setInterval(function() {
				       				time--;
				       				code.html(time+"秒");
				       				if (time==0) {
				       					clearInterval(t);
				       					code.html("重新获取");
				       					validCode=true;
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


	//验证登录
	function submitLogin(){
			var strs= new Array(); //定义一数组 
 			strs=$("#recommended").val().split("@"); 
    			$.ajax({
                type:"post",
                url:'<%=basePath%>html_member/ajaxYanZhengWxLogin.do', 
          	 	 data:{
          	 		 	"province_name":"${pd.province_name}" ,
          	 		 	"city_name":"${pd.city_name}" ,
          	 		 	"image_url":"${pd.image_url}" ,
          	 		 	"sex":"${pd.sex}" ,
          	 		 	"name":"${pd.name}" ,
          	 		 	"wxunionid":"${pd.wxunionid}" ,
          	 		 	"wxopen_id":"${pd.wxopen_id}" ,
          	 		 	"code":$("#messagecode").val(),
          	 		 	"phone":$("#phone").val(),
          	 		 	"recommended":strs[0],
 		  	 			"recommended_type":strs[1]
          	 		 },                
                dataType:"json",
                success: function(data){
               	 	if(data.result == "1"){
               	 		window.location.href="<%=basePath%>html_member/toLoginWx.do";
               	 	}else{
               	 		alert(data.message);
                	}
                 }
            });
		}
	
	
//===============================================================================
		
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
	        		window.location.href="html_member/gouShouYe.do?province_name="+province_name+
	        								"&area_name="+area_name+"&city_name="+city_name+
	        								"&address="+street+
	        								"&longitude="+lng+"&latitude="+lat;	 
   		        }
		        }
		   });
}
</script>

</html>