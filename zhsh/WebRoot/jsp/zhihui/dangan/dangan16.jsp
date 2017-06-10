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
        <title>档案管理</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/zhihui/dangan16.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/dangan16.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihui_friend/contactsList.do" method="post" name="Form" id="Form">
        <div class="dangan_d1">
          <select name="check_type" id="check_type" class="dangan_d1_ipt1">
          	<option value="1" ${pd.check_type eq '1'?'selected':'' }>商家</option>
          	<option value="2" ${pd.check_type eq '2'?'selected':'' }>会员</option>
          </select>
          <span class="dangan_d1_sp1">商家/会员手机号码</span>
           <input type="text" name="content" id="content" class="dangan_d1_ipt1" value="${pd.content}"/>
          <span class="dangan16_sp1" onclick="search()">查询</span>
         </div>
        <div class="dangan16_d1">
	          <div class="dangan16_d1_d1">
	            <span class="dangan16_d1_sp1">一度人脉</span>
	            <span>共${pd.firstnumber}人</span>
	          </div>
	          <div class="dangan16_d1_d1 flright">
	             <span class="dangan16_d1_sp1 ">二度人脉</span>
	             <span class="flright">共${pd.twonumber }人</span>
	          </div>
	          <c:forEach items="${firstContacts}" var="var">
		          <div class="dangan16_d1_d2 ms">
		               <span>${var.name}：${var.phone}</span>
		          </div>
		          <div class="dangan16_d1_d3">
			           <c:forEach items="${var.list}" var="n">
		 		             <span class="flright2 ">${n.name}：${n.phone}</span>
		 	           </c:forEach>
		          </div>
		          <br/>
	          </c:forEach>
          </div>
       </form>
      </c:if>
    </body>
    <script type="text/javascript">
	    $(function(){
	   	       var bool = 0;
 	           $('.ms').click(function(){
	               if(bool == 0){
	                   $(this).next().css('display','none');
	                   bool = 1;
	               }else{
	                   $(this).next().css('display','block');
	                   bool = 0;
	              }
	           });
 	   })
   		function search(){
   			if($("content").val() == ""){
   				alert("请在文本框填写搜索条件");
   			}
			$("#Form").submit();//提交
		}
		
    </script>
</html>