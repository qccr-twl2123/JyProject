<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		 <link rel="stylesheet" href="css/ace.min.css" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/dangan7.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/dangan7.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihui_clerk_file/list.do" method="post" name="Form" id="Form">
       <div class="dangan2_d1">
         <select class="dangan2_d1_st1" name="province_id">
            	<option value="">请选择省</option>
				<c:forEach items="${provincelist}" var="var" varStatus="vs">
					<option value="${var.pcd_id}">${var.name}</option>
				</c:forEach>
				<c:if test="${pd.province_id ne '' and  !empty pd.province_id}">
					<option value="${pd.province_id}"  selected="selected">${pd.province_name}</option>
				</c:if>
         </select>
          <input type="text" name="province_name" id="province_name" value="${pd.province_name}" style="display:none;width:1px;height:1px;"/>
         <input type="text" name="content" placeholder="可输业务员的姓名、身份证号、手机号码" class="dangan2_d1_ipt1"></input>
         <span  class="dangan2_d1_btncx" onclick="search()">查询</span>
           <c:if test="${qx.add eq '1'}">
	           <a class="middle_a" href="zhihui_clerk_file/goAdd.do?sp_file_id=${pd.sp_file_id}"  target="ifra"> 
	          <span class="dangan2_d1_btnmb">添加</span>
	         </a>
           </c:if>
        </div>
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr class="tdtop">
                <td>序号</td>
                <td>姓名</td>
                <td>手机</td>
                <td>身份证</td>
                <td>服务商</td>
                <td>合作商家数量</td>
				<td>创建时间</td>
                <td>操作</td>
              </tr>   
              <c:forEach items="${varList}" var="var" varStatus="vs">
              <tr>
                <td>${var.clerk_file_id}</td>
                <td>${var.clerk_name}</td>
                <td>${var.phone}</td>
                <td>${var.idnumber}</td>
                 <td>${var.team_name}</td>
                 <td>${var.allstore}</td>
                 <td>${fn:substring(var.createdate,0,19)} </td>
                <td> 
                	 <c:if test="${qx.edit eq '1'}">
                		<a href="zhihui_clerk_file/goEdit.do?clerk_file_id=${var.clerk_file_id}&currentPage=${page.currentPage}"><span class="td_sp1">修改</span></a>
                	</c:if>
                	<c:if test="${qx.delete eq '1'}">
                		<a href="zhihui_clerk_file/delete.do?clerk_file_id=${var.clerk_file_id}&currentPage=${page.currentPage}"><span class="td_sp1">删除</span></a>
                	</c:if>
               </td>
               </tr>
              </c:forEach>
          </table>
       </div>
        <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
        </form>
       </c:if>
       <!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/ace-elements.min.js"></script>
		<script src="js/ace.min.js"></script>
		
		<script type="text/javascript" src="js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框--> 
		</body>
    <script type="text/javascript">
 		
		//检索
		function search(){
			if($("#province_id option:selected").val() != ""){
				var province_name=$("#province_id option:selected").text();
				$("#province_name").show();
				$("#province_name").val(province_name);
			}
  			$("#Form").submit();
		}
    </script>
</html>