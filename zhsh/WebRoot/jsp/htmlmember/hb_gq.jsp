<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
 	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/style.css">
	<link rel="stylesheet" href="<%=basePath%>css/htmlmember/styles.css" type="text/css">
	<script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
</head>
<body style="background:#ededed;">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">过期红包</div>
</nav>
<div class="hb-list hb-gq-list">
	<ul id="redList">
		<%-- <c:forEach items="${varList}" var="var">
			<li>
				<a href="<%=basePath%>html_member/goStoreDetail.do?store_id=${var.set_id}&member_id=${pd.member_id}" >
					<span  class="hb-list-left fl">
						<p>￥<span>${var.redpackage_money }</span></p>
						<p>红包</p>
					</span>
					<div class="hb-list-right fl">
						<span>
							<p>[商家红包]${store_name }</p>
							<p><i class="hb-list-time"></i>有效期至${var.enddate }</p>
							<p><i class="hb-list-coupon"></i>${var.redpackage_content }  
								<c:if test="${var.redpackage_type eq '1' }">无条件使用</c:if>
								<c:if test="${var.redpackage_type eq '2' }">满XX元使用</c:if>
								<c:if test="${var.redpackage_type eq '3' }">首单立减</c:if>
							</p>
						</span>
					</div>
				</a>
			</li>
		</c:forEach>
		  <li>
			<span  class="hb-list-left fl">
				<p>￥<span>8</span></p>
				<p>红包</p>
			</span>
			<div class="hb-list-right fl">
				<span>
					<p>[商家红包]重庆小面武汉路店</p>
					<p><i class="hb-list-time"></i>有效期至2016-01-23</p>
					<p><i class="hb-list-coupon"></i>消费满16元可用，每单限用一张</p>
				</span>
			</div>
		</li> --%>
	</ul>
</div>

<p class="hb-gq-footer">没有更多可用劵了</p>
<script type="text/javascript">
 $(function(){
		$.ajax({
				type:"post",
				url:'<%=basePath%>app_member_redpackets/listOver.do',
				data:{
					member_id:"${pd.member_id}" 
				},
				success:function(data){
					 var redList=data.data;
					 for(var i=0;i<redList.length;i++){
						 var onlyred=redList[i];
						 var url="<%=basePath%>html_member/goStoreDetail.do?store_id="+onlyred.set_id+"&member_id=${pd.member_id}";
 						 var str="<li><a href='"+url+"'>"+
									"<span  class='hb-list-left fl'>"+
									"<p><span>"+onlyred.redpackage_money+"</span>"+onlyred.redpackage_type_name+"</p>"+
										"<p>红包</p>"+
									"</span>"+
									"<div class='hb-list-right fl'>"+
 										"<span>"+
											"<p>[商家红包]"+onlyred.store_name+"</p>"+
											"<p><i class='hb-list-time'></i>有效期至"+onlyred.enddate+"</p>"+
											"<p><i class='hb-list-coupon'></i>"+onlyred.redpackage_content+"</p>"+
										"</span>"+
									"</div>"+
								"</li>";
						 $("#redList").append(str);
 					 }
				}
		});
	});
 </script>
</body>
</html>
