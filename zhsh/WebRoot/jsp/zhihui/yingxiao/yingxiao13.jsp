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
        <script src="js/zhihui/yingxiao13.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihui_store/edit.do" name="Form" id="Form" method="post">
    	<input type="hidden" name="currentPage"  id="currentPage" value="${currentPage}"/>
      	<input type="hidden" name="store_id" value="${pd.store_id }" />
      <div class="yingxiao8_d1">
          <span class="yingxiao8_d1_sp1">区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;域</span>
          <input type="text" class="yingxiao8_d1_st1" name="province_name" id="province_name" value="${pd.province_name }"  readonly="readonly"/>
          <input type="text" class="yingxiao8_d1_st1" name="city_name" id="city_name" value="${pd.city_name}"  readonly="readonly"/>
          <input type="text" class="yingxiao8_d1_st1" name="area_name" id="area_name" value="${pd.area_name }"  readonly="readonly"/>
      </div>   
      <div class="yingxiao8_d1">
         <span class="yingxiao8_d1_sp1">商家名称</span>
        <input type="text"  class="yingxiao8_d1_ipt1" name="store_name" value="${pd.store_name }" placeholder="商家名称" readonly="readonly"/>
        <span class="yingxiao8_d1_sp1">负责人</span>
        <input type="text"  class="yingxiao8_d1_ipt1" name="principal" value="${pd.principal }" placeholder="负责人名称" readonly="readonly"/>
      </div>
      <div class="yingxiao8_d1">
	        <span class="yingxiao8_d1_sp1">手机号码</span>
	       <input type="text"  class="yingxiao8_d1_ipt1" name="phone" value="${pd.phone }" placeholder="联系方式" readonly="readonly"/>
	        <span class="yingxiao8_d1_sp1">当前星级</span>
	        <c:if test="${pd.merchant_level eq'1'}">
	       		<input type="text"  class="yingxiao8_d1_ipt1"  value="一星" placeholder="星级"  readonly="readonly"/>
	      	</c:if>
	      	 <c:if test="${pd.merchant_level eq'2'}">
	       		<input type="text"  class="yingxiao8_d1_ipt1"  value="二星" placeholder="星级" readonly="readonly"/>
	      	</c:if>
	      	 <c:if test="${pd.merchant_level eq'3'}">
	       		<input type="text"  class="yingxiao8_d1_ipt1"  value="三星" placeholder="星级" readonly="readonly"/>
	      	</c:if>
      </div>
      <div class="yingxiao8_d1">
	        <span class="yingxiao8_d1_sp1">增加星级至</span>
	        <select class="yingxiao8_d1_st1" name="merchant_level" >
	        	<option value="1" ${pd.merchant_level eq'1'?'selected':''}>一星</option>
	        	<option value="2" ${pd.merchant_level eq'2'?'selected':''}>二星</option>
	            <option value="3" ${pd.merchant_level eq'3'?'selected':''}>三星</option>
	        </select>
	        <span class="yingxiao8_d1_sp1">应收费用</span>
	       <input type="text"  class="yingxiao8_d1_ipt1" name="getMoney" />
      </div>
      <div class="yingxiao8_d1">
           <!-- <input class="yingxiao8_yes" type="submit" value="提交审核"> -->
           <c:if test="${qx.edit eq '1'}">
           		<span class="yingxiao8_yes" onclick="editMerchant_level()">提交</span>
           </c:if>
      </div>	 
	</form>
	</c:if>
	<script type="text/javascript">
		//	提交
 		function editMerchant_level(){
 			$("Form").submit();
 		}
	</script>
    </body>
 </html>