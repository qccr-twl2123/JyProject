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
        <title>支付管理</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/ace.min.css" />
        <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/zhifu3.css">
         <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/zhifu3.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
     <form action="zhihui_withdraw_approval/listCash.do" name="Form" id="Form" method="post">
       <div class="dangan2_d1">
	          <select class="zhifu1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
	           	<option value="">请选择省</option>
	           	<c:forEach items="${provincelist}" var="var" varStatus="vs">
					<option value="${var.pcd_id}">${var.name}</option>
				</c:forEach>
				<c:if test="${pd.province_id ne '' and  !empty pd.province_id}">
							<option value="${pd.province_id}"  selected="selected">${pd.province_name}</option>
				</c:if>
 	         </select>
	         <select class="zhifu1_st1" name="city_id" id="city_id" onchange="addsearcharea();">
	         	 <option value="${pd.city_id}">${pd.city_name}</option>
 	         </select>
	         <select class="zhifu1_st1" name="area_id" id="area_id">
	         	 <option value="${pd.area_id}">${pd.area_name}</option>
 	         </select>
 	         <input type="hidden" name="province_name" id="province_name" value="${pd.province_name}"  />
		     <input type="hidden" name="city_name" id="city_name" value="${pd.city_name}"  />
		     <input type="hidden" name="area_name" id="area_name" value="${pd.area_name}"  />
         <span  class="zhifu1_sp1">用户类型</span>
         <select class="zhifu1_st1" name="user_type">
           <option value="">全部</option>
           <option value="1" ${pd.user_type eq '1'?'selected':''}>商家</option>
           <option value="2" ${pd.user_type eq '2'?'selected':''}>会员</option>
           <option value="3" ${pd.user_type eq '3'?'selected':''}>服务商</option>
           <%-- <option value="4" ${pd.user_type eq '4'?'selected':''}>操作员</option> --%>
         </select>
         <span  class="zhifu1_sp1">到账方式</span>
         <select class="zhifu1_st1" name="account_type">
           <option value="">全部</option>
           <option value="3" ${pd.account_type eq '3'?'selected':''}>支付宝</option>
           <option value="4" ${pd.account_type eq '4'?'selected':''}>银行卡</option>
         </select>
        </div>
       <div class="dangan2_d1">
         <span  class="zhifu1_sp1">提现方式</span>
         <select class="zhifu1_st1" name="withdraw_type">
           <option value="">全部</option>
           <option value="2" ${pd.withdraw_type eq '1'?'selected':''}>自动</option>
           <option value="1" ${pd.withdraw_type eq '0'?'selected':''}>手动</option>
          </select>
         <span  class="zhifu1_sp1">提现状态</span>
         <select class="zhifu1_st1" name="withdraw_status">
           <option value="">全部</option>
            <option value="1" ${pd.withdraw_status eq '1'?'selected':''}>审批通过</option>
           <option value="2" ${pd.withdraw_status eq '2'?'selected':''}>审批驳回</option>
          </select>
       </div>
       <div class="dangan2_d1">
          <span  class="zhifu1_sp1">支付时间</span>
		  <input placeholder="支付时间" class="zhifu1_st1" type="text" name="apply_time" id="apply_time" value="${pd.apply_time}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
          <span  class="zhifu1_sp1">条件查询</span>
          <input class="zhifu1_ipt1" type="text" name="content" id="content" placeholder="可输入账号/金额等查询"></input>
          <span class="zhifu1_btn1" onclick="search()">查询</span>
          <span class="zhifu1_btn1" onclick="daochuexcel()">导出Excel</span>
        </div>
       
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr class="tdtop">
                <td>序号</td>
                <td>省</td>
                <td>市</td>
                <td>区/县</td>
                <td>用户类型</td>
                <td>联系电话</td>
                <td>款项金额</td>
                <td>当前的提现用户余额</td>
                <td>到账方式</td>
                <td style="width:300px;"> 用户名&nbsp;&nbsp;&nbsp;开户行&nbsp;&nbsp;&nbsp;提款账号 </td>
                <td>提款方式</td>
                <td>提现状态</td>
                <td>备注</td>
                <td>申请时间</td>
              </tr>
             <c:forEach items="${varList}" var="var" varStatus="vs">
              <tr >
                <td>${vs.index+1}</td>
                <td>${var.province_name}</td>
                <td>${var.city_name}</td>
                <td>${var.area_name}</td>
                <td>
                <c:if test="${var.user_type eq '1'}">商家</c:if>
                <c:if test="${var.user_type eq '2'}">会员</c:if>
                <c:if test="${var.user_type eq '3'}">业务员</c:if>
                <c:if test="${var.user_type eq '4'}">操作员</c:if>
                </td>
                <td>${var.phone}</td>
                <td>${var.money}</td>
                <td style="color:blue;">${var.nowmoney }</td>
                <td>
                	<c:if test="${var.account_type eq '1'}">现金</c:if>
	                <c:if test="${var.account_type eq '2'}">微信</c:if>
	                <c:if test="${var.account_type eq '3'}">支付宝</c:if>
	                <c:if test="${var.account_type eq '4'}">银行卡</c:if>
	                <c:if test="${var.account_type eq '5'}">Apple-play</c:if>
	                <c:if test="${var.account_type eq '6'}">积分支付</c:if>
                </td>
                <td>${var.withdraw_username }&nbsp;&nbsp;&nbsp;${var.account_name }&nbsp;&nbsp;&nbsp;${var.withdraw_number }</td>
                <td>
	                <c:if test="${var.withdraw_type eq '1'}">手动提现</c:if>
	                <c:if test="${var.withdraw_type eq '2'}">自动提现</c:if>
	                <c:if test="${var.withdraw_type eq '0'}">未提现</c:if>
                </td>
                <td>
	                <c:if test="${var.withdraw_status eq '0'}">待审批</c:if>
	                <c:if test="${var.withdraw_status eq '1'}">审批通过</c:if>
	                <c:if test="${var.withdraw_status eq '2'}">审批驳回</c:if>
                </td>
                <td>${var.remark }</td>
                <td>${var.apply_time }</td>
              </tr>
              </c:forEach>
           </table>
       </div>
       <br>
       <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
       </form>
       </c:if>
    </body>
    <script type="text/javascript">
    	 //获取城市
		function addsearchfind(){
			var str=$("#province_id option:selected").val();//获取被选中的value值
			$.ajax({
				  url: '<%=path%>/zhihui_subsidiary/citylist.do',
				  data:"province_id="+str,
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.citylist;
					  	$("#city_id option").remove();
 					  	$("#area_id option").remove();
 					  	$("#city_name").val("");
 					  	$("#area_name").val("");
 					  	$("#city_id").append("<option value=''>请选择市</option>");
 					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$("#city_id").append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
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
			var str=$("#city_id option:selected").val();//获取被选中的value值
			$.ajax({
				  url: '<%=path%>/zhihui_subsidiary/arealist.do',
				  data:"city_id="+str,
				  type:"post",
				  dataType:"json",
 				  success:function(data){
					  	var list=data.arealist;
					  	$("#area_id option").remove();
					  	$("#area_id").append("<option  value=''>请选择区</option>");
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$("#area_id").append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
						  	}
				  		}
				  },
				  error:function(a){
				  alert("异常");
				  }
			});
		}
		//---------------------------
		//查询提交
			function search(){
				if($("#city_id option:selected").val() != ""){
					var city_name=$("#city_id option:selected").text();
 					$("#city_name").val(city_name);
				}
				if($("#province_id option:selected").val() != ""){
					var province_name=$("#province_id option:selected").text();
 					$("#province_name").val(province_name);
				}
				if($("#area_id option:selected").val() != ""){
					var area_name=$("#area_id option:selected").text();
 					$("#area_name").val(area_name);
				}
		    	$("#Form").submit();
		    }
    
		//导出excel表格
		function daochuexcel(){
			var city_name=$("#city_id option:selected").text();
			var province_name=$("#province_id option:selected").text();
			var area_name=$("#area_id option:selected").text();
			var user_type=$("#user_type option:selected").val();
			var account_type=$("#account_type option:selected").val();
			var withdraw_type=$("#withdraw_type option:selected").val();
			var withdraw_status=$("#withdraw_status option:selected").val();
			var apply_time=$("#apply_time").val();
			var content=$("#content").val();
			//导出excel
 			window.location.href='<%=basePath%>zhihui_withdraw_approval/excel.do?city_name='+city_name+
 					'&province_name='+province_name+'&area_name='+area_name+ 
 					'&user_type='+user_type+'&account_type='+account_type+
 					'&apply_time='+apply_time+'&content='+content+
 					'&withdraw_type='+withdraw_type+'&withdraw_status='+withdraw_status;
		}		
     </script>
</html>