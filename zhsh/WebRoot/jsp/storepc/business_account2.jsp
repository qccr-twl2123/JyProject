<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>流水明细</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/zhxx_lsmx.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<script src="My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
         	.moneyred{color:red;}
         	.moneyblue{color:blue;}
         	.moneygreen{color:green;}
         	.alert_xg {
         		max-width: 900px;
         	}
         	.one{
         		padding-top: 4px;
         	}
         	.al_body tr td{
         		line-height: 1.8;
         	}
         	.al_body li {
			        box-sizing: border-box;
				    width: 100%;
				    display: inline-block;
				    line-height: 0;
				    padding-top: 0;
				    text-align: left;
				    padding-left: 152px;
			}
    </style>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
	<div class="dask">
<!-- 详情开始 -->
	    <div class="alert_xg xiangqing">		
	        <div class="al_head">
	            <div class="close"></div>
	            <div class="alert_tit">
	                <span>订单明细</span>
	                <div class="one"></div>
	            </div>
	        </div>
	        <div class="al_body onedetail">
				<table cellspacing="0" cellpadding="0" style="width:96%;padding:10px 3%;border:0;">
					<tr>
						<td >支付时间：<span id="detail_createdate" class="qingkong"></span></td>
						<td>订单号：<span id="detail_store_wealthhistory_id" class="qingkong"></span></td>
						<td>用户类型：<span id="detail_user_type" class="qingkong"></span></td>
					</tr>
					<tr>
						
						<td>用户名称：<span id="detail_user_name" class="qingkong"></span></td>
						<td>用户ID：<span id="detail_show_id" style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;width: 189px;display: block;float: right;" class="qingkong"></span></td>
						<td>款项类型：<span id="detail_profit_name" class="qingkong"></span></td>
					</tr>
					<tr>
						<td>发生金额：<span id="detail_sale_money" class="qingkong"></span></td>
						<td>优惠金额：<span id="detail_discount_money" class="qingkong"></span></td>
						<td>不优惠金额： <span id="detail_no_discount_money" class="qingkong"></span></td>
					</tr>
					<tr>
						<td>实际金额： <span id="detail_discount_after_money" class="col-blue qingkong"></span></td>
						<td id="detail_remittance_type_name" class="qingkong"></td>
						<td>积分支付：<span  id="detail_user_integral" class="qingkong"></span> </td>
  					</tr>
					<tr>
						<td>余额支付：<span  id="detail_user_balance"  class="qingkong"></span></td>
						<td>支付类型：<span id="detail_jiaoyi_type_name" class="qingkong"></span></td>
						<td>支付渠道：<span id="detail_application_channel_name" class="qingkong"></span></td>
 					</tr>
					<tr>
						<td>推广积分：<span id="detail_sendxitong_integral" class="qingkong"></span></td>
						<td>赠送积分：<span id="detail_get_integral" class="qingkong"></span></td>
 						<td>结余：<span id="detail_last_wealth" class="qingkong"></span></td>
 					</tr>
					<tr>
						<td>操作员：<span id="detail_operator_name" class="qingkong"></span></td>
 					</tr>
				</table>
				<ul style="width:100%" id="detailyouhui">
					<p style="padding-left: 8.3%;box-sizing: border-box;">优惠明细</p>
					<!-- <li class="liremove">1.231321a3sd22a3s</li> -->
  				</ul>
	        </div> 
	        <div class="al_body twodetail">
				<table cellspacing="0" cellpadding="0" style="width:96%;padding:10px 3%;border:0;">
					<tr>
						<td style="width:50%">支付时间：<span id="twodetail_createdate" class="qingkong"></span></td>
						<td>订单号：<span id="twodetail_store_wealthhistory_id" class="qingkong"></span></td>
					</tr>
 					<tr>
 						<td>款项类型：<span>人脉推广收益</span></td>
						<td>支付渠道：<span>系统自动结算</span></td>
					</tr>
 					<tr>
						<td>奖励金额：<span id="twodetail_oneday_renmaijifen" class="qingkong"></span></td>
 						<td>结余：<span id="twodetail_last_wealth" class="qingkong"></span></td>
					</tr>
 				</table>
	        </div>           
	    </div>
	</div>

    <form action="<%=basePath%>storepc_wealthhistory/list.do" method="post" name="Form" id="Form">
    	<input type="hidden" name="store_id" value="${storepd.store_id }" >
    	<input type="hidden" name="chuli_type" value="1" >
		<ul>
			<li style="line-height:2.5;">
				<span>查询时段</span>
				 <input class="inp-l" type="text" name="starttime" value="${pd.starttime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间"/>
				<span>至</span>
				<input class="inp-l"  type="text" name="endtime" id="end" value="${pd.endtime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间"/>
	 			<span>&nbsp;&nbsp;操作员</span>
				<select  name="store_operator_id">
		                	<option value="">请选择</option>
		                	<c:forEach items="${splist}" var="var" varStatus="vs">
		                    	<option value="${var.store_operator_id }" ${var.store_operator_id eq pd.store_operator_id?'selected':''}>${var.operator_name }</option>
		                    </c:forEach>
		        </select> 
				<span>&nbsp;&nbsp;班次</span>
				<select  name="store_shift_id">
		                	<option value="">请选择</option>
		                	<c:forEach items="${shiftList}" var="var" varStatus="vs">
		                    	<option value="${var.store_shift_id }" ${var.store_shift_id eq pd.store_shift_id?'selected':''}>${var.shift_name }</option>
		                    </c:forEach>
		        </select>
			</li>
			<li style="line-height:2.5;">
				<span>交易类型</span>
				<select  name="profit_type">
		                	<option value="">请选择</option>
		                    <option value="1" ${pd.profit_type eq'1'?'selected':'' }>提现</option>
		                    <option value="2"  ${pd.profit_type eq'2'?'selected':'' }>积分充值</option>
		                    <option value="3"  ${pd.profit_type eq'3'?'selected':'' }>消费收款</option>
		                    <option value="6"  ${pd.profit_type eq'6'?'selected':'' }>抢积分红包</option>
		                    <option value="7"  ${pd.profit_type eq'7'?'selected':'' }>返会员积分</option>
		                    <option value="8"  ${pd.profit_type eq'8'?'selected':'' }>发积分红包</option>
		                    <option value="19"  ${pd.profit_type eq'19'?'selected':'' }>退还积分红包</option>
		                    <option value="9"  ${pd.profit_type eq'9'?'selected':'' }>首次购买服务</option>
		                    <option value="10"  ${pd.profit_type eq'10'?'selected':'' }>服务续费</option>
		                    <option value="13"  ${pd.profit_type eq'13'?'selected':'' }>优选编辑费用</option>
	 	                    <option value="17"  ${pd.profit_type eq'17'?'selected':'' }>每天统计人脉收益</option> 
	 	                    <option value="18"  ${pd.profit_type eq'18'?'selected':'' }>商品超出限制收取费用</option> 
		         </select>
				&nbsp;&nbsp;
				<span class="anniu-s" onclick="select()">搜索</span>
			</li >
			<li style="line-height:1.6;">
				<table cellspacing="0" cellpadding="0" border="1" style="white-space: nowrap; width:100%;">
					<thead>
						<tr>
							<td rowspan=2>支付时间</td>
							<td rowspan=2>发生金额</td>
							<td rowspan=2>不优惠金额</td>
							<td rowspan=2>实付金额</td>
							<td colspan=7>付款明细</td>
							<td rowspan=2>会员积分</td>
							<td rowspan=2>共享人脉积分</td>
 							<td rowspan=2>服务费用 </td>
							<td rowspan=2>结余</td>
							<td rowspan="2">操作</td>
						</tr>
						<tr>
							<td>现金</td>
							<td>支付宝</td>
							<td>微信</td>
							<td>银行卡</td>
							<td>积分</td>
							<td>余额</td>
							<td>平台支付</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${varList}" var="var" varStatus="vs">
							  <tr >
								<td>${var.createdate}</td>
	 							<td class="money">${var.money}</td>
								<td>${var.no_discount_money}</td>
								<td class="money moneyblue">${var.arrivalmoney}</td>
								<td>${var.nowypay_money} </td>
								<td class="money">${var.alipay_money} </td>
								<td class="money">${var.wx_money} </td>
								<td class="money">${var.bank_money} </td>
								<td> ${var.user_integral} </td>
								<td> ${var.user_balance} </td>
								<td class="moneygreen"> ${var.jiuyu_money} </td>
 								<td class="moneyred"> ${var.get_integral} </td> 
								<td class="moneyred"> ${var.sendxitong_integral} </td>
								<td class="moneyred"> ${var.orderfuwu_money} </td>
								<td class="money"> ${var.last_wealth} </td> 
								<td><span class="biankuang-s btn-xq" onclick="godetail('${var.store_wealthhistory_id}')">详情</span></td>
							  </tr>
			             </c:forEach>
					</tbody>
					<tfoot>
						<tr>
			               		<td >本页合计</td>
			               		<td class="money">${nowpagesum.summoney}</td>
				                <td>${nowpagesum.sumno_discount_money}</td>
				                <td class="money moneyblue">${nowpagesum.sumarrivalmoney}</td>
			                 	<td> ${nowpagesum.sumnowypay_money} </td>
			                 	<td class="money"> ${nowpagesum.sumalipay_money} </td>
				                <td class="money"> ${nowpagesum.sumwx_money} </td>
				                <td class="money"> ${nowpagesum.sumbank_money} </td>
	 			                <td> ${nowpagesum.sumuser_integral} </td>
				                <td> ${nowpagesum.sumuser_balance} </td>
				                <td class="moneygreen"> ${nowpagesum.sumjiuyu_money} </td>
 				                <td class="moneyred col-red"> ${nowpagesum.sumget_integral} </td> 
				                <td class="moneyred col-red"> ${nowpagesum.sumsendxitong_integral} </td>
				                <td class="moneyred col-red"> ${nowpagesum.sumorderfuwu_money} </td>
								<td></td>
								<td></td>
						   </tr>
				           <tr>
				           		<td >总合计</td>
				                <td class="money">${allpagesum.summoney}</td>
				                <td>${allpagesum.sumno_discount_money}</td>
				                <td class="money moneyblue">${allpagesum.sumarrivalmoney}</td>
				                <td> ${allpagesum.sumnowypay_money} </td>
			                 	<td class="money"> ${allpagesum.sumalipay_money} </td>
				                <td class="money"> ${allpagesum.sumwx_money} </td>
				                <td class="money"> ${allpagesum.sumbank_money} </td>
	 			                <td> ${allpagesum.sumuser_integral} </td>
				                <td> ${allpagesum.sumuser_balance} </td>
				                <td class="moneygreen"> ${allpagesum.sumjiuyu_money} </td>
				                <td class="moneyred col-red"> ${allpagesum.sumget_integral} </td> 
	 			                <td class="moneyred col-red">  ${allpagesum.sumsendxitong_integral} </td>
	 			                <td class="moneyred col-red">  ${allpagesum.sumorderfuwu_money} </td>
 								<td></td>
								<td></td>
						  </tr>
				</table>
			</li>
			<li class="fenye clf">
				<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
			</li>
		</ul>
	</form>
	</c:if>
</body>
<script src="js/jquery-1.12.4.min.js"></script>	
<script>
	 $(".money").each(function(n,obj){
	        	if($(obj).text().trim() != ""){
	        		if(parseFloat($(obj).text().trim()) < 0){
	            		$(obj).css("color","red");
	            	}
	        	}
	});
	//提交
	function select(){
		$("#Form").submit();
	}
 	
	//查看详情
	function godetail(store_wealthhistory_id){
		$(".dask").css({"display":"block"})
		$(".xiangqing").css({"display":"block"})
		 $.ajax({
             type:"POST",
             url:"storepc_wealthhistory/liuShuiDetailById.do", 
             data:{"store_wealthhistory_id":store_wealthhistory_id}  ,              
             dataType:"json",
             success: function(data){
        		var pd=data.data;
        		if(data.result == "1"){
        			$(".qingkong").html("");
        			var profit_type=pd.profit_type;
        			if(profit_type == "17"){
        				$(".onedetail").hide();
        				$(".twodetail").show();
        				$("#twodetail_createdate").html(pd.createdate);
            			$("#twodetail_store_wealthhistory_id").html(pd.store_wealthhistory_id);
            			$("#twodetail_oneday_renmaijifen").html(pd.oneday_renmaijifen);
            			$("#twodetail_last_wealth").html(pd.last_wealth);
        			}else{
        				$(".onedetail").show();
        				$(".twodetail").hide();
        				$("#detail_createdate").html(pd.createdate);
            			$("#detail_store_wealthhistory_id").html(pd.store_wealthhistory_id);
            			$("#detail_user_type").html(pd.user_type);
            			$("#detail_user_name").html(pd.user_name);
            			$("#detail_show_id").html(pd.show_id);
            			$("#detail_profit_name").html(pd.profit_name);
            			$("#detail_sale_money").html(pd.money);
            			$("#detail_discount_money").html(pd.reduce_money);
            			$("#detail_discount_after_money").html(pd.arrivalmoney);
            			$("#detail_no_discount_money").html(pd.no_discount_money);
            			$("#detail_operator_name").html(pd.operator_name);
             			//1-银联支付，2- 现金支付（为0的情况下），3-支付宝支付，4-微信支付，5-苹果支付,6-平台支付（九鱼平台支付）
            			var remittance_type=pd.remittance_type;
            			if(remittance_type == "1"){
            				$("#detail_remittance_type_name").html("银联支付："+pd.bank_money);
            			}else if(remittance_type == "2"){
            				$("#detail_remittance_type_name").html("现金支付："+pd.nowypay_money);
            			}else if(remittance_type == "3"){
            				$("#detail_remittance_type_name").html("支付宝支付："+pd.alipay_money);
            			}else if(remittance_type == "4"){
            				$("#detail_remittance_type_name").html("微信支付："+pd.wx_money);
            			}else if(remittance_type == "5"){
            				$("#detail_remittance_type_name").html("苹果支付："+pd.apple_money);
            			}else{
            				$("#detail_remittance_type_name").html("平台支付："+pd.jiuyu_money);
            			}
            			$("#detail_user_integral").html(pd.user_integral);
        				$("#detail_user_balance").html(pd.user_balance);
            			var jiaoyi_type=pd.jiaoyi_type;
            			if(jiaoyi_type  == "0"){
            				$("#detail_jiaoyi_type_name").html("/");
            			}else if(jiaoyi_type  == "1"){
            				$("#detail_jiaoyi_type_name").html("当面付");
            			}else if(jiaoyi_type == "2"){
            				$("#detail_jiaoyi_type_name").html("当面在线付");
            			}else if(jiaoyi_type == "3"){
            				$("#detail_jiaoyi_type_name").html("提货券");
            			}else{
            				$("#detail_jiaoyi_type_name").html("优选提货券");
            			}
            			var application_channel=pd.application_channel;
            			if(application_channel  == "1"){
            				$("#detail_application_channel_name").html("C端");
            			}else if(application_channel  == "2"){
            				$("#detail_application_channel_name").html("B端");
            			}else if(application_channel == "3"){
            				$("#detail_application_channel_name").html("PC会员端");
            			}else if(application_channel == "4"){
            				$("#detail_application_channel_name").html("PC商家端");
            			}else if(application_channel == "5"){
            				$("#detail_application_channel_name").html("微信端");
            			}else{
            				$("#detail_application_channel_name").html("总后台");
            			}
            			$("#detail_sendxitong_integral").html(pd.sendxitong_integral);
            			$("#detail_get_integral").html(pd.get_integral);
            			$("#detail_last_wealth").html(pd.last_wealth);
        			}
        			$(".liremove").remove();
        			var discountList=pd.discountList;
        			for (var i = 0; i < discountList.length; i++) {
        				$("#detailyouhui").append("<li class='liremove'>"+(i+1)+"："+discountList[i].content+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-------------------------&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+discountList[i].number+"</li>");
					}
        			if(pd.profit_type == "3"){
        				$("#detailyouhui").show();
        			}else{
        				$("#detailyouhui").hide();
        			}
          		}else{
        			alert(data.message);
        		}
        		
             }
  		});
	}
 	 
	function guanbi(){
		$(".dask").css({"display":"none"})
	}
	$(".close").click(function(){
		guanbi()
	})

	$(".btn-xq").click(function(e){
		xiangqing()
	})
</script>
</html>