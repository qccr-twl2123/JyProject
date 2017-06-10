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
        <title>档案管理</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/zhihui/dangan15.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/dangan15.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    	<form action="zhihuiz_store_file/editStoreSp.do" name="Form" id="Form" method="post">
    		<input type="hidden" name="allstoreid" id="allstoreid" value="${pd.allstoreid}" />
	        <div class="dangan3_d1">
	          	<span class="dangan_d1_sp1">省</span>
		           <select class="dangan3_d1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
		             <c:if test="${msg eq 'save'}">
				 		 <option value="">请选择省</option>
				 	 </c:if>
				 	 <c:forEach items="${provincelist}" var="var">
						 <option value="${var.pcd_id}" >${var.name}</option>
					 </c:forEach>
		           </select>
		          <span class="dangan_d1_sp1">市</span>
		          <select class="dangan3_d1_st1" name="city_id" id="city_id" onchange="addsearcharea();">
 		           </select>
		          <span class="dangan_d1_sp1">区/县</span>
		          <select class="dangan3_d1_st1" name="area_id" id="area_id" onchange="getlistsp(this)">
 		           </select>
	        </div>
	        <div class="dangan3_d1">
	          	   <span class="dangan_d1_sp1">更换服务商</span>
		           <select class="dangan3_d1_st1" name="newsp_id" id="newsp_id">
		             	<option value="">请先选择区域</option>
		           </select>
	        </div>
	        <div class="dangan_d1 ">
	        <c:if test="${qx.edit eq '1'}">
	          <span class="dangan_d1_btn1  mgleft11" onclick="sureChange()">确认调整</span> 
	          </c:if>
	          <a class="middle_a" href="zhihuiz_store_file/listStoreRelations.do"  target="ifra"> 
	           <span class="dangan_d1_btn1">退出</span>
	          </a>  
	        </div>
        </form>
        </c:if>
      <script type="text/javascript">
    //获取城市
		function addsearchfind(){
			var str=$("#province_id option:selected").val();//获取被选中的value值
			
			$.ajax({
				  url: '<%=path%>/zhihui_subsidiary/citylist.do',
				  data:"province_id="+str,
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.citylist;
					  	$("#city_id option").remove();
 					  	$("#area_id option").remove();
 					  	$("#city_name").val("");
 					  	$("#area_name").val("");
 					  	$("#city_id").append("<option value=''>请选择市</option>");
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
		function addsearcharea(){
			var str=$("#city_id option:selected").val();//获取被选中的value值
			$.ajax({
				  url: '<%=path%>/zhihui_subsidiary/arealist.do',
				  data:"city_id="+str,
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.arealist;
					  	$("#area_id option").remove();
					  	$("#area_id").append("<option  value=''>请选择区</option>");
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
    	
    	//获取所有服务商
		function getlistsp(obj){
  			$.ajax({
				  url: 'zhihuiz_store_file/getSpListBycity.do',
				  data:{
					  "area_id":$(obj).val()
				  },
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.spList;
					  	$("#newsp_id").empty();
					  	$("#newsp_id").append("<option value=''>请选择服务商</option>");
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$("#newsp_id").append("<option value='"+list[i].sp_file_id+"'>"+list[i].team_name+"</option>");
						  	}
					  	} 
				  },
				  error:function(a){
				  	alert("异常");
				  }
			});
		}
    	
    	
    	function sureChange(){
     		$("#Form").submit();//提交
    	}
      
      </script>
    </body>
</html>