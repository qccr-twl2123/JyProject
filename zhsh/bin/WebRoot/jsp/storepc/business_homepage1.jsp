<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>首页-收银和取货</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/sy_sy.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<script src="js/jquery.tips.js"></script>
	<script src="js/jquery.form.js"></script>
	<style type="text/css">
	.cont1{
		display: block;overflow-y: auto;width: 92%;
	}


 	</style>
</head>
<body>
	<c:if test="${storeqx.look eq '1'}">
    <input type="hidden" class="order_id" value=""/>
	<c:choose>
		<c:when test="${pd.issortjf eq '3'}">
			<div class="list_cont">
				<ul>
					<li class="act">按总金额</li>
					<li>按类别金额</li>
				</ul>
			</div>
			<div class="cont0" >
				需开通营销控制台-积分方式-整店送积分设置
			</div>
			<div class="cont1">
				需开通营销控制台-积分方式-品类送积分设置		 
			</div>	
		</c:when>
		<c:when test="${pd.issortjf eq '0'}">
			<div class="list_cont">
				<ul>
					<li   class="act">按总金额</li>
					<li>按类别金额</li>
				</ul>
			</div>
			<div class="cont0" >
			    <form action="<%=basePath%>storeapp_payhHstory/sureConfirmed.do" method="post" name="Form" id="oneForm">
					<input type="hidden" name="session_orderid" value="${session_orderid}">
					<input type="hidden" name="pay_type" value="1">
					<input type="hidden" name="apptype" value="pc">
					<input type="hidden" name="whw_reittance" value="weihanwen">
					<input type="hidden" name="pay_sort_type" value="1">
					<input type="hidden" name="order_id" value="">
					<input type="hidden" name="store_operator_id" value="${pd.store_operator_id}">
					<input type="hidden" name="store_id" id="onestore_id" value="${pd.store_id}">
					<input type="hidden" name="member_id" id="onemember_id" value="">
					<ul>
					<li>
						<span class="inp">手机号码</span>：
						<input type="text" name="phone" value="" id="onephone" oninput="getInteger(this,'one')">
					</li>
					<li>
						<span class="inp">桌号</span>：
						<select  name="desk_no"  id="desk_no" class="zhuohao">
							<c:forEach items="${deskList}" var="var">
 								<option value="${var.table_name}">${var.table_name}</option>
 							</c:forEach>
						</select>
					</li>
					<li>
						<span class="inp">消费金额</span>：
						<input  name="sale_money"  id="onesale_money" type="text" value=""  oninput="leibieone('one')">
					</li>
					<li>
						<span class="inp">不优惠金额</span>：
						<input name="no_discount_money"   id="oneno_discount_money" type="text" value="" oninput="leibieone('one')"/>
					</li>
					<li>
						<span class="inp">优惠项</span>：
						<span id="onediscountList">
							<c:forEach items="${pd.yxlist}" var="var" varStatus="vsyx">
								<c:if test="${var ne '' }">
									<span class="col-blue" style="padding:0 5px;">${vsyx.index+1}.${var}</span>
								</c:if>
							</c:forEach>
						</span>
		 				<input name="discount_content" id="onediscount_content" type="hidden" value="" />
					</li>
					<li>
						<span class="inp">优惠后金额</span>：
						<span class="inp onemoney"></span><input name="discount_money" id="onediscount_money" type="hidden" value="" />
						<span class="inp last">送积分</span>：
						<span class="inp oneget_integral"></span><input  name="get_integral" id="oneget_integral" type="hidden" value="" />
					</li>
					<li>
						<span class="inp">用积分支付</span>：
						<input type="text"   name="user_integral" id="oneuser_integral"  value=""  oninput="changereduceacmoney(this,'one')"/> 
						<span>分</span>
						<span class="inp last">剩余积分</span>
						<span id="onenow_integral" style="color:red"></span>
					</li>
					<li>
						<span class="inp wid">还需支付</span>：
						<span class="inp acmoney oneactual_money"></span>
						<input name="actual_money" id="oneactual_money" type="hidden" value="" />
					</li>
					<c:if test="${storeqx.add eq '1'}">
	 					<li>
							<span class="anniu enter onesubmit" onclick="surebuyone()">确认收银</span>
							<input name="redpackage_id" id="oneredpackage_id" type="hidden" value="" />
							<input name="store_redpackets_id" id="onestore_redpackets_id" type="hidden" value="" />
						</li>
					</c:if>
					<li>
						<span class="tips">提示：请确认会员现金或刷卡支付成功后再点击</span>
					</li>
					</ul>
					<span class="anniu check al_xgmm">取货验货</span>
				</form>
			</div>
			<div class="cont1">
					需开通营销控制台-积分方式-品类送积分设置		 
			</div>
		</c:when>
		<c:otherwise>
			<div class="list_cont">
				<ul>
					<li>按总金额</li>
					<li  class="act">按类别金额</li>
				</ul>
			</div>
			<div class="cont0">
				 需开通营销控制台-积分方式-整店送积分设置
			</div>
			<div class="cont1">
			    <form action="<%=basePath%>storeapp_payhHstory/sureConfirmed.do" method="post" name="Form" id="twoForm">
					<input type="hidden" name="pay_type" value="1">
					<input type="hidden" name="session_orderid" value="${session_orderid}">
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
					<ul>
					<li>
						<span class="inp">手机号码</span>：
						<input type="text"  name="phone" value=""  id="twophone" oninput="getInteger(this,'two')"/>
					</li>
					<li>
						<span class="inp">桌号</span>：
						<select name="desk_no"  id="desk_no" class="zhuohao">
							<c:forEach items="${deskList}" var="var">
								<option value="${var.table_name}">${var.table_name}</option>
							</c:forEach>
						</select>
					</li>
					<li>
						<span class="inp" style="vertical-align: top;">商品大类：</span>
						<div class="item_sp_box">
							<c:forEach items="${pd.sortList}" var="sortvar" varStatus="vs">
								<div class="sp_item">
									<input type="hidden" class="goods_category_id" value="${sortvar.goods_category_id }"/>
									<span>
										${vs.index+1}.${sortvar.name} 
									</span>
									<input class="gci"  value="" type="text" id="${sortvar.goods_category_id}" oninput="leibietwo('two')">
									<span>返积分率<span class="col-red" id="${sortvar.goods_category_id}jf">${sortvar.back_rate }</span>%</span>
									<input type="hidden" id="${sortvar.goods_category_id}zk" value="${sortvar.zk_rate }"/>
 								</div>
							</c:forEach>
						</div>
					</li>
					<li>
						<span class="inp">实付金额</span>：
						<span class="inp" id="twosale_money"></span>
 						<span class="inp last">送积分</span>：
						<span class="inp twoget_integral"></span>
					</li>
					<li>
							<span class="inp">优惠项</span>：
							<span id="twodiscountList">
								<c:forEach items="${pd.yxlist}" var="var" varStatus="vsyx">
									<c:if test="${var ne '' }">
										<span class="col-blue" style="padding:0 5px;">${vsyx.index+1}.${var}</span>
									</c:if>
								</c:forEach>
							</span>
							<input name="discount_content" id="twodiscount_content" type="hidden" value="" />
 					</li>
					<li>
							<span class="inp">优惠后金额</span>：
							<span class="inp money twomoney"></span><input name="discount_money" id="twodiscount_money" type="hidden" value="0" />
					</li>
					<li>
						<span class="inp">用积分支付</span>：
						<input type="text"  name="user_integral" id="twouser_integral" value="" oninput="changereduceacmoney(this,'two')" />
						<span>分</span>
						<span class="inp last">剩余积分</span><span id="twonow_integral" style="color:red"></span>
					</li>
					<li>
						<span class="inp wid">还需支付</span>：
						<span class="inp acmoney twoactual_money"></span>
					</li>
					<c:if test="${storeqx.add eq '1'}">
						<li>
							<span class="anniu enter twosubmit" onclick="surebuytwo()">确认收银</span>
							<input name="redpackage_id" id="twodredpackage_id" type="hidden" value="" />
							<input name="store_redpackets_id" id="twostore_redpackets_id" type="hidden" value="" />
						</li>
					</c:if>
					<li>
						<span class="tips">提示：请确认会员现金或刷卡支付成功后再点击</span>
					</li>
					</ul>
					<span class="anniu check al_xgmm">取货验卷</span>
				</form>
			</div>
		</c:otherwise>
	</c:choose>
	
<!-- 提货  -->
	<div class="al_bgbox thhm">
		<div class="al_mark"></div>
			<div class="al_alert">
				<div class="al_title">
					<p>取货验货</p>
					<span class="al_close">×</span>
				</div>
				<div class="al_cont">
					<li>请输入提货券号码<input type="text" maxlength="12" onkeyup="formatBankNo(this)" id="tihuo_id"></li>
				</div>
				<div class="al_button">
					<c:if test="${storeqx.add eq '1'}">
						<div class="al_btn al_baocun" onclick="searchTihuo()">确定号码</div>
					</c:if>
					<div class="al_btn al_quxiao al_close">取消</div>
				</div>
			</div>
	</div>
	<!--提货详情 -->
	<div class="al_bgbox thxq">   
		<div class="al_mark th_mark"></div>
			<div class="al_alert th_al" >
				<div class="al_title">
					<p>取货验货</p>
					<span class="al_close">×</span>
				</div>
				<ul style="font-size:16px;padding-top:15px;overflow-y:scroll;height:80%;">
					<li>
						<span>定单用户：</span> <span id="thphone"></span> &nbsp; &nbsp; 
						<span>姓名：</span> <span id="thname"></span>
					</li>
					<li >
						<span style="float:left;width:19%;">订购物品：</span>
						<ul id="thgoods" style="float:left;;display: inline-block;min-height:60px; width:80%;word-break:break-all; padding:0;">
							<li>asdasdasdasdas</li>
 						</ul>
 					</li>
					<li>
						<span>订购时间：</span> <span id="thtime"></span>
					</li>
					<li>
						<span>提货卷编号：</span> <span id="thid"></span>

					</li>
					<div style="width:100%;text-align:center;">
						<span class="anniu-s qr_sh"  onclick="sureOk()">
							确认提货
						</span>
					</div>
				</ul>
				
			</div>
	</div>
<!-- 短信弹窗 -->
	<div class="al_bgbox dxyz">       
		<div class="al_mark dx_mark"></div>
			<div class="al_alert dx_al">
				<div class="al_title">
					<p>取货验货</p>
					<span class="al_close">×</span>
				</div>
				<div class="al_cont">
					<li>请输入会员提供的短信验证码<input type="text" maxlength="8" id="shouyincode" ></li>
				</div>
				<div class="al_button">
					<div class="al_btn al_baocun dxsure" onclick="shouyincode()">确定</div>
					<div class="al_btn al_quxiao al_close">取消</div>
				</div>
			</div>
	</div>
<!-- 短信验证结束 -->
	</c:if>
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

var bb=$(".list_cont").find("li")
for (var i = 0; i < bb.length; i++) {
	bb[i].num=i
};
bb.click(function(){
	$(bb).removeAttr("class")
	var cont="cont"+this.num
	for (var i = 0; i < bb.length; i++) {
		$(".cont"+i).css({"display":"none"})

	};
	$("."+cont).css({"display":"block"})
	$(this).attr({"class":"act"})

})

// 按钮点击	
	$(function(){
		if("${pd.issortjf}" == "1"){
			$(".cont1").show();
			$(".cont0").hide();
		}else{
			$(".cont0").show();
			$(".cont1").hide();
		}
		var qrsy=$(".enter")
		var btn=$(".al_xgmm");
		var bgbox=$(".thhm");
		var sr=$(".th_al");
		var lw=document.documentElement.clientWidth
		var lh=document.documentElement.clientHeight
		var srcw=0
		var srch=0
		btn.click(function (){
			bgbox.css({"display":"block"});
 		})
		qrsy.click(function(){
 			//$(".dxyz").css({"display":"block"})
			console.log(1)
  		})
		$(".go_th").click(function(){
			$(".thxq").css({"display":"block"})
		})
		$(".th_mark").click(function(){
			$(".thxq").css({"display":"none"})
		})
		$(".dx_mark").click(function(){
			$("dxyz").css({"display":"none"})
		})
		$(".th_mark").click(function (){
			bgbox.css({"display":"none"})
		})
		
		// 关闭、取消、确定按钮点击关闭提示窗
		$(".al_close").click(function(e){
			$(e.target).parent().parent().parent().css({"display":"none"});
			if(zhifutype == "one"){
				$(".onesubmit").attr("onclick","surebuyone()");
			}else if(zhifutype == "two"){
				$(".twosubmit").attr("onclick","surebuytwo()");
			}
			$(".dxsure").attr("onclick","shouyincode()");
			$(".lastsurethisorder").attr("onclick","sureOk()");
		});
		
		
		
		

	})
	
		
		var okbuy=true;
 		//获取用户积分信息
		function getInteger(obj,value){
 			if($(obj).val().length != 11){
 				return;
 			}
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
 			var phone=$(obj).val();
 			$.ajax({
				url:"<%=basePath%>storeapp_payhHstory/getInforByPhone.do",
				type:"post",
				dataType:"json",
				data:{"phone":phone},
				success:function(data){ 
					if(data.result == "0"){
						alert(data.message);
						$(obj).val("");
						$(obj).attr("placeholder","未注册:"+phone);
						okbuy=false;
 					}else{
						$("#"+value+"now_integral").html(parseFloat(data.data.now_integral).toFixed(2));
						$("#"+value+"now_money").html(parseFloat(data.data.now_money).toFixed(2));
						$("#"+value+"member_id").val(data.data.member_id);
					}
   				}
			});
						
		}
 		
		//获取总金额的优惠信息
		function leibieone(value){
			$("#oneuser_integral").val("0");
			$("#oneuser_balance").val("0");
			isNumberOk($("#onesale_money"));
			isNumberOk($("#oneno_discount_money"));
			var sale_money=$("#onesale_money").val();
			var no_discount_money=$("#oneno_discount_money").val();
			if(sale_money =="" ){//判断不是数子
				sale_money="0";
			}
			if(no_discount_money =="" ){
				no_discount_money="0";
			}
			if(parseFloat(sale_money)/2-parseFloat(no_discount_money) < 0){
				alert("不优惠金额不能大于总金额的50%");
				$("#oneno_discount_money").val(no_discount_money.substr(0,no_discount_money.length-1));
				return;
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
					if(data.result == "0"){
						alert(data.message);
						return;
					}
					var countpd=data.data.countpd;
					var yingxiaoList=data.data.yingxiaoList;
 					$(".onemoney").html(parseFloat(countpd.paymoney).toFixed(2));
					$("#onediscount_money").val(countpd.reducemoney);
 					$("#oneget_integral").val(countpd.zengjf);
					$(".oneget_integral").html(parseFloat(countpd.zengjf).toFixed(2));
					$("#oneredpackage_id").val(countpd.red_id);
					$("#onestore_redpackets_id").val(countpd.zengid);
					$("#oneactual_money").val(countpd.paymoney);
					$(".oneactual_money").html(parseFloat(countpd.paymoney).toFixed(2));
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
				isNumberOk($("#"+id));
				var s2=$("#"+id).val();
				if(s2 =="" || s2 == "0"){//判断不是数子
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
					$("#twosale_money").html(xxx.toFixed(2));
					$(".twosale_money").val(xxx.toFixed(2));
					$(".twomoney").html(countpd.paymoney);
					$("#twodiscount_money").val(countpd.reducemoney);
 					$("#twoget_integral").val(countpd.zengjf);
					$(".twoget_integral").html(parseFloat(countpd.zengjf).toFixed(2));
					$("#tworedpackage_id").val(countpd.red_id);
					$("#twostore_redpackets_id").val(countpd.zengid);
					$("#twoactual_money").val(countpd.paymoney);
					$(".twoactual_money").html(parseFloat(countpd.paymoney).toFixed(2));
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
		
		
		//改变实际金额
		var addnumber="0";
		function changereduceacmoney(obj,value){
			isNumberOk($(obj));
			var member_id=$("#"+value+"member_id").val();
			//获取实际支付金额
			var m1=$("#"+value+"now_integral").html();
 			var m5=$("."+value+"money").html();
			var m2=$("."+value+"actual_money").html();
			var m3=$("#"+value+"user_integral").val();//使用的积分
 			if(m1 == "" || parseFloat(m1).toFixed(2)-parseFloat(m3).toFixed(2) < 0){
				alert("积分不足");
 				$("#"+value+"user_integral").val(m3.substr(0,m3.length-1));
				return;
			}
 			if(m3 ==""){
				m3="0";
			}
 			var n=0;
 			if(m1 != "" && m2 != "" ){
  				n=parseFloat(m5).toFixed(2)-parseFloat(m3).toFixed(2);
  				if(n<0){
					n=0;
					alert("支付数目过多，请重新填写");
					$(obj).val($(obj).val().substr(0,$(obj).val().length-1));
					return;
				}else{
					$("#"+value+"actual_money").val(n.toFixed(2));
					$("."+value+"actual_money").html(n.toFixed(2));
				}
 			}
 		}
		
		
		
		var zhifutype="one";
 		//总金额提交
		function surebuyone(){
			$(".onesubmit").attr("onclick","");
 			if($("#onephone").val() == ""){
				alert("手机号码不能为空");
				$(".onesubmit").attr("onclick","surebuyone()");
				return;
			}
			if($("#onesale_money").val() == "" || $("#onesale_money").val() == ""){
				alert("金额不能为空或是0");
				$(".onesubmit").attr("onclick","surebuyone()");
				return;
			}
			//判断积分或余额是否支付
			var m=0;
			var user_integral=$("#oneuser_integral").val();
 			if(user_integral == ""){
				user_integral="0";
			}
   			m=parseFloat(user_integral).toFixed(2);
			if( m == 0){
				one();
			}else{
				zhifutype="one";
				$(".dxyz").show();
				//发送验证码
				getDxCode($("#onephone").val());
			}
 		}
 		function one(){
 			$("#oneForm").ajaxSubmit({  
			  	url : '<%=basePath%>storeapp_payhHstory/sureConfirmed.do',
		        type: "POST",//提交类型  
 		      	dataType:"json",
		   		success:function(data){
		   			 if(data.result == "0"){
		   				 alert(data.message);
		   				$(".onesubmit").attr("onclick","surebuyone()");
		   				$(".dxsure").attr("onclick","shouyincode()");
		   			 }else{
		   				alert(data.message);
		   				window.location.reload(); //刷新当前页面
		   			 }
				}
			});
		}
		
		//分类提交
		function surebuytwo(){
			$(".twosubmit").attr("onclick","");
			if($("#twophone").val() == ""){
				alert("手机号码不能为空");
				$(".twosubmit").attr("onclick","surebuytwo()");
				return;
			}
			if($("#twosale_money").text() == "" || $("#twosale_money").text() == ""){
				alert("金额不能为空或是0");
				$(".twosubmit").attr("onclick","surebuytwo()");
				return;
			}
			//判断积分或余额是否支付
			var m=0;
			var user_integral=$("#twouser_integral").val();
 			if(user_integral == ""){
				user_integral="0";
			}
   			m=parseFloat(user_integral).toFixed(2);
			if(m == 0){
				two();
			}else{
				zhifutype="two";
				$(".dxyz").show();
				//发送验证码
				getDxCode($("#twophone").val());
			}
 		}
		function two(){
   			$("#twoForm").ajaxSubmit({  
			  	url : '<%=basePath%>storeapp_payhHstory/sureConfirmed.do',
		        type: "POST",//提交类型  
 		      	dataType:"json",
		   		success:function(data){
		   			 if(data.result == "0"){
		   				 alert(data.message);
		   				$(".twosubmit").attr("onclick","surebuytwo()");
		   				$(".dxsure").attr("onclick","shouyincode()");
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
			$(".dxsure").attr("onclick","");
			var shouyincode=$("#shouyincode").val();
			if(shouyincode == ""){
				alert("验证码不能为空！！");
				$(".dxsure").attr("onclick","shouyincode()");
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
 				$(".dxsure").attr("onclick","shouyincode()");
 			}
		}
 		
 		

		//查询提货卷
		function searchTihuo(){
			var tihuo_id = $("#tihuo_id").val();
			if(tihuo_id == null || tihuo_id == "" ){
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
						$('.thxq').css('display','block');
						$('.thhm').css('display','none');
						var pd=data.data;
						order_id=pd.order_id;
						$("#thname").text(pd.name);
						$("#thid").text(pd.tihuo_id);
						$("#thphone").text(pd.phone);
						$("#thtime").text(pd.startdate);
						var goodsList=pd.goodsList;
						for(var i=0;i<goodsList.length;i++){
							$("#thgoods").append("<li' id='thgoods'> <span class='new_tc2_p2_sp1'></span> <span>"+goodsList[i].goods_name+"x"+goodsList[i].shop_number+"</span> </li>");
						}
					}else{
						alert(data.message);
						$('.thhm').css('display','block');
 					}
  				}
			});
						
		}
		
		
		//确认提货
		var order_id="";
		function sureOk(){
			$(".lastsurethisorder").attr("onclick","");
  			//获取提货信息
 			$.ajax({
				url:"<%=basePath%>/storeapp_payhHstory/sureTiHuo.do",
				type:"GET",
				dataType:"json",
				data:{"order_id":order_id,"login_id":"${storepd.oprator_id}","login_type":"${storepd.login_type}"},
				success:function(data){
					if(data.result == "1"){
						alert("提货成功");
						window.location.reload(); //刷新当前页面
					}else{
						alert(data.message);
						$(".lastsurethisorder").attr("onclick","sureOk()");
					}
  				}
			});
						
		}
		
		
		//判断是否为保留两位小数
		function isNumberOk(obj){
			var value=$(obj).val();
			value=value.replace(/[^\d\.]/g,'');
			var subvalue=value.substring(0, value.length-1);
			if (isNaN(value) ) { 
				value=subvalue;
 			} 
			var firstxiaoshu=value.indexOf(".");
			var endxiaoshu=value.lastIndexOf(".");
			if(firstxiaoshu != endxiaoshu){
				value=subvalue;
			}
			if(firstxiaoshu == 0 || (firstxiaoshu > 0 && value.length - firstxiaoshu > 3)){
				value=subvalue;
			}
			$(obj).val(value);
		}
		
	</script>
</body>
</html>