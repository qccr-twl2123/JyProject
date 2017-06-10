<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>新增优选商品</title>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
    <link rel="stylesheet" href="css/youxuan/bootstrap.min.css">
    <link rel="stylesheet" href="css/youxuan/normalize.min.css">
     <link rel="stylesheet" href="css/youxuan/fonts/iconfont.css">
     <link rel="stylesheet" href="css/youxuan/hsd_sjzx.css">
     <style type="text/css">
     .bigimgclear{
     	cursor:pointer; height: 75px;width: 7.8%;
     	display: inline-block;background-color: #2f8fe3;
     	float: right;margin-top: 9%;font-size: 29px;
     	text-align: center;color: #ffffff;
     }
     .smallimgclear{
     	cursor:pointer; height: 35px;width: 50%;
     	display: block;background-color: #2f8fe3;
     	margin: 0 auto;font-size: 16px;
     	text-align: center;color: #ffffff;
     }
     </style>
 </head>
<body>
	<c:if test="${storeqx.look eq '1'}">
    <form action="${msg}.do" name="Form" id="Form" method="post">
    	<c:if test="${look_type eq 'detail' }">
    		<input type="hidden" name="youxuangoods_id" id="youxuangoods_id"  value="${goodspd.youxuangoods_id}"/>
    	</c:if>
    	<c:if test="${look_type eq 'add' }">
    		<input type="hidden" name="youxuangoods_id" id="youxuangoods_id"  value=""/>
    	</c:if>
     	<input type="hidden" name="city_file_id" id="city_file_id"  value="${empty goodspd.city_file_id?citypd.city_file_id:goodspd.city_file_id }"/>
    	<input type="hidden" name="store_id"   value="${pd.store_id }"/>
		<div class="l_cont">
			<h4>一、固定区域</h4>
			<div class="content">
				<input disabled class="form-control" value="${citypd.province_name}" style="width: 30%;float: left; "/>
				<input disabled class="form-control" value="${citypd.city_name}"  style="width: 30%;float: left;margin-left: 1%;"/>
				<input disabled class="form-control" value="${citypd.area_name}"  style="width: 30%;float: left;margin-left: 1%;"/>
 			</div>
			<h4>二、选择档期</h4>
			<div class="content">
				<c:if test="${look_type eq 'detail' }">
 				<select class="form-control dq" name="youxuandq_id" id="youxuandq_id" >
					<option value="${goodspd.youxuandq_id }"  >${goodspd.youxuandq_name}</option>
				</select>
				</c:if>
				<c:if test="${look_type eq 'add' }">
				<select class="form-control dq" name="youxuandq_id" id="youxuandq_id" >
					<option value="">请选择</option>
				  	<c:forEach items="${ listAllDangqi}" var="var">
				    	<option value="${var.youxuandq_id }"  ${goodspd.youxuandq_id eq var.youxuandq_id?'selected':''  }  >第${var.youxuandq_id}期${var.startdate}-${var.enddate}</option>
				    </c:forEach>
				</select>
				</c:if>
				
			</div>
			<h4>三、选择美工编辑</h4>
			<div class="content">
				<c:if test="${look_type eq 'detail' }">
 					<label class="radio-inline">
					  	<c:if test="${goodspd.bianji_type eq '2'}">
						 九鱼网代编辑
						</c:if>
						<c:if test="${goodspd.bianji_type eq '1'}">
						  商家自己编辑
						</c:if>
					</label>
					<input type="hidden"  value="${goodspd.bianji_type eq'2'?'2':'1' }"  name="bianji_type" id="bianji_type" />
					<input type="hidden"  value="${empty goodspd.bianji_money?citypd.bj_fee:goodspd.bianji_money  }"  name="bianji_money" id="bianji_money" />
					<input type="hidden"  value="${empty goodspd.shangjia_money?citypd.sj_fee:goodspd.shangjia_money }"  name="shangjia_money" id="shangjia_money" />
					<label class="radio-inline">
					  <span class="xianshi_bianji">编辑费用：<span id="bj_fee" style="font-size:16px;color:red;">${empty goodspd.bianji_money?citypd.bj_fee:goodspd.bianji_money }</span>元/支单出</span>
					</label>
				</c:if>
				<c:if test="${look_type eq 'add' }">
					<label class="radio-inline">
					  <input type="radio" name="bianji"  onclick="isbj('1')"   ${goodspd.bianji_type ne '2'?'checked':'' }> 商家自己编辑
					</label>
					<label class="radio-inline">
					  <input type="radio" name="bianji"   onclick="isbj('2')"  ${goodspd.bianji_type eq '2'?'checked':'' } > 九鱼网代编辑
					</label>
					<input type="hidden"  value="${goodspd.bianji_type eq'2'?'2':'1' }"  name="bianji_type" id="bianji_type" />
					<input type="hidden"  value="${empty goodspd.bianji_money?citypd.bj_fee:goodspd.bianji_money  }"  name="bianji_money" id="bianji_money" />
					<input type="hidden"  value="${empty goodspd.shangjia_money?citypd.sj_fee:goodspd.shangjia_money }"  name="shangjia_money" id="shangjia_money" />
					<label class="radio-inline">
					  <span class="xianshi_bianji">编辑费用：<span id="bj_fee" style="font-size:16px;color:red;">${empty goodspd.bianji_money?citypd.bj_fee:goodspd.bianji_money }</span>元/支单出</span>
					</label>
				</c:if>
 			</div>
			<h4>四、编辑商品基础资料</h4>
			<div class="content">
			    <span>品牌名称：</span><input type="text" placeholder="品牌名称" value="${goodspd.brand_name}" name="brand_name" id="brand_name" />
			    <span>商品名称：</span><input type="text" placeholder="商品名称" value="${goodspd.goods_name}" name="goods_name" id="goods_name" />
			    <p>添加规格、颜色等参数</p>
				<input type="hidden"  name="ggliststr"  class="ggliststr"  value="" />
				<table cellspacing="0"  cellpadding="0" >
			    	<thead>
			    		<tr>
			    			<th>*图片</th>
			    			<th>*参数描述</th>
			    			<th>*爆品价</th>
			    			<th>*库存量</th>
			    			<th>*剩余售卖商品数量</th>
			    			<th>选项</th>
			    			<th>操作</th>
			    		</tr>
			    	</thead>
					<tbody class="cs gg_list">
	 	              <c:forEach items="${goodsgglist}" var="var" >
		 	               <tr class="gg" youxuangg_id="${var.youxuangg_id }">
		 	               		<td class="tj">
									<img src="${var.gg_imageurl }" class="zltp" />
									<input type="hidden" value="${var.gg_imageurl }"/>
								</td>
		 	               		<td class="inp"><input type="text" value="${var.gg_miaosu }" placeholder="请填写商品规格描述"/></td>
		 	               		<td class="inp"><input type="text" value="${var.sale_money }"  oninput="qingkongzk()"/></td>
		 	               		<td class="inp"><input type="text" value="${var.gudingsale_number }"  readonly="readonly"  style="background-color: #e3dfdf"/></td>
		 	               		<td class="inp"><input type="text" value="${var.needsale_number }"/></td>
		 	               		<td><a class="col" onclick="sznowpay(this)">该条设为引导信息</a></td>
		 	               		<td><a class="btn" onclick="deletegg(this)" >删除</a></td>
		 	               </tr>
  	              	  </c:forEach>
 	                </tbody>
			    	<tfoot>
			    		<tr>
			    			<td colspan="7"  >
			    				<span class="icon iconfont icon-tianjia tac " onclick="addtr()"></span>
			    			</td>
			    		</tr>
			    	</tfoot>
			    </table>
 
			    <p>编辑展示页引导信息：</p>
			    <div class="yindao">
			    	<p>
				    	<span >日常价</span><input type="text" placeholder="日常价" value="${goodspd.rc_salemoney}" name="rc_salemoney" id="rc_salemoney" onkeyup="value=value.replace(/[^\d\.]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" >
					    <span >官方在指导价</span>
					    <input type="text" placeholder="官方在指导价" value="${goodspd.gf_salemoney}" name="gf_salemoney" id="gf_salemoney" oninput="qingkongzk()" onkeyup="value=value.replace(/[^\d\.]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" >
					    <span>商品单位</span>
					    <input type="text" placeholder="商品单位" value="${goodspd.goods_dw}" name="goods_dw" id="goods_dw">
				    </p>
			    	<p>
			    		<span >优选爆品价</span>
				    	<input type="text" placeholder="由引导设置自动生成"  value="${goodspd.bp_salemoney}" name="bp_salemoney" id="bp_salemoney" readonly="readonly" style="background-color: #e3dfdf">
						<span >折扣率</span>
				    	<input type="text" placeholder="由引导设置自动生成" value="${goodspd.goods_zkrate}" name="goods_zkrate" id="goods_zkrate" readonly="readonly"  style="background-color: #e3dfdf">
						
				    </p>
 			    </div>
			    <div class="tongkuan">
					<span class="disib">是否淘宝、京东同款：</span>
					<div class="radio disib">
					  <label>
						<input type="radio" name="optionsRadios" ${goodspd.istongkuan ne '1'?'checked':'' } onclick="istk('0')" >否
					  </label>
					</div>
					<div class="radio disib">
					  <label>
						<input type="radio" name="optionsRadios" ${goodspd.istongkuan eq '1'?'checked':'' } onclick="istk('1')" >是
					  </label>
					</div>
					<div class="disib">
					  <input type="text" placeholder="网上售价"  value="${goodspd.ws_salemoney}"  name="ws_salemoney" id="ws_salemoney" onkeyup="value=value.replace(/[^\d\.]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />
					</div>						
			    </div>
				 <input type="hidden"  value="${goodspd.istongkuan eq '1'?'1':'0' }"  name="istongkuan" id="istongkuan" />
 
			    <p>以下为商品简介，字段由商家添加</p>
				 <input type="hidden" name="jjliststr" class="jjliststr"  value=""/>
				<table cellspacing="0"  cellpadding="0">
					<thead>
						<tr>
							<th class="tab_name">标题名称</th>
							<th class="tab_cont">内容介绍</th>
							<th class="tab_do">操作</th>
						</tr>
					</thead>
					<tbody class="sj jj_list">
						<c:forEach items="${goodsjjlist}" var="var" >
		 	               <tr class="jj" youxuanjj_id="${var.youxuanjj_id }">
		 	               		<td class="inp"><input type="text" value="${var.title }" /></td>
		 	               		<td class="inp"><input type="text" value="${var.text }"  style="width:90%;"/></td>
 		 	               		<td><a class="btn" onclick="deletejj(this)">删除</a></td>
		 	               </tr>
						</c:forEach>
					</tbody>
					<tfoot>
			    		<tr>
			    			<td colspan=3   >
			    				<span class="icon iconfont icon-tianjia tac "  onclick="addjj()"></span>
			    			</td>
			    		</tr>
			    	</tfoot>
				</table>
			</div>
			<h4>五、选择营销方案</h4>
			<div class="content">
				<span>赠送积分率</span>
				<input type="number" value="${goodspd.goods_jfrate}" name="goods_jfrate" id="goods_jfrate" >
				<span class=" ">是否限购：</span>
				<div class="radio disib">
				  <label>
				    <input type="radio" name="xiangou"  ${goodspd.isxiangou ne '1'?'checked':'' } onclick="isxg('0')" >否
				  </label>
				</div>
				<div class="radio disib">
				  <label>
				    <input type="radio" name="xiangou"  ${goodspd.isxiangou eq '1'?'checked':'' } onclick="isxg('1')" >是
				  </label>
				</div>
				<input type="hidden" value="${goodspd.isxiangou eq '1'?'1':'0'}"  name="isxiangou" id="isxiangou" />
				<span>每个会员限购</span>
				<input type="number" placeholder="限购数量" value="${goodspd.xiangou_number}"  name="xiangou_number" id="xiangou_number" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" >	
				<span class="clfb">提货地址：</span><input type="text" placeholder="提货地址" value="${goodspd.th_address}"  name="th_address" id="th_address">
				<span >提货时间（下单成功后）</span>
				<input type="text" placeholder="单位（天）" value="${goodspd.limit_day}" name="limit_day" id="limit_day"  onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />多少天内提货
			</div>
			<h4>六、编辑商品缩略图（最多上传5张）</h4>
			<input type="hidden" name="small_images" class="small_images" value="${goodspd.small_images}"  />
			<div class="content ">
				<div class="thumb_img">
					<c:forEach items="${smalllist }" var="smallimage">
						<div style="height:186px;    width: 15%;    float: left;    margin: 0px 1%;">
							<div class="img_box" onclick="upload(this)">           
								<img src="${smallimage}" class="th_img small">
							</div>
							<div class="smallimgclear" onclick="removesmallimgdiv(this)">清空</div>
						</div>
 					</c:forEach>
				</div>
			</div>
			<h4>七、编辑商品详情图</h4>
			<input type="hidden" name="big_images" class="big_images" value="${goodspd.big_images}"  />
			<div class="content xq">
				<c:forEach items="${biglist }" var="bigimage">
 	  				<div style="width:50%;margin:0 auto;">	
 	  					<div class="bigimgclear" onclick="removebigimgdiv(this)">删除</div>
	 	  				<div class="xq_box"  onclick="upload(this)">
		                    <img class="th_img big" src="${bigimage}">
	 	                </div>
   					</div>
  				</c:forEach>
                <div class="addxq_box" onclick="addbigimages(this)">
                	<div class="dask">
                        <img src="img/imgadd.png" alt="" class="add_xq_box">
                    </div>
                </div>
			</div>
			<c:if test="${look_type eq 'add' and storeqx.add eq '1' }">
				<div class="content" style="text-align: center;" >
						  点+可添加图片，点圈+可以新增图片
				</div>
			</c:if>
			<c:if test="${look_type eq 'add' }">
				<div class="content padd">
					<span class="tijiao"  onclick="tijiao()">保存并提交审核</span>
				</div>
			</c:if>
			<c:if test="${look_type eq 'detail' }">
				<div class="content padd">
					<span class="tijiao"  onclick="tijiao()">确认修改</span>
				</div>
			</c:if>
 			<div class="content padd">
				<span class="tijiao"  onclick="goAppYuLan()">预览</span>
			</div>
	</div>
	</form>
	</c:if>
	<form action="zhihui_city_file/uploanImage.do" method="post" name="imageForm" id="imageForm"  enctype="multipart/form-data"> 
 	        <input type="file" style="width:1px;display:none;"   id="upload-file"  name="uploanImage" class="upload-file" onchange="fileType(this)"/>
 	</form>
 	 <script src="js/jquery-1.8.0.min.js"></script>
	<script src="js/jquery.form.js"></script>
	<script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框-->
	<script src="js/bootstrap.min.js"></script>
	<script src="js/ace-elements.min.js"></script>
	<script src="js/ace.min.js"></script>
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="js/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="js/attention/zDialog/zDialog.js"></script>
	<script type="text/javascript">
		if("${goodspd.bianji_type}" == "2"){
	   	 $(".xianshi_bianji").show();
	    }else{
	   	 $(".xianshi_bianji").hide();
	    }
		function isbj(value){
			   $("#bianji_type").val(value);
			   if(value == "2"){
				  $(".xianshi_bianji").show();
			   }else{
				  $(".xianshi_bianji").hide();
			   }
	     }	
	
		
	   //新增规格
		function addtr(){
			var str="<tr class='gg' youxuangg_id='new'>"+
						"<td class='tj'><img src='' onclick='uploadTwo(this)' class='zltp'/><input type='hidden' value=''/></td>"+
						"<td class='inp'><input type='text' value='' placeholder='请填写商品规格描述'/> </td>"+
						"<td class='inp'><input type='text' value='' placeholder='请填写商品售价' /> </td>"+
						"<td class='inp'><input type='text' value='' placeholder='请填写商品库存' /> </td>"+
						"<td class='inp'><input type='text' value='' placeholder='剩余商品库存'  /> </td>"+
						"<td ><a onclick='sznowpay(this)' class='col'>该条设为引导信息</a></td>"+
						"<td><a onclick='deletegg(this)' class='btn'>删除</a></td>"+
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
 			var s=((bp_salemoney/gf_salemoney)*10)+"";
 			if(s.indexOf(".") > 0){
				s= parseFloat(s).toFixed(1)+"";
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
	     
	
	
	 	function istk(value){
		   $("#istongkuan").val(value);
	   }
		
		//新增简介
		function addjj(){
			var str="<tr class='jj' youxuanjj_id='new'>"+
						"<td><input class='inp' type='text' value='' placeholder='请填写标题' /> </td>"+
						"<td><input class='inp' type='text' value=''placeholder='请填写内容' style='width:90%;' /> </td>"+
						"<td><a onclick='deletejj(this)' class='btn'>删除</a></td>"+
					"</tr>";
			$(".jj_list").append(str);
		}
		//删除简介
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
	
	 	function isxg(value){
		   $("#isxiangou").val(value);
	   	}
	
	
		function addbigimages(obj){
		   var str="<div style='width:50%;margin:0 auto;'>	<div class='bigimgclear' onclick='removebigimgdiv(this)'>删除</div><div class='xq_box'  onclick='upload(this)'><img class='th_img big' src='img/changeadd.png'></div></div>";
		   $(obj).before(str);
		}
	
//			提交
	 		function tijiao(){
	 			 //档期不能为空
	 			 if($("#youxuandq_id").val()==""){
	 				$("#youxuandq_id").tips({
	 					side:3,
	 		            msg:'档期不能为空',
	 		            bg:'#AE81FF',
	 		            time:2
	 		        });
	 				$("#youxuandq_id").focus();
	 				return false;
	 			 }
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
 	 				$("#goods_name").focus();
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
	             /* var bianji_type=$("#bianji_type").val();
	             if(bianji_type == "1"){//自己编辑
	             } */
  	 			 $("#Form").ajaxSubmit({  
					  	url : '<%=basePath%>${msg}.do?tm='+new Date().getTime(),
				        type: "POST",//提交类型  
				      	dataType:"json",
				   		success:function(data){
				   			  alert(data.message);
				   			  if(data.result == "1"){
				   				  window.location.reload(); //刷新当前页面
				   			  } 
	 					}
				});  
	 		}
			
	  		//上传按钮点击
	 		function upload(obj){
	 			window._img=$(obj).find(".th_img");
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
 	   
	   
	    //预览详情
		function goAppYuLan(){
 	    	  var  brand_name=$("#brand_name").val();
	    	  var  goods_name=$("#goods_name").val();
	    	  var  gf_salemoney=$("#gf_salemoney").val();
	    	  var  bp_salemoney=$("#bp_salemoney").val();
	    	  var  goods_zkrate=$("#goods_zkrate").val();
	    	  var  goods_jfrate=$("#goods_jfrate").val();
	    	  var  th_address=$("#th_address").val();
	    	  var  xiangou_number=$("#xiangou_number").val();
	    	  var  isxiangou=$("#isxiangou").val();
	    	  var  goods_dw=$("#goods_dw").val();
	    	  var  store_id="${pd.store_id}";
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
 			  //循环所有的小图
			  var smallliststr="";
			  $(".small").each(function(n,obj){
	 			 if($(obj).attr("src") != "img/imgadd.png"){
	 				smallliststr+=$(obj).attr("src")+",";
	 			 }
   			  });
 			  //循环所有的大图
			  var bigliststr="";
			  $(".big").each(function(n,obj){
				  	if($(obj).attr("src") != "img/changeadd.png"){
				  		bigliststr+=$(obj).attr("src")+",";
				  	}
   			  });
			 var str="brand_name="+brand_name+"&goods_name="+goods_name+
  	  		"&gf_salemoney="+gf_salemoney+"&bp_salemoney="+bp_salemoney+
  	  		"&goods_zkrate="+goods_zkrate+"&goods_jfrate="+goods_jfrate+
  	  		"&th_address="+th_address+"&xiangou_number="+xiangou_number+
  	  		"&isxiangou="+isxiangou+"&goods_name="+goods_name+
  	  		"&store_id="+store_id+"&goods_dw="+goods_dw+
  	  		"&ggliststr="+ggliststr+"&jjliststr="+jjliststr+
  	  		"&smallliststr="+smallliststr+"&bigliststr="+bigliststr;
 	    	 var url="../youxuan/goYouxuanGoodsYuLan.do?"+str;
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="App界面预览";
			 diag.URL = url;
			 diag.Width = 500;
			 diag.Height = 850;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
 			 };
			 diag.show();
		}
	    
	    
	    //替换小图片
	    function removesmallimgdiv(obj){
 	    	$(obj).prev().find("img").attr("src","img/imgadd.png");
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
	    
	    
	  //判断是否为数字
   		function isNumber(obj){
   			if($(obj).val() == ""){
   				return;
   			}
   			if (isNaN($(obj).val())) { 
    				$(obj).val($(obj).val().substring(0, $(obj).val().length-1));
    			　　return;
   			} 
    		if($(obj).val().indexOf("-") >= 0){
   				return;
   			}
    		var xiaoshu=$(obj).val().length - $(obj).val().indexOf(".");
   			if($(obj).val().indexOf(".") >0 && xiaoshu > 3){
    			$(obj).val($(obj).val().substring(0, $(obj).val().length-1));
   			}
   		}
	    
	   
	    
 	</script>
</body>
</html>