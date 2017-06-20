<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>九鱼销链平台服务协议</title>
         <meta name="viewport" content="width=device-width">
         <meta charset="utf-8">
        <style type="text/css">
        html {
         font-size : 20px;
         /*6*/
		}
		@media only screen and (max-width: 320px){
		   html {
		         font-size: 17px !important;
		         /*5 4  */
		       }
		}
		 @media only screen and (min-width: 414px){
		    html {
		        font-size: 22px !important;
		        /*6s*/
		         }
		}
  		*{
		  margin: 0;
		  padding: 0;
		  
		}
        span{
          display: inline-block;
        }
        dl{
          width: 100%;
          /* line-height: 1.5rem; */
          font-size: .8rem;
        }
        .nav{
          width: 100%;
          height: 2rem;
          text-align: center;
          line-height: 2rem;
          background-color: #ff0600;
          color: #fff;
          position: relative;
          font-size: .85rem;
        }
        .nav>span{
          width: 1rem;
          height: 2rem;
          position: absolute;
          left: .75rem;
          font-size: 1rem;
        }
        dl dt{
          width: 100%;
          height: 2.5rem;
          line-height: 2.5rem;
          font-size: 18px;
          text-align: center;
          background-color: #eee;
              font-weight: 600;
        }
        dl dd{
          width: 100%;
          margin: 0 auto;
        }
        dl dd img{
          width: 100%;
          outline-width:0px;  
  		  vertical-align:top;
        }
        body dl:first-child{
          margin-top: 0;
        }
        .back-arrow {
		    width: 10px;
		    height: 10px;
		    border-width: 0 0 2px 2px;
		    margin-left: 10px;
 		    margin-top: 14px;
		    border: solid #fff;
		    font-size: 0;
		    line-height: 0;
		}
        </style>
        <script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
    </head>
    <body>
        <div class="nav">
          <span><a href="javascript:history.go(-1);" style="font-size: 23px;text-decoration: none;color:#fff;"><</a></span>
         	 商家详情图
        </div>
        <dl>
            <dt>${spd.store_name}</dt>
            <dd style="color:#02DCF5;">经营范围：</dd>
            <dd style="font-size: 24px;">${spd.management_projects_desc}</dd>
            <dd style="color: #02DCF5;">商家介绍：</dd>
            <dd style="font-size: 24px;">${spd.store_introduce }</dd>
            <c:forEach items="${three}" var="s" varStatus="vs">
             	<c:if test="${s.text ne ''  }">
            		<dd style="font-size: 24px;">${s.text}</dd>
            	</c:if>
            	<c:if test="${s.image_url ne '' }">
            		<dd><img src="${s.image_url}"></dd>
            	</c:if>
             </c:forEach>
          </dl>
     </body>
</html>