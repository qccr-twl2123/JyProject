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
        <title>流水记录</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/ace.min.css" />
        <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/zhifu1.css">
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihuiReportForm/renmaiByStoreList.do" name="Form" id="Form" method="post">
    	<div class="dangan2_d1">
          <span  class="zhifu1_sp1">关键字查询</span>
          <input class="zhifu1_ipt1"  value="${pd.content}" type="text" name="content" id="content" placeholder="输入商家ID/商家名称关键字"></input>
          <span class="zhifu1_btn1" onclick="search()">查询</span>
        </div>
 	    <div>
 		       <div class="dangan2_d2">
		          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table"  style="white-space: nowrap;line-height:36px;width:96%">
		              <tr class="tdtop">
		                <td>支付时间</td>
		                <td>商家ID</td>
		                <td>商家名称</td>
 		                <td>一度人脉收益</td>
		                <td>二度人脉收益</td>
		                <td>昨日总收益</td>
		                <td>操作</td>
 		              </tr> 
 		              <c:forEach items="${systoreList}" var="var" varStatus="vs">
			              <tr >
			                <td>${var.createtime}</td>
			                <td>${var.store_id}</td>
			                <td>${var.store_name}</td>
			                <td>${var.firstcontact_money}</td>
			                <td>${var.twocontact_money}</td>
			                <td>${var.allmoney}</td>
			                <td><a onclick="detailThis('${var.store_renmaijf_id}')">详情</a></td>
 	 		              </tr>
		              </c:forEach>
 		          </table>
		          <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		       </div>
       		</div>
        </form>
        </c:if>
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
  	    <script type="text/javascript" src="js/attention/zDialog/zDrag.js"></script>
	    <script type="text/javascript" src="js/attention/zDialog/zDialog.js"></script>
        <script type="text/javascript">
       //查看详情
 		function detailThis(store_renmaijf_id){
 			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="查看详情";
			 diag.URL = 'zhihuiReportForm/look_detail.do?store_renmaijf_id='+store_renmaijf_id;
			 diag.Width = 1200;
			 diag.Height = 500;
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
       
 		//---------------------------
		//查询提交
			function search(){
 		    	$("#Form").submit();
		    }
	    </script>
    </body>
 </html>