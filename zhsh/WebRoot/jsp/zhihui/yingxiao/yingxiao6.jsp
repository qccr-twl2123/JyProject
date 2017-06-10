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
        <link rel="stylesheet" href="css/zhihui/yingxiao6.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/yingxiao6.js"></script>
         <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihui_sort_score/list.do" id="Form" name="Form" method="post">
       <div class="dangan2_d1">
          	<select class="dangan2_d1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
	           	<option value="">请选择省</option>
	           	<c:forEach items="${provincelist}" var="var" varStatus="vs">
					<option value="${var.pcd_id}">${var.name}</option>
				</c:forEach>
				<c:if test="${pd.province_id ne '' and  !empty pd.province_id}">
							<option value="${pd.province_id}"  selected="selected">${pd.province_name}</option>
				</c:if>
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
         <span  class="zhifu1_sp1">搜索商家</span>
          <input class="zhifu1_ipt1" type="text" name="content" value="${pd.content }" placeholder="可输入商家名称查询"></input>
            <span class="zhifu1_btn1" onclick="search()">查询</span>
            <c:if test="${qx.add eq '1'}">
            <a class="middle_a" href="zhihui_sort_score/goAdd.do"  target="ifra">
           <span class="zhifu1_btn1">添加</span>
          </a>
          </c:if>
       </div>
       <div class="dangan2_d1">
           <span  class="zhifu1_sp1">有效时间</span>
             <input class="zhifu1_st1" type="text" name="starttime" id="starttime" value="${pd.starttime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间"/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至
           <input class="zhifu1_st1" type="text" name="endtime" id="endtime" value="${pd.endtime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间"/>
       </div>
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr >
                <td>序号</td>
                <td>商家名称</td>
                <td>商家现分值</td>
                <td>增加分值</td>
                <td>起止时间</td>
                <td>操作员</td>
                <td>费用</td>
                <!-- <td>审核状态</td> -->
                <td>审核人</td>
              </tr>
              <c:forEach items="${varList}" var="var" varStatus="vs"> 
              <tr >
                <td>${vs.index+1}</td>
                <td>${var.store_name}</td>
                <td>${var.now_score}</td>
                <td>${var.add_score}</td>
                <td>${var.starttime}--${var.endtime}</td>
                <td>${var.operator_name}</td>
                <td>￥：${var.spend_money }</td>
                <%-- <td>
                	<c:if test="${var.review_status eq '0'}">待审核</c:if>
                	<c:if test="${var.review_status eq '1'}">审核通过</c:if>
                	<c:if test="${var.review_status eq '99'}">审核不通过</c:if>
                 </td> --%>
                <td>${var.review_name}</td>
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
					  	$("#area_id option").remove();
					  	$("#area_id").append("<option  value=''>请选择区</option>");
 					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$("#area_id").append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
						  	}
				  		}
				  }
			});
		}
		//---------------------------
 		
		//检索
		function search(){
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
		
    
    </script>
</html>