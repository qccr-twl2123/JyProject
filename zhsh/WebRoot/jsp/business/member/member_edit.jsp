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
			if($("#image_url").val()==""){
			$("#image_url").tips({
				side:3,
	            msg:'请输入用户头像',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#image_url").focus();
			return false;
		}
		if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'请输入用户名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#name").focus();
			return false;
		}
		if($("#phone").val()==""){
			$("#phone").tips({
				side:3,
	            msg:'请输入用户绑定电话号码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#phone").focus();
			return false;
		}
		if($("#vip_level").val()==""){
			$("#vip_level").tips({
				side:3,
	            msg:'请输入用户会员等级',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#vip_level").focus();
			return false;
		}
		if($("#charm_number").val()==""){
			$("#charm_number").tips({
				side:3,
	            msg:'请输入用户魅力值',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#charm_number").focus();
			return false;
		}
		if($("#now_money").val()==""){
			$("#now_money").tips({
				side:3,
	            msg:'请输入用户余额',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#now_money").focus();
			return false;
		}
		if($("#now_integral").val()==""){
			$("#now_integral").tips({
				side:3,
	            msg:'请输入用户积分',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#now_integral").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="member/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="member_id" id="member_id" value="${pd.MEMBER_ID}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="image_url" id="image_url" value="${pd.image_url}" maxlength="32" placeholder="这里输入用户头像" title="用户头像"/></td>
			</tr>
			<tr>
				<td><input type="text" name="name" id="name" value="${pd.name}" maxlength="32" placeholder="这里输入用户名称" title="用户名称"/></td>
			</tr>
			<tr>
				<td><input type="text" name="phone" id="phone" value="${pd.phone}" maxlength="32" placeholder="这里输入用户绑定电话号码" title="用户绑定电话号码"/></td>
			</tr>
			<tr>
				<td><input type="text" name="vip_level" id="vip_level" value="${pd.vip_level}" maxlength="32" placeholder="这里输入用户会员等级" title="用户会员等级"/></td>
			</tr>
			<tr>
				<td><input type="text" name="charm_number" id="charm_number" value="${pd.charm_number}" maxlength="32" placeholder="这里输入用户魅力值" title="用户魅力值"/></td>
			</tr>
			<tr>
				<td><input type="text" name="now_money" id="now_money" value="${pd.now_money}" maxlength="32" placeholder="这里输入用户余额" title="用户余额"/></td>
			</tr>
			<tr>
				<td><input type="text" name="now_integral" id="now_integral" value="${pd.now_integral}" maxlength="32" placeholder="这里输入用户积分" title="用户积分"/></td>
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