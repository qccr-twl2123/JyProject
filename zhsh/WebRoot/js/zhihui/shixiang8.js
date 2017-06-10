$(function(){
	$('.shixiang8_d1_dl dd').click(function(){
		$(this).addClass('active_dd').siblings().removeClass('active_dd');
		$(this).find('.dl_text_sp2').css('color','#000');
		$(this).siblings().find('.dl_text_sp2').css('color','#ccc');

	})
	
})

    
   
