 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>首页</title>
        <base href="<%=basePath%>">
        <link rel="stylesheet" href="css/storepc/business_homepage4.css">
        <script src="js/jquery-1.8.0.min.js"></script>
         <script type="text/javascript" src="js/storepc/highcharts.js"></script>
        <script type="text/javascript">
        $(function(){
        	Chart(${num1}, ${num2}, ${num3}, ${num4}, ${num5}, ${num6}, ${num7}, ${num8}, ${num9}, ${num10}, ${num11}, ${num12}, ${num13}, ${num14}, ${num15},${mon1}, ${mon2}, ${mon3}, ${mon4}, ${mon5}, ${mon6}, ${mon7}, ${mon8}, ${mon9}, ${mon10}, ${mon11}, ${mon12}, ${mon13}, ${mon14}, ${mon15});
        });
         </script>
    </head>
<body>
	<c:if test="${storeqx.look eq '1'}">
       <div class="parent">
           <script type="text/javascript">
           		function Chart(num1, num2, num3, num4, num5, num6, num7, num8, num9, num10, num11, num12, num13, num14, num15,mon1, mon2, mon3, mon4, mon5, mon6, mon7, mon8, mon9, mon10, mon11, mon12, mon13, mon14, mon15)
           		{
	               
	                
	                $(document).ready(function() {
	                   var chart = new Highcharts.Chart({
	                    chart: {
	                      renderTo: 'container',
	                      margin: [100, 250, 60, 50]
	                    },
	                    xAxis: {
	                      categories: ['前1天', '前2天', '前3天', '前4天', '前5天', '前6天', 
	                        '前7天', '前8天', '前9天', '前10天', '前11天', '前12天','前13天','前14天','前15天'],
 	                    },
 	                    series: [{
 	                    	 	color: "#3e9ecb",
	                      		name: '订单量',
	                      		data: [num1, num2, num3, num4, num5, num6, num7, num8, num9, num10, num11, num12, num13, num14, num15]
	                    } ]
	                  });
  	                });
	                
	                $(document).ready(function() {
	                	var chart = new Highcharts.Chart({
		                    chart: {
		                      renderTo: 'container2',
		                      margin: [50, 250, 60, 50]
		                    },
		                    xAxis: {
		                      categories: ['前1天', '前2天', '前3天', '前4天', '前5天', '前6天', 
		                        '前7天', '前8天', '前9天', '前10天', '前11天', '前12天','前13天','前14天','前15天'],
		                      
		                    },
  		                    series: [ {
  		                      color: "#df8282",
 		                      name: '销售额',
		                      data: [mon1, mon2, mon3, mon4, mon5, mon6, mon7, mon8, mon9, mon10, mon11, mon12, mon13, mon14, mon15]
		                    }]
		                  });
		                  // 这里可以显示4条 统计线条
		                  
		                });
              	}
                </script>
      
	        <div id="container" style="margin-left: 160px; width: 900px;height:400px;"></div>
	        <div id="container2" style="margin-left: 160px; width: 900px;height:400px;"></div>
          </div>
       </c:if>

    </body>
</html>