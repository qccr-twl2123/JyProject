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
        <link rel="stylesheet" href="css/zhihui/index.css">
        <link rel="stylesheet" href="css/zhihui/dangan9.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/dangan9.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    	<form action="zhihui_operator_file/list.do" method="post" name="Form" id="Form">
	       <div class="dangan2_d1">
	         <input type="text" placeholder="可输序号名称，手机号码关键字" class="dangan2_d1_ipt1" name="content"></input>
	         <span class="dangan2_d1_btncx" onclick="check()">查询</span>
	         <c:if test="${qx.add eq '1'}">
		         <a class="middle_a" href="zhihui_operator_file/goAdd.do?operator_file_id=${var.operator_file_id}&login_provincename=${pd.login_provincename}&role_type=${pd.role_type}&login_areaname=${pd.login_areaname}&login_cityname=${pd.login_cityname}"  target="ifra"> 
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
	                <td>职务</td>
	                <td>入职时间</td>
	                <td>创建时间</td>
	                <td>操作</td>
	              </tr>  
	              <c:forEach items="${varList}" var="var">
	              	<tr>
		                <td>${var.operator_file_id }</td>
		                <td>${var.operator_name }</td>
		                <td>${var.phone }</td>
		                <td>${var.idnumber }</td>
		                <td>${var.post_name}</td>
		                <td>${var.entry_time }</td>
		                <td>${fn:substring(var.createdate,0,19)} </td>
		                <td>
		                	<c:if test="${qx.edit eq '1'}">
			                	<a target="ifra" href="zhihui_operator_file/goEdit.do?operator_file_id=${var.operator_file_id}&currentPage=${page.currentPage}&login_provincename=${pd.login_provincename}&role_type=${pd.role_type}&login_areaname=${pd.login_areaname}&login_cityname=${pd.login_cityname}" >
			                		<span class="td_sp1">修改</span>
			                	</a>
		                	</c:if>
		                	<c:if test="${qx.delete eq '1'}">
		                		<span class="td_sp1" onclick="del('${var.operator_file_id }')">删除</span>
		                	</c:if>
		                </td>
		              </tr>
	              </c:forEach> 
  	          </table>
	          <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
	       </div>
       </form>
       </c:if>
       <!-- 分页需要的js -->
		<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
 		<script type="text/javascript">window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/ace.min.js"></script>
 		<!-- 引入 -->
		<script type="text/javascript" src="js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="js/bootbox.min.js"></script><!-- 确认窗口 -->
		<script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		//检索
		function check(){
 			$("#Form").submit();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					var url = '<%=path%>/zhihui_operator_file/delete.do?operator_file_id='+Id+'&tm='+new Date().getTime();
					$.get(url,function(data){
						if(data=="success"){
							nextPage(${page.currentPage});
						}
					});
				}
			});
		}
        
		
		
		</script>
    </body>
</html>