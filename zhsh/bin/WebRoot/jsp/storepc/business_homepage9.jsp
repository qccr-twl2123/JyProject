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
    <title>我是广告主</title>
	<base href="<%=basePath%>">
    <link rel="stylesheet" href="css/daoliu/normalize.min.css">
     <link rel="stylesheet" href="css/daoliu/hsd_ggz.css">
      <link rel="stylesheet" href="css/pcstore/predefine.css">
    <script src="My97DatePicker/WdatePicker.js"></script>
	<script src="js/jquery-1.8.0.min.js"></script>
 	<script src="js/bootstrap.min.js"></script>
	<script src="js/ace-elements.min.js"></script>
	<script src="js/ace.min.js"></script>
	<script type="text/javascript" src="js/bootbox.min.js"></script><!-- 确认窗口 -->
 </head>
<body>
<section>
	<div class="list_cont">
		<ul>
			<li onclick="shaixin('1')">编辑推广链接</li>
			<li onclick="shaixin('2')">发布广告招商</li>
			<li onclick="shaixin('3')">接单</li>
			<li onclick="shaixin('4')">广告收入</li>
		</ul>
	</div>
	<script type="text/javascript">
	function shaixin(type){
		window.location.href="storepc_daoliu/StoredaoliulistPage.do?store_id=${storepd.store_id}&type="+type;
	}
	</script>
	<!-- 编辑推广链接 -->
<div class="cont0 hei one style_scroll">
	<h4>正在合作的推广列表：</h4>
	<div class="kapian">
		<c:forEach items="${hzStoreList}" var="var">
			<div class="item">
				<input class="ci_store_id"  value="${var.ci_store_id}" type="text" placeholder="点击添加客户ID" class="khid" maxlength="8" onkeyup="if(/\D/.test(this.value)){alert('请输入数字');this.value='';}">
				<div class="yxq">
					<p>有效期：</p>
					<input class="startdate" type="text" value="${var.startdate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间">	
					<p>至：</p>
					<input class="enddate" type="text" value="${var.enddate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间">
				</div>
				<p>单价：<input style="width: 60%;" class="click_fee" value="${var.click_fee}"  type="text" class="dj" maxlength="7">元/次</p>
				<p>
					<span class="qc" onclick="qingchu('${var.daoliurecord_id}')">清除</span>
					<span class="qr" onclick="sureadd('${var.daoliurecord_id}',this)">确认</span>
				</p>
			</div>
 		</c:forEach>
 	</div>
  	<script type="text/javascript">
 	//清除
 	function qingchu(daoliurecord_id){
  			bootbox.confirm("确定要清除吗?", function(result) {
				if(result) {
					var url = "<%=basePath%>/storepc_daoliu/qingchu.do?daoliu_status=99&daoliurecord_id="+daoliurecord_id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						if(data=="success"){
							nextPage(${page.currentPage});
						}
					});
				}
			});
  	}
 	//新增
 	function sureadd(daoliurecord_id,obj){
    	var ci_store_id=$(obj).parent().parent().find(".ci_store_id").val();
    	var startdate=$(obj).parent().parent().find(".startdate").val();
    	var enddate=$(obj).parent().parent().find(".enddate").val();
    	var click_fee=$(obj).parent().parent().find(".click_fee").val();
  		$.ajax({
			url:"storepc_daoliu/saveHzStore.do",
			type:"post",
			dataType:"json",
			data:{
					"zhu_store_id":"${pd.store_id}",
					"ci_store_id":ci_store_id,
					"startdate":startdate,
					"enddate":enddate,
					"click_fee":click_fee
			},
			success:function(data){
				if(data.result == "0"){
					alert(data.message);
 				}else{
 					window.location.reload(); //刷新当前页面
 				}
 			}
		});
 	}
  	</script>
 	<form action="storepc_daoliu/StoredaoliulistPage.do" method="post" name="bjForm" id="bjForm">
		<input type="hidden" name="store_id" value="${pd.store_id }"  />
		<input type="hidden" name="type" value="${pd.type }"  />
		<div class="tg_list">
			<h4>接到的推广申请：</h4>
			<table>
				<thead>
				<tr>
					<th>商家名称</th>
					<th>ID号</th>
					<th>推广时段</th>
					<th>出价</th>
					<th>申请时账户余额</th>	
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${varList}" var="var">
						<tr>
							<td>${var.ci_store_name}</td>
							<td>${var.ci_store_id}</td>
							<td>${var.startdate} 至 ${var.enddate}</td>
							<td>${var.click_fee}</td>
							<td>${var.ci_store_weath}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="fenye clf">
				<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
			</div>
		</div>
	</form>
</div>

<div class="cont1 hei">
	<!-- 发布广告招商 -->
	<form action="storepc_daoliu/StoredaoliulistPage.do" method="post" name="addForm" id="addForm">
	<input type="hidden" name="store_id" value="${pd.store_id }"  />
	<input type="hidden" name="type" value="${pd.type }"  />
		<p class="title"><span>说明：</span>发布广告招商时，本店会员数、月交易笔数将显示给本城市所有客户。客户的推广费用将以积分形势计入本店账户，结算时将以实际收费的80%计算，其余20%为系统运营费用。</p>
		<div class="tgj">
			<span class="tgj1">本店推广定价：</span><span class="tgj_ff">每点击一次链接付费</span><input type="text" class="tgj_inp" id="fb_click_fee">元
 			<br/>
 			<br/>
 			<div style="padding-left: 11%;">
	 			<div class="btnbox anniu-l" onclick="fabu()">
						确认发布
				</div>
			</div>
 		</div>
		<h6>推广规则：</h6>
		<p class="gz"><span>1、</span>当您同意推广客户链接时，在商家首页底端、商家详情页面底端，以及本店会员买单完成后的订单页面底端将显示客户的链接，点击后直接跳转到对方商家首页；</p>
		<p class="gz"><span>2、</span>当会员点击该链接时，即视为推广成功，系统将自动从对方帐户中扣除相应的推广费用；</p>
		<p class="gz"><span>3、</span>同一用户在一天内多次点击只记录一次推广成功。</p>
		<h6>温馨提示及建议：</h6>
		<p class="gz"><span>1、</span>为了获得更高的曝光率和推广成功率，需提高本店会员数和交易量；</p>
		<p class="gz"><span>2、</span>建议本店多发布促销信息，同时向到店的顾客推荐注册九鱼网会员。</p>
		</form>
</div>
 <script type="text/javascript">
//新增
function fabu(){
		var click_fee=$("#fb_click_fee").val();
		if(click_fee == ""){
			alert("点击费用不能为空");
			return;
		}
 		$.ajax({
			url:"storepc_daoliu/saveDaoliu.do",
			type:"post",
			dataType:"json",
			data:{
					"store_id":"${pd.store_id}",
					"role_type":"1",
					"click_fee":click_fee 
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


<div class="cont2 hei">
	<!-- 接单 -->
<form action="storepc_daoliu/StoredaoliulistPage.do" method="post" name="jdForm" id="jdForm">
	<input type="hidden" name="store_id" value="${pd.store_id }"  />
	<input type="hidden" name="type" value="${pd.type }" />
		<div class="px">     <!-- 排序 -->
			<span>展示排序 ：</span>
			<select name="paixu_type" onchange="jdFormSubmit()">
				<option value="1" ${pd.jdForm eq '1'?'selected':'' }>按时间倒序排序</option>
				<option value="2" ${pd.jdForm eq '2'?'selected':'' }>按照商家名称排序</option>
				<option value="3" ${pd.jdForm eq '3'?'selected':'' }>按价格排序</option>
			</select>
 		</div>
		<table class="zs">
			<thead>
				<th>商家名称</th>
				<th>报价</th>
				<th>时间段</th>
				<th>接单</th>
			</thead>
			<tbody>
				<c:forEach items="${varList}" var="var">
					<tr>
						<td>${var.store_name}</td>
						<td>${var.click_fee}</td>
						<td>${var.startdate} 至 ${var.enddate}</td>
						<td>
							<c:if test="${var.store_id ne pd.store_id}">
								<span class="btnbox_jd anniu-l" onclick="jd('${var.daoliu_id}')">接单</span>
							</c:if>
							<c:if test="${var.store_id eq pd.store_id}">
								<span class="btnbox_jd_hui" >无法操作</span>
							</c:if>
 						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="fenye clf">
				<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		</div>
		<h6>推广说明：</h6>
		<p style="font-size:14px;line-height:1.4;"><span>1、</span>当您点击接单时，即意味着您已同意广告主的报价；</p>
		<p style="font-size:14px;line-height:1.4;"><span>2、</span>在接到报价后，请联系编辑推广链接。</p>
 		</form>
</div>
<script type="text/javascript">
function jd(daoliu_id){
	$.ajax({
		url:"storepc_daoliu/saveHzStore.do",
		type:"post",
		dataType:"json",
		data:{
				"store_id":"${pd.store_id}",
					"daoliu_id":daoliu_id
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
function jdFormSubmit(){
	$("#jdForm").submit(); 
}
</script>
<div class="cont3 hei">
<!-- 广告收入 -->
<form action="storepc_daoliu/StoredaoliulistPage.do" method="post" name="srForm" id="srForm">
<input type="hidden" name="store_id" value="${pd.store_id }"  />
<input type="hidden" name="type" value="${pd.type }" />
	<div class="sjd">
		<span>时间段：</span><input value="${pd.startdate }"  placeholder="开始时间" type="text" name="startdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"><span>———</span><input value="${pd.enddate }"  placeholder="结束时间" type="text"  name="enddate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
		<select name="paixu_type" >
			<option value="1" ${pd.paixu_type eq '2'?'':'selected'}>按点击时间倒序排序</option>
 			<option value="2" ${pd.paixu_type eq '2'?'selected':''}>按金额倒序排序</option>
 		</select>
		<span class="btnbox_cx anniu-l" onclick="srFormSumbit()">查询</span>
	</div>
	<div class="tg_tit">
		<p>截止当前时间，已累计为 <span>${pd.tuiguang_number}</span>家商家提供推广，累计获得 <span>${pd.tuiguang_money}</span>元广告收入。</p>
	</div>
	<table>
		<thead>
			<tr>
				<th>序号</th>
				<th>客户ID</th>
				<th>客户名称</th>
				<th>点击时间</th>
				<th>会员ID</th>
				<th>广告收入</th>
				<th>本店广告收入</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${varList}" var="var" varStatus="vs">
				<tr>
					<td>${vs.index+1 }</td>
					<td>${var.ci_store_id }</td>
					<td>${var.ci_store_name }</td>
					<td>${var.createdate }</td>
					<td>${var.member_id }</td>
					<td><fmt:formatNumber value="${var.all_fee }" var="result" pattern="#.00" groupingUsed="false"/></td>
					<td><fmt:formatNumber value="${var.store_fee }" var="result" pattern="#.00" groupingUsed="false"/></td>
 				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="5">本页合计</td>
				<td><fmt:formatNumber value="${sumnowpage.sumall_fee}" var="result" pattern="#.00" groupingUsed="false"/></td>
				<td><fmt:formatNumber value="${sumnowpage.sumstore_fee}" var="result" pattern="#.00" groupingUsed="false"/></td>
 			</tr>
			<tr>
				<td colspan="5">总合计</td>
				<td><fmt:formatNumber value="${sumallpage.sumall_fee}" var="result" pattern="#.00" groupingUsed="false"/></td>
				<td><fmt:formatNumber value="${sumallpage.sumstore_fee}" var="result" pattern="#.00" groupingUsed="false"/></td>
 			</tr>
		</tfoot>
	</table>
	<div class="fenye">
		<!-- 分页导航位置 -->
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