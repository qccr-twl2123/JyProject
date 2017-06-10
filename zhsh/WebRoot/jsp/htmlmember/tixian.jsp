<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
        <meta charset="utf-8">
 		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/style.css">
		<link rel="stylesheet" href="<%=basePath%>css/htmlmember/styles.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/main.js"></script>
		<style type="text/css">
		.radioclass{
			opacity: 1;
		}
		</style>
</head>
<body style="background:#ededed;">
<nav class="top">
	<a href="<%=basePath%>html_me/listPurse.do?member_id=${pd.member_id}"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">余额提现</div>
</nav>

<div class="recharge-content reflect-content clearfix">
	<ul>
		<li>
			<span class="reflect-left">可提余额：</span>
			<span>￥<span id="now_money">${pd.now_money }</span></span>
		</li>
		<li>
			<span class="reflect-left">金额（元）：</span>
			<input type="text" class="reflect-input" placeholder="请输入提现金额" id="money" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
		</li>
		<c:if test="${!empty varList}">
	  		<li>
	 			<c:forEach items="${varList}" var="var" varStatus="vs">
					<p><input type="radio"  name="paylist" value="${var.bank_number }" class="radioclass">
					<span id="account">${var.account }</span><span>(${var.kh })</span>
					<input hidden="hidden" value="${var.member_name }" id = "member_name"/></p>
				</c:forEach>
				
			</li>
		</c:if>
		<li>
			<a href="<%=basePath%>html_me/gobank.do?member_id=${pd.member_id}">
			<i class="reflect-add">+</i>
			<span>新增银行卡</span>
			</a>
		</li>
	</ul>
</div>

<div class="recharge-content reflect-last clearfix">
	<ul>
		<li>
			<p>3-10个工作日内退回至最近消费的账户中</p>
			<p>银行处理可能有延迟，具体以账户的到账时间为准。由于余额可能会有多个支付来源，提现时也会对应分多笔到账。</p>
		</li>
	</ul>
</div>

	<input class="recharge-sure" type="button" value="确认提现" onclick="save()"/>

</body>
<script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
<script type="text/javascript">

	function save(){
		var money = $("#money").val().trim();
		var txkh = $('input:radio:checked').val();
		var now_money = $("#now_money").text();
		var account = $("#account").text();
		var member_name = $("#member_name").val();
		if(money == "" || money == null){
			alert("金额不能为空！");
			return ;
		}
		if(now_money <= money){
			alert("提现金额不能大于可提金额！");
			return ;
		}
		if(txkh == null || txkh == ""){
			alert("请选择要提现的银行卡！");
			return ;
		}
 		$.ajax({
					type:"post",
					url:"<%=basePath%>html_me/saveWithdraw.do",
					data:"user_id=${pd.member_id}"+"&user_type=2&money="+money+"&account_name="+account+"&withdraw_number="+txkh+
					"&member_name="+member_name+"&account_type=4",
					success:function(data){
						if(data.result == "00"){
						alert("今日你已提现过！");
	 					}else if(data.result == "01"){
							alert("提现审批，请等待1至2个工作日！");
							window.location.reload();
						}else if(data.result == "02"){
							alert("余额不足！");
						}else if(data.result == "03"){
							alert("系统异常！");
						}
 					}
			}); 
 	}
</script>
</html>
