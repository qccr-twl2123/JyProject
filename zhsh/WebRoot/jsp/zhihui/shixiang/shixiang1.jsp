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
        <title>事项与沟通</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
		<link rel="stylesheet" href="css/ace.min.css" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/shixiang1.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/shixiang1.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
     <c:if test="${qx.look eq '1'}">
      <form action="zhihuired_platform/listNRreview.do" name="Form" id="Form" method="post">
	       <div class="dangan2_d2">
	          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
	              <tr >
	                <td>序号</td>
	                <td>操作员</td>
	                <td>红包金额</td>
	                <td>数量</td>
	                <td>省</td>
	                <td>市</td>
	                <td>区/县</td>
	                <td>发送时间</td>
	                <td>操作</td>
	              </tr> 
	              <c:forEach items="${varList }" var="var" varStatus="vs">
	              <tr >
	                <td>${vs.index+1 }</td>
	                <td>${var.operator_name }</td>
	               	<td>${var.money}</td>
	                <td>${var.redpackage_number }</td>
	                <td>${var.province_name }</td>
	                <td>${var.city_name }</td>
	                <td>${var.area_name }</td>
	                <td>${var.starttime}-${var.endtime}</td>
	                
	                <td > <c:if test="${qx.edit eq '1'}">
		                <c:if test="${var.review_status eq '0'}">
		                  <a href="zhihuired_platform/edit.do?review_status=1&red_platform_id=${var.red_platform_id}">批准</a>
		                  <a href="zhihuired_platform/edit.do?review_status=99&red_platform_id=${var.red_platform_id}">驳回</a>
		                </c:if>
	                </c:if> </td>
	                 
	              </tr> 
	              </c:forEach>
	          </table>
	       </div>
	      <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
      </form>
      </c:if>
    </body>
 
</html>