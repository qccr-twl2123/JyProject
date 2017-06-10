 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>首页</title>
<meta charset="utf-8">
<base href="<%=basePath%>">
<link rel="stylesheet" href="css/storepc/business_homepage7.css">
<script src="js/jquery-1.8.0.min.js"></script>
<script src="js/jquery-1.7.2.js"></script>
<script src="js/storepc/business_homepage7.js"></script>
<script src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<div class="parent">
		<div class="d1">
				<span class="d1_sp1 ">
					<a href="/zhsh/storepc_myleague/goOwnLeader.do?store_id=${storepd.store_id}"> 我是盟主</a> 
				</span>
				<span class="d1_sp1"> 
				 	<a	href="/zhsh/storepc_myleague/goOtherLeader.do?store_id=${storepd.store_id}"> 我是盟友</a> 
				</span>
				<c:if test="${storeqx.add eq '1'}">
					<span class="d1_sp1 d1_sp1_active">
					    <a href="/zhsh/storepc_myleague/goAddStoreUnion.do?store_id=${storepd.store_id}"> 新建联盟</a> 
					</span>
				</c:if>
		</div>
 		<form action="storepc_myleague/addStoreUnion.do?store_id=${storepd.store_id}" method="post" name="Form" id="Form">
			<input type="hidden" name="store_id" value="${storepd.store_id}">
			<input type="hidden" name="allstore_id" id="allstore_id" value="0">
			<input type="hidden" name="type" id="type" value="0">
			<div class="d2">
				<span class="d2_sp1 mgleft5">联盟名称</span>
				<input class="d2_ipt1" type="text" name="name" id="name"/> 
				<span class="d2_sp1 ">起止时间</span>
				<input onchange="startbijiao(this)" value="" placeholder="开始时间" class="d2_ipt1" type="text" name="starttime" id="starttime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				 至 
				<input onchange="endbijiao(this)"  placeholder="结束时间" value="" class="d2_ipt1" type="text" name="endtime" id="endtime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			</div>
			<script type="text/javascript">
			$(function(){ 
				var thedate = new Date(); 
				var datestr=thedate.getFullYear() + '-' + (parseInt(thedate.getMonth())+1) + '-' + thedate.getDate();
				$("#starttime").val(datestr);
		 	});
			</script>
			<div class="d2">
				<span class="d2_sp1 mgleft5">邀请说明</span><input
					class="d2_ipt1 d2_ipt2" type="text" name="invite_desc" />
			</div>
			<div class="d2">
				<span class="d2_sp1 mgleft5"></span>
				<input class="d2_ipt1" type="text" name="content" id="content" placeholder="输入商家名称搜索"/> 
 				<a class="hands" onclick="searchStore()" style="color:blue;"><img src=""><span class="ssmy"><span class="sstp"><img/></span>搜索盟友</span><span style="color:red;font-size:12px;">*可根据商家名称以及负责人，商家电话进行检索*</span></a>
 			</div>
			<table border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<td>序号</td>
						<td>商家名称</td>
						<td>商家地址</td>
						<td>负责人</td>
						<td>联系电话</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody id="allStore">
				</tbody>
			</table>
			<div class="d3">
				<p>（说明：当你向盟友发出邀请后，你的一度人脉数、二度人脉数、本店会员数对方将可以看到。）</p>
			</div>
			<!-- <span class="bc" onclick="save('0')">仅保存</span> --> 
			<span class="play" onclick="save('1')">保存并发送通知</span>
		</form>
	</div>
	<script type="text/javascript">
 
	var allstore_id="0";//搜索出的所有商家ID
	//查询
	function searchStore(){
		if($("#content").val() == ""){
 			return;
		}
		$.ajax({
			url:"storepc_myleague/findStore.do",
			type:"GET",
			dataType:"json",
			data:{"content":$("#content").val() },
			success:function(data){
				var storeList=data.data;
				for(var i=0;i<storeList.length;i++){
 					var str="<tr><td>"+(i+1)+"<input type='hidden' class='unionstore' value='"+storeList[i].store_id+"'></td><td>"+storeList[i].store_name+"</td><td>"+storeList[i].address+"</td><td>"+storeList[i].principal+"</td><td>"+storeList[i].phone+"</td><td><a onclick='del(this)' class='hands' style='color:blue'>删除</a></td></tr>";
					$("#allStore").append(str);
				}
			}
		});
	}
	
	
	//保存
	function save(value){
		allstore_id=""; 
 		$(".unionstore").each(function(n,o){
			allstore_id+=$(o).val()+"@"; 
		})
		$("#allstore_id").val(allstore_id); 
		$("#type").val(value);
		if($("#name").val() == ""){
			alert("联盟名称不能为空");
			return;
		}
		if($("#endtime").val() == ""){
			alert("结束时间不能为空");
			return;
		}
		if($("#starttime").val() == ""){
			alert("开始时间不能为空");
			return;
		}
		if(allstore_id == ""){
			alert("商家不能为空");
			return;
		}
		//提交
		$("#Form").submit();
	}
	
	//删除
	function del(obj){
		$(obj).parent().parent().remove();
	}
	
	//开始时间的比较
	function startbijiao(obj){
		if($(obj).val() == null ||$(obj).val() == ""){
			return;
		}
		var thedate = new Date(); 
		var datestr=thedate.getFullYear() + '-' +(parseInt(thedate.getMonth())+1) + '-' + thedate.getDate();
		if((new Date(datestr.replace(/-/g,"\/"))) > (new Date($(obj).val().replace(/-/g,"\/")))){
			alert("时间不能选择当前时间之前");
			$(obj).val(datestr);
		}
		if((new Date($("#endtime").val().replace(/-/g,"\/"))) <(new Date($(obj).val().replace(/-/g,"\/")))){
			alert("开始时间不能超过结束时间");
			$(obj).val(datestr);
		}
 	}
	
	
	//结束时间的比较
	function endbijiao(obj){
		if($(obj).val() == null ||$(obj).val() == "" || $("#starttime").val() == ""){
			return;
		}
		if((new Date($("#starttime").val().replace(/-/g,"\/"))) > (new Date($(obj).val().replace(/-/g,"\/")))){
			alert("开始时间不能超过结束时间");
			$(obj).val($("#starttime").val());
		}
 		if((new Date("${pd.endtime}".replace(/-/g,"\/"))) > (new Date($(obj).val().replace(/-/g,"\/")))){
			alert("结束时间不能超过该商家的签约时间");
			$(obj).val("${pd.endtime}");
		}
 	}
	
	</script>
</body>
</html>