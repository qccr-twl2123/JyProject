<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>商家经营分析报表</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/ace.min.css" />
        <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/zhifu1.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="My97DatePicker/WdatePicker.js"></script>
        <style type="text/css">
        td{
        	padding:5px;
        }
        </style>
     </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihuiReportForm/jingYingFenXiByStore.do" name="Form" id="Form" method="post">
    	<div class="dangan2_d1">
    	  <span  class="zhifu1_sp1" >时段</span>
		  <input placeholder="开始时间" class="zhifu1_st1" type="text" name="starttime" id="starttime" value="${pd.starttime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
          &nbsp;&nbsp;
          <span  class="zhifu1_sp1" >至</span>
		  <input placeholder="结束时间" class="zhifu1_st1" type="text" name="endtime" id="endtime" value="${pd.endtime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
         </div>
        <div class="dangan2_d1">
          <span  class="zhifu1_sp1">关键字查询</span>
          <input class="zhifu1_ipt1"  value="${pd.content}" type="text" name="content" id="content" placeholder="输入商家ID/商家名称关键字"></input>
          <span class="zhifu1_btn1" onclick="search()">查询</span>
        </div>
 	    <div>
 		       <div class="dangan2_d2">
		          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table"  style="white-space: nowrap;line-height:36px;width:96%">
		              <tr class="tdtop">
		                <td>商家编号</td>
		                <td>商家名称</td>
		                <td>联系人</td>
 		                <td>手机号码</td>
		                <td>一度人脉数</td>
		                <td>二度人脉数</td>
		                <td>最后消费日期</td>
		                <td>可用积分</td>
		                <td>冻结积分</td>
		                <td>注册日期</td>
		                <td>总积分率</td>
		                <td>会员积分率</td>
		                <td>人脉收益积分率</td>
 		                <td>积分提醒额</td>
		                
		                <td>时段平均日送积分</td>
		                <td>时段平均日收积</td>
		                <td>时段被评论数</td>
		                <td>时段充值金额</td>
		                <td>时段提现金额</td>
		                <td>待审核提现金额</td>
		                <td>时段平台线下销售金额</td>
		                <td>时段团购销售金额</td>
		                <td>时段新增一度人脉数</td>
 		              </tr> 
 		              <c:forEach items="${jystoreList}" var="var" varStatus="vs">
			              <tr >
			                <td>${var.store_id}</td>
			                <td>${var.store_name}</td>
			                <td>${var.principal}</td>
			                <td>${var.phone}</td>
			                <td>${var.onecontacts_number}</td>
			                <td>${var.twocontacts_number}</td>
			                <td>${var.lastcreatetime}</td>
			                <td>${var.now_wealth}</td>
			                <td>${var.tixian_money}</td>
			                <td>${var.registertime}</td>
			                <td>${var.integral_rate}</td>
			                <td>${var.service_rate}</td>
 			                <td>20%</td>
 			                <td>${var.remind_integral}</td>
 			                
 			                
 			                <td>${var.qjzc_integer}</td>
 			                <td>${var.qjsy_integer}</td>
 			                <td>${var.qjcomment_number}</td>
 			                <td>${var.qjcz_money}</td>
 			                <td>${var.qjtxok_money}</td>
 			                <td>${var.qjtxcheck_money}</td>
 			                <td>${var.qjxxsale_money}</td>
 			                <td>${var.qjxssale_money}</td>
 			                <td>${var.qjonecontact_number}</td>
   	 		              </tr>
		              </c:forEach>
 		          </table>
		          <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		       </div>
       		</div>
        </form>
        </c:if>
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
  	    <script type="text/javascript" src="js/attention/zDialog/zDrag.js"></script>
	    <script type="text/javascript" src="js/attention/zDialog/zDialog.js"></script>
        <script type="text/javascript">
       //查看详情
 		function detailThis(store_renmaijf_id){
 			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="查看详情";
			 diag.URL = 'zhihuiReportForm/look_detail.do?store_renmaijf_id='+store_renmaijf_id;
			 diag.Width = 1200;
			 diag.Height = 500;
			 diag.CancelEvent = function(){ //关闭事件
				 if('${page.currentPage}' == '0'){
						 setTimeout("self.location.reload()",100);
				 }else{
					 nextPage(${page.currentPage});
				 }
				diag.close();
			 };
			 diag.show();
		}
       
 		//---------------------------
		//查询提交
			function search(){
 		    	$("#Form").submit();
		    }
	    </script>
    </body>
 </html>