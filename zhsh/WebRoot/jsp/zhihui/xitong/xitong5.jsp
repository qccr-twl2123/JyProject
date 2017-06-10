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
        <link rel="stylesheet" href="css/zhihui/xitong5.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/xitong5.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr >
                <td>编号</td>
                <td>名称</td>
                <td>类型</td>
                <td>位置</td>
                <td style="width: 500px;height: 50px">文本</td>
                <td></td> 
              </tr> 
              <c:forEach items="${varList }" var="var" varStatus="vs"> 
             <tr >
                <td>${var.menu_text_id }</td>
                <td>${var.name}</td>
                <td>
                	<c:if test="${var.type eq '1'}">app端</c:if>
                	<c:if test="${var.type eq '2'}">pc</c:if>
                </td>
                <td>${var.position }</td>
                <td style="width: 500px;height: 50px">${var.text }</td>
                <c:if test="${qx.edit eq '1'}">
                <td class="blue ">
                <a class="middle_a" href="zhihui_menu_text/goEdit.do?menu_text_id=${var.menu_text_id }"  target="ifra">修改</a>
                </td>
                </c:if>
              </tr> 
          </c:forEach>
          </table>
       </div>
 <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
 </c:if>
    </body>
</html>