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
	alert("修改");
			if($("#team_name").val()==""){
			$("#team_name").tips({
				side:3,
	            msg:'请输入团队名臣',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#team_name").focus();
			return false;
		}
		if($("#industry_commerce_name").val()==""){
			$("#industry_commerce_name").tips({
				side:3,
	            msg:'请输入工商名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#industry_commerce_name").focus();
			return false;
		}
		if($("#subsidiary_id").val()==""){
			$("#subsidiary_id").tips({
				side:3,
	            msg:'请输入隶属子公司名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#subsidiary_id").focus();
			return false;
		}
		if($("#province_id").val()==""){
			$("#province_id").tips({
				side:3,
	            msg:'请输入隶属省',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#province_id").focus();
			return false;
		}
		if($("#city_id").val()==""){
			$("#city_id").tips({
				side:3,
	            msg:'请输入隶属市',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#city_id").focus();
			return false;
		}
		if($("#area_id").val()==""){
			$("#area_id").tips({
				side:3,
	            msg:'请输入隶属区',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#area_id").focus();
			return false;
		}
		if($("#password").val()==""){
			$("#password").tips({
				side:3,
	            msg:'请输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#password").focus();
			return false;
		}
		if($("#principal").val()==""){
			$("#principal").tips({
				side:3,
	            msg:'请输入负责人',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#principal").focus();
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
	            msg:'请输入wechat',
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
		/* if($("#sp_file_id").val()==""){
			$("#sp_file_id").tips({
				side:3,
	            msg:'请输入sp_file_id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sp_file_id").focus();
			return false;
		} */
		if($("#sign_time").val()==""){
			$("#sign_time").tips({
				side:3,
	            msg:'请输入签约时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sign_time").focus();
			return false;
		}
		if($("#start_time").val()==""){
			$("#start_time").tips({
				side:3,
	            msg:'请输入启动时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#start_time").focus();
			return false;
		}
		if($("#earnest_money").val()==""){
			$("#earnest_money").tips({
				side:3,
	            msg:'请输入保证金',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#earnest_money").focus();
			return false;
		}
		if($("#sp_statestatus").val()==""){
			$("#sp_statestatus").tips({
				side:3,
	            msg:'请输入支付/退还状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sp_statestatus").focus();
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
	<form action="sp_file/${msg}.do" name="Form" id="Form" method="post">
		<input type="hidden" name="sp_file_id" id="sp_file_id" value="${pd.sp_file_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="team_name" id="team_name" value="${pd.team_name}" maxlength="32" placeholder="这里输入团队名臣" title="团队名臣"/></td>
			</tr>
			<tr>
				<td><input type="text" name="industry_commerce_name" id="industry_commerce_name" value="${pd.industry_commerce_name}" maxlength="32" placeholder="这里输入工商名称" title="工商名称"/></td>
			</tr>
			<tr>
				<td><input type="text" name="subsidiary_id" id="subsidiary_id" value="${pd.subsidiary_id}" maxlength="32" placeholder="这里输入隶属子公司名称" title="隶属子公司名称"/></td>
			</tr>
			<tr>
				<td><input type="text" name="province_id" id="province_id" value="${pd.province_id}" maxlength="32" placeholder="这里输入隶属省" title="隶属省"/></td>
			</tr>
			<tr>
				<td><input type="text" name="city_id" id="city_id" value="${pd.city_id}" maxlength="32" placeholder="这里输入隶属市" title="隶属市"/></td>
			</tr>
			<tr>
				<td><input type="text" name="area_id" id="area_id" value="${pd.area_id}" maxlength="32" placeholder="这里输入隶属区" title="隶属区"/></td>
			</tr>
			<tr>
				<td><input type="text" name="password" id="password" value="${pd.password}" maxlength="32" placeholder="这里输入密码" title="密码"/></td>
			</tr>
			<tr>
				<td><input type="text" name="principal" id="principal" value="${pd.principal}" maxlength="32" placeholder="这里输入负责人" title="负责人"/></td>
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
				<td><input type="text" name="wechat" id="wechat" value="${pd.wechat}" maxlength="32" placeholder="这里输入wechat" title="wechat"/></td>
			</tr>
			<tr>
				<td><input type="text" name="qq" id="qq" value="${pd.qq}" maxlength="32" placeholder="这里输入qq" title="qq"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sp_file_id" id="sp_file_id" value="${pd.sp_file_id}" maxlength="32" placeholder="这里输入sp_file_id" title="sp_file_id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sign_time" id="sign_time" value="${pd.sign_time}" maxlength="32" placeholder="这里输入签约时间" title="签约时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="start_time" id="start_time" value="${pd.start_time}" maxlength="32" placeholder="这里输入启动时间" title="启动时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="earnest_money" id="earnest_money" value="${pd.earnest_money}" maxlength="32" placeholder="这里输入保证金" title="保证金"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sp_statestatus" id="sp_statestatus" value="${pd.sp_statestatus}" maxlength="32" placeholder="这里输入支付/退还状态" title="支付/退还状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="createdate" id="createdate" value="${pd.createdate}" maxlength="32" placeholder="这里输入创建时间" title="创建时间"/></td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<a class="btn btn-mini btn-primary" onclick="save()">保存</a>
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