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
	<title>商品销售明细</title>
	<base href="<%=basePath%>">
 	<link rel="stylesheet" href="css/pcstore/zhxx_lsmx.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<script src="My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
	.al_body {
	    height: 69%;
 	    padding-left: 22%;
	}
	.tk_box{
			display: none;
			position: fixed;
			width: 100%;
			height: 100%;
		}
		.tk_box ul{
			width: 24%;
			height: 10%;
			border: 1px solid#ccc;
			background: #fff;
			position: absolute;
			left: 0;
			right: 0;
			bottom: 0;
			top: 0;
			margin: auto;
			border-radius: 4px;
		}
		.alert_xg {
		    display: block;
		    position: fixed;
		    z-index: 10;
		    max-width: 550px;
		    min-width: 550px;
		    max-height: 462px;
		    margin: auto;
		    left: 0;
		    right: 0;
		    top: 0;
		    bottom: 0;
		    background: #fff;
		    box-shadow: 0 0 20px #aaa;
		    border-radius: 13px;
		}
 	</style>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
    <!-- 确定退款 -->
	<div class="tk_box">
		<ul>
			<li style="height:80%;line-height:2.5;">
				<span class="padd_l">确定退款吗？</span>
			</li>
			<li style="text-align:right;height:85%;background:#ccc;line-height:2.5;padding-right:20px;">
				<span class="anniu-m qd">确定</span>
				<span class="anniu-m qd">取消</span>
			</li>
		</ul>
	</div>
	<!-- 遮罩 -->
	<div class="dask">
	    <!-- 详情 -->
	    <div class="alert_xg ">		
	        <div class="al_head">
	            <div class="close"></div>
	            <div class="alert_tit">
	                <span>商品明细</span>
	                <div class="one"></div>
	            </div>
	        </div>
	        <ul class="al_body">
	           	<li>
	           		<span>姓名：</span>
	           		<span id="thname"></span>
	           	</li>
	           	<li>
	           		<span>订单用户：</span>
	           		<span class="phonenum" id="thphone"></span>
	           	</li>
	           	<li>
	           		<span style="vertical-align: top;">订购物品：</span>
	           		<ul style="display:inline-block;" class="dgwp_list" id="thgoods">
	           			 
	           		</ul>
	           	</li>
	           	<li>
	           		<span>有效时间：</span>
	           		<span id="thtime"></span>
	           	</li>
	           	<li>
	           		<span>提货券编号：</span>
	           		<span class="th_num" id="thid"></span>
	           	</li>
	           	<li style="text-align:center;">
	            	<span class="anniu-m return">取消</span>
	           	</li>
	        </ul>           
	
	    </div>
	</div>
	
	<form action="storepc_wealthhistory/orderlistPage.do" method="post" name="Form" id="Form">
	    <input type="hidden" name="store_id" value="${pd.store_id}" />
	    <input type="hidden" name="pay_type" value="3" />
		<ul>
			<li style="line-height:2.5;">
				<span>创建时间</span>
				<input class="inp-l" type="text" name="starttime" value="${pd.starttime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间"/>
				<span>至</span>
				<input class="inp-l" type="text" name="endtime" id="end" value="${pd.endtime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间"/>
				<span>&nbsp;&nbsp;手机号</span>
				<input type="text" class="inp-l" placeholder="请输入手机号码" id= "phone" name ="phone" value="${pd.phone}">
				<span>&nbsp;&nbsp;提货码</span>
				<input type="text" class="inp-l" placeholder="请输入提货码" id= "tihuo_id" name ="tihuo_id" value="${pd.tihuo_id}">
			</li>
			<li style="line-height:2.5;">
				<span>订单状态</span>
				 <select   name="tihuo_status">
		                	<option value="">请选择</option>
		                    <option value="0"  ${pd.tihuo_status eq'0'?'selected':'' }>未提货</option>
		                    <option value="1" ${pd.tihuo_status eq'1'?'selected':'' }>已提货</option>
		                    <option value="99" ${pd.tihuo_status eq'99'?'selected':'' }>已退款</option>
		        </select>
				&nbsp;&nbsp;
				<span class="anniu-s"  onclick="select()">搜索</span>
			</li>
			<li style="line-height:1.4;overflow:auto;">
				<table cellspacing="0" cellpadding="0"  style="min-width:1760px;border:1px solid #999;white-space: nowrap;"  >
					<thead>
						<tr>
							<td rowspan=2>支付时间</td>
							<td rowspan=2>订单号</td>
							<td rowspan=2>交易对象</td>
							<td rowspan=2>消费金额</td>
							<td rowspan=2>优惠金额</td>
							<td rowspan=2>不优惠金额</td>
							<td rowspan=2>优惠后的实付</td>
							<td colspan=6>付款明细</td>
							<td rowspan=2>提货券号码</td>
							<td rowspan=2>提货券类型</td>
							<td rowspan=2>订单状态</td>
							<td rowspan=2>查看商品明细</td>
							<td rowspan=2>编辑备注</td>
						</tr>
						<tr>
							<td>现金</td>
							<td>支付宝</td>
							<td>微信</td>
							<td>银行卡</td>
							<td>积分</td>
							<td>余额</td>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${varList}" var="var" varStatus="vs">
		                <tr>
		                    <td>${var.createtime}</td>
		                    <td>${var.order_id }</td>
		                    <td>${var.show_id}</td>
		                    <td >${var.sale_money}</td>
		                    <td >${var.discount_money}</td>
		                    <td >${var.no_discount_money}</td>
		                    <td >${var.allmoney}</td>
		                    
		                    <td><c:if test="${var.money_pay_type eq '2'}">${var.actual_money}</c:if></td>
			                <td><c:if test="${var.money_pay_type eq '3'}"> ${var.actual_money}</c:if> </td>
			                <td><c:if test="${var.money_pay_type eq '4'}"> ${var.actual_money}</c:if> </td>
			                <td><c:if test="${var.money_pay_type eq '1'}"> ${var.actual_money}</c:if> </td>
	 		                <td>  ${var.user_integral} </td>
			                <td>  ${var.user_balance} </td>
			                
		                    <td>${var.tihuo_id}</td>
		                    <td>${var.pay_sort_type eq '1'?'商品':'优选商品'}</td>
	                     	<c:if test="${var.tihuo_status eq '0'}">
		                    		<td class="col-blue wth_box" order_id="${var.order_id}" >
		                    			未提货
 		                    		</td>
	                    	</c:if>
		                    <c:if test="${var.tihuo_status eq '1'}"><td>已提货</td></c:if>
		                    <c:if test="${var.tihuo_status eq '99'}"><td>已退款</td></c:if>
	 	                    <td style="color: blue;">
								<span class="biankuang-s chakan" onclick="findOrderDetail('${var.order_id}')">点击查看</span>
	 	                    </td>
		                    <td>
		                    	<input style="width: 100px" type ="text" id ="${var.order_id}remark" name="remark" value="${var.remark }">
		                        <span class="biankuang-s"   onclick="ok('${var.order_id}','${var.order_id}remark')">确认</span>
		                    </td>
		                </tr>
	                </c:forEach>
	 				</tbody>
				</table>
			</li>
			<li class="fenye clf">
				 <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
			</li>
		</ul>
	 	</form>
	</c:if>
  	<script type="text/javascript" src="js/bootbox.min.js"></script><!-- 确认窗口 -->
	<script type="text/javascript">
		 
 		//提交
		function select(){
			 $("#Form").submit();
		}
	
	  
	    //确认备注
    	function ok(id,remark){
    		var rem = $("#"+remark).val().trim();
    		if(rem == ""){
    			return;
    		}
    		$.ajax({
					type:"post",
					url:"storepc_wealthhistory/updRemark.do",
					data:"remark="+rem+"&order_id="+id,
					success:function(data){
						window.location.reload();
					}
			});
    	}
	   
		
		//查看订单详情
		function findOrderDetail(order_id){
			$("#thgoods").empty();
				//获取提货信息
 			$.ajax({
				url:'<%=basePath%>storepc_wealthhistory/findOrderDetail.do',
				type:"post",
				dataType:"json",
				data:{"order_id":order_id},
				success:function(data){
					if(data.result == '1'){
 						//显示出信息
 						var pd=data.data;
						order_id=pd.order_id;
						$("#thname").text(pd.name);
						$("#thid").text(pd.tihuo_id);
						$("#thphone").text(pd.phone);
						$("#thtime").text(pd.startdate+"-"+pd.enddate);
						var goodsList=pd.goodsList;
						for(var i=0;i<goodsList.length;i++){
							$("#thgoods").append("<li>"+goodsList[i].goods_name+"x"+goodsList[i].shop_number+"</li>");
						}
					}else{
						alert(data.message);
					}
  				}
			});
		}
	  
 	</script>
</body>
<script>
	$(function(){     /*遮罩部分*/
		function tianjia(){
			$(".dask").css({"display":"block"})
			$(".alert_xg").css({"display":"block"})
		}
		function guanbi(){
			$(".dask").css({"display":"none"})
			$(".alert_xl").css({"display":"none"})
		}
		$(".chakan").click(function(){
			tianjia()
		})
		$(".close").click(function(){
			guanbi()
		})
		$(".anniu-m").click(function(){
			guanbi()
		});
		//退款  
		$(".wth_box").mouseenter(function(e){
			add_remove(e)
		})
		
 		function add_remove(e){
			$(".wth_box div").remove()
				var ev=e||window.event
				var elem=ev.target||ev.srcElement
				//console.log(elem)
				if (elem.nodeName=="TD") {
					 if ($(elem)[0].childNodes.length==1) {
						var div=document.createElement("div")
						div.innerHTML="退款"
						div.className="closeto"
						div.onclick=function(){$(".tk_box").css("display","block");window.th_order_id=$(elem).attr("order_id");}
						div.onmouseout=function(e){$(e.target).remove();$(".wth_box  div").remove();}
						$(elem).append(div)
					 }
				}			
		}
		
		//确认退款
		$(".qd").click(function(){
			var url = "<%=basePath%>/storepc_wealthhistory/returnBackOrder.do?order_id="+th_order_id+"&tm="+new Date().getTime();
			$.get(url,function(data){
				if(data=="success"){
					nextPage(${page.currentPage});
				}
			});
		})
		
	})
</script>
</html>