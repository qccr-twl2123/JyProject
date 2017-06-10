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
			if($("#goods_category_id").val()==""){
			$("#goods_category_id").tips({
				side:3,
	            msg:'请输入类别编号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#goods_category_id").focus();
			return false;
		}
		if($("#goods_name").val()==""){
			$("#goods_name").tips({
				side:3,
	            msg:'请输入商品名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#goods_name").focus();
			return false;
		}
		if($("#image_url").val()==""){
			$("#image_url").tips({
				side:3,
	            msg:'请输入商品图片url',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#image_url").focus();
			return false;
		}
		if($("#market_money").val()==""){
			$("#market_money").tips({
				side:3,
	            msg:'请输入市场价',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#market_money").focus();
			return false;
		}
		if($("#retail_money").val()==""){
			$("#retail_money").tips({
				side:3,
	            msg:'请输入零售价',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#retail_money").focus();
			return false;
		}
		if($("#integral_rate").val()==""){
			$("#integral_rate").tips({
				side:3,
	            msg:'请输入积分率',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#integral_rate").focus();
			return false;
		}
		if($("#stock_number").val()==""){
			$("#stock_number").tips({
				side:3,
	            msg:'请输入库存量',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#stock_number").focus();
			return false;
		}
		if($("#consumption_number").val()==""){
			$("#consumption_number").tips({
				side:3,
	            msg:'请输入已消费量',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#consumption_number").focus();
			return false;
		}
		if($("#goods_status").val()==""){
			$("#goods_status").tips({
				side:3,
	            msg:'请输入商品状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#goods_status").focus();
			return false;
		}
		if($("#sales_status").val()==""){
			$("#sales_status").tips({
				side:3,
	            msg:'请输入单品销售状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sales_status").focus();
			return false;
		}
		if($("#starttime").val()==""){
			$("#starttime").tips({
				side:3,
	            msg:'请输入单品营销开始时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#starttime").focus();
			return false;
		}
		if($("#endtime").val()==""){
			$("#endtime").tips({
				side:3,
	            msg:'请输入单品营销结束时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#endtime").focus();
			return false;
		}
		if($("#promotion_type").val()==""){
			$("#promotion_type").tips({
				side:3,
	            msg:'请输入促销规则/类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#promotion_type").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="goods/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="goods_id" id="goods_id" value="${pd.goods_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="goods_category_id" id="goods_category_id" value="${pd.goods_category_id}" maxlength="32" placeholder="这里输入类别编号" title="类别编号"/></td>
			</tr>
			<tr>
				<td><input type="text" name="goods_name" id="goods_name" value="${pd.goods_name}" maxlength="32" placeholder="这里输入商品名称" title="商品名称"/></td>
			</tr>
			<tr>
				<td><input type="text" name="image_url" id="image_url" value="${pd.image_url}" maxlength="32" placeholder="这里输入商品图片url" title="商品图片url"/></td>
			</tr>
			<tr>
				<td><input type="text" name="market_money" id="market_money" value="${pd.market_money}" maxlength="32" placeholder="这里输入市场价" title="市场价"/></td>
			</tr>
			<tr>
				<td><input type="text" name="retail_money" id="retail_money" value="${pd.retail_money}" maxlength="32" placeholder="这里输入零售价" title="零售价"/></td>
			</tr>
			<tr>
				<td><input type="text" name="integral_rate" id="integral_rate" value="${pd.integral_rate}" maxlength="32" placeholder="这里输入积分率" title="积分率"/></td>
			</tr>
			<tr>
				<td><input type="text" name="stock_number" id="stock_number" value="${pd.stock_number}" maxlength="32" placeholder="这里输入库存量" title="库存量"/></td>
			</tr>
			<tr>
				<td><input type="text" name="consumption_number" id="consumption_number" value="${pd.consumption_number}" maxlength="32" placeholder="这里输入已消费量" title="已消费量"/></td>
			</tr>
			<tr>
				<td><input type="text" name="goods_status" id="goods_status" value="${pd.goods_status}" maxlength="32" placeholder="这里输入商品状态" title="商品状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sales_status" id="sales_status" value="${pd.sales_status}" maxlength="32" placeholder="这里输入单品销售状态" title="单品销售状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="starttime" id="starttime" value="${pd.starttime}" maxlength="32" placeholder="这里输入单品营销开始时间" title="单品营销开始时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="endtime" id="endtime" value="${pd.endtime}" maxlength="32" placeholder="这里输入单品营销结束时间" title="单品营销结束时间"/></td>
			</tr>
			<tr>
				<td><input type="text" name="promotion_type" id="promotion_type" value="${pd.promotion_type}" maxlength="32" placeholder="这里输入促销规则/类型" title="促销规则/类型"/></td>
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