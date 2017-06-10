$(function(){
	var $table=$('table tr').length;
	for(var i=0;i<=$table;i++){
            if(i%2==0){
                $('table tr:eq('+i+')').addClass('active_bg');
            }else if(i%2!=0){
                $('table tr:eq('+i+')').removeClass('active_bg');
            }
        };
})

    
   
