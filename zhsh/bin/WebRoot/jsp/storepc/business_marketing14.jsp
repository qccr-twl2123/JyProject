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
	<title>时段营销</title>
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
					1、在有效日期段内，每天固定的时间段内将启动该规则。
				</li>
				<li>
					2、时段营销是指在该时段所消费项目自动进行折扣或金额减除。
				</li>
			</ul>
			<li style="padding-bottom:20px;">
			<span class="col-r">例如：</span>
			<span>1月1日至1月31日，每天晚上0点到早上7点入住酒店，房费优惠5折</span>
			</li>
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
			<span>满</span>
			<input type="text" placeholder="" id="threeachieve_money" oninput="isNumberOk(this)">
			<span>元&nbsp;&nbsp;</span>
			<select class="data" id="marketsmall_type" onclick="guize(this.value)" style="width:60px">
	             	<option value="1">折扣</option>
	             	<option value="2">满减</option>
	        </select>
			<input type = "text" id="threediscount_rate" placeholder="折" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" maxlength="2"/>
 	        <input type = "text" id="threereduce_money" placeholder="元" style="display:none" oninput="isNumberOk(this)"/>
		</li>
		 <c:if test="${storeqx.add eq '1'}">
			<li style="text-align:center;" class="padd-td">
				<span class="anniu-l" onclick="save()">确定</span>
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
     		var marketsmall_type = $("#marketsmall_type").val().trim();
     		var threeachieve_money = $("#threeachieve_money").val().trim();
     		var threereduce_money = $("#threereduce_money").val().trim();
     		var threediscount_rate = $("#threediscount_rate").val().trim();
     		var content=startdate.substring(2,startdate.length)+"日至"+enddate.substring(2,enddate.length)+"日 每天"+starttime+"-"+endtime+" ";
 			if(marketsmall_type == "1"){
 				if(threediscount_rate == "" || threeachieve_money == ""){
 					alert("文本框不能为空");
 					return;
 				}else{
 					content+="满"+threeachieve_money+"元打"+threediscount_rate+"折";
 				}
 			}    		
 			if(marketsmall_type == "2"){
 				if(threereduce_money == "" || threeachieve_money == ""){
 					alert("文本框不能为空");
 					return;
 				}else{
 					content+="满"+threeachieve_money+"元减"+threereduce_money+"元";
 				}
 			}    		
     		$.ajax({
     			type:"post",
    			url:"storepc_marketingtype/save.do",
    			data:"startdate="+startdate
    			+"&enddate="+enddate
    			+"&starttime="+starttime
    			+"&endtime="+endtime
    			+"&content="+content
    			+"&threeachieve_money="+threeachieve_money
    			+"&threereduce_money="+threereduce_money
    			+"&threediscount_rate="+threediscount_rate
    			+"&marketsmall_name="+"时段营销"
    			+"&marketsmall_type="+marketsmall_type
    			+"&change_type="+marketsmall_type
    			+"&marketing_type="+"3"
    			+"&store_id="+"${storepd.store_id}",
    			success:function(data){
    				if(data.result == "01"){
    					window.location.reload();
    				} 
     			}
    		});
     	}
    	
    //选择优惠方式
    function guize(value){
    	if(value == "1"){
    		$("#threediscount_rate").show();
    		$("#threereduce_money").hide();
     	}else{
     		$("#threediscount_rate").hide();
    		$("#threereduce_money").show();
     	}
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