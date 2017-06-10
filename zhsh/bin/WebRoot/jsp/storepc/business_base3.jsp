<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<% String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>高级信息</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/jcxx_gjxx.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<style type="text/css">
	tr{
		line-height: 35px;
	}
	</style>
</head>
<body>
 <c:if test="${storeqx.look eq '1'}">
    <form action="<%=basePath%>storepc_StoreManageController/addRemind.do" name="Form" id="Form">
	<table style="border:0;margin:0;border-collapse:collapse;width:100%;">
		<tr>
			<td>
				<span>商家名称：</span>
				<span>${store.store_name}</span>
			</td>
			<td>
				<span>商家ID：</span>
				<span>${store.store_id}</span>
			</td>
		</tr>
		<tr>
			<td colspan=2>
				<span>行业分类：</span>
				<span>${sfs.name1 }--${sfs.parent_name1 }&nbsp;&nbsp;${sfs.name2 }--${sfs.parent_name2 }&nbsp;&nbsp;${sfs.name3 }--${sfs.parent_name3}</span>
			</td>
		</tr>
		<tr>
			<td>
				<span>商家注册手机号：</span>
				<span>${store.registertel_phone}</span>
			</td>
			<td>
				<span>会员联系号码：</span>
				<span>${store.phone_bymemeber}</span>
			</td>
		</tr>
		<tr>
			<td>
				<span>会员积分率：</span>
				<span>${store.integral_rate}</span>	
			</td>
			<td>
				<span>推广服务积分率：</span>
				<span>${store.service_rate}</span>
			</td>
		</tr>
		<tr>
			<td>
				<span>提现费率：</span>
				<span>${store.withdraw_rate}%</span>
			</td>
			<td colspan=2>
				<span>注册时间：</span>
				<span>${store.registertime}</span>
			</td> 
		</tr>
 		<tr>
			<td colspan=2>
				<span class="col-red">*</span>
				<span>积分提醒：</span>
				<input type="text" class="inp-s" value="${store.remind_integral }" name="remind_integral">
				<span class="col-6 font-s">注：系统默认为最低50，设置后地狱该积分值将自动发送短信提醒充值。</span>
			</td>
		</tr>
		<c:if test="${storeqx.edit eq '1'}">
			<tr>
				<td colspan=2 style="text-align:center;">
					<span class="anniu-l">提交</span>
				</td>
			</tr>
		</c:if>
	</table>
	</form>
   </c:if>
</body>
<script type="text/javascript">
    //提交
	function sureUp(){
    	    alert("设置成功");
	    	$("#Form").submit();
	}
    
    </script>
</html>