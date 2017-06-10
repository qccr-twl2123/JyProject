<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
        <meta charset="utf-8">
 		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=1.0" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>商品</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/swiper.min.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/sp.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/sp_index.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/style.css">
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/waypoints.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/navbar2.js"></script>
		<%-- <script src="<%=basePath%>js/jquery-1.8.0.min.js"></script> --%>
		<script type="text/javascript">
		if(true){
			//验证提交
			$.ajax({
			        type:"post",
			        url:'<%=basePath%>app_goods/goStoreGoods.do', 
			  	 	 data:{
			  	 		"store_id":"${pd.store_id}",
			 		 	"member_id":"${pd.member_id}" 
			  	 	 },                
			        dataType:"json",
			        success: function(data){ 
			        	var alllist=data.data;
			        	for (var i = 0; i < alllist.length; i++) {
			        		//红包
			        		if(alllist[i].sort =="1"){
			        			$("#nav").append(" <li class='current' onclick='delclass(this)'><a href='#"+alllist[i].id+"'>"+alllist[i].name+"</a><b></b></li>");
			        			$("#container").append("<div class='section' id='"+alllist[i].id+"'></div>");
								var list=alllist[i].list;
								for (var j = 0; j < list.length; j++) {
									var str="";
									str+="<div class='prt-lt'>"+
									    	"<div class='lt-lt'><img src='<%=basePath%>img/hongbao.png'></div>"+
									        "<div class='lt-ct'>"+
									        	"<p ><span id='"+list[j].store_redpackets_id+"'>"+list[j].money+"</span>"+list[j].redpackage_type_name+"</p>"+
									            "<p class='pr' style=' color: gray;  width: 95px; '><span class='price'>"+list[j].redpackage_content+"</span></p>"+
									        "</div>"+
									        "<div class='lt-rt'>"+
									        	"<a   style='text-align: center;background-color:#ff0000;color:#fff;display:inline-block;height:25px;width:70px;padding-top:5px;' onclick='getRedPackage(this)' store_redpackets_id='"+list[j].store_redpackets_id+"'>领取红包</a>"+
									        "</div>"+
									 "</div>";
									$("#"+alllist[i].id).append(str);
								}
							//非红包
			        		}else{
			        			$("#nav").append(" <li  onclick='delclass(this)'><a href='#"+alllist[i].id+"' >"+alllist[i].name+"</a><b></b></li>");
			        			$("#container").append("<div class='section' id='"+alllist[i].id+"'></div>");
			        			var list=alllist[i].list;
								for (var j = 0; j < list.length; j++) {
									var str="";
									str+="<div class='prt-lt'>"+
									    	"<div class='lt-lt'><img src='"+list[j].image_url+"'></div>"+
									        "<div class='lt-ct'>"+
									        	"<p>"+list[j].goods_name+"</p>"+
									            "<p class='pr' style=' color: gray; text-decoration: line-through; width: 95px; '><span>"+list[j].market_money+"</span>/"+list[j].company+"&nbsp;&nbsp;<span style='color: red' class='price money"+list[j].goods_id+"'>"+list[j].retail_money+"</span></p>"+
									        "</div>"+
 									        "<div class='lt-rt'>"+
									        	"<input type='button' class='minus'  onclick='minus(this)' goods_id='"+list[j].goods_id+"' value='-'>"+
									        	"<input type='text'  class='result "+list[j].goods_id+"'  value='0'>"+
									        	"<input type='button' class='add' onclick='add(this)' goods_id='"+list[j].goods_id+"' value='+'>"+
									        "</div>"+
									    "</div>";
									     
									$("#"+alllist[i].id).append(str);
								}
			        		}
			        		
						}
			        	//设置购物车
			        	if(true){
			        		var goods_id="";
			        		var goods_number="";
 			        			<c:forEach items="${shopList }" var="var">
				        			 goods_id="${var.goods_id}";
				        			 goods_number="${var.goods_number}";
				        			 $("."+goods_id).val(goods_number);
			        	 		</c:forEach>
   			        	}
			        }
	      });
		}
		if(true){
			//验证提交
			$.ajax({
			        type:"post",
			        url:'<%=basePath%>app_store/findById.do', 
			  	 	 data:{
			  	 		"store_id":"${pd.store_id}",
			 		 	"member_id":"${pd.member_id}" 
			  	 	 },                
			        dataType:"json",
			        success: function(data){
			       	 	 var storedetail=data.data;
			       	 	if(storedetail.iscollect != "0"){
			        		$(".sp-sc").css("background","url(../images/sj_06.png) no-repeat"); 
			        	}
 			        	//详情
			        	$(".sp-sc").attr("collect",storedetail.iscollect);
			        	$(".sp-vip").attr("vip",storedetail.vip);
 			        }
	      });
		}
 		</script>
 		</script>
</head>

<body>
<nav class="top">
        <i class="sp-sc fr" onclick="iscollect(this)"></i>
    	<i class="sp-vip fr" onclick="isvip(this)"></i>
        <a href="<%=basePath%>html_member/gouShouYe.do"><b class="back-arrow fl"></b></a>
        <div style="text-align:center;line-height:40px;color:#fff">商家商品</div>
</nav>
<script type="text/javascript">
//是否收藏
function iscollect(obj){
	var collect_id=$(obj).attr("collect");
	if(collect_id == "0"){//新增收藏
		$.ajax({
	        type:"post",
	        url:'<%=basePath%>app_store/iscolloctByMS.do', 
	  	 	data:{
	  	 		"store_id":"${pd.store_id}",
	  	 		"member_id":"${pd.member_id}"
		  	 	},                
	        dataType:"json",
	        success: function(data){
	        	$(obj).attr("collect","1");
	        	//改变图片
	        	$(".sp-sc").css("background","url(../images/sj_06.png) no-repeat");
	         }
	    });
		
	}else{//取消收藏
		$.ajax({
	        type:"post",
	        url:'<%=basePath%>app_store/iscolloctByMS.do', 
	  	 	data:{
	  	 		"store_id":"${pd.store_id}",
	  	 		"member_id":"${pd.member_id}"
		  	 	},                 
	        dataType:"json",
	        success: function(data){
	        	$(obj).attr("collect","0");
	            //改变图片
	        	$(".sp-sc").css("background","url(../images/sj_05.png) no-repeat"); 
	         }
	    });
	}
	
}

//是否为vip
function isvip(obj){
 	$.ajax({
        type:"post",
        url:'<%=basePath%>app_store/getVipForStore.do?', 
  	 	data:{
  	 		"store_id":"${pd.store_id}",
  	 		"member_id":"${pd.member_id}"
  	  	 	},                
        dataType:"json",
        success: function(data){
        	alert(data.message);
         }
    });
}

</script>
<div class="yh-title sj-title clearfix">
	<ul>
		<li  ><a href="<%=basePath%>html_member/goStoreDetail.do?store_id=${pd.store_id}&member_id=${pd.member_id}">商家</a></li>
		<li class="sj-current"><a href="<%=basePath%>html_member/goStoreGoods.do?store_id=${pd.store_id}&member_id=${pd.member_id}">商品</a></li>
		<li ><a href="<%=basePath%>html_member/goStoreComment.do?store_id=${pd.store_id}&member_id=${pd.member_id}">评价</a></li>
	</ul>
</div>

<div class="nav-lf">
<ul id="nav" > 
</ul>
</div>

<div id="container" class="container"><!-- 右边 -->
</div>
</div>
</div>
<footer>
	<div class="ft-lt">
        <p>合计:￥<span id="total" class="total">${pd.m}</span>元<span class="nm">(<label class="share">${pd.n}</label>份)</span></p>
    </div>
  
        <c:if test="${!empty pd.member_id and  pd.member_id ne ''}">
            <div class="ft-rt" >
        		<p onclick="jiesuan()">去结算</p>
        	</div>
        </c:if>
        <c:if test="${empty pd.member_id or  pd.member_id eq ''}">
        	<div class="ft-rt" style="background-color: #888888;">
        		<p>去结算</p>
        	</div>
        </c:if>
 </footer>

<script type="text/javascript" src="<%=basePath%>js/htmlmember/Adaptive.js"></script>
 <script type="text/javascript" src="<%=basePath%>js/htmlmember/swiper.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery.nav.js"></script>
<script type="text/javascript">
$(function(){
	$('#nav').onePageNav();
 });
//领取红包
function getRedPackage(obj){
	var store_redpackets_id=$(obj).attr("store_redpackets_id");
	var money=$("#"+store_redpackets_id).html();
 	$.ajax({
	        type:"post",
	        url:'<%=basePath%>app_shouye/getRedPackage.do', 
	  	 	 data:{
	  	 		"store_redpackets_id":store_redpackets_id,
	  	 		"money":money,
	 		 	"member_id":"${pd.member_id}" 
	  	 	 },                
	        dataType:"json",
	        success: function(data){ 
	        	if(data.result == "1"){
	        		window.location.reload(); //刷新当前页面
	        	}else{
	        		//alert(data.message);
	        	}
	        }
  	});
}


//标识红红的
function delclass(obj){
	$("#nav").find("li").removeClass("current");
	$(obj).addClass("current");
}



//----------------------------------------------------------
var swiper = new Swiper('.swiper-container', {
	pagination: '.swiper-pagination',
	paginationClickable: true,
	spaceBetween: 30,
});



</script>
<script> 
 		//添加商品
		function add(obj){
			var t=$(obj).parent().find('input[class*=result]'); 
			var goods_number=$(obj).prev().val();
			var goods_id=$(obj).attr("goods_id");
			$("."+goods_id).val(goods_number);
			var money=$(".money"+goods_id).html();
			if(caozuoShopCar(parseInt(t.val())+1+"",goods_id)){
				t.val(parseInt(t.val())+1);
				setTotal(parseFloat(money),1);
			}
    	}
 		
		//减少商品
		function minus(obj){ 
			var t=$(obj).parent().find('input[class*=result]'); 
			var x=t.val();
   			var goods_id=$(obj).attr("goods_id");
  			var money=$(".money"+goods_id).html();
 			var goods_number=$(obj).next().val();
 			$("."+goods_id).val(goods_number);
			if(caozuoShopCar(parseInt(t.val())-1+"",goods_id)){
				t.val(parseInt(t.val())-1);
				if(parseInt(t.val())<0){ 
					t.val(0); 
				} 
				if(x != "0"){
					setTotal(-parseFloat(money),-1);
	  			}
			}
     	}
		 
		
		//操作购物车
		function caozuoShopCar(goods_number,goods_id){
 			$.ajax({
		        type:"post",
		        url:'<%=basePath%>app_goods/caozuoShopCar.do', 
		  	 	 data:{
		  	 		"caozuo_number":goods_number,
		  	 		"goods_id":goods_id,
		 		 	"member_id":"${pd.member_id}" 
		  	 	 },                
		        dataType:"json",
		        success: function(data){ 
		        	 //新增成功或失败
		        	 if(data.result == "0"){
		        		 alert(data.message);
		        		 return false;
		        	 }
		        }
	  		});
 			return true;
		}
		
		
		//统计总数
		function setTotal(money , number){ 
				var s=parseFloat($("#total").html());
				var v=parseInt($(".share").html());
				//计算总额
				s+=money;
				v+=number;
 				$(".share").html(v);
				$("#total").html(s.toFixed(2));
		} 
		//setTotal(); 

	function jiesuan(){
		var total = $("#total").text();
		if(total == 0.00 || total == 0){
			alert("您还没有选择你要购买的商品！");
			return ;
		}else {
			window.location.href='<%=basePath%>html_member/goStoreGoodsOverBuy.do?store_id=${pd.store_id}&member_id=${pd.member_id}&paymoney='+total;
		}
	}
 </script> 
 </body>
</html>