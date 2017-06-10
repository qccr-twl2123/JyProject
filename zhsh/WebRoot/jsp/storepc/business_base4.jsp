<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<% String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>修改密码</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/jcxx_xgmm.css">
	<script src="js/jquery-1.8.0.min.js"></script>
 </head>
<body>
	<c:if test="${storeqx.look eq '1'}">
	<ul>
		<li style="line-height:4.5">
			<span class="item_name">原密码</span>：
			<input type="text" class="inp-l beforpassword">
		</li>
		<li  style="line-height:4.5">
			<span class="item_name">新密码</span>：
			<span class="new">
				<span class="inp_box ">
					<input type="password" class="inp-l" maxlength="12" id="xinpass">
					<ul>
						<li></li>
						<li></li>
						<li></li>
						<li></li>
						<li></li>
					</ul>
				</span>
			</span>
			<!-- <span class="xy">显隐</span>  --> <!--  密码显示隐藏 -->
			<span class="tit col-9" style="font-size:87.5%;">     <!-- 提示词 -->
					(密码必须由6~12个字符组成)
			</span>
		</li>
		<li  style="line-height:4.5">
			<spanclass="item_name">确认密码</span>：
			<input type="password" class="inp-l qrmm" maxlength="12" id="quepass" >
		</li>
 		<li style="text-align:center;">
				<span class="anniu-l" onclick="sub()" >
					提交
				</span>
		</li>
 	</ul>
	</c:if>
	<script src="js/jquery-1.12.4.min.js"></script>
	<script type="text/javascript">
 	$(function(){
		var qd= $(".inp_box li")    /*密码强度显示区*/
		var state=0;			/*	状态码*/
			var  num=new RegExp('[0-9]+');    
			var  s_char=new RegExp('[a-z]+')
			var  l_char=new RegExp('[A-Z]+')
			var  other= new RegExp("[^A-Za-z0-9_]")
			// var  err=new RegExp("[\+\\\/]")    非法字符判断
		$(".inp_box input").keyup(function(){
			for (var i = 0; i < qd.length; i++) {   /*显示状态初始化*/
				$(qd[i]).css({"background":"#999"})
			};
			var inp_val = $(".inp_box input")[0].value  
				if (inp_val.length<1) {		/*根据长度判断，改变状态码*/
					state=0;
				}else if (inp_val.length<6) {
					state=1;
				}else if (inp_val.length<13){
					state=1;
					if (num.test(inp_val)) {   /*正则检测每多一种状态码加一 即强度加强*/
						state+=1
					}
					if (s_char.test(inp_val)) {					
						state+=1
					}
					if (l_char.test(inp_val)) {					
						state+=1
					}
					if (other.test(inp_val)) {						
						state+=1
					};
				}else{
					state=999
				}
					if (state<2) {
						$(".tit")[0].innerText="密码必须由6~12个字符组成";
						for (var i = 0; i < state; i++) {
							$(qd[i]).css({"background":"red"})
						};
					}else if (state<4) {
						$(".tit")[0].innerText="密码强度仍有待提高"
						for (var i = 0; i < state; i++) {
							$(qd[i]).css({"background":"orange"})
						};
					}else if(state==999){
						console.log(qd)
						$(".tit")[0].innerText="密码长度过长，必须由6~12个字符组成";
						$(qd[0]).css({"background":"red"})
					}else{
						$(".tit")[0].innerText=""
						for (var i = 0; i < state; i++) {
							$(qd[i]).css({"background":"green"})
						};
					}	
				state=0;
		});
			
		// var flag=true
		// $(".xy").click(function(){   密码显示隐藏
		// 	if (flag) {
		// 		$(".inp_box input").attr("type","text")
		// 		flag=!flag
		// 	}else{
		// 		$(".inp_box input").attr("type","password")
		// 		flag=!flag
		// 	}
			
		// })
	});
	
	//确认修改
    function sub(){
     		var beforpassword = $(".beforpassword").val().trim();
    		var xinpass = $("#xinpass").val().trim();
    		var quepass = $("#quepass").val().trim();
    		if(beforpassword == ""){
    			alert("请输入旧密码！");
    			return ;
    		}
    		if(xinpass == ""){
    			alert("请输入新密码！");
    			return ;
    		}
    		if(quepass == ""){
    			alert("请输入确认密码！");
    			return ;
    		}
  			if ( xinpass == quepass ) {
				$(".tit")[0].innerText="两次密码输入一致";
 			}else{
				$(".tit")[0].innerText="两次密码输入不一致，请确认";
				return ;
			}
     		$.ajax({
					type:"post",
					url:"storepc_StoreManageController/modPassword.do",
					data:"store_id=${storepd.store_id}&password="+quepass+"&beforpassword="+beforpassword,
					success:function(data){
 						if(data.result == "1"){
							window.location.href="<%=basePath%>storepc/goLogin.do"; 
						}else{
							alert(data.message);
						}
						
					}
			});
     	} 
    </script>
</body>
</html>