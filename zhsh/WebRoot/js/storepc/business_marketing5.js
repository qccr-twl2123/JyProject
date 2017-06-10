$(function(){
	$('.d2_sp1').click(function(){
		$(this).addClass('d2_sp1_active').siblings().removeClass('d2_sp1_active');
	});
	
/*	
	$(".yes").click(function(){
		
		var content= $("#open").text();  
		var fourbackintegralIntegral = $('.d3_set1 option:selected').val();
		
		var grantrule = "每单返积分"+fourbackintegralIntegral;
		
		alert(grantrule);
		$.ajax({
			type:"post",
			url:"../../zhsh/storepc_scoreway/save.do",
			data:"content="+content+"&fourbackintegral_integral="+fourbackintegralIntegral+"&grantrule="+grantrule,
			data:{content:content,fourbackintegral_integral:fourbackintegralIntegral,grantrule:grantrule},
			success:function(data){
				
			}
		});
		
		$('.d3_set1').val("-1");
		
	});*/
});
    
   
