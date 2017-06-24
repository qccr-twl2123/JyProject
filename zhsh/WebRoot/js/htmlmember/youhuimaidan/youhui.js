/**
 * Created by Administrator on 2017/4/6.
 */
//点击去支付  链接未加
//    点击在线支付按钮  显示当前  json数据
//    余额、积分判断由于本地服务起缓存  未测试
//    info内会添加一个名为data的json为输入消费额时返回数据中的data
var info={
    inf_user:{
        member_id:"jy15291033941b962",  /*用户ID
                                            jy15291033941b962*/
    },
    inf_pay:{
        store_id:"33391593",         /*商家ID*/
        desk_no:"03"                  /*桌号*/
    }
};
//支付时可能用到的值
//使用余额、积分已存入inf_zf_btn
var inf_zf_btn={
    member_id:"jy18601765560ikl5",  /*用户ID*/
    user_balance:"0",            /*使用余额*/
    user_integral:"0",           /*使用积分*/
    get_integral:"0",           /*赠送积分*/
    actual_money:"0",           /* 现金/第三方支付的总金钱*/
    redpackage_id:"",           /*redpackage_id*/
    store_redpackets_id:"",     /*store_redpackets_id*/
    store_id:"33391593",         /*商家ID*/
    pay_way:"",                  /*支付方式： alipay,wx*/
    sale_money:"0",              /*出售金额*/
    no_discount_money:"0",       /* 不优惠金额*/
    discount_money:"",          /* 减少的金额*/
    pay_sort_type:"1",           /* 1-总金额支付，2-分类支付*/
    desk_no:"03"                  /*桌号*/
};
//app_goods/allMoneyByOne.do
function toJson(data){
    var inf=JSON.parse(data);
    return inf
}
function golink(link,fun){
    var go_link=link;
    $.ajax({
        url:'phpRoot/proxy.php?url='+go_link,
        type:'post',
        cache:false,
        beforeSend :function(xmlHttp){
            xmlHttp.setRequestHeader("If-Modified-Since","0");
            xmlHttp.setRequestHeader("Cache-Control","no-cache");
        },
        success:fun
    })
}
//input输入格式化
   //保留两位小数
   //非数字出现  置零   0.00
   //可添加3位分隔符
   //不接收负值   出现负值是  去掉负号
function formatCurrency(num) {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
        num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    cents = num%100;
    num = Math.floor(num/100).toString();
    if(cents<10)
        cents = "0" + cents;
    //单引号中加3位分割符
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
        num = num.substring(0,num.length-(4*i+3))+''+
            num.substring(num.length-(4*i+3));
    return (((sign)?'':'') + num + '.' + cents);
}
//营销list加载
//data.yingxiaoList===data
function yxlist_load(data){
    for (var i in data){
        if (data[i]){
            var youhui_str="<li><span>"+data[i].content+"</span><span class='loc_r'>"+data[i].number+"</span></li>"
            $(".yh_list").append(youhui_str)
        }else{
            continue;
        }
    }
}
//查找桌子
function findtable(tableList){
    for ( var i in tableList){
        var tablenum=""
        if(tableList[i].table_name==info.inf_pay.desk_no){
            tablenum=tableList[i]
        }
        if(tablenum){
            return tablenum
        }
    }

}
//信息
function sj_inf(){
    var go_link="https://www.jiuyuvip.com/app_goods/allMoneyByOne.do?store_id="+info.inf_pay.store_id+
        "&member_id="+info.inf_user.member_id+"&paymoney="+ $(".inp_num")[0].value;
    golink(go_link,shangjiaxinxi);
    function shangjiaxinxi(data){
        var data= toJson(data).data;
        //对应的桌子
        info.inf_user.table= findtable(data.tableNumberList);
        //数据转存
        info.data=data;
        $(".shopname span").html(data.store_name);
        //$(".money").html(data.mpd.now_money);
        //$(".integral").html(data.mpd.now_integral);
        $(".paymoney").html(data.countpd.paymoney);
            if($(".yh_list").children().length){
                $(".yh_list").removeChild
                yxlist_load(data.yingxiaoLis)
            }else{
                yxlist_load(data.yingxiaoLis)
            }
    }
}
//积分及余额检查
function num_chack(num,ele){
    var ret_num=num
    if(($(".ye_input").children().length)&&($(".jf_input").children().length)){
        var yenum=$(".ye_input").children()[0].value;
        var jfnum=$(".jf_input").children()[0].value;
        if(Number(yenum)+Number(jfnum)>=info.data.countpd.paymoney) {
            if($(ele).attr("class")=="yeinp"){
               var chazhi=info.data.countpd.paymoney-$(".jf_input").children()[0].value
                if(num>=info.data.memberInfor.now_integral) {
                    ret_num=info.data.memberInfor.now_integral
                }else{
                    ret_num=num
                }
            }else{
                if(num>=info.data.memberInfor.now_money) {
                    ret_num=info.data.memberInfor.now_money
                }else{
                    ret_num=num
                }
            }
        }else{
            ret_num=num
        }
    }else  if($(".jf_input").children().length){
        if(num>=info.data.memberInfor.now_integral) {
            ret_num=info.data.memberInfor.now_integral
        }else{
            ret_num=num
        }
    }else if($(".ye_input").children().length){
        if(num>=info.data.memberInfor.now_money) {
            ret_num=info.data.memberInfor.now_money
        }else{
            ret_num=num
        }
    }
    return ret_num
}
$(function(){
    $(".table_name").text(info.inf_pay.desk_no);
    sj_inf();
//余额点击
//duigou2  为选中状态
    $(".ye_chose").click(function(){
        var ye_inp=document.createElement("input")
        ye_inp.className="yeinp"
        ye_inp.placeholder="请点击输入"
        if( $($(this).children("img")).attr("src")=="img/duigou.png"){
            //使用
            $(".ye_input").html("");
            $(".ye_input").append(ye_inp);
            $(".yeinp").blur(function(){
                var val=formatCurrency(this.value);
                var num= num_chack(val,this)
                $(".yeinp")[0].value=num;
                inf_zf_btn.user_balance=num
            });
            $(".yeinp").focus(function(){
                this.value="";
            });
            $($(this).children("img")).attr("src","img/duigou2.png")
        }else{
            //不使用
            $(".yeinp").remove();
            $(".ye_input").html("0.00");
            $($(this).children("img")).attr("src","img/duigou.png")
        }
    });
//积分点击
    $(".jf_chose").click(function(){
        var jf_inp=document.createElement("input");
        jf_inp.placeholder="请点击输入"
        jf_inp.className="jfinp";
        if( $($(this).children("img")).attr("src")=="img/duigou.png"){
            //使用
            $(".jf_input").html("");
            $(".jf_input").append(jf_inp);
            $(".jfinp").blur(function(){
                var val=formatCurrency(this.value);
                var num= num_chack(val,this)
                $(".jfinp")[0].value=num;
                inf_zf_btn.user_integral=num

            });
            $(".jfinp").focus(function(){
                this.value="";
            });
            $($(this).children("img")).attr("src","img/duigou2.png")
        }else{
            $(".jfinp").remove();
            $(".jf_input").html("0.00");
            //不使用
            $($(this).children("img")).attr("src","img/duigou.png")
        }
    });
    $(".inp_num").focus(function(){
        this.value="";
    });
    //当input值改变时重新获取当前页面数据
    //change事件在移动端存在兼容性问题
    //input事件与change事件相似但不符合需求
    //故用失去焦点事件代替
    //$(".inp_num")  为消费金额及不优惠金额的输入框
    $(".inp_num").on("blur",function(){
        this.value=formatCurrency(this.value);
        if($(this).attr("id")=="xiaofei"){
            inf_zf_btn.sale_money=this.value
        }else{
            inf_zf_btn.no_discount_money=this.value
        }
        sj_inf()
    })
        //确认支付按钮点击
    $(".butt_qr").click(function(){
        console.log(info)
        console.log(inf_zf_btn)
    })
})

