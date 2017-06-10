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
        <!-- 导入的头文件的js和css -->
		<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
		<link rel="stylesheet" href="css/ace.min.css" />
		<!-- 分页 -->
		<link href="css/bootstrap.min.css" rel="stylesheet" /> 
        <link rel="stylesheet" href="css/zhihui/dangan2.css">
        <!--  -->
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/dangan2.js"></script>
        <script src="js/zhihui/bg.js"></script>
     </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <!-- 内容 -->
       <form action="zhihui_city_file/list.do" method="post" name="Form" id="Form">
	       <div class="dangan2_d1">
	         <select class="dangan2_d1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
	           	<c:if test="${pd.province_id ne '' and  !empty pd.province_id}">
							<option value="${pd.province_id}"  selected="selected">${pd.province_name}</option>
				</c:if>
	           	<option value="">请选择省</option>
	           	<c:forEach items="${provincelist}" var="var" varStatus="vs">
					<option value="${var.pcd_id}">${var.name}</option>
				</c:forEach>
  	         </select>
	         <select class="dangan2_d1_st1" name="city_id" id="city_id" onchange="addsearcharea();">
	         	 <option value="${pd.city_id}">${pd.city_name}</option>
 	         </select>
	         <select class="dangan2_d1_st1" name="area_id" id="area_id">
	         	 <option value="${pd.area_id}">${pd.area_name}</option>
 	         </select>
 	         <input type="hidden" name="province_name" id="province_name" value="${pd.province_name}"  />
		     <input type="hidden" name="city_name" id="city_name" value="${pd.city_name}"  />
		     <input type="hidden" name="area_name" id="area_name" value="${pd.area_name}"  />
	        <span class="dangan2_d1_st1">启用状态</span>
	        <select class="dangan2_d1_st2" name="open_status" id="open_status">
	           <option value="">不限</option>
	           <option value="1" ${pd.open_status eq '1'?'selected':'' }>已启用</option>
	           <option value="0" ${pd.open_status eq '0'?'selected':'' }>未启用</option>
 	         </select>
	         <input type="text" placeholder="可输省市区、关键字" class="dangan2_d1_ipt1" name="content" id="content" value="${pd.content}"></input>
	         <span class="dangan2_d1_btncx" onclick="check();">查询</span>
	         <c:if test="${qx.add eq '1'}">
		         <a class="middle_a" href="zhihui_city_file/goAdd.do"  target="ifra">
		            <span class="dangan2_d1_btntj">添加</span>
		         </a>
		     </c:if>
 	       </div>
	       <div class="dangan2_d2">
	          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
	          	<thead>
	          		<tr class="tdtop">
	                <td>序号</td>
	                <td>省</td>
	                <td>市</td>
	                <td>区/县</td>
	                <td>是/否启用</td>
	                <td>操作</td>
	              </tr>  
  	          	</thead>
	            <tbody>
	              <c:forEach items="${varList}" var="var" varStatus="vs">
	              	<tr>
	              		<td>${var.city_file_id}</td>
	              		<td>${var.province_name}</td>
	              		<td>${var.city_name}</td>
	              		<td>${var.area_name}</td>
	              		<td> 
	              			<c:if test="${var.open_status eq '0'}">否</c:if>
	              			<c:if test="${var.open_status eq '1'}">是</c:if>
	              		</td>
	              		<td>
	              			<c:if test="${qx.edit eq '1'}">
	              				<a href="zhihui_city_file/goEdit.do?city_file_id=${var.city_file_id}&currentPage=${page.currentPage}"><span class="td_sp1">修改</span></a>
	              			</c:if>
	              			<c:if test="${qx.delete eq '1'}">
	              				<span class="td_sp1" onclick="del('${var.city_file_id}')">删除</span>
	              			</c:if>
	              		</td>
	              	</tr>
	              </c:forEach>
	            </tbody>
  	          </table>
	       </div>
	       <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
       </form>
       </c:if>
        <!-- 分页需要的js -->
		<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
 		<script type="text/javascript">window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/ace.min.js"></script>
 		<!-- 引入 -->
		<script type="text/javascript" src="js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="js/bootbox.min.js"></script><!-- 确认窗口 -->
		<script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框-->
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
					  	$("#area_id").empty();
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
		function check(){
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
 			$("#Form").submit();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					var url = '<%=path%>/zhihui_city_file/delete.do?city_file_id='+Id+'&tm='+new Date().getTime();
					$.get(url,function(data){
						if(data=="success"){
							nextPage(${page.currentPage});
						}
					});
				}
			});
		}
        
        </script>
    </body>
</html>