$(function(){
	 var bool = 0;
        $('.weixin_right').click(function(){
          if(bool == 0){
               $('.content').css('display','block');
                bool = 1;
            }else{
               $('.content').css('display','none');
              bool = 0;
           }
        });
         
});
    
   
