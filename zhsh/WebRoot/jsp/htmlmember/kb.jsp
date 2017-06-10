<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
	<base href="<%=basePath%>">
    <title>文本说明</title>
    <link rel="stylesheet" href="css/htmlmember/labary/predefine.css">
    <script src="js/htmlmember/library/jquery-1.12.4.min.js"></script>
</head>
<style>
    body{
        overflow-y: hidden;
    }
    .local_box{
        width:100%;
        height:40px;
        background:#ff0600;
        box-sizing:border-box;
        position: relative;
        color: #fff;
    }
    .local_cont_box{
        width:60%;
        height:100%;
        margin:0 auto;
        text-align: center;
        overflow: hidden;
    }
    .local_cont_box img{
        height: 20px;
        padding: 10px 0;
    }
    .local_cont_box span{
        vertical-align: top;
        line-height: 40px;
        font-size: 100%;
    }
    .title{
        font-size: 150%;
    }
    .icon_left{
        font-size: 100%;
        position: absolute;
        left: 0;
        top:0;
        vertical-align: top;
        line-height: 50px;
        padding: 0 3.6%;
        font-weight: bold;
    }
    .cont_box{
        box-sizing: border-box;
        padding: 3%;
        line-height: 1.4;
        background: url("img/cont_bg.png") no-repeat;
        background-size: 100% 100%;
        display: none;
    }
    .tit{
        font-weight: bold;
    }
    .red{
        color: #ff0000;
    }

</style>
<body>
<!--顶部栏-->
<div class="local_box guding" >
    <div class="local_cont_box">
        <span class="title"></span>
    </div>
    <span class="icon_left" onclick="back_url()"><img src="img/fanhui.png" alt=""></span>
</div>
<script type="text/javascript">
function back_url(){
	window.location.href="javascript:window.history.back()";
}
</script>
<!--积分  type=8-->
<ul class="change cont_box jf">
    <li class="tit">
        积分是九鱼网会员重要的优惠措施之一，1积分＝1元人民币，每笔消费都有积分赠送。
    </li>
    <li class="red">
        Q1：积分用来做什么？
    </li>
    <li>
        积分1分＝1元钱，积分在消费时可以抵等值现金使用。
    </li>
    <li class="red">
        Q2：怎么获得积分？
    </li>
    <li>
        每笔消费、推荐好友都能获得积分奖励：</br>
        1、在商家消费时，每笔消费均赠送积分，积分的赠送额度和数值根据商家的积分比例，由系统自动进行计算；</br>
        2、向您的好友推荐九鱼网，注册成功后即成为您的一度人脉；您的好友再次推荐的用户即成为您的二度人脉；您的一度和二度人脉数量不限，他们每次消费时，都将奖励您相应积分；</br>
        3、您的好友向您发送的积分红包；</br>
        4、商家和平台不定期奖励的积分。</br>
    </li>
    <li class="red">
        Q3：积分如何使用？
    </li>
    <li >
        1、在消费时直接使用积分抵扣等值现金；</br>
        2、向您人脉圈中的好友或群聊好友发送积分红包。</br>
    </li>
    <li class="red">
        Q4、积分有效期说明吗?
    </li>
    <li>
        积分获取后长期有效，永不清零。
    </li>
    <li class="red">
        Q5、积分可以提现吗？
    </li>
    <li>
        不可以，积分是奖励性质，获得的积分可以消费，可以转让给好友，但不能提现。
    </li>
</ul>
<!--余额  type=1-->
<ul class="change cont_box ye">
    <li class="red">
        Q1:余额如何使用？
    </li>
    <li>
        1、在买单时，如果帐户中有余额，则优先自动使用；</br>
        2、如果您需要退还到个人银行帐户，请点击提现，按提示进行操作即可。</br>
    </li>
    <li class="red">
        Q2：余额是怎么产生的？
    </li>
    <li>
       1、当您的订单被取消，或者退款时，已经支付的款项暂时被退回到余额；</br>
       2、当您为了方便向小伙伴们发送积分红包而临时充值的金额。</br>
    </li>
</ul>
<!--会员卡 type=9-->
<ul class="change cont_box hy">
    <li class="tit">
        您领取的会员卡将显示在这里。
    </li>
    <li class="red">
        Q1：会员卡领取方式？
    </li>
    <li>
        1、进入商家页面，点击右上角VIP按钮；</br>
        2、商家主动邀请您成为会员时，点击同意；</br>
        3、当您新注册为商家的一度人脉时，将自动成为该商家的VIP会员；</br>
    </li>
    <li class="red">
        Q2：为什么要成为商家会员？
    </li>
   <li>
       1、不定期收到商家发放的会员专属红包；</br>
       2、有机会获得商家提供的礼品；</br>
       3、汇总消费记录，消费帐单一目了然。</br>
   </li>
</ul>
<!--红包  type=6-->
<ul class="change cont_box hb">
    <li class="tit">
        优惠红包是您在消费时可以直接抵扣支付金额的优惠凭证。 优惠红包分为两类：现金红包和折扣红包；
    </li>
    <li class="red">
        Q1：怎么获取红包？
    </li>
    <li>
        1、每天开机后有一次免费领取红包的机会；</br>
        2、进入商家页面后直接领取红包；</br>
        3、点击页面右上方的+号，主动查找附近商家的红包；</br>
        4、在人脉圈里，点击“找商家发红包”，让商家为您定制一个专属红包吧；</br>
    </li>
    <li class="red">
        Q2：红包如何使用？
    </li>
    <li>
        在商家消费买单时，只要您的消费订单满足红包的使用要求，红包自动抵扣对应金额。
    </li>
    <li class="red">
        Q3：红包到期了怎么办？
    </li>
    <li>
        若到期的红包不能继续使用，您可以在到期前将红包转赠给人脉圈中的好友使用。
    </li>
    <li class="red">
        Q4：一个红包能拆开多次使用吗？
    </li>
    <li>
        不能，一个红包只能一次性使用，不能分开使用。
    </li>
    <li class="red">
        Q5：怎样才能增加获得红包的概率？
    </li>
    <li>
        您可以领取常去商家的VIP卡，收藏该商家，商家一般会针对VIP用户和收藏本店的用户发放专属红包哦！ </br>
        方法一：进入商家页面，点击右上角的VIP卡标志和收藏标志； </br>
        方法二：在商家消费时，接受商家的VIP邀请。
    </li>
    <li class="red">
        Q6：为什么在同一个店领到的现金红包金额不一样呢？
    </li>
    <li>
        现金红包分为两种，固定金额和拼手气金额，在拼手气的状态下，每个人领到的红包金额是不一样的。
    </li>
    <li class="red">
        Q7：为什么使用折扣红包时折扣值会不一样呢？
    </li>
    <li>
        折扣红包分为原价折扣和优惠后折扣两种，具体优惠方式由商家设定，在买单时会根据预先设定的方案自动计算。
    </li>
    <li class="red">
        Q8：红包在使用时有什么注意事项？
    </li>
    <li>
        每笔订单只限使用一个红包，系统将自动在消费买单时选择最佳红包使用方案； 红包是否与其他优惠同时使用，系统将根据设置自动识别； 您在使用过程中遇到的任何问题，均可直接咨询商家。
    </li>
</ul>
</body>
<script>
    $(function(){
//        视区  高度
        var hei=$("body").height();
        var height=0;
        for (var i=0;i<2;i++){
            height=Number($($("body").children(".guding")[i]).height())+height
        }
        $(".change").height(hei-height);
        $(".change").css("overflow-y","auto");
//        type:待传参数  上下类型须一致   下string
        var type="6";
        switch(type)
        {
            case "1":    /*余额*/
                $(".ye").css("display","block")
                    $(".title").html("余额说明")
                    document.title="余额说明"
                break;
            case "6":   /*红包*/
                $(".hb").css("display","block")
                $(".title").html("红包说明")
                    document.title="红包说明"
                break;
            case "8":   /*积分*/
                $(".jf").css("display","block")
                $(".title").html("积分说明")
                document.title="积分说明"
                break;
            case "9":   /*会员*/
                $(".hy").css("display","block")
                $(".title").html("会员卡说明");
                document.title="会员卡说明"
                break;
            default:
        }
    })

</script>
</html>