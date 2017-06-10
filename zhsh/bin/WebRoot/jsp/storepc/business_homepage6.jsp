 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>首页</title>
<meta charset="utf-8">


<!-- 导入的分页头文件的js和css -->
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<link rel="stylesheet" href="css/ace.min.css" />
<link href="css/bootstrap.min.css" rel="stylesheet" />

<link rel="stylesheet" href="css/storepc/business_homepage5.css">
<script src="js/jquery-1.8.0.min.js"></script>
<script src="js/storepc/business_homepage5.js"></script>
<script src="js/jquery-1.7.2.js"></script>
</head>
<body>
	<c:if test="${storeqx.look eq '1'}">
			<div class="parent">
			<div class="d1">
				<span class="d1_sp1">
					<a href="/zhsh/storepc_myleague/goOwnLeader.do?store_id=${storepd.store_id}"> 我是盟主</a> 
				</span>
				<span class="d1_sp1  d1_sp1_active"> 
				 	<a	href="/zhsh/storepc_myleague/goOtherLeader.do?store_id=${storepd.store_id}"> 我是盟友</a> 
				</span>
				<c:if test="${storeqx.add eq '1'}">
					<span class="d1_sp1">
					    <a href="/zhsh/storepc_myleague/goAddStoreUnion.do?store_id=${storepd.store_id}"> 新建联盟</a> 
					</span>
				</c:if>
			</div>
			<form action="storepc_myleague/goOtherLeader.do?store_id=${storepd.store_id}" method="post" name="Form" id="Form">
			<!-- 我是盟主页面 -->	
				<c:forEach items="${leaderList}" var="league" varStatus="vs">
 		 				<div class="d2">
							联盟${vs.index+1}.&nbsp;&nbsp;<span >${league.name}</span>
							<span class="d2_sp1"><a onclick="del('${league.store_union_id}','${storepd.store_id}')">退出联盟</a></span>
						</div>
 						<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>序号</td>
									<td>商家名称</td>
									<td>负责人</td>
									<td>手机号码</td>
									<td>加入时间</td>
									<!-- <td>操作</td> -->
								</tr>
							<c:forEach items="${league.getList}" var="league2" varStatus="league2Vs">
									<tr>
										<td>${league2Vs.index+1}</td>
 										<td>${league2.store_name}</td>
										<td>${league2.principal}</td>
										<td>${league2.phone}</td>
										<td>${league2.addtime}</td>
										<%-- <td>
											<c:if test="${storeqx.del eq '1'}"><a onclick="del('${league2.su_all_id}')">删除</a></c:if>
										</td> --%>
									</tr>
 							</c:forEach>
  						</table>
 				</c:forEach>
 				<br/>
 				<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
			</form>
		</div>
	</c:if>
	<script type="text/javascript">
	//退出联盟
	function del(value,storeid){
 			$.ajax({
			url:"storepc_myleague/delUnionByStore.do",
			type:"GET",
			dataType:"json",
			data:{"store_id":storeid,"store_union_id":value},
			success:function(data){
				$("#Form").submit();
			}
		});
	}
			

	
	</script>
</body>
</html>