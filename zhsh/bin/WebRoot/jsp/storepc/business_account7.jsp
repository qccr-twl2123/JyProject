<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>班次汇总</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/zhxx_txhz.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<script src="My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
         	.moneyred{color:red;}
    </style>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
<form action="storepc_wealthhistory/BanCiHuiZonglist.do" method="post" name="Form" id="Form">
    <input type="hidden" name="store_id" value="${storepd.store_id }"/>
	<ul>
		<li>
			<%-- <span>上下机时间</span>
			<input type="text" name="starttime" value="${pd.starttime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间"/>
			<span>至</span>
			<input  type="text" name="endtime"   value="${pd.endtime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间"/> --%>
			<span>操作员</span>
			<select class="d1_p1_ipt1"  name="store_operator_id">
	                	<option value="">请选择</option>
	                	<c:forEach items="${splist}" var="var" varStatus="vs">
	                    	<option value="${var.store_operator_id }"  ${var.store_operator_id eq pd.store_operator_id?'selected':''}>${var.operator_name }</option>
	                    </c:forEach>
	        </select> 
			<span>班次</span>
			<select class="d1_p1_ipt1" name="store_shift_id" >
	                   <option value ="">请选择</option>
	                   <c:forEach items="${shiftList}" var="var" varStatus="vs">
	                    	<option value="${var.store_shift_id }" ${var.store_shift_id eq pd.store_shift_id?'selected':''}>${var.shift_name}</option>
	                   </c:forEach>
	        </select>
             <span class="anniu-s"  onclick="select()">   搜索 </span>
		</li>
		<li style="overflow:auto;">
			<table cellspacing="0" cellpadding="0" border="1" style="white-space: nowrap;min-width:100%;">
				<thead>
					<tr>
						<td rowspan="2">操作员</td>
						<!-- <td rowspan="2">上机时间</td>
						<td rowspan="2">下机时间</td> -->
						<td rowspan="2">班次</td>
						<td rowspan="2">销售金额</td>
						<td rowspan="2">优惠金额</td>
						<td rowspan="2">实收金额</td>
						<td colspan="6">收入明细</td>
						<td rowspan="2">赠送积分</td>
						<td rowspan="2">赠送系统积分</td>
						<td rowspan="2">销售笔数</td>
					</tr>
					<tr>
						<td>现金</td>
						<td>支付宝</td>
						<td>微信</td>
						<td>银行卡</td>
						<td>积分</td>
						<td>余额</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${tjList}" var="var" varStatus="vs">
						<tr>
								<td>${var.operator_name }</td>
								<%-- <td>${var.logintime}</td>
								<td>${var.downtime}</td>   --%>
								<td>${empty var.shift_name?'/':var.shift_name }</td>
								<td>${var.sumsale_money}</td>
								<td>${var.sumdiscount_money}</td>
								<td>${var.sumarrivalmoney}</td>
								<td>${var.sumnowypay_money}</td>
								<td>${var.sumalipay_money} </td>
								<td>${var.sumwx_money} </td>
								<td>${var.sumbank_money} </td>
								<td>${var.sumuser_integral} </td>
								<td>${var.sumuser_balance} </td>
								<td class="moneyred">${var.sumget_integral} </td>
								<td class="moneyred">${var.sumsendxitong_integral} </td>
								<td>${var.ordernumber}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</li>
	</ul>
</form>
</c:if>
<script type="text/javascript">
 			function select(){
				 $("#Form").submit();
			}
</script>    
</body>
</html>