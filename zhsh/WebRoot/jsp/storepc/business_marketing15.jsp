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
	<title>买N减1</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/yxkt_hb.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<script src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
	<ul>
		<li style="padding-left:3%;line-height:2.5;">
			<span class="col-red">说明：</span>
			<span>单品的买N减一规则请在商品管理中进行设置，本处设置为不同品类，不同单品之间的组合营销规则。</span>
		</li>
		<li class="title" >
			<span>第一步：设定设置基本条件</span>
		</li>
		<li style="padding-left:8%;line-height:2.5;">
			<span>有效时间</span>：
			<input  type="text" placeholder="开始时间" name="stratdate" id="startdate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			<span>&nbsp;至&nbsp;</span>  
			<input   type="text" placeholder="结束时间" name="enddate" id="enddate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		</li>
		<li style="padding-left:8%;line-height:2.5;">
			<span>有效时段</span>：
			<input   type="text" placeholder="开始时段" name="strattime" id="starttime" onclick="WdatePicker({dateFmt:'HH:ss:mm'})"/>
			<span>&nbsp;至&nbsp;</span>
			<input   type="text"  placeholder="结束时段" name="endtime" id="endtime"  onclick="WdatePicker({dateFmt:'HH:ss:mm'})"/>
		</li>
		<li style="padding-left:8%;line-height:2.5;">
			<span>营销规则：满</span>
			<input type="number" id="fourachieve_number">
			<span>件减一件&nbsp;&nbsp;</span>
			<span class="col-red" style="font-size:87.5%;">注：多个商品价格不一样的，将自动减少商品单价最低的商品</span>
		</li>
		<li class="title" >
			<span>第一步：选择商品（可选商品、细分类别在“商品管理-类别管理”中另行设置）</span>
		</li>
		<li style="padding-left:8%;line-height:2.5;">
			<span>类别选择</span>：
			<select class="d3_set1 data" id="goods_category_id" name="goods_category_id" onchange="selectGoods()">
                <option value="">请选择</option>
                 <c:forEach items="${categoryList}" var="var" varStatus="vs">
					<option value="${var.goods_category_id}">${var.name}</option>
				</c:forEach>
             </select>
			<span>&nbsp;&nbsp;&nbsp;&nbsp;商品选择</span>：
			<select  id = "goods_id" name = "goods_id">
				<option value="">请选择商品</option>
			</select>
			<span class="anniu-s" style="line-height:1.2;" onclick="addgoods()">添加</span>
		</li>
		<li style="padding-left:5%;line-height:2.5;">
			<span>已添加商品：</span>
		</li>
		<li class="tjsp_item addgoods">
 
		</li>
		<c:if test="${storeqx.add eq '1'}">
		<li style="text-align:center;padding-top:15px;">
			<span class="anniu-m" onclick="save()">确定</span>
		</li>
		</c:if>
	</ul>
	<ul style="color:#999;line-height:1.5;padding-left:5%;">
		<li style="line-height:2;">
			<span>
				已发布的满赠活动：
			</span>
		</li>
		<c:forEach items="${varList}" var="var" varStatus="vs">
 				<li class="padd_l"> <span> ${vs.index+1 }、${var.content} </span> </li>
         </c:forEach>
	</ul>
	</c:if>
    <script type="text/javascript">
    	var allgoods="";//所有商品
     	var allgoodsname="";//所有商品名称
    	
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
     		var marketsmall_type ="1";
     		var fourachieve_number = $("#fourachieve_number").val();
     		var fourreduce_number = "1";
     		var foursend_money = $("#foursend_money").val();
     		var content1=startdate.substring(2,startdate.length)+"日至"+enddate.substring(2,enddate.length)+"日，每天"+starttime+"-"+endtime+",";
     		var content="";
 			if(marketsmall_type == "1"){
 				if(fourreduce_number == "" || fourachieve_number == ""){
 					alert("文本框不能为空");
 					return;
 				}else{
 					content="满"+fourachieve_number+"件减"+fourreduce_number+"件";
 				}
 			}    		 
      		var goods_category_id=$("#goods_category_id option:selected").val();
    		var goods_id=$("#goods_id option:selected").val();
    		if(allgoods == ""){
    			alert("优惠的商品不能为空");
    			return;
    		}
    		content=content1+"购买商品"+allgoodsname+content;
      		 $.ajax({
    			type:"post",
    			url:"storepc_marketingtype/save.do",
    			data:"startdate="+startdate
    			+"&enddate="+enddate
    			+"&content="+content
    			+"&content="+content
    			+"&starttime="+starttime
    			+"&endtime="+endtime
     			+"&fourachieve_number="+fourachieve_number
    			+"&fourreduce_number="+fourreduce_number
    			+"&foursend_money="+foursend_money
    			+"&marketsmall_type="+marketsmall_type
     			+"&change_type="+marketsmall_type
    			+"&marketing_type="+"4"
    			+"&marketsmall_name="+"买N减优惠"
    			+"&goods_id="+allgoods
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
    	
    	//通过类别选择商品
    	function selectGoods(){
			var str=$("#goods_category_id option:selected").val();//获取被选中的value值
			 $.ajax({
				  url: "storepc_marketingtype/goodsList.do",
				  data:"goods_category_id="+str,
				  type:"post",
				  dataType:"json",
 				  success:function(data){
 					  	$("#goods_id option").remove();
 					  	$("#goods_id").append("<option value=''>请选择商品</option>");
					  	if(data.length>0){
						  	for(var i=0;i<data.length;i++){
						  		$("#goods_id").append("<option value='"+data[i].goods_id+"'>"+data[i].goods_name+"</option>");
						  	}
				  		}
				  }
			}); 
		}
  
    	 //添加商品
    	 function addgoods(){
     		 var goods_id=$("#goods_id option:selected").val();
    		 if(goods_id == ""){
    			 alert("请选择商品");
    			 return;
    		 }else{
    			 allgoods+=goods_id+",";
    			 var goods_name=$("#goods_id option:selected").text();
     			 //alert(allgoodsname.indexOf(goods_name));
    			 if(allgoodsname.indexOf(goods_name) == -1 ){
    				 allgoodsname+="["+goods_name+"]";
        			 $(".addgoods").append("<span>"+goods_name+"</span>");
    			 } 
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