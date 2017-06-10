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
	<title>支付方式</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/yxkt_zffs.css">
	 <script src="js/jquery-1.8.0.min.js"></script>
	 <style type="text/css">
		 .anniu-m {
		    display: block;
		    width: 8%;
		     color: #fff;
		    background: #1187e2;
		     cursor: pointer;
		    border-radius: 2px;
		    line-height: 1.2;
		    margin-left: 29%;
		}
	 </style>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
	<ul >
		<li class="title">
			<span>第一步:选择消费场景
			</span>
		</li>
		<li>
			<ul class="clfa" style="padding-left:8%">
				<li class="check_item">
					<input type="checkbox" name="check1" value="1" id = "zhifu1" ${pd.type1 eq '1'?'checked':'' } >
					<span>到店消费</span>
				</li>
				<li class="check_item">
					<input type="checkbox" name="check1" value="2" id = "zhifu2" ${pd.type2 eq '1'?'checked':'' }>
					<span>网购自提</span>
				</li>
			</ul>
		</li>
		<li class="title">
			<span>
			第二步：选择消费者支付方式（必选）
			</span>
		</li>
		<li>
			<ul class="clfa" style="padding-left:8%">
				<li class="check_item">
					<input type="checkbox"  name="check2" value="1"  ${pd.zfb eq '1'?'checked':'' }>
					<span>支付宝支付</span>
				</li>
				<li class="check_item">
					<input type="checkbox" name="check2" value="2" ${pd.wx eq '1'?'checked':'' }>
					<span>微信支付</span>
				</li>
				<li class="check_item">
					<input type="checkbox" name="check2"  value="3" ${pd.pos eq '1'?'checked':'' }>
					<span>Apple Pay支付</span>
				</li>
				<li class="check_item">
					<input type="checkbox" name="check2"  value="4" ${pd.apple eq '1'?'checked':'' }>
					<span>pos机支付</span>
				</li>
			</ul>
		</li>
		 <c:if test="${storeqx.add eq '1'}">
		<li style="text-align:center;clear:both;">
			<span class="anniu-m" onclick="save()">确认</span>
		</li>
		 </c:if>
	</ul>
	</c:if>
	<script type="text/javascript">
    
    	function save(){
    		 var type1 = $("#zhifu1").val();
    		 var type2 = $("#zhifu2").val();
     		 var obj = document.getElementsByName('check2');
    		 var zhifu = '';
    		 for ( var i = 0; i < obj.length; i++) {
				 if(obj[i].checked) zhifu += obj[i].value+',';
			 }
     		if(zhifu==''){
    			alert('你还没有选择支付方式！');  
    			return;
    		}
     		var zhifu1 = $("#zhifu1").is(':checked');
    		var zhifu2 = $("#zhifu2").is(':checked');
    		if(zhifu1 == 1 && zhifu2 == 1){
    			 var type=type1+","+type2;
    		}else if(zhifu2 == 1 && zhifu1 == 0){
    			 var type="0,"+type2;
    		}else{
    			 var type=type1+",0";
    		}
    		$.ajax({
				type:"post",
				url:"storepc_way/save.do",
				data:"way_type="+type+"&way_status="+zhifu+"&store_id="+"${storepd.store_id}",
				success:function(data){
					alert("设置成功");
					window.location.reload(); //刷新当前页面
				}
			});  
    		  
    	}
    
    </script>
</body>
</html>