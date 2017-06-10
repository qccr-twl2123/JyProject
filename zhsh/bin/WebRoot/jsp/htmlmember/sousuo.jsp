<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Cache-Control" content="no-cache,must-revalidate">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
    <title>商家搜索</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="css/htmlmember/labary/predefine.css">
    <link rel="stylesheet" href="css/htmlmember/search.css">
	<link rel="stylesheet" href="css/htmlmember/main.css">
 </head>

<body>
<div class="search_box" style="position: fixed;top: 0;z-index:1;overflow:hidden;">
    <span class="return_back" style="vertical-align: bottom;position: absolute;left: 0;padding: 1.8% 3.6%;display: block;height: 35px;"><img src="img/fanhui.png" alt=""></span>
	<div style="text-align: center;width: 100%;height: 100%;font-size: 1rem;line-height: 35px;">
		<span style="color: #fff;">商家搜索</span>
	</div>
</div>
<div class="search_box yingcang" style="background: #c9c9c9;position: fixed;top: 50px;z-index: 1;">
        <div class="inputbox">
            <input type="text" maxlength="10" oninput="search_content()" id="search_content" placeholder="输入商家地址/商家名称进行检索">
        </div>
</div>
<ul class="sj_list" style="position: relative;padding-top: 100px;">

</ul>
 
</body>
<script src="js/htmlmember/library/jquery-1.12.4.min.js"></script>
<script src="js/htmlmember/star/jquery.raty.min.js"></script>
<script type="text/javascript">
var base_inf={
	    base_herf:"<%=basePath%>",
	    city_name:"${pd.city_name}",
        area_name:"${pd.area_name}",
         longitude:"${pd.longitude}",
        latitude:"${pd.latitude}",
	    nowpage:1
};

$(function(){
	 /*上拉加载*/
    var star, mov, end;
    $(window).on('touchstart', function (e) {
        var touch = e.originalEvent.targetTouches[0];
        star = touch.pageY;
    });
    $(window).on('touchmove', function (e) {
        var touch = e.originalEvent.targetTouches[0];
        mov = touch.pageY;
        if ($(document).scrollTop() >= $(document).height() - $(window).height()) {

         }
    });
    if (Number(star - mov) > 50) {

    }
    if (50 < Number(star - mov) < 300) {
        $(".load").css({"height": star - mov, "line-height": star - mov});
        $(window).on('touchend', function (e) {
            var touch = e.originalEvent.changedTouches[0];
            end = touch.pageY;
            if ($(document).scrollTop() >= $(document).height() - $(window).height()){
                base_inf.nowpage=Number(base_inf.nowpage)+1;
                zhongzhuan();
                $(".load").css("display", "none")
            }
        });
    }
    
    //返回
    $(".return_back").click(function(){
    	window.location.href="<%=basePath%>html_member/gouShouYe.do";
    });
    
    zhongzhuan();
        	
});

//中专方法
function zhongzhuan(){
	if($("#search_content").val() == ""){
		return;
	}
	 var url=base_inf.base_herf+"html_member/gSLPage.do?content="+$("#search_content").val()+"&nowpage="+base_inf.nowpage+"&city_name="+base_inf.city_name+"&area_name="+base_inf.area_name+"&longitude="+base_inf.longitude+"&latitude="+base_inf.latitude;
 	 golink(url,shangjiazairu);
}
 
//搜索框搜索
function search_content(){
	 base_inf.nowpage=1;
	 zhongzhuan();
}

//跳转ajax获取链接
function golink(go_link,fun){//链接，跳转地址
     $.ajax({
        url:  go_link,
        type:'post',
        cache:false,
        beforeSend :function(xmlHttp){
            xmlHttp.setRequestHeader("If-Modified-Since","0");
            xmlHttp.setRequestHeader("Cache-Control","no-cache");
        },
        success:fun
    })
}
//商家载入;
function shangjiazairu(data) {
	 var storelist=data.data;
	 if(base_inf.nowpage == "1"){
		 $(".sj_list").empty();
	 }
 	 if(parseInt(base_inf.nowpage) >1 && storelist.length == 0){
		 base_inf.nowpage=parseInt(base_inf.nowpage)-1;
	 }
 	 for (var i = 0; i < storelist.length; i++) {
		 var storepd=storelist[i];
		 var makstr="";
		 for (var j = 0; j < storepd.marketlist.length; j++) {
			 	var m_type="";
			    if(storepd.marketlist[j].marketing_type == "1"){
			    	m_type="满赠";
			    }else if(storepd.marketlist[j].marketing_type == "2"){
			    	m_type="立减";
			    }else if(storepd.marketlist[j].marketing_type == "3"){
			    	m_type="时段";
			    }else if(storepd.marketlist[j].marketing_type == "4"){
			    	m_type="立减";
			    }else if(storepd.marketlist[j].marketing_type == "5"){
			    	m_type="累计";
			    }else if(storepd.marketlist[j].marketing_type == "6"){
			    	m_type="积分";
			    }else{
			    	m_type="折扣";
			    }
			    makstr +=  "<li> <span class='ji'>"+m_type+"</span> <span>"+storepd.marketlist[j].grantrule+"</span> </li>";
 		}
		 //判断是否有红包
		 var redstr="";
		 if(storepd.haveRed == "1"){
			 redstr="<div class='hongbao'><span>红包</span></div>";
		 }//判断是否有折扣
		 var zkstr="";
		 if(storepd.zkstatus == "1"){
			 zkstr="<span class='zhekou'>折</span>";
		 }
		 var thistorestr =   " <li class='shangjia ' sk_shop='"+storepd.new_store_id+"' num='1' onclick='goStoreDetail(this)'>"+
			        "    <div class='sj_jcxx'>"+
			        "        <div class='img_box'>"+zkstr+
			        "            <img src='"+storepd.pictrue_url+"'>"+
			        "        </div>"+
			        "        <div class='sj_inf'>"+
			        "            <div style='margin-bottom: 0.5rem;'>"+
			        "                <span class='sj_tit'>"+storepd.store_name+"</span>"+
			        "                <span class='loc_num'>"+storepd.distance+"</span>"+
			        "            </div>"+
			        "            <span id='"+(i+1)+"'  class='star' starnum='"+storepd.comment_score+"' title='good'  style='width: 110px;'></span><span class='sj_danshu'>已接63单</span>"+
			        "            <span style='color: #ff0600;'>"+storepd.comment_score+"分</span>"+
			        "        </div>"+
			        "    </div>"+
			        "    <div class='sj_youhui'>"+
			        "       <div class='zs_jf'>"+
			        "            <div>积分"+storepd.integral_rate+"</div>"+
 			        "        </div>"+
			        "        <ul class='sj_jf_inf'>"+makstr
 			        "        </ul>"+
			        "    </div>"+redstr+
			        "</li>";
 		  $(".sj_list").append(thistorestr);
 	}
 	starload();
}
//星级初始化
function starload(){
    var star= $(".star")
    $.fn.raty.defaults.path = 'js/htmlmember/star/img';   /*星级图片 地址*/
    for(var i = 0; i<star.length;i++ ){
        var str=$(star[i]).attr("id");
        var num=$(star[i]).attr("starnum")
        $("#"+str).raty({ readOnly:true,score:num,half:true,space:false});
    }
}
</script>
</html>