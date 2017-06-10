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
	            msg:'请输入vip背景图片',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#image_url").focus();
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
		if($("#member_id").val()==""){
			$("#member_id").tips({
				side:3,
	            msg:'请输入当前用户id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#member_id").focus();
			return false;
		}
		if($("#shop_number").val()==""){
			$("#shop_number").tips({
				side:3,
	            msg:'请输入购买次数',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#shop_number").focus();
			return false;
		}
		if($("#total_money").val()==""){
			$("#total_money").tips({
				side:3,
	            msg:'请输入消费总金额',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#total_money").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="member_vipcard/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="member_vipcard_id" id="member_vipcard_id" value="${pd.MEMBER_VIPCARD_ID}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="image_url" id="image_url" value="${pd.image_url}" maxlength="32" placeholder="这里输入vip背景图片" title="vip背景图片"/></td>
			</tr>
			<tr>
				<td><input type="text" name="store_id" id="store_id" value="${pd.store_id}" maxlength="32" placeholder="这里输入商家id" title="商家id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="member_id" id="member_id" value="${pd.member_id}" maxlength="32" placeholder="这里输入当前用户id" title="当前用户id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="shop_number" id="shop_number" value="${pd.shop_number}" maxlength="32" placeholder="这里输入购买次数" title="购买次数"/></td>
			</tr>
			<tr>
				<td><input type="text" name="total_money" id="total_money" value="${pd.total_money}" maxlength="32" placeholder="这里输入消费总金额" title="消费总金额"/></td>
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