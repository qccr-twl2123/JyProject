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
         <link rel="stylesheet" href="css/zhihui/shixiang4.css">
        <script src="js/jquery-1.8.0.min.js"></script>
     </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    	<form action="zhaoshang/datalistPageCompay.do" name="Form" id="Form" method="post">
		       <div class="dangan2_d1">
		         	<span  class="zhifu1_sp1">区&nbsp;&nbsp;&nbsp;&nbsp;域 </span>
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
	 				<select class="dangan2_d1_st1" id="area_id" name="area_id"  >
					       <option value="${pd.area_id}">${pd.area_name}</option>
					</select>
					<input type="hidden" name="province_name" id="province_name" value="${pd.province_name}"  />
			        <input type="hidden" name="city_name" id="city_name" value="${pd.city_name}"  />
			        <input type="hidden" name="area_name" id="area_name" value="${pd.area_name}"  />
			        <span  class="zhifu1_sp1">企业类型 </span>
		          	<select class="dangan2_d1_st1"   name="company_type" id="company_type">
					       <option value="">全部</option>
					       <option value="1" ${pd.company_type eq '1'?'selected':'' }>大型商超</option>
					       <option value="2" ${pd.company_type eq '2'?'selected':'' }>连锁企业</option>
					       <option value="3" ${pd.company_type eq '3'?'selected':'' }>大型市场</option>
					       <option value="4" ${pd.company_type eq '4'?'selected':'' }>行业协会</option>
					       <option value="5" ${pd.company_type eq '5'?'selected':'' }>当地名企</option>
					       <option value="6" ${pd.company_type eq '6'?'selected':'' }>其他</option>
					</select>
 		       </div>
		       <div class="dangan2_d1">
 		          <input type="text" name="content" class="dangan2_d1_ipt1" placeholder="可输入相关信息查询" />
		          <span class="zhifu1_btn1" onclick="search()">查询</span>
		          <span class="zhifu1_btn1" onclick="toExcel()">导出excel</span>
		        </div>
		       <div class="dangan2_d2">
		          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table"  style="    white-space: nowrap;line-height:36px">
		              <tr>
		                <td>序号</td>
		                <td>企业类型</td>
		                <td>企业名称</td>
		                <td>联系人</td>
		                <td>职位</td>
		                <td>电话</td>
		                <td>邮箱</td>
 		                <td>申请时间</td>  
 		              </tr> 
		              <c:forEach items="${varList}" var="var" varStatus="vs">
			              <tr >
			                <td>${vs.index+1}</td>
			                <td>
			                	<c:if test="${var.company_type eq '1'}">大型商超</c:if>
			                	<c:if test="${var.company_type eq '2'}">连锁企业</c:if>
			                	<c:if test="${var.company_type eq '3'}">大型市场</c:if>
			                	<c:if test="${var.company_type eq '4'}">行业协会</c:if>
			                	<c:if test="${var.company_type eq '5'}">当地名企</c:if>
			                	<c:if test="${var.company_type eq '6'}">其他</c:if>
			                </td>
			                <td>${var.company_name}</td>
			                <td>${var.company_contacts}</td>
			                <td>${var.company_position}</td>
			                <td>${var.company_phone}</td>
			                <td>${var.company_mailbox}</td>
			                <td>${var.apple_time}</td>
 			              </tr> 
			          </c:forEach>
		          </table>
		          <br/>
		          <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		       </div>
       </form>
       </c:if>
    <script src="js/bootstrap.min.js"></script>
	<script src="js/ace-elements.min.js"></script>
	<script src="js/ace.min.js"></script>
	<script type="text/javascript" src="js/bootbox.min.js"></script><!-- 确认窗口 -->
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
     	
 
    	//导出excel
		function toExcel(){
 			var city_id=$("#city_id option:selected").val()
 			var province_id=$("#province_id option:selected").val()
 			var area_id=$("#area_id option:selected").val()
 			var company_type=$("#company_type option:selected").val()
 			window.location.href='<%=basePath%>zhaoshang/excel.do?company_type='+company_type+'&city_id='+city_id+'&province_id='+province_id+'&area_id='+area_id;
		}
    	
    	
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					var url = "<%=basePath%>/app_advert/delete.do?app_advert_id="+Id+"&tm="+new Date().getTime();
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