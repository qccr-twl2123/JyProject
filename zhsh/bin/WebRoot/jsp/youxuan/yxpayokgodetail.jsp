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
	<title>支付成功详情</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/youxuan/normalize.min.css">
	<link rel="stylesheet" href="css/youxuan/qrdd.css">
	<script src="js/jquery-1.8.0.min.js"></script>
</head>
<body>
	<header>
		<div class="goback" onclick="goBaoPing()">‹</div>
		<div class="title">
			 <div class="djs">
		        <h6>订单详情</h6>
	    	</div>
		</div>
	</header>
 	<div class="shopping">
	</div>
	<script type="text/javascript">
	$(function(){
  		$.ajax({
	        type:"post",
	        url:'<%=basePath%>youxuan/successPayOrderDeTail.do', 
		  	data:{
	  	 		"orderno":"${pd.orderno}" 
		  	},                
	        dataType:"json",
 	        success: function(data){
 		          var storeList=data.data;
  		          for(var i=0;i<storeList.length;i++){
		        	  var storepd=storeList[i];
		        	  var store_name=storepd.store_name;
		        	  var pictrue_url=storepd.pictrue_url;
		        	  var allmoney=storepd.sale_money;
		        	  var alljifen=storepd.get_integral;
		        	  var allnumber=storepd.goods_number;
		        	  var allshenmoney=storepd.shen_money;
		        	  var tihuo_id=storepd.tihuo_id;
		        	  var str="<ul class='dd_sj'> <li class='sj_head'> <img src='"+pictrue_url+"'  > <span> "+store_name+"</span> </li>";
 		        	  var goodsList=storepd.goodsList;
 		        	  for(var j=0;j<goodsList.length;j++){
 		        		 var goodspd=goodsList[j];
 		        		 var str1="<li class='sj_sp'> <img src='"+goodspd.image_url+"' ><div class='cont'> <p>"+goodspd.brand_name+"|"+goodspd.goods_name+"<p>"+goodspd.gg_miaosu+"</p> <p class='sp_jg'><span>￥"+goodspd.sale_money+"</span> <span>￥"+goodspd.gf_salemoney+"</span> <span class='sj_num' id='"+goodspd.goods_id+"number'>x"+goodspd.shop_number+"</span></p></div></li>";
  		        		 str+=str1;
 		        	  }
 		        	  var str3="";
 		        	  if(parseInt(alljifen) > 0 && parseInt(allshenmoney) >0){
 		        		 str3="<li class='sj_jf'><span>赠送积分</span><span class='jf_num' >"+alljifen+"</span></li><li class='sj_ysje'><span>已省金额</span><span class='ys_money'>"+allshenmoney+"</span></li><li class='sj_xj'><span>共<span>"+allnumber+"</span>件商品</span> <span>小计：</span> <span>￥"+allmoney+"</span></li><li class='sj_hm'> <span>提货券号码：</span> <span>"+tihuo_id+"</span> </li></ul>";
 		        	  }else if(parseInt(alljifen) > 0 && parseInt(allshenmoney) <=0){
 		        		 str3="<li class='sj_jf'><span>赠送积分</span><span class='jf_num' >"+alljifen+"</span></li><li class='sj_xj'><span>共<span>"+allnumber+"</span>件商品</span> <span>小计：</span> <span>￥"+allmoney+"</span></li><li class='sj_hm'> <span>提货券号码：</span> <span>"+tihuo_id+"</span> </li></ul>";
 		        	  }else if(parseInt(alljifen) <= 0 && parseInt(allshenmoney) >0){
 		        		 str3="<li class='sj_ysje'><span>已省金额</span><span class='ys_money'>"+allshenmoney+"</span></li><li class='sj_xj'><span>共<span>"+allnumber+"</span>件商品</span> <span>小计：</span> <span>￥"+allmoney+"</span></li><li class='sj_hm'> <span>提货券号码：</span> <span>"+tihuo_id+"</span> </li></ul>";
 		        	  }else{
 		        		 str3="<li class='sj_xj'><span>共<span>"+allnumber+"</span>件商品</span> <span>小计：</span> <span>￥"+allmoney+"</span></li><li class='sj_hm'> <span>提货券号码：</span> <span>"+tihuo_id+"</span> </li></ul>";
  		        	  }
  		        	  str+=str3;
 		        	  $(".shopping").append(str);
		          }
 		    }
	    });
	});
	//爆屏列表
	function goBaoPing(){
		window.location.href='<%=basePath%>html_member/goMyYouXuan.do?city_name=${pd.city_name}&area_name=${pd.area_name}';
	}
	</script>
</body>
</html>