<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>营销管理</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
		<link rel="stylesheet" href="css/ace.min.css" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/yingxiao7.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/zhihui/yingxiao7.js"></script>
    </head>
    <body>
     <c:if test="${qx.look eq '1'}">
       <form action="zhihui_sort_score/save.do" name="Form" id="Form" method="post">
	        <span class="yingxiao5_sp1">增加综合分值</span> 
	        <div class="dangan2_d1">
	         <span  class="zhifu1_sp1">商家</span>
	         <span id="goods"><input class="yingxiao5_ipt1" name="content" id="content"  placeholder="输入商家名称、商家编号查寻" /></span>
	          <span class="zhifu1_btn1  sousuo" id="search" >搜索</span>
	       </div>
	       <div class="dangan2_d1">
	        <input type="hidden" name="store_id" id="store_id" >
	         <span  class="zhifu1_sp1" >当前分值</span>
	         <input class="yingxiao5_ipt1" name="now_score" id="now_score" />
	         <span  class="zhifu1_sp1" >增加分值</span>
	         <input class="yingxiao5_ipt1"  name="add_score" id="add_score" />
	       </div>
	       <div class="dangan2_d1 mgtop">
	         <span  class="zhifu1_sp1">有效时间</span>
	         <input class="zhifu1_st1" type="text" name="starttime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间"/>
	         <span  class="zhifu1_sp1">起</span>
	         <input class="zhifu1_st1" type="text" name="endtime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间"/>
	         <span  class="zhifu1_sp1">止</span>
	       </div>
	       <div class="dangan2_d1">
	         <span  class="zhifu1_sp1" >费用</span> 
	         <input class="yingxiao5_ipt1"  name="spend_money"  onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"  />
	       </div>
	       <c:if test="${qx.add eq '1'}">
	          <span class="zhifu1_btn1" id="submit">确认提高</span>
	          </c:if>
        </form>
       </c:if>
     </body>
    <script type="text/javascript">
    //提交
    	$("#submit").click(function(){
 				$("Form").submit();   	
    	})
    	
    //查找
    	 $("#search").click(function(){
    	 	var str=$("#content").val();
    		$.ajax({
    			url:"zhihui_sort_score/findName.do",
    			data:"content="+str,
    			type:"post",
    			dataType:"json",
    			success:function(data){
	    			if(data.store_id == null){
	    				alert("没有该商店");
 	    			}else{
 	        			$("#store_id").val(data.store_id);
	        			$("#now_score").val(data.complex_score);
 	    			}
     			},
    		})
    	}) 
    	
    </script>
</html>