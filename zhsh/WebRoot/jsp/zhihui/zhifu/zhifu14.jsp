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
        <title>销售明细记录</title>
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
        	padding:6px;
        }
        .moneyred{
        	color:red;
        }
        </style>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihuiWaterRecordController/orderSolelist.do" name="Form" id="Form" method="post">
	    <div>
		       <div class="dangan2_d1">
		           <span  class="zhifu1_sp1">区域 </span>
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
		           <input type="text" name="area_name" id="area_name" value="${pd.area_name}" style="display:none;width:1px;height:1px;"/>
		           <span  class="zhifu1_sp1">支付类型</span>  
 		           <select class="dangan2_d1_st1"   name="jiaoyi_type">
		               <option value="0">全部</option>
		             <%--   <option value="1" ${pd.jiaoyi_type eq '1'?'selected':'' }>当面付</option>
		               <option value="2" ${pd.jiaoyi_type eq '2'?'selected':'' }>当面在线支付</option> --%>
		               <option value="3" ${pd.jiaoyi_type eq '3'?'selected':'' }>提货券</option>
		               <option value="5" ${pd.jiaoyi_type eq '3'?'selected':'' }>优选提货券</option>
		           </select>
		           <span  class="zhifu1_sp1">支付状态</span>  
 		           <select class="dangan2_d1_st1"   name="tihuo_status">
		               <option value="">全部</option>
		               <option value="0" ${pd.tihuo_status eq '0'?'selected':'' }>未提货</option>
		               <option value="1" ${pd.tihuo_status eq '1'?'selected':'' }>已提货</option>
		               <option value="99" ${pd.tihuo_status eq '99'?'selected':'' }>已退款</option>
 		           </select>
 		  	   </div>
 		  	   <div  class="dangan2_d1">
 		  	    <span  class="zhifu1_sp1">时段 </span> 
		           <input placeholder="开始时间" class="zhifu1_st1" type="text" name="starttime" id="starttime" value="${pd.starttime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		           <span  class="zhifu1_sp1">至 </span>  
		           <input placeholder="结束时间" class="zhifu1_st1" type="text" name="endtime" id="endtime" value="${pd.endtime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
     		       <span  class="zhifu1_sp1">关键字&nbsp;&nbsp;&nbsp;&nbsp; </span>
		          <input class="zhifu1_ipt1" type="text" name="store_checked"  placeholder="请输入ID号、商家名称 进行查询" value="${pd.store_checked}"></input> 
		          <span class="zhifu1_btn1" onclick="search()">查询</span>
 		       </div>
		       <div class="dangan2_d2">
		          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table"  style="    white-space: nowrap;line-height:36px">
		              <tr class="tdtop">
		               	<td rowspan="2">序号</td>
		                <td  rowspan="2">支付时间</td>
		                <td  rowspan="2">订单号</td>
		                <td  colspan="2">付款方</td>
 		                <td rowspan="2">发生金额</td>
		                <td rowspan="2">优惠金额</td>
		                <td rowspan="2">实际金额</td>
		                <td colspan="10">付款明细</td>
 		                <td colspan="5">商家信息</td>
		              </tr> 
		              <tr>
 	                    <td>用户名称</td>
	                    <td>用户ID号</td>
	                    
	                    <td>现金</td>
	                    <td>支付宝</td>
	                    <td>微信</td>
	                    <td>银行卡</td>
	                    <td>积分</td>
	                    <td>余额</td>
	                    <td>支付类型</td>
	                    <td>当前状态</td>
 	                    <td>赠送积分</td>
	                    <td>推广积分</td>
	                    
	                    <td>省</td>
	                    <td>市</td>
	                    <td>区</td>
	                    <td>商家名称</td>
	                    <td>商家ID</td>
 	                  </tr> 
		              <c:forEach items="${varList}" var="var" varStatus="vs">
		              <tr >
		              	<td>${vs.index+1}</td>
		                <td>${var.over_time}</td>
		                <td>${var.waterrecord_id}</td>
  		                <td>${var.member_name} </td>
		                <td>${var.user_id} </td>
  		                <td>${var.money}</td>
		                <td>${var.discount_money}</td>
		                <td>${var.arrivalmoney}</td>
		                <td>${var.nowypay_money} </td>
		                <td>${var.alipay_money} </td>
		                <td>${var.wx_money} </td>
		                <td>${var.bank_money} </td>
 		                <td> ${var.user_integral} </td>
		                <td> ${var.user_balance} </td>
 		                <td> 
		                		<c:if test="${var.jiaoyi_type eq '0'}">/</c:if>
		                		<c:if test="${var.jiaoyi_type eq '1'}">当面付</c:if>
			                	<c:if test="${var.jiaoyi_type eq '2'}">当面在线支付</c:if>
			                	<c:if test="${var.jiaoyi_type eq '3'}">提货券</c:if>
			                	<c:if test="${var.jiaoyi_type eq '4'}">/</c:if>
			                	<c:if test="${var.jiaoyi_type eq '5'}">优选提货券</c:if>
			            </td>
			             <td> 
		                		<c:if test="${var.tihuo_status eq '0'}">未提货</c:if> 
		                		<c:if test="${var.tihuo_status eq '1'}"><span style="color:blue;">已提货</span></c:if>
			                	<c:if test="${var.tihuo_status eq '99'}"><span style="color:red;">已退款</span></c:if>
 			            </td>   	
			            <td class="moneyred"><c:if test="${var.tihuo_status eq '1'}"><span style="color:blue;"> ${var.get_integral} </span></c:if></td> 
			            <td class="moneyred" ><c:if test="${var.tihuo_status eq '1'}"><span style="color:blue;">  ${var.sendxitong_integral}  </span></c:if></td> 
 		                <td>${var.province_name}</td>
 		                <td>${var.city_name}</td>
 		                <td>${var.area_name}</td>
		                <td>${var.store_name }<!-- 收款方 ID --></td>
		                <td>${var.store_id }<!-- 收款方 名称 --></td>
 		              </tr>
		              </c:forEach>
		               <tr>
		               		<td colspan="5">本页合计</td>
		               		<td>${nowpagesum.summoney}</td>
			                <td>${nowpagesum.sumdiscount_money}</td>
			                <td>${nowpagesum.sumarrivalmoney}</td>
		                 	<td>${nowpagesum.sumnowypay_money} </td>
		                 	<td>${nowpagesum.sumalipay_money} </td>
			                <td>${nowpagesum.sumwx_money} </td>
			                <td>${nowpagesum.sumbank_money} </td>
			                <td> ${nowpagesum.sumuser_integral} </td>
			                <td> ${nowpagesum.sumuser_balance} </td>
			                <td>/</td>
			                <td>/</td>
			                <td class="moneyred"> ${nowpagesum.sumget_integral} </td> 
			                <td class="moneyred"> ${nowpagesum.sumsendxitong_integral} </td>
 			                <td colspan="5"></td>
			           </tr>
			           <tr>
			           		<td colspan="5">总合计</td>
			                <td>${allpagesum.summoney}</td>
			                <td>${allpagesum.sumdiscount_money}</td>
			                <td>${allpagesum.sumarrivalmoney}</td>
		                 	<td>${allpagesum.sumnowypay_money} </td>
		                 	<td>${allpagesum.sumalipay_money} </td>
			                <td>${allpagesum.sumwx_money} </td>
			                <td>${allpagesum.sumbank_money} </td>
			                <td> ${allpagesum.sumuser_integral} </td>
			                <td> ${allpagesum.sumuser_balance} </td>
			                <td>/</td>
			                <td>/</td>
			                <td class="moneyred" > ${allpagesum.sumget_integral} </td> 
			                <td class="moneyred" > ${allpagesum.sumsendxitong_integral} </td>
 			                <td colspan="5"></td>
			          </tr>
		          </table>
		          <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		       </div>
       		</div>
        </form>
        </c:if>
        <script type="text/javascript">
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
 						$("#city_name").val(city_name);
					}
					if($("#province_id option:selected").val() != ""){
						var province_name=$("#province_id option:selected").text();
 						$("#province_name").val(province_name);
					}
					if($("#area_id option:selected").val() != ""){
						var area_name=$("#area_id option:selected").text();
 						$("#area_name").val(area_name);
					}
			    	$("#Form").submit();
			}
			
			 
	    </script>
    </body>
 </html>