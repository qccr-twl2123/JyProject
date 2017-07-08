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
        <title>流水记录</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/ace.min.css" />
        <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/zhifu1.css">
         <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/zhifu1.js"></script>
        <script src="js/zhihui/bg.js"></script>
        <style type="text/css">
        td{
        	padding:2px;
        }
        .bluemoney{
        	color:blue;
        }
        .greenmoney{
        	color: green ;
        }
        .cengmoney{
        	color: #f59a07 ;
        }
        </style>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihuiWaterRecordController/liushuilist.do" name="Form" id="Form" method="post">
	    <div>
		       <div class="dangan2_d1">
		           <select class="zhifu1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
			           	<c:if test="${pd.province_id ne '' and  !empty pd.province_id}">
									<option value="${pd.province_id}"  selected="selected">${pd.province_name}</option>
						</c:if>
			           	<option value="">请选择省</option>
			           	<c:forEach items="${provincelist}" var="var" varStatus="vs">
							<option value="${var.pcd_id}">${var.name}</option>
						</c:forEach>
 		 	         </select>
			         <select class="zhifu1_st1" name="city_id" id="city_id" onchange="addsearcharea();">
			         	 <option value="${pd.city_id}">${pd.city_name}</option>
		 	         </select>
			         <select class="zhifu1_st1" name="area_id" id="area_id">
			         	 <option value="${pd.area_id}">${pd.area_name}</option>
		 	         </select>
		 	         <input type="hidden" name="province_name" id="province_name" value="${pd.province_name}"  />
				     <input type="hidden" name="city_name" id="city_name" value="${pd.city_name}"  />
				     <input type="hidden" name="area_name" id="area_name" value="${pd.area_name}"  />
		          <span  class="zhifu1_sp1">收款方</span>
			      <select name="payee_type" class="zhifu1_st1" id="payee_type">
			            <option value="">全部</option>
			            <option value="0" ${pd.payee_type eq '0'?'selected':''}>公司</option>
			            <option value="1" ${pd.payee_type eq '1'?'selected':''}>商家</option>
			            <%-- <option value="1" ${pd.payee_type eq '2'?'selected':''}>会员</option> --%>
 			  	      </select>
 		          <span  class="zhifu1_sp1">付款方</span>
		          <select name="user_type" class="zhifu1_st1" id="user_type" >
		           <option value="">全部</option>
		           <option value="1" ${pd.user_type eq '1'?'selected':''}>商家</option>
		           <option value="2" ${pd.user_type eq '2'?'selected':''}>会员</option>
		           <option value="3" ${pd.user_type eq '3'?'selected':''}>服务商</option>
		           <%-- <option value="3" ${pd.user_type eq '4'?'selected':''}>业务员</option> --%>
 		  	      </select>
 		  	   </div>
		       <%--<div class="dangan2_d1">
 		       		<!-- 1-app会员端，2-app商家端，3会员pc端，4-商家pc端，5-微信公众号端，6-总后台 -->
		       		<span  class="zhifu1_sp1">申请通道</span>
			         <select name="application_channel" class="zhifu1_st1" id="application_channel" >
			            <option value="">全部</option>
			            <option value="1" ${pd.application_channel eq '1'?'selected':''}>B端</option>
			            <option value="2" ${pd.application_channel eq '2'?'selected':''}>C端</option>
			            <option value="3" ${pd.application_channel eq '3'?'selected':''}>PC会员端</option>
	           			<option value="4" ${pd.application_channel eq '4'?'selected':''}>PC商家端</option>
	           			<option value="5" ${pd.application_channel eq '5'?'selected':''}>微信端</option>
	           			<option value="6" ${pd.application_channel eq '6'?'selected':''}>PC总后台</option>
			  	      </select>
		       		 <!-- 1-银联支付，2- 现金支付（为0的情况下），3-支付宝支付，4-微信支付，5-苹果支付 -->
		       		<span  class="zhifu1_sp1">支付方式</span>
			         <select name="remittance_type" class="zhifu1_st1" id="remittance_type" >
			            <option value="">全部</option>
			            <option value="1" ${pd.remittance_type eq '1'?'selected':''}>银联支付</option>
			            <option value="2" ${pd.remittance_type eq '2'?'selected':''}>现金支付</option>
			            <option value="3" ${pd.remittance_type eq '3'?'selected':''}>支付宝支付</option>
	           			<option value="4" ${pd.remittance_type eq '4'?'selected':''}>微信支付</option>
	           			<option value="5" ${pd.remittance_type eq '5'?'selected':''}>苹果支付</option>
			  	      </select> 
			  	      <!-- 1-银联支付，2- 现金支付（为0的情况下），3-支付宝支付，4-微信支付，5-苹果支付 -->
		       		<span  class="zhifu1_sp1">支付方式</span>
			         <select name="remittance_type" class="zhifu1_st1" id="remittance_type" >
			            <option value="">全部</option>
			            <option value="1" ${pd.remittance_type eq '1'?'selected':''}>银联支付</option>
			            <option value="2" ${pd.remittance_type eq '2'?'selected':''}>现金支付</option>
			            <option value="3" ${pd.remittance_type eq '3'?'selected':''}>支付宝支付</option>
	           			<option value="4" ${pd.remittance_type eq '4'?'selected':''}>微信支付</option>
	           			<option value="5" ${pd.remittance_type eq '5'?'selected':''}>苹果支付</option>
	           			<option value="6" ${pd.remittance_type eq '6'?'selected':''}>平台支付</option>
			  	      </select> 
  			  </div>--%>
 			  <div class="dangan2_d1">
 			      <span  class="zhifu1_sp1">时段 </span> 
		          <input placeholder="开始时间" class="zhifu1_st1" type="text" name="starttime" id="starttime" value="${pd.starttime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		          <span  class="zhifu1_sp1">至 </span>  
		          <input placeholder="结束时间" class="zhifu1_st1" type="text" name="endtime" id="endtime" value="${pd.endtime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		          <span  class="zhifu1_sp1">月份 </span>  
		          <input placeholder="结束时间" class="zhifu1_st1" type="text" name="month" id="month" value="${pd.month}" onclick="WdatePicker({dateFmt:'yyyy-MM'})"/>
		  	  	   <!-- 1-银联支付，2- 现金支付（为0的情况下），3-支付宝支付，4-微信支付，5-苹果支付 -->
		       	  <span  class="zhifu1_sp1">支付方式</span>
			      <select name="remittance_type" class="zhifu1_st1" id="remittance_type" >
			            <option value="">全部</option>
			            <option value="1" ${pd.remittance_type eq '1'?'selected':''}>银联支付</option>
			            <option value="2" ${pd.remittance_type eq '2'?'selected':''}>现金支付</option>
			            <option value="3" ${pd.remittance_type eq '3'?'selected':''}>支付宝支付</option>
	           			<option value="4" ${pd.remittance_type eq '4'?'selected':''}>微信支付</option>
	           			<option value="5" ${pd.remittance_type eq '5'?'selected':''}>苹果支付</option>
	           			<option value="6" ${pd.remittance_type eq '6'?'selected':''}>平台支付</option>
			  	  </select> 
		  	  </div>
		  	  <div class="dangan2_d1">
		  	      <!-- 1-充值（商家或会员充积分），2-消费，3-商家购买保证金，4-服务商支付保证金 -->
 		  	      <span  class="zhifu1_sp1">款项类型</span>
		          <select name="money_type" class="zhifu1_st1" id="money_type" >
			           <option value="">全部</option>
			           <option value="1" ${pd.money_type eq '1'?'selected':''}>充值（商家或会员充积分）</option>
			           <option value="2" ${pd.money_type eq '2'?'selected':''}>消费</option>
			           <option value="3" ${pd.money_type eq '3'?'selected':''}>商家购买支付保证金</option>
	           		   <option value="4" ${pd.money_type eq '4'?'selected':''}>服务商支付保证金</option>
	           		   <option value="5" ${pd.money_type eq '5'?'selected':''}>提现</option>
	           		   <option value="7" ${pd.money_type eq '7'?'selected':''}>优选商品编辑费用</option>
	           		   <option value="18" ${pd.money_type eq '18'?'selected':''}>商品超出数量收费</option>
	           		   <option value="19" ${pd.money_type eq '19'?'selected':''}>服务商月销售收益</option>
              	  </select>
              	  <span class="zhifu1_btn1" onclick="search()">查询</span>
		          <span class="zhifu1_btn1" onclick="daochuexcel()" style="width: 120px;">导出excel表格</span>
   		       </div>
		       <div class="dangan2_d2">
		          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table"  style="    white-space: nowrap;line-height:36px">
		              <tr class="tdtop">
		                <td  rowspan="2">序号</td>
		                <td  rowspan="2">完成时间</td>
		                <td  rowspan="2">订单号</td>
		                <td  colspan="3">付款方</td>
 		                <td rowspan="2">款项类型</td>
		                <td rowspan="2">发生金额</td>
		                <td rowspan="2">优惠金额/系统扣费</td>
		                <td rowspan="2">不优惠金额</td>
		                <td rowspan="2">实际金额</td>
		                <td colspan="7">付款明细</td>
		                <td rowspan="2">支付类型</td>
		                <td rowspan="2">支付渠道</td>
		                <td rowspan="2">支付账号</td>
		                <td colspan="4">平台收益</td>
		                <td rowspan="2">赠送积分</td>
 		                <td rowspan="2">结余</td>
 		                <td colspan="3">收款方</td>
 		                <td rowspan="2">付款所在区域</td>
		              </tr> 
		              <tr>
	                    <td>用户类型</td>
	                    <td>用户名称</td>
	                    <td>用户ID号</td>
	                    
	                    <td>现金</td>
	                    <td>支付宝</td>
	                    <td>微信</td>
	                    <td>银行卡</td>
	                    <td>积分</td>
	                    <td>余额</td>
	                    <td>平台支付</td>
	                    
	                    <td>推广积分</td>
 	                    <td>子公司</td>
	                    <!-- <td>服务商</td> -->
 	                    <td>一度人脉</td>
	                    <td>二度人脉</td>
	                    
	                    <td>收款方</td>
		                <td>收款方ID</td>
		                <td>收款方名称</td>
	                  </tr> 
		              <c:forEach items="${varList}" var="var" varStatus="vs">
		              <tr >
		                <td>${vs.index+1}</td>
		                <td>${var.over_time}</td>
		                <td>${var.waterrecord_id}</td>
		                <td>
			                <c:if test="${var.user_type eq '1'}">商家 </c:if>
			                <c:if test="${var.user_type eq '2'}">会员 </c:if>
			                <c:if test="${var.user_type eq '3'}">服务商 </c:if>
			                <c:if test="${var.user_type eq '4'}">业务员 </c:if>
  		                </td>
 		                <td>  ${var.name} </td>
		                <td> ${var.user_id} </td>
		                <td>
		               			<c:if test="${var.money_type eq '1'}">充值（商家或会员充积分） </c:if>
			                	<c:if test="${var.money_type eq '2'}">消费 </c:if>
			                	<c:if test="${var.money_type eq '3'}">商家购买保证金</c:if>
			                	<c:if test="${var.money_type eq '4'}">服务商支付保证金</c:if>
			                	<c:if test="${var.money_type eq '5'}">提现</c:if>
			                	<c:if test="${var.money_type eq '6'}">退款</c:if>
			                	<c:if test="${var.money_type eq '7'}">优选商品编辑费用</c:if>
			                	<c:if test="${var.money_type eq '10'}">发送积分红包</c:if>
			                	<c:if test="${var.money_type eq '11'}">抢积分红包</c:if>
			                	<c:if test="${var.money_type eq '12'}">退还积分红包</c:if>
		                </td>
 		                <td class="money">${var.money}</td>
		                <td>${var.reduce_money}</td>
		                <td>${var.no_discount_money}</td>
		                <td class="bluemoney" >${var.arrivalmoney}</td>
		                <td>${var.nowypay_money} </td>
		                <td class="cengmoney">${var.alipay_money} </td>
		                <td class="cengmoney">${var.wx_money} </td>
		                <td class="cengmoney">${var.bank_money} </td>
 		                <td> ${var.user_integral} </td>
		                <td> ${var.user_balance} </td>
		                <td class="greenmoney" > ${var.jiuyu_money} </td>
 		                <td> 
		                		<c:if test="${var.jiaoyi_type eq '0'}">/</c:if>
		                		<c:if test="${var.jiaoyi_type eq '1'}">当面付</c:if>
			                	<c:if test="${var.jiaoyi_type eq '2'}">当面付</c:if>
			                	<c:if test="${var.jiaoyi_type eq '3'}">提货券</c:if>
			                	<c:if test="${var.jiaoyi_type eq '4'}">/</c:if>
			                	<c:if test="${var.jiaoyi_type eq '5'}">优选提货券</c:if>
			                	<c:if test="${var.jiaoyi_type eq '6'}">/</c:if>
			            </td>
 		                <td>
		                  	    <c:if test="${var.application_channel eq '1'}">C端</c:if>
			                	<c:if test="${var.application_channel eq '2'}">B端 </c:if>
			                	<c:if test="${var.application_channel eq '3'}">PC会员端</c:if>
			                	<c:if test="${var.application_channel eq '4'}">PC商家端</c:if>
			                	<c:if test="${var.application_channel eq '5'}">微信端</c:if>
			                	<c:if test="${var.application_channel eq '6'}">PC总后台</c:if>
		                </td>
		                <td> ${var.remittance_number} 
		                		<%-- <c:if test="${var.remittance_type eq '1'}">银联支付</c:if>
			                	<c:if test="${var.remittance_type eq '2'}">现金支付</c:if>
			                	<c:if test="${var.remittance_type eq '3'}">支付宝支付</c:if>
			                	<c:if test="${var.remittance_type eq '4'}">微信支付</c:if>
			                	<c:if test="${var.remittance_type eq '5'}">苹果支付</c:if> --%>
			            </td>
		                <td> ${var.sendxitong_integral} </td>
		                <td> ${var.subsidiary_getmoney} </td>
		                <%-- <td> ${var.sp_getmoney} </td> --%>
 		                <td> ${var.onecontacts_getmoney} </td>
		                <td> ${var.twocontacts_getmoney} </td>
		                <td> ${var.get_integral} </td> 
		                <td class="money"> ${var.nowuser_money} </td> 
 		                <td>
 		                		<c:if test="${var.payee_type eq '0'}">九鱼平台 </c:if>
			                	<c:if test="${var.payee_type eq '1'}">商家</c:if>
			                	<c:if test="${var.payee_type eq '2'}">会员</c:if>
		                </td>
		                <td>${var.payee_number }<!-- 收款方 ID --></td>
		                <td>${var.payee_name }<!-- 收款方 名称 --></td>
  		                <td>${var.province_name}${var.city_name}${var.area_name}</td>
		              </tr>
		              </c:forEach>
		               <tr>
		               		<td colspan="6">本页合计</td>
		               		<td class="money">${nowpagesum.summoney}</td>
			                <td>${nowpagesum.sumreduce_money}</td>
			                <td>${nowpagesum.sumno_discount_money}</td>
			                <td class="bluemoney">${nowpagesum.sumarrivalmoney}</td>
		                 	<td> ${nowpagesum.sumnowypay_money} </td>
		                 	<td class="cengmoney"> ${nowpagesum.sumalipay_money} </td>
			                <td class="cengmoney"> ${nowpagesum.sumwx_money} </td>
			                <td class="cengmoney"> ${nowpagesum.sumbank_money} </td>
 			                <td> ${nowpagesum.sumuser_integral} </td>
			                <td> ${nowpagesum.sumuser_balance} </td>
			                <td class="greenmoney"> ${nowpagesum.sumjiuyu_money} </td>
			                <td colspan="3"></td>
			                <td> ${nowpagesum.sumsendxitong_integral} </td>
			                <td> ${nowpagesum.sumsubsidiary_getmoney} </td>
			                <%-- <td> ${nowpagesum.sumsp_getmoney} </td> --%>
 			                <td> ${nowpagesum.sumonecontacts_getmoney} </td>
			                <td> ${nowpagesum.sumtwocontacts_getmoney} </td>
			                <td> ${nowpagesum.sumget_integral} </td> 
			                <td colspan="5"></td>
			           </tr>
			           <tr>
			           		<td colspan="6">总合计</td>
			                <td class="money">${allpagesum.summoney}</td>
			                <td>${allpagesum.sumreduce_money}</td>
			                <td>${allpagesum.sumno_discount_money}</td>
			                <td class="bluemoney">${allpagesum.sumarrivalmoney}</td>
			                <td> ${allpagesum.sumnowypay_money} </td>
		                 	<td class="cengmoney"> ${allpagesum.sumalipay_money} </td>
			                <td class="cengmoney"> ${allpagesum.sumwx_money} </td>
			                <td class="cengmoney"> ${allpagesum.sumbank_money} </td>
 			                <td> ${allpagesum.sumuser_integral} </td>
			                <td> ${allpagesum.sumuser_balance} </td>
			                <td class="greenmoney"> ${allpagesum.sumjiuyu_money} </td>
			                <td colspan="3"></td>
			                <td> ${allpagesum.sumsendxitong_integral} </td>
			                <td> ${allpagesum.sumsubsidiary_getmoney} </td>
			               <%--  <td> ${allpagesum.sumsp_getmoney} </td> --%>
 			                <td> ${allpagesum.sumonecontacts_getmoney} </td>
			                <td> ${allpagesum.sumtwocontacts_getmoney} </td>
			                <td> ${allpagesum.sumget_integral} </td> 
			                <td colspan="5"></td>
			          </tr>
		          </table>
		          <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		       </div>
       		</div>
        </form>
        </c:if>
        <script type="text/javascript">
        $(".money").each(function(n,obj){
        	if($(obj).text().trim() != ""){
        		if(parseFloat($(obj).text().trim()) < 0){
            		$(obj).css("color","red");
            	}
        	}
         });
 	    //获取城市
			function addsearchfind(){
				var str=$("#province_id option:selected").val();//获取被选中的value值
				$.ajax({
					  url: '<%=path%>/zhihui_subsidiary/citylist.do',
					  data:"province_id="+str,
					  type:"post",
					  dataType:"json",
					  success:function(data){
						  	var list=data.citylist;
	 					  	$("#city_id option").remove();
	 					  	$("#area_id option").remove();
	 					  	$("#city_name").val("");
	 					  	$("#area_name").val("");
	 					  	$("#city_id").append("<option value=''>请选择市</option>");
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
					  url: '<%=path%>/zhihui_subsidiary/arealist.do',
					  data:"city_id="+str,
					  type:"post",
					  dataType:"json",
	 				  success:function(data){
						  	var list=data.arealist;
						  	$("#area_id option").remove();
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
			
			//---------------------------提交----------
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
			
			//导出excel表格
			function daochuexcel(){
				var city_name=$("#city_name").val();//$("#city_id option:selected").text()
				var province_name=$("#province_name").val();
				var area_name=$("#area_name").val();
 				var user_type=$("#user_type").val();
				var money_type=$("#money_type").val();
				var starttime=$("#starttime").val();
				var endtime=$("#endtime").val();
 				var payee_type=$("#payee_type").val();
				//导出excel
		 		window.location.href='<%=basePath%>zhihuiWaterRecordController/exportExcel.do?province_name='+province_name+
		 				'&city_name='+city_name+'&area_name='+area_name+'&user_type='+user_type+
		 				'&money_type='+money_type+'&starttime='+starttime+'&endtime='+endtime+
		 				'&payee_type='+payee_type;
			}	
	    </script>
    </body>
 </html>