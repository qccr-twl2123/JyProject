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
        <title>支付管理</title>
        <meta charset="utf-8">
         <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
         <link rel="stylesheet" href="css/ace.min.css" />
         <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/zhihui/zhifu2.css">
         <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/zhifu2.js"></script>
        <script src="js/zhihui/bg.js"></script>
         <style type="text/css">
        	input[type=checkbox]{
			    opacity: 1;
			    position: initial;
			    background-color: white;
 			}
        </style>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihui_withdraw_approval/list.do" name="Form" id="Form" method="post">
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
	           <%-- <option value="4" ${pd.user_type eq '4'?'selected':''}>业务员</option> --%>
	         </select>
	         <input class="zhifu1_ipt1" type="text" name="content" placeholder="可输入账号/金额等查询"></input>
       </div>
       <div class="dangan2_d1">
         <span  class="zhifu1_sp1">申请时间</span>
          <input class="zhifu1_st1" type="text" name="apply_time" id="apply_time" value="${pd.apply_time}"  placeholder="申请时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
          <span class="zhifu1_btn1" onclick="search()">查询</span>
          <c:if test="${qx.edit eq '1'}">
          	<span class="zhifu1_btn1" onclick="makeAll('确定要选中的通过么?');">批量通过</span>
          </c:if>
       </div>
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr class="tdtop">
              	<th class="center">
					<label><input type="checkbox" id="zcheckbox" class="dangan14_ipt1 dangan14iptclick"/></label>
				</th>
                <td>序号</td>
                <td>省</td>
                <td>市</td>
                <td>区/县</td>
                <td>用户类型</td>
                <td>联系电话</td>
                <td>款项金额</td>
                <td>当前的提现用户余额</td>
                <td>到账方式</td>
                <td> 用户名&nbsp;&nbsp;&nbsp;开户行&nbsp;&nbsp;&nbsp;提款账号 </td>
                <td>提款方式</td>
                <td>提现状态</td>
                <td>备注</td>
                <td>申请时间</td>
              </tr>
              <c:forEach items="${varList}" var="var" varStatus="vs">
              <tr >
               <td class='center' style="width: 30px;">
					<label><input type='checkbox' name='ids' class="dangan14_ipt1" value="${var.withdraw_approval_id}" /></label>
				</td>
                <td>${vs.index+1}</td>
                <td>${var.province_name}</td>
                <td>${var.city_name}</td>
                <td>${var.area_name}</td>
                <td>
	                <c:if test="${var.user_type==1}">商家</c:if>
	                <c:if test="${var.user_type==2}">会员</c:if>
	                <c:if test="${var.user_type==4}">业务员</c:if>
                 	<c:if test="${var.user_type==3}">服务商</c:if>
                </td>
                <td>${var.phone }</td>
                <td>${var.money }</td>
                <td style="color:blue;">${var.nowmoney }</td>
                <td>
	                <c:if test="${var.account_type==1}">现金</c:if>
	                <c:if test="${var.account_type==2}">微信</c:if>
	                <c:if test="${var.account_type==3}">支付宝</c:if>
	                <c:if test="${var.account_type==4}">银行卡</c:if>
	                <c:if test="${var.account_type==5}">Apple-play</c:if>
	                <c:if test="${var.account_type==6}">积分支付</c:if>
                </td>
                <td>${var.withdraw_username }&nbsp;&nbsp;&nbsp;${var.account_name }&nbsp;&nbsp;&nbsp;${var.withdraw_number }</td>
                 <c:if test="${qx.edit eq '1'}">
                <td>
	                	<a href="zhihui_withdraw_approval/edit.do?withdraw_approval_id=${var.withdraw_approval_id}&withdraw_type=1&withdraw_status=1">手动提现</a>
	                	<a href="zhihui_withdraw_approval/edit.do?withdraw_approval_id=${var.withdraw_approval_id}&withdraw_type=2&withdraw_status=1">自动提现</a>
                </td>
                <td>
 	                	<a href="zhihui_withdraw_approval/edit.do?withdraw_approval_id=${var.withdraw_approval_id}&withdraw_status=1&withdraw_type=1">审批通过</a> 
	               		<a onclick="bohui('${var.withdraw_approval_id}')">审批驳回</a>
                </td>
                </c:if>
                <td>${var.remark }</td>
                <td>${fn:substring(var.apply_time,0,19)}    </td>
              </tr>
              </c:forEach>
          </table>
          <div class="pagination" style="float:right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
       </div>
       </form>
       </c:if>
       <div class="out_tc">
          <p class="out_tc_p1">备注信息 <span class="uot2">X</span></p>
          <textarea rows="10" cols="50" class="out_text" name="remark" id="remark"></textarea>
          <c:if test="${qx.edit eq '1'}">
          <span class="out_tc_yes" onclick="saveramark()">保存</span>
          </c:if>
          <span class="out_tc_none">取消</span>
       </div>
    </body>
   <!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/ace-elements.min.js"></script>
		<script src="js/ace.min.js"></script>
		
		<script type="text/javascript" src="js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		/* $(function(){  
		  $("table").colResizable();  
		}); 
		 */
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
					$("#city_name").show();
					$("#city_name").val(city_name);
				}
				if($("#province_id option:selected").val() != ""){
					var province_name=$("#province_id option:selected").text();
					$("#province_name").show();
					$("#province_name").val(province_name);
				}
				if($("#area_id option:selected").val() != ""){
					var area_name=$("#area_id option:selected").text();
					$("#area_name").show();
					$("#area_name").val(area_name);
				}
		   		 $("#Form").submit();
		    }
		    
		
		 //批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									//Example.show("great success");
									}
								}
							 ]
						);
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						return;
					}else{
						if(msg == '确定要选中的通过么?'){
							$.ajax({
								type: "POST",
								url: '<%=basePath%>/zhihui_withdraw_approval/editAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}
					}
				}
			});
		}
		 
		 
		 var withdraw_approval_id="";
		 //审批驳回
		 function bohui(value){
 			$('.out_tc').css('display','block');
 			withdraw_approval_id=value;
 		 }
		 //保存
		 function saveramark(){
			 var remark=$("#remark").val();
 			 window.location.href="zhihui_withdraw_approval/edit.do?withdraw_approval_id="+withdraw_approval_id+"&withdraw_status=1&withdraw_type=1&remark="+remark;
 		 }
    </script>
</html>