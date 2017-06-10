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
			if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'请输入联盟名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#name").focus();
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
		if($("#invite_desc").val()==""){
			$("#invite_desc").tips({
				side:3,
	            msg:'请输入邀请说明',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#invite_desc").focus();
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
		if($("#leader_store_id").val()==""){
			$("#leader_store_id").tips({
				side:3,
	            msg:'请输入盟主id（商家id）',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#leader_store_id").focus();
			return false;
		}
		if($("#type").val()==""){
			$("#type").tips({
				side:3,
	            msg:'请输入联盟担当类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#type").focus();
			return false;
		}
		if($("#union_status").val()==""){
			$("#union_status").tips({
				side:3,
	            msg:'请输入联盟状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#union_status").focus();
			return false;
		}
		if($("#addunion_time").val()==""){
			$("#addunion_time").tips({
				side:3,
	            msg:'请输入加入时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#addunion_time").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="store_union/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="store_union_id" id="store_union_id" value="${pd.store_union_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="name" id="name" value="${pd.name}" maxlength="32" placeholder="这里输入联盟名称" title="联盟名称"/></td>
			</tr>
			<tr>
				<td><input type="text" name="starttime" id="starttime" value="${pd.starttime}" maxlength="32" placeholder="这里输入开始时间" title="开始时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="endtime" id="endtime" value="${pd.endtime}" maxlength="32" placeholder="这里输入结束时间" title="结束时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="invite_desc" id="invite_desc" value="${pd.invite_desc}" maxlength="32" placeholder="这里输入邀请说明" title="邀请说明"/></td>
			</tr>
			<tr>
				<td><input type="text" name="store_id" id="store_id" value="${pd.store_id}" maxlength="32" placeholder="这里输入商家id" title="商家id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="leader_store_id" id="leader_store_id" value="${pd.leader_store_id}" maxlength="32" placeholder="这里输入盟主id（商家id）" title="盟主id（商家id）"/></td>
			</tr>
			<tr>
				<td><input type="text" name="type" id="type" value="${pd.type}" maxlength="32" placeholder="这里输入联盟担当类型" title="联盟担当类型"/></td>
			</tr>
			<tr>
				<td><input type="text" name="union_status" id="union_status" value="${pd.union_status}" maxlength="32" placeholder="这里输入联盟状态" title="联盟状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="addunion_time" id="addunion_time" value="${pd.addunion_time}" maxlength="32" placeholder="这里输入加入时间" title="加入时间"/></td>
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