<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>首页</title>
        <meta charset="utf-8">
         <base href="<%=basePath%>">
        <link rel="stylesheet" href="css/storepc/business_marketing3.css">
        <script src="js/jquery-1.8.0.min.js"></script>
      <!--   <script src="../../js/storepc/business_marketing3.js"></script> -->
      <style type="text/css">
      .d3_sp1 {
    		 width: 126px;
		}
      </style>
    </head>
    <body>
    <c:if test="${storeqx.look eq '1'}">
       <div class="parent">
           <div class="d1">
                <p class="d1_p1">积分方式 
                	<span class="d1_sp1">商家根据自身经营状况五选一</span>
                 </p>               
           </div>
           <div class="d2">
             <span class="d2_sp1 " ><a href="storepc_marketing/goIntegral.do?store_id=${storepd.store_id}&type=1&jichushezhi=${pd.jichushezhi}">整店送积分设置</a></span>
             <span class="d2_sp1 d2_sp1_active" id="open"><a  href="storepc_scoreway/list.do?store_id=${storepd.store_id}&jichushezhi=${pd.jichushezhi}">品类送积分设置</a></span>
             <span class="d2_sp1 " ><a href="storepc_scoreway/goSingleproductIntegral.do?store_id=${storepd.store_id}&jichushezhi=${pd.jichushezhi}">单品送积分设置</a></span>
             <span class="d2_sp1 " ><a href="storepc_scoreway/gotransaction.do?store_id=${storepd.store_id}&jichushezhi=${pd.jichushezhi}">交易笔数送积分设置</a></span>
             <span class="d2_sp1 " ><a href="storepc_scoreway/goAmountLadder.do?store_id=${storepd.store_id}&jichushezhi=${pd.jichushezhi}">金额阶梯送积分设置</a></span>
           </div>
           <p class="parent_p1">按照<span class="orange">类别金额X整店积分率</span>的方式进行积分返还</p>
           <p class="parent_p1">例如：某烟酒专卖店将类别设置为烟、酒、茶三个类别，每个类别设置不同的积分率</p>
           <p class="parent_p1">分别为2%，5%，15%，不同品类的积分率设置方式可有效帮助商家进行精细化营销<span style="color:red;  font-size: 18px;">修改积分后，需点击修改方可生效</span></p></p>
         	<c:forEach items="${varList}" var="var" varStatus="vs">
         	    <div class="d3 cate">
		             <span class="d3_sp1"  id = "name">${var.name}积分率</span>
 		             <select class="d3_set1 jifen ${var.goods_category_id}"  >
		               <option value="0">请选择</option>
		               <c:forEach var="num" begin="1" end="100">
		               	 	<option value="${num}" ${num eq var.back_rate?'selected':'' }>${num}%</option>
		               </c:forEach>
		             </select>
 		      		 <c:if test="${storeqx.add eq '1'}">
		              	<span class="yestwo" onclick="edit('${var.goods_category_id}')">修改</span>
		             </c:if>
            	</div>
            </c:forEach> 
            <div class="d3" style="margin-top:40px;">
            	 <label>
	                <input type="checkbox" class="d3_ipt1" ${pd.change_type eq '2'?'checked':'' } onclick="showtext()"/>
	                <span class="d3_sp2">使用</span>
	              </label>
	              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 	             <c:if test="${storeqx.add eq '1'}">
			          <span class="yes" onclick="sure()">确定 </span>
			     </c:if>
            </div>
            <div class="d3">
              <p class="d3_p1"><span  class="red">重要声明：</span><span>一、积分设置是商家在会员消费完成后，赠送给会员的消费返利，目的是促进会员再次消费，从而形成有效的集客引流效果，积分1分=1元钱，会员凭积分可以九鱼网商家抵等值现金使用；（例：商家设定为整店赠送积分率为10%，会员张三消费了100元，该笔消费商家应赠送张三100×10%=10积分）</span></p>
             <p class="d3_p1"><span  class="widhth"></span><span>二、九鱼网为商家提供五种积分赠送方式，商家可根据行业、规模、经营状况选择其中一种；根据商家选择积分赠送方式的不同，系统自动调整相对应的营销功能；</span></p>
             <p class="d3_p1"><span  class="widhth"></span><span>三、积分赠送额将根据交易状况从商家资金帐户中自动扣除，为保证商家的正常使用，建议商家在初次使用时根据门店经营流水预存一定金额的积分额，同时请留意帐户积分余额，余额不足时请及时充值；</span></p>
             <p class="d3_p1"><span  class="widhth"></span><span>四、会员在商家消费时使用积分支付后，与积分相对应的现金可随时从后台进行提现操作，提现在3-10个工作日到达商家指定帐户，遇节假日顺延；</span></p>
             <p class="d3_p1"><span  class="widhth"></span><span>五、为了给商家提供更好的售后服务和增值服务，每笔交易赠送积分额的25%将另行扣除，用于会员拓展、推广和九鱼网服务体系建设；（例：如商家在一笔消费中向会员张三赠送了10积分，则另需赠送10×25%=2.5积分）</span></p>
             <p class="d3_p1"><span  class="widhth"></span><span>六、以上设置的比例或积分值一经设定，商家可随时上调设定的数值，如需下调需经审核后方可生效。&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></p>
           </div>
       </div>
       </c:if>
    </body>
    <script type="text/javascript">
	    function showtext(){
			if($("input:checkbox").is(":checked")){
				alert("您正在切换积分设置，切换后原有设置立即失效，同时部分功能将无法使用，请慎重选择");
			}
	 	}
	    var ww_allgoods_category_id="";
    	//修改分类的积分率
		function edit(goods_category_id){
			if(ww_allgoods_category_id.indexOf(goods_category_id) == -1 ){
				ww_allgoods_category_id+=goods_category_id+"@";//积分商品分类id
 			} 
   			var back_rate = $("."+goods_category_id).val();//返积分率
 			$.ajax({
				type:"post",
				url:"storepc_CategoryManageController/editRate.do",
				async:false,
 				data:{
 						goods_category_id : goods_category_id,
 						back_rate : back_rate 
					},
				success:function(data){
					if(data.result="01"){
						alert("修改成功");
 					}
  				}
			});  
  			
		}
		
		
		//确认使用
		function sure(){
			  var max=0;
			  var min=0;
			  //var m=0;
			  //获取最大值和最小值的积分
			  $(".jifen").each(function(index,obj){
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
			  $(".jifen").each(function(index,obj){
				     var jfl=parseFloat($(obj).val());
					 if(max < jfl ){
						 max=jfl;
					 }
  		  	  });
			 // alert(min+"-"+max);
			  var integral_rate=min+"%~"+max+"%";
			  if(!$("input:checkbox").is(":checked")){
					alert("请先打勾");
					return ;
			  }
			  var grantrule="";
			  var n=0;
			  $(".cate").each(function(){
					  if($(this).children("#jifen").val() != "0"){
						  n=n+1;
						  if(n<4){
							//判断包含的逗号数
	 						  grantrule+=$(this).children("#name").html()+$(this).children(".jifen").val()+"%，";
						  }
 					  }
    		  });
  			  //grantrule=grantrule.substring(0,grantrule.length-1);
 			  grantrule="积分率："+min+"-"+max+"%";
  			  var content = $("#open").text();
    		  $.ajax({
				type:"post",
				url:"storepc_scoreway/save.do",
 				data:{
						content : content,
						grantrule : grantrule,
						integral_rate : integral_rate,
						selected_status:"1",
 						change_type:"2",
 						allgoods_category_id : ww_allgoods_category_id,
						store_id:"${storepd.store_id}"
				},
				success:function(data){
					if(data.result="01"){
						alert("新增成功");
						window.location.reload(); //刷新当前页面
					}
 				}
			});    
		}
    </script>
</html> --%>