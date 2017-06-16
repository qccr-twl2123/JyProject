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
        <title>档案管理</title>
        <meta charset="utf-8">
        <link rel="stylesheet" href="../css/zhihui/dangan3.css">
        <script src="../js/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="../js/jquery.form.js"></script>
        <script src="../js/zhihui/dangan3.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    	<form action="${msg}.do" name="Form" id="Form" method="post" >
    		<input type="text" name="allcity_file_sort_id" id="allcity_file_sort_id" value="" style="display:none;width:1px;height:1px;">
    		<input type="hidden" name="city_file_id" id="city_file_id" value="${pd.city_file_id}">
    		<input type="hidden" name="currentPage" id="currentPage" value="${pd.currentPage}">
 	        <div class="dangan3_d1">
	          <span class="dangan_d1_sp1">省</span>
	           <select class="dangan3_d1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
	             <c:if test="${msg eq 'save'}">
			 		 <option value="">请选择省</option>
			 	 </c:if>
			 	 <c:forEach items="${provincelist}" var="var">
					 <option value="${var.pcd_id}" ${pd.province_id eq var.pcd_id ?'selected':''}>${var.name}</option>
				 </c:forEach>
	           </select>
	          <span class="dangan_d1_sp1">市</span>
	          <select class="dangan3_d1_st1" name="city_id" id="city_id" onchange="addsearcharea();">
	             <option value="${pd.city_id}">${pd.city_name}</option>
	           </select>
	          <span class="dangan_d1_sp1">区/县</span>
	          <select class="dangan3_d1_st1" name="area_id" id="area_id">
	             <option value="${pd.area_id}">${pd.area_name}</option>
	           </select>
	        </div>
	        <div class="dangan3_d2">
	          <span class="dangan3_d2_sp1">城市状态</span>
	          <label >
	          	<input type="checkbox"  ${pd.open_status eq '1'?'checked':''}  onclick="openclik(this)" />
	          	<input type="text"  name="open_status" id="open_status" value="${empty pd.open_status ?'0':pd.open_status}" style="display:none;width:1px;height:1px;" />
	          	<span class="dangan3_d2_ipt1" >开启</span>
 	          </label>
	        </div>
	        <div class="dangan3_d3">
	          <div class="dangan3_d3_d1">
	            <span class="dangan3_d3_sp1">一级分类</span>
	            <span class="dangan3_d3_sp1">二级分类</span>
	            <span class="dangan3_d3_sp1">排序</span>
	            <span class="dangan3_d3_sp1">图标</span>
	            <span class="dangan3_d3_sp1">操作</span>
	          </div>
	          <c:if test="${msg eq 'save'}">
		          <!-- 新增一级分类 -->
		          <div class="dangan3_d3_d2">  
		          <c:if test="${qx.add eq '1'}">
		            <span class="dangan3_tj2" onclick="addfirstsort(this)"> 添加一级分类 </span>
		            </c:if>
		          </div>
	          </c:if>
	          <c:if test="${msg eq 'edit'}">
	          		<c:forEach items="${firstList}" var="var" varStatus="vsFirst">
	          			<!-- 一级分类 -->
				          <div class="dangan3_d3_d2">  
				          		<input type="text" name="allsort${var.city_file_sort_id}" id="allsort${var.city_file_sort_id}" value="" style="display:none;width:1px;height:1px;">
				          		<input type="hidden"  class="city_file_sort_id" value="${var.city_file_sort_id}">
					            <span class="dangan3_d3_d2_sp1">
						              <input class="dangan3_d3_d2_sp1_ms ms" value="${var.sort_name}"  name="${var.city_file_sort_id}sort_name" id="${var.city_file_sort_id}sort_name">
				 	            </span>
					            <span class="dangan3_d3_d2_sp1"></span>
					            <span class="dangan3_d3_d2_sp1">
					            	<!-- 排序不能重复 -->
					              <input type="number" class="dangan3_d3_d2_sp1_ms onesequence" value="${var.sequence}" name="${var.city_file_sort_id}sequence" id="${var.city_file_sort_id}sequence" onchange="issequence('onesequence',this)"/> 
					            </span>
					            <span class="dangan3_d3_d2_sp1">
					            	<!-- 上传图片 -->
					            	<a style="margin:0 auto;display:block;" onclick="upload('${var.city_file_sort_id}')">
					            		<img src="${var.sort_imageurl}" width="46px" height="40px" class="${var.city_file_sort_id}"/>
					            	</a>
					            	<input type="text" name="${var.city_file_sort_id}sort_imageurl" id="${var.city_file_sort_id}sort_imageurl" value="${var.sort_imageurl}" style="display:none;width:1px;height:1px;"/>
					            	<!-- 结束 -->
					            </span>
					            <span class="dangan3_d3_d2_sp1">
					               <label >
					               		<input type="checkbox"  ${var.open_status eq '1'?'checked':''}  onclick="firstclick(this)"/>
					               		<input type="text"  id="${var.city_file_sort_id}open_status" name="${var.city_file_sort_id}open_status" value="${var.open_status}" style="display:none;width:1px;height:1px;"/>
 					               		<span class="mgleft10">开启</span>
					               	</label>
					            </span>
								<!-- 二级分类 -->
							  	<c:if test="${!empty var.twoList}">  
									<c:forEach items="${var.twoList}" var="two" varStatus="vsTwo">
										<div class="dangan3_d3_d3"> 
										  <input type="hidden" value="${two.city_file_sort_id}" class="two${var.city_file_sort_id}">
							              <span class="dangan3_d3_d2_sp1">
							              <span class="dangan3_d3_d3_xian"></span>
							              </span>
							              <span class="dangan3_d3_d2_sp1">
							                <input class="dangan3_d3_d2_sp1_ms" value="${two.sort_name}" name="${two.city_file_sort_id}sort_name"/> 
							              </span>
							              <span class="dangan3_d3_d2_sp1">
							              	<!-- 二级分类排序 -->
							                <input type="number" class="dangan3_d3_d2_sp1_ms w_${var.city_file_sort_id}" value="${two.sequence}" name="${two.city_file_sort_id}sequence" onchange="issequence('w_${var.city_file_sort_id}',this)"/> 
							              </span>
							              <span class="dangan3_d3_d2_sp1">
							              	<!-- 上传图片 -->
								            	<a style="margin:0 auto;display:block;" onclick="upload('${two.city_file_sort_id}')">
								            		<img src="${two.sort_imageurl}" width="46px" height="40px" class="${two.city_file_sort_id}"/>
								            	</a>
								            	<input type="text" name="${two.city_file_sort_id}sort_imageurl" id="${two.city_file_sort_id}sort_imageurl" value="${two.sort_imageurl}" style="display:none;width:1px;height:1px;"/>
 							              	<!-- 结束 -->
 							              </span>
 							              <span class="dangan3_d3_d2_sp1">
							                 <label >
							                 	<input type="checkbox" ${two.open_status eq '1'?'checked':''}  onclick="twoclick(this)"/>
							                 	<input type="text"  name="${two.city_file_sort_id}open_status" id="${two.city_file_sort_id}open_status"   value="${two.open_status}" style="display:none;width:1px;height:1px;"/>
							                 	<span class="mgleft10">开启</span>
							                 </label>
							              </span>
							            </div> 
	 								</c:forEach>
								 </c:if> 
  					            <!-- 新增二级分类 -->
  					            <c:if test="${qx.add eq '1'}">
				 	            <span class="dangan3_tj1" onclick="addtwosort('${var.city_file_sort_id}',this)">添加二级分类</span>
				          	</c:if>
				          </div>
	          		</c:forEach>
	          		<!-- 新增一级分类 -->
			        <div class="dangan3_d3_d2" >  
			            <span class="dangan3_tj2" onclick="addfirstsort(this)"> 添加一级分类 </span>
			        </div>
	          </c:if>
 	        </div>
 	        <script type="text/javascript">
 	        function issequence(value,obj){
 	        	var n=0;
 	        	var nowvalue=$(obj).val();
 	        	$("."+value).each(function(){
 	        		var str=$(this).val();
 	        		if(nowvalue == str){
 	        			n=n+1;
 	        		}
 	        	});
 	        	if( n > 1){
 	        		alert("排序不可重复");
 	        		$(obj).val("");
 	        		return;
 	        	}
 	        }
 	        
 	        </script>
	        <div class="dangan3_footer">
	        	<c:if test="${qx.add eq '1'}">
 	            	<span class="dangan3_d3_bc" onclick="savecityfile()" >保存</span>
 	            </c:if>
 	          	<a class="middle_a" href="list.do"  target="ifra">
	            	<span class="dangan3_d3_tc">退出</span>
	            </a>
	        </div>
        </form>
        </c:if>
        <c:if test="${qx.add eq '1'}">
        <form action="uploanImage.do" method="post" name="imageForm" id="imageForm"  enctype="multipart/form-data"> 
        	<input type="file" style="width:1px;display:none;" name="uploanImage" class="uploanImage" onchange="fileType(this)"/>
        </form>
        </c:if>
        
        <script type="text/javascript" src="../js/jquery.tips.js"></script><!--提示框-->
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
 					  	$("#city_id").empty();
  					  	$("#area_id").empty();
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
					  	$("#area_id").empty();
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
		
		//城市选中状态
		function openclik(obj){
			var value=$(obj).next().val();
			$(obj).next().show();
 			if(value == "1"){
				$(obj).next().val("0");
			}else{
				$(obj).next().val("1");
 			}
 			$(obj).next().hide();
		}
		
		//一级菜单选中状态
		function firstclick(obj){
			var value=$(obj).next().val();
			$(obj).next().show();
 			if(value == "1"){
				$(obj).next().val("0");
			}else{
				$(obj).next().val("1");
 			}
 			$(obj).next().hide();
		}
		
		//二级菜单选中状态
		function twoclick(obj){
			var value=$(obj).next().val();
			$(obj).next().show();
 			if(value == "1"){
				$(obj).next().val("0");
			}else{
				$(obj).next().val("1");
 			}
 			$(obj).next().hide();
		}
		
		var suijiid="";//唯一标识ID
		
		//新增一级分类
		function addfirstsort(obj){
			getId();
 			var str="<div class='dangan3_d3_d2' id='"+suijiid+"'>"+
 			"<input type='text' name='allsort"+suijiid+"' id='allsort"+suijiid+"' value='' style='display:none;width:1px;height:1px;'>"+
 			"<input type='hidden'  class='city_file_sort_id' value='"+suijiid+"'/>"+
            "<span class='dangan3_d3_d2_sp1'>"+
			"	<input class='dangan3_d3_d2_sp1_ms ms' value=''  name='"+suijiid+"sort_name' id='"+suijiid+"sort_name'>"+
	         "       <span class='jian'> <img src='../img/jian.png' width='100%'> </span> "+
	          "  </span>"+
            "<span class='dangan3_d3_d2_sp1'></span>"+
            "<span class=\"dangan3_d3_d2_sp1\"><input  type=\"number\" class=\"dangan3_d3_d2_sp1_ms onesequence\"  name=\""+suijiid+"sequence\" id=\""+suijiid+"sequence\"  onchange=\"issequence(\'onesequence\',this)\"/></span>"+
            "<span class='dangan3_d3_d2_sp1'>"+
	         "   	<a style=\"margin:0 auto;display:block;\" onclick=\"upload(\'"+suijiid+"\')\">"+
				"	        <img src='../img/xinxi.png' width='46px' height='40px' class='"+suijiid+"'/>"+
				"	</a>"+
				"	<input type='text' name='"+suijiid+"sort_imageurl' id='"+suijiid+"sort_imageurl' value='' style='display:none;width:1px;height:1px;'/>"+
            "</span>"+
            "<span class='dangan3_d3_d2_sp1'>"+
             "  <label >"+
              " 		<input type='checkbox' onclick='firstclick(this)'/>"+
              " 		<input type='text' id='"+suijiid+"open_status' name='"+suijiid+"open_status'  value='0'  style='display:none;width:1px;height:1px;'/>"+
	           "    		<span class='mgleft10'>开启</span>&nbsp;&nbsp;&nbsp;"+
               	"</label>"+
               	"<span style='color: red' onclick=\"delpcd1(\'"+suijiid+"\')\" class='delpcd'>删除</span>"+
            "</span>"+
	          "  <span class=\"dangan3_tj1\" onclick=\"addtwosort(\'"+suijiid+"\',this)\">添加二级分类</span>"+
      		"</div>"; 
 			$(obj).parent().before(str);
 			getId();
		}
		
		//新增二级分类
		function addtwosort(value,obj){
			getId();
 			var str="<div class='dangan3_d3_d3' id='"+suijiid+"'> "+
 					"<input type='hidden'  value='"+suijiid+"' class='two"+value+"'/>"+
		            "	<span class='dangan3_d3_d2_sp1'> <span class='dangan3_d3_d3_xian'></span></span>"+
		           	"	<span class='dangan3_d3_d2_sp1'>"+
		             " 		<input class='dangan3_d3_d2_sp1_ms' value='' name='"+suijiid+"sort_name'/> "+
		            	"</span>"+
		            	"<span class='dangan3_d3_d2_sp1'>"+
		              "		<input type=\"number\" class=\"dangan3_d3_d2_sp1_ms w_"+value+"\" name=\""+suijiid+"sequence\" onchange=\"issequence(\'w_"+value+"\',this)\"/> "+
		            "	</span>"+
		            "	<span class='dangan3_d3_d2_sp1'>"+
		            "		<a style=\"margin:0 auto;display:block;\" onclick=\"upload(\'"+suijiid+"\')\">"+
            		"			<img src='../img/xinxi.png' width='46px' height='40px' class='"+suijiid+"'/>"+
            		" 		</a>"+
            		"		<input type='text' name='"+suijiid+"sort_imageurl'  id='"+suijiid+"sort_imageurl' value='' style='display:none;width:1px;height:1px;'/> "+
 		            "   </span>"+
		            "	<span class='dangan3_d3_d2_sp1'>"+
		             "  	<label >"+
		              " 			<input type='checkbox'  onclick='twoclick(this)'/>"+
		              " 			<input type='text' id='"+suijiid+"open_status'  name='"+suijiid+"open_status'  value='0' style='display:none;width:1px;height:1px;'/>"+
		               "			<span class='mgleft10'>开启</span>&nbsp;&nbsp;&nbsp;"+
		               	"	</label>"+
		               	"<a><span style='color: red' onclick=\"delpcd2(\'"+suijiid+"\')\" class='delpcd'>删除</span></a>"+
		           " 	</span>"+
		          " </div> ";
		   $(obj).before(str);
		   getId();
		}
		
		//删除二级分类
		function delpcd1(id){
			var ids = "#"+id;
			$(ids).remove();
		}
		
		//删除二级分类的小类
		function delpcd2(id){
			var ids = "#"+id;
			$(ids).remove();
		}
		
		
		
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
		
		//载入一开始就运行
		$(function(){
			//获取Id
			getId();
		});  
		
		
		//保存
		function savecityfile(){
			var n="0";
			$(".dangan3_d3_d2_sp1_ms").each(function(){
			    var value = $(this).val().trim(); //这里的value就是每一个input的value值~
			    if(value == ""){
			    	$(this).tips({
		  				side:3,
		  	            msg:'文本框内容不能为空',
		  	            bg:'#AE81FF',
		  	            time:2
		  	        });
		  			n="1";
				}
			 });
			//判断省
			if($("#province_id").val() == ""){
				$("#province_id").tips({
	  				side:3,
	  	            msg:'省不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			n="1";
			}
			//判断市
			if($("#city_id").val() == ""){
				$("#city_id").tips({
	  				side:3,
	  	            msg:'市不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			n="1";
			}
			//判断区
			if($("#area_id").val() == ""){
				$("#area_id").tips({
	  				side:3,
	  	            msg:'区不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			n="1";
			}
			var allId="";//一级分类
			var allTwoId="";//二级分类
  			if(n == "0"){
   				//获取所有的一级city_file_sort_id的标签
  				$(".city_file_sort_id").each(function(index,obj){
  				    var value = $(obj).val().trim(); //这里的value就是每一个input的value值
  				    allId=allId+"@"+value;
  					//获取所有的二级city_file_sort_id的标签
  					$(".two"+value).each(function(twoindex,twoobj){
  						var twovalue = $(twoobj).val().trim(); //这里的value就是每一个input的value值
  						allTwoId=allTwoId+"@"+twovalue;
  					});
  					$("#allsort"+value).show();
  		 			$("#allsort"+value).val(allTwoId); 
  		 			allTwoId="";
   	 			 });
   				$("#allcity_file_sort_id").show();
 				$("#allcity_file_sort_id").val(allId);//将所有的city_filt_sort_id拼接起来
				$("#Form").submit();
			}
 		}
		
		
		var classId="";//class的唯一标示
		
		//上传按钮点击
		function upload(value){
			classId=value;
			$(".uploanImage").click();
		}
		
		//上传图片
		function fileType(obj){
			var d=/\.[^\.]+$/.exec(obj.value); 
			if(!validaImage(d)){
				alert("请上传照片gif,png,jpg,jpeg格式");
			}else{
				$("#imageForm").ajaxSubmit({  
				  	url : '<%=basePath%>zhihui_city_file/uploadheadimage.do',
			        type: "POST",//提交类型  
			      	dataType:"json",
			   		success:function(result){
			   			 var url=result.url;
			   			 $("#"+classId+"sort_imageurl").show();
						 $("#"+classId+"sort_imageurl").val(url);
						 $("#"+classId+"sort_imageurl").hide();
						 //替换展示图片
						 $("."+classId).attr("src",url);
 						 classId="";
					}
 				});
					
			}
				
		}	
		//判断图片是否符合格式
		function validaImage(filename){
			if('.gif.png.jpg.jpeg'.indexOf(filename)<0&&'.GIF.PNG.JPG.JPEG'.indexOf(filename)<0){
				return false;
			}
			return true;
		}
        
        </script>
    </body>
</html>