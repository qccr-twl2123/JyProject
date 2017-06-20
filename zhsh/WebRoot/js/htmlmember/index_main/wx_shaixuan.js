 
	function adddrag(){
        touch.on('.sx_click', 'touchstart', function(ev){
        });
        var dx, dy;
        touch.on('.sx_click', 'drag', function(ev){
            var offy = ev.y + "px";
            if($(ev.srcElement)[0].className=="sx_click"){
//        大于零  向下拉
//        小于零 向上拉
//            此处为禁止下拉效果显示
                if (ev.y>0){
                    offy=0
                }
                 this.style.webkitTransform = "translate3d( 0," + offy + ",0)";
            }else{
                this.style.webkitTransform = "translate3d( 0,0,0)";
            }
        });
        touch.on('.sx_click', 'dragend', function(ev){
            if(ev.y<-100){
                $(".sx_box_list_ul").hide();
                $(".sx_click").css("height","40px");
                $(".arrow").attr("class","arrow");
            }
            dx = 0;
            dy = 0;
            this.style.webkitTransform = "translate3d( 0,0,0)";
        });
    }
    adddrag();
    
     
    //隐藏一级分类
    function hideul(){
        $(".paixu_ul").css({"display":"none","z-index":"1"})
        $(".all_list_ul").css({"display":"none","z-index":"1"});
        $(".show_ul").css({"display":"none","z-index":"1"});
        //小三角改变
    }
    function spanhide(){
        $(".show_nav li").children(".arrow").attr({"class":"arrow  arrow_up","flag":"0"});
        $(".sx_box_list_ul").hide();
    }
    /*内部显示区大小改变*/
    var sec_height;
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
   
    
    
    
    
	//下拉加载更多
	function xl_load(){
	   base_inf.nowpage=parseInt(base_inf.nowpage)+1;
	   tongyong();
	}


  //一级分类点击
  function oneclick(obj){
  	 event.stopPropagation();
  	 $(".all_list_ul_2").hide();
  	 $(obj).find(".all_list_ul_2").show();
  }

  //二级分类点击
  function twoclick(obj){
  	$("#cfsivalue").val($(obj).attr("sort_id"));
  	$("#stvalue").val($(obj).attr("sort_type"));
  	$("#snvalue").val($(obj).attr("sort_name"));
   	$(".flsx").html($(obj).attr("sort_name"));
   	base_inf.nowpage=1; 
    tongyong();
  }

//通过方法
function tongyong(){
	 spanhide();//箭头为关闭状态
 	var url=base_inf.base_herf+"html_member/gSLPage.do?city_file_sort_id="+$("#cfsivalue").val()+"&sort_type="+$("#stvalue").val()+"&sort_name="+
 	$("#snvalue").val()+"&paixu="+$("#pxvalue").val()+"&shaixuan="+$("#sxvalue").val()+"&nowpage="+base_inf.nowpage+
 	"&city_name="+base_inf.city_name+"&area_name="+base_inf.area_name+"&longitude="+base_inf.longitude+"&latitude="+base_inf.latitude;
 	golink(url,shangjiazairu);
}

//上拉加载
var load_flag=0 

//商家载入;
function shangjiazairu(data) {
	 load_flag=0;
 	 var storelist=data.storeList;
	 if(base_inf.nowpage == "1"){
		 $(".sj_list").find("li").remove();
		 $(".sj_list").scrollTop(0);
		 $(".load").show();
	 }
 	 if(parseInt(base_inf.nowpage) >1 && storelist.length == 0){
		 base_inf.nowpage=parseInt(base_inf.nowpage)-1;
		 $(".load").hide();
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
			        "        <ul class='sj_jf_inf'>"+makstr
 			        "        </ul>"+
			        "    </div>"+redstr+
			        "</li>";
 		  $(".sj_list").append(thistorestr);
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
    var star= $(".star")
    $.fn.raty.defaults.path = 'js/htmlmember/star/img';   /*星级图片 地址*/
    for(var i = 0; i<star.length;i++ ){
        var str=$(star[i]).attr("id");
        var num=$(star[i]).attr("starnum")
        $("#"+str).raty({ readOnly:true,score:num,half:true,space:false});
    }
}




$(function(){
    //筛选条显示隐藏
    $(".show_box_click").click(function(){
        $(".sx_click").show();
        $(".sx_box_list_ul").css({"display":"block"})
    });

    heightchange();
    //筛选点击事件
    $(".show_box_click li").click(function(){
        var name= $(this).attr("toname");
        $("li[liname="+name+"]")[0].click()
    });
     $(".show_nav li").click(function(e){
        var ele;
        if(e.target.tagName=="SPAN"){
            ele= $(e.target).parent("li")
        }else{
            ele=$(e.target)
        }
        var  str=ele.attr("liname")+"_ul";
        if(ele.children(".arrow").attr("flag")=="1"){
            $(".sx_box_list_ul").css({"display":"block","z-index":"1","height":sec_height})
            hideul();
            spanhide();
         }else{
            hideul();
            spanhide();
            $(ele).children(".arrow").attr({"class":"arrow arrow_down","flag":"1"})
            $("."+str).css({"height":sec_height,"display":"block","z-index":"2"});
            $(".sx_box_list_ul").css({"display":"block"})
         }
        if(ele.children()[0].tagName=="SELECT"){
            $(".sx_box_list_ul").hide()
        }
        $(".all_list_ul").css({"overflow-y":"scroll","height":sec_height-40})
        $(".all_list_ul_2").css({"overflow-y":"scroll","height":sec_height-40})
    });

    $("section").on("scroll",function(){
        if(($(".load").offset().top-80)<$("section").height()){
            if(load_flag == 0){
                load_flag=1
                xl_load();
            }
        }
    })
    //加载商家
    tongyong();
    //星级初始化
    starload();
});


function goStoreDetail(obj){
 	window.location.href=base_inf.base_herf+"html_member/goStoreDetail.do?sk_shop="+$(obj).attr("sk_shop");
}
