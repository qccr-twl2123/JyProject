$(function(){
	$('.d2_sp1').click(function(){
		$(this).addClass('d2_sp1_active').siblings().removeClass('d2_sp1_active');
	});
	
	/*$(".yes").click(function(){
		
		var content= $("#open").text();  
		var threeminRate = $('.d3_set1 option:selected').val();
		var threemaxRate = $('.d3_set2 option:selected').val();
		var grantrule = "最高"+threemaxRate+"%最低"+threeminRate+"%";
		$.ajax({
			type:"post",
			url:"../../zhsh/storepc_scoreway/save.do",
			data:"content="+content+"&threemin_rate="+threeminRate+"&threemax_rate="+threemaxRate+"grantrule"+grantrule,
			data:{content:content,threemin_rate:threeminRate,threemax_rate:threemaxRate,grantrule:grantrule,store_id:"${storepd.store_id}"},
			success:function(datas){
				
			}
		});
		
		$('.d3_set1').val("-1");
		$('.d3_set2').val("-1");
		
	});*/
});
    
   
