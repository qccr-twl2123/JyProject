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
        <link rel="stylesheet" href="css/zhihui/dangan5.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/dangan5.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihui_sp_file/list.do" method="post" name="Form" id="Form">
       <div class="dangan2_d1">
          <select class="dangan2_d1_st1" name="province_id" id="province_id" onchange="addsearchfind()">
          	<option value="">请选择省</option>
			<c:forEach items="${provincelist}" var="var" varStatus="vs">
				<option value="${var.pcd_id}">${var.name}</option>
			</c:forEach>
			<c:if test="${pd.province_id ne '' and  !empty pd.province_id}">
				<option value="${pd.province_id}"  selected="selected">${pd.province_name}</option>
			</c:if>
         </select>
         <select class="dangan2_d1_st1" id="city_id" name="city_id" onchange="addsearcharea();" >
           <option value="${pd.city_id}">${pd.city_name}</option>
         </select>
          <select class="dangan2_d1_st1" id="area_id" name="area_id"  >
           <option value="${pd.area_id}">${pd.area_name}</option>
         </select>
         <input type="hidden" name="province_name" id="province_name" value="${pd.province_name}"  />
	     <input type="hidden" name="city_name" id="city_name" value="${pd.city_name}"  />
	     <input type="hidden" name="area_name" id="area_name" value="${pd.area_name}" />
         <input type="text" name="content" placeholder="输入服务商团队名称、负责人、联系方式" class="dangan2_d1_ipt1"  value="${pd.content}"/>
          <span class="dangan2_d1_btncx" onclick="check()" >查询</span> 
         <c:if test="${qx.add eq '1'}"><a class="middle_a" href="zhihui_sp_file/goAdd.do"  target="ifra"> 
           <span class="dangan2_d1_btnmb">添加</span>
         </a></c:if>
       </div>
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr class="tdtop">
                <td>序号</td>
                <td>子公司名称</td>
                <td>隶属城市</td>
                <td>服务行业</td>
                <td>负责人</td>
                <td>联系方式</td>
                <td>当前账号余额</td>
                <td>创建时间</td>
                <td>操作</td>
              </tr>  
              <c:forEach items="${varList}" var="var" varStatus="vs">
              <tr>
                <td>${var.sp_file_id}</td>
                <td>${var.subsidiary_name}</td>
                <td>${var.area_name}</td>
                <td>${var.sort_name}</td>
                <td>${var.principal}</td>
                <td>${var.phone}</td>
                <td>${var.nowmoney}</td>
                <td>${fn:substring(var.createdate,0,19)} </td>
                <td>
                	<c:if test="${qx.edit eq '1'}"><a href="zhihui_sp_file/goEdit.do?sp_file_id=${var.sp_file_id}&currentPage=${page.currentPage}"><span class="td_sp1">修改</span></a></c:if>
                	<c:if test="${qx.delete eq '1'}"><a href="zhihui_sp_file/delete.do?sp_file_id=${var.sp_file_id}&currentPage=${page.currentPage}"><span class="td_sp1">删除</span></a></c:if>
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
				  	$("#area_id").append("<option value=''>请选择区</option>");
				  	if(list.length>0){
					  	for(var i=0;i<list.length;i++){
					  		$("#area_id").append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
					  	}
			  		}
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
		
    </script>
</html>