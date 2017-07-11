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
        <title>总数据分析报表</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/ace.min.css" />
        <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/zhifu1.css">
        <link rel="stylesheet" href="css/zhihui/dangan1.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="My97DatePicker/WdatePicker.js"></script>
        <style type="text/css">
        td{
        	padding:5px;
        }
        </style>
     </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihuiBaoBiao/baoBiaoTypeTotol.do" name="Form" id="Form" method="post">
    	<div class="dangan1_d1">
	          <span class="dangan_d1_sp1">省</span>
	           <select class="dangan3_d1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
			 	  <option value="">请选择省</option>
			 	 <c:forEach items="${provincelist}" var="var">
					 <option value="${var.pcd_id}" ${pd.province_id eq var.pcd_id ?'selected':''}>${var.name}</option>
				 </c:forEach>
	           </select>
	          <span class="dangan_d1_sp1">市</span>
	          <select class="dangan3_d1_st1" name="city_id" id="city_id" onchange="addsearcharea();">
	             <option value="${pd.city_id}">${pd.city_name}</option>
	           </select>
	          <span class="dangan_d1_sp1">区/县</span>
	          <select class="dangan3_d1_st1" name="area_id" id="area_id">
	             <option value="${pd.area_id}">${pd.area_name}</option>
	           </select>
	    </div>
	    <input type="hidden" name="province_name" id="province_name" value="${pd.province_name}"  />
		<input type="hidden" name="city_name" id="city_name" value="${pd.city_name}"  />
		<input type="hidden" name="area_name" id="area_name" value="${pd.area_name}"  />
    	<div class="dangan2_d1">
    	  <%-- <span  class="zhifu1_sp1" >时段</span>
		  <input placeholder="开始时间" class="zhifu1_st1" type="text" name="starttime" id="starttime" value="${pd.starttime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
          &nbsp;&nbsp;
          <span  class="zhifu1_sp1" >至</span>
		  <input placeholder="结束时间" class="zhifu1_st1" type="text" name="endtime" id="endtime" value="${pd.endtime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
          --%>
        	<span  class="dangan_d1_sp1" >月份</span>
		  	<input placeholder="选择月份" class="zhifu1_st1" type="text" name="monthtime" id="monthtime" value="${pd.monthtime}" onclick="WdatePicker({dateFmt:'yyyy-MM'})"/>  
        	<span class="zhifu1_btn1" onclick="search()">查询</span>
        </div>
 	    <div>
 		       <div class="dangan2_d2">
		          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table"  style="white-space: nowrap;line-height:36px;width:96%">
		              <tr class="tdtop">
		                <td>序列号</td>
		                <td>款项类型</td>
		                <td>支付方式</td>
 		                <td>单数</td>
		                <td>支付宝总金额</td>
		                <td>银联总金额</td>
		                <td>微信总金额</td>
		                <td>九鱼总金额</td>
  		              </tr> 
 		              <c:forEach items="${bbList}" var="var" varStatus="vs">
			             	 <tr >
				                <td>${vs.index+1}</td>
				                <td>
				                	<c:if test="${var.money_type eq '1'}">充值</c:if>
				                	<c:if test="${var.money_type eq '2'}">消费</c:if>
				                	<c:if test="${var.money_type eq '3'}">保证金/交易扣点</c:if>
	 			                	<c:if test="${var.money_type eq '5'}">提现</c:if>
				                	<c:if test="${var.money_type eq '7'}">优选编辑费用</c:if>
	 			                </td>
				                <td>
				                	<c:if test="${var.remittance_type eq '1'}">银行卡</c:if>
	 			                	<c:if test="${var.remittance_type eq '3'}">支付宝</c:if>
	 			                	<c:if test="${var.remittance_type eq '4'}">微信</c:if>
				                	<c:if test="${var.remittance_type eq '6'}">九鱼平台</c:if>
				                </td>
				                <td>${var.typecount}</td>
				                <td class="number">${var.sumalipay_money}</td>
				                <td class="number">${var.sumbank_money}</td>
				                <td class="number">${var.sumwx_money}</td>
				                <td class="number">${var.sumjiuyu_money}</td>
     	 		           </tr>
		              </c:forEach>
 		          </table>
 		       </div>
       		</div>
       		</form>
       		<span  style="display: block;color:blue;font-size: 24px;">当月系统收支明细</span>
       		<table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table"  style="white-space: nowrap;line-height:36px;width:96%">
		              <tr class="tdtop">
		                <td>支付方式\￥</td>
		                <td>收入金额-单数</td>
		                <td>支出金额-单数</td>
 		                <td>合计总金额</td>
   		              </tr> 
 		              <tr >
				           <td>支付宝</td>
				           <td>${sumupalipay}  ----  ${countupalipay}单</td>
				           <td>${sumdownalipay} ----  ${countdownalipay}单</td>
				           <td style="color:green;">${allalipay}</td>
     	 		      </tr>
     	 		      <tr >
				           <td>微信</td>
				           <td>${sumupwx}  ----   ${countupwx}单</td>
				           <td>${sumdownwx}  ----   ${countdownwx}单</td>
				           <td style="color:green;">${allwx}</td>
     	 		      </tr>
     	 		      <tr >
				           <td>银行卡</td>
				           <td>${sumupbank}  ----  ${countupbank}单</td>
				           <td>${sumdownbank}  ----  ${countdownbank}单</td>
				           <td style="color:green;">${allbank}</td>
     	 		      </tr>
     	 		      <tr >
				           <td>九鱼网</td>
				           <td colspan="3">${sumjiuyu}和${countjiuyu}单</td>
     	 		      </tr>
     	 		      <tr><td colspan="4" >总金额： <span style="font-size: 24px;color:#df1ac5;"> ${allmoney}</span></td></tr>
 		     </table>
 		     <span style="display: block;color:blue;font-size: 24px;">当月九鱼账户收支明细</span>
       		 <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table"  style="white-space: nowrap;line-height:36px;width:96%">
		              <tr class="tdtop">
		                <td>支付方式\￥</td>
		                <td>总收入金额</td>
		                <td>收入支出服务费</td>
		                <td>支出金额</td>
 		                <td>支出服务费</td>
 		                <td>总支出金额</td>
 		                <td>合计收益</td>
   		              </tr> 
 		              <tr >
				           <td>支付宝</td>
				           <td  style="color:red">${jiuyualipayup}</td>
				           <td>${jiuyualipayupkou}</td>
				           <td>支付宝：${jiuyualipaydown}，银行卡：${jiuyubankdown }和${countdownbank}单</td>
     	 		      	   <td>${downfuwukou}</td>
     	 		      	   <td style="color:red">${jiuyualipayalldown}</td>
      	 		      	   <td style="color:green;">${jiuyualipay}</td>
     	 		      </tr>
     	 		      <tr >
				           <td>微信</td>
				           <td  style="color:red">${jiuyuwxup}</td>
				           <td>${jiuyuwxupkou}</td>
				           <td></td>
     	 		      	   <td></td>
     	 		      	   <td style="color:red">${jiuyuwxupkou}</td>
     	 		      	   <td style="color:green;">${jiuyuwx}</td>
     	 		      </tr>
      	 		      <tr><td colspan="7"  >总金额：  <span style="color:#df1ac5;font-size: 24px;">  ${alljiuyu} </span>  加上扣除的服务费 +${jiuyualipayupkou} +${downfuwukou }+${jiuyuwxupkou}  =  (${notkou})</td></tr>
 		     </table>
         </c:if>
      </body>
      <script type="text/javascript">
      //获取城市
		function addsearchfind(){
			var str=$("#province_id option:selected").val();//获取被选中的value值
			$("#city_name").val("");
			$("#area_name").val("");
			$("#province_name").val("");
			$("#city_id").empty();
		  	$("#area_id").empty();
		    $("#city_id").append("<option value=''>请选择市</option>");
		  	$("#area_id").append("<option  value=''>请选择区</option>");
 			$.ajax({
				  url: 'zhihuiBaoBiao/citylist.do',
				  data:"province_id="+str,
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.citylist;
 					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$("#city_id").append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
						  	}
					  	}
				  },
				  error:function(a){
				  	alert("异常");
				  }
			});
		}
			
		//获取区域
		function addsearcharea(){
			var str=$("#city_id option:selected").val();//获取被选中的value值
			$.ajax({
				  url: 'zhihuiBaoBiao/arealist.do',
				  data:"city_id="+str,
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.arealist;
					  	$("#area_id").empty();
					  	$("#area_id").append("<option  value=''>请选择区</option>");
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$("#area_id").append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
						  	}
				  		}
				  },
				  error:function(a){
				  	alert("异常");
				  }
			});
		}
		
		function search(){
			if($("#city_id option:selected").val() != ""){
				var city_name=$("#city_id option:selected").text();
				$("#city_name").show();
				$("#city_name").val(city_name);
			}
			if($("#province_id option:selected").val() != ""){
				var province_name=$("#province_id option:selected").text();
				$("#province_name").show();
				$("#province_name").val(province_name);
			}
			if($("#area_id option:selected").val() != ""){
				var area_name=$("#area_id option:selected").text();
				$("#area_name").show();
				$("#area_name").val(area_name);
			}
	    	$("#Form").submit();
	}
		
		
	$(function(){
		$(".number").each(function(i){
			   var number= $(this).html().trim();
			   if(parseFloat(number) != 0 ){
				   $(this).css("color","red");
			   }else{
				   $(this).html("/");
			   }
		});
	});
      </script>
 </html>