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
    <title>消费场景</title>
	 <base href="<%=basePath%>">
	 <link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
    <link rel="stylesheet" href="css/pcstore/bootstrap.min.css">
    <link rel="stylesheet" href="css/pcstore/hsd_scene.css">
	<script src="js/jquery-1.8.0.min.js"></script>
</head>
<body  onkeydown="BindEnter(event)">
<div class="bg">
<header>
    <div class="head_cont">
        <img src="img/page/scene.png" alt="" class="logo">
        <div class="title">•  消费场景 </div>
        <div class="one"></div>
    </div>
</header>
<section>
    <div class="cont">
        <div class="fir">第<span class="bord">1</span>步：选择消费场景</div>
        <div class="fir_cont">
            <div class="checkbox font">
                <label>
				<input name="check1" type="checkbox"  value="1" id = "zhifu1" ${pd.type1 eq '1'?'checked':'' }/>
                到店消费
                </label>
            </div>
            <div class="checkbox font">
                <label>
                    <input name="check1" type="checkbox"   value="2" id = "zhifu2" ${pd.type2 eq '1'?'checked':'' }/>
                    网购自提
                </label>
            </div>
        </div>
        <div class="fir"> 第 <span class="bord">2</span> 步 ：选择消费者支付方式（必选）</div>
        <div class="sec_cont">
            <div class="checkbox font">
                <label>
                   <input name="check2" type="checkbox"  value="1"  ${pd.zfb eq '1'?'checked':'' }/>
                    支付宝支付
                </label>
            </div>
            <div class="checkbox font">
                <label>
                   <input name="check2" type="checkbox"  value="2" ${pd.wx eq '1'?'checked':'' }/>
                    微信支付
                </label>
            </div>
            <div class="checkbox font">
                <label>
                    <input name="check2" type="checkbox" value="3" ${pd.pos eq '1'?'checked':'' }/>
                    POS机支付
                 </label>
            </div>
            <div class="checkbox font">
                <label>
                   <input name="check2" type="checkbox"  value="4" ${pd.apple eq '1'?'checked':'' }/>
                    Apple Pay支付
                </label>
            </div>
        </div>
    </div>
</section>
<footer>
    <div class="font_cont">
         <div class="button_box">
             <div class="butt"  onclick="save()" id="gonext">确定,并前往下一步</div>
        </div>
    </div>
</footer>
</div>
<script type="text/javascript">
     	function save(){
    		 var type1 = $("#zhifu1").val();
    		 var type2 = $("#zhifu2").val();
     		 var obj = document.getElementsByName('check2');
    		 var zhifu = '';
    		 for ( var i = 0; i < obj.length; i++) {
				 if(obj[i].checked) zhifu += obj[i].value+',';
			 }
     		if(zhifu==''){
    			alert('你还没有选择支付方式！');  
    			return;
    		}
     		var zhifu1 = $("#zhifu1").is(':checked');
    		var zhifu2 = $("#zhifu2").is(':checked');
    		if(zhifu1 == 1 && zhifu2 == 1){
    			 var type=type1+","+type2;
    		}else if(zhifu2 == 1 && zhifu1 == 0){
    			 var type="0,"+type2;
    		}else{
    			 var type=type1+",0";
    		}
    		$.ajax({
				type:"post",
				url:"storepc_way/save.do",
				data:"way_type="+type+"&way_status="+zhifu+"&store_id="+"${pd.store_id}",
				success:function(data){
					gonext();
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