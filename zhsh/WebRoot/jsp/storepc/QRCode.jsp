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
<title>生成桌号二维码</title>

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<script src="js/handler-chinese.js"></script>
<script src="js/jquery-1.7.2.js"></script>
<script src="js/jquery-1.8.0.min.js"></script>
<script src="js/jquery.qrcode.min.js"></script>
<script type="text/javascript">
    //生成二维码
    function create(obj){
    	var value=$(obj).prev().val();
    	if(value == ""){
    		alert("桌号不能为空");
     		return;
    	}
    	$(obj).parent().parent().next().empty();//清空
    	//判断当前桌号是否已绑定被的操作员
    	//获取提货信息
			$.ajax({
			url:"storepcOperator_file/isdesk_no.do",
			type:"post",
			dataType:"json",
			data:{"store_operator_id":"${pd.store_operator_id}","store_id":"${pd.store_id}","desk_no":value},
			success:function(data){
				 if(data.result == "0"){
					 alert(data.message);
					 $(obj).prev().val("");
					 return;
				 }else{
					 //获取用户输入的内容并保存
				       /*  var store_name = "${pd.store_name}";
				        var store_id =  "${pd.store_id}";
				        var desk_no = $("#desk_no").val();
				        var content=store_id+"@"+desk_no;
				        //中文转码
				        var str = utf16to8(content);
				        //生成二维码：商家ID以及zhuohao.生成的二维码下载，图片尺寸为5*6CM；
 				        jQuery($(obj).parent().parent().next()).qrcode({ width: 100, height: 100, text: str }); 
 				         */ 
 				         var url=data.data;
 						 var str="<a target='_bank' href='../storepcOperator_file/downPic.do?image_url="+url+"'>"+ 
 								 "<img src='"+url+"' width='190px' height='190px'>"+
 							      "</a>";
 						  $(obj).parent().parent().next().append(str);
				 }
			}
		});
     }
    //新增桌号
    function adddesk(){
    	var str="<div id='fist'><span>桌号：<input name='desk_no' id='desk_no' value='' readonly='readonly'/>"+
				"	<input type='button' value='产生二维码' onclick='create(this)' /></span>"+
				"</div>"+
				"<div id='output'></div>";
		$(".adddn").append(str);
    }
    </script>
    <style type="text/css">
    #fist{float: left;	margin: 0 auto;text-align: center;height:190px;margin-right:10px;width:350px;}
    #output{float: left; margin: 0 auto;text-align: center;height:200px;margin-right:10px;width:200px;}
    .adddn{width:100%;}
    .hands{  cursor: pointer; }
    </style>
 </head>
 <body>
 	<h2 align="center">扫一扫二维码&nbsp;&nbsp;&nbsp;&nbsp;
	 	<!-- <span>
	 		<a class="hands" onclick="adddesk()" style="color:blue;">新增桌号</a>
	 	</span>   -->
 	</h2>
	<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<div class="adddn">
		<c:if test="${!empty varList}">
			<c:forEach items="${varList }" var="var" varStatus="vs">
				<div id="fist">
					<span>桌号：<input name="desk_no" id="desk_no" value="${var.desk_no}" readonly="readonly"/>
					<input type="button" value="产生二维码" onclick="create(this)"  /></span>
				</div>
				<div id="output"></div>
			</c:forEach>
	 	</c:if>
		<c:if test="${empty varList}">
			<div id="fist">
				<span>桌号：<input name="desk_no" id="desk_no" value=""/>
				<input type="button" value="产生二维码" onclick="create(this)" /></span>
			</div>
			<div id="output"></div>
		</c:if>
 	</div>
 </body>
</html>
