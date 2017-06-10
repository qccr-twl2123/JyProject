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
        <link rel="stylesheet" href="css/zhihui/shixiang6.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihiu/shixiang6.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihui_keyword_reply/list.do" id="Form" name="Form" method="post">
    	<br/>
       <div class="dangan2_d1">
	         <span  class="zhifu1_sp1">选择用户类型</span>
	         <select class="zhifu1_st1" name="target_type">
	           <option>全部</option>
	           <option value="1">商家</option>
	           <option value="2">会员</option>
	         </select>
 	         <input type="text" name="content"  placeholder="输入关键字搜索"  class="dangan2_d1_ipt1" />
	         <span class="zhifu1_btn1" onclick="sreach()">查询</span>
	         <c:if test="${qx.add eq '1'}">
	         <a class="middle_a" href="zhihui_keyword_reply/goAdd.do"  target="ifra">
	           <span class="zhifu1_btn1">添加</span>
	         </a>
	         </c:if>
       </div>
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr >
                <td>序号</td>
                <td>关键字</td>
                <td>用户类型</td>
                <td>内容</td>
                <td></td>  
              </tr> 
              <c:forEach items="${varList }" var="var" varStatus="vs">
              <tr >
                <td>${vs.index+1 }</td>
                <td>${var.keyword }</td>
                <td>
                <c:if test="${var.target_type eq '2' }">会员</c:if>
                <c:if test="${var.target_type eq '1' }">商家</c:if>
                </td>
                
                <td>${var.content }</td>
                <td >
                <c:if test="${qx.edit eq '1'}">
                  <a href="zhihui_keyword_reply/goEdit.do?keyword_reply_id=${var.keyword_reply_id }&currentPage=${page.currentPage}">
                  <span>修改</span>
                  </a>
                  </c:if>
                  <c:if test="${qx.delete eq '1'}">
                  <a href="zhihui_keyword_reply/delete.do?keyword_reply_id=${var.keyword_reply_id }&currentPage=${page.currentPage}">
                   <span>删除</span>
                 </a>
                 </c:if>
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
    	function sreach(){
    	$("#Form").submit();
    	}
    </script>
</html>