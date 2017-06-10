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
        <link rel="stylesheet" href="../css/zhihui/dangan1.css">
        <script src="../js/jquery-1.8.0.min.js"></script>
        <script src="../js/zhihui/dangan1.js"></script>
        
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
        <div class="zhihui_body_right_header">
                   <span class="dingwei"><img src="../img/dingwei.png" ></span>
                   <div class="dingwei_right">
                       <span >您所在的位置：</span>
                       >
                       <span class="dingweitext">档案管理</span>
                       >
                       <span class="dingweitext2">子公司档案</span>
                   </div>
                  
        </div>
		<form action="${msg}.do" name="Form" id="Form" method="post" target="_top">
				<input type="hidden" name="subsidiary_id" id="subsidiary_id" value="${pd.subsidiary_id}"/>
				<input type="hidden" name="currentPage" id="currentPage" value="${pd.currentPage}"/>
		        <div class="dangan_d1">
		          <span class="dangan_d1_sp1">子公司名称</span>
		          <input type="text" class="dangan_d1_ipt1" name="subsidiary_name" id="subsidiary_name" value="${pd.subsidiary_name}"/>
		          <span class="dangan_d1_sp1" >内部名称</span>
		          <input type="text" class="dangan_d1_ipt1" name="house_name" id="house_name" value="${pd.house_name}" />
		        </div>
		        <div class="dangan_d1">
		          <span class="dangan_d1_sp1">子公司工商名称</span>
		          <input type="text" class="dangan_d1_ipt1" name="subsidiary_ic_name" id="subsidiary_ic_name" value="${pd.subsidiary_ic_name}"/>
		          <span class="dangan_d1_sp1">子公司编码</span>
		          <span class="blue">${pd.subsidiary_id}</span>
		        </div>
		        <div class="dangan_d2 ywqx">
		        	  <input type="hidden" name="allpcd" value="" id="allpcd"/>
		         	  <span class="dangan_d1_sp1">业务区域</span> 
		         	  <c:if test="${qx.add eq '1'}"><span class="addpcd hands"><a onclick="addpcd()">添加</a></span></c:if>
		         	   <br>
  		         	  <c:forEach items="${subsidiarypcd}" var="pcdvar">
	 	  		         	  <span>
		  		         	  <br>
			 			          <select class="dangan_d1_st1 province"   onchange="addsearchfind(this);" id="sheng"> 
					 		           	<option value="">请选择省</option>
					 		           	<c:forEach items="${provicelist}" var="var">
			 								<option value="${var.pcd_id}" ${pcdvar.province_id eq var.pcd_id ?'selected':''}>${var.name}</option>
										</c:forEach>
				 		          </select>
						          <select class="dangan_d1_st1" onchange="addsearcharea(this);" id="shi">
						            	<option value="${pcdvar.city_id}">${pcdvar.city_name}</option>
						          </select>
						          <select class="dangan_d1_st1" id="qu" >
						            	<option value="${pcdvar.area_id}">${pcdvar.area_name}</option>
				 		          </select>
				 		          <span onclick="delpcd(this)" class="delpcd">×</span>
			 		           <br>
		 		         	 </span>
 	 		          </c:forEach>
		        </div>
		        <script type="text/javascript">
		      	//1、首先创建js数组 
  		        function addpcd(){
		      		var id=randomWord(32);
		      		var qu=$("#qu option:selected").html();
			         var sheng=$("#sheng option:selected").html();
			         var shi=$("#shi option:selected").html();
		    		 if(sheng == ""){
		    			 alert("请选择省！");
		    			 return;
		    		 }
		        	var str="<span> <br><select class='dangan_d1_st1 province'  onchange='addsearchfind(this)'>";
		        	str+="<option value=''>请选择</option>"
		        	//2、通过c标签循环sinList 并生成添加到array的字符串 
			        <c:forEach items='${provicelist}' var='a'> 
			        str+="<option value='${a.pcd_id}'>${a.name}</option>"
  			        </c:forEach> 
			        str+="</select>";
			        str+="<select class='dangan_d1_st1' onchange='addsearcharea(this);'></select>";
			        str+="<select class='dangan_d1_st1' ></select>";
			        str+="<span class='delpcd' onclick='delpcd(this)' >×</span> <br></span>";
			        $(".ywqx").append(str);
			       /*  var pcdxx="<span style='margin-left: 90px; color: red'"+"已添加城市："+sheng+"-"+shi+"-"+qu;
			        pcdxx+="</span>";
			        $(".pcdxx").append(pcdxx); */
				        }
 		     	//随机生成指定个数的字符串
 		       function randomWord(range){
 		           var str = "",
 		              arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

 		           for(var i=0; i<range; i++){
 		               pos = Math.round(Math.random() * (arr.length-1));
 		               str += arr[pos];
 		           }
 		           return str;
 		        }
 		     	//移除区域
 		     	function delpcd(obj){
 		     		$(obj).parent().remove();
 		     		$(obj).parent().prev().remove();
 		     		$(obj).parent().next().remove();
 		     	}
  		        </script>
		        <div class="dangan_d1">
		          <span class="dangan_d1_sp1">办公地址</span>
		          <input type="text" class="dangan_d1_ipt1 ipt3" name="address" id="address" value="${pd.address}"/>
		        </div>
		        <div class="dangan_d1">
		          <span class="dangan_d1_sp1">重要岗位联系</span>
		          <c:if test="${qx.add eq '1'}">
		          	<span class="dangan_d1_btn1" onclick="addZhiwu()">添加</span>
		          </c:if>
		        </div>
		        <div class="zhiwu">
		        	<input type="hidden" name="allpostid" id="allpostid" value="" >
 			        <c:if test="${!empty postList}">
			        	<c:forEach items="${postList}" var="var">
			        		<span>
			        		<input type="hidden"  class="post_id" value="${var.subsidiary_posts_id}">
				        	<div class="dangan_d1">
						          <span class="dangan_d1_sp1" >姓名</span>
						          <input type="text" class="dangan_d1_ipt2" name="${var.subsidiary_posts_id}post" id="post" value="${var.post}"/>
						          <span class="dangan_d1_sp2">固话</span>
						          <input type="text" class="dangan_d1_ipt2" name="${var.subsidiary_posts_id}fixed_telephone" id="fixed_telephone" maxlength="15" value="${var.fixed_telephone}" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
						          <span class="dangan_d1_sp2">手机</span>
						          <input type="text" class="dangan_d1_ipt2" name="${var.subsidiary_posts_id}phone" id="phone" value="${var.phone}" maxlength="11" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
					        </div>
			 		        <div class="dangan_d1">
						          <span class="dangan_d1_sp1">e-mail</span>
						          <input type="email" class="dangan_d1_ipt2" name="${var.subsidiary_posts_id}email" id="email" value="${var.email}" />
						          <span class="dangan_d1_sp2">微信</span>
						          <input type="text" class="dangan_d1_ipt2" name="${var.subsidiary_posts_id}wechat" id="wechat" value="${var.wechat}"/>
						          <span class="dangan_d1_sp2">QQ</span>
						          <input type="text" class="dangan_d1_ipt2" name="${var.subsidiary_posts_id}qq" id="qq" value="${var.qq}" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" maxlength="12"/>
					        	  <span class="delpost" onclick="delpost(this)">移除</span>
					        </div>
 					        </span>
		 		        </c:forEach>
			        </c:if>
			        <c:if test="${empty postList}">
			        	<span>
				        	<input type="hidden"  class="post_id" value="${pd.subsidiary_posts_id}">
				        	<div class="dangan_d1">
	 						          <span class="dangan_d1_sp1" >姓名</span>
							          <input type="text" class="dangan_d1_ipt2" name="${pd.subsidiary_posts_id}post" id="post" value=" "/>
							          <span class="dangan_d1_sp2">固话</span>
							          <input type="text" class="dangan_d1_ipt2" name="${pd.subsidiary_posts_id}fixed_telephone" id="fixed_telephone" maxlength="15" value=" " onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
							          <span class="dangan_d1_sp2">手机</span>
							          <input type="text" class="dangan_d1_ipt2" name="${pd.subsidiary_posts_id}phone" id="phone" value=" " maxlength="11" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
						     </div>
				 		     <div class="dangan_d1">
							          <span class="dangan_d1_sp1">e-mail</span>
							          <input type="email" class="dangan_d1_ipt2" name="${pd.subsidiary_posts_id}email" id="email" value="" />
							          <span class="dangan_d1_sp2">微信</span>
							          <input type="text" class="dangan_d1_ipt2" name="${pd.subsidiary_posts_id}wechat" id="wechat" value=""/>
							          <span class="dangan_d1_sp2">QQ</span>
							          <input type="text" class="dangan_d1_ipt2" name="${pd.subsidiary_posts_id}qq" id="qq" value="" maxlength="12" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
						      		  <span class="delpost" onclick="delpost(this)">X</span>
						      </div>
					      </span>
 			        </c:if>
		        </div>
  		        <div class="dangan_d1">
  		        <c:if test="${qx.add eq '1'}">
		           <span class="dangan_d1_btn1 mgleft11" onclick="save();">保存</span> 
		         </c:if>
		         <span class="dangan_d1_btn1 tc" onclick="backtop()">退出</span>
		        </div>
	      </form>
	      </c:if>
	      <script type="text/javascript" src="../js/jquery.tips.js"></script><!--提示框-->
	      <script type="text/javascript">
			
			//载入一开始就运行
			$(function(){
				//获取Id
				getId();
			});  
			
	      		//退出
	      		function backtop(){
	      			window.top.location.href='<%=path%>/zhihui_index.do';
	      		}	      
			    //保存
			  	function save(){
			  		if($("#subsidiary_name").val()==""){
			  			$("#subsidiary_name").tips({
			  				side:3,
			  	            msg:'请输入子公司名称',
			  	            bg:'#AE81FF',
			  	            time:2
			  	        });
			  			$("#subsidiary_name").focus();
			  			return false;
			  		}
			  		if($("#house_name").val()==""){
			  			$("#house_name").tips({
			  				side:3,
			  	            msg:'请输入内部公司名称',
			  	            bg:'#AE81FF',
			  	            time:2
			  	        });
			  			$("#house_name").focus();
			  			return false;
			  		}
			  		if($("#subsidiary_ic_name").val()==""){
			  			$("#subsidiary_ic_name").tips({
			  				side:3,
			  	            msg:'请输入子公司工商名称',
			  	            bg:'#AE81FF',
			  	            time:2
			  	        });
			  			$("#subsidiary_ic_name").focus();
			  			return false;
			  		}
			  		if($("#province_id").val()==""){
			  			$("#province_id").tips({
			  				side:3,
			  	            msg:'请输入省id',
			  	            bg:'#AE81FF',
			  	            time:2
			  	        });
			  			$("#province_id").focus();
			  			return false;
			  		}

			  		//统计所有职务
			  		var allpostid="";
			  		$(".post_id").each(function(i,obj){
  						var value = $(obj).val().trim(); //这里的value就是每一个input的value值
  						allpostid+=value+"@";
  					});
			  		$("#allpostid").val(allpostid); 
			  		var allpcd="";
			  		//统计所有的省市区区域
			  		$(".province").each(function(n,data){
			  			var id=$(data).attr("id");
			  			var province_id=$(data).val();
			  			if(province_id != ""){
			  				var city_id=$(data).next().val();
				  			var area_id=$(data).next().next().val();
				  			var onepcd=province_id+"@"+city_id+"@"+area_id;
				  			allpcd+=onepcd+",";
			  			}
  			  		});
			  		$("#allpcd").val(allpcd);
  			  		 //提交
		 	  		$("#Form").submit();
			  	}
			    
			    $("#aaa").click();
			    
			    //获取城市
				function addsearchfind(obj){
					var str=$(obj).val();//获取被选中的value值
 					$.ajax({
						  url: '<%=path%>/zhihui_subsidiary/citylist.do',
						  data:"province_id="+str,
						  type:"post",
						  dataType:"json",
						  success:function(data){
							  	var list=data.citylist;
		 					  	$(obj).next().empty();
		 					  	$(obj).next().next().empty();
							  	if(list.length>0){
							  		$(obj).next().append("<option value='0'>请选择市</option>");
							  		$(obj).next().next().append("<option  value='0'>请选择区</option>");
								  	for(var i=0;i<list.length;i++){
								  		$(obj).next().append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
								  	}
							  	}
						  },
						  error:function(a){
						  	alert("异常");
						  }
					});
				}
					
				//获取区域
				function addsearcharea(obj){
					var str=$(obj).val();//获取被选中的value值
					$.ajax({
						  url: '<%=path%>/zhihui_subsidiary/arealist.do',
						  data:"city_id="+str,
						  type:"post",
						  dataType:"json",
		 				  success:function(data){
							  	var list=data.arealist;
							  	$(obj).next().empty();
							  	$(obj).next().append("<option  value='0'>全市</option>");
							  	if(list.length>0){
								  	for(var i=0;i<list.length;i++){
								  		$(obj).next().append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
								  	}
						  		}
						  },
						  error:function(a){
						  alert("异常");
						  }
					});
				}
				
				//新增职务
				function addZhiwu(){
					getId();
 					var str="<span><div class='dangan_d1'>"+
 							  "     <input type='hidden'  class='post_id' value='"+suijiid+"'/>"+
					          " 	<span class='dangan_d1_sp1' >姓名</span>"+
					          " 	<input type='text' class='dangan_d1_ipt2' name='"+suijiid+"post' id='post' value=' '/>"+
					          " 	<span class='dangan_d1_sp2'>固话</span>"+
					          "		<input type='text' class='dangan_d1_ipt2' name='"+suijiid+"fixed_telephone' id='fixed_telephone' value=' '/>"+
					          "		<span class='dangan_d1_sp2'>手机</span>"+
					          "		<input type='text' class='dangan_d1_ipt2' name='"+suijiid+"phone' id='phone' value=' '/>"+
						     "</div>"+
						     "<div class='dangan_d1'>"+
							  "     <span class='dangan_d1_sp1'>e-mail</span>"+
							  "     <input type='text' class='dangan_d1_ipt2' name='"+suijiid+"email' id='email' value='' />"+
							  "     <span class='dangan_d1_sp2'>微信</span>"+
							  "     <input type='text' class='dangan_d1_ipt2' name='"+suijiid+"wechat' id='wechat' value=''/>"+
							  "     <span class='dangan_d1_sp2'>QQ</span>"+
							  "     <input type='text' class='dangan_d1_ipt2' name='"+suijiid+"qq' id='qq' value=''/>"+
							  "     <span class='delpost' onclick='delpost(this)''>X</span>"+
						     "</div></span>";
					$(".zhiwu").append(str);
 				}
 				
				var suijiid="";//唯一标识ID
				
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

				
				//移除区域
 		     	function delpost(obj){
  		     		$(obj).parent().parent().remove();
 		     	}
				
 	      </script>
      </body>
</html>