$(function(){
    $('.new_d1_sp2').click(function(){
    	$('.new_tc').css('display','block');
    	$('.new_ipt1').val("");
     });
     $('.new_d1_sp3').click(function(){
        $('.new_tc3').css('display','block');
    });
     $('.new_yes2').click(function(){
        $(this).parent().parent().css('display','none');
        $('.new_tc4').css('display','block');
    })
      $('.new_yes3').click(function(){
        $(this).parent().parent().css('display','none');
    })
    $('.new_sp1').click(function(){
    	$(this).parent().parent().css('display','none');

    })
 });

    
   
