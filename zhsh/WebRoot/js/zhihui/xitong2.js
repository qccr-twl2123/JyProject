$(function(){
	$('.huiyuan').click(function(){
		$('.rightnone2').css('display','none');
		$('.rightnone').css('display','block');

	})
	$('.shangjia').click(function(){
		$('.rightnone2').css('display','block');
		$('.rightnone').css('display','none');
	});
	$('.new_click').click(function(){
         $('.xitong2_d1').css('display','block');
         $('.xitong2_d2').css('display','none');
        })
	$('.xitong2_d1_d1_sp3').click( function(){
			$(this).parents('.xitong2_d3').css('display','none');
		}
		)
		
	//全选
	$('.xitong2_d1_st1_sp2').click(function(){
			var num=$(this).index();
			$(this).parents('.xitong2_d1_st1').siblings('.xitong2_no').find('.xitong2_d1_st2').find('.xitong2_d1_st1_sp2:eq('+(num-1)+')').find('input').prop('checked',$(this).find('input').prop('checked'));
 			if($(this).children("label").children("input").is(":checked") && $(this).children("label").children("span").html()== "全部"){
//				alert($(this).parents('.xitong2_d1_st1').siblings('.xitong2_no').find('.xitong2_d1_st2').find('.xitong2_d1_st1_sp2:eq('+(num-1)+')').html());
				$(this).parents('.xitong2_d1_st1').siblings('.xitong2_no').find('.xitong2_d1_st2').find('.xitong2_d1_st1_sp2:eq('+(num-1)+')').find('input').val("1");
			}else if(!$(this).children("label").children("input").is(":checked") && $(this).children("label").children("span").html()== "全部" ){
//				alert("fou");
				$(this).parents('.xitong2_d1_st1').siblings('.xitong2_no').find('.xitong2_d1_st2').find('.xitong2_d1_st1_sp2:eq('+(num-1)+')').find('input').val("0");
			}
  	});

   $('.xitong2_d1_st1_sp1').click(function(){
         	$(this).parents('.new_xitong2').siblings('.new_xitong2').find('.new_tb').attr('src','img/new_right.png');
         	$(this).parents('.new_xitong2').siblings('.new_xitong2').find('.xitong2_no').css('display','none');

         	if($(this).parents('.new_xitong2').find('.xitong2_no').css('display')=='block'){

         		$(this).parents('.new_xitong2').find('.xitong2_no').css('display','none');
         		$(this).find('.new_tb').attr('src','img/new_right.png');

         	}else if($(this).parents('.new_xitong2').find('.xitong2_no').css('display')=='none'){

         		$(this).parents('.new_xitong2').find('.xitong2_no').css('display','block');
         		$(this).find('.new_tb').attr('src','img/new_down.png');

         	}
         })
         
         //选中
        $('.xitong2_ipt1').click(function(){
  				if($(this).next().val() == "0"){
 					$(this).next().val("1");
 				}else{
 					$(this).next().val("0");
 				}
        });
 
})



    
   
