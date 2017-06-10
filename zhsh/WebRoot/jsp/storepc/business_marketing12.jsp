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
	<title>满赠</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/yxkt_mz.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<script src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>
 <c:if test="${storeqx.look eq '1'}">
	<ul>
		<li class="clf" style="padding:10px 0 20px 0;">
			<span class="col-red" style="float:left;">声明：</span>
			<ul style="float:left;">
				<li>
					1、满赠与其他营销规则可合并，请在计算综合成本进行选择
				</li>
				<li>
					2、满赠所赠送的现金券和折扣券在指定日期方可生效，礼品为当场有效。
				</li>
			</ul>
		</li>
		<li class="item_li">
			<span>有效时间</span>：
			<input  placeholder="开始时间" type="text"   id="starttime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
			<span>&nbsp;至&nbsp;</span>
			<input   placeholder="结束时间" type="text"  id="endtime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		</li>
		<li class="item_li">
			<span>消费满</span>：
			<input type="text" placeholder="元" id="oneachievemoney" oninput="isNumberOk(this)"  >
			<span>&nbsp;元&nbsp;</span>
		</li>
		<li class="item_li">
			<span>赠</span>：
			<select name="marketsmall_type" id="marketsmall_type" onchange="getType(this.value)" style="  width: 100px; ">
	             	<option value="1">折扣红包</option>
	             	<option value="2">现金红包</option>
	             	<option value="3">送物品</option>
	        </select>
			 <span id="one" >
	             	使用条件：再次消费满<input type="text" id="zkovermoney" placeholder="XX元" oninput="isNumberOk(this)"/>
	             	折扣规则：   <select id="onereduce_zktype">
			 	             		<option value="1">原价折扣</option>
			 	             		<option value="2">优惠后折扣</option>
			 	             </select>
	             	打<input type="text" id="onereduce_zk"  placeholder="折" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" maxlength="2"/>折
  	         </span>
	         <span id="two" style="display:none">
	             	使用条件：再次消费满<input type="text" id="rdovermoney" placeholder="XX元" oninput="isNumberOk(this)"   />
	             	减 <input  placeholder="元" type="text" id="onereduce_money" oninput="isNumberOk(this)"  />元
 	          </span>
	          <span id="three" style="display:none">
 	              	礼品名称：<input type="text" id="onesend_wuping"  placeholder="礼品"/>
	          </span>
		</li>
		<li class="item_li timenumber">
			<span>使用时间</span>：
			<select name="timenumber" >
	             	<option value="1">领取后1天内</option>
	             	<option value="2">领取后2天内</option>
	             	<option value="3">领取后3天内</option>
	             	<option value="5">领取后5天内</option>
	             	<option value="7">领取后7天内</option>
	             	<option value="10">领取后10天内</option>
	             	<option value="15">领取后15天内</option>
	             	<option value="30">领取后30天内</option>
	        </select>
		</li>
		<c:if test="${storeqx.add eq '1'}">
		<li style="text-align:center;">
			<span class="anniu-m" onclick="save()">确定</span>
		</li>
		</c:if>
	</ul>
	<ul style="color:#999;line-height:1.5">
		<li style="line-height:2;">
			<span>
				已发布的满赠活动：
			</span>
		</li>
		<c:forEach items="${mzList}" var="var" varStatus="vs">
		    <li class="padd_l">
				<span>
					${vs.index+1 }、${var.content}
				</span>
			</li>
         </c:forEach>
		 

	</ul>
	</c:if>
       <script type="text/javascript">
       //赠送的类型
		function getType(value){
			if(value == "1"){
				$("#two").hide();
				$(".timenumber").show();
				$("#one").show();
				$("#three").hide();
			}else if(value=="2"){
				$("#two").show();
				$(".timenumber").show();
				$("#one").hide();
				$("#three").hide();
 			}else{
 				$("#two").hide();
				$(".timenumber").hide();
				$("#one").hide();
				$("#three").show();
 			}
		}
		
		//新增
		function save(){
 			var startdate = $("#starttime").val().trim();
			var enddate = $("#endtime").val().trim(); 
			var oneachievemoney = $("#oneachievemoney").val().trim();
  			if(oneachievemoney == ""){
  				alert("金额不能为空");
  				return;
  			}
			var marketsmall_type = $("#marketsmall_type").val().trim();
    			var onesend_redpackage_content = $("#onesend_redpackage").find("option:selected").text();
   			var timenumber=$("#timenumber").val();
   			if(timenumber == null || timenumber == ""){
  				timenumber="0";
  			}
  			var content="";
   			if(startdate == "" || enddate == ""){
  				alert("时间不能为空");
  				return;
  			}
  			var rdovermoney = $("#rdovermoney").val();
  			var onereduce_money = $("#onereduce_money").val();
   			var zkovermoney = $("#zkovermoney").val();
  			var onereduce_zktype = $("#onereduce_zktype").val();
  			var onereduce_zk = $("#onereduce_zk").val();
  			var onesend_wuping = $("#onesend_wuping").val();
  			var redcontent="";
  			var w_money="";
  			if(marketsmall_type == "2"){
  				if(rdovermoney == "" || onereduce_money== ""){
  					alert("金额不能为空");
  	  				return;
  				}
  				if(parseFloat(rdovermoney ) < onereduce_money){
  					alert("满足的金额不能小于优惠金额");
  	  				return;
  				}
  				content=startdate.substring(2,startdate.length)+"日至"+enddate.substring(2,enddate.length)+"日  满"+oneachievemoney+"元送"+onereduce_money+"元现金红包";
  				redcontent="满"+rdovermoney+"元减"+onereduce_money+"元";
  				w_money=onereduce_money;
  			}
   			if(marketsmall_type == "1"){
  				if(zkovermoney == "" || onereduce_zk== ""){
  					alert("折扣/金额不能为空");
  	  				return;
  				}
  				content=startdate.substring(2,startdate.length)+"日至"+enddate.substring(2,enddate.length)+"日  满"+oneachievemoney+"元送"+onereduce_zk+"折红包";
  				redcontent="满"+zkovermoney+"元打"+onereduce_zk+"折";
  				w_money=onereduce_zk;
  			}
  			if(marketsmall_type == "3" && onesend_wuping !=""){
  				if(onesend_wuping == "" ){
  					alert("礼品不能为空");
  	  				return;
  				}
  				content=startdate.substring(2,startdate.length)+"日至"+enddate.substring(2,enddate.length)+"日  满"+oneachievemoney+"元送礼品"+onesend_wuping;
  				w_money="0";
  				timenumber="0";
  			}
  			$.ajax({
				type:"post",
				url:"storepc_marketingtype/save.do",
				data:"change_type=1&content="+content
				+"&startdate="+startdate
				+"&enddate="+enddate
 				+"&timenumber="+timenumber
 				+"&w_money="+w_money
 				+"&zkovermoney="+zkovermoney
 				+"&rdovermoney="+rdovermoney
 				+"&redcontent="+redcontent
 				+"&onereduce_zktype="+onereduce_zktype
 				+"&oneachieve_money="+oneachievemoney
				+"&onereduce_money="+onereduce_money
				+"&marketsmall_name=满赠&marketsmall_type="+marketsmall_type
				+"&marketing_type=1&store_id="+"${storepd.store_id}"
				, 
				success:function(data){
					if(data.result == "01"){
						window.location.reload();
					}else{
						alert("请联系客服");
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