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
	<title>管理我的营销</title>
	 <base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/yxkt_yxgl.css">
	<script src="js/jquery-1.8.0.min.js"></script>
</head>
<body>
 <c:if test="${storeqx.look eq '1'}">
 <form action="storepc_marketing/list.do?store_id=${storepd.store_id}" method="post" name="Form" id="Form" style="white-space: nowrap;">
	<ul>
		<li style="line-height:2.5;">
			<span class="col-9">当前开通的营销方案</span>
			<span class="col-red"style="font-size:87.5%;">（说明：同一时段内最多可以同时发布五种营销方案）</span>
		</li>
		<li style="line-height:2.5;">
			<table border="1px solid #666;" cellspacing="0" cellpadding="0" style="width:100%;">
				<thead>
					<td>序号</td>
					<td>营销项目</td>
					<td>发布规则</td>
					<td>有效日期</td>
					<td>排序</td>
					<td>操作</td>
				</thead>
				<tbody>
				<c:forEach items="${varList}" var="var" varStatus="vs">
	                <tr>
	                    <td class = "marketingid" id = "marketingid" value = "${var.store_marketing_id }">${vs.index + 1}</td>
	                    <td >
	                    	<c:if test="${var.marketing_type eq '0'}"> 红包</c:if>
	                    	<c:if test="${var.marketing_type eq '1'}"> 满赠</c:if>
	                    	<c:if test="${var.marketing_type eq '2'}"> 满减</c:if>
	                    	<c:if test="${var.marketing_type eq '3'}"> 时段营销</c:if>
	                    	<c:if test="${var.marketing_type eq '4'}"> 买n减1</c:if>
	                    	<c:if test="${var.marketing_type eq '5'}"> 累计购买次数/总金额</c:if>
	                    	<c:if test="${var.marketing_type eq '6'}"> 积分方式</c:if>
	                    	<c:if test="${var.marketing_type eq '7'}"> 折扣</c:if>
	                    	<input class = "marketing_type" hidden="" value="${var.marketing_type}"/>
	                    </td>
	                    <td>${var.grantrule }<input value="${var.grantrule }" class = "grantrule" hidden=""/></td>
 	                    <td>${var.endtime }之前</td>
 	                    <td><input type="text" style="width: 13px" id = "${var.store_marketing_id }sort" value ="${var.sort}"/>
 	                    	<c:if test="${storeqx.edit eq '1'}">
		                    	 <span class="col-blue bor-b" onclick="upd('${var.store_marketing_id }')">修改</span>
	                    	</c:if>
 	                    </td>
 	                    <td class="blue">
	                    	<c:if test="${storeqx.edit eq '1' and var.marketing_type ne '6'}">
		                    	<c:if test="${var.open_status eq '0'}" ><span class="col-blue bor-b"  id = "status1"  onclick="qiyong('${var.store_marketing_id }')">点击启用</span></c:if>
		                    	<c:if test="${var.open_status eq '1'}" ><span  class="col-blue bor-b" id = "status2"  onclick="guanbi('${var.store_marketing_id }')">点击关闭</span></c:if>
	                    	</c:if>
 	 	                    <c:if test="${storeqx.delete eq '1' and var.marketing_type ne '6'}">
		                    	&nbsp;<span class="col-blue bor-b" onclick="del('${var.store_marketing_id }','${var.marketing_id}','${var.marketing_type}')">点击删除</span>
	                    	</c:if>
	                    </td>
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
			//启用
			function qiyong(store_marketing_id){
   				$.ajax({
						type:"post",
						url:"storepc_marketing/edit.do",
						data:"store_marketing_id="+store_marketing_id+"&open_status=1&store_id="+"${storepd.store_id}",
						success:function(data){
							if(data.result == "01"){
								window.location.reload(); 
							}else{
								alert(data.message);
							}
						}
				});  
 			}
			
			//关闭
			function guanbi(id){
 				$.ajax({
						type:"post",
						url:"storepc_marketing/edit.do",
						data:"store_marketing_id="+id+"&open_status=0&store_id="+"${storepd.store_id}",
						success:function(data){
							window.location.reload(); 
						}
				});
 			}
			
			//删除我的营销
			 function del(id,marketing_id,marketing_type){
  					$.ajax({
						type:"post",
						url:"storepc_marketing/delete.do",
						data:"store_marketing_id="+id+"&marketing_id="+marketing_id+"&marketing_type="+marketing_type,
						success:function(data){
							window.location.reload();
						}
					});
 			} 
			//修改排序
			 function upd(id){
			 	var sort = $("#"+id+"sort").val().trim();
  					$.ajax({
						type:"post",
						url:"storepc_marketing/updSort.do",
						data:"store_marketing_id="+id+"&store_id="+"${storepd.store_id}"+"&sort="+sort,
						success:function(data){
							if(data.result=="0"){
								alert("修改失败，请联系管理员！");
							}else if(data.result=="1"){
								window.location.reload(); //刷新当前页面
							}else if(data.result=="2"){
								alert("该排序已存在！");
							}
						}
					});
 			} 
		
		
		</script>
</body>
</html>