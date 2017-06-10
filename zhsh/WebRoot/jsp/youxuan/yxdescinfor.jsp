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
	<title>图文详情/参数说明</title>
	<base href="<%=basePath%>">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
	<link rel="stylesheet" href="css/youxuan/normalize.min.css">
	<link rel="stylesheet" href="css/youxuan/swiper.min.css">
	<link rel="stylesheet" href="css/youxuan/fonts/iconfont.css">
	<link rel="stylesheet" href="css/youxuan/spgm.css">
	<link rel="stylesheet" href="css/youxuan/tit.css">
 	<script src="js/jquery-1.8.0.min.js"></script>
 	<style>
 		
 		::-webkit-scrollbar  
		{  
		    width: 6px;  
		    height: 6px;  
		    background-color: #f6f6f6;  
		}  
		::-webkit-scrollbar-track  
		{  
		    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);  
		    border-radius: 10px;  
		    background-color: #f6f6f6;  
		}  
		::-webkit-scrollbar-thumb  
		{  
		    border-radius: 10px;  
		    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);  
		    background-color: #aaa;  
		}  
	</style>
 </head>
<body>
<!-- 回到顶部按钮 -->
	 <c:if test="${pd.type eq '2'}">
		<!-- 顶部条 -->
		<header>
			<div class="goback">
				<a href="javascript:window.history.back();" >
					<img src="https://www.jiuyuvip.com/img/fanhui.png" alt="">
				</a>
			</div>
			<div class="title">
				 <div class="djs">
		        <h6>距离本次结束还剩：</h6>
		        <h6 class="jieshu"></h6>
		    	</div>
			</div>
	 	</header>
	 	<div class="select">
			<div class="xqtu xq chushi">图文详情</div>
			<div class="xqlist xq ">产品参数</div>
		</div>
	 </c:if>
	<section  id="tw" style="-webkit-overflow-scrolling : touch;">
 			<c:forEach items="${biglist }" var="var">
				<img src="${var}"  >
			</c:forEach>
  	</section>
	<div class="list"  id="cp">
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
</body>
<script src="js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">



	//倒计时
  	window.endtimestamp=parseInt("${pd.endtimestamp}");
	window.t1 = setInterval(leftTimer, 1000);//1秒执行一次
 	function leftTimer(){ 
		 var leftTime = endtimestamp - (new Date()).getTime(); //计算剩余的毫秒数 
		 var days = parseInt(leftTime / 1000 / 60 / 60 / 24 , 10); //计算剩余的天数 
		 var hours = parseInt(leftTime / 1000 / 60 / 60 % 24 , 10); //计算剩余的小时 
		 var minutes = parseInt(leftTime / 1000 / 60 % 60, 10);//计算剩余的分钟 
		 var seconds = parseInt(leftTime / 1000 % 60, 10);//计算剩余的秒数 
		 days = checkTime(days); 
		 hours = checkTime(hours); 
		 minutes = checkTime(minutes); 
		 seconds = checkTime(seconds); 
		 if(days == 0 && hours ==0 && minutes== 0 && seconds==0 ){
			 clearInterval(t1);
		 }
		 $(".jieshu").html("<span class='day'>"+ days+"</span>天<span class='hour'>" + hours+"</span>小时<span class='min'>" + minutes+"</span>分<span class='sec'>"+seconds+"</span>秒") ;
	} 
 	function checkTime(i){ //将0-9的数字前面加上0，例1变为01 
		 if(i<10) 
		 { 
		  i = "0" + i; 
		 } 
		 return i; 
	} 
 	//刚进来判断展示哪一个
 	if("${pd.show_type}" == "1"){
 		$("#tw").show();	
 		$("#cp").hide();	
 	}else{
 		$("#tw").hide();	
 		$("#cp").show();	
 	}
 	// 图文切换	
 	$(".xqtu").click(function(){
 		$(".xqtu").css({"color":"#FFCF7C"})
 		$(".xqlist").css({"color":"#666"})
 		$("section").css({"display":"block"})
 		$(".list").css({"display":"none"})
 		scroll=$(".section")
 	});
 	$(".xqlist").click(function(){
 		$(".xqlist").css({"color":"#FFCF7C"})
 		$(".xqtu").css({"color":"#666"})
 		$(".list").css({"display":"block"})
 		$("section").css({"display":"none"})
 		scroll=$(".list")
 	});
 	//设置高度
 	if("${pd.type}" == "1"){
 		$("#tw").css("height","100%");
 	}


 	var hei=$(window).height()-$("header").outerHeight()-$(".select").outerHeight()
	$("section").height(hei)
	$(".list").height(hei)
</script>
</html>
 