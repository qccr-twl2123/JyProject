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
        <link rel="stylesheet" href="css/zhihui/yingxiao2.css">
         <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/yingxiao2.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    	<form action="zhihuicity_marketing/${msg}.do" name="Form" id="Form" method="post" >
    		<input type="hidden" name="province_name" id="province_name" value="${pd.province_name}">
    		<input type="hidden" name="city_name" id="city_name" value="${pd.city_name}">
    		<input type="hidden" name="area_name" id="area_name" value="${pd.area_name}">
    		<input type="hidden" name="city_marketing_id" id="city_marketing_id" value="${pd.city_marketing_id}">
    		<input type="hidden" name="currentPage" id="currentPage" value="${pd.currentPage}">
    		<input type="hidden" name="alloneid" id="alloneid" value="${pd.alloneid}">
    		<input type="hidden" name="alltwoid" id="alltwoid" value="${pd.alltwoid}">
    		<input type="hidden" name="allthreeid" id="allthreeid" value="${pd.allthreeid}">
    		<input type="hidden" name="allfourid" id="allfourid" value="${pd.allfourid}">
    		<input type="hidden" name="allsevenid" id="allsevenid" value="${pd.allsevenid}">
    		<%-- <input type="text" name="allfiveid" id="allfiveid" value="${pd.allfiveid}" style="display:none;"> --%>
    		<input type="hidden" name="allsixid" id="allsixid" value="${pd.allsixid}">
    		<input type="hidden" name="alleightid" id="alleightid" value="${pd.alleightid}">
    		<input type="hidden" name="oneList" id="oneList" value="${oneList}">
         	<div class="yingxiao2_d1">
         	<c:if test="${qx.add eq '1'}">
	             <a class="middle_a" onclick="saveReturn()">
	                      <div class="bctc" >保存并退出</div>
	            </a>
	            </c:if>
        </div>
        <div class="yingxiao2_d1 mgtop10">
          <span class="yingxiao2_d1_sp1">设置一：出现在开机红包中的商家</span>
          <c:if test="${qx.add eq '1'}">
          <a class="middle_a" href="zhihuicity_marketing/goAdd.do?city_file_id=${pd.city_file_id}&city_marketing_id=${pd.city_marketing_id}&currentPage=${pd.currentPage}"  target="ifra">
             <span class="yingxiao2_d1_btn1 ">添加商家</span>
          </a>
          </c:if>   
        </div>
        <div>
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr >
                <td>序号</td>
                <td>商家</td>
                <td>红包详情</td>
                <td>有效时间</td>
                <td>编辑</td>
              </tr> 
             <c:forEach items="${oneList}" var="var" varStatus="vs">
	             <tr>
	                <td><input type="hidden" name="${var.cm_one_id}store_redpackets_id" value="${var.store_redpackets_id}">${vs.index+1}</td>
	                <td>${var.store_name}</td>
	                <td>
	                	${var.money}元红包，${var.redpackage_number}个
	                </td>
 	                <td>
 	                	${var.starttime}至${var.endtime}
 	                </td>
 	                <c:if test="${qx.delete eq '1'}">
	                <td class="blue"><a onclick="deleteRedStore(this)">删除</a></td>
	                </c:if>
	              </tr>
             </c:forEach>
           </table>
       </div>
       <br/>
        <div class="yingxiao2_d1">
          <span class="yingxiao2_d1_sp2">(说明：该项未设置时，默认自动有红包的商家中获取；红包数量最低20个；红包领完的商家自动删除。)</span>
        </div>
        <div class="yingxiao2_d1">
          <span class="yingxiao2_d1_sp1">设置二：会员获取开机红包的来源</span>
        </div>
         <div class="yingxiao2_d1">
         	<c:forEach items="${twoList}" var="var">
         		<label>
	              <input type="checkbox"  class="yingxiao2_d1_ipt1" ${var.check_status eq '1'?'checked':''} id="two" onclick="changechecked(this)"  value="${var.cm_two_id}"/>
	              <span class="yingxiao2_d1_sp3">${var.source_name}</span>
	              <input type="text" name="${var.cm_two_id}check_status" value="${var.check_status}" style="display:none;width:1px;height:1px;">
	            </label>
          	</c:forEach>
        </div>
        <div class="yingxiao2_d1">
          <span class="yingxiao2_d1_sp1">设置三：会员抢附近商家红包的规则</span>
        </div>
        <div class="yingxiao2_d1">
        	<c:forEach items="${threeList}" var="var">
        		<c:if test="${var.rule_type eq '1' }">
        			<select class="yingxiao2_d1_st1" name="${var.cm_three_id}rule_status">
		              <option value="1" ${var.rule_status eq '1'?'selected':''}>商家综合分值从高到低</option>
		              <option value="2" ${var.rule_status eq '2'?'selected':''}>商家综合分值从低到高</option>
		              <option value="3" ${var.rule_status eq '3'?'selected':''}>商家距离从远到近</option>
		              <option value="4" ${var.rule_status eq '4'?'selected':''}>商家距离从近到远</option>
		            </select>
        		</c:if>
        	<%-- 	<c:if test="${var.rule_type eq '2' }">
	 	            <select class="yingxiao2_d1_st1" name="${var.cm_three_id}rule_status">
		              <option value="1" ${var.rule_status eq '1'?'selected':''}>商家距离从远到近</option>
		              <option value="2" ${var.rule_status eq '2'?'selected':''}>商家距离从近到远</option>
		            </select>
	            </c:if> --%>
	            <c:if test="${var.rule_type eq '3' }">
		            <select class="yingxiao2_d1_st1" name="${var.cm_three_id}rule_status">
		              <option value="1" ${var.rule_status eq '1'?'selected':''}>最大距离1KM</option>
		              <option value="2" ${var.rule_status eq '2'?'selected':''}>最大距离2KM</option>
		            </select>
	            </c:if>
            </c:forEach>
        </div>
        <div class="yingxiao2_d1">
          <span class="yingxiao2_d1_sp1">设置四：会员领开机红包的频数和刷新时间</span>
        </div>
         <div class="yingxiao2_d1">
            <c:forEach items="${fourList}" var="var">
	        	<select class="yingxiao2_d1_st1" name="${var.cm_four_id}refresh_type">
	              <option value="1" ${var.refresh_type eq '1'?'selected':''}>每天一次</option>
	              <option value="2" ${var.refresh_type eq '2'?'selected':''}>每天两次</option>
	            </select>
	            <span class="yingxiao2_sp4">刷新时间</span>
	            <span class="yingxiao2_sp4">第一次</span>
	            <input type="text" class="yingxiao2_d1_st1" name="${var.cm_four_id}onerefresh_time" value="${var.onerefresh_time}" placeholder="第一次" onclick="WdatePicker({dateFmt:'HH:ss:mm'})"/>
	            <span class="yingxiao2_sp4">第二次</span>
	            <input type="text" class="yingxiao2_d1_st1" name="${var.cm_four_id}tworefresh_time" value="${var.tworefresh_time }" placeholder="第二次" onclick="WdatePicker({dateFmt:'HH:ss:mm'})"/>
	        </c:forEach>
        </div>
         <div class="yingxiao2_d1">
          <span class="yingxiao2_d1_sp2">(说明：刷新时间的意思是当天在该时间段之后第一次打开APP，会自动获取红包。)</span>
        </div>
        <div class="yingxiao2_d1">
          <span class="yingxiao2_d1_sp1">设置五：超出商品限度的费用</span>
        </div>
        <div class="yingxiao2_d1">
            <c:forEach items="${sevenList}" var="var">
           	   <span class="yingxiao2_d1_sp5">超出商品限度的费用</span>
	           <input class="yingxiao2_d1_st1" type="text" name="${var.cm_seven_id}billing_money" value="${var.billing_money }"/>
	           <span class="yingxiao2_d1_sp5">元/天，结算时间</span>
	           <input class="yingxiao2_d1_st1" type="text" value="${var.billing_time}" name="${var.cm_seven_id}billing_time" onclick="WdatePicker({dateFmt:'HH:ss:mm'})"/>
           </c:forEach>
        </div>
        <div class="yingxiao2_d1">
          <span class="yingxiao2_d1_sp1">设置六：收费标准</span>
        </div>
      
        	<div class="yingxiao2_d1">
	           <span >默认收费：</span>
	           <span class="mgleft">一星</span>
	           	￥<input class="yingxiao2_ipt1" type="text" id="one" value="${fee.onexing_money }"/>
	           <span >二星</span>
	           	￥<input class="yingxiao2_ipt1" type="text" id="two" value="${fee.twoxing_money }"/>
	           <span >三星</span>
	           	￥<input class="yingxiao2_ipt1" type="text" id="three" value="${fee.threexing_money }"/>
	           	<c:if test="${qx.edit eq '1'}">
	           <span class="yingxiao2_d1_btn1_two" onclick="savexingfee()">保存</span>
	           </c:if>
	        </div>
	     <c:forEach items="${fiveList}" var="var">
	     	<div class="yingxiao2_d1">
	     	<c:choose>
	     		<c:when test="${var.sort_type eq '1' }">
	     			   <span >选择行业</span>
			           <select class="yingxiao2_d1_st1"  onchange="changeleibie(this)">
				              <option>请选择一级</option>
				              <c:forEach items="${firstList}" var="first">
				              		<option value="${first.city_file_sort_id}" ${var.city_file_sort_id eq first.city_file_sort_id?'selected':''}>${first.sort_name}</option>
				              </c:forEach>
			           </select>
			           <select class="yingxiao2_d1_st1" >
			              <option value=""></option>
			           </select>
			           <span class="mgleft">一星</span>
			           	￥<input class="yingxiao2_ipt1" type="text" placeholder="￥" value="${var.onexing_money }"  />
			           <span >二星</span>
			           	￥<input class="yingxiao2_ipt1" type="text" placeholder="￥"  value="${var.twoxing_money }"  />
			           <span >三星</span>
			           	￥<input class="yingxiao2_ipt1" type="text" placeholder="￥"  value="${var.threexing_money }"   />
			           	<c:if test="${qx.save eq '1'}">
			        	<span class="yingxiao2_d1_btn1_two" onclick="savefee(this)">保存</span>
			        	</c:if>
			        	<c:if test="${qx.delete eq '1'}">
			           	<span class="yingxiao2_d1_btn1_two" onclick="delfee(this)">删除</span>
			           	</c:if>
	     		</c:when>
	     		<c:otherwise>
	     			   <span >选择行业</span>
			           <select class="yingxiao2_d1_st1" onchange="changeleibie(this)">
				              <option>请选择一级</option>
				              <c:forEach items="${firstList}" var="first">
				              		<option value="${first.city_file_sort_id}" ${var.sort_parent_id eq first.city_file_sort_id?'selected':''}>${first.sort_name}</option>
				              </c:forEach>
			           </select>
			           <select class="yingxiao2_d1_st1" >
			              <option value="${var.city_file_sort_id}">${var.sort_name}</option>
			           </select>
			           <span class="mgleft">一星</span>
			           	￥<input class="yingxiao2_ipt1" type="text" placeholder="￥" value="${var.onexing_money }"  />
			           <span >二星</span>
			           	￥<input class="yingxiao2_ipt1" type="text" placeholder="￥"  value="${var.twoxing_money }" />
			           <span >三星</span>
			           	￥<input class="yingxiao2_ipt1" type="text" placeholder="￥"  value="${var.threexing_money }" />
			           	<c:if test="${qx.edit eq '1'}">
			        	<span class="yingxiao2_d1_btn1_two" onclick="editfee(this)">修改</span>
			        	</c:if>
			        	<c:if test="${qx.delete eq '1'}">
			           	<span class="yingxiao2_d1_btn1_two" onclick="delfee(this)">删除</span>
			           	</c:if>
	     		</c:otherwise>
	     	</c:choose>
  	        </div>
        </c:forEach>
        <div class="yingxiao2_d1" id="addnewfee">
        <c:if test="${qx.add eq '1'}">
	           <span class="yingxiao2_d1_btn1_two" onclick="addcopy()">+</span>
	           </c:if>
	    </div>
        <div class="yingxiao2_d1">
          <span class="yingxiao2_d1_sp1">设置七：提成与补贴</span>
        </div>
         <c:forEach items="${sixList}" var="var">
	         	<c:if test="${var.subsidy_type eq '1'}">
	         		<div class="yingxiao2_d1">
			          <span>收取保证金时，业务员提成</span>
			          <input class="yingxiao2_ipt1" type="text" value="${var.clerk_commission}" name="${var.cm_six_id}clerk_commission"/>%
			          <span class="yingxiao2_d1_sp5">服务商提成</span>
			          <input class="yingxiao2_ipt1" type="text" value="${var.service_provider_commission}" name="${var.cm_six_id}service_provider_commission"/>%
			        </div>
	         	</c:if>
	         	<c:if test="${var.subsidy_type eq '2'}">
	         		<div class="yingxiao2_d1">
			          <span>收取年费时，业务员提成</span>
			          <input class="yingxiao2_ipt1" type="text" value="${var.clerk_commission}" name="${var.cm_six_id}clerk_commission"/>%
			          <span class="yingxiao2_d1_sp5">服务商提成</span>
			          <input class="yingxiao2_ipt1" type="text" value="${var.service_provider_commission}" name="${var.cm_six_id}service_provider_commission"/>%
			        </div>
	         	</c:if>
	         	<c:if test="${var.subsidy_type eq '3'}">
		         	<div class="yingxiao2_d1">
			          <span>商家自然月销售流水达到</span>
			          <input class="yingxiao2_ipt1" type="text" value="${var.monthly_sales}" name="${var.cm_six_id}monthly_sales"/>
			          <span class="yingxiao2_d1_sp5">服务商另奖励</span>
			                         ￥<input class="yingxiao2_ipt1" type="text" value="${var.send_money}" name="${var.cm_six_id}send_money"/>
			        </div>
	         	</c:if>
 	       </c:forEach>
        <div class="yingxiao2_d1">
          <span class="yingxiao2_d1_sp1">设置八：PC搜索框下关键字设置</span>
        </div>
        <div class="yingxiao2_d1">
          <span>关键字</span>
          <c:forEach items="${eightList}" var="var">
          		<input class="yingxiao2_ipt1" type="text" value="${var.keyword}" id="keyword"/>
          </c:forEach>
          <input type="text" name="${pd.alleightid}keyword" style="display:none;" id="keywords">
        </div>
	</form>
	</c:if>
		<script type="text/javascript">
		
 		
		var keywords="";
		//保存并退出
		function saveReturn(){
 			//获取所有的关键字
       		var keywordobj = $('input[id="keyword"]'); //获取当前checked的value值 如果选中多个则循环
       		keywordobj.each(function() { 
       			var m= this.value; 
       			keywords +=m+"@"; 
       		}); 
			$("#keywords").show();
			$("#keywords").val(keywords);
			$("#Form").submit();
		}
		
		//删除红包商家
		function deleteRedStore(obj){
			$(obj).parent().parent().remove();
		}
		
		//改变开机红包的选择
		function changechecked(obj){
			$(obj).next().next().show();
			var value=$(obj).next().next().val();
			if(value=="1"){
				$(obj).next().next().val("0");
			}else{
				$(obj).next().next().val("1");
			}
			$(obj).next().next().hide();
		}
		
		
		//选择类别
		function changeleibie(obj){
			var value=$(obj).val();
			$.ajax({
				  url: 'zhihui_city_file/getSortList.do',
				  data:{"sort_parent_id":value},
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.sortlist;
					  	$(obj).next().empty();
					  	$(obj).next().append("<option value=''>请选择二级分类</option>");
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$(obj).next().append("<option value='"+list[i].city_file_sort_id+"'>"+list[i].sort_name+"</option>");
						  	}
					  	} 
					  	$(obj).next().next().next().val("");
					  	$(obj).next().next().next().next().next().val("");
					  	$(obj).next().next().next().next().next().next().next().val("");
				  }
			});
		}
		
		
		//设置默认星级收费
		function savexingfee(){
			var one=$("#one").val();
			var two=$("#two").val();
			var three=$("#three").val();
			//设置默认的星级收费
			$.ajax({
				  url: 'zhihui_city_file/setMoRengXingFee.do',
				  data:{
					  	  "onexing_money":one,
						  "twoxing_money":two,
						  "threexing_money":three,
						  "city_file_id":"${pd.city_file_id}",
						  "ismoreng":"0"
				  },
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	if(data.result == "01"){
					  		alert("设置成功");
					  	}
 				  }
			});
		}
		
		//新增新的星级收费
		function addcopy(){
 			var s="<div class='yingxiao2_d1'><span >选择行业</span>"+
			         "  <select class='yingxiao2_d1_st1 ' onchange='changeleibie(this)' id='first'>"+
		 	         "  </select>"+
			         "  <select class='yingxiao2_d1_st1'><option value=''></option></select>"+
			         "  <span class='mgleft'>一星</span>￥<input class='yingxiao2_ipt1' type='text' placeholder='￥'  />"+
			         "  <span >二星</span>￥<input class='yingxiao2_ipt1' type='text' placeholder='￥'  />"+
			         "  <span >三星</span>￥<input class='yingxiao2_ipt1' type='text' placeholder='￥'  />"+
			         "  <span class='yingxiao2_d1_btn1_two' onclick='editfee(this)'>修改</span>"+
			         "  <span class='yingxiao2_d1_btn1_two' onclick='delfee(this)'>删除</span>"+
			    "</div>";
			 $("#addnewfee").before(s);
			 //获取一级分类
			 $.ajax({
				  url: 'zhihui_city_file/getSortList.do',
				  data:{"city_file_id":"${pd.city_file_id}","sort_type":"1"},
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.sortlist;
 					  	$("#first").append("<option value=''>请选择一级分类</option>");
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$("#first").append("<option value='"+list[i].city_file_sort_id+"'>"+list[i].sort_name+"</option>");
						  	}
					  	} 
					  	$("#first").removeAttr("id");
				  }
			});
 		}
			
		
		//修改星级分类星级收费
		function editfee(obj){
			var city_file_sort_id="";
			var one=$(obj).prev().val();
			var two=$(obj).prev().prev().prev().val();
			var three=$(obj).prev().prev().prev().prev().prev().val();
			if($(obj).prev().prev().prev().prev().prev().prev().prev().val() == ""){
				city_file_sort_id=$(obj).prev().prev().prev().prev().prev().prev().prev().prev().val();
			}else{
				city_file_sort_id=$(obj).prev().prev().prev().prev().prev().prev().prev().val();
			}
			//设置修改默认的星级收费
			$.ajax({
				  url: 'zhihui_city_file/setMoRengXingFee.do',
				  data:{
					  	  "onexing_money":one,
						  "twoxing_money":two,
						  "threexing_money":three,
						  "city_file_id":"${pd.city_file_id}",
						  "city_file_sort_id":city_file_sort_id,
						  "ismoreng":"1"
				  },
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  if(data.result == "01"){
					  		alert("设置成功");
					  	}
 				  }
			});
		}
		//删除星级收费
		function delfee(obj){
			var city_file_sort_id="";
			var one=$("#one").val();
			var two=$("#two").val();
			var three=$("#three").val();
			if($(obj).prev().prev().prev().prev().prev().prev().prev().prev().val() == ""){
				city_file_sort_id=$(obj).prev().prev().prev().prev().prev().prev().prev().prev().prev().val();
			}else{
				city_file_sort_id=$(obj).prev().prev().prev().prev().prev().prev().prev().prev().val();
			}
			//设置默认的星级收费
			$.ajax({
				  url: 'zhihui_city_file/setMoRengXingFee.do',
				  data:{
					  	  "onexing_money":one,
						  "twoxing_money":two,
						  "threexing_money":three,
						  "city_file_id":"${pd.city_file_id}",
						  "city_file_sort_id":city_file_sort_id,
						  "ismoreng":"0"
				  },
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	if(data.result == "01"){
					  		alert("设置成功");
					  		$(obj).parent().remove();
					  	}
 				  }
			});
		}
		</script>
    </body>
</html>