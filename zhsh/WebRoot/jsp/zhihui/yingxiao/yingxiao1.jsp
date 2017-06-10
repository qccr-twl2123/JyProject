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
        <link rel="stylesheet" href="css/zhihui/yingxiao1.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/yingxiao1.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    	<form action="zhihuicity_marketing/list.do" name="Form" id="Form" method="post">
	       <div class="dangan2_d1">
 	         	<select class="zhifu1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
				    <option value="">请选择省</option>
				     <c:forEach items="${provincelist}" var="var" varStatus="vs">
							<option value="${var.pcd_id}" >${var.name}</option>
					</c:forEach>
					<c:if test="${pd.province_id ne '' and  !empty pd.province_id}">
							<option value="${pd.province_id}"  selected="selected">${pd.province_name}</option>
					</c:if>
				</select>
 				<select class="zhifu1_st1" id="city_id" name="city_id" onchange="addsearcharea();">
					  <option value="${pd.city_id}">${pd.city_name}</option>
				</select>
 				<select class="zhifu1_st1" id="area_id" name="area_id">
				       <option value="${pd.area_id}">${pd.area_name}</option>
				</select>
				
 	            <input type="hidden" name="province_name" id="province_name" value="${pd.province_name}"  />
		        <input type="hidden" name="city_name" id="city_name" value="${pd.city_name}"  />
		        <input type="hidden" name="area_name" id="area_name" value="${pd.area_name}"  />
	       </div>
	        <div class="dangan2_d1">
		        <input class="zhifu1_ipt1" type="text" placeholder="可输入城市名称关键字" name="content" id="content" value="${pd.content}"></input>
	 	        <span class="zhifu1_btn1" onclick="checked()">查询</span>
	        </div>
	       <div class="dangan2_d2">
	          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
	              <tr >
	                <td>序号</td>
	                <td>省</td>
	                <td>市</td>
	                <td>区/县</td>
	                <td>启用状态</td>
	                <td>编辑栏</td>
	              </tr> 
	              <c:forEach items="${varList}" var="var" varStatus="vs">
	              	<tr>
		                <td>${var.city_file_id}</td>
	              		<td>${var.province_name}</td>
	              		<td>${var.city_name}</td>
	              		<td>${var.area_name}</td>
	              		<td>
	              			<c:if test="${var.open_status eq '0'}">未启用</c:if>
	              			<c:if test="${var.open_status eq '1'}">已启用</c:if>
	              		</td>
		                <td class="blue bctitle">
		                <c:if test="${qx.edit eq '1'}"><a class="middle_a" href="zhihuicity_marketing/goEdit.do?city_file_id=${var.city_file_id}&currentPage=${page.currentPage}"  target="ifra">设置营销参数</a></c:if>
		                </td>
 		             </tr> 
 	              </c:forEach>           
	          </table>
	          <br/>
	          <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
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
		
		//检索
     	function checked(){
     		if($("#city_id option:selected").val() != ""){
				var city_name=$("#city_id option:selected").text();
 				$("#city_name").val(city_name);
			}
			if($("#province_id option:selected").val() != ""){
				var province_name=$("#province_id option:selected").text();
 				$("#province_name").val(province_name);
			}
			if($("#area_id option:selected").val() != ""){
				var area_name=$("#area_id option:selected").text();
 				$("#area_name").val(area_name);
			}
     		$("#Form").submit();//提交
     	}
     	
       </script>
    </body>
</html>