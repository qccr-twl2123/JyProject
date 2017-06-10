 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
         <title>九鱼网-每笔消费 必有优惠</title>
         <meta http-equiv="content-type" content="text/html;charset=utf-8">
         <meta name="Keywords" Content="九鱼销链,九鱼销链加盟项目,九鱼销链官网,九鱼销链加盟费,九鱼销链加盟流程,九鱼销链加盟条件">
		 <meta name="Description" content="九鱼网-每笔消费 必有优惠,九鱼销链为您提供九鱼销链加盟费多少钱,九鱼销链加盟条件等相关招商加盟信息.更多九鱼销链加盟信息尽在中华创业网">
         <link rel="shortcut icon" href="<%=basePath%>favicon.ico" >
         <link rel="Bookmark" href="<%=basePath%>favicon.ico">
   		 <link rel="icon" type="image/gif" href="<%=basePath%>animated_favicon1.gif" >
         <link rel="stylesheet" href="<%=basePath%>css/memberpc/business_Buyers1.css">
          <link rel="stylesheet" href="<%=basePath%>css/memberpc/swiper.min.css">
          <style type="text/css">
			a{
				outline:0;			
			}          
          </style>
       </head>
    <body>
         <div class="Buyers_body" style=" width: 1200px;">
	            <!-- 顶部导航 -->
            <div class="Buyers_hnav">
                <span class="Buyers_hnav_sp1" >  
                	<a href="<%=basePath%>storepc/goLogin.do" > 商家登录 </a>
                 </span> 
                <span class="Buyers_hnav_sp1">
                	<a href="<%=basePath%>storepc/goRegister.do" > 商家加盟 </a>
                </span> 
                <span class="Buyers_hnav_sp2"> 常见问题 </span>
                 <c:if test="${empty memberpd}">
                	 <span class="Buyers_hnav_sp2">
                	 	<a href="<%=basePath%>memberpc/goMemberRegister.do">会员注册</a> 
                	 </span>
                	 <span class="Buyers_hnav_sp2">
                	 	<a href="<%=basePath%>memberpc/goMemberLogin.do" >会员登录</a>
                	 </span>
	                <span class="Buyers_hnav_sp2"> 
		                <a href="#"> 我的订单 </a>
	                 </span>
	                <span class="Buyers_hnav_sp2">
                	 	<a href="#" >我的九鱼 </a>
                	</span><!-- 个人中心 -->
                </c:if>
                <c:if test="${!empty memberpd}">
                  	<span class="Buyers_hnav_sp2">
                  		<a href="<%=basePath%>memberpc/loginOut.do" style="color:red;">退出</a>
                  	</span>
	                <a href="<%=basePath%>memberorderPc/orderInfoList.do">
		                <span class="Buyers_hnav_sp2">
		                	我的订单
		                </span>
	                </a>
	                <span class="Buyers_hnav_sp2">
                		<a href="<%=basePath%>memberpc/memberInfoList.do" >我的九鱼 </a>
                	</span><!-- 个人中心 -->
                </c:if>
              </div>
             <!-- 头部 -->
            <div class="Buyers_header">
                 <a href="<%=basePath%>"><span class="Buyers_header_logo">  <img src="<%=basePath%>img/logo.png"></span></a>
                <span class="Buyers_header_sp1">
                	<select class="Buyers_p2" name="city_name" onchange="search('0')" id="city_name" style=" width: 92px; height: 40px;"> 
                		<option value="">请选择</option>
                	</select>
                 </span>
                <span class="Buyers_header_sp2" style="    margin-left: 37px;width: 59%;">
                    <p>
                        <input placeholder="根据商家名称/地址进行检索"  type="text" class="Buyers_header_sp2_input" name="content" id="content" maxlength="40" />
                        <span class="Buyers_header_sp2_sp sousuoparent" onclick="checked('','')" >
                        	<img src="<%=basePath%>img/b_ss.png" style="width:30px;height:30px" class="sousuoimag" />
                        </span>
                    </p>
                    <!-- 关键字 -->
                    <p class="Buyers_header_p2" id="keywordList">
                    	<%-- <c:forEach items="${keywordList}" var="infor" end="5">
                    	  <span class="" style="width: 4em"><a href="#">${infor}</a></span>
                    	</c:forEach>  --%>
                      </p> 
                </span>
                <span class="Buyers_header_sp3_w" style="margin-left: 8%;width: 147px;height: 53px;"> <a target="_blank”" href="http://www.jiuyuvip.com/FileSave/zhihuiPC/business_erweima2.html" > <img src="<%=basePath%>img/downmember.png" /> </a> </span>
                <div class="erweimaclass" style="width: 130px; height: 130px; position: relative; top: -102%; left: 75%; display: none;"> <img src="<%=basePath%>img/downmemberapp.png" width="100%" />   </div>
            </div>
            <!-- 导航 -->
            <div class="Buyers_nav">
                <span class="Buyers_nav_sp1"  style="position: relative;">
                    	全部分类
                    	<div class="all-nav">
                    		<ul id="allfenglei"> </ul>
                    	</div>
                </span>
                <span class="Buyers_nav_sp2 nav_active"> <a href="#">首页</a> </span>
                <span class="Buyers_nav_sp2"> <a onclick="checked('','6')">最新上线</a> </span>
                <span class="Buyers_nav_sp2">  <a onclick="checked('1','')">首单立减</a> </span> 
                <span class="Buyers_nav_sp2"> <a onclick="checked('','1')">智能排序</a> </span>
                <span class="Buyers_nav_sp2"> <a onclick="checked('','3')">人气递减 </a> </span>
                <span class="Buyers_nav_sp2"> <a onclick="checked('','2')">距离近到远 </a> </span>
                <span class="Buyers_nav_sp2"> <a onclick="checked('','5')"> 销量递减  </a>  </span>
                <span class="Buyers_nav_sp2"> <a onclick="checked('','4')">积分率递减 </a> </span>
            </div>
            <!-- banner -->
            <div class="Buyers_coutent" > 
            	<div class="Buyers_coutent_rt">
                    <p class="Buyers_coutent_rt_p1" style="border:none;">
                          <span><span class="Buyers_coutent_rt_p1_img"><img src="<%=basePath%>img/b_ding.png"></span> 全部地区 </span>
                          <span class="blue">
                            	<select class="Buyers_p2" name="area_name" id="area_name" onchange="search('1')" style="height:23px; margin-top: 12px;">
                              		<option value="">请选择</option>
                              	</select>
                         </span>
                    </p>
                    <div style="width:80%;float:left;">
                    <div class="swiper-container" >
                         	    <!-- 广告位 -->
                                <div class="swiper-wrapper" id="advertList"> </div>
                                <div class="swiper-button-prev"> </div>
                                <div class="swiper-button-next"> </div>
                     </div>
                     </div>
                      <div class="Buyers_gg" style="float:right">
		                <div class="Buyers_gg_top">
		                    <span class="Buyers_gg_top_sp1">
		                        <img src="<%=basePath%>img/b_gg.png">
		                    </span>
		                    <span class="Buyers_gg_top_sp2">
		                        <p class="Buyers_gg_top_sp2_p1">积分变钱</p>
		                        <p class="Buyers_gg_top_sp2_p2">1积分等于1元</p>
		                    </span>
		                    <!-- <span class="Buyers_gg_top_sp3">
		                        <img src="../img/b_gg_out.png">
		                    </span> -->
		                </div>
		                <div class="Buyers_gg_mid">
		                    <img src="<%=basePath%>img/b_2wm.png">
		                </div>
		                <div class="Buyers_gg_btm">
		                    <p>扫描二维码</p>
		                    <p>关注九鱼网官方微信</p>
		                    <p class="red">每笔消费 必有优惠</p>
		                </div>
		            </div>
                </div>
            </div>
            <div class="Buyers_left">
                <dl id="zuobianallfenglei">
                 </dl>
            </div>
            <form action="<%=basePath%>memberpc/tzall.do" name="storeForm" id="storeForm" method="post">
  		    <input name="store_id"  class="store_id"  type="hidden"/>
 		    <input name="city_name"  class="${pd.city_name}"  type="hidden"/>
 		    <div id="allstore">
	 		    <!-- 美食一级分类的循环 -->
	            <c:forEach items="${firstList}" var="sortvar" >
	                <c:if test="${!empty sortvar.storeList}">
		            	<div class="Buyers_classify" id="${sortvar.city_file_sort_id}">
			                <div class="Buyers_classify_d1">
			                    <span class="">
			                        <img src="${sortvar.sort_imageurl}" style="width:50px;height:50px;margin-top:37px;">
			                    </span>
			                   ${sortvar.sort_name}
	 		                </div>
	 		                <!-- 小类循环 -->
			                <span> 
			                	<a  style="font-size: 20px" onclick="checkedByCitySort('${sortvar.city_file_sort_id}','${sortvar.sort_type}','${sortvar.sort_name}')">全部 </a> 
			                </span>
			                <c:forEach items="${sortvar.twoList}" var="twosortvar" end="6">
	   		                		<span><a onclick="checkedByCitySort('${twosortvar.city_file_sort_id}','${twosortvar.sort_type}','${twosortvar.sort_name}')">${twosortvar.sort_name}</a></span>
	  		                </c:forEach>
			            </div>
			            <div class="Buyers_classify2">
 			            		<!-- 商家循环 -->
				            	<c:forEach items="${sortvar.storeList}" var="storevar"  varStatus="vsindex">
				            		<c:if test="${vsindex.index+1 <= 15}">
		  				                <span class="Buyers_classify2_sp">
			 				                <a onclick="goStoreDetail('${storevar.store_id}')">
							                    <p class="Buyers_classify2_img"> <img src="${storevar.pictrue_url}"> </p>
							                    <p class="Buyers_classify2_text">
							                        <span class="Buyers_classify2_text_sp1">
							                            <span class="wstorename">&nbsp;&nbsp;&nbsp;&nbsp;${storevar.store_name}</span>
							                            <c:if test="${storevar.one ne ''}">
							                            	<span class="Buyers_classify2_text_sp1_j">${storevar.one}</span>
							                            </c:if>
							                            <c:if test="${storevar.two ne ''}">
							                            	 <span class="Buyers_classify2_text_sp1_j">${storevar.two}</span>
							                            </c:if>
			 				                           <%--  <span class="Buyers_classify2_text_sp1_rt">${storevar.zan_times}<span><img src="../img/b_zan.png"></span></span> --%>
							                        </span>
							                    </p>
							                    <p></p>
							                    <p class="Buyers_classify2_text">
							                    	<!-- 星级 -->
							                        <span class="Buyers_classify2_text_lt "> 
		 					                        	<c:if test="${storevar.comment_star eq '0' }">
							                        	<img src="<%=basePath%>img/new_star0.png" width="100px">
							                        	</c:if>
		 					                        	<c:if test="${storevar.comment_star eq '1' }">
							                        	<img src="<%=basePath%>img/new_star1.png" width="100px">
							                        	</c:if>
							                        	<c:if test="${storevar.comment_star eq '2' }">
							                        	<img src="<%=basePath%>img/new_star2.png">
							                        	</c:if>
							                        	<c:if test="${storevar.comment_star eq '3' }">
							                        	<img src="<%=basePath%>img/new_star3.png">
							                        	</c:if>
							                        	<c:if test="${storevar.comment_star eq '4' }">
							                        	<img src="<%=basePath%>img/new_star4.png">
							                        	</c:if>
							                        	<c:if test="${storevar.comment_star eq '5' }">
							                        	<img src="<%=basePath%>img/new_star5.png">
							                        	</c:if>
			 				                        </span>
			 				                        <span class="Buyers_classify2_text_rt"> 已出售${storevar.transaction_times}件 </span>
							                    </p>
							                     </a>
							                </span>
						                </c:if>
		  		            	</c:forEach>
 			            </div>
			            </c:if>
	            </c:forEach>
  		     </div>
             </form>
             <!-- footer -->
	       <div class="signin_footer">
	          <div class="footer_d1">
	            	<span class="index_footer_sp1 mgleft20"><a href="<%=basePath%>storepc/goRegister.do" >我要开店</a></span>
	                <span class="index_footer_sp1"> <a  href="<%=basePath%>jsp/storepc/gyjy.html"  target="_blank">关于九鱼</a></span>
	                 <span class="index_footer_sp1">加入我们</span>
	          </div>
	          <div class="footer_d2" style="color:#000;">
	             [浙] ICP备16025718号-2 本站发布所有内容，未经许可，不得转载
	          </div> 
	       </div>
            
            <!-- 悬浮 -->
            <!-- <div id="whw">
            <div class="Buyers_righst" style="width:58px;">
                  <a href="#"><span class="Buyers_righst_img"><img src="../img/b_top.png"></span></a>
                 <span class="xian"></span>
            </div>
            </div> -->
            <form action="<%=basePath%>memberpc_shouye/sytzall.do" name="syForm" id="syForm" method="post">
  		            		<input name="area_name"  class="area_name"  type="hidden"/>
		            		<input name="city_name"  class="city_name"  type="hidden"/>
		            		<input name="province_name"  class="province_name"  type="hidden"/>
		            		<input name="longitude"  class="longitude"  type="hidden"/>
		            		<input name="latitude"  class="latitude"  type="hidden"/>
		            		<input name="flag"  class="flag"  type="hidden" value="0"/>
		    </form>
            <form action="<%=basePath%>memberpc_shouye/stzall.do" name="clickForm" id="clickForm" method="post">
  		            		<input name="area_name"  class="area_name"  type="hidden"/>
		            		<input name="city_name"  class="city_name"  type="hidden"/>
 		            		<input name="paixu"  class="paixu"  type="hidden"/>
		            		<input name="shaixuan"  class="shaixuan"  type="hidden"/>
		            		<input name="content"  class="content"  type="hidden" />
		            		<input name="city_file_sort_id"  class="city_file_sort_id"  type="hidden" />
		            		<input name="sort_type"  class="sort_type"  type="hidden" />
		            		<input name="sort_name"  class="content"  type="hidden" />
		            		<input name="flag"  class="flag"  type="hidden" value="1"/>
		            		<input name="infor_text"  class="infor_text"  type="hidden" value=""/>
		    </form>
		    </div>
           <script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
         <script src="<%=basePath%>js/memberpc/swiper.min.js"></script>
          <script src="<%=basePath%>js/memberpc/business_Buyers1.js"></script>
<!--     <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9"></script>-->        <script type="text/javascript">
        //var wlng="${lng},${lat}";
    	$(function (){
      		if("${flag}" != "1"){
    			//gogogo1();
    			gogogo2();
          	}else{
        		getShouyeList("${pd.province_name}","${pd.city_name}","${pd.area_name}","${pd.lng}","${pd.lat}");
        	}
    		
    		
    		//普通定位定位开始
    		function gogogo1(){
    			$.ajax({
    	         	type:"post",
    	         	url:"https://api.map.baidu.com/location/ip?ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&coor=bd09ll", 
    		        dataType:"jsonp",
    		        success: function(data){
     		        	var content=data.content;
     		        	var address_detail=content.address_detail;
     		        	var province_name=address_detail.province;
     		        	var city_name=address_detail.city;
     		        	var area_name=address_detail.district;
     		        	var point=content.point;
     		        	var lng=point.x;
     		        	var lat=point.y;
     		        	getShouyeList(province_name,city_name,area_name,lng,lat);
     		        }
    		   });
    		}
    		
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
      		        		pcd(content.location.lng,content.location.lat);
     		        	}
     		        }
    		   });
    		}
    		

    		//精确----->获取省市区
        	function pcd(lng,lat){
        		$.ajax({
       	         	type:"post",
       	         	url:"https://api.map.baidu.com/geocoder/v2/?ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&callback=renderReverse&location="+lat+","+lng+"&output=json&pois=1", 
       		        dataType:"jsonp",
       		        success: function(data){
       		        	if(data.status == "0"){
    	   		        	var result=data.result;
          			  		getShouyeList(result.addressComponent.province,result.addressComponent.city,result.addressComponent.district,lng,lat);
       		        	}
       		        }
       		   });
        	}	 
     	
    		
    	    //首页数据
    	   function getShouyeList(province_name,city_name,area_name,lng,lat){
	    		$.ajax({
	   	         	type:"post",
	   	         	url:"<%=basePath%>memberpc_shouye/ajaxpcMemberSy.do", 
	   	         	data:{
	   	         		"province_name":province_name,
	   	         		"city_name":city_name,
	   	         		"area_name":area_name,
	   	         		"lng":lng,
	   	         		"lat":lat,
	   	         		"flag":"0" 
 	   	         	},
	   		        dataType:"json",
	   		        success: function(data){
	   		        	 //关键字
	   		        	 var keywordList=data.keywordList;
	   		        	 if(keywordList != null && keywordList != ''){
	   		        		for (var i = 0; i < keywordList.length; i++) {
								$("#keywordList").append("<span class='' style='width: 4em'><a onclick='infor_text(this)'>"+keywordList[i]+"</a></span>");
						 	}
	   		        	 }
   	   		        	  //广告位
	   		        	 var pcadvert=data.pcadvert;
	   		        	if(pcadvert != null && pcadvert != ''){
	   		        		for (var i = 0; i < pcadvert.length; i++) {
								$("#advertList").append("<div hyperlink_url='"+pcadvert[i].hyperlink_url+"' onclick='tiaozhuan(this)' class='swiper-slide' > <img src="+pcadvert[i].image_url+"></div> ");
							 }
	   		        	}
		   		         var swiper = new Swiper('.swiper-container', {
		   			        nextButton: '.swiper-button-next',
		   			        prevButton: '.swiper-button-prev',
		   			        autoplay : 2000,//可选选项，自动滑动
		   					loop : true,//可选选项，开启循环
		   			    })
 	   		        	 
	   		        	 //城市
	   		        	var cityList=data.cityList;
	   		        	if(cityList != null && cityList != ''){
	   		        		for (var i = 0; i < cityList.length; i++) {
	  	   		        		 if(cityList[i].city_name == city_name){
	  	   		        			$("#city_name").append("<option value="+cityList[i].city_name+"  selected>"+cityList[i].city_name+"</option>");
		   		        		 }else{
		   		        			$("#city_name").append("<option value="+cityList[i].city_name+"  >"+cityList[i].city_name+"</option>");
		   		        		 }
								
							 }
	   		        	}
 	   		        	
	   		        	 //区域
	   		        	 var areaList=data.areaList;
						if(areaList != null && areaList != ''){
							for (var i = 0; i < areaList.length; i++) {
		   		        		if(areaList[i].area_name == area_name){
	  	   		        			$("#area_name").append("<option value="+areaList[i].area_name+"  selected>"+areaList[i].area_name+"</option>");
		   		        		 }else{
		   		        			$("#area_name").append("<option value="+areaList[i].area_name+"  >"+areaList[i].area_name+"</option>");
		   		        		 }
							 }
	   		        	}
 	   		        	//种类1
	   		        	var firstList=data.firstList;
	   		        	if(firstList != null && firstList != ''){
	   		        		for (var i = 0; i < firstList.length; i++) {
		   		        		 var str1="<li w_type=\"one\"><a onclick=\"checkedByCitySort(\'"+firstList[i].city_file_sort_id+"\',\'"+firstList[i].sort_type+"\',\'"+firstList[i].sort_name+"\')\">"+firstList[i].sort_name+"</a><div class=\"all-nav-list\"><ul>";
			   		        	 var str2="";
			   		        	 var twoList=firstList[i].twoList;
			   		        	 if(twoList != null && twoList != ''){
			   		        		for (var j = 0; j < twoList.length; j++) {
				   		        		str2+="<li w_type=\"two\"><a onclick=\"checkedByCitySort(\'"+twoList[j].city_file_sort_id+"\',\'"+twoList[j].sort_type+"\',\'"+twoList[j].sort_name+"\')\">"+twoList[j].sort_name+"</a></li>";
									 }
			   		        	 }
 	 				         	 var str3="</ul></div></li>";	
	 				         	 $("#allfenglei").append(str1+str2+str3);
							 }
	   		        	}
		   		        	 //js
		   		        	$(".Buyers_nav_sp1").hover(function(){
		   			       		$(".all-nav").css("display","block");
		   			       	},function(){
		   			       		$(".all-nav").css("display","none");
		   			       	});
	 	   			       	$(".all-nav li").hover(function(){
		   			       		$(this).children().css("display","block");
		   			       		//隐藏显示
		   			         		$(this).hover(function(){
		   			       			//..................
		   			            	},function(){
		   			             		if($(this).attr("w_type") == "one"){
		   			             			$(this).children(".all-nav-list").css("display","none");
		   			            		}
		   			            	});
		   			       	});
	 	   			       	
		   		        	 //左边列表
	 	   		        	 for (var i = 0; i < firstList.length; i++) {
	 	   		        		 var storeList=firstList[i].storeList;
	 	   		        		 if(storeList != null && storeList != '' && storeList.length >0){
 	 	   		        			 var str1=" <a href='#"+firstList[i].city_file_sort_id+"'  id='"+(i+1)+"'><dd  class='dd1'>";
	 	   		        		 	 var str2="<span class='dd_sp1'><img src='"+firstList[i].sort_imageurl+"' style='width:38px;height:38px;margin:7px 0 0 16px;'></span><span class='dd_sp2'>"+firstList[i].sort_name+"</span>";
	 	   		        			 var str3="</dd> </a>";
	 	   		        		 	 $("#zuobianallfenglei").append(str1+str2+str3);
	 	   		        		 }
	 						 }
		   		        	 
		   		        	 
	 	   		       		//商家集合
	 	   		        	 for (var m = 0; m < firstList.length; m++) {
	   	   		        			 var str1="<div class='Buyers_classify' id='"+firstList[m].city_file_sort_id+"'> "+
	   	   		        			 			"<div class='Buyers_classify_d1'> <span><img src='"+firstList[m].sort_imageurl+"' style='width:50px;height:50px;margin-top:37px;'> </span>"+firstList[m].sort_name+"</div>";
	  	   		        		 	 var str2="<span><a  style=\"font-size: 20px\" onclick=\"checkedByCitySort(\'"+firstList[m].city_file_sort_id+"\',\'"+firstList[m].sort_type+"\',\'"+firstList[m].sort_name+"\')\">全部 </a>   </span>";
	  	   		        		 	 var str3="";
	  	   		        		 	 var twoList=firstList[m].twoList;
 		  	   		        		 if(twoList != null && twoList != ''){
		  	   		        			for (var x = 0 ; x < twoList.length ; x++) {
				   		        		 	if(x <6){
				   		        		 		var s="<span><a onclick=\"checkedByCitySort(\'"+twoList[x].city_file_sort_id+"\',\'"+twoList[x].sort_type+"\',\'"+twoList[x].sort_name+"\')\">"+twoList[x].sort_name+"</a></span>";
				   		        				str3+=s;
				   		        		 	}
	 								 	}
		  	   		        		 }   
  	  	   		        			var str5="";
 	   	   		        		 	var storeList=firstList[m].storeList;
 	   	   		        			if(storeList != null && storeList != '' ){
	 	   	   		        			for (var k = 0; k < storeList.length; k++) {
		   	   		        				if(k<15){
			   	   		        				var str6="<span class=\"Buyers_classify2_sp\" ><a onclick=\"goStoreDetail(\'"+storeList[k].store_id+"\')\">";
			   	   		        				var str7="<p class='Buyers_classify2_img'> <img src='"+storeList[k].pictrue_url+" ' style='width: 140px; height: 140px; '> </p>";
			   	   		        				var str8="<p class='Buyers_classify2_text'> <span class='Buyers_classify2_text_sp1'> <span class='wstorename'>&nbsp;&nbsp;&nbsp;&nbsp;"+storeList[k].store_name+"</span>";
			   	   		        				var str9="";
			   	   		        				if(storeList[k].one != "" || storeList[k].two != ""){
			   	   		        					str9="<span class='Buyers_classify2_text_sp1_j'>"+storeList[k].one+storeList[k].two+"</span> ";
			   	   		        				} 
			   	   		        				var str10="</span></p> <p></p> <p class='Buyers_classify2_text'><span class='Buyers_classify2_text_lt '> ";
			   	   		        				var n=storeList[k].comment_star;
			   	   		        				var str11="<img src='../../img/new_star"+n+".png' width='100px'>";
			     	   		        			var str12=" </span><span class='Buyers_classify2_text_rt'> 已出售"+storeList[k].transaction_times+"件 </span> </p> </a> </span>";
			     	   		        			var str13=str6+str7+str8+str9+str10+str11+str12;
			     	   		        			str5+=str13;
		   	   		        				}
		    	   		        		}
	   	   		        			}
  	   	   		        			if(storeList.length >0 ){
	   	   		        				$("#allstore").append(str1+str2+str3+"</div><div class='Buyers_classify2'>"+str5+"</div>");
	   	   		        			}
 	 	   		        	} 
    	   		        }
	   		   });
	    	}
 
	       	    //隐藏显示
	     		$(".Buyers_header_sp3_w").hover(function(){
	   				$(".erweimaclass").show();
	        	},function(){
	        		$(".erweimaclass").hide();
	        	});
	       	    
	 	});
    	
    	
    	
    	
    	
        
        //切换城市
        function search(value){
 	        	var area_name="";
	        	if(value == "1"){
	        		area_name=$("#area_name").val();
	        	} 
	        	var city_name=$("#city_name").val();
        		$(".province_name").val("");
	       		$(".city_name").val(city_name);
	       		$(".area_name").val(area_name);
	       		$(".address").val("");
	       		$(".flag").val("1");
		  		$("#syForm").submit();
         }
        
        //点击关键字
        function infor_text(obj){
    	  	var infor_text=$(obj).html();
    	  	$(".infor_text").val(infor_text);
        	var city_name=$("#city_name").val();
        	$(".city_name").val(city_name);
        	var area_name=$("#area_name").val();
        	$(".area_name").val(area_name);
        	var content=$("#content").val();
        	$(".content").val(content);
        	$(".shaixuan").val("");
       		$(".paixu").val("");
       		$(".sort_name").val("全部分类");
       		$(".flag").val("1");
 	  		$("#clickForm").submit();
        }
        
        
        //筛选
        function checked(shaixuan,paixu){
        	var city_name=$("#city_name").val();
        	var area_name=$("#area_name").val();
        	var content=$("#content").val();
        	$(".city_name").val(city_name);
       		$(".area_name").val(area_name);
       		$(".content").val(content);
       		$(".shaixuan").val(shaixuan);
       		$(".paixu").val(paixu);
       		$(".sort_name").val("全部分类");
       		$(".flag").val("1");
	  		$("#clickForm").submit();
        }
        
        //分类筛选
        function checkedByCitySort(city_file_sort_id,sort_type,sort_name){
        	var city_name=$("#city_name").val();
        	var area_name=$("#area_name").val();
        	$(".city_name").val(city_name);
       		$(".area_name").val(area_name);
       		$(".content").val("");
       		$(".shaixuan").val("");
       		$(".paixu").val("");
       		$(".city_file_sort_id").val(city_file_sort_id);
       		$(".sort_type").val(sort_type);
       		$(".sort_name").val("全部分类");
       		$(".flag").val("1");
	  		$("#clickForm").submit();
       }
          
         //查看详情
         function goStoreDetail(store_id){
        	 $(".store_id").val(store_id);
         	 $("#storeForm").submit();
         }
         
         document.onkeydown=function(ev){//检测鼠标按下事件
 	 		var oEvent=ev||event;
 		 	if(oEvent.keyCode==13){//如果按下的是enter键
   				  $(".sousuoparent").click();
   		 	}
 	 	}
         
         //广告跳转
         function tiaozhuan(obj){
        	 var url=$(obj).attr("hyperlink_url");
        	 window.open(url);  
         }
         
        </script>
        
    </body>
</html>