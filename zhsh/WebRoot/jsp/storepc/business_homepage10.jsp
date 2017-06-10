 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我要推广</title>
	<base href="<%=basePath%>">
     <link rel="stylesheet" href="css/daoliu/normalize.min.css">
     <link rel="stylesheet" href="css/daoliu/hsd_ggz.css">
     <link rel="stylesheet" href="css/pcstore/predefine.css">
 	<script src="js/jquery-1.8.0.min.js"></script>
	<script src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<section>
	<div class="list_cont">
		<ul>
			<li  onclick="shaixin('1')">查看优质商家</li>
			<li onclick="shaixin('2')">发布推广需求</li>
			<li onclick="shaixin('3')">广告效果</li>
		</ul>
	</div>
	<script type="text/javascript">
	function shaixin(type){
		window.location.href="storepc_daoliu/StoreTuiGuanglistPage.do?store_id=${storepd.store_id}&type="+type;
	}
	</script>
<div class="cont0 hei">
<!-- 查看优质商家 -->
<form action="storepc_daoliu/StoreTuiGuanglistPage.do" method="post" name="jdForm" id="jdForm">
<input type="hidden" name="store_id" value="${pd.store_id }"  />
<input type="hidden" name="type" value="${pd.type }"  />
	<div class="px">     <!-- 排序 -->
			<span>展示排序 ：</span>
			<select name="paixu_type">
				<option value="0" >按发布时间倒序排序</option>
				<option value="1" ${pd.paixu_type eq '1'?'selected':'' }>按会员量倒序排序</option>
				<option value="2" ${pd.paixu_type eq '2'?'selected':'' }>按上月交易笔数倒序排序</option>
				<option value="3" ${pd.paixu_type eq '3'?'selected':'' }>按推广价格倒序排序</option>
			</select>
			<span class="btnbox_cx anniu-m" onclick="jdFormSubmit()">查询</span>
	</div>
	<table class="table zs">
		<thead>
			<th>商家名称</th>
			<th>会员量</th>
			<th>上月交易笔数</th>
			<th>推广价格</th>
			<th>申请推广</th>
			<th>推广时段</th>
		</thead>
		<tbody>
			<c:forEach items="${varList}" var="var">
				<tr>
					<td>${var.store_name}</td>
					<td>${var.countmembernumber}</td>
					<td>${var.countordernumber}</td>
					<td><span>${var.click_fee}</span>元/次</td>
					<td>
						<c:if test="${var.store_id ne pd.store_id}">
							<span class="btnbox_jd anniu-l" onclick="shenqing('${var.daoliu_id}')" style="    padding: 3.8% 17.4%;">申请</span>
						</c:if>
						<c:if test="${var.store_id eq pd.store_id}">
							<span class="btnbox_jd_hui" >无法操作</span>
						</c:if>
					</td>
					<td><input placeholder="开始时间" type="text" class="startdate${var.daoliu_id}" name="startdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">至<input class="enddate${var.daoliu_id}"  placeholder="结束时间" type="text"  name="enddate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
				</tr>
			 </c:forEach>
		</tbody>
	</table>
	<div class="fenye clf">
		<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
	</div>
	<script type="text/javascript">
	function shenqing(daoliu_id){
		var startdate=$(".startdate"+daoliu_id).val();
		var enddate=$(".enddate"+daoliu_id).val();
		if(startdate == "" || enddate == "" ){
			alert("时间不能为空");
			return;
		}
		$.ajax({
			url:"storepc_daoliu/saveHzStore.do",
			type:"post",
			dataType:"json",
			data:{
					"store_id":"${pd.store_id}",
					"daoliu_id":daoliu_id,
					"startdate":startdate,
					"enddate":enddate
			},
			success:function(data){
				if(data.result == "0"){
					alert(data.message);
 				}else{
 					alert(data.message);
 					window.location.reload(); //刷新当前页面
 				}
 			}
		});
	}
	</script>
	<h6 style="padding-top:15px;line-height:2;">推广说明：</h6>
	<p><span>1、</span>当您点击接单时，即意味着您已同意广告主的报价；</p>
	<p><span>2、</span>广告主将您的链接置入后，当同时在广告主的商家首页底端、商家详情页面底端，以及会员买单完成后的订单页面底端显示您的链接，点击后直接跳转到您的商家首页；</p>
	<p><span>3、</span>当会员点击该链接时，即视为推广成功，系统将自动从您的帐户中扣除相应的推广费用；</p>
	<p><span>4、</span>为了保障您和广告主的利益，在申请时须确保您的积分余额不能低于500元，否则无法申请成功；同时在推广过程中，如果您的积分余额低于100元，将自动从对方推广中下架；</p>
	<p><span>5、</span>同一用户在一天内多次点击只记录一次推广成功（即只扣费一次）。</p>

	<h6 style="padding-top:15px;line-height:2;">温馨提示及建议：</h6>
	<p><span>1、</span>在推广前，建议将本店的促销方案进行周密的设置；</p>
	<p><span>2、</span>在推广前，建议将本店的商家详情页进行美工编辑，以获得更好的展示交易，同时对商家缩略图和界面进行优化；</p>
	<p><span>3、</span>为保障推广过程中效果最大化，建议先进行积分充值，积分余额太少时广告主有可能不接受您的推广申请。</p>
</form>
</div>

<script type="text/javascript">
function jdFormSubmit(){
	$("#jdForm").submit(); 
}
</script>

<div class="cont1 hei">
<!-- 推广效果 -->
<form action="storepc_daoliu/StoreTuiGuanglistPage.do" method="post" name="bjForm" id="bjForm">
<input type="hidden" name="store_id" value="${pd.store_id }"  />
<input type="hidden" name="type" value="${pd.type }"  />
	<p class="title"><span>说明：</span>发布推广需求时，请确保您的积分余额不低于500元。</p>
	<div class="tgj">
		<h5><span class="tgj1">本店推广定价：</span><span class="tgj_ff">每点击一次链接付费</span><input type="text" class="tgj_inp" id="fb_click_fee">元</h5>
		<h5><span class="tgj1">时间段：&nbsp;从</span><input placeholder="开始时间" type="text" id="fb_startdate" name="startdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">至<input id="fb_enddate"  placeholder="结束时间" type="text"  name="enddate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">止。</h5>
		
		<div style="padding-left: 11%;">
			<div class="btnbox anniu-m" onclick="fabu()">
				确认发布
			</div>
		</div>
	</div>
	<h6>推广规则：</h6>
	<p class="gz"><span>1、</span>当您同意推广客户链接时，在商家首页底端、商家详情页面底端，以及本店会员买单完成后的订单页面底端将显示客户的链接，点击后直接跳转到对方商家首页；</p>
	<p class="gz"><span>2、</span>当会员点击该链接时，即视为推广成功，系统将自动从对方帐户中扣除相应的推广费用；</p>
	<p class="gz"><span>3、</span>同一用户在一天内多次点击只记录一次推广成功。</p>
	<h6>温馨提示及建议：</h6>
	<p class="gz"><span>1、</span>为了获得更高的暴光率和推广成功率，需提高本店会员数和交易量；</p>
	<p class="gz"><span>2、</span>建议本店多发布促销信息，同时向到店的顾客推荐注册九鱼网会员。</p>
	</form>
</div>

<script type="text/javascript">
//新增
function fabu(){
		var click_fee=$("#fb_click_fee").val();
		var startdate=$("#fb_startdate").val();
		var enddate=$("#fb_enddate").val();
		if(click_fee == ""){
			alert("点击费用不能为空");
			return;
		}
		if(startdate == "" || enddate == ""){
			alert("时间不能为空");
			return;
		}
 		$.ajax({
			url:"storepc_daoliu/saveDaoliu.do",
			type:"post",
			dataType:"json",
			data:{
					"store_id":"${pd.store_id}",
					"role_type":"2",
					"click_fee":click_fee ,
					"startdate":startdate ,
					"enddate":enddate 
			},
			success:function(data){
				if(data.result == "1"){
					alert(data.message);
					window.location.reload(); //刷新当前页面
				}else{
					alert(data.message);
				}
			}
		});
	}
</script>




<div class="cont2  hei heiss">
<!-- 广告收入 -->
<form action="storepc_daoliu/StoreTuiGuanglistPage.do" method="post" name="srForm" id="srForm">
<input type="hidden" name="store_id" value="${pd.store_id }"  />
<input type="hidden" name="type" value="${pd.type }"  />
	<div class="sjd">
		<span>时间段：</span><input value="${pd.startdate }" placeholder="开始时间" type="text" name="startdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"><span>———</span><input placeholder="结束时间" value="${pd.enddate }"  type="text"  name="enddate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
		<select name="paixu_type" >
			<option value="1" ${pd.paixu_type eq '2'?'':'selected'}>按点击时间倒序排序</option>
 			<option value="2" ${pd.paixu_type eq '2'?'selected':''}>按金额倒序排序</option>
 		</select>
		<span class="btnbox_cx anniu-m" onclick="srFormSumbit()">查询</span><span> 时间段共产生 <span class="col">${pd.click_number }</span>次链接</span>
	</div>
 	<table style="margin-top:10px;">
		<thead>
			<tr>
				<th>序号</th>
				<th>广告主ID</th>
				<th>广告主名称</th>
				<th>点击时间</th>
				<th>会员ID</th>
				<th>广告支出</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${varList}" var="var" varStatus="vs">
				<tr>
					<td>${vs.index+1 }</td>
					<td>${var.zhu_store_id }</td>
					<td>${var.zhu_store_name }</td>
					<td>${var.createdate }</td>
					<td>${var.member_id }</td>
					<td><fmt:formatNumber value="${var.all_fee }" var="result" pattern="#.00" groupingUsed="false"/></td>
 				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="5">本页合计</td>
				<td><fmt:formatNumber value="${sumnowpage.sumall_fee}" var="result" pattern="#.00" groupingUsed="false"/></td>
  			</tr>
			<tr>
				<td colspan="5">总合计</td>
				<td><fmt:formatNumber value="${sumallpage.sumall_fee}" var="result" pattern="#.00" groupingUsed="false"/></td>
  			</tr>
		</tfoot>
	</table>
	<div class="fenye clf">
		<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
	</div>
	</form>
</div>

<script type="text/javascript">
function srFormSumbit(){
	$("#srForm").submit(); 
}
</script>
</section>
<script src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
//刚进来就运行的代码
var type="${pd.type}";
if(type == ""){
	type="1";
} 
var n=parseInt(type)-1;
 $(".list_cont").children("ul").children("li").eq(n).addClass("act");
 for (var i = 0; i < 5; i++) {
		if(i == n){
			$($(".hei")[i]).css("display","block");
		}else{
			$($(".hei")[i]).addClass("needremove")
		}
}
$(".needremove").remove();
//================
</script>
	
</body>

 
</html>