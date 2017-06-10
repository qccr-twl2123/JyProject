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
        <title>系统管理</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
		<link rel="stylesheet" href="css/ace.min.css" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/xitong7.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/xitong7.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
		<form action="zhihui_menu_marketing/list.do" id="Form" name="Form" method="post">
	       <div class="dangan2_d1">
 	          <!-- <a class="middle_a" href="xitong2.html"  target="ifra">
	           <span class="zhifu1_btn1">添加规则</span>
	          </a>   -->
	       </div>
	       <div class="dangan2_d2">
	          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
	              <tr >
	                <td>序号</td>
	                <td>商家名称</td> 
	                <td>营销规则</td>
	                <td>营销场景</td>
	                <!-- <td >操作</td> -->
	              </tr> 
	              <c:forEach items="${varList }" varStatus="vs" var ="var">
		              <tr >
		                <td>${vs.index +1 }</td>
		                <td>${var.store_name }</td>
		                <td>${var.grantrule }</td>
		                <td>
		                	<c:if test="${var.marketing_type eq '0'}"> 红包</c:if>
	                    	<c:if test="${var.marketing_type eq '1'}"> 满赠</c:if>
	                    	<c:if test="${var.marketing_type eq '2'}"> 满减</c:if>
	                    	<c:if test="${var.marketing_type eq '3'}"> 时段营销</c:if>
	                    	<c:if test="${var.marketing_type eq '4'}"> 买n减1</c:if>
	                    	<c:if test="${var.marketing_type eq '5'}"> 累计购买次数/总金额</c:if>
	                    	<c:if test="${var.marketing_type eq '6'}"> 积分方式</c:if>
	                    	<c:if test="${var.marketing_type eq '7'}"> 折扣</c:if>
		                </td>
		               <!--  <td>满100元减10</td> -->
		                <%-- <td >
		                  <c:if test="${qx.edit eq '1'}">
		                 	 	<span class="blue ">修改</span>
		                  </c:if>
		                  <c:if test="${qx.delete eq '1'}">
		                   		<span>删除</span>
		                   </c:if>
		                </td> --%>
		              </tr> 
	              </c:forEach>
	              <%-- <tr >
	                <td>0002</td>
	                <td>满赠</td>
	                <td>满xx元赠礼品</td>
	                <td>满100元赠雨伞</td>
	                <td >
	                  <span class="blue ">修改</span>
	                   <span>删除</span>
	                </td>
	              </tr> 
	              <tr >
	                <td>0001</td>
	                <td>满减</td>
	                <td>满xx元减xx</td>
	                <td>满100元减10</td>
	                <td >
	                  <span class="blue ">修改</span>
	                   <span>删除</span>
	                </td>
	              </tr> 
	              <tr >
	                <td>0002</td>
	                <td>满赠</td>
	                <td>满xx元赠礼品</td>
	                <td>满100元赠雨伞</td>
	                <td >
	                  <span class="blue ">修改</span>
	                   <span>删除</span>
	                </td>
	              </tr> 
	              <tr >
	                <td>0001</td>
	                <td>满减</td>
	                <td>满xx元减xx</td>
	                <td>满100元减10</td>
	                <td >
	                <c:if test="${qx.edit eq '1'}">
	                  <span class="blue ">修改</span>
	                  </c:if>
	                  <c:if test="${qx.delete eq '1'}">
	                   <span>删除</span>
	                   </c:if>
	                </td>
	              </tr>  --%>
	          </table>
	       </div>
	       <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
       </form>
       </c:if>
    </body>
</html>