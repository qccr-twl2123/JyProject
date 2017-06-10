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
			if($("#out_trade_no").val()==""){
			$("#out_trade_no").tips({
				side:3,
	            msg:'请输入流水编号（支付后会有）',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#out_trade_no").focus();
			return false;
		}
		if($("#look_number").val()==""){
			$("#look_number").tips({
				side:3,
	            msg:'请输入给客户看的编号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#look_number").focus();
			return false;
		}
		if($("#sale_money").val()==""){
			$("#sale_money").tips({
				side:3,
	            msg:'请输入销售金额',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sale_money").focus();
			return false;
		}
		if($("#discount_money").val()==""){
			$("#discount_money").tips({
				side:3,
	            msg:'请输入优惠金额',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#discount_money").focus();
			return false;
		}
		if($("#actual_money").val()==""){
			$("#actual_money").tips({
				side:3,
	            msg:'请输入实收金额',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#actual_money").focus();
			return false;
		}
		if($("#pay_type").val()==""){
			$("#pay_type").tips({
				side:3,
	            msg:'请输入支付方式',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#pay_type").focus();
			return false;
		}
		if($("#get_integral").val()==""){
			$("#get_integral").tips({
				side:3,
	            msg:'请输入赠送积分',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#get_integral").focus();
			return false;
		}
		if($("#pay_phone ").val()==""){
			$("#pay_phone ").tips({
				side:3,
	            msg:'请输入购买手机号码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#pay_phone ").focus();
			return false;
		}
		if($("#store_operator_id").val()==""){
			$("#store_operator_id").tips({
				side:3,
	            msg:'请输入操作员id（app端支付即为支付人）',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#store_operator_id").focus();
			return false;
		}
		if($("#payer_id").val()==""){
			$("#payer_id").tips({
				side:3,
	            msg:'请输入支付人id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#payer_id").focus();
			return false;
		}
		if($("#store_shift_id").val()==""){
			$("#store_shift_id").tips({
				side:3,
	            msg:'请输入班次',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#store_shift_id").focus();
			return false;
		}
		if($("#address_type").val()==""){
			$("#address_type").tips({
				side:3,
	            msg:'请输入地点类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#address_type").focus();
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
		if($("#createtime").val()==""){
			$("#createtime").tips({
				side:3,
	            msg:'请输入创建时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#createtime").focus();
			return false;
		}
		if($("#order_status").val()==""){
			$("#order_status").tips({
				side:3,
	            msg:'请输入订单状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#order_status").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="order/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="order_id" id="order_id" value="${pd.order_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="out_trade_no" id="out_trade_no" value="${pd.out_trade_no}" maxlength="32" placeholder="这里输入流水编号（支付后会有）" title="流水编号（支付后会有）"/></td>
			</tr>
			<tr>
				<td><input type="text" name="look_number" id="look_number" value="${pd.look_number}" maxlength="32" placeholder="这里输入给客户看的编号" title="给客户看的编号"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sale_money" id="sale_money" value="${pd.sale_money}" maxlength="32" placeholder="这里输入销售金额" title="销售金额"/></td>
			</tr>
			<tr>
				<td><input type="text" name="discount_money" id="discount_money" value="${pd.discount_money}" maxlength="32" placeholder="这里输入优惠金额" title="优惠金额"/></td>
			</tr>
			<tr>
				<td><input type="text" name="actual_money" id="actual_money" value="${pd.actual_money}" maxlength="32" placeholder="这里输入实收金额" title="实收金额"/></td>
			</tr>
			<tr>
				<td><input type="text" name="pay_type" id="pay_type" value="${pd.pay_type}" maxlength="32" placeholder="这里输入支付方式" title="支付方式"/></td>
			</tr>
			<tr>
				<td><input type="text" name="get_integral" id="get_integral" value="${pd.get_integral}" maxlength="32" placeholder="这里输入赠送积分" title="赠送积分"/></td>
			</tr>
			<tr>
				<td><input type="text" name="pay_phone " id="pay_phone " value="${pd.pay_phone }" maxlength="32" placeholder="这里输入购买手机号码" title="购买手机号码"/></td>
			</tr>
			<tr>
				<td><input type="text" name="store_operator_id" id="store_operator_id" value="${pd.store_operator_id}" maxlength="32" placeholder="这里输入操作员id（app端支付即为支付人）" title="操作员id（app端支付即为支付人）"/></td>
			</tr>
			<tr>
				<td><input type="text" name="payer_id" id="payer_id" value="${pd.payer_id}" maxlength="32" placeholder="这里输入支付人id" title="支付人id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="store_shift_id" id="store_shift_id" value="${pd.store_shift_id}" maxlength="32" placeholder="这里输入班次" title="班次"/></td>
			</tr>
			<tr>
				<td><input type="text" name="address_type" id="address_type" value="${pd.address_type}" maxlength="32" placeholder="这里输入地点类型" title="地点类型"/></td>
			</tr>
			<tr>
				<td><input type="text" name="remark" id="remark" value="${pd.remark}" maxlength="32" placeholder="这里输入备注" title="备注"/></td>
			</tr>
			<tr>
				<td><input type="text" name="createtime" id="createtime" value="${pd.createtime}" maxlength="32" placeholder="这里输入创建时间" title="创建时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="order_status" id="order_status" value="${pd.order_status}" maxlength="32" placeholder="这里输入订单状态" title="订单状态"/></td>
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