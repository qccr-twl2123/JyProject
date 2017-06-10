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
        <title>发票寄送信息</title>
         <meta charset="utf-8">
         <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link href="css/bootstrap.min.css" rel="stylesheet" />
        <script src="js/jquery-1.8.0.min.js"></script>
     </head>
    <body>
 		<form action="fapiao/startSend.do" name="Form" id="Form" method="post" >
				<input type="hidden" name="bill_id"  value="${pd.bill_id}"/>
				<input type="hidden" name="chuli_status"   value="${pd.chuli_status}"/>
 				<table id="table_report" class="table table-striped table-bordered table-hover">
					<tr>
						<td>收件人:</td>
						<td>${fppd.principal}</td>
					</tr>
					<tr>
						<td>收件地址:</td>
						<td>${fppd.sp_address}</td>
					</tr>
					<tr>
						<td>联系电话:</td>
						<td>${fppd.phone}</td>
					</tr>
					<tr>
						<td>物流公司:</td>
						<td><input type="text"   name="kd_name" id="kd_name" value=""/></td>
					</tr>
					<tr>
						<td>快递编号:</td>
						<td> <input type="text"   name="kd_number" id="kd_number" value="" /></td>
					</tr>
					<tr>
						<td style="text-align: center;" colspan="100">
							<a class="btn btn-mini btn-primary" onclick="save();">寄送</a>
							<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
 						</td>
					</tr>
 				</table>
 	      </form>
 	      <script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框-->
	      <script type="text/javascript">
 			    //保存
			  	function save(){
			  		if($("#kd_name").val()==""){
			  			$("#kd_name").tips({
			  				side:3,
			  	            msg:'请输入快递名称',
			  	            bg:'#AE81FF',
			  	            time:2
			  	        });
			  			$("#kd_name").focus();
			  			return false;
			  		}
			  		if($("#kd_number").val()==""){
			  			$("#kd_number").tips({
			  				side:3,
			  	            msg:'请输入快递编号',
			  	            bg:'#AE81FF',
			  	            time:2
			  	        });
			  			$("#kd_number").focus();
			  			return false;
			  		}
   			  		 //提交
		 	  		$("#Form").submit();
			  	}
  				
 	      </script>
      </body>
</html>