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
	<title>预览</title>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<link rel="stylesheet" href="css/youxuan/normalize.min.css">
	<link rel="stylesheet" href="css/youxuan/swiper.min.css">
	<link rel="stylesheet" href="css/youxuan/fonts/iconfont.css">
	<link rel="stylesheet" href="css/youxuan/yulan.css">
	<script src="js/jquery-1.8.0.min.js"></script>
</head>
<body>
<!-- 选择属性 -->
<div class="select_box">
	<div class="sel_cont">
		<div class="sel_tit">
			<div class="sel_img">
				<img src="${pd.look_imageurl}" id="gg_image">
			</div>
			<div class="sel_price">
				<span class="close iconfont icon-guanbi"></span>
				<p class="sel_jg" id="gg_money">￥${pd.ggsale_qujian}</p>
				<p class="sel_kc" >库存<span id="gg_kucun">${pd.ggcount_number}</span> ${pd.goods_dw}</p>
				<p class="sel_gg"  >请选择商品规格</p>
			</div>
		</div>	
		<div class="guige">
			<p class="gg_tit">
				商品规格
			</p>
			<ul class="gg_list">
				<c:forEach items="${gglist}" var="ggvar">
					<li gg_imageurl="${ggvar.gg_imageurl}" needsale_number="${ggvar.needsale_number}" sale_money="${ggvar.sale_money}"   onclick="shopthis(this)">${ggvar.gg_miaosu}</li>
				</c:forEach>
 			</ul>
		</div>	
		<div class="num">
			<span class="num_tit">购买数量</span><span class="tips num_tit">${pd.goods_dw }数至少为一${pd.goods_dw }</span>
			<span class="sl"><a class="down sljj">-</a><span class="number sljj" id="shopnumber">1</span><a  class="up sljj">+</a></span>
		</div>	
		<div class="sel_anniu">
			<a class="sel_button sel_jrgwc">加入购物车</a>
			<a class="sel_button sel_nowbay">立即购买</a>
		</div>
	</div>
</div>
<div class="sect">
		<!-- 轮播图   -->
		<div class="lunbo">
			<div class="banner swiper-container">
		      <div class="swiper-wrapper">
		      	<c:forEach items="${smalllist}" var="smallvar">
		      		 <div class="swiper-slide img_box"><img src="${smallvar.small_url}"  ></div>
		      	</c:forEach>
 		      </div>
		    </div>
    	</div>
		<div class="sp_title_box">
			<h4 class="sp_tit">${pd.brand_name} | ${pd.goods_name}</h4>
			<p>
				<span class="xj">￥${pd.bp_salemoney}</span>
				<span class="yj">￥${pd.gf_salemoney}/${pd.goods_dw}</span>
				<span class="zk">${pd.goods_zkrate}折</span>
			</p>
		</div>
		<div class="chose">
			<span class="sele">请选择商品规格</span>
			<span class="chose_go">›</span>
		</div>
		<div class="sj_inf">
			<p><span>销售商家：</span><span>${pd.store_name}</span></p>
			<p><span>提货地址：</span><span>${pd.th_address}</span></p>
			<p><span>积分率：</span>${pd.jifen_text}</p>
			<c:if test="${pd.isxiangou eq '1'}">
				<p><span>限购：</span>${pd.xiangou_text}</p>
			</c:if>
 		</div>
		<div class="dianpu">
			<div class="dp_img">
				<img src="${pd.pictrue_url}"  >
			</div>
			<div class="dp_tit">
				<p>${pd.store_name}</p>
				<p>${pd.store_address}</p>
			</div>
			<div class="dp_sc">
				<a >收藏</a>
			</div>
		</div>
		<div class="checkbox">
			<div class="xqcs xqimg act">图文详情</div>
			<div class="xqcs xqlist ">产品参数</div>
		</div>
		<div class="imgbox ">
			<c:forEach items="${biglist}" var="bigvar">
				<img src="${bigvar.big_image }"  >
			</c:forEach>
  		</div>
		<div class="list disnone" style="background-color: #fff;">
			<table>
				<c:forEach items="${jjlist}" var="jjvar">
					<tr>
						<td>${jjvar.title }</td>
						<td>${jjvar.text }</td>
					</tr>
				</c:forEach>
 			</table>
		</div>
		</div>
</body>
<script src="js/youxuan/jquery-2.1.4.min.js"></script>
<script src="js/youxuan/swiper.min.js"></script>
<script>
$(".xqimg").click(function(){
	$(".xqcs").css({"color":"#000"})
	$(".xqimg").css({"color":"rgb(255,207,128)"})
	$(".imgbox").css({"display":"block"})
	$(".list").css({"display":"none"})
})
$(".xqlist").click(function(){
	$(".xqcs").css({"color":"#000"})
	$(".xqlist").css({"color":"rgb(255,207,128)"})
	$(".list").css({"display":"block"})
	$(".imgbox").css({"display":"none"})
});

var needsale_number="${pd.ggcount_number}";

//选择优选
function shopthis(obj){
	needsale_number=$(obj).attr("needsale_number");
	sale_money=$(obj).attr("sale_money");
	gg_imageurl=$(obj).attr("gg_imageurl");
 	$("#gg_image").attr("src",gg_imageurl);
	$("#gg_kucun").html(needsale_number);
	$("#gg_money").html("￥"+sale_money);
	if(parseInt($("#shopnumber").html()) > parseInt(needsale_number) ){
		$("#shopnumber").html(needsale_number);
	}
	$(".gg_list li").css({"background":"#ccc"})
	$(obj).css({"background":"#eee"});
}

//商品数量加减
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

$(".sel_cont").click(function(e){
    e.stopPropagation();

})


// 点击选择规格
var gg= $(".gg_list li")
gg.click(function(){
	gg.css({"background":"#ccc"})
	$(this).css({"background":"#eee"})
	$(".sel_gg")[0].innerText="您选择的是："+$(this)[0].innerText
})



  // 选择弹窗
 $(".select_box").click(function(){
  	$(".select_box").css({"transform":"translateY(110%)"})
  })
  $(".chose").click(function(){
  	$(".select_box").css({"transform":"translateY(0)"})
  })
  $(".close").click(function(){
  	$(".select_box").css({"transform":"translateY(110%)"})
  })
　　var windouhei=$(window).height()
	$("section").css({"height":windouhei*0.9})



// 轮播构建
    var banner = new Swiper('.banner',{
        pagination: '.swiper-pagination',
        paginationClickable: true,
        autoHeight: true, 
    });
</script>

</html>