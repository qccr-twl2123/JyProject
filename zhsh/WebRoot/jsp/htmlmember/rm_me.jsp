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
    <title>我的人脉</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="css/htmlmember/labary/predefine.css">
    <link rel="stylesheet" href="css/htmlmember/renmai.css">
     <script type="text/javascript">
 	 var base_inf={
	         base_herf:"<%=basePath%>",
	         member_id:"${pd.member_id}",
	      };
	</script>
</head>
<body>
<div class="local_box guding">
    <div class="local_cont_box">
        <span class="title">我的人脉</span>
    </div>
    <span class="icon_left" onclick="goRenMai()"><img src="img/fanhui.png" alt=""></span>
</div>
<div class="search_box-2">
    <div class="inputbox-2">
        <input type="text" maxlength="11" oninput="change(this)" class="tuijiannum">
        <!--背景图  renmai.css   42-->
    </div>
    <span class="btn_search-2" onmouseup="tuijian()">推荐好友</span>
</div>
<ul class="renmai clf">
    <li class="yidu">
        <img src="img/rm_me_05.png" alt="">
        <p>一度人脉 <span>0</span>人</p>
        <p>奖励积分 <span>0</span>分</p>
    </li>
    <li class="erdu">
        <img src="img/rm_me_06.png" alt="">
        <p>二度人脉 <span>0</span>人</p>
        <p>奖励积分 <span>0</span>分</p>
    </li>
</ul>
<div class="link_goin sj_xqimg">
    <span>一度人脉</span>
</div>
<ul class="yd_list">

</ul >
<ul class="er_list">

</ul>
</body>
<script src="js/htmlmember/library/jquery-1.12.4.min.js"></script>
<script src="js/htmlmember/renmai/rm.js"></script>
</html>
