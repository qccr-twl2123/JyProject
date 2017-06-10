<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>九鱼网-每笔消费 必有优惠</title>
	<base href="<%=basePath%>">
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta name="Keywords" Content="九鱼销链,九鱼销链加盟项目,九鱼销链官网,九鱼销链加盟,九鱼,jiuyu,九鱼网">
    <meta name="Description" content="九鱼网-每笔消费 必有优惠">
    <link rel="stylesheet" href="css/memberpc/library/predefine.pc.css">
    <link rel="stylesheet" href="css/memberpc/library/swiper.min.css">
    <link rel="stylesheet" href="css/memberpc/font/iconfont.css">
    <link rel="stylesheet" href="css/memberpc/shouye.css">
     <script type="text/javascript">
    var base_inf={
    	    base_href:"<%=basePath%>" 
      };
    </script>  
 </head>
<body>
<!--头部-->
<header>
    <div class="top_bar">
        <div class="tp_bar_box inner_width">
            <a href="storepc/goLogin.do">
                <span>商家登陆</span>
            </a>
            <a href="storepc/goRegister.do">
                <span>商家加盟</span>
            </a>
             <c:choose>
            	<c:when test="${ !empty memberpd }">
            		 <a href="memberorderPc/orderInfoList.do?area_name=${pd.area_name}&city_name=${pd.city_name}">
		                <span>我的订单</span>
		            </a>
            		<a href="memberpc/memberInfoList.do?area_name=${pd.area_name}&city_name=${pd.city_name}">
		                <span>${memberpd.name}</span>
		            </a>
		            <a href="memberpc/loginOut.do">
		                <span>退出登录</span>
		            </a>
            	</c:when>
            	<c:otherwise>
            		 <a href="memberpc/goMemberLogin.do">
		                <span>我的订单</span>
		            </a>
            		<a href="memberpc/goMemberLogin.do">
		                <span>会员登陆</span>
		            </a>
		            <a href="memberpc/goMemberRegister.do">
		                <span>会员注册</span>
		            </a>
            	</c:otherwise>
            </c:choose>
             <a>
                <span>常见问题</span>
            </a>
            <a>
                <span class="show_ewm">手机九鱼
                    <div class="ewm_appdown">
                        <img src="img/three.png" alt="" class="sanjiao">
                        <img src="img/downmemberapp.png" alt="" class="ewm" >
                    </div>
                </span>
            </a>
        </div>
    </div>
    <div class="head_bar">
        <div class="head_bar_box inner_width">
            <span class="img_box" onclick="goindex()">
                <img src="img/logo.png" alt="">
            </span>
            <span class="sel_box">
                <select name="city" id="loc_shi" class="select_item" onchange="addsearcharea()">
                    <option value="请选择">请选择</option>
                    <c:forEach items="${cityList }" var="var" >
                    	<option value="${var.city_id }" ${pd.city_name eq var.city_name?'selected':'' }>${var.city_name }</option>
                    </c:forEach>
                 </select>
                <select name="area" id="loc_qu" class="select_item" onchange="shaixin()">
                	 <c:forEach items="${areaList }" var="var" >
                	 	<option value="${var.area_id }" ${pd.area_name eq var.area_name?'selected':'' }>${var.area_name }</option>
                	 </c:forEach>
                 </select>
            </span>
            <span class="inp_box">
                <input type="text" id="sj_search" placeholder="根据商家名称/地址进行检索" class="input_item" >
                <div class="search_btn" onclick="search_click()"></div>
            </span>
        </div>
    </div>
</header>
<!--导航栏-->
<nav>
    <div class="nav_box inner_width">
        <span class="all_list" onmouseover="nav_mouseover()" onmouseleave="nav_mouseleave()"><span class="alllist_tit">全部分类</span>
               <ul class="xl_list cycc">
               		<c:forEach items="${firstList}" var="var">
               			 <li onmouseover="li_mouseover(this)" onmouseout="li_mouseleave(this)" s_id="${var.city_file_sort_id}"  s_type="${var.sort_type}" s_name="${var.sort_name}" onclick="checkedByCitySort(this)" > 
			                     <p class="item_color">  
				                     <div class="iconfont  ${var.sort_name}" style="float: left"></div> 
				                    	${var.sort_name}
				                     <div class="iconfont icon-jinru1" style="float:right"></div>  
			                     </p>  
 			                       <ul class="two_list ccyc" onmouseover="two_over(this)" onmouseout="two_leave(this)">
			                           <li class='two_list_title'>${var.sort_name}</li> 
			                            <li style="overflow-x: hidden;overflow-y: scroll;" class="style_scroll">
 			                           <c:forEach items="${var.twoList}" var="var2">
 			                               <p s_id="${var2.city_file_sort_id}" s_type="${var2.sort_type}" s_name="${var2.sort_name}"  onclick="checkedByCitySort(this)">${var2.sort_name}</p>
 			                           </c:forEach>
			                            </li>
 			                       </ul>
 		                 </li> 
                		</c:forEach>
                </ul>
        </span>
        <span class="act" onclick="goindex()">
            <a >首页</a>
        </span>
        <span  onclick="checked('','6')">
            <a >最新上线</a>
        </span>
        <span onclick="checked('1','')">
            <a >首单立减</a>
        </span>
        <span onclick="checked('','1')">
            <a >智能排序</a>
        </span>
        <span onclick="checked('','3')">
            <a >人气递减</a>
        </span>
        <span onclick="checked('','2')">
            <a >距离远近</a>
        </span>
        <span onclick="checked('','5')">
            <a >销量递减</a>
        </span>
        <span onclick="checked('','4')">
            <a >积分率递减</a>
        </span>
    </div>
</nav>

<!--左导航-->
<div class="zuodaohang">
	<c:forEach items="${leftfirstsort}" var="var">
	 	<a onclick="atoscroll(this)">
          <div class="daohang_item">
	          <span class="iconfont  dh_icon "></span>
	          <span class="dh_title lefticon ">${var.sort_name}</span>
          </div>
      	</a>
	</c:forEach>
</div>


<!--导航栏结束-->
<div class="banner">
    <div class="swiper-container sw_banner inner_width swiper_view">
        <div class="swiper-wrapper swiper-slide_box" >
        	<c:forEach items="${advertList}" var="var">
        		<div class="swiper-slide"><a href="${var.hyperlink_url}"><img src="${var.image_url }" ></a></div>
        	</c:forEach>
         </div>
        <div class="swiper-pagination"></div>
        <div class="swiper-button-next swiper-button-white swiper_btn">
            <div class="bg_block"></div>
        </div>
        <div class="swiper-button-prev swiper-button-white swiper_btn">
            <div class="bg_block"></div>
        </div>
    </div>
</div>


<!--商家列表-->
<div class='list_item shangjialist' >
	<c:forEach items="${firstList}" var="var">
				<div class="list_item shangjialist" > 
                        <div class="list_item_head inner_width"> 
                            <div class="list_title"> 
                                <img src="${var.sort_imageurl }"> 
                                <span>  ${var.sort_name }  </span> 
                            </div> 
                            <div class="class_show_list" > 
                               <c:forEach items="${var.twoList}" var="twovar" varStatus="twovs">
                               			<c:if test="${twovs.index <6 }">
                               				<span  onclick="erjifenlei('${twovar.city_file_sort_id }','${twovar.sort_name }','${twovar.sort_type }')">${twovar.sort_name }</span>
                               			</c:if>
                                </c:forEach>
                            <span  onclick="erjifenlei('${var.city_file_sort_id }','${var.sort_name }','${var.sort_type }')">全部</span> 
                            </div> 
                        </div> 
                        <ul class="sj_list inner_width clf"> 
                                <c:forEach items="${var.storeList}" var="storevar">
										<li onmouseover="list_hover(this)" onmouseleave="list_leave(this)"  s_id="${storevar.new_store_id}" onclick="gostore(this)">
								                <div class="sj_img_box" >
								                    <img src="${storevar.pictrue_url}">
								                </div>
								                <div class="sj_name_box">
								                    <p>
								                        <span class="sj_name ccyc">
								                            <span >【${storevar.area_name}】</span>
								                            <span >${storevar.store_name}</span>
								                        </span>
								                    <span class="sj_yx">
 								                         <c:forEach items="${storevar.marketlist}" var="marvar">
 								                         	<c:if test="${marvar.marketing_type  eq '1'}"><span>满赠</span></c:if>
 								                         	<c:if test="${marvar.marketing_type  eq '2'}"><span>立减</span></c:if>
 								                         	<c:if test="${marvar.marketing_type  eq '3'}"><span>时段</span></c:if>
 								                         	<c:if test="${marvar.marketing_type  eq '4'}"><span>立减</span></c:if>
 								                         	<c:if test="${marvar.marketing_type  eq '5'}"><span>累计</span></c:if>
 								                         	<c:if test="${marvar.marketing_type  eq '6'}"><span>积分</span></c:if>
 								                         	<c:if test="${marvar.marketing_type  eq '7'}"><span>折扣</span></c:if>
 								                         </c:forEach>
 								                    </span>
								                    </p>
								                    <p class="cclhxs" style="height: 2.7rem;">
								                        ${storevar.store_introduce}
								                    </p>
								                </div>
								                <div class="sj_star_box">
								                    <span id="${storevar.store_id}"  class="star_box" str_num="${storevar.comment_score}"></span>
								                    <span class="sj_ys">已售${storevar.transaction_times}</span>
								                </div>
								            </li>
							          </c:forEach> 
             			</ul> 
        		</div>
 	</c:forEach>

</div>
<!--商家列表结束-->


<footer>
    <div class="line_one"></div>
    <div class="foot_cont inner_width">
        <span class="ewm_box">
            <img src="img/userapp.png" alt="">
            <span>用户端下载</span>
        </span>
        <span class="ewm_box">
            <img src="img/sjapp.png" alt="">
            <span>商家端app下载</span>
        </span>
        <span class="ewm_box">
            <img src="img/wechat.png" alt="">
            <span>微信公众号</span>
        </span>
        <div class="link_box">
            <span >
                <a href="storepc/goRegister.do">我要开店</a>
            </span>
            <span >
                <a href="jsp/storepc/gyjy.html" target="_blank">关于九鱼</a>
            </span>
            <span >
                <a>加入我们</a>
            </span>
            <span >
                <a>合作流程</a>
            </span>
            <span >
                <a>常见问题</a>
            </span>
        </div>
        <div class="beihao">
            <span>©2016 jiuyuvip.com [浙] ICP备16025718号-2 ,All Rights Reserved.</span>
        </div>
    </div>
</footer>
<script src="js/memberpc/library/swiper.min.js"></script>
<script src="js/memberpc/library/jquery-1.12.4.min.js"></script>
<script src="js/memberpc/library/lib/jquery.raty.min.js"></script>
<script src="js/memberpc/toubu.js"></script>
<script src="js/memberpc/jy_index.js"></script> 
</body>
</html>