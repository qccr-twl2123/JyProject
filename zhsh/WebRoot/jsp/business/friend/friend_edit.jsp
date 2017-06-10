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
			if($("#invite_id").val()==""){
			$("#invite_id").tips({
				side:3,
	            msg:'请输入邀请人id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#invite_id").focus();
			return false;
		}
		if($("#be_invite_id").val()==""){
			$("#be_invite_id").tips({
				side:3,
	            msg:'请输入被邀请人id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#be_invite_id").focus();
			return false;
		}
		if($("#invite_status").val()==""){
			$("#invite_status").tips({
				side:3,
	            msg:'请输入邀请状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#invite_status").focus();
			return false;
		}
		if($("#be_invite_type").val()==""){
			$("#be_invite_type").tips({
				side:3,
	            msg:'请输入被邀请人的类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#be_invite_type").focus();
			return false;
		}
		if($("#invite_id_type").val()==""){
			$("#invite_id_type").tips({
				side:3,
	            msg:'请输入邀请人类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#invite_id_type").focus();
			return false;
		}
		if($("#invite_time").val()==""){
			$("#invite_time").tips({
				side:3,
	            msg:'请输入邀请时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#invite_time").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="friend/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="friend_id" id="friend_id" value="${pd.friend_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="invite_id" id="invite_id" value="${pd.invite_id}" maxlength="32" placeholder="这里输入邀请人id" title="邀请人id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="be_invite_id" id="be_invite_id" value="${pd.be_invite_id}" maxlength="32" placeholder="这里输入被邀请人id" title="被邀请人id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="invite_status" id="invite_status" value="${pd.invite_status}" maxlength="32" placeholder="这里输入邀请状态" title="邀请状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="be_invite_type" id="be_invite_type" value="${pd.be_invite_type}" maxlength="32" placeholder="这里输入被邀请人的类型" title="被邀请人的类型"/></td>
			</tr>
			<tr>
				<td><input type="text" name="invite_id_type" id="invite_id_type" value="${pd.invite_id_type}" maxlength="32" placeholder="这里输入邀请人类型" title="邀请人类型"/></td>
			</tr>
			<tr>
				<td><input type="text" name="invite_time" id="invite_time" value="${pd.invite_time}" maxlength="32" placeholder="这里输入邀请时间" title="邀请时间"/></td>
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