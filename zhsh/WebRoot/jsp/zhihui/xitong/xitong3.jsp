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
        <link rel="stylesheet" href="css/zhihui/xitong3.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/xitong3.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
       <form action="<%=basePath%>/zhihui_menu_qx/list.do" name="Form" id="Form" method="post">
	       <c:if test="${qx.look eq '1'}">
	       <div class="dangan2_d1">
	          <span  class="zhifu1_sp1">角色</span>
	         <select class="zhifu1_st1" name="menu_role_id" >
	         	<option value="">请选择</option>
	           	<c:forEach items="${roleList}" var="var">
	           		<option value="${var.menu_role_id}" ${var.menu_role_id eq pd.menu_role_id?'selected':''}  >${var.menu_role_name}</option>
	           	</c:forEach>
	         </select>
	         	 <input class="zhifu1_ipt1" type="text" placeholder="可输入账号/角色名称等查询" name="content" value="${pd.content}"/>
	            <span class="zhifu1_btn1" onclick="search()">搜索</span>
	          <!-- </a> -->
	       </div>
	       <div class="dangan2_d2">
	          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
	              <tr >
	                <td>序号</td>
	                <td>名称</td>
	                <td>账号</td>
	                <td>角色</td>
	                 <td>操作</td> 
	              </tr> 
	              <c:forEach items="${varList}" var="var" varStatus="vs">
	              <tr >
	                <td>${var.operator_file_id }</td>
	                <td>${var.operator_name }</td>
	                <td>${var.phone }</td>
	                <td>${var.menu_role_name}</td>
	                <c:if test="${qx.edit eq '1'}">
	                <td class="blue bctitle">
	                	<a class="middle_a" href="zhihui_menu_qx/goEdit.do?operator_file_id=${var.operator_file_id}&menu_role_id=${var.menu_role_id }"  target="ifra">修改</a>
	                </td>
	                </c:if>
	              </tr> 
	              </c:forEach>
	          </table>
	       </div>
	      <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
	   </c:if>
   </form>
    </body>
    <script type="text/javascript">
    	function search(){
    		$("#Form").submit();
    	}
    </script>
</html>