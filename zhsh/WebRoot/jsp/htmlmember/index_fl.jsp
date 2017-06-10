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
     <base href="<%=basePath%>">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <link rel="stylesheet" href="css/htmlmember/labary/predefine.css">
    <link rel="stylesheet" href="css/htmlmember/main.css">
    <link rel="stylesheet" href="css/htmlmember/shaixuan.css">
    <link rel="stylesheet" href="css/htmlmember/wx_index.css">
    <title>商家筛选</title>
</head>
<style>
    .local_box{
        width:100%;
        height:40px;
        background:#ff0600;
        box-sizing:border-box;
        position: relative;
        color: #fff;
        line-height: 40px;
    }
    .local_cont_box{
        width:60%;
        height:100%;
        margin:0 auto;
        text-align: center;
    }
    .local_cont_box img{
        height: 20px;
        padding: 10px 0;
    }
    .local_cont_box span{
        vertical-align: top;
        line-height: 40px;
        font-size: 100%;
    }
    .icon_left{
        font-size: 100%;
        position: absolute;
        left: 0;
        top:0;
        vertical-align: top;
        line-height: 40px;
        padding: 0 3.6%;
        font-weight: bold;
    }
    .icon_left img{
        vertical-align: middle;
    }
    .firstdiv{
        width: 100%;
	    height: 4rem;
	    text-align: center;
	    line-height: 3rem;
	    padding: 0.5rem 0;
	    box-sizing: border-box;
	    background: #FF0600;
	    color: #FFF;
	    font-size: 1.1rem;
	}
</style>
<body>
<header class="local_box ">
    <div class="local_cont_box">
        <span class="font_s local">筛选</span>
    </div>
    <a href="html_member/gouShouYe.do"><span class="icon_left local_l" >
        <img src="img/fanhui.png" alt=""></a>
    </span>
</header>
<section>
     <div class="sx_click"  >
        <div class="sx_box use_sx_box do_box">
            <ul class="sx_nav show_nav">
                <li liname="all_list">
                    <span class="sx_title flsx">${empty pd.sort_name?'全部分类':pd.sort_name}</span>
                    <span class="arrow arrow_up"></span>
                 </li>

                <li liname="paixu" >
                    <select name="pxvalue" id="pxvalue" onchange="tongyong()">
                        <option value="">智能排序</option>
                        <option value="2">距离由近到远</option>
                        <option value="3">人气从高到低</option>
                        <option value="4">积分率从高到低</option>
                        <option value="5">销售量从高到低</option>
                    </select>
                  </li>
                
               <li liname="show">
                   <select name="sxvalue" id="sxvalue" onchange="tongyong()" >
                       <option value="">筛选</option>
                       <option value="1">首单立减</option>
                       <option value="2">折扣商品</option>
                       <option value="3">满返代金券</option>
                   </select>
                </li>
            </ul>
            <div class="sx_box_list_ul">
                <ul class="all_list_ul">
                	<li sort_id=""  sort_type=""  sort_name="全部分类" onclick="twoclick(this)">
	                        <span>全部分类</span> 
 	                </li>
                	<c:forEach items="${firstList}" var="firstvar">
                		<li sort_id="${firstvar.city_file_sort_id}"  sort_type="${firstvar.sort_type}"  sort_name="${firstvar.sort_name}" onclick="oneclick(this)">
	                        <span>${firstvar.sort_name}</span><img src='img/goin.png'>
	                        <ul class='all_list_ul_2' >
	                        	<li style="padding: 0;" >
	                                <div class="firstdiv" sort_id="${firstvar.city_file_sort_id}"  sort_type="${firstvar.sort_type}"  sort_name="${firstvar.sort_name}" onclick="twoclick(this)"    >${firstvar.sort_name}</div>
	                            </li>
	                        	<c:forEach items="${firstvar.twoList}" var="twovar">
 	                        		<li sort_id="${twovar.city_file_sort_id}"  sort_type="${twovar.sort_type}"  sort_name="${twovar.sort_name}" onclick="twoclick(this)">${twovar.sort_name}</li>
	                        	</c:forEach>
 	                        </ul>
	                    </li>
 		        	</c:forEach>
                 </ul>
            </div>
        </div>
     </div>
    <input value="${pd.city_file_sort_id}" id="cfsivalue"  type="hidden">
	<input value="${pd.sort_name}" id="snvalue"  type="hidden">
	<input value="${pd.sort_type}" id="stvalue"  type="hidden">
    <ul class="sj_list change" style="padding-top: 40px;">
         <%-- <!-- 商家-->
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
     </ul>
     <div class="load">
        <img src="img/loading.gif">
 	</div>
 </section>
 
</body>
<script src="js/htmlmember/library/jquery-1.12.4.min.js"></script>
<script src="js/htmlmember/library/touch.min.js"></script>
<script src="js/htmlmember/star/jquery.raty.min.js"></script>
<script src="js/htmlmember/library/swiper.min.js"></script>
<script>
	var base_inf={
        base_herf:"<%=basePath%>",
        city_name:"${pd.city_name}",
        area_name:"${pd.area_name}",
        longitude:"${pd.longitude}",
        latitude:"${pd.latitude}",
        nowpage:1
    };
</script>
<script src="js/htmlmember/index_main/wx_shaixuan.js"></script>
</html>