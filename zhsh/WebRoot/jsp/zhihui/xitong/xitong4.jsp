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
        <link rel="stylesheet" href="css/zhihui/xitong4.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/xitong4.js"></script>
    </head>
    <body>
    <form action="<%=path %>/zhihui_menu_qx/edit.do" name="Form" id="Form" method="post">
    <input type="hidden" value="${pd.operator_file_id }" name="operator_file_id"/>
    <c:if test="${qx.look eq '1'}">
      <div class="yingxiao8_d1">
        <span class="yingxiao8_d1_sp1">角色</span>
        
        	<select name="menu_role_id" name="menu_role_id">
        		<c:forEach items="${roleList}" var="var">
        			<option value="${var.menu_role_id }" ${var.menu_role_id eq pd.menu_role_id?'selected':''}>${var.menu_role_name}</option>
        		</c:forEach>
        	</select>
        <span class="yingxiao8_d1_sp1">账号</span>
        <input class="yingxiao8_d1_ipt1" type="text" value="${pd.phone }" readonly="readonly"/>
      </div> 
      <div class="yingxiao8_d1">
        <span class="yingxiao8_d1_sp1">名称</span>
        <input class="yingxiao8_d1_ipt1" type="text" value="${pd.operator_name }"  readonly="readonly"></input>
      </div>
      <c:if test="${qx.add eq '1'}">
       <div class="yingxiao8_d1">
          <span class="yingxiao8_yes" onclick="search()">确定</span>
      </div> 
      </c:if>
      </c:if>
      </form>
     </body>
     <script type="text/javascript">
     	function search(){
    	    $("#Form").submit();
    	}
     </script>
</html>