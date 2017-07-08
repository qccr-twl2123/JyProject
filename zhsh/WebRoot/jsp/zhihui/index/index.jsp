<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>九鱼销链</title>
        <meta charset="utf-8">
        <!-- 导入的头文件的js和css -->
		<base href="<%=basePath%>"><!-- 表示一个路径，引入的话可以直接用url:类地址+方法地址访问-->
		<link rel="shortcut icon" href="favicon.ico" >
        <link rel="Bookmark" href="favicon.ico">
   		<link rel="icon" type="image/gif" href="animated_favicon1.gif" >
		<!-- 以下两个是分页所需的两个分页样式css -->
		<link rel="stylesheet" href="css/ace.min.css" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
		<!--  -->
        <link rel="stylesheet" href="css/zhihui/index.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/index.js"></script>
        <style type="text/css">
        .logo{
 			    height: 100px;
 			    margin-left: 72px;
        }
        </style>
    </head>
    <body>
       <div class="zhihui_header">
           <div class="zhuhui_header_middle">
           		   <img src="img/storelogo.png"  class="logo">•九鱼销链
           </div>
           <div class="zhihui_header_right"> 
               		欢迎您！${sessionUser.login_name} 
               		<a  onclick="editpassword()"><span class="hands" style="color:blue;">&nbsp;&nbsp;修改密码</span></a>  
               		<a href="logout.do"><span class="zhihui_header_right_btn hands"></span></a>
           </div>
       </div>
       <div class="zhihui_body">
           <div class="zhihui_body_left">
               <!-- 分界 -->
               <div class="zhihui_body_left_d2 left_active hands  demo danganclick" data-id="#dangan">
                    <a class="middle_a" href="zhihui_subsidiary/list.do"  target="ifra"><!-- 地址（类地址+方法地址访问）：zhihui_subsidiary/list.do -->
                   <div class="zhihui_body_left_d2_img">
                       <img src="img/dangan.png" width="100%" height="100%" >
                   </div>
                   <div class="zhihui_body_left_d2_size">
                    	   档案管理
                   </div>
                   </a>
               </div>
               <div class="zhihui_body_left_d2 hands zhuclick zhifuclick" data-id="#zhifu">
                 <a class="middle_a" href="zhihuiWaterRecordController/liushuilist.do"  target="ifra">
                   <div class="zhihui_body_left_d2_img">
                       <img src="img/zhifu.png" width="100%" height="100%" >
                   </div>
                   <div class="zhihui_body_left_d2_size">
                      	 支付管理
                   </div>
                  </a>
               </div>
              
                <div class="zhihui_body_left_d2 hands zhuclick  yingxiaoclick" data-id="#yingxiao">
                  <a class="middle_a" href="zhihuicity_marketing/list.do"  target="ifra">
                   <div class="zhihui_body_left_d2_img">
                       <img src="img/yingxiao.png" width="100%" height="100%" >
                   </div>
                   <div class="zhihui_body_left_d2_size">
                      	 营销管理
                   </div>
                   </a>
               </div>
                <div class="zhihui_body_left_d2 hands zhuclick  xitongclick" data-id="#xitong">
                  <a class="middle_a" href="zhihui_menu_role/list.do"  target="ifra">
                   <div class="zhihui_body_left_d2_img">
                       <img src="img/xitong.png" width="100%" height="100%" >
                   </div>
                   <div class="zhihui_body_left_d2_size">
                      	 系统管理
                   </div>
                  </a>
               </div>
              <div class="zhihui_body_left_d2 hands zhuclick  baobiaoclick" data-id="#baobiao">
                <a class="middle_a" href="zhihuiReportForm/integralIncome.do"  target="ifra">
                   <div class="zhihui_body_left_d2_img">
                       <img src="img/baobiao.png" width="100%" height="100%" >
                   </div>
                   <div class="zhihui_body_left_d2_size">
                     	  报表
                   </div>
                   </a>
               </div>
                <div class="zhihui_body_left_d2 hands zhuclick shixiangclick" data-id="#shixiang">
                  <a class="middle_a" href="zhaoshang/datalistPageCompay.do?menu_id=49"  target="ifra">
                   <div class="zhihui_body_left_d2_img">
                       <img src="img/zhuyi.png" width="100%" height="100%" >
                   </div>
                   <div class="zhihui_body_left_d2_size">
                     	  事项与沟通
                   </div>
                  </a>
               </div>
           </div>
           <div class="zhihui_body_right">
               <!-- <div class="zhihui_body_right_header">
                   <span class="dingwei"><img src="img/dingwei.png" style="width: 82%; " ></span>
                   <div class="dingwei_right">
                       <span >您所在的位置：</span>
                       >
                       <span class="dingweitext">档案管理</span>
                       >
                       <span class="dingweitext2">子公司档案</span>
                   </div>
                </div> -->
               <!-- 档案管理内容导航 -->
               <div class="zhihui_body_right_middle " id="dangan">
                   <a class="middle_a" href="zhihui_subsidiary/list.do?menu_id=1">
                      <div class="zhihui_body_right_middle_d1 left_active2 disnone demo  hands" >
                       	<div class="zhihui_body_right_middle_d1_content">子公司档案</div>
                      </div>
                   </a>
                   <a class="middle_a" href="zhihui_city_file/list.do?menu_id=2"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">城市档案</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihui_citymanager/list.do?menu_id=54"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">城市经理档案</div>
                       </div>
                   </a>
                    <a class="middle_a" href="zhihui_sp_file/list.do?menu_id=3"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">服务商档案</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihui_clerk_file/list.do?menu_id=4"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">业务员档案</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihui_operator_file/list.do?menu_id=5"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">操作员档案</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihuiz_store_file/list.do?menu_id=6"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">商家正式档案</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihuiz_store_file/notlist.do?menu_id=40"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">商家待审核档案</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihui_member/list.do?menu_id=7"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">会员档案</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihuiz_store_file/listStoreRelations.do?menu_id=8"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">商家关系调整</div>
                       </div>
                   </a>
                    <a class="middle_a" href="zhihui_friend/contactsList.do?menu_id=9"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">人脉联查</div>
                       </div>
                   </a>
                </div>
               <!-- 支付管理内容导航 -->
               <div class="zhihui_body_right_middle " id="zhifu">
                   <a class="middle_a" href="zhihuiWaterRecordController/liushuilist.do?menu_id=10"  target="ifra">
                      <div class="zhihui_body_right_middle_d1  disnone left_active2 hands">
                       <div class="zhihui_body_right_middle_d1_content">流水记录</div>
                     </div>
                   </a>
                   <a class="middle_a" href="zhihuiWaterRecordController/orderSolelist.do?menu_id=42"  target="ifra">
                      <div class="zhihui_body_right_middle_d1 hands demo2">
                       <div class="zhihui_body_right_middle_d1_content">商家在线销售明细</div>
                     </div>
                   </a>
                   <a class="middle_a" href="zhihuiWaterRecordController/listTxPage.do?menu_id=11&pay_status=0&chuli_ok=0"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">提现风控审核</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihuiWaterRecordController/listTxPage.do?menu_id=36&remittance_type=3&pay_status=3&chuli_ok=0"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">支付宝待支付</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihuiWaterRecordController/listTxPage.do?menu_id=37&remittance_type=1&pay_status=3&chuli_ok=0"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">银行卡待支付</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihuiWaterRecordController/listTxPage.do?menu_id=38&pay_status=2&chuli_ok=0"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">异常提现待支付</div>
                       </div>
                   </a>
                    <a class="middle_a" href="zhihuiWaterRecordController/listTxPage.do?menu_id=12&chuli_ok=3"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2" id="tixian">
                          <div class="zhihui_body_right_middle_d1_content">提现成功记录</div>
                       </div>
                   </a>
                    <a class="middle_a" href="zhihui_service_performance/list.do?menu_id=14&first=first"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">服务商业绩</div>
                       </div>
                   </a>
                    <a class="middle_a" href="zhihui_service_performance/listServiceClerk.do?menu_id=15&first=first"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">业务员业绩</div>
                       </div>
                   </a> 
                   <a class="middle_a" href="zhihuicount_allmoney/countList.do?menu_id=13"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">统计</div>
                       </div>
                   </a> 
               </div>
               <!-- 营销管理内容导航 -->
               <div class="zhihui_body_right_middle " id="yingxiao">
                  <a class="middle_a" href="zhihuicity_marketing/list.do"  target="ifra">
                   <div class="zhihui_body_right_middle_d1 disnone  left_active2 hands">
                       <div class="zhihui_body_right_middle_d1_content">城市营销参数</div>
                     </div>
                   </a>
                   <!-- <a class="middle_a" href="zhihuired_platform/list.do?menu_id=17"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">平台红包</div>
                       </div>
                   </a> -->
                    <a class="middle_a" href="zhihui_sort_score/list.do?menu_id=18"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">增加综合分值</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihui_pc_advert/list.do?menu_id=19"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">PC广告位管理</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihui_app_advert/list.do?menu_id=20"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">APP广告位管理</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihui_sort_chain/list.do?menu_id=21"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">外链推广</div>
                       </div>
                   </a>
                  <!--  <a class="middle_a" href="zhihui_store/list.do?menu_id=22"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">星级设置</div>
                       </div>
                   </a> -->
                  <!--  <a class="middle_a" href="oneYuan/datalistPageGoods.do"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">一元购商品管理</div>
                       </div>
                   </a> -->
                   <a class="middle_a" href="youxuan/youxuancsgl.do?menu_id=46"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">优选参数管理</div>
                       </div>
                   </a>
                   <a class="middle_a" href="youxuan/datalistPageGoods.do?goods_check=0&menu_id=43"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">优选商品上线审核</div>
                       </div>
                   </a>
                   <a class="middle_a" href="youxuan/datalistPageGoods.do?goods_check=1&bianji_type=2&menu_id=44"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">优选商品审核通过待支付</div>
                       </div>
                   </a>
                   <a class="middle_a" href="youxuan/datalistPageGoods.do?goods_check=2&menu_id=45"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">优选商品审核通过</div>
                       </div>
                   </a>
                   <a class="middle_a" href="fapiao/fapiaolist.do?chuli_status=0&menu_id=51"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">待开发票</div>
                       </div>
                   </a>
                   <a class="middle_a" href="fapiao/fapiaolist.do?chuli_status=1&menu_id=52"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">待寄发票</div>
                       </div>
                   </a>
                   <a class="middle_a" href="fapiao/fapiaolist.do?chuli_status=2&menu_id=53"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">已寄发票</div>
                       </div>
                   </a>
                  </div>
               <!-- 系统管理内容导航 -->
               <div class="zhihui_body_right_middle " id="xitong">
                   <a class="middle_a" href="zhihui_menu_role/list.do?menu_id=23" target="ifra">
                   <div class="zhihui_body_right_middle_d1 disnone  left_active2 hands">
                       <div class="zhihui_body_right_middle_d1_content">角色管理</div>
                     </div>
                   </a>
                   <a class="middle_a" href="zhihui_menu_qx/list.do?menu_id=24"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">权限管理</div>
                       </div>
                   </a>
                    <!-- <a class="middle_a" href="zhihui_menu_text/list.do?menu_id=25"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">文本管理</div>
                       </div>
                   </a> -->
                   <!-- <a class="middle_a" href="zhihui_menu_marketing/list.do?menu_id=26"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">营销规则显示设置</div>
                       </div>
                   </a> -->
                   <a class="middle_a" href="zhihui_menu_marketing/golist.do?menu_id=39"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">重置密码</div>
                       </div>
                   </a>
               </div>
               <!-- 报表管理内容导航 -->
               <div class="zhihui_body_right_middle " id="baobiao">
                   <a class="middle_a" href="zhihuiReportForm/integralIncome.do?menu_id=27"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 disnone left_active2 hands ">
                          <div class="zhihui_body_right_middle_d1_content">平台积分收入查询</div>
                       </div>
                   </a>
                    <a class="middle_a" href="zhihuiReportForm/storeBond.do?menu_id=28"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">商家服务费查询</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihuiReportForm/serviceProviderBond.do?menu_id=29"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">服务商保证金收入查询</div>
                       </div>
                   </a>
                    <a class="middle_a" href="youxuan/datalistPageGoodsSaleInFor.do?menu_id=47"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">优选商品销售报表</div>
                       </div>
                   </a>
                   <a class="middle_a" href="youxuan/datalistPageGoodsFee.do?menu_id=48"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">优选商品编辑费用报表</div>
                       </div>
                   </a>
                   <a class="middle_a" href="storepc_daoliu/daoliuList.do?menu_id=50"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">导流报表</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihuiReportForm/renmaiByStoreList.do?menu_id=55"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">商家人脉推广收益报表</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihuiReportForm/jingYingFenXiByStore.do?menu_id=56"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">商家经营分析报表</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihuiBaoBiao/baoBiaoTypeTotol.do?menu_id=57"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">总服务报表</div>
                       </div>
                   </a>
               </div>
               <!-- 事项管理内容导航 -->
               <div class="zhihui_body_right_middle " id="shixiang">
                   <a class="middle_a" href="zhaoshang/datalistPageCompay.do?menu_id=49"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 disnone left_active2 hands ">
                          <div class="zhihui_body_right_middle_d1_content">招商事项管理</div>
                       </div>
                   </a>
                   <!-- <a class="middle_a" href="zhihuired_platform/listNRreview.do?menu_id=31"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2 ">
                          <div class="zhihui_body_right_middle_d1_content">未处理事项</div>
                       </div>
                   </a> -->
                    <a class="middle_a" href="zhihui_send_notifications/list.do?menu_id=32"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">发送通知</div>
                       </div>
                   </a>
                   <!--  <a class="middle_a" href="zhihui_menu_record/list.do"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">记录</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihui_keyword_reply/list.do"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">关键字回复设置</div>
                       </div>
                   </a>
                   <a class="middle_a" href="zhihui_menu_customer/list.do"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">客服</div>
                       </div>
                   </a> -->
                   <a class="middle_a" href="http://kefu.easemob.com/"  target="ifra">
                       <div class="zhihui_body_right_middle_d1 hands demo2">
                          <div class="zhihui_body_right_middle_d1_content">客服</div>
                       </div>
                   </a>
               </div>
			   <c:if test="${qx.look eq '1'}">
               <!-- 内容 -->
               <form action="zhihui_subsidiary/list.do" method="post" name="Form" id="Form"><!-- form表单：name必须为Form,id也一样 -->
	               <div class="zhihui_body_right_contern"  >
	                   <div class="zhihui_body_right_contern_d1">
	                       <input type="text" class="zhihui_body_right_contern_ipt1" placeholder="可输入序号公司名称等查询"  name="content" id="content" value="${pd.content}"/>
	                       <span class="zhihui_body_right_contern_cx" onclick="check()">查询</span>   
	                       <c:if test="${qx.add eq '1'}">
		                       <a href="zhihui_subsidiary/goAdd.do" class="dangan_ifra_a1" target="ifra">
		                       		<span class="zhihui_body_right_contern_tj">添加</span>
		                       </a>
	                       </c:if>
	                   </div>
	                   <div class="zhihui_body_right_contern_d2">
	                       <table  border="0" cellspacing="0" cellpadding="0" class="zhihui_body_right_contern_table">
	                           <tr>
	                                <td>序号</td>
	                                <td>子公司工商名称</td>
	                                <td>内部名称</td>
	                                <td>公司地址</td>
	                                <td>业务区域</td>
	                               <!--  <td>第一负责人名称</td>
	                                <td>联系方式</td> -->
	                                <td>操作</td>
	                           </tr>
	                           <c:forEach items="${varList}" var="var" varStatus="vs">
	                           		<tr>
	                          			<td>${var.subsidiary_id}</td>
	                          			<td>${var.subsidiary_ic_name}</td>
										<td>${var.house_name}</td>
										<td>${var.address}</td>
										<td>
											<c:if test="${var.area_id != '0'}">
												${var.province_name}-${var.city_name}-${var.area_name} ...
											</c:if>
											<c:if test="${var.area_id eq '0'}">
												${var.city_name}-${var.area_name}
											</c:if>
										</td>
										<%-- <td>${var.province_name}${var.city_name}${var.area_name}</td> --%>
										<%-- <td>${var.post}</td>
										<td>${var.phone}</td> --%>
										<td>
											<c:if test="${qx.edit eq '1'}">
											<a href="zhihui_subsidiary/goEdit.do?subsidiary_id=${var.subsidiary_id}&currentPage=${page.currentPage}" target="ifra" class="zhihui_body_right_contern_xg"><span class="table_xg">修改</span></a>
											</c:if>
											<c:if test="${qx.look eq '0'}">
											无权限
											</c:if>
										</td>
									</tr>
 	                           </c:forEach>
	                       </table>
	                   </div>
	                   <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
	                   <!-- 这一句div就是表示分页了，不过在这个页面得有form表单 -->
 	               </div>
 	               
               </form><!-- 分页在这个form表单里面 -->
               </c:if>
               <div class="zhihui_body_right_ifra">
                   <iframe name="ifra" class="dangan_ifra"></iframe>
               </div>
               <div class="zhihui_body_right_footer">
                   	版权所有：杭州玖鱼科技有限公司
               </div>
           </div>
       </div>
        <!-- 分页需要的js -->
		<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
 		<script type="text/javascript">window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/ace-elements.min.js"></script>
		<script src="js/ace.min.js"></script>
 		<!-- 引入 -->
		<script type="text/javascript" src="js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="js/bootbox.min.js"></script><!-- 确认窗口 -->
		<script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框-->
		<!--引入弹窗组件start-->
		<script type="text/javascript" src="js/attention/zDialog/zDrag.js"></script>
		<script type="text/javascript" src="js/attention/zDialog/zDialog.js"></script>
         <script type="text/javascript">
      <%--  //获取城市
 		function addsearchfind(){
 			var str=$("#province_id option:selected").val();//获取被选中的value值
 			
 			$.ajax({
 				  url: '<%=path%>/zhihui_subsidiary/citylist.do',
 				  data:"province_id="+str,
 				  type:"post",
 				  dataType:"json",
 				  success:function(data){
 					  	var list=data.citylist;
 					  	$("#city_id option").remove();
 					  	$("#area_id option").remove();
 					  	$("#city_name").val("");
 					  	$("#area_name").val("");
 					  	$("#city_id").append("<option value=''>请选择市</option>");
 					  	if(list.length>0){
   						  	for(var i=0;i<list.length;i++){
 						  		$("#city_id").append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
 						  	}
 					  	}
 				  },
 				  error:function(a){
 				  	alert("异常");
 				  }
 			});
 		}
 			
 		//获取区域
 		function addsearcharea(){
 			var str=$("#city_id option:selected").val();//获取被选中的value值
 			$.ajax({
 				  url: '<%=path%>/zhihui_subsidiary/arealist.do',
 				  data:"city_id="+str,
 				  type:"post",
 				  dataType:"json",
  				  success:function(data){
 					  	var list=data.arealist;
 					  	$("#area_id").empty();
 					  	$("#area_id").append("<option  value=''>请选择区</option>");
 					  	if(list.length>0){
 						  	for(var i=0;i<list.length;i++){
 						  		$("#area_id").append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
 						  	}
 				  		}
 				  },
 				  error:function(a){
 				  alert("异常");
 				  }
 			});
 		} --%>
 		
 		//检索
 		function check(){
 			/* if($("#city_id option:selected").val() != ""){
 				var city_name=$("#city_id option:selected").text();
  				$("#city_name").val(city_name);
 			}
 			if($("#province_id option:selected").val() != ""){
 				var province_name=$("#province_id option:selected").text();
  				$("#province_name").val(province_name);
 			}
 			if($("#area_id option:selected").val() != ""){
 				var area_name=$("#area_id option:selected").text();
  				$("#area_name").val(area_name);
 			} */
  			$("#Form").submit();
 		}
		
		 
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					var url = "<%=basePath%>/subsidiary/delete.do?subsidiary_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
					//	if(data=="success"){
							nextPage(${page.currentPage});
					//	}
					});
				}
			});
		}
		
		//修改密码
		function editpassword( ){
 			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=path%>/zhihui_goEditpassword.do' ;
			 diag.Width = 450;
			 diag.Height = 200;
			 diag.CancelEvent = function(){ //关闭事件
 				diag.close();
			 };
			 diag.show();
		}
		
		 
		 
		</script>
     </body>
</html>