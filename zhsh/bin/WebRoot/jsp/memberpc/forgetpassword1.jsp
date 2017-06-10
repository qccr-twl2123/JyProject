<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>忘记密码</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="css/memberpc/library/predefine.pc.css">
    <link rel="stylesheet" href="css/memberpc/changepassword.css">
    <script type="text/javascript">
    var base_inf={
    	    base_href:"<%=basePath%>" 
      };
    </script>
 </head>
<body ondragstart ="return false" oncontextmenu="return false">
<hrader>
    <div class="headbox innerwidth">
        <img src="img/logo.png" alt="<%=basePath%>">
        <div class="flr head_r">
            <span>已有九鱼账号？</span>
            <a href="<%=basePath%>memberpc/goMemberLogin.do"><span class="loading">登录</span></a>
        </div>
    </div>
    <div style="border-bottom: 4px solid #e90000"></div>
</hrader>
<section>
    <!--内容顶部-->
    <div class="sec_box innerwidth">
        <p class="fs120">找回密码</p>
        <div style="border-bottom:2px solid #dcdcdc;position: relative;">
            <div style="position: absolute; border-bottom: 2px solid #222; left:0; bottom: -2px;width: 5rem;"></div>
        </div>
    </div>
    <!--进度条-->
    <!--在step上添加act属性 即改变颜色-->
    <div class="step4 innerwidth txc mg_b_12">
        <div class="step act">
            <span>1.确认账号</span>
            <div class="sj_box">
                <div class="triangle_border_right ">
                    <span></span>
                </div>
            </div>
        </div>
        <div class="step ">
            <div class="sj_lbox ">
                <div class="triangle_border_right_bottom"></div>
                <div class="triangle_border_right_top"></div>
            </div>
            <span>2.选择验证方式</span>
            <div class="sj_box">
                <div class="triangle_border_right">
                    <span></span>
                </div>
            </div>
        </div>
        <div class="step ">
            <div class="sj_lbox">
                <div class="triangle_border_right_bottom"></div>
                <div class="triangle_border_right_top"></div>
            </div>
            <span>3.验证</span>
            <div class="sj_box">
                <div class="triangle_border_right">
                    <span></span>
                </div>
            </div>
        </div>
        <div class="step">
            <div class="sj_lbox">
                <div class="triangle_border_right_bottom"></div>
                <div class="triangle_border_right_top"></div>
            </div>
            <span>4.完成</span>
        </div>
    </div>

<!--步骤一-->
<form action="memberpc/gFP2.do" name="Form" id="Form" method="post">
    <div class="inf innerwidth b1 lh2 mg_b_8">
        <ul style="padding: 5% 0 10% 0;">
            <li class="clf">
                <div style="width: 40%;" class="tar fll">九鱼账户：</div>
                <div style="width: 60%;" class="tal fll">
                    <input type="text" style="width: 190px;" placeholder="请输入手机号码" maxlength="11" id="jphone_y">
                </div>
            </li>
            <li class="clf">
                <div style="width: 40%;" class="tar fll">验证码：</div>
                <div style="width: 60%;" class="tal fll">
                    <input type="text" style="width:90px;" class="fll" id="txcd_lu">
                    <span class="yzm b1 fll "><img src="verifyCodeServlet" alt="" class="yzm_img"  id="imgObj" onclick="changeImg()" style=" width: 100%; height: 26px; "></span>
                    <span class="col_e9 fll cp" onclick="changeImg()" >看不清楚？换一张</span>
                </div>
            </li>
            <li class="clf">
                 <div style="width: 40%;" class="tar fll">&nbsp;</div>
                <div style="width: 60%;" class="tal fll">
                    <span class="qr_button" >下一步</span>
                </div>
            </li>
        </ul>
    </div>
 </form>
</section>
<footer>
    <div class="bb1"></div>
    <div class="foot_box innerwidth tac col_a pd_15">
        <span>©2016 jiuyuvip.com [浙] ICP备16025718号-2 ,All Rights Reserved.</span>
    </div>
</footer>
</body>
<script src="js/memberpc/library/jquery-1.12.4.min.js"></script>
<script src="js/memberpc/fp1.js"></script>
</html>