<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		<link rel="stylesheet" href="css/ace.min.css" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/dangan11.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/dangan11.js"></script>
        <script src="js/zhihui/bg.js"></script>
        <style type="text/css">
        .dangan2_d2_table td {
		    padding: 5px;
		}
        </style>
    </head>
    <body>
     <c:if test="${qx.look eq '1'}">
    	<!-- 内容 -->
       <form action="zhihuiz_store_file/notlist.do" method="post" name="Form" id="Form">
	       <div class="dangan2_d1">
	         	<select class="dangan2_d1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
				    <option value="">请选择省</option>
				     <c:forEach items="${provincelist}" var="var" varStatus="vs">
							<option value="${var.pcd_id}" >${var.name}</option>
					</c:forEach>
					<c:if test="${pd.province_id ne '' and  !empty pd.province_id}">
							<option value="${pd.province_id}"  selected="selected">${pd.province_name}</option>
					</c:if>
				</select>
 				<select class="dangan2_d1_st1" id="city_id" name="city_id" onchange="addsearcharea();">
					  <option value="${pd.city_id}">${pd.city_name}</option>
				</select>
 				<select class="dangan2_d1_st1" id="area_id" name="area_id">
				       <option value="${pd.area_id}">${pd.area_name}</option>
				</select>
				<input type="hidden" name="province_name" id="province_name" value="${pd.province_name}"  />
		        <input type="hidden" name="city_name" id="city_name" value="${pd.city_name}"  />
		        <input type="hidden" name="area_name" id="area_name" value="${pd.area_name}"  />
		        <input type="text"  value="${pd.content }" placeholder="可输入序列号商家名称等查询" class="dangan2_d1_ipt1" name="content"/>
	         	<span class="dangan2_d1_btncx" onclick="check()">查询</span>
	       </div>
	       <div class="dangan2_d2">
		          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table" style="    white-space: nowrap;line-height:36px">
		              <tr class="tdtop">
		                <td>序号</td>
 		                <td style="width: 180px;">商家全称</td>
		                <td>商家简称</td>
		                <td>区域</td>
		                <td>地址</td>
		                <td>联系方式</td>
		                <td>联系人</td>
		                <td>客户管理</td>
		                <td>商家状态</td>
		                <td>创建时间</td>
		                <td>操作</td>
		              </tr>   
		     		<c:forEach items="${varList}" var="var" varStatus="vs">
		              <tr>
		                <td>${var.store_id}</td>
 		                <td  style="width: 180px;">${var.business_licenses_name}</td>
		                <td>${var.store_name}</td>
		                <td>${var.province_name}${var.city_name}${var.area_name}</td>
		                <td>${var.address}</td>
		                <td>${var.phone}</td>
		                <td>${var.principal}</td>
		                <td> 
		                	<c:choose>
		                		<c:when test="${var.check_status eq '0'}">申请上线</c:when>
		                		<c:when test="${var.biaozhun_status eq '0' and var.check_status eq '1'  }">审核通过/待支付</c:when>
 		                		<c:when test="${var.biaozhun_status eq '1' and var.check_status eq '1' }">已上线</c:when>
  		                	</c:choose>
 		                </td>
		                <td>
		                	<c:if test="${var.open_status eq '0'}">停用</c:if>
	              			<c:if test="${var.open_status eq '1'}">正常</c:if>
		                </td>
		                <td>${fn:substring(var.createdate,0,19)} </td>
		                <td>
		                	<c:if test="${qx.edit eq '1'}">
		                	<a href="zhihuiz_store_file/goEdit.do?store_file_id=${var.store_file_id}&currentPage=${page.currentPage}&ok=0&content=${pd.content}&province_id=${pd.province_id}&province_name=${pd.province_name}&city_id=${pd.city_id}&city_name=${pd.city_name}&area_id=${pd.area_id}&area_name=${pd.area_name}" target="ifra"><span class="td_sp1">审核</span></a>
		                	</c:if>
		                	<c:if test="${qx.delete eq '1'}">
		                	<span class="td_sp1" onclick="del('${var.store_id}')">删除</span> 
		                	</c:if>
		                </td>
		              </tr>
		            </c:forEach>
 		         </table>
	          	 <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
 	        </div>
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
		function check(){
			if($("#city_id option:selected").val() != ""){
				var city_name=$("#city_id option:selected").text();
				$("#city_name").show();
				$("#city_name").val(city_name);
			}
			if($("#province_id option:selected").val() != ""){
				var province_name=$("#province_id option:selected").text();
				$("#province_name").show();
				$("#province_name").val(province_name);
			}
			if($("#area_id option:selected").val() != ""){
				var area_name=$("#area_id option:selected").text();
				$("#area_name").show();
				$("#area_name").val(area_name);
			}
			$("#Form").submit();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					var url = '<%=path%>/zhihuiz_store_file/delete.do?store_id='+Id+'&tm='+new Date().getTime();
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