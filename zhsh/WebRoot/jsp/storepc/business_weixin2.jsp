<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>互动沟通</title>
	 <base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/hdgt.css">
</head>
<body  >
	<!-- 加载动画 -->
	<div class="loading_box">
		<div class="load_imgbox">
			<img src="img/loading.gif" alt="">
		</div>
	</div>

	<!-- 群聊位置 -->
	<div>
	
   </div>
	<div class="cy_alert" style="display:none">    <!-- alert  成员 -->
		<ul class="alert_box">
			<li class="alt_head">
				<span class="fll font-l">成员</span>
				<div class="al_clo_box flr">
					<div class="al_close_img"></div>
				</div>
			</li>
			<li class="alt_body">
				<ul  style="width:90%;height:100%;overflow-y:auto;padding-left:15px;border-top:1px solid #ccc;line-height:1.6;" class="style_scroll_inf chengyuanList al_list">
					<!-- <li style="width:100%;">aaaaaaaaaaa</li> -->
 				</ul>
			</li>
		</ul>
	</div>
	<ul style="width:100%;height:93%;background:#eee;position:relative;min-width:1050px;">
		<li class="list">
			<ul>
				<!-- 商家logo  名字 -->
				<li class="sj">
					<img src="https://www.jiuyuvip.com/FileSave/File/userFile/${storepd.store_id}.png" alt="">
					<span class="ccyc">
						 ${storepd.store_name}
					</span>
				</li>
				<li class="hy msr">
					<span ></span>陌生人
					<span class="hy_add">﹀</span>
				</li>
				<ul class="style_scroll hy_list moshengren">
					
				</ul>
				<li  class="haoyou hy">
					<span style="dispaly:block;height:0;">我的好友</span>
					<span class="hy_add">﹀</span>
				</li>
				<ul class="hy_list style_scroll"  id="friendList">  <!-- 好友列表 -->
					<!-- <li><img src="img/hy_del.png" alt=""><span class="ccyc">金溪测试threeaaaaaaaaaa</span></li> -->					
				</ul>
				<li class="hy qunzu">
					<span >我的群组</span>
					<span class="hy_add">﹀</span>
				</li>
				<ul class="qz_list style_scroll" id="groupList">  <!-- 群组列表 -->
					 <!-- <li> <img src="static/img/head/group_normal.png" alt=""> <span class="ccyc"> ### </span></li>   -->
				</ul>
			</ul>
		</li>
		<li style=" height: 100%;">
			<ul class="friend_liaotian" style=" height: 100%;display:none; ">
				<li class="cont  friend_chuangkou"  style="display:none;"  >
					<ul style="height:100%;"> 
						<!-- 标题区 -->
						<li class="cont-tit">
							<span>与</span>
 							<span class="col-blue forfriend_name"  ></span>
							<span>聊天中</span>
							<span class="flr clearscreen" onclick="clearscreen()">
								<img src="img/hy_del.png" alt="" title="清屏">
							</span>
 						</li>
						<!-- 信息区 -->
						<li  class="cont-inf style_scroll_inf"  id="friend_xiaoxi" >
						
						  
							<ul style="padding:15px 5%;display:none;" class="jieshou">  
								<li style="text-align:center;" class="time"></li>
								<li class="inf_user_box">
									<span class="icon-user-r">
										<img src="img/ewm.png"  class="jieshou_pictrue" >
									</span>
									<span class="info-cont">
										<img src="img/sanjiao.gif" alt="" class="icon-sanjiao">
										<span style="line-height:2;" class="jieshou_text">接收</span>
									</span>
								</li>
							</ul>
							
							<ul style="padding:15px 5%;display:none;" class="fasong"> 
								<li style="text-align:center;" class="time"></li>
								<li class="inf_user_box">
									<span class="icon-user-l">
										<img src="img/ewm.png" class="fasong_pictrue" >
									</span>
									<span class="info-cont-l">
										<img src="img/sj_me.gif" alt="" class="icon-sanjiao-l">
										<span style="line-height:2;" class="fasong_text" >发送</span>
									</span>
								</li>
							</ul> 
							
							
						</li>
						<!-- 输入区 -->
						 <li class="sr_gjt">   
							<ul>
								<li>
									<span class="sr_img_box biaoqing">  
										<img src="img/hy_icon.png" alt="" style="width:100%;height:100%" onclick="showEmotionDialog()">
 										<ul class="alert_window alt_bq">   
											<li >	
 												<ul>
													<li class="xxk_item item_act">
														<span>表情</span>
													</li>
													<li class="xxk_close" onclick="xxk_close()">
														<div class="close_img"></div>
													</li>
												</ul>
 												<ul style="height:168px;" class="icon_show">
													 
												</ul>
											</li>
										</ul>
										<img src="img/bq_arrow.png" alt="" class="arr_bq">
									</span>
								</li>
								<li>
									<span class="sr_img_box" onClick=inp("#imginput")>
										<img src="img/hy_img.png" alt="" style="width:100%;height:100%" >
										<input type="file" style="display:none;" id="imginput" onchange="sendPrivateImg()">
									</span>
								</li>
								<!-- <li>
									<span class="sr_img_box" onClick=inp("#videoinput")>
										<img src="img/hy_mov.png" alt="" style="width:100%;height:100%" >
										<input type="file" style="display:none;" id="videoinput" onchange=sendPrivateVideo("33391593")>
									</span>
								</li>
								<li>
									<span class="sr_img_box" onClick=inp("#fileinput")>
										<img src="img/hy_file.png" alt="" style="width:100%;height:100%" >
										<input type="file" style="display:none;" id="fileinput" onchange=sendPrivateFile("33391593")>
									</span>
								</li> -->
							</ul>
						</li> 
						<li class="input_box">
 							<div contenteditable="true" class="style_scroll_inf message"></div>
							<span style="text-align:right;width:100%;float:right;">
								<!-- <span>按Enter发送</span> -->
								<span class="anniu-s" onclick="sendFriendMessage(this)">发送</span>
							</span>
						</li>
					</ul>
				</li>
			</ul>
			<ul class="group_liaotian"  style=" height: 100%;display:none;">
				<li class="cont group_chuangkou" style="display:none;">
					<ul style="height:100%;"> 
						<!-- 标题区 -->
						<li class="cont-tit">
							<span>与</span>
							<span>群组</span>
							<span class="col-blue forgroup_name">群聊</span>
							<span>聊天中</span>
							<span class="flr clearscreen">
								<img src="img/hy_del.png" alt="" title="清屏">
							</span>
							<span class="flr chengyuan" onclick="chengyuan()" >
								<img src="img/hy_add.png" alt="" title="成员">
							</span>
 						</li>
						<!-- 信息区 -->
						<li  class="cont-inf style_scroll_inf"  id="group_xiaoxi">
							
							<!-- <ul style="padding:15px 5%;display:none;" class="jieshou">  
								<li style="text-align:center;" class="time"></li>
								<li class="inf_user_box">
									<span class="icon-user">
										<img src="img/ewm.png"  class="jieshou_pictrue" >
									</span>
									<span class="info-cont">
										<img src="img/sanjiao.gif" alt="" class="icon-sanjiao">
										<span style="line-height:2;" class="jieshou_text">接收</span>
									</span>
								</li>
							</ul>
							
							<ul style="padding:15px 5%;display:none;" class="fasong"> 
								<li style="text-align:center;" class="time"></li>
								<li class="inf_user_box">
									<span class="icon-user-l">
										<img src="img/ewm.png" class="fasong_pictrue" >
									</span>
									<span class="info-cont-l">
										<img src="img/sj_me.gif" alt="" class="icon-sanjiao-l">
										<span style="line-height:2;" class="fasong_text" >发送</span>
									</span>
								</li>
							</ul> -->
							
							
						</li>
						<!-- 输入区 -->
						  <li class="sr_gjt">  
							<ul>
								<li>
									<span class="sr_img_box biaoqing">   
										<img src="img/hy_icon.png" alt="" style="width:100%;height:100%" onclick="showEmotionDialog()">
										<ul class="alert_window alt_bq">   
											<li >	
 												<ul>
													<li class="xxk_item item_act">
														<span>表情</span>
													</li>
													<li class="xxk_close" onclick="xxk_close()">
														<div class="close_img"></div>
													</li>
												</ul>
 												<ul style="height:168px;" class="icon_show">
												<!-- 	<li>
														<img src="" alt="">
													</li> -->
												</ul>
											</li>
										</ul>
										<img src="img/bq_arrow.png" alt="" class="arr_bq">
									</span>
								</li>
							</ul>
						</li> 
						<li class="input_box">
  							<div contenteditable="true" class="style_scroll_inf message"></div>
							<span style="text-align:right;width:100%;float:right;">
								<!-- <span>按Enter发送</span> -->
								<span class="anniu-s" onclick="sendGroupMessage(this)">发送</span>
							</span>
						</li>
					</ul>
				</li>
			</ul>
 		</li>
 		
	</ul>
<c:if test="${storepd.login_type eq '1'}">
     <input type="hidden" name="username"  id="username" tabindex="1" value="${storepd.store_id}"/>
      <input type="hidden" name="password"  id="password" tabindex="2" value="${storepd.store_id}"/>
</c:if>
 <c:if test="${storepd.login_type eq '2'}">
    <input type="hidden" name="username"  id="username" tabindex="1" value="${storepd.operator_id}"/>
     <input type="hidden" name="password"  id="password" tabindex="2" value="${storepd.operator_id}"/>
</c:if>
</body>
<!-- 
sdk相关的js
<script type='text/javascript' src='static/sdk/strophe.js'></script>
<script>
    window.WebIM = {};       // 这行代码需要加在引用strophe.js文件的代码后面
</script>
<script type='text/javascript' src='static/sdk/websdk-1.4.10.js'></script>
<script type='text/javascript' src='static/sdk/websdk.shim.js'></script>
webim相关配置
<script type='text/javascript' src='static/js/easemob.im.config.js'></script>
 -->
 <script src="static/js/jquery-1.11.1.js"></script>
<script type='text/javascript' src='static/js/webim.config.js'></script> 
<script type='text/javascript' src='static/sdk/strophe-1.2.8.min.js'></script> 
<script type='text/javascript' src='static/sdk/websdk-1.4.10.js'></script>

<script type="text/javascript">
var conn = null;
var curUserId = null;//当前登录人的ID
var nowid = null;//当前聊天的ID
var curChatUserId = null;//当前聊天人的ID
var curChatGroupId = null;//当前聊天群的ID
var bothRoster = [];
var toRoster = [];

var conn = new WebIM.connection({
    https: WebIM.config.https,
    url: WebIM.config.xmppURL,
    isAutoLogin: WebIM.config.isAutoLogin,
    isMultiLoginSessions: WebIM.config.isMultiLoginSessions
});

var options = { 
		  apiUrl: WebIM.config.apiURL,
		  user: $("#username").val(),
		  pwd: $("#password").val(),
		  appKey: WebIM.config.appkey
};
conn.open(options);

conn.listen({
    onOpened: function ( message ) {          //连接成功回调
        // 如果isAutoLogin设置为false，那么必须手动设置上线，否则无法收消息
        // 手动上线指的是调用conn.setPresence(); 如果conn初始化时已将isAutoLogin设置为true
        // 则无需调用conn.setPresence();    
        console.log("登录成功");
        handleOpen(conn);
    },  
    onClosed: function ( message ) {},         //连接关闭回调
    onTextMessage: function ( message ) {
    	shoudaoxiaoxi(message,"1");
     },    //收到文本消息
    onEmojiMessage: function ( message ) {
    	shoudaoxiaoxi(message,"2");
     },   //收到表情消息
    onPictureMessage: function ( message ) {
    	shoudaoxiaoxi(message,"3");
    }, //收到图片消息
    onCmdMessage: function ( message ) {
    	
    },     //收到命令消息
    onAudioMessage: function ( message ) {
    	
    },   //收到音频消息
    onLocationMessage: function ( message ) {
    	
    },//收到位置消息
    onFileMessage: function ( message ) {
    	
    },    //收到文件消息
    onVideoMessage: function (message) {
        var node = document.getElementById('privateVideo');
        var option = {
            url: message.url,
            headers: {
              'Accept': 'audio/mp4'
            },
            onFileDownloadComplete: function (response) {
                var objectURL = WebIM.utils.parseDownloadResponse.call(conn, response);
                node.src = objectURL;
            },
            onFileDownloadError: function () {
                console.log('File down load error.')
            }
        };
        WebIM.utils.download.call(conn, option);
    },   //收到视频消息
    onPresence: function ( message ) {},       //收到联系人订阅请求、处理群组、聊天室被踢解散等消息
    onRoster: function ( message ) {},         //处理好友申请
    onInviteMessage: function ( message ) {},  //处理群组邀请
    onOnline: function () {},                  //本机网络连接成功
    onOffline: function () {},                 //本机网络掉线
    onError: function ( message ) {},          //失败回调
    onBlacklistUpdate: function (list) {       //黑名单变动
        // 查询黑名单，将好友拉黑，将好友从黑名单移除都会回调这个函数，list则是黑名单现有的所有好友信息
        console.log(list);
    }
});

 




//处理连接时函数,主要是登录成功后对页面元素做处理
var handleOpen = function(conn) {
 	//从连接中获取到当前的登录人注册帐号名
	curUserId = conn.context.userId;
	//获取当前登录人的联系人列表
	conn.getRoster({
		success : function(roster) {
			// 页面处理
			var curroster;
			for ( var i in roster) {
				var ros = roster[i];
				//both为双方互为好友，要显示的联系人,from我是对方的单向好友
				if (ros.subscription == 'both' || ros.subscription == 'from') {
					bothRoster.push(ros);
				} else if (ros.subscription == 'to') {
					//to表明了联系人是我的单向好友
					toRoster.push(ros);
				}
			}
 			//设置当前登录人的好友列表
			if (bothRoster.length > 0) {
				curroster = bothRoster[0];
				buildContactDiv( bothRoster);//联系人列表页面处理
  			}
			//获取当前登录人的群组列表
			conn.listRooms({
				success: function(rooms) {
                    conn.setPresence();//设置用户上线状态，必须调用
					if (rooms && rooms.length > 0) {
						buildListRoomDiv(rooms);//群组列表页面处理
 					}
				},
				error: function(e) {
                    conn.setPresence();//设置用户上线状态，必须调用
                }
			});
 		}
	});
 
	 
};


//构造联系人列表我的好友列表myfriend=========================================================================================================
var buildContactDiv = function(roster) {
	for (i = 0; i < roster.length; i++) {
		 var ros = roster[i];   
 	      //ros.subscription值为both/to为要显示的联系人，此处与APP需保持一致，才能保证两个客户端登录后的好友列表一致
	      if ( !(ros.subscription === 'both' || ros.subscription === 'to') ) {
	    	  continue;
	      }
     	  var jid = ros.jid;
		  var userName = jid.substring(jid.indexOf("_") + 1).split("@")[0];//获取ID
		  var lielem = $("<li>").attr({
				"id" : userName,
				"class" : "liaotian",
				"displayName" : userName
	
		  }).click(function() {
			  friendchooseContactDivClick(this);
		  });
		  var image_url="https://www.jiuyuvip.com/FileSave/File/userFile/"+userName+".png";
		  var _name=getname(userName);
		  var str="<img src="+image_url+"><span>"+_name+"</span>";
		  $(lielem).append(str);
		  $("#friendList").append(lielem); 
   	    //-------------------------------------------------
    }
 };
 
 
//点击切换好友联系人聊天窗口div
 var friendchooseContactDivClick = function(e) { 
	 curChatGroupId=null;
 	$(".group_liaotian").hide(); 
	$(".friend_chuangkou").hide(); 
	$(".friend_liaotian").show(); 
	var chatUserId=$(e).attr("id");
	$(e).find("span").removeClass("shanshuo");
	$(e).parent("ul").prev().removeClass("shanshuo");
	curChatUserId=chatUserId;
	nowid=chatUserId;
 	$(".liaotian").css("background","#0859a2");
	$("#"+chatUserId).css("background","#33ccff");
 	var chat_name=getname(chatUserId);
 	if($("#chat_"+chatUserId).length >0){
		$("#chat_"+chatUserId).show();
	}else{
		var str="<li class='cont  friend_chuangkou' id='chat_"+chatUserId+"' >";
 		var mobanhtml=$(".friend_chuangkou").html();
		str=str+mobanhtml+"</li>";
		$(".friend_liaotian").append(str);
  		$("#chat_"+chatUserId).find(".forfriend_name").html(chat_name);
  		$("#chat_"+chatUserId).find("#friend_xiaoxi").addClass("xiaoxi_"+curChatUserId);
 	}
 	dingbu($(".xiaoxi_"+nowid));
 };
 
 
//构造群组列表========================================================================================================================
 var buildListRoomDiv = function( rooms ) {
   	for (i = 0; i < rooms.length; i++) {
 		var roomsName = rooms[i].name;
 		var roomId = rooms[i].roomId || rooms[i].id;
  		var lielem = $('<li>').attr({
 			'id' :  roomId,
 			'class' : 'liaotian',
  			'displayName' : roomsName,
 			'roomId' : roomId 
  		}).click(function() {
 			groupchooseContactDivClick(this);
 		});
 		var str="<img src='static/img/head/group_normal.png' ><span>"+roomsName+"</span>";
  		$(lielem).append(str);
 		$("#groupList").append(lielem);
 	}
 	$(".loading_box").css("display","none")
 };
 

//点击切换群窗口div  
 var groupchooseContactDivClick = function(e) { 
	 curChatUserId=null;
 	$(".cont").hide();
	$(".friend_liaotian").hide(); 
 	$(".group_liaotian").show(); 
 	var chatUserId=$(e).attr("id");
 	$(e).find("span").removeClass("shanshuo");
 	$(e).parent("ul").prev().removeClass("shanshuo");
 	curChatGroupId=chatUserId;
 	nowid=chatUserId;
 	var chat_name=$(e).attr("title");
 	$(".liaotian").css("background","#0859a2");
 	$("#"+chatUserId).css("background","#33ccff");
  	if($("#chat_"+chatUserId).length >0){
		$("#chat_"+chatUserId).show();
	}else{
		var str="<li class='cont  group_chuangkou' id='chat_"+chatUserId+"' >";
 		var mobanhtml=$(".group_chuangkou").html();
		str=str+mobanhtml+"</li>";
		$(".group_liaotian").append(str);
  		$("#chat_"+chatUserId).find(".forgroup_name").html(chat_name);
  		$("#chat_"+chatUserId).find(".chengyuan").attr("group_id",chatUserId);
  		$("#chat_"+chatUserId).find("#group_xiaoxi").addClass("xiaoxi_"+chatUserId);
 	}
  	dingbu($(".xiaoxi_"+nowid));
 };
function chengyuan(){
	$(".cy_alert").css("display","block");
	 queryRoomMember();
}
//根据roomId查询room成员列表
var queryRoomMember = function () {
	$(".loading_box").css("display","block")
    var member = '';
    conn.queryRoomMember({
        roomId: curChatGroupId,
        success: function (members) {
            for (var o in members) {
            	$(".chengyuanList").empty();
                var jid =  members[o].jid;
        		var userName = jid.substring(jid.indexOf("_") + 1).split("@")[0];
        		var _name=getname(userName);
        		var url="https://www.jiuyuvip.com/FileSave/File/userFile/"+userName+".png"
        	
        		var str="<li style='width:100%;'><img src="+url+">"+_name+"</li>";
        		$(".chengyuanList").append(str);
               // console.log(member);
               $(".loading_box").css("display","none")
            }
        }
    });
};

//清屏
function clearscreen(){
	$(".cont-inf").children("ul").remove()
}





//获取用户名
function getname(id){
	var thisname=id;
 	if(thisname.length == 8){
		thisname=thisname.substring(0,2)+"****"+thisname.substring(6,8);
	}else if(thisname.length >= 17){
		thisname=thisname.substring(0,5)+"****"+thisname.substring(9,17);
	}
  	$.ajax({
	    type:"post",
	    url:'<%=basePath%>app_member/imgae_urlById.do', 
	    data:{"id":id},              
	    dataType:"json",
	    async: false,
	    success: function(data){
				if(data.result == "1"){
 					thisname=data.data.name;
	 			} 
	     }
	});
 	return thisname;
}


//处理接收消息
function shoudaoxiaoxi(message,shoudao_type){
	var serverMsgId = message.id;//消息的发送者
	var from = message.from;//消息的发送者
	if(from != nowid){
		$("#"+from).find("span").addClass("shanshuo");
	}
 	var mestype = message.type;//消息发送的类型是群组消息还是个人消息
	var messageContent = message.data;//文本消息体
	var shoudao_xiaoxi="";
	if(shoudao_type == "2"){
		var flg = messageContent.length;
 		for (var i = 0; i < flg; i++) {
			var msg = messageContent[i];
			var type = msg.type;
			var data = msg.data;
			if(type == "emoji"){
				data="<img src='"+data+"'>";
			}
			shoudao_xiaoxi+=data;
		}
	}else if(shoudao_type == "1"){
		shoudao_xiaoxi=messageContent;
	}else if(shoudao_type == "3"){
		shoudao_xiaoxi="<img style='width:100%;' src='"+message.url+"'>";
	}
 	//alert(shoudao_xiaoxi);
 	//TODO  根据消息体的to值去定位那个群组的聊天记录
	var id = message.to;
 	if(id == curUserId ){//单聊
		if($("#"+from).length == 0){//陌生人
			haoyoupanduan(from,serverMsgId,messageContent);
			$(".msr").addClass("shanshuo");

		}else{//好友
		var msr=$(".moshengren li[id='"+from+"']")
		if(msr.length>0){
			$(".msr").addClass("shanshuo");
		}else{
			$(".haoyou").addClass("shanshuo");
		}
			if($("#chat_"+from).length == 0){
				var str="<li class='cont  friend_chuangkou' id='chat_"+from+"'  style='display:none;'>";
		 		var mobanhtml=$(".friend_chuangkou").html();
				str=str+mobanhtml+"</li>";
				$(".friend_liaotian").append(str);
		  		$("#chat_"+from).find(".forfriend_name").html(getname(from));
		  		$("#chat_"+from).find("#friend_xiaoxi").addClass("xiaoxi_"+from);
	 		} 
			var fasong=$(".jieshou").html();
	 		fasong="<ul style='padding:15px 5%;clear: both;overflow:hidden;' id='"+serverMsgId+"'  >"+fasong+"</ul>";
			$(".xiaoxi_"+from).append(fasong);
			$("#"+serverMsgId).find(".jieshou_pictrue").attr("src","https://www.jiuyuvip.com/FileSave/File/userFile/"+from+".png");
			$("#"+serverMsgId).find(".jieshou_text").html(shoudao_xiaoxi);
		}
 	}else{//群聊
 		$(".qunzu").addClass("shanshuo");
 		if($("#chat_"+from).length == 0){
			var str="<li class='cont  group_chuangkou' id='chat_"+from+"'  style='display:none;'>";
	 		var mobanhtml=$(".group_chuangkou").html();
			str=str+mobanhtml+"</li>";
			$(".group_liaotian").append(str);
	  		$("#chat_"+from).find(".forgroup_name").html(getname(from));
	  		$("#chat_"+from).find("#friend_xiaoxi").addClass("xiaoxi_"+from);
 		} 
		var fasong=$(".jieshou").html();
 		fasong="<ul style='padding:15px 5%;clear: both;overflow:hidden;' id='"+serverMsgId+"'  >"+fasong+"</ul>";
		$(".xiaoxi_"+from).append(fasong);
		$("#"+serverMsgId).find(".jieshou_pictrue").attr("src","https://www.jiuyuvip.com/FileSave/File/userFile/"+from+".png");
		$("#"+serverMsgId).find(".jieshou_text").html(shoudao_xiaoxi);
	}
	dingbu($(".xiaoxi_"+id));
/* 	 $('img').error(function(){
         $(this).attr('src', "img/storelogo.png");
      }); */
}



//接收到好友消息
function moshengren(from,serverMsgId,messageContent){
	if($("#chat_"+from).length == 0){
		var str="<li class='cont  friend_chuangkou' id='chat_"+from+"'  style='display:none;'>";
 		var mobanhtml=$(".friend_chuangkou").html();
		str=str+mobanhtml+"</li>";
		$(".friend_liaotian").append(str);
  		$("#chat_"+from).find(".forfriend_name").html(getname(from));
  		$("#chat_"+from).find("#friend_xiaoxi").addClass("xiaoxi_"+from);
		} 
	var fasong=$(".jieshou").html();
		fasong="<ul style='padding:15px 5%;clear: both;overflow:hidden;' id='"+serverMsgId+"'  >"+fasong+"</ul>";
	$(".xiaoxi_"+from).append(fasong);
	$("#"+serverMsgId).find(".jieshou_pictrue").attr("src","https://www.jiuyuvip.com/FileSave/File/userFile/"+from+".png");
	$("#"+serverMsgId).find(".jieshou_pictrue").error(function(){
		imgerror(this)
	})
	$("#"+serverMsgId).find(".jieshou_text").html(messageContent);
}


//陌生人判断  消息加载
function haoyoupanduan(fromid,serverMsgId,messageContent){
	var hy = $(".hy_list li[id='"+fromid+"']")
		if (hy.length>0) {
			hy.find("span").addClass("shanshuo");
		}else{
			 var lielem = $("<li>").attr({
				"id" : fromid,
				"class" : "liaotian",
				"displayName" : fromid
	
			  }).click(function() {
				  friendchooseContactDivClick(this);
			  });
			  var image_url="https://www.jiuyuvip.com/FileSave/File/userFile/"+fromid+".png";
			  var _name=getname(fromid);
			  var str="<img src="+image_url+" onError=imgerror(this)><span class='shanshuo'>"+_name+"</span>";
			  $(lielem).append(str);
 			  $(".moshengren").append(lielem); 
 			  moshengren(fromid,serverMsgId,messageContent)
 		}
	};

	function imgerror(img_this){
		img_this.src="img/storelogo.png";
		img_this.error=null;
	}












//生成随机字符
var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
function generateMixed() {
     var res = "";
     for(var i = 0; i < 8 ; i ++) {
         var id = Math.ceil(Math.random()*35);
         res += chars[id];
     }
     return res;
}


 

//单聊发送消息
function sendFriendMessage(obj){
	 	var id = conn.getUniqueId();                 // 生成本地消息id
	    var msg = new WebIM.message('txt', id);      // 创建文本消息
	    var div_fasong=$(obj).parent().prev();
	    var sendMessage=$(div_fasong).html();
        msg.set({
	        msg: imgchangeText(div_fasong),                  // 消息内容
	        to: curChatUserId,                          // 接收消息对象（用户id）
	        roomType: false,
	        success: function (id, serverMsgId) {
   	        	var fasong=$(".fasong").html();
	        	fasong="<ul style='padding:15px 5%;clear: both;overflow:hidden;' id='"+serverMsgId+"'  >"+fasong+"</ul>";
	        	$(".xiaoxi_"+curChatUserId).append(fasong);
	        	$("#"+serverMsgId).find(".fasong_pictrue").attr("src","https://www.jiuyuvip.com/FileSave/File/userFile/"+curUserId+".png");
	        	$("#"+serverMsgId).find(".fasong_text").html(parseEmoji(sendMessage));
	        	$(div_fasong).html("");
	            //console.log('send private text Success');
	        	dingbu($(".xiaoxi_"+nowid));
	        }
	    });
	    msg.body.chatType = 'singleChat';
	    conn.send(msg.body);
}


// 单聊发送图片消息    img
var sendPrivateImg = function () {
    var id = conn.getUniqueId();                   // 生成本地消息id
    var msg = new WebIM.message('img', id);        // 创建图片消息
    var input = document.getElementById('imginput');  // 选择图片的input
    var file = WebIM.utils.getFileUrl(input);      // 将图片转化为二进制文件
    console.log(file)
    var allowType = {
        'jpg': true,
        'gif': true,
        'png': true,
        'bmp': true
    };
    if (file.filetype.toLowerCase() in allowType) {
        var option = {
            apiUrl: WebIM.config.apiURL,
            file: file,
            to: curChatUserId,                       // 接收消息对象
            roomType: false,
            chatType: 'singleChat',
            onFileUploadError: function () {      // 消息上传失败
                //console.log('onFileUploadError');
            	alert("发送失败");
            },
            onFileUploadComplete: function () {   // 消息上传成功
                console.log('onFileUploadComplete');
            },
            success:function (id, serverMsgId) {
   	        	var fasong=$(".fasong").html();
	        	fasong="<ul style='padding:15px 5%;clear: both;overflow:hidden;' id='"+serverMsgId+"'  >"+fasong+"</ul>";
	        	$(".xiaoxi_"+curChatUserId).append(fasong);
	        	$("#"+serverMsgId).find(".fasong_pictrue").attr("src","https://www.jiuyuvip.com/FileSave/File/userFile/"+curUserId+".png");
	        	$("#"+serverMsgId).find(".fasong_text").html("<img src='"+file.url+"' style='width:100%;padding:12px 25px 5px 0;'>");
	        	//console.log(file)
	        	$(div_fasong).html("");
	            //console.log('send private text Success');
	        	dingbu($(".xiaoxi_"+nowid));
	        	//console.log("Success")
	        },
            flashUpload: WebIM.flashUpload
        };
        msg.set(option);
        conn.send(msg.body);
    }
}
//群聊发送消息
function sendGroupMessage(obj){
	var id = conn.getUniqueId();            // 生成本地消息id
    var msg = new WebIM.message('txt', id); // 创建文本消息
    var div_fasong=$(obj).parent().prev();
    var sendMessage=$(div_fasong).html();
    var option = {
        msg: imgchangeText(div_fasong),             // 消息内容
        to: curChatGroupId,                     // 接收消息对象(群组id)
        roomType: false,
        chatType: 'chatRoom',
        success: function () {
        	var fasong=$(".fasong").html();
        	var serverMsgId=generateMixed();
        	fasong="<ul style='padding:15px 5%;clear: both;overflow:hidden;' id='"+serverMsgId+"'  >"+fasong+"</ul>";
        	//alert(curChatGroupId);
        	$(".xiaoxi_"+curChatGroupId).append(fasong);
        	$("#"+serverMsgId).find(".fasong_pictrue").attr("src","https://www.jiuyuvip.com/FileSave/File/userFile/"+curUserId+".png");
        	$("#"+serverMsgId).find(".fasong_text").html(parseEmoji(sendMessage));
        	$(div_fasong).html("");
        	dingbu($(".xiaoxi_"+nowid));
            //console.log('send room text success');
        },
        fail: function () {
            console.log('failed');
        }
    };
    msg.set(option);
    msg.setGroup('groupchat');
    conn.send(msg.body);
}








 
var emotionFlag = false;
  //表情
$(".biaoqing img").click(function(){
	
})  
function xxk_close(){
	$(".alt_bq").css("display","none")
	$(".arr_bq").css("display","none")
 	$('.icon_show').empty();
	emotionFlag = false;
 }
function showEmotionDialog() {
	if(emotionFlag){
		xxk_close();
		emotionFlag = false;
		return;
	}
	emotionFlag=true;
   	// Easemob.im.Helper.EmotionPicData设置表情的json数组
	var sjson =WebIM.Emoji ,
	data = sjson.map,
	path = sjson.path;
 	for ( var key in data) {
		var emotions = $('<img>').attr({
			"id" : key,
			"src" : path + data[key],
			"style" : "cursor:pointer;"
		}).click(function() {
			selectEmotionImg(this);
		});
		$('<li>').append(emotions).appendTo($('.icon_show'));
	}
	$(".alt_bq").css("display","block")
	$(".arr_bq").css("display","block")
};
 
//选择表情
function selectEmotionImg(e){
	var str=$("#chat_"+nowid).find(".message").html()+"<img class='emoji' src='" +e.src+"' /><span class='bq_span' style='display:none;'>"+e.id+"</span>";
 	$("#chat_"+nowid).find(".message").html(str);
	//$("#chat_"+nowid).find(".message").prev().val(str);
	
}
 
//文本替换图片
function parseEmoji(msg) {
    if (typeof WebIM.Emoji === 'undefined' || typeof WebIM.Emoji.map === 'undefined') {
        return msg;
    } else {
        var emoji = WebIM.Emoji,
            reg = null;

        for (var face in emoji.map) {
            if (emoji.map.hasOwnProperty(face)) {
                while (msg.indexOf(face) > -1) {
                     msg = msg.replace(face, "<img class='emoji' src='" + emoji.path + emoji.map[face] +"' />");
                }
            }
        }
        return msg;
    }
}

//图片替换文本
function imgchangeText(obj) {
    $(obj).find(".emoji").remove();
    var send_message=$(obj).text();
    return send_message;
}


function parseLink(msg) {

    var reg = /(https?\:\/\/|www\.)([a-zA-Z0-9-]+(\.[a-zA-Z0-9]+)+)(\:[0-9]{2,4})?\/?((\.[:_0-9a-zA-Z-]+)|[:_0-9a-zA-Z-]*\/?)*\??[:_#@*&%0-9a-zA-Z-/=]*/gm;

    msg = msg.replace(reg, function (v) {

        var prefix = /^https?/gm.test(v);

        return "<a href='" + (prefix ? v : '//' + v) + "' target='_blank'>" + v + "</a>";
    });

    return msg;
}

 
WebIM.Emoji  = {
	    path: 'static/img/faces/'
	    , map: {
	        '[):]': 'ee_1.png',
	        '[:D]': 'ee_2.png',
	        '[;)]': 'ee_3.png',
	        '[:-o]': 'ee_4.png',
	        '[:p]': 'ee_5.png',
	        '[(H)]': 'ee_6.png',
	        '[:@]': 'ee_7.png',
	        '[:s]': 'ee_8.png',
	        '[:$]': 'ee_9.png',
	        '[:(]': 'ee_10.png',
	        '[:\'(]': 'ee_11.png',
	        '[:|]': 'ee_12.png',
	        '[(a)]': 'ee_13.png',
	        '[8o|]': 'ee_14.png',
	        '[8-|]': 'ee_15.png',
	        '[+o(]': 'ee_16.png',
	        '[<o)]': 'ee_17.png',
	        '[|-)]': 'ee_18.png',
	        '[*-)]': 'ee_19.png',
	        '[:-#]': 'ee_20.png',
	        '[:-*]': 'ee_21.png',
	        '[^o)]': 'ee_22.png',
	        '[8-)]': 'ee_23.png',
	        '[(|)]': 'ee_24.png',
	        '[(u)]': 'ee_25.png',
	        '[(S)]': 'ee_26.png',
	        '[(*)]': 'ee_27.png',
	        '[(#)]': 'ee_28.png',
	        '[(R)]': 'ee_29.png',
	        '[({)]': 'ee_30.png',
	        '[(})]': 'ee_31.png',
	        '[(k)]': 'ee_32.png',
	        '[(F)]': 'ee_33.png',
	        '[(W)]': 'ee_34.png',
	        '[(D)]': 'ee_35.png'
	    }
	};

 
  	 //按enter事件
	 window.onload=function(){
 	 	document.onkeydown=function(ev){//检测鼠标按下事件
	 		var oEvent=ev||event;
		 	if(oEvent.keyCode==13){//如果按下的是enter键
			 	if(nowid != null){
 				  $("#chat_"+nowid).find(".anniu-s").click();
			  	}
		 	  	return false;//阻止默认事件
		 	}
	 	}
	 }
 
	 
	 // 滚动条底部  
	 function  dingbu(elem){
	 	$(elem).scrollTop($(elem)[0].scrollHeight)
	 }
	
 
	 
	 
	 
	 
	 
	 //文件icon点击调用input
	 function inp(str){
		 $(str)[0].click()
	 }
	 
	 

	 
	 
	$(function(){
		
 		// 列表收放
		var flag=0;
	$(".hy").click(function(e){
			if (flag==0) {
				$this=$(e.target)
				var height=$this.next().children().length*60
				$this.next().css("height",height)
 				$this.children(".hy_add").html("︿")
 				flag=1
			}else{
				if ($(e.target)[0].className==$this[0].className) {
					$this.next().css("height","0")
					$this.children(".hy_add").html("﹀")
 					flag=0
				}else{
					height=$(e.target).next().children().length*60
					$(e.target).next().css("height",height)
					$this.children(".hy_add").html("﹀")
					flag=1
					$this=$(e.target)
				}
			}
		})
		$(".hy span").click(function(e){
			$(e.target).parent(".hy")[0].click()
		})


		

		

		// 成员弹窗
		$(".al_clo_box").click(function(){
			$(".cy_alert").css("display","none")
		})
		$(".cy_alert").click(function(){
			$(".cy_alert").css("display","none")
		})
	
/* 
		// 成员弹窗按钮显示
		$(".hy_list li").click(function(){
			$(".chengyuan").css("display","none")
		})
		$(".qz_list li").click(function(){
			$(".chengyuan").css("display","block")
		}) */
	})
	
</script>
</html>