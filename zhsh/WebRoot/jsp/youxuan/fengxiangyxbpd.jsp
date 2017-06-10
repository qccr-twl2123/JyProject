<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>爆品详情</title>
	<base href="<%=basePath%>">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
	<link rel="stylesheet" href="css/youxuan/normalize.min.css">
	<link rel="stylesheet" href="css/youxuan/swiper.min.css">
	<link rel="stylesheet" href="css/youxuan/fonts/iconfont.css">
	<link rel="stylesheet" href="css/youxuan/spgm.css">
	<script src="js/jquery-1.8.0.min.js"></script>
</head>
<body>
<div class="select_box">
	<div class="sel_cont">
		<div class="sel_tit">
			<div class="sel_img">
				<img src="" id="gg_image">
			</div>
			<div class="sel_price">
				<span class="close iconfont icon-guanbi"></span>
				<p class="sel_jg" id="gg_money"> </p>
				<p class="sel_kc">库存<span id="gg_kucun"> </span><span class="goods_dw">件</span></p>
				<p class="sel_gg">请选择商品规格</p>
			</div>
		</div>	
		<div class="guige">
			<p class="gg_tit">
				商品规格
			</p>
			<ul class="gg_list" id="gg_list">
				
  			</ul>
		</div>	
		<div class="num">
			<span class="num_tit">购买数量</span><span class="tips num_tit">件数至少为一<span class="goods_dw">件</span></span>
			<span class="sl"><a class="down sljj">-</a><span class="number sljj" id="shopnumber">1</span><a class="up sljj">+</a></span>
		</div>	
		<div class="sel_anniu">
			<a class="sel_button sel_jrgwc" onclick="goRegister()" >加入购物车</a>
			<a class="sel_button sel_nowbay" onclick="goRegister()" >立即购买</a>
		</div>
	</div>
</div>
<!-- 顶部条 -->
	<header>
 		<div class="title">
			 <div class="djs">
		        <h6>距离本次结束还剩：</h6>
		        <h6 class="jieshu"></h6>
	    	</div>
		</div>
	</header>
<div class="all swiper-container">
  <div class="swiper-wrapper">
    <div class="swiper-slide">
	    <div class="sect">
		<!-- 轮播图   -->
		<div class="lunbo">		
			<div class="banner swiper-container">
			      <div class="swiper-wrapper" id="smalllist">
			       <!--  <div class="swiper-slide img_box"><img src="" alt=""></div>
				    <div class="swiper-slide img_box"><img src="" alt=""></div>
				    <div class="swiper-slide img_box"><img src="" alt=""></div>
				    <div class="swiper-slide img_box"><img src="" alt=""></div> -->
			      </div>
			</div>
		</div>
		<div class="sp_title_box">
			<h4 class="sp_tit" id="goods_name"> </h4>
			<p>
				<span class="xj" id="xianjia"> </span>
				<span class="yj" id="yuanjia"> </span>
 				<span class="zk" id="zhekou"> </span>
			</p>
		</div>
		<div class="chose">
			<span class="sele">请选择商品规格</span>
			<span class="chose_go">›</span>
		</div>
		<div class="sj_inf" id="text_des">
			 
		</div>
		<div class="dianpu">
			<div class="dp_img">
				<img src="" alt="" id="pictrue_url">
			</div>
			<div class="dp_tit">
				<p id="store_name"> </p>
				<p id="store_address"> </p>
			</div>
			<div class="dp_sc" onclick="goRegister()" >
				<a >收藏</a>
			</div>
		</div>
		<div class="load">
			<div class="xian">
				<div class="line"></div>
			</div>
			<div class="font">继续拖动查看图文详情</div>
		</div>
		</div>
	</div>
  </div>
</div>


<footer>
	<a href="" id="dianpu" class="foot_dp"><span class="iconfont icon-dianpu icon1"></span><p>店铺</p></a>
	<a onclick="goRegister()"  class="foot_guc"><span class="iconfont icon-gouwuche1 icon2"></span><span  class="foot_font_sp">商品将保留</span><span class="bl_djs shopcarttime"></span></a>
	<a   class="foot_gm"><span>立即购买</span></a>
</footer>
	<a href="" class="drag_load" id="tiaozhuan"></a>  <!--  跳转链接 -->
	<a  href="javascript:void(0)" onclick="location.reload()" class="reflash"></a>	 <!-- 刷新链接 -->
</body>



<script src="js/youxuan/jquery-2.1.4.min.js"></script>
<script src="js/youxuan/idangerous.swiper-2.0.min.js"></script>
<script src="js/youxuan/touch.min.js"></script>
<script>
 var needsale_number="0";
//优选详情
if(true){
	//获取数据
 	$.ajax({
	    	type:"post",
	    	url:'<%=basePath%>youxuan/findDetailByYouxuan.do', 
		 	data:{
 		 		 	"youxuangoods_id":"${pd.youxuangoods_id}" 
	 	 	},                
	    	dataType:"json",
/* 	    	async: false,
 */	    	success: function(data){
		   	 	if(data.result == "1"){
		   	 		 var goodspd=data.data;
		   	 		 window.store_id=goodspd.store_id;
		   	 		 $(".goods_dw").html(goodspd.goods_dw);
		   	 		 $("#yuanjia").html("￥"+goodspd.gf_salemoney+"/"+goodspd.goods_dw);
		   	 		 $("#xianjia").html("￥"+goodspd.bp_salemoney);
		   	 		 $("#zhekou").html(goodspd.goods_zkrate);
		   	 		 $("#goods_name").html( goodspd.brand_name +"|"+goodspd.goods_name);
 		   	 		 $("#text_des").append("<p><span>销售商家：</span><span>"+goodspd.store_name+"</span></p>");
		   	 		 $("#text_des").append("<p><span>提货地址：</span><span style='color:red;'>"+goodspd.th_address+"</span></p>");
		   	 		 $("#text_des").append("<p><span>积分率：</span><span>"+goodspd.jifen_text+"</span></p>");
 		   	 		 if(goodspd.isxiangou == "1"){
 		   	 			$("#text_des").append("<p><span>限购商品：</span><span>"+goodspd.xiangou_text+"</span></p>");
 		   	 		 }
 		   	 		 var smalllist=goodspd.smalllist;//轮播图
		   	 		 for (var i = 0; i < smalllist.length; i++) {
						var str="<div class='swiper-slide img_box'><img src='"+smalllist[i].small_url+"' ></div>";
					 	$("#smalllist").append(str);
		   	 		 }
 		   	 		 var endtimestamp=goodspd.endtimestamp;//优选结束时间
		   	 		 window.endtimestamp=parseInt(endtimestamp);
			   	 	 window.t1 = setInterval(youxuanTimer, 1000);//1秒执行一次
 		   	 		 var lasttime=goodspd.lasttime;//购物车清空倒计时时间
 		   	 	 	 window.lasttime=parseInt(lasttime);
 		   	 		 window.t2 = setInterval(ShopcartTimer, 1000);//1秒执行一次
 		   	 		 var goods_imageinfor=goodspd.goods_imageinfor;//前往详情页地址
		   	 		 $("#dianpu").attr("onclick","goRegister()");
		   	 		 $("#tiaozhuan").attr("href","html_member/goYouXuanDescInfor.do?show_type=1&type=2&youxuangoods_id="+goodspd.youxuangoods_id);
 		   	 		 $("#pictrue_url").attr("src",goodspd.pictrue_url);
		   	 		 $("#store_name").html(goodspd.store_name);
		   	 		 $("#store_address").html(goodspd.address);
  		   	 	 	 var iscollect=goodspd.iscollect;//是否已经收藏：0-未收藏，1-已收藏
   		   	 	 	 if(iscollect == "1"){
		 		   		$(".dp_sc a").css({"background":"#ccc"})
		 		   		$(".dp_sc a")[0].innerText="取消收藏"
		 		   	 }else if(iscollect == "0"){
		 		   		$(".dp_sc a").css({"background":"#f60008"})
		 		   	 	$(".dp_sc a")[0].innerText="收藏"
		 		   	 }
		   	 		 //规格
		   	 		 var ggpd=goodspd.ggpd;
		   	 		 var goodsgglist=ggpd.goodsgglist;
		   	 		 for (var g = 0; g < goodsgglist.length; g++) {
						$("#gg_list").append("<li gg_imageurl='"+goodsgglist[g].gg_imageurl+"' needsale_number='"+goodsgglist[g].needsale_number+"' sale_money='"+goodsgglist[g].sale_money+"' youxuangg_id='"+goodsgglist[g].youxuangg_id+"' class='"+goodsgglist[g].youxuangg_id+"' onclick='shopthis(this)'>"+goodsgglist[g].gg_miaosu+"</li>");
					 }
		   	 		 var ggsale_qujian=ggpd.ggsale_qujian;
		   	 		 var ggcount_number=ggpd.ggcount_number;
		   	 		 var look_imageurl=ggpd.look_imageurl;
		   	 		 $("#gg_image").attr("src",look_imageurl);
		   	 		 $("#gg_kucun").html(ggcount_number);
		   	 		 needsale_number=ggcount_number;
 		   	 		 $("#gg_money").html(ggsale_qujian);
   		   	 		//轮播构建
		   	 		var banner = new Swiper('.banner',{
		   	 		     pagination: '.swiper-pagination',
		   	 		     paginationClickable: true,
		   	 		     autoHeight: true, //enable auto height
		   	 		});
   		   	 	}else{
		   	 		alert(data.message);
		   	 		return;
		   	 	}
	     }
	});
}
 


//优选倒计时
function youxuanTimer(){ 
	 var leftTime = endtimestamp - (new Date()).getTime(); //计算剩余的毫秒数 
	 var days = parseInt(leftTime / 1000 / 60 / 60 / 24 , 10); //计算剩余的天数 
	 var hours = parseInt(leftTime / 1000 / 60 / 60 % 24 , 10); //计算剩余的小时 
	 var minutes = parseInt(leftTime / 1000 / 60 % 60, 10);//计算剩余的分钟 
	 var seconds = parseInt(leftTime / 1000 % 60, 10);//计算剩余的秒数 
	 days = checkTime(days); 
	 hours = checkTime(hours); 
	 minutes = checkTime(minutes); 
	 seconds = checkTime(seconds); 
	 if(days == 0 && hours ==0 && minutes== 0 && seconds==0 ){
		 clearInterval(t1);
	 }
	 $(".jieshu").html("<span class='day'>"+ days+"</span>天<span class='hour'>" + hours+"</span>小时<span class='min'>" + minutes+"</span>分<span class='sec'>"+seconds+"</span>秒") ;
}
//购物车倒计时
function ShopcartTimer(){ 
	if(lasttime != 0){
		lasttime = parseInt(lasttime)-1000; //计算剩余的毫秒数 
		 var minutes = parseInt(lasttime / 1000 / 60 % 60, 10);//计算剩余的分钟 
		 var seconds = parseInt(lasttime / 1000 % 60, 10);//计算剩余的秒数 
		 minutes=checkTime(minutes);
		 seconds=checkTime(seconds);
	 	 if( minutes== 0 && seconds==0 ){
			 clearInterval(t2);
		 }
		 $(".shopcarttime").html( "<span class='min'>" + minutes+"</span>分<span class='sec'>"+seconds+"</span>秒") ;
	}
};
//将0-9的数字前面加上0，例1变为01 
function checkTime(i){ 
	 if(i<10) 
	 { 
	  i = "0" + i; 
	 } 
	 return i; 
} 



 
 

var youxuangg_id="";
var sale_money="0";
var gg_imageurl="";
//选择优选
function shopthis(obj){
	youxuangg_id=$(obj).attr("youxuangg_id");
	needsale_number=$(obj).attr("needsale_number");
	sale_money=$(obj).attr("sale_money");
	gg_imageurl=$(obj).attr("gg_imageurl");
 	$("#gg_image").attr("src",gg_imageurl);
 	$("#gg_kucun").html(needsale_number);
	$("#gg_money").html("￥"+sale_money);
	if(parseInt($("#shopnumber").html()) > parseInt(needsale_number) ){
		$("#shopnumber").html("1");
 	}
	$(".gg_list li").css({"background":"#EFEEEE"});
	$(obj).css({"background":"red"})
}
 

// 商品数量加减
$(".up").click(function(){
	if(parseInt($("#shopnumber").html()) >= parseInt(needsale_number) ){
		$("#shopnumber").html(needsale_number);
	}else{
		sp_num=Number($(".number")[0].innerText)+1
		$(".number")[0].innerText=sp_num;
		$(".tips").css({"display":"none"});
	}
});

$(".down").click(function(){
	sp_num=$(".number")[0].innerText-1
	if (sp_num>=1) {
		$(".number")[0].innerText=sp_num;
	}else {
		$(".tips").css({"display":"block"})
	}
});
 
// 上下拉加载刷新
  var reflash_flag=0
  var mySwiper = new Swiper('.all',{
  		mode: 'vertical',
		onTouchStart: function() {
		      holdPosition = 0;
		},
		onResistanceAfter: function(s, pos){
			reflash_flag=1
			$(".load").css({"display":"block"})
			holdPosition=pos;
		},
		onResistanceBefore: function(s, pos){
			reflash_flag=0
			holdPosition=pos;
		},
		onTouchEnd: function(){
			if (holdPosition>200&&reflash_flag) {
				$(".drag_load")[0].click()
			}
			if (holdPosition>200&&!reflash_flag){
				$(".reflash")[0].click()
			}
		}
	});



  // 选择弹窗
  $(".foot_gm").click(function(){
  	$(".select_box").css({"transform":"translateY(0)"})
  })
  $(".chose").click(function(){
  	$(".select_box").css({"transform":"translateY(0)"})
  })
  $(".close").click(function(){
  	$(".select_box").css({"transform":"translateY(110%)"})
  })
　　var windouhei=$(window).height()
	$("section").css({"height":windouhei*0.9})

//前往注册
function goRegister(){
	window.location.href="<%=basePath%>html_member/goRegister.do?recommended=${pd.recommended}&recommended_type=${pd.recommended_type}";
}
	

</script>
</html>