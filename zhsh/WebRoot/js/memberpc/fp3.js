//表单提交
 function tj(){
	 if(base_inf.x == ""){
			window.location.href=base_inf.base_href+"memberpc/gfp2.do";
			return;
	 }else{
		 if($("#newpassword").val() == $("#againpassword_lq").val()){
			 $("#Form").submit();
		 }else{
			 alert("密码不一致，请重新输入");
			 return;
		 }
	 }
 }
//步骤三
var fir_flag=0;
    $(function(){
        $(".step3_inp input").focus(function(e){
            var val=$(e.target).val()
            $(e.target).next(".tips").show();
            $(e.target).css({"border":"1px solid #e90000"})
            if($(e.target)[0].className=="inp1"){
                regtest(val,e);
            }
            if(fir_flag){
                regtest(val,e);
            }
        })
        $(".step3_inp input").keyup(function(e){
            if($(e.target)[0].className=="inp1"){
                var val=$(e.target).val()
               if(val.length<6){
                   $(e.target).next(".tips").children("img").attr("src","img/tips_10.png");
                   $(e.target).next(".tips").children(".tips_text").html("密码长度不足");
                   fir_flag=0;
               }else  if(val.length>32){
                   $(e.target).next(".tips").children("img").attr("src","img/tips_10.png");
                   $(e.target).next(".tips").children(".tips_text").html("密码长度过长");
                   fir_flag=0
               }else{
                   regtest(val,e)
               }
           }
            if($(e.target)[0].className=="inp2"){
                if(fir_flag){
                    console.log($(".inp2").val())
                    console.log($(".inp1").val())
                    if($(".inp2").val()==$(".inp1").val()){
                        $(e.target).next(".tips").children("img").attr("src","img/tips_06.png");
                        $(e.target).next(".tips").children(".tips_text").html("");
                        $(e.target).css({"border":"1px solid #29abfd"})
                    }else{
                        $(e.target).next(".tips").children("img").attr("src","img/tips_10.png");
                        $(e.target).next(".tips").children(".tips_text").html("两次密码不一致");
                    }
                }

            }
        })
        $(".step3_inp input").blur(function(e){
            var val=$(e.target).val();
            if(val.length==0){
                $(e.target).css({"border":"1px solid #333"});
                $(e.target).next(".tips").hide();
            }
            if($(e.target)[0].className=="inp1"){
                var val=$(e.target).val();
                regtest(val,e)
            }
            if($(e.target)[0].className=="inp2"){

            }

        })


    })


    function regtest(val,ele){
        if(val.length>5&&val.length<33){
            var reg=new RegExp('^[a-zA-Z\\w]{5,32}$')
            var flag=reg.test(val)
            if(flag){
                $(ele.target).next(".tips").children("img").attr("src","img/tips_06.png");
                $(ele.target).next(".tips").children(".tips_text").html("");
                $(ele.target).css({"border":"1px solid #29abfd"})
                fir_flag=1;
            }
        }
    }