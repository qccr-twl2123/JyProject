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
        <link rel="stylesheet" href="css/zhihui/yingxiao4.css">
        <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/yingxiao4.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    	 <!-- 内容 -->
       <form action="zhihuired_platform/list.do" method="post" name="Form" id="Form">
       <div class="dangan2_d1">
         <span  class="zhifu1_sp1">用户类型</span>
         <select class="zhifu1_st1" name="target_type">
           		   <option value="">全部</option>
		           <option value="1" ${pd.target_type eq '1'?'selected':''}>商家</option>
		           <option value="2" ${pd.target_type eq '2'?'selected':''}>会员</option>
         </select>
<!--          <span  class="zhifu1_sp1">红包类型</span>
         <select class="zhifu1_st1">
           <option value="1">现金红包</option>
           <option value="2">折扣红包</option>
         </select> 
         <span  class="zhifu1_sp1">搜索商家</span>
         <input class="zhifu1_ipt1" type="text" placeholder="可输入账号/金额等查询"></input>-->
        <span  class="zhifu1_sp1">查询时间</span>
        <input class="zhifu1_st1" type="text" name="starttime" id="starttime" value="${pd.starttime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间"/>
         <span  class="zhifu1_sp1">至</span>
         <input class="zhifu1_st1" type="text" name="endtime" id="endtime" value="${pd.endtime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间"/>
          <span class="zhifu1_btn1"  onclick="checked()">查询</span>
          <c:if test="${qx.add eq '1'}">
	          <a class="middle_a" href="zhihuired_platform/goAdd.do"  target="ifra">
	            <span class="zhifu1_btn1"  >添加</span>
	          </a> 
          </c:if>
       </div>
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr >
                <td>序号</td>
                <td>用户类型</td>
                <td>操作人员</td>
                <td>红包价值</td>
                <td>红包个数</td>
                <td>发放对象</td>
                <td>审核状态</td>
                <td>审核人员</td>
              </tr> 
              <c:forEach items="${varList}" var="var" varStatus="vs">
	              <tr >
	                <td>${vs.index+1 }</td>
	                <td>
						<c:if test="${var.target_type eq '1' }">商家</c:if>
						<c:if test="${var.target_type eq '2' }">会员</c:if>
					</td>
	                <td>${var.operation_name}</td>
	                <td>￥${var.money}</td>
	                <td>${var.redpackage_number}</td>
	                <td>
	                	<c:if test="${var.newmember_status eq '1' }">新注册用户</c:if>
						<c:if test="${var.downapp_status eq '1' }">下载APP用户</c:if>
						<c:if test="${var.firstcz_status eq '1' }">完成首次充值用户</c:if>
						<c:if test="${var.firstjy_status eq '1' }">首单交易成功后的</c:if>
						<c:if test="${var.mlz_status eq '1' }">魅力值首次达到${var.mlz_number}点</c:if>
						<c:if test="${var.tj_status eq '1' }">推荐${var.tj_numberfriend}好友注册成功后的用户，每人限领${var.tj_getrednumber}次</c:if>
						<c:if test="${var.praise_status eq '1' }">好评首次达到${var.full_praise }星的商家</c:if>
	                </td>
	                <td>
	                	<c:if test="${var.review_status eq '0' }">待审核</c:if>
						<c:if test="${var.review_status eq '1' }">审核通过</c:if>
						<c:if test="${var.review_status eq '99' }">审核不通过</c:if>
 	                </td>
	                <td>${var.review_name}</td>
	              </tr>
              </c:forEach>         
          </table>
          <br/>
          <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
       </div>
       </form>
       </c:if>
       <script type="text/javascript">
       
       //提交
       function checked(){
    	   $("#Form").submit();
       }
       
       
       </script>
    </body>
</html>