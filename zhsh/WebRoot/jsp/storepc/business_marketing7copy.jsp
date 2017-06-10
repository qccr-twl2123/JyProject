<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>首页</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>">
        <link rel="stylesheet" href="css/storepc/business_marketing7.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/storepc/business_marketing7.js"></script>
        <script src="My97DatePicker/WdatePicker.js"></script>
    </head>
    <body style="font-size: 16px;">
    <c:if test="${storeqx.look eq '1'}">
       <div class="parent">
           <div class="d1">
                <p class="d1_p1">第一步：设定红包类型、金额和个数 <span class="d1_sp2">(发放的红包只能在本店使用)</span><span class="d1_sp1">红包记录</span></p>               
           </div>
           <div class="d2">
              <div class="d2_p2"> 
               	 <c:forEach  items="${varList}" var="var" varStatus="vs">
	                  	<p class="p2">
	                      <span>红包编号：${var.store_redpackets_id}</span>
	                      <span>内容：${var.money}
	                      	 <c:if test="${var.redpackage_type eq '1'}">元</c:if>
	                      	 <c:if test="${var.redpackage_type eq '2'}">折</c:if>
	                         <c:if test="${var.redpackage_type eq '1'}">现金红包</c:if>
	                      	 <c:if test="${var.redpackage_type eq '2'}">折扣红包</c:if>
	                      	${var.redpackage_number}个 &nbsp; &nbsp; &nbsp; &nbsp;  
 	                      </span>
 	                      <span>
 	                      	状态：
 	                      	<c:if test="${var.isguoqi eq '0'}"> <strong style="color:blue;">未过期</strong></c:if>
	                      	<c:if test="${var.isguoqi eq '1'}"> <strong style="color:red;">已过期</strong></c:if>	
	                      	<input type="button" value="删除" style="color: blue;position: relative;left: 82px;width: 100px;cursor: pointer;" onclick="delect('${var.store_redpackets_id}')"/>
 	                      </span>
	                      <span> 已领取个数：${var.overget_number}个</span>
	                      <span>使用条件：${var.srp_usercondition_content}</span>
	                      <span>发送到：${var.content}...</span>
	                      <span>有效期:${var.starttime}至${var.endtime}</span>
	                  </p>
                  </c:forEach>  
              </div>
              <p class="p1">
                <label><input type="radio" name="red" class="d2_ipt1" onclick="checkbox1()" id="checkbox1"></input><span class="hands">现金红包</span></label>
                <input type="text" class="d2_ipt2" id="yuan1" placeholder="红包总金额" style="text-align: center;"></input>元
                <input type="text" class="d2_ipt2" id="ge1"></input>个
                <select class="d2_ipt2" id="money">
                    <option value = "1">随机金额</option>
                    <option value = "2">平均金额</option>
                </select>
              </p>
               <p class="p1">
                <label><input type="radio"  name="red" class="d2_ipt1" onclick="checkbox2()" id="checkbox2"></input><span class="hands">折扣红包</span></label>
                 <select class="d2_ipt2" id="yuan2">
	               <option value="0">请选择</option>
 	             </select>折
                <input type="text" class="d2_ipt2" id="ge2"/>个
                <select class="d2_ipt2" id = "zhekou">
                    <option value = "1">原价折扣</option>
                    <option value = "2">优惠后折扣</option>
                </select>
                <br>
                <!-- <span style="color:red;display: block;margin-top: -30px;">例如：9折请输入"90"，88折请输入"88"</span> -->
              </p>
                <script type="text/javascript">
                for(var n=99;n>0;n--){
                	$("#yuan2").append("<option value='"+n+"'>"+n+"</option>");
                }
                </script>
              <p class="d1_p1">第二步：设定使用条件 </p>
              <p class="p1">
                <span class="d2_sp1">使用条件</span>
                <select class="d2_sp1" id = "tiaojian"  onchange="usercontent(this.value)">
                 	 <c:forEach  items="${srpList}" var="srp" varStatus="vs">
                		 <option value = "${srp.type}">${srp.name}</option>
                	</c:forEach> 
                </select>
                	<span class="xx">请输入金额：<input type="text" name="usermoney" id="usermoney" value="" onchange="moneydayumoeny()"/></span>
              </p>
              <script type="text/javascript">
              //判断金额
              function moneydayumoeny(){
            	  if($("#checkbox1").is(":checked")){
            		  var redmoney=$("#yuan1").val();
            		  if(redmoney == ""){
            			  alert("金额不能为空");
            			  return;
            		  }
            		  var ge1=$("#ge1").val();
            		  var money=$("#money").val();
            		  var n=0;
            		  if(money== "2"){
            			  n=parseFloat(redmoney)/parseFloat(ge1);
            		  }else{
            			  n=parseFloat(redmoney)/parseFloat(ge1)+parseFloat(redmoney)/parseFloat(ge1)/2;
            		  }
             		  var usermoney=$("#usermoney").val();
             		  if(usermoney != ""){
             			  if(2*n >= parseFloat(usermoney)){
             				  alert("满足条件金额必须大于红包金额");
             				 $("#usermoney").val("");
             				  return;
             			  }
             		  }
            	  }
              }
              </script>
              <p class="p1">
                <span class="d2_sp1">有效时间</span>
                 <input class="stratdate" type="text" name="stratdate" id="start" value="2016-01-01" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                &nbsp; &nbsp; &nbsp; &nbsp;
                <span>至</span>
                &nbsp; &nbsp; &nbsp; &nbsp;
                <!-- <select class="d2_sp1">
                    <option>11111111</option>
                </select> -->
                 <input class="enddate" type="text" name="enddate" id="end" value="2050-01-01" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
              </p>
              <p class="d1_p1">第三步：设定发放范围 </p>
              
              <p class="p1">
              	<span>会员选项：</span>
                <label><input type="checkbox"class="d2_ipt1" name= "fanwei" value="12"></input><span class="hands d2_sp1" >本店会员</span></label>
                <label><input type="checkbox"class="d2_ipt1" name= "fanwei" value="6"></input><span class="hands d2_sp1" >消费过的会员</span></label>
                <label><input type="checkbox"class="d2_ipt1" name= "fanwei" value="3"></input><span class="hands d2_sp1" >收藏本店会员</span></label>
              </p>
              <p class="p1">
              	<span>人脉选项：</span>
              	<label><input type="checkbox"class="d2_ipt1" name= "fanwei" value="1"></input><span class="hands d2_sp1">一度人脉</span></label>
                <label><input type="checkbox"class="d2_ipt1" name= "fanwei" value="2"></input><span class="hands d2_sp1" >二度人脉</span></label>
                <label><input type="checkbox"class="d2_ipt1" name= "fanwei" value="11"></input>
                 		<select class="hands d2_sp1" name="mystore_id" id="mystore_id">
                 			<option value="">我的盟友</option>
                 			<c:forEach items="${unionList}" var="var">
                 				<option value="${var.store_id}">${var.store_name}</option>
                 			</c:forEach>
                  		</select>
                </label>
              </p>
              <p class="p1">
              	<span>距离选项：</span>
              	<label><input type="checkbox"class="d2_ipt1" name= "juli" value="4"></input><span class="hands d2_sp1" >500M以内</span></label>
                <label><input type="checkbox"class="d2_ipt1" name= "juli" value="5"></input><span class="hands d2_sp1" >1KM以内</span></label>
                <label><input type="checkbox"class="d2_ipt1" name= "juli" value="7"></input><span class="hands d2_sp1" >2KM以内</span></label>
               	 
               </p>
               <p class="p1">
               	 <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
              	 <label><input type="checkbox"class="d2_ipt1" name= "juli" value="8"></input><span class="hands d2_sp1" >5KM以内</span></label>
                 <label><input type="checkbox"class="d2_ipt1" name= "juli" value="9"></input><span class="hands d2_sp1" >所在县区</span></label>
                 <label><input type="checkbox"class="d2_ipt1" name= "juli" value="10"></input><span class="hands d2_sp1" >所有地级市</span></label>
              </p>
			<c:if test="${storeqx.add eq '1'}">
            <span class="yes" onclick="save()">  立即发送  </span>
            </c:if>
           </div>
           
       </div>
       </c:if>
    </body>
    
    <script type="text/javascript">
    	$(".xx").hide();
    	function save(){
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
    		var juli = document.getElementsByName('juli');
    		var fw = '';
    		var jl = '';
    		//获取会员选项和人脉选项的value
    		for ( var i = 0; i < obj.length; i++) {
				if(obj[i].checked) fw += obj[i].value+',';
			}
			//获取距离选项的value
			for ( var j = 0; j < juli.length; j++) {
				if(juli[j].checked) jl += juli[j].value+',';
			}
     		if(fw=='' && jl==''){
    			alert('你还没有选择任何内容！');
    			return;
    		}
    		fw = fw + jl;//所有范围的value
			var checked1 = $("#checkbox1").is(':checked');
			var checked2 = $("#checkbox2").is(':checked');
			if(checked1 == 0 && checked2 == 0){
				alert("请选择红包类型！");
				return false;
			}
			//判断是否选择满减条件
			if(tiaojian  == "2"){
			  	var usermoney=$("#usermoney").val();
				if(usermoney=== ""){
					alert("满减金额不能为空");
					return;
				}
			}
			 
 			if(checked1 ==  1){
 				if(ge1 ==""){
 					alert("个数不能为空");
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
				if(ge2 ==""){
 					alert("个数不能为空");
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
    	
    	
   		 	function checkbox1(){
    		 	var checked = $("input[type='radio']").is(':checked');
     			$("#checkbox2").attr("checked",false);
    			$("#yuan2").val("");
    			$("#ge2").val(""); 
    		}
    		
    		function checkbox2(){
    			var checked = $("input[type='radio']").is(':checked');
     			$("#checkbox1").attr("checked",false);
    			$("#yuan1").val("");
    			$("#ge1").val("");
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
</html> --%>