// JavaScript Document
$(function(){
	$(".xzdz-radio").click(function(){
		$(this).addClass("xzdz-radio-hover").parent().siblings().children("i").removeClass("xzdz-radio-hover")
	})
	$(".pay_list_c1").on("click",function(){
		if ($(this).is('.on')) {
			$(this).removeClass("on");
		}else{
			 $(this).addClass("on");
			 $(this).parent().siblings().children().removeClass("on");
		};
	})
	$(".input-tj").click(function(){
		$(".tjsj-forms").css("display","block")
	})
	$(".close").click(function(){
		$(this).parent().css("display","none")
	});
	$(".gx").click(function(){
		if ($(this).hasClass("gx-on")) {
			$(this).removeClass("gx-on")
		}else{
			$(this).addClass("gx-on")
		}
	})
	//双击效果
	$(".sc-edit").toggle(function(){
	    $(this).text("取消编辑");
	    $(".gx").css("display","block")
	    $(".sc-delet").css("display","block")
	    },function(){
	    $(this).text("编辑");
	    $(".gx").css("display","none")
	    $(".sc-delet").css("display","none")
	});

	$(".recharge-sure").click(function(){
		$("i").each(function(index) {
			if($(this).hasClass("gx-on")){
					$(this).parent().parent().remove();
				}
		})
	})
	$(".sp-sc").on("click",function(){
		if ($(this).is('.sp-sc-on')) {
			$(this).removeClass("sp-sc-on");
		}else{
			 $(this).addClass("sp-sc-on");
		};
	})
	$(".order-title i").click(function(){
		$(this).parent().css("display","none")
	});

	$(".index-title >ul >li").click(function(){
		/*$(this).children("div").addClass("triangle-up").parent().siblings().children("div").removeClass("triangle-up")	*/	
	    /*$(this).addClass("index-current").siblings().removeClass("index-current");*/
		/*$(".all:eq("+$(this).index(".index-title >ul >li")+")").show().siblings().hide();	*/
		return false;			
	});
	$(".zhxx-tx-anniu").click(function(){
		$(".bj").css("display","block")
	})


	$(" body").click(function(){

　　      $(".all").hide();

	});

	$(".all").click(function(e){//自己要阻止

	　　e.stopPropagation();//阻止冒泡到body

	});




})