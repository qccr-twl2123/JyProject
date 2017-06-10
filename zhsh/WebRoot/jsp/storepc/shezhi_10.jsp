<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>高级设置</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
    <link rel="stylesheet" href="css/pcstore/bootstrap.min.css">
    <link rel="stylesheet" href="css/pcstore/hsd_setting.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript">
	//下一步
	var n="${store.remind_integral}";
	if(n !="" &&  parseFloat(n) >= 50 ){
		 window.location.href='<%=basePath%>storepc/goSheZhiOne.do?store_id=${pd.store_id}&jichushezhi=${pd.jichushezhi}';
	} 
	</script>
</head>
<body onkeydown="BindEnter(event)">
<div class="bg">
	<div class="dask">
	    <div class="alertwindow">
	        <div class="button_box">
	            <div class="next">确定</div>
	        </div>
	    </div>
		<div class="show">
			<img src="img/page/daskpng.png" alt="">
	    </div>
	</div>
	<script type="text/javascript">
		var n="${store.remind_integral}";
		if(parseFloat(n) > 0){
		 $(".dask").css({"display":"none"});
		}else{
			 $(".dask").css({"display":"block"});
		}
	</script>
	<form action="<%=basePath%>storepc_StoreManageController/addRemind.do" name="Form" id="Form">
	<input type="hidden" name="jichushezhi"   value="${pd.jichushezhi}"/>
	<input type="hidden" name="store_id" value="${pd.store_id}"/>
	<div class="bg">
	    <header>
	        <div class="head_cont">
	            <img src="img/page/setting.png" alt="" class="logo">
	            <div class="title">•  高级设置 </div>
	            <div class="one"></div>
	        </div>
	    </header>
	    <section>
	        <div class="sec_cont">
	        <ul>
	            <li class="li_size"><span>商家名称：</span><span>${store.store_name}</span></li>
	            <li class="li_size"><span>商家ID：</span><span>${store.store_id}</span></li>
				 <li class="li_size"><span>行业分类：</span><span>${sfs.name1 }--${sfs.parent_name1 }&nbsp;&nbsp;${sfs.name2 }--${sfs.parent_name2 }&nbsp;&nbsp;${sfs.name3 }--${sfs.parent_name3}</span></li>
	            <li class="li_size"><span>注册手机号：</span><span>${store.registertel_phone}</span></li>
	            <li class="li_size"><span>绑定手机号：</span><span>${store.registertel_phone}</span></li>
	            <li class="li_size"><span>会员积分率：</span><span>${store.integral_rate}</span></li>
	            <li class="li_size"><span>推广服务积分率：</span><span>${store.service_rate}</span></li>
	            <li class="li_size"><span>提现费率：</span><span>${store.withdraw_rate}%</span></li>
	            <%--  <li class="li_size"><span>保底积分：</span><span>${store.lowest_score}</span></li> --%>
	            <li class="li_size"><span>注册时间：</span><span>${fn:substring(store.registertime,0,19)} </span></li>
	        </ul>
	        <p ><span class="col">*</span>积分提醒：<input type="text" name="remind_integral" id="remind_integral"  value="${store.remind_integral}"><span class="eg">注：系统默认为最低50，设置后低于该积分将自动发送短信提醒充值。</span></p>
	            <!-- <div class="button_box">
	                <div class="butt" onclick="sureUp()">提交</div>
	            </div> -->
	        </div>
	    </section>
	    <footer>
	        <div class="font_cont">
	            <div class="button_box">
	                <div class="butt" onclick="sureUp()" id="gonext">确认并前往下一步</div>
	            </div>
	        </div>
	    </footer>
	</div>
	</form>
</div>
<script src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
	   //提交
		function sureUp(){
				if($("#remind_integral").val() != ""){
					if(parseFloat($("#remind_integral").val()) < 50 || n==""){
			             alert("默认为最低50，该项为必填");
			             return;
			        }else{
			        	$("#Form").submit();
			        }
 				}else{
					alert("积分提醒不能为空");
				}
				
		}
	
	   //使用document.getElementById获取到按钮对象
		function BindEnter(event){
			var gonext = document.getElementById("gonext");
			if(event.keyCode == 13){
				gonext.click();
				event.returnValue = false;
			}
		}
			
</script> 
<script>
    $(".next").click(function(){
        $(".dask").css({"display":"none"})
    })
</script>
</body>
 </html>
