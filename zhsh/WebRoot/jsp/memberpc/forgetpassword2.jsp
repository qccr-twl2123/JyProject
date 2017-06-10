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
    	    base_href:"<%=basePath%>" ,
    	    x:"${fgpphone}"
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
        <div class="step ">
            <span>1.确认账号</span>
            <div class="sj_box">
                <div class="triangle_border_right ">
                    <span></span>
                </div>
            </div>
        </div>
        <div class="step act">
            <div class="sj_lbox  ">
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
    
<form action="memberpc/guP3.do" method="post" name="Form" id="Form">  
    <!--步骤二-->
    <div class="inf innerwidth b1 lh2 mg_b_8">
        <ul class="tac" style="padding: 5% 0 10% 0;">
            <li>
                <img src="img/tip.png" alt="">
            </li>
            <li>
                <span>您绑定的手机号</span>
                <span class="col_e9">${jmfgpphone}</span>
            </li>
            <li>
                <!--time在js中预定义即可-->
                <input type="hidden" name="password_token" id="password_token" value="${password_token}" >
                <span class="cxhq_button"><span class="time">60</span>s后重新获取</span>
                <span class="hq_button" onclick="gc()">获取动态码</span>
                <span class="notext">没有收到短信验证码？
                    <div class="notext_tip">
                        <div class="triangle_border_up">
                            <span></span>
                        </div>
                        <ul>
                            <li>网络通讯异常可能会造成短信丢失，请重新发送短信</li>
                            <li>请核实手机是否已欠费停机，或者屏蔽了系统短信</li>
                            <li>如果手机<span class="col_e9">${jmfgpphone}</span>已丢失或停用，请 <a href="" class="col_42b1fd">联系客服</a></li>
                        </ul>
                    </div>
                </span>
            </li>
            <li>
                <span>短信动态码：</span>
                <input type="text" id="mscode" maxlength="6" >
             </li>
            <li>
                <span class="qr_button mg_r_8" onclick="sc()">下一步</span>
                <a href="javascript:window.history.back(-1);" class="before_bz"><span class="col_e9 cp"> 上一步</span></a>
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
<script src="js/memberpc/fp2.js"></script>
</html>