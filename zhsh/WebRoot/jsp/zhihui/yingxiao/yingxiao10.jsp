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
        <title>营销管理</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
		<link rel="stylesheet" href="css/ace.min.css" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/yingxiao10.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/yingxiao10.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
	<c:if test="${qx.look eq '1'}">
       <div class="dangan2_d1">
          <c:if test="${qx.add eq '1'}">
	          <a class="middle_a" href="zhihui_sort_chain/goAdd.do"  target="ifra">
	           <span class="zhifu1_btn1">添加外链</span>
	          </a>
          </c:if>
       </div>
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
          	   <tr>
          	  	<td style="height: 10px;width: 200px">广告位置</td>
          	  	<td>排序</td>
          	  	<td>显示位置</td>
          	  	<td>省市区</td>
          	  	<td>图片</td>
          	  	<td>网址链接</td>
           	  	<td>操作</td>
          	  </tr> 
          <c:forEach items="${varList }" var="var" varStatus="vs">
              <tr >
                <td style="height: 10px;width: 100px">${var.advertising }</td>
                <td>${var.ranking}</td>
                 <td>${var.show_position }</td>
                <td>${var.province_name }${var.city_name }${var.area_name }</td>
                <td><img src="${var.image_url}" width="100px" height="100px"/></td>
                <td>${var.website }</td>
                 <td >
                <%-- <c:if test="${qx.edit eq '1'}">
                <a href="zhihui_sort_chain/goEdit.do?sort_chain_id=${var.sort_chain_id}&currentPage=${page.currentPage}">
                  <span class="blue ">修改</span>
                </a>
                </c:if> --%>
                <c:if test="${qx.delete eq '1'}">
	                <a href="zhihui_sort_chain/delete.do?sort_chain_id=${var.sort_chain_id}&currentPage=${page.currentPage}">
	                  <span>删除</span>
	                </a> 
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