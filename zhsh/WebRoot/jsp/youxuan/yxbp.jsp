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
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <title>优选爆品</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="yyg/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/youxuan/normalize.min.css">
    <link rel="stylesheet" href="css/youxuan/response.css">
    <link rel="stylesheet" href="css/youxuan/fonts/iconfont.css">
    <link rel="stylesheet" href="css/youxuan/hsd_baopin.css">
  	<script src="js/jquery-1.8.0.min.js"></script>
 	<style type="text/css">
 		.zishu{
 		    overflow: hidden;
		    white-space: nowrap;
		    text-overflow: ellipsis;
  		}
  		#pullUp {
			background: #fff;
		    height: 40px;
		    line-height: 30px;
		    padding: 5px 80px;  
		    border-bottom: 1px solid #ccc;
		    word-spacing:8px; 
		    font-weight: bold;
		    font-size: 11px;
		    color: #8A8885;
		    text-align: center;
		    clear:both;
		}
 	</style>
</head>
<body>
	<div class="dask">
	    <div class="dask_cont">
	        <ul id="allcitysort">
	           <!--  <li onclick="cityclick(this)" >全部分类</li> -->
	        </ul>
	    </div>
	</div>
     <div class="title">
    <div class="left" onclick="goshuoming()">
        <span class="icon iconfont icon-wenhaoweiwanchengyanzheng"  style="font-size:20px;"></span><span>抢购说明</span>
    </div>
    <script type="text/javascript">
    //去说明页面
    function goshuoming(){
    	window.location.href="<%=basePath%>html_member/goMyYouXuanText.do?type=2";
     }
    </script>
    <div class="tit_cont">
        优选爆品
    </div>
    <div class="right">
        <a onclick="goshopcart()">
        <span class="icon iconfont icon-gouwuche1"></span>
        </a>  
    </div>
     <script type="text/javascript">
    //去购物车页面
    function goshopcart(){
    	if("${pd.member_id}" != "") {
    		window.location.href="<%=basePath%>html_member/goMyYouXuanShopCart.do";
    	}else{
    		alert("请前往登录");
    		return;
    	}
      }
    </script>
    </div>
    <div class="items">
         <div class="item ">
            <a onclick="paixuclick('1')" class="jg_px">
                <span>价格</span>
                <span class="jg_shang icon iconfont icon-shangjiantou"></span>
                <span class="jg_xia icon iconfont  icon-xiajiantou"></span>
            </a>
        </div>
        <div class="item">
            <a onclick="paixuclick('2')" class="zk_px">
                <span>折扣</span>
                <span class="zk_shang icon iconfont icon-shangjiantou"></span>
                <span class="zk_xia icon iconfont icon-xiajiantou"></span>
            </a>
        </div>
        <div class="item" >
            <a  class="sx_px">
                <span id="sort_name">筛选</span>
                <span class="icon iconfont icon-shiliangzhinengduixiang2" style="padding-top: 2px;"></span>
            </a>
         </div>
    </div>
    <div class="djs">
        <h6 class="jieshu"></h6>
    </div>
    <div class="item_lists"  >
    		<div id="goodsall">
    		
			</div>
			<div id="pullUp" onclick="clickmore()">
				<span class="pullUpLabel moreshuju" style="font-size:1.6rem;">点击加载更多... </span>
			</div>
    </div>
    
 	<input type="hidden" id="page" value="1" />
    <footer class="footerdi">
	<ul>
		<li class="f_whole">
			<a  href="<%=basePath%>html_member/gouShouYe.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude}">
				<i></i>
				首页
			</a>
		</li>
		<li class="f_jiexiao">
			<a style=" color: #e90000; " href="<%=basePath%>html_member/goMyYouXuan.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude} ">
				<i  class="cur"></i>
				优选爆品
			</a>
		</li>
		<li class="f_car">
			<a href="<%=basePath%>html_member/gouRenMai.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude} ">
				<i></i>
				人脉圈
			</a>
		</li>
		<li class="f_personal">
 			<a href="<%=basePath%>html_me/goMe.do?province_name=${pd.province_name}&area_name=${pd.area_name}&city_name=${pd.city_name}&address=${pd.address}&longitude=${pd.longitude}&latitude=${pd.latitude} ">
				<i></i>
				我的
			</a>
		</li>
	</ul>
</footer>
</body>
<script src="js/jquery-1.12.4.min.js"></script>
<script language="javascript" type="text/javascript">
  	
		//刚进来运行
		if("${pd.paixu_number}" == ""){
			youxuan(1,"4");
			 $(".jg_shang").css({"color":"red"});
		}else{
			if("${pd.paixu_number}" == "2"){
				 $(".zk_shang").css({"color":"red"});
			}else if("${pd.paixu_number}" == "3"){
				 $(".jg_xia").css({"color":"red"});
			}else if("${pd.paixu_number}" == "4"){
				 $(".jg_shang").css({"color":"red"});
			}else{
				 $(".zk_xia").css({"color":"red"});
			}
			youxuan(1);
		}
  		
	//点击排序
	function paixuclick(type){
		var paixu_number="${pd.paixu_number}";
		if(type == "1"){
			if(paixu_number == "3"){
				paixu_number="4";
			}else{
				paixu_number="3";
			}
		}else{
			if(paixu_number == "1"  ){
				paixu_number="2";
			}else{
				paixu_number="1";
			}
		}
		var city_file_sort_id="";
		window.location.href="<%=basePath%>html_member/goMyYouXuan.do?area_name=${pd.area_name}&city_name=${pd.city_name}&longitude=${pd.longitude}&latitude=${pd.latitude}&paixu_number="+paixu_number+"&city_file_sort_id="+city_file_sort_id;
	}
	
	
	//筛选侧滑
	$(".sx_px").click(function(){
	     $(".dask").css({"display":"block"})
	     $(".dask_cont").animate({right:"0"})
	     $(".anjian").click(close)
	     $(".dask").click(close)
	})
	
	function close(){
	     $(".dask_cont").animate({right:"-100%"},function(){
	         $(".dask").css({"display":"none"})
	     })
	}
	
	//城市分类的筛选
	function cityclick(obj){
		//关闭侧滑
		$(".dask_cont").animate({right:"-100%"},function(){
			   $(".dask").css({"display":"none"})
		});
		var city_file_sort_id=$(obj).attr("city_file_sort_id");
		var sort_name=$(obj).html();
        //刷新
		window.location.href="<%=basePath%>html_member/goMyYouXuan.do?area_name=${pd.area_name}&city_name=${pd.city_name}&longitude=${pd.longitude}&latitude=${pd.latitude}&paixu_number=${pd.paixu_number}&city_file_sort_id="+city_file_sort_id+"&sort_name="+sort_name;
	}
	
	//点击加载更多
	function clickmore(){
	 		//设置页数
			var page=parseInt( $("#page").val() )+1;
			$("#page").val(page);
			youxuan(page);
			$(".moreshuju").html("<img src='images/jiazai.gif' width='28px;'' />");
	}
	
		
 	//优选列表
 	function youxuan(currentPage){
 		//获取数据
 	 	$.ajax({
 		    	type:"post",
 		    	url:'<%=basePath%>youxuan/getAllYouxuanList.do', 
 			 	data:{
 			 		 	"currentPage":currentPage,
 			 		 	"city_name":"${pd.city_name}",
 			 		 	"area_name":"${pd.area_name}",
 			 		 	"city_file_sort_id":"${empty pd.city_file_sort_id?'':pd.city_file_sort_id}",
 			 		 	"sort_name":"${empty pd.sort_name?'':pd.sort_name}",
 			 		 	"paixu_number":"${empty pd.paixu_number?'4':pd.paixu_number}" 
 		 	 	},                
 		    	dataType:"json",
 		    	success: function(data){
 			   	 	if(data.result == "1"){
 			   	 		//城市一级分类的集合
 			  			var pd=data.data.pd;
  			   	 		if(pd.sort_name != null && pd.sort_name != ""){
  			   	 			if(pd.sort_name == "全部分类"){
  			   	 				$("#sort_name").html("筛选");
  			   	 			}else{
  			   	 				$("#sort_name").html(pd.sort_name);
  			   	 			}
  			   	 		}
   			   	 		if(currentPage == "1"){
	   			   	 		window.endtimestamp=parseInt(pd.endtimestamp);
	 			   	 		window.t1 = setInterval(leftTimer, 1000);//1秒执行一次
 			   	 			$(".goodsall").empty();
 			   	 			//设置筛选
 	 			   	 		var firstList=data.data.firstList;
 	 			   	 		for (var i = 0; i < firstList.length; i++) {
 								var e=firstList[i];
 								var str=" <li class='anjian' onclick='cityclick(this)' city_file_sort_id='"+e.city_file_sort_id+"' >"+e.sort_name+"</li>";
 	 							$("#allcitysort").append(str);
 				   	 		}
 			   	 		}
 			   	 		//设置商品
 			   	 		var goodslist=data.data.goodslist;
 			   	 		if(goodslist.length == 0){
	 			   	 		//设置页数
	 			   			var page=parseInt( $("#page").val() )-1;
	 			   			$("#page").val(page);
	 			   			$(".moreshuju").html("<span style='font-size:1.6rem;'>已经到顶了</span>");
 			   	 		}else{
	 			   	 		for (var i = 0; i < goodslist.length; i++) {
								var e=goodslist[i];
								var str="";
								if(e.goods_status == "98" || e.lesssale_number == "0"){
									str="<div class='sp_item col-xs-6' onclick='detailGoods(this)' youxuangoods_id='"+e.youxuangoods_id+"'><div class='cont'><div class='shouqin zuoshang'>商品售罄</div><div class='cont_img'><img src='"+e.image_url+"' alt='' class='spimg' style='height:100%' ><img src='img/ysq_03.png'  class='sq'></div>";
								}else{
									if(parseInt(e.lesssale_number) < 20 ){//小于20
										str="<div class='sp_item col-xs-6' onclick='detailGoods(this)' youxuangoods_id='"+e.youxuangoods_id+"'><div class='cont'> <div class='shengyu zuoshang'>仅剩 <span>"+e.lesssale_number+"</span>"+e.goods_dw+"</div><div class='cont_img'><img style='height:100%' src='"+e.image_url+"' alt='' class='spimg'></div>";
									}else{
										str="<div class='sp_item col-xs-6' onclick='detailGoods(this)' youxuangoods_id='"+e.youxuangoods_id+"'><div class='cont'> <div class='shengyu zuoshang'></div><div class='cont_img'><img src='"+e.image_url+"' alt='' style='height:100%' class='spimg'></div>";
									}
 								}
	 							var str1="<div class='sp_cont'> <div class='jgzk'><span class='xianjia'>￥"+e.bp_salemoney+"</span><span class='yuanjia'>￥"+e.gf_salemoney+"/"+e.goods_dw+"</span><span class='zhekou'>"+e.goods_zkrate+"</span></div>"+
			                		"<div class='sp_title zishu'> "+e.brand_name+" | "+e.goods_name+"</div>"+
			                		"<div class='sp_jdt'><div class='jdt_tit col-xs-6'>  已售进度<span>"+e.jxjd+"</span>%</div>"+
			                    	"<div class='jdt_t col-xs-6'><div class='jdtbg '><div class='progress-bar progress-bar-danger progress-bar-striped active' style='width:"+e.jxjd+"%;border-radius: 1rem;'></div></div></div>"+
			                    "</div> </div> </div> </div> </div>";
								$("#goodsall").append(str+str1);
	 			   	 		}
	 			   	 		$(".moreshuju").html("<span style='font-size:1.6rem;'>点击加载更多...</span>");
 			   	 		}
 			   	 	}else{
 			   	 		alert(data.message);
 			   	 		return;
 			   	 	}
 		     }
 		});
  	}
 	
 	
 	
 	//倒计时
		function leftTimer(){ 
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
				 window.location.reload(); //刷新当前页面
			 }
			 $(".jieshu").html("距离本次结束还剩：<span class='day'>"+ days+"</span>天<span class='hour'>" + hours+"</span>小时<span class='min'>" + minutes+"</span>分<span class='sec'>"+seconds+"</span>秒") ;
		} 
 	    
		function checkTime(i){ //将0-9的数字前面加上0，例1变为01 
			 if(i<10) 
			 { 
			  i = "0" + i; 
			 } 
			 return i; 
		} 
		
		//前往详情界面
		function detailGoods(obj){
			var youxuangoods_id=$(obj).attr("youxuangoods_id");
			window.location.href="<%=basePath%>html_member/goMyYouXuanDetail.do?youxuangoods_id="+youxuangoods_id;
		}
		

 </script> 
</html>