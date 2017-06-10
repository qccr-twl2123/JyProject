<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>优选销售查询</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/sj_hy.css">
	<link rel="stylesheet" href="css/pcstore/predefine.css">
	<script src="js/jquery-1.8.0.min.js"></script>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
    <form action="<%=basePath%>youxuan/store_pageggGoods.do" name="Form" id="Form" method="post">
	<input type="hidden" name="store_id" id="store_id" value="${storepd.store_id}"  />
	<ul>
		<li class="padd_td" style="padding-top: 0;">
			<span class="cx_item">区域</span>：
				<input disabled  class="inp_l"  value="${citypd.province_name}"  />
				<input disabled  class="inp_l"  value="${citypd.city_name}"   />
				<input disabled  class="inp_l"  value="${citypd.area_name}"   />
 		</li>
		<li class="padd_td">
			<span>选择档期</span>：
			<select class="sel_sty" name="youxuandq_id" id="youxuandq_id"  >
					<option value="">请选择</option>
 				    <c:if test="${!empty pd.youxuandq_id  and pd.youxuandq_id ne ''}">
				    	<option value="${pd.youxuandq_id }" selected="selected">${pd.youxuandq_name}</option>
				    </c:if>
				    <c:if test="${!empty listAllDangqi }">
				    	<c:forEach items="${ listAllDangqi}" var="var">
				    		<option value="${var.youxuandq_id }">第${var.youxuandq_id}期${var.startdate}至${var.enddate}</option>
				    	</c:forEach>
  				    </c:if>
 			</select>
 			<input type="hidden" name="youxuandq_name" id="youxuandq_name" value="${pd.youxuandq_name}"  />
			<input type="text" name="content"  value="${pd.content}"   placeholder="请输入商品名称进行检索">
			<span class="anniu-m" onclick="checked()">查询</span>
		</li>
	</ul>
	<table cellspacing="0" cellpadding="0" style="white-space: nowrap;width:98%;border:1px solid #a4a4a4;" class="cx_tab">
		<thead>
			<td>序号</td>
			<td>商家名称</td>
			<td>商家ID</td>
			<td>所属档期</td>
			<td>商品名称</td>
			<td>商品编号</td>
			<td>规格参数</td>
			<td>销售数量</td>
			<td>销售单价</td>
			<td>销售金额</td>
			<td>会员积分</td>
			<td>推广积分</td>
		</thead>
		<tbody>
 				<c:forEach items="${goodslist}" var="var" varStatus="vs">
	              	<tr>
		                <td>${vs.index+1}</td>
   	              		<td>${var.store_name}</td>
	              		<td>${var.store_id}</td>
	              		<td>${var.youxuandq_id}</td>
	              		<td>${var.goods_name}</td>
	              		<td>${var.youxuangoods_id}</td>
	              		<td>${var.gg_miaosu}</td>
	              		<td  class="num">${var.nowsale_number}</td>
	              		<td>${var.sale_money}</td>
	              		<td class="money">${var.nowsale_money}</td>
	              		<td class="hy_jf">${var.member_jf}</td>
	              		<td class="tg_jf">${var.xt_jf}</td>
   		             </tr> 
 	              </c:forEach> 
 	              <tr >
	                <td colspan="7">本页合计</td>
	                <td>${nowpagesum.sumnowsale_number}</td>
	                <td>/</td>
	                <td>${nowpagesum.sumnowsale_money}</td>
	                <td>${nowpagesum.summember_jf}</td>
	                <td>${nowpagesum.sumxt_jf}</td>
  	              </tr>  
 	              <tr >
	                <td colspan="7">总合计</td>
	                <td>${allpagesum.sumnowsale_number}</td>
	                <td>/</td>
	                <td>${allpagesum.sumnowsale_money}</td>
	                <td>${allpagesum.summember_jf}</td>
	                <td>${allpagesum.sumxt_jf}</td>
 	              </tr>  
		</tbody>
	</table>
	<li>
		<div class="fenye clf">
			<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		</div>
	</li>
	</form>
	</c:if>
	<script src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
       
       
       
 		
        //检索
    	function checked(){
 			if($("#youxuandq_id option:selected").val() != ""){
				var youxuandq_name=$("#youxuandq_id option:selected").text();
 				$("#youxuandq_name").val(youxuandq_name);
			}
    		$("#Form").submit();//提交
    	}
         
</script>
</body>
</html>