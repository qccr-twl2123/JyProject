<%--  <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>首页</title>
<meta charset="utf-8">
<base href="<%=basePath%>">
<link rel="stylesheet" href="css/storepc/business_homepage1.css"/>
<script src="js/jquery-1.8.0.min.js"></script>
<script src="js/jquery-1.7.2.js"></script>
<script src="js/jquery.tips.js"></script>
<script src="js/jquery.form.js"></script>
<script src="js/storepc/business_homepage1.js"></script>
<style type="text/css">
#output {
	width: 500px;
	height: 500px;
	margin: 0 auto;
}
</style>
<script type="text/javascript">
	//生成桌面二维码
	function QRCode()
	{		
 		var content = "商家的信息";
		window.location.href="<%=basePath%>storepcOperator_file/QRCode.do?store_id=" + "${pd.store_id}"+"&store_name"+"${storepd.store_name}"+"&store_operator_id="+"${storepd.oprator_id}";
	}
	
 	
</script>
</head>
<body>
	<div></div>
	<div id="output" style="display:none;">
		<h2 align="center">如图所示：<span id="paymoney"></span>）</h2>
		<img src="" width="300px;" height="300px;" class="erma"/>
	</div>
	<div class="parent">
 		<c:if test="${storeqx.look eq '1'}">
 	  		<div class="d1">
				<span class="d1_sp1"> 收银和取货 </span>
			<!-- 	<span class="d1_sp3" onclick="QRCode()">生成桌号二维码</span>  -->
				<span class="d1_sp2 new_d1_sp2">取货验货</span>
			</div>
	 		<div class="new_tc">
				<p class="new_p1">
					<span class="new_sp1">X</span>
				</p>
				<p class="new_p2">
					<span>请输入代金券号码</span><br/> 
					<input class="new_ipt1" type="text" maxlength="12" onkeyup="formatBankNo(this)" />
					<span class="new_yes" onclick="searchTihuo(this)">确定</span>
				</p>
			</div>
			<div class="new_tcwhw">
				<p class="new_p1">
					<span class="new_sp1">X</span>
				</p>
				<p class="new_p2">
					<span style="font-size: 16px;">使用积分支付时请输入会员提供的短信验证码</span><br/> 
					<input class="new_ipt1" type="text" maxlength="12" id="shouyincode"  />
					<span class="new_yes" onclick="shouyincode()" >确定</span>
				</p>
			</div>
			<script type="text/javascript">
				function formatBankNo(obj){
					if(obj.value =="")return;
 					var n = $(obj).val();
					var account="";
					if(n.length >2 && n.length <7 ){
						if(n.indexOf("-") > 0){
							account = n.substring (0,2) + "-" + n.substring (3,n.length);
						}else{
							account = n.substring (0,2) + "-" + n.substring (2,n.length);
						}
 					}else if(n.length >7){
 						if(n.lastIndexOf("-") > 6){
 							account = n.substring (0,2) + "-" + n.substring (3,7)+ "-" + n.substring (8,n.length);
 						}else{
 							account = n.substring (0,2) + "-" + n.substring (3,7)+ "-" + n.substring (7,n.length);
 						}
 					}else{
						account=n;
					}
					$(obj).val(account);
 				}
 			</script>
			<div class="new_tc2">
				<p class="new_p1">
					<span class="new_sp1">X</span>
				</p>
				<p class="new_tc2_p1">
					<span>定单用户：</span> <span id="thphone"></span> &nbsp; &nbsp; 
					<span>姓名：</span> <span id="thname"></span>
				</p>
				<p class="new_tc2_p2">
					<span class="new_tc2_p2_sp1">订购物品：</span>
					<div  id="thgoods" style="overflow-y: auto;height:150px;">
					
					</div> 
				</p>
				<p class="new_tc2_p1">
					<span>订购时间：</span> <span id="thtime"></span>
 				</p>
				<p class="new_tc2_p1">
					<span>提货卷编号：</span> <span id="thid"></span>
 				</p>
 				<c:if test="${storeqx.edit eq '1'}">
	 				<p class="new_tc2_p1">
	 					<span class="new_yes2" onclick="sureOk()">确定提货</span>
					</p>
				</c:if>
			</div>
			<input type="hidden" class="order_id" value=""/>
			<c:choose>
				<c:when test="${pd.issortjf eq '3'}">
				
				</c:when>
 				<c:when test="${pd.issortjf eq '1'}">
						<div class="d2">
							<c:if test="${pd.issortjf eq '0' }">
							<a href="storepc/goShQyStore.do?store_id=${pd.store_id}&pay_sort_type=1"><span class="d2_sp1 ">按总金额</span> </a> 
							</c:if>
							<c:if test="${pd.issortjf eq '1' }">
								<a href="storepc/goShQyStore.do?store_id=${pd.store_id}&pay_sort_type=2"> <span class="d2_sp1 d2_sp1_active">按类别金额</span> </a>
							</c:if>
						</div>
						<form action="<%=basePath%>storeapp_payhHstory/sureConfirmed.do" method="post" name="Form" id="twoForm">
							<input type="hidden" name="pay_type" value="1">
							<input type="hidden" name="apptype" value="pc">
							<input type="hidden" name="whw_reittance" value="weihanwen">
							<input type="hidden" name="pay_sort_type" value="2">
							<input type="hidden" name="order_id" value="">
							<input type="hidden" name="no_discount_money" value="0">
							<input type="hidden" name="store_operator_id" value="${pd.store_operator_id}">
							<input type="hidden" name="store_id" id="twostore_id" value="${pd.store_id}">
							<input type="hidden" name="member_id" id="twomember_id" value="">
							<input  name="get_integral" id="twoget_integral" type="hidden" value="" />
							<input name="actual_money" id="twoactual_money" type="hidden" value="" />
							<input name="sale_money" class="twosale_money" type="hidden" value="" />
							<div class="d2">
									<span class="d2_sp2">手机号码</span> 
									<input type="text" class="d2_ipt1" name="phone" value=""  id="twophone" onchange="getInteger(this,'two')"/>
									<!-- <span class="d2_sp2 d2_sp2_2">积分： <span class="red" id="twonow_integral"></span> </span> -->
							</div>
		 					<div class="d2">
									<span class="d2_sp2">桌号</span>
									<select class="d2_ipt1"  name="desk_no"  id="desk_no">
										<c:forEach items="${deskList}" var="var">
	 											<option value="${var.table_name}">${var.table_name}</option>
	 									</c:forEach>
									</select>
  							</div>
							<div class="" style="width:100%;font-size:14px;">
								<span class="d2_sp2">商品大类：</span><br/>
								<c:forEach items="${pd.sortList}" var="sortvar" varStatus="vs">
									<input type="hidden" class="goods_category_id" value="${sortvar.goods_category_id }"/>
									<span class="d2_sp2">${vs.index+1}.</span>
 									<span class="d2_ipt1" >
 										<span> <span style="width:120px;display: inline-block; text-align: right;">${sortvar.name}类 &nbsp;</span></label>
 										<input class="gci" value="" type="text" id="${sortvar.goods_category_id}" onchange="leibietwo('two')">
	 									<span>  
		 									返积分率 
		 									<span class="red2" id="${sortvar.goods_category_id}jf">${sortvar.back_rate }</span>% 
	 									</span> 
 										<input type="hidden" id="${sortvar.goods_category_id}zk" value="${sortvar.zk_rate }"/>
 									</span> 
 									<br/>
 									<br/>
  	 							</c:forEach>
	 						</div>
 							<div class="d2">
									<span class="d2_sp2 ">实付金额 </span>
 									<span class="d2_sp3"  id="twosale_money" ></span>
 									<input name="sale_money" class="twosale_money"  type="hidden" value="" />
 									<span class="d2_sp2 ">送积分： </span> 
									<span class="d2_sp3 twoget_integral"></span>
  							</div>
							<div class="d2">
									<span class="d2_sp2" >优惠项 </span><br/>
									<span id="twodiscountList">
									<c:forEach items="${pd.yxlist}" var="var" varStatus="vsyx">
											 <c:if test="${var ne '' }">
												<span class="d2_sp3_youhui" style="color:blue;">${vsyx.index+1}、${var}</span>
			 		 							<span style="color:blue;" class="d2_ipt1" > &nbsp;&nbsp;&nbsp;&nbsp; </span><br/>
  											</c:if>
  									</c:forEach>
		 							</span>
		 							<input name="discount_content" id="twodiscount_content" type="hidden" value="" />
							</div>
	 						<div class="d2">
	 								<label>
									<span class="d2_sp2 ">优惠后金额： </span> 
									</label>
									<span class="d2_sp3 money twomoney"></span>
									<input name="discount_money" id="twodiscount_money" type="hidden" value="0" />
 							</div>
	 						<div class="d2">
									<span class="d2_sp2  hands">
									<label>
									<span class="hands">使用积分支付</span>
									</label>
									</span> 
									<input type="text" class="d2_ipt1" name="user_integral" id="twouser_integral" value="" oninput="changereduceacmoney(this,'two')" />
 									<span>剩余积分：</span>
 									<span id="twonow_integral" style="color:red"></span>
 							</div>
							<div class="d2" style="display: none;">
									<span class="d2_sp2  hands">
									<label><span class="hands">使用余额支付</span></label>
									</span> 
									<input type="text" class="d2_ipt1" name="user_balance" id="twouser_balance" value="0" oninput="changereduceacmoney(this,'two')" />
									<span>剩余余额：</span>
 									<span id="twonow_money" style="color:red"></span>
							</div>  
							<div class="d2">
									<span class="d2_sp2 ">实际应收金额 </span> <span class="d2_sp3 acmoney twoactual_money" style="font-size: 28px;"></span>元
 							</div>
 							<c:if test="${storeqx.add eq '1'}">
								<div class="d2 font14">
									<span class="yes" onclick="surebuytwo()">确认收银</span><span>提示：请确认会员现金或刷卡支付成功后再点击</span>
									<input name="redpackage_id" id="twodredpackage_id" type="hidden" value="" />
									<input name="store_redpackets_id" id="twostore_redpackets_id" type="hidden" value="" />
	 							</div>
	 						</c:if>
						</form>
				</c:when>
				<c:otherwise>
						<div class="d2">
							<c:if test="${pd.issortjf eq '0' }">
							<a href="storepc/goShQyStore.do?store_id=${pd.store_id}&pay_sort_type=1"><span class="d2_sp1 d2_sp1_active">按总金额</span> </a> 
 							</c:if>
 							<c:if test="${pd.issortjf eq '1' }">
								<a href="storepc/goShQyStore.do?store_id=${pd.store_id}&pay_sort_type=2"> <span class="d2_sp1 ">按类别金额</span> </a>
							</c:if>
						</div>
		 				<form action="<%=basePath%>storeapp_payhHstory/sureConfirmed.do" method="post" name="Form" id="oneForm">
							<input type="hidden" name="pay_type" value="1">
							<input type="hidden" name="apptype" value="pc">
							<input type="hidden" name="whw_reittance" value="weihanwen">
							<input type="hidden" name="pay_sort_type" value="1">
							<input type="hidden" name="order_id" value="">
							<input type="hidden" name="store_operator_id" value="${pd.store_operator_id}">
							<input type="hidden" name="store_id" id="onestore_id" value="${pd.store_id}">
							<input type="hidden" name="member_id" id="onemember_id" value="">
							<div class="d2">
								<span class="d2_sp2">手机号码</span> 
								<input type="text" class="d2_ipt1" name="phone" value="" id="onephone" onchange="getInteger(this,'one')"/>
 							</div>
	 						<div class="d2">
								<span class="d2_sp2">桌号</span>
								<select class="d2_ipt1"  name="desk_no"  id="desk_no">
									<c:forEach items="${deskList}" var="var">
 											<option value="${var.table_name}">${var.table_name}</option>
 									</c:forEach>
								</select>
							</div>
							<div class="d2">
								<span class="d2_sp2 ">消费金额 </span>
  								<input name="sale_money" class="d2_ipt1" id="onesale_money" type="text" value=""  onchange="leibieone('one')"/>
  							</div>
 							<div class="d2">
								<span class="d2_sp2 ">不优惠金额 </span>
  								<input name="no_discount_money" class="d2_ipt1" id="oneno_discount_money" type="text" value="" onchange="leibieone('one')"/>
  							</div>
							<div class="d2">
									<span class="d2_sp2">优惠项 </span><br/>
									<span id="onediscountList"> 
										<c:forEach items="${pd.yxlist}" var="var" varStatus="vsyx">
											<c:if test="${var ne '' }">
												<span class="d2_sp3_youhui" style="color:blue;">${vsyx.index+1}、${var}</span>
			 		 							<span style="color:blue;" class="d2_ipt1" > &nbsp;&nbsp;&nbsp;&nbsp; </span><br/>
											</c:if>
 										</c:forEach>
  		 							</span>
		 							<input name="discount_content" id="onediscount_content" type="hidden" value="" />
							</div>
 							<div class="d2">
 								<label>
								<span class="d2_sp2 ">优惠后金额 ：</span>
								</label> 
								<span class="d2_sp3 onemoney"></span> 
								<input name="discount_money" id="onediscount_money" type="hidden" value="" />
								<span class="d2_sp2 ">送积分 </span> 
								<span class="d2_sp3 oneget_integral"></span> 
								<input  name="get_integral" id="oneget_integral" type="hidden" value="" />
							</div>
 							<div class="d2">
								<span class="d2_sp2  hands"> 
								<label>
								<span class="hands">使用积分支付:</span>
								 </label>
								</span> 
								<input type="text" class="d2_ipt1" name="user_integral" id="oneuser_integral"  value=""  oninput="changereduceacmoney(this,'one')"/>分
								<span>剩余积分：</span>
								<span id="onenow_integral" style="color:red"></span>
							</div>
							<div class="d2" style="display: none;">
								<span class="d2_sp2  hands">
								<label>
								<span class="hands">使用余额支付</span> 
								</label>
								</span> 
								<input type="text" class="d2_ipt1" name="user_balance" id="oneuser_balance" value="0"  oninput="changereduceacmoney(this,'one')"/>元
								<span>剩余余额：</span>
								<span id="onenow_money" style="color:red"></span>
							</div>
							<div class="d2">
									<span class="d2_sp2 ">实际应收金额 </span> <span class=" d2_sp3 acmoney oneactual_money"  style="font-size: 28px;"></span>元
									<input name="actual_money" id="oneactual_money" type="hidden" value="" />
							</div>
							<c:if test="${storeqx.add eq '1'}">
								<div class="d2 font14">
									<span class="yes" onclick="surebuyone()">确认收银</span><span>提示：请确认会员现金或刷卡支付成功后再点击</span>
									<input name="redpackage_id" id="oneredpackage_id" type="hidden" value="" />
									<input name="store_redpackets_id" id="onestore_redpackets_id" type="hidden" value="" />
								</div>
							</c:if>
					</form>
				</c:otherwise>
			</c:choose>
 		</c:if>
	</div>
	<script type="text/javascript">
		//查询提货卷
		function searchTihuo(obj)
		{
			var tihuo_id = $(obj).prev().val();
			if(tihuo_id == null ){
				alert("提货号码不能为空");
				return;
			}
		    tihuo_id = tihuo_id.substring (0,2) + tihuo_id.substring (3,7) + tihuo_id.substring (8,tihuo_id.length);
 			//获取提货信息
 			$.ajax({
				url:"<%=basePath%>storeapp_payhHstory/isTiHuo.do",
				type:"post",
				dataType:"json",
				data:{"tihuo_id":tihuo_id,"store_id":"${pd.store_id}"},
				success:function(data){
					if(data.result == '1'){
 						//显示出信息
						$('.new_tc2').css('display','block');
				    	$(this).parent().parent().css('display','none');
						var pd=data.data;
						order_id=pd.order_id;
						$("#thname").text(pd.name);
						$("#thid").text(pd.tihuo_id);
						$("#thphone").text(pd.phone);
						$("#thtime").text(pd.enddate);
						var goodsList=pd.goodsList;
						for(var i=0;i<goodsList.length;i++){
							$("#thgoods").append("<p class='new_tc2_p2' id='thgoods'> <span class='new_tc2_p2_sp1'></span> <span>"+goodsList[i].goods_name+"x"+goodsList[i].shop_number+"</span> </p>");
						}
					}else{
						alert(data.message);
						//显示出信息
						$('.new_tc2').css('display','none');
					}
  				}
			});
						
		}
		
		
		//确认提货
		var order_id="";
		function sureOk(){
  			//获取提货信息
 			$.ajax({
				url:"<%=basePath%>/storeapp_payhHstory/sureTiHuo.do",
				type:"GET",
				dataType:"json",
				data:{"order_id":order_id},
				success:function(data){
					if(data.result == "1"){
						alert("提货完成");
						//显示出信息
						$('.new_tc2').css('display','none');
						$('.new_tc').css('display','none');
	 					var pd=data.data;
						order_id="";
						$("#thname").text("");
						$("#thid").text("");
						$("#thphone").text("");
						$("#thtime").text("");
						$("#thgoods").empty();
					}else{
						alert(data.message);
					}
					
 				}
			});
						
		}
		
		
		
 		//获取用户积分信息
		function getInteger(obj,value){
 			//判断手机格式
			var myreg = /^((13[0-9])|(15[^4,\\D])|(17[2,7-8])|(18[0,5-9]))\\d{8}$/;
 			if($(obj).val().length != 11 && !myreg.test($(obj).val())){
				$(obj).tips({
					side:3,
		            msg:'手机号格式不正确',
		            bg:'#AE81FF',
		            time:3
		        });
				$(obj).focus();
				$(obj).val("")
				return false;
 	 		} 
 			$.ajax({
				url:"<%=basePath%>storeapp_payhHstory/getInforByPhone.do",
				type:"post",
				dataType:"json",
				data:{"phone":$(obj).val()},
				success:function(data){ 
					$("#"+value+"now_integral").html(data.data.now_integral);
					$("#"+value+"now_money").html(data.data.now_money);
					$("#"+value+"member_id").val(data.data.member_id);
  				}
			});
						
		}
 		
		//获取总金额的优惠信息
		function leibieone(value){
			$("#oneuser_integral").val("0");
			$("#oneuser_balance").val("0");
			var sale_money=$("#onesale_money").val();
			var no_discount_money=$("#oneno_discount_money").val();
			if(sale_money =="" || isNaN(sale_money)){//判断不是数子
				return;
			}
			if(no_discount_money =="" ){
				no_discount_money="0";
			}
  			//获取营销信息
 			$.ajax({
				url:"<%=basePath%>storeapp_payhHstory/allMoneyByOne.do",
				type:"post",
				dataType:"json",
				data:{
					"member_id":$("#onemember_id").val(),
					"store_id":$("#onestore_id").val(),
					"paymoney":sale_money,
					"notmoney":no_discount_money},
				success:function(data){ 
					var countpd=data.data.countpd;
					var yingxiaoList=data.data.yingxiaoList;
 					$(".onemoney").html(countpd.paymoney);
					$("#onediscount_money").val(countpd.reducemoney);
 					$("#oneget_integral").val(countpd.zengjf);
					$(".oneget_integral").html(countpd.zengjf);
					$("#oneredpackage_id").val(countpd.red_id);
					$("#onestore_redpackets_id").val(countpd.zengid);
					$("#oneactual_money").val(countpd.paymoney);
					$(".oneactual_money").html(countpd.paymoney);
 					var discount_content="";
 					$("#onediscountList").empty();
  					for(var n=0; n<yingxiaoList.length ; n++){
  						var s1=yingxiaoList[n].content;
  						var s2=yingxiaoList[n].id;
  						var s3=yingxiaoList[n].number;
  						var s4=yingxiaoList[n].type;
   						var s=s1+"@"+s2+"@"+s3+"@"+s4;
  						discount_content+=s+",";
  						var sspan="<span class='d2_sp2' style='color:blue;'>"+(parseInt(n)+1)+"、</span><span style='color:blue;' class='d2_ipt1'>"+s1+"&nbsp;&nbsp;&nbsp;&nbsp;"+s3+"</span><br/>";
  						$("#onediscountList").append(sspan);
  					}
  					$("#onediscount_content").val(discount_content );
 				}
			});
						
		}
 		
		
		//获取类别的优惠信息
		function leibietwo(value){
			$("#twouser_integral").val("0");
			$("#twouser_balance").val("0");
			var allleibie="";
			var xxx=0;
			$(".goods_category_id").each(function(n,o){
				var id=$(o).val();
				var s2=$("#"+id).val();
				if(s2 =="" || s2 == "0" || isNaN(s2)){//判断不是数子
					return;
				}else{
					var s1=id;
	 				var s3=$("#"+id+"jf").html();
					var s4=$("#"+id+"zk").val();
	  				if(s2 != null && s2 != "" ){
	 					var s=s1+"@"+s2+"@"+s3+"@"+s4;
						allleibie+=s+",";
						xxx+=parseFloat(s2);
					}
				}
  			});
  			//获取提货信息
 			$.ajax({
				url:"<%=basePath%>storeapp_payhHstory/allMoneyByTwo.do",
				type:"post",
				dataType:"json",
				data:{"allleibie":allleibie,"member_id":$("#twomember_id").val(),"store_id":$("#twostore_id").val()},
				success:function(data){ 
					var countpd=data.data.countpd;
					var yingxiaoList=data.data.yingxiaoList;
					$("#twosale_money").html(xxx);
					$(".twosale_money").val(xxx);
					$(".twomoney").html(countpd.paymoney);
					$("#twodiscount_money").val(countpd.reducemoney);
 					$("#twoget_integral").val(countpd.zengjf);
					$(".twoget_integral").html(countpd.zengjf);
					$("#tworedpackage_id").val(countpd.red_id);
					$("#twostore_redpackets_id").val(countpd.zengid);
					$("#twoactual_money").val(countpd.paymoney);
					$(".twoactual_money").html(countpd.paymoney);
 					var discount_content="";
 					$("#twodiscountList").empty();
  					for(var n=0; n<yingxiaoList.length ; n++){
  						var s1=yingxiaoList[n].content;
  						var s2=yingxiaoList[n].id;
  						var s3=yingxiaoList[n].number;
  						var s4=yingxiaoList[n].type;
   						var s=s1+"@"+s2+"@"+s3+"@"+s4;
  						discount_content+=s+",";
  						var sspan="<span class='d2_sp2' style='color:blue;'>"+(parseInt(n)+1)+"、</span><span style='color:blue;' class='d2_ipt1'>"+s1+"&nbsp;&nbsp;&nbsp;&nbsp;"+s3+"</span><br/>";
  						$("#twodiscountList").append(sspan);
  					}
  					$("#twodiscount_content").val(discount_content );
 				}
			});
						
		}
		
		var addnumber="0";
		//改变实际金额
		function changereduceacmoney(obj,value){
			var member_id=$("#"+value+"member_id").val()
			//获取实际支付金额
			var m1=$("#"+value+"now_integral").html();
			var m6=$("#"+value+"now_money").html();
			var m5=$("."+value+"money").html();
			var m2=$("."+value+"actual_money").html();
			var m3=$("#"+value+"user_integral").val();//使用的积分
			var m4=$("#"+value+"user_balance").val();//使用的余额
			if(m1 == "" || parseFloat(m1)-parseFloat(m3) < 0){
				alert("积分不足");
 				$("#"+value+"user_integral").val($("#"+value+"user_integral").val().substr(0,$("#"+value+"user_integral").val().length-1));
				return;
			}
			if(m6 == "" || parseFloat(m6)-parseFloat(m4) < 0){
				alert("余额不足");
				$("#"+value+"user_balance").val($("#"+value+"user_balance").val().substr(0,$("#"+value+"user_balance").val().length-1));
 				return;
			}
			if(m4 ==""){
				m4="0";
			}
			if(m3 ==""){
				m3="0";
			}
 			var n=0;
 			if(m1 != "" && m2 != "" ){
  				n=parseFloat(m5)-parseFloat(m3)-parseFloat(m4);
  				if(n<0){
					n=0;
					alert("支付数目过多，请重新填写");
					$(obj).val($(obj).val().substr(0,$(obj).val().length-1));
					return;
				}else{
					$("#"+value+"actual_money").val(n+"");
					$("."+value+"actual_money").html(n+"");
				}
 			}
 		}
		
		var zhifutype="one";
 		 
		//表单提交
		function surebuyone(){
			if($("#onephone").val() == ""){
				alert("手机号码不能为空");
				return;
			}
			if($("#onesale_money").val() == "" || $("#onesale_money").val() == ""){
				alert("金额不能为空或是0");
				return;
			}
			//判断积分或余额是否支付
			var m=0;
			var user_integral=$("#oneuser_integral").val();
 			if(user_integral == ""){
				user_integral="0";
			}
 			var user_balance=$("#oneuser_balance").val();
  			if(user_balance == ""){
  				user_balance="0";
			}
  			m=parseFloat(user_integral)+parseFloat(user_balance);
			if( m == 0){
				one();
			}else{
				zhifutype="one";
				$(".new_tcwhw").show();
				//发送验证码
				getDxCode($("#onephone").val());
			}
 		}
 		function one(){
			$("#oneForm").ajaxSubmit({  
			  	url : '../zhsh/storeapp_payhHstory/sureConfirmed.do',
		        type: "POST",//提交类型  
 		      	dataType:"json",
		   		success:function(data){
		   			 if(data.result == "0"){
		   				 alert(data.message);
		   			 }else{
		   				alert(data.message);
		   				window.location.reload(); //刷新当前页面
		   			 }
				}
			});
		}
		
		//表单提交
		function surebuytwo(){
			if($("#twophone").val() == ""){
				alert("手机号码不能为空");
				return;
			}
			var n=0;
			$(".gci").each(function(i,o){
				if($(o).val() != "" && $(o).val() != "0"){
					n=1;
				}
			});
			if(n == 0){
				alert("金额不能为空");
				return;
			}
			//判断积分或余额是否支付
			var m=0;
			var user_integral=$("#twouser_integral").val();
 			if(user_integral == ""){
				user_integral="0";
			}
 			var user_balance=$("#twouser_balance").val();
  			if(user_balance == ""){
  				user_balance="0";
			}
  			m=parseFloat(user_integral)+parseFloat(user_balance);
			if(m == 0){
				two();
			}else{
				zhifutype="two";
				$(".new_tcwhw").show();
				//发送验证码
				getDxCode($("#twophone").val());
			}
 		}
		//分类买单提交
		function two(){
 			$("#twoForm").ajaxSubmit({  
			  	url : '<%=basePath%>storeapp_payhHstory/sureConfirmed.do',
		        type: "POST",//提交类型  
 		      	dataType:"json",
		   		success:function(data){
		   			 if(data.result == "0"){
		   				 alert(data.message);
		   			 }else{
		   				alert(data.message);
		   				window.location.reload(); //刷新当前页面
		   			 }
				}
			});
		}
		
		//获取验证码
		function getDxCode(phone){
			$.ajax({
		        type:"post",
		        url:'<%=basePath%>app_member/getCode.do', 
		  	 	data:{"phone":phone,"type":"9","user_type":"1"},                
		        dataType:"json",
		        success: function(data){
		        	 	if(data.result == "1"){
		        		 	 window.newCode=data.data;
 				       	 }else{
			       			 alert(data.message);
			       	 	}
		         }
		    });
		}
 		
 		//验证验证码
		function shouyincode(){
			var shouyincode=$("#shouyincode").val();
			if(shouyincode == ""){
				return;
			}
			if(newCode == shouyincode){
				if(zhifutype == "one"){
					one();
				}else if(zhifutype == "two"){
					two();
				}
 			}else{
 				alet("验证码错误");
 			}
		}
	</script>
</body>
</html> --%>