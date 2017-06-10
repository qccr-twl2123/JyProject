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
        <link rel="stylesheet" href="css/zhihui/xitong6.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/xitong6.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihui_menu_text/edit.do" method="post" name="Form" id="Form">
	    	<input type="hidden" name="menu_text_id" value="${pd.menu_text_id }"  />
	      	<div class="yingxiao8_d1">
	 	        <span class="yingxiao8_d1_sp1">名称</span>
		        <input type="text" name="name" value="${pd.name }" class="yingxiao8_d1_ipt1" />
	      	</div>
	       <div class="yingxiao8_d1">
	        <span class="yingxiao8_d1_sp1">类型</span>
	        <select name="type" class="yingxiao8_d1_ipt1">
	        	<option value="1">app端</option>
	        	<option value="2">pc端</option>
	        </select>
	      </div>
	       <div class="yingxiao8_d1">
	        <span class="yingxiao8_d1_sp1">位置</span>
	        <input type="text" name="position" value="${pd.position }" class="yingxiao8_d1_ipt1" placeholder="我的-我的余额" />
	      </div>
	      <div class="new_d1">
	      		<span class="yingxiao8_d1_sp1">文本</span>
	      		<textarea rows="5" cols="72" name="text">"${pd.text }" </textarea>
	      </div> 
	      <%-- <div class="photo"  id="show">${pd.text }</div>  --%>
	      <br/>
	      <c:if test="${qx.edit eq '1'}">
	      <div class="yingxiao8_d1">
	      	  <a  target="ifra" onclick="save()">
	          	<span   class="yingxiao8_yes" >确定</span>
	          </a>
	      </div> 
	      </c:if>
		</form>
		</c:if>
		    <script type="text/javascript">
		    	//文件展示
				function save(){
					$("#Form").submit();
				}
		    </script>
    </body>

</html>