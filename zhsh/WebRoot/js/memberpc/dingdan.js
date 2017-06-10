/**
 * Created by Administrator on 2017/5/17.
 */

//返回顶部
function gotop(){
    $(window).scrollTop(1);
}



$(function(){
    
//订单详情判断放在订单加载完成后
    for (var i = 0;i<$(".sp_sq").length;i++){
        var length=$($(".sp_sq")[i]).children("b").length;
        if(length<3){
            $($(".sp_sq")[i]).css({"line-height":"100px"})
        }else if(length<6){
            $($(".sp_sq")[i]).css({"line-height":"50px","height":"100px;"})
        }else if(length<9){
            $($(".sp_sq")[i]).css({"line-height":"33px","height":"100px;"})
        }else{
            $($(".sp_sq")[i]).css({"line-height":"33px","height":"100px;", "white-space":"pre-wrap","overflow":"hidden","text-overflow":"ellipsis"})
        }
    }
   
    
    
//滚动到底部时自动加载
//加载放在console.log(1111)
    var flag=0;
    $(window).on("scroll",function(){
        var scrTop=$("body").scrollTop()
        var allheight=document.body.scrollHeight;
        var height=(allheight-$("footer")[0].scrollHeight)-$(window).height();
        if(scrTop>height){
            if (flag==0){

            }
            flag=1;
        }
        if (scrTop<height){
            flag=0;
        }


        //回到顶部按钮显示
        if(scrTop>($(window).height()-100)){
            $(".gotop").css("opacity","1")
        }else{
            $(".gotop").css("opacity","0")
        }
    })

    $(".gotop").mouseover(function(){
        $(".dh_icon").css("opacity","0");
        $(".dh_title").css("opacity","1");
    })
    $(".gotop").mouseleave(function(){
        $(".dh_icon").css("opacity","1");
        $(".dh_title").css("opacity","0");

    })

});