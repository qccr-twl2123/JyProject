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
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/main.js"></script>
</head>
<body>
<nav class="top">
	<a href="javascript:;" class="fr sc-edit" style="right: 20px;position:absolute;font-size: 0.9rem;">编辑</a>
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">收藏</div>
</nav>
 
	<div style="width:100%;" class="waiclass">
 		<c:forEach items="${storeList}" var="var">
 			<i class="gx" collect_id="${var.collect_id}" sk_shop="${var.new_store_id}"></i>
			<div class='business-list'>
				<a href="<%=basePath%>html_member/goStoreDetail.do?sk_shop=${var.new_store_id}" class="clearfix">
					<div class="index-tx clearfix">
						<i><img src="${var.pictrue_url }"  style="width:80px;height:80px;"></i>
						<p style="color:#ff271d;font-weight:bold">积分${var.integral_rate }</p>
 					</div>
					<div>
						<div class="business-xq"  >
							<div>${var.store_name }<span style="float:right;font-size:12px;color:#aeaeae;font-weight:normal;margin-right:7%">${var.distance }</span></div>
							<rating class="grst-detail rating">
								<svg class="eleme-svg svg-rating-star ng-scope" viewBox="10 0 50 10">
								    <g id="svg-rating-star">
								    <polygon class="svg-rating-star-active" points="4.732,0 6.194,2.963 9.463,3.438 7.098,5.745 7.656,9 4.732,7.463 1.807,9 2.366,5.745 0,3.438 3.269,2.963 "></polygon>
								    <polygon class="${var.comment_score >= '1'?'svg-rating-star-active':'svg-rating-star-inactive'}" points="14.732,0 16.194,2.963 19.463,3.438 17.098,5.745 17.656,9 14.732,7.463 11.807,9 12.366,5.745 10,3.438 13.269,2.963 "></polygon>
								    <polygon class="${var.comment_score >= '2'?'svg-rating-star-active':'svg-rating-star-inactive'}" points="24.732,0 26.194,2.963 29.463,3.438 27.098,5.745 27.656,9 24.732,7.463 21.807,9 22.365,5.745 20,3.438 23.27,2.963 "></polygon>
								    <polygon class="${var.comment_score >= '3'?'svg-rating-star-active':'svg-rating-star-inactive'}" points="34.732,0 36.194,2.963 39.463,3.438 37.098,5.745 37.656,9 34.732,7.463 31.807,9 32.365,5.745 30,3.438 33.27,2.963 "></polygon>
								    <polygon class="${var.comment_score >= '4'?'svg-rating-star-active':'svg-rating-star-inactive'}" points="44.732,0 46.194,2.963 49.463,3.438 47.098,5.745 47.656,9 44.732,7.463 41.807,9 42.365,5.745 40,3.438 43.27,2.963 "></polygon>
								    <polygon class="${var.comment_score >= '5'?'svg-rating-star-active':'svg-rating-star-inactive'}" points="54.732,0 56.194,2.963 59.463,3.438 57.098,5.745 57.656,9 54.732,7.463 51.807,9 52.365,5.745 50,3.438 53.27,2.963 "></polygon>
								    <polygon class="svg-rating-star-inactive" points="64.732,0 66.194,2.963 69.463,3.438 67.098,5.745 67.656,9 64.732,7.463 61.807,9 62.365,5.745 60,3.438 63.27,2.963 "></polygon>
								    <polygon class="svg-rating-star-inactive" points="74.732,0 76.194,2.963 79.463,3.438 77.098,5.745 77.656,9 74.732,7.463 71.807,9 72.365,5.745 70,3.438 73.27,2.963 "></polygon>
								    <polygon class="svg-rating-star-inactive" points="84.732,0 86.194,2.963 89.463,3.438 87.098,5.745 87.656,9 84.732,7.463 81.807,9 82.365,5.745 80,3.438 83.27,2.963 "></polygon>
								    <polygon class="svg-rating-star-inactive" points="94.732,0 96.194,2.963 99.463,3.438 97.098,5.745 97.656,9 94.732,7.462 91.807,9 92.365,5.745 90,3.438 93.27,2.963 "></polygon>
								    <polygon class="svg-rating-star-active" points="4.732,10 6.194,12.963 9.463,13.438 7.098,15.745 7.656,19 4.732,17.463 1.807,19 2.366,15.745 0,13.438 3.269,12.963 "></polygon>
								    <polygon class="svg-rating-star-active" points="14.732,10 16.194,12.963 19.463,13.438 17.098,15.745 17.656,19 14.732,17.463 11.807,19 12.366,15.745 10,13.438 13.269,12.963 "></polygon>
								    <polygon class="svg-rating-star-active" points="24.732,10 26.194,12.963 29.463,13.438 27.098,15.745 27.656,19 24.732,17.463 21.807,19 22.365,15.745 20,13.438 23.27,12.963 "></polygon>
								    <polygon class="svg-rating-star-active" points="34.732,10 36.194,12.963 39.463,13.438 37.098,15.745 37.656,19 34.732,17.463 31.807,19 32.365,15.745 30,13.438 33.27,12.963 "></polygon>
								    <polygon class="svg-rating-star-active" points="44.732,10 44.732,17.463 41.807,19 42.365,15.745 40,13.438 43.27,12.963 "></polygon>
								    <polygon class="svg-rating-star-inactive" points="44.732,10 46.195,12.963 49.464,13.438 47.099,15.745 47.657,19 44.732,17.463 "></polygon>
								    <polygon class="svg-rating-star-inactive" points="54.732,10 56.194,12.963 59.463,13.438 57.098,15.745 57.656,19 54.732,17.463 51.807,19 52.365,15.745 50,13.438 53.27,12.963 "></polygon>
								    <polygon class="svg-rating-star-inactive" points="64.732,10 66.194,12.963 69.463,13.438 67.098,15.745 67.656,19 64.732,17.463 61.807,19 62.365,15.745 60,13.438 63.27,12.963 "></polygon>
								    <polygon class="svg-rating-star-inactive" points="74.732,10 76.194,12.963 79.463,13.438 77.098,15.745 77.656,19 74.732,17.463 71.807,19 72.365,15.745 70,13.438 73.27,12.963 "></polygon>
								    <polygon class="svg-rating-star-inactive" points="84.732,10 86.194,12.963 89.463,13.438 87.098,15.745 87.656,19 84.732,17.463 81.807,19 82.365,15.745 80,13.438 83.27,12.963 "></polygon>
								    </g>
								   </svg>
							</rating>
							<span style="color:#ff4e00">${var.comment_score }分</span>
							<span style="color:#aeaeae;font-weight:normal;font-size:12px;"> 已售${var.transaction_times }单</span>
	 					</div>
	 					<c:forEach items="${var.marketlist }" var="mvar">
	 						<c:if test="${ mvar.marketing_type eq '2'}">
		            			<p class="yh"><span class="jian">立减</span>${mvar.grantrule }</p>
			            	</c:if>
			            	<c:if test="${ mvar.marketing_type eq '1'}">
			            		<p class="yh"><span class="zeng">满赠</span>${mvar.grantrule }</p>
			            	</c:if>
			            	<c:if test="${ mvar.marketing_type eq '3'}">
			            		<p class="yh"><span class="shi">时端</span>${mvar.grantrule }</p>
			            	</c:if>
			            	<c:if test="${ mvar.marketing_type eq '4'}">
			            		<p class="yh"><span class="song">立减</span>${mvar.grantrule }</p>
			            	</c:if>
			            	<c:if test="${ mvar.marketing_type eq '5'}">
			            		<p class="yh"><span class="gou">累计</span>${mvar.grantrule }</p>
			            	</c:if>
			            	<c:if test="${ mvar.marketing_type eq '6'}">
			            		<p class="yh"><span class="ji">积分</span>${mvar.grantrule }</p>
			            	</c:if>
			            	<c:if test="${ mvar.marketing_type eq '7'}">
			            		<p class="yh"><span class="zhe">折扣</span>${mvar.grantrule }</p>
			            	</c:if>
	 					</c:forEach>
 	 				</div>
				</a>
			</div>
			</c:forEach>
		</div>
 
 
<div class="sc-delet">
	<a class="recharge-sure" onclick="qxshoucang()">取消收藏</a>
</div>
<script type="text/javascript">
//取消收藏
function qxshoucang(){
	var collectstr="";
	$(".gx-on").each(function(){
		var collect_id=$(this).attr("collect_id");
		var sk_shop=$(this).attr("sk_shop");
		collectstr+=collect_id+"@"+sk_shop+",";
	});
	if(collectstr == ""){
		return;
	}
	//验证提交
	$.ajax({
	        type:"post",
	        url:'<%=basePath%>html_me/deleteCollocation.do', 
	  	 	 data:{
  	 		 	"collectstr":collectstr
	  	 	 },                
	        dataType:"json",
	        success: function(data){
	       	 	if(data.result == "1"){
	       	 		window.location.reload(); //刷新当前页面
	       	 	}else{
	       	 		alert(data.message);
	       	 	}
	        }
	});
}
 

</script>
</body>
</html>
