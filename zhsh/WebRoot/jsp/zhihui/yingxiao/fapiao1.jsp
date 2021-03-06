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
    	    <input type="hidden" name="chuli_status" value="0"  />
 	        <div class="dangan2_d1">
		        <input class="zhifu1_ipt1" type="text" placeholder="可输入发票编号查询" name="content" id="content" value="${pd.content}"></input>
	 	        <span class="zhifu1_btn1" onclick="checked()">查询</span>
	        </div>
	       <div class="dangan2_d2">
	          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
	              <thead>
	              	<tr>
	              		<td colspan="5">客户信息</td>
	              		<td colspan="5">发票信息</td>
	              	</tr>
	              	<tr>
	              		<td>序号</td>
	              		<td>客户ID</td>
	              		<td>客户名称</td>
	              		<td>隶属服务商</td>
	              		<td>服务商ID</td>
	              		<td>申请时间</td>
	              		<td>发票抬头</td>
	              		<td>发票编号</td>
	              		<td>发票类型</td>
	              		<td>操作</td>
	              	</tr>
	              </thead> 
	              <c:forEach items="${varList}" var="var" varStatus="vs">
	              	<tr>
		                <td>${vs.index+1}</td>
	              		<td>${var.sq_id}</td>
	              		<td>
	              			<c:if test="${var.sq_type eq '1' }"> ${var.store_name}</c:if> 
	              			<c:if test="${var.sq_type eq '2' }"> ${var.team_name}</c:if></td>
	              		<td>${var.ls_team_name}</td>
	              		<td>${var.ls_sp_file_id}</td>
 		                <td>${var.createtime}</td>
 		                <td>${var.bill_tt}</td>
 		                <td><input  id="bill_number" value=""></td>
 		                <td>${var.bill_type}</td>
 		                <td>
 		                	<c:if test="${qx.edit eq '1' }">
 		                		<a   onclick="change_status('${var.bill_id}');"  >开票</a>
 		                	</c:if>
 		                	<a   onclick="look_detail('${var.bill_id}','${var.billinfor_id}');"   >详情</a>
 		                </td>
 		             </tr> 
 	              </c:forEach>           
	          </table>
 	       </div>
	       <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;clear: both;">${page.pageStr}</div>
 	   </form>
 	   </c:if>
 	   <!--引入弹窗组件start-->
 	   <script src="js/bootstrap.min.js"></script>
  	   <script type="text/javascript" src="js/attention/zDialog/zDrag.js"></script>
	   <script type="text/javascript" src="js/attention/zDialog/zDialog.js"></script>
	   <script type="text/javascript" src="js/bootbox.min.js"></script><!-- 确认窗口 -->
       <script type="text/javascript">
		//检索
     	function checked(){
      		$("#Form").submit();//提交
     	}
		
     	//开票
		function change_status(bill_id){
     		var bill_number=$("#bill_number").val();
     		if(bill_number == ""){
     			alert("发票编号不能为空，请填写");
     			return;
     		}
			bootbox.confirm("确定开票吗?", function(result) {
				if(result) {
					var url = "fapiao/updatebill.do?bill_number="+bill_number+"&bill_id="+bill_id+"&chuli_status=1&tm="+new Date().getTime();
					$.get(url,function(data){
						if(data=="success"){
							nextPage(${page.currentPage});
						}
					});
				}
			});
		}
     	
		//查看详情
 		function look_detail(bill_id,billinfor_id){
 			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="查看详情";
			 diag.URL = 'fapiao/look_detail.do?bill_id='+bill_id+'&billinfor_id='+billinfor_id;
			 diag.Width = 800;
			 diag.Height = 320;
			 diag.CancelEvent = function(){ //关闭事件
				 if('${page.currentPage}' == '0'){
						 setTimeout("self.location.reload()",100);
				 }else{
					 nextPage(${page.currentPage});
				 }
				diag.close();
			 };
			 diag.show();
		}
		
       </script>
    </body>
</html>