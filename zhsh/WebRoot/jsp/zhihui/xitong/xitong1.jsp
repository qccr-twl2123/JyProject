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
        <title>系统管理</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
		<link rel="stylesheet" href="css/ace.min.css" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/xitong1.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/xitong1.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
	<c:if test="${qx.look eq '1'}">
       <div class="dangan2_d1">
          <c:if test="${qx.add eq '1'}">
          <a class="middle_a" href="zhihui_menu_role/goAdd.do"  target="ifra">
           <span class="zhifu1_btn1">添加角色</span>
          </a>
          </c:if>
       </div>
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr >
                <td>序号</td>
                <td>角色名称</td>
                <td>操作 </td>
              </tr> 
              <c:forEach items="${varList }" var="var" varStatus="vs">
              <tr >
                <td>${vs.index+1 }</td>
                <td>${var.menu_role_name }</td>
                <td>
                	<c:if test="${var.role_type eq '1' || var.role_type eq '2' }"> 
	                	<c:if test="${qx.edit eq '1'}">
	 	               	 	<a href="zhihui_menu_role/goEdit.do?menu_role_id=${var.menu_role_id }&currentPage=${page.currentPage}"><span>修改</span></a>
	  	                	</c:if>
	  	                	<c:if test="${qx.delete eq '1'}">
	  	                	<a href="zhihui_menu_role/delete.do?menu_role_id=${var.menu_role_id }&currentPage=${page.currentPage}"><span>删除</span></a>
		                 	</c:if>
	                 </c:if> 
	                 <c:if test="${var.role_type eq '0' }"> 
		                 <c:if test="${qx.edit eq '1'}">
	 	               	 	<a href="zhihui_menu_role/goEdit.do?menu_role_id=${var.menu_role_id }&currentPage=${page.currentPage}"><span>修改</span></a>
	 	                 </c:if>
 	                 </c:if> 
 	                 <c:if test="${var.role_type eq '99' }"> 
 	               	 	<span>不可操作</span>
 	                 </c:if> 
                  </td>
              </tr> 
               </c:forEach>              
          </table>
       </div>
 <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
 </c:if>
    </body>
</html>