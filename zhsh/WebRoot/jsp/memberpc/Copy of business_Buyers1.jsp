<%--  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>九鱼网——每笔消费，必有优惠</title>
        <meta charset="utf-8">
        <link rel="stylesheet" href="<%=basePath%>css/memberpc/business_Buyers1.css">
        <script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
        <script src="<%=basePath%>js/memberpc/business_Buyers1.js"></script>
        <link rel="stylesheet" href="<%=basePath%>css/memberpc/swiper.min.css">
        <script src="<%=basePath%>js/memberpc/swiper.min.js"></script>
        <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9"></script>
   		<script type="text/javascript">
   		//通过经纬度获取坐标位置
		   var lng="${lng}";
	       var lat="${lat}";
	       var province_name="${province_name}";
	       var city_name="${city_name}";
	       var area_name="${area_name}";
	       var address="${address}";
	   		if(lng == ""){
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
				   		        		window.addressComponent=result.addressComponent;
				   		        		province_name=addressComponent.province;
				   		        		city_name=addressComponent.city;
				   		        		area_name=addressComponent.district;
				   		        		address=city_name+area_name+addressComponent.street;
				   		        		if("${flag}" != "1"){
				   			  				window.location.href ="<%=basePath%>memberpc_shouye/pcMemberSy.do?province_name="+province_name+"&city_name="+city_name+"&area_name="+area_name+"&longitude="+lng+"&latitude="+lat+"&member_id="+"${memberpd.member_id}&flag=0";
				   			  			}
					   		        }
				   		        }
				   		   });
			 	   		}else {
				   			alert('failed'+this.getStatus());
				   		}        
				 });
	   		}else{
	   			if("${flag}" != "1"){
	  				window.location.href ="<%=basePath%>memberpc_shouye/pcMemberSy.do?province_name=${province_name}&city_name=${city_name}&area_name=${area_name}&longitude="+lng+"&latitude="+lat+"&member_id="+"${memberpd.member_id}&flag=0";
	  			}
	   		}
  		</script>
		 <style type="text/css">
        	.all-nav{
	        	background: #eaeaea;
	        	position: absolute;
	        	left: 0;
	        	top:50px;
	        	width:100%;
	        	left:-48px;
	        	display: none;
	        	z-index:999
        	}
        	.all-nav ul{
        		margin:0
        	}
        	
        	.all-nav ul li{
        		list-style: none;
        		text-align: center;
        		position: relative;
        		line-height: 40px;
        		border-bottom:1px solid #ffffff;
        		border-right:1px solid #ffffff;
        	}
        	.all-nav ul li:hover{
        		background: #fe2929
        	}
        	.all-nav ul li a:hover{
 	        	color: #ffffff;
        	}
        	.all-nav-list{
				position: absolute;   
				background: #eaeaea;
				left:100%; 
				top:0;
				width:150px;
				display: none;  	
        	}
         	.Buyers_hnav_sp1, .Buyers_hnav_sp2 {
    			 margin-top: 4px;
			}
			.sousuoparent{
				margin-left: -71px;
				display: inline-block;
				background-color: #fe2929;
				height: 40px;
			}
			.sousuoimag{
					width: 39px;
				    height: 30px;
				    display: inline-block;
				    /* margin: 0 auto; */
				    margin-left: 18px;
				    margin-top: 5px;
 			}
 			.wstorename{
	 			font-size: 14px;
			    display: inline-block;
			    width:129px;overflow: hidden; white-space: nowrap; text-overflow: ellipsis;
 			}
 			.Buyers_classify2_text_sp1_j {
			    margin-left: 0em;
			}
			.Buyers_classify2_text_sp1_rt {
			    font-size: 2px;
			}
			li:HOVER {
				cursor: pointer;
			}
			.fenleisort:HOVER {
				cursor: pointer;
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
                	 	<a href="<%=basePath%>memberpc/goMemberRegister.do">注册</a> 
                	 </span>
                	 <span class="Buyers_hnav_sp2">
                	 	<a href="<%=basePath%>memberpc/goMemberLogin.do" >会员登录</a>
                	 </span>
                	 <!-- <span class="Buyers_hnav_sp2">
	                	 <a href="#">购物车 
	                	 <span class="orange">0</span>件 </a>
	                </span> -->
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
                	<span class="Buyers_hnav_sp2">
	                	<a href="<%=basePath%>membershopCarPc/shopCar.do?member_id=${memberpd.member_id}">购物车
	                	<span class="orange">${empty shopnumber ?'0': shopnumber}</span>
	                	件 </a>
	                </span> 
	                <a href="<%=basePath%>memberorderPc/orderInfoList.do?member_id=${memberpd.member_id}">
		                <span class="Buyers_hnav_sp2">
		                	我的订单
		                </span>
	                </a>
	                <span class="Buyers_hnav_sp2">
                		<a href="<%=basePath%>memberpc/memberInfoList.do?member_id=${memberpd.member_id}" >我的九鱼 </a>
                	</span><!-- 个人中心 -->
                 </c:if>
              </div>
            <!-- 头部 -->
            <div class="Buyers_header">
                <span class="Buyers_header_logo">
                    <img src="<%=basePath%>img/logo.png">
                </span>
                <span class="Buyers_header_sp1">
                	<select class="Buyers_p2" name="city_name" onchange="search('0')" id="city_name" style=" width: 92px; height: 40px;">
                		<option value="">请选择</option>
	                    <c:forEach items="${cityList}" var="var" >
	                    	<option value="${var.city_name }" ${pd.city_name eq var.city_name?'selected':'' }>${var.city_name }</option>
	                    </c:forEach>
                    </select>
                 </span>
                <span class="Buyers_header_sp2" style="    margin-left: 37px;width: 59%;">
                    <p>
                        <input type="text" class="Buyers_header_sp2_input" name="content" id="content" maxlength="40" />
                        <span class="Buyers_header_sp2_sp sousuoparent" onclick="checked('','')" >
                        	<img src="<%=basePath%>img/b_ss.png" style="width:30px;height:30px" class="sousuoimag" />
                        </span>
                    </p>
                    <!-- 关键字 -->
                    <p class="Buyers_header_p2">
                    	<c:forEach items="${keywordList}" var="infor" end="5">
                    	  <span class="" style="width: 4em">
                            	${infor}
                          </span>
                    	</c:forEach>
                      </p>
                </span>
                <span class="Buyers_header_sp3_w" style="margin-left: 8%;width: 147px;height: 53px;">
                    <a target="_blank”" href="http://www.jiuyuvip.com/FileSave/zhihuiPC/business_erweima2.html" >
                    	<img src="<%=basePath%>img/downmember.png" /> 
                    	
                    </a>
                </span>
                <div class="erweimaclass" style="width: 130px; height: 130px; position: relative; top: -102%; left: 75%; display: none;">
                     	  <img src="<%=basePath%>img/downmemberapp.png" width="100%" /> 
                 </div>
            </div>
            <!-- 导航 -->
            <div class="Buyers_nav">
                <span class="Buyers_nav_sp1"  style="position: relative;">
                    	全部分类
                    	<div class="all-nav">
                    		<ul>
                    			<c:forEach items="${firstList}" var="sortvar" >
                    				<li w_type="one"><a onclick="checkedByCitySort('${sortvar.city_file_sort_id}','${sortvar.sort_type}','${sortvar.sort_name}')">${sortvar.sort_name}</a>
                    					<div class="all-nav-list">
		                    					<ul>
			                    					<c:forEach items="${sortvar.twoList}" var="twosortvar" >
	 		                    						<li w_type="two"><a onclick="checkedByCitySort('${twosortvar.city_file_sort_id}','${twosortvar.sort_type}','${twosortvar.sort_name}')">${twosortvar.sort_name}</a></li>
			                    					</c:forEach>
                    						   </ul>
		                    			</div>
                    				</li>
                    			</c:forEach>
                    		</ul>
                    	</div>
                </span>
                <span class="Buyers_nav_sp2 nav_active">
                    	<a href="#">首页</a>
                </span>
                <span class="Buyers_nav_sp2">
                  	    <a onclick="checked('','6')">最新上线</a>
                </span>
                <span class="Buyers_nav_sp2">
                		  <!-- <a  href="<%=basePath%>jsp/storepc/gyjy.html"  target="_blank">关于九鱼</a>-->
                    	<a onclick="checked('1','')">首单立减</a>
                </span> 
                <span class="Buyers_nav_sp2">
                    	<a onclick="checked('','1')">智能排序</a>
                </span>
                <span class="Buyers_nav_sp2">
                    	<a onclick="checked('','3')">人气递减 <span><img src="<%=basePath%>img/b_down.png"></span></a>
                </span>
                <span class="Buyers_nav_sp2">
                    	<a onclick="checked('','2')">距离近到远 <span><img src="<%=basePath%>img/b_down.png"></span></a>
                </span>
                <span class="Buyers_nav_sp2">
                   		<a onclick="checked('','5')"> 销量递减 <span><img src="<%=basePath%>img/b_down.png"></span></a>
                </span>
                <span class="Buyers_nav_sp2">
                    	<a onclick="checked('','4')">积分率递减 <span><img src="<%=basePath%>img/b_down.png"></span></a>
                </span>
            </div>
            <!-- banner -->
            <div class="Buyers_coutent" >
                
                <div class="Buyers_coutent_rt">
                    <p class="Buyers_coutent_rt_p1" style="border:none;">
                        <span >
                            <span class="Buyers_coutent_rt_p1_img"><img src="<%=basePath%>img/b_ding.png"></span>
                            	全部地区
                        </span>
                        <span>
                            ${pd.area_name}
                        </span>
                         <span class="blue">
                            	<select class="Buyers_p2" name="area_name" id="area_name" onchange="search('1')" style="height:23px; margin-top: 12px;">
                            		<option value="">更多 <span class="xia"></span> </option>
                            		<c:forEach items="${areaList}" var="areavar">
                            			<option value="${areavar.area_name}"  ${areavar.area_name eq pd.area_name?'selected':''} >${areavar.area_name }</option>
                            		</c:forEach>
                             	</select>
                         </span>
                    </p>
                    <div style="width:80%;float:left;">
                    <div class="swiper-container" >
                         <!-- 广告位 -->
                                <div class="swiper-wrapper">
                                	<c:forEach items="${pcadvert}" var="advertvar">
                                		  <div class="swiper-slide" style="background-color: red">
                                		  	<img src="${advertvar.image_url}">
                                		  </div>
                                 	</c:forEach>
                                 </div>
                                <div class="swiper-button-prev">
                                 </div>
                                <div class="swiper-button-next">
                                 </div>
                     </div>
                     </div>
                      <div class="Buyers_gg" style="float:right">
		                <div class="Buyers_gg_top">
		                    <span class="Buyers_gg_top_sp1">
		                        <img src="../img/b_gg.png">
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
		                    <img src="../img/b_2wm.png">
		                </div>
		                <div class="Buyers_gg_btm">
		                    <p>扫描二维码</p>
		                    <p>关注九鱼网官方微信</p>
		                    <p class="red">每笔消费 必有优惠</p>
		                </div>
		            </div>
                </div>
            </div>
            <!-- 美食一级分类的循环 -->
            <c:forEach items="${firstList}" var="sortvar" >
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
		            		<c:if test="${vsindex.index+1 <= 12}">
  				                <span class="Buyers_classify2_sp">
	 				                <a href="<%=basePath%>memberpc/storeInfoList.do?store_id=${storevar.store_id}&member_id=${memberpd.member_id}">
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
	 				                            <span class="Buyers_classify2_text_sp1_rt">${storevar.zan_times}<span><img src="../img/b_zan.png"></span></span>
					                        </span>
					                    </p>
					                    <p></p>
					                    <p class="Buyers_classify2_text">
					                    	<!-- 星级 -->
					                        <span class="Buyers_classify2_text_lt "> 
 					                        	<c:if test="${storevar.comment_star eq '1' }">
					                        	<img src="../img/new_star1.png" width="100px">
					                        	</c:if>
					                        	<c:if test="${storevar.comment_star eq '2' }">
					                        	<img src="../img/new_star2.png">
					                        	</c:if>
					                        	<c:if test="${storevar.comment_star eq '3' }">
					                        	<img src="../img/new_star3.png">
					                        	</c:if>
					                        	<c:if test="${storevar.comment_star eq '4' }">
					                        	<img src="../img/new_star4.png">
					                        	</c:if>
					                        	<c:if test="${storevar.comment_star eq '5' }">
					                        	<img src="../img/new_star5.png">
					                        	</c:if>
	 				                        </span>
	 				                        <span class="Buyers_classify2_text_rt"> 已出售${storevar.transaction_times}件 </span>
					                    </p>
					                     </a>
					                </span>
				                </c:if>
  		            </c:forEach>
		            </div>
            </c:forEach>
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
            <div class="Buyers_left">
                <dl>
                	 <c:forEach items="${firstList}" var="sortvar" varStatus="vs">
	                    <a href="#${sortvar.city_file_sort_id}" id="${vs.index+1}">
	                        <dd  class="dd1">
	                            <span class="dd_sp1"><img src="${sortvar.sort_imageurl}" style="width:38px;height:38px;margin:7px 0 0 16px;"></span>
	                            <span class="dd_sp2">${sortvar.sort_name}</span>
	                        </dd>
	                    </a>
                    </c:forEach>
                  </dl>
            </div>
            <!-- 悬浮 -->
            <div id="whw">
            <div class="Buyers_righst" style="width:58px;">
                <span class="xian"></span>
                <c:if test="${!empty memberpd.member_id}">
                 	<a href="<%=basePath%>membershopCarPc/shopCar.do?member_id=${memberpd.member_id}"></a>
                </c:if>
                <c:if test="${empty memberpd.member_id}">
                 	<a href="#"></a>
                </c:if>
                 <span class="Buyers_righst_img">
                	<img src="../img/b_gw2.png">
                </span>
                 <a href="#"><span class="Buyers_righst_img"><img src="../img/b_top.png"></span></a>
                 <span class="xian"></span>
            </div>
            </div>
            <!--  -->
         </div>
        <script type="text/javascript">
        //切换城市
        function search(value){
         
        	var area_name="";
        	if(value == "1"){
        		area_name=$("#area_name").val();
        	} 
        	var city_name=$("#city_name").val();
         	window.location.href ="../memberpc_shouye/pcMemberSy.do?city_name="+city_name+"&area_name="+area_name+"&member_id="+"${memberpd.member_id}&flag=1";
        }
        
        //筛选
        function checked(shaixuan,paixu){
        	var city_name=$("#city_name").val();
        	var area_name=$("#area_name").val();
        	var content=$("#content").val();
        	window.location.href ="../memberpc_shouye/listAllStore.do?city_name="+city_name+"&area_name="+area_name+"&member_id="+"${memberpd.member_id}&flag=1&paixu="+paixu+"&shaixuan="+shaixuan+"&content="+content+"&sort_name=全部分类";
         }
        
        //分类筛选
        function checkedByCitySort(city_file_sort_id,sort_type,sort_name){
        	var city_name=$("#city_name").val();
        	var area_name=$("#area_name").val();
        	window.location.href ="../memberpc_shouye/listAllStore.do?city_name="+city_name+"&area_name="+area_name+"&member_id="+"${memberpd.member_id}&flag=1&paixu=&shaixuan=&content=&city_file_sort_id="+city_file_sort_id+"&sort_type="+sort_type+"&sort_name="+sort_name;
         }
        
         $(function(){
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
        	
        	//隐藏显示
      		$(".Buyers_header_sp3_w").hover(function(){
    			$(".erweimaclass").show();
         	},function(){
         		$(".erweimaclass").hide();
         	});
        	
        });
        </script>
        
    </body>
</html> --%>