<%--  <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
        <link rel="stylesheet" href="css/storepc/business_marketing5.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/storepc/business_marketing5.js"></script>
    </head>
    <body>
    <c:if test="${storeqx.look eq '1'}">
       <div class="parent">
           <div class="d1">
                <p class="d1_p1">积分方式 <span class="d1_sp1">商家根据自身经营状况五选一</span>
            </div>
           <div class="d2">
             <span class="d2_sp1 "><a href="storepc_marketing/goIntegral.do?store_id=${storepd.store_id}&type=1">整店送积分设置</a></span>
             <span class="d2_sp1 "><a href="storepc_marketing/goIntegral.do?store_id=${storepd.store_id}">品类送积分设置</a></span>
             <span class="d2_sp1 "><a href="storepc_marketing/goIntegral.do?store_id=${storepd.store_id}">单品送积分设置</a></span>
             <span class="d2_sp1 d2_sp1_active" id = "open"><a   href="storepc_marketing/goIntegral.do?store_id=${storepd.store_id}">交易笔数送积分设置</a></span>
             <span class="d2_sp1 "><a href="storepc_marketing/goIntegral.do?store_id=${storepd.store_id}">金额阶梯送积分设置</a></span>
           </div>
           <p class="parent_p1">无论价格多少，按照<span class="orange">每笔交易固定值</span>的积分赠送</p>
           <p class="parent_p1">例如：某快餐店平均客单价均约为20元，设定为每笔消费固定返3元对客流量大，经营品种相对固定，单品之间价格差距不到的商家更为合适。</p>
           <div class="d3">
             <span class="d3_sp1">每单返积分</span>
             <input type="text" class="d3_set1" id="jfw"  value="${pd.fourbackintegral_integral }" style="text-align: center;    font-size: 19px;" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
            </div>
            <div class="d3">
              <label>
                <input type="checkbox" class="d3_ipt1" ${pd.change_type eq '4'?'checked':'' } onclick="showtext()"/>
                <span class="d3_sp2">使用</span>
              </label>
           </div>
          <div class="d3">
          	<c:if test="${storeqx.add eq '1'}">
             <span class="yes" onclick="save()"> 确定 </span>
            </c:if>
          </div>
           <div >
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
    
    	function save(){
    		if(!$("input:checkbox").is(":checked")){
				alert("请先打勾");
				return ;
			}
    		var content= $("#open").text();  
			var fourbackintegralIntegral = $('#jfw').val();
 			//var grantrule = "每单返积分"+fourbackintegralIntegral;
 			var grantrule = fourbackintegralIntegral+"分/笔";
 			if(fourbackintegralIntegral == ""){
 				alert("积分不能为空");
 				return;
 			}
  			$.ajax({
				type:"post",
				url:"storepc_scoreway/save.do",
 				data:{
 					content:content,
 					selected_status:"1",
					change_type:"4",
 					fourbackintegral_integral:fourbackintegralIntegral,
 					integral_rate :fourbackintegralIntegral,
 					grantrule:grantrule,
 					store_id:"${storepd.store_id}"
 				},
				success:function(data){
					window.location.reload(); //刷新当前页面
				}
			});
      	}
    
    </script>
</html> --%>