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
			if($("#user_type").val()==""){
			$("#user_type").tips({
				side:3,
	            msg:'请输入用户类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#user_type").focus();
			return false;
		}
		if($("#user_account").val()==""){
			$("#user_account").tips({
				side:3,
	            msg:'请输入用户账号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#user_account").focus();
			return false;
		}
		if($("#money_type").val()==""){
			$("#money_type").tips({
				side:3,
	            msg:'请输入款项类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#money_type").focus();
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
		if($("#remittance_type").val()==""){
			$("#remittance_type").tips({
				side:3,
	            msg:'请输入汇款方式',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#remittance_type").focus();
			return false;
		}
		if($("#remittance_number").val()==""){
			$("#remittance_number").tips({
				side:3,
	            msg:'请输入汇款账号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#remittance_number").focus();
			return false;
		}
		if($("#pay_time").val()==""){
			$("#pay_time").tips({
				side:3,
	            msg:'请输入支付时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#pay_time").focus();
			return false;
		}
		if($("#pay_status").val()==""){
			$("#pay_status").tips({
				side:3,
	            msg:'请输入支付状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#pay_status").focus();
			return false;
		}
		if($("#order_tn").val()==""){
			$("#order_tn").tips({
				side:3,
	            msg:'请输入订单号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#order_tn").focus();
			return false;
		}
		if($("#payee_number").val()==""){
			$("#payee_number").tips({
				side:3,
	            msg:'请输入收款人编号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#payee_number").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="pay_history/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="pay_history_id" id="pay_history_id" value="${pd.PAY_HISTORY_ID}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="user_type" id="user_type" value="${pd.user_type}" maxlength="32" placeholder="这里输入用户类型" title="用户类型"/></td>
			</tr>
			<tr>
				<td><input type="text" name="user_account" id="user_account" value="${pd.user_account}" maxlength="32" placeholder="这里输入用户账号" title="用户账号"/></td>
			</tr>
			<tr>
				<td><input type="text" name="money_type" id="money_type" value="${pd.money_type}" maxlength="32" placeholder="这里输入款项类型" title="款项类型"/></td>
			</tr>
			<tr>
				<td><input type="text" name="money" id="money" value="${pd.money}" maxlength="32" placeholder="这里输入款项金额" title="款项金额"/></td>
			</tr>
			<tr>
				<td><input type="text" name="remittance_type" id="remittance_type" value="${pd.remittance_type}" maxlength="32" placeholder="这里输入汇款方式" title="汇款方式"/></td>
			</tr>
			<tr>
				<td><input type="text" name="remittance_number" id="remittance_number" value="${pd.remittance_number}" maxlength="32" placeholder="这里输入汇款账号" title="汇款账号"/></td>
			</tr>
			<tr>
				<td><input type="text" name="pay_time" id="pay_time" value="${pd.pay_time}" maxlength="32" placeholder="这里输入支付时间" title="支付时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="pay_status" id="pay_status" value="${pd.pay_status}" maxlength="32" placeholder="这里输入支付状态" title="支付状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="order_tn" id="order_tn" value="${pd.order_tn}" maxlength="32" placeholder="这里输入订单号" title="订单号"/></td>
			</tr>
			<tr>
				<td><input type="text" name="payee_number" id="payee_number" value="${pd.payee_number}" maxlength="32" placeholder="这里输入收款人编号" title="收款人编号"/></td>
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