 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>商家会员注册</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>">
        <link rel="stylesheet" href="css/storepc/business_register.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/storepc/business_register.js"></script>
         <style type="text/css">
	a:hover{
 		cursor: pointer;
	}
	</style>
    </head>
    <body>
        <!-- header -->
       <div class="signin_header">
           <a href="<%=basePath%>"><span class="signin_logo">
              <img src="img/logo.png" width="100%" height="100%">
           </span></a>
          <a href="<%=path %>/jsp/storepc/business_signin.jsp">
              <span class="signin_homepage">
                  <span>已有账号？</span>
                  <span class="signin_homepage_sp1">直接登录</span>
              </span>
          </a>
       </div>
       <!-- body -->
       <div class="signin_body">
           <div class="signin_body_right">
                  <div class="signin_body_right_d1">
                      <p>使用手机号注册</p>
                  </div>
                  <div  class="signin_body_right_d2">
                       <span class="signin_body_right_d2_img">
                           <img src="img/body_right0.png" width="100%" height="100%">
                       </span>
                       <input  type="text" placeholder="请输入手机号"  class="right_ipt1" />
                  </div>
                  <div  class="signin_body_right_d2">
                       <span class="signin_body_right_d2_img">
                           <img src="img/body_right1.png" width="100%" height="100%">
                       </span>
                       <input  type="text" placeholder="请输入推荐人手机号"  class="right_ipt1" />
                  </div>
                  <div  class="signin_body_right_d2 signin_body_right_d3">
                       <span class="signin_body_right_d2_img1 signin_body_right_d2_img">
                           <img src="img/body_right3.png" width="100%" height="100%">
                       </span>
                       <input  type="text" placeholder=""  class="right_ipt1 right_ipt2" />
                  </div>
                  <span class="signin_body_right_d2_img2">
                        获取验证码
                  </span>
                   <div  class="signin_body_right_d2">
                       <span class="signin_body_right_d2_img">
                           <img src="img/body_right2.png" width="100%" height="100%">
                       </span>
                       <input  type="text" placeholder="密码长度为6~20个字符"  class="right_ipt1" />
                   </div>
                  <div class="signin_body_right_d2 signin" >
                    同意协议并注册
                  </div>
                  <a href="javascript:vido(0);">
                    <div class="item">
                    《使用条款和协议》
                  </div>
                  </a>
           </div>
            <div class="signin_body_left">
                <p>注册成功后</p>
                <p>在智慧平台全国各城市实体店消费时</p>
                <p>报上 <span class="red">手机号码</span>或 <span class="red">获得优惠</span></p>
                <p class="mgtop10">为你甄选的优质商家</p>
                <p>享受各种优惠外</p>
                <p>还能获得积分获送</p>
                <p class="mgtop3"><span class="red">积分1分</span>=<span class="red">1元钱</span></p>
                <p class="mgtop3">全国通用   永不清零</p>
           </div>
       </div>
       <!-- footer -->
       <div class="signin_footer">
          <div class="footer_d1">
            <span>
               我要开店
            </span>
            <span>
               服务条款和协议
            </span>
            <span>
               联系我们
            </span>
            <span>
               加入我们
            </span>
          </div>
          <div class="footer_d2">
            [沪]CP备504336号-2 本站发布所有内容，未经许可，不得转载
          </div>
       </div>

    </body>

</html>