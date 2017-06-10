$(function(){
	$('.d2_sp1').click(function(){
		$(this).addClass('d2_sp1_active').siblings().removeClass('d2_sp1_active');
	})
	
/*$(".yes").click(function(){
		
		var content = $("#open").text();  
		alert($(this).attr("a"));
		var twoproductType = $(this).prev(".d3_set1 option:selected").val();
		
		alert(twoproductType+content);
		$.ajax({
			type:"post",
			url:"../../storepc_scoreway/save.do",
			data:"content="+content+"&twoproductType="+twoproductType,
			success:function(data){
				
			}
		});
		//$('.d3_set1').val("-1");
		
	});*/
});
    
   
