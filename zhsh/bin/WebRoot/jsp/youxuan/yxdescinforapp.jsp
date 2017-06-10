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
	<base href="<%=basePath%>">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
	<link rel="stylesheet" href="css/youxuan/normalize.min.css">
	<title>图文详情/参数说明</title>
	<style>
			html,body,h1,h2,h3,h4,h5,h6,p{
			margin: 0;
			border: 0;
			padding: 0;
		}
		body,html{
			height: 100%;
 			-webkit-overflow-scrolling : touch;
			
		}
		body{
			font-family: Arial, "微软雅黑", sans-serif;
			overflow: scroll;
		}
		ul,li{
			list-style: none;
		}
		img{
			border: 0;
			line-height: 0;
			margin: 0;
			width: 100%;
		}
			.select{
		width: 100%;
		background: #fff;
		border-bottom: 1px solid #666;
		overflow:hidden;
		position:fixed;
		top:0;
	}
	section {
    padding-top: 44px;
    display: block;
    -webkit-overflow-scrolling: touch;
}
		section img{
			display: block;
			width: 100%;

		}
		.list{

		}
		.list_box{
			height: 100%;
			display: block;
			padding: 1rem 4rem;

		}
		table,th,td{border-collapse: collapse;border-spacing: 0;}
		table,tr{
			width: 100%;
		}
		tr td:nth-child(1){
			width: 30%;
			height: 15px;
		}
		tr td:nth-child(2){
			width: 80%;
			height: 15px;
		}
		td{
			padding: 2% 2%;
			box-sizing:border-box;
			font-size: 1.5rem;
			border: 1px solid #aaa;
			background: #fff;
	
		}
		 
		.xq{
			width: 50%;
			height: 44px;
			line-height: 44px;
			text-align: center;
			color: #666;
			float: left;
			font-size: 1.8rem;
			background: #fff;
		}
		.xqtu{
			color: #FFCF7C;
		}
	</style>
</head>
<body>
<div class="select">
		<div class="xqtu xq">图文详情</div>
		<div class="xqlist xq">产品参数</div>
	</div>
<section>
		<div class="imgbox">
			<c:forEach items="${biglist }" var="var">
				<img src="${var}"  >
			</c:forEach>
		</div>
		

		<div class="list" style="display: none;">
		<div class="list_box">
			<table>
			<c:forEach items="${goodsjjlist}" var="jjvar">
						<tr>
							<td>${jjvar.title}</td>
							<td>${jjvar.text}</td>
						</tr>	
			</c:forEach>
			
		</table>
		</div>
		
	</div>
</section>
	
</body>
<script src="js/jquery-2.1.4.min.js"></script>
<script>
$(".xqtu").click(function(){
	$(".xqtu").css({"color":"#FFCF7C"})
	$(".xqlist").css({"color":"#666"})
	$(".imgbox").css({"display":"block"})
	$(".list").css({"display":"none"})

})
$(".xqlist").click(function(){
	$(".xqlist").css({"color":"#FFCF7C"})
	$(".xqtu").css({"color":"#666"})
	$(".list").css({"display":"block"})
	$(".imgbox").css({"display":"none"})

})
</script>
</html>