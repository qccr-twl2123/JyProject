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
			if($("#operator_name").val()==""){
			$("#operator_name").tips({
				side:3,
	            msg:'请输入操作员名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#operator_name").focus();
			return false;
		}
		if($("#operator_position").val()==""){
			$("#operator_position").tips({
				side:3,
	            msg:'请输入职务',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#operator_position").focus();
			return false;
		}
		if($("#operator_status").val()==""){
			$("#operator_status").tips({
				side:3,
	            msg:'请输入状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#operator_status").focus();
			return false;
		}
		if($("#operator_phone").val()==""){
			$("#operator_phone").tips({
				side:3,
	            msg:'请输入手机号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#operator_phone").focus();
			return false;
		}
		if($("#operator_password").val()==""){
			$("#operator_password").tips({
				side:3,
	            msg:'请输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#operator_password").focus();
			return false;
		}
		if($("#sy_competence").val()==""){
			$("#sy_competence").tips({
				side:3,
	            msg:'请输入收银权限',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sy_competence").focus();
			return false;
		}
		if($("#yx_competence").val()==""){
			$("#yx_competence").tips({
				side:3,
	            msg:'请输入营销控制台',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#yx_competence").focus();
			return false;
		}
		if($("#tz_competence").val()==""){
			$("#tz_competence").tips({
				side:3,
	            msg:'请输入通知',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#tz_competence").focus();
			return false;
		}
		if($("#lm_competence").val()==""){
			$("#lm_competence").tips({
				side:3,
	            msg:'请输入我的联盟',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#lm_competence").focus();
			return false;
		}
		if($("#我的联盟").val()==""){
			$("#我的联盟").tips({
				side:3,
	            msg:'请输入商品管理',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#我的联盟").focus();
			return false;
		}
		if($("#store_shift_id").val()==""){
			$("#store_shift_id").tips({
				side:3,
	            msg:'请输入所属班次',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#store_shift_id").focus();
			return false;
		}
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
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="store_operator/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="store_operator_id" id="store_operator_id" value="${pd.store_operator_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="operator_name" id="operator_name" value="${pd.operator_name}" maxlength="32" placeholder="这里输入操作员名称" title="操作员名称"/></td>
			</tr>
			<tr>
				<td><input type="text" name="operator_position" id="operator_position" value="${pd.operator_position}" maxlength="32" placeholder="这里输入职务" title="职务"/></td>
			</tr>
			<tr>
				<td><input type="text" name="operator_status" id="operator_status" value="${pd.operator_status}" maxlength="32" placeholder="这里输入状态" title="状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="operator_phone" id="operator_phone" value="${pd.operator_phone}" maxlength="32" placeholder="这里输入手机号" title="手机号"/></td>
			</tr>
			<tr>
				<td><input type="text" name="operator_password" id="operator_password" value="${pd.operator_password}" maxlength="32" placeholder="这里输入密码" title="密码"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sy_competence" id="sy_competence" value="${pd.sy_competence}" maxlength="32" placeholder="这里输入收银权限" title="收银权限"/></td>
			</tr>
			<tr>
				<td><input type="text" name="yx_competence" id="yx_competence" value="${pd.yx_competence}" maxlength="32" placeholder="这里输入营销控制台" title="营销控制台"/></td>
			</tr>
			<tr>
				<td><input type="text" name="tz_competence" id="tz_competence" value="${pd.tz_competence}" maxlength="32" placeholder="这里输入通知" title="通知"/></td>
			</tr>
			<tr>
				<td><input type="text" name="lm_competence" id="lm_competence" value="${pd.lm_competence}" maxlength="32" placeholder="这里输入我的联盟" title="我的联盟"/></td>
			</tr>
			<tr>
				<td><input type="text" name="我的联盟" id="我的联盟" value="${pd.我的联盟}" maxlength="32" placeholder="这里输入商品管理" title="商品管理"/></td>
			</tr>
			<tr>
				<td><input type="text" name="store_shift_id" id="store_shift_id" value="${pd.store_shift_id}" maxlength="32" placeholder="这里输入所属班次" title="所属班次"/></td>
			</tr>
			<tr>
				<td><input type="text" name="store_id" id="store_id" value="${pd.store_id}" maxlength="32" placeholder="这里输入商家id" title="商家id"/></td>
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