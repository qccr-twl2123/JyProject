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
	<base href="<%=basePath%>">
	<title>账户设置</title>
	<link rel="stylesheet" href="css/pcstore/zhxx_zhsz.css">
 	<style type="text/css">
	body, html {
	    height: 98%;
 	}
 	.anniu-m {
     padding: 1.4% 8.4%;
	}
 	</style>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
<div class="tk_box">
	<ul >
		<li style="height:34%;line-height:2.5;">
			<span class="padd_l">确定删除<span class="col-blue"></span></span>
		</li>
		<li>
			<span class="padd_l">验证码</span>
 			<input type="text" class="inp-m" id ="delcode" >
			<span class="anniu-hq delcode" onclick="getCode('10');">重新获取</span>
		</li>
		<li style="text-align:right;height:50%;line-height:2.5;padding-right:20px;">
			<span class="anniu-m qd">确定</span>
			<span class="anniu-m qx">取消</span>
		</li>
	</ul>
</div>


	<ul class="list-cont clf" style="">
		<li class="anniu-m fll" style="padding:0.8% 2.4%;margin:0 1px;"  onclick="change('0')" id="cont0">添加支付宝账号</li>
		<li class="anniu-m fll" style="padding:0.8% 2.4%;"  onclick="change('1')" id="cont1">添加银行卡</li>
	</ul>
	<ul>
		<form action="storepc_bankcard/saveAliay.do" method="post" name="alipayForm" id="alipayForm" >
			<input type="hidden" name="store_id" value="${pd.store_id }">
			<li class="cont0">
				<ul>
					<li>
						<span class="inp-m"><span class="col-red">*</span>支付宝帐号</span>：
						<input id = "alipay_number" type = "text" name="alipay_number" placeholder="请填写您的支付宝帐号" >
					</li>
					<li>
						<span class="inp-m"><span class="col-red">*</span>真实姓名</span>：
						<input id = "alipay_name" type = "text" name="alipay_name" placeholder="请填写支付宝实名认证姓名" >
					</li>
					<li>
						<span class="inp-m"><span class="col-red">*</span>验证码</span>：
						<input type="text" class="inp-m" placeholder="" id ="alipayyanzheng" >
						<span class="anniu-hq code" onclick="getCode('7');">获取验证码</span>
					</li>
					<c:if test="${storeqx.add eq '1' and storepd.login_type eq '1'}">
						<li style="text-align:center">
							<span class="anniu-l" onclick="alipaybangding()">绑定</span>
						</li>
					</c:if>
					<li class="zfb">
						<span>我的支付宝帐号：</span>
						<c:forEach items="${alipayList}" var="vs">
							<span class="zf_zh" del_id="${vs.store_alipay_id}" del_type="0"  style="padding:11px 23px">${vs.alipay_number}</span>
	 					</c:forEach>
					</li>
				</ul>
			</li>
		</form>
		<form action="storepc_bankcard/save.do" method="post"  name="bankForm"  id="bankForm">
			<input type="hidden" name="store_id" value="${pd.store_id }">
	 		<li class="cont1">
				<ul>
					<li>
						<span class="inp-m"><span class="col-red">*</span>持卡人姓名</span>：
						<input id = "name" type = "text" name="bankcard_name" placeholder="" >
					</li>
					<li>
						<span class="inp-m"><span class="col-red">*</span>银行账号</span>：
						<input size="25" oninput="formatBankNo(this)" id ="kahao" type = "text" name="bank_number" placeholder="" >
					</li>
					<li>
						<span class="inp-m"><span class="col-red">*</span>银行</span>：
						<input id = "kaihu" type = "text" name="account" placeholder="选择银行"  onclick="getBankName(this)"   >
					</li>				
 					<li>
						<span class="inp-m"><span class="col-red">*</span>开户行网点</span>：
						<input size="25" id ="dot" type = "text" name="dot" placeholder="" >
					</li>
					<li>
						<span class="inp-m"><span class="col-red">*</span>验证码</span>：
						<input type="text" class="inp-m" placeholder="" id ="bankyanzheng" >
						<span class="anniu-hq code" onclick="getCode('7');">获取验证码</span>
					</li>
					<c:if test="${storeqx.add eq '1' and storepd.login_type eq '1'}">
						<li style="text-align:center;">
							<span class="anniu-l" onclick="bangding()">绑定</span>
						</li>
					</c:if>
					<li class="zfb" style="    width: 75%;">
						<span>我的银行卡：</span>
						<c:forEach items="${bankList}" var="vs">
							<span class="padd_lr zf_zh" del_id="${vs.store_bankcard_id}" del_type="1" style="padding:11px 23px">尾号${vs.kh}的储蓄卡</span>
						</c:forEach>
					</li>
				</ul>
			</li>
		</form>
	</ul>
</c:if>
<script src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript">
	//提交绑定支付宝
	function alipaybangding(){
		   if($("#alipay_number").val() == ""){
			   alert("支付宝账号不能为空");
			   return;
		   }
		   if($("#alipay_name").val() == ""){
			   alert("实名不能为空");
			   return;
		   }
		   var alipayyanzheng = $("#alipayyanzheng").val().trim();
	   		if(alipayyanzheng ==  null || alipayyanzheng == ""){
	   			alert("验证码不能为空");
	   			return ;
	   		}
		   $("#alipayForm").ajaxSubmit({  
			  	url : 'storepc_bankcard/saveAliay.do',
		        type: "post",//提交类型  
		        data:{"code":alipayyanzheng},
		      	dataType:"json",
 		   		success:function(data){
		   			 if(data.result == "1"){
		   				window.location.href="storepc_bankcard/list.do?save_type=0&store_id=${pd.store_id}";
		   			 }else{
		   				 alert(data.message);
		   			 }
					 
				}
			}); 
		  // $("#alipayForm").submit();
 	}

	//判断银行卡号
	function formatBankNo (BankNo){
        if (BankNo.value == "") return;
        var bank_number = new String (BankNo.value);
        bank_number = bank_number.substring(0,23); /*帐号的总数, 包括空格在内 */
        if (bank_number.match (".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null){
            /* 对照格式 */
            if (bank_number.match (".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" +".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null){
                var accountNumeric = accountChar = "", i;
                for (i=0;i<bank_number.length;i++){
                	accountChar = bank_number.substr (i,1);
                 	if (!isNaN (accountChar) && (accountChar != " ")) accountNumeric = accountNumeric + accountChar;
                }
                bank_number = "";
                for (i=0;i<accountNumeric.length;i++){    /* 可将以下空格改为-,效果也不错 */
                    if (i == 4) bank_number = bank_number + " "; /* 帐号第四位数后加空格 */
                    if (i == 8) bank_number = bank_number + " "; /* 帐号第八位数后加空格 */
                    if (i == 12) bank_number = bank_number + " ";/* 帐号第十二位后数后加空格 */
                    if (i == 16) bank_number = bank_number + " ";/* 帐号第十6位后数后加空格 */
                    bank_number = bank_number + accountNumeric.substr (i,1)
                }
            }
        }else{
        	bank_number = " " + bank_number.substring (1,5) + " " + bank_number.substring (6,10) + " " + bank_number.substring (14,18) + "-" + bank_number.substring(18,25);
        }
        if (bank_number != BankNo.value){
        	BankNo.value = bank_number;
        }
    }
	
	//获取开户行银行名称
	function getBankName(obj){
		$.get("storepc_bankcard/getBankName.do?cardNumber="+$("#kahao").val(), function(data){
			 $(obj).val(data);
		});
	}


    //绑定银行卡
     	function bangding(){
       		var name = $("#name").val().trim();
       		if(name ==  null || name == ""){
    			alert("开户名不能为空");
    			return ;
    		}
    		var kaihu = $("#kaihu").val().trim();
    		if(kaihu ==  null || kaihu == ""){
    			alert("开户行不能为空");
    			return ;
    		}
    		var kahao = $("#kahao").val().trim();
    		if(kahao ==  null || kahao == ""){
    			alert("卡号不能为空");
    			return ;
    		}
     		var dot = $("#dot").val().trim();
    		if(dot ==  null || dot == ""){
    			alert("开户行网点不能为空");
    			return ;
    		}
    		var bankyanzheng = $("#bankyanzheng").val().trim();
    		if(bankyanzheng ==  null || bankyanzheng == ""){
    			alert("验证码不能为空");
    			return ;
    		}
    		$("#bankForm").ajaxSubmit({  
			  	url : 'storepc_bankcard/save.do',
		        type: "post",//提交类型  
		        data:{"code":bankyanzheng},
		      	dataType:"json",
 		   		success:function(data){
		   			 if(data.result == "1"){
		   				window.location.href="storepc_bankcard/list.do?save_type=1&store_id=${pd.store_id}";
 		   			 }else{
		   				 alert(data.message);
		   			 }
					 
				}
			}); 
     	}
   
 
    	//0-支付宝，1-银行卡
    	if("${pd.save_type}" == "1"){
    		$(".cont0").css({"display":"none"});
    		$("#cont0").css("background","#999")
    		$(".cont1").css({"display":"block"});
    		$("#cont1").css("background","#117dd0");
    	}else{
    		$(".cont0").css({"display":"block"});
    		$("#cont0").css("background","#117dd0")
    		$(".cont1").css({"display":"none"});
    		$("#cont1").css("background","#999");
     	}
    	
    	//修改
    	function change(value){
    		if(value == "0"){
    			window.location.href="storepc_bankcard/list.do?save_type=0&store_id=${pd.store_id}";
    		}else{
    			window.location.href="storepc_bankcard/list.do?save_type=1&store_id=${pd.store_id}";
    		}
    	}



    	// 鼠标移入
    	$(".zf_zh").mouseenter(function(e){
    		$(".zf_zh").css({"color":"#000","border":"1px solid #fff"})
    		$(e.target).css({"color":"#be6b04","border":"1px solid #be6b04"})
     		add_remove(e)
    	})
    	
    	//删除号码
    	var del_id="";
    	var del_type="";
    	function add_remove(e){
    		$(".zf_zh div").remove()
    			var ev=e||window.event
    			var elem=ev.target||ev.srcElement
    			if (elem.nodeName=="SPAN") {
    				if ($(elem)[0].childNodes.length==1) {
    					var div=document.createElement("div")
    					div.innerHTML="x"
    					div.className="closeto"
    					div.onclick=function(){
    						if("${storepd.login_type}" == "2"){
    							alert("无权限删除");
    							return;
    						}
    						getCode("10");
     						//当前点的那个元素$(elem)[0]
    						//del_type:0-支付宝，1-银行卡
    						//del_id:id号
    						del_id=$(elem).attr("del_id");
    						del_type=$(elem).attr("del_type");
    						var del_content= $(elem)[0].innerText;//内容
    						del_content=del_content.substring(0,del_content.length-1);
     						$(".tk_box .col-blue").html(del_content);
    						$(".tk_box").css({"display":"block"});
    						$(".qd").attr("onclick","del_number('"+del_id+"','"+del_type+"','"+elem+"')");
      					}
    					$(elem).append(div)
    				}
    			}
    		
    		$(".zfb").mouseout(function(e){
    			if ($(e.target).parent()[0].nodeName=="UL") {
    				$(".zf_zh div").remove()
    				$(".zf_zh").css({"border":"1px solid #fff","color":"#000"})
    			};	
    		})	

    	}
    	
    	//取消窗口
    	$(".qx").click(function(){
    		$(".tk_box").css({"display":"none"})
    		$(".zf_zh").css({"color":"#000","border":"1px solid #fff"})
    		$(".zf_zh div").remove()
    	})

    	
    	//删除
    	function del_number(id,type,obj){
    		var delcode=$("#delcode").val();
    		if(delcode == ""){
    			alert("验证码不能为空");
    			return;
    		}
       		$.ajax({
				type:"post",
				url:"storepc_bankcard/deleteCard.do",
				data:"store_alipay_id="+id+"&store_bankcard_id="+id+"&delcode="+delcode+"&type="+type,
				success:function(data){
					 if(data.result == "1"){
						 $(obj).remove();
						 $(".tk_box").css({"display":"none"});
					 }else{
						 alert(data.message);
					 }
				}
			}); 
      	}
    	
    	
     //获取验证码
     	var validCode=true;
     	function getCode(codetype){
        		if(validCode){
     			$.ajax({
    				type:"post",
    				url:"storepc/findCodeByCard.do",
    				data:"phone=${storepd.registertel_phone}&type="+codetype+"&in_position=pc",
    				success:function(data){
    					alert(data.message);
    					if(data.result == "1"){
    						var time=60;
    						var code;
    						if(codetype == "7"){
    							code=$(".code");
    						}else{
    							code=$(".delcode");
    						}
     		       			validCode=false;
    		       			code.attr("onclick","");
    	       				var t=setInterval(function() {
    		       				time--;
    		       				code.html(time+"秒");
    		       				if (time==0) {
    		       					clearInterval(t);
    		       					code.html("重新获取");
    		       					validCode=true;
    			       				code.attr("onclick","getCode("+codetype+")");
     		       				}
    		       			},1000)
    					} 
    				}
    			}); 
     		}
      	}  
       	
     
    </script>
</body>
</html>