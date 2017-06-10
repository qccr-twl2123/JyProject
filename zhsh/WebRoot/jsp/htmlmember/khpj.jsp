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
	<script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/htmlmember/comment.js"></script>
	<script src="<%=basePath%>js/jquery.form.js"></script>
</head>

<body style="background:#dedede;">
<nav class="top">
	<!-- <a href="###" class="fr" style="margin-right:5px;">魅力值说明</a> -->
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">评价</div>
</nav>
<form action="<%=basePath%>app_comment/addComment.do" method="post" enctype="multipart/form-data" name="Form" id="Form" >
<input type="hidden" name="store_id" value="${pd.store_id}"/>
<input type="hidden" name="member_id" value="${pd.member_id}"/>
<input type="hidden" name="order_id" value="${pd.order_id}"/>
<div class="khpj-div clearfix">
	<span class="fl">总体评价</span>
	<div class="fl" id="rate-comm-1" class="rate-comm"></div>
</div>
<textarea class="khpj-text" placeholder="写点评价吧，对其他小伙伴帮助很大哦！" name="content" id="content" maxlength="140"></textarea>
<span style="margin-left: 1800px; color: red;">字数限制为140字</span>
<div class="khpj-div">上传照片</div>
<div class="photo">
	<div class="photo-overflow">
		<ul id="showimage" name = "img">
			<!-- <li><img src="/imgmem/20130518113434876(1).jpg"></li>
			<li><img src="/imgmem/20130518113434876(1).jpg"></li>
			<li><img src="/imgmem/20130518113434876(1).jpg"></li> -->
		</ul>
	</div>
	<a href="javascript:;" class="a-upload" >
	    <input type="file" name="image_url" id="file" multiple="multiple" onchange="showImage(this)">+
	</a>
</div>
 <span class="recharge-sure" onclick="sureAjax()">提交</span>
 </form>
<script type="text/javascript">
//图片展示
function showImage(obj){
	var docObj = document.getElementById("file");
	var fileList = docObj.files;
	var num = document.getElementsByTagName("img").length;
 	if(num > 2){
 		alert('最多选择3张');
        document.getElementById("file").value="";
    	return false;
 	}else{
 		//$("#showimage").empty();
 		for (var i = 0; i < fileList.length; i++) {
 	  		$("#showimage").append("<li><img src='"+window.URL.createObjectURL(fileList[i])+"' /></li>");
 		}	
 	}
  }







//表单提交
function sureAjax(){
	//获取星级
	if($(".rater-star-result") == null){
		alert("请先评论星级");
		return;
	}
	if($("#content").val() == ""){
		alert("内容不能为空");
		return;
	}
	var star_number=$(".rater-star-result").html().trim();
	star_number=star_number.substring(0,1);
 	$("#Form").ajaxSubmit({  
	  	url : '<%=basePath%>app_comment/addComment.do',
        type: "POST",//提交类型  
      	data:{
      		"star_number":star_number
      	},
      	dataType:"json",
   		success:function(data){
   			if(data.result=="1"){
   				window.location.href="<%=basePath%>html_me/goMe.do?type=7";
   			}else{
   				alert(data.message);
   			}
		} 
 	});
}

</script>
</body>
</html>

