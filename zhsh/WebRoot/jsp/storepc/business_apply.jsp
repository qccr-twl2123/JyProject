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
    <meta http-equiv = "X-UA-Compatible" content = "IE=Edge,chrome=1" >
    <title>申请开店</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
    <link rel="stylesheet" href="css/pcstore/hsd_sqkd.css">
    <link rel="stylesheet" href="css/pcstore/normalize.min.css">
    <link rel="stylesheet" href="css/pcstore/response.css">
    <link rel="stylesheet" href="css/pcstore/hsd_mdxx.css">
  	<style type="text/css">
		footer .footcont ul li a {
	    	width: 133px;
		}
		section .yzm_img {
		    width: 25%;
		    height: 68%;
		    border: 1px solid #ccc;
		    float: right;
		    margin-left: 1%;
		    margin-right: 11%;
		    margin-top: 2%;
		    position: static;
	 	}
	 	#allmap {
	 		width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";
	 	}
		a:hover{
					color:red;
					cursor: pointer;
		}
	</style>
<script src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script src="js/jquery.tips.js"></script>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9"></script>
</head>
<body>
<div class="zc1" style="display: block;">
 <!--头部-->
    <header>
        <div class="head_cont">
             <div>
            <a href=""><img src="img/storelogo.png" alt="" class="logo"></a>
            •
            申请开店</div>
        </div>
    </header>
<!--内容-->
<section>
	<form action="" method="post" name="MessageCodeForm" id="MessageCodeForm"> 
	    <div class="cont">
	    <h5>每个实体店都应该有自己的互联网营销平台</h5>
	    <img src="img/kd_banner.png" alt="">
	        <div class="denglu">
	            <h6>欢迎您的到来！若您有意在九鱼网开店，请验证手机号码</h6>
	             <ul>
	            	<li><span>手机号码：</span><input class="inp" type="text" placeholder="请输入手机号码"  id="registertel_phone" maxlength="11"   onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"></li>
	            	<li><span>图形码：</span><input type="text" placeholder="图形码" maxlength="4" name="txcode" id="txcode" class="yzminp"><img class="yzm_img" src="verifyCodeServlet" alt="" id="imgObj" onclick="changeImg()"></li>
	            	<li><span>验证码：</span><input type="text" placeholder="验证码" class="yzminp"  id="code" style="text-align:left; vertical-align:middle"><a class="huoqu code" id="search">获取验证码</a></li>
	            	<li style="height:40px;"><input type="checkbox" class="fonts" name="sure" id="sure"><span style="width:auto;">我已阅读并同意</span><a href="jsp/storepc/userAgreement.html" target="_blank" style="color:rgb(85,26,139)">《九鱼销链平台服务协议》</a></li>
	                <li style="padding-top:15px;"><p class="zhuce" onclick="upSure()">提交申请</p></li>
 	            </ul>
	        </div>
	    </div>
    </form>
</section>
<script type="text/javascript"> 
     var validCode=true;
    //判断该手机号码是否注册
    	$("#search").click(function(){
    		//判断手机格式
			var myreg = /^((13[0-9])|(15[^4,\\D])|(17[2,7-8])|(18[0,5-9]))\\d{8}$/;
 			if($("#registertel_phone").val().length != 11 && !myreg.test($("#registertel_phone").val())){
				$("#registertel_phone").tips({
					side:3,
		            msg:'手机号格式不正确',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#registertel_phone").focus();
				$("#registertel_phone").val("");
				return false;
	 		} 
 			if($("#txcode").val() == ""){
 				alert("图形码不能为空");
 				return;
 			}
 			if(validCode){
 				$.ajax({  
 			         type : "POST",  
 			         url : "<%=basePath%>storepc/findCodeByZhuCe.do",  
 			         data :{
 			        	registertel_phone:$("#registertel_phone").val(),
 			        	"in_position":"pc",
 			        	"txcode":$("#txcode").val()
 			         },  
 			         dataType:"json",
 			         success : function(data) {  
 			        	alert(data.message);
 			         	if(data.result=="0"){
   			         		return false;
 	 		         	}else{
  	 			         	//alert("验证码已发至你手机，请注意查收");
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
 			       			},1000)
 	 		         	}
 			         } 
 			        }); 
 	    		}
 			});
    
    
      	//提交申请
    	function upSure(){
    		var registertel_phone=$("#registertel_phone").val();
    		if(registertel_phone == ""){
    			alert("手机号码不能为空");
    			return;
    		}
    		if($("#sure").is(":checked")){
    			var messagecode=$("#code").val();
    			$("#MessageCodeForm").ajaxSubmit({  
    			  	url : 'storepc/IsOKMessageCode.do',
    		        type: "post",//提交类型  
    		        data:{"messagecode":messagecode,"codetype":"1","phone":registertel_phone},
    		      	dataType:"json",
     		   		success:function(data){
    		   			 if(data.result == "1"){
    		   				 $(".zc1").hide();
    		   				 $(".zc2").show();
    		   				 $(".zc2_phone").val(registertel_phone);
    		   				$(".zc1").remove();
    		   				dinwei();
     		   			 }else{
    		   				 alert(data.message);
    		   			 }
     				}
    			});
       		}else{
    			alert("请阅读合作服务协议，并勾选");
    			return;
    		}
    		
    	}
		
      	//按enter键
		document.onkeydown=function(e){
	        var ev=e||window.event
	        var code=ev.keyCode;   //获取键码
	       // console.log(code)
	        if(code==13){
	        	upSure();
	        }
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
    
 </script>
</div>
<div class="zc2" style="display: none;">
<!--头部-->
    <header>
        <div class="head_cont">
            <div style="width:80%;"> 
            	<a href="<%=basePath%>"><img src="img/storelogo.png" alt="" class="logo"></a>• 填写门店信息
             	<p>注意：门店定位后设定后不可更改！请务必提高精准度，否则很可能无法通过审核。</p>
             </div>
         </div>
    </header>
    <div class="tit_cont">
        <div class="tit_search">
            <span class="tit_name">请选择关键字:</span><input type="text" id="suggestId"><span class="zhushi">(选择提示框中的内容)</span>
        </div>
    </div>
	<input name="phone" type="hidden" class="zc2_phone" id="phone" value="${pd.phone}"/>
	<!-- 地图地位位置 -->
    <div class="map" id="allmap" style="border: 3px solid #c0c0c0;">
    
    </div>
 <script type="text/javascript">
       var lng="";
       var lat="";
       var province_name="";
       var city_name="";
       var area_name="";
       var address="";
       // 百度地图API功能（定位到当前位置）
  	    var map = new BMap.Map("allmap",{enableMapClick:false});
  		var point = new BMap.Point(116.331398,39.897445);
	   	map.centerAndZoom(point,14);
	   	map.enableScrollWheelZoom(true);
      function dinwei(){
    	   $.ajax({
	         	type:"post",
	         	url:"https://api.map.baidu.com/highacciploc/v1?qcip=&callback_type=jsonp&qterm=pc&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&coord=bd09ll", 
		        dataType:"jsonp",
		        success: function(data){
		        	var result=data.result;
		        	var content=data.content;
		        	if(result.error == 161){
 		        		nowbmap(content.location.lng,content.location.lat);
		        	}
		        }
		   });
       }
      function nowbmap(lng,lat){
     	    var point = new BMap.Point(lng,lat);
		   	map.centerAndZoom(point,14);
		   	map.enableScrollWheelZoom(true);
		   	var mk = new BMap.Marker(point);
		   	map.addOverlay(mk);
      }
      
   		//单击获取点击的经纬度 ，获取当前地址
   		var geoc = new BMap.Geocoder();   
   		map.addEventListener("click", function(e){        
			var pt = e.point;
			lng=pt.lng;
			lat=pt.lat;
	 		geoc.getLocation(pt, function(rs){
				var addComp = rs.addressComponents;
				province_name=addComp.province;
				city_name=addComp.city;
				area_name=addComp.district;
				address=addComp.city+addComp.district+addComp.street+addComp.streetNumber;
				//文本显示
				var opts = {
				  width : 100,     // 信息窗口宽度
				  height: 50     // 信息窗口高度
		  		};
				point = new BMap.Point(lng,lat);
 				var infoWindow = new BMap.InfoWindow("<p>"+address+"</p><a onclick=\"sureStore()\" style=\"color:blue;cursor:pointer;\">设为门店</a>", opts);  // 创建信息窗口对象 
				map.openInfoWindow(infoWindow,point); //开启信息窗口
	 		});  
 	 	});
   	
   	//输入框的筛选
 	function G(id) {
		return document.getElementById(id);
	}
   	
   	var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
		{"input" : "suggestId"
		,"location" : map
	});
 	ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
	var str = "";
		var _value = e.fromitem.value;
		var value = "";
		if (e.fromitem.index > -1) {
			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		}    
		str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
		
		value = "";
		if (e.toitem.index > -1) {
			_value = e.toitem.value;
			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		}    
		str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
		//G("searchResultPanel").innerHTML = str;
	});

	var myValue;
	ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
	var _value = e.item.value;
		myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		//G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
 		setPlace();
	});
	
 	function setPlace(){
		map.clearOverlays();    //清除地图上所有覆盖物
		function myFun(){
			var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
			map.centerAndZoom(pp, 18);
			map.addOverlay(new BMap.Marker(pp));    //添加标注
		}
		var local = new BMap.LocalSearch(map, { //智能搜索
		  onSearchComplete: myFun
		});
		local.search(myValue);
	}
 	
 	
	//设置门店
	function sureStore(){
		$(".zc2").hide();
		$(".zc3").show();
		$(".zc3_phone").val($("#phone").val());
  		$(".zc3_longitude").val(lng);
		$(".zc3_latitude").val(lat);
		$(".zc3_province_name").val(province_name);
		$(".zc3_city_name").val(city_name);
		$(".zc3_area_name").val(area_name);
		$(".zc3_address").html(address);
		$(".zc2").remove();
 	}
</script>
</div>
<div class="zc3" style="display: none;">
<form action="" name="storeFrom" id="storeFrom" method="post">
	<input name="registertel_phone" type="hidden" value="" class="zc3_phone"/>
	<input name="longitude" type="hidden" value=""  class="zc3_longitude"/>
	<input name="latitude" type="hidden" value=""  class="zc3_latitude"/>
	<input name="province_name" type="hidden" value=""  class="zc3_province_name"/>
	<input name="city_name" type="hidden" value=""  class="zc3_city_name"/>
	<input name="area_name" type="hidden" value=""  class="zc3_area_name"/>
 	<!--头部-->
    <header>
        <div class="head_cont">
             <div><a href="<%=basePath%>"><img src="img/storelogo.png" alt="" class="logo"></a>• 填写门店信息</div>
        </div>
    </header>
  	<div class="title">
  		<div class="sec_head">
    		<p>请填写您的门店信息</p>
    		<span>(带*必填，九鱼网绝对不会将您的个人信息透漏给第三方)</span>
    	</div>
  	</div>
    <div class="list">
    	<div class="list_cont">
    		<ul>
    			<li> 
    			<div class="name"><span>*</span>商店名称：</div><input type="text" name="business_licenses_name" id = "business_licenses_name"><span class="zhushi">注：营业执照上的全称</span>
    			</li>
    			<li> 
    			<div class="name"><span>*</span>显示名称：</div><input type="text" name="store_name" id = "store_name"><span class="zhushi">注：显示在网页上和APP上的商店简称</span>
    			</li>
    			<li><div class="name"><span>*</span>商店地址：</div><span class="zc3_address"></span>  <input type="text" name="address" id="address" value=""></li>
    			<li><div class="name"><span>*</span>联系人：</div><input type="text" name="principal" id = "principal"></li>
    			<li><div class="name_pinlei"><span>*</span>经营品类：</div><textarea name="management_projects_desc" id = "management_projects_desc" cols="30" rows="10"></textarea></li>
    			<li><div class="name"><span>*</span>手机号码：</div><input readonly="readonly" type="text"  name="phone" class="zc3_phone"  value=""></li>
    			<li><div class="button"><div class="btnbox" id="search" onclick="sureAddStore()">现在开始</div></div></li>
    		</ul>
    	</div>
    	<div class="list_img">
    		<img src="img/mdxx_img_03.jpg" alt="">
    	</div>
    </div>
 </form>
 <script type="text/javascript">
	    //注册
	    function sureAddStore(){
	    	    var address = $("#address").val();
	    	    var business_licenses_name = $("#business_licenses_name").val().trim();
	    	    var store_name = $("#store_name").val().trim();
	    	    var principal = $("#principal").val().trim();
	    	    var management_projects_desc = $("#management_projects_desc").val().trim();
  	    	    if(business_licenses_name == '' || business_licenses_name == null){
 	    	    	$("#business_licenses_name").tips({
    					side:3,
    		            msg:'商店名称不能为空',
    		            bg:'#AE81FF',
    		            time:1
	    		    });
	    			$("#business_licenses_name").focus();
	    	    	return ;
	    	    }
	    	    if(store_name == '' || store_name == null){
 	    	    	$("#store_name").tips({
    					side:3,
    		            msg:'显示名称不能为空',
    		            bg:'#AE81FF',
    		            time:1
	    		    });
	    			$("#store_name").focus();
	    	    	return ;
	    	    }
	    	    if(address == '' || address == null){
 	    	    	$("#address").tips({
	    					side:3,
	    		            msg:'商店地址不能为空',
	    		            bg:'#AE81FF',
	    		            time:1
	    		    });
	    			$("#address").focus();
	    	    	return ;
	    	    } 
	    	    if(principal == '' || principal == null){
 	    	    	 $("#principal").tips({
	    					side:3,
	    		            msg:'联系人姓名不能为空',
	    		            bg:'#AE81FF',
	    		            time:1
	    		    });
	    			$("#principal").focus();
	    	    	return ;
	    	    }
	    	     if(management_projects_desc == '' || management_projects_desc == null){
	    	    	 $("#management_projects_desc").tips({
	    					side:3,
	    		            msg:'经营品项不能为空',
	    		            bg:'#AE81FF',
	    		            time:1
	    		    });
	    			$("#management_projects_desc").focus();
	    	    	return ;
 	    	    }
 	    	   $("#address").val("${pd.address}"+address);
  	    	   $("#storeFrom").ajaxSubmit({  
	   			  	url : 'storepc/sS_P.do',
	   		        type: "post",//提交类型  
 	   		      	dataType:"json",
	    		   	success:function(data){
	   		   			 if(data.result == "1"){
	   		   				 alert("开店申请已提交，请等待审核");
	   		   				 window.location.href="storepc/goLogin.do";
	    		   		  }else{
	   		   				 alert(data.message);
	   		   				 window.location.href="storepc/goRegister.do";
	   		   			 }
	    			}
	   			});
   	    } 
 
     </script>
</div>
<footer>
    <div class="footcont">
        <ul>
            <li><a href="storepc/goRegister.do" class="noborder">我要开店</a></li>
            <li><a href="jsp/storepc/gyjy.html" target="_blank">关于九鱼</a></li>
            <li><a href="">加入我们</a></li>
            <li><a href="">合作流程</a></li>
            <li><a href="">常见问题</a></li>
        </ul>
    </div>
    <div class="beian">©2016 jiuyuvip.com [浙] ICP备16025718号-2 ,All Rights Reserved.</div>
</footer>
</body>
 </html>