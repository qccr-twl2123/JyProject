<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
    <head>
        <title>档案管理</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/zhihui/dangan17.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/dangan17.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    	 <form action="zhihui_service_performance/saveWithdraw.do" name="Form" id="Form" method="post">
	            <div class="dangan_d1">
	          		<span class="dangan_d1_sp1">提现金额：</span>
		            <input type="text" class="dangan_d1_ipt1" value="" name="money" id="money"/>
		        </div>
		        <div class="dangan_d1">
		          <span class="dangan_d1_sp1">开户行：</span>
		          <input type="text" class="dangan_d1_ipt1" name="account_name"/>
		        </div>
		        <div class="dangan_d1">
		          <span class="dangan_d1_sp1">卡号：</span>
		          <input type="text" class="dangan_d1_ipt1" name="withdraw_number"/>
		        </div>
		        <div class="dangan_d1">
		          <span class="dangan_d1_sp1">卡绑定的姓名：</span>
		          <input type="text" class="dangan_d1_ipt1" name="withdraw_username"/>
		        </div>
		        <div class="dangan_d1">
		        <c:if test="${qx.add eq '1'}">
		            <a onclick="sure()"  target="ifra"> 
			            <span class="dangan16_sp1 mgleft">确认</span>
			        </a>
			        </c:if>
		            <a class="middle_a" href="zhihui_service_performance/listService.do"  target="ifra"> 
			           	<span class="dangan16_sp1">取消</span>
			        </a>
		       </div>
         </form>
         </c:if>
         <script type="text/javascript">
         	function sure(){
         		if($("#money").val().trim() == ""){
         			alert("提现金额不能为空");
         			return;
         		}else{
         			if((parseFloat("${pd.nowmoney}")-parseFloat("${pd.notmoney}")) < parseFloat($("#money").val().trim())){
             			alert("余额不足,最多提现"+(parseFloat("${pd.nowmoney}")-parseFloat("${pd.notmoney}")+"有"+parseFloat("${pd.notmoney}")+"未激活"));
             			return;
             		}
         		}
         		$("#Form").submit();//提交
         	}
         </script>
    </body>
</html> --%>