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
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/main.js"></script>
 </head>
<body style="background:#ededed">
<nav class="top">
	<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
	<div style="text-align:center;line-height:40px;color:#fff">新增地址</div>

</nav>

<div class="xzdz-content clearfix">
	<ul>
		<li class="clearfix">
				<span class="xzdz-left fl">联系人</span>
				<input class="fl xzdz-input" placeholder="你的姓名" type="text" id = "name" value="${address.name}"/>
		</li>
		<li class="clearfix">
				<span class="xzdz-left fl"></span>
				<div class="fl xzdz-right">
					<span><i class="xzdz-radio ${address.type eq 'add'?'xzdz-radio-hover':''}"></i>先生</span>
					<span><i class="xzdz-radio ${address.type eq 'edit'?'xzdz-radio-hover':''}"></i>女士</span> 
					<!-- <input type="radio" name="xb" value="1" >先生</input>
                    <input type="radio" name="xb" value="2" >女士</input> -->
				</div>
		</li>
		<li class="clearfix">
				<span class="xzdz-left fl">联系电话</span>
				<input value="${address.phone}" class="fl xzdz-input" placeholder="请填写收货手机号码" type="text" maxlength="11" id="phone"
					onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
		</li>
		<li class="clearfix">
				<span class="xzdz-left fl">收货地址</span>
				<input value="${address.address}" class="fl xzdz-input" placeholder="小区/写字楼/学校等" type="text" id="address"/>
				<a href="###" class="fr xzdz-dw"></a>
		</li>
		<li class="clearfix">
				<span class="xzdz-left fl"></span>
				<input class="fl xzdz-input" placeholder="详细地址（如门牌号等）" type="text" id="menpai"/>
		</li>
	</ul>
</div>
<input class="recharge-sure" type="button" value="确定" onclick="save()"/>
<script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
	function save(){
		var name = $("#name").val().trim();
		var sex ="1";
		if($(".xzdz-radio-hover").parent().text().trim() == "先生"){
			sex="1";
		}else{
			sex="2";
		}
		//alert(sex);
		return;
		var phone = $("#phone").val().trim();
		var address = $("#address").val().trim();
		var menpai = $("#menpai").val().trim();
		if(name == null || name == ""){
			alert("联系人不能为空！");
			return ;
		}
		if(sex == null || sex == ""){
			alert("性别不能为空！");
			return ;
		}
		if(phone == null || phone == ""){
			alert("手机号不能为空！");
			return ;
		}
		if(address == null || address == ""){
			alert("地址不能为空！");
			return ;
		}
		address = address+menpai+"";
		var url="<%=basePath%>html_me/addAddressById.do";
		if("${address.type}" == "edit"){
			 url="<%=basePath%>app_member/editAddressById.do";
		}
		$.ajax({
				type:"post",
				url:url,
				data:{
					member_id:"${pd.member_id}",
					name:name,
					sex:sex,
					phone:phone,
					address:address
					},
				success:function(data){
   					if(data.result == "00" || data.result == "0"){
						alert("新增失败，请联系管理员！");
 					}else{
 						window.location.href='<%=basePath%>html_me/goaddress.do?member_id=${pd.member_id}';
					}
			}
		});
		
	}
</script>
</body>
</html>
