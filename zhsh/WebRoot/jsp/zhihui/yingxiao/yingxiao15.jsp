<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>营销管理</title>
        <meta charset="utf-8">
       	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
		<link rel="stylesheet" href="css/ace.min.css" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/yingxiao13.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="js/jquery.form.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="${msg}.do" name="Form" id="Form" method="post">
    	<input type="hidden" name="currentPage"    value="${empty pd.currentPage?'1':pd.currentPage}"/>
    	<input type="hidden" name="oneyuangoods_id"   value="${pd.oneyuangoods_id}"/>
    	<input type="hidden" name="type"   value="${type}"/>
       <div class="yingxiao8_d1">
         	<span class="yingxiao8_d1_sp1">商品头像</span>
        	<!-- 上传图片 -->
			<a style="margin:0 auto;display:inline-block;" onclick="upload()">
  				<img src="${goodspd.oneyuangoodsimage_url}"  id="oneyuangoodsimage_url" style="width:60px;height:60px;"/>
			</a>
			<input type="hidden" name="oneyuangoodsimage_url" class="oneyuangoodsimage_url" value="${goodspd.oneyuangoodsimage_url}"  />
			<!-- 结束 -->
       </div>
      <div class="yingxiao8_d1">
	        <span class="yingxiao8_d1_sp1">商品名称</span>
	        <input type="text"  class="yingxiao8_d1_ipt1" name="oneyuangoods_name" value="${goodspd.oneyuangoods_name }" id="oneyuangoods_name" placeholder="商品名称" />
       </div>
       <div class="yingxiao8_d1">
	        <span class="yingxiao8_d1_sp1">期数</span>
	        <input type="number"  class="yingxiao8_d1_ipt1" name="nowtimes" value="${goodspd.nowtimes }" id="nowtimes" placeholder="商品名称" />
       </div>
      <div class="yingxiao8_d1">
	        <span class="yingxiao8_d1_sp1">所需数量</span>
 	        <input type="number" placeholder="需要数量"  class="yingxiao8_d1_ipt1" value="${goodspd.oneyuangoodsneed_quantity}" name="oneyuangoodsneed_quantity" id="oneyuangoodsneed_quantity" />
 	        <span>已购买数量： <span style="color:red;">${goodspd.oneyuangoodsnowpay_quantity }</span></span>
 	        <span>比例： <span style="color:red;">${goodspd.oneyuangoodsrate }</span></span>
 	       
      </div>
      <c:if test="${type eq 'edit'}">
	      <div class="yingxiao8_d1">
		        <span class="yingxiao8_d1_sp1">数值A</span>
		        <input type="text"  class="yingxiao8_d1_ipt1" name="value_a" value="${goodspd.value_a }" placeholder="数值A" />
		        <span>数值A说明</span>
		        <input type="text"  class="yingxiao8_d1_ipt1" name="value_a_text" value="${goodspd.value_a_text }" placeholder="数值A说明" />
	       </div>
	       <div class="yingxiao8_d1">
		        <span class="yingxiao8_d1_sp1">数值B</span>
		        <input type="text"  class="yingxiao8_d1_ipt1" name="value_b" value="${goodspd.value_b }" placeholder="数值A" />
		        <span>数值B说明</span>
		        <input type="text"  class="yingxiao8_d1_ipt1" name="value_b_text" value="${goodspd.value_b_text }" placeholder="数值A说明" />
	       </div>
       </c:if>
        <div class="yingxiao8_d1">
	        <span class="yingxiao8_d1_sp1">幸运号 <span style="color:red;">${goodspd.locky_number }</span></span>
 	        <span>结束时间</span>
	        <input type="text"  class="yingxiao8_d1_ipt1" id="overtime" name="overtime" value="${goodspd.overtime }" placeholder="结束时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:ss:mm' })" />
       </div>
      <div class="yingxiao8_d1">
            <c:if test="${qx.edit eq '1'}">
           		<span class="yingxiao8_yes" onclick="tijiao()">提交</span>
           </c:if>
           <span class="yingxiao8_yes" onclick="backtop()">返回</span>
      </div>	 
	</form>
	</c:if>
	<form action="zhihui_city_file/uploanImage.do" method="post" name="imageForm" id="imageForm"  enctype="multipart/form-data"> 
 	        <input type="file" style="width:1px;display:none;"   id="upload-file"  name="uploanImage" class="upload-file" onchange="fileType(this)"/>
 	</form>
	<script type="text/javascript" src="<%=basePath%>js/jquery.tips.js"></script><!--提示框-->
	<script type="text/javascript">
		//退出
		function backtop(){
			window.location.href='<%=basePath%>oneYuan/datalistPageGoods.do?currentPage=${empty pd.currentPage?'1':pd.currentPage}';
		}	
	
		//	提交
 		function tijiao(){
 			if($(".oneyuangoodsimage_url").val()==""){
	  			$(".oneyuangoodsimage_url").tips({
	  				side:3,
	  	            msg:'头像不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			$(".oneyuangoodsimage_url").focus();
	  			return false;
	  		}
 			if($("#oneyuangoods_name").val()==""){
	  			$("#oneyuangoods_name").tips({
	  				side:3,
	  	            msg:'名称不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			$("#oneyuangoods_name").focus();
	  			return false;
	  		}
 			if($("#oneyuangoodsneed_quantity").val()==""){
	  			$("#oneyuangoodsneed_quantity").tips({
	  				side:3,
	  	            msg:'需要的数量不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			$("#oneyuangoodsneed_quantity").focus();
	  			return false;
	  		}
 			if($("#nowtimes").val()==""){
	  			$("#nowtimes").tips({
	  				side:3,
	  	            msg:'期数不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			$("#nowtimes").focus();
	  			return false;
	  		}
 			if($("#overtime").val()==""){
	  			$("#overtime").tips({
	  				side:3,
	  	            msg:'结束时间不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			$("#overtime").focus();
	  			return false;
	  		}
  			$("#Form").submit();//提交
 		}
		
 		//上传按钮点击
 		function upload(){
  			$(".upload-file").click();
 		}

 		//上传图片
 		function fileType(obj){
 	   		var d=/\.[^\.]+$/.exec(obj.value); 
 	  		if(!validaImage(d)){
 				alert("请上传照片gif,png,jpg,jpeg格式");
 			}else{
   	 			$("#imageForm").ajaxSubmit({  
 				  	url : '<%=basePath%>zhihui_city_file/uploadheadimage.do?tm='+new Date().getTime(),
 			        type: "POST",//提交类型  
 			      	dataType:"json",
 			   		success:function(result){
 			   			 var url=result.url;
 						 $(".oneyuangoodsimage_url").val(url);
 						 //替换展示图片
 						 $("#oneyuangoodsimage_url").attr("src",url);
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
	</script>
    </body>
 </html>