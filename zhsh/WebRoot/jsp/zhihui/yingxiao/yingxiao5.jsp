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
        <link rel="stylesheet" href="css/zhihui/yingxiao5.css">
        <script src="My97DatePicker/WdatePicker.js"></script>
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/yingxiao5.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
       <form action="zhihuired_platform/save.do" method="post" name="Form" id="Form">
        <input type="hidden" name="red_platform_id" id="red_platform_id" value="${pd.red_platform_id}"/>
	       <div class="dangan2_d1">
	          <span class="yingxiao5_sp1">发放红包-红包编号：${pd.red_platform_id}</span>  
	          <div>
	            <span class="yingxiao5_sp2">
	              <label>
	                <input type="radio" name="form" class="yingxiao5_ipt1" value="2" id= "member" onclick="radio()" />
	                <span class="yingxiao5_sp2_sp1  huiyuan">会员</span>
	              </label>
	             </span>
	            <span class="yingxiao5_sp2">
	              <label>
	                <input type="radio"  name="form" class="yingxiao5_ipt1" value="1" id ="store" onclick="radio()"/>
	                <span class="yingxiao5_sp2_sp1  shangjia">商家</span>
	              </label>
	            </span>
	          </div>
	       </div>
	       <div class="dangan2_d1">
	          <span  class="zhifu1_sp1">红包价值</span>
	          <input type="text" name="money" class="zhifu1_st1" /> 元
	       </div>
	       <div class="dangan2_d1">
	          <span  class="zhifu1_sp1">红包个数</span>
	          <input type="text" name="redpackage_number" class="zhifu1_st1" />个
	       </div>
	       <div class="dangan2_d1">
	         <span  class="zhifu1_sp1">有效时间</span>
	         <input class="zhifu1_st1" type="text" name="starttime" id="starttime" placeholder="开始时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${pd.starttime}"/>
	          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;起
	           <input class="zhifu1_st1" type="text" name="endtime" id="endtime" placeholder="结束时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${pd.endtime}"/>
	          	止
	       </div>
	       <div class="dangan2_d1">
	          <span  class="zhifu1_sp1">使用条件</span>
	          <select class="zhifu1_st1" name="rp_use_condition_id">
	            <option value="">无使用条件</option>
	            <c:forEach items="${conditionList}" var="var">
	            	<option value="${var.rp_use_condition_id}">${var.content}</option>
	            </c:forEach>
	           </select>
	       </div>
	       <div id = "memberDiv">
	       <div class="dangan2_d1" >
	         <span  class="zhifu1_sp1">发放对象</span>
	          <span class="yingxiao5_sp2_2">
 	              <label>
	                <input type="checkbox" name="duoxuan" class="yingxiao5_ipt2"  onclick="ischecked(this)"/>
	                <input type="text" name="newmember_status" value="0" style="display:none;width:1px;height:1px;"/>
	                <span class="yingxiao5_sp2_sp2">新注册用户</span>
	              </label>
 	          </span>
	          <span class="yingxiao5_sp2_2">
	              <label>
	                <input type="checkbox" name="duoxuan" class="yingxiao5_ipt2" onclick="ischecked(this)"/>
	                <input type="text" name="downapp_status" value="0" style="display:none;width:1px;height:1px;"/>
	                <span class="yingxiao5_sp2_sp2">下载APP用户</span>
	              </label>
	          </span>
	          <span class="yingxiao5_sp2_2">
 	              <label>
	                <input type="checkbox" name="duoxuan" class="yingxiao5_ipt2" onclick="ischecked(this)" />
	                <input type="text" name="firstcz_status" value="0" style="display:none;width:1px;height:1px;"/>
	                <span class="yingxiao5_sp2_sp2">完成首次充值用户</span>
	              </label>
 	          </span>
	       </div>
	       <div class="dangan2_d1 mgtop">
	           <span class="yingxiao5_sp2_2 left">
	              <label>
	                 <input type="checkbox" name="duoxuan" class="yingxiao5_ipt2" onclick="ischecked(this)" />
	                 <input type="text" name="firstjy_status" value="0" style="display:none;width:1px;height:1px;"/>
	                 <span class="yingxiao5_sp2_sp2">首单交易成功后的用户</span>
 	              </label>
	          </span>
	          <span class="rightnone">
	               <span class="yingxiao5_sp2_3 ">
	                    <label>
	                      <input type="checkbox" name="duoxuan" class="yingxiao5_ipt2" onclick="ischecked(this)"/>
	                      <input type="text" name="mlz_status" value="0" style="display:none;width:1px;height:1px;"/>
	                      <span class="yingxiao5_sp2_sp2">
	                      	魅力值首次达到 <input type="text" class="red" value="" style="width:30px" name="mlz_number">点的用户</span>
	                    </label>
	                </span>
	                <span class="yingxiao5_sp2_3 ">
	                    <label>
	                      <input type="checkbox" name="duoxuan" class="yingxiao5_ipt2" onclick="ischecked(this)"/>
 	                      <input type="text" name="tj_status" value="0" style="display:none;width:1px;height:1px;"/>
	                      <span class="yingxiao5_sp2_sp2">
	                      	推荐 <input type="text" class="red" value="10" style="width:30px" name="tj_numberfriend">
	                      		好友注册成功后的用户，每人限领 <input type="text" class="red" value="5" style="width:30px" name="tj_getrednumber">次</span>
	                    </label>
	                </span>
	          </span>
	       </div>
	       </div>
	      <!-- ****************************** -->
	      <div id="storeDiv" style="display:none">
	      <div class="dangan2_d1">
	         <span  class="zhifu1_sp1">发放对象</span>
	          <span class="yingxiao5_sp2_2">
 	              <label>
	                <input type="checkbox" name="duoxuan" class="yingxiao5_ipt2"  onclick="ischecked(this)"/>
	                <input type="text" name="newmember_status" value="0" style="display:none;width:1px;height:1px;"/>
	                <span class="yingxiao5_sp2_sp2">新注册商家</span>
	              </label>
 	          </span>
	          <span class="yingxiao5_sp2_2">
	              <label>
	                <input type="checkbox" name="duoxuan" class="yingxiao5_ipt2" onclick="ischecked(this)"/>
	                <input type="text" name="downapp_status" value="0" style="display:none;width:1px;height:1px;"/>
	                <span class="yingxiao5_sp2_sp2">下载APP商家</span>
	              </label>
	          </span>
	          <span class="yingxiao5_sp2_2">
 	              <label>
	                <input type="checkbox" name="duoxuan" class="yingxiao5_ipt2" onclick="ischecked(this)" />
	                <input type="text" name="firstcz_status" value="0" style="display:none;width:1px;height:1px;"/>
	                <span class="yingxiao5_sp2_sp2">完成首次充值商家</span>
	              </label>
 	          </span>
	       </div>
	       <div class="dangan2_d1 mgtop">
	           <span class="yingxiao5_sp2_2 left">
	              <label>
	                 <input type="checkbox" name="duoxuan" class="yingxiao5_ipt2" onclick="ischecked(this)" />
	                 <input type="text" name="firstjy_status" value="0" style="display:none;width:1px;height:1px;"/>
	                 <span class="yingxiao5_sp2_sp2">首单交易成功后的商家</span>
 	              </label>
	          </span>
	              <span class="yingxiao5_sp2_3 ">
	              <label>
	                <input type="checkbox" name="duoxuan" class="yingxiao5_ipt2" /><span class="yingxiao5_sp2_sp2" onclick="ischecked(this)">
	                 <input type="text" name="praise_status" value="1" style="display:none;width:1px;height:1px;"/>
	                	好评首次达到 <input type="text" class="red" value="10" style="width:30px" name = "full_praise">星的商家</span>
	              </label>
	          	</span>
	         </div>
	         </div>
 	       <div class="dangan2_d1 mgtop">
	         <span  class="zhifu1_sp1">发放范围</span>
	         <select class="zhifu1_st1 st2"  name="province_id" id="province_id" onchange="searchfind();">
	            <option value="">请选择省</option>
			    <c:forEach items="${provincelist}" var="var" varStatus="vs">
					<option value="${var.pcd_id}">${var.name}</option>
			    </c:forEach>
	         </select>
	         <select class="zhifu1_st1 st2" id="city_id" name="city_id" onchange="searcharea();">
	            <option value="">请选择市</option>
	         </select>
	         <select class="zhifu1_st1 st2" id="area_id" name="area_id">
	            <option value="">请选择区</option>
	         </select>
	        
	         <input type="text" name="province_name" id="province_name" value="" style="display:none;width:1px;height:1px;"/>
	          <input type="text" name="city_name" id="city_name" value="" style="display:none;width:1px;height:1px;"/>
	          <input type="text" name="area_name" id="area_name" value="" style="display:none;width:1px;height:1px;"/>
	          <input type="text" name="target_type" id="target_type" value="" style="display:none;width:1px;height:1px;"/>
	       </div>
	       <c:if test="${qx.add eq '1'}">
	          <span class="zhifu1_btn1" onclick="search()" >提交审核</span>
	          </c:if>
 	   </form>
	   </c:if>
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
				  },
				  error:function(a){
				  	alert("异常");
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
				  },
				  error:function(a){
				  alert("异常");
				  }
			});
		}
		
		//---------------------------提交----------
		function search(){
		
			var list= $('input:radio[name="form"]:checked').val();
            if(list==null){
                alert("请选中一个!");
                return false;
            }
           	 
				if($("#city_id option:selected").val() != ""){
					var city_name=$("#city_id option:selected").text();
					$("#city_name").show();
					$("#city_name").val(city_name);
				}
				if($("#province_id option:selected").val() != ""){
					var province_name=$("#province_id option:selected").text();
					$("#province_name").show();
					$("#province_name").val(province_name);
				}
				if($("#area_id option:selected").val() != ""){
					var area_name=$("#area_id option:selected").text();
					$("#area_name").show();
					$("#area_name").val(area_name);
				}
				//发放对象：1-商家，2-会员
				var target_type = $('input:radio[name="form"]:checked').val();
 				$("#target_type").show();
				$("#target_type").val(target_type);
		    	$("#Form").submit();
		}
		
		//复选框操作
		function ischecked(obj){
			var value=$(obj).next().val();
			$(obj).next().show();
			if(value == "1"){
				$(obj).next().val("0");
			}else{
				$(obj).next().val("1");
			}
			$(obj).next().hide();
		}
		
		
		//判断是选中商家还是会员
		function radio(){
			switch($("input[name=form]:checked").attr("id")){
			  case "member":
			   $("#storeDiv").hide();
			   $("#memberDiv").show();
			   break;
			  case "store":
			   $("#memberDiv").hide();
			   $("#storeDiv").show();
			   break;
			  	default:
			  		$("#memberDiv").show();
			  		$("#storeDiv").hide();
			   break;
 			}
		}
       </script>
    </body>
</html>