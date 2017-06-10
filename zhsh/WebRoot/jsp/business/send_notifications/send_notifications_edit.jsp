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
			if($("#user_type").val()==""){
			$("#user_type").tips({
				side:3,
	            msg:'请输入用户类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#user_type").focus();
			return false;
		}
		if($("#phone").val()==""){
			$("#phone").tips({
				side:3,
	            msg:'请输入精确用户（手机号）',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#phone").focus();
			return false;
		}
		if($("#send").val()==""){
			$("#send").tips({
				side:3,
	            msg:'请输入是否是精确用户发送',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#send").focus();
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
	            msg:'请输入区/县id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#area_id").focus();
			return false;
		}
		if($("#send_startdate").val()==""){
			$("#send_startdate").tips({
				side:3,
	            msg:'请输入发送开始时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#send_startdate").focus();
			return false;
		}
		if($("#send_enddate").val()==""){
			$("#send_enddate").tips({
				side:3,
	            msg:'请输入发送结束时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#send_enddate").focus();
			return false;
		}
		if($("#send_starttime").val()==""){
			$("#send_starttime").tips({
				side:3,
	            msg:'请输入发送开始时段',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#send_starttime").focus();
			return false;
		}
		if($("#send_endtime").val()==""){
			$("#send_endtime").tips({
				side:3,
	            msg:'请输入发送结束时段',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#send_endtime").focus();
			return false;
		}
		if($("#content").val()==""){
			$("#content").tips({
				side:3,
	            msg:'请输入发送内容',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#content").focus();
			return false;
		}
		if($("#operator_id").val()==""){
			$("#operator_id").tips({
				side:3,
	            msg:'请输入操作员id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#operator_id").focus();
			return false;
		}
		if($("#review_id").val()==""){
			$("#review_id").tips({
				side:3,
	            msg:'请输入审核人员',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#review_id").focus();
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
	<form action="send_notifications/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="send_notifications_id" id="send_notifications_id" value="${pd.send_notifications_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="user_type" id="user_type" value="${pd.user_type}" maxlength="32" placeholder="这里输入用户类型" title="用户类型"/></td>
			</tr>
			<tr>
				<td><input type="text" name="phone" id="phone" value="${pd.phone}" maxlength="32" placeholder="这里输入精确用户（手机号）" title="精确用户（手机号）"/></td>
			</tr>
			<tr>
				<td><input type="text" name="send" id="send" value="${pd.send}" maxlength="32" placeholder="这里输入是否是精确用户发送" title="是否是精确用户发送"/></td>
			</tr>
			<tr>
				<td><input type="text" name="province_id" id="province_id" value="${pd.province_id}" maxlength="32" placeholder="这里输入省id" title="省id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="city_id" id="city_id" value="${pd.city_id}" maxlength="32" placeholder="这里输入市id" title="市id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="area_id" id="area_id" value="${pd.area_id}" maxlength="32" placeholder="这里输入区/县id" title="区/县id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="send_startdate" id="send_startdate" value="${pd.send_startdate}" maxlength="32" placeholder="这里输入发送开始时间" title="发送开始时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="send_enddate" id="send_enddate" value="${pd.send_enddate}" maxlength="32" placeholder="这里输入发送结束时间" title="发送结束时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="send_starttime" id="send_starttime" value="${pd.send_starttime}" maxlength="32" placeholder="这里输入发送开始时段" title="发送开始时段"/></td>
			</tr>
			<tr>
				<td><input type="text" name="send_endtime" id="send_endtime" value="${pd.send_endtime}" maxlength="32" placeholder="这里输入发送结束时段" title="发送结束时段"/></td>
			</tr>
			<tr>
				<td><input type="text" name="content" id="content" value="${pd.content}" maxlength="32" placeholder="这里输入发送内容" title="发送内容"/></td>
			</tr>
			<tr>
				<td><input type="text" name="operator_id" id="operator_id" value="${pd.operator_id}" maxlength="32" placeholder="这里输入操作员id" title="操作员id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="review_id" id="review_id" value="${pd.review_id}" maxlength="32" placeholder="这里输入审核人员" title="审核人员"/></td>
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