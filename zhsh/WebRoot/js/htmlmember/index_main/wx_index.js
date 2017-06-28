//下拉加载更多
function xl_load(){
   base_inf.nowpage=parseInt(base_inf.nowpage)+1;
   tongyong();
}

//通过方法
function tongyong(){
	var url=base_inf.base_herf+"html_member/gSLPage.do?city_file_sort_id=&sort_type=&sort_name="+
 	"&paixu=&shaixuan=&nowpage="+base_inf.nowpage+
 	"&city_name="+base_inf.city_name+"&area_name="+base_inf.area_name+"&longitude="+base_inf.longitude+"&latitude="+base_inf.latitude;
 	golink(url,shangjiazairu);
}
//上拉加载
var load_flag=0 

//商家载入;
function shangjiazairu(data) {
	 load_flag=0;
	 var storelist=data.storeList;
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
		 var thistorestr =   " <li class='shangjia ' sk_shop='"+storepd.sk_shop+"' num='1' onclick='goStoreDetail(this)'>"+
			        "    <div class='sj_jcxx'>"+
			        "        <div class='img_box'>"+zkstr+
			        "            <img src='"+storepd.pictrue_url+"'>"+
			        "        </div>"+
			        "        <div class='sj_inf'>"+
			        "            <div style='margin-bottom: 0.5rem;'>"+
			        "                <span class='sj_tit'>"+storepd.store_name+"</span>"+
			        "                <span class='loc_num'>"+storepd.distance+"</span>"+
			        "            </div>"+
			        "            <span id='"+storepd.only_store_id+"'  class='star' starnum='"+storepd.comment_score+"' title='good'  style='width: 110px;'></span><span class='sj_danshu'>已接"+storepd.transaction_times+"单</span>"+
			        "            <span style='color: #ff0600;'>"+storepd.comment_score+"分</span>"+
			        "        </div>"+
			        "    </div>"+
			        "    <div class='sj_youhui'>"+
			        "       <div class='zs_jf'>"+
			        "            <div>积分"+storepd.integral_rate+"</div>"+
 			        "        </div>"+
			        "        <ul class='sj_jf_inf'>"+makstr+
 			        "        </ul>"+
			        "    </div>"+redstr+
			        "</li>";
 		  $(".load").before(thistorestr);
 	}
 	starload();
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


//星级初始化
function starload(){
    var star= $(".star");
     $.fn.raty.defaults.path = 'js/htmlmember/star/img';   /*星级图片 地址*/
    for(var i = 0; i<star.length;i++ ){
        var str=$(star[i]).attr("id");
        var num=$(star[i]).attr("starnum")
        $("#"+str).raty({ readOnly:true,score:num,half:true,space:false});
    }
}

//前往筛选页面
function gSSX(){
	 window.location.href=base_inf.base_herf+"html_member/gSSX.do?city_name="+base_inf.city_name+"&area_name="+base_inf.area_name+"&longitude="+base_inf.longitude+"&latitude="+base_inf.latitude;; 
}

//一级级分类点击
function oneclick(obj){
   	window.location.href=base_inf.base_herf+"html_member/gSSX.do?city_file_sort_id="+$(obj).attr("sort_id")+"&sort_type="+$(obj).attr("sort_type")+"&sort_name="+
  	$(obj).attr("sort_name")+"&paixu=&shaixuan=&nowpage=1"+
 	"&city_name="+base_inf.city_name+"&area_name="+base_inf.area_name+"&longitude="+base_inf.longitude+"&latitude="+base_inf.latitude;
}

function goStoreDetail(obj){
 	window.location.href=base_inf.base_herf+"html_member/goStoreDetail.do?sk_shop="+$(obj).attr("sk_shop");
}

function goArea(){
	window.location.href=base_inf.base_herf+"html_member/goArea.do?city_name="+base_inf.city_name+"&area_name="+base_inf.area_name;
}
function add_li(){
	$(".add_to_list ul").empty();
    var tjhyzc="<li><a href='"+base_inf.base_herf+"/html_member/goTuiJianRegister.do'>推荐会员注册</a></li>"
    var fjsjhb="<li><a href='"+base_inf.base_herf+"/html_member/goFuJinRed.do?address="+base_inf.address+"&city_name="+base_inf.city_name+"&area_name="+base_inf.area_name+"&longitude="+base_inf.longitude+"&latitude="+base_inf.latitude+"'>附近商家红包</a></li>"
    var sysyhmd="<li><a onclick='saoyisao()'>扫一扫优惠买单</a></li>";
    if( $(".add_to_list ul").children().length==0){
         $(".add_to_list ul").append(tjhyzc+fjsjhb+sysyhmd)
   }
}

//页面加载后执行
$(function(){
	//构造轮播 分类
  var swiper = new Swiper('.swiper-container_1', {
      pagination: '.swiper-pagination_1',
      slidesPerView: 5,
      slidesPerColumn: 2,
      slidesPerGroup : 4,
      paginationClickable: true
  });
  //构造轮播  图片
  var swiper = new Swiper('.swiper-container_2', {
      pagination: '.swiper-pagination_2',
      paginationClickable: true,
      autoplay : 5000,
      loop:true
  });
  
   /*内部显示区大小改变*/
  function heightchange(){
      $("section").height($(window).height()-$("header").innerHeight()-$("footer").innerHeight());
      $(window).resize(function(){
          $("section").height($(window).height()-$("header").innerHeight()-$("footer").innerHeight());
          sec_height=$(window).height()-$("header").innerHeight()-$("footer").innerHeight();
          return  sec_height
      });
      sec_height=$(window).height()-$("header").innerHeight()-$("footer").innerHeight();
      return  sec_height
  }
  //内部高度改变
  var sec_height=heightchange();
   //上拉加载
   $("section").on("scroll",function(){
      if(($(".load").offset().top-80)<$("section").height()){
      	if(load_flag==0){
              load_flag=1
              xl_load()
          }
      }
  })
   //置顶
   $("section").on("scroll",function(){
      var top=$("section").scrollTop()
      if(top>3000){
          $(".gotop").show()
          $(".gotop").click(function(){
              $("section").scrollTop(0)
          })
      }else{
          $(".gotop").hide()
      }
  });
   /*搜索框点击事件*/
   $(".search_box").click(function(){
  	 window.location.href=base_inf.base_herf+"html_member/gSSS.do?city_name="+base_inf.city_name+"&area_name="+base_inf.area_name+"&longitude="+base_inf.longitude+"&latitude="+base_inf.latitude;; 
   });
   //加载商家
   tongyong();
   //星级载入
   starload();
    /*加号*/
   var list_flag;
   $(".add_to").click(function(){
       add_li();
       if(list_flag){
           list_flag=0;
           $(".add_to_list").css("display","none")
       }else{
           list_flag=1;
           $(".add_to_list").css("display","block")
       }
   })
});