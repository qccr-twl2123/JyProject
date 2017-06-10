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
	<title>排行榜</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/sj_hy.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<style type="text/css">
	.clickcolor {
	    background: #8a8b8b;
 	}
 	</style>
 </head>
<body>
<c:if test="${storeqx.look eq '1'}">
<form action="<%=basePath%>storepc_CategoryManageController/showShop3.do" name="Form" id="Form" method="post">
	<input type="hidden" name="store_id" value="${pd.store_id}" />
	<input type="hidden" name="paixu_w"  id="paixu_w" value="${empty pd.paixu_w?'0':pd.paixu_w }" />
	<ul>
		 <li class="padd_td">
		    <span class="anniu-s sort2" onclick="sortGoods('2',this)">大类排序</span>
		    <span class="anniu-s sort1" onclick="sortGoods('1',this)">销量排序</span>
 			<input style=" width: 20%;  height: 32px;     font-size: 16px;"   placeholder="检索商品名称，大类名称或小类名称" type="text" name="goods_name" id="goods_name" value="${pd.goods_name}"/> 
 			<span class="anniu-s" onclick="find()">查询</span>
 		</li>
		<li>
			<table cellspacing="0" cellpadding="0" style="width:100%;border:1px solid #a4a4a4;">
				<thead>
					<td>商品ID</td>
					<td>大类序号</td>
					<td>大类</td>
					<td>小类</td>
					<td>商品名称</td>
					<td>市场价</td>
					<td>零售价</td>
					<td>积分率</td>
					<td>销售数量</td>
					<td>商品状态</td>
				</thead>
				<tbody>
					<c:forEach items="${good_list}" var="slist" varStatus="v">
					<tr>
						<td>${v.index+1}</td>
						<td style="color:blue;">${slist.parent_sort}</td>
						<td>${slist.parent_name}</td>
						<td>${slist.son_name}</td>
						<td>${slist.goods_name}</td>
						<td>${slist.market_money}</td>
						<td>${slist.retail_money}</td>
						<td>${slist.integral_rate}</td>
						<td style="color:blue;">${slist.consumption_number}</td>
 						<td>
							<c:if test="${slist.goods_status eq '0'}">
								正常
							</c:if>
							<c:if test="${slist.goods_status eq '1'}">
								<span style="color:red;">停用</span>
							</c:if>
							<c:if test="${slist.goods_status eq '1'}">
								<span style="color:red;">已售完</span>
							</c:if>
	 					</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="fenye clf">
				<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
			<div>
		</li>
	</ul>
	</form>
	</c:if>
	<script type="text/javascript">
			//排序提交：大类=2，销售=1，创建时间排序(默认)=0
			$(function(){
				if("${pd.paixu_w}" == "1"){
					$(".sort2").addClass("clickcolor");
				}else if("${pd.paixu_w}" == "2"){
					$(".sort1").addClass("clickcolor");
				}else{
					$(".sort1").addClass("clickcolor");
					$(".sort2").addClass("clickcolor");
				}
			});
	
			
			function sortGoods(value,obj) {
				var nowsort=$("#paixu_w").val();
				$(".anniu-s").addClass("clickcolor");
				if(nowsort != value){
					$("#paixu_w").val(value);
					$(obj).removeClass("clickcolor");
				}else{
					$("#paixu_w").val("0");
				}
 				$("#Form").submit();
			}
 			
			
			//时间查询
			function find() {
				$("#goods_name").val($("#goods_name").val().trim());
				$("#Form").submit();
			}
	</script>
</body>
</html>