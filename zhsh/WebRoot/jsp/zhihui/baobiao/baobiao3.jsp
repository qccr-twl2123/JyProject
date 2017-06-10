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
         <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/jquery-1.8.0.min.js"></script>
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
     <form action="zhihuiReportForm/serviceProviderBond.do" name="Form" id="Form" method="post">
       <div class="dangan2_d1" style="margin-right: 50px">
        <span  class="zhifu1_sp1" >订单日期</span>
		  <input placeholder="开始时间" class="zhifu1_st1" type="text" name="starttime" id="starttime" value="${pd.starttime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
          &nbsp;&nbsp;
          <span  class="zhifu1_sp1" >至</span>
		  <input placeholder="结束时间" class="zhifu1_st1" type="text" name="endtime" id="endtime" value="${pd.endtime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
       </div>
       <div class="dangan2_d1">
          <span  class="zhifu1_sp1">关键字查询</span>
          <input class="zhifu1_ipt1" type="text" name="content" id="content" placeholder="输入关键字"></input>
          <span class="zhifu1_btn1" onclick="search()">查询</span>
        </div>
       
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table" style="width:100%">
              <tr class="tdtop">
              	<td>日期</td>
                <td>订单号</td>
                <td>子公司ID</td>
                <td>子公司名称</td>
                <td>省</td>
                <td>市</td>
                <td>县/区</td>
                <td>服务商ID</td>
                <td>服务商名称</td>
                <td>合同签约日期</td>
                <td>合同开始日期</td>
                <td>保证金数额</td>
              </tr>
             <c:forEach items="${providerBondList}" var="var" varStatus="vs">
              <tr >
                <td>${var.over_time }</td>
                <td>${var.waterrecord_id }</td>
                <td>${var.subsidiary_id }</td>
                <td>${var.subsidiary_name }</td>
                <td>${var.province_name}</td>
                <td>${var.city_name}</td>
                <td>${var.area_name}</td>
                <td>${var.sp_file_id }</td>
                <td>${var.team_name }</td>
                <td>${var.sign_time}</td>
                <td>${var.start_time}</td>
                <td class="tx_money">${var.money }</td>
               </tr>
              </c:forEach>
              <tr>
                 <td colspan="11">
                 	本页合计  保证金总额
                  </td>
                  <td><span  style="color:red;" class="benye_money">${nowsumpd.summoney }</span></td>
               </tr>
              <tr>
                 <td colspan="11">
                 	总合计  保证金总额
                 </td>
                   <td><span style="color:red;" class="all_money">${allsumpd.summoney }</span></td>
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
				if($("#city_id option:selected").val() != ""){
					var city_name=$("#city_id option:selected").text();
					$("#city_name").show();
					$("#city_name").val(city_name);
				}
				if($("#province_id option:selected").val() != ""){
					var province_name=$("#province_id option:selected").text();
					$("#c").show();
					$("#province_name").val(province_name);
				}
				if($("#area_id option:selected").val() != ""){
					var area_name=$("#area_id option:selected").text();
					$("#area_name").show();
					$("#area_name").val(area_name);
				}
		    	$("#Form").submit();
		    }
     </script>
</html>