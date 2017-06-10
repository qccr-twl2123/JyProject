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
	<title>定位门店</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
	<link rel="stylesheet" href="css/pcstore/hsd_mdxx.css">
   
	<style type="text/css">
			body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
			a:hover{
				color:red;
				cursor: pointer;
			}
	</style>
	<script src="js/jquery-1.8.0.min.js"></script>
 	<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9"></script>
</head>
<body>
	<!--头部-->
    <header>
        <div class="head_cont">
            <div>
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
	<input name="phone" type="hidden" id="phone" value="${pd.phone}"/>
	<!-- 地图地位位置 -->
    <div class="map" id="allmap" style="border: 3px solid #c0c0c0;">
    
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
       if(true){
    	   $.ajax({
	         	type:"post",
	         	url:"https://api.map.baidu.com/highacciploc/v1?qcip=&callback_type=jsonp&qterm=pc&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&coord=bd09ll", 
		        dataType:"jsonp",
		        success: function(data){
		        	var result=data.result;
		        	var content=data.content;
		        	if(result.error == 161){
		        		//alert(content.location.lat+","+content.location.lng);
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
 		window.location.href="<%=basePath%>storepc/goRegisterlast.do?phone="+$("#phone").val()+"&longitude="+lng+"&latitude="+lat+"&province_name="+province_name+
				"&city_name="+city_name+"&area_name="+area_name+"&address="+address;
	}
   	 
   	
</script>
</body>
</html>