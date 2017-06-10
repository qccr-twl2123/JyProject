<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
	<title>填写门店信息</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/htmlmember/normalize.min.css">
	<link rel="stylesheet" href="css/htmlmember/kd_inf.css">
 	<style type="text/css">
	.dingwei_w{
		color: blue;
	    cursor: pointer;
	    font-size: 24px;
	    display: inline-block;
 	    padding-top: 15%;
	    padding-left: 5%;
	}
	</style>
	<script type="text/javascript">
	 		var base_inf={
 	            base_herf:"<%=basePath%>" 
 	        };
	</script>
</head>
<body>
	<header>
		<div class="goback" onclick="backreturn()">‹</div>
		<div class="title">
			 <div class="djs">
		        <h6>填写门店信息</h6>
	    	</div>
		</div>
		 
	</header>
	<form action="<%=basePath%>app_store/saveStore.do" id="From" method="post">
		<input name="province_name" id="province_name" type="hidden" value=" "/>
		<input name="city_name"  id="city_name" type="hidden" value=" "/>
		<input name="area_name"  id="area_name"  type="hidden" value=" "/>
		<section class="inf">
			<p>欢迎入驻九鱼网，具有合法营业资质的实体店家均可申请加盟！请如实填写以下信息，成功提交后，工作人员经与您联系。</p>
			<h5><span>商店名称：</span><input name="business_licenses_name" id = "business_licenses_name" type="text" placeholder="请输入营业执照上的字号名称"></h5>
			<h5><span>商店简称：</span><input name="store_name" id = "store_name" type="text" placeholder="APP上的名称，便于消费者识别，请勿侵权"></h5>
			<h5 style="padding:0.5rem 1rem;">
				<span>经营地址：</span>
				<select name="province_id" id="province_id" onchange="addsearchfind(this);">
					<option value="">请选择省</option>
					<c:forEach items="${provincelist}" var="var">
				 		<option value="${var.pcd_id}" >${var.name}</option>
					</c:forEach>
				</select>
				<select name="city_id" id="city_id" onchange="addsearcharea(this);">
					<option value="">市</option>
				</select>
				<select name="area_id" id="area_id">
					<option value="">区/县</option>
				</select>
				<input name="longitude"  id="longitude" type="hidden" value=""/>
				<input name="latitude"  id="latitude"  type="hidden" value=""/>
			</h5>
	 		<h5>
				<span>街道：</span>
				<input type="text" placeholder="请输入详细商店地址" name="address" id="address">
			</h5>
			<h5 onclick="showmap()">
				<span style="width:30%;">标注地图位置：</span>
				<span style="font-size:3rem;color:#199bd7;float:right;line-height:0.4;width:1rem;font-weight:normal">›</span>
				<span class="isbiaozhu" style="float:right;    width: 64%;    padding-right: 0.6rem;text-align: right;font-weight: normal;font-size:1.2rem;">未标注</span>		
			</h5>
			<h5 style="position: relative;">
				<span style="vertical-align:top;">经营品项：</span>
				<textarea class="jypx" name="management_projects_desc" id = "management_projects_desc" cols="" rows="" onkeyup="change()" maxlength=50></textarea>
				<p class="tip">您还可以输入50个字</p>
			</h5>
			<h5>
				<span style="width:25%;">申请人姓名：</span>
				<input type="text" placeholder="请输入申请人姓名" style="width:73%;"  name="principal" id = "principal">
			</h5>
			<h5 style="position: relative;">
				<span>手机号码：</span>
				<input name="phone" id="phone" type="text" class="phonenum" placeholder="请输入手机号码" style="width:40%"maxlength="11">
				<input name="registertel_phone"  id="registertel_phone" type="hidden" />
				<div class="button code" style="position: absolute;" id="search">
					获取验证码
				</div>
			</h5>
			<h5>
				<span>验证码：</span>
				<input type="text" id="code" placeholder="请输入获取的验证码" name="messagecode">
 			</h5>
			<h5 style="font-size:1.3rem;">
				必须由老板或法人授权的管理人员进行申请，申请通过后，申请人的手机号码将默认为商家的最高权限管理员
			</h5>
			<div class="tjsq">
				<div style="width:100%;">
					<input type="checkbox" checked><span >我已阅读并同意</span><a href="jsp/storepc/userAgreement2.html" style="color:#199bd7;display: inline-block;">九鱼网商家合作协议</a>
				</div>
				<div class="button tijiao" style="width:30%;margin:1rem auto;" onclick="sureAddStore()">
					提交申请
				</div>
			</div>
		</section>
	</form>
	<span class="back" onclick="fanhui()"><img src="img/back.png" alt=""></span>
	
	<div class="map_box map" id="allmap">
	
	</div>
<script src="js/jquery-1.8.0.min.js"></script>
<script src="js/wx/jweixin-1.0.0.js"></script>
<script src="js/wx/zepto.min.js"></script>
<script src="js/htmlmember/weixindemo.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script src="js/jquery.tips.js"></script>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9"></script>
<script type="text/javascript" src="https://api.map.baidu.com/library/AreaRestriction/1.2/src/AreaRestriction_min.js"></script>
<script>
function backreturn(){
	window.history.back();
}

//获取城市
function addsearchfind(obj){
	var str=$(obj).val();//获取被选中的value值
		$.ajax({
		  url: '<%=path%>/zhihui_subsidiary/citylist.do',
		  data:"province_id="+str,
		  type:"post",
		  dataType:"json",
		  success:function(data){
			  	var list=data.citylist;
				$(obj).next().empty();
				$(obj).next().next().empty();
			  	if(list.length>0){
			  		$(obj).next().append("<option value=''>市</option>");
			  		$(obj).next().next().append("<option  value=''>区/县</option>");
				  	for(var i=0;i<list.length;i++){
				  		$(obj).next().append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
				  	}
			  	}
		  },
		  error:function(a){
		  	alert("异常");
		  }
	});
}
	
//获取区域
function addsearcharea(obj){
	var str=$(obj).val();//获取被选中的value值
	$.ajax({
		  url: '<%=path%>/zhihui_subsidiary/arealist.do',
		  data:"city_id="+str,
		  type:"post",
		  dataType:"json",
			  success:function(data){
			  	var list=data.arealist;
			  	$(obj).next().empty();
			  	$(obj).next().append("<option  value=''>区/县</option>");
			  	if(list.length>0){
				  	for(var i=0;i<list.length;i++){
				  		$(obj).next().append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
				  	}
		  		}
		  },
		  error:function(a){
		  alert("异常");
		  }
	});
}

	function change(){
		var tip=document.getElementsByClassName("tip")[0]
		var textlength=String(document.getElementsByClassName("jypx")[0].value).length
		var zs=50-textlength
		if (zs<=0) {
			zs=0
		};
		tip.innerText="您还可以输入"+zs+"个字"
	}
	 
	var inf=document.getElementsByClassName("inf")[0];
	var mapbox=document.getElementsByClassName("map_box")[0];
	var goback=document.getElementsByTagName("header")[0]
	
	function showmap(){
		inf.style="display:none";
		goback.style="display:none";
		mapbox.style="display:block";
		$(".back").show();
	}
	function fanhui(){
		inf.style="display:block";
		goback.style="display:block";
		mapbox.style="display:none";
		$(".back").hide();
	}
	
	var nowcode="";
    var validCode=true;
    //判断该手机号码是否注册
    	$("#search").click(function(){
    		//判断手机格式
			var myreg = /^((13[0-9])|(15[^4,\\D])|(17[2,7-8])|(18[0,5-9]))\\d{8}$/;
 			if($("#phone").val().length != 11 && !myreg.test($("#phone").val())){
 				$("#phone").tips({
					side:3,
		            msg:'手机号格式不正确',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#phone").focus();
 				return false;
	 		} 
 			if(validCode){
 				$.ajax({  
 			         type : "post",  
 			         url : "<%=path%>/storepc/findCodeByZhuCe.do",  
 			         data :{
 			        	 	"registertel_phone" : $("#phone").val(),
 			        	 	"in_position":"wx"
 			         },  
 			         dataType:"json",
 			         success : function(data) {  
 			         	if(data.result=="0"){
 			         		alert(data.message);
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
 			       					if($(".tijiao").attr("onclick") == ""){
 			       						code.css("background","#A0A5A7");
 			       					}else{
 			       						code.attr("onclick","getDxCode()");
 			       					}
 			       					
 	 		       				}
 			       			},1000)
 	 		         	}
 			         } 
 			        }); 
 	    		}
 	   });
    
    //地图定位
       var lng="";
       var lat="";
       var province_name="";
       var city_name="";
       var area_name="";
       var address="";
       // 百度地图API功能（定位到当前位置）
  	    var map = new BMap.Map("allmap",{enableMapClick:false});
  		var point = new BMap.Point(116.331398,39.897445);
	   	map.centerAndZoom(point,15);
	    map.enableScrollWheelZoom(true);
  		var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
 		map.addControl(top_left_navigation);  //添加放大缩小的按钮
		if(true){
			  //微信定位
  		    wxdingwei();
       }
 		
 	  //获取到的经纬度
      function pcd(lng,lat){
    	    // 设置显示范围
    	  	var b = new BMap.Bounds(new BMap.Point(lng+0.2, lat+0.2),new BMap.Point(lng-0.2, lat-0.2));
  			BMapLib.AreaRestriction.setBounds(map, b);
      	    var new_point = new BMap.Point(lng,lat);
  		   	var marker = new BMap.Marker(new_point);
 		  	map.addOverlay(marker);              // 将标注添加到地图中
			map.panTo(new_point); 
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
 				var infoWindow = new BMap.InfoWindow("<a onclick=\"sureStore()\" class=\"dingwei_w\">标注为当前位置</a>", opts);  // 创建信息窗口对象 
				map.openInfoWindow(infoWindow,point); //开启信息窗口
 	 		});  
 		});
    		
   	
   	 	
	//设置门店
	function sureStore(){
 		inf.style="display:block";
		goback.style="display:block";
		mapbox.style="display:none";
		$(".back").hide();
		$(".isbiaozhu").html("经度"+lng+",维度"+lat);
		$("#longitude").val(lng);
		$("#latitude").val(lat);
	}
   	 
	
	//注册
    function sureAddStore(){
    	    var address = $("#address").val();
    	    var business_licenses_name = $("#business_licenses_name").val().trim();
    	    var store_name = $("#store_name").val().trim();
    	    var principal = $("#principal").val().trim();
    	    var management_projects_desc = $("#management_projects_desc").val().trim();
    	    var phone = $("#phone").val().trim();
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
    	    if($("#area_id").val() == '' || $("#area_id").val()  == null){
    	    	$("#area_id").tips({
					side:3,
		            msg:'区/县不能为空',
		            bg:'#AE81FF',
		            time:1
			    });
				$("#area_id").focus();
		    	return ;
	    	} 
    	    $("#province_name").val($("#province_id option:selected").text());
 			$("#city_name").val($("#city_id option:selected").text());
 			$("#area_name").val($("#area_id option:selected").text());
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
     	   $("#address").val($("#city_id option:selected").text()+$("#area_id option:selected").text()+address);
    	    if($("#longitude").val() == '' || $("#longitude").val() == null){
	    	    $(".isbiaozhu").tips({
					side:3,
		            msg:'未标明位置',
		            bg:'#AE81FF',
		            time:1
    		    });
    			$(".isbiaozhu").focus();
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
     	    if(phone == '' || phone == null){
    	    	$("#phone").tips({
    					side:3,
    		            msg:'手机号码不能为空',
    		            bg:'#AE81FF',
    		            time:1
    		    });
    			$("#phone").focus();
	    	    return ;
    	    }else{
    	    	var myreg = /^((13[0-9])|(15[^4,\\D])|(17[2,7-8])|(18[0,5-9]))\\d{8}$/;
     			if(phone.length != 11 && !myreg.test(phone)){
    				$("#phone").tips({
    					side:3,
    		            msg:'手机号格式不正确',
    		            bg:'#AE81FF',
    		            time:1
    		        });
    				$("#phone").focus();
	    				return false;
    	 		} 
    	    }
     	   if($("#code").val() == '' || $("#code").val() == null){
	   	    	 $("#code").tips({
						side:3,
			            msg:'验证码不能为空',
			            bg:'#AE81FF',
			            time:1
			    });
				$("#code").focus();
		    	return ;
		    }else{
		    	if(IsOKMessageCode($("#code").val()) == "0"){
		    		return;
		    	}
		    }
     	    //提交
    	    $("#From").ajaxSubmit({  
			  	url : '<%=basePath%>app_store/saveStore.do',
		        type: "POST",//提交类型  
		      	dataType:"json",
		   		success:function(data){
		   			 if(data.result == "1"){
		   				 alert("入驻成功，请等待审核");
		   				 $(".tijiao").attr("onclick","");
		   				 $(".tijiao").css("background","#A0A5A7");
		   			 }
				}
			});  
 	    } 
	
		//判断短信验证码是否正确
	    function IsOKMessageCode(value){
	    	var isok="0";
	    	$.ajax({  
		         type : "post",  
		         url : "<%=path%>/storepc/IsOKMessageCode.do",  
		         data :{
		        	 "messagecode":value,"codetype":"1"
		         },  
		         dataType:"json",
		         async: false,  
		         success : function(data) {  
		        	 if(data.result == "1"){
		        		 isok="1";
			   		 }else{
			   			 alert(data.message);
 			   		 }
		         } 
		    }); 
	    	return isok;
	    }
	    
	
	
   	
</script>
  </body>
</html>