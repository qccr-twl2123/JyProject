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
        <title>系统管理</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
		<link rel="stylesheet" href="css/ace.min.css" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/xitong3.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/xitong3.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
       <form action="<%=path %>/zhihui_menu_marketing/selectAll.do" name="Form" id="Form" method="post">
       <c:if test="${qx.look eq '1'}">
       <div class="dangan2_d1">
          <input class="zhifu1_ipt1" type="text" placeholder="可输入操作员、业务员、服务商或者子公司ID" name="content" style="width: 300px;"></input>
           <span class="zhifu1_btn1" onclick="search()">搜索</span>
       </div>
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr >
                <td>ID</td>
                <td>名称</td>
                <td>手机号</td>
                <td>创建时间</td>
                 <td>操作</td> 
              </tr> 
              <c:if test="${pd.type eq '1' }">
              	<tr >
	                <td>${pd.operator_file_id }</td>
	                <td>${pd.operator_name }</td>
	                <td>${pd.phone }</td>
	                <td><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
								${fn:substring(pd.createdate,0,19)}</td>
	                
	                <td class="blue bctitle"><c:if test="${qx.edit eq '1'}">
	                	<a class="middle_a" onclick="updPass('${pd.operator_file_id }','${pd.phone }')" target="ifra">密码重置</a>
	                	 </c:if>
	                </td>
	               
               </tr> 
              </c:if>
              <c:if test="${pd.type eq '2' }">
              	 <tr >
	                <td>${pd.clerk_file_id }</td>
	                <td>${pd.clerk_name }</td>
	                <td>${pd.phone }</td>
	                <td>${pd.menu_role_name}</td>
	               
	                <td class="blue bctitle"> <c:if test="${qx.edit eq '1'}">
	                	<a class="middle_a" onclick="updPass('${pd.clerk_file_id }','${pd.phone }')"  target="ifra">密码重置</a>
	                 </c:if></td>
	               
              	</tr> 
              </c:if>
              <c:if test="${pd.type eq '3' }">
              	<tr >
	                <td>${pd.sp_file_id }</td>
	                <td>${pd.team_name }</td>
	                <td>${pd.phone }</td>
	                <td>${pd.menu_role_name}</td>
	               
	                <td class="blue bctitle"> <c:if test="${qx.edit eq '1'}">
	                	<a class="middle_a" onclick="updPass('${pd.sp_file_id }','${pd.phone }')"  target="ifra">密码重置</a>
	                  </c:if></td>
	              
                </tr> 
              </c:if>
              <c:if test="${pd.type eq '4' }">
              	<tr >
	                <td>${pd.subsidiary_id }</td>
	                <td>${pd.subsidiary_name }</td>
	                <td>${pd.phone }</td>
	                <td>${pd.menu_role_name}</td>
	               
	                <td class="blue bctitle"> <c:if test="${qx.edit eq '1'}">
	                	<a class="middle_a" onclick="updPass('${pd.subsidiary_id }','${pd.phone }')" target="ifra">密码重置</a>
	                 </c:if></td>
	               
                </tr> 
              </c:if>
          </table>
       </div>
      <%-- <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div> --%>
   </c:if>
   </form>
    </body>
    <script type="text/javascript">
    	function search(){
    		$("#Form").submit();
    	}
    	
    	//随机生成指定个数的字符串
    	function randomWord(){
    	    var str = "",
    	       arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];

    	    for(var i=0; i<6; i++){
    	        pos = Math.round(Math.random() * (arr.length-1));
    	        str += arr[pos];
    	    }
    	    return str;
    	}
    	
    	function updPass(id,phone){
    		if(phone == ""){
    			alert("手机号码不能为空");
    			return;
    		}
    		var password=randomWord();
	   		$.ajax({
					type:"post",
					url:'<%=basePath%>zhihui_menu_marketing/updPass.do',
					data:{
						id:id,
						password : password,
						phone:phone
					},
					success:function(data){
	   					if(data.result == "00"){
							alert("密码重置失败，请联系管理员！");
		 				}else if(data.result == "01"){
		 					alert("密码重置成功，已短信通知改用户！修改密码为="+password+"，请谨记密码");
 		 					window.location.reload(); 
		 				}
				}
			});
    	}
    	
    </script>
</html>