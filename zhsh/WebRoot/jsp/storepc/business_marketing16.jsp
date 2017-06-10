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
	<title>累计次数/金额营销</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/yxkt_mz.css">
	<script src="js/jquery-1.8.0.min.js"></script>
    <script src="js/storepc/business_marketing16.js"></script>
    <script src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
<ul>
<li class="clf" style="padding:10px 0 20px 0;">
			<span class="col-red" style="float:left;">说明：</span>
			<ul style="float:left;">
				<li>
					单品的买N减1规则请再单品管理中进行设置，本处设置为不同品类，不同单品之间的组合营销规则。
				</li>
				<li>
					该功能是指在一段时间内，会员在本商家消费达到多少次或累计消费满一定金额后获得的优惠会员一旦领取红包或礼品后，消费次数需重新计算
				</li>
			</ul>
		</li>
		<li class="item_li">
			<span>有效时间</span>：
			<input  type="text" placeholder="开始时间" name="stratdate" id="startdate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			<span>&nbsp;至&nbsp;</span>  
			<input   type="text" placeholder="结束时间" name="enddate" id="enddate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		</li>
		<li class="item_li">
			<span>有效时段</span>：
			<input   type="text" placeholder="开始时段" name="strattime" id="starttime" onclick="WdatePicker({dateFmt:'HH:ss:mm'})"/>
			<span>&nbsp;至&nbsp;</span>
			<input   type="text"  placeholder="结束时段" name="endtime" id="endtime"  onclick="WdatePicker({dateFmt:'HH:ss:mm'})"/>
		</li>
		<li class="item_li">
			<span>营销规则</span>：
			<span>消费满</span>
			<input type="number" style="width:60px"  id="fiveachieve_number" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
			<span>次&nbsp;&nbsp;</span>
			<span>金额达到</span>
			<input type="text" style="width:60px" id="fiveachieve_money" oninput="isNumberOk(this)" >
			<span>元</span>
		</li>
		<li class="item_li">
			<span>赠</span>：
			<select name="marketsmall_type" id="marketsmall_type" onchange="getType(this.value)">
		             	<option value="1">折扣红包</option>
		             	<option value="2">现金红包</option>
		             	<option value="3">物品</option>
		    </select>
			<span id="one" >
		             	使用条件：再次消费满<input type="text" id="zkovermoney" placeholder="XX元" oninput="isNumberOk(this)" />
		             	折扣规则：   <select id="onereduce_zktype">
				 	             		<option value="1">原价折扣</option>
				 	             		<option value="2">优惠后折扣</option>
				 	             </select>
		             	打<input type="text" id="onereduce_zk"  placeholder="折"  style="width:60px" maxlength="2"/>折
	  	    </span>
		    <span id="two" style="display:none">
		             	使用条件：再次消费满<input type="text" id="rdovermoney" oninput="isNumberOk(this)" />
		             	减 <input  placeholder="元" type="text" id="onereduce_money"  style="width:60px" oninput="isNumberOk(this)" />元
	 	    </span>
		    <span id="three" style="display:none">
	 	              	礼品名称：<input type="text" id="onesend_wuping"  placeholder="物品"  style="width:60px"/>
		    </span>
			<select name="timenumber" id="timenumber" >
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
			<span class="anniu-l" onclick="save()">确认</span>
		</li>
		</c:if>
	</ul>
	<ul style="color:#999;line-height:1.5">
		<li style="line-height:2;">
			<span>
				已发布的满赠活动：
			</span>
		</li>
		<c:forEach items="${varList}" var="var" varStatus="vs">
	        <li class="padd_l">
				<span>
					${vs.index+1 }、${var.content }
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
			$("#timenumber").show();
			$("#one").show();
			$("#three").hide();
		}else if(value=="2"){
			$("#two").show();
			$("#timenumber").show();
			$("#one").hide();
			$("#three").hide();
		}else{
			$("#two").hide();
			$("#timenumber").hide();
			$("#one").hide();
			$("#three").show();
		}
	}
	
	
    	function save(){
    		var startdate = $("#startdate").val();
    		var enddate = $("#enddate").val();
    		var starttime = $("#starttime").val();
    		var endtime = $("#endtime").val();
    		if(startdate == "" || enddate == ""){
  				alert("时间不能为空");
  				return;
  			}
    		if(starttime == "" || endtime == ""){
  				alert("时期不能为空");
  				return;
  			}
    		var content1=startdate.substring(2,startdate.length)+"日至"+enddate.substring(2,enddate.length)+"日，每天"+starttime+"-"+endtime+",";
    		var marketsmall_type = $("#marketsmall_type").val();
    		var fiveachieve_money = $("#fiveachieve_money").val();
    		var fiveachieve_number = $("#fiveachieve_number").val();
    		if(fiveachieve_money == "" && fiveachieve_number== ""){
    			alert("选择其一");
    			return;
    		}
    		if(fiveachieve_money == "" ){
    			fiveachieve_money="0";
			}
    		if(fiveachieve_number== ""){
    			fiveachieve_number="0";
			}
  			var timenumber = $("#timenumber").val();
  			if(timenumber == null || timenumber == ""){
  				timenumber="0";
  			}
  			var content="";
   			var w_x=0;
  			var rdovermoney = $("#rdovermoney").val();
  			var onereduce_money = $("#onereduce_money").val();
  			var zkovermoney = $("#zkovermoney").val();
  			var onereduce_zktype = $("#onereduce_zktype").val();
  			var onereduce_zk = $("#onereduce_zk").val();
  			var onesend_wuping = $("#onesend_wuping").val();
  			var redcontent="";
       		if(marketsmall_type == "2" ){
	       			if(rdovermoney == "" || onereduce_money== ""){
	  					alert("金额不能为空");
	  	  				return;
	  				}
	       			if(parseFloat(rdovermoney ) < onereduce_money){
	  					alert("满足的金额不能小于优惠金额");
	  	  				return;
	  				}
	       			if(fiveachieve_money == "0"){
	       				content="累计消费"+fiveachieve_number+"次送"+onereduce_money+"元现金红包";
	       			}else if(fiveachieve_number =="0"){
	       				content="累计金额达到"+fiveachieve_money+"元送"+onereduce_money+"元现金红包";
	       			}else{
	       				content="累计消费"+fiveachieve_number+"次累计金额达到"+fiveachieve_money+"元送减"+onereduce_money+"元现金红包";
	       			}
       				redcontent="满"+rdovermoney+"元减"+onereduce_money+"元";
      				w_money=onereduce_money;
      		}
      		if(marketsmall_type == "1" ){
	      			if(zkovermoney == "" || onereduce_zk== ""){
	  					alert("金额不能为空");
	  	  				return;
	  				}
	      			if(fiveachieve_money == "0"){
	       				content="累计消费"+fiveachieve_number+"次送"+onereduce_zk+"折红包";
	       			}else if(fiveachieve_number =="0"){
	       				content="累计金额达到"+fiveachieve_money+"元送"+onereduce_zk+"折红包";
	       			}else{
	       				content="累计消费"+fiveachieve_number+"次累计金额达到"+fiveachieve_money+"元送"+onereduce_zk+"折红包";
	       			}
       				redcontent="满"+zkovermoney+"元打"+onereduce_zk+"折";
      				w_money=onereduce_zk;
      		}
      		if(marketsmall_type == "3" && onesend_wuping !=""){
	      			if(onesend_wuping == "" ){
	  					alert("礼品不能为空");
	  	  				return;
	  				}
	      			if(fiveachieve_money == "0"){
	      				 content="累计消费"+fiveachieve_number+"次送礼品"+onesend_wuping;
	       			}else if(fiveachieve_number =="0"){
	       				content="累计金额达到"+fiveachieve_money+"元送礼品"+onesend_wuping;
	       			}else{
	       				content="累计消费"+fiveachieve_number+"次累计金额达到"+fiveachieve_money+"元送礼品"+onesend_wuping;
       				}
      				content="满"+fiveachieve_number+"次金额达到"+fiveachieve_money+"元送礼品"+onesend_wuping;
      				w_money=onesend_wuping;
      		}
      		content=content1+content;
       		$.ajax({
				type:"post",
				url:"storepc_marketingtype/save.do",
				data:"startdate="+startdate
				+"&enddate="+enddate
				+"&starttime="+starttime
				+"&endtime="+endtime
				+"&zkovermoney="+zkovermoney
 				+"&rdovermoney="+rdovermoney
 				+"&redcontent="+redcontent
 				+"&onereduce_zktype="+onereduce_zktype
				+"&timenumber="+timenumber
				+"&change_type="+marketsmall_type
 				+"&w_money="+w_money
				+"&content="+content
				+"&fiveachieve_number="+fiveachieve_number
				+"&fiveachieve_money="+fiveachieve_money
				+"&marketsmall_type="+marketsmall_type
				+"&marketing_type="+"5"
				+"&marketsmall_name="+"累计优惠"
				+"&store_id="+"${storepd.store_id}",
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