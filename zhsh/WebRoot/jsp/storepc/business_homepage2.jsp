<%--  
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>首页</title>
<meta charset="utf-8">
 <base href="<%=basePath%>">
<link rel="stylesheet" href="css/storepc/business_homepage2.css">
<script src="js/jquery-1.8.0.min.js"></script>
<script src="js/storepc/business_homepage2.js"></script>
<script type="text/javascript">
	function QRCode()
	{		
		var content = "商家的所有信息";
		window.location.href="QRCode.html?content=" + content;
	}
	function findScore() {
		var phone = $("#phone_text").val();
		$.ajax({
			url : "/zhsh/storepc_cashandpickup/findScore.do",
			type : "GET",
			dataType : "text",
			data : "phone=123",
			success : function(msg) {
				var dat = String(msg);
				dat = dat.replace("\"", "").replace("\"", "");
				$("#ajax_text1").text(dat);
			},
			error : function() {
				alert('error');
			}
		});

	}
</script>
</head>
<body>
	<div class="parent">
		<div class="d1">
			<span class="d1_sp1"> 收银和取货 </span> <span class="d1_sp3" onclick="QRCode()">生成桌号支付二维码</span>
			<span class="d1_sp2">取货验货</span>
		</div>
		<div class="d2">
			<a href="business_homepage2.jsp"><span
				class="d2_sp1 d2_sp1_active">按总金额</span>
			</a> <a href="jsp/storepc/business_homepage1.jsp"> <span class="d2_sp1 ">按类别金额</span>
			</a>
		</div>
		<form action="/zhsh/storepc_cashandpickup/userTotalMoney.do"
			method="post" name="Form" id="Form">
			<div class="d2">
				<span class="d2_sp2">手机号码</span> <input type="text" class="d2_ipt1"
					name="phone" /> <span class="d2_sp2 d2_sp2_2">积分： <span
					class="red" id="ajax_text1"></span>
				</span>
			</div>
			<div class="d2">
				<span class="d2_sp2">桌号</span> <input type="text" class="d2_ipt1"
					name="desk_no" />
			</div>
			<div class="d2">
				<span class="d2_sp2">消费金额</span> <input type="text" class="d2_ipt1"
					name="consume_money" />
			</div>
			<div class="d2">
				<span class="d2_sp2">不优惠金额</span> <input type="text" class="d2_ipt1"
					name="not_discount_money" />
			</div>

			<div class="d2">
				<span class="d2_sp2  width">优惠项 </span> <span class="d2_sp4">优惠项1</span>
			</div>
			<div class="d2">
				<span class="d2_sp2  ">优惠项 </span> <span class="d2_sp4">优惠项2</span>
			</div>
			<div class="d2">
				<span class="d2_sp2  width">优惠项 </span> <span class="d2_sp4">优惠项3</span>
			</div>
			<div class="d2">
				<span class="d2_sp2 ">应收合计 </span> <span class=" d2_sp3">???</span>元
				<span class="d2_sp2 ">送积分 </span> <span class=" d2_sp3">???</span>分
			</div>
			<div class="d2">
				<span class="d2_sp2  hands"> <label> <input
						type="checkbox" class="dx" name="score_checkbox" /> <span
						class="hands">使用积分支付</span> </label> </span> <input type="text" class="d2_ipt1"
					name="get_integral" />
			</div>
			<div class="d2">
				<span class="d2_sp2 ">还需支付金额 </span> <span class=" d2_sp3">???</span>元
			</div>
			<div class="d2 font14">
				<input type="submit" class="yes">确认收银<span>提示：请确认会员现金或刷卡支付成功后再点击</span>
			</div>
		</form>
	</div>
</body>
</html> --%>