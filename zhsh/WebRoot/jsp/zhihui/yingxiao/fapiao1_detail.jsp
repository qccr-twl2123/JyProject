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
         <title>发票详情</title>
         <meta charset="utf-8">
         <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
         <link href="css/bootstrap.min.css" rel="stylesheet" />
     </head>
    <body>
  	          <table   class="table table-striped table-bordered table-hover"    style="width:100%;">
	              <thead>
 	              	<tr>
	              		<td>序号</td>
	              		<td>订单编号</td>
						<td>产品名称</td>
						<td>金额</td>
						<td>成功时间</td>
	              	</tr>
	              </thead> 
	              <c:forEach items="${varList}" var="var" varStatus="vs">
	              	<tr>
		                <td>${vs.index+1}</td>
	              		<td>${var.waterrecord_id}</td>
	              		<td>
	              			<c:if test="${var.profit_type eq '9' }">商家服务费</c:if>
	              			<c:if test="${var.profit_type eq '10' }">商家服务费（续）</c:if>
	              		</td>
	              		<td>${var.money}</td>
	              		<td>${var.createtime}</td>
  		             </tr> 
 	              </c:forEach>           
	          </table>
	          <br/>
	          <table   class="table table-striped table-bordered table-hover"    style="width:100%;">
	              <thead>
	              	<tr>
	              		<td colspan="2">发票信息</td>
 	              	</tr>
 	              	<tr>
	              		<td>名称</td><td>${pd.bill_tt }</td>
 	              	</tr>
 	              	<tr>
	              		<td>纳税人识别号</td><td>${pd.swdj_number }</td>
 	              	</tr>
 	              	<tr>
	              		<td>电话和地址</td><td>${pd.yy_phone }${pd.yy_address }</td>
 	              	</tr>
 	              	<tr>
	              		<td>开户行及账号</td><td>${pd.sj_bankname }${pd.sj_banknumber }</td>
 	              	</tr>
	              </thead> 
 	          </table>
	          
      </body>
</html>