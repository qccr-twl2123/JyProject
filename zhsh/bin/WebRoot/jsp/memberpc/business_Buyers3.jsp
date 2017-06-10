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
    <title>九鱼网</title>
	<base href="<%=basePath%>">
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <link rel="stylesheet" href="css/memberpc/library/predefine.pc.css">
    <link rel="stylesheet" href="css/memberpc/library/swiper.min.css">
    <link rel="stylesheet" href="css/memberpc/font/iconfont.css">
    <link rel="stylesheet" href="css/memberpc/shouye.css">
    <link rel="stylesheet" href="css/memberpc/sj_inf.css">
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
        <span onclick="goindex()">
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

<!--导航栏结束-->
<div class="sj_inf cclhxs   " style="padding-top: 1rem;">
    <div class="inner_width ccyc">
        <div class="left_img_show">
            <div class="swiper-container gallery-top">
                <div class="swiper-wrapper">
                	<c:forEach items="${smallList }" var="var">
                		<div class="swiper-slide" style="background-image:url(${var})"></div>
                	</c:forEach>
                 </div>
            </div>
            <div class="swiper-container gallery-thumbs" style="padding: 0">
                <div class="swiper-wrapper" >
                    <c:forEach items="${smallList }" var="var">
                		<div class="swiper-slide" style="background-image:url(${var})"></div>
                	</c:forEach>
                </div>
            </div>
        </div>
        <ul class="right_text_show lh2">
        	 <li>
                <span class="fll fwb fs120">${storepd.store_name }</span>
              </li>
            <li class="clf bb1" style="line-height: 1">
                <span class=" fll star_box fs120 " id="312r3216y" str_num="${storepd.comment_score}"></span>
                <span class="flr" onclick="sy()" style="margin-top: -20px;">
                	<span>
                		<c:choose>
                			<c:when test="${iscollect eq '0' }"><img src="img/ysc.png" alt="" class="sy_class" style="width: 18px;" class="mg_r_8"></c:when>
                			<c:otherwise><img src="img/wsc.png" alt="" class="sy_class" style="width: 18px;" class="mg_r_8"></c:otherwise>
                		</c:choose>
                 	</span><span >收藏本店</span>
                </span>
            </li>
            <li >
                <span class="fwb fs120">商家信息</span>
            </li>
            <li class="bb1">
                <span class="fwb">经营类别：</span>
                <span> ${storepd.onesortname }</span>
            </li>
            <li >
                <span class="fwb">店铺地址：</span>
                <span> ${storepd.address }</span>
             </li>
            <li class="bb1">
                <span class="fwb">联系电话：</span>
                 <span> ${storepd.phone_bymemeber }</span>
            </li>
            <li>
                <span class="fwb fll">优惠信息：</span>
                <ul class="youhui" style="padding-top: 10px;">
                	<c:forEach items="${marketlist }" var="var">
				    		<li>
				    			<span class=" bg_e9 jifen">
					    			 <c:if test="${var.marketing_type  eq '1'}"><span>满赠</span></c:if>
 								     <c:if test="${var.marketing_type  eq '2'}"><span>立减</span></c:if>
 								     <c:if test="${var.marketing_type  eq '3'}"><span>时段</span></c:if>
 								     <c:if test="${var.marketing_type  eq '4'}"><span>立减</span></c:if>
 								     <c:if test="${var.marketing_type  eq '5'}"><span>累计</span></c:if>
 								     <c:if test="${var.marketing_type  eq '6'}"><span>积分</span></c:if>
 								     <c:if test="${var.marketing_type  eq '7'}"><span>折扣</span></c:if>
				    			</span>
 		                        <span>${var.grantrule }</span>
		                    </li> 
				    </c:forEach>
                 </ul>
            </li>
        </ul>
    </div>
</div>
<div class="nav_title inner_width bb1">
    <p>
        <span style="border-left: 3px solid #e90000;border-radius: 0;padding-left: 4px;" class="fwb lh2 fs120">商家详情</span>
    </p>
</div>
<div class="img_cont inner_width">
	<div style="padding:20px 0;word-wrap: break-word; word-break: normal;" class="bb1 information">
        <span>${storepd.store_introduce}</span>
    </div>
	<c:forEach items="${bigList }" var="var">
		<img src="${var.image_url }" style="width: 100%;display: block" alt="">
		<c:if test="${var.text ne ''}">
			<p class="pd_15">${var.text}</p>	
		</c:if>
  	</c:forEach>
</div>
<form action="memberpc/storeInfoList.do" name="Form" id="Form" method="post" >
<input name="city_name" value="${pd.city_name}" type="hidden">
<input name="area_name" value="${pd.area_name}" type="hidden">
<input name="area_name" value="${pd.area_name}" type="hidden">
<input name="sk" value="${pd.sk}" type="hidden">
<input name="sc" value="${pd.sc}" type="hidden">
<input name="r_cl" value="${pd.r_cl}" type="hidden">
<input name="timestamp" value="${pd.timestamp}" type="hidden">
<input name="comment" value="1" type="hidden">
<div class="nav_title inner_width bb1 comment">
    <p>
        <span style="border-left: 3px solid #e90000;border-radius: 0;padding-left: 4px;" class="fwb lh2 fs120">消费评价</span>
    </p>
</div>
<div class="pj_list">
    <div class="inner_width">
    	<ul>
    		<c:forEach items="${commentList }" var="var">
	    		<li class="sj_user_pj_box clf">
			        <div class="pj_img tac lh2 col_e9">
			            <span>
			                <img src="${var.image_url}" alt="">
			                ${var.name}
			            </span>
 			        </div>
			        <div class="pj_cont">
			            <p><span class="star_box" id="${var.comment_id}" str_num="${var.star_number}"></span> <span>${var.star_time}</span></p>
			            <div class="user-cont">
			                 ${var.content}
			            </div>
			            <div class="user_img clf">
 			                	<c:forEach items="${var.imgList }" var="imgvar">
			                		 <span> <img src="${imgvar.icon_url }" alt=""> </span>
			                	</c:forEach>
 			            </div>
 			            <c:if test="${var.comment_store_status eq '1'}">
 			            	<p class="sj_hf col_e9">
			                	商家回复：${var.comment_store}
			            	</p>
 			            </c:if>
 			        </div>
			    </li>
    		</c:forEach>
        </ul>
     </div>
     <div class="pagination fenye clf">
               ${page.pageStr2}
      </div>
</div>
</form>
<!--回到顶部-->
<div class="gotop">
    <div class="daohang_item" style="border:0;" onclick="gotop()">
        <span class="iconfont icon-huidaodingbu dh_icon back_up"></span>
        <span class="dh_title">回到顶部</span>
    </div>
</div>

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
<script src="js/memberpc/sj_inf.js"></script>
<script type="text/javascript">
//收藏取消收藏
function sy(){
	$.ajax({
		  url:"memberpc/iscolloctByMS.do",
		  data:"sk=${pd.sk}",
		  type:"post",
		  dataType:"json",
		  success:function(data){
			  if(data.result == "1"){
				  if (data.data == "0") {
			            $(".sy_class").attr("src", "img/ysc.png");
			      } else if (data.data == "1") {
			            $(".sy_class").attr("src", "img/wsc.png");
			      }
			  }else{
				  alert(data.message);
			  }
 		  },
		  error:function(a){
		  	alert("异常");
		  }
	});
}

window.onload = function() { 
	if( "${pd.comment}" == "1"){
  	    var n=$(".comment").offset().top;
	    $("body").scrollTop(n);
	}
 }; 
 //由于商家回复定位在底部   当字数多时会挡住上面的图片   故添加上函数
 $(function(){
     function changeHF(){
         var pj=$(".pj_cont");
         for (var i = 0 ; i < pj.length ; i++){
             if($($(".pj_cont")[i]).children().length==4){
                 $($($(".pj_cont")[i]).children(".sj_hf")[0]).css({"position": "initial","width":"100%"})
             }else{
                 $($($(".pj_cont")[i]).children(".sj_hf")[0]) .css({"position": "absolute"})
             }
         }
     }
     changeHF()
 })
 

</script>
</body>
</html>