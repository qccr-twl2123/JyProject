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
        <title>档案管理</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/zhihui/dangan12.css">
 		<style type="text/css">
			 #fist{float: left;	margin: 0 auto;text-align: center;height:240px;margin-right:10px;width:350px;}
	    	 #output{float: left;  text-align: center;height:220px;    margin-left: 70px;width:200px;}
		</style>
     </head>
    
    <body>
    <c:if test="${qx.edit eq '1'}">
    	<form action="zhihuiz_store_file/${msg}.do" name="Form" id="Form" method="post" >
    		<input type="hidden" name="store_id" id="store_id" value="${pd.store_id}"/>
    		<input type="hidden" name="store_file_id" id="store_file_id" value="${pd.store_file_id}"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${beforpd.currentPage}"/>
			<input type="hidden" name="ok" id="ok" value="${beforpd.ok}"/>
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">商家名称</span>
	          <input type="text" class="dangan_d1_ipt1" name="store_name" id="store_name" value="${pd.store_name}" />
	          <span class="dangan_d1_sp1">商家工商执照全称</span>
	          <input type="text" class="dangan_d1_ipt1" name="business_licenses_name" id="business_licenses_name" value="${pd.business_licenses_name }"/>
	        </div>
	         <div class="dangan_d1"  style=" height: 33px; ">
  		          <span class="dangan_d1_sp1">
	 		          <c:if test="${pd.check_status eq  '0'}">
		        	   	<input type="checkbox"  onclick="isopen_points_change(this)" ${pd.isopen_points eq '1'?'checked':''}  >
	 	        	  </c:if>
	 	        	  <span id="koudian">
	 	        	  	交易扣点
 	 	        	  </span>
	 	        	  <input type="hidden" name="isopen_points" id="isopen_points" value="${pd.isopen_points}"  />
	 	          </span>
		          <span class="dangan8_sp1">
 		          		 <input type="radio"  name="tra" ${pd.transaction_points eq '1'?'checked':'' } onclick="transchange('1')"  /><span>1%</span> 
 		          </span>
		          <span class="dangan8_sp1">
 		          		 <input type="radio"  name="tra" ${pd.transaction_points eq '2'?'checked':'' } onclick="transchange('2')"  /><span>2%</span> 
 		          </span>
		          <span class="dangan8_sp1">
 		          		 <input type="radio"  name="tra" ${pd.transaction_points eq '3.5'?'checked':'' } onclick="transchange('3.5')"/><span>3.5%</span> 
 		          </span>
		          <span class="dangan8_sp1">
 		          		 <input type="radio" name="tra"  ${pd.transaction_points eq '5'?'checked':'' } onclick="transchange('5')" /><span>5%</span> 
 		          </span>
		          <input type="hidden" name="transaction_points" id="transaction_points" value="${pd.transaction_points}"  />
 	        	  <span style="color:red;">
	 	        	  <c:if test="${pd.isopen_points eq '0'}"><span style="color:red;">（未开通）</span></c:if>
		 	          <c:if test="${pd.isopen_points eq '1'}"></c:if>
 	        	  		注：扣点比率由代理商家审核时选择。
 	        	  </span>
	        </div>
   	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">省</span>
		           <select class="dangan_d1_st1" name="province_id" id="province_id" >
		             <option value="${pd.province_id}" >${pd.province_name}</option>
		           </select>
		          <span class="dangan_d1_sp1">市</span>
		          <select class="dangan_d1_st1" name="city_id" id="city_id"  >
		             <option value="${pd.city_id }">${pd.city_name}</option>
		           </select>
		          <span class="dangan_d1_sp1">区/县</span>
		          <select class="dangan_d1_st1" name="area_id" id="area_id"  >
		             <option value="${pd.area_id }">${pd.area_name}</option>
		           </select>
	        </div>
 	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">详细地址</span>
	          <input type="text" class="dangan_d1_ipt1 ipt3" name="address" id="address" value="${pd.address }"/>
 	          <span class="dangan_d1_btn1" onclick="dingwei('${pd.store_id}','${pd.province_name}${pd.city_name}${pd.area_name}${pd.address}','${pd.longitude}','${pd.latitude}')">重新定位</span>
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
		          <span class="dangan_d1_sp1">负责人手机号码</span>
		          <input type="text" class="dangan_d1_ipt2" name="phone" id="phone" value="${pd.phone}"/>
 	        </div>
	        <div class="dangan_d1 ">
		          <span class="dangan_d1_sp1">e-mail</span>
		          <input type="text" class="dangan_d1_ipt2" name="email" id="email" value="${pd.email }"/>
		          <span class="dangan_d1_sp1">商家联系方式</span>
		          <input type="text" class="dangan_d1_ipt2" name="phone_bymemeber" id="phone_bymemeber" value="${pd.phone_bymemeber}"/> 
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
		          <input type="hidden" name="open_status" id="open_status" value="${pd.open_status eq '1'?'1':'0'}"  />
	        </div>
	        <div class="dangan_d1 mgtop">
	          	<span class="dangan_d1_sp1">商家ID号</span>
	          	<input type="text" class="dangan_d1_ipt1" name="user_number" id="user_number" value="${pd.user_number }" readonly="readonly"/>
	        	<span class="dangan_d1_sp1">提现费率</span>
	        	<input type="text"  disabled="disabled" class="dangan_d1_ipt2" name="withdraw_rate" id="withdraw_rate" value="${pd.withdraw_rate}"/>%
	        </div>
	        <div class="dangan12_d1" style=" width: 96%; ">
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
  	        <div style="width:100%; margin-top: 24px;">
	            <span style="text-align: center;width:100%; display: inline-block;">桌号二维码</span>
 	        </div>
  	        <div style="width:100%;margin-top: 24px;">
 	            <c:forEach items="${deskList}" var="var" varStatus="vs">
		            <div id="fist">
						<span>
							桌号：
							<input name="desk_no" id="desk_no" value="${var.table_name}" readonly="readonly"/>
							<input type="button" value="产生二维码" onclick="create(this)"  />
						</span><br/>
						<div id="output"></div>
					</div>
 	            </c:forEach>
 	        </div>
	        <div style="width:100%;">
	            <span style="text-align: center;width:100%; display: inline-block;">商家推广二维码</span>
 	        </div>
 	        <div style="width:100%;margin-bottom: 24px;">
	            <div style="width:200px;margin: 0 auto;">
	            	<a target="_bank" href="<%=basePath%>storepcOperator_file/downPic.do?image_url=https://www.jiuyuvip.com/FileSave/File/storeErFile/${pd.store_id}.png"> 
							<img class="storetuiguan" src="https://www.jiuyuvip.com/FileSave/File/storeErFile/${pd.store_id}.png"   style=" width: 192px; height: 210px; " > 
					</a>
	            </div>
 	        </div>
 	        <c:if test="${pd.isopen_points eq '0'}">
	 	        <div class="dangan_d1">
		          <span class="dangan_d1_sp1">已购买服务时段</span>
		          <c:if test="${pd.biaozhun_stauts eq '0' }"><span style="color:red;">服务费未支付</span></c:if>
		          <c:if test="${pd.biaozhun_stauts eq '1' }"><span style="color:blue;">服务费已支付</span></c:if>
		          <span style="text-align: left;margin-top: 10px;width:100%; display: inline-block;margin-left: 145px;font-size: 16px">${pd.biaozhun_content}</span>
	 	        </div>
 	        </c:if>
 	        <div class="dangan_d1">
	        	<c:if test="${qx.edit eq '1'}">
	          		<span class="dangan_d1_btn1  mgleft11" onclick="save()">保存</span> 
	          	</c:if>
	          	<a class="middle_a" onclick="return_url()"  target="ifra"> 
	           		<span class="dangan_d1_btn1">退出</span>
	          	</a>  
  	           	<c:if test="${pd.check_status eq '0' and qx.add eq '1'  }">
 	 	           			 <a class="middle_a" onclick="passOver();" target="ifra"> 
	 	           				<span class="dangan_d1_btn1">通过审核</span>
	 	           			 </a>
  	           	</c:if>
 	           	<c:if test="${pd.check_status eq '1' }">
 	           			<span class="dangan_d1_btn1" style="background-color:#7B7A77;">审核通过</span>
 	           	</c:if>
 	        </div>
          </form>
        <form action="zhihui_city_file/uploanImage.do" method="post" name="imageForm" id="imageForm"  enctype="multipart/form-data"> 
        <c:if test="${qx.add eq '1'}">
        	<input type="file" style="width:1px;display:none;"    name="uploanImage" class="uploanImage" onchange="fileType(this)"/>
        </c:if>
        </form>
        </c:if>
        <script src="js/jquery-1.8.0.min.js"></script>
       <script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框-->
        <script type="text/javascript" src="js/jquery.form.js"></script>
 		<script type='text/javascript' src='static/js/webim.config.js'></script> 
		<script type='text/javascript' src='static/sdk/strophe-1.2.8.min.js'></script> 
		<script type='text/javascript' src='static/sdk/websdk-1.4.10.js'></script>
      <script type="text/javascript">
      $(".storetuiguan").attr("src",$(".storetuiguan").attr("src")+"?tm="+new Date().getTime());
      
      //退出
      function return_url(){
    	  if("${beforpd.ok}" == "0"){
    		  window.location.href="zhihuiz_store_file/notlist.do?province_id=${beforpd.province_id}&province_name=${beforpd.province_name}"+
    				  					"&city_id=${beforpd.city_id}&city_name=${beforpd.city_name}&area_id=${beforpd.area_id}&area_name=${beforpd.area_name}"+
    				      						"&content=${beforpd.content}&currentPage=${beforpd.currentPage}";
    	  }else{
    		  window.location.href="zhihuiz_store_file/list.do?province_id=${beforpd.province_id}&province_name=${beforpd.province_name}"+
				"&city_id=${beforpd.city_id}&city_name=${beforpd.city_name}&area_id=${beforpd.area_id}&area_name=${beforpd.area_name}"+
						"&content=${beforpd.content}&currentPage=${beforpd.currentPage}";

    	  }
      }
      
      //修改是否选择交易扣点
      function isopen_points_change(obj){
      	if($(obj).is(":checked")){
      		$("#isopen_points").val("1");
      	}else{
      		$("#isopen_points").val("0");
      	}
      }
      
       //交易扣点的点数
       function transchange(value){
     		  $("#transaction_points").val(value);
        }
      
     
   //生成二维码
      function create(obj){
      	var value=$(obj).prev().val();
      	if(value == ""){
      		alert("桌号不能为空");
       		return;
      	}
      	$(obj).parent().next().next().empty();//清空
   			$.ajax({
  			url:'<%=basePath%>zhihuiz_store_file/isdesk_no.do',
  			type:"post",
  			dataType:"json",
  			data:{"store_id":"${pd.store_id}","desk_no":value},
  			success:function(data){
  				 if(data.result == "0"){
  					 alert(data.message);
  					 $(obj).prev().val("");
  					 return;
  				 }else{
  				 	var url=data.data;
					 var str="<a target='_bank' href='<%=basePath%>storepcOperator_file/downPic.do?image_url="+url+"'>"+ 
							 "<img src='"+url+"' style=' width: 155px; height: 210px; '>"+
						      "</a>";
					  $(obj).parent().next().next().append(str);
  				 }
  			}
  		});
       }
   
    
     
	     //修改用户开启状态
	     function changeopen(value){
	     	 $("#open_status").val(value);
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
		
		//如果已经选好服务商
		if("${pd.sp_file_id}" != ""){
			getClerk($("#sp_file_id"));
			$("#sp_file_id").val("${pd.sp_file_id}");
		}
		
		//获取业务员
		function getClerk(obj){
			if($(obj).val() == ""){
				alert("请先选择服务商");
				return;
			}
  			$.ajax({
				  url: 'zhihuiz_store_file/getClerkList.do',
				  data:{"sp_file_id":$(obj).val()},
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.clerklist;
					  	$("#clerk_file_id").empty();
					  	$("#clerk_file_id").append("<option  value=''>请选择业务员</option>");
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		if("${pd.clerk_file_id}" == list[i].clerk_file_id){
						  			$("#clerk_file_id").append("<option value='"+list[i].clerk_file_id+"' selected='selected'>"+list[i].clerk_name+"</option>");
						  		}else{
						  			$("#clerk_file_id").append("<option value='"+list[i].clerk_file_id+"'>"+list[i].clerk_name+"</option>");
						  		}
						  		
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
				  	url : '<%=basePath%>zhihuiz_store_file/uploadheadimageByZhiZhao.do?tm='+new Date().getTime(),
			        type: "POST",//提交类型  
			      	dataType:"json",
			      	resetForm:true,
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
			//判断是否选择交易扣点的方式
			if($("#isopen_points").val() == "1"){
				if($("#transaction_points").val() == "0"){
 					alert("请选择交易扣点类型");
 					window.location.hash = "#koudian";
 					return;
				}
			}else{
				$("#transaction_points").val("0"); 
			}
			
 			if($("#store_name").val().trim() ==""){
 				    alert("请输入商家名称");
					window.location.hash = "#store_name";
					return;
 			}
			if($("#business_licenses_name").val().trim() ==""){
				alert("请输入商家工商执照全程");
				window.location.hash = "#business_licenses_name";
				return;
 			}
			if($("#principal").val().trim() ==""){
				alert("请输入负责人");
				window.location.hash = "#principal";
				return;
 			}
			if($("#phone").val().trim() ==""){
				alert("请输入负责人手机号码");
				window.location.hash = "#phone";
				return;
 			}
 			if($("#phone_bymemeber").val().trim() ==""){
 				alert("请输入商家联系电话");
				window.location.hash = "#phone_bymemeber";
				return;
 			}
 			 
 			if($("#sp_file_id").val().trim() ==""){
 				alert("请选择服务商");
				window.location.hash = "#sp_file_id";
				return;
			}  
			if($("#clerk_file_id").val().trim() ==""){
 				alert("请选择服务商");
				window.location.hash = "#clerk_file_id";
				return;
			}  
 			
			//表单提交
			$("#Form").ajaxSubmit({  
			  	url : 'zhihuiz_store_file/${msg}.do',
		        type: "post",//提交类型  
		      	dataType:"json",
		      	resetForm:true,
		   		success:function(result){
		   			  window.location.reload();
 				}
			});
 		}
		
		

		   var conn = new WebIM.connection({
	    	    https: WebIM.config.https,
	    	    url: WebIM.config.xmppURL,
	    	    isAutoLogin: WebIM.config.isAutoLogin,
	    	    isMultiLoginSessions: WebIM.config.isMultiLoginSessions
	    	});
	       
	       
	       // 添加好友
	      function addFriends() {
	    	   //登录环信
 		       if("${logininfor.login_type}" != "0"){
		    	   var options = { 
		     			  apiUrl: WebIM.config.apiURL,
		     			  user:"${logininfor.login_id}",
		     			  pwd: "${logininfor.login_id}",
		     			  appKey: WebIM.config.appkey
		     		};
		     		conn.open(options);
		       }
		       conn.listen({
		    	    onOpened: function ( message ) {          //连接成功回调
	 	    	        console.log("登录成功");
		    	    }
	 	    	});
		       
	           conn.subscribe({
	               to: "${pd.store_id}",
	               // Demo里面接收方没有展现出来这个message，在status字段里面
	               message: '加个好友呗!'   
	           });
	       };
	        
		
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
 			//先注册环信
 			var options = { 
 				username : "${pd.store_id}",
 				password : "${pd.store_id}",
 				nickname : "${pd.store_id}",
			    appKey: WebIM.config.appkey,
			    success: function () { alert("成功通过审核!");},  
			    error: function () { alert("环信注册失败！请联系管理员");}, 
			    apiUrl: WebIM.config.apiURL
			}; 
			conn.registerUser(options);
 			//添加服务商为好友
			//addFriends();
 			var withdraw_rate = $("#withdraw_rate").val();
			//通过审核
			$.ajax({
				  url: 'zhihuiz_store_file/checkedOk.do',
				  data:{
					  	"store_id":"${pd.store_id}",
					  	"store_file_id":"${pd.store_file_id}",
					  	"check_status":"1",
					  	"withdraw_rate":withdraw_rate
				  },
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  if(data == "1"){
 						  window.location.reload();
					  }else{
						  alert("系统出错，请联系技术人员");
					  }
 				  } 
			});
  			 
		}
		
		
		//点击寄送
 		function dingwei(store_id,address,lng,lat){
  			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="点击定位";
			 diag.URL = 'zhihuiz_store_file/dingwei.do?store_id='+store_id+'&address='+address+'&lng='+lng+'&lat='+lat;
			 diag.Width = 1000;
			 diag.Height = 800;
			 diag.CancelEvent = function(){ //关闭事件
 				diag.close();
			 	window.location.reload();
			 };
			 diag.show();
		} 
		 
     
     </script>
    </body>
</html>