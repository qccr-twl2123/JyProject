<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>提现</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/jcxx_gjxx.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<style type="text/css">
		tr{
		    line-height: 2.5;
		}
	</style>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
    <input type="hidden" id="wealth_type" value="2">
    <input type="hidden" id="profit_type" value="1">
    <input type="hidden" id="pay_type" value="3">
	<table style="border:0;margin:0;border-collapse:collapse;width:100%;">
		<tr>
			<td>
				<span>商家名称：</span>
				<span>${pd.store_name }</span>
			</td>
			<td>
				<span>商家ID：</span>
				<span>${pd.store_id }</span>
			</td>
		</tr>
		<tr>
			<td >
				<span>账户总金额：</span>
				<span>${pd.allnow_wealth }</span>
			</td>
			<td>
				<span>冻结金额：</span>
				<span>
					${pd.tixian_money}（申请提现后未到账的金额）
				</span>
				
			</td>
		</tr>
		<tr>
			<td>
				<span>本次提现金额：</span>
				<input type="text" class="inp-l" id = "money"  oninput="showmoney(this)" value="0" onclick="chuquzero(this)">
			</td>
			<td>
				<span>提现费率：</span>
				<span>提现金额 > 100提现费率为${pd.withdraw_rate}%；&lt;= 100提现费率为2+${pd.withdraw_rate}%。</span>
			</td>
		</tr>
		<tr>
			<td>
				<span>本次提现手续费：</span>
				<span class="koumoney"></span>	
			</td>
			<td>
				<span>转账金额：</span>
				<span id="daozhang"></span>
			</td>
		</tr>
		<tr>
			<td>
				<span>选择提现账户：</span>
 				<select class="inp-m" id="account_type" onchange="changepaytype(this.value)">
 	               		<option value="3">支付宝</option>
 	               		<option value="4">银联</option>
 	            </select>
			</td>
			<td>
				<span>选择提现帐号：</span>
				<select class="inp-m bankclass"   id = "store_bankcard_id" name = "store_bankcard_id"  style="display:none;">
		                 <option value = "">请选择</option>
		               	 <c:forEach items="${bankList}" var="var" varStatus="vs">
		                    <option value = "${var.store_bankcard_id}">${var.account }${var.dot}尾号${var.kh}</option>
		                 </c:forEach>
		        </select>
				<select class="inp-m alipayclass"   id ="store_alipay_id" name = "store_alipay_id" >
		                 <option value = "">请选择</option>
		               	 <c:forEach items="${alipayList}" var="var" varStatus="vs">
		                    <option value = "${var.store_alipay_id}">${var.alipay_name }账号${var.alipay_number}</option>
		                 </c:forEach>
		        </select>
			</td>
		</tr>
		<tr>
			<td colspan=2>
				<span>登录密码:</span>
				<input id ="pass" type="password" class="inp-l">
			</td>
		</tr>
		<c:if test="${storeqx.add eq '1' and storepd.login_type eq '1'}">
			<tr>
				<td colspan=2 style="text-align:center;">
					<span class="anniu-l" onclick="save()">提交</span>
				</td>
			</tr>
		</c:if>
	</table>
	</c:if>
	<script type="text/javascript">
				//除去O
				function chuquzero(obj){
					var money=$(obj).val();
	        	   	if(money == "0"){
	        	   	   $(obj).val("");
	        	   	   $(".koumoney").html("");
 	        		   $("#daozhang").html("");
	        	   		return;
	        	   	}
				}
				
	
				//判断提现金额
	           function showmoney(obj){
	        	   	var money=$(obj).val();
	        	   	if(money == "" ){
	        	   	   $(".koumoney").html("");
 	        		   $("#daozhang").html("");
	        	   		return;
	        	   	}
	        	    var allmoney="${pd.allnow_wealth}";
	        	    var withdraw_rate="${pd.withdraw_rate}";
	        	    var koumoney=0;
	        	    var daozhang=0;
	        	    if(parseFloat(money) > parseFloat(allmoney)){
	        		   alert("当前余额不足");
	        		   $(obj).val(money.substr(0,money.length-1));
	        		   return;
	        	    }else{
	        	       if(parseFloat(money) >= 100){
	        	    		 koumoney=parseFloat(money)*parseFloat(withdraw_rate)/100;
	        	       }else{
	        	    		 koumoney=parseFloat(money)*parseFloat(withdraw_rate)/100+2;
	        	       }
 	        		   $(".koumoney").html(koumoney.toFixed(2)+"元");
	        		   daozhang=parseFloat(money)-koumoney;
	        		   $("#daozhang").html(daozhang.toFixed(2)+"元");
	        	    }
 	           }
			   
			    //选择到账方式
	           function changepaytype(value){
	        	   if(value == "3"){
	        		   $(".bankclass").hide();
	        		   $(".alipayclass").show();
	        	   }else{
	        		   $(".bankclass").show();
	        		   $(".alipayclass").hide();
	        	   }
	           }
			   
			   //新增提现
				function save(){
					var money = $("#money").val().trim();
					if(money == "" || money == "0"){
					   alert("提现金额不能为空/为0");
					   return;
					} 
					var allmoney="${pd.allnow_wealth}";
					var withdraw_rate="${pd.withdraw_rate}";
					if(parseFloat(money) > parseFloat(allmoney)){
					   alert("当前余额不足");
					   return;
					} 
					var account_type = $("#account_type").val().trim();
					var store_bankcard_id = $("#store_bankcard_id option:selected").val(); 
					var store_alipay_id = $("#store_alipay_id option:selected").val(); 
					if(account_type == "3"){
						if(store_alipay_id == 0 || store_alipay_id == null || store_alipay_id == ""){
							alert("请选择支付宝!");
							return ;
						}
						store_bankcard_id="";
					}else{
						if(store_bankcard_id == 0 || store_bankcard_id == null || store_bankcard_id == ""){
							alert("请选择银行卡!");
							return ;
						}
						store_alipay_id="";
					}
					var password = $("#pass").val().trim();
					var daozhang = $("#daozhang").text();
					if(money == 0 || money == ""){
						alert("请输入金额");
						return ;
					}
					if(password == ""){
						alert("请输入密码");
						return ;
					}
					$.ajax({
							type:"post",
							url:"storepc_pay/saveWithdraw.do",
							data:"in_jiqi=4&store_alipay_id="+store_alipay_id+"&account_type="+account_type+"&user_id="+"${storepd.store_id}"+"&user_type=1&money="+money+"&wealth_type=2&profit_type=1"+
							"&password="+password+"&store_bankcard_id="+store_bankcard_id+"&store_operator_id="+"${storepd.operator_id}"+"&store_id="+"${storepd.store_id}"+"&arrivalMoney="+daozhang,
							success:function(data){
								if(data.result == "1"){
									alert("提现审批，请等待1至2个工作日!");
									window.location.reload();   
								}else{
									alert(data.message);
								}
							}
					});   
						
				}
	</script>
</body>
</html>