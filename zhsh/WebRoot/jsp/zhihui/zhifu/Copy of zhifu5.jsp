<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <title>服务商业绩</title>
        <meta charset="utf-8">
         <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
         <link rel="stylesheet" href="css/ace.min.css" />
         <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/zhifu5.css">
        <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/jquery-1.8.0.min.js"></script>
         <script type="text/javascript">
        	if("${message}" != ""){
        		alert("${message}");
        	}
        </script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
	    <form action="zhihui_service_performance/list.do" name="Form" id="Form" method="post">
	       <div class="dangan2_d1">
	          <span  class="zhifu1_sp1">查询时段</span>
	          <input class="zhifu1_st1" type="text" name="starttime" id="starttime" value="${pd.starttime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间"/>
	          <input class="zhifu1_st1" type="text" name="endtime" id="endtime" value="${pd.endtime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间"/>
	           <span class="zhifu1_btn1" onclick="search()">查询</span>
	           <c:if test="${qx.add eq '1' and logininfor.login_type eq '1'}">
		           <span class="zhifu1_btn1" onclick="getmoney()">提现</span>
		           <span class="zhifu5_sp1">该时段内的总金额： <span id="nowmoney">${sppd.nowmoney}</span>元</span>
		           <span class="zhifu5_sp1">未激活金额：  ${sppd.notmoney} 元</span>
	           </c:if>
	          </div>
	           <c:if test="${qx.add eq '1' and logininfor.login_type eq '1'}">
	         <div class="dangan2_d1">
	             <c:if test="${sppd.earnest_status eq '0' }">
	           		<span  class="zhifu1_sp1">待支付账单：</span>
	           	</c:if>
	           <span  class="zhifu1_sp1">保证金</span>
	           <input class="zhifu1_ipt1" type="text" id="paymoney"  value="${sppd.earnest_money}" />元
	           <c:if test="${sppd.earnest_status eq '0' }">
		           <c:if test="${qx.add eq '1'}">
		           		<span class="zhifu1_btn1" onclick="zhifu()">支付</span>
	           		</c:if>
	            </c:if>
	       </div>
	       </c:if>
	       <div class="dangan2_d2">
	          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
	              <tr class="tdtop">
	                 <td>订单号</td>
	                 <td>收益对象</td>
	                 <td>业务对象ID</td>
	                 <td>业务对象昵称</td>
	                 <td>业务类型</td>
	                <td>金额</td>
	                <td>款项类型</td>
	                <td>款项状态</td> 
	                <td>发生时间</td>
	              </tr>
	              <c:forEach items="${varList}" var="var" varStatus="vs">
		              <tr >
		                <td>${var.service_performance_id}</td>
	 	                <td>${var.profit_name}</td>
	 	                <td>${var.yewu_id}</td>
	 	                <td>${var.yewu_name}</td>
	 	                <td>
	 	                	<c:if test="${var.yewu_type eq '2'}">会员消费</c:if>
		 	                <c:if test="${var.yewu_type eq '1'}">业绩提成</c:if>
		 	                <c:if test="${var.yewu_type eq '3'}">公司奖励</c:if>
		 	                <c:if test="${var.yewu_type eq '0'}">提现</c:if>
	 	               </td>
		                <td>${var.money}</td>
		                <td>
							<c:if test="${var.money_type eq '1'}">销售提成</c:if>
		                	<c:if test="${var.money_type eq '2'}">积分收益</c:if>
		                	<c:if test="${var.money_type eq '3'}">平台奖励</c:if>
		                	<c:if test="${var.money_type eq '5'}">提现</c:if>
						</td>
						 <td>
							<c:if test="${var.isjihuo eq '0' && var.isshouyi eq '0'}"><span style="color:red;">未激活</span></c:if>
		                	<c:if test="${var.isjihuo eq '1' && var.isshouyi eq '0'}">已激活</c:if>
		                	<c:if test="${var.isjihuo eq '0' && var.isshouyi eq '1'}"><span style="color:red;">等待处理</span></c:if>
		                	<c:if test="${var.isjihuo eq '1' && var.isshouyi eq '1'}">已处理完成，注意查收短信</c:if>
	 	                	<c:if test="${var.isjihuo eq '99' && var.isshouyi eq '1' }">驳回</c:if>
						</td>  
		                <td> ${fn:substring(var.pay_time,0,19)}</td>
		              </tr>
	              </c:forEach>
	          </table>
	       </div>
	       <br/>
	      <div class="pagination" style="float:right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
	      </form>
      </c:if>
      </body>
        <script type="text/javascript">
        //查询提交
    	function search(){
    		$("#Form").submit();
    	}
        
        //提现
        function getmoney(){
        	var money=$("#nowmoney").text();//当前余额
        	if(nowmoney == "" || nowmoney == "0"){
         	 	alert("余额不足");
        	 	return;
        	 }
       		 window.location.href = '<%=path%>/zhihui_service_performance/goWithdraw.do?notmoney=${sppd.notmoney}&nowmoney='+money;
        }
        
        //支付
        function zhifu(){
        	var money=$("#nowmoney").text();//当前余额
        	window.location.href = '<%=path%>/zhihui_service_performance/zhifu.do?notmoney=${sppd.notmoney}&nowmoney='+money ;
        }
        
        //退换保证金
        function returnmoney(){
        	 var nowmoney=$("#nowmoney").text();//当前余额
        	 var money = $("#paymoney").val();
        	 if(money == null || money == "" || nowmoney == "" || nowmoney == null){
        	 	money = 0;
        	 	alert("保证金不足");
        	 	return;
        	 }
        	  window.location.href = '<%=path%>/zhihui_service_performance/goWithdraw.do?notmoney=${sppd.notmoney}&nowmoney='+nowmoney+'&money='+money; 
        }
    </script>
</html> --%>