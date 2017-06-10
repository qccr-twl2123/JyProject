<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>服务商业绩</title>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<link rel="stylesheet" href="css/ace.min.css" />
    <link href="css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="css/zhihui/zhifu5.css">
    <script src="js/jquery-1.8.0.min.js"></script>
    <script src="My97DatePicker/WdatePicker.js"></script>
	<style>
		html,body{
			height:100%;
		}
		.cont{
			height: 92%;
			display: none;
		}
		.zhifu1_sp1_span {
		    display: inline-block;
 		    text-align: right;
		    margin-left: 2.2%;
		    float: none;
		}
	</style>
</head>
<body>
 <c:if test="${qx.look eq '1'}">
	<ul class="nav nav-pills nav_item">
      <li role="presentation"><a onclick ="go_type('1')" >服务商月报表</a></li>
      <li role="presentation"><a onclick ="go_type('2')" >服务商服务费收入明细</a></li>
      <li role="presentation"><a onclick ="go_type('3')" >服务商积分收入明细</a></li>
      <li role="presentation"><a onclick ="go_type('4')" >服务商广告收入明细</a></li>
      <li role="presentation"><a onclick ="go_type('5')" >服务商提现申请及报表</a></li>
      <li role="presentation"><a onclick ="go_type('6')" >服务商交易扣点收入明细</a></li>
    </ul>
 	<div class="cont">
		<form action="zhihui_service_performance/list.do" name="oneForm" id="oneForm" method="post">
		  	   <input type="hidden" name="look_type" value="1">
		       <div class="dangan2_d1">
		          <span  class="zhifu1_sp1_span">服务商编号及名称</span>
		          <select name="sp_file_id" >
		          			<option value="">请选择</option>
			          		<c:forEach items="${spList}" var="var">
			          				<option value="${var.sp_file_id}" ${var.sp_file_id eq pd.sp_file_id?'selected':''} >${var.team_name} ${var.sp_file_id}</option>	
			          	    </c:forEach>
			      </select>
 		          <span class="zhifu1_btn1" onclick="onesearch()">查询</span>
 		       </div>
	 	       <div class="dangan2_d2">
		          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
		              <thead>
		              	 <tr class="tdtop">
			                 <td rowspan="2">年/月</td>
			                 <td colspan="2">商家数</td>
			                 <td colspan="2">会员数</td>
 			                 <td colspan="2" style="    background-color: green;">销售收入</td>
 			                 <td colspan="2" >订单交易流水</td>
			                 <td colspan="2" style="    background-color: green;">积分收入</td>
			                 <td colspan="2" style="    background-color: green;">交易扣点收入</td>
			                 <td colspan="2" style="    background-color: green;">广告及其他收入</td>
			                 <td colspan="2" style="    background-color: red;">收入累计</td>
			                 <td colspan="2">已提现收入</td>
			                 <td rowspan="2">未提现实时余额</td>
	 	 	              </tr>	
	 	 	              <tr>
	 	 	              	<td>新增</td>
	 	 	              	<td>累计</td>
	 	 	              	
	 	 	              	<td>新增</td>
	 	 	              	<td>累计</td>
	 	 	              	
	 	 	              	<td>新增</td>
	 	 	              	<td>累计</td>
	 	 	              	
	 	 	              	<td>新增</td>
	 	 	              	<td>累计</td>
	 	 	              	
	 	 	              	<td>新增</td>
	 	 	              	<td>累计</td>
	 	 	              	
	 	 	              	<td>新增</td>
	 	 	              	<td>累计</td>
	 	 	              	
	 	 	              	<td>新增</td>
	 	 	              	<td>累计</td>
	 	 	              	
	 	 	              	<td>新增</td>
	 	 	              	<td>累计</td>
	 	 	              	
	 	 	              	<td>笔数</td>
	 	 	              	<td>金额</td>
	 	 	              	
	 	 	              </tr>
		              </thead>
		              <tbody>
			              <c:forEach items="${varList}" var="var" varStatus="vs">
				              <tr >
						            <td>${var.month}</td>
						            
						            <td>${var.actual_user_number}</td>
			 	 	              	<td>${var.leiji_store}</td>
			 	 	              	
			 	 	              	<td>${var.add_member}</td>
			 	 	              	<td>${var.leiji_member}</td>
			 	 	              	
			 	 	              	<td  style="color: green;">${var.add_xiaoshou}</td>
			 	 	              	<td  style="color: green;">${var.leiji_xiaoshou}</td>
			 	 	              	
			 	 	              	<td>${var.actual_water}</td>
			 	 	              	<td>${var.leiji_water}</td>
			 	 	              	
			 	 	              	<td style="color: green;">${var.add_jifen}</td>
			 	 	              	<td style="color: green;">${var.leiji_jifen}</td>
			 	 	              	
			 	 	              	<td style="color: green;">${var.add_koudian}</td>
			 	 	              	<td style="color: green;">${var.leiji_koudian}</td>
			 	 	              	
			 	 	              	<td style="color: green;">${var.add_guanggao}</td>
			 	 	              	<td style="color: green;">${var.leiji_guanggao}</td>
			 	 	              	
			 	 	              	<td style="color: red;">${var.add_shouru}</td>
			 	 	              	<td style="color: red;">${var.leiji_shouru}</td>
			 	 	              	
			 	 	              	<td>${var.add_tixiannumber}</td>
			 	 	              	<td>${var.leiji_tixianmoney}</td>
			 	 	              	
			 	 	              	<td>${var.lastday_money}</td>
 				              </tr>
			              </c:forEach>
		              </tbody>
 		          </table>
		       </div>
		       <br/>
		       <div class="pagination" style="float:right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		  </form>
	</div>
	<div class="cont">
		<form action="zhihui_service_performance/list.do" name="twoForm" id="twoForm" method="post">
		  	   <input type="hidden" name="look_type" value="2">
		       <div class="dangan2_d1">
		          <span  class="zhifu1_sp1_span">时间段</span>
		          <input class="zhifu1_st1" type="text" name="starttime" id="starttime" value="${pd.starttime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间"/> 
		           <span  class="zhifu1_sp1_span">至</span>
		          <input class="zhifu1_st1" type="text" name="endtime" id="endtime" value="${pd.endtime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间"/>
		          <span  class="zhifu1_sp1_span">服务商编号及名称</span>
		          <select name="sp_file_id" >
		          			<option value="">请选择</option>
			          		<c:forEach items="${spList}" var="var">
			          				<option value="${var.sp_file_id}" ${var.sp_file_id eq pd.sp_file_id?'selected':''} >${var.team_name} ${var.sp_file_id}</option>	
			          	    </c:forEach>
			      </select>
 		          <span class="zhifu1_btn1" onclick="twosearch()">查询</span>
 		       </div>
	 	       <div class="dangan2_d2">
		          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
		              <tr class="tdtop">
		                 <td>发生时间</td>
		                 <td>订单号</td>
		                 <td>商家名称</td>
		                 <td>ID号</td>
	 	                 <td>发生金额</td> 
 	 	                 <td>赠送商家积分</td> 
	 	                 <td>实际金额</td> 
	 	                 <td>收入金额</td> 
	 	              </tr>
		              <c:forEach items="${varList}" var="var" varStatus="vs">
			              <tr >
			                <td>${var.createtime}</td>
		 	                <td>${var.waterrecord_id}</td>
		 	                <td>${var.store_name}</td>
		 	                <td>${var.user_id}</td>
			                <td>${var.money}</td>
 			                <td>${var.send_jf}</td>
			                <td>${var.less_money}</td>
			                <td> ${var.sp_getmoney}</td>
			              </tr>
		              </c:forEach>
		          </table>
		       </div>
		       <br/>
		       <div class="pagination" style="float:right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		  </form>
	</div>
	<div class="cont">
		<form action="zhihui_service_performance/list.do" name="threeForm" id="threeForm" method="post">
		  	   <input type="hidden" name="look_type" value="3">
		       <div class="dangan2_d1">
		          <span  class="zhifu1_sp1_span">时间段</span>
		          <input class="zhifu1_st1" type="text" name="starttime" id="starttime" value="${pd.starttime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间"/> 
		           <span  class="zhifu1_sp1_span">至</span>
		          <input class="zhifu1_st1" type="text" name="endtime" id="endtime" value="${pd.endtime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间"/>
		          <span  class="zhifu1_sp1_span">服务商编号及名称</span>
		          <select name="sp_file_id" >
		          			<option value="">请选择</option>
			          		<c:forEach items="${spList}" var="var">
			          				<option value="${var.sp_file_id}" ${var.sp_file_id eq pd.sp_file_id?'selected':''} >${var.team_name} ${var.sp_file_id}</option>	
			          	    </c:forEach>
			      </select>
 		          <span class="zhifu1_btn1" onclick="threesearch()">查询</span>
 		       </div>
	 	       <div class="dangan2_d2">
		          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
		              <tr class="tdtop">
		                 <td>发生时间</td>
		                 <td>订单号</td>
		                 <td>发生商家名称</td>
		                 <td>ID号</td>
	 	                 <td>消费者名称</td> 
	 	                 <td>ID号</td> 
	 	                 <td>发生金额</td> 
	 	                 <td>优惠金额</td> 
	 	                 <td>实际金额</td> 
	 	                 <td>赠送消费者积分</td> 
	 	                 <td>系统积分</td> 
	 	                 <td>代理商收入</td> 
	 	              </tr>
		              <c:forEach items="${varList}" var="var" varStatus="vs">
			              <tr >
			                <td>${var.createtime}</td>
		 	                <td>${var.waterrecord_id}</td>
		 	                <td>${var.store_name}</td>
		 	                <td>${var.payee_number}</td>
			                <td>${var.name}</td>
			                <td> ${var.user_id}</td>
			                <td> ${var.money}</td>
			                 <td>${var.reduce_money}</td>
			                <td>${var.arrivalmoney}</td>
			                <td> ${var.get_integral}</td>
			                <td> ${var.sendxitong_integral}</td>
			                <td> ${var.sp_getmoney}</td>
			              </tr>
		              </c:forEach>
		          </table>
		       </div>
		       <br/>
 		  </form>
 		  <div class="pagination" style="float:right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
	</div>
	<div class="cont">
		<form action="zhihui_service_performance/list.do" name="fourForm" id="fourForm" method="post">
		  	   <input type="hidden" name="look_type" value="4">
		       <div class="dangan2_d1">
		          <span  class="zhifu1_sp1_span">时间段</span>
		          <input class="zhifu1_st1" type="text" name="starttime" id="starttime" value="${pd.starttime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间"/> 
		           <span  class="zhifu1_sp1_span">至</span>
		          <input class="zhifu1_st1" type="text" name="endtime" id="endtime" value="${pd.endtime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间"/>
		          <span  class="zhifu1_sp1_span">服务商编号及名称</span>
		          <select name="sp_file_id" >
		          			<option value="">请选择</option>
			          		<c:forEach items="${spList}" var="var">
			          				<option value="${var.sp_file_id}" ${var.sp_file_id eq pd.sp_file_id?'selected':''} >${var.team_name} ${var.sp_file_id}</option>	
			          	    </c:forEach>
			      </select>
 		          <span class="zhifu1_btn1" onclick="foursearch()">查询</span>
 		       </div>
	 	       <div class="dangan2_d2">
		          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
		              <tr class="tdtop">
		                 <td>发生时间</td>
		                 <td>订单号</td>
		                 <td>订单类型</td>
		                 <td>收入对象名称</td>
		                 <td>收入对象ID号</td>
	 	                 <td>支出对象名称</td> 
	 	                 <td>支出对象ID号</td> 
   	 	                 <td>发生金额</td> 
   	 	                 <td>系统收入金额</td> 
   	 	                 <td>服务商收入金额</td> 
	 	              </tr>
		              <c:forEach items="${varList}" var="var" varStatus="vs">
			              <tr >
			                <td>${var.createtime}</td>
		 	                <td>${var.waterrecord_id}</td>
		 	                <td>
		 	                	<c:if test="${var.money_type eq '7' }">编辑优选费用</c:if>
		 	                	<c:if test="${var.money_type eq '8' }">上架优选费用</c:if>
		 	                	<c:if test="${var.money_type eq '13' }">广告费用</c:if>
		 	                </td>
		 	                <td>${var.store_name}</td>
		 	                <td>${var.user_id}</td>
			                <td>${var.team_name}</td>
			                <td> ${var.payee_number}</td>
			                <td> ${var.arrivalmoney}</td>
			                <td> ${var.xt_money}</td>
			                <td> ${var.sp_money}</td>
 			              </tr>
		              </c:forEach>
		          </table>
		       </div>
		       <br/>
		       <div class="pagination" style="float:right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		  </form>
	</div>
	<div class="cont">
	  <form action="zhihui_service_performance/list.do" name="fiveForm" id="fiveForm" method="post">
	  	   <input type="hidden" name="look_type" value="5">
	       <div class="dangan2_d1">
	          <span  class="zhifu1_sp1_span">时间段</span>
	          <input class="zhifu1_st1" type="text" name="starttime" id="starttime" value="${pd.starttime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间"/>
	           <span  class="zhifu1_sp1_span">至</span>
	          <input class="zhifu1_st1" type="text" name="endtime" id="endtime" value="${pd.endtime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间"/>
	       </div>
	       <div class="dangan2_d1">    
	          <span  class="zhifu1_sp1_span">服务商编号及名称</span>
		          <select name="sp_file_id" >
		          			<option value="">请选择</option>
			          		<c:forEach items="${spList}" var="var">
			          				<option value="${var.sp_file_id}" ${var.sp_file_id eq pd.sp_file_id?'selected':''} >${var.team_name} ${var.sp_file_id}</option>	
			          	    </c:forEach>
			      </select>
	          <span class="zhifu1_btn1" onclick="fivesearch()">查询</span>
	          <c:if test="${qx.add eq '1' and logininfor.login_type eq '1'}">
		           <span class="zhifu1_btn1" onclick="getmoney()">提现</span>
		           <span class="zhifu5_sp1">可提现金额： <span id="nowmoney">${sppd.nowmoney}</span>元</span>
		          <%--  <span class="zhifu5_sp1">未激活金额：  ${sppd.notmoney} 元</span> --%>
	          </c:if>
	       </div>
 	       <div class="dangan2_d2">
	          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
	              <tr class="tdtop">
	                 <td>发生时间</td>
	                 <td>订单号</td>
	                 <td>申请金额</td>
	                 <td>申请后的实时余额</td>
 	                 <td>提现状态</td> 
 	                 <td>累计提现</td> 
 	              </tr>
	              <c:forEach items="${varList}" var="var" varStatus="vs">
		              <tr >
		                <td>${var.createtime}</td>
	 	                <td>${var.waterrecord_id}</td>
	 	                <td>${var.money}</td>
	 	                <td>${var.nowuser_money}</td>
	 	                <td>
	 	                	<c:choose>
	 	                		<c:when test="${var.pay_status eq '1'}">已处理</c:when>
	 	                		<c:when test="${var.pay_status eq '99'}">驳回</c:when>
	 	                		<c:otherwise>正在处理</c:otherwise>
	 	                	</c:choose>
   	 	               </td>
		                <td>${var.summoney}</td>
 		              </tr>
	              </c:forEach>
	          </table>
	       </div>
	       <br/>
	       <div class="pagination" style="float:right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
	  </form>
 	</div>
	<div class="cont">
		<form action="zhihui_service_performance/list.do" name="sixForm" id="sixForm" method="post">
		  	   <input type="hidden" name="look_type" value="6">
		       <div class="dangan2_d1">
		          <span  class="zhifu1_sp1_span">时间段</span>
		          <input class="zhifu1_st1" type="text" name="starttime" id="starttime" value="${pd.starttime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间"/> 
		           <span  class="zhifu1_sp1_span">至</span>
		          <input class="zhifu1_st1" type="text" name="endtime" id="endtime" value="${pd.endtime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间"/>
		          <span  class="zhifu1_sp1_span">服务商编号及名称</span>
		          <select name="sp_file_id" >
		          			<option value="">请选择</option>
			          		<c:forEach items="${spList}" var="var">
			          				<option value="${var.sp_file_id}" ${var.sp_file_id eq pd.sp_file_id?'selected':''} >${var.team_name} ${var.sp_file_id}</option>	
			          	    </c:forEach>
			      </select>
 		          <span class="zhifu1_btn1" onclick="sixsearch()">查询</span>
 		       </div>
	 	       <div class="dangan2_d2">
		          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
		              <tr class="tdtop">
		                 <td>发生时间</td>
		                 <td>订单号</td>
		                 <td>商家名称</td>
		                 <td>消费者ID号</td>
	 	                 <td>发生金额</td> 
	 	                 <td>优惠金额</td> 
	 	                 <td>实际金额</td> 
	 	                 <td>交易扣点</td> 
	 	                 <td>系统收入</td> 
	 	                 <td>收入金额</td> 
	 	              </tr>
		              <c:forEach items="${varList}" var="var" varStatus="vs">
			              <tr >
			                <td>${var.createtime}</td>
		 	                <td>${var.waterrecord_id}</td>
		 	                <td>${var.store_name}</td>
		 	                <td>${var.user_id}</td>
			                <td>${var.money}</td>
			                <td>${var.reduce_money}</td>
			                <td>${var.arrivalmoney}</td>
			                <td>${var.transaction_points}</td>
			                <td>${var.orderfuwu_money}</td>
			                <td> ${var.ordersp_getmoney}</td>
			              </tr>
		              </c:forEach>
		          </table>
		       </div>
		       <br/>
		       <div class="pagination" style="float:right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		  </form>
	</div>
<script type="text/javascript">

	
    function onesearch(){
		  $("#oneForm").submit();
	}
	function twosearch(){
		  $("#twoForm").submit();
	}
	function threesearch(){
		  $("#threeForm").submit();
	}
	 function foursearch(){
		  $("#fourForm").submit();
	}
		function fivesearch(){
		  $("#fiveForm").submit();
	}
		function sixsearch(){
		  $("#sixForm").submit();
	}
	//显示隐藏移除
	var n=parseInt("${pd.look_type}")-1;
	$($(".nav_item li")[n]).attr("class","active");
	for (var i = 0; i < 6; i++) {
		if(i == n){
			$($(".cont")[i]).css("display","block");
		}else{
			$($(".cont")[i]).addClass("needremove")
		}
	}
	$(".needremove").remove();
	
	
	function go_type(type){
		  window.location.href="zhihui_service_performance/list.do?look_type="+type;
	}
</script>
</c:if>
</body>
</html>






