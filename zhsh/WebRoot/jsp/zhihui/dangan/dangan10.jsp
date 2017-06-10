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
        <link rel="stylesheet" href="css/zhihui/dangan10.css">
        <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/dangan10.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    	<form action="zhihui_operator_file/${msg}.do" name="Form" id="Form" method="post">
    		<input type="hidden" name="operator_file_id" id=operator_file_id value="${pd.operator_file_id}">
	    	<input type="hidden" name="currentPage" id="currentPage" value="${pd.currentPage}"/>
 	        <div class="dangan_d1">
	        	  <span class="dangan_d1_sp1">省</span>
	        	 <select class="dangan_d1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
 				 	<c:if test="${oppd.province_id ne '' and  !empty oppd.province_id}">
							<option value="${oppd.province_id}"  selected="selected">${oppd.province_name}</option>
					</c:if>
 				 	 <option value="">请选择省</option>
 				 	 <c:forEach items="${provincelist}" var="var" varStatus="vs">
							<option value="${var.province_id}" >${var.province_name}</option>
					</c:forEach>
 		           </select>
		          <span class="dangan_d1_sp1">市</span>
		          <select class="dangan_d1_st1" name="city_id" id="city_id" onchange="addsearcharea();">
		             <option value="${oppd.city_id }">${oppd.city_name}</option>
		           </select>
		          <span class="dangan_d1_sp1">区/县</span>
		          <select class="dangan_d1_st1" name="area_id" id="area_id" >
		             <option value="${oppd.area_id }">${oppd.area_name}</option>
		           </select>
	        </div>
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">名字</span>
	          <input type="text" class="dangan_d1_ipt1" name="operator_name" id="operator_name" value="${oppd.operator_name }"/>
	          <span class="dangan_d1_sp1">身份证</span>
	          <input type="text"  size="18"   class="dangan_d1_ipt1" name="idnumber" id="idnumber" value="${oppd.idnumber}" />
	        </div>
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">手机</span>
	          <input type="text" maxlength="11" class="dangan_d1_ipt1" name="phone" id="phone"  value="${oppd.phone }" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />
	          <span class="dangan_d1_sp1">入职时间</span>
	          <input placeholder="入职时间" type="text" class="dangan_d1_ipt1" name="entry_time" id="entry_time" value="${oppd.entry_time}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
	        </div>
 	         <div class="dangan_d1">
		          <span class="dangan_d1_sp1">角色</span>
		          <select class="dangan_d1_st1" name="menu_role_id" id="menu_role_id" >
			          	<c:if test="${msg eq 'save'}">
			          		<option value="" selected>请选择角色</option>
			          	</c:if>
			          	<c:forEach items="${roleList}" var="var">
			          		<option value="${var.menu_role_id }"  ${var.menu_role_id eq oppd.menu_role_id?'selected':'' }> ${var.menu_role_name }</option>
			          	</c:forEach>
	  	          </select>
		          <span class="dangan_d1_sp1" >职务</span>
		          <input type="text" name="post_name" id="post_name" class="dangan_d1_ipt1" value="${oppd.post_name}">
 	        </div>
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">状态</span>
	          <span class="dangan8_sp1">
	          <c:if test="${qx.edit eq '1'}">
	            <label><input type="radio" name="zt" ${oppd.open_status eq '1'?'checked':'' }  onclick="changeStatus('1')" /><span>开启</span></label>
	            </c:if>
	          </span>
	          <span class="dangan8_sp1">
	          <c:if test="${qx.edit eq '1'}">
	            <label><input type="radio" name="zt" ${oppd.open_status eq '0'?'checked':'' }  onclick="changeStatus('0')" /><span>关闭</span></label>
	          </c:if>
	          </span>
	          <input type="text" name="open_status" id="open_status" value="${oppd.open_status eq '1'?'1':'0' }"  style="display:none;width:1px;height:1px;">
	          
	          <span class="dangan_d1_sp1 mgleft">编号</span>
	          <span class="blue">${pd.operator_file_id}</span>
	          
	        </div>
	         <div class="dangan_d1 mgtop">
	          <span class="dangan_d1_sp1">初始密码</span>
	          <input type="text" class="dangan_d1_ipt1"  name="password" id="password" value="${oppd.password}"/>
	        </div>
	        <div class="dangan_d1 ">
	          <c:if test="${qx.add eq '1'}">
	          	<span class="dangan_d1_btn1  mgleft11" onclick="save()">保存</span>
	          </c:if> 
	          <a class="middle_a" href="zhihui_operator_file/list.do"  target="ifra"> 
	           	<span class="dangan_d1_btn1">退出</span>
	          </a>  
	        </div>
        </form>
        </c:if>
         <script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框-->
      	<script type="text/javascript">
      	 //修改open_status的选中状态
		 function changeStatus(value){
			 $("#open_status").show();
			 $("#open_status").val(value);
			 $("#open_status").hide();
		 }
		//保存
			function save(){
				if($("#operator_name").val()==""){
					$("#operator_name").tips({
						side:3,
			            msg:'请输入名字',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#operator_name").focus();
					return false;
				}
				if($("#phone").val()==""){
					$("#phone").tips({
						side:3,
			            msg:'请输入手机号码',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#phone").focus();
					return false;
				}
				if($("#idnumber").val()==""){
					$("#idnumber").tips({
						side:3,
			            msg:'请输入身份证号',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#idnumber").focus();
					return false;
				}else{
					// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
		            var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
		            if(reg.test($("#idnumber").val()) === false)  
		            {  
		                alert("身份证输入不合法");  
		                return  false;  
		            }  
				}
				if($("#menu_role_id").val()==""){
					$("#menu_role_id").tips({
						side:3,
			            msg:'请选择角色',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#menu_role_id").focus();
					return false;
				}
				if($("#entry_time").val()==""){
					$("#entry_time").tips({
						side:3,
			            msg:'请输入入职时间',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#entry_time").focus();
					return false;
				}
 				$("#Form").submit();//提交
			}
      	 
		
			   
			   //获取城市
					function addsearchfind(){
						var str=$("#province_id option:selected").val();//获取被选中的value值
			 			$.ajax({
							  url: '<%=path%>/zhihui_city_file/citylist.do',
							  data:{
								  "province_id":str,
								  "login_cityname":"${pd.login_cityname}",
								  "open_status":"1"
							  },
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
							  data:{"province_id":str1,"city_id":str2,"login_areaname":"${pd.login_areaname}","open_status":"1"},
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
       	</script>
    </body>
</html>