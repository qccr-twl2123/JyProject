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
			if($("#process_type").val()==""){
			$("#process_type").tips({
				side:3,
	            msg:'请输入处理类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#process_type").focus();
			return false;
		}
		if($("#money").val()==""){
			$("#money").tips({
				side:3,
	            msg:'请输入红包金钱',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#money").focus();
			return false;
		}
		if($("#redpackage_number").val()==""){
			$("#redpackage_number").tips({
				side:3,
	            msg:'请输入红包数量',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#redpackage_number").focus();
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
		if($("#sendtime").val()==""){
			$("#sendtime").tips({
				side:3,
	            msg:'请输入发送时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sendtime").focus();
			return false;
		}
		if($("#approval").val()==""){
			$("#approval").tips({
				side:3,
	            msg:'请输入是否批准/驳回',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#approval").focus();
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
		if($("#operator_id").val()==""){
			$("#operator_id").tips({
				side:3,
	            msg:'请输入操作员id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#operator_id").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="processing_issues/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="processing_issues_id" id="processing_issues_id" value="${pd.processing_issues_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="process_type" id="process_type" value="${pd.process_type}" maxlength="32" placeholder="这里输入处理类型" title="处理类型"/></td>
			</tr>
			<tr>
				<td><input type="text" name="money" id="money" value="${pd.money}" maxlength="32" placeholder="这里输入红包金钱" title="红包金钱"/></td>
			</tr>
			<tr>
				<td><input type="text" name="redpackage_number" id="redpackage_number" value="${pd.redpackage_number}" maxlength="32" placeholder="这里输入红包数量" title="红包数量"/></td>
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
				<td><input type="text" name="sendtime" id="sendtime" value="${pd.sendtime}" maxlength="32" placeholder="这里输入发送时间" title="发送时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="approval" id="approval" value="${pd.approval}" maxlength="32" placeholder="这里输入是否批准/驳回" title="是否批准/驳回"/></td>
			</tr>
			<tr>
				<td><input type="text" name="review_id" id="review_id" value="${pd.review_id}" maxlength="32" placeholder="这里输入审核人员" title="审核人员"/></td>
			</tr>
			<tr>
				<td><input type="text" name="operator_id" id="operator_id" value="${pd.operator_id}" maxlength="32" placeholder="这里输入操作员id" title="操作员id"/></td>
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