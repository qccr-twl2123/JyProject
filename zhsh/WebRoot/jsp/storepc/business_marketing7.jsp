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
	<title>红包</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/yxkt_hb.css">
	 <script src="js/jquery-1.8.0.min.js"></script>
	  <script src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>
    <c:if test="${storeqx.look eq '1'}">
	<ul>
		<li class="title">
			<span>第一步：设定红包类型、金额和个数</span>
			<span>（发放的红包只能在本店使用）</span>
		</li>
		<li style="padding-left:8%;line-height:3;">
			<input type="radio" name="type_hb"   id="checkbox1" checked onclick="qingkong_input()">
			<span>现金红包</span>
			<input type="text" placeholder="红包总金额" oninput="showevery_money()"        id="yuan1"   onkeyup="value=value.replace(/[^\d\.]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"   >
			<span>元&nbsp;&nbsp;</span>
			<input type="number" id="ge1" oninput="showevery_money()" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"  >
			<span>个&nbsp;&nbsp;</span>
			<select  id="money" >
                   <!--  <option value = "1">随机金额</option> -->
                    <option value = "2">平均金额</option>
            </select>
            <span >每个红包<span style="color:red" class="every_money"></span>元</span>
		</li>
		
		<li style="padding-left:8%;line-height:3;"  >
			<input type="radio" name="type_hb" id="checkbox2" onclick="qingkong_input()">
			<span>折扣红包</span>
			<select  id="yuan2">
	               <option value="0">请选择</option>
 	        </select>折
			<span>折&nbsp;&nbsp;</span>
			<input type="number" id="ge2"  onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" >
			<span>个&nbsp;&nbsp;</span>
			<select   id = "zhekou">
                    <option value = "1">原价折扣</option>
                    <option value = "2">优惠后折扣</option>
            </select>
		</li>
		<li class="title">
			<span>第二步：设定使用条件</span>
		</li>
		<li style="padding-left:8%;line-height:3;">
			<span>使用条件</span>
			<select class="d2_sp1" id = "tiaojian"  onchange="usercontent(this.value)">
                 	 <c:forEach  items="${srpList}" var="srp" varStatus="vs">
                		 <option value = "${srp.type}">${srp.name}</option>
                	</c:forEach> 
            </select>
			<span class="xx">请输入金额：<input type="text" name="usermoney" id="usermoney" value="" onchange="moneydayumoeny()"/><span style="  font-size: 6px; ">*金额必须 &gt;= 红包总金额/个数*2  *</span></span>
 		</li>
 
		<li style="padding-left:8%;line-height:3;">
			<span>有效时间</span>
			<input class="stratdate" type="text" name="stratdate" id="start" value="2017-01-01" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			<span>&nbsp;至&nbsp;</span>
			<input class="enddate" type="text" name="enddate" id="end" value="2018-01-01" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		</li>
		<li class="title">
			<span>第三步：设定发放范围</span>
		</li>
		<li style="padding-left:8%;line-height:3;">
			<span>会员选项：</span>
			<input type="checkbox" name= "fanwei" value="12">
			<span class="xuanzhong">本店会员</span>
			<input type="checkbox" name= "fanwei" value="6">
			<span class="xuanzhong">消费过的会员</span>
			<input type="checkbox" name= "fanwei" value="3">
			<span class="xuanzhong">收藏本店的会员</span>
		</li>
		<li style="padding-left:8%;line-height:2.5;">
			<span>人脉选项：</span>
			<input type="checkbox" name= "fanwei" value="1">
			<span class="xuanzhong">一度人脉</span>
			<input type="checkbox" name= "fanwei" value="2">
			<span class="xuanzhong">二度人脉</span>
		</li>
		<li style="padding-left:8%;line-height:2.5;">
			<span>距离选项：</span>
			<input type="radio" name="jlxx"  value="4">
			<span class="xuanzhong">500M以内</span>
			<input type="radio" name="jlxx"  value="5">
			<span class="xuanzhong">1KM以内</span>
			<input type="radio" name="jlxx"  value="7">
			<span class="xuanzhong">2KM以内</span>
			<input type="radio" name="jlxx"  value="8">
			<span class="xuanzhong">5KM以内</span>
			<input type="radio" name="jlxx"  value="9">
			<span class="xuanzhong">所在县区</span>
			<input type="radio" name="jlxx"  value="10">
			<span class="xuanzhong">所在地级市</span>
			<input type="radio" name="jlxx"  value="0" checked>
			<span class="xuanzhong">无</span>
		</li>
		<c:if test="${storeqx.add eq '1'}">
		<li style="text-align:center;">
			<span class="anniu-m" onclick="save(this)">立即发送</span>
		</li>
		</c:if>
	</ul>
	<div class="one">
		<span class="hbjl">红包记录</span>
	</div>
	<ul >    <!-- 红包记录box -->
	 <c:forEach  items="${varList}" var="var" varStatus="vs">
		<li class="hb_item">	<!-- 红包记录item -->
			<ul class="item_list">
				<li>
					<span>红包编号</span>：
					<span>${var.store_redpackets_id}</span>
				</li>
				<li>
					<span>内容</span>：
					<span>${var.money}</span>
					<span>
						<c:if test="${var.redpackage_type eq '1'}">元</c:if>
	                    <c:if test="${var.redpackage_type eq '2'}">折</c:if>
					</span>
					&nbsp;
					&nbsp;
					&nbsp;
					&nbsp;
					 <c:if test="${var.redpackage_type eq '1'}">现金红包</c:if>
	                 <c:if test="${var.redpackage_type eq '2'}">折扣红包</c:if>
				     <span class="col-red">${var.redpackage_number}</span>个
				</li>
				<li>
					<span>状态</span>：
					<c:if test="${var.isguoqi eq '0'}"> <span class="col-blue">未过期</span></c:if>
	                <c:if test="${var.isguoqi eq '1'}"> <span class="col-red">已过期</span></c:if>	
  				</li>
				<li>
					<span>已领取数</span>：
					<span>${var.overget_number}</span>个					
				</li>
				<li>
					<span>使用条件</span>：
					<span>${var.srp_usercondition_content}</span>
				</li>
				<li>
					<span>发送到</span>：
					<span>${var.content}</span>
				</li>
				<li>
					<span>有效期</span>：
					<span>${var.starttime}</span>
					<span>至</span>
					<span>${var.endtime}</span>
				</li>
			</ul>
			<span class="anniu-s del_item" style="position: absolute;right:5%;bottom:5%;" onclick="delect('${var.store_redpackets_id}')">删除</span>
		</li>
	 </c:forEach>   
	</ul>
	</c:if>
	<script type="text/javascript">
	//清空文本框
	function qingkong_input(){
		$("#yuan1").val("");
		$("#ge1").val("");
		$("#ge2").val("");
		$(".every_money").html("");
	}
	
	
	
	//显示平均金额
	function showevery_money(){
		  var xianjin_money=$("#yuan1").val();
		  if(xianjin_money == ""){
			  $(".every_money").html("");
			  return;
		  }
		  var xianjin_number=$("#ge1").val();
		  if(xianjin_number == ""){
			  $(".every_money").html("");
			  return;
		  }
		  var _n=(parseFloat(xianjin_money)/parseFloat(xianjin_number)).toFixed(2);
		  $(".every_money").html(_n);
		  
	  }
	
	//显示折扣
	 for(var n=99;n>0;n--){
     	$("#yuan2").append("<option value='"+n+"'>"+n+"</option>");
     }
	
	 //判断金额
    function moneydayumoeny(){
  	  if($("#checkbox1").is(":checked")){
  		  var redmoney=$("#yuan1").val();
  		  if(redmoney == "" || redmoney == "0"){
  			  alert("金额不能为空/0");
  			  return;
  		  }
  		  var ge1=$("#ge1").val();
  		  var money=$("#money").val();
  		  var n=0;
  		  if(money== "2"){//2-平均金额。1-随机金额
  			  n=parseFloat(redmoney)/parseFloat(ge1);
  		  }else{
  			  n=parseFloat(redmoney)/parseFloat(ge1)+parseFloat(redmoney)/parseFloat(ge1)/2;
  		  }
   		  var usermoney=$("#usermoney").val();
   		  if(usermoney != ""){
   			  if(2*n >= parseFloat(usermoney)){
   				  alert("满足条件金额必须  大于等于 红包总金额/个数*2  ");
   				 $("#usermoney").val("");
   				  return;
   			  }
   		  }
  	  }
    }
	
	
	
	
		//新增红包
    	$(".xx").hide();
    	function save(now_obj){
    		$(now_obj).attr("onclick","");
    		$(now_obj).css("background","#ccc");
     		var yuan1 = $("#yuan1").val().trim();
    		var ge1 = $("#ge1").val().trim();
    		var yuan2 = $("#yuan2").val().trim();
    		var ge2 = $("#ge2").val().trim();
    		var tiaojian = $("#tiaojian option:selected").val(); 
    		var tiaojiantext = $("#tiaojian option:selected").text(); 
    		var money = $("#money option:selected").val();
    		var zhekou = $("#zhekou option:selected").val();
    		var startdate = $(".stratdate").val();
    		var enddate = $(".enddate").val(); 
     		//var checked = $("input[type='checkbox']").is(':checked');
    		//获取所有复选框值(方法1)
    		var obj = document.getElementsByName('fanwei');
    		var juli = document.getElementsByName('jlxx');
    		var fw = '';
    		var jl = '';
    		//获取会员选项和人脉选项的value
    		for ( var i = 0; i < obj.length; i++) {
				if(obj[i].checked) fw += obj[i].value+',';
			}
			//获取距离选项的value
			for ( var j = 0; j < juli.length; j++) {
				if(juli[j].checked){
					if(juli[j].value != "0"){
						jl += juli[j].value+',';
					}
 				}
			}
     		if(fw=='' && jl==''){
    			alert("你还没有选择任何内容！");
    			$(now_obj).attr("onclick","save(this)");
    			$(now_obj).css("background","#1187e2");
    			return;
    		}
    		fw = fw + jl;//所有范围的value
			var checked1 = $("#checkbox1").is(':checked');
			var checked2 = $("#checkbox2").is(':checked');
			if(checked1 == 0 && checked2 == 0){
				alert("请选择红包类型！");
				$(now_obj).attr("onclick","save(this)");
				$(now_obj).css("background","#1187e2");
				return false;
			}
			//判断是否选择满减条件
			if(tiaojian  == "2"){
			  	var usermoney=$("#usermoney").val();
				if(usermoney=== ""){
					alert("满减金额不能为空");
					$(now_obj).attr("onclick","save(this)");
					$(now_obj).css("background","#1187e2");
					return;
				}
			}
			 
 			if(checked1 ==  1){
 				if(yuan1 == "" || yuan1 == "0"){
      			  	alert("金额不能为空/0");
      			  	$(now_obj).attr("onclick","save(this)");
					$(now_obj).css("background","#1187e2");
      			  	return;
      		 	 }
 				if(ge1 =="" || ge1 =="0"){
 					alert("个数不能为空/0");
 					$(now_obj).attr("onclick","save(this)");
 					$(now_obj).css("background","#1187e2");
 					return;
 				}
				 $.ajax({
						type:"post",
						url:"<%=basePath%>storepc_redpackets/save.do",
						data:"money="+yuan1+"&redpackage_number="+ge1+"&redpackage_type="+1+
	    				"&srp_usercondition_id="+tiaojian+"&starttime="+startdate+"&endtime="+enddate+"&tiaojiantext="+tiaojiantext+
	    				"&srp_opentype_id="+fw+"&choice_type="+money+"&store_id="+"${storepd.store_id}"+"&usermoney="+$("#usermoney").val()+"&mystore_id="+$("#mystore_id").val(),
						success:function(data){
 							window.location.reload();
						}
					});
 			}else if(checked2  ==  1){
				if(ge2 =="" || ge2 =="0"){
 					alert("个数不能为空/0");
 					$(now_obj).attr("onclick","save(this)");
 					$(now_obj).css("background","#1187e2");
 					return;
 				}
				 $.ajax({
						type:"post",
						url:"<%=basePath%>storepc_redpackets/save.do",
						data:"money="+yuan2+"&redpackage_number="+ge2+"&redpackage_type="+2+
	    				"&srp_usercondition_id="+tiaojian+"&starttime="+startdate+"&endtime="+enddate+"&tiaojiantext="+tiaojiantext+
	    				"&srp_opentype_id="+fw+"&choice_type="+zhekou+"&store_id="+"${storepd.store_id}"+"&usermoney="+$("#usermoney").val(),
						success:function(data){
							window.location.reload();
						}
					}); 
			
			}
     	}
    	 
    		
    	//选择条件
    	function usercontent(value){
    		if(value == "2"){
    				$(".xx").show();
    		}else{
    				$(".xx").hide();
    		}
    	}
    
    //多选框单选		
    $(function(){ 
		$(':checkbox[name=juli]').each(function(){ 
			$(this).click(function(){ 
				if($(this).attr('checked')){ 
					$(':checkbox[name=juli]').removeAttr('checked'); 
					$(this).attr('checked','checked'); 
				} 
			}); 
		}); 
	}); 
	
    
    //删除红包
	function delect(id){
		 $.ajax({
				type:"post",
				url:"<%=basePath%>storepc_redpackets/deleteRed.do",
				data:"store_redpackets_id="+id,
				success:function(data){
					if(data.result == "1"){
						window.location.reload();
					}else if(data.result == "2"){
						alert("该红包已经有人领取，不可删除！");
 					}else if(data.result == "0"){
						alert("删除失败，请联系管理员！");
					}
					
				}
			}); 
	}
    </script>
</body>
<script src="js/jquery-1.12.4.min.js"></script>
<script>
	$(function(){
		$(".xuanzhong").click(function(e){   /*点击汉字选择input*/
			$(e.target).prev().prop("checked",!($(e.target).prev()[0].checked))
		})
		 /*删除按钮*/
		/* $(".del_item").click(function(e){  
			$(e.target).parent().remove()
		}) */
	})
</script>
</html>