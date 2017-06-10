/**
 * Created by Administrator on 2017/5/3.
 */
var base_inf={
    base_href:"https://www.jiuyuvip.com/",
    province_name:"浙江省",
    city_name:"杭州市",
    area_name:"萧山区",
    lng:"",
    lat:"",
    flag:"0",
    content:""
};
//首页地址
var zhuye_href="http://localhost/jiuyupc/shouyi.html";
var text;
function strToJson(str){
    return JSON.parse(str);
}
function golink(link,fun){
    var go_link=link;
    $.ajax({
        //url:go_link,
        url:'phpRoot/proxy.php?url='+go_link,
        type:'post',
        cache:false,
        beforeSend :function(xmlHttp){
            xmlHttp.setRequestHeader("If-Modified-Since","0");
            xmlHttp.setRequestHeader("Cache-Control","no-cache");
        },
        success:fun
    })
}
//回到首页
function goindex(){
    window.location=zhuye_href
}

//百度api提供ip定位
function gogogo2(){
    $.ajax({
        type:"post",
        url:"https://api.map.baidu.com/highacciploc/v1?qcip=&callback_type=jsonp&qterm=pc&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&coord=bd09ll",
        dataType:"jsonp",
        success: function(data){
            var result=data.result;
            var content=data.content;
            if(result.error == 161){
                pcd(content.location.lng,content.location.lat);
            }
        }
    });
}
//经纬度转位置信息
function pcd(lng,lat){
    $.ajax({
        type:"post",
        url:"https://api.map.baidu.com/geocoder/v2/?ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&callback=renderReverse&location="+lat+","+lng+"&output=json&pois=1",
        dataType:"jsonp",
        success: function(data){
            if(data.status == "0"){
                var result=data.result;
                getShouyeList(result.addressComponent.province,result.addressComponent.city,result.addressComponent.district,lng,lat);
            }
        }
    });
}
//nav点击跳转
function checked(shaixuan,paixu){
    if(event.target.className=="act"){
        window.location.reload();
    }else if(event.target.innerText=="首页"){
        window.location=zhuye_href
    }else{
        base_inf.city_name=$("#loc_shi").val();
        base_inf.area_name=$("#loc_qu").val();
        base_inf.shaixuan=shaixuan;
        base_inf.paixu=paixu;
        base_inf.sort_name="全部分类";
        base_inf.flag="1";
        base_inf.content=$("#sj_search")[0].value;

        window.location=base_inf.base_href+"memberpc_shouye/stzall.do?area_name="+base_inf.area_name+"" +
            "&city_name="+base_inf.city_name+"&paixu="+base_inf.paixu+"&shaixuan="+base_inf.shaixuan+"" +
            "&content="+base_inf.content+"&flag=1"
    }

}
//首页加载
function getShouyeList(province_name,city_name,area_name,lng,lat){
    if(city_name==undefined){

    }else{
        base_inf.base_href="https://www.jiuyuvip.com/";
        base_inf.province_name=province_name;
        base_inf.city_name=city_name;
        base_inf.area_name=area_name;
        base_inf.lng=lng;
        base_inf.lat=lat;
    }

    var link=base_inf.base_href+"memberpc_shouye/ajaxpcMemberSy.do?province_name="+base_inf.province_name+"" +
        "&city_name="+base_inf.city_name+"&area_name="+base_inf.area_name+"&lng="+base_inf.lng+"&lat="+base_inf.lat+"&flag="+base_inf.flag;
    golink(link,index_inf);
    function index_inf(data){
        var sy_data=strToJson(data);
        var area=sy_data.areaList;
        var city=sy_data.cityList;
        console.log(sy_data);
        var loc_load = new Vue({
            el:".sel_box",
            data:{
                city_inf:city,
                area_inf:area,
                cityname:base_inf.city_name,
                areaname:base_inf.area_name
            },
            mounted:function(){
                //市区切换
                var nowdata=sy_data.pd;
                    $("#loc_shi")[0].value=nowdata.city_name?nowdata.city_name:base_inf.city_name;
                    $("#loc_qu")[0].value=nowdata.area_name?nowdata.area_name:base_inf.area_name;
                if( $("#loc_qu")[0].value==""){
                    $("#loc_qu")[0].value="请选择"
                }

                $("#loc_shi").change(function(){
                    base_inf.city_name=$("#loc_shi")[0].value;
                    $("body").html(text);
                    getShouyeList("",base_inf.city_name,"","","")
                })
                $("#loc_qu").change(function(){
                    base_inf.city_name=$("#loc_shi")[0].value;
                    base_inf.area_name=$("#loc_qu")[0].value;
                    $("body").html(text)
                    getShouyeList("",base_inf.city_name,base_inf.area_name,"","")
                })
            }
        });

        $("#loc_shi").value=base_inf.city_name;
        //顶部搜索
        var search = new Vue({
            el:".inp_box",
            data:{
                inp_val:""
            },
            methods:{
                search_click:function(){
                    window.location=base_inf.base_href+"memberpc_shouye/stzall.do?content="+ this.inp_val;
                }
            }
        });
        //轮播
        var banner_load = new Vue({
            el: ".banner",
            data: {
                data: sy_data.pcadvert
            },
            mounted:function(){
                //轮播构造
                var swiper = new Swiper('.swiper-container', {
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
        });

        //全部分类
        var   banner_list= new Vue({
            el: ".all_list",
            data: {
                list: sy_data.firstList,
                icon_list:{
                    "美食":"icon-meishi",
                    "酒店":"icon-jiudian1",
                    "休闲娱乐":"icon-baolingqiu1",
                    "丽人/美业":"icon-liren",
                    "家装建材":"icon-jiancai",
                    "亲子/娱乐":"icon-qinzi1",
                    "服装服饰":"icon-fushi2",
                    "商超购物":"icon-shangchao",
                    "品牌专卖":"icon-pinpai1",
                    "电器3C":"icon-dianqi",
                    "果蔬生鲜":"icon-shengxian2",
                    "周边游":"icon-gongnengzhoubianyou",
                    "医疗保健":"icon-unie61a",
                    "便民/杂货":"icon-bianmin2",
                    "生活服务":"icon-shenghuofuwu1"
                }
            },
            methods:{
                nav_mouseover:function(){
                    $(".xl_list").show()
                },
                li_mouseover:function(e){
                    var ele;
                    if($(e.target)[0].nodeName=="LI"){
                        ele=$(e.target)
                    }else{
                        ele=$(e.target).parents("li")
                    }
                    ele.find(".two_list").show();
                    ele.css("color","#e90000")
                },
                li_mouseleave:function(e){
                    var ele;
                    if($(e.target)[0].nodeName=="LI"){
                        ele=$(e.target)
                    }else{
                        ele=$(e.target).parents("li")
                    }
                    ele.find(".two_list").hide();
                    ele.css("color","#333")
                },
                nav_mouseleave:function(){
                    $(".xl_list").hide()
                },
                two_over:function(e){
                    $(e.target).parents(".two_list").parent("li").children(".item_color").css("color","#e90000")
                },
                two_leave:function(){
                    $(".xl_list .item_color").css("color","")
                },
                checkedByCitySort:function(id,type,name){
                    event.preventDefault()
                    base_inf.sort_id=id;
                    base_inf.sort_name=name;
                    base_inf.sort_type=type;
                    base_inf.city_name=$("#loc_shi").val();
                    base_inf.area_name=$("#loc_qu").val();
                    base_inf.shaixuan="";
                    base_inf.paixu="";
                    base_inf.flag="1";
                    base_inf.content=$("#sj_search")[0].value;
                    console.log(base_inf);
                    $(".alllist_tit").text(name);
                    window.location=base_inf.base_href+"memberpc_shouye/stzall.do?area_name="+base_inf.area_name+"" +
                            "&city_name="+base_inf.city_name+"&paixu="+base_inf.paixu+"&shaixuan="+base_inf.shaixuan+"" +
                            "&content="+base_inf.content+"&flag="+base_inf.flag+"&city_file_sort_id="+base_inf.sort_id+"" +
                            "&sort_name="+base_inf.sort_name+"&sort_type="+base_inf.sort_type
                }


    }
        })
        //商家列表
        var firlist = new Vue({
            el:".shangjialist",
            data:{
                first_list:sy_data.firstList
            },
            methods:{
                gostore:function(id){
                    window.location=base_inf.base_href+"memberpc/tzall.do?store_id="+id;
                },
                erjifenlei:function(id,name,type){
                    window.location=base_inf.base_href+"memberpc_shouye/stzall.do?city_file_sort_id="+id+"&sort_type="+type+"sort_name="+name;
                },
                list_hover:function(e){
                    var ele;
                    if($(e.target)[0].nodeName=="LI"){
                        ele=$(e.target)
                    }else{
                        ele=$(e.target).parents("li")
                    }
                    ele.css("box-shadow","0px 0px 8px #ccc")
                },
                list_leave:function(e){
                    var ele;
                    if($(e.target)[0].nodeName=="LI"){
                        ele=$(e.target)
                    }else{
                        ele=$(e.target).parents("li")
                    }
                    ele.css("box-shadow","")
                }
            },
            mounted:function(){    /*挂载后*/
                //星级评价
                $.fn.raty.defaults.path = 'js/library/lib/img';
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
        })

        //左导航
        var left_daohang = new Vue({
            el:".zuodaohang",
            data:{
                fenleilist:sy_data.firstList
            },
            methods:{
              gotop:function(){
                  $(window).scrollTop(1);
              }
            },
            mounted:function(){
                //左导航鼠标移入事件
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
        })
    }
}
//预加载
if (base_inf.area_name==""){
    gogogo2();
}else{
    getShouyeList()
}
$(function(){
    //二维码显示
    $(".show_ewm").last().parent().mouseenter(function(){
       $(".ewm_appdown").show()
    })
    $(".show_ewm").last().parent().mouseleave(function(){
        $(".ewm_appdown").hide()
    })

text=$("body").html()







    //按需加载
    //var floor=$("body");
    //console.log(floor);
    //var arr=[];
    //for (var z = 0; z < floor.length; z++) {
    //    arr.push(floor[z].offsetTop)
    //}
    //$("body").scrollTop=1;
    //$(window).on("scroll",function(){
    //        var wh=document.documentElement.clientHeight;
    //        for (var i = 0; i < floor.length; i++) {
    //            if(($("body").scrollTop+100)>arr[i]&&!floor[i].getAttribute("flag")){
    //                console.log(1);
    //                var imgs=$("img",floor[i]);
    //                for (var j = 0; j < imgs.length; j++) {
    //                    imgs[j].src=imgs[j].getAttribute("asrc")
    //                }
    //                floor[i].setAttribute("flag",true)
    //            }
    //        }
    //    }
    //)
})