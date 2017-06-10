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
    <meta name="Keywords" Content="九鱼销链,九鱼销链加盟项目,九鱼销链官网,九鱼销链加盟,九鱼,jiuyu,九鱼网">
    <meta name="Description" content="九鱼网-每笔消费 必有优惠">
    <link rel="stylesheet" href="css/memberpc/library/predefine.pc.css">
    <link rel="stylesheet" href="css/memberpc/library/swiper.min.css">
    <link rel="stylesheet" href="css/memberpc/font/iconfont.css">
    <link rel="stylesheet" href="css/memberpc/shouye.css">
    <script src="js/memberpc/library/swiper.min.js"></script>
    <script src="js/memberpc/library/jquery-1.12.4.min.js"></script>
    <script src="js/memberpc/library/lib/jquery.raty.min.js"></script>
     <script type="text/javascript">
    var base_inf={
    	    base_href:"<%=basePath%>",
    	    city_name:"",
    	    area_name:"",
    	    lng:"",
    	    lat:"",
     	    content:"",
     	    flag:"${flag}"
     };
    </script>
     <script src="js/memberpc/jy_index.js"></script>
</head>
<body>
<!--头部-->
<header>
    <div class="top_bar">
        <div class="tp_bar_box inner_width">
            <a href="<%=basePath%>storepc/goLogin.do">
                <span>商家登陆</span>
            </a>
            <a href="<%=basePath%>storepc/goRegister.do">
                <span>商家加盟</span>
            </a>
            <a>
                <span>我的九鱼</span>
            </a>
            <a>
                <span>我的订单</span>
            </a>
            <a href="<%=basePath%>memberpc/goMemberLogin.do">
                <span>会员登陆</span>
            </a>
            <a href="<%=basePath%>memberpc/goMemberRegister.do">
                <span>会员注册</span>
            </a>
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
                <select name="city" id="loc_shi" class="select_item">
                    <option value="请选择">请选择</option>

                </select>
                <select name="area" id="loc_qu" class="select_item">
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

</div>


<!--导航栏结束-->
<div class="banner">
    <div class="swiper-container sw_banner inner_width swiper_view">
        <div class="swiper-wrapper swiper-slide_box" >
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
                <a>我要开店</a>
            </span>
            <span >
                <a>关于九鱼</a>
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

</body>
</html>