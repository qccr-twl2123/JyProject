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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/memberpc/business_Buyers1.css">
        <link rel="stylesheet" href="css/memberpc/swiper.min.css">
<style type="text/css">
	body{ line-height:1.2; font: 12px Arial, "微软雅黑", sans-serif;}
body,ul,li,p,ol,dl,dt,dd,form,input,h1,h2,h3,h4,h5,h6{ margin:0; padding:0;}
li{ list-style:none}
a{ text-decoration:none;}
a,input,button{-webkit-tap-highlight-color:rgba(255,0,0,0); outline:none}
button{ cursor:pointer}
.rel{ position:relative}
.abs{ position:absolute}
.fr{ float:right}
.fl{ float:left}
.clearfix:before,.clearfix:after{ 
    content:""; 
    display:table; 
} 
.clearfix:after{clear:both;} 
.clearfix{ 
    *zoom:1;/*IE/7/6*/
}
.red{
	color: #ff0600
}
.center{
	width: 1200px;
	margin:auto;
	background: #ebebeb;
	border:1px solid #dbdbdb;
	height: 75%;
	padding-top: 53px;
}
.my-top-tx{
	width: 52px;
	height: 52px;
	border-radius: 52px;
	overflow: hidden;
}
.my-top-tx img{
	width: 100%;
}
.ddxq-text{
	font-size: 20px;
	line-height: 52px;
	margin-left: 8px;
}
.sj-dj{
	margin-left: 468px;
	margin-top: 14px;
	margin-bottom: 14px;
}
.pc-border{
	background: #ffedd3;
	border:1px solid #ffb34a;
	line-height: 46px;
	padding:0 15px;
	margin:0 36px;
	font-size: 16px;
	margin-bottom: 8px;
}
.gay{
	color: #8f8f8f;
}
.pc-border-gay{
	background: #ebebeb;
	border:1px solid #bfbfbf;
	margin:0 36px 22px;
	
}
.pc-ul{
	padding:0 15px;
	line-height: 48px;
}
.pc-ul li{
	float: left;
	width: 30%;
	
}
.pc-ul li span{
	display: inline-block;
	margin-left: 8px;
}
.wallet-content-title{
	height: 48px;
	line-height: 48px;
	background: #dcdcdc;
	padding:0 15px;
}
.ddxq-red{
	text-align: center;
}
.pc-ddxq-list{
	padding:0 15px;
	line-height: 25px
}
</style>
</head>
<body>
		<div class="Buyers_body">
            <!-- 顶部导航 -->
            <div class="Buyers_hnav">
                 <span class="Buyers_hnav_sp1" >  
                	<a href="storepc/goLogin.do" > 商家登录 </a>
                </span> 
                <span class="Buyers_hnav_sp1">
                	<a href="storepc/goRegister.do" > 商家加盟 </a>
                </span>
                <span class="Buyers_hnav_sp2"> 常见问题 </span> 
                  <c:if test="${empty memberpd}">
                	<span class="Buyers_hnav_sp2">
                	 	<a href="<%=basePath%>memberpc/goMemberRegister.do">注册</a> 
                	 </span>
                	 <span class="Buyers_hnav_sp2">
                	 	<a href="<%=basePath%>memberpc/goMemberLogin.do" >会员登录</a>
                	 </span>
                	<!--  <span class="Buyers_hnav_sp2">
	                	 <a href="#">购物车 
	                	 <span class="orange">0</span>件 </a>
	                </span> -->
	                <span class="Buyers_hnav_sp2"> 
		                <a href="#"> 我的订单 </a>
	                 </span>
	                <span class="Buyers_hnav_sp2">
                	 	<a href="#" >我的九鱼 </a>
                	</span><!-- 个人中心 -->
                </c:if>
                <c:if test="${!empty memberpd}">
                  	<span class="Buyers_hnav_sp2">
                  		<a href="<%=basePath%>memberpc/loginOut.do" style="color:red;">退出</a>
                  	</span>
                	<%-- <span class="Buyers_hnav_sp2">
	                	<a href="<%=basePath%>membershopCarPc/shopCar.do?member_id=${memberpd.member_id}">购物车
	                	<span class="orange">${empty shopnumber ?'0': shopnumber}</span>
	                	件 </a>
	                </span> --%> 
	                <span class="Buyers_hnav_sp2">
		                <a href="<%=basePath%>memberorderPc/orderInfoList.do?member_id=${memberpd.member_id}">
	 		                	我的订单
	 	                </a>
	                 </span>
	                <span class="Buyers_hnav_sp2">
                		<a href="<%=basePath%>memberpc/memberInfoList.do?member_id=${memberpd.member_id}" >我的九鱼 </a>
                	</span><!-- 个人中心 -->
                </c:if>
                
             </div>
            <!-- 导航 -->
            <div class="Buyers_nav">
                <span class="Buyers_nav_sp2">
                    	<a style="color:#fff" href="<%=basePath%>memberpc/goMemberOne.do?member_id=${memberpd.member_id}&flag=0">首页</a>
                </span> 
            </div>
<div class="center">
<div class="clearfix sj-dj">
	<span class="my-top-tx fl">
		<img src="${pd.pictrue_url }">
	</span>
	<div class="fl ddxq-text">${pd.store_name }
	</div>
	
</div>
<div class="pc-border clearfix">
<span class="fl">订单状态：</span>
<p class="red fl">${pd.sale_money }</p>
<span class="red fr">交易成功</span>
</div>

<div class="pc-border clearfix">
	<span>提货券号码</span>
	<span class="red">${pd.tihuo_id }</span>
	<span class="gay">(请凭提货券号码到商家门店提货)</span>
</div>
<div class="pc-border-gay">
<div class="wallet-content clearfix">
	<p class="wallet-content-title">付款方式</p>
	<ul class="pc-ul claerfix">
		<c:forEach items="${fukaungList }" var="var">
			<li>
				<p>${var.fsname }<span>${var.fsnumber }</span></p>
			</li>
		</c:forEach>
		<!-- <li>
			<p>支付宝（188****1487）<span>500.00</span></p>	
		</li>
		<li>
			<p>余额支付<span>500.00</span></p>
		</li>
		<li>
			<p>积分支付<span>500.00</span></p>
		</li> -->
	</ul>
</div>

<div class="my-thj-list clearfix">
	<p class="wallet-content-title">优惠明细</p>
	<p class="pc-ul">本次消费共优惠了<span class="red">${pd.discount_money }</span>元,另赠送积分<span class="red">${pd.get_integral }</span>积分</p>
</div>

<div class="wallet-content clearfix">
	<p class="wallet-content-title">优惠明细</p>
	<ul  class="pc-ul claerfix">
		<li >
			<p>订单号<span>${pd.look_number }</span></p>
		</li>
		<li>
			<p>创建时间<span><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
								${fn:substring(pd.createtime,0,19)}</span></p>
		</li>
	</ul>
</div>
<p class="red ddxq-red wallet-content-title">到店后，请将本页面出示给商家收银员，作为付款凭证 返回后也可以
到<span class="blod">我的订单</span>查看</p>

<!-- <div class="pc-ddxq-list">
	<p class="fourteen-px"><b>说明</b></p>
	<p class="gay">1.该界面是在购物车付款成功之后跳转的界面，点击返回后是到商家详情页（注意：现在的版本买单后购物车未清空）；</p>
	<p class="gay">2.如果使用余额支付或积分支付的款项已经足够，则直接跳转到该页面，不需要再跳转到支付宝和微信支付的界面。</p>
</div> -->
</div>
</div>
</body>
</html>
