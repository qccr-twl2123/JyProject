<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>事项预沟通</title>
        <meta charset="utf-8">
         <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/zhihui/shixiang7.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/shixiang7.js"></script>
        <style type="text/css">
        	input[type=redio]{
			    opacity: 1;
			    position: initial;
 			}
        </style>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihui_keyword_reply/${msg }.do" name="Form" id="Form" method="post">
       <div class="yingxiao8_d1">
        <span class="yingxiao8_d1_sp1">关键字</span>
          <input type="hidden" name="keyword_reply_id" value="${pd.keyword_reply_id}"/>
        <input type="text" name="keyword" value="${pd.keyword }"  class="yingxiao8_d1_ipt1" />
      </div> 
      <div class="yingxiao8_d1">
        <span class="yingxiao8_d1_sp1">用户类型</span>
         <span class="">
           <label>
             <input class="shixiang3_ipt1" type="radio" value="1" <c:if test="${pd.target_type eq '2' }">checked</c:if>   name="target_type" />
             <span class="shishiang3_sp1_sp">商家</span>
           </label>
         </span>
         
         <span class="shishiang3_sp1">
           <label>
             <input class="shixiang3_ipt1" type="radio" value="2" <c:if test="${pd.target_type eq '1' }">checked</c:if> name="target_type" />
             <span class="shishiang3_sp1_sp">会员</span>
           </label>
         </span>
      </div>
      <div class="yingxiao8_d1  height100" >
          <span class="yingxiao8_d1_sp1 ftleft">回复内容</span>
          <input  type="text" name="content" value="${pd.content }"  class="shixiang3_ipt ftleft"  />
      </div>
      <div class="yingxiao8_d1">
      <c:if test="${qx.add eq '1'}">
          <input type="submit" class="yingxiao8_yes mgleft" value="保存"/>
          </c:if>
          <a class="middle_a" href="zhihui_keyword_reply/list.do"  target="ifra">
            <span class="yingxiao8_yes">退出</span>
          </a>   
      </div> 
     </form>
      </c:if>

    </body>
</html>