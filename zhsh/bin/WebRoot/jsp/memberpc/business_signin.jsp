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
    <title>会员登录</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" >
    <link rel="Bookmark" href="<%=basePath%>favicon.ico">
   	<link rel="icon" type="image/gif" href="<%=basePath%>animated_favicon1.gif" >
    <link rel="stylesheet" href="css/memberpc/hsd_hydl.css">
    <link rel="stylesheet" href="css/memberpc/bootstrap.min.css">
    <script type="text/javascript">
    var base_inf={
    	    base_href:"<%=basePath%>" 
      };
    </script>
 </head>
<body>
<!--头部-->
    <header>
        <div class="head_cont">
            <a href="<%=basePath%>"><img src="img/hydl-logo_03.png" alt="" class="logo"></a>
            <span>&nbsp;•九鱼网</span>
        </div>
    </header>
<!--内容-->
<section>
    <div class="cont">
       
    </div>
     <div class="denglu">
            <h6>会员登录 <a href="memberpc/goMemberRegister.do">免费注册</a></h6>
            <div class="yhm"><!--用户名-->
                <div class="rowborder">
                    <img src="img/hy_load3.png" alt="">
                </div>
                <input type="text" placeholder="已注册手机号" name="phone" id="phone" maxlength="11"   onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"  >
            </div>
            <div class="mm"><!--密码-->
                <div class="rowborder">
                    <img src="img/hy_load1.png" alt="">
                </div>
                <input type="password" placeholder="密码" maxlength="16" name="password"  id="password"  onclick="changeText()">
            </div>
            
            <div class="yzm txcode" style="display:none"><!--验证码-->
                <div class="rowborder">
                    <img src="img/hy_load2.png" alt="">
                </div>
                <input type="text" placeholder="图形码" maxlength="6" name="code" id="code"  onclick="changeText()">
                <img src="verifyCodeServlet" alt="" class="yzm_img"  id="imgObj" onclick="changeImg()">
            </div>
            
            <div class="zhcz"><!--账号操作-->
                <input type="checkbox" checked  id="saveid">
                <span>记住登录帐号</span>
                <a href="memberpc/gfp2.do" style="color:#38d8ea;">忘记密码？</a>
            </div>
            <div class="button"    id="login">登录</div>
        </div>
</section>
<!--底部-->
<footer>
    <div class="footcont">
        <ul>
            <li><a href="storepc/goRegister.do" class="noborder">我要开店</a></li>
            <li><a href="jsp/storepc/gyjy.html" target="_blank">关于九鱼</a></li>
            <li><a href="">加入我们</a></li>
            <li><a href="">合作流程</a></li>
            <li><a href="">常见问题</a></li>
        </ul>
    </div>
    <div class="beian">[浙] ICP备16025718号-2 本站发布所有内容，未经许可，不得转载</div>
</footer>
</body>
<script src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script src="js/memberpc/login.js"></script>
</html>