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
        <link rel="stylesheet" href="css/zhihui/yingxiao1.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <style type="text/css">
        a:hover {
			cursor: pointer;
		}
        </style>
     </head>
    <body>
    <c:if test="${qx.look eq '1'}">
 	       <form action="fapiao/fapiaolist.do" name="Form" id="Form" method="post">
    	    <input type="hidden" name="chuli_status" value="1"  />
 	        <div class="dangan2_d1">
		        <input class="zhifu1_ipt1" type="text" placeholder="可输入发票编号查询" name="content" id="content" value="${pd.content}"></input>
	 	        <span class="zhifu1_btn1" onclick="checked()">查询</span>
	        </div>
	       <div class="dangan2_d2">
	          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
	              <thead>
	              	<tr>
	              		<td colspan="5">客户信息</td>
	              		<td colspan="6">发票信息</td>
	              		<td colspan="4">收件地址</td>
	              	</tr>
	              	<tr>
	              		<td>序号</td>
	              		<td>客户ID</td>
	              		<td>客户名称</td>
	              		<td>隶属服务商</td>
	              		<td>服务商ID</td>
 	              		<td>发票编号</td>
	              		<td>发票抬头</td>
 	              		<td>发票类型</td>
	              		<td>申请时间</td>
	              		<td>发票状态</td>
	              		<td>开户行</td>
	              		<td>银行账号</td>
	              		<td>联系电话</td>
	              		<td>邮编</td>
	              		<td>操作</td>
	              	</tr>
	              </thead> 
	              <c:forEach items="${varList}" var="var" varStatus="vs">
	              	<tr>
		                <td>${vs.index+1}</td>
	              		<td>${var.sq_id}</td>
	              		<td>
	              			<c:if test="${var.sq_type eq '1' }"> ${var.store_name}</c:if> 
	              			<c:if test="${var.sq_type eq '2' }"> ${var.team_name}</c:if>
	              		</td>
	              		<td>${var.ls_team_name}</td>
	              		<td>${var.ls_sp_file_id}</td>
  		                <td>${var.bill_number}</td>
 		                <td>${var.bill_tt}</td>
  		                <td>${var.bill_type}</td>
 		                <td>${var.createtime}</td>
 		                <td>
 		               		 <c:if test="${var.chuli_status eq '1'}">待寄送</c:if>
  		                </td>
 		                <td>${var.sj_bankname}</td>
 		                <td>${var.sj_banknumber}</td>
 		                <td>${var.sj_phone}</td>
 		                <td>${var.postcode}</td>
 		                <td>
 		                	<c:if test="${qx.edit eq '1' }">
 		                		<a   onclick="change_status('${var.bill_id}');"  >寄送</a>
 		                	</c:if>
  		                </td>
 		             </tr> 
 	              </c:forEach>           
	          </table>
 	       </div>
	       <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
 	   </form>
  </c:if>
  <script type="text/javascript" src="js/attention/zDialog/zDrag.js"></script>
  <script type="text/javascript" src="js/attention/zDialog/zDialog.js"></script>
  <script type="text/javascript">
  		//检索
     	function checked(){
      		$("#Form").submit();//提交
     	}
  		
     	//点击寄送
 		function change_status(bill_id){
  			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="点击寄送";
			 diag.URL = 'fapiao/goAddKD.do?chuli_status=2&bill_id='+bill_id;
			 diag.Width = 600;
			 diag.Height = 300;
			 diag.CancelEvent = function(){ //关闭事件
				nextPage(${page.currentPage});
				diag.close();
			 };
			 diag.show();
		} 
     	
  </script>
    </body>
</html>