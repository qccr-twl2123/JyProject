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
	<title>开发票</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/zhxx_fwxf.css">
	<style type="text/css">
		table tr:last-child td {
	    	 border-bottom: 1px solid #a4a4a4;
		}
	 	.style_scroll::-webkit-scrollbar
		{
		    width: 8px;
		    height: 8px;
		    background-color: #F5F5F5;
		}
		 
 		.style_scroll::-webkit-scrollbar-track
		{
		    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
		    border-radius: 10px;
		    background-color: #ccffff;
		}
 		.style_scroll::-webkit-scrollbar-thumb
		{
		    border-radius: 10px;
		    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
		    background-color: #fff;
		}
 	</style>
 </head>
<body style="width: 98%;">
<div class="fpxq_alt">   <!-- 发票详情 -->
	<ul class="fp_xq style_scroll" style="background: #ccffff;position: absolute;max-height: 400px;overflow-y: scroll;margin-top: 6%;">
		<li style="text-align:center;line-height:2.8;">
			<span>开票明细</span>
		</li>
		<li>
			<table cellspacing="0" cellpadding="0" style="width: 94%;margin: 10px 3%;">
				<tr>
				    <td>
				    	<span>发票金额：</span>
				    	<span>￥<span id="detail_bill_money" class="qingkong"></span></span>
				    </td> 
				    <td>
				    	<span>发票抬头：</span>
				    	<span id="detail_bill_tt" class="qingkong"></span>
				    </td>
				</tr>
				<tr>
					<td>
						<span>发票状态：</span>
						<span id="detail_chuli_status" class="qingkong"></span>
					</td>
					<td>
						<span>发票编号：</span>
						<span id="detail_bill_number" class="qingkong"></span>
					</td>
				</tr>
				<tr>
					<td>
						<span>发票类型：</span>
						<span id="detail_bill_type" class="qingkong">增值税普通发票</span>
					</td>
					<td>
						<span>申请时间：</span>
						<span id="detail_createtime" class="qingkong"></span>
					</td>
				</tr>
				<tr>
					<td>
						<span>开户行：</span>
						<span id="detail_sj_bankname" class="qingkong"></span>
					</td>
					<td>
						<span>银行账号：</span>
						<span id="detail_sj_banknumber" class="qingkong"></span>
					</td>
				</tr>
				<tr>
					<td>
						<span>邮件编码：</span>
						<span id="detail_postcode" class="qingkong"></span>
					</td>
					<td>
						<span>联系电话：</span>
						<span id="detail_sj_phone" class="qingkong"></span>
					</td>
				</tr>
				<tr>
					<td>
						<span>物流公司：</span>
						<span id="detail_kd_name" class="qingkong"></span>
					</td>
					<td>
						<span>快递编号：</span>
						<span id="detail_kd_number" class="qingkong"></span>
					</td>
				</tr>
			</table>
		</li>
		<li style="text-align:center;line-height:2.8;">
			<span>关联订单</span>
		</li>
 		<li>
			<table cellspacing="0" cellpadding="0" style="width: 94%;margin: 10px 3%;">
				<thead>
					<td>订单编号</td>
					<td>产品名称</td>
					<td>金额</td>
					<td>成功时间</td>
				</thead>
				<tbody id="allorderandid">
				
				</tbody>
			</table>
		</li>
	</ul>
</div>

	<ul class="fp">   <!--  发票页 -->
		<li class="fp_head clf" style="line-height:2.5;">
			<a href="storepc/goFeeNextNumber.do?store_id=${storepd.store_id}" class="img_box fp_back go_xf">
				<img src="img/zhxx_back.png" alt="">
			</a>
			<a  class="img_box fp_back go_prop" >
				<img src="img/zhxx_back.png" alt="">
			</a>
			<span>①开发票订单选择</span>
			<span>②确认发票信息和地址</span>
			<div class="fp_bg"></div>
		</li>
		<li style="line-height:2.5;padding-left:20px;" class="fpxz">  
			<span class="col-red" style="display:inline-block;height:100%;vertical-align: top;">温馨提示：</span>
			<ul style="display:inline-block;">
				<li>
					<span>1、首次申请开发票请先填写《<span class="col-blue sc_inf_list">开票主体信息和地址</span>》</span>
				</li>
				<li>
					<span>2、开票主体信息和地址</span>
				</li>
				<li>
					<span>3、发票于每月5、15、25号统一开具并寄送服务商处，请自行到服务商处索取</span>
				</li>

			</ul>
		</li>
		<li style="line-height:2.5;padding-left:20px;" class="fpxz">
			<span style="font-weight: bold;">可开订单列表</span>
		</li>
		<li style="line-height:2.5;padding-left:20px;" class="fpxz">
			<!-- 可开订单列表 -->
			<table  cellspacing="0" cellpadding="0" border="1" style="width:90%;margin:0 3%;" class="kklb">    
				<thead>
					<td><input type="checkbox" class="checkall setAll" id = "setAll"  ></td>
					<td>订单编号</td>
					<td>产品名称</td>
					<td>订单实付金额</td>
					<td>可开票金额</td>
					<td>订单支付时间</td>
				</thead>
				<tbody>
					<!-- tr数量为1时    show
					不等于1    hide -->
					<c:choose>
						<c:when test="${order_size == 0 }">
							<tr style="text-align:center;padding-top:20px;" class="dd_tips">	
								<td colspan="6">
									<img src="img/zhxx_tips.png" alt="" style="width:30px;height:30px;vertical-align: middle;">
									<span style="font-weight:bord;vertical-align: middle;font-size:112.5%;
									line-height:20px;">你目前没有可开的订单</span>
								</td>	
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${orderList}" var="var">
								<tr>
									<td><input name = "chose"   type="checkbox" value="${var.waterrecord_id}"  order_money="${var.arrivalmoney}"></td>
									<td>${var.waterrecord_id}</td>
									<td>
										<c:if test="${var.profit_type eq '9'}">商家购买服务费</c:if>
										<c:if test="${var.profit_type eq '10'}">商家购买服务费（续）</c:if>
									</td>
									<td>${var.money}</td>
									<td>${var.arrivalmoney}</td>
									<td>${var.createtime}</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
 				</tbody>
				<tfoot>
					<td colspan="5">
						<span style="float:left;">已选总金额：￥<span class="allmoney"></span></span>
						<span style="float:right;">待开发票金额：￥<span class="allmoney"></span></span>
					</td>
					<td class="sqfp_btn"  onclick="goTwoSuoQu()">索取发票</td>
				</tfoot>
			</table>
		</li>

		
		<li style="line-height:2.5;padding-left:20px;" class="fpxz">
			<span style="font-weight: bold;">已开订单列表</span>
		</li>
		<li class="fpxz">
			<table cellspacing="0" cellpadding="0" border="1" style="width:90%;margin:0 3%;">
				<thead>
					<td>序号</td>
					<td>发票金额</td>
					<td>发生时间</td>
					<td>发票抬头</td>
					<td>收取方式</td>
					<td>状态</td>
					<td>操作</td>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fp_size == 0 }">
							<tr style="text-align:center;padding-top:20px;" class="dd_tips">	
								<td colspan="7">
									<img src="img/zhxx_tips.png" alt="" style="width:30px;height:30px;vertical-align: middle;">
									<span style="font-weight:bord;vertical-align: middle;font-size:112.5%;
									line-height:20px;">你目前没有发票</span>
								</td>	
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${fpList}" var="var" varStatus="vs">
								<tr>
									<td>${vs.index+1 }</td>
									<td>${var.bill_money }</td>
									<td>${var.createtime }</td>
 									<td>${var.bill_tt}</td>
									<td>${var.kd_name}</td>
									<td>
										<c:if test="${var.chuli_status eq '0' }">等待处理</c:if>
										<c:if test="${var.chuli_status eq '1' }">已处理</c:if>
										<c:if test="${var.chuli_status eq '2' }">正在寄送</c:if>
										<c:if test="${var.chuli_status eq '3' }">待签收</c:if>
										<c:if test="${var.chuli_status eq '4' }">已签收</c:if>
									</td>
									<td>
										<c:if test="${var.chuli_status eq '3' }"><span class="biankuang-s" onclick="qianshou('${var.bill_id}')">签收</span></c:if>
 										<span class="biankuang-s xq_btn" onclick="fapiaoDetail('${var.bill_id}')">详情</span>
									</td>
								</tr>
		 					</c:forEach>
						</c:otherwise>
					</c:choose>
  				</tbody>
			</table>
		</li>
		
		
		<!-- 第二步：确认发票信息 地址 -->
		<li class="qrxxdz">
			<table cellspacing="0" cellpadding="0" style="width:100%;">
				<tr>
				    <td>
				    	<span>发票金额：</span>
				    	<span>￥<span class="allmoney">1700.00</span></span>
				    </td>
				   <%--  <td>
						<span>收件人：</span>
						<span>${fppd.sj_name}</span>
					</td> --%>
				    <td>
						<span>开户行：</span>
						<span>${fppd.sj_bankname}</span>
					</td>
				</tr>
 				<tr>
 					<td>
				    	<span>发票抬头：</span>
				    	<span>${fppd.bill_tt}</span>
				    </td>
					<%-- <td>
						<span>收件地址：</span>
						<span>${fppd.sj_address}</span>
					</td> --%>
					<td>
						<span>银行账号：</span>
						<span>${fppd.sj_banknumber}</span>
					</td>
 				</tr>
				<tr>
					<td>
						<span>发票类型：</span>
						<span>${fppd.bill_type}</span>
					</td>
					<td>
						<span>联系电话：</span>
						<span>${fppd.sj_phone}</span>
					</td>
 				</tr>
				<tr>
					<td>
						<span>邮件编码：</span>
						<span>${fppd.postcode}</span>
					</td>
 				</tr>
 			</table>
 		</li>
		<li class="qrxxdz" style="text-align:right;width:94%;padding:10px 3%;">
 				<span class="anniu-l qr_sqfp" onclick="saveBill()">索取发票</span>
		</li>
	</ul>
	
	 <!-- 首次申请信息表 -->
	<ul class="sc_fp">   
		<li class="sc_fp_head clf" style="line-height:2.5;">
			<a href="fapiao/goAddFaPiao.do?store_id=${storepd.store_id}" class="img_box sc_fp_back">
				<img src="img/zhxx_back.png" alt="">
			</a>
 			<span>首次申请开发票信息表</span>
			<div class="sc_fp_bg"></div>
		</li>
		<li class="list_name">
			<ul>
				<li>
					<span class="col-red">*</span><span>开票主体名称：</span>
				</li>
				<li>
					<span class="col-red">*</span><span>主体类型：</span>
				</li>
				<li>
					<span class="col-red">*</span><span>纳税人类型：</span>
				</li>
				<li>
					<span class="col-red">*</span><span>营业地址：</span>
				</li>
				<li>
					<span class="col-red">*</span><span>营业电话：</span>
				</li>
				<li>
					<span class="col-red">*</span><span>统一社会信用代码/纳税人识别号：</span>
				</li>
				<li>
					<span class="col-red">*</span><span>开户行：</span>
				</li>
				<li>
					<span class="col-red">*</span><span>银行账号：</span>
				</li>
				<li>
					<span class="col-red">*</span><span>联系电话：</span>
				</li>
				<li>
					<span class="col-red">*</span><span>邮政编码：</span>
				</li>
			</ul>
		</li>
		<li class="list_inp">
			<ul>
				<li>
					<!-- 开票主体名称：	 -->
					<input type="text" id="bill_tt" class="bitian" value="${fppd.bill_tt }">
				</li>
				<li>
					<!-- 主体类型： -->
					<input type="text" id="bill_type" class="bitian" value="${fppd.bill_type }">
				</li>
				<li>
					<!-- 纳税人类型： -->
					<span class="check_item">
						<input type="radio" name="nsr" value="1" ${fppd.nsr_type eq '1'?'checked':''}>
						<span>一般纳税人</span>
					</span>
					<span class="check_item">
						<input type="radio" name="nsr"  value="2" ${fppd.nsr_type ne '1'?'checked':''}>
						<span>小规模纳税人</span>	
					</span>
					
				</li>
				<li>
					<!-- 营业地址： -->
					<input type="text" id="yy_address" class="bitian" value="${fppd.yy_address }">
				</li>
				<li>
					<!-- 营业电话： -->
					<input type="text" id="yy_phone" class="bitian" value="${fppd.yy_phone }">
				</li>
				<li>
					<!-- 统一社会信用代码/税务等级号： -->
					<input type="text" id="swdj_number" class="bitian" value="${fppd.swdj_number }">
				</li>
<%-- 				<li>
					<!-- 收件人： -->
					<input type="text" id="sj_name" class="bitian" value="${fppd.sj_name }">
				</li>
				<li>
					<!-- 收件地址： -->
					<input type="text" id="sj_address" class="bitian" value="${fppd.sj_address }">
				</li> --%>
				<li>
					<!-- 开户行： -->
					<input type="text" id="sj_bankname" class="bitian" value="${fppd.sj_bankname }">
				</li>
				<li>
					<!-- 银行账号： -->
					<input type="text" id="sj_banknumber" class="bitian" value="${fppd.sj_banknumber }">
				</li>
				<li>
					<!-- 联系电话： -->
					<input type="text" id="sj_phone" class="bitian" value="${fppd.sj_phone }">
				</li>
				<li>
					<!-- 邮政编码： -->
					<input type="text" id="postcode" class="bitian" value="${fppd.postcode }">
				</li>
				<li>
					<span class="anniu-l mar_l sc_tj" onclick="savebillinfor()">提交</span>
				</li>
			</ul>
		</li>
	</ul>
</body>
<script src="js/jquery-1.8.0.min.js"></script>
<script>
var allorder="";

//前往下一步索取发票
function goTwoSuoQu(){
	if("${infor_ok}" == "0"){
		alert("请先填写*开票主体信息和地址*");
	}else{
		if(allorder == ""){
			alert("请勾选订单！！");
			return;
		}
		//显示隐藏窗口
		$(".qrxxdz").css("display","block")
		$(".fpxz").css("display","none")
		$(".fp_bg").css("left","54%")
		$(".go_prop").css("display","block")
		$(".go_xf").css("display","none")
	}
}


//信息提交
function savebillinfor(){
	var n=0;
	$(".bitian").each(function(x,obj){
		if($(obj).val().trim() == ""){
			n=1;
		}
	});
	if(n==1){
		alert("带*都是必填项，请检查一下");
		return;
	}else{
		$.ajax({
            type:"POST",
            url:"fapiao/savebillinfor.do", 
            data:{
            	"billinfor_id":"${fppd.billinfor_id}",
            	"bill_tt":$("#bill_tt").val(),
            	"bill_type":$("#bill_type").val(),
            	"nsr_type": $("input[name='nsr']:checked").val(),
            	"yy_address":$("#yy_address").val(),
            	"yy_phone":$("#yy_phone").val(),
            	"swdj_number":$("#swdj_number").val(),
            	"sj_bankname":$("#sj_bankname").val(),
            	"sj_banknumber":$("#sj_banknumber").val(),
            	"sj_name":$("#sj_name").val(),
            	"sj_phone":$("#sj_phone").val(),
            	"sj_address":$("#sj_address").val(),
            	"postcode":$("#postcode").val(),
            	"sq_id":"${storepd.store_id}",
            	"sq_type":"1"
             }  ,              
            dataType:"json",
            success: function(data){
       			if(data.result == "1"){
        				window.location.href="<%=basePath%>fapiao/goAddFaPiao.do?store_id=${storepd.store_id}"; 
       			}
            }
 		});
	}
}

//索取发票
function saveBill(){
	var sp_file_id="${fppd.ls_sp_file_id}";
	if("${fppd.sq_type}" == "2"){
		sp_file_id="${fppd.sq_id}";
	}
	$.ajax({
            type:"POST",
            url:"<%=basePath%>fapiao/saveBill.do", 
            data:{
            	"bill_money":allmoney,
            	"billinfor_id":"${fppd.billinfor_id}",
            	"sp_file_id":sp_file_id,
             	"allorder":allorder,
            	"chuli_status":"0"
             }   ,             
            dataType:"json",
            success: function(data){
       			if(data.result == "1"){
       				alert("提交成功");
       				window.location.href="<%=basePath%>fapiao/goAddFaPiao.do?store_id=${storepd.store_id}"; 
       			}
            }
 	});
	}

//发票详情
function fapiaoDetail(bill_id){
	$.ajax({
        type:"POST",
        url:"fapiao/fapiaoDetail.do", 
        data:{
        	"bill_id":bill_id  
         }   ,             
        dataType:"json",
        success: function(data){
        	var pd=data.data;
        	var billlistandid=pd.billlistandid;
   			if(data.result == "1"){
    				$(".fpxq_alt").css("display","block");
    				$(".qingkong").html("");
    				$("#detail_bill_money").html(pd.bill_money);
    				$("#detail_bill_type").html(pd.bill_type);
    				$("#detail_bill_tt").html(pd.bill_tt);
    				$("#detail_bill_number").html(pd.bill_number);
    				var chuli_status=pd.chuli_status;
    				if(chuli_status =="0"){
    					$("#detail_chuli_status").html("等待处理");
    				}else if(chuli_status == "1"){
    					$("#detail_chuli_status").html("等待寄出");
    				}else if(chuli_status == "2"){
    					$("#detail_chuli_status").html("等待收件");
    				}else if(chuli_status == "3"){
    					$("#detail_chuli_status").html("等待签收");
    				}else{
    					$("#detail_chuli_status").html("已签收");
    				}
    				
    				$("#detail_createtime").html(pd.createtime);
    				$("#detail_sj_bankname").html(pd.sj_bankname);
    				$("#detail_sj_banknumber").html(pd.sj_banknumber);
    				$("#detail_sj_phone").html(pd.sj_phone);
    				$("#detail_postcode").html(pd.postcode);
    				$("#detail_kd_name").html(pd.kd_name);
    				$("#detail_kd_number").html(pd.kd_number);
    				$("#allorderandid").empty();
    				for (var i = 0; i < billlistandid.length; i++) {
    					var text_content="";
    					if(billlistandid[i].profit_type == "9"){
    						text_content="商家服务费";
    					}else{
    						text_content="商家服务费（续）";
    					}
    					var str="<tr> <td>"+billlistandid[i].waterrecord_id+"</td> <td>"+text_content+"</td> <td>￥"+billlistandid[i].money+"</td> <td>"+billlistandid[i].createtime+"</td> </tr>";
    					$("#allorderandid").append(str);
					}
   			}
        }
		});
}
//商家签收
function qianshou(bill_id){
	var url = "<%=basePath%>/fapiao/updatebill.do?bill_id="+bill_id+"&chuli_status=4&tm="+new Date().getTime();
	$.get(url,function(data){
		if(data=="success"){
			 window.location.reload();
		}
	});
}


$(function(){
	$(".qrxxdz").css("display","none")
	$(".sc_fp").css("display","none")
 
	$(".sc_inf_list").click(function(){    /*首次申请开发票信息表   进入*/
		$(".fp").css("display","none")
		$(".sc_fp").css("display","block")
	})


	// 已开列表  详情  关闭
	$(".fpxq_alt").click(function(){
		$(".fpxq_alt").css("display","none")
	})

 

	// 文字选择input
	$(".check_item span").css({"cursor":"pointer"})
	$(".check_item span").click(function(e){
		$(e.target).prev()[0].click()
	});
	
	//
	$(".go_prop").css("display","none")
 	$(".go_prop").click(function(){
		$(".qrxxdz").css("display","none")
		$(".fp_bg").css("left","4%")
		$(".fpxz").css("display","block")
	})
	
	//复选框的全选和全不选
	$(".setAll").click(function() {
		$('input[name="chose"]').attr("checked",this.checked); 
		clickAllMoney();
	});
	var $subBox = $("input[name='chose']"); 
	$subBox.click(function(){
		 $(".setAll").attr("checked",$subBox.length == $("input[name='chose']:checked").length ? true : false);
		 clickAllMoney();
	});
	//统计金钱
	function clickAllMoney(){
			var m="";
			var h=0;
			$("input[name='chose']:checked").each(function(n,obj){
				m+=$(obj).val()+",";
				var x=$(obj).attr("order_money");
 				h+=parseFloat(x);
			});
 			window.allorder=m;
			window.allmoney=h.toFixed(2);
			$(".allmoney").html(allmoney);
	}
	
});




 </script>
</html>