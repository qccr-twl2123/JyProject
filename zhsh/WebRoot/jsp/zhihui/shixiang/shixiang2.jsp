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
        <title>事项预沟通</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
		<link rel="stylesheet" href="css/ace.min.css" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/shixiang2.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/shixiang2.js"></script>
        <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
     <c:if test="${qx.look eq '1'}">
    <form action="zhihui_send_notifications/list.do" nam="Form" id="Form" method="post">
       <div class="dangan2_d1">
         <span  class="zhifu1_sp1">区&nbsp;&nbsp;&nbsp;&nbsp;域 </span>
         <select class="dangan2_d1_st1" name="province_id" id="province_id" onchange="searchfind();">
          <option value="">请选择省</option>
          <c:forEach items="${provincelist}" var="var" varStatus="vs">
		  <option value="${var.pcd_id}">${var.name}</option>
	   	  </c:forEach>
           </select>
          <select class="dangan2_d1_st1" id="city_id" name="city_id" onchange="searcharea();">
              <option value="">请选择市</option>
           </select>
          <select class="dangan2_d1_st1" id="area_id" name="area_id">
              <option value="">请选择区</option>
           </select>
	        <span  class="zhifu1_sp1">发送时间</span>
	        <input type="text" class="dangan2_d1_st1" name="send_startdate" value="${pd.send_startdate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间" />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span  > 至</span> 
          <input type="text" class="dangan2_d1_st1" name="send_enddate" value="${pd.send_enddate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  placeholder="结束时间"/>
          <c:if test="${qx.add eq '1'}">
       		<a class="middle_a" href="zhihui_send_notifications/goAdd.do"  target="ifra">
           		<span class="zhifu1_btn1">发送通知</span>
            </a>
            </c:if>
       </div>
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr >
                <td>序号</td>
                <td>用户类型</td>
                <td>省</td>
                <td>市</td>
                <td>区/县</td>
                <td>内容</td>
                <td>发送时间</td>  
                <td>审核状态</td>  
              </tr> 
             <c:forEach items="${varList}" var="var" varStatus="vs" >
	              <tr >
	                <td>${vs.index+1}</td>
	                <td>
		                <c:if test="${var.user_type eq '1'}">子公司</c:if>
		                <c:if test="${var.user_type eq '2'}">服务商</c:if>
		                <c:if test="${var.user_type eq '3'}">业务员</c:if>
		                <c:if test="${var.user_type eq '4'}">商家</c:if>
		                <c:if test="${var.user_type eq '5'}">会员</c:if>
	                </td>
	                <td>${var.province_name }</td>
	                <td>${var.city_name }</td>
	                <td>${var.area_name }</td>
	                <td>${var.content}</td>
	                <td>${var.send_time}</td> 
	                <td>
	                	<c:if test="${var.review_status eq '0'}">待审核  
		                	<c:if test="${qx.edit eq '1'}">
			                		&nbsp;&nbsp;&nbsp;<a id = "sh" href="zhihui_send_notifications/toExamine.do?review_status=1&send_notifications_id=${var.send_notifications_id }">通过</a>
			                		&nbsp;&nbsp;&nbsp;<a id = "bh" href="zhihui_send_notifications/toExamine.do?review_status=99&send_notifications_id=${var.send_notifications_id }">驳回</a>
			                </c:if>
		                </c:if>
		                <c:if test="${var.review_status eq '1'}">通过审核</c:if>
		                <c:if test="${var.review_status eq '99'}">审核不通过</c:if>
	                </td> 
	              </tr> 
             </c:forEach> 
          </table>
       </div>
       <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
       </form>
       </c:if>
    </body>
    <script type="text/javascript">
    //获取城市
		function searchfind(){
			var str=$("#province_id option:selected").val();//获取被选中的value值
			$.ajax({
				  url: '<%=path%>/zhihui_subsidiary/citylist.do',
				  data:"province_id="+str,
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.citylist;
 					  	$("#city_id option").remove();
 					  	$("#city_id").append("<option value=''>请选择市</option>");
					  	$("#area_id").append("<option  value=''>请选择区</option>");
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$("#city_id").append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
						  	}
					  	} 
				  },
				  error:function(a){
				  	alert("异常");
				  }
			});
		}
			
		//获取区域
		function searcharea(){
			var str=$("#city_id option:selected").val();//获取被选中的value值
			$.ajax({
				  url: '<%=path%>/zhihui_subsidiary/arealist.do',
				  data:"city_id="+str,
				  type:"post",
				  dataType:"json",
 				  success:function(data){
					  	var list=data.arealist;
					  	$("#area_id option").remove();
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$("#area_id").append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
						  	}
				  		}
				  },
				  error:function(a){
				  alert("异常");
				  }
			});
		}
		//---------------------------
		//检索
		function sarech(){
 			$("#Form").submit();
		}
		
    </script>
</html>