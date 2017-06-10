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
 	<title>商家信息</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/jcxx_sjxx.css">
 </head>
<body>
	<c:if test="${storeqx.look eq '1'}">
	<form action="storepc_StoreManageController/editStore.do" method="post" name="Form" id="Form">
	<input type="hidden" name="store_id" value="${pd.store_id }">
	<ul style="line-height:2.5; padding-left:20px;">
		<li style="position: relative;">
			<span class="col-red" style="vertical-align: top;">*</span>
			<span class="inp-s" style="text-align-last:justify;vertical-align: top;">上传头像</span>
			<span style="vertical-align: top;">：</span>
			<span style="display:inline-block;border:1px solid #999;width: 100px;height: 100px;">
				<a style="display:inline-block;width:100px;padding-top:100%;position: relative;" onclick="upload('pictrue_url')">
					<img  class="pictrue_url" src="${pd.pictrue_url eq ''?'img/sjht_add.png':pd.pictrue_url}"  style="position:absolute;top:0;width:100%;height: 100%;">	
					<input type="text" name="pictrue_url" id="pictrue_url" value="${pd.pictrue_url}" style="display:none;width:1px;height:1px;"/>
				</a>
			</span>
			<span style="color:red;font-size: 12px">注：替换后即马上生效(图片比例：1:1，否则头像显示会变形)</span>
		</li>
		<li >
			<span class="col-red">*</span>
			<span class="inp-s" style="text-align-last:justify;">联系电话</span>：
			<input type="text" class="inp-m" maxlength="11" name="phone_bymemeber" value="${pd.phone_bymemeber}">
			<span class="col-6" style="font-size:87.5%">&nbsp;注：该手机号码为门店服务号码，将展示给会员。</span>
		</li>
		<li>
			<span class="dq-top clf"><span class="col-red" style="vertical-align: top;">*</span>详细地址：</span>
			<input type="text" name="address" value="${pd.address}">
			<span>楼层：</span>
			<input type="text" class="inp-s" name="store_storey" value="${pd.store_storey}">
			<span class="col-red">关键字：</span>
			<input type="text" class="inp-m"  name="keyword" value="${pd.keyword}">
		</li>
		<li>
			<span class="dq-top clf"><span class="col-red" style="vertical-align: top;">*</span>经营范围：</span>
			<textarea name="management_projects_desc" id="management_projects_desc" cols="30" rows="4">${pd.management_projects_desc }</textarea>
		</li>
		<li>
			
			<span class="dq-top clf"><span class="col-red" style="vertical-align: top;">*</span>商家介绍：</span>
			<textarea name="store_introduce" id="store_introduce" cols="30" rows="5">${pd.store_introduce }</textarea>
		</li>
		<li>
			<span class="dq-top clf">&nbsp;&nbsp;公告信息：</span>
			<textarea name="notice_information"  id="notice_information" cols="30" rows="5">${pd.notice_information }</textarea>
		</li>
		<li>
			<span>&nbsp;&nbsp;官方地址：</span>
			<input type="text"  name="website_address" value="${pd.website_address }" >

		</li>
		<li>
			<ul>
				<li style="padding-left:8px;">
					<span>营业执照：</span>
				</li>
				<li class="zhizhao">
					<c:if test="${!empty pd.business_licenses_image_one}">
						<img src="${pd.business_licenses_image_one}"   class="business_licenses_image_one"/>
 					</c:if>
 				</li>
				<li style="padding-left:8px;">
					<span>营业许可证：</span>
				</li>
				<li class="zhizhao">
					<c:if test="${!empty pd.license_image_one}">
  						     <img src="${pd.license_image_one}"   class="license_image_one"/>
  					</c:if>
					<c:if test="${!empty pd.license_image_two}">
 							<img src="${pd.license_image_two}"    class="license_image_two"/>
  					</c:if>
					<c:if test="${!empty pd.license_image_three}">
 						    <img src="${pd.license_image_three}"    class="license_image_three"/>
  					</c:if>
					<c:if test="${!empty pd.license_image_four}">
  							 <img src="${pd.license_image_four}"   class="license_image_four"/>
  					</c:if>
					<c:if test="${!empty pd.license_image_fix}">
  						     <img src="${pd.license_image_fix}"  class="license_image_fix"/>
  					</c:if>
				</li>
			</ul>
 		</li>
		<c:if test="${storeqx.edit eq '1'}">
			<li style="text-align:center">
				<span class="anniu-s" onclick="subm()">提交</span>
			</li>
		</c:if>
	</ul>
	</form>
	 <form action="storeapp_operator/editImgae_url.do" method="post" name="imageForm" id="imageForm"  enctype="multipart/form-data"> 
         	<input type="file" style="width:1px;display:none;"    name="image_url" class="image_url" onchange="fileType(this)"/>
         	<input type="hidden" value="${storepd.store_id }"   name="store_id"  />
     </form>
	</c:if>
	<script src="js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script type="text/javascript">
		function subm() {
			$("#Form").submit();
		}
		
	var classId="";//class的唯一标示
 	//上传按钮点击
	function upload(value){
		classId=value;
		$(".image_url").click();
	}

   //上传图片
	function fileType(obj){
  	    var d=/\.[^\.]+$/.exec(obj.value); 
  		if(!validaImage(d)){
			alert("请上传照片gif,png,jpg,jpeg格式");
		}else{
			$("#imageForm").ajaxSubmit({  
			  	url : 'storeapp_operator/editImgae_url.do?tm='+new Date().getTime(),
		        type: "POST",//提交类型  
		      	dataType:"json",
		      	cache:false,
		      	ifModified :true,
		   		success:function(data){
		   			 var url=data.data;
 				     $("#"+classId).val("");
    				 $("."+classId).attr("src","");
 				     $("#"+classId).val(url);
    				 $("."+classId).attr("src",url+"?timestamp=" + new Date().getTime() );
					 classId="";   
					 //window.location.reload();
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