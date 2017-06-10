<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>折扣设置</title>
	 <base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/yxkt_zffs.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<script src="My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
	.anniu-m{
		background:#a4a4a4;
	}
	.act{
	    background: #156cb1;
	}
	</style>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
	<div class="list_cont clfa">
		<ul style="overflow: hidden;">
			<li class="anniu-m fll one" style="padding:0.4% 2.4%;" onclick="zhekouType('1')">整店折扣设置</li>
			<li class="anniu-m fll two" style="padding:0.4% 2.4%;" onclick="zhekouType('2')">类别折扣设置</li>
			<li class="anniu-m fll three" style="padding:0.4% 2.4%;" onclick="zhekouType('3')">件数折扣设置</li>
			<li class="anniu-m fll four" style="padding:0.4% 2.4%;" onclick="zhekouType('4')">金额阶梯折扣设置 </li>
		</ul>
	</div>
	<script type="text/javascript">
	function zhekouType(discount_type){
		window.location.href="storepc_marketing/goDiscount.do?store_id=${storepd.store_id}&type=1&discount_type="+discount_type;
	}
 	</script>
	<ul >
		<li class="list" id="one">
			<ul class="content">
				<li style="line-height:2.5;">
					<span class="col-r">注：</span>
					<span class="col-6">在有效时间段内，整店商品或服务统一进行折扣</span>
				</li>
				<li style="padding-left:25px;line-height:2.5;">
					<span>折扣率</span>：
					<select id="onezhekou" style="display: inline-block;width: 8em;">
					   <option value="0">请选择</option>
					   <c:forEach var="num" begin="1" end="99">
						 <option value="${num}"  ${pd.onealldiscount_rate eq num?'selected':''}>${num}</option>
					   </c:forEach>
				   </select>
					<span>折</span>
				</li>
				<li style="padding-left:25px;line-height:2.5">
					<span>有效时间</span>：
					<input  placeholder="开始时间" type="text"   id="onestart" value = "" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					 至 
					<input   placeholder="结束时间" type="text"   id="oneend" value = "" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				</li>
				<li style="line-height:2">
					<input type="checkbox"   ${pd.discount_type eq '1'?'checked':'' } id="onecheckbox" />
					<span>使用</span>
				</li>
				<c:if test="${storeqx.add eq '1'}">
				<li style="text-align:center;">
					<span class="anniu-m" onclick="saveone()">确认</span>
				</li>
				</c:if>
			</ul>
		<script type="text/javascript">
		//新增
		function saveone(){
 			var onealldiscount_rate = $("#onezhekou").val().trim();
			var starttime = $("#onestart").val().trim();
			var endtime = $("#oneend").val().trim(); 
			if(starttime == '' && endtime == ''){
				alert("请选择时间！");
				return ;
			}
  			var grantrule = starttime.substring(2,starttime.length)+"日至"+endtime.substring(2,endtime.length)+"日，全场"+onealldiscount_rate+"折";
  			if(onealldiscount_rate == ''){
				alert("请填写折扣");
				return ;
			}
			if(!$("#onecheckbox").is(":checked")){
				alert("请先打勾");
				return ;
			}
			$.ajax({
				type:"post",
				url:"storepc_discountway/save.do",
				data:{
					content:"整店折扣",
					change_type:"1",
					selected_status : "1",
					onealldiscount_rate:onealldiscount_rate,
					starttime:starttime,
					endtime:endtime,
					discount_type:"1",
					grantrule:grantrule,
					store_id:"${storepd.store_id}"
				}, 
				success:function(data){
					if(data.result == "01"){
						alert("新增成功");
						//window.location.reload();
					}else{
						alert("系统错误，请联系客服");
					}
				}
			});
  		}
		</script>  
		</li>
		<li class="list" id="two">
			<ul class="content">
				<li style="line-height:2.5;">
					<span class="col-r">注：</span>
					<span class="col-6">在有效时间段内，整店商品或服务统一进行折扣</span>
				</li>
				<c:forEach items="${varList}" var="var" varStatus="vs">
				<li style="padding-left:25px;line-height:2.5;" class="twocate">
					<span class = "twoname">${var.name}</span>：
					<select class="${var.goods_category_id} twojifen"   name="back_rate" onchange="edit('${var.goods_category_id}')">
		               <option value="0">请选择</option>
		               <c:forEach var="num" begin="1" end="99">
		               	 	<option value="${num}" ${num eq var.zk_rate?'selected':'' }>${num}折</option>
		               </c:forEach>
		             </select>
					<span>折</span>
				</li>	
				 </c:forEach>
				<li style="padding-left:25px;line-height:2.5">
					<span>有效时间</span>：
					<input  placeholder="开始时间" type="text"   id="twostart" value = "" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					至 
					<input  placeholder="结束时间" type="text"  id="twoend" value = "" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				</li>
				<li style="line-height:2">
					<input type="checkbox" ${pd.discount_type eq '2'?'checked':'' } id="twocheckbox"/>
					<span>使用</span>
				</li>
				<c:if test="${storeqx.add eq '1'}">
					<li style="text-align:center;">
						<span class="anniu-m" onclick="savetwo()">确认</span>
					</li>
				</c:if>
			</ul>
		<script type="text/javascript">
    	//新增
		function savetwo(){
    		    var twoproductdiscount_rate="";
 				var starttime = $("#twostart").val().trim();
				var endtime = $("#twoend").val().trim(); 
	   			if(starttime == '' && endtime == ''){
					alert("请选择时间！");
					return ;
				}
	   			//最高或最小
	   			  var max=0;
				  var min=0;
				  //var m=0;
				  //获取最大值和最小值的积分
				  $(".twojifen").each(function(index,obj){
   					     if(index == 0){
					    	 min=parseFloat($(obj).val());
					     }
					     if($(obj).val() != "0"){
					    	 var jfl=parseFloat($(obj).val());
							 if(min > jfl ){
								 min=jfl;
							 }
					     }
	      		  });
				  $(".twojifen").each(function(index,obj){
					     var jfl=parseFloat($(obj).val());
						 if(max < jfl ){
							 max=jfl;
						 }
	  		  	  });
				  twoproductdiscount_rate=min+"-"+max+"折";
	   			//---------
				 var grantrule="";
				  $(".twocate").each(function(){
					  if($(this).children(".twojifen").val() != "0"){
						  grantrule+=$(this).children(".twoname").html()+$(this).children(".twojifen").val()+"折，";
					  }
	 			 });
 				grantrule=starttime.substring(2,starttime.length)+"日至"+endtime.substring(2,endtime.length)+"日 "+grantrule.substring(0,grantrule.length-1);
 	   			if(!$("#twocheckbox").is(":checked")){
					alert("请先打勾");
					return ;
				}
 			    $.ajax({
					type:"post",
					url:"storepc_discountway/save.do",
					data:{
						content:"类别折扣",
						change_type:"2",
						selected_status : "1",
						twoproductdiscount_rate:twoproductdiscount_rate,
						starttime:starttime,
						endtime:endtime,
						discount_type:"2",
						grantrule : grantrule,
 						store_id:"${storepd.store_id}"
 					},
					success:function(data){
						if(data.result == "01"){
							alert("新增成功");
 						}else{
							alert("系统错误，请联系客服");
						}
 					}
				});   
 		}
		
		//修改分类的折扣率
		function edit(goods_category_id){
 			var zk_rate = $("."+goods_category_id).val();//返积分率
 			$.ajax({
				type:"post",
				url:"storepc_CategoryManageController/editRate.do",
 				data:{
 						goods_category_id : goods_category_id,
 						zk_rate : zk_rate 
					},
				success:function(data){
					if(data.result="01"){
						//alert("修改成功");
 					}
  				}
			});    
		}
    
    </script>
		</li>
		<li class="list" id="three">
			<ul class="content">
				<li style="line-height:2.5;">
					<span class="col-r">注：</span>
					<span class="col-6">件数折扣设置请在<span class="col-blue">“商品管理-单品管理”</span>中进行设置，本处设置是否启用该功能</span>
					
				</li>
				<li style="line-height:2">
					<input type="checkbox"  ${pd.discount_type eq '3'?'checked':'' } id="threecheckbox" />
					<span>使用</span>
				</li>
				<c:if test="${storeqx.add eq '1'}">
					<li style="text-align:center;">
						<span class="anniu-m" onclick="savethree()">确认</span>
					</li>
				</c:if>
			</ul>
			 <script type="text/javascript">
				function savethree(){
 					if(!$("#threecheckbox").is(":checked")){
						alert("请先打勾");
						return ;
					}
					 $.ajax({
						type:"post",
						url:"storepc_discountway/save.do",
						data:"content=单品优惠&grantrule=单品优惠&discount_type=3&change_type=3&store_id="+"${storepd.store_id}"+"&selected_status=1&starttime=2017-01-01&endtime=2018-01-01",
						success:function(data){
							if(data.result == "01"){
								alert("新增成功");
 							}else{
								alert("系统错误，请联系客服");
							}
						}
					});   
 				}
			
			</script>
		</li>
		<li class="list" id="four">
			<ul class="content">
				<li style="line-height:2.5;">
					<span class="col-r">注：</span>
					<span class="col-6">在有效时间段内，根据消费金额进行阶梯折扣</span>
				</li>		
				 <c:forEach items="${zkList}" var="zkvar" varStatus="vs">
					<li style="padding-left:25px;line-height:2.5;">
						<span style="display:inline-block;width:68px;text-align:right;">消费满</span>：
						 <input type = "text" id ="fourmanjian${vs.index+1}" value="${zkvar.money }" placeholder="元"/>
						<span>元</span>
						&nbsp;&nbsp;&nbsp;
						<span>打</span>
						<input type = "text" id ="fourzhekou${vs.index+1}" value="${zkvar.rate }" placeholder="折"/>
						<span>折</span>
					</li>
				 </c:forEach> 
				<li style="padding-left:25px;line-height:2.5">
					<span style="display:inline-block;width:68px;text-align:right;">有效时间</span>：
					<input  placeholder="开始时间" type="text"  id="fourstart" value = "" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					至 
					<input  placeholder="结束时间" type="text"   id="fourend" value = "" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				</li>
				<li style="line-height:2">
					<input type="checkbox"   ${pd.discount_type eq '4'?'checked':'' } id="fourcheckbox"/>
					<span>使用</span>
				</li>
				<c:if test="${storeqx.add eq '1'}">
					<li style="text-align:center;">
						<span class="anniu-m" onclick="savefour()">确认</span>
					</li>
				</c:if>
			</ul>
			<script type="text/javascript">
    	//新增
    	function savefour(){
      		if(!$("#fourcheckbox").is(":checked")){
				alert("请先打勾");
				return ;
			}
     		var manjian1 = $("#fourmanjian1").val().trim();
    		var zhekou1 = $("#fourzhekou1").val().trim();
    		var manjian2 = $("#fourmanjian2").val().trim();
    		var zhekou2 = $("#fourzhekou2").val().trim();
    		var manjian3 = $("#fourmanjian3").val().trim();
    		var zhekou3 = $("#fourzhekou3").val().trim();
			if(zhekou1 == "" && zhekou3== "" && zhekou2== ""){
				alert("请填写，并且折扣不能为0");
   				return;
   			}
    		var starttime = $("#fourstart").val().trim();
			var endtime = $("#fourend").val().trim(); 
   			if(starttime == '' && endtime == ''){
				alert("请选择时间！");
				return ;
			}
 			var grantrule=starttime.substring(2,starttime.length)+"日至"+endtime.substring(2,endtime.length)+"日 ";
			if(manjian1 != "" && zhekou1 != 0){
				grantrule+= "消费满"+ manjian1 +"元打"+zhekou1+"折,";
			}
			if( manjian2 != "" && zhekou2 != 0  ){
				grantrule+= "消费满"+ manjian2 +"元打"+zhekou2+"折,";
			}
			if( manjian3 != "" && zhekou3 != 0){
				grantrule+= "消费满"+ manjian3 +"元打"+zhekou3+"折,";
			}
			grantrule=grantrule.substring(0,grantrule.length-1);
  			$.ajax({
					type:"post",
					url:"storepc_discountway/save.do",
					data:{content:"金额阶梯折扣",selected_status:"1",change_type:"4",starttime:starttime,endtime:endtime,discount_type:"4",grantrule:grantrule,store_id:"${storepd.store_id}"},
					success:function(data){
						if(data.result == "01"){
							alert("新增成功");
 						}else{
							alert("系统错误，请联系客服");
						}
					}
		    }); 
     	}
    </script>
		</li>
		<li style="padding-top:6%;">
				<span class="col-r fll" style="width:8%;">重要声明：</span>
				<ul class="fll" style="width:92%;">
					<li>
						<span>一、在同一时间段内，以上四种折扣方式只能选择一种折扣进行设置；</span>
					</li>
					<li>
						<span>二、当你需要更改为其他折扣方式时，原折扣方式自动失效，根据系统规则，有可能导致部分功能无法同时使用，请更换折扣方式时注意记录原有选项和设置；</span>
					</li>
					<li>
						<span> 三、如你在使用过程中有任何疑问，请在线咨询服务商；</span>
					</li>
				</ul>
		</li>
	</ul>
</c:if>	
</body>
<script src="js/jquery-1.12.4.min.js"></script>
<script>
	var item=$(".list_cont li")
	var list=$(".list")
	for (var i = 0; i < item.length; i++) {
		$(item[i]).attr("num",i)
	};
	item.click(function(e){
		item.css("background","#1187e2");
		$(e.target).css({"background":"#085ca9"});
 		var a=$(e.target).attr("num")

		list.css("display","none")
		$(list[a]).css("display","block")
	});
 	$(function(){
		//设置显示隐藏页面
		var discount_type="${discount_type}";
		if(discount_type == "1"){
			$(".list").hide();
			$(".one").addClass("act");
			$("#one").show();
			$("#onestart").val("${pd.starttime}");
			$("#oneend").val("${pd.endtime}");
		}else if(discount_type == "2"){
			$(".list").hide();
			$(".two").addClass("act");
			$("#two").show();
			$("#twostart").val("${pd.starttime}");
			$("#twoend").val("${pd.endtime}");
		}else if(discount_type == "3"){
			$(".list").hide();
			$(".three").addClass("act");
			$("#three").show();
		}else{
			$(".list").hide();
			$(".four").addClass("act");
			$("#four").show();
			$("#fourstart").val("${pd.starttime}");
			$("#fourend").val("${pd.endtime}");
		}
		
	});
	
	
</script>
</html>