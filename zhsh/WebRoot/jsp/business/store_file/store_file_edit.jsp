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
			if($("#store_name").val()==""){
			$("#store_name").tips({
				side:3,
	            msg:'请输入商家名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#store_name").focus();
			return false;
		}
		if($("#store_abbreviation_name").val()==""){
			$("#store_abbreviation_name").tips({
				side:3,
	            msg:'请输入商家简称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#store_abbreviation_name").focus();
			return false;
		}
		if($("#business_licenses_name").val()==""){
			$("#business_licenses_name").tips({
				side:3,
	            msg:'请输入商家工商执照全程',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#business_licenses_name").focus();
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
	            msg:'请输入区id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#area_id").focus();
			return false;
		}
		if($("#address").val()==""){
			$("#address").tips({
				side:3,
	            msg:'请输入详细地址',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#address").focus();
			return false;
		}
		if($("#management_projects_desc").val()==""){
			$("#management_projects_desc").tips({
				side:3,
	            msg:'请输入经营品项目介绍',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#management_projects_desc").focus();
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
		if($("#store_phone").val()==""){
			$("#store_phone").tips({
				side:3,
	            msg:'请输入商家联系电话',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#store_phone").focus();
			return false;
		}
		if($("#open_statestatus").val()==""){
			$("#open_statestatus").tips({
				side:3,
	            msg:'请输入开启状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#open_statestatus").focus();
			return false;
		}
		if($("#username").val()==""){
			$("#username").tips({
				side:3,
	            msg:'请输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#username").focus();
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
		if($("#business_licenses_image").val()==""){
			$("#business_licenses_image").tips({
				side:3,
	            msg:'请输入工商执照',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#business_licenses_image").focus();
			return false;
		}
		if($("#license_image_one").val()==""){
			$("#license_image_one").tips({
				side:3,
	            msg:'请输入许可证执照',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#license_image_one").focus();
			return false;
		}
		if($("#license_image_two").val()==""){
			$("#license_image_two").tips({
				side:3,
	            msg:'请输入许可证执照',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#license_image_two").focus();
			return false;
		}
		if($("#license_image_three").val()==""){
			$("#license_image_three").tips({
				side:3,
	            msg:'请输入许可证执照',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#license_image_three").focus();
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
		if($("#em_statestatus").val()==""){
			$("#em_statestatus").tips({
				side:3,
	            msg:'请输入保证金状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#em_statestatus").focus();
			return false;
		}
		if($("#system_service_fee").val()==""){
			$("#system_service_fee").tips({
				side:3,
	            msg:'请输入系统服务费',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#system_service_fee").focus();
			return false;
		}
		if($("#sf_statestatus").val()==""){
			$("#sf_statestatus").tips({
				side:3,
	            msg:'请输入服务费状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sf_statestatus").focus();
			return false;
		}
		if($("#sf_starttime").val()==""){
			$("#sf_starttime").tips({
				side:3,
	            msg:'请输入服务费开始有效时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sf_starttime").focus();
			return false;
		}
		if($("#sf_endtime").val()==""){
			$("#sf_endtime").tips({
				side:3,
	            msg:'请输入服务费结束有效时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sf_endtime").focus();
			return false;
		}
		if($("#sp_file_id").val()==""){
			$("#sp_file_id").tips({
				side:3,
	            msg:'请输入添加商家的服务商id（操作人）',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sp_file_id").focus();
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
	<form action="store_file/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="store_file_id" id="store_file_id" value="${pd.store_file_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="store_name" id="store_name" value="${pd.store_name}" maxlength="32" placeholder="这里输入商家名称" title="商家名称"/></td>
			</tr>
			<tr>
				<td><input type="text" name="store_abbreviation_name" id="store_abbreviation_name" value="${pd.store_abbreviation_name}" maxlength="32" placeholder="这里输入商家简称" title="商家简称"/></td>
			</tr>
			<tr>
				<td><input type="text" name="business_licenses_name" id="business_licenses_name" value="${pd.business_licenses_name}" maxlength="32" placeholder="这里输入商家工商执照全程" title="商家工商执照全程"/></td>
			</tr>
			<tr>
				<td><input type="text" name="province_id" id="province_id" value="${pd.province_id}" maxlength="32" placeholder="这里输入省id" title="省id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="city_id" id="city_id" value="${pd.city_id}" maxlength="32" placeholder="这里输入市id" title="市id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="area_id" id="area_id" value="${pd.area_id}" maxlength="32" placeholder="这里输入区id" title="区id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="address" id="address" value="${pd.address}" maxlength="32" placeholder="这里输入详细地址" title="详细地址"/></td>
			</tr>
			<tr>
				<td><input type="text" name="management_projects_desc" id="management_projects_desc" value="${pd.management_projects_desc}" maxlength="32" placeholder="这里输入经营品项目介绍" title="经营品项目介绍"/></td>
			</tr>
			<tr>
				<td><input type="text" name="principal" id="principal" value="${pd.principal}" maxlength="32" placeholder="这里输入负责人" title="负责人"/></td>
			</tr>
			<tr>
				<td><input type="text" name="phone" id="phone" value="${pd.phone}" maxlength="32" placeholder="这里输入手机号码" title="手机号码"/></td>
			</tr>
			<tr>
				<td><input type="text" name="email" id="email" value="${pd.email}" maxlength="32" placeholder="这里输入e-mail" title="e-mail"/></td>
			</tr>
			<tr>
				<td><input type="text" name="store_phone" id="store_phone" value="${pd.store_phone}" maxlength="32" placeholder="这里输入商家联系电话" title="商家联系电话"/></td>
			</tr>
			<tr>
				<td><input type="text" name="open_statestatus" id="open_statestatus" value="${pd.open_statestatus}" maxlength="32" placeholder="这里输入开启状态" title="开启状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="username" id="username" value="${pd.username}" maxlength="32" placeholder="这里输入用户名" title="用户名"/></td>
			</tr>
			<tr>
				<td><input type="text" name="password" id="password" value="${pd.password}" maxlength="32" placeholder="这里输入密码" title="密码"/></td>
			</tr>
			<tr>
				<td><input type="text" name="business_licenses_image" id="business_licenses_image" value="${pd.business_licenses_image}" maxlength="32" placeholder="这里输入工商执照" title="工商执照"/></td>
			</tr>
			<tr>
				<td><input type="text" name="license_image_one" id="license_image_one" value="${pd.license_image_one}" maxlength="32" placeholder="这里输入许可证执照" title="许可证执照"/></td>
			</tr>
			<tr>
				<td><input type="text" name="license_image_two" id="license_image_two" value="${pd.license_image_two}" maxlength="32" placeholder="这里输入许可证执照" title="许可证执照"/></td>
			</tr>
			<tr>
				<td><input type="text" name="license_image_three" id="license_image_three" value="${pd.license_image_three}" maxlength="32" placeholder="这里输入许可证执照" title="许可证执照"/></td>
			</tr>
			<tr>
				<td><input type="text" name="earnest_money" id="earnest_money" value="${pd.earnest_money}" maxlength="32" placeholder="这里输入保证金" title="保证金"/></td>
			</tr>
			<tr>
				<td><input type="text" name="em_statestatus" id="em_statestatus" value="${pd.em_statestatus}" maxlength="32" placeholder="这里输入保证金状态" title="保证金状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="system_service_fee" id="system_service_fee" value="${pd.system_service_fee}" maxlength="32" placeholder="这里输入系统服务费" title="系统服务费"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sf_statestatus" id="sf_statestatus" value="${pd.sf_statestatus}" maxlength="32" placeholder="这里输入服务费状态" title="服务费状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sf_starttime" id="sf_starttime" value="${pd.sf_starttime}" maxlength="32" placeholder="这里输入服务费开始有效时间" title="服务费开始有效时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sf_endtime" id="sf_endtime" value="${pd.sf_endtime}" maxlength="32" placeholder="这里输入服务费结束有效时间" title="服务费结束有效时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="store_file_id" id="store_file_id" value="${pd.store_file_id}" maxlength="32" placeholder="这里输入唯一标示编号id" title="唯一标示编号id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sp_file_id" id="sp_file_id" value="${pd.sp_file_id}" maxlength="32" placeholder="这里输入添加商家的服务商id（操作人）" title="添加商家的服务商id（操作人）"/></td>
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