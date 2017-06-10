<!-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> -->
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
        <title>欢迎来到九鱼商家中心</title>
        <base href="<%=basePath%>">
        <link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
        <meta charset="utf-8">
        <link rel="stylesheet" href="<%=basePath%>css/storepc/payment.css">
        <script src="<%=basePath%>js/storepc/jquery-1.8.0.min.js"></script>
         <style type="text/css">
            a:hover{
            	color:red;
            }
			.whwclass {
			    font-size: 26px;
			    margin: 8px auto;
			    line-height: 68px;
			}
   			.signin_header {
			    width: 50%;
			    min-height: 122px;
			    margin: 2% auto;
			    border-bottom: 1px solid #ddd;
			}	
			.signin_logo {
			    display: block;
			    float: left;
			    width: 112px;
			    height: 112px;
			    margin-left: 0;
 			}
 			.signin_body {
			    display: block;
			    width: 50%;
			    margin: 0 auto;
			}
 			.header_p1 {
			    width: 30%;
			    float: right;
			    font-size: 22px;
			    line-height: 122px;
			    text-align: center;
			}
 			.header_p1 span {
			    display: inline-block;
			    height: 24px;
			    line-height: 24px;
			    border-radius: 5px;
			    color: #fff;
			    text-align: center;
			    background-color: #169ada;
			    margin-left: 20px;
			    cursor: pointer;
			    padding: 5px;
			}
        </style>
    </head>
    <body>
       <!-- header -->
       <div class="signin_header">
           <span class="signin_logo">
               <img src="img/logo.png" style="width:70px;height:70px;margin-top:25px;">
           </span>
           <span class="signin_header_sp1"> 
              	欢迎你！${storepd.store_name}
           </span>
        </div>
        <div class="signin_body">
            <iframe name="shezhiifra"  src="${pd.url}"></iframe>
       </div>
     <script type="text/javascript"> 
        //开始设置
     	function nowstart(){
     		window.location.href='<%=basePath%>storepc/goSheZhiOne.do?store_id=${pd.store_id}&jichushezhi=10000000000';
      	}
     </script>
    </body>
</html>