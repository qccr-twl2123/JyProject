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
        <link rel="stylesheet" href="css/storepc/business_marketing11.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/storepc/business_marketing11.js"></script>
        <script src="My97DatePicker/WdatePicker.js"></script>
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
             <span class="d2_sp1 "><a href="storepc_discountway/goNumberDiscount.do?store_id=${storepd.store_id}">件数折扣设置</a></span>
             <span class="d2_sp1 d2_sp1_active" id = "open"><a   href="storepc_discountway/goAmountLadderDiscount.do?store_id=${storepd.store_id}">金额阶梯折扣设置</a></span>
           </div>
           <p class="parent_p1">在有效时间段内，根据消费金额进行阶梯折扣</p>
           <c:forEach items="${zkList}" var="zkvar" varStatus="vs">
	           <div class="d3">
	             <span class="d3_sp1">消费满</span>
	             <input type = "text" id ="manjian${vs.index+1}" value="${zkvar.money }" placeholder="元"/>
	             <span>元</span>
	             <span>&nbsp;&nbsp;&nbsp;打&nbsp;&nbsp;&nbsp;</span>
	             <input type = "text" id ="zhekou${vs.index+1}" value="${zkvar.rate }" placeholder="折"/>
	              <span>折</span>
	           </div>
            </c:forEach>
            <div class="d3">
             <span class="d3_sp1">有效时间</span>
              <input class="statrTime" placeholder="开始时间" type="text" name="statrTime" id="statr" value = "${pd.starttime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
              &nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;
              <input class="endTime" placeholder="结束时间" type="text" name="endTime" id="end" value = "${pd.endtime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            </div>
           <div class="d3">
              <label>
                <input type="checkbox" class="d3_ipt1" ${pd.discount_type eq '4'?'checked':'' }/>
                <span class="d3_sp2">使用</span>
              </label>
           </div>
           <c:if test="${storeqx.add eq '1'}">
          		<span class="yes" onclick="save()"> 确定 </span>
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
    	//新增
    	function save(){
   			if(zhekou1 == 0 && zhekou3==0 && zhekou2==0){
   				return;
   			}
      		if(!$("input:checkbox").is(":checked")){
				alert("请先打勾");
				return ;
			}
     		var content= $("#open").text();
    		var manjian1 = $("#manjian1").val().trim();
    		var zhekou1 = $("#zhekou1").val().trim();
    		var manjian2 = $("#manjian2").val().trim();
    		var zhekou2 = $("#zhekou2").val().trim();
    		var manjian3 = $("#manjian3").val().trim();
    		var zhekou3 = $("#zhekou3").val().trim();
    		var starttime = $("#statr").val().trim();
			var endtime = $("#end").val().trim(); 
   			if(starttime == '' && endtime == ''){
				alert("请选择时间！");
				return ;
			}
 			var grantrule=starttime.substring(5,starttime.length)+"日至"+endtime.substring(5,endtime.length)+"日 ";
			if(manjian1 != "" && zhekou1 != 0){
				grantrule+= "消费满"+ manjian1 +"元打"+zhekou1+"折,";
			}
			if( manjian2 != "" && zhekou2 != 0  ){
				grantrule+= "消费满"+ manjian2 +"元打"+zhekou2+"折,";
			}
			if( manjian3 != "" && zhekou3 != 0){
				grantrule+= "消费满"+ manjian3 +"元打"+zhekou3+"折,";
			}
			grantrule=grantrule.substring(0,grantrule.length-1);
  			$.ajax({
					type:"post",
					url:"storepc_discountway/save.do",
					data:{content:content,selected_status:"1",change_type:"4",starttime:starttime,endtime:endtime,discount_type:"4",grantrule:grantrule,store_id:"${storepd.store_id}"},
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
    </script>
    
    
</html> --%>