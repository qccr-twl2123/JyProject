$(function(){
    $('.zhihui_body_left_d2').click(function(){
      $(this).addClass('left_active').siblings().removeClass('left_active');
      var text=$(this).find('.zhihui_body_left_d2_size').text();
       $('.dingweitext').text(text);
       $($(this).attr('data-id')).css('display','block');
        $($(this).attr('data-id')).siblings('#dangan,#zhifu,#yingxiao,#xitong,#baobiao,#shixiang').css('display','none');
        // $('.disnone').addClass('left_active2');
    })


     $('.middle_a').click(function(){
      $(this).find('.zhihui_body_right_middle_d1').addClass('left_active2');
      $(this).siblings().find('.zhihui_body_right_middle_d1').removeClass('left_active2');
      var text=$(this).find('div').find('div').text();
     $('.dingweitext2').text(text);
     
    })
    // 子公司档案点击切换（新增）
     $('.zhihui_body_right_contern_tj').click(function(){
	      $('.zhihui_body_right_contern').css('display','none');
	      $('.zhihui_body_right_ifra').css('display','block');
     })
     // 子公司档案点击切换（修改）
     $('.zhihui_body_right_contern_xg').click(function(){
    	 $('.zhihui_body_right_contern').css('display','none');
    	 $('.zhihui_body_right_ifra').css('display','block');
     })
     
 

     $('.demo').click(function(){
      $('.zhihui_body_right_contern').css('display','block');
      $('.zhihui_body_right_ifra').css('display','none');
     })

      // 其他档案点击切换
     $('.demo2').click(function(){
      $('.zhihui_body_right_contern').css('display','none');
      $('.zhihui_body_right_ifra').css('display','block');
     })

     // 主菜单点击切换iframe页面
      $('.zhuclick').click(function(){
      $('.zhihui_body_right_contern').css('display','none');
      $('.zhihui_body_right_ifra').css('display','block');
     })

      // 点击档案主菜单跳转第一条 添加active
      $('.danganclick').click(function(){
        $('.dingweitext2').text(' 子公司档案');
        $('.disnone').addClass('left_active2');
        $('.demo2').removeClass('left_active2');
      })
      // 点击支付主菜单跳转第一条 添加active

      $('.zhifuclick').click(function(){
        // $('.zhihui_body_right_middle_d1:eq(0)').addClass('left_active2');
        // $('.zhihui_body_right_middle_d1:gt(0)').removeClass('left_active2');
        $('.dingweitext2').text('支付记录');
        $('.disnone').addClass('left_active2');
        $('.demo2').removeClass('left_active2');
      })
       // 点击营销主菜单跳转第一条 添加active
      $('.yingxiaoclick').click(function(){
        $('.dingweitext2').text('城市营销参数');
        $('.disnone').addClass('left_active2');
        $('.demo2').removeClass('left_active2');
      })
       // 点击系统主菜单跳转第一条 添加active
      $('.xitongclick').click(function(){
        $('.dingweitext2').text(' 角色管理');
        $('.disnone').addClass('left_active2');
        $('.demo2').removeClass('left_active2');
      })
       // 点击报表主菜单跳转第一条 添加active
      $('.baobiaoclick').click(function(){
        $('.dingweitext2').text(' 报表');
        $('.disnone').addClass('left_active2');
        $('.demo2').removeClass('left_active2');
      })
      // 点击报表主菜单跳转第一条 添加active
      $('.shixiangclick').click(function(){
        $('.dingweitext2').text(' 未处理事项');
        $('.disnone').addClass('left_active2');
        $('.demo2').removeClass('left_active2');
      })

      // 提现管理点击出现导出按钮
      $("#tixian").click(function(){
        $('.daochu').css('display','block');

      })
       $('#tixian').parent().siblings().click(function(){
          $('.daochu').css('display','none');
        })
       // 点击保存并退出
       $('.bctc').click(function(){
          // $(this).css('display','none');
       })

       var $table=$('table tr').length;
      for(var i=0;i<=$table;i++){
            if(i%2==0){
                $('table tr:eq('+i+')').addClass('active_bg');
            }else if(i%2!=0){
                $('table tr:eq('+i+')').removeClass('active_bg');
            }
        };
       

 });

    
   
