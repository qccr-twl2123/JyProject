<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>定位门店</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>">
        <link rel="stylesheet" href="css/storepc/business_apply2.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/storepc/business_apply2.js"></script>
        <style type="text/css">
			body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
			a:hover{
				color:red;
			}
		</style>
 		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9"></script>
    </head>
    <body>
        <!-- header -->
       <div class="signin_header">
           <span class="signin_logo">
               <img src="img/logo.png" width="100%" height="100%">
           </span>
           <span class="signin_header_sp1">定位门店地址 </span>
           <div style="float:right;margin-top:2%;margin-right:2%;">
	           <a href="<%=path %>/jsp/storepc/business_index.jsp">
	             <span class="signin_homepage">
	               <p class="signin_homepage_img">
	                   <img src="img/homepage.png">
	               </p>
	               <p>首页</p>
	          	 </span>
	          </a>
           </div>
        </div>
       <!-- body -->
       <div class="red">   注意：门店定位后设定后不可更改！请务必提高精准度，否则很可能无法通过审核。 </div>
       <div class="black">请选择关键字:<input type="text" id="suggestId" value=""  />(选择提示框的内容)</div>
       <div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
           <input name="phone" type="hidden" id="phone" value="${pd.phone}"/>
	       <div class="signin_body">
 	            <!-- 地图地位位置 -->
	            <div id="allmap"></div>
	            <!--  -->
 	       </div>
        <!-- footer -->
       <div class="signin_footer">
          <div class="footer_d1">
          		 <span class="index_footer_sp1"></span>
            	<a href="<%=path %>/storepc/goRegister.do" ><span class="index_footer_sp1 mgleft20">我要开店</span></a>
                <span class="index_footer_sp1"> <a  href="<%=basePath%>jsp/storepc/gyjy.html"  target="_blank">关于九鱼</a></span>
                <span class="index_footer_sp1">加入我们</span>
          </div>
           <div class="footer_d2">
            [浙]ICP备16025718号 本站发布所有内容，未经许可，不得转载
          </div>  
       </div>
       <script type="text/javascript">
        var lng="${lng}";
        var lat="${lat}";
        var province_name="${province_name}";
        var city_name="${city_name}";
        var area_name="${area_name}";
        var address="${address}";
	    // 百度地图API功能（定位到当前位置）
	   	var map = new BMap.Map("allmap",{enableMapClick:false});
	   	var point = new BMap.Point(lng,lat);
	   	map.centerAndZoom(point,17);
	   	map.enableScrollWheelZoom(true);
	   	var mk = new BMap.Marker(point);
	   	map.addOverlay(mk);
   		//单击获取点击的经纬度 ，获取当前地址
   		var geoc = new BMap.Geocoder();   
   		map.addEventListener("click", function(e){        
		var pt = e.point;
		lng=pt.lng;
		lat=pt.lat;
		//alert(pt.lng + "," + pt.lat);
		geoc.getLocation(pt, function(rs){
			var addComp = rs.addressComponents;
			province_name=addComp.province;
			city_name=addComp.city;
			area_name=addComp.district;
			address=addComp.city+addComp.district+addComp.street+addComp.streetNumber;
		});   
		//文本显示
		var opts = {
		  width : 100,     // 信息窗口宽度
		  height: 40     // 信息窗口高度
  		};
		point = new BMap.Point(lng,lat);
		var infoWindow = new BMap.InfoWindow("<br/><a onclick=\"sureStore()\" style=\"color:blue;cursor:pointer;\">设为门店</buttom>", opts);  // 创建信息窗口对象 
		map.openInfoWindow(infoWindow,point); //开启信息窗口
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
		G("searchResultPanel").innerHTML = str;
	});

	var myValue;
	ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
	var _value = e.item.value;
		myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
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
</html> --%>