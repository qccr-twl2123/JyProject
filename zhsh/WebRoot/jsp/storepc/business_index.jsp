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
        <title>商家首页</title>
        <meta charset="utf-8">
          <base href="<%=basePath%>">
          <link rel="shortcut icon" href="<%=basePath%>favicon.ico" >
        <link rel="Bookmark" href="<%=basePath%>favicon.ico">
   		<link rel="icon" type="image/gif" href="<%=basePath%>animated_favicon1.gif" >
        <link rel="stylesheet" href="css/storepc/business_index.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/storepc/business_index.js"></script>
        <style type="text/css">
        .index_footer {
		    width: 90%;
		    left: 5%;
		    margin-bottom: 2%;
		    z-index: 7;
		}
		.index_header {
		    width: 90%;
		    margin: 0 auto;
		}
		.index_footer_img {
		     width: 115px;
		}
		a:hover{
			color:red;
		}
		
        </style>
     </head>
    <body> 
        <div class="index_header">
           <a href="<%=path %>/memberpc/goMemberRegister.do">
             <span class="index_header_sp1">会员注册</span>
           </a>
          <a href="<%=path %>/memberpc/goMemberLogin.do" >
            <span class="index_header_sp1">会员登录</span>
          </a>
          <a href="<%=path %>/storepc/goLogin.do" >
            <span class="index_header_sp1 ftright">商家主页</span>
          </a>
          <a href="<%=path %>/memberpc/goMemberOne.do" >
          <span class="index_header_sp1 ftright">网站首页</span>
          </a>
        </div>
        <div class="index_body"> 
             <div class="index_bg">
                <img src="img/indexbg.png"  style="width: 63%;display: block;margin: 0 auto;">
            </div>
         </div>
        <div class="index_footer ">
            <span class="index_footer_img mgleft10">
                <img src="img/weixin1.png" width="110px">
                                                 安卓手机下载
            </span>
            <span class="index_footer_img">
                <img src="img/weixin2.png" width="110px">
                                                苹果手机下载
            </span>
            <span class="index_footer_img">
                <img src="img/weixin3.png" width="110px">
                                                关注微信公众号
            </span>
            <div>
             	<span class="index_footer_sp1"></span>
                <a href="<%=path %>/storepc/goRegister.do" ><span class="index_footer_sp1 mgleft20">我要开店</span></a>
                <a  href="<%=basePath%>jsp/storepc/gyjy.html"  target="_blank">
                	<span class="index_footer_sp1">关于九鱼</span>
                </a>
                <span class="index_footer_sp1">加入我们</span>
            </div>
            <div class="index_footer_d1">
                [浙] ICP备16025718号-2 本站发布所有内容，未经许可，不得转载
            </div>
        </div>
    </body>
</html>