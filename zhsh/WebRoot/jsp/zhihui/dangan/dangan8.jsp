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
        <link rel="stylesheet" href="css/zhihui/dangan8.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/dangan8.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihui_clerk_file/${msg}.do" name="Form" id="Form" method="post">
	    <input type="hidden" name="clerk_file_id" id="clerk_file_id" value="${pd.clerk_file_id}">
	    <input type="hidden" name="menu_role_id" id="menu_role_id" value="2">
	    <input type="hidden" name="currentPage" id="currentPage" value="${pd.currentPage}"/>
        <div class="dangan_d1">
          <span class="dangan_d1_sp1">名字</span>
          <input type="text" class="dangan_d1_ipt1" name="clerk_name" id="clerk_name" value="${pd.clerk_name}"></input>
          <span class="dangan_d1_sp1">身份证</span>
          <input type="text" size="25" size="18"    class="dangan_d1_ipt1"  name="idnumber" id="idnumber" value="${pd.idnumber}"  />
        </div>
        <div class="dangan_d1">
          <span class="dangan_d1_sp1">手机</span>
          <input type="text" maxlength="11" class="dangan_d1_ipt1" name="phone" id="phone" value="${pd.phone}" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"  />
        </div>
         <div class="dangan_d1">
          <span class="dangan_d1_sp1">服务商</span>
          <select class="dangan_d1_st1" name="sp_file_id">
             	<c:forEach items="${spList}" var="var">
            		<option value="${var.sp_file_id}" ${var.sp_file_id eq pd.sp_file_id ? 'selected':'' }>${var.team_name}</option>
            	</c:forEach>
          </select>
        </div>
        <div class="dangan_d1">
          <span class="dangan_d1_sp1">身份证地址</span>
          <input type="text" class="dangan_d1_ipt1 ipt3" name="clerk_address" value="${pd.clerk_address}" />
        </div>
        
        <div class="dangan_d1">
          <span class="dangan_d1_sp1">状态</span>
          <c:if test="${pd.open_status eq '1'}">
          	 <span class="dangan8_sp1">
	            <label><input type="radio" name="cz" checked="checked"  value="1" onclick="changeStatus('1')"/><span>开启</span></label>
	          </span>
	          <span class="dangan8_sp1">
	            <label><input type="radio" name="cz" value="0" onclick="changeStatus('0')"/><span>关闭</span></label>
	          </span>
	          <input type="text" name="open_status" id="open_status" value="1"  style="display:none;width:1px;height:1px;">
           </c:if>
          <c:if test="${pd.open_status ne '1'}">
          	 <span class="dangan8_sp1">
            <label><input type="radio"  name="cz" value="1" onclick="changeStatus('1')"/><span>开启</span></label>
	          </span>
	          <span class="dangan8_sp1">
	            <label><input type="radio" name="cz" checked="checked" value="0" onclick="changeStatus('0')"/><span>关闭</span></label>
	          </span>
	          <input type="text" name="open_status" id="open_status"  value="0"  style="display:none;width:1px;height:1px;">
          </c:if>
         </div>
         
        <div class="dangan_d1">
        		<c:if test="${qx.add eq '1'}">
         	 		<span class="dangan_d1_btn1  mgleft11" onclick="save()">保存</span> 
         	 	</c:if>
         		<a class="middle_a" href="zhihui_clerk_file/list.do"  target="ifra"> 
           	 		<span class="dangan_d1_btn1">退出</span>
         		</a>  
        </div>
       </form>
       </c:if>
       <script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框-->
       <script type="text/javascript">
		 	//保存
			function save(){
				if($("#clerk_name").val()==""){
					$("#clerk_name").tips({
						side:3,
			            msg:'请输入名字',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#clerk_name").focus();
					return false;
				}
				if($("#clerk_head").val()==""){
					$("#clerk_head").tips({
						side:3,
			            msg:'请输入责任人',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#clerk_head").focus();
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
					//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
			        var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
			        if(reg.test($("#idnumber").val()) === false) {  
			               alert("身份证输入不合法"); 
	 		               return  false;  
			        }  
				}
 				if($("#phone").val()==""){
					$("#phone").tips({
						side:3,
			            msg:'请输入电话',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#phone").focus();
					return false;
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
				if($("#address").val()==""){
					$("#address").tips({
						side:3,
			            msg:'请输入地址',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#address").focus();
					return false;
				}
		 		if($("#username").val()==""){
					$("#username").tips({
						side:3,
			            msg:'请输入用户名',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#username").focus();
					return false;
				}
				$("#Form").submit();
			}
		 	
		 //修改open_status的选中状态
		 function changeStatus(value){
			 $("#open_status").show();
			 $("#open_status").val(value);
			 $("#open_status").hide();
		 }
		</script>
    </body>
</html>