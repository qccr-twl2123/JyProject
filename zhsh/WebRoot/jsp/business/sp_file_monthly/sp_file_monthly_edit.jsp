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
			if($("#sp_file_monthly_id").val()==""){
			$("#sp_file_monthly_id").tips({
				side:3,
	            msg:'请输入唯一标示id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sp_file_monthly_id").focus();
			return false;
		}
		if($("#sp_file_id").val()==""){
			$("#sp_file_id").tips({
				side:3,
	            msg:'请输入服务商id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sp_file_id").focus();
			return false;
		}
		if($("#month").val()==""){
			$("#month").tips({
				side:3,
	            msg:'请输入月度的月份',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#month").focus();
			return false;
		}
		if($("#user_number_indicator").val()==""){
			$("#user_number_indicator").tips({
				side:3,
	            msg:'请输入扩展用户指标数',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#user_number_indicator").focus();
			return false;
		}
		if($("#actual_user_number").val()==""){
			$("#actual_user_number").tips({
				side:3,
	            msg:'请输入实际扩展用户数',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#actual_user_number").focus();
			return false;
		}
		if($("#completion_rate").val()==""){
			$("#completion_rate").tips({
				side:3,
	            msg:'请输入扩展完成率',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#completion_rate").focus();
			return false;
		}
		if($("#flow_indicators").val()==""){
			$("#flow_indicators").tips({
				side:3,
	            msg:'请输入流水指标',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#flow_indicators").focus();
			return false;
		}
		if($("#actual_water").val()==""){
			$("#actual_water").tips({
				side:3,
	            msg:'请输入实际流水',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#actual_water").focus();
			return false;
		}
		if($("#water_completion_rate").val()==""){
			$("#water_completion_rate").tips({
				side:3,
	            msg:'请输入流水完成率',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#water_completion_rate").focus();
			return false;
		}
		if($("#isqualified").val()==""){
			$("#isqualified").tips({
				side:3,
	            msg:'请输入是否合格：默认为2:0-不合格，1-合格，2-未审核',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#isqualified").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="sp_file_monthly/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="sp_file_monthly_id" id="sp_file_monthly_id" value="${pd.sp_file_monthly_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="sp_file_monthly_id" id="sp_file_monthly_id" value="${pd.sp_file_monthly_id}" maxlength="32" placeholder="这里输入唯一标示id" title="唯一标示id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sp_file_id" id="sp_file_id" value="${pd.sp_file_id}" maxlength="32" placeholder="这里输入服务商id" title="服务商id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="month" id="month" value="${pd.month}" maxlength="32" placeholder="这里输入月度的月份" title="月度的月份"/></td>
			</tr>
			<tr>
				<td><input type="text" name="user_number_indicator" id="user_number_indicator" value="${pd.user_number_indicator}" maxlength="32" placeholder="这里输入扩展用户指标数" title="扩展用户指标数"/></td>
			</tr>
			<tr>
				<td><input type="text" name="actual_user_number" id="actual_user_number" value="${pd.actual_user_number}" maxlength="32" placeholder="这里输入实际扩展用户数" title="实际扩展用户数"/></td>
			</tr>
			<tr>
				<td><input type="text" name="completion_rate" id="completion_rate" value="${pd.completion_rate}" maxlength="32" placeholder="这里输入扩展完成率" title="扩展完成率"/></td>
			</tr>
			<tr>
				<td><input type="text" name="flow_indicators" id="flow_indicators" value="${pd.flow_indicators}" maxlength="32" placeholder="这里输入流水指标" title="流水指标"/></td>
			</tr>
			<tr>
				<td><input type="text" name="actual_water" id="actual_water" value="${pd.actual_water}" maxlength="32" placeholder="这里输入实际流水" title="实际流水"/></td>
			</tr>
			<tr>
				<td><input type="text" name="water_completion_rate" id="water_completion_rate" value="${pd.water_completion_rate}" maxlength="32" placeholder="这里输入流水完成率" title="流水完成率"/></td>
			</tr>
			<tr>
				<td><input type="text" name="isqualified" id="isqualified" value="${pd.isqualified}" maxlength="32" placeholder="这里输入是否合格：默认为2:0-不合格，1-合格，2-未审核" title="是否合格：默认为2:0-不合格，1-合格，2-未审核"/></td>
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