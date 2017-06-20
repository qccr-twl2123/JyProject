//ajax处理
function golink(go_link,fun){
     $.ajax({
        url:go_link,
        type:'post',
        cache:false,
        beforeSend :function(xmlHttp){
            xmlHttp.setRequestHeader("If-Modified-Since","0");
            xmlHttp.setRequestHeader("Cache-Control","no-cache");
        },
        success:fun
    })
}

//商家信息
function sj_inf(){
    var go_link=base_inf.base_herf+"app_store/findById.do?sk_shop="+base_inf.sk_shop;
    golink(go_link,load_sj_inf);
    function load_sj_inf(data){
        var data_inf=data.data;
        var daoliu_data=data_inf.daoLiuStoreList;
        //商家名称
        $(".sj_name").html(data_inf.store_name);
        //星级
        //$(".score").html(data_inf.comment_score)   评分
        $.fn.raty.defaults.path = 'js/htmlmember/star/img';   /*星级图片 地址*/
        $("#star").raty({ readOnly:true,score:data_inf.comment_score,half:true,space:false,size: 22});
        //商家推荐
         var sjtj_str="";
        for(var k in daoliu_data){
            var market=daoliu_data[k].marketlist;
            var markstr="";
            for(var n in market){
                var mark_type=market[n].marketing_type;
                var grantrule=market[n].grantrule;
                if(mark_type == "1"){
                    markstr+="<span class='zeng tit_bg_box'>满赠</span>";
                }else if(mark_type == "2"){
                    markstr+="<span class='jian tit_bg_box'>立减</span>";
                }else if(mark_type == "3"){
                    markstr+="<span class='shi tit_bg_box'>时段</span>";
                }else if(mark_type == "4"){
                    markstr+="<span class='song tit_bg_box'>立减</span>";
                }else if(mark_type == "5"){
                    markstr+="<span class='song tit_bg_box'>累计</span>";
                }else if(mark_type == "6"){
                    markstr+="<span class='ji tit_bg_box'>积分</span>";
                }else if(mark_type == "7"){
                    markstr+="<span class='zhe tit_bg_box '>折扣</span>";
                }
            }
            sjtj_str ="<li sk_shop='"+daoliu_data[k].sk_shop+"' onclick='goStoreDetail(this)'><div class='sjtj_img_box'><img src='"+daoliu_data[k].pictrue_url+"' ></div><div class='sjtj_text ccyc'><span>'"+daoliu_data[k].store_name+"'</span>'"+markstr+"'</div><div class='sjtj_text ccyc'><span>'"+daoliu_data[k].sort_name+"'</span></div><div class='sjtj_text ccyc'><span id='star'"+daoliu_data[k].daoliurecord_id+"' starnum='"+daoliu_data[k].comment_score+"'></span><span>已售'"+daoliu_data[k].transaction_times+"'</span></div></li>";
            $(".sjtuijian").append(sjtj_str);
            var star_Str="#star"+daoliu_data[k].daoliurecord_id;
            $(star_Str).raty({ readOnly:true,score:daoliu_data[k].comment_score,half:true,space:false,size:10});
            var size=$("body").css("font-size");
            $(star_Str).find("img").width(size)
        }


        //商家图片
        $(".sj_img").attr("src",data_inf.pictrue_url);
        //购买方式
        var img_type_str="";
        for (var i in data_inf.way_typeList){
            if(data_inf.way_typeList[i].way_type=="1"){
                img_type_str+="<img src='img/ddxf.png'>"
            }
            if(data_inf.way_typeList[i].way_type=="2"){
                img_type_str+="<img src='img/wgzt.png'>"
            }
        }
        //详情
    	if(data_inf.iscollect != "0"){
    		$(".sp-sc").attr("src","images/sj_06.png"); 
    	}
        //支付方式
        var img_way_str="";
        for (var i in data_inf.way_statusList){
            if(data_inf.way_statusList[i].way_status=="1"){
                img_way_str+="<img src='img/zhifubao_sj.png'>"
            }
            if(data_inf.way_statusList[i].way_status=="2"){
                img_way_str+="<img src='img/wechat_sj.png'>"
            }
            if(data_inf.way_statusList[i].way_status=="3"){
                img_way_str+="<img src='img/pos_sj.png'>"
            }
            if(data_inf.way_statusList[i].way_status=="4"){
                img_way_str+="<img src='img/apple-pay_sj.png'>"
            }
        }

        var sj_yh_str="<div>" +
            "<p>"+data_inf.minjfrate+"</p>" +
            "<p>赠送积分率</p>" +
            "</div>" +
            "<div>" +
            "<p>"+data_inf.minzkrate+"</p>" +
            "<p>折扣</p>" +
            "</div>" +
            "<div>" +
            "<p>"+img_type_str+"</p>" +
            "<p>购买方式</p>" +
            "</div>" +
            "<div>" +
            "<p>"+img_way_str+"</p>" +
            "<p>支付方式</p>" +
            "</div>";
        $(".youhui_box").append(sj_yh_str);
        //支付方式  个数   放大图片
        if(img_type_str.length<25){
            $(".youhui_box div:nth-child(3) img").css("width","65%")
            $($(".youhui_box div:nth-child(3) p")[0]).css("line-height","0")
        }
        //地址
        var addres_str=$(".sj_address span").html()+data_inf.address;
        //电话
        var sj_phone="tel:"+data_inf.phone_bymemeber;
        $(".sj_address span").text(addres_str);
        $(".sj_phone img").attr("src","img/dianhua.png")
        $(".sj_phone").attr("href",sj_phone);
        $(".sj_gg").text(data_inf.notice_information);
        //营销
        var market=data_inf.marketlist
        var onemarkstr="";
        for(var m in market){
            var onemark_type=market[m].marketing_type;
            var onegrantrule=market[m].grantrule;
            if(onemark_type == "1"){
                onemarkstr+="<p><span class='zeng tit_bg_box' style='margin:0;'>满赠</span>";
            }else if(onemark_type == "2"){
                onemarkstr+="<p><span class='jian tit_bg_box' style='margin:0;'>立减</span>";
            }else if(onemark_type == "3"){
                onemarkstr+="<p><span class='shi tit_bg_box' style='margin:0;'>时段</span>";
            }else if(onemark_type == "4"){
                onemarkstr+="<p><span class='song tit_bg_box' style='margin:0;'>立减</span>";
            }else if(onemark_type == "5"){
                onemarkstr+="<p><span class='song tit_bg_box' style='margin:0;'>累计</span>";
            }else if(onemark_type == "6"){
                onemarkstr+="<p><span class='ji tit_bg_box' style='margin:0;'>积分</span>";
            }else if(onemark_type == "7"){
                onemarkstr+="<p><span class='zhe tit_bg_box ' style='margin:0;'>折扣</span>";
            }
            onemarkstr+=onegrantrule+"<p/>";
        }
        $(".sj_market").append(onemarkstr);
        //商家详情图
        if( data_inf.smallList.length==0){
        }else{
            var slide_str="";
            var num=(parseInt(data_inf.smallList.length/2));
            for (var i in data_inf.smallList){
                slide_str+="<div class='swiper-slide' style='background-image:url("+data_inf.smallList[i].small_url+")'></div>"
            }
            $(".sjxq_imgbox").html(slide_str);
            //详情轮播构建
            var swiper = new Swiper('.swiper-container', {
                pagination: '.swiper-pagination',
                effect: 'coverflow',
                grabCursor: true,
                centeredSlides: true,
                slidesPerView: 'auto',
                initialSlide :num,
                coverflow: {
                    rotate: 0,
                    stretch: 0,
                    depth: 240,
                    modifier: 1,
                    slideShadows : true
                }
            });
        }

        $(".ck_sjxq").click(function(){
            var xq_link=base_inf.base_herf+"html_member/goStoreInforByH5.do?sk_shop="+base_inf.sk_shop;
            $(".sj_xqimg a").attr("href",xq_link);
            $(".sj_xqimg a")[0].click();
        })

        $(".sj_jyxkz").click(function(){
            var go_link=base_inf.base_herf+"html_member/goStoreImage.do?image_one="+data_inf.license_image_one+
            	"&image_two="+data_inf.license_image_two+"&image_three="+data_inf.license_image_three+"&image_four="+data_inf.license_image_four+
            	"&image_fix="+data_inf.license_image_fix;
            $(".sj_jyxkz a").attr("href",go_link);
            $(".sj_jyxkz a")[0].click();
        })
        $(".sj_yyzz").click(function(){
            var go_link=base_inf.base_herf+"html_member/goStoreImage.do?image_one="+data_inf.business_licenses_image_one+
            	"&image_two="+data_inf.business_licenses_image_two+"&image_three=&image_four=&image_fix="
            $(".sj_yyzz a").attr("href",go_link);
            $(".sj_yyzz a")[0].click();
        })

    }

}


//评价加载
function pingjia_load(){
        var pj_link=base_inf.base_herf+"app_comment/listAll.do?sk_shop="+base_inf.sk_shop;
        golink(pj_link,pj_list);
        //评价
        function pj_list(data){
            var pj_inf=data.data;
            $.fn.raty.defaults.path = 'js/htmlmember/star/img';
            $("#sj_pj_star").raty({ readOnly:true,score:pj_inf.score,half:true,space:false,size:80});
            //用户评价
            $(".zj_zhpf").text(pj_inf.score+"分")
            $(".pf_bfb").text("高于周边商家"+pj_inf.percent+"%");

            for (var i in pj_inf.commentList){
                var imgstr="";
                var huifu="";
                var pjstr="";
                if(pj_inf.commentList[i].comment_store){
                    pjstr="<p class='sj_hf'>店家回复："+pj_inf.commentList[i].comment_store+"</p></div></li>"
                }
                if (String(pj_inf.commentList[i].comment_store)){
                }else{
                    huifu="<div class='user-cont'>"+pj_inf.commentList[i].content+"</div>";
                }
                for (var j in pj_inf.commentList[i].imgList){
                    if (j>2){
                        return;
                    }
                    if(pj_inf.commentList[i].imgList[j].icon_url){
                        imgstr+="<span><img src='"+pj_inf.commentList[i].imgList[j].icon_url+"' ></span>"
                    }else{
                        return;
                    }
                }
                var pj_str="<li class='sj_user_pj_box clf'>" +
                    "<div class='pj_img'>" +
                    "<span><img src='"+pj_inf.commentList[i].image_url+"'></span>" +
                    "</div>" +
                    "<div class='pj_cont'>" +
                    "<p><span>"+pj_inf.commentList[i].phone+"</span><span>"+pj_inf.commentList[i].star_time+"</span></p>" +
                    "<p class='star_show'><span id='user_star_"+i+"' style='width: 100%;'></span></p>" +
                    "<div class='user-cont'>"+pj_inf.commentList[i].content+"</div>" +
                    "<div class='user_img clf'>" +
                    imgstr +
                    "</div>"+pjstr;
                $(".pingjia_cont").append(pj_str);

                var star_str="#user_star_"+i;
                $.fn.raty.defaults.path = 'js/htmlmember/star/img';
                $(star_str).raty({ readOnly:true,score:pj_inf.commentList[i].star_number,space:false,half:true});
            }

            //图片全屏预览
            $(".user_img img").mousedown(function(){
                var url=this.url;
                var show_box=document.createElement("div");
                var show_img=document.createElement("img");
                show_img.src=this.src;
                show_box.className="img_show_box";
                $(show_box).append(show_img);
                $("body").append(show_box);
                $(".img_show_box").click(function () {
                    $(".img_show_box").remove()
                })
            })
        }
    }


//商品列表
//获得商品列表及载入
function get_sp_list(){
    $(".list_box").children().remove();
    $(".sp_list_box").children().remove();
    var go_link=base_inf.base_herf+"app_goods/goStoreGoods.do?sk_shop="+base_inf.sk_shop;
    golink(go_link,sp_load)
    function sp_load(data){
         var inf=data.data;
        for (var i in inf){
            //侧栏列表
            var class_str="<li id='"+inf[i].id+"'>"+inf[i].name+"</li>";
            $(".list_box").append(class_str);
            $(".list_box").children()[0].click()    /*初始化事件:点击分类列表第一个*/
            var list=inf[i].list

            if(inf[i].sort =="1"){
                var hb_str="<ul class='hb_class'>";
                for (var j in list){
                    hb_str+="<li class='clf'>" +
                        "<div><img src='img/hongbao.png'></div>" +
                        "<div><p>"+list[j].money+list[j].redpackage_type_name+"</p><p>"+list[j].redpackage_content+"</p></div>" +
                        "<div><span class='get_hb' packet_id='"+list[j].store_redpackets_id+"'money='"+list[j].money+"'>领取红包</span></div>" +
                        "</li>"
                }
                hb_str+="</ul>";
                $(".sp_list_box").append(hb_str)
                //红包领取
                $(function(){
                    $(".get_hb").click(function(){
                        $(this).parents(".clf").slideUp(200).css({"opacity":"0"});
                        var redpacker_id=$(this).attr("packet_id");
                        var money=$(this).attr("money");
                        hd_lq(redpacker_id,money)
                    })
                })
            }else{
                if (list.length=="0"){
                    var sp_str="<ul class='sp_list' classid='"+inf[i].id+"'></ul>";
                    $(".sp_list_box").append(sp_str)
                }else{
                    var sp_str="<ul class='sp_list' classid='"+inf[i].id+"'>";
                    for (var j in list){
                        sp_str+="<li class='clf'good_id='list[j].goods_id'><div><img src='"+list[j].image_url+"'></div>" +
                            "<div><p>"+list[j].goods_name+"</p>" +
                            "<p><span class='xianjia' xj='"+list[j].retail_money+"'>"+list[j].retail_money+"/"+list[j].company+"</span>" +
                            "<span class='yuanjia'>"+list[j].market_money+"/"+list[j].company+"</span></p></div>" +
                            "<div><span class='jj_shangpin'><img src='img/jiajian.png'><span class='down'></span>" +
                            "<span class='num' goodid='"+list[j].goods_id+"'>0</span><span class='up'></span></span></div></li>"
                    }
                    sp_str+="</ul>";
                    $(".sp_list_box").append(sp_str)
                }
            }
            //列表对应滚动
            var ceng=$(".sp_list_box>ul");
            var btn=$(".list_box li");
            var doc=$(".sp_list_box");
            for (var i = 0; i < ceng.length; i++) {
                $(btn[i])[0].num=i
                for (var j = 0; j < ceng.length; j++) {
                    btn[j].onclick = function () {   /*侧栏点击时*/
                        var gao = ceng[this.num].offsetTop-80;
                        doc.animate({scrollTop: gao}, 600)
                        $(".list_box li").css("background","rgb(255,255,255)");   /*侧栏背景颜色*/
                        this.style.backgroundColor="#999";    /*修改侧栏子类  选中时的背景颜色*/
                    }
                }
            }
            $(".sp_list_box").scroll(function(){   /*商品滚动*/
                $(".list_box li").css("background","rgb(255,255,255)");    /*侧栏背景颜色同上*/
                var op=doc.scrollTop();
                for (var j = 0; j < ceng.length; j++) {
                    if(op>=ceng[j].offsetTop-80.01){
                        for (var i = 0; i < ceng.length; i++) {
                            btn[i].style.backgroundColor=""
                        }
                        btn[j].style.backgroundColor="#999";     /*颜色同上  也是选中时的背景颜色*/
                    }
                }
            })
        }
        //点击显示相关类别
        $(".sp_list").click(function(){
            var thisId=$(this).attr("classid")
            $(".list_box li").css("background","rgb(255,255,255)");
            $("[id="+thisId+"]").css("background","#999")
        });
        //件数加减事件
         function sameGoodsChange(goodid,num){
            var goodsid= $("[goodid="+goodid+"]");
                if(goodsid.length<2){
                return;
                }else{
                    goodsid.html(num)
                }
        }
        $(".down").click(function(){
        	
            var goods=$(this).next();
            var goodsid=goods.attr("goodid");
            var sp_id=$(this).parents(".jj_shangpin").children(".num").attr("goodid");
            if(goods.text()>0){
                goods.text(goods.text()-1);
                $(".allGoodsNum").text($(".allGoodsNum").text()-1);
                colorChange();
                reDoCash(goods,0);
                sameGoodsChange(goodsid,goods.text())
            }else{
                goods.text(0)
            }
            var goods_num=$(this).parents(".jj_shangpin").children(".num").html();
            goodsNum(goods_num,sp_id)
        })
        $(".up").click(function(){
            var sp_id=$(this).parents(".jj_shangpin").children(".num").attr("goodid");
            var goods=$(this).prev();
            var goodsid=goods.attr("goodid")
            goods.text(Number(goods.text())+1);
            $(".allGoodsNum").text(Number($(".allGoodsNum").text())+1);
            colorChange();

            reDoCash(goods,1);
            var goods_num=$(this).parents(".jj_shangpin").children(".num").html();
            sameGoodsChange(sp_id,goods_num);
            goodsNum(goods_num,sp_id)
        })

    }

}


//操作商品数量
function goodsNum(num,goods){
      var go_link=base_inf.base_herf+"app_goods/caozuoShopCar.do?caozuo_number="+num+"&goods_id="+goods;
    golink(go_link,numChange)
     function numChange(data){
        var message=data.message;
        var result=data.result;
        if (result==0){
            // var mess= confirm(message);
            // if (mess){
            //     //window.location.href=
            // }else{

            // }
        }
        if (result==1){

        }
    }
}

//红包领取    红包处理函数未测试
function hd_lq(redpacket_id,money){
	if(base_inf.member_id == ""){
		alert("请先前往登录");
		return ;
	}
    var go_link=base_inf.base_herf+"app_shouye/getRedPackage.do?store_redpackets_id="+redpacket_id+"&money="+money;
    golink(go_link,get_redpack)
    function get_redpack(data){
    	
    }
}
//件数颜色
function colorChange(){
    if(Number($(".allGoodsNum").text())>0){
        $(".allGoodsNum").parent().css("color","#f00")
    }else{
        $(".allGoodsNum").parent().css("color","#fff")
    }
}
//总价加减
function reDoCash(ele,flag){
    var cash=Number($(".allCash").html());
    var xj_cash=Number($(ele).parents("li").find(".xianjia").attr("xj"));
    if(flag){
        cash+=xj_cash
    }else{
        cash-=xj_cash
    }
    var num_fix=Math.round(cash*100)/100;
    $(".allCash").html(num_fix)
}
//vip卡片会员 验证
function hy_yz(){
     var go_link=base_inf.base_herf+"app_store/getVipForStore.do?sk_shop="+base_inf.sk_shop;
    golink(go_link,yz_hygz)
    function yz_hygz(data){
    	alert(data.message);
     }
}
//关注验证
function iscollect(e) {
     var go_link=base_inf.base_herf+"app_store/iscolloctByMS.do?sk_shop="+base_inf.sk_shop;
    golink(go_link, changeimg)
    function changeimg(data) {
         alert(data.message);
         if (data.message == "取消收藏成功") {
            $(".sp-sc").attr("src", "images/sj_05.png");
          } else if (data.message == "收藏成功") {
            $(".sp-sc").attr("src", "images/sj_06.png");
          }
    }
}
//返回上一级
function back_url(){
	window.location.href=base_inf.base_herf+"html_member/gouShouYe.do";
}

//前往结算页面
function jiesuan(){
 	var total = $(".allCash").text();
	if(total == 0.00 || total == 0 || total == 0.0){
		alert("您还没有选择你要购买的商品！");
		return ;
	}else {
		window.location.href=base_inf.base_herf+"html_member/goStoreGoodsOverBuy.do?sk_shop="+base_inf.sk_shop+"&paymoney="+total;
	}
}

function goStoreDetail(obj){
	 	window.location.href=base_inf.base_herf+"html_member/goStoreDetail.do?sk_shop="+$(obj).attr("sk_shop");
}


$(function(){
    //会员收藏 验证   事件绑定
    $(".vip").click(function(){
        hy_yz()
    });
    $(".guanzhu").click(function(e){
        iscollect(e);
    });
    var flag=[0,0,0];
    for (var i = 0 ; i < $(".sx_nav li").length;i++ ){
        $(".sx_nav li")[i].num=i
        $($(".sx_nav li")[i]).click(function(){
            	$(".viewbox").css("display","none");
            	$($(".viewbox")[this.num]).css("display","block");
                if(this.num==0){
                    if(flag[0]){
                    	
                    }else{
                    	get_sp_list();
                        var hei=$("body").height();
                        var height=0;
                        for (var i=0;i<2;i++){
                            height=Number($($("body").children(".guding")[i]).height())+height
                        }
                        $(".change").height(hei-height);
                        flag[0]=1
                    }
                }else if(this.num==1){
                    if(flag[1]){
                    	
                    }else{
                    	 sj_inf();
                        //设置显示区大小
                        var hei=$("body").height();
                        var height=0;
                        for (var i=0;i<$(".guding").length;i++){
                            height=Number($($(".guding")[i]).height())+height
                        }
                        $(".change").height(hei-height);
                        flag[1]=1
                    }
                }else if(this.num==2){
                    if(flag[2]){

                    }else{
                        pingjia_load()

                        var hei=$("body").height();
                        var height=0;
                        for (var i=0;i<2;i++){
                            height=Number($($("body").children(".guding")[i]).height())+height
                        }
                        $(".change").height(hei-height);
                        flag[2]=1
                    }
                }
        })
    }
    //项目数量 改变li宽度 并添加点击事件
    var len =$(".sx_nav").children().length;
    var wid=100/len;
    $(".sx_nav li").css({"width":wid+"%"});
    //下选中红线
    $(".sx_nav li").click(function() {
        $(".sx_nav li").attr("click","0").css("color","#333").children(".tit_dibu").css("display","none");
        $(".tit_dibu").css("display","none");
        $(this).attr("click", "1");
        $(this).css("color","#f00");
        $($("[click=1]").find(".tit_dibu")).css("display","block");
    });

  //初始化
  $(".sx_nav li")[0].click();


});
