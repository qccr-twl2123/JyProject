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
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
    <title>商家</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="css/htmlmember/labary/predefine.css">
    <link rel="stylesheet" href="css/htmlmember/labary/swiper.min.css">
    <link rel="stylesheet" href="css/htmlmember/sjsp_main.css">
</head>
<style>
	    /*轮播图*/
    .lunbo{
        width: 100%;
        height: 20rem;
        background: #fff;
        position:relative;
        border-bottom: 1px solid #aaa;
        padding: 10px 0 10px 0;
    }
    .sj_xinxi{
        padding-left:5%;
        position:absolute;
        left:0;
        bottom:0.4rem;
        width: 70%;
        height:4rem;
        z-index: 1;
    }
    .sj_title_font{
        display:block;
        padding-left:4.5rem;
        line-height:1.4;
        color:#aaa;
    }
    .ck_sjxq{
        position:absolute;
        right:0;
        bottom:0;
        border-radius:50%;
        width: 2rem;
        height: 2rem;
        background:#e90000;
        text-align:center;
        line-height:1;
        padding:0.5rem;
        color:#fff;
        z-index: 1;
    }
    .sj_xinxi img{
        width: 4.2rem;
        height:4.2rem;
        display: inline-block;
        position:absolute;
        left:5%;
        bottom:0;
    }
    .sj_name{
        font-size:16px;

        line-height:1.5;
    }
    .banner{
        width: 20rem;
        height: 20rem;
        margin: 0 auto;
    }
    .img_box img{
        height: 20rem;
        width: 20rem;
        display: block;
        margin: 0 auto;
    }
    .sect{
        overflow-y:scroll ;
        height: 100%;
    }
    #star img{
        width: 14%;
    }
</style>
<body>
<div class="local_box guding">
    <div class="local_cont_box">
        <span class="title">商家详情</span>
    </div>
    <span class="icon_left local_l" onclick="back_url()"><img src="img/fanhui.png" alt=""></span>
    <span class="icon_right">
        <span class="vip">
            <img src="img/sj_03.png" alt="">
        </span>
        <span class="guanzhu">
            <img src="img/sj_05.png" alt="" class="sp-sc">
        </span>
    </span>
</div>
<div class="sj_title guding">
    <ul class="sx_nav">
        <li>
            <span class="sx_title">
               商品
            </span>
            <span class="tit_dibu"></span>
        </li>
        <li>
            <span class="sx_title">
                商家
            </span>
            <span class="tit_dibu"></span>
        </li>
        <li>
            <span class="sx_title">
                评价
            </span>
            <span class="tit_dibu"></span>
        </li>
    </ul>
    <span class="tit_dibu"></span>
</div>

<!--商品-->

<ul class="viewbox">
    <div class="view_box change">
        <ul class="list_box style_scroll change">

        </ul>
        <ul class="sp_list_box style_scroll change">

        </ul>
    </div>
    <div class="guding foot">
        <div class="foot_l">
            <span class="jine">
                <span>合计：￥</span>
                <span class="allCash">0.00</span>
                <span>元</span>
            </span>
            <span>（<span class="allGoodsNum">0</span>份）</span>
        </div>
        <div class="foot_r" onclick="jiesuan()">
            <a>去结算</a>
        </div>
    </div>
</ul>

<!--商家-->
<ul class="viewbox change clf">
	<li class="clf sj_xqimg bor_b_1_9">
        <a href=""></a>
        <div class="lunbo">
            <div class="banner swiper-container">
                <div class="swiper-wrapper sjxq_imgbox">
<!--                <div class="swiper-slide img_box"><img src="img/text1.jpg" alt=""></div>
                    <div class="swiper-slide img_box"><img src="img/test/test2.jpg" alt=""></div>
                    <div class="swiper-slide img_box"><img src="img/test/test3.jpg" alt=""></div>
                    <div class="swiper-slide img_box"><img src="img/test/test4.jpg" alt=""></div> -->
                </div>
            </div>

            <!--<div class="sj_xinxi">-->
                <!--<img src="img/4.jpg" alt="" class="sj_img">-->
				<!--<span class="sj_title sj_title_font">-->
					<!--<span class="sj_name">-->
						<!--湘阴七匹狼专卖店-->
					<!--</span>-->
					<!--<br/>-->
					<!--<span>4.6分&nbsp; 已售1234单</span>-->
				<!--</span>-->
            <!--</div>-->
            <div class="ck_sjxq">
		    	<span>
		    		查看<br/>详情
		    	</span>
            </div>
        </div>

<!-- 
        <div class="swiper-container">
            <div class="swiper-wrapper sjxq_imgbox">

            </div>
        </div> -->
    </li>

    <li class="sj_tit_inf clf">
        <div>
            <img src="" alt="" class="sj_img">
        </div>
        <div>
            <p class="sj_name"></p>
            <p>
                <span id="star"></span><span class="score"></span>
            </p>
        </div>
        <div>
        	 
            <!-- <a href="http://www.jiuyuvip.com/FileSave/zhihuiPC/business_erweima2.html">
                	点击下载app</br>即可体验优惠买单
            </a> -->
            <a onclick="saoyisao()">
                	点击扫一扫优惠买单 
            </a>  
        </div>
    </li>
    <li class="youhui_box clf bor_b_1_9">

    </li>
    <li class="contact bor_b_1_9">
        <div class="sj_address">
            <span>地址：</span>
        </div>
        <a class="sj_phone" href="#mp.weixin.qq.com" style="border-left: 1px solid  #999">
            <span> <img src="" alt=""></span>
        </a>
    </li>
    <li>
        <h3 style="padding-left:3%;line-height: 2; ">公告与活动</h3>
    </li>
    <li class="sj_huodong">
        <div class="sj_gg" style="padding-left: 8%;"></div>
        <div class="sj_market"></div>
    </li>
<!--     <li class="link_goin sj_xqimg">
        <span>商家详情</span>
        <span><img src="img/goin.png" alt=""></span>
    </li> -->

    <li class="link_goin sj_yyzz bor_b_1_9">
        <span>营业执照</span>
        <span><img src="img/goin.png" alt=""></span>
        <a href=""></a>
    </li>
    <li class="link_goin sj_jyxkz bor_b_1_9">
        <span>经营许可证</span>
        <span><img src="img/goin.png" alt=""></span>
        <a href=""></a>
    </li>
    <li class="link_goin sj_sjtj ">
        <span>优质商家推荐</span>
    </li>
    <li >
        <ul class="sjtuijian ccyc">

        </ul>
    </li> 
</ul>
<!--评价-->
<ul class="viewbox pingjia_cont change">
    <li class="sj_pingjia clf">
        <div>
            <p class="zj_zhpf"></p>
            <p>综合评分</p>
            <p class="pf_bfb"></p>
        </div>
        <div>
            <span id="sj_pj_star"></span>
        </div>
    </li>
    <!--<li class="sj_user_pj_box clf">-->
        <!--<div class="pj_img">-->
            <!--<span>-->
                <!--<img src="" alt="">-->
            <!--</span>-->
        <!--</div>-->
        <!--<div class="pj_cont">-->
            <!--<p>-->
                <!--<span>138********</span><span>2017-03-30 10:06:45</span>-->
            <!--</p>-->
            <!--<p><span id="user_star-1"></span></p>-->
            <!--<div class="user-cont">-->

            <!--</div>-->
            <!--<div class="user_img clf">-->
                <!--<span></span>-->
                <!--<span></span>-->
                <!--<span></span>-->
            <!--</div>-->
            <!--<p class="sj_hf">-->
                <!--店家回复：-->
            <!--</p>-->
        <!--</div>-->
    <!--</li>-->
</ul>
</body>
	<script type="text/javascript">
        var base_inf={
        	base_herf:"<%=basePath%>",
        	sk_shop:"${pd.sk_shop}",
     	};
    </script>
    <script src="js/htmlmember/library/jquery-1.12.4.min.js"></script>
    <script src="js/htmlmember/library/swiper.min.js"></script>
    <script src="js/htmlmember/star/jquery.raty.min.js"></script>
    <script src="js/htmlmember/sj/sjsj.js"></script>
    <script src="js/wx/jweixin-1.0.0.js"></script>
 	<script src="js/wx/zepto.min.js"></script>
    <script src="js/htmlmember/weixindemo.js"></script>
</html>