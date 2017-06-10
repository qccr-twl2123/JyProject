

$(function(){
    $(".yidu").click(function(){
        $(".yidu img").attr("src","img/rm_me_05.png");
        $(".erdu img").attr("src","img/rm_me_06.png");
        $(".link_goin span").text("一度人脉");
        $(".yd_list").css("display","block");
        $(".er_list").css("display","none")
    })
    $(".erdu").click(function(){
        $(".yidu img").attr("src","img/rm_me_07.png");
        $(".erdu img").attr("src","img/rm_me_08.png");
        $(".link_goin span").text("二度人脉");
        $(".er_list").css("display","block");
        $(".yd_list").css("display","none")
    })
});
function change(ele){
    ele.value=ele.value.replace(/\D/gi,"")
}
function jsonToStr(data){
   // var inf=JSON.parse(data);
    return data
}
function golink(link,fun){
    var go_link=link;
    $.ajax({
        url:go_link,
        type:'post',
        cache:false,
        beforeSend :function(xmlHttp){
            xmlHttp.setRequestHeader("If-Modified-Since","0");
            xmlHttp.setRequestHeader("Cache-Control","no-cache");
        },
        success:fun
    })
}
//人脉加载
function inf_load(){
    var go_link=base_inf.base_herf+"app_friend/listContacts.do?member_id="+base_inf.member_id;
    golink(go_link,load);
    function load(data){
    	if(data.result == "0"){
    		alert(data.message);
    		return;
    	}
        var data=data.data;
        $($(".yidu p")[0]).children("span").text(data.firstcount);
        $($(".yidu p")[1]).children("span").text(data.firstcountScore);
        $($(".erdu p")[0]).children("span").text(data.twocount);
        $($(".erdu p")[1]).children("span").text(data.twocountScore);
        for (var i in data.firstcontacts){
            var yidu_str="<li><span toid='"+data.firstcontacts[i].id+"'><img src='"+data.firstcontacts[i].image_url+"' style='width: 60px;height: 60px;'></span><div style='padding-left: 5%;'>"+data.firstcontacts[i].name+"</div></li>";
            $(".yd_list").append(yidu_str)
        }
        for (var j in data.twocontacts){
            var erdu_str="<li><span toid='"+data.twocontacts[j].id+"'><img src='"+data.twocontacts[j].image_url+"' style='width: 60px;height: 60px;'></span><div style='padding-left: 5%;'>"+data.twocontacts[j].name+"</div></li>";
            $(".er_list").append(erdu_str)
        }
        //初始化
        $(".yidu")[0].click()
    }
}
var timeflag=1
function tuijian(){
    if(timeflag){
        //延时两秒   即两秒可以有效点击一次
        setTimeout(function(){
            timeflag=1
        },2000)
    }
    if (timeflag){
        //调用
        var go_link=base_inf.base_herf+"app_shouye/tuijianRegister.do?be_phone="+$(".tuijiannum")[0].value+"&id="+base_inf.member_id+"&content"+base_inf.member_id+"推荐你注册";
        golink(go_link,tuijanzhuce);
        function tuijanzhuce(data){
            var mess=data.message;
            alert(mess);
        }
    }
    timeflag=0
}
//返回
function goRenMai(){
	window.location.href=base_inf.base_herf+"html_member/gouRenMai.do";
}

//初始化加载数据
inf_load();

