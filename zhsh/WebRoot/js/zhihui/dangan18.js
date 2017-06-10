$(function(){
	 var bool = 0;
   var bool2 = 0;

        $('.ms').click(function(){
          if(bool == 0){
               $('.dangan16_d1_d3').css('display','none');
                bool = 1;
            }else{
               $('.dangan16_d1_d3').css('display','block');
              bool = 0;
           }
        });
         $('.xx').click(function(){
          if(bool2 == 0){
               $('.dangan16_d1_d4').css('display','none');
                bool2 = 1;
            }else{
               $('.dangan16_d1_d4').css('display','block');
              bool2 = 0;
           }
        });

})

    
   
