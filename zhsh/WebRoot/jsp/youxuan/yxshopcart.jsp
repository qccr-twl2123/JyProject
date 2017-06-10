<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
	<title>购物车</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/youxuan/gwc.css">
	<link rel="stylesheet" href="css/youxuan/fonts/iconfont.css">
	<link rel="stylesheet" href="css/youxuan/normalize.min.css">
	<script src="js/jquery-1.8.0.min.js"></script>
</head>
<body>
	<header>
		<div class="goback" onclick="backreturn()">‹</div>
		<div class="title">
			 <div class="djs">
		        <h6>购物车倒计时：</h6>
		        <h6 class="shopcarttime"></h6>
	    	</div>
		</div>
	</header>
	<script type="text/javascript">
	function backreturn(){
		window.location.href="<%=basePath%>html_member/goMyYouXuan.do";
	}
	</script>
	<div class="shopping">
		 
	</div>
	<div class="footer">
		<span class="iconfont icon-dui foot_xz" onclick="chose()" name="quanxuan"></span><span>全选</span>
		<span>合计：</span><span class="zongjia"></span>
		<a class="foot_tj" onclick="goReadyPayOrder()" style="background:#aaa;">提交订单</a>
	</div>
</body>
<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/touch.min.js"></script>
<script>
 	// 选择
	function chose(e){   //选择点击
			var ev=e||window.event;
	        var target=ev.target||ev.srcElement;
	        if ($(target).attr("check")=="checked") {
	        	qxxuanze(target)
	        }else{
	        	xuanze(target)
	        }
	        zongjia();
	}
	
	function xuanze(target){    //由父级类名判断点击位置 并向相应元素添加属性
		var farther=$(target).parent()[0].className
		switch (farther)
		{
			case "footer":   //全选
			$(".icon-dui").attr("check","checked");
			xuanran();
			break;
			case "sign":    //店铺
			$(target).attr("check","checked");
			$(target).parent().parent().parent().children(".shop_item").children(".shop_sign").children("span").attr("check","checked");
			xuanran();
			break;
			case "shop_sign":    //商品
			$(target).attr("check","checked");
			xuanran();
			break;
		}
		$(".foot_tj").css("background","#fc0d1b")
	}
	function xuanran(target){     //渲染页面
	 	$(".icon-dui").removeAttr("style")
		if($("body [check=checked]").length>0){
			$("body [check=checked]").css({"background":"#f85e61","border":"2px solid #f85e61"})
		}
	}
	function qxxuanze(target){		//取消选择
		var farther=$(target).parent()[0].className
		switch (farther)
		{
			case "footer":    
			$(".icon-dui").removeAttr("check");
			xuanran();
			break;
			case "sign":
			$(target).removeAttr("check");
			$(target).parent().parent().parent().children(".shop_item").children(".shop_sign").children("span").removeAttr("check");
			xuanran();
			break;
			case "shop_sign":
			$(target).removeAttr("check");
			xuanran();
			break;
		}
		if( str=="0元" || str==""){
			$(".foot_tj").css("background","#aaa")
		}
	}
	
	//计算总价
	function zongjia(){
		var zj=0;
		var allshopcart_id="";
		var sel_span = $("span[check=checked]");
		for (var i = 0; i < sel_span.length; i++) {
			if($(sel_span[i]).attr("check")=="checked"&&$(sel_span[i]).parent()[0].className=="shop_sign") {
				var shopcart_id=$(sel_span[i]).attr("shopcart_id");
				if(shopcart_id != ""){
					allshopcart_id+=shopcart_id+",";
				}
 			}
		};
		$.ajax({
	        type:"post",
	        url:'<%=basePath%>youxuan/countAllShopMoney.do', 
 	  	 	data:{
	  	 		"allshopcart_id":allshopcart_id 
		  	},                
	        dataType:"json",
	        async: false,
	        success: function(data){
		         var pd=data.data;
		         zj=pd.allmoney;
		    }
	    });
		$(".zongjia")[0].innerText=zj+"元";
	}



//购物车列表
if(true){
	//获取数据
 	$.ajax({
	    	type:"post",
	    	url:'<%=basePath%>youxuan/MyShopGoodsList.do', 
		 	data:{},                
	    	dataType:"json",
	    	async: false,
	    	success: function(data){
		   	 	if(data.result == "1"){
		   	 		 var map1=data.data;
		   	 		//购物车清空倒计时时间
		   	 		 var lasttime=map1.lasttime;
		   	 	 	 window.lasttime=parseInt(lasttime);
		   	 		 if(lasttime != "0"){
		   	 		 	window.t2 = setInterval(ShopcartTimer, 1000);//1秒执行一次
		   	 		 }
		   	 		 //购物车列表
		   	 		 var storeList=map1.storeList;
 		   	 		 for (var i = 0; i < storeList.length; i++) {
 		   	 			 var str1="<ul class='shop'><li class='shop_tit'><div class='sign'><span shopcart_id='' class='iconfont icon-dui' onclick='chose()' ></span></div><div class='shopname'>"+storeList[i].store_name+"</div></li>";
  		   	 			 var goodsList=storeList[i].goodsList;
  		   	 			 var str2="";
 		   	 			 for (var j = 0; j < goodsList.length; j++) {
 		   	 				 var str3="<li class=' del shop_item'>"+  
 		   	 							    "<div class='shop_sign'> <span youxuangg_id='"+goodsList[j].goods_id+"' shopcart_id='"+goodsList[j].shopcart_id+"' class='iconfont icon-dui' onclick=chose()></span> </div>"+
 		   									"<div class='sp'>"+ 
 		   										"<div class='sp_chose'> <img src='"+goodsList[j].gg_imageurl+"' > </div>"+
 		   										"<div class='sp_cont'> "+
	 		   										"<div class='sp_tit'>"+goodsList[j].brand_name+" | "+goodsList[j].goods_name+"  </div> <div class='sp_gg'>"+goodsList[j].gg_miaosu+"</div> <div class='sp_sl' youxuangg_id='"+goodsList[j].goods_id+"' ><a  class='down sljj' onclick='reduceGoodsNumber(this)' style='height:100%;'>-</a> <span class='number' id='number"+goodsList[j].goods_id+"'>"+goodsList[j].goods_number+"</span> <a  class='up sljj' onclick='addGoodsNumber(this)' style='height:100%;'>+</a> </div>"+
	 		   										"<div class='sp_spr'> <p class='sp_xj'>￥"+goodsList[j].sale_money+"</p> <p class='sp_yj'>￥"+goodsList[j].gf_salemoney+"/"+goodsList[j].goods_dw+"</p> </div>"+
 		   										"</div>"+
 		   								  	"</div>"+
 		   									"<p class='sp_address'> <span class='address'>提货地址："+goodsList[j].th_address+" </span> </p>"+
 		   									"<div class='del_button' youxuangg_id='"+goodsList[j].goods_id+"' >删除</div>"
 		   								"</li>";
 		   	 				str2+=str3;
 		   	 			 }
 		   	 			 var str=str1+str2+"</ul>";
 		   	 			 $(".shopping").append(str);
  		   	 		 }
     		   	}else{
			   	 		alert(data.message);
			   	 		return;
		   	 	}
	     }
	});
};
 

//// 删除
$(function(){
	var del_s =$(".del")    //在需要被滑动删除的元素上加上   类名   
	function del(){     //滑动后 父元素 是否非空   空则删除当前店铺	
			if (fa_ul.children("li").length==1) {
				fa_ul.remove()
			};
	}
	touch.on(del_s, 'swipeleft', function(ev){   //左划
		drag=$(this).closest("li")
		fa_ul=$(this).closest("ul") 
		var str=drag[0].style.left.substring(0,1)
		if (str=="-") {
			return;
				}else{
					$(".del").css({"left":"0"})
						$(".del_button").css({"display":"none"});
							drag.animate({left:"-20%"},function(){
								$(drag).find(".del_button").css({"display":"block"}).click(function(e){
									drag.animate({left:"-100%"},function( ){
										var ev=e||window.event;
								        var shanchu=ev.target||ev.srcElement;
										var youxuangg_id=$(shanchu).attr("youxuangg_id");
										var number="-"+$("#number"+youxuangg_id).html();
										if(addShopCart(number,youxuangg_id)){
											drag.remove();
											del();
										};
									})
								})
							})		
				};
	touch.on(del_s, 'swiperight', function(ev){    //左滑后右滑恢复
		drag.animate({left:"0"},function(){$(drag).find(".del_button").css({"display":"none"})});	
	})
	})
	zongjia();
	if($(".shopping").children().length==0){
		$(".zongjia").text("0元")
		$(".foot_tj").css("background","#aaa")
	}
})

//购物车加
function addGoodsNumber(obj){
	var youxuangg_id=$(obj).parent().attr("youxuangg_id");
  	if(addShopCart("1",youxuangg_id)){
		sp_num=Number($(obj).prev()[0].innerText)+1
		$(obj).prev()[0].innerText=sp_num;
		zongjia();
	}
}
//购物车减
function reduceGoodsNumber(obj){
	if($(obj).next()[0].innerText != "1"){
		var youxuangg_id=$(obj).parent().attr("youxuangg_id");
		if(addShopCart("-1",youxuangg_id)){
			sp_num=$(obj).next()[0].innerText-1
			if (sp_num>=1) {
				$(obj).next()[0].innerText=sp_num;
			}
			zongjia();
		}
	}
 }
//添加进购物车
function addShopCart(number,youxuangg_id){
	var flag=true;
	$.ajax({
        type:"post",
        url:'<%=basePath%>app_goods/caozuoShopCar.do', 
  	 	data:{
  	 		"goods_id":youxuangg_id,
  	 		"goods_type":"2",
  	 		"caozuo_number":number,
  	 		"member_id":"${pd.member_id}"
	  	 	},                
        dataType:"json",
        async: false,
        success: function(data){
	        if(data.result == "0"){
 	        	alert(data.message);
 	        	flag=false;
       	 	}else{
       	 		lasttime=30*60*1000;
        	} 
	    }
    });
	return flag;
}


//购物车倒计时
function ShopcartTimer(){ 
	 lasttime = parseInt(lasttime)-1000; //计算剩余的毫秒数 
	 var minutes = parseInt(lasttime / 1000 / 60 % 60, 10);//计算剩余的分钟 
	 var seconds = parseInt(lasttime / 1000 % 60, 10);//计算剩余的秒数 
	 minutes=checkTime(minutes);
	 seconds=checkTime(seconds);
 	 if( minutes== 0 && seconds==0 ){
		 clearInterval(t2);
		 window.location.reload(); //刷新当前页面
	 }
	 $(".shopcarttime").html( "<span class='min'>" + minutes+"</span>分<span class='sec'>"+seconds+"</span>秒") ;
};
//将0-9的数字前面加上0，例1变为01 
function checkTime(i){ 
	 if(i<10) 
	 { 
	  i = "0" + i; 
	 } 
	 return i; 
} 

//去结算页面
function goReadyPayOrder(){
	//参数goodsinfor gopay_type  
	var str=$(".zongjia").text()
	if(str==""|| str=="0元"){
		$(".foot_tj").css("background","#aaa")
		alert("请选择商品");
	}else{
	var goodsinfor="";
	var sel_span = $("span[check=checked]");
	for (var i = 0; i < sel_span.length; i++) {
		if($(sel_span[i]).attr("check")=="checked"&&$(sel_span[i]).parent()[0].className=="shop_sign") {
				var youxuangg_id=$(sel_span[i]).attr("youxuangg_id");
 				if(youxuangg_id != ""){
					var number=$("#number"+youxuangg_id).html();
					goodsinfor+=youxuangg_id+"@"+number+",";
				}
  		}
	};
	//判断库存以及商家积分充不充足
	$.ajax({
        type:"post",
        url:'<%=basePath%>youxuan/gosurePayOrder.do', 
	  	data:{
  	 		"goodsinfor":goodsinfor, 
  	 		"gopay_type":"2"
	  	},                
        dataType:"json",
        async: false,
        success: function(data){
        	  var result_status=data.result;
        	  if(result_status == "0"){
	        		alert(data.message);
        	  }else{
        		  window.location.href="<%=basePath%>html_member/goReadyPayOrder.do?gopay_type=2&goodsinfor="+goodsinfor;
        	  }
 		}
    });
}
}

</script>
</html>