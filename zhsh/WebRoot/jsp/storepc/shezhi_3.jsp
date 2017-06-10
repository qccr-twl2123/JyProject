<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<% String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>商家图片</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
    <link rel="stylesheet" href="css/pcstore/hsd_picture.css">
    <link rel="stylesheet" href="css/pcstore/bootstrap.min.css">
    <script type="text/javascript" src="js/jquery-1.8.0.min.js"></script> 
 	<script type="text/javascript" src="js/jquery.form.js"></script>
</head>
<body onkeydown="BindEnter(event)">
<header>
    <div class="head_cont">
        <img src="img/page/business_inf/inf_logo.png" alt="" class="logo">
        <div class="title">•  商家图片 </div>
        <div class="one"></div>
    </div>
</header>
<form action="<%=basePath%>storepc_StoreManageController/addPic.do" method="post" name="Form" id="Form">
<input type="hidden" name="store_id"  id="store_id" value="${pd.store_id}"/>
<input type="hidden" name="address"  id="address" value=""/>
<input type="hidden" name="jichushezhi"   value="${pd.jichushezhi}"/>
<input type="hidden" name="address1image" id="address1image"  value=""/>
<input type="hidden" name="address1text" id="address1text"  value=""/>
<section>
    <div class="sec_cont">
        <div class="thumb">
            <span>缩略图</span>
            <p>注：每张图片不大于1M（400*400），支持jpg，png，jap。</p>
            <div class="thumb_img">
				<c:forEach items="${one}" var="var1" varStatus="vs1">
					<div class="img_box" onclick="upload('one${vs1.index+1}')">
						<div class="desk">
							<img src="img/picture/add2.png" alt="">
						</div>
						<img src="" alt="" class="th_img"  id="one${vs1.index+1}">
						<input type="hidden"  class="one${vs1.index+1} oneimage" value="${var1}" style="display:none;width:1px;height:1px;"/>
					</div>
				</c:forEach>
            </div>

        </div>
        <div class="details">
            <span>详情图</span><p>注：每张图片不大于1M，支持jpg，png，jap。</p>
            <div class="detail_img">
				<c:forEach items="${two}" var="var2" varStatus="vs2">
					<div class="img_box" >
						<div class="desk" onclick="upload('two${vs2.index+1}')">
							<img src="img/picture/add2.png" alt="">
						</div>
						<img     class="th_img" id="two${vs2.index+1}">
						<input type="hidden"  class="two${vs2.index+1} twoimage" value="" />
						<div class="introduce">
							<input type="text" placeholder="点击输入文字" class="twotext">
						</div>
					</div>
				</c:forEach>	 
             </div>
        </div>
        </div>
</section>
</form>
<form action="zhihui_city_file/uploanImage.do" method="post" name="imageForm" id="imageForm"  enctype="multipart/form-data"> 
 	 <input type="file" style="width:1px;display:none;"   id="upload-file"  name="uploanImage" class="upload-file" onchange="fileType(this)"/>
</form>
<footer>
    <div class="next"  onclick="sureUp()" id="sureUp">
        <img src="img/page/next.png" alt="">
    </div>
</footer>
<script type="text/javascript">
    
     //------------------以上裁剪-------------------------------------------
    //提交
	function sureUp(){
    	//缩略图的内容
    	var address="";
    	$(".oneimage").each(function(){
    		if($(this).val() == ""){
    			address+="img/base_tp.png"+",";
    		}else{
    			address+=$(this).val()+",";
    		}
    	});
    	$("#address").val(address);
    	//详情图的内容
    	var address1image="";
    	$(".twoimage").each(function(){
    		if($(this).val() != ""){
    			address1image+=$(this).val()+",";
    		}else{
    			address1image+="img/base_tp.png"+",";
    		}
    	});
    	$("#address1image").val(address1image);
    	//文本的内容
    	var address1text="";
    	$(".twotext").each(function(){
    		if($(this).val() != ""){
    			address1text+=$(this).val()+",";
    		}else{
    			address1text+=" "+",";
    		}
    	});
    	$("#address1text").val(address1text);
    	//提交
	    $("#Form").submit();
	}
	var classId="";//class的唯一标示
	
	//上传按钮点击
	function upload(value){
		classId=value;
		$(".upload-file").click();
	}

	//上传图片
	function fileType(obj){
		if(obj.value == null || obj.value == ""){
  	    	return;
  	    }
   		var d=/\.[^\.]+$/.exec(obj.value);
   		if(!validaImage(d)){
			alert("请上传照片gif,png,jpg,jpeg格式");
		}else{
   			$("#imageForm").ajaxSubmit({  
			  	url : '<%=basePath%>zhihui_city_file/uploadheadimage.do?tm='+new Date().getTime(),
		        type: "POST",//提交类型  
		      	dataType:"json",
		      	resetForm:true,
		   		success:function(result){
		   			 var url=result.url;
						 $("."+classId).val(url);
						 //替换展示图片
					  $("#"+classId).attr("src",url);
					  classId="";
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
	
	//使用document.getElementById获取到按钮对象
	function BindEnter(event){
		var sureUp = document.getElementById("sureUp");
		if(event.keyCode == 13){
			sureUp.click();
			event.returnValue = false;
		}
	}

</script>
</body>
</html>