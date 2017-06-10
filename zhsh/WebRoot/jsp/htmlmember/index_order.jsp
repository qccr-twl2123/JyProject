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
    <title>列表</title>
     <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <link rel="stylesheet" href="<%=basePath%>css/htmlmember/labary/predefine.css">
    <link rel="stylesheet" href="<%=basePath%>css/htmlmember/list.css">
    <script type="text/javascript">
     var base_inf={
    	    base_herf:"<%=basePath%>",
    	    member_id:"${pd.member_id}"
    };
    </script>
</head>
<script src="<%=basePath%>js/htmlmember/library/jquery-1.12.4.min.js"></script>
<script src="<%=basePath%>js/htmlmember/library/vue.min.js"></script>
<script src="<%=basePath%>js/htmlmember/list.js"></script>
<body>
<!--顶部-->
<header class="top_bar guding">
    <a class="l_item" href="javascript:window.history.back();">
        <img src="<%=basePath%>img/fanhui.png" class="l_fanhui">
    </a>
    <span class="top_bar_name">{{title}}</span>
    <a class="r_item" :href="dd_url">
        <img src="<%=basePath%>img/order_icon_06.png" class="r_jinru">
        <span class="r_jinru">历史订单</span>
    </a>
</header>
    <ul class="change">
        <li class="shopping_list"  v-for="list in data" :id="list.order_id">
            <!--订单状态-->
            <div class="list_title" @click="dd_del(list.order_id)">
                <span>订单已完成</span><span class="ddsj">&nbsp;-&nbsp;{{list.createtime}}</span>
                <span class="ddzt"><img src="<%=basePath%>img/delete_03.png" alt="" style="vertical-align: bottom;"></span>
            </div>
            <div class="cont_bgbox clf" @click="go_store(list.store_id,member_id)">
                <div class="img_box">
                    <div class="img">
                        <img :src="list.pictrue_url" alt="">
                    </div>
                </div>
                <ul class="cont_box">
                    <li>
                        <div>
                            <b style="font-size: 112.5%">{{list.store_name}}</b>
                            <span class="zengsongjf">赠送积分率{{list.integral_rate}}%</span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span>消费金额{{list.sale_money}}元，为您节省{{list.discount_money}}元，实付{{list.actual_money}}元</span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span>本次赠送通用积分{{list.get_integral}}分</span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span>评价增加魅力值{{list.charm_number}}点</span>
                            <span class="pj_wwc" v-if="list.isJudge==0" @click.stop="pj_click(list.order_id,list.store_id,member_id)">点击评价</span>
                            <span class="pj_ywc" v-else>已完成评价</span>
                         </div>
                    </li>
                </ul>
            </div>
        </li>
    </ul>
</body>
</html>