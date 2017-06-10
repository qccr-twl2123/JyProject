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
        <link rel="stylesheet" href="css/zhihui/yingxiao1.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/bg.js"></script>
        <style type="text/css">
         a{cursor: pointer;color:blue;}
        .dangan2_d1_st1{
		    width: 19%;
		    height: 40%;
		    margin-top: 1%;
		    margin-left: 2%;
		}
        </style>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    	<form action="<%=basePath%>youxuan/datalistPageGoods.do" name="Form" id="Form" method="post">
 	       <c:if test="${login_type ne '1' and login_type ne '2' }">
	   	       <div class="dangan2_d1">
		         	<span  class="zhifu1_sp1">区&nbsp;&nbsp;&nbsp;&nbsp;域 </span>
		         	<select class="dangan2_d1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
					    <option value="">请选择省</option>
					     <c:forEach items="${provincelist}" var="var" varStatus="vs">
								<option value="${var.province_id}" >${var.province_name}</option>
						</c:forEach>
						<c:if test="${pd.province_id ne '' and  !empty pd.province_id}">
								<option value="${pd.province_id}"  selected="selected">${pd.province_name}</option>
						</c:if>
					</select>
	 				<select class="dangan2_d1_st1" id="city_id" name="city_id" onchange="addsearcharea();">
						  <option value="${pd.city_id}">${pd.city_name}</option>
					</select>
	 				<select class="dangan2_d1_st1" id="area_id" name="area_id" onchange="addsearchdangqi()">
					       <option value="${pd.area_id}">${pd.area_name}</option>
					</select>
					<input type="hidden" name="province_name" id="province_name" value="${pd.province_name}"  />
			        <input type="hidden" name="city_name" id="city_name" value="${pd.city_name}"  />
			        <input type="hidden" name="area_name" id="area_name" value="${pd.area_name}"  />
	 		        <input type="hidden" name="goods_check" id="goods_check" value="0"  />
		       </div>
		       <div class="dangan2_d1">
		         	<span  class="zhifu1_sp1">选择档期 </span>
		         	<select class="dangan2_d1_st1" name="youxuandq_id" id="youxuandq_id"  >
					    <option value="">请选择</option>
					    <c:if test="${!empty pd.youxuandq_id  and pd.youxuandq_id ne ''}">
					    	<option value="${pd.youxuandq_id }" selected="selected">${pd.youxuandq_name}</option>
					    </c:if>
					    <c:if test="${!empty listAllDangqi }">
					    	<c:forEach items="${ listAllDangqi}" var="var">
					    		<option value="${var.youxuandq_id }"  >第${var.youxuandq_id}期${var.startdate}-${var.enddate}</option>
					    	</c:forEach>
	  				    </c:if>
	 				</select>
	 				 <input type="hidden" name="youxuandq_name" id="youxuandq_name" value="${pd.youxuandq_name}"  />
	 				<span class="zhifu1_btn1" onclick="checked()">查询</span>
	 	       </div>
 	       </c:if>
 	       <c:if test="${login_type eq '1' or login_type eq '2' }">
 	       	   <div class="dangan2_d1">
		         	<input class="dangan2_d1_st1" type="text" name="province_name" id="province_name" value="${cpd.province_name}"  readonly="readonly" />
			        <input class="dangan2_d1_st1" type="text" name="city_name" id="city_name" value="${cpd.city_name}" readonly="readonly"  />
			        <input class="dangan2_d1_st1" type="text" name="area_name" id="area_name" value="${cpd.area_name}"  readonly="readonly" />
   		       </div>
		       <div class="dangan2_d1">
		         	<span  class="zhifu1_sp1">选择档期 </span>
		         	<select class="dangan2_d1_st1" name="youxuandq_id" id="youxuandq_id"  >
					    <option value="">请选择</option>
					    <c:if test="${!empty pd.youxuandq_id  and pd.youxuandq_id ne ''}">
					    	<option value="${pd.youxuandq_id }" selected="selected">${pd.youxuandq_name}</option>
					    </c:if>
					    <c:if test="${!empty listAllDangqi }">
					    	<c:forEach items="${ listAllDangqi}" var="var">
					    		<option value="${var.youxuandq_id }"  >第${var.youxuandq_id}期${var.startdate}-${var.enddate}</option>
					    	</c:forEach>
	  				    </c:if>
	 				</select>
	 				 <input type="hidden" name="youxuandq_name" id="youxuandq_name" value="${pd.youxuandq_name}"  />
	 				<span class="zhifu1_btn1" onclick="checked()">查询</span>
	 	       </div>
 	       </c:if>
	       <div class="dangan2_d2">
	          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table"   style="    white-space: nowrap;line-height:36px">
	              <tr >
	                <td>申请时间</td>
	                <td>档期</td>
	                <td>商家名称</td>
	                <td>商家ID号</td>
	                <td>商品名称</td>
	                <td>单位</td>
	                <td>商品编号</td>
	                <td>日常售价</td>
	                <td>官方指导价</td>
	                <td>优选价</td>
	                <td>折扣率</td>
	                <td>是否网商同款</td>
	                <td>网商价</td>
	                <td>积分率</td>
	                <td>是否限购</td>
	                <td>商家编辑</td>
	                <td>应收费用</td>
	                <td>审核</td>
	                <td>备注</td>
	                <td>查看详情</td>
	              </tr> 
	              <c:forEach items="${goodslist}" var="var" varStatus="vs">
	              	<tr>
		                <td>${var.createtime}</td>
	              		<td>${var.youxuandq_name}</td>
	              		<td>${var.store_name}</td>
	              		<td>${var.store_id}</td>
	              		<td>${var.goods_name}</td>
	              		<td>${var.goods_dw}</td>
  	              		<td>${var.youxuangoods_id}</td>
	              		<td>${var.rc_salemoney}</td>
	              		<td>${var.gf_salemoney}</td>
	              		<td>${var.bp_salemoney}</td>
	              		<td>${var.goods_zkrate}</td>
	              		<td>${var.istongkuan eq '1'?'是':'否'}</td>
	              		<td>${var.ws_salemoney}</td>
	              		<td>${var.goods_jfrate}</td>
 	              		<td>${var.isxiangou eq '1'?'是':'否'}</td>
 	              		<td>${var.bianji_type eq '1'?'是':'否'}</td>
	              		<td>${var.bianji_type eq '1'?'': var.bianji_money } </td>
	              		<td>
	              			<c:if test="${qx.edit eq '1'}">
		              			<c:choose>
		              				<c:when test="${var.goods_status eq '0'  }">
			              				<c:if test="${var.bianji_type eq '1'}">
				              				<a  onclick="changestatus('${var.youxuangoods_id}','2')" >通过</a>|
				              			</c:if>
				              			<c:if test="${var.bianji_type eq '2'}">
				              				<a  onclick="changestatus('${var.youxuangoods_id}','1')" >通过</a>|
				              			</c:if>
				              			<a  onclick="changestatus('${var.youxuangoods_id}','97')" >驳回</a>
		              				</c:when>
		              				<c:otherwise><span style="color:red;">已驳回</span></c:otherwise>
		              			</c:choose>
	              			</c:if>
	              		</td>
	              		<td><input placeholder="通过/驳回原因" type="text" value="${var.beizhu_text}"  id="beizhu_text${var.youxuangoods_id}"/></td>
		                <td>
		                	<a  href="<%=basePath%>youxuan/goEditGoods.do?youxuangoods_id=${var.youxuangoods_id}&currentPage=${page.currentPage}&goods_check=${pd.goods_check}" >查看详情</a>
		                </td>
 		             </tr> 
 	              </c:forEach>           
	          </table>
	          <br/>
	          <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
	       </div>
 	   </form>
 	   </c:if>
 	   <script src="js/bootstrap.min.js"></script>
 	   <script src="js/ace-elements.min.js"></script>
		<script src="js/ace.min.js"></script>
 	   <script type="text/javascript" src="js/bootbox.min.js"></script><!-- 确认窗口 -->
 	   
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
						$("#youxuandq_id option").remove();
					  	$("#city_id").append("<option value=''>请选择市</option>");
					  	$("#area_id").append("<option  value=''>请选择区</option>");
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
					  	$("#youxuandq_id option").remove();
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
		
		//获取档期
		function addsearchdangqi(){
			var area_id=$("#area_id option:selected").val();
			var city_id=$("#city_id option:selected").val(); 
			var province_id=$("#province_id option:selected").val(); 
			$.ajax({
				  url: '<%=path%>/youxuan/getDangqiByPcd.do',
				  data:{"province_id":province_id,"city_id":city_id,"area_id":area_id},
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.listAllDangqi;
					  	$("#youxuandq_id option").remove();
					  	$("#youxuandq_id").append("<option  value=''>请选择档期</option>");
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$("#youxuandq_id").append("<option value='"+list[i].youxuandq_id+"'>第"+list[i].youxuandq_id+"期"+list[i].startdate+"-"+list[i].enddate+"</option>");
						  	}
				  		}
				  },
				  error:function(a){
				  alert("异常");
				  }
			});
		}
       
       
 		
        //检索
    	function checked(){
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
			if($("#youxuandq_id option:selected").val() != ""){
				var youxuandq_name=$("#youxuandq_id option:selected").text();
 				$("#youxuandq_name").val(youxuandq_name);
			}
    		$("#Form").submit();//提交
    	}
     	

		//修改状态
		function changestatus(youxuangoods_id,goods_status){
			var beizhu_text=$("#beizhu_text"+youxuangoods_id).val();
			if(beizhu_text == "" && goods_status == "97"){
				alert("原因不能为空");
				return;
			}
			//====
			bootbox.confirm("确定?", function(result) {
				if(result) {
					var url = "<%=basePath%>youxuan/changestatus.do?youxuangoods_id="+youxuangoods_id+"&goods_status="+goods_status+"&beizhu_text="+beizhu_text+"&tm="+new Date().getTime();
					$.get(url,function(data){
						if(data=="success"){
							nextPage(${page.currentPage});
						}
					});
				}
			});
		}
        
        
       </script>
    </body>
</html>