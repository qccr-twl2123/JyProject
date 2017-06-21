<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="css/htmlmember/style.css">
	<link rel="stylesheet" href="css/htmlmember/styles.css" type="text/css">
	<style type="text/css">
	.lingqu {
	    background: #ffb900;
	    border: none;
	    width: 60px;
	    height: 25px;
	    line-height: 25px;
	    color: #fff;
	    text-align: center;
	    border-radius: 5px;
	    margin-top: 10px;
	}
	</style>
 </head>
<body style="background:#ededed;">
	<nav class="top">
		<a href="html_me/textDesc.do?type=6" class="fr" style="margin-right:5px;"><i class="order-two"></i>红包说明</a>
		<a href="html_member/gouShouYe.do"><b class="back-arrow fl"></b></a>
		<div style="text-align:center;line-height:40px;color:#fff">抢红包</div>
	</nav>
	<div class="dress clearfix">
		<a class="dress-again" onclick="chongxindingwei()"><i></i>重新定位</a>
		<span><i></i>${pd.city_name}${pd.area_name}${pd.address}</span>	
	</div>
	<div class="qhb-banner" id="allavert">
	
	</div>
	<div class="hb-list">
		<ul id="redList">
		
		</ul>
	</div>
</body>
<script src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
var base_inf={
        base_herf:"<%=basePath%>",
        city_name:"${pd.city_name}",
        area_name:"${pd.area_name}",
        address:"${pd.address}",
        longitude:"${pd.longitude}",
        latitude:"${pd.latitude}"
 };
 </script>
<script src="js/wx/jweixin-1.0.0.js"></script>
<script src="js/wx/zepto.min.js"></script>
<script src="js/htmlmember/weixindemo.js"></script>
<script type="text/javascript">
 var base_inf={
         base_herf:"<%=basePath%>",
         city_name:"${pd.city_name}",
         area_name:"${pd.area_name}",
         address:"${pd.address}",
         longitude:"${pd.longitude}",
         latitude:"${pd.latitude}"
  };
 
//获取省市区
 function pcd(lng,lat){
 	$.ajax({
         	type:"post",
         	url:"https://api.map.baidu.com/geocoder/v2/?ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&callback=renderReverse&location="+lat+","+lng+"&output=json&pois=1", 
 	        dataType:"jsonp",
 	        success: function(data){
 	        	if(data.status == "0"){
 		        	var result=data.result;
 		        	base_inf.province_name=result.addressComponent.province;
 		        	base_inf.city_name=result.addressComponent.city;
 		        	base_inf.area_name=result.addressComponent.district;
 		        	base_inf.address=result.addressComponent.street;
 		        	base_inf.longitude=lng;
 		        	base_inf.latitude=lat;
 		        	shaixin();
  		        }
 	        }
 	   });
 }
 //重新定位
 function chongxindingwei(){
 	  //微信定位
 	  wxdingwei();
 }
 
 function shaixin(){
	 $.ajax({
			type:"post",
			url:base_inf.base_herf+'html_member/ajaxGetFuJinRed.do',
			data:{
				longitude: base_inf.longitude,
				latitude: base_inf.latitude,
				province_name: base_inf.province_name,
				city_name: base_inf.city_name,
				area_name: base_inf.area_name,
				address: base_inf.address  
			},
			success:function(data){
				 var map1=data.data;
				 $("#redList").empty();
				 $("#allavert").empty();
 				 if(map1 != "" && map1 != "0"){
					 var advertList=map1.advertList;
					 for(var i=0;i<advertList.length;i++){
						 $("#allavert").append("<img src='"+advertList[i].image_url+"'/>");
					 }
					 var redList=map1.redList;
					 for(var i=0;i<redList.length;i++){
						 var onlyred=redList[i];
						 var redpackage_type=onlyred.redpackage_type;
						 var redtypestr="";
						 if(redpackage_type == "1"){
							 redtypestr="<p><span id ='"+onlyred.store_redpackets_id+"money'>"+onlyred.money+"</span>元</p>";
						 }else{
							 redtypestr="<p><span id ='"+onlyred.store_redpackets_id+"money'>"+onlyred.money+"</span>折</p>";
						 }
						 var str="<li>"+
									"<span  class='hb-list-left fl'>"+
										redtypestr+
										"<p>红包</p>"+
									"</span>"+
									"<div class='hb-list-right fl'>"+
										"<span class=\"fr lingqu\" onclick=\"getRed(\'"+onlyred.store_redpackets_id+"\')\">立即领取</span>"+
										"<span>"+
											"<p>[商家红包]"+onlyred.store_name+"</p>"+
											"<p><i class='hb-list-time'></i>有效期至"+onlyred.endtime+"</p>"+
											"<p><i class='hb-list-coupon'></i>"+onlyred.srp_usercondition_name+"</p>"+
										"</span>"+
									"</div>"+
								"</li>";
						 $("#redList").append(str);
					 }
				 }else{
					 alert(data.message);
					 return;
				 }
			}
	});
 }

	
	//领取红包
 	function getRed(store_redpackets_id){
		var money = $("#"+store_redpackets_id+"money").text();
		$.ajax({
				type : "post",
				url  : base_inf.base_herf+'html_member/ajaxGetRedPackage.do',
				data : {
					money : money ,
					store_redpackets_id : store_redpackets_id
				},
				success:function(data){
   					if(data.result == "1"){
						alert("领取成功！");
						window.location.reload();
	 				}else if(data.result == "0"){
 	 					alert(data.message);
	 				}
			}
		});
	}

	
	$(function(){
		shaixin();
	});
</script>
</html>
