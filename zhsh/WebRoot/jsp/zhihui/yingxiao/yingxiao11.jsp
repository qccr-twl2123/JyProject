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
        <link rel="stylesheet" href="css/zhihui/yingxiao11.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/yingxiao11.js"></script>
        <style type="text/css">
        	img{
        		height: inherit;
        	}
        </style>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihui_sort_chain/${msg }.do" id="Form" name="Form" method="post" enctype="multipart/form-data"> 
      <input type="hidden" name="currentPage" value="${currentPage }">
      <div class="yingxiao8_d1">
       <input type="hidden" value="${pd.sort_chain_id }" name="sort_chain_id">
        <span class="yingxiao8_d1_sp1">广告位置</span>
        <input type="text" name = "advertising" id ="advertising" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
        <%-- <select class="yingxiao8_d1_st1" name="advertising">
        <c:if test="${msg eq 'save' }">
            <option>请选择广告位置</option>
        </c:if>
         <c:if test="${msg eq 'edit' }">
            <option>${pd.advertising}</option>
        </c:if>
            <option>主页面广告滚动1</option>
            <option>主页面广告滚动2</option>
        </select> --%>
      </div>
      <div class="yingxiao8_d1">
        <span class="yingxiao8_d1_sp1">排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序</span>
       <!--  <span class="yingxiao8_d1_sp1">3~6随机</span> -->
        <input name="ranking" value="${pd.ranking }">
      </div>  
      <div class="yingxiao8_d1">
     
        <span class="yingxiao8_d1_sp1">显示位置</span>
        <input type="text" name="show_position" value ="商家列表" id = "show_position" readonly/>
        <%-- <select class="yingxiao8_d1_st1" name="show_position">
        <c:if test="${msg eq 'save' }">
            <option>请选择显示位置</option>
        </c:if>
         <c:if test="${msg eq 'edit' }">
            <option>${pd.show_position}</option>
        </c:if>
            <option>全部</option>
            <option>主页面广告滚动2</option>
        </select> --%>
      </div>
      <div class="yingxiao8_d1">
        <span class="yingxiao8_d1_sp1">区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;域</span>
        <select class="yingxiao8_d1_st1 st2" name="province_id" id="province_id" onchange="searchfind();">
          	<c:if test="${msg eq 'save' }">
            <option value="">请选择省</option>
	        </c:if>
	         <c:if test="${msg eq 'edit' }">
	           <option value="${pd.province_id }">${pd.province_name }</option>
	        </c:if>
          <c:forEach items="${provicelist}" var="var" varStatus="vs">
		  <option value="${var.pcd_id}">${var.name}</option>
		  </c:forEach>
          </select>
          <select class="yingxiao8_d1_st1 st2" id="city_id" name="city_id" onchange="searcharea();">
          	<c:if test="${msg eq 'save' }">
            <option value="">请选择市</option>
	        </c:if>
	         <c:if test="${msg eq 'edit' }">
	           <option value="${pd.city_id }">${pd.city_name }</option>
	        </c:if>
          </select>
          <select class="yingxiao8_d1_st1 st2" id="area_id" name="area_id">
              <c:if test="${msg eq 'save' }">
            <option value="">请选择区</option>
	        </c:if>
	         <c:if test="${msg eq 'edit' }">
	           <option value="${pd.area_id }">${pd.area_name }</option>
	        </c:if>
          </select>
      </div>    
      <c:if test="${qx.add eq '1'}"> 
     <div class="yingxiao8_d1">
        <!-- <a  href="javascript:;" class="file">上传照片<input type="file"  id="photo" class="business_right1_add_photo" /></a>  -->
				上传照片：<input type="file" name="image_url"   value=""  id="image_url"  placeholder="这里输入图片" title="图片" onchange="imageShow();"/>
      </div> 
      </c:if>
      <div  class="photo" >
      	<img src="${pd.image_url }"  id="show"  width="880px" height="400px">
      </div> 
      <div class="yingxiao8_d1">
        <span class="yingxiao8_d1_sp1">网站链接</span>
        <input class="yingxiao8_d1_ipt1" type="text" value="${pd.website }" name="website"></input>
      </div> 
      <div class="yingxiao8_d1">
     	 <c:if test="${qx.add eq '1'}">
          	<span class="yingxiao8_yes" onclick="sarech()">确定</span>
          </c:if>
          <a class="middle_a" href="zhihui_sort_chain/list.do"  target="ifra">
            <span class="yingxiao8_tc">退出</span>
          </a>
      </div> 
	</form>
	</c:if>
    </body>
   <script type="text/javascript">
    	//获取城市
		function searchfind(){
			var str=$("#province_id option:selected").val();//获取被选中的value值
			$.ajax({
				  url: '<%=path%>/zhihui_subsidiary/citylist.do',
				  data:"province_id="+str,
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.citylist;
 					  	$("#city_id option").remove();
 					  	$("#city_id").append("<option value=''>请选择市</option>");
					  	$("#area_id").append("<option  value=''>请选择区</option>");
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$("#city_id").append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
						  	}
					  	} 
				  }
			});
		}
			
		//获取区域
		function searcharea(){
			var str=$("#city_id option:selected").val();//获取被选中的value值
			$.ajax({
				  url: '<%=path%>/zhihui_subsidiary/arealist.do',
				  data:"city_id="+str,
				  type:"post",
				  dataType:"json",
 				  success:function(data){
					  	var list=data.arealist;
					  	$("#area_id option").remove();
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$("#area_id").append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
						  	}
				  		}
				  }
			});
		}
		
		//图片展示
		function imageShow(){
			var image=$("#image_url");
			var fileList=image.prop("files");
			$("#show").attr("src",window.URL.createObjectURL(fileList[0]));
		}
	
		//提交
		function sarech(){
			$("#Form").submit();
		}
		</script>
</html>