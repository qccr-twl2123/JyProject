<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>充值提现汇总</title>
	<base href="<%=basePath%>">
 	<link rel="stylesheet" href="css/pcstore/zhxx_txhz.css">
	<link href="css/bootstrap.min.css" rel="stylesheet" />
  	<style type="text/css">
	table tr:last-child td { 
	        border-bottom: 1px solid #a4a4a4;
	}
	.pagination ul>li>a{
		border: 0;
		line-height:1.2;
		padding:0;
	}
 	</style>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
 <form action="storepc_wealthhistory/list.do" method="post" name="Form" id="Form">
    <input name="store_id" value="${pd.store_id }" type="hidden"/>
    <input type="hidden" name="chuli_type" value="3" />
	<ul>
		<li>
			<span>创建时间</span>
			 <input type="text" name="starttime" value="${pd.starttime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间"/>
			<span>至</span>
			<input  type="text" name="endtime" id="end" value="${pd.endtime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间" />
			<span>交易类型</span>
			<select class="d1_p1_ipt1"  name="profit_type">
	                	<option value="">全部</option>
	                    <option value="1" ${pd.profit_type eq'1'?'selected':'' }>提现</option>
	                    <option value="2"  ${pd.profit_type eq'2'?'selected':'' }>积分充值</option>
 	        </select>
            <span class="anniu-s" onclick="select()"> 搜索 </span>
		</li>
		<li >
			<table cellspacing="0" cellpadding="0"  style="white-space: nowrap;border:1px solid #999; ">
			<thead>
				<tr>
					<td>订单号</td>
					<td>类型</td>
					<td>金额</td>
					<td>提现/充值费率</td>
					<td>入账金额</td>
					<td>支付方式</td>
					<td>支付账号</td>
					<td>提现后剩余余额</td>
					<td>状态</td>
					<td>日期</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${varList}" var="var" varStatus="vs">
	                <tr>
	                    	<td>${var.store_wealthhistory_id }</td>
   	                    	<c:if test="${var.profit_type eq '1' }"><td>提现</td> <td class="money" style="color:red;">${var.money }</td></c:if>
	                    	<c:if test="${var.profit_type eq '2' }"><td>积分充值</td> <td class="money" >${var.money }</td></c:if>
  	                    	<td>${var.withdraw_rate}</td>
  	                    	<td class="money">${var.arrivalmoney}</td>
 	                    	<td>
 	                    		<c:if test="${var.remittance_type eq '1'}">银联支付</c:if>
			                	<c:if test="${var.remittance_type eq '2'}">现金支付</c:if>
			                	<c:if test="${var.remittance_type eq '3'}">支付宝支付</c:if>
			                	<c:if test="${var.remittance_type eq '4'}">微信支付</c:if>
			                	<c:if test="${var.remittance_type eq '5'}">苹果支付</c:if>
 	                    	</td>
	                    	<td>${var.remittance_name}--${var.kh}</td>
	                    	<td>${var.last_wealth}</td>
	                    	<td> 
	                    		<c:if test="${var.process_status eq '0' and var.profit_type eq '1'}">正在审核 <span  style="color:blue;cursor: pointer;" onclick="chehui('${var.store_wealthhistory_id}')">撤回申请</span></c:if>
	                    		<c:if test="${var.process_status eq '0' and var.profit_type eq '2'}">无效订单（凌晨自动删除）</c:if>
	                    		<c:if test="${var.process_status eq '1' }">审核通过，请注意短信</c:if>
	                    		<c:if test="${var.process_status eq '99' }">当前订单已驳回/已撤回</c:if>
	                    	</td>
	                    	<td>${var.createdate}</td>
	                </tr>
	            </c:forEach>
			</tbody>
			<tfoot>
				<tr>
	            	<td colspan="2">本页合计</td>
	            	<td class="money">${nowpagesum.summoney}</td>
	            	<td>/</td>
 			        <td class="money">${nowpagesum.sumarrivalmoney}</td>
	            	<td colspan="5">/</td>
	            </tr>
	            <tr>
	            	<td colspan="2">总合计</td>
	            	<td class="money">${allpagesum.summoney}</td>
	            	<td>/</td>
 			        <td class="money">${allpagesum.sumarrivalmoney}</td>
	            	<td colspan="5">/</td>
	            </tr>
			</tfoot>
			</table>
		</li>
		<li class="fenye clf">
			<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		</li>
	</ul>
</form>
</c:if>
<script src="js/jquery-1.8.0.min.js"></script>
<script src="My97DatePicker/WdatePicker.js"></script>
<!--引入弹窗组件start-->
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootbox.min.js"></script><!-- 确认窗口 -->
<script type="text/javascript">
	//提交
	function select(){
		$("#Form").submit();
	}
	//撤回申请
	function chehui(store_wealthhistory_id){
		bootbox.confirm("确定撤回吗?", function(result) {
			if(result) {
				var url = "storepc_wealthhistory/txReturn.do?store_wealthhistory_id="+store_wealthhistory_id;
				$.get(url,function(data){
					if(data=="success"){
						nextPage(${page.currentPage});
					}
				});
			}
		});
 	}
		
</script>
</body>
</html>