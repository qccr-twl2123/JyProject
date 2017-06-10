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
        <title>档案管理</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <!-- 分页 -->
		<link rel="stylesheet" href="css/ace.min.css" />
 		<link href="css/bootstrap.min.css" rel="stylesheet" /> 
         <!--  -->
        <link rel="stylesheet" href="css/zhihui/dangan13.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/dangan13.js"></script>
        <script src="js/zhihui/bg.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
     <form action="zhihui_member/list.do" method="post" name="Form" id="Form">
       <div class="dangan2_d1">
       		<c:if test="${pd.isshenhe eq '1' }">
       			<select class="dangan2_d1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
				    <option value="">请选择省</option>
				     <c:forEach items="${provincelist}" var="var" varStatus="vs">
							<option value="${var.pcd_id}" >${var.name}</option>
					</c:forEach>
					<c:if test="${pd.province_id ne '' and  !empty pd.province_id}">
							<option value="${pd.province_id}"  selected="selected">${pd.province_name}</option>
					</c:if>
				</select>
 				<select class="dangan2_d1_st1" id="city_id" name="city_id" onchange="addsearcharea();">
					  <option value="${pd.city_id}">${pd.city_name}</option>
				</select>
       		</c:if>
  			 <select class="dangan2_d1_st1" id="area_id" name="area_id">
				       <option value="${pd.area_id}" >${pd.area_name}</option>
			 </select>
			 <input type="hidden" name="province_name" id="province_name" value="${pd.province_name}"  />
		     <input type="hidden" name="city_name" id="city_name" value="${pd.city_name}"  />
		     <input type="hidden" name="area_name" id="area_name" value="${pd.area_name}"  />
	         <input type="text" placeholder="可输入会员名称号等查询" name="content" class="dangan2_d1_ipt1" value="${pd.content}" />
	         <span class="dangan2_d1_btncx" onclick="search()">查询</span>
       </div>
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr class="tdtop">
                 <td rowspan='2'>ID</td>
                <td rowspan='2'>账号</td>
                <!-- <td rowspan='2'>微信标识ID</td> -->
                <td rowspan='2'>名称</td>
                <!-- <td rowspan='2'>性别</td> -->
                <td rowspan='2'>推荐人</td>
                <td rowspan='2'>推荐人类型</td>
                <!-- <td rowspan='2'>状态</td> -->
                <td colspan='3'>最近所在区域</td>
                <td colspan='5'>付费方式次数</td>
                <td rowspan='2'>消费总额</td>
                <td rowspan='2'>注册时间</td>
                <td rowspan='2'>注册渠道</td>
              <!--   <td rowspan='2'>操作</td> -->
              </tr>   
              <tr>
                <td>省</td>
                <td>市</td>
                <td>区/县</td>
                <td>现金</td>
                <td>微信</td>
                <td>支付宝</td>
                <!--<td>银行卡</td>
                <td>Apple-play</td> -->
                <td>积分支付</td>
                <td>余额支付</td>
    <!--             <td></td>
                <td></td>
                <td></td> -->
              </tr>   
              <c:forEach items="${varList}" var="var" varStatus="vs">
	              <tr>
 	                <td > ${var.show_lookid}</td>
	                <td>
 		                <span id ="phone1"> ${var.phone}</span>
		                <%-- <input id = "phone2" value="${var.phone}" hidden="hidden" style="width: 100px" maxlength="11"
		onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/> --%>
	               </td>
	                <%-- <td>${var.wxopen_id}</td> --%>
	                <td>${var.name}</td>
	                <%-- <td>
	                	<c:if test="${var.sex eq '1'}">男</c:if>
	                	<c:if test="${var.sex eq '2'}">女</c:if>
	                </td> --%>
	                <td>
	                	${var.recommended_name}
	               </td>
	                <td>
	                	<c:if test="${var.recommended_type eq '1'}">商家推荐</c:if>
	                	<c:if test="${var.recommended_type eq '2'}">会员推荐</c:if>
	                	<c:if test="${var.recommended_type eq '0'}">/</c:if>
	                </td>
	               <%--  <td>
	                	<c:if test="${var.status eq '0'}">关闭</c:if>
	                	<c:if test="${var.status eq '1'}">启用</c:if>
	                </td> --%>
	                <td>${var.province_name}</td>
	                <td>${var.city_name}</td>
	                <td>${var.area_name}</td>
	                <td>${var.money_pay_number}</td>
	                <td>${var.wechat_pay_number}</td>
	                <td>${var.alipay_pay_number}</td>
 	                <td>${var.integral_pay_number}</td>
 	                <td>${var.balance_pay_number}</td>
	                <td>${var.consumption}</td>
	                <td>${var.createdate} </td>
	                <td>
		                <c:if test="${var.zhuce_shebei eq '1'}">C端注册</c:if>
		                <c:if test="${var.zhuce_shebei eq '2'}">B端注册</c:if>
		                <c:if test="${var.zhuce_shebei eq '3'}">微信端注册</c:if>
		                <c:if test="${var.zhuce_shebei eq '4'}">会员PC端注册</c:if>
		                <c:if test="${var.zhuce_shebei eq '4'}">商家PC端注册</c:if>
	                </td>
	               <%--  <td><a href="zhihui_member/goEdit.do?member_id=${var.member_id}&currentPage=${page.currentPage}"><span class="td_sp1">修改</span></a>
	                	<a href="zhihui_member/delete.do?member_id=${var.member_id}&currentPage=${page.currentPage}" id="del"><span class="td_sp1">删除</span></a>
	                	<a onclick="updPhone()" id="upd">修改</a>
	                	<a onclick="updOk('${var.member_id}')" id="queding" hidden="hidden">确定</a>
	                </td> --%>
	              </tr>
              </c:forEach>
          </table>
       </div>
      
       </form>
       <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
   </c:if>
    </body>
    <script type="text/javascript">
   /*  function updPhone(){
    	$("#phone1").hide();
    	$("#phone2").show();
    	$("#upd").hide();
    	$("#del").hide();
    	$("#queding").show();
    } */
    
   <%--  function updOk(id){
    	var phone = $("#phone2").val().trim();
			$.ajax({
					type:"post",
	                url:'<%=path%>/zhihui_member/goEdit.do', 
	                data:{
	                	phone:phone,
 			         	member:id
 	                },
	                success: function(data){
	                },
	                error:function(a){
				  		alert("异常");
				  }
	       });
    } --%>
    
  //---------------------------提交----------
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
    //获取城市
		function addsearchfind(){
			var str=$("#province_id option:selected").val();//获取被选中的value值
			$.ajax({
				  url: 'zhihui_subsidiary/citylist.do',
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
				  url: 'zhihui_subsidiary/arealist.do',
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
    </script>
</html>