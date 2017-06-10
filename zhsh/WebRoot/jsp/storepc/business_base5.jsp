<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	<title>操作员</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/jcxx_czy.css">
	<script src="js/jquery-1.8.0.min.js"></script>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
  	<form action="<%=basePath%>storepcOperator_file/findOperator.do" method="post" name="Form" id="Form">  
 	<input type="hidden" name="store_id" value="${pd.store_id}"/>
	<ul>
		<li style="line-height:2.5;margin-bottom: 8px;" class="padd_lr">
			<span class="item_name-3">状态</span>：
			<select name="operator_status" id ="operator_status">
				<option value="">请选择</option>
				<option value="1" ${pd.operator_status eq '1'?'selected':'' }>启用</option>
				<option value="0"  ${pd.operator_status eq '0'?'selected':'' }>停用</option>
			</select>
			<span class="item_name-4" >手机号</span>：
			<input type="text" class="inp-m"  name="operator_phone">
			<span class="anniu-s" onclick="sub()">搜索</span>
			<c:if test="${storeqx.add eq '1' and storepd.login_type eq '1'}"><span class="anniu-s btn_xinjian" >新建操作员</span></c:if>
		</li>
		<li class="padd_lr" style="line-height:2.5;">
			<table style="border:1px solid #666;;margin:0;border-collapse:collapse;width:100%;">
				<thead>
					<td>ID号</td>
					<td>手机号码</td>
					<td>姓名</td>
					<td>职务</td>
					<td>状态</td>
					<td>上机时间</td>
					<td>创建时间</td>
					<td>操作</td>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="l">
						<tr>
							<td>${l.store_operator_id}</td>
							<td>${l.operator_phone}</td>
							<td>${l.operator_name}</td>
	 						<td>
								<c:if test="${l.operator_position eq '1'}">员工</c:if>
								<c:if test="${l.operator_position eq '2'}">老板</c:if>
							</td>
							<td>
								<c:if test="${l.operator_status eq '1'}">正常</c:if>
								<c:if test="${l.operator_status eq '0'}">禁用</c:if>
							</td>
							<%-- <td>${l.logintime}</td> --%>
							<td>	
								${fn:substring(l.logintime,0,19)}
							</td>
							<td>	
								${fn:substring(l.createdate,0,19)}
							</td>
	 						<td>
	 						   <c:choose>
	 						   		<c:when test="${!fn:contains(l.store_operator_id, 'jy') and storepd.login_type eq '1'}">
	 						   			<c:if test="${storeqx.edit eq '1'}">
											<span class="biankuang-s btn_xiugai" onclick="edit('${l.store_operator_id}')">修改</span>
											<c:if test="${l.operator_status eq '0'}">
												<span class="biankuang-s"   onclick="changeHim('${l.store_operator_id}','1')">点击启用</span>
											</c:if>
											<c:if test="${l.operator_status eq '1'}">
												<span class="biankuang-s"   onclick="changeHim('${l.store_operator_id}','0')">点击停用</span>
											</c:if>
										</c:if>
										<c:if test="${storeqx.delete eq '1' }">
			  								<span class="biankuang-s" onclick="delHim('${l.store_operator_id}')">删除</span>
			 							</c:if>
	 						   		</c:when>
	 						   		<c:otherwise>禁止操作</c:otherwise>
	 						   </c:choose>
 	 						</td>
	 					</tr>
					</c:forEach>
				</tbody>
			</table>
		</li>
		<li>
			<div class="fenye clf">
				<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
			</div>
		</li>
	</ul>
	</form>
	<!-- 遮罩 -->
<div class="dask">
<!-- 新建开始 -->
	<form action="<%=basePath%>storepcOperator_file/save.do" name="addForm" id="addForm">
	<input type="hidden" name="store_id" value="${pd.store_id}"/>
    <div class="alert_xg xinjian">		
        <div class="al_head">
            <div class="close"></div>
            <div class="alert_tit">
                <span>新建操作员</span>
                <div class="one"></div>
            </div>
        </div>
        <ul class="al_body">
			<li>
				<span>姓名</span>：
				<input type="text" name="operator_name">
			</li>
			<li>
				<span>手机号码</span>：
				<input type="text" maxlength="11" name="operator_phone" maxlength="11" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
			</li>
           	<li>
           		<span>角色</span>：
	           	<select name="operator_position" style="width:80px;">
	           		<option value="1">员工</option>
	           	</select>
           		
           	</li>
           <%-- <li>
           		<span>班次</span>：
				<select style="width:80px;"  name="store_shift_id">
					<c:forEach items="${bcList}" var="var" varStatus="vs">
	                    <option value="${var.store_shift_id }">${var.shift_name}</option>
	                </c:forEach>
	 			</select> 
           	</li> --%>
           	<li style="position: relative;">
           		<span  style="position: absolute;">权限管理</span>：
				<ul class="clfa" style="padding-left:12%;overflow:hidden;position: relative;">
					<li class="check_item">
						<span>收银</span>
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="sylook" type="hidden" value="0"> <span>查看</span> 
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="syadd" type="hidden" value="0"> <span>可增</span>  
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="sydelete" type="hidden" value="0"><span>删除</span> 
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="syedit" type="hidden" value="0"><span >修改</span>  
					</li>
 				</ul>
				<ul class="clfa" style="padding-left:12%;overflow:hidden;">
					<li class="check_item">
						<span>营销</span>
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="yxlook" type="hidden" value="0"> <span>查看</span> 
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="yxadd" type="hidden" value="0"> <span>可增</span>  
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="yxdelete" type="hidden" value="0"><span>删除</span> 
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="yxedit" type="hidden" value="0"><span >修改</span>  
					</li>
 				</ul>
 				<ul class="clfa" style="padding-left:12%;overflow:hidden;">
					<li class="check_item">
						<span>商品</span>
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="splook" type="hidden" value="0"> <span>查看</span> 
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="spadd" type="hidden" value="0"> <span>可增</span>  
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="spdelete" type="hidden" value="0"><span>删除</span> 
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="spedit" type="hidden" value="0"><span >修改</span>  
					</li>
 				</ul>
				<ul class="clfa" style="padding-left:12%;overflow:hidden;">
					<li class="check_item">
						<span>财务</span>
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="cwlook" type="hidden" value="0"> <span>查看</span> 
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="cwadd" type="hidden" value="0"> <span>可增</span>  
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="cwdelete" type="hidden" value="0"><span>删除</span> 
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="cwedit" type="hidden" value="0"><span >修改</span>  
					</li>
 				</ul>
				<ul class="clfa" style="padding-left:12%;overflow:hidden;">
					<li class="check_item">
						<span>互动</span>
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="hdlook" type="hidden" value="0"> <span>查看</span> 
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="hdadd" type="hidden" value="0"> <span>可增</span>  
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="hddelete" type="hidden" value="0"><span>删除</span> 
					</li>
					<li class="check_item">
						<input type="checkbox"  onclick="changeStatus(this)"/><input name="hdedit" type="hidden" value="0"><span >修改</span>  
					</li>
 				</ul>
           	</li>
        </ul>           
        <div class="al_foot">
            <div class="button_box" onclick="addform()" >
                确定
            </div>
        </div>
    </div>
	</form>
	<script type="text/javascript">
		function addform(){
				$("#addForm").submit();
		}
	</script>
<!-- 新建结束 -->


<!-- 
修改开始 -->
<form action="storepcOperator_file/edit.do" name="editForm" id="editForm">
	<input type="hidden" id="store_operator_id" name="store_operator_id" >
	<input type="hidden" id="store_id" name="store_id" value="${storepd.store_id}">
	<div class="alert_xg xiugai">		
        <div class="al_head">
            <div class="close"></div>
            <div class="alert_tit">
                <span>新建操作员</span>
                <div class="one"></div>
            </div>
        </div>
        <ul class="al_body">
			<li>
				<span>姓名</span>：
				<input type="text" id="operator_name" name="operator_name">
			</li>
			<li>
				<span>手机号码</span>：
				<input type="text" maxlength="11" id="operator_phone" name="operator_phone">
			</li>
           	<li>
           		<span>角色</span>：
	           	<select name="operator_position" id="operator_position" style="width:80px;">
	           		<option value="1">员工</option>
	           	</select>
           		
           	</li>
          <%--  	<li>
           		<span>班次</span>：
				<select style="width:80px;" name="store_shift_id" id="store_shift_id">
					<c:forEach items="${bcList}" var="var" varStatus="vs">
	                    	<option value="${var.store_shift_id }">${var.shift_name}</option>
	                </c:forEach>
	 			</select> 
           	</li> --%>
           	<li style="position: relative;" id="qxgl">
           		<span  style="position: absolute;">权限管理</span>：
				 
				 
           	</li>
        </ul>           
        <div class="al_foot">
            <div class="button_box"  onclick="editform()" >
                确定
            </div>
        </div>
    </div>
    </form>
</div>
<script type="text/javascript">
	function editform(){
		$("#editForm").submit();
	}
</script>
<!-- 修改结束 -->
	</c:if>
	<script type="text/javascript">
		//搜索
		function sub() {
			$("#Form").submit();  
		}
		//停用/启用
		function changeHim(iid,value) {
 			window.location.href="<%=basePath%>storepcOperator_file/updateOperator.do?store_operator_id="+iid+"&operator_status="+value+"&store_id=${storepd.store_id}";
		}
		//删除操作员
		function delHim(iid){
 			window.location.href="<%=basePath%>storepcOperator_file/delOperator.do?store_operator_id="+iid+"&store_id=${storepd.store_id}";
		}
		//到修改页面
		function edit(value){
			$.ajax({
			   type: "post",
			   url: "<%=basePath%>storepcOperator_file/goEdit.do",
			   data:{"store_operator_id":value},
			   success: function(data){
 				    $("#qxgl").empty();
  				   	$("#store_operator_id").val(data.data.store_operator_id);
				   	$("#operator_name").val(data.data.operator_name);
				   	$("#operator_phone").val(data.data.operator_phone);
				   	$("#operator_position").val(data.data.operator_position).attr("selected");
  			   		//权限
			   		var allList=data.allList;
			   		var allstr="<span  style='position: absolute;'>权限管理：(修改之后app端需重新登录生效)</span>：";
			   		for(var i=0 ; i<allList.length; i++){
			   			 var str="";
			   			 var str="<ul class='clfa' style='padding-left:12%;overflow:hidden;position: relative;'><li class='check_item'>  <span>"+allList[i].name+"</span>  </li> ";
			   			if(allList[i].look == "1"){
			   				str+= "<li class='check_item'><input type='checkbox' checked  onclick='changeStatus(this)'/><input name='"+allList[i].type+"look' type='hidden' value='1'> <span>查看</span></li> ";  			   			 
			   			}else{
			   				str+= "<li class='check_item'><input type='checkbox'  onclick='changeStatus(this)'/><input name='"+allList[i].type+"look' type='hidden' value='0'> <span>查看</span></li> ";  			   		
  			   			}
			   			 if(allList[i].save == "1"){
			   				str+="<li class='check_item'><input type='checkbox' checked onclick='changeStatus(this)'/><input name='"+allList[i].type+"add' type='hidden' value='1'> <span>增加</span></li>";  
			   			 }else{
			   				str+="<li class='check_item'><input type='checkbox'  onclick='changeStatus(this)'/><input name='"+allList[i].type+"add' type='hidden' value='0'> <span>增加</span></li>"  ;
			   			 }
			   			if(allList[i].del == "1"){
			   				str+="<li class='check_item'><input type='checkbox' checked  onclick='changeStatus(this)'/><input name='"+allList[i].type+"delete' type='hidden' value='1'><span>删除</span></li>" ;
 			   			 }else{
 			   				str+="<li class='check_item'><input type='checkbox'  onclick='changeStatus(this)'/><input name='"+allList[i].type+"delete' type='hidden' value='0'><span>删除</span></li>" ;
			   			 }
			   			if(allList[i].edit == "1"){
			   				str+="<li class='check_item'><input type='checkbox' checked  onclick='changeStatus(this)'/><input name='"+allList[i].type+"edit' type='hidden' value='1'><span >修改</span></li>"  ;
  			   			 }else{
  			   				str+="<li class='check_item'><input type='checkbox'  onclick='changeStatus(this)'/><input name='"+allList[i].type+"edit' type='hidden' value='0'><span >修改</span></li>"  ;			   		 
  			   			 }
			   			str+="</ul>";
  			   			allstr+=str;
  			   		}
			   		$("#qxgl").html(allstr);
			   }
			});
		}
 
	    //改变状态
	    function changeStatus(obj){
  			if($(obj).is(":checked")){
				$(obj).next().val("1");
			}else{
				$(obj).next().val("0");
			}
 		}
	     
	    
		function xiugai(){
			$(".dask").css({"display":"block"})
			$(".xiugai").css({"display":"block"})
			$(".xinjian").css({"display":"none"})

		}
		function xinjian(){
			$(".dask").css({"display":"block"})
			$(".xiugai").css({"display":"none"})
			$(".xinjian").css({"display":"block"})
		}

		$(".btn_xiugai").click(function(e){
			xiugai()
		})
		$(".btn_xinjian").click(function(e){
			xinjian()
		})
		function guanbi(){
			$(".dask").css({"display":"none"})
		}
		$(".close").click(function(){
			guanbi()
		})
		$(".button_box").click(function(){
			guanbi()
		})

	/*点击文字选择checkbox*/
		$(".check_item span").css({"cursor":"pointer"}) 
		$(".check_item span").click(function(e){
			$(e.target).prev()[0].click()
		})
		
	</script>
 </body>
</html>