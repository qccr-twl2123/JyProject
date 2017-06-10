<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>首页</title>
	 <base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/fonts/iconfont.css">
	<link rel="stylesheet" href="css/pcstore/hsd_sjzx.css">
 	<script src="js/jquery-1.8.0.min.js"></script>
</head>
<script>
	var list=[{
				name:"首页",
				listitem:["收银和收货","概要概括","15天营销统计图","我是广告主","我要推广","我的会员"],
				linkitem:["storepc/goShQyStore.do?store_id=${storepd.store_id}","storepc_briefsummary/showMessage.do?store_id=${storepd.store_id}","storepc_briefsummary/showMessage.do?store_id=${storepd.store_id}","storepc_daoliu/StoredaoliulistPage.do?store_id=${storepd.store_id}&type=1","storepc_daoliu/StoreTuiGuanglistPage.do?store_id=${storepd.store_id}&type=1","storepc_vip/goMyVIP.do?store_id=${storepd.store_id}"],
				num:0},
				
			  {
			  	name:"营销控台",
			  	listitem:["消费及支付方式","1.折扣设置","2.积分方式（必选）","3.红包","满赠","时段营销","买N减一","累计次数/金额营销","管理我的营销","效果分析"],
			  	linkitem:["storepc_marketing/goZhifu.do?store_id=${storepd.store_id}","storepc_marketing/goDiscount.do?store_id=${storepd.store_id}","storepc_marketing/goIntegral.do?store_id=${storepd.store_id}","storepc_redpackets/list.do?store_id=${storepd.store_id}","storepc_marketing/goGive.do?store_id=${storepd.store_id}","storepc_marketing/goGive.do?store_id=${storepd.store_id}","storepc_marketingtype/list.do?marketing_type=4&store_id=${storepd.store_id}","storepc_marketingtype/list.do?marketing_type=5&store_id=${storepd.store_id}","storepc_marketing/list.do?store_id=${storepd.store_id}","storepc_marketingeffect/list.do?store_id=${storepd.store_id}"],
			  	num:1},
			  {
			  	name:"商品管理",
			  	listitem:["类别管理","单品管理","排行榜","人气榜","今日特价","优选爆品编辑","优选上线申请","优选销售查询"],
			  	linkitem:["storepc_CategoryManageController/showShop.do?store_id=${storepd.store_id}","storepc_CategoryManageController/showShop2.do?store_id=${storepd.store_id}","storepc_CategoryManageController/showShop3.do?store_id=${storepd.store_id}","storepc_CategoryManageController/showShop4.do?store_id=${storepd.store_id}","storepc_CategoryManageController/showShop5.do?store_id=${storepd.store_id}","youxuan/store_gosaveGoods.do?store_id=${storepd.store_id}","youxuan/store_datalistPageGoods.do?store_id=${storepd.store_id}&goods_check=0","youxuan/store_pageggGoods.do?store_id=${storepd.store_id}"],
			  	num:2},
			  {
			  	name:"基础信息",
			  	listitem:["商家信息","商家图片","高级信息","修改密码","今日特价","操作员","班次","桌号"],
			  	linkitem:["storepc_StoreManageController/goInformation.do?store_id=${storepd.store_id}","storepc_StoreManageController/goImage.do?store_id=${storepd.store_id}","storepc_StoreManageController/showSenior.do?store_id=${storepd.store_id}","storepc_StoreManageController/showPassword.do?store_id=${storepd.store_id}","storepcOperator_file/findOperator.do?store_id=${storepd.store_id}","storepcOperator_file/list.do?store_id=${storepd.store_id}","storepc_tableNumber/list.do?store_id=${storepd.store_id}"],
			  	num:3},
			  {
			  	name:"账户信息",
			  	listitem:["积分充值","提现","流水明细","商品销售明细表","商品销售明细","充值提现汇总","班次汇总","账户设置","服务续费"],
			  	linkitem:["storepc_wealth/list.do?store_id=${storepd.store_id}","storepc_withdrawals/list.do?store_id=${storepd.store_id}","storepc_wealthhistory/list.do?store_id=${storepd.store_id}&chuli_type=1","storepc_wealthhistory/list.do?store_id=${storepd.store_id}&chuli_type=4&profit_type=3","storepc_wealthhistory/orderlistPage.do?store_id=${storepd.store_id}&pay_type=3","storepc_wealthhistory/list.do?store_id=${storepd.store_id}&chuli_type=3","storepc_wealthhistory/BanCiHuiZonglist.do?store_id=${storepd.store_id}","storepc_bankcard/list.do?store_id=${storepd.store_id}","storepc/goFeeNextNumber.do?store_id=${storepd.store_id}"],
			  	num:4}			
				]	
</script>
<body>
	<header style="background:#eee;font-size:12px;text-align:right;">
		<div class="cont">
			<span >${storepd.store_name} &nbsp;${storepd.oprator_name}</span>
			<a  onclick="editpassword()"  style="color:#e7031e;font-size:12px;padding-left:30px;cursor: pointer;"><span class="iconfont icon-password"></span>修改密码</a>
			<a  href="<%=basePath%>storepc/loginOut.do" style="color:#1b8ce3;font-size:12px;padding-left:30px;cursor: pointer;"><span class="iconfont icon-guanbi1"></span>安全退出</a>
		</div>
	</header>
	<header>
		<div class="cont">
			<a href="<%=basePath%>">
				<img src="img/logo.png" alt="" style="width:45px;display:inline-block;">
				<span style="font-size:18px;line-height: 3.3;display: inline-block;vertical-align: top;">九鱼销链商家中心</span>
			</a>
		</div>
	</header>
       <!--引入弹窗组件start-->
 	   <script type="text/javascript" src="js/attention/zDialog/zDialog.js"></script>
       <script type="text/javascript">
      //修改密码
		function editpassword( ){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>storepc/goEditpassword.do' ;
			 diag.Width = 450;
			 diag.Height = 200;
			 diag.CancelEvent = function(){ //关闭事件
 				diag.close();
			 };
			 diag.show();
		}
       </script>

	<nav>
		<div class="navcont clf">
		<!-- 插入点1  导航条 -->
			<li><a target='ifra' style="color:#fff;" href="<%=basePath%>storepc_CategoryManageController/goweixin2.do?store_id=${storepd.store_id}">互动沟通</a></li>
			<div>营业状态：</div>
			<select name="merchant_status" id="merchant_status"  onchange="changeMs()">
				<option value="1" ${pd.merchant_status eq '1'?'selected':''}>营业中</option>
	            <option value="4" ${pd.merchant_status eq '4'?'selected':''}>暂停营业</option>
			</select>
			<div class="app">九鱼销链手机APP下载
				<div class="download">
					<img src="img/three.png" alt="" class="sanjiao">
					<img src="img/download.png" alt="">
				</div>
			</div>
		</div>
	</nav>


	<section>
		<div class="seccont">
 				<!-- 左侧列表 -->
				<div class="itemlist">
				<!-- 插入点2  左侧导航列表 -->
				</div>
				<div class="r_cont">
					<!-- 右侧内容 -->
					 <iframe name="ifra" class="dangan_ifra"></iframe>
					<!-- 内容结束 -->
				</div>
 		</div>
	</section>
	<footer>
    <div class="footcont">
        <ul>
            <li><a href="<%=basePath%>storepc/goRegister.do" class="noborder">我要开店</a></li>
            <li><a href="<%=basePath%>jsp/storepc/gyjy.html" target="_blank">关于九鱼</a></li>
            <li><a href="">加入我们</a></li>
            <li><a href="">合作流程</a></li>
            <li><a href="">常见问题</a></li>
        </ul>
    </div>
    <div class="beian">[浙] ICP备16025718号-2 本站发布所有内容，未经许可，不得转载 </div>
</footer>
</body>
	<script src="js/jquery-1.12.4.min.js"></script>
	<script>
	$(function(){

		// 导航加载 插入点一
		for (var i = list.length - 1; i >= 0; i--) {
			 $(".navcont").prepend("<li num="+list[i].num+">"+list[i].name+"</li>");
		};
		// 点击选择
		$(".navcont li").click(function(){
			$(".itemlist li").remove()
			var num=$(this).attr("num")
			$(".navcont li").css({"background":""})
			$(this).css({"background":"radial-gradient(at top center,#3e9ecb,#085ca9 70%)"})
			// 交流按钮点击时 
			if (num==5) {
				return;
			};
			// 项目加载 插入点二
			for (var i = list[num].listitem.length - 1; i >= 0; i--) {
				$(".itemlist").prepend("<a href="+list[num].linkitem[i]+"   target='ifra'><li num="+i+">"+list[num].listitem[i]+"</li></a>");
			};
			// 点击选择
			$(".itemlist li").click(function(){
				$(".itemlist li").css({"background":"","color":""})
				$(this).css({"background":"linear-gradient(to right,#1187e2 0%,rgba(255,255,255,0.8) 50%,#1187e2 100%)","color":"#0861a7"})
			})
			// 初始状态设置
				$(".itemlist li")[0].click()  		
		})
		$(".navcont li")[0].click()
		$(".itemlist li")[0].click()
	})


	// app二维码
	$(".app").mouseover(function(){
		$(".download").css({"display":"block"})}
		)
	$(".app").mouseout(function(){
		$(".download").css({"display":"none"})}
		)
	 

	function changeMs(){
	    	 $.ajax({
             type: "post",
             url: "storepc/edit.do",
             data:{"merchant_status":$("#merchant_status option:selected").val()},
             dataType: "json",
             success: function(data){
             }
         });
    	}
	
	</script>
	
	 
</html> --%>