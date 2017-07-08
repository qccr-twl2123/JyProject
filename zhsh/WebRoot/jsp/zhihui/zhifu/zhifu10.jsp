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
        <link rel="stylesheet" href="css/zhihui/zhifu3.css">
         <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/zhifu3.js"></script>
        <script src="js/zhihui/bg.js"></script>
        <style type="text/css">
        input[type=checkbox]{
			    opacity: 1;
			    position: initial;
			    background-color: white;
 		}
         .zhifu1_sp1 {
    		margin-left: 3%;
		}
        </style>
     </head>
    <body>
     <c:if test="${qx.look eq '1'}">
     <form action="zhihuiWaterRecordController/listTxPage.do" name="Form" id="Form" method="post">
        <input type="hidden" name="pay_status" value="2" />
     	<input type="hidden" name="chuli_ok" value="0" />
     	<input type="hidden" name="remittance_type" value="3" />
       <div class="dangan2_d1">
           <span  class="zhifu1_sp1">区域 </span>
		           <select class="zhifu1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
			           	<c:if test="${pd.province_id ne '' and  !empty pd.province_id}">
									<option value="${pd.province_id}"  selected="selected">${pd.province_name}</option>
						</c:if>
			           	<option value="">请选择省</option>
			           	<c:forEach items="${provincelist}" var="var" varStatus="vs">
							<option value="${var.pcd_id}">${var.name}</option>
						</c:forEach>
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
          <%--  <option value="4" ${pd.user_type eq '4'?'selected':''}>业务员</option> --%>
         </select>
       </div>
       <div class="dangan2_d1" style="margin-right: 50px">
        <span  class="zhifu1_sp1" >申请时段</span>
		  <input placeholder="开始时间" class="zhifu1_st1" type="text" name="starttime" id="starttime" value="${pd.starttime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
          &nbsp;&nbsp;
          <span>至</span>
		  <input placeholder="结束时间" class="zhifu1_st1" type="text" name="endtime" id="endtime" value="${pd.endtime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
       </div>
       <div class="dangan2_d1">
          <span  class="zhifu1_sp1">关键字查询</span>
          <input class="zhifu1_ipt1" type="text" name="content" id="content" placeholder="输入提现账号/提现用户名"></input>
          <span class="zhifu1_btn1" onclick="search()">查询</span>
          <c:if test="${qx.edit eq '1'}">
          	<span class="zhifu1_btn1" onclick="caozuostatus('1')">已付款</span>
          	<span class="zhifu1_btn1" onclick="caozuostatus('2')">转为异常</span>
          </c:if>
           	<span class="zhifu1_btn1" onclick="daochuexcel()">导出excel表格</span>
        </div>
       
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table" style="    white-space: nowrap;line-height:36px">
              <tr class="tdtop">
              	<td><input type="checkbox" id = "setAll" class="setAll"/></td>
              	<td>序号</td>
                <td>订单号</td>
                <td>省</td>
                <td>市</td>
                <td>县/区</td>
                <td>申请时间</td>
                <td>用户类型</td>
                <td>提现ID号</td>
                <td>用户名称</td>
                <td>联系方式</td>
                <td>应到账金额</td>
                <td>支付宝账号</td>
                <td>真实姓名</td>
                <td>异常标注</td>
                <td>打款标注</td>
              </tr>
             <c:forEach items="${varList}" var="var" varStatus="vs">
              <tr >
              	<td><input type="checkbox" name = "check"  value="${var.waterrecord_id}" /></td>
                <td>${vs.index+1}</td>
                <td>${var.waterrecord_id}</td>
                <td>${var.province_name}</td>
                <td>${var.city_name}</td>
                <td>${var.area_name}</td>
                <td>${var.createtime}</td>
                <td>
	                <c:if test="${var.user_type eq '1'}">商家</c:if>
	                <c:if test="${var.user_type eq '2'}">会员</c:if>
	                <c:if test="${var.user_type eq '3'}">服务商</c:if>
                   <%--  <c:if test="${var.user_type eq '4'}">业务员</c:if> --%>
                </td>
                <td>${var.user_id}</td>
                <td>${var.user_name}</td>
                <td>${var.phone}</td>
                <td class="tx_money">${var.arrivalmoney }</td>
                <td>${var.remittance_number }</td>
                <td>${var.remittance_name }</td>
                 <td><input id='exception${var.waterrecord_id}' value='${var.exception_remark}'/></td>
                 <td><input id='dakuan${var.waterrecord_id}' value='${var.dakuan_remark}'/></td>
              </tr>
              </c:forEach>
             <tr>
                 <td colspan="11">本页合计应到账金额：</td>
                 <td><span  class="benye_money">${nowpagesum.sumarrivalmoney}</span></td>
                 <td colspan="4">/</td>
               </tr>
              <tr>
                 <td colspan="11">总合计应到账金额：</td>
                 <td><span  class="benye_money">${allpagesum.sumarrivalmoney}</span></td>
                 <td colspan="4">/</td>
               </tr>
            </table>
       </div>
        <br>
       <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
       </form>
       </c:if>
     </body>
    <script type="text/javascript">
  //复选框的全选和全不选
	$(function() {
       $(".setAll").click(function() {
            $('input[name="check"]').attr("checked",this.checked); 
            checkboxsure();
        });
        var $subBox = $("input[name='check']"); 
        $subBox.click(function(){
            $(".setAll").attr("checked",$subBox.length == $("input[name='check']:checked").length ? true : false);
        });
  });
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
					  	$("#area_id").append("<option value=''>请选择区</option>");
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
					$("#c").show();
					$("#province_name").val(province_name);
				}
				if($("#area_id option:selected").val() != ""){
					var area_name=$("#area_id option:selected").text();
					$("#area_name").show();
					$("#area_name").val(area_name);
				}
		    	$("#Form").submit();
		    }
    
		
		//导出excel表格
		function daochuexcel(){
				var abnormal = document.getElementsByName('check');
				var allwaterrecord_id = '';
		    	//获取异常选择的value
				for ( var i = 0; i < abnormal.length; i++) {
					if(abnormal[i].checked){
						var id=abnormal[i].value;
						allwaterrecord_id += id+',';
					}
				}
				if(allwaterrecord_id==''){
		    		alert('你还没有选择任何内容！');
		    		return;
		    	} 
				//导出excel
	 			window.location.href='<%=basePath%>zhihuiWaterRecordController/Alipayexcel.do?allwaterrecord_id='+allwaterrecord_id;
		}	
		
		
		 //已转账或转为异常
	    function caozuostatus(value){
	    	var abnormal = document.getElementsByName('check');
	    	var exception_status="";
	    	if(value == "2"){
	    		exception_status="2";
	    	}
	    	var abno = '';
	    	//获取异常选择的value
			for ( var i = 0; i < abnormal.length; i++) {
				if(abnormal[i].checked){
					var id=abnormal[i].value;
					var exception=$("#exception"+id).val();
					var dakuan=$("#dakuan"+id).val();
					if(value == "1"){
						var allxinxi=dakuan+"@"+id;
					}else if(value == "2"){
						var allxinxi=exception+"@"+id;
					}
	 				abno += allxinxi+',';
				}
			}
			if(abno==''){
	    		alert('你还没有选择任何内容！');
	    		return;
	    	}
			$.ajax({
				type:"post",
				url:'<%=path%>/zhihuiWaterRecordController/updateAll.do',
				dataType:"json",
					data:{
						    abno :abno,
	 						pay_status :  value ,
	 						exception_status :exception_status
					},
				success:function(data){
					if(data.result == "01"){
						window.location.reload(); //刷新当前页面
					}else if(data.result == "00"){
						alert("操作失败");
					}
				}
			}); 
	    
	    }
		
		
     </script>
</html>