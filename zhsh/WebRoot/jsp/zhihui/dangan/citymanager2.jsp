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
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/zhihui/dangan1.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/dangan1.js"></script>
     </head>
    <body>
    <c:if test="${qx.look eq '1'}">
		<form action="zhihui_citymanager/${msg}.do" name="Form" id="Form" method="post" >
				<input type="hidden" name="citymanager_id" id="citymanager_id" value="${pd.citymanager_id}"/>
				<input type="hidden" name="currentPage" id="currentPage" value="${pd.currentPage}"/>
		        <div class="dangan_d1">
		          <span class="dangan_d1_sp1">城市经理名称</span>
		          <input type="text" class="dangan_d1_ipt1" name="citymanager_name" id="citymanager_name" value="${pd.citymanager_name}"/>
		          <span class="dangan_d1_sp1">城市经理编码</span>
		          <span class="blue">${pd.citymanager_id}</span>
		        </div>
		        <div class="dangan_d1">
 		          <span class="dangan_d1_sp1">办公地址</span>
		          <input type="text" class="dangan_d1_ipt1 ipt3" name="address" id="address" value="${pd.address}"/>
 		          <span class="dangan_d1_sp1">联系电话</span>
		          <input type="text" class="dangan_d1_ipt1" name="phone" id="phone" value="${pd.phone}"/>
 		          
		        </div>
		        <div class="dangan_d2 ywqx">
		        	  <input type="hidden" name="allpcd" value="" id="allpcd"/>
		         	  <span class="dangan_d1_sp1">业务区域</span> 
		         	  <span class="addpcd hands"><a onclick="addpcd()">添加</a></span>
		         	   <br>
  		         	  <c:forEach items="${citymanagerpcd}" var="pcdvar">
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
     		    <div class="dangan_d1">
  		        <c:if test="${qx.look eq '1'}">
		           <span class="dangan_d1_btn1 mgleft11" onclick="save();">保存</span> 
		           </c:if>
		           <span class="dangan_d1_btn1 tc" onclick="backtop()">退出</span>
		        </div>
	      </form>
	      </c:if>
	      <script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框-->
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
	     	
 	      		//退出
	      		function backtop(){
	      			window.top.location.href='zhihui_citymanager/list.do';
	      		}	      
	      		
			    //保存
			  	function save(){
			  		if($("#citymanager_name").val()==""){
			  			$("#citymanager_name").tips({
			  				side:3,
			  	            msg:'请输入城市经理名称',
			  	            bg:'#AE81FF',
			  	            time:2
			  	        });
			  			$("#citymanager_name").focus();
			  			return false;
			  		}
 			  		if($("#phone").val()==""){
			  			$("#phone").tips({
			  				side:3,
			  	            msg:'请输入电话',
			  	            bg:'#AE81FF',
			  	            time:2
			  	        });
			  			$("#phone").focus();
			  			return false;
			  		}
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
   				 
				
 	      </script>
      </body>
</html>