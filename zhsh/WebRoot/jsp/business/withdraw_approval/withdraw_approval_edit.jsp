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
		<title></title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
        <meta name="renderer" content="webkit">
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
		<link href="css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="css/font-awesome.min.css" />
		<!--[if IE 7]><link rel="stylesheet" href="css/font-awesome-ie7.min.css" /><![endif]-->
		<!--[if lt IE 9]><link rel="stylesheet" href="css/ace-ie.min.css" /><![endif]-->
		<!-- 下拉框 -->
		<link rel="stylesheet" href="css/chosen.css" />
		
		<link rel="stylesheet" href="css/ace.min.css" />
		<link rel="stylesheet" href="css/ace-responsive.min.css" />
		<link rel="stylesheet" href="css/ace-skins.min.css" />
		
		<link rel="stylesheet" href="css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="js/jquery.tips.js"></script>
		
<script type="text/javascript">
	
	
	
	//保存
	function save(){
			if($("#user_number").val()==""){
			$("#user_number").tips({
				side:3,
	            msg:'请输入用户编号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#user_number").focus();
			return false;
		}
		if($("#user_type").val()==""){
			$("#user_type").tips({
				side:3,
	            msg:'请输入用户类型id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#user_type").focus();
			return false;
		}
		if($("#phone").val()==""){
			$("#phone").tips({
				side:3,
	            msg:'请输入用户手机账号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#phone").focus();
			return false;
		}
		if($("#money").val()==""){
			$("#money").tips({
				side:3,
	            msg:'请输入款项金额',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#money").focus();
			return false;
		}
		if($("#withdraw_number").val()==""){
			$("#withdraw_number").tips({
				side:3,
	            msg:'请输入提款账号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#withdraw_number").focus();
			return false;
		}
		if($("#withdraw_type").val()==""){
			$("#withdraw_type").tips({
				side:3,
	            msg:'请输入提现方式',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#withdraw_type").focus();
			return false;
		}
		if($("#withdraw_status").val()==""){
			$("#withdraw_status").tips({
				side:3,
	            msg:'请输入提现状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#withdraw_status").focus();
			return false;
		}
		if($("#remark").val()==""){
			$("#remark").tips({
				side:3,
	            msg:'请输入备注',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#remark").focus();
			return false;
		}
		if($("#apply_time").val()==""){
			$("#apply_time").tips({
				side:3,
	            msg:'请输入申请时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#apply_time").focus();
			return false;
		}
		if($("#operator_number").val()==""){
			$("#operator_number").tips({
				side:3,
	            msg:'请输入操作人编号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#operator_number").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="withdraw_approval/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="withdraw_approval_id" id="withdraw_approval_id" value="${pd.withdraw_approval_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="user_number" id="user_number" value="${pd.user_number}" maxlength="32" placeholder="这里输入用户编号" title="用户编号"/></td>
			</tr>
			<tr>
				<td><input type="text" name="user_type" id="user_type" value="${pd.user_type}" maxlength="32" placeholder="这里输入用户类型id" title="用户类型id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="phone" id="phone" value="${pd.phone}" maxlength="32" placeholder="这里输入用户手机账号" title="用户手机账号"/></td>
			</tr>
			<tr>
				<td><input type="text" name="money" id="money" value="${pd.money}" maxlength="32" placeholder="这里输入款项金额" title="款项金额"/></td>
			</tr>
			<tr>
				<td><input type="text" name="withdraw_number" id="withdraw_number" value="${pd.withdraw_number}" maxlength="32" placeholder="这里输入提款账号" title="提款账号"/></td>
			</tr>
			<tr>
				<td><input type="text" name="withdraw_type" id="withdraw_type" value="${pd.withdraw_type}" maxlength="32" placeholder="这里输入提现方式" title="提现方式"/></td>
			</tr>
			<tr>
				<td><input type="text" name="withdraw_status" id="withdraw_status" value="${pd.withdraw_status}" maxlength="32" placeholder="这里输入提现状态" title="提现状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="remark" id="remark" value="${pd.remark}" maxlength="32" placeholder="这里输入备注" title="备注"/></td>
			</tr>
			<tr>
				<td><input type="text" name="apply_time" id="apply_time" value="${pd.apply_time}" maxlength="32" placeholder="这里输入申请时间" title="申请时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="operator_number" id="operator_number" value="${pd.operator_number}" maxlength="32" placeholder="这里输入操作人编号" title="操作人编号"/></td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/ace-elements.min.js"></script>
		<script src="js/ace.min.js"></script>
		<script type="text/javascript" src="js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript">
		$(window.parent.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		</script>
</body>
</html>