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
	<title>人脉圈</title>
 	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/style.css">
	<link rel="stylesheet" href="<%=basePath%>css/htmlmember/styles.css" type="text/css">
	<script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript">
	if(true){
  		//我的好友列表
		$.ajax({
		        type:"post",
		        url:'<%=basePath%>app_friend/myFriendList.do', 
		  	 	 data:{
 		 		 	"member_id":"${pd.member_id}" 
		  	 	 },                
		        dataType:"json",
		        success: function(data){
		        	if(data.result=="1"){
		        		var friend=data.data.user;
		        		var image_url=friend.image_url;
 						if(friend.image_url == ""){
 							image_url="<%=basePath%>img/moren.png";
 						}
						$("#tuijianmefriend").append("<li><a href=''><span class='rm-tx'><img src='"+image_url+"'></span>"+friend.name+"</a></li>");
		        		//我的好友集合
		        		var friendList=data.data.endList;
		        		var n=friendList.length;
		        		for (var i = 0; i < friendList.length ; i++) {
		        			//if(friendList[i].name != null || i<4){
		        				for(var key in friendList[i]) { 
		                            //alert("键：" + key + ",值  姓名："+ friendList[i][key].name);
 		                            if(friendList[i][key].length != 0){
		                            	$("#myFriendList").append("<p class='tj-title'>"+key+"</p>");
			                            for (var j = 0; j < friendList[i][key].length; j++) {
			                            	//alert(friendList[i][key].length);
			                            	//alert(friendList[i][key][j].id);
 			                            	  var str="<article class='rm-list tj-list'>"+
				                            				"<ul>"+
				                      						"<li><a onclick='' type='"+friendList[i][key][j].type+"' id='"+friendList[i][key][j].id+"'><span class='rm-tx'><img src='"+ friendList[i][key][j].image_url+"'></span>"+friendList[i][key][j].name+"</a></li>"+
				                      						"</ul>"+
				                      				"</article>";
				                              $("#myFriendList").append(str);
 										}
		                            }
 		                         }
		        			//}
 						}
		        		$("#myFriendList").append("<br><br><br>");
		        	}
 			    }
      });
	}
	</script>
</head>
<body style="background:#ededed;">
<nav class="top">
	<!-- <span class="fr top-right">+
		<div class="top-right-hide" style="display:none;">
			<i class="top-san"></i>
			<ul>
				<li><a href="###"><i class="icon1"></i>抢附近商家红包</a></li>
				<li><a href="###"><i class="icon2"></i>推荐好友注册</a></li>
				<li><a href="###"><i class="icon3"></i>扫一扫优惠买单</a></li>
			</ul>
		</div>
	</span>
	<span class="fr rm-serach"><img src="<%=basePath%>imgmem/rm_serach_03.png"></span> -->
	<div style="text-align:center;line-height:40px;color:#fff">人脉圈</div>
</nav>

<article class="rm-list">
	<ul>
		<li><a href="<%=basePath%>html_member/gouRenMai.do?type=1"><i class="rm-icon1"></i>我的人脉</a></li>
		<%-- <li><a href="<%=basePath%>html_member/gouRenMai.do?type=3"><i class="rm-icon2"></i>推荐手机联系人</a></li>
		<li><a href="<%=basePath%>html_member/gouRenMai.do?type=4"><i class="rm-icon3"></i>群聊</a></li> --%>
		<li><a href="<%=basePath%>FileSave/zhihuiPC/business_erweima2.html"><i class="rm-icon4"></i>找商家发红包</a></li>
		<%-- <li><a href="<%=basePath%>html_member/gouRenMai.do?type=5"><i class="rm-icon5"></i>通知、客服、建议与反馈</a></li> --%>
		<%-- <li><a href="<%=basePath%>html_member/gouRenMai.do?type=6"><i class="rm-icon6"></i>最新聊天记录</a></li> --%>
	</ul>
</article>
<p class="tj-title">推荐我来注册的朋友</p>
<article class="rm-list tj-list">
	<ul id="tuijianmefriend"></ul>
</article>
<!-- 我的好友 -->
<div id="myFriendList">
	<%-- <p class="tj-title">A</p>
	<article class="rm-list tj-list">
		<ul>
			<li><a href="###"><span class="rm-tx"><img src="<%=basePath%>imgmem/20130518113434876(1).jpg"></span>江山妩媚<b class="z-arrow"></b></a></li>
		</ul>
	</article> --%>
</div>
<footer class="footerdi">
	<ul>
		<li class="f_whole">
			<a href="<%=basePath%>html_member/gouShouYe.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}">
				<i></i>
				首页
			</a>
		</li>
		<li class="f_jiexiao">
			<a href="<%=basePath%>html_member/goMyYouXuan.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}">
				<i></i>
				优选爆品
			</a>
		</li>
		<li class="f_car">
			<a style=" color: #e90000; " href="<%=basePath%>html_member/gouRenMai.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}">
				<i  class="cur"></i>
				人脉圈
			</a>
		</li>
		<li class="f_personal">
 			<a href="<%=basePath%>html_me/goMe.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}">
				<i></i>
				我的
			</a>
		</li>
	</ul>
</footer>
</body>
</html>
