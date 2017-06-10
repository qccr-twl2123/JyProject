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
	<title>效果分析</title>
	 <base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/yxkt_yxgl.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<script src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>
  <c:if test="${storeqx.look eq '1'}">
  <form action="storepc_marketingeffect/list.do?store_id=${storepd.store_id}" name="Form" id="Form" method="post"> 
	<ul>
		<li style="line-height:3;">
			<span>开始时间：</span>
 			<input value="${pd.startdate}" style="width: 100px;"  type="text" name="startdate"  id="startdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间"/>
			<span>&nbsp;&nbsp;&nbsp;&nbsp;截止时间：</span>
			<input  value="${pd.enddate}" style="width: 100px;"  type="text" name="enddate" id="enddate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间" />
 			<span class="anniu-s"  onclick="select()">查询</span>
		</li>
		<li style="line-height:2.5;">
			<table border="1px solid #999;" cellspacing="0" cellpadding="0" style=" width: 100%; "> 
				<thead>
					<tr>
						<td>序号</td>
						<td>方案</td>
						<td>详情</td>
						<td>发放到</td>
						<td>领取数</td>
						<td>使用数</td>
						<td>销售额</td>
						<td>营销时间</td>
					</tr>
				</thead>
				<tbody>
					 <c:forEach  items="${varList}" var="var" varStatus="vs" >
						<tr>
							<td>${vs.index + 1}</td>
							<td>
								<c:if test="${var.marketing_type eq '0'}"> 红包</c:if>
								<c:if test="${var.marketing_type eq '1'}"> 满赠</c:if>
								<c:if test="${var.marketing_type eq '2'}"> 满减</c:if>
								<c:if test="${var.marketing_type eq '3'}"> 时段营销</c:if>
								<c:if test="${var.marketing_type eq '4'}"> 买n减1</c:if>
								<c:if test="${var.marketing_type eq '5'}"> 购买次数</c:if>
								<c:if test="${var.marketing_type eq '6'}"> 折扣</c:if>
								<c:if test="${var.marketing_type eq '7'}"> 积分方式</c:if>
							</td>
							<td>${var.content }</td>
							<td>${var.grant_number }</td>
							<td>${var.receive_number }</td>
							<td>${var.use_number }</td>
							<td>${var.sale_quota }</td>
							<td>${var.startdate }-${var.enddate }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</li>
		<li class="fenye clf">
			<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		</li>
	</ul>
	</form>
	</c:if>
	<script type="text/javascript">
			//查询
			function select(){
 	    		$("#Form").submit();
    		}
 	</script>
</body>
</html>