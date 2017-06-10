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
	            msg:'请输入商店名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#store_name").focus();
			return false;
		}
		if($("#app_address").val()==""){
			$("#app_address").tips({
				side:3,
	            msg:'请输入app显示名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#app_address").focus();
			return false;
		}
		if($("#detailed_address").val()==""){
			$("#detailed_address").tips({
				side:3,
	            msg:'请输入详细地址',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#detailed_address").focus();
			return false;
		}
		if($("#storey").val()==""){
			$("#storey").tips({
				side:3,
	            msg:'请输入楼层',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#storey").focus();
			return false;
		}
		if($("#keyword").val()==""){
			$("#keyword").tips({
				side:3,
	            msg:'请输入关键字',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#keyword").focus();
			return false;
		}
		if($("#contacts_name").val()==""){
			$("#contacts_name").tips({
				side:3,
	            msg:'请输入联系人姓名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#contacts_name").focus();
			return false;
		}
		if($("#contacts_job").val()==""){
			$("#contacts_job").tips({
				side:3,
	            msg:'请输入联系人职务',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#contacts_job").focus();
			return false;
		}
		if($("#management").val()==""){
			$("#management").tips({
				side:3,
	            msg:'请输入经营品项介绍',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#management").focus();
			return false;
		}
		if($("#store_phone").val()==""){
			$("#store_phone").tips({
				side:3,
	            msg:'请输入会员联系号码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#store_phone").focus();
			return false;
		}
		if($("#registertel_phone").val()==""){
			$("#registertel_phone").tips({
				side:3,
	            msg:'请输入注册手机号码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#registertel_phone").focus();
			return false;
		}
		if($("#password").val()==""){
			$("#password").tips({
				side:3,
	            msg:'请输入登陆密码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#password").focus();
			return false;
		}
		if($("#check_status").val()==""){
			$("#check_status").tips({
				side:3,
	            msg:'请输入审核状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#check_status").focus();
			return false;
		}
		if($("#vip_image").val()==""){
			$("#vip_image").tips({
				side:3,
	            msg:'请输入商家vip图片',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#vip_image").focus();
			return false;
		}
		if($("#merchant_status").val()==""){
			$("#merchant_status").tips({
				side:3,
	            msg:'请输入商家状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#merchant_status").focus();
			return false;
		}
		if($("#merchant_level").val()==""){
			$("#merchant_level").tips({
				side:3,
	            msg:'请输入商家星级',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#merchant_level").focus();
			return false;
		}
		if($("#scope").val()==""){
			$("#scope").tips({
				side:3,
	            msg:'请输入经营范围',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#scope").focus();
			return false;
		}
		if($("#introduce").val()==""){
			$("#introduce").tips({
				side:3,
	            msg:'请输入商家介绍',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#introduce").focus();
			return false;
		}
		if($("#license_image").val()==""){
			$("#license_image").tips({
				side:3,
	            msg:'请输入营业执照',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#license_image").focus();
			return false;
		}
		if($("#permit_image").val()==""){
			$("#permit_image").tips({
				side:3,
	            msg:'请输入营业许可证',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#permit_image").focus();
			return false;
		}
		if($("#registertime").val()==""){
			$("#registertime").tips({
				side:3,
	            msg:'请输入注册时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#registertime").focus();
			return false;
		}
		if($("#website_address").val()==""){
			$("#website_address").tips({
				side:3,
	            msg:'请输入官网地址',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#website_address").focus();
			return false;
		}
		if($("#integral_rate").val()==""){
			$("#integral_rate").tips({
				side:3,
	            msg:'请输入本店积分率',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#integral_rate").focus();
			return false;
		}
		if($("#service_rate").val()==""){
			$("#service_rate").tips({
				side:3,
	            msg:'请输入推广服务积分率',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#service_rate").focus();
			return false;
		}
		if($("#withdraw_rate").val()==""){
			$("#withdraw_rate").tips({
				side:3,
	            msg:'请输入提现费率',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#withdraw_rate").focus();
			return false;
		}
		if($("#lowest_score").val()==""){
			$("#lowest_score").tips({
				side:3,
	            msg:'请输入保底低分',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#lowest_score").focus();
			return false;
		}
		if($("#remind_integral").val()==""){
			$("#remind_integral").tips({
				side:3,
	            msg:'请输入积分提醒',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#remind_integral").focus();
			return false;
		}
		if($("#sort_score").val()==""){
			$("#sort_score").tips({
				side:3,
	            msg:'请输入排序分值',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sort_score").focus();
			return false;
		}
		if($("#complex_score").val()==""){
			$("#complex_score").tips({
				side:3,
	            msg:'请输入综合评分值',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#complex_score").focus();
			return false;
		}
		if($("#recharge_times").val()==""){
			$("#recharge_times").tips({
				side:3,
	            msg:'请输入充值次数',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#recharge_times").focus();
			return false;
		}
		if($("#transaction_times").val()==""){
			$("#transaction_times").tips({
				side:3,
	            msg:'请输入交易次数',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#transaction_times").focus();
			return false;
		}
		if($("#withdraw_times").val()==""){
			$("#withdraw_times").tips({
				side:3,
	            msg:'请输入提现次数',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#withdraw_times").focus();
			return false;
		}
		if($("#zan_times").val()==""){
			$("#zan_times").tips({
				side:3,
	            msg:'请输入累计赞次数',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#zan_times").focus();
			return false;
		}
		if($("#bigtype_max").val()==""){
			$("#bigtype_max").tips({
				side:3,
	            msg:'请输入大类上限',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#bigtype_max").focus();
			return false;
		}
		if($("#smalltype_min").val()==""){
			$("#smalltype_min").tips({
				side:3,
	            msg:'请输入小类上限',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#smalltype_min").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="store/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="store_id" id="store_id" value="${pd.store_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="store_name" id="store_name" value="${pd.store_name}" maxlength="32" placeholder="这里输入商店名称" title="商店名称"/></td>
			</tr>
			<tr>
				<td><input type="text" name="app_address" id="app_address" value="${pd.app_address}" maxlength="32" placeholder="这里输入app显示名称" title="app显示名称"/></td>
			</tr>
			<tr>
				<td><input type="text" name="detailed_address" id="detailed_address" value="${pd.detailed_address}" maxlength="32" placeholder="这里输入详细地址" title="详细地址"/></td>
			</tr>
			<tr>
				<td><input type="text" name="storey" id="storey" value="${pd.storey}" maxlength="32" placeholder="这里输入楼层" title="楼层"/></td>
			</tr>
			<tr>
				<td><input type="text" name="keyword" id="keyword" value="${pd.keyword}" maxlength="32" placeholder="这里输入关键字" title="关键字"/></td>
			</tr>
			<tr>
				<td><input type="text" name="contacts_name" id="contacts_name" value="${pd.contacts_name}" maxlength="32" placeholder="这里输入联系人姓名" title="联系人姓名"/></td>
			</tr>
			<tr>
				<td><input type="text" name="contacts_job" id="contacts_job" value="${pd.contacts_job}" maxlength="32" placeholder="这里输入联系人职务" title="联系人职务"/></td>
			</tr>
			<tr>
				<td><input type="text" name="management" id="management" value="${pd.management}" maxlength="32" placeholder="这里输入经营品项介绍" title="经营品项介绍"/></td>
			</tr>
			<tr>
				<td><input type="text" name="store_phone" id="store_phone" value="${pd.store_phone}" maxlength="32" placeholder="这里输入会员联系号码" title="会员联系号码"/></td>
			</tr>
			<tr>
				<td><input type="text" name="registertel_phone" id="registertel_phone" value="${pd.registertel_phone}" maxlength="32" placeholder="这里输入注册手机号码" title="注册手机号码"/></td>
			</tr>
			<tr>
				<td><input type="text" name="password" id="password" value="${pd.password}" maxlength="32" placeholder="这里输入登陆密码" title="登陆密码"/></td>
			</tr>
			<tr>
				<td><input type="text" name="check_status" id="check_status" value="${pd.check_status}" maxlength="32" placeholder="这里输入审核状态" title="审核状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="vip_image" id="vip_image" value="${pd.vip_image}" maxlength="32" placeholder="这里输入商家vip图片" title="商家vip图片"/></td>
			</tr>
			<tr>
				<td><input type="text" name="merchant_status" id="merchant_status" value="${pd.merchant_status}" maxlength="32" placeholder="这里输入商家状态" title="商家状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="merchant_level" id="merchant_level" value="${pd.merchant_level}" maxlength="32" placeholder="这里输入商家星级" title="商家星级"/></td>
			</tr>
			<tr>
				<td><input type="text" name="scope" id="scope" value="${pd.scope}" maxlength="32" placeholder="这里输入经营范围" title="经营范围"/></td>
			</tr>
			<tr>
				<td><input type="text" name="introduce" id="introduce" value="${pd.introduce}" maxlength="32" placeholder="这里输入商家介绍" title="商家介绍"/></td>
			</tr>
			<tr>
				<td><input type="text" name="license_image" id="license_image" value="${pd.license_image}" maxlength="32" placeholder="这里输入营业执照" title="营业执照"/></td>
			</tr>
			<tr>
				<td><input type="text" name="permit_image" id="permit_image" value="${pd.permit_image}" maxlength="32" placeholder="这里输入营业许可证" title="营业许可证"/></td>
			</tr>
			<tr>
				<td><input type="text" name="registertime" id="registertime" value="${pd.registertime}" maxlength="32" placeholder="这里输入注册时间" title="注册时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="website_address" id="website_address" value="${pd.website_address}" maxlength="32" placeholder="这里输入官网地址" title="官网地址"/></td>
			</tr>
			<tr>
				<td><input type="text" name="integral_rate" id="integral_rate" value="${pd.integral_rate}" maxlength="32" placeholder="这里输入本店积分率" title="本店积分率"/></td>
			</tr>
			<tr>
				<td><input type="text" name="service_rate" id="service_rate" value="${pd.service_rate}" maxlength="32" placeholder="这里输入推广服务积分率" title="推广服务积分率"/></td>
			</tr>
			<tr>
				<td><input type="text" name="withdraw_rate" id="withdraw_rate" value="${pd.withdraw_rate}" maxlength="32" placeholder="这里输入提现费率" title="提现费率"/></td>
			</tr>
			<tr>
				<td><input type="text" name="lowest_score" id="lowest_score" value="${pd.lowest_score}" maxlength="32" placeholder="这里输入保底低分" title="保底低分"/></td>
			</tr>
			<tr>
				<td><input type="text" name="remind_integral" id="remind_integral" value="${pd.remind_integral}" maxlength="32" placeholder="这里输入积分提醒" title="积分提醒"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sort_score" id="sort_score" value="${pd.sort_score}" maxlength="32" placeholder="这里输入排序分值" title="排序分值"/></td>
			</tr>
			<tr>
				<td><input type="text" name="complex_score" id="complex_score" value="${pd.complex_score}" maxlength="32" placeholder="这里输入综合评分值" title="综合评分值"/></td>
			</tr>
			<tr>
				<td><input type="text" name="recharge_times" id="recharge_times" value="${pd.recharge_times}" maxlength="32" placeholder="这里输入充值次数" title="充值次数"/></td>
			</tr>
			<tr>
				<td><input type="text" name="transaction_times" id="transaction_times" value="${pd.transaction_times}" maxlength="32" placeholder="这里输入交易次数" title="交易次数"/></td>
			</tr>
			<tr>
				<td><input type="text" name="withdraw_times" id="withdraw_times" value="${pd.withdraw_times}" maxlength="32" placeholder="这里输入提现次数" title="提现次数"/></td>
			</tr>
			<tr>
				<td><input type="text" name="zan_times" id="zan_times" value="${pd.zan_times}" maxlength="32" placeholder="这里输入累计赞次数" title="累计赞次数"/></td>
			</tr>
			<tr>
				<td><input type="text" name="bigtype_max" id="bigtype_max" value="${pd.bigtype_max}" maxlength="32" placeholder="这里输入大类上限" title="大类上限"/></td>
			</tr>
			<tr>
				<td><input type="text" name="smalltype_min" id="smalltype_min" value="${pd.smalltype_min}" maxlength="32" placeholder="这里输入小类上限" title="小类上限"/></td>
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