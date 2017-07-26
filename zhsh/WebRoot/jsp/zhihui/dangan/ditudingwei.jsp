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
	<title>重新定位位置</title>
	<base href="<%=basePath%>">
	<link href="css/bootstrap.min.css" rel="stylesheet" />
    <script src="js/jquery-1.8.0.min.js"></script>
  	<style type="text/css">
			body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
			a:hover{
				color:red;
				cursor: pointer;
			}
	</style>
	<script src="js/jquery-1.8.0.min.js"></script>
 	<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=ltKk6fs4gHVPtWqtAsErXpBOd7ezHIWe"></script>
</head>
<body>
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
	    // 百度地图API功能
	   	var map = new BMap.Map("allmap",{enableMapClick:false});
		map.enableScrollWheelZoom(true);
	    if("${pd.lng}" != ""){
	    	var point = new BMap.Point("${pd.lng}","${pd.lat}");
		   	map.centerAndZoom(point,19);
 		   	map.addOverlay(new BMap.Marker(point));
	    }else{
	    	// 创建地址解析器实例
		   	// 将地址解析结果显示在地图上,并调整地图视野
		   	  var myGeo = new BMap.Geocoder();
	 	   	myGeo.getPoint("${pd.address}", function(point){
		   		if (point) {
		   			map.centerAndZoom(point, 19);
		   			map.addOverlay(new BMap.Marker(point));
		   		}else{
		   			alert("您选择地址没有解析到结果!");
		   		}
		   	}, "北京市"); 
	    }
 	   	
	    
   	 	 //单击获取点击的经纬度 ，获取当前地址
     	map.addEventListener("click", function(e){  
 			var pt = e.point;
			window.lng=pt.lng;
			window.lat=pt.lat;
  		    //文本显示
			var opts = {
			  width : 50,     // 信息窗口宽度
			  height: 10     // 信息窗口高度
	  		};
			point = new BMap.Point(lng,lat);
			var infoWindow = new BMap.InfoWindow("<div style=\"width:100%;height:100%;    padding: 15px 0 0 55px;font-size: 26px; \"> <a onclick=\"sureStore()\" style=\"color:blue;cursor:pointer;\">确认重置？</a></div>", opts);  // 创建信息窗口对象 
 			var mk = new BMap.Marker(point);
 		   	map.openInfoWindow(infoWindow,point); //开启信息窗口
 	 });
     
	//重置定位度
	function sureStore(){
 		window.location.href="zhihuiz_store_file/updatedingwei.do?store_id=${pd.store_id}&longitude="+lng+"&latitude="+lat ;
	}
   	 
   	
</script>
</body>
</html>