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
    	<form action="<%=basePath%>storepc_daoliu/daoliuList.do" name="Form" id="Form" method="post">
 	       <div class="dangan2_d1">
	         	 <span  class="zhifu1_sp1">时段 </span> 
		          <input placeholder="开始时间" class="zhifu1_st1" type="text" name="startdate" id="startdate" value="${pd.startdate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		          <span  class="zhifu1_sp1">至 </span>  
		          <input placeholder="结束时间" class="zhifu1_st1" type="text" name="enddate" id="enddate" value="${pd.enddate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
  				<span  class="zhifu1_sp1">排序方式</span> 
		         <select class="dangan2_d1_st1" name="paixu_type" >
		         		<option value="1" ${pd.paixu_type eq '1'?'selected':''}>点击时间排序</option>
		         		<option value="2" ${pd.paixu_type eq '2'?'selected':'' }>金额排序</option>
 		         </select>
  	       </div>
 	       <div class="dangan2_d1">
 	       		 <input class="dangan2_d1_st1" type="text" name="ci_content"  value="${pd.ci_content}"  placeholder="输入客户名称/客户ID进行检索"/>
  				<input class="dangan2_d1_st1" type="text" name="zhu_content"  value="${pd.zhu_content}"  placeholder="输入广告主名称/广告主ID进行检索"/>
	         	 
 				 <span class="zhifu1_btn1" onclick="checked()">查询</span>
 	       </div>
	       <div class="dangan2_d2">
	          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table"   style="    white-space: nowrap;line-height:36px">
	              <tr >
	                <td>序号</td>
	                <td>客户ID</td>
 	                <td>客户名称</td>
	                <td>点击时间</td>
	                <td>会员ID</td>
	                <td>广告主ID</td>
  	                <td>广告主名称</td>
  	                <td>广告收入</td>
  	                <td>广告主收入</td>
  	                <td>平台收入</td>
 	              </tr> 
	              <c:forEach items="${varList}" var="var" varStatus="vs">
	              	<tr>
		                <td>${vs.index+1}</td>
  	              		<td>${var.ci_store_id}</td>
  	              		<td>${var.ci_store_name}</td>
	              		<td>${var.createdate}</td>
	              		<td>${var.member_id}</td>
	              		<td>${var.zhu_store_id}</td>
	              		<td>${var.zhu_store_name}</td>
	              		<td>${var.all_fee}</td>
	              		<td>${var.store_fee}</td>
	              		<td>${var.xitong_fee}</td>
    		        </tr> 
 	              </c:forEach> 
 	              <tr >
	                <td colspan="7">本页合计</td>
	                <td>${sumnowpage.sumall_fee}</td>
	                <td>${sumnowpage.sumstore_fee}</td>
	                <td>${sumnowpage.sumxitong_fee}</td>
  	              </tr>  
 	              <tr >
	                <td colspan="7">总合计</td>
	                <td>${sumallpage.sumall_fee}</td>
	                <td>${sumallpage.sumstore_fee}</td>
	                <td>${sumallpage.sumxitong_fee}</td>
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