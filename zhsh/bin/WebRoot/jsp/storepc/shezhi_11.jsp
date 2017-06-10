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
    <title>红包记录</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
    <link rel="stylesheet" href="css/pcstore/bootstrap.min.css">
    <link rel="stylesheet" href="css/pcstore/hsd_record.css">
    <script src="js/jquery-1.8.0.min.js"></script>
</head>
<body  onkeydown="BindEnter(event)">
<div class="bg">
    <header>
        <div class="head_cont">
            <img src="img/page/red.png" alt="" class="logo">
            <div class="title">•  红包记录 </div>
        </div>
    </header>
    <section>
        <div class="cont">
            <div class="fir">第<span class="bord">1</span>步：设定红包类型、金额、个数 <span class="eg">(发放的红包只能在本店使用)</span></div>
            <div class="sec_cont">
                <h5>
                    <label class="checkbox-inline lab">
                        <input type="radio" name="redpacket" onclick="checkbox1()" id="checkbox1"> <span>现金红包：</span>
                        <input type="text" class="wid"  placeholder="红包总金额" id="yuan1">元
                        <input type="text" class="wid"  placeholder="红包总个数" id="ge1">个
                        <select class="wid" id="money">
                            <option value = "1">随机金额</option>
							<option value = "2">平均金额</option>
                        </select>
                    </label>
                </h5>
                <h5>
                    <label class="checkbox-inline lab">
                        <input type="radio" name="redpacket" onclick="checkbox2()" id="checkbox2"> <span>折扣红包：</span>
                        <select class="wid" id="yuan2">
						   <option value="0">请选择</option>
						</select>折
                        <input type="text" class="wid" placeholder="红包总个数" id="ge2">个
                        <select class="wid" id = "zhekou">
                            <option value = "1">原价折扣</option>
							<option value = "2">优惠后折扣</option>
                        </select>
                    </label>
                </h5>
            </div>
			<script type="text/javascript">
			    //折扣率
                for(var n=99;n>0;n--){
                	$("#yuan2").append("<option value='"+n+"'>"+n+"</option>");
                }
				//单选框
				function checkbox1(){
					$("#yuan2").val("");
					$("#ge2").val(""); 
				}
				function checkbox2(){
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
            </script>
            <div class="fir"> 第 <span class="bord">2</span> 步 ：设定使用条件</div>
            <div class="sec_cont">
                <h5>
                    使用条件：
                    <select class="wid" id = "tiaojian"  onchange="usercontent(this.value)">
                        <c:forEach  items="${srpList}" var="srp" varStatus="vs">
							 <option value = "${srp.type}">${srp.name}</option>
						</c:forEach> 
                    </select>
					<span class="xx">请输入金额：<input type="text" class="wid"  name="usermoney" id="usermoney" value="" onchange="moneydayumoeny()"/></span>
                </h5>
                <h5>
                    有效时间：
                    <input type="text" class="wid2 stratdate"   name="stratdate" id="start" value="2016-01-01" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
                    至
                    <input type="text" class="wid2 enddate"   name="enddate" id="end" value="2050-01-01" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
                </h5>
            </div>
			<script type="text/javascript">
			//选择条件
    		function usercontent(value){
    			if(value == "2"){
    				$(".xx").show();
    			}else{
    				$(".xx").hide();
    			}
    		}
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
            <div class="fir"> 第 <span class="bord">3</span> 步 ：设定发放范围</div>
            <div class="sec_cont">
                <h5>
                    <span class="fl">会员选项：</span>
                    <label class="checkbox-inline col-xs-2">
                        <input type="checkbox" name= "fanwei"  value="12"> 本店会员
                    </label>
                    <label class="checkbox-inline col-xs-2">
                        <input type="checkbox" name= "fanwei" value="6"> 消费过的会员
                    </label>
                    <label class="checkbox-inline col-xs-2">
                        <input type="checkbox"  name= "fanwei" value="3"> 收藏本店会员
                    </label>
                </h5>
                <h5>
                    <span class="fl">人脉选项：</span>
                    <label class="checkbox-inline col-xs-2">
                        <input type="checkbox" name= "fanwei"  value="1"> 一度人脉
                    </label>
                    <label class="checkbox-inline col-xs-2">
                        <input type="checkbox" name= "fanwei" value="2"> 二度人脉
                    </label>
                    <%-- <label class="checkbox-inline col-xs-2">
                        <input type="checkbox"  name= "fanwei" value="11">
                        <select name="mystore_id" id="mystore_id">
                 			<option value="">我的盟友</option>
                 			<c:forEach items="${unionList}" var="var">
                 				<option value="${var.store_id}">${var.store_name}</option>
                 			</c:forEach>
                  		</select>
                    </label> --%>
                </h5>
                <h5>
                    距离选项：
                    <label class="radio-inline">
                        <input type="radio" name="juli" value="4"> 500M以内
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="juli" value="5"> 1KM以内
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="juli" value="7"> 2KM以内
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="juli" value="8"> 5KM以内
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="juli" value="9"> 所在县区
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="juli" value="10"> 所在地级城市
                    </label>
                </h5>
            </div>
        </div>
        <div class="font_cont">
            <div class="button_box">
                <div class="butt fl" onclick="save()">确定发送</div>
                <div class="butt" onclick="gonext()" id="gonext">稍后发送，前往下一步</div>
            </div>
        </div>
    </section>
	<h6>
        <hr>
        <div class="interval">
            红包记录
        </div>
    </h6>
    <footer>
		<div class="foor_cont">
			<c:forEach  items="${varList}" var="var" varStatus="vs">
				<div class="tips col-xs-5">
					<div class="button" onclick="delect('${var.store_redpackets_id}')">删除</div>
					<ul>
						<li><span>红包编号：</span><span>${var.store_redpackets_id}</span></li>
						<li><span>内容：</span><span>${var.money}</span>
 							<c:if test="${var.redpackage_type eq '1'}">元&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
	                      	 <c:if test="${var.redpackage_type eq '2'}">折&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
	                         <c:if test="${var.redpackage_type eq '1'}">现金红包</c:if>
	                      	 <c:if test="${var.redpackage_type eq '2'}">折扣红包</c:if>
	                      	<span class="red">${var.redpackage_number}</span>个
						</li> 
						<li><span>状态：</span>
							<c:if test="${var.isguoqi eq '0'}"> <span class="blue">未过期</span></c:if>
	                      	<c:if test="${var.isguoqi eq '1'}"> <span class="red">已过期</strong></c:if>	
 						</li>
						<li><span>已领取数：</span><span>${var.overget_number}</span>个</li>
						<li><span>使用条件：</span><span>${var.srp_usercondition_content}</span></li>
						<li><span>发送到：</span><span class="zsxz">${var.content}</span></li>
						<li><span>有效期：</span><span>${var.starttime}</span>至<span>${var.endtime}</span></li>
					</ul>
				</div>
			</c:forEach>   
         </div>
    </footer>
</div>
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
    			alert('你还没有选择发放范围！');
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
	//使用document.getElementById获取到按钮对象
		function BindEnter(event){
			var gonext = document.getElementById("gonext");
			if(event.keyCode == 13){
				gonext.click();
				event.returnValue = false;
			}
		}
	
	//下一步
	 function gonext(){
		window.location.href='<%=basePath%>storepc/goSheZhiOne.do?store_id=${pd.store_id}&jichushezhi=${pd.jichushezhi}';
	}
    </script>
</body>
</html>