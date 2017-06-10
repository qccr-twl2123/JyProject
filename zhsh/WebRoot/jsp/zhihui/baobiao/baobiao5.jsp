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
        <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/bg.js"></script>
        <style type="text/css">
         a{cursor: pointer;color:blue;}
        .dangan2_d1_st1{
		    width: 19%;
		    height: 40%;
		    margin-top: 1%;
		    margin-left: 2%;
		}
        </style>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    	<form action="<%=basePath%>youxuan/datalistPageGoodsFee.do" name="Form" id="Form" method="post">
 	       <div class="dangan2_d1">
	         	 <span  class="zhifu1_sp1">时段 </span> 
		          <input placeholder="开始时间" class="zhifu1_st1" type="text" name="startdate" id="startdate" value="${pd.startdate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		          <span  class="zhifu1_sp1">至 </span>  
		          <input placeholder="结束时间" class="zhifu1_st1" type="text" name="enddate" id="enddate" value="${pd.enddate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
  				<input class="dangan2_d1_st1" type="text" name="content"  value="${pd.content}"  placeholder="输入商品名称/商家名称进行检索"/>
 				<span class="zhifu1_btn1" onclick="checked()">查询</span>
 	       </div>
	       <div class="dangan2_d2">
	          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table"   style="    white-space: nowrap;line-height:36px">
	              <tr >
	                <td>序号</td>
	                <td>支付时间</td>
 	                <td>商家名称</td>
	                <td>商家ID号</td>
	                <td>商品名称</td>
	                <td>商品编号</td>
  	                <td>编辑费用</td>
 	              </tr> 
	              <c:forEach items="${goodslist}" var="var" varStatus="vs">
	              	<tr>
		                <td>${vs.index+1}</td>
  	              		<td>${var.paytime}</td>
  	              		<td>${var.store_name}</td>
	              		<td>${var.store_id}</td>
	              		<td>${var.goods_name}</td>
	              		<td>${var.youxuangoods_id}</td>
	              		<td>${var.bianji_money}</td>
    		             </tr> 
 	              </c:forEach> 
 	              <tr >
	                <td colspan="6">本页合计</td>
	                <td>${nowpagesum.sumbianji_money}</td>
  	              </tr>  
 	              <tr >
	                <td colspan="6">总合计</td>
	                <td>${allpagesum.sumbianji_money}</td>
  	              </tr>           
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