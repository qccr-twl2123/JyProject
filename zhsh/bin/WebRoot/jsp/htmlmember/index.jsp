<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>九鱼网</title>
     <base href="<%=basePath%>">
    <link rel="stylesheet" href="css/htmlmember/labary/predefine.css">
    <link rel="stylesheet" href="css/htmlmember/labary/swiper.min.css">
    <link rel="stylesheet" href="css/htmlmember/shaixuan.css">
    <link rel="stylesheet" href="css/htmlmember/main.css">
    <link rel="stylesheet" href="css/htmlmember/wx_index.css">
</head>
<body>
<!--顶部选择地址-->
<div class="gotop">
    
</div>
<header class="local_box gonone">
    <div class="local_cont_box" onclick="goArea()" >
        <img src="images/dw_03.png">
        <span class="font_s local">${pd.city_name}${pd.area_name}${pd.address}</span>
    </div>
    <span class="icon_left local_l"></span>
    <span class="icon_right add_to"><img src="img/sys_img.png" alt="" style="margin:10px 0"></span>
    <div class="add_to_list">
        <span class="arrow_up"></span>
        <ul>


         </ul>
    </div>
</header>
<!--搜索-->
<div class="search_box yingcang">
        <div class="inputbox">
            <input type="text" maxlength="10" placeholder="输入商家地址/商家名称进行检索">
            <a></a>
        </div>
</div>
<section>
     <!--轮播-->
    <div class="swiper-container swiper-container_1 yingcang">
        <div class="swiper-wrapper shaixuanItem_box">
        	<c:forEach items="${firstList}" var="cityvar">
        			 <span class='swiper-slide' sort_id="${cityvar.city_file_sort_id }" sort_type="${cityvar.sort_type }" sort_name="${cityvar.sort_name }" onclick="oneclick(this)"><img src="${cityvar.sort_imageurl }">${cityvar.sort_name}</span>
        	</c:forEach>
         </div>
        <div class="swiper-pagination swiper-pagination_1"></div>
    </div>
    
    <div class="swiper-container swiper-container_2  yingcang">
        <div class="swiper-wrapper lunbo_box" >
        	<c:forEach items="${advertList}" var="advervar">
        		<div class='swiper-slide'><a href="${advervar.hyperlink_url}"><img src="${advervar.image_url}"></a></div>
        	</c:forEach>
         </div>
        <div class="swiper-pagination swiper-pagination_2"  style=" width: 100%;"></div>
    </div>

    <!--筛选条-->
    <div class="sx_box show_sx_tab show_box_click" onclick="gSSX()">
        <ul class="sx_nav">
            <li toname="all_list">
	            <span class="sx_title flsx" toname="all_list">
	                	全部分类
	            </span>
	             <span class="arrow arrow_down"></span>
             </li>
            <li toname="paixu">
	            <span class="sx_title znpx" >
	                	智能排序
	            </span>
	             <span class="arrow arrow_down"></span>
             </li>
            <li toname="show">
	            <span class="sx_title sx">
	                <!--按商家/商品显示-->
	               	 筛选
	            </span>
	             <span class="arrow arrow_down"></span>
             </li>
        </ul>
    </div>
    <ul class="sj_list change">
        <%--  <!-- 商家-->
        <c:forEach items="${storeList}" var="storevar" varStatus="vs">
	        <li class="shangjia " sk_shop="${storevar.new_store_id}" num="${vs.index+1}" onclick="goStoreDetail(this)">
	            <div class="sj_jcxx">
	                <div class="img_box">
	                	<c:if test="${storevar.zkstatus eq '1'  }">
	                		<span class="zhekou">折</span>
	                	</c:if>
 	                    <img src="${storevar.pictrue_url}">
	                </div>
	                <div class="sj_inf">
	                    <div style="margin-bottom: 0.5rem;">
	                        <span class="sj_tit">${storevar.store_name}</span>
	                        <span class="loc_num">${storevar.distance}</span>
	                    </div>
	                    <span id="${storevar.only_store_id}" class="star" starnum="${storevar.comment_score}"  title="good" style="width: 110px;"></span><span class="sj_danshu">已接${storevar.transaction_times}单</span>
	                    <span style="color: #ff0600;">${storevar.comment_score}分</span>
	                </div>
	            </div>
	            <div class="sj_youhui">
	                <div class="zs_jf">
	                    <div>${storevar.integral_rate}</div>
	                    <div>赠送积分率</div>
	                </div>
	                <ul class="sj_jf_inf">
	                	<c:forEach items="${storevar.marketlist }" var="mavar">
		                	<li>
		                        <span class="ji">
		                        	<c:choose>
					    				<c:when test="${mavar.marketing_type eq '1' }">满</c:when>
					    				<c:when test="${mavar.marketing_type eq '2' }">满</c:when>
					    				<c:when test="${mavar.marketing_type eq '3' }">时</c:when>
					    				<c:when test="${mavar.marketing_type eq '4' }">买</c:when>
					    				<c:when test="${mavar.marketing_type eq '5' }">购</c:when>
					    				<c:when test="${mavar.marketing_type eq '6' }">积</c:when>
					    				<c:otherwise>折</c:otherwise>
					    			</c:choose>
					    		</span>
 		                        <span>${mavar.grantrule}</span>
		                    </li>
	                	</c:forEach>
 	                </ul>
	            </div>
	            <c:if test="${storevar.haveRed eq '1'  }">
	            	 <div class="hongbao"><span>红包</span></div>
	            </c:if>
  	        </li>
        </c:forEach> --%>
<!--        <li class="shangjia " stor_id="47175224" num="1" onclick="goStoreDetail(47175224)">
            <div class="sj_jcxx">
                <div class="img_box">
                    <img src="https://www.jiuyuvip.com/FileSave/File/userFile/47175224.png">
                </div>
                <div class="sj_inf">
                    <div style="margin-bottom: 0.5rem;">
                        <span class="sj_tit">商家E</span>
                        <span class="loc_num">1.43km</span>
                    </div>
                    <span id="star47175224" title="good" style="width: 110px;"></span><span class="sj_danshu">已接63单</span>
                    <span style="color: #ff0600;">4.2分</span>
                </div>
            </div>
            <div class="sj_youhui">
                <div class="zs_jf">
                    <div>5%</div>
                    <div>赠送积分率</div>
                </div>
                <ul class="sj_jf_inf">
                    <li>
                        <span class="ji">积</span>
                        <span>积分率：5%</span>
                    </li>
                </ul>
            </div>
        </li>-->

        <!--商品-->
        <!--<li class="shangpin">-->
            <!--<div class="spxx clf">-->
                <!--<div class="img_box">-->
                    <!--<img src="img/rm_me_06.png" alt="">-->
                <!--</div>-->
                <!--<ul class="cont_box">-->
                    <!--<li class="cont_tit" style="margin-bottom: 5px">七匹狼 夏季经典男装七匹季经典男装七匹季经典男装七匹季经典男装七匹狼 夏季经典男装七 夏季经典男装</li>-->
                    <!--<li class="jige">-->
                        <!--<span class="sq_jiage" >￥<span class="col_e0" style="font-size: 120%">13</span><span style="font-size: 80%">.00</span></span>-->
                        <!--<span class="youhui">优惠</span>-->
                        <!--<span class="yuanjia">原价 123.13</span>-->
                    <!--</li>-->
                    <!--<li><span class="dianpu">店铺 </span>地址地址店铺地址</li>-->
                <!--</ul>-->
            <!--</div>-->
            <!--</div>-->
        <!--</li>-->
        <div class="load">
	        <img src="img/loading.gif">
	    </div>
     </ul>
     
</section>
<footer class="footerdi guding clf">
	<ul>
		<li class="f_whole">
			<a style=" color: #e90000; " href="html_member/gouShouYe.do?area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}">
				<i class="cur"></i>
				首页
			</a>
		</li>
		<li class="f_jiexiao">
			<a href="html_member/goMyYouXuan.do?area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}">
				<i></i>
				优选爆品
			</a>
		</li>
		<li class="f_car">
			<a  href="html_member/gouRenMai.do?area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}">
				<i></i>
				人脉圈
			</a>
		</li>
		<li class="f_personal">
 			<a href="<%=basePath%>html_me/goMe.do?area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}">
				<i></i>
				我的
			</a>
		</li>
	</ul>
</footer>
</body>
<script src="js/htmlmember/library/jquery-1.12.4.min.js"></script>
<script src="js/htmlmember/star/jquery.raty.min.js"></script>
<script src="js/htmlmember/library/swiper.min.js"></script>
<script>
	var base_inf={
        base_herf:"<%=basePath%>",
        city_name:"${pd.city_name}",
        area_name:"${pd.area_name}",
        address:"${pd.address}",
        longitude:"${pd.longitude}",
        latitude:"${pd.latitude}",
        nowpage:1
    };
</script>
<script src="js/wx/jweixin-1.0.0.js"></script>
<script src="js/wx/zepto.min.js"></script>
<script src="js/htmlmember/weixindemo.js"></script>
<script src="js/htmlmember/index_main/wx_index.js"></script>
<script type="text/javascript">
    $(function(){
    	if("${cityflag}" == "false"){
    		alert("当前城市未开通，请选择其他的城市");
    		goArea();
    	}
    });
</script>
</html>