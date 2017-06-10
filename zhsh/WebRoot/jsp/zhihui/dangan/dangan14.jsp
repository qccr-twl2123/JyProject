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
		<link rel="stylesheet" href="css/ace.min.css" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/dangan14.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/dangan14.js"></script>
        <script src="js/zhihui/bg.js"></script>
        <style type="text/css">
        	input[type=checkbox]{
			    opacity: 1;
			    position: initial;
 			}
        </style>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    	<!-- 内容 -->
       <form action="zhihuiz_store_file/listStoreRelations.do" method="post" name="Form" id="Form">
       <div class="dangan2_d1">
         <span class="dangan_d1_sp1">查询区域</span>
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
         		<input type="text" placeholder="输入服务商名称/编号/商家名称/商家手机号码" class="dangan2_d1_ipt1" name="content"/>
         <span class="dangan2_d1_btncx" onclick="check()">查询</span>
         <c:if test="${qx.edit eq '1'}">
	         <a class="middle_a"   target="ifra" onclick="updateStore()"> 
	          	<span class="dangan2_d1_btnmb">批量调整</span>
	         </a>
         </c:if>
       </div>
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr class="tdtop">
                <td><input type="checkbox" name="allids" class="dangan14_ipt1 dangan14iptclick" /></td>
                <td>序号</td>
                <td>商家名称</td>
                <td>省</td>
                <td>市</td>
                <td>区/县</td>
                <td>所属服务商</td>
               </tr>  
              <c:forEach items="${varList}" var="var" varStatus="vs"> 
	              <tr>
	                <td><input type="checkbox" name="ids" class="dangan14_ipt1" value="${var.store_file_id}"/></td>
	                <td>${vs.index+1}</td>
	                <td>${var.store_name}</td>
	                <td>${var.province_name}</td>
	                <td>${var.city_name}</td>
	                <td>${var.area_name}</td>
	                <td>${var.team_name}</td>
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
					  	if(list.length>0){
					  		$("#city_id").append("<option value=''>请选择市</option>");
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
		
		//所有商家ID
		var allstoreid="";
 		//批量操作
		function updateStore(){
			$("input[name='ids']:checkbox").each(function(){ 
				if (true == $(this).is(":checked")) { 
					allstoreid += $(this).attr('value')+"@"; 
 				} 
			}); 
			if(allstoreid == ""){
				alert("请先选中要改变的数据");
			}else{
				window.location.href='<%=basePath%>/zhihuiz_store_file/goUpdateStoreRelations.do?allstoreid='+allstoreid;
			}
 			
		}
		
		
		$(function(){
			//选中/不选中
			$('.dangan14iptclick').click(function(){
		         $('.dangan14_ipt1').attr('checked',$(this).prop('checked'));
			})
		})
       
       </script>
    </body>
</html>