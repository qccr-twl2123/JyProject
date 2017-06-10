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
        <link rel="stylesheet" href="css/zhihui/shixiang3.css">
        <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/shixiang3.js"></script>
        <style type="text/css">
        	input[type=checkbox]{
			    opacity: 1;
			    position: initial;
 			}
        </style>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihui_send_notifications/save.do" name="Form" id="Form" method="post">
	       <div class="yingxiao8_d1">
	        <span class="yingxiao8_d1_sp1">用户类型</span>
	         <span class="shishiang3_sp1">
	           <label>
	             <input class="shixiang3_ipt1" type="checkbox" value="1" name="user_type1" />
	             <span class="shishiang3_sp1_sp">子公司</span>
	           </label>
	         </span>
	         <span class="shishiang3_sp1">
	           <label>
	             <input class="shixiang3_ipt1" type="checkbox" value="2" name="user_type2" />
	             <span class="shishiang3_sp1_sp">服务商</span>
	           </label>
	         </span>
	         <span class="shishiang3_sp1">
	           <label>
	             <input class="shixiang3_ipt1" type="checkbox" value="3" name="user_type3" />
	             <span class="shishiang3_sp1_sp">业务员</span>
	           </label>
	         </span>
	         <span class="shishiang3_sp1">
	           <label>
	             <input class="shixiang3_ipt1" type="checkbox" value="4" name="user_type4"/>
	             <span class="shishiang3_sp1_sp">商家</span>
	           </label>
	         </span>
	         <span class="shishiang3_sp1">
	           <label>
	             <input class="shixiang3_ipt1" type="checkbox" value="5" name="user_type5" />
	             <span class="shishiang3_sp1_sp">会员</span>
	           </label>
	         </span>
	      </div>
	      
	      <!-- -------- -->
	     <div class="yingxiao8_d1">
	      <span class="yingxiao8_d1_sp1">抄送</span>
	     	<span class="shishiang3_sp1">
	           <label>
	             <input class="shixiang3_ipt1" type="checkbox" value="1" name="user_type1" />
	             <span class="shishiang3_sp1_sp">子公司</span>
	           </label>
	         </span>
	         <span class="shishiang3_sp1">
	           <label>
	             <input class="shixiang3_ipt1" type="checkbox" value="2" name="user_type2" />
	             <span class="shishiang3_sp1_sp">服务商</span>
	           </label>
	         </span>
	         <span class="shishiang3_sp1">
	           <label>
	             <input class="shixiang3_ipt1" type="checkbox" value="3" name="user_type3" />
	             <span class="shishiang3_sp1_sp">业务员</span>
	           </label>
	         </span>
	         <span class="shishiang3_sp1">
	           <label>
	             <input class="shixiang3_ipt1" type="checkbox" value="4" name="user_type4"/>
	             <span class="shishiang3_sp1_sp">商家</span>
	           </label>
	         </span>
	         <span class="shishiang3_sp1">
	           <label>
	             <input class="shixiang3_ipt1" type="checkbox" value="5" name="user_type5" />
	             <span class="shishiang3_sp1_sp">会员</span>
	           </label>
	         </span>
	     </div>
	     <!-- -------- -->
	     
	      <div class="yingxiao8_d1">
	        <span class="yingxiao8_d1_sp1">用户区域</span>
	        <select class="yingxiao8_d1_st1 st2" name="province_id" id="province_id" onchange="searchfind();">
	          <option value="">请选择省</option>
	          <c:forEach items="${provincelist}" var="var" varStatus="vs">
			  <option value="${var.pcd_id}">${var.name}</option>
		   	  </c:forEach>
	           </select>
	          <select class="yingxiao8_d1_st1 st2" id="city_id" name="city_id" onchange="searcharea();">
	              <option value="">请选择市</option>
	           </select>
	          <select class="yingxiao8_d1_st1 st2" id="area_id" name="area_id">
	              <option value="">请选择区</option>
	           </select>
	      </div>
	      <div class="yingxiao8_d1">
	        <span class="yingxiao8_d1_sp1">发送时间</span>
	        <input placeholder="开始时间" type="text" class="yingxiao8_d1_st1 st3" name="send_time" id="send_time" value="${pd.send_time}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:ss:mm'})"/>
	      </div> 
	      <input  type="text" name="content"  class="shixiang3_ipt" placeholder="输入通知内容" />
	      <c:if test="${qx.add eq '1'}">
 	      <div class="yingxiao8_d1">
	          <span class="yingxiao8_yes" onclick="search()">提交审核</span>
	      </div> 
	      </c:if>
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
		function search(){
 			$("#Form").submit();
		}
		
	</script>
</html>