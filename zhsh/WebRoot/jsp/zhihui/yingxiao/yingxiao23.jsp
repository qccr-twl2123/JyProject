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
          <style type="text/css">
        a{cursor: pointer;color:blue;}
        img{ cursor: pointer;  }
        td input[type=text]{    width: 80%;}
         .yingxiao23_d1_sp1 {
		        display: inline-block;
			    width: 100%;
			    height: 51px;
			    line-height: 50px;
			    font-size: 16px;
			    color: #5dc9ba;
 		}
		.yingxiao23_d1{
		    width: 90%;
 		    margin-left: 5%;
		    margin-top: 20px;
		    line-height: 34px;
		        clear: both;
		} 
		.yingxiao23_d1_sp5{
		    display: inline-block;
		}
		.yingxiao2_d1_sp5_title{
		    display:inline-block;
			width:33%;
			text-align: center;
			float: left;
		}
		.yingxiao23_d1_ipt1_one{
		   		width:50%;
		     	height: 26px;
		}
		.yingxiao23_d1_ipt1_two{
		   		width:20%;
		        height: 26px;
		}
		.dangan23_d2_table{
			width: 90%;
		    border: 2px solid #8f8f8f;
		    border-top: 1px solid #8f8f8f;
		    border-left: 1px solid #8f8f8f;
		    text-align: center;
		    line-height: 30px;
		    border-radius: 5px;
		    margin-left: 2%;
		    font-size: 14px !important;
		}
		.dangan23_d2_table td {
		    border-left: 1px solid #8f8f8f;
		    border-top: 1px solid #8f8f8f;
		}
		.dangan23_btn1 {
		    display: inline-block;
		    width: 120px;
		    height: 40px;
		    line-height: 40px;
		    text-align: center;
		    background-color: #5dc9ba;
		    border-radius: 5px;
		    cursor: pointer;
		    margin-left: 4%;
		    color: #fff;
		    font-size: 16px;
		}
		.small{
		   width: 100%;height: 170px;
 		}
		.big{
		  max-width: 84%; 
  		  outline-width:0px;  
  		  vertical-align:top;
		}
		.bigimgclear{
	     	cursor:pointer; height: 75px;width: 7.8%;
	     	display: inline-block;background-color: #5dc9ba;
	     	float: right;margin-top: 9%;font-size: 29px;
	     	text-align: center;color: #ffffff;
	     }
	     .smallimgclear{
	     	cursor:pointer; height: 35px;width: 50%;
	     	display: block;background-color: #5dc9ba;
	     	margin: 0 auto;font-size: 16px;
	     	text-align: center;color: #ffffff;
     }
     </style>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="${msg}.do" name="Form" id="Form" method="post">
    	<input type="hidden" name="currentPage"    value="${empty pd.currentPage?'1':pd.currentPage}"/>
    	<input type="hidden" name="youxuangoods_id"   value="${pd.youxuangoods_id}"/>
    	<input type="hidden" name="goods_check"   value="${pd.goods_check}"/>
       <div class="yingxiao23_d1">
	        <span class="yingxiao23_d1_sp1">一、编辑商品基础资料：档期（${goodspd.youxuandq_name}）</span>
        </div>
       <div class="yingxiao23_d1">
	        <span class="yingxiao23_d1_sp5">品牌名称</span>
 	        <input type="text" placeholder="品牌名称"  class="yingxiao23_d1_ipt1_one" value="${goodspd.brand_name}" name="brand_name" id="brand_name" /><br/>
	        <span class="yingxiao23_d1_sp5">商品名称</span>
 	        <input type="text" placeholder="商品名称"  class="yingxiao23_d1_ipt1_one" value="${goodspd.goods_name}" name="goods_name" id="goods_name" /><br/>
        	<span>添加规格，颜色等参数</span><br/>
        	<input type="hidden" name="ggliststr"  class="ggliststr"  value=""/>
        	<table  border="0" cellspacing="0" cellpadding="0" class="dangan23_d2_table">
	              <tr >
	              	 <td>图片</td>
	                <td>参数描述</td>
	                <td>爆品价</td>
	                <td>库存量</td>
	                <td>剩余售卖商品数量</td>
	                <td>选项</td>
	                <td>操作</td>
 	              </tr> 
 	              <tbody class="gg_list">
	 	              <c:forEach items="${goodsgglist}" var="var" >
		 	               <tr class="gg" youxuangg_id="${var.youxuangg_id }">
		 	               		<td><img onclick="uploadTwo(this)" src="${var.gg_imageurl }" width="60px" height="60px" /><input type="hidden" value="${var.gg_imageurl }"/></td>
		 	               		<td><input type="text" value="${var.gg_miaosu }" /></td>
		 	               		<td><input type="text" value="${var.sale_money }"  oninput="qingkongzk()"/></td>
		 	               		<td><input type="text" value="${var.gudingsale_number }" readonly="readonly" style="background-color: #e3dfdf"/></td>
		 	               		<td><input type="text" value="${var.needsale_number }"   /></td>
		 	               		<td><a onclick="sznowpay(this)">该条设为引导信息</a></td>
		 	               		<td><a onclick="deletegg(this)" class="dangan23_btn1">删除</a></td>
		 	               </tr>
  	              	  </c:forEach>
 	              </tbody>
  	           </table>
  	           <div class="addtr" style="text-align: center;" onclick="addtr()">
 	              		<img src="img/add.png"  width="3%"/>
 	           </div>
 	           <br/>
 	           <span >编辑展示页引导信息</span><br/>
 	           <span class="yingxiao23_d1_sp5">日常价</span>
 	           <input type="text" placeholder="日常价"  class="yingxiao23_d1_ipt1_two" value="${goodspd.rc_salemoney}" name="rc_salemoney" id="rc_salemoney" />
 	           <span class="yingxiao23_d1_sp5">官方指导价</span>
 	           <input type="text" placeholder="官方指导价"  class="yingxiao23_d1_ipt1_two" value="${goodspd.gf_salemoney}" name="gf_salemoney" id="gf_salemoney" oninput="qingkongzk()" />
 	           <span class="yingxiao23_d1_sp5">商品单位</span>
 	           <input type="text" placeholder="商品单位"  class="yingxiao23_d1_ipt1_two" value="${goodspd.goods_dw}" name="goods_dw" id="goods_dw" />
 	           <br><span class="yingxiao23_d1_sp5">优选爆品价</span>
 	           <input type="text" placeholder="优选爆品价"  class="yingxiao23_d1_ipt1_two" value="${goodspd.bp_salemoney}" name="bp_salemoney" id="bp_salemoney" readonly="readonly" style="background-color: #e3dfdf"/>
 	           <span class="yingxiao23_d1_sp5">折扣率</span>
 	           <input type="text" placeholder="折扣率"  class="yingxiao23_d1_ipt1_two" value="${goodspd.goods_zkrate}" name="goods_zkrate" id="goods_zkrate" readonly="readonly" style="background-color: #e3dfdf"/>
          	   <br/><span>是否淘宝、京东同款：</span>
         	   <input type="radio"  name="tk"  class="yingxiao23_d1_ipt1" ${goodspd.istongkuan eq '0'?'checked':'' } onclick="istk('0')"  />否
         	   <input type="radio"  name="tk"  class="yingxiao23_d1_ipt1" ${goodspd.istongkuan eq '1'?'checked':'' } onclick="istk('1')" />是
         	   <input type="hidden"  value="${goodspd.istongkuan}"  name="istongkuan" id="istongkuan" />
         	   <span class="yingxiao23_d1_sp5">网上售价</span>
  	           <input type="text" placeholder="网上售价"  class="yingxiao23_d1_ipt1_two" value="${goodspd.ws_salemoney}"  name="ws_salemoney" id="ws_salemoney" />
        	   <br/><span >以下为商品简介，字段由商家添加：</span>
        	   <input type="hidden" name="jjliststr" class="jjliststr"  value=""/>
        	   <br/>
        	   <table  border="0" cellspacing="0" cellpadding="0" class="dangan23_d2_table">
	              <tr >
	                <td>标题名称</td>
	                <td>内容简介</td>
	                <td>操作</td>
  	              </tr> 
 	              <tbody class="jj_list">
	 	              <c:forEach items="${goodsjjlist}" var="var" >
		 	               <tr class="jj" youxuanjj_id="${var.youxuanjj_id }">
		 	               		<td><input type="text" value="${var.title }" /></td>
		 	               		<td><input type="text" value="${var.text }" /></td>
 		 	               		<td><a onclick="deletejj(this)" class="dangan23_btn1">删除</a></td>
		 	               </tr>
  	              	  </c:forEach>
 	              </tbody>
  	           </table>
  	           <div class="addjj" style="text-align: center;" onclick="addjj()">
 	              		<img src="img/add.png"  width="3%"/>
 	           </div>
       </div>
        <div class="yingxiao23_d1">
	        <span class="yingxiao23_d1_sp1">二、选择营销方案：</span>
        </div>
       <div class="yingxiao23_d1">
	        <span class="yingxiao23_d1_sp5">赠送积分率：</span>
 	        <input type="number" placeholder="赠送积分率（单位%）"  class="yingxiao23_d1_ipt1_two" value="${goodspd.goods_jfrate}" name="goods_jfrate" id="goods_jfrate" />
 	   		<span class="yingxiao23_d1_sp5">是否限购：</span>
         	   <input type="radio"  name="xg"    ${goodspd.isxiangou eq '0'?'checked':'' } onclick="isxg('0')"  />否
         	   <input type="radio"  name="xg"    ${goodspd.isxiangou eq '1'?'checked':'' } onclick="isxg('1')"   />是
         	   <input type="hidden" value="${goodspd.isxiangou}"  name="isxiangou" id="isxiangou" />
         	   <span class="yingxiao23_d1_sp5">每个会员限购</span>
 	           <input type="number" placeholder="限购数量"  class="yingxiao23_d1_ipt1_two" value="${goodspd.xiangou_number}"  name="xiangou_number" id="xiangou_number" />
         	   <br>
         	   <span class="yingxiao23_d1_sp5">提货地址</span>
 	           <input type="text" placeholder="提货地址"  class="yingxiao23_d1_ipt1_one" value="${goodspd.th_address}"  name="th_address" id="th_address" />
         	   <br>
         	   <span class="yingxiao23_d1_sp5">提货时间（下单成功后）</span>
 	           <input type="text" placeholder="单位（天）"  class="yingxiao23_d1_ipt1_one" value="${goodspd.limit_day}"  name="limit_day" id="limit_day" />多少天内提货
 	   </div>
       <div class="yingxiao23_d1">
         	<span class="yingxiao23_d1_sp1">三、编辑商品缩略图：</span>
         	<input type="hidden" name="small_images" class="small_images" value="${goodspd.small_images}"  />
       </div>
       <div  class="yingxiao23_d1">
       		<c:forEach items="${smalllist }" var="smallimage">
       				  <div style="height:215px;    width: 15%;    float: left;    margin: 0px 1%;">
							<img src="${smallimage}"  class="small"  onclick="upload(this)"   />
							<div class="smallimgclear" onclick="removesmallimgdiv(this)">清空</div>
					   </div>
         	</c:forEach>
  			<!-- 结束 -->
       </div>
       <div class="yingxiao23_d1">
         	<span class="yingxiao23_d1_sp1">四、编辑商品详情图：</span>
         	<input type="hidden" name="big_images" class="big_images" value="${goodspd.big_images}"  />
       </div>
       <div  class="yingxiao23_d1" style="text-align: center;">	
       		<c:forEach items="${biglist }" var="bigimage">
       				<div style="width:50%;margin:0 auto;">	
 	  					<div class="bigimgclear" onclick="removebigimgdiv(this)">删除</div>
	 	  				<img src="${empty bigimage?'img/changeadd.png':bigimage}" class="big" onclick="upload(this)"  /><br/>
   					</div>
         	</c:forEach>
       </div>
       <div class="yingxiao23_d1" style="text-align: center;" onclick="addbigimages(this)">
 	              	<img src="img/add.png"  width="3%"  />
 	   </div>
 	   <div class="yingxiao23_d1" style="text-align: center;" >
 	              点+可添加图片，点圈+可以新增图片
 	   </div>
       <div class="yingxiao23_d1">
             <c:if test="${qx.edit eq '1' }">
           		<span class="dangan23_btn1" onclick="tijiao()">提交</span>
           </c:if>
           <span class="dangan23_btn1" onclick="backtop()">返回</span>
      </div>	 
	</form>
	</c:if>
	<form action="zhihui_city_file/uploanImage.do" method="post" name="imageForm" id="imageForm"  enctype="multipart/form-data"> 
 	        <input type="file" style="width:1px;display:none;"   id="upload-file"  name="uploanImage" class="upload-file" onchange="fileType(this)"/>
 	</form>
 	<script src="js/jquery-1.8.0.min.js"></script>
	<script src="js/jquery.form.js"></script>
	<script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框-->
	<script type="text/javascript">
		//退出
		function backtop(){
			window.location.href='youxuan/datalistPageGoods.do?currentPage=${empty pd.currentPage?'1':pd.currentPage}&goods_check=${empty pd.goods_check?'0':pd.goods_check}';
		}	
	
		//新增规格
			function addtr(){
				var str="<tr class='gg' youxuangg_id='new'>"+
							"<td><img src='' onclick='uploadTwo(this)' width='60px' height='60px'/><input type='hidden' value=''/></td>"+
							"<td><input type='text' value='' /></td>"+
							"<td><input type='text' value='' /></td>"+
							"<td><input type='text' value='' /></td>"+
							"<td><input type='text' value=''  /></td>"+
							"<td><a onclick='sznowpay(this)'>该条设为引导信息</a></td>"+
							"<td><a onclick='deletegg(this)' class='dangan23_btn1'>删除</a></td>"+
						"</tr>";
				$(".gg_list").append(str);
			}
		
			//设置为引导项
			function sznowpay(obj){
				var gf_salemoney=$("#gf_salemoney").val();
				var bp_salemoney=$(obj).parent().prev().prev().prev().find("input").val()
				if(gf_salemoney == "" || bp_salemoney==""){
					alert("官方价/爆品价不能为空");
					return;
				}
				$("#bp_salemoney").val(bp_salemoney);
				var s=(bp_salemoney/gf_salemoney).toFixed(2)+"";
				if(s.substring(s.indexOf(".")).length == 1){
					s=((bp_salemoney/gf_salemoney).toFixed(2)*10).toFixed(0)+"";
				}else{
					s=((bp_salemoney/gf_salemoney).toFixed(2)*100).toFixed(0)+"";
				}
				$("#goods_zkrate").val(s);
			}
			//删除规格
			function deletegg(obj){
				$(obj).parent().parent().remove();
				var youxuangg_id=$(obj).parent().parent().attr("youxuangg_id");
				var url="<%=basePath%>youxuan/deletegg.do?youxuangg_id="+youxuangg_id;
				$.get(url,function(data){
				if(data=="success"){
					 //OK
				}
			});
			}
         
     	//新增规格
			function addjj(){
				var str="<tr class='jj' youxuanjj_id='new'>"+
							"<td><input class='yingxiao2_d1_sp5_two' type='text' value='' /></td>"+
							"<td><input class='yingxiao2_d1_sp5_two' type='text' value=''/> </td>"+
							"<td><a onclick='deletejj(this)' class='dangan23_btn1'>删除</a></td>"+
						"</tr>";
				$(".jj_list").append(str);
			}
			//删除规格
			function deletejj(obj){
				$(obj).parent().parent().remove();
				var youxuanjj_id=$(obj).parent().parent().attr("youxuanjj_id");
				var url="<%=basePath%>youxuan/deletejj.do?youxuanjj_id="+youxuanjj_id;
				$.get(url,function(data){
				if(data=="success"){
					 //OK
				}
			});
			}
		
			
			function istk(value){
      		   $("#istongkuan").val(value);
      	   }
		
			
			 function isxg(value){
       		   $("#isxiangou").val(value);
       	   }
			
			
			
			
			
			 function addbigimages(obj){
		 		   var str=" <div style='width:50%;margin:0 auto;'>	<div class='bigimgclear' onclick='removebigimgdiv(this)'>删除</div><img src='img/changeadd.png' onclick='upload(this)' class='big' /></div>";
		  		   $(obj).prev().append(str);
		 	   }
		
		
		//	提交
 		function tijiao(){
	 			if($("#brand_name").val()==""){
	 				$("#brand_name").tips({
	 					side:3,
	 		            msg:'品牌名称不能为空',
	 		            bg:'#AE81FF',
	 		            time:2
	 		        });
	 				$("#brand_name").focus();
	 				return false;
	 			 }
		 			 if($("#goods_name").val()==""){
	 				$("#goods_name").tips({
	 					side:3,
	 		            msg:'商品名称不能为空',
	 		            bg:'#AE81FF',
	 		            time:2
	 		        });
	 				$("#goods_name").focus();
	 				return false;
	 			 }
	 			 if($("#rc_salemoney").val()==""){
	 				$("#rc_salemoney").tips({
	 					side:3,
	 		            msg:'日常售价不能为空',
	 		            bg:'#AE81FF',
	 		            time:2
	 		        });
	 				$("#rc_salemoney").focus();
	 				return false;
	 			 }
	 			 if($("#istongkuan").val()=="1" && $("#ws_salemoney").val().trim() == "" ){
	 				$("#ws_salemoney").tips({
	 					side:3,
	 		            msg:'网上售价不能为空',
	 		            bg:'#AE81FF',
	 		            time:2
	 		        });
	 				$("#ws_salemoney").focus();
	 				return false;
	 			 }
	 			if($("#goods_zkrate").val()==""){
	 				$("#goods_zkrate").tips({
	 					side:3,
	 		            msg:'折扣率不能为空',
	 		            bg:'#AE81FF',
	 		            time:2
	 		        });
	 				$("#goods_zkrate").focus();
	 				return false;
	 			 }
 			  //循环所有的规格
 			  var flag=true;
 			  var ggliststr="";
 			  $(".gg").each(function(n,obj){
 				 var youxuangg_id=$(obj).attr("youxuangg_id");
				  var ggstr="";
				  for(var i=0;i<5;i++){
					 if($(obj).find("td").eq(i).find("input").val().trim() == ""){
						 	flag=false;
					 }
 					 ggstr+=$(obj).find("td").eq(i).find("input").val()+"@";
				  }
				 ggliststr+=youxuangg_id+"!"+ggstr+",";
  			  });
 			 if(!flag){
				  alert("商品的规格必要参数不能为空");
				  return;
			  }
 			  $(".ggliststr").val(ggliststr);
 			 if(ggliststr == ""){
				  alert("规格不能为空");
				  return;
			  }
 			  //循环所有的简介
 			  var jjliststr="";
 			  $(".jj").each(function(n,obj){
 				  var youxuanjj_id=$(obj).attr("youxuanjj_id");
 				  var jjstr="";
 				  for(var i=0;i<2;i++){
  					  jjstr+=$(obj).find("td").eq(i).find("input").val()+"@";
 				  }
 				 jjliststr+=youxuanjj_id+"!"+jjstr+",";
  			  });
 			  $(".jjliststr").val(jjliststr);
 			 if(jjliststr == ""){
				  alert("简介不能为空");
				  return;
			  }
			 if($("#goods_jfrate").val()==""){
	 				$("#goods_jfrate").tips({
	 					side:3,
	 		            msg:'折扣率不能为空',
	 		            bg:'#AE81FF',
	 		            time:2
	 		        });
	 				$("#goods_jfrate").focus();
	 				return false;
	 		  }
			  var isxiangou=$("#isxiangou").val();
			  if(isxiangou == "1"){
				 if($("#xiangou_number").val()==""){
	 	 				$("#xiangou_number").tips({
	 	 					side:3,
	 	 		            msg:'限购数量不能为空',
	 	 		            bg:'#AE81FF',
	 	 		            time:2
	 	 		        });
	 	 				$("#xiangou_number").focus();
	 	 				return false;
	 	 		  }
			  }
  			  //循环所有的小图
  			  var smallliststr="";
 			  $(".small").each(function(n,obj){
	 				  if($(obj).attr("src") != "img/imgadd.png"){
	 					 smallliststr+=$(obj).attr("src")+",";
	 				  }
     			  });
 			  $(".small_images").val(smallliststr);
 			  if(smallliststr == ""){
				  alert("缩略图不能为空");
				  return;
			  }
 			  //循环所有的大图
 			   var bigliststr="";
 			  $(".big").each(function(n,obj){
 				  	if($(obj).attr("src") != "img/changeadd.png"){
 				  		bigliststr+=$(obj).attr("src")+",";
 				  	}
     			  });
 			  $(".big_images").val(bigliststr);
 			 if(bigliststr == ""){
				  alert("详情图不能为空");
				  return;
			  }
 			$("#Form").ajaxSubmit({  
				  	url : '<%=basePath%>${msg}.do',
			        type: "POST",//提交类型  
			      	dataType:"json",
			   		success:function(data){
			   			if(data.result=="1"){
			   				window.location.reload();
			   			}else{
			   				window.location.href='<%=basePath%>'+data.url;
			   			}
 					}
			}); 
  			//$("#Form").submit();//提交
 		}
		
 		//上传按钮点击
 		function upload(obj){
 			window._img=obj;
 			window.image_type="1";
	  		$(".upload-file").click();
 		}
 		
  		//头像上传按钮点击
 		function uploadTwo(obj){
 			window._img=$(obj);
 			window.image_type="0";
  			$(".upload-file").click();
 		}
  		
  		//上传图片
 		function fileType(obj){
 	   		var d=/\.[^\.]+$/.exec(obj.value); 
 	  		if(!validaImage(d)){
 				alert("请上传照片gif,png,jpg,jpeg格式");
 			}else{
   	 			$("#imageForm").ajaxSubmit({  
 				  	url : '<%=basePath%>zhihui_city_file/uploadheadimageByGoods.do?tm='+new Date().getTime(),
 			        type: "POST",//提交类型  
 			      	dataType:"json",
 			   		success:function(result){
 			   			 var url=result.url;
  						 $(_img).attr("src",url);
  						 if(image_type == "0"){
  							$(_img).next().val(url);
  						 }
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
  		
  		
 		 //替换小图片
	    function removesmallimgdiv(obj){
 	    	$(obj).prev().attr("src","img/imgadd.png");
 	    }
	    
	    //移除详情图片
	    function removebigimgdiv(obj){
	    	$(obj).parent().remove();
	    }
	    

	    //清空折扣率以及爆屏价
	    function qingkongzk(){
	    	$("#bp_salemoney").val("");
	    	$("#goods_zkrate").val("");
 	    }
	    
	    
	    
	    
	</script>
    </body>
 </html>