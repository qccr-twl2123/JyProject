/**
 * Created by Administrator on 2017/5/21.
 */
$(function(){
    var galleryTop = new Swiper('.gallery-top', {
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev',
        spaceBetween: 10,
    });
    var galleryThumbs = new Swiper('.gallery-thumbs', {
        spaceBetween: 10,
        centeredSlides: true,
        slidesPerView: 'auto',
        touchRatio: 0.2,
        slideToClickedSlide: true
    });
    galleryTop.params.control = galleryThumbs;
    galleryThumbs.params.control = galleryTop;

    xjzr();
    
  //星级载入
    function xjzr(){
        $.fn.raty.defaults.path = 'js/memberpc/library/lib/img';
        for (var i=0;i<$(".star_box").length;i++){
            var star_id= $($(".star_box")[i]).attr("id");
            var star_num=$($(".star_box")[i]).attr("str_num");
            $($("#"+star_id)).raty({
                num :5,
                readOnly: true,
                halfShow: true,
                score   : star_num,
                hints   : star_num+"分",   /*鼠标移入是显示分值*/
                noRatedMsg:"暂无评分"       /*鼠标移入  无评分时*/
            });
        }
    }
    
})
    
    
