$(function(){
         $('.d3_click').click(function(){
                var src=$('.click_img').attr('src');
                src=src=='img/index_checked1.png'?'img/index_checked.png':'img/index_checked1.png';
                $('.click_img').attr('src',src);
            });
           
          $(window).scroll(function () {
                if($(document).scrollTop()>= 750){
                        $(".Buyers_left").show();
                        $(".Buyers_righst").show();

                    }else{
                        $(".Buyers_left").hide();
                        $(".Buyers_righst").hide();
                    };
                console.log($(document).scrollTop());
                    
                /* if ($(document).scrollTop()>=900) {
                    $('.Buyers_left  dd ').removeClass('dd_active');
                    $('.dd1').addClass('dd_active');
                }
                if ($(document).scrollTop()>=1500) {
                    $('.Buyers_left dd ').removeClass('dd_active');
                    $('.dd2').addClass('dd_active');
                }
                if ($(document).scrollTop()>=2100) {
                    $('.Buyers_left dd ').removeClass('dd_active');
                    $('.dd3').addClass('dd_active')
                }
                if ($(document).scrollTop()>=2700) {
                    $('.Buyers_left dd ').removeClass('dd_active');
                    $('.dd4').addClass('dd_active')
                }
                if ($(document).scrollTop()>=3300) {
                    $('.Buyers_left dd ').removeClass('dd_active');
                    $('.dd5').addClass('dd_active')
                }
                if ($(document).scrollTop()>=3600) {
                    $('.Buyers_left dd ').removeClass('dd_active');
                    $('.dd6').addClass('dd_active')
                }*/




                });
            $('.Buyers_gg_top_sp3').click(function(){
                $(this).parents('.Buyers_gg').hide();
            });
       $('.Buyers_left dd').click(function(){
         $(this).addClass('dd_active');
         $(this).parent().siblings().find('dd').removeClass('dd_active');
       })     

        
})

    
   
