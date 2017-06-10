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
        <link rel="stylesheet" href="css/zhihui/dangan6.css">
     </head>
    <body>
    <c:if test="${qx.look eq '1'}">
	    <form action="zhihui_sp_file/${msg}.do" name="Form" id="Form" method="post">
			<input type="hidden" name="allcity_file_sort_id" id="allcity_file_sort_id" value=""/>
			<input type="hidden" name="oldallcity_file_sort_id" id="oldallcity_file_sort_id" value="${pd.allcity_file_sort_id }"/>
			<input type="hidden" name="sp_file_id" id="sp_file_id" value="${pd.sp_file_id}"/>
			<input type="hidden" name="menu_role_id" id="menu_role_id" value="1"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${pd.currentPage}"/>
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">团队名称</span>
	          <input type="text" class="dangan_d1_ipt1" name="team_name" id="team_name" value="${pd.team_name}" onchange="isname()"></input>
	          <span class="dangan_d1_sp1">隶属子公司名称</span>
	          <select  name="subsidiary_id" id="subsidiary_id" class="dangan_d1_ipt1" onchange="getPcdList(this)">
	          		<c:if test="${msg eq 'save' }">
	          			<option value="">请选择子公司</option>
	          		</c:if>
	          		<c:if test="${msg eq 'edit' }">
	          			<option value="${pd.subsidiary_id}">${pd.subsidiary_name}</option>
	          		</c:if>
	          		<c:forEach items="${spList}" var="var">
	          			<option value="${var.subsidiary_id}" ${var.subsidiary_id eq pd.subsidiary_id?'selected':'' }>${var.subsidiary_name}</option>
	          		</c:forEach>
	          </select>
 	        </div>
 
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">工商名称</span>
	          <input type="text" class="dangan_d1_ipt1" name="industry_commerce_name" id="industry_commerce_name" value="${pd.industry_commerce_name}"></input>
	          <span class="dangan_d1_sp1">隶属城市</span>
	          <select  name="area_id" id="area_id" class="dangan_d1_ipt1"  onchange="getSortByarea()" >
 	          		<option value="${pd.area_id}">${pd.area_name}</option>
 	          		<c:forEach items="${areaList}" var="var">
	          			<option value="${var.area_id}" ${var.area_id eq pd.area_id?'selected':'' }>${var.area_name}</option>
	          		</c:forEach>
	          </select>
 	        </div>
 
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">办公地址</span>
	          <input type="text" class="dangan_d1_ipt1 ipt3" name="sp_address" id="sp_address" value="${pd.sp_address}"/>
	        </div>
	        <div class="dangan_d1" style="height:100%;">
	          <span class="dangan_d1_sp1">签约行业</span><br/>
	          <span class="firstsort" style=" display: block; width: 90%; margin: 0 auto; ">
	          	  <!-- 循环所有的未被选中的 -->
	          	  <c:forEach items="${sortlist}" var="var" varStatus="vs">
	          	  		<c:if test="${vs.index+1 eq '8' or vs.index+1 eq '15' }">
	          	  			 <span class="dangan_d1_sp1_w" style="width:141px;">&nbsp;&nbsp;&nbsp;&nbsp;</span><!-- 9 -->
		         			 <span class="dangan_d1_sp1_w"><input class="dangan_d1_sp1_w_input" ${fn:contains(pd.allcity_file_sort_id, var.city_file_sort_id) eq true?'checked':''} type="checkbox" id="xuan" value="${var.city_file_sort_id}"    >${var.sort_name}</span>
	          	  		</c:if>
	          	  		<c:if test="${vs.index+1 ne '8' and vs.index+1 ne '15'}">
 		         			 <span class="dangan_d1_sp1_w"><input class="dangan_d1_sp1_w_input" ${fn:contains(pd.allcity_file_sort_id, var.city_file_sort_id) eq true?'checked':''}      type="checkbox" id="xuan" value="${var.city_file_sort_id}">${var.sort_name}</span>
	          	  		</c:if>
 	          	  </c:forEach>
 	          </span>
 	        </div>
 
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">负责人</span>
	          <input type="text" class="dangan_d1_ipt2" name="principal" id="principal" value="${pd.principal}"></input>
	          <span class="dangan_d1_sp2" >固话</span>
	          <input type="text"  maxlength="15" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" class="dangan_d1_ipt2" name="fixed_telephone" id="fixed_telephone" value="${pd.fixed_telephone}"></input>
	          <span class="dangan_d1_sp2">手机</span>
	          <input type="text" maxlength="11" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" class="dangan_d1_ipt2"  name="phone" id="phone" value="${pd.phone}"></input>
	        </div>
	        <div class="dangan_d1">
	          <span class="dangan_d1_sp1">e-mail</span>
	          <input type="email" class="dangan_d1_ipt2" name="email" id="email" value="${pd.email}"></input>
	          <span class="dangan_d1_sp2">微信</span>
	          <input type="text" class="dangan_d1_ipt2" name="wechat" id="wechat" value="${pd.wechat}"></input>
	          <span class="dangan_d1_sp2">QQ</span>
	          <input type="text" maxlength="12" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" class="dangan_d1_ipt2" name="qq" id="qq" value="${pd.qq}"></input>
	        </div>
	         <div class="dangan_d1">
	          <span class="dangan_d1_sp1 date-picke">签约日期</span>
	          <input onchange="iscomplax(this)" placeholder="签约日期" type="text" class="dangan_d1_ipt2" name="sign_time" id="sign_time" value="${pd.sign_time}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></input>
	          <span class="dangan_d1_sp2 date-picke">启动日期</span>
	          <input onchange="iscomplax(this)" placeholder="启动日期" type="text" class="dangan_d1_ipt2" name="start_time" id="start_time" value="${pd.start_time}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></input>
	          <span class="dangan_d1_sp2">保证金</span>
	          <input type="number" class="dangan_d1_ipt2" name="earnest_money" id="earnest_money" value="${pd.earnest_money}"></input>
	        </div>
 
	       	<input type="hidden" id="allmonth_id" name="allmonth_id" value=""  >
	        <div class="dangan6_d1" style="    width: 92%;"> 
		         <table  border="0" cellspacing="0" cellpadding="0" >
			          <tr>
			            <td>月度</td>
			            <td>拓展商户数</td>
			            <td>实际拓展商户数</td>
			            <td>完成率</td>
			            <td>流水指标</td>
			            <td>实际流水</td>
			            <td>完成率</td>
			            <td>是否合格</td>
			          </tr>
			          <c:if test="${!empty monthList }" >
			          		<c:forEach items="${monthList}" var="var">
				          	  <tr>
					            <td>
					            	<input value="${var.sp_file_monthly_id}" type="hidden" class="month_id"/> 
					            	<input value="${var.month}" type="text" name="${var.sp_file_monthly_id}month" onclick="WdatePicker({dateFmt:'yyyy-MM'})"  readonly="readonly" style="background-color: #cccccc" />
					            </td>
					            <td><input value="${var.user_number_indicator}" type="text" name="${var.sp_file_monthly_id}user_number_indicator" /></td>
					            <td><input value="${var.actual_user_number}" type="text" name="${var.sp_file_monthly_id}actual_user_number" readonly="readonly" style="background-color: #cccccc" /></td>
					            <td>
					            	<input value="${var.completion_rate}" type="text" name="${var.sp_file_monthly_id}completion_rate" readonly="readonly" style="color:red ;background-color: #cccccc"  />
					            </td>
					            <td><input value="${var.flow_indicators}" type="text" name="${var.sp_file_monthly_id}flow_indicators" /></td>
					            <td><input value="${var.actual_water}" type="text" name="${var.sp_file_monthly_id}actual_water" readonly="readonly"  style="background-color: #cccccc"/></td>
					            <td>
					            	<input value="${var.water_completion_rate}" type="text" name="${var.sp_file_monthly_id}water_completion_rate" readonly="readonly" style="color:red;background-color: #cccccc"/>
  					            </td>
					            <td>
					            	<select   name="${var.sp_file_monthly_id}isqualified"  >
					            		<option value="2" ${var.isqualified eq '2' ? 'selected':''}>待定</option>
					            		<option value="1" ${var.isqualified eq '1' ? 'selected':''}>是</option>
					            		<option value="0" ${var.isqualified eq '0' ? 'selected':''}>否</option>
					            	</select>
  					            </td>
					          </tr>
				         	</c:forEach>
 			          </c:if>
			          <tr>
			            <td  colspan="8">
			              <c:if test="${qx.add eq '1'}">
			                    <span class="new_td" onclick="addtr(this)">+</span>
			              </c:if>
			            </td>
			          </tr>
 		        </table>
	        </div>
	        <div class="dangan_d1">
	        	  <c:if test="${qx.add eq '1'}">
	        	  	<span class="dangan_d1_btn1" onclick="save()">保存</span> 
	        	  </c:if>
		          <c:if test="${msg eq 'edit' }">
		          	  <c:if test="${pd.earnest_status eq '0' }">
				          <a class="middle_a" href="zhihui_sp_file/goPayEarnest_money.do?sp_file_id=${pd.sp_file_id}&currentPage=${pd.currentPage}"  target="ifra"> 
				            <span class="dangan_d1_btn1 ">支付保证金</span> 
				          </a>
			          </c:if>
			          <c:if test="${pd.earnest_status eq '2' }">
			          		<span class="dangan_d1_btn1 " style="background-color:#7B7A77;width:200px">退还保证金，正在审核</span> 
			          </c:if>
<%-- 			          <c:if test="${pd.earnest_status eq '1' }">
				          <a class="middle_a" href="zhihui_sp_file/returnEarnest_money.do?sp_file_id=${pd.sp_file_id}&currentPage=${pd.currentPage}"  target="ifra"> 
				          	<span class="dangan_d1_btn1 ">申请退还保证金</span> 
				          </a>
				      </c:if> --%>
			          <c:if test="${pd.sp_status eq '0' }">
			          		<c:if test="${qx.edit eq '1'}">
			          			<span class="dangan_d1_btn1" onclick="sureapproved('${pd.sp_file_id}',this)">通过审核</span>
			          		</c:if>
			          </c:if>
			           <c:if test="${pd.sp_status eq '1' }">
			           		<span class="dangan_d1_btn1" style="background-color:#7B7A77;">审核通过</span>
			           </c:if>
		          </c:if>
  	  	          <a class="middle_a" href="zhihui_sp_file/list.do"  target="ifra"> 
		           <span class="dangan_d1_btn1">退出</span>
		          </a>  
	        </div>
       </form>
       </c:if>
         <!-- 引入 -->
        <script src="js/jquery-1.8.0.min.js"></script>
 		<script src="js/bootstrap.min.js"></script>
		<script src="js/ace-elements.min.js"></script>
		<script src="js/ace.min.js"></script>
		<script type="text/javascript" src="js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框-->
		<script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/zhihui/dangan6.js"></script>
		<script type="text/javascript">
		
		var suijiid="";//唯一标识ID
		
 		$(function() {
 			//获取Id
			getId();
		});
		
 		//获取随机32位ID
		function getId(){
			$.ajax({
				 type:"post",
                 url:'<%=path%>/zhihui_city_file/get32Id.do?tm='+(new Date()).getTime(), 
                 success: function(data){
                	 suijiid=data.id;
                 }
  			});
		}
		
 		//比较时间
    	function iscomplax(obj){
     		var sign_time=$("#sign_time").val();
    		var start_time=$("#start_time").val();
			if(sign_time == "" || start_time==""){
    			return;
    		}
    		if((new Date(sign_time.replace(/-/g,"\/"))) > (new Date(start_time.replace(/-/g,"\/")))){
    			alert("启动日期不能早于签约日期");
    			$(obj).val("");
    			return;
    		}
    	}
 		
 		
 		
		//判断这个团队名称是否存在
        function isname(){
        	var name = $("#team_name").val().trim();
        	$.ajax({
				type:"post",
                url:'<%=path%>/zhihui_sp_file/isname.do', 
                data:{
                	team_name:name
	                },
                success: function(data){
                	if(data.result == "01"){
						alert("团队名称已存在！");
						return ;
 					}
                }
 			});
        }
        //获取当前子公司下的所有区域
        function getPcdList(obj){
        	if($(obj).val() == ""){
        		return;
        	}
        	$.ajax({
				type:"post",
                url:'<%=path%>/zhihui_sp_file/findSubPcdList.do', 
                data:{
                	"subsidiary_id":$(obj).val()
	                },
                success: function(data){
               	 	$("#area_id").empty();
               	 	$("#area_id").append("<option value='' >请选择</option>");
               	 	 var areaList=data.subsidiarypcd;
               	 	 for(var i=0;i<areaList.length;i++){
               	 	 	$("#area_id").append("<option value='"+areaList[i].area_id+"' >"+areaList[i].area_name+"</option>");
               	 	 }
                }
 			});
        }
	        
	 		//获取城市的一级分类
			function getSortByarea(){
				var str3=$("#area_id option:selected").val();//获取被选中的value值
				$.ajax({
					  url: '<%=path%>/zhihui_city_file/citySortList.do',
					  data:{"area_id":str3,"sort_parent_id":"0","sort_type":"1","choose_status":"0"},
					  type:"post",
					  dataType:"json",
					  success:function(data){
						  	var list=data.sortlist;
						  	$(".firstsort").empty();
						  	if(list.length>0){
							  	for(var i=0;i<list.length;i++){
							  		if(i==7 || i==14){
							  			$(".firstsort").append("<span class='dangan_d1_sp1_w' style='width:145px;'></span>");
							  			$(".firstsort").append("<span class='dangan_d1_sp1_w'><input class='dangan_d1_sp1_w_input' type='checkbox' id='xuan' value='"+list[i].city_file_sort_id+"'>"+list[i].sort_name+"</span>");
							  		}else{
							  			$(".firstsort").append("<span class='dangan_d1_sp1_w'><input class='dangan_d1_sp1_w_input' type='checkbox' id='xuan' value='"+list[i].city_file_sort_id+"'>"+list[i].sort_name+"</span>");
							  		}
							  	}
					  		}  	
					  }
				});
			} 
	 		
	 		
	        //出去已选择的分类
	        function ischangesort(obj){
	        	if($(obj).val() == ""){
	        		return;
	        	}
	        	var n=0;
	        	//循环
	        	$(".firstsort").each(function(){
	        		if($(obj).val() == $(this).val()){
	        			 n=n+1;
	        		}
	        	});
	        	if(n == 2){
	        		alert("当前分类以选中，请选择其他的");
	        		$(obj).val("");
	        		return;
	        	}
	        }
 		
 		//新增一个tr
 		function addtr(obj){
 			getId();
 	 		var str="<tr>"+
			         "   <td>"+
			         		"<input value='"+suijiid+"' type='hidden' class='month_id'/>"+
			         		"<input value='' type='text' name='"+suijiid+"month' onclick=\"WdatePicker({dateFmt:\'yyyy-MM\'})\" />"+
			         "	 </td>"+
			          "  <td><input value='' type='text' name='"+suijiid+"user_number_indicator' /></td>"+
			           " <td><input value='' type='text' name='"+suijiid+"actual_user_number'  readonly='readonly' /></td>"+
			           " <td><input value='' type='text' name='"+suijiid+"completion_rate' readonly='readonly' /></td>"+
			            "<td><input value='' type='text' name='"+suijiid+"flow_indicators' /></td>"+
			            "<td><input value='' type='text' name='"+suijiid+"actual_water'   readonly='readonly' /></td>"+
			           " <td><input value='' type='text' name='"+suijiid+"water_completion_rate' readonly='readonly'  /></td>"+
			           " <td> "+
				      "      <select name='${var.sp_file_monthly_id}isqualified' >"+
					"			<option value='2'>待定</option>"+
					"			<option value='1'>是</option>"+
					"			<option value='0'>否</option>"+
					"		</select>"+
			         "   </td>"+
			       " <tr>";
			$(obj).parent().parent().before(str);
 			getId();
 		}
 		
 		
 		
 		
 		
 		//保存
 		function save(){
 			if($("#team_name").val()==""){
 				$("#team_name").tips({
 					side:3,
 		            msg:'请输入团队名臣',
 		            bg:'#AE81FF',
 		            time:2
 		        });
 				$("#team_name").focus();
 				return false;
 			}
 			if($("#industry_commerce_name").val()==""){
 				$("#industry_commerce_name").tips({
 					side:3,
 		            msg:'请输入工商名称',
 		            bg:'#AE81FF',
 		            time:2
 		        });
 				$("#industry_commerce_name").focus();
 				return false;
 			}
 			if($("#subsidiary_name").val()==""){
 				$("#subsidiary_name").tips({
 					side:3,
 		            msg:'请输入隶属子公司名称',
 		            bg:'#AE81FF',
 		            time:2
 		        });
 				$("#subsidiary_name").focus();
 				return false;
 			}
 			if($("#principal").val()==""){
 				$("#principal").tips({
 					side:3,
 		            msg:'请输入负责人',
 		            bg:'#AE81FF',
 		            time:2
 		        });
 				$("#principal").focus();
 				return false;
 			}
 		 
 			if($("#phone").val()==""){
 				$("#phone").tips({
 					side:3,
 		            msg:'请输入手机号码',
 		            bg:'#AE81FF',
 		            time:2
 		        });
 				$("#phone").focus();
 				return false;
 			}
 			if($("#earnest_money").val()==""){
 				$("#earnest_money").tips({
 					side:3,
 		            msg:'请输入保证金',
 		            bg:'#AE81FF',
 		            time:2
 		        });
 				$("#earnest_money").focus();
 				return false;
 			}
 			if($("#sign_time").val()==""){
 				$("#sign_time").tips({
 					side:3,
 		            msg:'请输入签约日期',
 		            bg:'#AE81FF',
 		            time:2
 		        });
 				$("#sign_time").focus();
 				return false;
 			}
 			if($("#start_time").val()==""){
 				$("#start_time").tips({
 					side:3,
 		            msg:'请输入启动日期',
 		            bg:'#AE81FF',
 		            time:2
 		        });
 				$("#start_time").focus();
 				return false;
 			}
 			var allmonth="";
 			$(".month_id").each(function(i,obj){
					var obj = $(obj).val().trim(); //这里的value就是每一个input的value值
					allmonth=allmonth+"@"+obj;
			});
 			var allcity_file_sort_id="";
 			$(".dangan_d1_sp1_w_input").each(function(i,obj){
					if($(this).is(":checked")){
						allcity_file_sort_id+=$(this).val().trim()+",";
					}
			});
 	 		$("#allmonth_id").val(allmonth); 
	 		$("#allcity_file_sort_id").val(allcity_file_sort_id); 
   			$("#Form").submit();
 		}
 		
 		
 		//通过审核
 		function  sureapproved(value,obj){
  			//后通过审核
  			$.ajax({
				type:"post",
                url:'<%=path%>/zhihui_sp_file/editSpstatus.do?tm='+(new Date()).getTime(), 
                data:{
                	"sp_file_id":value,
                	"sp_status":"1"
                },
                success: function(data){
               	 	if(data.result == "01"){
               	 		alert("通过审核成功！");
               	 		$(obj).css("background-color","#7B7A77");
               	 		$(obj).removeAttr("onclick");
               	 		$(obj).html("审核通过");
               	 	}
                }
 			});
 		}
 		
 
 		
 		
		</script>
    </body>
</html>