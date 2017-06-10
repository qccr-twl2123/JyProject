<%--  <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>首页</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>">
        <link rel="stylesheet" href="css/storepc/business_marketing9.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/storepc/business_marketing9.js"></script>
        <script src="My97DatePicker/WdatePicker.js"></script>
        <style type="text/css">
        .parent {
 		    overflow-y: scroll;
		}
        </style>
    </head>
    <body>
    <c:if test="${storeqx.look eq '1'}">
       <div class="parent">
           <div class="d1">
                <p class="d1_p1">折扣设置 </p>               
           </div>
           <div class="d2">
             <span class="d2_sp1 "><a href="storepc_marketing/goDiscount.do?store_id=${storepd.store_id}&type=1">整店折扣设置</a></span>
             <span class="d2_sp1 d2_sp1_active" id = "open"><a  href="storepc_discountway/list.do?store_id=${storepd.store_id}">类别折扣设置</a></span>
             <span class="d2_sp1 "><a href="storepc_discountway/goNumberDiscount.do?store_id=${storepd.store_id}">件数折扣设置</a></span>
             <span class="d2_sp1 "><a href="storepc_discountway/goAmountLadderDiscount.do?store_id=${storepd.store_id}">金额阶梯折扣设置</a></span>
           </div>
           <p class="parent_p1">在有效时间段内，指定类别将进行折扣优惠</p>
           <c:forEach items="${varList}" var="var" varStatus="vs">
         	    <div class="d3 cate">
		             <span class="d3_sp1"  id = "name">${var.name}折扣率</span>
 		             <select class="d3_set1 ${var.goods_category_id}"  id="jifen" name="back_rate" onchange="edit('${var.goods_category_id}')">
		               <option value="0">请选择</option>
		               <c:forEach var="num" begin="1" end="99">
		               	 	<option value="${num}" ${num eq var.zk_rate?'selected':'' }>${num}折</option>
		               </c:forEach>
		             </select>
             	</div>
            </c:forEach>
          <div class="d3">
             <span class="d3_sp1">有效时间</span>
             <input class="statrTime" placeholder="开始时间" type="text" name="statrTime" id="statr" value = "${pd.starttime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
              &nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;
              <input class="endTime" placeholder="结束时间" type="text" name="endTime" id="end" value = "${pd.endtime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            </div> 
           <div class="d3"> 
           		 <span class="d3_sp1"></span>
 	             <input type="checkbox" class="d3_ipt1" ${pd.discount_type eq '2'?'checked':'' }/>
	             <span class="d3_sp2">使用</span>
  	      </div>
 	      <br/>
  	      <br/>
  	      <br/>
 	      <div class="d3">
 	      	 <span class="d3_sp1" ></span>
 	     	 <c:if test="${storeqx.add eq '1'}">
		         	 <span class="yes" onclick="save()"> 确定 </span>  
		     </c:if>
 	      </div>
 	      <div class="d3">
              <p class="d3_p1"><span  class="red">重要声明：</span><span>一、在同一时间段内，以上四种折扣方式只能选择一种折扣进行设置；</span></p>
             <p class="d3_p1"><span  class="widhth">重要声明：</span><span>二、当你需要更改为其他折扣方式时，原折扣方式自动失效，根据系统规则，<br/>有可能导致部分功能无法同时使用，请更换折扣方式时注意记录原有选项和设置；</span></p>
             <p class="d3_p1"><span  class="widhth">重要声明：</span><span>三、如你在使用过程中有任何疑问，请在线咨询服务商；</span></p>
           </div>
	   </div>
    </c:if>
    <script type="text/javascript">
    	//新增
		function save(){
    		    var twoproductdiscount_rate="";
				var content= $("#open").text();
				var starttime = $("#statr").val().trim();
				var endtime = $("#end").val().trim(); 
	   			if(starttime == '' && endtime == ''){
					alert("请选择时间！");
					return ;
				}
	   			//最高或最小
	   			  var max=0;
				  var min=0;
				  //var m=0;
				  //获取最大值和最小值的积分
				  $("#jifen").each(function(index,obj){
   					     if(index == 0){
					    	 min=parseFloat($(obj).val());
					     }
					     if($(obj).val() != "0"){
					    	 var jfl=parseFloat($(obj).val());
							 if(min > jfl ){
								 min=jfl;
							 }
					     }
	      		  });
				  $("#jifen").each(function(index,obj){
					     var jfl=parseFloat($(obj).val());
						 if(max < jfl ){
							 max=jfl;
						 }
	  		  	  });
				  twoproductdiscount_rate=min+"-"+max+"折";
	   			//---------
				 var grantrule="";
				  $(".cate").each(function(){
					  if($(this).children("#jifen").val() != "0"){
						  grantrule+=$(this).children("#name").html()+$(this).children("#jifen").val()+"折，";
					  }
	 			 });
 				grantrule=starttime.substring(5,starttime.length)+"日至"+endtime.substring(5,endtime.length)+"日 "+grantrule.substring(0,grantrule.length-1);
 	   			if(!$("input:checkbox").is(":checked")){
					alert("请先打勾");
					return ;
				}
 			    $.ajax({
					type:"post",
					url:"storepc_discountway/save.do",
					data:{
						content:content,
						change_type:"2",
						selected_status : "1",
						twoproductdiscount_rate:twoproductdiscount_rate,
						starttime:starttime,
						endtime:endtime,
						discount_type:"2",
						grantrule : grantrule,
 						store_id:"${storepd.store_id}"
 					},
					success:function(data){
						if(data.result == "01"){
							alert("新增成功");
							window.location.reload();
						}else{
							alert("系统错误，请联系客服");
						}
 					}
				});   
 		}
		
		//修改分类的折扣率
		function edit(goods_category_id){
 			var zk_rate = $("."+goods_category_id).val();//返积分率
 			$.ajax({
				type:"post",
				url:"storepc_CategoryManageController/editRate.do",
 				data:{
 						goods_category_id : goods_category_id,
 						zk_rate : zk_rate 
					},
				success:function(data){
					if(data.result="01"){
						//alert("修改成功");
 					}
  				}
			});    
		}
    
    </script>
    </body>
 </html> --%>