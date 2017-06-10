$(function(){
    $('.nav dl dd').click(function(){
    	$(this).addClass('nav_active').siblings().removeClass('nav_active');
    })
    $('.middle_a').click(function(){
    	$(this).find('dd').addClass('signin_body_left_active');
      	$(this).siblings().find('dd').removeClass('signin_body_left_active');
    })
     $('.middle_b').click(function(){
        $(this).find('dd').addClass('signin_body_left_active2');
        $(this).siblings().find('dd').removeClass('signin_body_left_active2');
    })

    $('.nav dd').click(function(){
    	$($(this).attr('data-id')).css('display','block');
    	$($(this).attr('data-id')).siblings('#homepage,#marketing,#shop,#base,#account').css('display','none');

    })
    $('#weixin').click(function(){
    	$('.signin_body').css('display','none');
    	$('.signin_body_weiixn').css('display','block');
    })
    $('.ddclick').click(function(){
    	$('.signin_body').css('display','block');
    	$('.signin_body_weiixn').css('display','none');
    })
    $('#marketing dd').click(function(){
        // console.log(111);
    })

    // 沟通
    $('.weixin_img2').click(function(){
        $('.signin_body_weiixn_content').css('display','block');
         $('.signin_body_weiixn_content2').css('display','none');
          $('.signin_body_weiixn_content3').css('display','none');

        $('.img1 img').attr('src','img/weixin_msg2.png');
         $('.weixin_img3 img').attr('src','img/weixin_lx.png');
          $('.weixin_img4 img').attr('src','img/weixin_pp.png');  
    })
     $('.weixin_img3').click(function(){
        $('.signin_body_weiixn_content').css('display','none');
         $('.signin_body_weiixn_content2').css('display','block');
          $('.signin_body_weiixn_content3').css('display','none');

        $('.img1 img').attr('src','img/weixin_msg.png');
         $('.weixin_img3 img').attr('src','img/weixin_lx2.png');
          $('.weixin_img4 img').attr('src','img/weixin_pp.png');    
    })
      $('.weixin_img4').click(function(){
        $('.signin_body_weiixn_content').css('display','none');
         $('.signin_body_weiixn_content2').css('display','none');
          $('.signin_body_weiixn_content3').css('display','block');

        $('.img1 img').attr('src','img/weixin_msg.png');
         $('.weixin_img3 img').attr('src','img/weixin_lx.png');
          $('.weixin_img4 img').attr('src','img/weixin_pp2.png');  
    })
 });

    
   
