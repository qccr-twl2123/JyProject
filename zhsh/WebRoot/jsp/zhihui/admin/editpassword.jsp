<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
 		<meta charset="utf-8" />
		<title>修改密码</title>
 		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
 	</head>
<body>


<form action="zhihui_editpassword.do" name="Form" id="Form" method="post">
	<input type="hidden" name="type" value="${logininfor.login_type}"/> 
	<input type="hidden" name="id" value="${logininfor.login_id}"/> 
    <div>
  				<table id="table_report" class="table table-striped table-bordered table-hover">
					<tr>
						<td style="width:15%">旧密码:</td>
						<td><input type="password"   id="oldpassword" name="oldpassword" placeholder="请输入旧密码" style="width:90%"  /></td>
					</tr>
					<tr>
						<td style="width:15%">新密码:</td>
						<td><input type="password" id="newpassword"  name="newpassword" placeholder="请输入新密码" style="width:90%"/></td>
					</tr>
					<tr>
						<td style="text-align: center;" colspan="100">
							<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
							<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
						</td>
					</tr>
 				</table>
	</div>
 </form>
 	 	<!-- 引入 -->
	 	<script type="text/javascript">window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");</script>
 		<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="js/jquery.tips.js"></script>
 		<script type="text/javascript">
			//保存
			function save(){
 		 		if($("#oldpassword").val()  !=  "${logininfor.login_password}"){
					$(".btn-primary").tips({
						side:1,
			            msg:'旧密码错误请重新填写',
			            bg:'#AE81FF',
			            time:3
			        });
					$("#oldpassword").focus();
					return false;
				}
 				$("#Form").submit();
		 	}
		</script>
 </body>
</html>