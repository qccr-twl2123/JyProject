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
        <link rel="stylesheet" href="css/zhihui/yingxiao2.css">
        <script src="<%=basePath%>My97DatePicker/WdatePicker.js"></script>
        <script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
        <script src="<%=basePath%>js/zhihui/yingxiao2.js"></script>
        <script src="<%=basePath%>js/jquery.tips.js"></script><!--提示框-->
        <style type="text/css">
        .bctc {
		    float: right;
		    width: 120px;
		    height: 40px;
		    background-color: #5dc9ba;
		    border-radius: 5px;
		    margin-top: 3.5%;
		    margin-right: 40px;
		    text-align: center;
		    line-height: 40px;
		    color: #fff;
		    cursor: pointer;
		}
		.bcsz {
		    float: left;
		    width: 120px;
		    height: 40px;
		    background-color: #5dc9ba;
		    border-radius: 5px;
		     margin-left: 14%;
		    text-align: center;
		    line-height: 40px;
		    color: #fff;
		    cursor: pointer;
		}
		.yingxiao2_d1_dq {
		    width: 90%;
 		    margin-left: 5%;
		    margin-top: 20px;
		    line-height: 34px;
		}
        </style>
     </head>
    <body>
	    <c:if test="${qx.look eq '1'}">
 	    	<input type="hidden" name="city_file_id" id="city_file_id" value="${pd.city_file_id}">
	    	<input type="hidden" name="currentPage" id="currentPage" value="${pd.currentPage}">
	    	<div class="yingxiao2_d1 mgtop10">
		           <div class="bctc" onclick="back_list()" >退出</div>
   	        </div>
 	        <div class="yingxiao2_d1 mgtop10">
	          <span class="yingxiao2_d1_sp1">设置一：档期设置</span>
 	        </div>
 	         
   	        <script type="text/javascript">
   	        function back_list(){
   	        	window.location.href="<%=basePath%>youxuan/youxuancsgl.do?currentPage=${pd.currentPage}";
   	        }
   	        </script>
 	        <div class="yingxiao2_d1 ">
		          <span>档期序号：</span>
		          <select name="youxuandq_id" id="youxuandq_id" class="yingxiao2_d1_st1">
		          		<option value="00">请选择档期</option>
		          </select>
 	        </div>
 	        <script type="text/javascript">
 				 for(var n=1;n<101;n++){
		            	 var s=n+"";
		            	 if( n < 10){
		            		 s="00"+s;
		            	 }else if(n >= 10 && n< 100){
		            		 s="0"+s;
		            	 }
		            	 //判断是否包含
		            	 if(!("${dangqistr}".indexOf(s) > -1)){
		            		 $("#youxuandq_id").append("<option value='"+s+"'>"+s+"</option>");
		            	 }
 	             }
  	        </script>
  	        <div class="yingxiao2_d1  ">
		          <span>档期时间：</span>
		           <input type="text" class="yingxiao2_d1_st1" id="startdate" name="startdate" value="" placeholder="开始时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		           <span class="yingxiao2_sp4">起至</span>
		           <input type="text" class="yingxiao2_d1_st1" id="enddate" name="enddate" value="" placeholder="结束时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		           	止
  	        </div>
  	        <div class="yingxiao2_d1  ">
		          <span>档期切换时间：档期当天</span>
		           <input type="text" class="yingxiao2_d1_st1" id="opentime" name="opentime" value="" placeholder="档期切换时间" onclick="WdatePicker({dateFmt:'HH:ss:mm'})"/>
		           	自动切换到下一档
  	        </div>
  	        <div class="yingxiao2_d1  ">
		          <span>本档主题：</span>
		           <input type="text" class="yingxiao2_d1_st1" id="dq_title" name="dq_title" value="" placeholder="本档主题"/>
   	        </div>
   	        <div class="yingxiao2_d1">
		          <span>备注：</span>
		           <input type="text" class="yingxiao2_d1_st1" id="text" name="text" value="" placeholder="本档主题"/>
   	        </div>
   	        <c:if test="${qx.add eq '1'}">
	   	        <div class="yingxiao2_d1  ">
			           <div class="bcsz" onclick="saveyouxuandq()">保存</div>
	   	        </div>
   	        </c:if>
	        <div class="yingxiao2_d1_dq ">
	          <span>已设置的档期</span><br/>
	          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
	              <tr >
	                <td>档期序号</td>
	                <td>时间段</td>
	                <td>开档切换时间</td>
	                <td>本档主题</td>
	                <td>备注</td>
	                <td>修改</td>
	              </tr> 
	             <c:forEach items="${listdangqi}" var="var" varStatus="vs">
		             <tr>
		                <td class="dqxh">${var.youxuandq_id }</td>
		                <td>${var.startdate}-${var.enddate}</td>
		                <td> ${var.opentime}  </td>
	 	                <td> ${var.dq_title} </td>
	 	                <td> ${var.text} </td>
	 	                <c:if test="${qx.edit eq '1'}">
		                	<td class="blue"><a onclick="editdq('${var.youxuandq_id}','${var.city_file_id}')">修改</a></td>
		                </c:if>
		              </tr>
	             </c:forEach>
	           </table>
	       </div>
	       <br/>
  	        <div class="yingxiao2_d1">
	          <span class="yingxiao2_d1_sp1">设置二：定价设置</span>
	        </div>
			<div class="yingxiao2_d1">
 				    <span>编辑费用：</span>
				    <input onkeyup="value=value.replace(/[^\.\d]/g,'')" class="yingxiao2_d1_st1" type="text" value="${pd.bj_fee}" name="bj_fee" id="bj_fee" placeholder="编辑费用"/>
				       元/支单品（以优选显示为准）
 			</div>
 		    <div class="yingxiao2_d1" style="display:none">
 				    <span>全年免费上线支数：</span>
				    <input class="yingxiao2_d1_st1" type="number" value="${pd.nf_number}" name="nf_number" id="nf_number" placeholder="免费次数"/>
 					（每个商家）
 			</div>
 			<div class="yingxiao2_d1" style="display:none">
 				    <span>超出部分收上架费：</span>
				    <input onkeyup="value=value.replace(/[^\.\d]/g,'')" class="yingxiao2_d1_st1" type="text" value="${pd.sj_fee}" name="sj_fee" id="sj_fee" placeholder="上架费用"/>
 					元/支单品
 			</div>
 			<c:if test="${qx.add eq '1'}">
 			 <div class="yingxiao2_d1">
		           <div class="bcsz"  onclick="saveyouxuanfee()">保存</div>
   	        </div>
   	        </c:if>
 		</c:if>
 		<script type="text/javascript">
 		
		function saveyouxuandq(){
			//判断是否参数完整
			var youxuandq_id=$("#youxuandq_id").val();
			var startdate=$("#startdate").val();
			var enddate=$("#enddate").val();
			var opentime=$("#opentime").val();
			var dq_title=$("#dq_title").val();
			var text=$("#text").val();
			if($("#youxuandq_id").val()=="00"){
	  			$("#youxuandq_id").tips({
	  				side:3,
	  	            msg:'档期不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			$("#youxuandq_id").focus();
	  			return false;
	  		}
			if($("#startdate").val()==""){
	  			$("#startdate").tips({
	  				side:3,
	  	            msg:'开始时间不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			$("#startdate").focus();
	  			return false;
	  		}
			if($("#enddate").val()==""){
	  			$("#enddate").tips({
	  				side:3,
	  	            msg:'结束时间不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			$("#enddate").focus();
	  			return false;
	  		}
			if($("#opentime").val()==""){
	  			$("#opentime").tips({
	  				side:3,
	  	            msg:'切换时间不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			$("#opentime").focus();
	  			return false;
	  		}
			if($("#dq_title").val()==""){
	  			$("#dq_title").tips({
	  				side:3,
	  	            msg:'标题不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			$("#dq_title").focus();
	  			return false;
	  		}
			$.ajax({
				  url: '<%=basePath%>/youxuan/saveyouxuandq.do',
				  data:{
					  	"city_file_id":"${pd.city_file_id}",
					  	"youxuandq_id":youxuandq_id,
 					  	"startdate":startdate,
					  	"enddate":enddate,
					  	"opentime":opentime,
					  	"dq_title":dq_title,
					  	"text":text
				  },
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	 if(data.result == "1"){
					  		window.location.reload(); //刷新当前页面
					  	 }
				  } 
			});
		}
		//费用
		function saveyouxuanfee(){
			//判断是否参数完整
			var bj_fee=$("#bj_fee").val();
			var sj_fee=$("#sj_fee").val();
			var nf_number=$("#nf_number").val();
			if($("#bj_fee").val()==""){
	  			$("#bj_fee").tips({
	  				side:3,
	  	            msg:'编辑费用不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			$("#bj_fee").focus();
	  			return false;
	  		}
			if($("#sj_fee").val()==""){
	  			$("#sj_fee").tips({
	  				side:3,
	  	            msg:'上架费用不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			$("#sj_fee").focus();
	  			return false;
	  		}
			if($("#nf_number").val()==""){
	  			$("#nf_number").tips({
	  				side:3,
	  	            msg:'免费次数不能为空',
	  	            bg:'#AE81FF',
	  	            time:2
	  	        });
	  			$("#nf_number").focus();
	  			return false;
	  		}
			 
			$.ajax({
				  url: '<%=basePath%>/youxuan/edityouxuanfee.do',
				  data:{
					  	"city_file_id":"${pd.city_file_id}",
					  	"bj_fee":bj_fee,
					  	"sj_fee":sj_fee,
					  	"nf_number":nf_number 
				  },
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	 if(data.result == "1"){
					  		window.location.reload(); //刷新当前页面
					  	 }
				  } 
			});
		}
		
		//修改
		function editdq(value,city_file_id){
 			$.ajax({
				  url: '<%=basePath%>/youxuan/detailyouxuandq.do',
				  data:{
 					  	"youxuandq_id":value,"city_file_id":city_file_id 
 				  },
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	 if(data.result == "1"){
					  		 var pd=data.data;
					  		 $("#youxuandq_id").append("<option value="+pd.youxuandq_id+" selected>"+pd.youxuandq_id+"</option>");
							 $("#startdate").val(pd.startdate);
							 $("#enddate").val(pd.enddate);
							 $("#opentime").val(pd.opentime);
							 $("#dq_title").val(pd.dq_title);
							 $("#text").val(pd.text);
					  	 }
				  } 
			});
			
		}
 		
		 
		</script>
    </body>
</html>