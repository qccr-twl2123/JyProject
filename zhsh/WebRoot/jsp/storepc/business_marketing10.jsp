<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
        <link rel="stylesheet" href="css/storepc/business_marketing10.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/storepc/business_marketing10.js"></script>
    </head>
    <body>
     <c:if test="${storeqx.look eq '1'}">
       <div class="parent">
           <div class="d1">
                <p class="d1_p1">折扣设置 </p>               
           </div>
           <div class="d2">
             <span class="d2_sp1 "><a href="storepc_marketing/goDiscount.do?store_id=${storepd.store_id}&type=1">整店折扣设置</a></span>
             <span class="d2_sp1 "><a href="storepc_discountway/list.do?store_id=${storepd.store_id}">类别折扣设置</a></span>
             <span class="d2_sp1 d2_sp1_active" id = "open"><a   href="storepc_discountway/goNumberDiscount.do?store_id=${storepd.store_id}">件数折扣设置</a></span>
             <span class="d2_sp1 "><a href="storepc_discountway/goAmountLadderDiscount.do?store_id=${storepd.store_id}">金额阶梯折扣设置</a></span>
           </div>
           <p class="parent_p1">
             <span>件数折扣设置请在</span><span class="blue">商品管理 - 单品管理</span>中进行设置，本处设置是否启用该功能
           </p>
            <div class="d3">
              <label>
                <input type="checkbox" class="d3_ipt1" ${pd.discount_type eq '3'?'checked':'' } />
                <span class="d3_sp2">使用</span>
              </label>
           </div>
              <c:if test="${storeqx.add eq '1'}">
	          	<span class="yes" onclick="save()">确定 </span>
	          </c:if>
	          <div class="d3">
              <p class="d3_p1"><span  class="red">重要声明：</span><span>一、在同一时间段内，以上四种折扣方式只能选择一种折扣进行设置；</span></p>
             <p class="d3_p1"><span  class="widhth">重要声明：</span><span>二、当你需要更改为其他折扣方式时，原折扣方式自动失效，根据系统规则，<br/>有可能导致部分功能无法同时使用，请更换折扣方式时注意记录原有选项和设置；</span></p>
             <p class="d3_p1"><span  class="widhth">重要声明：</span><span>三、如你在使用过程中有任何疑问，请在线咨询服务商；</span></p>
           </div>
       </div>
       </c:if>
    </body>
    
    <script type="text/javascript">
    	function save(){
    		var content= $("#open").text();
   			if(!$("input:checkbox").is(":checked")){
				alert("请先打勾");
				return ;
			}
      		 $.ajax({
				type:"post",
				url:"storepc_discountway/save.do",
				data:"content="+content+"&grantrule="+content+"&discount_type=3&change_type=3&store_id="+"${storepd.store_id}"+"&selected_status=1&starttime=2016-01-01&endtime=2050-01-01",
				success:function(data){
					if(data.result == "01"){
						alert("新增成功");
						window.location.reload();
					}else{
						alert("系统错误，请联系客服");
					}
				}
			});   
    		window.location.reload();
    	}
    
    </script>
</html> --%>