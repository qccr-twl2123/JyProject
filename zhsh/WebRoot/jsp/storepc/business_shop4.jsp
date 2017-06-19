<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>人气榜</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/predefine.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<style type="text/css">
	body, html {
   	 height: 480px;
 	}
 	@media screen and (max-height:750px){
		html,body{		
			height:90%;
		}   
	}
	</style>
</head>
<body >
<c:if test="${storeqx.look eq '1'}">
<form action="<%=basePath%>storepc_CategoryManageController/showShop4.do" name="Form" id="Form" method="post">
	<input type="hidden" name="store_id" value="${pd.store_id}" />
	<input type="hidden" name="paixu_w"  id="paixu_w" value="0" />
	<table cellspacing="0" cellpadding="0" border="1" style="width:96%;border:1px solid #a4a4a4;margin:15px 2%;;">
				<thead>
						<tr>
							<td>序号</td>
							<td>大类排序</td>
							<td>大类名称</td>
							<td>小类名称</td>
							<td>商品名称</td>
							<td>原价</td>
							<td>零售价</td>
							<td>积分率</td>
							<td>积分值</td>
							<td>单品销售状态</td>
							<td>单位</td>
							<td>操作</td>
						</tr>
				</thead>
				<tbody>
					<c:forEach items="${good_list}" var="slist" varStatus="v">
						<tr>
							<td>${v.index+1}</td>
							<td>${slist.parent_sort}</td>
							<td>${slist.parent_name}</td>
							<td>${slist.son_name}</td>
							<td>${slist.goods_name}</td>
							<td>${slist.market_money}</td>
							<td>${slist.retail_money}</td>
							<td>${slist.integral_rate}%</td>
							<td>${slist.integral_number}</td>
							<td>
								<c:if test="${slist.promotion_type eq '0'}">
								无
								</c:if>
								<c:if test="${slist.promotion_type eq '1'}">
								满减
								</c:if>
								<c:if test="${slist.promotion_type eq '2'}">
								单品折扣
								</c:if>
								<c:if test="${slist.promotion_type eq '3'}">
								买N减1
								</c:if>
							</td>
							<td>${slist.company}</td>
							<td><a  class="col-blue del"   href="<%=basePath%>storepc_CategoryManageController/delRenqi.do?goods_id=${slist.goods_id}&goods_rq_id=${slist.goods_rq_id}">删除</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</c:if>
</body>
</html>