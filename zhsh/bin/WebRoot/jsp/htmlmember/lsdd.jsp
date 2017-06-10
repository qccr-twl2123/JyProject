<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
 	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/htmlmember/style.css">
	<link rel="stylesheet" href="<%=basePath%>css/htmlmember/styles.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/htmlmember/comment.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/htmlmember/laydate.js"></script>
	<script type="text/javascript">
	if(true){
		//验证提交
		$.ajax({
		        type:"post",
		        url:'<%=basePath%>app_order/listhistory.do', 
		  	 	 data:{
		  	 		"member_id":"${pd.member_id}" 
		  	 	 },                
		        dataType:"json",
		        success: function(data){
		        	var list=data.data;
		        	var pd=list[0];
	 				if(data.result == "1"){
	  					$("#membername").html("${pg.name}");
	  					$("#ordernumber").html(pd.number+"笔");
	 					$("#ordermoney").html("￥"+pd.sumsale_money);
	 					$("#lessmoney").html("￥"+pd.sumdiscount_money);
	 					$("#paymoney").html("￥"+pd.sumactual_money);
	 					$("#getintegral").html(pd.sumget_integral+"积分");
	 				}	       	 	 
		        }
	 	 });
	}
 	</script>
</head>
<body style="background:#dedede;">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">历史订单</div>
</nav>

<div class="khpj-div clearfix">
	亲爱的<span id="membername"></span>，最近一个月来
</div>

<div class="lsdd-div">
	<ul>
		<li>
			<i class="lsdd-one"></i>
			一共消费了
			<span class="fr" id="ordernumber"></span>
		</li>
		<li>
			<i class="lsdd-two"></i>
			累计消费了
			<span class="fr" id="ordermoney"></span>
		</li>
	</ul>
</div>

<div  class="lsdd-div">
	<ul>
		<li>
			<i class="lsdd-three"></i>
			为您节省了
			<span class="fr" id="lessmoney"></span>
		</li>
		<li>
			<i class="lsdd-four"></i>
			实际支付
			<span class="fr" id="paymoney"></span>
		</li>
		<li>
			<i class="lsdd-five"></i>
			另外赠送您
			<span class="fr" id="getintegral"></span>
			<p style="color:#8f8f8f">（注：1分=1元钱哦！ 平台内所有商户均可使用）</p>
		</li>
	</ul>
</div>

	<div class="demo3">
		<ul class="inline">
		开始日期<a onchange="isOkDate('0')"><li class="inline laydate-icon" id="start" style="width:100%;margin:10px 0" ></li></a>
		结束日期<li class="inline laydate-icon" id="end" style="width:100%;margin:10px  0" onchange="isOkDate('1')"></li>
		</ul>
	</div>

<div class="lsdd-anniu">
	<input class="his-button" type="button" value="查询更多历史订单"  onclick="findtimeHistoryOrder()"/>
 	<p>（历史账单仅可查询最近三个月纪录，更多消费纪录请到网站后台查询）</p>
</div>

<script type="text/javascript">
//判断日期是否正确
function isOkDate(value){
	var starttime=$("#start").html();
	var endtime=$("#end").html();
	if(value == "0"){
		var arr = starttime.split("-");
	    var onetime = new Date(arr[0], arr[1], arr[2]);
	    var onetimes = onetime.getTime();
		var date = new Date();
		date=date.setMonth(date.getMonth()-3);
		if (onetime < date) {
	        alert("最多最近三个月纪录");
	        $("#start").html("");
	        return false;
	    }
	}else{
		var arr = starttime.split("-");
	    var onetime = new Date(arr[0], arr[1], arr[2]);
	    var onetimes = onetime.getTime();
	    var arrs =endtime.split("-");
	    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
	    var lktimes = lktime.getTime();
	    if (onetime >= lktimes) {
	        alert("开始时间不能大于结束时间 ");
	        return false;
	    }
	}
}

//查询更多历史订单
function findtimeHistoryOrder(){
	var starttime=$("#start").html();
	var endtime=$("#end").html();
	if(starttime == "" || endtime == ""){
		alert("时间不能为空");
		return;
	} 
	//验证提交
	$.ajax({
	        type:"post",
	        url:'<%=basePath%>app_order/listhistorym.do', 
	  	 	 data:{
	  	 		"member_id":"${pd.member_id}" ,
	  	 		"starttime":starttime ,
	  	 		"endtime":endtime
	  	 	 },                
	        dataType:"json",
	        success: function(data){
	        	var list=data.data;
	        	var pd=list[0];
 				if(data.result == "1"){
  					$("#ordernumber").html(pd.number+"笔");
 					$("#ordermoney").html("￥"+pd.sumsale_money);
 					$("#lessmoney").html("￥"+pd.sumdiscount_money);
 					$("#paymoney").html("￥"+pd.sumactual_money);
 					$("#getintegral").html(pd.sumget_integral+"积分");
 				}	       	 	 
	        }
 	 });
}








!function(){
	laydate.skin('molv');//切换皮肤，请查看skins下面皮肤库
	laydate({elem: '#demo'});//绑定元素
}();

//日期范围限制
var start = {
    elem: '#start',
    format: 'YYYY-MM-DD',
   /*  min: laydate.now(), */  //设定最小日期为当前日期
    max: '2099-06-16', //最大日期
    istime: true,
    istoday: false,
    choose: function(datas){
         end.min = datas; //开始日选好后，重置结束日的最小日期
         end.start = datas //将结束日的初始值设定为开始日
         isOkDate('0');
    }
};

var end = {
    elem: '#end',
    format: 'YYYY-MM-DD',
   /* min: laydate.now(),*/
    max: '2099-06-16',
    istime: true,
    istoday: false,
    choose: function(datas){
        start.max = datas; //结束日选好后，充值开始日的最大日期
        isOkDate('1');
    }
};
laydate(start);
laydate(end);

//自定义日期格式
laydate({
    elem: '#test1',
    format: 'YYYY年MM月DD日',
    festival: true, //显示节日
    choose: function(datas){ //选择日期完毕的回调
        alert('得到：'+datas);
    }
});

//日期范围限定在昨天到明天
laydate({
    elem: '#hello3',
    min: laydate.now(-1), //-1代表昨天，-2代表前天，以此类推
    max: laydate.now(+1) //+1代表明天，+2代表后天，以此类推
});
</script>
</body>
</html>

