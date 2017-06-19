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
	<title>单品管理</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/sj_hy.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
 
<c:forEach items="${big_list}" var="big">
	 	<input style="width:0px;height:0px;" type="hidden" value="${big.back_rate}" id="back_rate${big.goods_category_id}"/> 
</c:forEach>

<!-- 商品列表 -->
<form action="/storepc_CategoryManageController/showShop2.do" method="post" name="Form" id="Form">
	<input type="hidden" name="store_id" value="${storepd.store_id}" />
	<input type="hidden" name="type" value="${storepd.login_type}" />
	<input type="hidden" name="login_id" value="${storepd.login_id}" />
 	<ul>
		<li class="padd_td">
		    <c:if test="${storeqx.add eq '1'}">
				<span class="anniu-s tianjia">添加商品</span>
			</c:if>
 			<span class="anniu-s" onclick="Export()">全部商品导出EXCEL</span>
 			<input type="text" name="goods_name" value="${pd.goods_name}"  placeholder="输入类别、商品名称搜索"  style="width:180px;height:21px;" />
 			<span class="anniu-s chaxun"  onclick="findSub()">查询商品</span>
			 <c:if test="${storeqx.add eq '1'}">
				 <span class="anniu-s flr" onclick="saverenqi()" >加入人气榜</span>
				 <span class="anniu-s flr" onclick="savetejia()">加入今日特价</span>
			 </c:if>
		</li>
		<li>
			<table cellspacing="0" cellpadding="0" style="width:100%;border:1px solid #a4a4a4;">
				<thead>
					<td><input type="checkbox" class="checkall setAll" id = "setAll"  ></td>
					<td>大类排序</td>
					<td>大类名称</td>
					<td>小类名称</td>
					<td>商品名称</td>
					<td>原价</td>
					<td>零售价</td>
					<td>积分率</td>
					<td>积分值</td>
					<td>单品销售状态</td>
					<td>单位</td>
					<td>所属类别</td>
					<td>操作</td>
				</thead>
				<tbody>
					<c:forEach items="${good_list}" var="slist">
							<tr>
								<td><input type="checkbox" name = "chose" value="${slist.goods_id}" /></td>
								<td>${slist.parent_sort}</td>
								<td>${slist.parent_name}</td>
	 							<td>${slist.son_name}</td>
								<td>${slist.goods_name}</td>
								<td>${slist.market_money}</td>
								<td>${slist.retail_money}</td>
								<td>${slist.integral_rate}%</td>
								<td>${slist.integral_number}</td>
								<td>
									<c:if test="${slist.promotion_type eq '0'}">
									无
									</c:if>
									<c:if test="${slist.promotion_type eq '1'}">
									满减
									</c:if>
									<c:if test="${slist.promotion_type eq '2'}">
									单品折扣
									</c:if>
									<c:if test="${slist.promotion_type eq '3'}">
									买N减1
									</c:if>
								</td>
								<td>${slist.company}</td>
								<td>
									<c:if test="${slist.goods_type eq '0'}">
									正常商品
									</c:if>
									<c:if test="${slist.goods_type eq '1'}">
									今日特价商品
									</c:if>
									<c:if test="${slist.goods_type eq '2'}">
									人气版商品
									</c:if>
 								</td>
								<td class="blue">
	 								<c:if test="${storeqx.edit eq '1'}"><span class="xiugai change" onclick="editGoods('${slist.goods_id}')">修改</span></c:if>
									<c:if test="${storeqx.delete eq '1'}"><span class="xiugai" onclick="delgoods('${slist.goods_id}','${slist.store_id}')">删除</span></c:if>
								</td>
							</tr>
					</c:forEach>
				</tbody>
			</table>
		</li>
		<li class="fenye clf">
		     <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		</li>
	</ul>
</form>

<!-- 上传图片 -->
<form action="uploanImage.do" method="post" name="imageForm" id="imageForm"  enctype="multipart/form-data"> 
	   <input type="file" style="width:1px;display:none;" name="uploanImage" class="uploanImage" onchange="fileType(this)"/>
</form>

<!-- 遮罩 -->
<div class="dask">
	<!-- 修改商品 -->
	<form action="storepc_CategoryManageController/editGoods.do"  method="post" name="dorm2" id="dorm2"  >
		<input type="hidden" name="goods_id" id="goods_id" value=""/>
		<input type="hidden" name="store_id" value="${pd.store_id}" />
		<input type="hidden" name="goods_id" id="editgoods_id">
	    <div class="alert_xg al_xiugai">		
	        <div class="al_head">
	            <div class="close"></div>
	            <div class="alert_tit">
	                <span>修改商品</span>
	                <div class="one"></div>
	            </div>
	        </div>
	        <ul class="al_body">
	           	<li style="font-size:86%;"><span class="col-r">说明</span>：九鱼网免费提供100个单品云存储空间，超出商品限度的费用${citypd.billing_money}/天，结算时间（${citypd.billing_time}），您当前已上传<span>${goodsnumber}</span>.
	           	</li>
	           	<li>
	           		<span>商品大类</span>：
		           	<select name="BigName" id="big" onclick="searchSmall(this)" >
								<option value="">请选择</option>
								<c:forEach items="${big_list}" var="big">
		 							<option value="${big.goods_category_id}">${big.name}</option>
		 						</c:forEach>
					</select>
	           		<span>商品小类</span>：
					<select name="goods_category_id" id="small" style="width:80px;">
						 
					</select>
					<span>商品名称</span>：
					<input type="text" name="goods_name"  id="goods_name">
	           	</li>
	           	<li>
	           		<span class="span_sty" >原价</span>：
	           		<input type="text" name="market_money" id="market_money" oninput="editmoneyisOK(this,'0')">
	           		<span class="span_sty">零售价</span>：
	           		<input type="text" name="retail_money" id="retail_money" oninput="editmoneyisOK(this,'1')" >
	           		<span class="span_sty">单位</span>：
	           		<input type="text" id = "company" name ="company" maxlength="2">
	           	</li>
	           	<li>
	           		<span class="span_sty">积分率</span>：
	           		<input type="text" style="width:107px"   style="cursor:not-allowed" name="integral_rate" id="integral_rate" onchange="editintegral(this)">%
	           		<span class="span_sty">积分值</span>：
	           		<input type="text"  readonly="readonly"  style="cursor:not-allowed" name="integral_number" id="integral_number" readonly="readonly">分
	           	</li>
	           	<li>
	           		<span>营销时段</span>：
	           		<input class="statrTime" placeholder="开始时间" type="text" name="starttime" id="starttime" value = "" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
               		&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;
               		<input class="endTime" placeholder="结束时间" type="text" name="endtime" id="endtime" value = "" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
 	           	</li>
	           	<li>
	           		<span>营销规则</span>：
	           		<select class="editcx" name="promotion_type" id="promotion_type" onchange="showYx2(this)">
								<option value="0">请选择</option>
								<!-- <option value="4">送物品</option> -->
								<option value="3">买N件减1件</option>
								<option value="2">单品折扣</option>
								<option value="1">满减</option>
					</select>
					<span>说明：三种促销方式三选一</span>
					<input type="hidden" name="promotion_content" value="" id="promotion_content"  />
	           	</li>
				<li class="fontwt fontwt11">
	 						 买<input type="text" class="fontwt11input"  onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/> 件减一件
				</li> 
		        <li class="fontwt fontwt21">
		               	 	<span class="">折扣：</span>
		               	 	<input type="text" class="fontwt21input" maxlength="2" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" max="2"/>折
		        </li>
		        <li class="tfontwt fontwt31">
		                		<span class="">满</span>
		                		<input type="text" class="  fontwt311input"   oninput="isNumberOk(this)" /> 
		               			<span class="">元减</span>
		               			<input type="text"  class="  fontwt312input"   oninput="isNumberOk(this)" />元 
		        </li>	
	           	<li>
	           		<span style="vertical-align: top;">上传商品图片：</span>
	           		<div class="imgbox" onclick="upload('editimage_url')">
	           			<img src="img/dpgl_addimg.png" id="editimage_url">
						<input type="hidden" class="editimage_url"  name="image_url"/>
	           		</div>
	           	</li>
	        </ul>           
	        <div class="al_foot">
	            <div class="button_box" onclick="sureEdit()"> 确定  </div>
	        </div>
	    </div>
	</form>
	
	
    <!-- 添加商品 -->
	<form action="storepc_CategoryManageController/addGoods.do"  method="post" name="dorm1" id="dorm1">
	    <input type="hidden" name="store_id" value="${pd.store_id}" />
		<div class="alert_xg al_tianjia">		
	        <div class="al_head">
	            <div class="close"></div>
	            <div class="alert_tit">
	                <span>添加商品</span>
	                <div class="one"></div>
	            </div>
	        </div>
	        <ul class="al_body">
	           	<li style="font-size:86%;"><span class="col-r">说明</span>：九鱼网免费提供100个单品云存储空间，超出商品限度的费用${citypd.billing_money}/天，结算时间（${citypd.billing_time}），您当前已上传<span>${goodsnumber}</span>.
	           	</li>
	           	<li>
	           		<span>商品大类</span>：
		           	<select style="width:80px;"   name="BigName" onclick="searchSmall(this)" id="addbiggoods_category_id">
								<option value="">请选择</option>
								<c:forEach items="${big_list}" var="big">
		 							<option value="${big.goods_category_id}">${big.name}</option>
		 						</c:forEach>
		 			</select>
	           		<span>商品小类</span>：
					<select name="goods_category_id"  id="addxiaogoods_category_id" style="width:80px;">
						 
					</select>
					<span>商品名称</span>：
					<input type="text" name="goods_name" >
	           	</li>
	           	<li>
	           		<span class="span_sty">原价</span>：
	           		<input type="text" name="market_money" id="addmarket_money" oninput="moneyisOK(this,'0')">
	           		<span class="span_sty">零售价</span>：
	           		<input type="text" name="retail_money" id="addretail_money" oninput="moneyisOK(this,'1')">
	           		<span class="span_sty">单位</span>：
	           		<input type="text" id = "addcompany" name ="company" maxlength="2">
	           	</li>
				<li>
	           		<span class="span_sty">积分率</span>：
	           		<input type="text" style="width:107px" name="integral_rate" id="addintegral_rate"  oninput="addeditintegral(this)"  >%
	           		<span class="span_sty">积分额</span>：
	           		<input type="text"  name="integral_number" id="addintegral_number" readonly="readonly">分
	           	</li>
	           	<li>
	           		<span>营销时段</span>：
	           		<input class="statrTime" placeholder="开始时间" type="text" name="starttime" id="addstarttime" value = "" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
               		&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;
               		<input class="endTime" placeholder="结束时间" type="text" name="endtime" id="addendtime" value = "" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
 	           	</li>
	           	<li>
	           		<span>营销规则</span>：
	           		<select class="addcx"  name="promotion_type" onchange="showYx(this)">
						<option value="0">请选择</option>
						<!-- <option value="4">送礼品</option> -->
						<option value="3">买N件减1件</option>
						<option value="2">单品折扣</option>
						<option value="1">满减</option>
					</select> 
					<span>说明：三种促销方式三选一</span>
					<input type="hidden" name="promotion_content" value="" id="addpromotion_content"/>
	           	</li>
				<li class="fontwt fontwt1">
 					买<input type="text" class="addfontwt1"  onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/><span></span>件减一件
				</li>
		        <li class="fontwt fontwt2">
		               	 	<span class="">折扣：</span>
		               	 	<input type="text" class="addfontwt2" maxlength="2" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" max="2"/><span></span>折
		        </li>
		        <li class="fontwt fontwt3">
		                		<span class="">满</span>
		                		<input type="text" class="addfontwt31"   oninput="isNumberOk(this)"/><span></span>
		               			<span class="">元减</span>
		               			<input type="text"  class="addfontwt32"   oninput="isNumberOk(this)"/>元<span></span>
		       </li>
	           	<li>
	           		<span style="vertical-align: top;">上传商品图片：</span>
	           		<div class="imgbox" onclick="upload('addimage_url')">
	           			<img src="img/dpgl_addimg.png" alt=""   id="addimage_url">
	           		</div>
					<input type="hidden" name="image_url" class="addimage_url"/>
	           	</li>
	        </ul>           
	        <div class="al_foot">
	            <div class="button_box"  onclick="addSub()"> 确定 </div>
	        </div>
	    </div>
	</form>
</div>
</c:if>
<script type="text/javascript">
		//查询商品===============================================================================================
		function findSub() {
			$("#Form").submit();
		}

		//导出==================================================================================================
		function Export() {
			window.location.href="storepc_CategoryManageController/toExcel.do?store_id=${storepd.store_id}";
		}

		//加入人气版=============================================================================================
		function saverenqi(){
			var obj = document.getElementsByName('chose');
			var ch = '';
			//获取会员选项和人脉选项的value
    		for ( var i = 0; i < obj.length; i++) {
				if(obj[i].checked){
					ch += obj[i].value+',';
				}
			}
			if(ch==''){
    			alert('你还没有选择任何内容！');
    			return;
    		}
			$.ajax({
				type:"post",
				url:'<%=basePath%>storepc_CategoryManageController/saverenqi.do',
				data:{
					store_id:"${pd.store_id}",
					goods_ids:ch
				},
				success:function(data){
   					 if(data.result == "1"){
	 					alert(data.message);
	 					window.location.reload();
	 				}else if(data.result == "2"){
	 					alert(data.message);
 	 				}else{
 	 					alert("添加失败，请联系管理员！");
 	 				}
				}
			});
		}
		
		//加入特价版=============================================================================================
		function savetejia(){
			var obj = document.getElementsByName('chose');
			var ch = '';
			//获取会员选项和人脉选项的value
    		for ( var i = 0; i < obj.length; i++) {
				if(obj[i].checked){
					ch += obj[i].value+',';
				}
			}
			if(ch==''){
    			alert('你还没有选择任何内容！');
    			return;
    		}
    		$.ajax({
					type:"post",
					url:'<%=basePath%>storepc_CategoryManageController/savetejia.do',
					data:{
						store_id:"${pd.store_id}",
						goods_ids:ch
					},
					success:function(data){
	   					if(data.result == "1"){
		 					alert(data.message);
		 					 window.location.reload();
		 				}else if(data.result == "2"){
		 					alert(data.message);
 		 				}else{
		 					alert("添加失败，请联系管理员！");
		 				}
				}
			});
		}




	/*
	新增商品=====================================================================================================
	*/
				//查询小类
				function searchSmall(obj){
					var value=$(obj).val();
					if(value==""){
						return;
					}
			 		$.ajax({
			            type:"post",
			            url:'<%=basePath%>storepc_CategoryManageController/getSmallForBig.do', 
			       		data:{"category_parent_id":value ,"store_id":"${pd.store_id}"},                
			            dataType:"json",
			            success: function(data){
			            	if(data.result == "1"){
			            		$(obj).next().next().empty();
			            		$(obj).next().next().append("<option value=''>请选择</option>");
			            		var smallList=data.data;
			             		for(var i=0 ; i<smallList.length ; i++){
			            			$(obj).next().next().append("<option value='"+smallList[i].goods_category_id+"'>"+smallList[i].name+"</option>");
			            		}
			            	}else{
			            		 alert(data.message);
			            	}
			             }
			        });
				}
	
				//判断原价是否大于零售价
				function moneyisOK(obj,value){
							isNumberOk(obj);
							var retail_money=$("#addretail_money").val();//零
							if(retail_money == ""){
								retail_money="0";
							}
							var market_money=$("#addmarket_money").val();//市
							if(market_money == ""){
								market_money="0";
							}
							var goods_category_id=$("#addbiggoods_category_id").val();
							var back_rate=$("#back_rate"+goods_category_id).val();
							if(parseFloat(retail_money) > parseFloat(market_money)){
								alert("零售价不能大于原价");
								$(obj).val("");
	 							return;
							}
							if(value == "1"){//输入零售价之后的判断
								$(".jfopen").show();
								if("${jfpd.change_type}" == "1"){//整店送积分
									var n= parseFloat(retail_money);
									var m= parseFloat("${jfpd.oneback_rate}");
									var g=n*m/100;
									//alert(g);
									$("#addintegral_rate").val("${jfpd.oneback_rate}");
									$("#addintegral_rate").attr("readonly","readonly");
									$("#addintegral_number").val(g.toFixed(2));
									$("#addintegral_number").attr("readonly","readonly");
									
								}else if("${jfpd.change_type}" == "2"){//分类送积分
									var n= parseFloat( retail_money );
									var m= parseFloat(back_rate);
									var g=n*m/100;
	 								$("#addintegral_rate").val(back_rate);
									$("#addintegral_rate").attr("readonly","readonly");
									$("#addintegral_number").val(g.toFixed(2));
									$("#addintegral_number").attr("readonly","readonly");
								}else if("${jfpd.change_type}" == "3"){//单品送积分
									var n= parseFloat( retail_money );
									var m= parseFloat("${jfpd.threemax_rate}");
									var g=n*m/100;
	 								$("#addintegral_rate").val("${jfpd.threemax_rate}");
	 								$("#addintegral_number").val(g.toFixed(2));
	 								$("#addintegral_number").attr("readonly","readonly");
	 							}else{
	 								$("#addintegral_rate").val("--");
	 								$("#addintegral_number").val("--");
	 								$("#addintegral_rate").attr("readonly","readonly");
	 								$("#addintegral_number").attr("readonly","readonly");
	 							}
							}
	 					}
						
	 					//修改积分率
	 					function addeditintegral(obj){
	 						if("${jfpd.change_type}" == "3"){//单品送积分
	 								var x=parseFloat($(obj).val());
	 								var retail_money=$("#addretail_money").val();//零
	 								var n= parseFloat("${jfpd.threemin_rate}");
									var m= parseFloat("${jfpd.threemax_rate}");
									if(parseFloat(x) >= parseFloat(n) && parseFloat(x) <= parseFloat(m)){
										var g=parseFloat(retail_money)*x/100;
	 									$("#addintegral_number").val(g);
	 								}else{
										alert("积分率最高"+m+"，最低"+n);
										$(obj).val(m);
										return;
									}
	 						}
	 					}
	
	
	
				//新增展示营销
					function showYx(obj){
						var val= $(obj).val();
				     	if (val== "1") {
				     		if("${mjstatus}" == "1"){
				    	 		alert("已开通满减营销优惠，请选择别的优惠规则");
				    	 		$('.fontwt1').css('display','none');
						     	$('.fontwt2').css('display','none');
						     	$('.fontwt3').css('display','none');
						     	$(obj).val("");
				     			return;
				    	 	}
				      		$('.fontwt1').css('display','none');
					     	$('.fontwt2').css('display','none');
					     	$('.fontwt3').css('display','block');
					     }
				     	else if (val== "2") {
				     		$('.fontwt1').css('display','none');
					     	$('.fontwt2').css('display','block');
					     	$('.fontwt3').css('display','none');
					     }
				     	else if (val== "3") {
	 			     		$('.fontwt1').css('display','block');
					     	$('.fontwt2').css('display','none');
					     	$('.fontwt3').css('display','none');
					     }else{
					    	 	$('.fontwt1').css('display','none');
						     	$('.fontwt2').css('display','none');
						     	$('.fontwt3').css('display','none');
					     }
					}
	
	
	
	
	//新增商品
	function addSub(){
		var market_money = $("#addmarket_money").val().trim();
		if(market_money == null || market_money == "" || parseFloat(market_money) <= 0){
			alert("原价必须大于0！");
			return ;
		}
		var retail_money = $("#addretail_money").val().trim();
		if(retail_money == null || retail_money == "" || parseFloat(retail_money) <= 0){
			alert("零售价必须大于0！");
			return ;
		}
 		if(parseFloat(retail_money) >  parseFloat(market_money)){
			alert("零售价必须小于原价！");
			return ;
		}
		var company = $("#addcompany").val().trim();
		if(company == null || company == "" ){
			alert("单位不能为空！");
			return ;
		}
		var addxiaogoods_category_id = $("#addxiaogoods_category_id").val().trim();
		if(addxiaogoods_category_id == null || addxiaogoods_category_id == ""){
			alert("商品小类不能为空！");
			return ;
		}
 		
		if($(".addcx").val() == "1"){//满减
			if($(".addfontwt31").val() == ""){
				alert("满足的金额条件不能为空");
				return;
			}	
			if($(".addfontwt32").val() == ""){
				alert("优惠的金额不能为空");
				return;
			}
			if(parseFloat($(".addfontwt32").val()) >= parseFloat($(".addfontwt31").val())){
				alert("优惠金额不能大于等于满足的条件");
				return;
			}
			var manjian = $("#addpromotion_content").val("满"+$(".addfontwt31").val()+"元减"+$(".addfontwt32").val()+"元");
 		}else if($(".addcx").val() == "2"){//折扣
			$("#addpromotion_content").val($(".addfontwt2").val()+"折");
		}else if($(".addcx").val() == "3"){//买N减1
			if($(".addfontwt1").val() == ""){
				alert("满足件数不能为空");
				return;
			}
			if(parseFloat($(".addfontwt1").val()) <= 1){
				alert("满足件数不能小于1");
				return;
			}
			$("#addpromotion_content").val("买"+$(".addfontwt1").val()+"件减1");
		}
  		$("#dorm1").submit();
	}
	

	/*
	修改商品======================================================================================================
	*/
	//编辑修改
	function editGoods(value){
		$.ajax({
			type:"POST",
			url:"storepc_CategoryManageController/findGoodsById.do",
			dataType:"JSON",
			data:{"goods_id":value},
			success:function(data){
				if(data.result == "0"){
					alert(data.message);
					return;
				}else{
					$('.fontwt11').css('display','none');
			     	$('.fontwt21').css('display','none');
			     	$('.fontwt31').css('display','none');
			     	$(".fontwt311input").val("");
					$(".fontwt312input").val("");
					$(".fontwt11input").val("");
					$(".fontwt21input").val("");
					 $(".image_url").attr("src","");
				}
				$(".tc_content4").show();
				var pd=data.data;
				$("#goods_name").val(pd.goods_name);
				$("#editgoods_id").val(pd.goods_id);
				$("#editimage_url").attr("src",pd.image_url);
				$(".editimage_url").val(pd.image_url);
				$("#retail_money").val(pd.retail_money);
				$("#market_money").val(pd.market_money);
				$("#company").val(pd.company);
 				if("${jfpd.change_type}" == "1"){//整店送积分
					$("#integral_rate").val(pd.integral_rate);
					$("#integral_rate").attr("readonly","readonly");
					$("#integral_number").val(pd.integral_number);
					$("#integral_number").attr("readonly","readonly");
 				}else if("${jfpd.change_type}" == "2"){//分类送积分
 					$("#integral_rate").val(pd.integral_rate);
					$("#integral_rate").attr("readonly","readonly");
					$("#integral_number").val(pd.integral_number);
					$("#integral_number").attr("readonly","readonly");
				}else if("${jfpd.change_type}" == "3"){//单品送积分
					$("#integral_rate").val(pd.integral_rate);
 					$("#integral_number").val(pd.integral_number);
 				}else{
					$("#integral_rate").val(pd.integral_rate);
					$("#integral_rate").attr("readonly","readonly");
					$("#integral_number").val(pd.integral_number);
					$("#integral_number").attr("readonly","readonly");
				}
				$("#stock_number").val(pd.stock_number);
				$("#starttime").val(pd.starttime);
				$("#endtime").val(pd.endtime);
				$("#promotion_content").val(pd.promotion_content);
				var promotion_type=pd.promotion_type;
				var promotion_content=pd.promotion_content;
				$(".editcx").val(promotion_type).attr("selected","selected");
				if(promotion_type == "1"){//满减
					var x=promotion_content.indexOf("满"); 
					var w=promotion_content.indexOf("元"); 
					var y=promotion_content.indexOf("减"); 
					var z=promotion_content.lastIndexOf("元"); 
					var start=promotion_content.substring(x+1,w);
					var end=promotion_content.substring(y+1,z);
					$(".fontwt311input").val(start);
					$(".fontwt312input").val(end);
					$("#promotion_content").val("满"+start+"元减"+end+"元");
					$(".fontwt31").show();
				}else if(promotion_type == "2"){//折扣
					var z=promotion_content.lastIndexOf("折"); 
					var number=promotion_content.substring(0,z);
					$(".fontwt21input").val(number);
 					$("#promotion_content").val(number+"折");
					$(".fontwt21").show();
				}else if(promotion_type == "3"){//买N减1
					var x=promotion_content.indexOf("买"); 
					var w=promotion_content.indexOf("件"); 
					var number=promotion_content.substring(x+1,w);
					$(".fontwt11input").val(number);
					$("#promotion_content").val("买"+number+"件减1");
					$(".fontwt11").show();
				}else{
					
				}
				$("#big").val(pd.parent_id).attr("selected","selected");
				$("#small").append("<option value='"+pd.son_id+"'>"+pd.son_name+"</option>");
				window.editgoods_id=pd.goods_id;//设为全局变量
			}
		});
	}
	
	
						//判断原价是否大于零售价
						function editmoneyisOK(obj,value){
							isNumberOk(obj);
							var retail_money=$("#retail_money").val();//零
							if(retail_money == ""){
								retail_money="0";
							}
							var market_money=$("#market_money").val();//市
							if(market_money == ""){
								market_money="0";
							}
							var goods_category_id=$("#big").val();
							var back_rate=$("#back_rate"+goods_category_id).val();
							if(parseFloat(retail_money) > parseFloat(market_money)){
								alert("零售价不能大于原价");
								$(obj).val("");
	 							return;
							}
	 						if(value == "1"){//输入零售价之后的判断
								$(".jfopen").show();
								if("${jfpd.change_type}" == "1"){//整店送积分
									var n= parseFloat(retail_money);
									var m= parseFloat("${jfpd.oneback_rate}");
									var g=n*m/100;
									//alert(g);
									$("#integral_rate").val("${jfpd.oneback_rate}");
									$("#integral_rate").attr("readonly","readonly");
	 								$("#integral_number").val(g.toFixed(2));
									$("#integral_number").attr("readonly","readonly");
 								}else if("${jfpd.change_type}" == "2"){//分类送积分
									var n= parseFloat( retail_money );
									var m= parseFloat(back_rate);
									var g=n*m/100;
	 								$("#integral_rate").val(back_rate);
									$("#integral_rate").attr("readonly","readonly");
									$("#integral_number").val(g.toFixed(2));
									$("#integral_number").attr("readonly","readonly");
								}else if("${jfpd.change_type}" == "3"){//单品送积分
									var n= parseFloat( retail_money );
									var m= parseFloat("${jfpd.threemax_rate}");
									var g=n*m/100;
	 								$("#integral_rate").val("${jfpd.threemax_rate}");
	 								$("#integral_number").val(g.toFixed(2));
	 							}else{
	 								$("#integral_rate").val("--");
	 								$("#integral_number").val("--");
	 								$("#integral_rate").attr("readonly","readonly");
	 								$("#integral_number").attr("readonly","readonly");
	 							}
							}
						}
						
						//修改积分率
	 					function editintegral(obj){
	 						if("${jfpd.change_type}" == "3"){//单品送积分
	 								var x=parseFloat($(obj).val());
	 								var retail_money=$("#retail_money").val();//零
	 								var n= parseFloat("${jfpd.threemin_rate}");
									var m= parseFloat("${jfpd.threemax_rate}");
									if(parseFloat(x) >= parseFloat(n) && parseFloat(x) <= parseFloat(m)){
										var g=parseFloat(retail_money)*x/100;
	 									$("#integral_number").val(g);
	 								}else{
										alert("积分率最高"+m+"，最低"+n);
										$(obj).val(m);
										return;
									}
	 						}
	 					}
	
	 				//修改展示营销
					function showYx2(obj){
						var val= $(obj).val();
				     	if (val==1) { 
				     		if("${mjstatus}" == "1"){
				    	 		alert("已开通满减营销优惠，请选择别的优惠规则");
				    	 		$('.fontwt11').css('display','none');
						     	$('.fontwt21').css('display','none');
						     	$('.fontwt31').css('display','none');
						     	$(obj).val("");
				     			return;
				    	 	}
				      		$('.fontwt11').css('display','none');
					     	$('.fontwt21').css('display','none');
					     	$('.fontwt31').css('display','block');
					   }else if (val==2) { 
				     		$('.fontwt11').css('display','none');
					     	$('.fontwt21').css('display','block');
					     	$('.fontwt31').css('display','none');
					   }else if (val==3) { 
	 				    	 	if("${allyxgoodsid}".indexOf(editgoods_id) >= 0){
					     			alert("该商品已进行买N减N优惠，请选择别的优惠规则");
					     			$('.fontwt11').css('display','none');
							     	$('.fontwt21').css('display','none');
							     	$('.fontwt31').css('display','none');
							     	$(obj).val("");
					     			return;
					     		}
					    	 	$('.fontwt11').css('display','block');
						     	$('.fontwt21').css('display','none');
						     	$('.fontwt31').css('display','none');
					   }else if (val=="0") {
					     		$('.fontwt11').css('display','none');
						     	$('.fontwt21').css('display','none');
						     	$('.fontwt31').css('display','none');
					   }
					}
					
					
					
	//确认修改
	function sureEdit(){
		var market_money = $("#market_money").val().trim();
		if(market_money == null || market_money == "" || parseFloat(market_money) <= 0){
			alert("原价必须大于0！");
			return ;
		}
		var retail_money = $("#retail_money").val().trim();
		if(retail_money == null || retail_money == "" || parseFloat(retail_money) <= 0){
			alert("零售价必须大于0！");
			return ;
		}
		if(parseFloat(retail_money) >  parseFloat(market_money)){
			alert("零售价必须小于原价！");
			return ;
		}
		var company = $("#company").val().trim();
		if(company == null || company == ""){
			alert("单位不能为空！");
			return ;
		}
		var small = $("#small").val().trim();
		if(small == null || small == ""){
			alert("商品小类不能为空！");
			return ;
		}
  		
		if($(".editcx").val() == "1"){//满减
			if($(".fontwt311input").val() == ""){
				alert("满足的金额条件不能为空");
				return;
			}	
			if($(".fontwt312input").val() == ""){
				alert("优惠的金额不能为空");
				return;
			}
			if(parseFloat($(".fontwt312input").val()) >= parseFloat($(".fontwt311input").val())){
				alert("优惠金额不能大于等于满足的条件");
				return;
			}
 			$("#promotion_content").val("满"+$(".fontwt311input").val()+"元减"+$(".fontwt312input").val()+"元");
 		}else if($(".editcx").val() == "2"){//折扣
			$("#promotion_content").val($(".fontwt21input").val()+"折");
		}else if($(".editcx").val() == "3"){//买N减1
			if($(".fontwt11input").val() == ""){
				alert("满足件数不能为空");
				return;
			}
			if(parseFloat($(".fontwt11input").val()) <= 1){
				alert("满足件数不能小于等于1");
				return;
			}
			$("#promotion_content").val("买"+$(".fontwt11input").val()+"件减1");
		}
		$("#dorm2").submit();
	}
	
	
	
	
	 
	
	//删除商品====================================================================================================
	function delgoods(goods_id,store_id){
		window.location.href="storepc_CategoryManageController/deleteGoods.do?goods_id="+goods_id+"&store_id="+store_id;
	}
	
	
	
	
	//===========================================================================================================
	//复选框的全选和全不选
	$(function() {
		$(".setAll").click(function() {
			$('input[name="chose"]').attr("checked",this.checked); 
			checkboxsure();
		});
		var $subBox = $("input[name='chose']"); 
		$subBox.click(function(){
			 $(".setAll").attr("checked",$subBox.length == $("input[name='chose']:checked").length ? true : false);
		});
	});
	
	
	
	//上传按钮点击
	var upload_id="";
	function upload(value){
		upload_id=value;
 		$(".uploanImage").click();
	}
	
	//上传图片
	function fileType(obj){
		var d=/\.[^\.]+$/.exec(obj.value); 
		if(!validaImage(d)){
			alert("请上传照片gif,png,jpg,jpeg格式");
		}else{
			$("#imageForm").ajaxSubmit({  
			  	url : '<%=basePath%>storepc_CategoryManageController/uploadheadimageByGoods.do',
		        type: "POST",//提交类型  
		      	dataType:"json",
		   		success:function(result){
		   			 var url=result.url;
 					 //展示图片
					 $("#"+upload_id).attr("src",url);
					 $("."+upload_id).val(url);
 				}
			});
				
		}
			
	}	
	//判断图片是否符合格式
	function validaImage(filename){
		if('.gif.png.jpg.jpeg'.indexOf(filename)<0&&'.GIF.PNG.JPG.JPEG'.indexOf(filename)<0){
			return false;
		}
		return true;
	}
	
	
	function xiugai(){
  		if("${jfpd.change_type}" == "1"){//整店送积分
  			$("#integral_rate").attr("readonly","readonly");
 		}else if("${jfpd.change_type}" == "2"){//分类送积分
 			$("#integral_rate").attr("readonly","readonly");
		}else if("${jfpd.change_type}" == "3"){//单品送积分
			//可修改
		}else{
			$("#integral_rate").val("--");
			$("#integral_number").val("--");
			$("#integral_rate").attr("readonly","readonly");
			$("#integral_number").attr("readonly","readonly");
		}
		$(".dask").css({"display":"block"})
		$(".al_xiugai").css({"display":"block"})
		$(".al_tianjia").css({"display":"none"})
 		$(".fontwt").css({"display":"none"})
	}
	function tianjia(){
 		if("${jfstatus}" == "0"){
   		  alert("请先前往积分设置设置积分");
   		  return;
   	    }else{
   	    	if("${jfpd.change_type}" == "1"){//整店送积分
   	  			$("#addintegral_rate").attr("readonly","readonly");
   	 		}else if("${jfpd.change_type}" == "2"){//分类送积分
   	 			$("#addintegral_rate").attr("readonly","readonly");
   			}else if("${jfpd.change_type}" == "3"){//单品送积分
   				//可修改
   			}else{
   				$("#addintegral_rate").val("--");
   				$("#addintegral_number").val("--");
   				$("#addintegral_rate").attr("readonly","readonly");
   				$("#addintegral_number").attr("readonly","readonly");
   			}
   	    }
		$(".dask").css({"display":"block"})
		$(".al_tianjia").css({"display":"block"})
		$(".al_xiugai").css({"display":"none"})
  		$(".fontwt").css({"display":"none"})
	}
	$(".change").click(function(e){   /*修改*/
		xiugai()
	})
	$(".tianjia").click(function(e){   /*添加*/
		tianjia()
	})
	 
	$(".chaxun").click(function(){
		chaxun()
	})
	function guanbi(){
		$(".dask").css({"display":"none"});
		$(".al_tianjia").css({"display":"none"})
		$(".al_xiugai").css({"display":"none"})
 	}
	$(".close").click(function(){
		guanbi()
	})
	
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