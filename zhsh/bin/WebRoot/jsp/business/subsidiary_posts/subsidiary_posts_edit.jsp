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
			if($("#post").val()==""){
			$("#post").tips({
				side:3,
	            msg:'请输入职务',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#post").focus();
			return false;
		}
		if($("#fixed_telephone").val()==""){
			$("#fixed_telephone").tips({
				side:3,
	            msg:'请输入固定电话',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#fixed_telephone").focus();
			return false;
		}
		if($("#phone").val()==""){
			$("#phone").tips({
				side:3,
	            msg:'请输入手机号码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#phone").focus();
			return false;
		}
		if($("#email").val()==""){
			$("#email").tips({
				side:3,
	            msg:'请输入e-mail',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#email").focus();
			return false;
		}
		if($("#wechat").val()==""){
			$("#wechat").tips({
				side:3,
	            msg:'请输入微信号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#wechat").focus();
			return false;
		}
		if($("#qq").val()==""){
			$("#qq").tips({
				side:3,
	            msg:'请输入qq',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#qq").focus();
			return false;
		}
		if($("#subsidiary_posts_id").val()==""){
			$("#subsidiary_posts_id").tips({
				side:3,
	            msg:'请输入唯一标识',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#subsidiary_posts_id").focus();
			return false;
		}
		if($("#subsidiary_id").val()==""){
			$("#subsidiary_id").tips({
				side:3,
	            msg:'请输入子公司id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#subsidiary_id").focus();
			return false;
		}
		if($("#createdate").val()==""){
			$("#createdate").tips({
				side:3,
	            msg:'请输入创建时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#createdate").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="subsidiary_posts/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="subsidiary_posts_id" id="subsidiary_posts_id" value="${pd.subsidiary_posts_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="post" id="post" value="${pd.post}" maxlength="32" placeholder="这里输入职务" title="职务"/></td>
			</tr>
			<tr>
				<td><input type="text" name="fixed_telephone" id="fixed_telephone" value="${pd.fixed_telephone}" maxlength="32" placeholder="这里输入固定电话" title="固定电话"/></td>
			</tr>
			<tr>
				<td><input type="text" name="phone" id="phone" value="${pd.phone}" maxlength="32" placeholder="这里输入手机号码" title="手机号码"/></td>
			</tr>
			<tr>
				<td><input type="text" name="email" id="email" value="${pd.email}" maxlength="32" placeholder="这里输入e-mail" title="e-mail"/></td>
			</tr>
			<tr>
				<td><input type="text" name="wechat" id="wechat" value="${pd.wechat}" maxlength="32" placeholder="这里输入微信号" title="微信号"/></td>
			</tr>
			<tr>
				<td><input type="text" name="qq" id="qq" value="${pd.qq}" maxlength="32" placeholder="这里输入qq" title="qq"/></td>
			</tr>
			<tr>
				<td><input type="text" name="subsidiary_posts_id" id="subsidiary_posts_id" value="${pd.subsidiary_posts_id}" maxlength="32" placeholder="这里输入唯一标识" title="唯一标识"/></td>
			</tr>
			<tr>
				<td><input type="text" name="subsidiary_id" id="subsidiary_id" value="${pd.subsidiary_id}" maxlength="32" placeholder="这里输入子公司id" title="子公司id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="createdate" id="createdate" value="${pd.createdate}" maxlength="32" placeholder="这里输入创建时间" title="创建时间"/></td>
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