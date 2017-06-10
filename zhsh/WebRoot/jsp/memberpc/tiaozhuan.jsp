<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	//out.print(path);
%>

<!DOCTYPE html>
<html>
  <head>
	    <title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script src="../js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9"></script>
  </head>
  
  <body>
  	 <span id="pca"></span>
     <script type="text/javascript">
     var geolocation = new BMap.Geolocation();
	 geolocation.getCurrentPosition(function(r){
	   		if(this.getStatus() == BMAP_STATUS_SUCCESS){
	   			lng=r.point.lng;
	   			lat=r.point.lat;
 	   			$.ajax({
	   	         type:"post",
	   	         url:"http://api.map.baidu.com/geocoder/v2/?ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&callback=renderReverse&location="+lat+","+lng+"&output=json&pois=1", 
	   		        dataType:"jsonp",
	   		        success: function(data){
	   		        	if(data.status == "0"){
	   		        		var result=data.result;
	   		        		var addressComponent=result.addressComponent;
	   		        		var province_name=addressComponent.province;
		   		  			var city_name=addressComponent.city;
		   		  			var area_name=addressComponent.district;
		   		  			document.location ="../memberpc_shouye/pcMemberSy.do?province_name="+province_name+"&city_name="+city_name+"&area_name="+area_name+"&longitude="+lng+"&latitude="+lat+"&member_id="+"${memberpd.member_id}";
	   		        	}
	   		        }
	   		   });
 	   		}
	   		else {
	   			alert('failed'+this.getStatus());
	   		}        
	 },{enableHighAccuracy: true})
     </script>
  </body>
</html>
 --%>