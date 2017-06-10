<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
         <title>商家人脉收益详情</title>
         <meta charset="utf-8">
         <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
         <link href="css/bootstrap.min.css" rel="stylesheet" />
         <style type="text/css">
         	a:HOVER {
				cursor: pointer;
			}
         </style>
     </head>
    <body>
  	          <table   class="table table-striped table-bordered table-hover"    style="white-space: nowrap;line-height:36px">
	              <tr class="tdtop">
		                <td  rowspan="2">支付时间</td>
		                <td  rowspan="2">订单号</td>
		                <td  colspan="2">付款方</td>
 		                <td rowspan="2">发生金额</td>
		                <td rowspan="2">优惠金额/系统扣费</td>
		                <td rowspan="2">不优惠金额</td>
		                <td rowspan="2">实际金额</td>
		                <td colspan="7">付款明细</td>
		                <td rowspan="2">支付类型</td>
		                <td rowspan="2">支付渠道</td>
		                <td rowspan="2">支付账号</td>
		                <td colspan="5">平台收益</td>
		                <td rowspan="2">赠送积分</td>
  		                <td colspan="2">收款方</td>
 		                <td rowspan="2">付款所在区域</td>
		              </tr> 
		              <tr>
 	                    <td>用户名称</td>
	                    <td>用户ID号</td>
	                    
	                    <td>现金</td>
	                    <td>支付宝</td>
	                    <td>微信</td>
	                    <td>银行卡</td>
	                    <td>积分</td>
	                    <td>余额</td>
	                    <td>平台支付</td>
	                    
	                    <td>推广积分</td>
 	                    <td>子公司</td>
	                    <td>服务商</td>
 	                    <td>一度人脉</td>
	                    <td>二度人脉</td>
	                    
 		                <td>收款方ID</td>
		                <td>收款方名称</td>
	                  </tr> 
		              <c:forEach items="${varList}" var="var" varStatus="vs">
			              <tr >
			                <td>${var.createtime}</td>
			                <td>${var.waterrecord_id}</td>
	  		                <td> ${var.name} </td>
			                <td> ${var.user_id} </td>
	  		                <td>${var.money}</td>
			                <td>${var.reduce_money}</td>
			                <td>${var.no_discount_money}</td>
			                <td>${var.arrivalmoney}</td>
			                <td>${var.nowypay_money} </td>
			                <td>${var.alipay_money} </td>
			                <td>${var.wx_money} </td>
			                <td>${var.bank_money} </td>
	 		                <td>${var.user_integral} </td>
			                <td>${var.user_balance} </td>
			                <td> ${var.jiuyu_money} </td>
	 		                <td> 
			                		<c:if test="${var.jiaoyi_type eq '0'}">/</c:if>
			                		<c:if test="${var.jiaoyi_type eq '1'}">当面付</c:if>
				                	<c:if test="${var.jiaoyi_type eq '2'}">当面付</c:if>
				                	<c:if test="${var.jiaoyi_type eq '3'}">提货券</c:if>
				                	<c:if test="${var.jiaoyi_type eq '4'}">/</c:if>
				                	<c:if test="${var.jiaoyi_type eq '5'}">优选提货券</c:if>
				            </td>
	 		                <td>
			                  	    <c:if test="${var.application_channel eq '1'}">C端</c:if>
				                	<c:if test="${var.application_channel eq '2'}">B端 </c:if>
				                	<c:if test="${var.application_channel eq '3'}">PC会员端</c:if>
				                	<c:if test="${var.application_channel eq '4'}">PC商家端</c:if>
				                	<c:if test="${var.application_channel eq '5'}">微信端</c:if>
				                	<c:if test="${var.application_channel eq '6'}">PC总后台</c:if>
			                </td>
			                <td> ${var.remittance_number}  </td>
			                <td> ${var.sendxitong_integral} </td>
			                <td> ${var.subsidiary_getmoney} </td>
			                <td> ${var.sp_getmoney} </td>
	 		                <td> ${var.onecontacts_getmoney} </td>
			                <td> ${var.twocontacts_getmoney} </td>
			                <td> ${var.get_integral} </td> 
 	 		                <td> ${var.payee_number }<!-- 收款方 ID --></td>
			                <td> ${var.payee_name }<!-- 收款方 名称 --></td>
	  		                <td> ${var.province_name}${var.city_name}${var.area_name}</td>
		              </tr>
		              </c:forEach>          
	          </table>
       </body>
</html>