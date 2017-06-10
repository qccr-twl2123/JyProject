<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>档案管理</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/zhihui/dangan12.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/dangan12.js"></script>
         <script type="text/javascript" src="js/jquery.form.js"></script>
        <!--sdk-->
		<script src="static/sdk/strophe.js"></script>
		<script src="static/sdk/easemob.im-1.1.1.js"></script>
		<script src="static/sdk/easemob.im.shim.js"></script><!--兼容老版本(1.0.7含以前版本)sdk需引入此文件-->
 		<!--config-->
		<script src="static/js/easemob.im.config.js"></script>
    </head>
    
    <body>
    <c:if test="${qx.edit eq '1'}">
    	<form action="zhihuiz_store_file/${msg}.do" name="Form" id="Form" method="post" >
    		<input type="hidden" name="store_id" id="store_id" value="${pd.store_id}"/>
    		<input type="hidden" name="store_file_id" id="store_file_id" value="${pd.store_file_id}"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${pd.currentPage}"/>
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">商家名称</span>
	          <input type="text" class="dangan_d1_ipt1" name="store_name" id="store_name" value="${pd.store_name}" />
	          <span class="dangan_d1_sp1">商家工商执照全称</span>
	          <input type="text" class="dangan_d1_ipt1" name="business_licenses_name" id="business_licenses_name" value="${pd.business_licenses_name }"/>
	        </div>
 	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">省</span>
		           <select class="dangan_d1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
		             <c:if test="${msg eq 'save'}">
				 		 <option value="">请选择省</option>
				 	 </c:if>
				 	 <c:forEach items="${provincelist}" var="var">
						 <option value="${var.province_id}" ${pd.province_name eq var.province_name ?'selected':''}>${var.province_name}</option>
					 </c:forEach>
		           </select>
		          <span class="dangan_d1_sp1">市</span>
		          <select class="dangan_d1_st1" name="city_id" id="city_id" onchange="addsearcharea();">
		             <option value="${pd.city_id }">${pd.city_name}</option>
		           </select>
		          <span class="dangan_d1_sp1">区/县</span>
		          <select class="dangan_d1_st1" name="area_id" id="area_id" onchange="addSearchFirstCitySort()">
		             <option value="${pd.area_id }">${pd.area_name}</option>
		           </select>
	        </div>
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">详细地址</span>
	          <input type="text" class="dangan_d1_ipt1 ipt3" name="address" id="address" value="${pd.address }"/>
	        </div>
	        <div >
	          <span class="dangan_d1_sp1">经营项目</span>
	          <input type="text" class="dangan_d1_ipt1 ipt3 ipt4" name="management_projects_desc" id="management_projects_desc" value="${pd.management_projects_desc}"/>
	        </div>
 	        <div class="dangan_d1 ">
		          <span class="dangan_d1_sp1"></span>
		          <span class=" dangan_d1_sp2">行业大类</span>
		          <select class="dangan_d1_st1 firstsort" onchange="addSearchTwoCitySort(this,'1')">
		          		<option value="">全部</option>
						<c:forEach items="${sortlist}" var="var">
							<option value="${var.city_file_sort_id}" ${var.city_file_sort_id eq pd.city_sort_two_parent?'selected':''}>${var.sort_name}</option>
						</c:forEach>
		          </select>
		          <span class="dangan_d1_sp2">行业小类</span>
		          <select class="dangan_d1_st1 twosort" name="city_sort_two" id="city_sort_two">
		          		<option value="${pd.city_sort_two}">${pd.city_sort_two_name}</option>
		          </select>
		          <span class="dangan12_sp1"></span>
	        </div>
	        <div class="dangan_d1 mgtop">
		          <span class="dangan_d1_sp1"></span>
		          <span class=" dangan_d1_sp2">行业大类</span>
		          <select class="dangan_d1_st1 firstsort" onchange="addSearchTwoCitySort(this,'2')">
		          		<option value="">全部</option>
		             	<c:forEach items="${sortlist}" var="var">
							<option value="${var.city_file_sort_id}" ${var.city_file_sort_id eq pd.city_sort_three_parent?'selected':''}>${var.sort_name}</option>
						</c:forEach>
		          </select>
		          <span class="dangan_d1_sp2">行业小类</span>
		          <select class="dangan_d1_st1 twosort" name="city_sort_three" id="city_sort_three">
		          		<option value="${pd.city_sort_three}">${pd.city_sort_three_name}</option>
		          </select>
		          <span class="dangan12_sp1"></span>
	        </div>
	        <div class="dangan_d1 mgtop">
		          <span class="dangan_d1_sp1">商家行业分类入口</span>
		          <span class=" dangan_d1_sp2">行业大类</span>
		          <select class="dangan_d1_st1 firstsort" onchange="addSearchTwoCitySort(this,'3')" >
		          	<option value="">全部</option>
					<c:forEach items="${sortlist}" var="var">
						<option value="${var.city_file_sort_id}" ${var.city_file_sort_id eq pd.city_sort_one_parent?'selected':''}>${var.sort_name}</option>
					</c:forEach>
		          </select>
		          <span class="dangan_d1_sp2">行业小类</span>
		          <select class="dangan_d1_st1 twosort" name="city_sort_one" id="city_sort_one">
		          		<option value="${pd.city_sort_one}">${pd.city_sort_one_name}</option>
		          </select>
		          <span class="dangan12_sp1">默认服务商分类</span>
	        </div>
	        <div class="dangan_d1 ">
		          <span class="dangan_d1_sp1">服务商</span>
		           <select class="dangan_d1_st1" name="sp_file_id" id="sp_file_id" onchange="getClerk(this)" >
 		           		<option value="">请选择</option>
 		             	<c:forEach items="${splist}" var="var">
							<option value="${var.sp_file_id}" ${var.sp_file_id eq pd.sp_file_id?'selected':''}>${var.team_name}</option>
						</c:forEach>
		          </select>
		          <span class="dangan_d1_sp1">业务员</span>
		           <select class="dangan_d1_st1" name="clerk_file_id" id="clerk_file_id">
 		           			<option value="${pd.clerk_file_id}">${pd.clerk_file_name}</option>
 		          </select>
	        </div>
	        <div class="dangan_d1 ">
		          <span class="dangan_d1_sp1">商家负责人</span>
		          <input type="text" class="dangan_d1_ipt2" name="principal" id="principal" value="${pd.principal}"/>
		          <span class="dangan_d1_sp1">手机号码</span>
		          <input type="text" class="dangan_d1_ipt2" name="phone" id="phone" value="${pd.phone}"/>
	        </div>
	        <div class="dangan_d1 ">
		          <span class="dangan_d1_sp1">e-mail</span>
		          <input type="text" class="dangan_d1_ipt2" name="email" id="email" value="${pd.email }"/>
		          <span class="dangan_d1_sp1">商家联系方式</span>
		          <input type="text" class="dangan_d1_ipt2" name="store_phone" id="store_phone" value="${pd.store_phone }"/>
	        </div>
	        <div class="dangan_d1">
		          <span class="dangan_d1_sp1">商家状态</span>
		          <span class="dangan8_sp1">
		          <c:if test="${qx.edit eq '1'}">
		            <label><input type="radio"  name="zt" ${pd.open_status eq '1'?'checked':'' }  onclick="changeopen('1')" /><span>正常</span></label>
		          </c:if>
		          </span>
		          <span class="dangan8_sp1">
		          <c:if test="${qx.edit eq '1'}">
		            <label><input type="radio" name="zt"  ${pd.open_status eq '0'?'checked':'' } onclick="changeopen('0')"  /><span>停用</span></label>
		          </c:if>
		          </span>
		          <input type="text" name="open_status" id="open_status" value="${pd.open_status eq '1'?'1':'0'}" style="display:none;width:1px;height:1px;"/>
	        </div>
	        <div class="dangan_d1 mgtop">
	          	<span class="dangan_d1_sp1">商家ID号</span>
	          	<input type="text" class="dangan_d1_ipt1" name="user_number" id="user_number" value="${pd.user_number }" readonly="readonly"/>
	        	<span class="dangan_d1_sp1">提现费率</span>
	        	<input type="number" class="dangan_d1_ipt2" name="withdraw_rate" id="withdraw_rate" value="${pd.withdraw_rate}"/>%
	        </div>
	        <div class="dangan12_d1">
	          <div class="dangan12_d1_left" style="width:15%;">
	              <span class="dangan12_d1_left_sp1">工商营业执照</span>
	              <div class="dangan12_d1_left_img1">
	                <!-- <img src="img/zhizhao.png"> -->
	                <!-- 上传图片 -->
					<a style="margin:0 auto;display:block;" onclick="upload('business_licenses_image_one')">
					     <img src="${pd.business_licenses_image_one}" width="148px" height="172px" class="business_licenses_image_one" />
					</a>
					<input type="text" name="business_licenses_image_one" id="business_licenses_image_one" value="${pd.business_licenses_image_one}" style="display:none;width:1px;height:1px;" />
					<!-- 结束 -->
	              </div>
	              <div class="dangan12_d1_left_img1">
	               <!--  <img src="img/zhizhao2.png"> -->
	                <!-- 上传图片 -->
					<a style="margin:0 auto;display:block;" onclick="upload('business_licenses_image_two')">
					     <img src="${pd.business_licenses_image_two}" width="148px" height="172px" class="business_licenses_image_two"/>
					</a>
					<input type="text" name="business_licenses_image_two" id="business_licenses_image_two" value="${pd.business_licenses_image_two}" style="display:none;width:1px;height:1px;"/>
					<!-- 结束 -->
	              </div>
	          </div>
	          <div class="dangan12_d1_left">
	            <span class="dangan12_d1_left_sp1">经营许可证及其它证照</span>
	            <div class="dangan12_d1_left_img1">
 	              <!-- 上传图片 -->
					<a style="margin:0 auto;display:block;" onclick="upload('license_image_one')">
					     <img src="${pd.license_image_one}"  class="license_image_one" width="148px" height="172px" />
					</a>
					<input type="text" name="license_image_one" id="license_image_one" value="${pd.license_image_one}" style="display:none;width:1px;height:1px;" />
					<!-- 结束 -->
	            </div>
	            <div class="dangan12_d1_left_img1">
 	              <!-- 上传图片 -->
					<a style="margin:0 auto;display:block;" onclick="upload('license_image_two')">
					     <img src="${pd.license_image_two}" width="148px" height="172px" class="license_image_two" />
					</a>
					<input type="text" name="license_image_two" id="license_image_two" value="${pd.license_image_two}" style="display:none;width:1px;height:1px;" />
					<!-- 结束 -->
	            </div>
	          </div>
	          <div class="dangan12_d1_left">
	            <span class="dangan12_d1_left_sp1"></span>
	            <div class="dangan12_d1_left_img1">
 	              <!-- 上传图片 -->
					<a style="margin:0 auto;display:block;" onclick="upload('license_image_three')">
					     <img src="${pd.license_image_three}" width="148px" height="172px" class="license_image_three" />
					</a>
					<input type="text" name="license_image_three" id="license_image_three" value="${pd.license_image_three}" style="display:none;width:1px;height:1px;" />
				  <!-- 结束 -->
	            </div>
	            <div class="dangan12_d1_left_img1">
 	              <!-- 上传图片 -->
					<a style="margin:0 auto;display:block;" onclick="upload('license_image_four')">
					     <img src="${pd.license_image_four}" width="148px" height="172px" class="license_image_four" />
					</a>
					<input type="text" name="license_image_four" id="license_image_four" value="${pd.license_image_four}" style="display:none;width:1px;height:1px;"/>
				  <!-- 结束 -->
	            </div>
	          </div>
	          <div class="dangan12_d1_left" style="width:15%">
	            <span class="dangan12_d1_left_sp1"></span>
	            <div class="dangan12_d1_left_img1">
 	              <!-- 上传图片 -->
					<a style="margin:0 auto;display:block;" onclick="upload('license_image_fix')">
					     <img src="${pd.license_image_fix}" width="148px" height="172px" class="license_image_fix" />
					</a>
					<input type="text" name="license_image_fix" id="license_image_fix" value="${pd.license_image_fix}" style="display:none;width:1px;height:1px;" />
				  <!-- 结束 -->
	            </div>
	          </div>
 	        </div>
	        <div class="dangan_d1">
	             <label>  
					<input type="checkbox" class="dangan12_ipt1 w_checkbox" ${pd.earnest_status eq '1'?'checked':'' } onclick="changeStatus(this)" value="${pd.earnest_status eq '1'?'1':'0' }" id = "cheak1" />
 	              	<span class="dangan12_ipt1_sp1">保证金支付与退还</span>
	              	<input type="text" name="earnest_status" id="earnest_status" value="${pd.earnest_status eq '1'?'1':'0'}" style="display:none;width:1px;height:1px;"/>
	             </label>
	        </div>
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">保证金金额</span>
	          <input type="number" class="dangan_d1_ipt2" value="${pd.earnest_money}" name="earnest_money" id="earnest_money" onchange="ischecked(earnest_status,this)" />
	          <span class="dangan_d1_sp1">保证金状态</span>
	          <select class="dangan_d1_st1" name="em_statestatus" id="em_statestatus">
	          	<c:if test="${pd.em_statestatus eq '0'}">
	          		<option value="0">未支付</option>
	          	</c:if>
	          	<c:if test="${pd.em_statestatus eq '1'}">
	          		<option value="1">已支付</option>
	          	</c:if>
 	          </select>
	        </div>
	         <div class="dangan_d1">
	             <label>  
	                <input onchange="getServiceFee(this)" type="checkbox" class="dangan12_ipt1 w_checkbox"  ${pd.service_status eq '1'?'checked':'' } onclick="changeStatus(this)"  value="${pd.service_status eq '1'?'1':'0' }"  id= "cheak2"/>
	              	<span class="dangan12_ipt1_sp1">服务费</span>
	              	<input type="text" name="service_status" id="service_status" value="${pd.service_status eq '1'?'1':'0'}" style="display:none;width:1px;height:1px;" />
	             </label>
	        </div>
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">服务费金额</span>
	          <input type="number" class="dangan_d1_ipt2" name="system_service_fee" id="system_service_fee" value="${pd.system_service_fee}" onchange="ischecked(service_status,this)"/>
	          <span class="dangan_d1_sp1">服务费状态</span>
	          <select class="dangan_d1_st1" name="sf_statestatus" id="sf_statestatus">
	          	<c:if test="${pd.sf_statestatus eq '0'}">
	          		<option value="0">未支付</option>
	          	</c:if>
	          	<c:if test="${pd.sf_statestatus eq '1'}">
	          		<option value="1">已支付</option>
	          	</c:if>
 	          </select>
	        </div>
 	        <div class="dangan_d1">
	            <span class="dangan_d1_sp1">有效期</span>
	            <input type="date"   class="dangan_d1_st2" name="starttime" id="starttime" value="${pd.starttime}"  >
	 	        <span>&nbsp;&nbsp;&nbsp;&nbsp;至</span>
		        <input  type="date"  class="dangan_d1_st2" name="endtime" id="endtime" value="${pd.endtime}" >
	        </div>
	        <script type="text/javascript">
	        $(function(){ 
	    		var thedate = new Date(); 
	    		var datestr=thedate.getFullYear() + '-' +  (parseInt(thedate.getMonth())+1)  + '-' + thedate.getDate();
 	     		if("${pd.starttime}" == null || "${pd.starttime}" == ""){
 	     			$("#starttime").val("");
 	     			$("#starttime").val(datestr);
 	     		}
	        });
	        
	/*       //开始时间的比较
	    	function startbijiao(obj){
	    	    var n=$(obj).val().length;
	    		if(n < 10){
					return;
				}
	    		return;
	    		if($(obj).val() == null ||$(obj).val() == "" || $("#endtime").val()== ""){
	    			return;
	    		}
	    		
 	     	}
 	    	
	    	//结束时间的比较
	    	function endbijiao(obj){
				if($(obj).val().length < 10){
					return;
				}
	    		if($(obj).val() == null ||$(obj).val() == ""){
	    			return;
	    		}
	    		
	     	} */
	    	
	    	
	  
	    	
 	        </script>
	        <div class="dangan_d1">
	        <c:if test="${qx.add eq '1'}">
	          <span class="dangan_d1_btn1  mgleft11" onclick="save()">保存</span> 
	          </c:if>
	          <a class="middle_a" href="zhihuiz_store_file/list.do"  target="ifra"> 
	           <span class="dangan_d1_btn1">退出</span>
	          </a>  
  	           <c:if test="${pd.check_status eq '0' and pd.isshenhe eq '1' }">
	  	           <c:if test="${qx.edit eq '1'}">
	 	           			 <a class="middle_a" onclick="passOver();" target="ifra"> 
	 	           				<span class="dangan_d1_btn1">通过审核</span>
	 	           			 </a>
	 	           	</c:if>
 	           </c:if>
 	           <c:if test="${pd.check_status eq '1' }">
 	           			<span class="dangan_d1_btn1" style="background-color:#7B7A77;">审核通过</span>
 	           </c:if>
 	          </a>  
	        </div>
	        <input type="text" name="province_name" id="province_name" value="" style="display:none;width:1px;height:1px;"/>
	        <input type="text" name="city_name" id="city_name" value="" style="display:none;width:1px;height:1px;"/>
	        <input type="text" name="area_name" id="area_name" value="" style="display:none;width:1px;height:1px;"/>
         </form>
        <form action="zhihui_city_file/uploanImage.do" method="post" name="imageForm" id="imageForm"  enctype="multipart/form-data"> 
        <c:if test="${qx.add eq '1'}">
        	<input type="file" style="width:1px;display:none;"    name="uploanImage" class="uploanImage" onchange="fileType(this)"/>
        </c:if>
        </form>
        </c:if>
     <script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框-->
     <script type="text/javascript">
     
     //判断保证金/服务费是否为选中状态
     function ischecked(obj2,obj){
    	 if($(obj2).val() == "1"){
    		 
      	 }else{
    		 alert("请先选中该选项");
    		 $(obj).val("");
    		 //$(obj2).attr("checked","checked");
    	 }
     }
     
     
     
     //修改选中状态
     function changeStatus(obj){
    	 var value=$(obj).val();
    	 if(value == "0"){
    		 value="1";
    		 $(obj).val(value);
    	 }else{
    		 value="0";
    		 $(obj).val(value);
    	 }
    	 $(obj).next().next().show();
    	 $(obj).next().next().val(value);
    	 $(obj).next().next().hide();
     }
     
     //修改用户开启状态
     function changeopen(value){
    	 $("#open_status").show();
    	 $("#open_status").val(value);
    	 $("#open_status").hide();
     }
     
      
   //获取城市
		function addsearchfind(){
			var str=$("#province_id option:selected").val();//获取被选中的value值
 			$.ajax({
				  url: '<%=path%>/zhihui_city_file/citylist.do',
				  data:"province_id="+str,
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.citylist;
					  	$("#city_id option").remove();
					  	$("#area_id option").remove();
					  	if(list.length>0){
					  		$("#city_id").append("<option value=''>请选择市</option>");
						  	$("#area_id").append("<option  value=''>请选择区</option>");
						  	for(var i=0;i<list.length;i++){
						  		$("#city_id").append("<option value='"+list[i].city_id+"'>"+list[i].city_name+"</option>");
						  	}
					  	}
				  },
				  error:function(a){
				  	alert("异常");
				  }
			});
		}
			
		//获取区域
		function addsearcharea(){
			var str1=$("#province_id option:selected").val();//获取被选中的value值
			var str2=$("#city_id option:selected").val();//获取被选中的value值
			$.ajax({
				  url: '<%=path%>/zhihui_city_file/arealist.do',
				  data:{"province_id":str1,"city_id":str2},
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.arealist;
					  	$("#area_id option").remove();
					  	$("#area_id").append("<option  value=''>请选择区</option>");
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$("#area_id").append("<option value='"+list[i].area_id+"'>"+list[i].area_name+"</option>");
						  	}
				  		}  	
				  },
				  error:function(a){
				  alert("异常");
				  }
			});
		}
		
		
		//获取城市的一级分类
		function addSearchFirstCitySort(){
			var str1=$("#province_id option:selected").val();//获取被选中的value值
			var str2=$("#city_id option:selected").val();//获取被选中的value值
			var str3=$("#area_id option:selected").val();//获取被选中的value值
			$.ajax({
				  url: '<%=path%>/zhihui_city_file/citySortList.do',
				  data:{"province_id":str1,"city_id":str2,"area_id":str3,"sort_parent_id":"0","sort_type":"1"},
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.sortlist;
					  	$(".firstsort option").remove();
					  	$(".twosort option").remove();
					  	$(".firstsort").append("<option  value=''>请选择1级分类</option>");
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$(".firstsort").append("<option value='"+list[i].city_file_sort_id+"'>"+list[i].sort_name+"</option>");
						  	}
				  		}  	
				  },
				  error:function(a){
				  alert("异常");
				  }
			});
		}
		
		
		//获取城市的二级分类
		function addSearchTwoCitySort(obj,number_w){
			var str1=$("#province_id option:selected").val();//获取被选中的value值
			var str2=$("#city_id option:selected").val();//获取被选中的value值
			var str3=$("#area_id option:selected").val();//获取被选中的value值
			var str4=$(obj).val();//获取被选中的value值
			$.ajax({
				  url: '<%=path%>/zhihui_city_file/citySortList.do',
				  data:{"province_id":str1,"city_id":str2,"area_id":str3,"sort_parent_id":str4,"city_sort_one_parent":str4,"sort_type":"2","sp_status":"1"},
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.sortlist;
					  	$(obj).next().next().empty();
					  	$(obj).next().next().append("<option  value=''>请选择2级分类</option>");
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$(obj).next().next().append("<option value='"+list[i].city_file_sort_id+"'>"+list[i].sort_name+"</option>");
						  	}
				  		}  	
					  	if(number_w =="3"){
					  		var splist=data.splist;
						  	$("#sp_file_id").empty();
 						  	$("#sp_file_id").append("<option  value=''>请选择服务商</option>");
						  	$("#clerk_file_id").empty();
						  	$("#clerk_file_id").append("<option  value=''>请选择服务商</option>");
						  	if(splist.length>0){
							  	for(var i=0;i<splist.length;i++){
							  		$("#sp_file_id").append("<option value='"+splist[i].sp_file_id+"'>"+splist[i].team_name+"</option>");
							  	}
					  		} 
					  	}
 				  } 
			});
		}
		
		
		//获取业务员
		function getClerk(obj){
			if($(obj).val() == ""){
				alert("请先选择服务商");
				return;
			}
  			$.ajax({
				  url: '<%=path%>/zhihuiz_store_file/getClerkList.do',
				  data:{"sp_file_id":$(obj).val()},
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.clerklist;
					  	$("#clerk_file_id").empty();
					  	$("#clerk_file_id").append("<option  value=''>请选择业务员</option>");
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$("#clerk_file_id").append("<option value='"+list[i].clerk_file_id+"'>"+list[i].clerk_name+"</option>");
						  	}
				  		}  	
				  } 
			});
		}
		
		
		
		
		var classId="";//class的唯一标示
 		
		//上传按钮点击
		function upload(value){
			classId=value;
			$(".uploanImage").click();
		}
		
		//上传图片
		function fileType(obj){
			var d=/\.[^\.]+$/.exec(obj.value); 
			if(!validaImage(d)){
				alert("请上传照片gif,png,jpg,jpeg格式");
			}else{
				$("#imageForm").ajaxSubmit({  
				  	url : '<%=basePath%>zhihui_city_file/uploadheadimage.do',
			        type: "POST",//提交类型  
			      	dataType:"json",
			   		success:function(result){
			   			 var url=result.url;
			   			 $("#"+classId).show();
						 $("#"+classId).val(url);
						 $("#"+classId).hide();
						 //替换展示图片
						 $("."+classId).attr("src",url);
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
		
		
		
		//保存
		function save(){
			
			if($("#store_name").val()==""){
				$("#store_name").tips({
					side:3,
		            msg:'请输入商家名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_name").focus();
				return false;
			}
			if($("#store_abbreviation_name").val()==""){
				$("#store_abbreviation_name").tips({
					side:3,
		            msg:'请输入商家简称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_abbreviation_name").focus();
				return false;
			}
			if($("#business_licenses_name").val()==""){
				$("#business_licenses_name").tips({
					side:3,
		            msg:'请输入商家工商执照全程',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#business_licenses_name").focus();
				return false;
			}
			/* if($("#province_id").val()==""){
				$("#province_id").tips({
					side:3,
		            msg:'请输入省 ',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#province_id").focus();
				return false;
			}
			if($("#city_id").val()==""){
				$("#city_id").tips({
					side:3,
		            msg:'请输入市 ',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#city_id").focus();
				return false;
			}
			if($("#area_id").val()==""){
				$("#area_id").tips({
					side:3,
		            msg:'请输入区 ',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#area_id").focus();
				return false;
			}  */
			if($("#principal").val()==""){
				$("#principal").tips({
					side:3,
		            msg:'请输入负责人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#principal").focus();
				return false;
			}
			if($("#phone").val()==""){
				$("#phone").tips({
					side:3,
		            msg:'请输入负责人手机号码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#phone").focus();
				return false;
			}
 			if($("#store_phone").val()==""){
				$("#store_phone").tips({
					side:3,
		            msg:'请输入商家联系电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_phone").focus();
				return false;
			}
 			if($("#starttime").val()==""){
				$("#starttime").tips({
					side:3,
		            msg:'请输入服务费开始有效时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#starttime").focus();
				return false;
			}else{
				var thedate = new Date(); 
	    		var datestr=thedate.getFullYear() + '-' +(parseInt(thedate.getMonth())+1) + '-' + thedate.getDate();
	    		if("${pd.starttime}" == null || "${pd.starttime}"  == ""){
	    			if((new Date(datestr.replace(/-/g,"\/"))) >(new Date($("#starttime").val().replace(/-/g,"\/")))){
		    			alert("时间不能选择当前时间"+datestr+"之前");
	 	    			return;
		    		}
	    		}
  	    		if((new Date($("#endtime").val().replace(/-/g,"\/"))) <(new Date($("#starttime").val().replace(/-/g,"\/")))){
	    			alert("开始时间不能超过结束时间"+$("#endtime").val());
 	    			return;
	    		}
			}
			if($("#endtime").val()==""){
				$("#endtime").tips({
					side:3,
		            msg:'请输入服务费结束有效时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#endtime").focus();
				return false;
			}else{
				if((new Date($("#starttime").val().replace(/-/g,"\/"))) > (new Date($("#endtime").val().replace(/-/g,"\/")))){
	    			alert("结束日期不能早于开始日期"+$("#starttime").val());
	    			return;
 	    		}
	    		//不能晚于开始时间的两年后
	    		var overday=addmulMonth($("#starttime").val(),24);
	    		//alert(overday);
	    		if((new Date(overday.replace(/-/g,"\/"))) < (new Date($("#endtime").val().replace(/-/g,"\/")))){
	    			alert("时间应早于开始日期二年后的时间:"+overday);
	    			return;
	    		}
			}
 			
 			if($("#sp_file_id").val()==""){
				$("#sp_file_id").tips({
					side:3,
		            msg:'请选择服务商',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sp_file_id").focus();
				return false;
			}  
			if($("#clerk_file_id").val()==""){
				$("#clerk_file_id").tips({
					side:3,
		            msg:'请选择业务员',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#clerk_file_id").focus();
				return false;
			}  
			//保证金或服务费必须填写
 			var w_n="0";
			$(".w_checkbox").each(function(){
				if($(this).is(":checked")){
					w_n="1";
				}
			});
			if(w_n == "0"){
				alert("保证金或服务费必须填写");
				return;
			}
 			//城市名称统计
			if($("#city_id option:selected").val() != ""){
				var city_name=$("#city_id option:selected").text();
				$("#city_name").show();
				$("#city_name").val(city_name);
			}
			if($("#province_id option:selected").val() != ""){
				var province_name=$("#province_id option:selected").text();
				$("#province_name").show();
				$("#province_name").val(province_name);
			}
			if($("#area_id option:selected").val() != ""){
				var area_name=$("#area_id option:selected").text();
				$("#area_name").show();
				$("#area_name").val(area_name);
			}
 			 $("#Form").submit();//提交 
		}
		
		//服务费的价格
		function getServiceFee(obj){
			var city_file_sort_id=$("#city_sort_one").val();
			var merchant_level=$("#merchant_level").text();
			if($(obj).is(':checked')){//判断该服务费状态是否被选中
				//获取服务费
				$.ajax({
					  url: '<%=path%>/zhihui_city_file/getServiceFee.do',
					  data:{"city_file_sort_id":city_file_sort_id,"merchant_level":merchant_level},
					  type:"post",
					  dataType:"json",
					  success:function(data){
						  if(data.result == "01"){
							  	var serviceFee=data.serviceFee;
								$("#system_service_fee").val(serviceFee);
								//$("#system_service_fee").attr("readonly","readonly");
						  }
 					  }
				});
					
			}
 		}
		
		
		
		//通过审核
		function passOver(){
			//在没有上传营业执照，没有勾选保证金或服务费的情况下，且没有填写有效日期的情况下，是不允许审核通过的
			var img1 = $("#business_licenses_image_one").val();
			var img2 = $("#license_image_one").val();
			var img3 = $("#license_image_two").val();
			var img4 = $("#license_image_three").val();
			var img5 = $("#license_image_four").val();
			var img6 = $("#license_image_fix").val();
			if(img1 == null || img1 == ""){
				alert("商家工商营业执照不能为空！");
				return ;
			}
			if(img2 == null && img3 == null && img4 == null && img5 == null && img6 == null){
				alert("商家经营许可证及其它证照不能为空！");
				return ;
			}
			if($("#cheak1").is(':checked')==false){
				   alert("保证金支付与退还未选中！");
				   return ;
			 }
			 if($("#cheak2").is(':checked')==false){
				   alert("服务费未选中！");
				   return ;
			 }
			var starttime = $("#starttime").val();
			var endtime = $("#endtime").val();
			if(starttime == "" || starttime == null){
				alert("开始时间不能为空！");
				return ;
			}
			if(endtime == "" || endtime == null ){
				alert("结束时间不能为空！");
				return ;
			}
			//先注册环信
 			var options = {
				username : "${pd.store_id}",
				password : "${pd.store_id}",
				nickname : "${pd.store_name}",
				appKey : Easemob.im.config.appkey,
				success : function(result) {
					alert("成功通过审核!");
 				},
				error : function(e) {
					//alert(e.error);
					alert("环信注册失败！请联系管理员");
				},
				apiUrl : Easemob.im.config.apiURL
			};
			Easemob.im.Helper.registerUser(options);
			var withdraw_rate = $("#withdraw_rate").val();
			//通过审核
			window.location.href="zhihuiz_store_file/checkedOk.do?store_id=${pd.store_id}&store_file_id=${pd.store_file_id}&currentPage=${pd.currentPage}&check_status=1&withdraw_rate="+withdraw_rate;
 		}
		
		
		 //   n个月后
    	function addmulMonth(dtstr, n)
    	{      
    	    var s = dtstr.split("-");
    	    var yy = parseInt(s[0]);
    	    var mm = parseInt(s[1]) - 1; 
    	    var dd = parseInt(s[2]); 
    	    var dt = new Date(yy, mm, dd); 
    	    dt.setMonth(dt.getMonth() + n);
    	    if ((dt.getYear() * 12 + dt.getMonth()) > (yy * 12 + mm + n)) {
    	        dt = new Date(dt.getYear(), dt.getMonth(), 0); 
    	    }
    	    return dt.getFullYear() + "-" + dt.getMonth() + "-" + dt.getDay();
    	}   
     
     </script>
    </body>
</html> --%>