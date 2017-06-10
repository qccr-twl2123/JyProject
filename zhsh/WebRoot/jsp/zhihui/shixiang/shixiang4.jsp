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
		<script src="My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" href="css/zhihui/shixiang4.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/shixiang4.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    	<form action="zhihui_menu_record/list.do" name="Form" id="Form" method="post">
		       <div class="dangan2_d1">
		         	<span  class="zhifu1_sp1">区&nbsp;&nbsp;&nbsp;&nbsp;域 </span>
		         	<select class="dangan2_d1_st1" name="province_id" id="province_id" onchange="searchfind();">
					    <option value="">请选择省</option>
					     <c:forEach items="${provincelist}" var="var" varStatus="vs">
								<option value="${var.pcd_id}" >${var.name}</option>
						</c:forEach>
						<c:if test="${pd.province_id ne '' and  !empty pd.province_id}">
								<option value="${pd.province_id}"  selected="selected">${pd.province_name}</option>
						</c:if>
					</select>
	 				<select class="dangan2_d1_st1" id="city_id" name="city_id" onchange="searcharea();">
						  <option value="${pd.city_id}">${pd.city_name}</option>
					</select>
	 				<select class="dangan2_d1_st1" id="area_id" name="area_id" onchange="searchdangqi()">
					       <option value="${pd.area_id}">${pd.area_name}</option>
					</select>
					<input type="hidden" name="province_name" id="province_name" value="${pd.province_name}"  />
			        <input type="hidden" name="city_name" id="city_name" value="${pd.city_name}"  />
			        <input type="hidden" name="area_name" id="area_name" value="${pd.area_name}"  />
 			       <span  class="zhifu1_sp1">发送时间</span>
			       <input type="text" class="dangan2_d1_st1" name="send_startdate" value="${pd.send_startdate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间" />
			          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span  > 至</span> 
			       <input type="text" class="dangan2_d1_st1"  name="send_enddate" value="${pd.send_enddate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  placeholder="结束时间"/>
		        </div>
		       <div class="dangan2_d1">
		          <input type="text" class="dangan2_d1_ipt1" placeholder="可输入用户名、账号等查询" />
		          <span class="zhifu1_btn1" onclick="search()">查询</span>
		        </div>
		       <div class="dangan2_d2">
		          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
		              <tr >
		                <td>序号</td>
		                <td>用户类型</td>
		                <td>用户名</td>
		                <td>账号</td>
		                <td>省</td>
		                <td>市</td>
		                <td>区/县</td>
		                <td>最后发送时间</td>  
		                <td></td>
		              </tr> 
		              <c:forEach items="${varList }" var="var" varStatus="vs">
			              <tr >
			                <td>0001</td>
			                <td>商家</td>
			                <td>牛肉火锅</td>
			                <td>45215625846847</td>
			                <td>浙江省</td>
			                <td>杭州市</td>
			                <td>西湖区</td>
			                <td>2016-04-25</td>  
			                <td class="blue ">
			                <a class="middle_a" href="shixiang5.html"  target="ifra">查看</a>
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
     //检索
		function search(){
			$("#Form").submit();
		}
     
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
						$("#area_id option").remove();
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