<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>业务员业绩</title>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<link rel="stylesheet" href="css/ace.min.css" />
    <link href="css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="css/zhihui/zhifu5.css">
 	<style>
		html,body{
			height:100%;
		}
		.cont{
			height: 92%;
 		}
		.zhifu1_sp1_span {
		    display: inline-block;
 		    text-align: right;
		    margin-left: 2.2%;
		    float: none;
		}
	</style>
</head>
<body>
 <c:if test="${qx.look eq '1'}">
  	<div class="cont">
		<form action="zhihui_service_performance/listServiceClerk.do" name="Form" id="Form" method="post">
 		       <c:if test="${login_type ne '2' }">
	 		       <div class="dangan2_d1">
			          <span  class="zhifu1_sp1_span">业务员编号及名称</span>
			          <input name="content" value="${pd.content}" placeholder="业务员编号及名称">
	 		          <span class="zhifu1_btn1" onclick="onesearch()">查询</span>
	 		       </div>
 		       </c:if>
 	 	       <div class="dangan2_d2">
		          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
		              <thead>
		              	 <tr class="tdtop">
			                 <td rowspan="2">年/月</td>
			                 <td colspan="2">商家数</td>
			                 <td colspan="2">会员数</td>
  			                 <td colspan="2" >订单交易流水</td>
  			                 <td rowspan="2" >备注</td>
  	 	 	              </tr>	
	 	 	              <tr>
	 	 	              	<td>新增</td>
	 	 	              	<td>累计</td>
	 	 	              	
	 	 	              	<td>新增</td>
	 	 	              	<td>累计</td>
	 	 	              	
	 	 	              	<td>新增</td>
	 	 	              	<td>累计</td>
 	 	 	              </tr>
		              </thead>
		              <tbody>
			              <c:forEach items="${varList}" var="var" varStatus="vs">
				              <tr >
						            <td>${var.month}</td>
						            
						            <td>${var.add_store}</td>
			 	 	              	<td>${var.leiji_store}</td>
			 	 	              	
			 	 	              	<td>${var.add_member}</td>
			 	 	              	<td>${var.leiji_member}</td>
 			 	 	              	 
 			 	 	              	<td>${var.add_water}</td>
			 	 	              	<td>${var.leiji_water}</td>
			 	 	              	
			 	 	              	<td><input value="${var.remark}"> <span onclick="editRemark('${var.clerk_file_monthly_id}',this)" style="color:blue;cursor: pointer;">修改</span></td>
  				              </tr>
			              </c:forEach>
		              </tbody>
 		          </table>
		       </div>
		       <br/>
		       <div class="pagination" style="float:right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		  </form>
	</div>
	</c:if>
	<script src="js/jquery-1.8.0.min.js"></script>
 	<script type="text/javascript">
	function onesearch(){
		$("#Form").submit();
	}
	
	//修改备注
	function editRemark(clerk_file_monthly_id,obj){
		var url = "zhihui_service_performance/editRemark.do.do?clerk_file_monthly_id="+clerk_file_monthly_id+"&remark="+$(obj).prev().val();
		$.get(url,function(data){
			if(data=="success"){
				nextPage(${page.currentPage});
			}
		});
	}
	
	</script>
</body>
</html>






