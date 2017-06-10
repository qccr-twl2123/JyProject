<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
	<title>我的推广二维码</title>
	<link rel="stylesheet" href="<%=basePath%>css/erweima/normalize.min.css">
	<link rel="stylesheet" href="<%=basePath%>css/erweima/headandstyle.css">
	<script src="<%=basePath%>js/jquery-1.7.2.js"></script>
 	<script src="<%=basePath%>js/jquery.qrcode.min.js"></script>
</head>
<body>
	<header>
		<div class="goback" onclick="backreturn()">‹</div>
		<div class="title">
			 <div class="djs">
		        <h6>我的推广二维码</h6>
	    	</div>
		</div>
		<script type="text/javascript">
	function backreturn(){
		window.history.back();
	}
	</script>
	</header>	
	<section>
		<div class="inf">
			<div class="cont">
				<div class="picture">
					<img src="${empty pg.image_url?'../../img/moren.png':pg.image_url }" >
				</div>
				<h4 style="padding-top:2%;">
					  ${pg.name}
				</h4>
				<h4>
					 免费注册会员，每笔消费必有优惠
				</h4>
					<div style="padding:0 21%;width:100%;">
						<div class="img_box erweima">
							 
						</div>
					</div>
				<h4 style="line-height:3;font-size:1.6rem;">识别图中二维码注册会员</h4>
			</div>
		</div>
	</section>
	<script type="text/javascript">
		var content="<%=basePath%>html_member/goRegister.do?recommended=${pg.member_id}&recommended_type=2&recommended_phone=${pg.phone}";
		$(".erweima").qrcode({ 
		    render: "canvas", //table方式 
  		    text: toUtf8(content) //任意内容 
		}); 
		//格式化
		function toUtf8(str) {    
 		    var out, i, len, c;    
 		    out = "";    
 		    len = str.length;    
 		    for(i = 0; i < len; i++) {    
 		        c = str.charCodeAt(i);    
 		        if ((c >= 0x0001) && (c <= 0x007F)) {    
 		            out += str.charAt(i);    
 		        } else if (c > 0x07FF) {    
 		            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));    
 		            out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));    
 		            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
 		        } else {    
 		            out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));    
 		            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
 		        }    
 		    }    
 		    return out;    
 		} 	  
  		</script>
</body>
</html>