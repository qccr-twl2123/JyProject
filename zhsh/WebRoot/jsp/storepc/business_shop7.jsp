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
	 <title>优选商品管理</title>
	 <base href="<%=basePath%>">
      <link rel="stylesheet" href="<%=basePath%>css/youxuan/normalize.min.css">
      <link rel="stylesheet" href="<%=basePath%>css/youxuan/hsd_ggz.css">
 	 <style type="text/css">
	 form {
	    margin: 0px;
	 }
	 body{
	 	overflow:hidden;
	 }
	 </style>
</head>
<c:if test="${storeqx.look eq '1'}">
<body>
	<form action="<%=basePath%>youxuan/store_datalistPageGoods.do" name="Form" id="Form" method="post" style="height:100%;">
    <input type="hidden" name="goods_check" id="goods_check"  value="${pd.goods_check}"/>
    <input type="hidden" name="store_id" id="store_id"  value="${pd.store_id}"/>
	<section style="height:89%;">
	<div class="list_cont">
		<ul>
			<li class="type0" onclick="changeCheck('0')">已提交申请</li>
			<li class="type1" onclick="changeCheck('1')">待付款申请</li>
			<li class="type2" onclick="changeCheck('2')">已通过申请</li>
		</ul>
	</div>
	<div class="cont0" style="height:100%;">
		 <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
			<thead>	             
				 <tr >
	                <td>区域</td>
	                <td>上线档期</td>
 	                <td>商品名称</td>
 	                <td>商品编号</td>
 	                <c:if test="${pd.goods_check eq '0'}">
 	                	<td>审核状态</td>
		                <td>操作</td>
		                <td>申请日期</td>
  	                </c:if>
 	                <c:if test="${pd.goods_check eq '1'}">
 	                	<td>付费金额</td>
		                <td>去支付</td>
		                <td>备注</td>
  	                </c:if>
 	                <c:if test="${pd.goods_check eq '2'}">
 	                    <td>状态</td>
 	                    <td>备注</td>
 	                	<td>操作</td>
		                <td>申请日期</td>
  	                </c:if>
 	              </tr> 
			</thead>
			<tbody>  
	              <c:forEach items="${goodslist}" var="var" varStatus="vs">
	              	<tr>
		                <td>${var.city_address}</td>
	              		<td>${var.dangqi}</td>
 	              		<td>${var.goods_name}</td>
 	              		<td>${var.youxuangoods_id}</td>
	              		<c:if test="${pd.goods_check eq '0'}">
 	 	                	<td>
 	 	                	 <c:if test="${var.goods_status eq '0'}">等待审核</c:if>
	 	                	 <c:if test="${var.goods_status eq '97'}">
	 	                	 	<span style="color:red;">已驳回 备注：${var.beizhu_text}</span>
	 	                	 </c:if>
			                </td>
			                <td><a onclick="goDetail('${var.youxuangoods_id}')" style="color:blue;">编辑</a></td>
			                <td>${var.createtime}</td>
	  	                </c:if>
						<c:if test="${pd.goods_check eq '1'}">
							<c:if test="${var.goods_status eq '1'}">
								<td>${var.bianji_money}</td>
								 <td><a onclick="goyouxuanpay('${var.youxuangoods_id}','${var.bianji_money}')" style="color:blue;">去支付</a></td>
								<td>商品编辑费</td>
							</c:if>
							<c:if test="${var.goods_status eq '2'}">
								<td>${var.shangjia_money}</td>
								<td><a onclick="goyouxuanpay('${var.youxuangoods_id}','${var.shangjia_money}')" style="color:blue;">去支付</a></td>
								<td>上架费</td>
							</c:if>
 						</c:if>
	 	                <c:if test="${pd.goods_check eq '2'}">
	 	                    <td>
	 	                    	 <c:if test="${var.goods_status eq '2'}">已通过</c:if>
	 	                    	 <c:if test="${var.goods_status eq '4'}">正在销售</c:if>
	 	                    	 <c:if test="${var.goods_status eq '99'}">已结束</c:if>
	 	                    	 <c:if test="${var.goods_status eq '98'}">库存已售完</c:if>
	 	                    	 <c:if test="${var.goods_status eq '97'}">驳回</c:if>
 	 	                    </td>
	 	                    <td>${var.beizhu_text}</td>
	 	                	<td>
	 	                		<a onclick="goDetail('${var.youxuangoods_id}')" style="color:blue">编辑</a>
	 	                		|
	 	                		<a onclick="goaddNowGoods('${var.youxuangoods_id}')" style="color:blue">转为新商品</a>
	 	                	</td>
			                <td>${var.createtime}</td>
	  	                </c:if>
 		             </tr> 
 	              </c:forEach>
				</tbody>
 		</table>
 		<div class="fenye clf">
			<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		</div>
	</div>
 	</section>
 	</form>
</body>
</c:if>
<script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
<script src="<%=basePath%>js/bootstrap.min.js"></script>
<script src="<%=basePath%>js/ace-elements.min.js"></script>
<script src="<%=basePath%>js/ace.min.js"></script>
<!--引入弹窗组件start-->
<script type="text/javascript" src="<%=basePath%>js/attention/zDialog/zDrag.js"></script>
<script type="text/javascript" src="<%=basePath%>js/attention/zDialog/zDialog.js"></script>
<script type="text/javascript">

			$(".type${pd.goods_check}").addClass("act");

 		    //检索
		   	function checked(){
		    		$("#Form").submit();//提交
		   	}
 		    
		  //检索
		   	function changeCheck(value){
			        $("#goods_check").val(value);
		    		$("#Form").submit();//提交
		   	}
 		    
		    //详情
			function goDetail(youxuangoods_id){
 				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="查看详情";
				 diag.URL = "../youxuan/store_godetailGoods.do?store_id=${pd.store_id}&youxuangoods_id="+youxuangoods_id;
				 diag.Width = 1300;
				 diag.Height = 800;
				 diag.CancelEvent = function(){ //关闭事件
 					diag.close();
 					window.location.reload(); //刷新当前页面
				 };
				 diag.show();
			}
 		    
		    //添加为的商品
			function goaddNowGoods(youxuangoods_id){
 				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="添加为的商品";
				 diag.URL = "../youxuan/store_goaddNowGoods.do?store_id=${pd.store_id}&youxuangoods_id="+youxuangoods_id;
				 diag.Width = 1300;
				 diag.Height = 800;
				 diag.CancelEvent = function(){ //关闭事件
 					diag.close();
 					$(".type0").click();
				 };
				 diag.show();
			}

		    //去支付页面
			function goyouxuanpay(youxuangoods_id,money){
  				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="前往支付页面";
				 diag.URL = "../youxuan/goyouxuanpay.do?store_id=${pd.store_id}&money="+money+"&youxuangoods_id="+youxuangoods_id;
				 diag.Width = 800;
				 diag.Height =300;
				 diag.CancelEvent = function(){ //关闭事件
					diag.close();
					window.location.reload(); //刷新当前页面
				 };
				 diag.show();
			}
   </script>
</html>