/**
 * Created by Administrator on 2017/5/3.
 */
 function strToJson(str){
    return str;
}
function golink(link,fun){
    var go_link=link;
    $.ajax({
        //url:go_link,
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
function list_load() {
    var link = base_inf.base_herf+"app_order/list.do?member_id=" + base_inf.member_id;
    golink(link, load);
    function load(data) {
        var data = strToJson(data).data;
        new Vue({
            el: ".change",
            data: {
                data: data,
                member_id: base_inf.member_id
            },
            methods: {
                pj_click: function (order_id, store_id, member_id) {
                    window.location.href = base_inf.base_herf +'html_member/goMyOrderAddComment.do?order_id=' + order_id + '&store_id=' + store_id + '&member_id=' + base_inf.member_id;
                },
                go_store:function(store_id,member_id){
                    window.location.href = base_inf.base_herf + 'html_member/goStoreDetail.do?store_id=' + store_id + '&member_id=' + base_inf.member_id;
                },
                dd_del:function(order_id){
                    $.ajax({
                        type:"post",
                        url:base_inf.base_herf + 'app_order/delete.do',
                        data:{
                            "order_id":order_id
                        },
                        dataType:"json",
                        success: function(data1){
                            if(data1.result == "1"){
                                var aa= $("#"+order_id);
                                aa.fadeOut("normal");
                            }
                        }
                    });
                }
            }
        })
    }
}
list_load();


$(function(){
//        视区高度改变
    function HeiChange(){
        var hei=$("body").height();
        var height=0;
        for (var i=0;i<$("body").children(".guding").length;i++){
            height=Number($($("body").children(".guding")[i]).height())+height
        }
        $(".change").height(hei-height);
        $(".change").css("overflow","scroll")
    }
    HeiChange();
var top=new Vue({
    el:".guding",
    data:{
        title:"我的订单",
        dd_url:base_inf.base_herf + "html_me/goMe.do?type=79"
    }
})
})