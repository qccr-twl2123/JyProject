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
        <link rel="stylesheet" href="css/zhihui/yingxiao12.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/yingxiao12.js"></script>
        <script src="js/zhihui/bg.js"></script>
        <style type="text/css">
        	input[type=radio]{
			    opacity: 1;
			    position: initial;
 			}
        </style>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihui_store/list.do" name="Form" id="Form" method="post">
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
        <!-- <span  class="zhifu1_sp1">用户类型</span>
          <select class="zhifu1_st1">
           <option value="">全部</option>
           <option>服务商</option>
           <option>会员</option>
         </select> -->
        
       </div>
       <div class="dangan2_d1">
        <span  class="zhifu1_sp1">选择星级</span>
        <span class="yingxiao12_sp1">
            <label>
             <input type="radio"  name="duoxuan" value="1"/><span class="yingxiao12_sp2">一星</span>
            </label>
        </span>
        <span class="yingxiao12_sp1">
            <label>
              <input type="radio"  name="duoxuan" value="2"/><span class="yingxiao12_sp2">二星</span>
            </label>
        </span>
        <span class="yingxiao12_sp1">
            <label>
              <input type="radio" name="duoxuan"  value="3"/><span class="yingxiao12_sp2">三星</span>
            </label>
        </span>
        <input class="zhifu1_ipt1" type="text" name="content" placeholder="可输入账号/金额等查询"></input>
        <span class="zhifu1_btn1" onclick="search()">查询</span>
       </div>
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr >
                <td>序号</td>
                <td>商家ID</td>
                <td>省</td>
                <td>市</td>
                <td>区/县</td>
                <td>商家名称</td>
                <td>负责人</td>
                <td>手机号</td>
                <td>当前星级</td>
                <td>编辑栏</td>
              </tr> 
              <c:forEach items="${varList}" var="var" varStatus="vs">
             <tr >
             	<td>${vs.index+1}</td>
                <td>${var.store_id}</td>
                <td>${var.province_name}</td>
                <td>${var.city_name}</td>
                <td>${var.area_name}</td>
                <td>${var.store_name}</td>
                <td>${var.principal}</td>
                <td>${var.phone }</td>
                <td>
               	 <c:if test="${var.merchant_level eq'1'}">一星</c:if>
		      	 <c:if test="${var.merchant_level eq'2'}">二星</c:if>
		      	 <c:if test="${var.merchant_level eq'3'}">三星</c:if>
                </td>
                <td class="blue ">
                	<c:if test="${qx.edit eq '1'}">
 	                    <a class="middle_a" href="zhihui_store/goEdit.do?store_id=${var.store_id}&currentPage=${page.currentPage}"  target="ifra">修改</a>
                 	</c:if>
                 </td>
              </tr> 
              </c:forEach>
          </table>
          <br/>
          <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
       </div>
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
					  	$("#area_id").append("<option value=''>请选择区</option>");
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