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
        <link rel="stylesheet" href="css/storepc/business_homepage.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/storepc/business_homepage.js"></script>
        <style type="text/css">
		a:hover{
			color:red;
		}
		</style>
    </head>
    <body>
        <!-- header -->
       <div class="signin_header">
           <span class="signin_logo">
               <img src="img/storelogo.png" style="width:70px;height:70px;margin-top:10px;">
           </span>
           <span class="signin_header_sp1"> 
              	九鱼销链商家中心
           </span>
            <p class="header_p1" >${storepd.store_name} &nbsp;${storepd.oprator_name}<a onclick="editpassword()" style="color:blue;cursor: pointer;">修改密码</a> <a href="<%=path %>/storepc/loginOut.do"><span>安全退出</span></a></p>
       </div>
       <!-- nav -->
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
       <div class="nav"> 
          <dl>
            <dd class="nav_active ddclick" data-id="#homepage"><a href="<%=basePath%>storepc/goShQyStore.do?store_id=${storepd.store_id}" target="ifra">首页</a></dd>
            <dd class=" ddclick" data-id="#marketing"><a href="<%=basePath%>storepc_marketing/goZhifu.do?store_id=${storepd.store_id}" target="ifra">营销控台</a></dd>
            <dd class=" ddclick" data-id="#shop"><a href="<%=basePath%>storepc_CategoryManageController/showShop.do?store_id=${storepd.store_id}" target="ifra">商品管理</a></dd>
            <dd class=" ddclick" data-id="#base"><a href="<%=basePath%>storepc_StoreManageController/goInformation.do?store_id=${storepd.store_id}" target="ifra">基础信息</a></dd>
            <dd class=" ddclick" data-id="#account"><a href="<%=basePath%>storepc_wealth/list.do?store_id=${storepd.store_id}"  target="ifra">账户信息</a></dd>
            <dd id="weixin"><a target="ifra2" href="<%=basePath%>storepc_CategoryManageController/goweixin2.do?store_id=${storepd.store_id}">互动/沟通</a></dd>
            <dt style="margin-left:50px;">
            	 营业状态：
	             <select id="merchant_status" name="merchant_status" onchange="changeMs()">
	              	<option value="1" ${pd.merchant_status eq '1'?'selected':''}>营业中</option>
	              	<option value="2" ${pd.merchant_status eq '2'?'selected':''}>商家休息</option>
	              	<option value="3" ${pd.merchant_status eq '3'?'selected':''}>节假日休息</option>
	              	<option value="4" ${pd.merchant_status eq '4'?'selected':''}>暂停营业</option>
	            </select>
            </dt>
            <dt  style="margin-left:150px;line-height: 41px;" class="erweimaw"><a target="_blank”" href="http://www.jiuyuvip.com/FileSave/zhihuiPC/business_erweima.html" style="color:#fff; display: inline-block;">九鱼销链手机app下载</a></dt>
            </dl>
           <div class="erweimaclass" style="width: 130px;height: 130px;position: relative;top: -27%;left: 84%;display: none; ">
                     	  <img src="<%=basePath%>img/downstoreapp.png" width="100%" /> 
           </div>
       </div>
       <!-- body -->
        <div class="signin_body">
          <!-- 首页 -->
          <dl class="signin_body_left" id="homepage">
            <a class="middle_a" href="storepc/goShQyStore.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="signin_body_left_active">收银和取货</dd>
            </a>
             <a class="middle_a" href="storepc_briefsummary/showMessage.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">简要概括</dd>
            </a>
             <a class="middle_a" href="storepc_fifteenmarketchart/showChart.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">15天营销统计图</dd>
            </a>
             <a class="middle_a" href="storepc_myleague/goOwnLeader.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">我的联盟</dd>
            </a>
            <a class="middle_a" href="storepc_daoliu/StoredaoliulistPage.do?store_id=${storepd.store_id}&type=1"  target="ifra">
              <dd class="">我是广告主</dd>
            </a>
            <a class="middle_a" href="storepc_daoliu/StoreTuiGuanglistPage.do?store_id=${storepd.store_id}&type=1"  target="ifra">
              <dd class="">我要推广</dd>
            </a>
            <a class="middle_a" href="storepc_vip/goMyVIP.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">我的会员</dd>
            </a>
          </dl>
          <!-- 营销控台 -->
          <dl class="signin_body_left marketingdd" id="marketing">
            <a class="middle_b" href="storepc_marketing/goZhifu.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="signin_body_left_active2">消费及支付方式</dd>
            </a>
            <a class="middle_b" href="storepc_marketing/goDiscount.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">1.折扣设置</dd>
            </a>
             <a class="middle_b" href="storepc_marketing/goIntegral.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">2.积分方式(必选)</dd>
            </a>
              <a class="middle_b" href="storepc_redpackets/list.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">3.红包</dd>
            </a> 
             <a class="middle_b" href="storepc_marketing/goGive.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">满赠</dd>
            </a>
             <a class="middle_b" href="storepc_marketingtype/list.do?marketing_type=2&store_id=${storepd.store_id}"  target="ifra">
              <dd class="">满减</dd>
            </a>
             <a class="middle_b" href="storepc_marketingtype/list.do?marketing_type=3&store_id=${storepd.store_id}"  target="ifra">
              <dd class="">时段营销</dd>
            </a>
             <a class="middle_b" href="storepc_marketingtype/list.do?marketing_type=4&store_id=${storepd.store_id}"  target="ifra">
              <dd class="">买N减一</dd>
            </a>
            <a class="middle_b " href="storepc_marketingtype/list.do?marketing_type=5&store_id=${storepd.store_id}"  target="ifra">
              <dd class="">累计次数/金额营销</dd>
            </a>
            <a class="middle_b " href="storepc_marketing/list.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">管理我的营销</dd>
            </a>
            <a class="middle_b " href="storepc_marketingeffect/list.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">效果分析</dd>
            </a>
          </dl>
          <!-- 商品管理 -->
          <dl class="signin_body_left" id="shop">
            <a class="middle_a" href="storepc_CategoryManageController/showShop.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="signin_body_left_active">类别管理</dd>
            </a>
             <a class="middle_a" href="storepc_CategoryManageController/showShop2.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">单品管理</dd>
            </a>
             <a class="middle_a" href="storepc_CategoryManageController/showShop3.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">排行榜</dd>
            </a>
             <a class="middle_a" href="storepc_CategoryManageController/showShop4.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">人气榜</dd>
            </a>
             <a class="middle_a" href="storepc_CategoryManageController/showShop5.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">今日特价</dd>
            </a>
             <a class="middle_a" href="youxuan/store_gosaveGoods.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">优选爆品编辑</dd>
            </a>
             <a class="middle_a" href="youxuan/store_datalistPageGoods.do?store_id=${storepd.store_id}&goods_check=0"  target="ifra">
              <dd class="">优选上线申请</dd>
            </a>
             <a class="middle_a" href="youxuan/store_pageggGoods.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">优选销售查询</dd>
            </a>
          </dl>
          <!-- 基础信息 -->
          <dl class="signin_body_left"id="base">
            <a class="middle_a" href="storepc_StoreManageController/goInformation.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="signin_body_left_active">商家信息</dd>
            </a>
             <a class="middle_a" href="storepc_StoreManageController/goImage.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">商家图片</dd>
            </a>
             <a class="middle_a" href="storepc_StoreManageController/showSenior.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">高级信息</dd>
            </a>
             <a class="middle_a" href="storepc_StoreManageController/showPassword.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">修改密码</dd>
            </a>
             <a class="middle_a" href="storepcOperator_file/findOperator.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">操作员</dd>
            </a>
             <a class="middle_a" href="storepcOperator_file/list.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">班次</dd>
            </a>
            </a>
             <a class="middle_a" href="storepc_tableNumber/list.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">桌号</dd>
            </a>
          </dl>
          <!-- 账户信息 -->
          <dl class="signin_body_left" id="account">
            <a class="middle_a" href="storepc_wealth/list.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="signin_body_left_active">积分充值</dd>
            </a>
             <a class="middle_a" href="storepc_withdrawals/list.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">提现</dd>
            </a>
              <a class="middle_a" href="storepc_wealthhistory/list.do?store_id=${storepd.store_id}&chuli_type=1"  target="ifra">
              <dd class="">流水明细</dd>
            </a>
            <a class="middle_a" href="storepc_wealthhistory/list.do?store_id=${storepd.store_id}&chuli_type=4&profit_type=3"  target="ifra">
               <dd class="">商品销售明细表</dd>
            </a>
            <a class="middle_a" href="storepc_wealthhistory/orderlistPage.do?store_id=${storepd.store_id}&pay_type=3"  target="ifra">
              <dd class="">商品在线销售明细</dd>
            </a>
             <a class="middle_a" href="storepc_wealthhistory/list.do?store_id=${storepd.store_id}&chuli_type=3"  target="ifra">
              <dd class="">充值提现汇总</dd>
            </a> 
             <a class="middle_a" href="storepc_wealthhistory/BanCiHuiZonglist.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">班次汇总表</dd>
            </a>
             <a class="middle_a" href="storepc_wealthhistory/goAccount.do?store_id=${storepd.store_id}"  target="ifra">
             <a class="middle_a" href="storepc_bankcard/list.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">账户设置</dd>
            </a>
            <a class="middle_a" href="storepc/goFeeNextNumber.do?store_id=${storepd.store_id}"  target="ifra">
              <dd class="">服务续费</dd>
            </a>
          </dl>
          <span class="right_bg">
               
          </span>
          <div class="signin_body_right">
               <div class="signin_body_right_ifra">
                   <iframe name="ifra" class="dangan_ifra"></iframe>
               </div>
          </div>
       </div>

       <!-- 沟通 -->
       <div class="signin_body_weiixn" style="height:100%;width:100%;margin:0 auto;">
          <div class="signin_body_weiixn_left">
              <span class="weixin_img1">
               <img src="img/weixin_pop1.png" >
              </span>
              <span class="weixin_img2 img1">
                <a      target="ifra2" href="<%=path%>/storepc_CategoryManageController/goweixin1.do?store_id=${storepd.store_id}"><img src="img/weixin_msg2.png"></a>
              </span>
              <span class="weixin_img2 weixin_img3">
                <a      target="ifra2" href="<%=path%>/storepc_CategoryManageController/goweixin2.do?store_id=${storepd.store_id}"><img src="img/weixin_lx.png"></a>
                
              </span>
           </div>
           <div class="signin_body_weiixn_right" style="height:100%;width:100%;margin:0 auto;">
                <div class="signin_body_weiixn_rightifra" style="height:100%;width:100%;">
                   <iframe name="ifra2" class="dangan_ifra" src="<%=basePath%>storepc/goShQyStore.do?store_id=${storepd.store_id}"></iframe>
               </div>
           </div>
        </div>
       <!-- footer -->
       <div class="signin_footer">
          <div class="footer_d1">
           		<span></span>
                <a href="<%=basePath%>storepc/goRegister.do" ><span>我要开店</span></a>
				<span><a  href="<%=basePath%>jsp/storepc/gyjy.html"  target="_blank">关于九鱼</a></span>
                <span>加入我们</span>
          </div>
          <div class="footer_d2">
             [浙] ICP备16025718号-2 本站发布所有内容，未经许可，不得转载
          </div>
       </div>
    </body>
    <script type="text/javascript">
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
    	
    	//隐藏显示
  		$(".erweimaw").hover(function(){
			$(".erweimaclass").show();
     	},function(){
     		$(".erweimaclass").hide();
     	});
    </script>
</html> --%>