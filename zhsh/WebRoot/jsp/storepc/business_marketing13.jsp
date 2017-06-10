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
	<title>满减</title>
	 <base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/yxkt_mz.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<script src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>
 <c:if test="${storeqx.look eq '1'}">
	<ul>
		<li class="clf" style="padding:10px 0 20px 0;">
			<span class="col-red">声明：</span>
			<span class="col-9">满减与其他营销规则可合并，请在计算成本后进行选择</span>
		</li>
		<li class="item_li">
			<span>有效时间</span>：
			<input type="text" placeholder="开始时间" id="starttime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
			<span>&nbsp;至&nbsp;</span>
			<input type="text" placeholder="结束时间"  id="endtime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
		</li>
		<li class="item_li">
			<span>
				消费满
			</span>：
			<input type="text" placeholder="元" id="oneachieve_money" oninput="isNumberOk(this)">
			<span>&nbsp;元&nbsp;</span>
			<span>&nbsp;&nbsp;&nbsp;&nbsp;立减</span>
			<input type="text" id="onereduce_monye" oninput="isNumberOk(this)">
			<span>元</span>
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
		<c:forEach items="${varList}" var="var" varStatus="vs">
		<li class="padd_l">
			<span>
				${vs.index+1}、${var.content }
			</span>
		</li>
		</c:forEach>
	</ul>
	</c:if>
    <script type="text/javascript">
     	function save(){
     		var oneachieve_money = $("#oneachieve_money").val().trim();
    		var onereduce_monye = $("#onereduce_monye").val().trim();
    		if(parseFloat(onereduce_monye) >= parseFloat(oneachieve_money)){
    			alert("满减金额不能大于等于满足金额");
    			return;
    		}
  			var startdate = $("#starttime").val().trim();
			var enddate = $("#endtime").val().trim(); 
			if(startdate == "" || enddate == ""){
  				alert("时间不能为空");
  				return;
  			}
			if(oneachieve_money == "" || onereduce_monye == ""){
				alert("文本框不能为空");
  				return;
			}
			var content=startdate.substring(2,startdate.length)+"日至"+enddate.substring(2,enddate.length)+"日  满"+oneachieve_money+"元立减"+onereduce_monye+"元";
			$.ajax({
				type:"post",
				url:"storepc_marketingtype/save.do",
				data:"startdate="+startdate
				+"&enddate="+enddate
				+"&content="+content
				+"&change_type="+"1"
				+"&oneachieve_money="+oneachieve_money
				+"&onereduce_monye="+onereduce_monye
				+"&marketsmall_name="+"满减"
				+"&marketsmall_type="+"1"
				+"&marketing_type="+"2"
				+"&store_id="+"${storepd.store_id}", 
				success:function(data){
					if(data.result == "01"){
    					window.location.reload();
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