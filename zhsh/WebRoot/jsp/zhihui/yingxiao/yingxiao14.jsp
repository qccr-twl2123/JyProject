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
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    	<form action="<%=basePath%>oneYuan/datalistPageGoods.do" name="Form" id="Form" method="post">
 	       <div class="dangan2_d1">
	         	<span  class="zhifu1_sp1">活动状态</span>
	         	<select class="dangan2_d1_st1" name="isover" id="isover"  >
				    <option value="">全部</option>
				    <option value="0" ${pd.isover eq '0'?'selected':'' }>正在进行</option>
				    <option value="99"  ${pd.isover eq '99'?'selected':'' }>已结束</option>
				</select>
				<input class="zhifu1_ipt1" type="text" placeholder="可输入商品名称检索" name="content" id="content" value="${pd.content}"></input>
 	            <span class="zhifu1_btn1" onclick="checked()">查询</span>
 	            <c:if test="${qx.add eq '1'}"><a class="zhifu1_btn1" href="<%=basePath%>oneYuan/goAddGoods.do">新增</a></c:if>
	       </div>
	       <div class="dangan2_d2">
	          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
	              <tr >
	                <td>商品编号</td>
	                <td>商品图片</td>
	                <td>商品名称</td>
	                <td>所需金额</td>
	                <td>已经购买金额</td>
	                <td>比例</td>
	                <td>活动状态</td>
	                <td>期数</td>
	                <td>操作</td>
	              </tr> 
	              <c:forEach items="${goodslist}" var="var" varStatus="vs">
	              	<tr>
		                <td>${var.oneyuangoods_id}</td>
	              		<td><img src="${var.oneyuangoodsimage_url}"  width="60px"></td>
	              		<td>${var.oneyuangoods_name}</td>
	              		<td>${var.oneyuangoodsneed_quantity}</td>
	              		<td>${var.oneyuangoodsnowpay_quantity}</td>
	              		<td>${var.oneyuangoodsrate}</td>
 	              		<td>
	              			<c:if test="${var.isover eq '0'}">正在进行</c:if>
	              			<c:if test="${var.isover eq '99'}"><span style="color:red;">已结束</span></c:if>
	              		</td>
	              		<td>${var.nowtimes}</td>
		                <td>
		                	<c:if test="${qx.edit eq '1'}"><a  href="<%=basePath%>oneYuan/goEditGoods.do?oneyuangoods_id=${var.oneyuangoods_id}&currentPage=${page.currentPage}" >修改/更换期数</a></c:if>
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
     	function checked(){
     		$("#Form").submit();//提交
     	}
     	
       </script>
    </body>
</html>