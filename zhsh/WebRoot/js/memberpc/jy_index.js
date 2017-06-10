
//轮播加载
function lunboload(){
    var swiper = new Swiper('.sw_banner', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev',
        loop:true
    });
    //轮播图按钮
    $(".swiper_view").mouseenter(function(){
        $(".swiper_btn").show()
    })
    $(".swiper_view").mouseleave(function(){
        $(".swiper_btn").hide()
    })
}
//左边展示
function incon(){
	var icon_list={
	        "美食":{icon:"icon-meishi",style:"line-height: 38px;",name:"美食"},
	        "酒店":{icon:"icon-jiudian1",style:"line-height: 38px;",name:"酒店"},
	        "休闲娱乐":{icon:"icon-baolingqiu1",name:"休闲娱乐"},
	        "丽人/美业":{icon:"icon-liren",name:"丽人美业"},
	        "家装建材":{icon:"icon-jiancai",name:"家装建材"},
	        "亲子/孕婴":{icon:"icon-qinzi3",name:"亲子/孕婴"},
	        "亲子/娱乐":{icon:"icon-qinzi3",name:"亲子/娱乐"},
	        "服装服饰":{icon:"icon-fushi2",name:"服装服饰"},
	        "商超购物":{icon:"icon-shangchao",name:"商超购物"},
	        "品牌专卖":{icon:"icon-pinpai1",name:"品牌专卖"},
	        "电器3C":{icon:"icon-dianqi",name:"电器3C"},
	        "果蔬生鲜":{icon:"icon-shengxian2",name:"果蔬生鲜"},
	        "周边游":{icon:"icon-gongnengzhoubianyou",name:"周边游"},
	        "医药保健":{icon:"icon-yiyaoxiang",name:"医药保健"},
	        "医疗保健":{icon:"icon-yiyaoxiang",name:"医疗保健"},
	        "便民/杂货":{icon:"icon-bianmin2",name:"便民杂货"},
	        "生活服务":{icon:"icon-shenghuofuwu1",name:"生活服务"}
	 }
	 $(".lefticon").each(function(){
		 var nowtext=$(this).html();
		 for(var j in icon_list){
			 if(j == nowtext){
				 $(this).attr("style",icon_list[j].style);
				 $(this).prev().addClass(icon_list[j].icon);
 	         }
		 }
 	 });
 	$(".zuodaohang div").mouseenter(function(){
        $(this).css({"color":"#fff","background":"#ff5f63"});
        $(this).children(".dh_title").css("opacity",0);
        $(this).children(".dh_icon").css("opacity",1)
    })
    $(".zuodaohang div").mouseleave(function(){
        $(this).css({"color":"#bbb","background":"#fff"});
        $(this).children(".dh_title").css("opacity",1);
        $(this).children(".dh_icon").css("opacity",0)
    })
    var daohang_item=$(".zuodaohang").find("a")
    for(var i=0; i<daohang_item.length-1;i++){
        daohang_item[i].top=$($(".list_item").children("div")[i]).offset().top;
     }
    $(window).on("scroll",function(){
        for(var i=0; i<daohang_item.length-1;i++) {
            daohang_item[i].num=i
            if ($("body")[0].scrollTop > daohang_item[i].top -200) {
                for(var j=0;j<daohang_item.length-1;j++){
                    $(daohang_item[j]).find(".dh_title").css("opacity",1);
                    $(daohang_item[j]).find(".dh_icon").css("opacity",0);
                    $(daohang_item[j]).find(".daohang_item").css({"color":"#bbb","background":"#fff"});
                }
                $(daohang_item[i]).find(".dh_title").css("opacity",0);
                $(daohang_item[i]).find(".dh_icon").css("opacity",1);
                $(daohang_item[i]).find(".daohang_item").css({"color":"#fff","background":"#ff5f63"});
            }
        }
    });
    //左导航载入
    var dh=document.getElementsByClassName('zuodaohang')[0];
    var kg1=true;
    var kg2=true;
    if (document.documentElement.scrollTop==1) {
        var scrolltopobj=document.documentElement;
    }else{
        var scrolltopobj=document.body
    }
    // 到固定位置显示
    $(window).on("scroll",xianshi);
    function xianshi(){
        if (scrolltopobj.scrollTop>600){    /*显示和隐藏点的位置*/
            if (kg1) {
                kg1=false;
                kg2=true;
                $(dh).animate({opacity:1},200)
            }
        }else {
            if (kg2) {
                kg1=true ;
                kg2=false;
                $(dh).animate({opacity:0},200)
            }
        }}
    document.documentElement.scrollTop=1;
    if (document.documentElement.scrollTop==1) {
        var scrolltopobj=document.documentElement;
    }else{
        var scrolltopobj=document.body
    }
 }
 
//icon点击事件
function atoscroll(ele){
    $("body").scrollTop(ele.top)
}

//星级载入
function xjzr(){
    $.fn.raty.defaults.path = 'js/memberpc/library/lib/img';
    for (var i=0;i<$(".star_box").length;i++){
        var star_id= $($(".star_box")[i]).attr("id");
        var star_num=$($(".star_box")[i]).attr("str_num");
        if($("#"+star_id).children().length>0){
            $("#"+star_id).html("");
            $("#"+star_id).raty({
                num :5,
                readOnly: true,
                halfShow: true,
                score   : star_num,
                hints   : star_num+"分",   /*鼠标移入是显示分值*/
                noRatedMsg:"暂无评分"       /*鼠标移入  无评分时*/
            });
        }else{
            $("#"+star_id).raty({
                num :5,
                readOnly: true,
                halfShow: true,
                score   : star_num,
                hints   : star_num+"分",
                noRatedMsg:"暂无评分"
            });
        }
    }
}
//商家展示的效果
function list_hover(e){
    var ele;
    if($(e)[0].nodeName=="LI"){
        ele=$(e)
    }else{
        ele=$(e).parents("li")
    }
    ele.css("box-shadow","0px 0px 8px #ccc")
}
function list_leave(e){
    var ele;
    if($(e)[0].nodeName=="LI"){
        ele=$(e)
    }else{
        ele=$(e).parents("li")
    }
    ele.css("box-shadow","")
}



//前往商家详情页
function gostore(ele){
    window.location=base_inf.base_href+"memberpc/storeInfoList.do?city_name="+$("#loc_shi option:selected").text()+"&area_name="+$("#loc_qu option:selected").text()+"&sk="+$(ele).attr("s_id")+"&sc=1&r_cl=0&timestamp="+new Date().getTime();
}


$(function() {
    lunboload();//轮播加载
    incon();//左边展示
    xjzr();//星级载入
 });
