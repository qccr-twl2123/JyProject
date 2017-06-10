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
        <link rel="stylesheet" href="css/zhihui/yingxiao8.css">
        <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/jquery.tips.js"></script>
        <script src="js/zhihui/yingxiao8.js"></script>
        <script src="js/zhihui/bg.js"></script>
        <style type="text/css">
        	img{
        		height: inherit;
        	}
        </style>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <br><br>
    <form action="zhihui_pc_advert/list.do?menu_id=19" id="Form" name="Form" method="post">
         	<div class="dangan2_d1">
		          <select class="dangan2_d1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
			           	<c:if test="${pd.province_id ne '' and  !empty pd.province_id}">
									<option value="${pd.province_id}"  selected="selected">${pd.province_name}</option>
						</c:if>
			           	<option value="">请选择省</option>
			           	<c:forEach items="${provincelist}" var="var" varStatus="vs">
							<option value="${var.pcd_id}">${var.name}</option>
						</c:forEach>
 		 	         </select>
			         <select class="dangan2_d1_st1" name="city_id" id="city_id" onchange="addsearcharea();">
			         	 <option value="${pd.city_id}">${pd.city_name}</option>
		 	         </select>
			         <select class="dangan2_d1_st1" name="area_id" id="area_id">
			         	 <option value="${pd.area_id}">${pd.area_name}</option>
		 	         </select>
		 	         <input type="hidden" name="province_name" id="province_name" value="${pd.province_name}"  />
				     <input type="hidden" name="city_name" id="city_name" value="${pd.city_name}"  />
				     <input type="hidden" name="area_name" id="area_name" value="${pd.area_name}"  />
          		<span class="zhifu1_btn1" onclick="search()">查询</span>
     	 </div>
    </form>
  	  <div style="margin-left: 50px; font-size: 20px;"> 当前筛选位置：${pd.province_name}-${pd.city_name}-${pd.area_name} </div>
      <div class="yingxiao8_d1_tb">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
           		<tr>
          			<td>编号</td>
          			<td>广告显示位置</td>
          			<td>排序</td>
          			<td>图片</td>
          			<td>有效起止时间</td>
          			<td>跳转网页</td>
          			<td>操作</td>
           		</tr>
         		<c:forEach items="${varList}" var="var" varStatus="vs">
	               <tr>
		                <td>${var.pc_advert_id }</td>
		                <td>${var.pc_advert_name }</td>
		                <td class="paixu">${var.ranking}</td>
		                <td><img src="${var.image_url }" width="120px" height="120px"/></td>
		                <td>${var.starttime } 至  ${var.endtime }</td>
		                 <td>${var.hyperlink_url }</td>
		                <td>
	 		            <%--    	<c:if test="${var.ifimage eq '1'}">
			               	  <span>添加</span>  --%>
			               	  <c:if test="${qx.edit eq '1'}">
			                  <span  class="blue" onclick="upd('${var.pc_advert_id }')">修改</span>
			                  </c:if>
	 		               <%-- 	</c:if>
			               	<c:if test="${var.ifimage eq '0'}">
			               	  <span  class="blue"  onclick="upd('${var.pc_advert_id }')">添加</span>
			                  <span>修改</span>
			               	</c:if>  --%>
		                 </td>
	              </tr>
               </c:forEach> 
           </table>
      </div>
      <br/>
      <br/>
      <div style="display:none; background-color: #fff;font-size:18px;" class="editpcadvrt">
	      <form action="zhihui_pc_advert/edit.do" name="editForm" id="editForm" method="post" enctype="multipart/form-data">
		      <input name="city_file_id" value="${pd.city_file_id}" type="hidden"/>
		      <div class="yingxiao8_d1">
		      	 <input type="hidden"  id="pc_advert_id" name="pc_advert_id"  />
			      <span class=" ">广告位置：</span>
			      <span id="pc_advert_name" ></span>
			    <br/><br/>
		        <span>排序：</span>
	 	        <input id="ranking" name="ranking" class="" onkeyup="ispaixu(this)" type="number"/>
	 	        <input  type="hidden" class="ranking"  />
	 	        <script type="text/javascript">
	 	        function ispaixu(obj){
	 	        	if($(obj).val() != $(".ranking").val()){
	 	        		$(".paixu").each( function(){
		 	        		 if($(this).html() == $(obj).val()  ){
		 	        			 alert("当前排序已存在！");
		 	        			$(obj).val("");
		 	        			 return;
		 	        		 }
		 	        	});
	 	        	}
 	 	        }
	 	        </script>
				<br/>
		        <span>有效时间：</span>
		        <input type="date" name="starttime" id="starttime"   placeholder="开始时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${pd.starttime}"/>
		        <span class="yingxiao8_d1_sp1">至</span>
		        <input type="date" name="endtime" id="endtime"    placeholder="结束时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${pd.starttime}">
		      </div> 
		      <br/>
		      <div>
 			      <div class="yingxiao8_d1">
		 				上传照片：<input type="file" name="image_url"   value=""  id="image_url"  placeholder="这里输入图片" title="图片" onchange="imageShow();"/>
			      </div> 
 		      </div>  
		      <div  class="photo" ><img src=""  id="show"  width="880px" height="400px"></div> 
		      <br/>
		      <div class="yingxiao8_d1">
		        <span class="yingxiao8_d1_sp1">链接网页</span>
		        <input id="hyperlink_url" name="hyperlink_url" class="yingxiao8_d1_ipt1" type="text"/>
		      </div> 
		      <br/>
		      <div class="yingxiao8_d1">
		      		<c:if test="${qx.add eq '1'}">
		          <span class="yingxiao8_yes" onclick="sureEdit()">确定</span>
		          </c:if>
		      </div> 
		</form>
	</div>
	</c:if>
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
				  }
			});
		}
    //查询
    function search(){
 		if($("#area_id").val()=="" || $("#area_id").val()==null){
    		$("#area_id").tips({
  				side:3,
  	            msg:'请选择区域',
  	            bg:'#AE81FF',
  	            time:2
  	        });
  			$("#area_id").focus();
  			return false;
  		}
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
    
    //修改
    function sureEdit(){
    	if($("#starttime").val()==""){
    		$("#starttime").tips({
  				side:3,
  	            msg:'开始时间不能为空',
  	            bg:'#AE81FF',
  	            time:2
  	        });
  			$("#starttime").focus();
  			return false;
  		}
  		if($("#endtime").val()==""){
  			$("#endtime").tips({
  				side:3,
  	            msg:'结束时间不能为空',
  	            bg:'#AE81FF',
  	            time:2
  	        });
  			$("#endtime").focus();
  			return false;
  		}
    		$("#editForm").submit();
    	}
    
       //点修改
    	function upd(Id){
    	   	//展示
    	    $(".editpcadvrt").show();
    	    $(".yingxiao8_d1_tb").hide();
    		$.ajax({
             type: "POST",
             url: "zhihui_pc_advert/findbyid.do",
             data: {"pc_advert_id":Id},
             dataType: "json",
             success: function(data){
		             $("#advertising").text(data.pagedatas.image_name);//图片名称
		             $("#endtime").val(data.pagedatas.endtime);		//结束时间
		             $("#starttime").val(data.pagedatas.starttime);	//开始时间
		             $("#ranking").val(data.pagedatas.ranking);			//排序
		             $(".ranking").val(data.pagedatas.ranking);			//排序
		             $("#hyperlink_url").val(data.pagedatas.hyperlink_url);	//网址
		             document.getElementById("show").src=data.pagedatas.image_url;	//图片
		             $("#pc_advert_id").val(data.pagedatas.pc_advert_id);//id
		             $("#pc_advert_name").text(data.pagedatas.pc_advert_name);//名称
             }
         });
    	}
    
    	//图片展示
		function imageShow(){
			var image=$("#image_url");
			var fileList=image.prop("files");
			$("#show").attr("src",window.URL.createObjectURL(fileList[0]));
		}
	
    </script>
    </body>
 </html>