<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	     	<form action="zhihui_sp_file/editEarnestStatus.do" method="post" name="Form" id="Form">
	        <input type="hidden" name="sp_file_id" value="${pd.sp_file_id}" />
	        <input type="hidden" name="currentPage" value="${pd.currentPage}" />
	        <input type="hidden" name="earnest_status" value="2" />
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">申请退还保证金的金额</span>
	          <input type="text" name="money" disabled="ture" class="dangan_d1_ipt1" value="${e.earnest_money}" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
	         </div>
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">退还的开户行</span>
	          <input type="text" class="dangan_d1_ipt1" name="account_name"></input>
	         </div>
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">退还的户名</span>
	          <input type="text" class="dangan_d1_ipt1" name="withdraw_username"></input>
	         </div>
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">退还的银行卡号</span>
	          <input type="text"  id="account" class="dangan_d1_ipt1" name="withdraw_number" size="25" onkeyup="formatBankNo(this)" onkeydown="formatBankNo(this)" />
	        </div>
	        <script type="text/javascript">
 	        function formatBankNo (BankNo){
	            if (BankNo.value == "") return;
	            var account = new String (BankNo.value);
	            account = account.substring(0,22); /*帐号的总数, 包括空格在内 */
	            if (account.match (".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null){
	                /* 对照格式 */
	                if (account.match (".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" +
	                ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null){
	                    var accountNumeric = accountChar = "", i;
	                    for (i=0;i<account.length;i++){
	                        accountChar = account.substr (i,1);
	                        if (!isNaN (accountChar) && (accountChar != " ")) accountNumeric = accountNumeric + accountChar;
	                    }
	                    account = "";
	                    for (i=0;i<accountNumeric.length;i++){    /* 可将以下空格改为-,效果也不错 */
	                        if (i == 4) account = account + " "; /* 帐号第四位数后加空格 */
	                        if (i == 8) account = account + " "; /* 帐号第八位数后加空格 */
	                        if (i == 12) account = account + " ";/* 帐号第十二位后数后加空格 */
	                        account = account + accountNumeric.substr (i,1);
	                    }
	                }
	            }else{
	                account = " " + account.substring (1,5) + " " + account.substring (6,10) + " " + account.substring (14,18) + "-" + account.substring(18,25);
	            }
	            if (account != BankNo.value){
	            	BankNo.value = account;
	            }
	        }
	        </script>

	        <div class="dangan_d1">
	            <c:if test="${qx.edit eq '1'}">
		            <a class="middle_a" onclick="editEarnestStatus()"  target="ifra"> 
			            <span class="dangan16_sp1 mgleft">确认</span>
			        </a>
		        </c:if>
		         <c:if test="${qx.edit eq '1'}">
	            <a class="middle_a" href="zhihui_sp_file/goEdit.do?sp_file_id=${pd.sp_file_id}&currentPage=${pd.currentPage}"  target="ifra"> 
		           	<span class="dangan16_sp1">取消</span>
		        </a>
		        </c:if>
	         </div>
	         </form>
         </c:if>
         <script type="text/javascript">
         //提交
         function editEarnestStatus(){
        	 alert("你已申请请等待审核");
        	 $("#Form").submit();
         }
          </script>
    </body>
</html>