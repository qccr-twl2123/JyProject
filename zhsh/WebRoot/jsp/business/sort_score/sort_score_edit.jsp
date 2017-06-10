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
			if($("#store_id").val()==""){
			$("#store_id").tips({
				side:3,
	            msg:'请输入商家id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#store_id").focus();
			return false;
		}
		if($("#add_score").val()==""){
			$("#add_score").tips({
				side:3,
	            msg:'请输入增加分值',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#add_score").focus();
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
	            msg:'请输入截止时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#endtime").focus();
			return false;
		}
		if($("#spend_money").val()==""){
			$("#spend_money").tips({
				side:3,
	            msg:'请输入花费费用',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#spend_money").focus();
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
		if($("#operation_id").val()==""){
			$("#operation_id").tips({
				side:3,
	            msg:'请输入操作员',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#operation_id").focus();
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
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="sort_score/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="sort_score_id" id="sort_score_id" value="${pd.sort_score_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="store_id" id="store_id" value="${pd.store_id}" maxlength="32" placeholder="这里输入商家id" title="商家id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="add_score" id="add_score" value="${pd.add_score}" maxlength="32" placeholder="这里输入增加分值" title="增加分值"/></td>
			</tr>
			<tr>
				<td><input type="text" name="starttime" id="starttime" value="${pd.starttime}" maxlength="32" placeholder="这里输入开始时间" title="开始时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="endtime" id="endtime" value="${pd.endtime}" maxlength="32" placeholder="这里输入截止时间" title="截止时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="spend_money" id="spend_money" value="${pd.spend_money}" maxlength="32" placeholder="这里输入花费费用" title="花费费用"/></td>
			</tr>
			<tr>
				<td><input type="text" name="review_status" id="review_status" value="${pd.review_status}" maxlength="32" placeholder="这里输入审核状态" title="审核状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="operation_id" id="operation_id" value="${pd.operation_id}" maxlength="32" placeholder="这里输入操作员" title="操作员"/></td>
			</tr>
			<tr>
				<td><input type="text" name="review_id" id="review_id" value="${pd.review_id}" maxlength="32" placeholder="这里输入审核人员" title="审核人员"/></td>
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