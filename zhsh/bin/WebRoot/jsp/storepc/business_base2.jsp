 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<% String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商家图片</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/jcxx_sjtp.css">
	<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script> 
 	<script type="text/javascript" src="js/jquery.form.js"></script>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
	<form action="<%=basePath%>storepc_StoreManageController/addPic.do" method="post" name="Form" id="Form">
    <input type="hidden" name="store_id"  id="store_id" value="${pd.store_id}"/>
    <input type="hidden" name="address"  id="address" value=""/>
    <input type="hidden" name="address1image" id="address1image"  value=""/>
    <input type="hidden" name="address1text" id="address1text"  value=""/>
	<ul>
		<li style="line-height:2.5;">
			<span style="font-size:112.5%;">缩略图</span>
			<span style="font-size:87.5%;" class="col-6">注：每张图片必须是等比例1:1，否则显示会变形，支持jpg、png、jap等</span>
		</li>
		<li>
			<ul class="img_list_box clf">
				<c:forEach items="${one}" var="var1" varStatus="vs1">
                	<li class="img_item" onclick="upload('one${vs1.index+1}')">
						<div> 
							<img src="${var1 eq ''?'img/sjht_add.png':var1}"  id="one${vs1.index+1}" >
							<input type="hidden"  class="one${vs1.index+1} oneimage" value="${var1}" />
						</div>
					</li>
                </c:forEach>
			</ul>
		</li>
		<li>
			<span style="font-size:112.5%;">详情图</span>
			<span style="font-size:87.5%;" class="col-6">注：每张图片 支持jpg、png、jap等</span>
		</li>
		<li>
			<ul class="img_list_box clf">
				<c:forEach items="${two}" var="var2" varStatus="vs2">
					<li class="img_item">
						<div onclick="upload('two${vs2.index+1}')">
							<img src="${var2.twoimage eq ''?'img/sjht_add.png':var2.twoimage} " id="two${vs2.index+1}">
							<input type="hidden"  class="two${vs2.index+1} twoimage" value="${var2.twoimage}" />
						</div>
						<input type="text" class="dd_ipt twotext" placeholder="点击在此输入文字" value="${var2.twotext }" >
					</li>
				</c:forEach>
				 
			</ul>
		</li>
		<li style="text-align:center;">
			<c:if test="${storeqx.edit eq '1'}">
            	<span class="anniu-s" onclick="sureUp()">提交</span>
            </c:if>
		</li>
	</ul>
	</form>
	<form action="zhihui_city_file/uploanImage.do" method="post" name="imageForm" id="imageForm"  enctype="multipart/form-data"> 
	        <c:if test="${storeqx.edit eq '1'}">
	        	<input type="file" style="display:none;"   id="upload-file"  name="uploanImage" class="upload-file" onchange="fileType(this)"/>
	        </c:if>
	         <img src=""  id="before_image" style="display:none" />
	</form>
  </c:if>
       <script type="text/javascript">
    
     //------------------以上裁剪-------------------------------------------
    //提交
	function sureUp(){
    	//缩略图的内容
    	var address="";
    	$(".oneimage").each(function(){
    		if($(this).val() == ""){
    			address+=""+",";
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
    			address1image+=""+",";
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
			var file = obj.files[0];
			//alert(window.URL.createObjectURL(file));
	        if (window.FileReader) {
	            var reader = new FileReader();
	            reader.readAsDataURL(file);
	            //监听文件读取结束后事件
	            reader.onloadend = function (e) {
	                var n=validate_img(e.target.result,file.fileName);
	                if(n == 0){
	                	alert("宽必须小鱼100");
	                }else{
	                	$("#imageForm").ajaxSubmit({  
	        			  	url : '<%=basePath%>zhihui_city_file/uploadheadimageByStore.do?tm='+new Date().getTime(),
	        		        type: "POST",//提交类型  
	        		      	dataType:"json",
	        		      	resetForm:true,
	        		   		success:function(result){
 	        		   			 var url=result.url;
	        				     $("."+classId).val(url);
	        						 //替换展示图片
	        					 $("#"+classId).attr("src",url);
	        					 classId="";
	        					 //$("#imageForm").val("");
	        				}
	        			});  
	                }
	            };
	        }
  		}
 	}	 

	//判断图片是否符合格式
	function validaImage(filename){
		if('.gif.png.jpg.jpeg'.indexOf(filename)<0&&'.GIF.PNG.JPG.JPEG'.indexOf(filename)<0){
			return false;
		}
		
		return true;
	}
	
 //判断图片是否满足条件
	function  validate_img(source){
		var img = document.getElementById("before_image");
	    img.src = source;
	    return 1;
 	}
     
 

</script>
</body>
</html>