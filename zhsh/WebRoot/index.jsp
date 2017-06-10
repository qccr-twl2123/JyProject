<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
  %>  
 <!DOCTYPE>
<html>
    <head>
    	<title>九鱼网-每笔消费 必有优惠</title>
    </head>
  <body>
  <script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
  <script type="text/javascript">
      $(function(){
    	  $.ajax({
    	       	type:"post",
    	       	url:"https://api.map.baidu.com/highacciploc/v1?qcip=&callback_type=jsonp&qterm=pc&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&coord=bd09ll", 
    		        dataType:"jsonp",
    		        success: function(data){
    		        	var result=data.result;
    		        	var content=data.content;
    		        	if(result.error == 161){
    		        		pcd(content.location.lng,content.location.lat);
    		        	}
    		        }
    		   });
    			//精确----->获取省市区
    	  		function pcd(lng,lat){
    		  		$.ajax({
    		 	         	type:"post",
    		 	         	url:"https://api.map.baidu.com/geocoder/v2/?ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&callback=renderReverse&location="+lat+","+lng+"&output=json&pois=1", 
    		 		        dataType:"jsonp",
    		 		        success: function(data){
    		 		        	var result=data.result;
    		 		        	if(data.status == "0"){
    		 		        		document.location = '<%=basePath%>jiuyusy.do?city_name='+result.addressComponent.city+'&area_name='+result.addressComponent.district+'&lat='+lat+'&lng='+lng+'&s_l=2&fd_ss=0'; 
    		  		        	}
    		 		        }
    		 		   });
    		  	}	
      });
      
  	/* String url = request.getScheme()+"://"+request.getServerName()+"/jiuyusy.do"; 
	response.sendRedirect(url); */
      </script>
  </body> 
</html>
