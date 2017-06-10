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
			if($("#serial_number").val()==""){
			$("#serial_number").tips({
				side:3,
	            msg:'请输入序号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#serial_number").focus();
			return false;
		}
		if($("#target_type").val()==""){
			$("#target_type").tips({
				side:3,
	            msg:'请输入发放红包对象',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#target_type").focus();
			return false;
		}
		if($("#money").val()==""){
			$("#money").tips({
				side:3,
	            msg:'请输入红包总金额',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#money").focus();
			return false;
		}
		if($("#redpackage_number").val()==""){
			$("#redpackage_number").tips({
				side:3,
	            msg:'请输入红包个数',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#redpackage_number").focus();
			return false;
		}
		if($("#starttime").val()==""){
			$("#starttime").tips({
				side:3,
	            msg:'请输入开始时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#starttime").focus();
			return false;
		}
		if($("#endtime").val()==""){
			$("#endtime").tips({
				side:3,
	            msg:'请输入结束时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#endtime").focus();
			return false;
		}
		if($("#rp_use_condition_id").val()==""){
			$("#rp_use_condition_id").tips({
				side:3,
	            msg:'请输入使用条件',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#rp_use_condition_id").focus();
			return false;
		}
		if($("#rp_grant_condition_id").val()==""){
			$("#rp_grant_condition_id").tips({
				side:3,
	            msg:'请输入发放条件',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#rp_grant_condition_id").focus();
			return false;
		}
		if($("#province_id").val()==""){
			$("#province_id").tips({
				side:3,
	            msg:'请输入省id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#province_id").focus();
			return false;
		}
		if($("#city_id").val()==""){
			$("#city_id").tips({
				side:3,
	            msg:'请输入市id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#city_id").focus();
			return false;
		}
		if($("#area_id").val()==""){
			$("#area_id").tips({
				side:3,
	            msg:'请输入区/县id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#area_id").focus();
			return false;
		}
		if($("#review_status").val()==""){
			$("#review_status").tips({
				side:3,
	            msg:'请输入审核状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#review_status").focus();
			return false;
		}
		if($("#review_id").val()==""){
			$("#review_id").tips({
				side:3,
	            msg:'请输入审核人员',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#review_id").focus();
			return false;
		}
		if($("#operation_id").val()==""){
			$("#operation_id").tips({
				side:3,
	            msg:'请输入操作人员',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#operation_id").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="red_platform/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="red_platform_id" id="red_platform_id" value="${pd.red_platform_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="serial_number" id="serial_number" value="${pd.serial_number}" maxlength="32" placeholder="这里输入序号" title="序号"/></td>
			</tr>
			<tr>
				<td><input type="text" name="target_type" id="target_type" value="${pd.target_type}" maxlength="32" placeholder="这里输入发放红包对象" title="发放红包对象"/></td>
			</tr>
			<tr>
				<td><input type="text" name="money" id="money" value="${pd.money}" maxlength="32" placeholder="这里输入红包总金额" title="红包总金额"/></td>
			</tr>
			<tr>
				<td><input type="text" name="redpackage_number" id="redpackage_number" value="${pd.redpackage_number}" maxlength="32" placeholder="这里输入红包个数" title="红包个数"/></td>
			</tr>
			<tr>
				<td><input type="text" name="starttime" id="starttime" value="${pd.starttime}" maxlength="32" placeholder="这里输入开始时间" title="开始时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="endtime" id="endtime" value="${pd.endtime}" maxlength="32" placeholder="这里输入结束时间" title="结束时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="rp_use_condition_id" id="rp_use_condition_id" value="${pd.rp_use_condition_id}" maxlength="32" placeholder="这里输入使用条件" title="使用条件"/></td>
			</tr>
			<tr>
				<td><input type="text" name="rp_grant_condition_id" id="rp_grant_condition_id" value="${pd.rp_grant_condition_id}" maxlength="32" placeholder="这里输入发放条件" title="发放条件"/></td>
			</tr>
			<tr>
				<td><input type="text" name="province_id" id="province_id" value="${pd.province_id}" maxlength="32" placeholder="这里输入省id" title="省id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="city_id" id="city_id" value="${pd.city_id}" maxlength="32" placeholder="这里输入市id" title="市id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="area_id" id="area_id" value="${pd.area_id}" maxlength="32" placeholder="这里输入区/县id" title="区/县id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="review_status" id="review_status" value="${pd.review_status}" maxlength="32" placeholder="这里输入审核状态" title="审核状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="review_id" id="review_id" value="${pd.review_id}" maxlength="32" placeholder="这里输入审核人员" title="审核人员"/></td>
			</tr>
			<tr>
				<td><input type="text" name="operation_id" id="operation_id" value="${pd.operation_id}" maxlength="32" placeholder="这里输入操作人员" title="操作人员"/></td>
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