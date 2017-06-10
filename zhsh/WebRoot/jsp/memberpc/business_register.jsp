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
    <title>会员注册</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" >
    <link rel="Bookmark" href="<%=basePath%>favicon.ico">
   	<link rel="icon" type="image/gif" href="<%=basePath%>animated_favicon1.gif" >
    <link rel="stylesheet" href="css/memberpc/hsd_hyzc.css">
    <link rel="stylesheet" href="css/memberpc/normalize.min.css">
    <link rel="stylesheet" href="css/memberpc/response.css">
    <script type="text/javascript">
    var base_inf={
    	    base_href:"<%=basePath%>" ;
      };
    </script>
 </head>
<body>
<!--头部-->
    <header>
        <div class="head_cont">
            <a href="<%=basePath%>"><img src="img/hydl-logo_03.png" alt="" class="logo"></a>
            <div>&nbsp;•会员注册</div>
        </div>
    </header>
<!--内容-->
<section>
	<form action="" name="Form" id="Form" method="post">
     <div class="cont">
        <div class="denglu">
            <h6>手机号码注册 <a href="memberpc/goMemberLogin.do">已有账号登录>></a></h6>
            <ul>
            	<li><span>手机号码：</span><input oninput="listPhone()" class="inp" type="text" placeholder="请输入手机号码"  id="be_phone" name="be_phone"  maxlength="11"  onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" ></li>
            	<li class="tj">
            		<span>推荐人：</span><select class="inp" id="phone" name="phone"  ><option value="0@0@0">可不填写，直接注册</option></select>
                </li>
            	<li><span>密码：</span><input class="inp"  type="password" placeholder="密码长度为6~20个字符" name="password" id="password"></li>
                <li><span>图形码：</span><input placeholder="图形码" type="text"   class="yzminp"  name="txcode" id="txcode">
                						<img class="yzmimg"  id="imgObj" src="verifyCodeServlet" onclick="changeImg()" ></a></li>
				<li><span>验证码：</span><input type="text"   class="yzminp" id="phonecode" name="phonecode">
										<a class="huoqu numbercode" onclick="getCode()">获取验证码</a></li>
            	<li><p class="zhuce" onclick="zhuceOK()">同意协议并注册</p></li>
            	<li style="height:40px;    text-align: right;"><a href="jsp/storepc/memberuserAgreement.html"target="_blank" style="color:#1ca4ea;line-height:2;font-size:14px;">《会员使用条款和协议》</a></li>
             </ul>
        </div>
    </div>
    </form>
</section>
<!--底部-->
<footer>
    <div class="footcont">
        <ul>
            <li><a href="storepc/goRegister.do" class="noborder">我要开店</a></li>
            <li><a href="jsp/storepc/gyjy.html"  target="_blank">关于九鱼</a></li>
            <li><a href="">加入我们</a></li>
            <li><a href="">合作流程</a></li>
            <li><a href="">常见问题</a></li>
        </ul>
    </div>
    <div class="beian"> [浙] ICP备16025718号-2 本站发布所有内容，未经许可，不得转载</div>
</footer>
<script src="js/jquery-1.8.0.min.js"></script>
<script src="js/jquery.form.js"></script>
<script src="js/jquery.tips.js"></script>
<script src="js/memberpc/register.js"></script>
</body>
</html>