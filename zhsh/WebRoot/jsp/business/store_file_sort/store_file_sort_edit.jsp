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
			if($("#store_file_id").val()==""){
			$("#store_file_id").tips({
				side:3,
	            msg:'请输入商家档案id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#store_file_id").focus();
			return false;
		}
		if($("#dalei_id").val()==""){
			$("#dalei_id").tips({
				side:3,
	            msg:'请输入大类id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#dalei_id").focus();
			return false;
		}
		if($("#xiaolei_id").val()==""){
			$("#xiaolei_id").tips({
				side:3,
	            msg:'请输入小类id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#xiaolei_id").focus();
			return false;
		}
		if($("#store_file_sort_id").val()==""){
			$("#store_file_sort_id").tips({
				side:3,
	            msg:'请输入唯一标识id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#store_file_sort_id").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="store_file_sort/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="store_file_sort_id" id="store_file_sort_id" value="${pd.store_file_sort_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="store_file_id" id="store_file_id" value="${pd.store_file_id}" maxlength="32" placeholder="这里输入商家档案id" title="商家档案id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="dalei_id" id="dalei_id" value="${pd.dalei_id}" maxlength="32" placeholder="这里输入大类id" title="大类id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="xiaolei_id" id="xiaolei_id" value="${pd.xiaolei_id}" maxlength="32" placeholder="这里输入小类id" title="小类id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="store_file_sort_id" id="store_file_sort_id" value="${pd.store_file_sort_id}" maxlength="32" placeholder="这里输入唯一标识id" title="唯一标识id"/></td>
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