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
        <title>报表</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/ace.min.css" />
        <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/zhifu3.css">
        <script src="js/jquery-1.8.0.min.js"></script>
         <script src="My97DatePicker/WdatePicker.js"></script>
         <script src="js/zhihui/zhifu3.js"></script>
        <script src="js/zhihui/bg.js"></script>
        <style type="text/css">
        	 .zhifu1_sp1 {
    		margin-left: 3%;
		}
        </style>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
     <form action="zhihuiReportForm/integralIncome.do" name="Form" id="Form" method="post">
       <div class="dangan2_d1" style="margin-right: 50px">
        <span  class="zhifu1_sp1" >时段</span>
		  <input placeholder="开始时间" class="zhifu1_st1" type="text" name="starttime" id="starttime" value="${pd.starttime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
          &nbsp;&nbsp;
          <span  class="zhifu1_sp1" >至</span>
		  <input placeholder="结束时间" class="zhifu1_st1" type="text" name="endtime" id="endtime" value="${pd.endtime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		   <span  class="zhifu1_sp1">付款方式</span>
         <select class="zhifu1_st1" name="pay_type">
           <option value="">全部</option>
           <option value="1" ${pd.pay_type eq '1'?'selected':''}>收银</option>
           <option value="2" ${pd.pay_type eq '2'?'selected':''}>优惠买单</option>
           <option value="3" ${pd.pay_type eq '3'?'selected':''}>提货券</option>
          </select>
       </div>
       <div class="dangan2_d1">
          <span  class="zhifu1_sp1">ID查询</span>
          <input class="zhifu1_ipt1" type="text" name="order_id" id="order_id" placeholder="输入关键字"></input>
          <span  class="zhifu1_sp1">关键字查询</span>
          <input class="zhifu1_ipt1" type="text" name="content" id="content" placeholder="输入关键字"></input>
          <span class="zhifu1_btn1" onclick="search()">查询</span>
        </div>
       
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table" style="width:100%">
              <tr class="tdtop">
              	<td rowspan="2">交易时间</td>
                <td rowspan="2">订单号</td>
                <td rowspan="2">省</td>
                <td rowspan="2">市</td>
                <td rowspan="2">县/区</td>
                <td rowspan="2">商家ID</td>
                <td rowspan="2">商家名称</td>
                <td rowspan="2">交易金额</td>
                <td rowspan="2">付款方式</td>
                <td rowspan="2">本次赠会员积分</td>
                <td rowspan="2">赠平台积分</td>
                <td colspan="15">其中</td>
              </tr>
              <tr><!-- 
                    <td>业务员ID</td>
                    <td>业务员名称</td>
                    <td>金额</td>
                    <td>服务商ID</td>
                    <td>服务商名称</td>
                    <td>金额</td> -->
                    <td>一度人脉ID</td>
                    <td>一度人脉名称</td>
                    <td>金额</td>
                    <td>二度人脉ID</td>
                    <td>二度人脉名称</td>
                    <td>金额</td>
                    <td>子公司ID</td>
                    <td>子公司名称</td>
                    <td>金额</td>
             </tr>
             <c:forEach items="${integraList}" var="var" varStatus="vs">
              <tr >
                <td>${var.over_time}</td>
                <td>${var.waterrecord_id }</td>
                <td>${var.province_name}</td>
				<td>${var.city_name}</td>
                <td>${var.area_name}</td>
                <td>${var.store_id }</td>
                <td>${var.store_name}</td>
                <td class="a">${var.actual_money}</td>
                <td>
                	<c:if test="${var.pay_type eq '1' }">当面付</c:if>
                	<c:if test="${var.pay_type eq '2' }">当面在线付</c:if>
                	<c:if test="${var.pay_type eq '3' }">提货券</c:if>
                	<c:if test="${var.pay_type eq '4' }">一元夺宝</c:if>
                	<c:if test="${var.pay_type eq '5' }">优选提货券</c:if>
                </td>
                <td class="b">${var.get_integral }</td>
                <td class="c">${var.sendxitong_integral }</td>
               <%--  <td>${var.clerk_file_id }</td>
                <td>${var.clerk_name }</td>
                <td class="d">${var.clerk_getmoney }</td> 
                <td>${var.sp_file_id }</td>
                <td>${var.team_name }</td>
                <td class="e">${var.sp_getmoney }</td>--%>
                 <td>${var.onecontacts_id }</td>
                 <td>${var.onename }</td>
                 <td class="f">${var.onecontacts_getmoney }</td>
                 <td>${var.twocontacts_id }</td>
                 <td>${var.twoname }</td>
                 <td class="g">${var.twocontacts_getmoney }</td>
                 <td>${var.subsidiary_id }</td>
                 <td>${var.subsidiary_name }</td>
                 <td class="h">${var.subsidiary_getmoney }</td>
               </tr>
              </c:forEach>
              <tr>
                	<td colspan="7">
                 		本页合计
                 	</td>
                 	<td style="color:red;">${nowsumpd.sumactual_money}</td>
                 	<td>/</td>
                	<td style="color:red;">${nowsumpd.sumget_integral}</td>
                	<td style="color:red;">${nowsumpd.sumsendxitong_integral}</td>
                	<%-- <td colspan="3" style="color:red;">${nowsumpd.sumclerk_getmoney }</td> 
                	<td colspan="3" style="color:red;">${nowsumpd.sumsp_getmoney }</td>--%>
                	<td colspan="3" style="color:red;">${nowsumpd.sumonecontacts_getmoney }</td>
                	<td colspan="3" style="color:red;">${nowsumpd.sumtwocontacts_getmoney }</td>
                	<td colspan="3" style="color:red;">${nowsumpd.sumsubsidiary_getmoney }</td>
                </tr>
              <tr>
                 <td colspan="7">
                 		总合计
                 	</td>
                 	<td style="color:red;">${allsumpd.sumactual_money}</td>
                 	<td>/</td>
                	<td style="color:red;">${allsumpd.sumget_integral}</td>
                	<td style="color:red;">${allsumpd.sumsendxitong_integral}</td>
                	<%-- <td colspan="3" style="color:red;">${allsumpd.sumclerk_getmoney }</td> 
                	<td colspan="3" style="color:red;">${allsumpd.sumsp_getmoney }</td>--%>
                	<td colspan="3" style="color:red;">${allsumpd.sumonecontacts_getmoney }</td>
                	<td colspan="3" style="color:red;">${allsumpd.sumtwocontacts_getmoney }</td>
                	<td colspan="3" style="color:red;">${allsumpd.sumsubsidiary_getmoney }</td>
               </tr>
           </table>
       </div>
       <br>
       <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
       </form>
       </c:if>
    </body>
    <script type="text/javascript">
 			
		//---------------------------
		//查询提交
			function search(){
 		    	$("#Form").submit();
		    }
  
     </script>
</html>