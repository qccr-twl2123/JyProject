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
        <title>九鱼销链</title>
        <meta charset="utf-8">
 		<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
		<!-- 以下两个是分页所需的两个分页样式css -->
		<link rel="stylesheet" href="css/ace.min.css" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
         <link rel="stylesheet" href="css/zhihui/index.css">
        <script src="js/jquery-1.8.0.min.js"></script>
     </head>
    <body>
         <c:if test="${qx.look eq '1'}">
               <!-- 内容 -->
               <form action="zhihui_citymanager/list.do" method="post" name="Form" id="Form"> 
	               <div class="zhihui_body_right_contern"  >
	                   <div class="zhihui_body_right_contern_d1">
 	                       <input type="text" class="zhihui_body_right_contern_ipt1" placeholder="可输入城市经理/登录账号等查询"  name="content" id="content" value="${pd.content}"/>
	                       <span class="zhihui_body_right_contern_cx" onclick="check()">查询</span>   
	                       <c:if test="${qx.add eq '1'}">
		                       <a href="zhihui_citymanager/goAdd.do" class="dangan_ifra_a1" target="ifra">
		                       		<span class="zhihui_body_right_contern_tj">添加</span>
		                       </a>
	                       </c:if>
	                   </div>
	                   <div class="zhihui_body_right_contern_d2">
	                       <table  border="0" cellspacing="0" cellpadding="0" class="zhihui_body_right_contern_table">
	                           <tr>
	                                <td>序号</td>
	                                <td>城市经理名称</td>
	                                <td>联系电话</td>
	                                <td>公司地址</td>
  	                                <td>操作</td>
	                           </tr>
	                           <c:forEach items="${varList}" var="var" varStatus="vs">
	                           		<tr>
	                          			<td>${var.citymanager_id}</td>
	                          			<td>${var.citymanager_name}</td>
	                          			<td>${var.phone}</td>
 										<td>${var.address}</td>
  										<td>
											<c:if test="${qx.edit eq '1'}">
											<a href="zhihui_citymanager/goEdit.do?citymanager_id=${var.citymanager_id}&currentPage=${page.currentPage}" target="ifra" class="zhihui_body_right_contern_xg"><span class="table_xg">修改</span></a>
											</c:if>
											<c:if test="${qx.look eq '0'}">
											无权限
											</c:if>
										</td>
									</tr>
 	                           </c:forEach>
	                       </table>
	                   </div>
	                   <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
  	               </div>
                </form> 
          </c:if>
          <script type="text/javascript">
       //获取城市
 		function addsearchfind(){
 			var str=$("#province_id option:selected").val();//获取被选中的value值
 			
 			$.ajax({
 				  url: '<%=path%>/zhihui_citymanager/citylist.do',
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
 				  url: '<%=path%>/zhihui_citymanager/arealist.do',
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
					var url = "<%=basePath%>/citymanager/delete.do?citymanager_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
					//	if(data=="success"){
							nextPage(${page.currentPage});
					//	}
					});
				}
			});
		}
		
		 
		</script>
     </body>
</html>