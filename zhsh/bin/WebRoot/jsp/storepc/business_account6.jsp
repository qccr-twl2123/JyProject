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
	<title>商品销售明细表</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/zhxx_lsmx.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<script src="My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
          	.moneyred{color:red;}
         	.moneyblue{color:blue;}
    </style>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
<form action="storepc_wealthhistory/list.do" method="post" name="Form" id="Form">
    <input name="store_id" value="${pd.store_id }" type="hidden"/>
    <input type="hidden" name="profit_type" value="3" />
    <input type="hidden" name="chuli_type" value="4" />
	<ul>
		<li style="line-height:2.5;">
			<span>创建时间</span>
			<input class="inp-l" type="text" name="starttime" value="${pd.starttime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间"/>
			<span>至</span>
			<input class="inp-l"  type="text" name="endtime"  value="${pd.endtime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间"/>
			<span>&nbsp;&nbsp;操作员</span>
			<select  name="store_operator_id">
	                	<option value="">请选择</option>
	                	<c:forEach items="${splist}" var="var" varStatus="vs">
	                    	<option value="${var.store_operator_id }" ${var.store_operator_id eq pd.store_operator_id?'selected':''}>${var.operator_name }</option>
	                    </c:forEach>
	        </select> 
			<span>&nbsp;&nbsp;班次</span>
			<select   name="store_shift_id" >
	                   <option value ="">请选择</option>
	                   <c:forEach items="${shiftList}" var="var" varStatus="vs">
	                    	<option value="${var.store_shift_id }" ${var.store_shift_id eq pd.store_shift_id?'selected':''}>${var.shift_name}</option>
	                   </c:forEach>
	        </select>
		</li>
		<li style="line-height:2.5;">
			<span>支付方式</span>
			<select class="d1_p1_ipt1"  name="jiaoyi_type">
                    <option value ="">请选择</option>
                    <option value ="1" ${'1'eq pd.pay_type?'selected':''}>当面付</option>
                    <option value ="2" ${'2'eq pd.pay_type?'selected':''}>当面在线付</option>
                    <option value ="3" ${'3'eq pd.pay_type?'selected':''}>提货券</option>
                    <option value ="5" ${'5'eq pd.pay_type?'selected':''}>优选提货券</option>
            </select>
			&nbsp;&nbsp;
			<span >订单号</span>
            <input type = "text" class="inp-l"  name="order_id" value="${pd.order_id}"/>
			<span class="anniu-s" onclick="select()">搜索</span>
		</li>
		<li style="line-height:1.8;overflow:auto;">
			<table cellspacing="0" cellpadding="0" border="1" style="white-space: nowrap;min-width:1430px;">
				<thead>
					<tr>
						<td rowspan=2>支付时间</td>
						<td rowspan=2>订单号</td>
						<td colspan=2>付款方</td>
						<td rowspan=2>发生金额</td>
						<td rowspan=2>优惠金额</td>
						<td rowspan=2>实际金额</td>
						<td colspan=9>付款明细</td>
					</tr>
					<tr>
						<td>用户名称</td>
						<td>用户ID</td>
						<td>现金</td>
						<td>支付宝</td>
						<td>微信</td>
						<td>银行卡</td>
						<td>积分</td>
						<td>余额</td>
						<td>支付类型</td>
						<td>赠送积分</td>
						<td>推广积分</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${varList}" var="var" varStatus="vs">
		              <tr >
		                <td>${var.createdate}</td>
		                <td>${var.store_wealthhistory_id}</td>
  		                <td>${var.user_name} </td>
		                <td>${var.show_id} </td>
  		                <td>${var.money}</td>
		                <td>${var.discount_money}</td>
		                <td class="moneyblue">${var.arrivalmoney}</td>
		                <td>${var.nowypay_money} </td>
		                <td>${var.alipay_money} </td>
		                <td>${var.wx_money} </td>
		                <td>${var.bank_money} </td>
 		                <td> ${var.user_integral} </td>
		                <td> ${var.user_balance} </td>
 		                <td> 
		                		<c:if test="${var.jiaoyi_type eq '0'}">/</c:if>
		                		<c:if test="${var.jiaoyi_type eq '1'}">当面付</c:if>
			                	<c:if test="${var.jiaoyi_type eq '2'}">当面在线付</c:if>
			                	<c:if test="${var.jiaoyi_type eq '3'}">提货券</c:if>
			                	<c:if test="${var.jiaoyi_type eq '5'}">优选提货券</c:if>
			            </td>
			            <td class="moneyred"> ${var.get_integral} </td> 
			            <td class="moneyred" >${var.sendxitong_integral} </td> 
  		              </tr>
		             </c:forEach>
				</tbody>
				<tfoot>
					 <tr>
		               		<td colspan="4">本页合计</td>
		               		<td>${nowpagesum.summoney}</td>
			                <td>${nowpagesum.sumdiscount_money}</td>
			                <td  class="moneyblue">${nowpagesum.sumarrivalmoney}</td>
		                 	<td>${nowpagesum.sumnowypay_money} </td>
		                 	<td>${nowpagesum.sumalipay_money} </td>
			                <td>${nowpagesum.sumwx_money} </td>
			                <td>${nowpagesum.sumbank_money} </td>
			                <td> ${nowpagesum.sumuser_integral} </td>
			                <td> ${nowpagesum.sumuser_balance} </td>
			                <td>/</td>
			                <td class="moneyred"> ${nowpagesum.sumget_integral} </td> 
			                <td class="moneyred"> ${nowpagesum.sumsendxitong_integral} </td>
 			           </tr>
			           <tr>
			           		<td colspan="4">总合计</td>
			                <td>${allpagesum.summoney}</td>
			                <td>${allpagesum.sumdiscount_money}</td>
			                <td  class="moneyblue">${allpagesum.sumarrivalmoney}</td>
		                 	<td>${allpagesum.sumnowypay_money} </td>
		                 	<td>${allpagesum.sumalipay_money} </td>
			                <td>${allpagesum.sumwx_money} </td>
			                <td>${allpagesum.sumbank_money} </td>
			                <td> ${allpagesum.sumuser_integral} </td>
			                <td> ${allpagesum.sumuser_balance} </td>
			                <td>/</td>
			                <td class="moneyred" > ${allpagesum.sumget_integral} </td> 
			                <td class="moneyred" > ${allpagesum.sumsendxitong_integral} </td>
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
<script type="text/javascript">
   		
 		//提交
		function select(){
			 $("#Form").submit();
		}
 		
  		</script>
</body>

</html>