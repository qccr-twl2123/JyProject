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
	<title>首页</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/fonts/iconfont.css">
	<link rel="stylesheet" href="css/pcstore/hsd_dlxl.css">
	<script src="js/jquery-1.8.0.min.js"></script>
</head>
 
<body>
<c:if test="${storeqx.look eq '1'}">
<!-- 遮罩 -->
<div class="dask">
<!-- 修改 -->
    <div class="alert_xg">		
        <div class="al_head">
            <div class="close"></div>
            <div class="alert_tit">
                <span>类别管理</span>
                <div class="one"></div>
            </div>
        </div>
        <ul class="al_body">
           	<li>
           		<span>父类名称：</span>
           		<select name="parent_id" id="parent_id">
            	</select>
           	</li>
           	<li>
           		<span>当前名称：</span>
           		<input type="text"  name="name" id="name" >
			</li>
           	<li>
           		<span>当前序号：</span>
           		<select name="sort" id="sort">
           			 
           		</select>
           	</li>
        </ul>           
        <div class="al_foot">
            <div class="button_box editsure" onclick="editSure('0')">
                	确定
            </div>
        </div>
    </div>


    <!-- 小类 -->
    <div class="alert_xl">		
        <div class="al_head">
            <div class="close"></div>
            <div class="alert_tit">
                <span>添加小类</span>
                <div class="one"></div>
            </div>
        </div>
        <ul class="al_body">
           	<li>
           		<span>选择大类：</span>
           		<select name="category_parent_id" id="category_parent_id" >
						<option value="0">请选择大类</option>
						<c:forEach items="${big_list}" var="big">
							<option value="${big.goods_category_id}">${big.name}</option>
						</c:forEach>
					</select>
           	</li>
           	<li>
           		<span>新建小类：</span>
           		<input type="text" id="smallname"></li>
           	<li>
           		<span>小类排序：</span>
           		<select name="smallsort" id="smallsort" class="new_p2_ipt1"  >
						<option value="0">请选择</option>
				</select> 
           	</li>
        </ul>           
        <div class="al_foot">
            <div class="button_box"  onclick="yes_small('1','smallsort')">
                确定
            </div>
        </div>
		<script type="text/javascript">
			
   			</script>
    </div>

	<!-- 大类 -->
    <div class="alert_dl">
        <div class="al_head">
            <div class="close"></div>
            <div class="alert_tit">
                <span>添加大类</span>
                <div class="one"></div>
            </div>
        </div>
        <ul class="al_body">
           	<li>
           		<span>新建大类：</span>
           		<input type="text" name="name" id="bigname" >
			</li>
           	<li>
           		<span>大类排序：</span>
           		<select  name="sort" id="bigsort" class="new_p2_ipt1">
					<option value="0">请选择</option>
				</select>
           	</li>
        </ul>           
        <div class="al_foot">
            <div class="button_box" onclick="yes_big('0','bigsort')">
                确定
            </div>
        </div>
		<script type="text/javascript">
		
  		</script>
    </div>
</div>

    <form action="storepc_CategoryManageController/showShop.do" method="post" name="Form" id="Form">
	<section>
		<div class="seccont">
			<!-- 左侧列表 -->
			<div class="itemlist">
			<!-- 插入点2  左侧导航列表 -->
			</div>
			<div class="r_cont">
				<!-- 右侧内容 -->
				<section>
					<p>说明：1、商品类别管理支持二级分类，大类最多支持6个分类，每个大类最多支持10个细类，请根据本店实际情况设置</p>
					<p>说明：2、大类仅在“按类别积分”状态下显示，在会员端上默认只显示小类。</p>
					<h4>商品类别</h4>
					<div class="tab">
					    <c:if test="${storeqx.add eq '1'}">
							<div class="btn_box clf">
								<!-- xl  dl   为点击事件 -->
								<span class="btn xl" onclick="addSmall()">添加小类</span>
								<span class="btn dl" onclick="addBig()">添加大类</span>
							</div>
						</c:if>
 						<table>
							<thead>
								<tr>
									<th>大类排序</th>
									<th>大类名称</th>
									<th>小类名称</th>
									<th>小类排序</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${allList}" var="parent">
									<tr>
										<td>${parent.sort}</td>
										<td>${parent.name}</td>
										<td></td>
										<td></td>
										<td>
											<c:if test="${storeqx.edit eq '1'}"><span  onclick="updateCate('1','${parent.goods_category_id}')" >修改</span> </c:if>
											<c:if test="${storeqx.delete eq '1'}"><span   onclick="delCate('${parent.goods_category_id}')">删除</span></c:if>
										</td>
									</tr>
									<c:if test="${!empty parent.sonList}">
										<c:forEach items="${parent.sonList}" var="son">
											<tr>
												<td></td>
												<td></td>
												<td>${son.name}</td>
												<td>${son.sort}</td>
												<td>
													<c:if test="${storeqx.edit eq '1'}"><span onclick="updateCate('2','${son.goods_category_id}')">修改</span></c:if>
													<c:if test="${storeqx.delete eq '1'}"><span  onclick="delCate('${son.goods_category_id}')">删除</span></c:if>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</c:forEach>
 							</tbody>
						</table>
						<!-- 分页导航 -->
						<div class="fenye clf">
							<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
						</div>
					</div>
				</section>
				<!-- 内容结束 -->
			</div>
		</div>
	</section>
	</form>
	</c:if>
<script src="js/jquery-1.12.4.min.js"></script>
<script>	
	// 新增大类
	function dalei(){
		$(".dask").css({"display":"block"})
		$(".alert_dl").css({"display":"block"})
	}	
	// 新增小类
	function xiaolei(){
		$(".dask").css({"display":"block"})
		$(".alert_xl").css({"display":"block"})
		$("#category_parent_id").val("0");
	} 
	// 关闭窗口
	function guanbi(){
		$(".dask").css({"display":"none"})
		$(".alert_xl").css({"display":"none"})
		$(".alert_dl").css({"display":"none"})
	}
	
	//判断排序
	function IsSortOk(sort,category_parent_id,type){
	    var n=1;
 		$.ajax({
            type:"post",
            url:'<%=basePath%>storepc_CategoryManageController/sortOk.do', 
       		data:{"sort":sort,"store_id":"${pd.store_id}","category_parent_id":category_parent_id},                
            dataType:"json",
            async:false,
            success: function(data){
            	if(data.result == "0"){
            		if(type == "edit"){
            			alert(data.message);
            		}
             		n=0;
            	}  
             }
        });
		if(n==1){
			return true;
		}else{
			return false;
		}
	}
	
	//新增小类判断排序
	function addSmall(){
		 $("#smallsort").empty();
		 $("#smallsort").append("<option value='0'>请选择</option>");
		 for(var n=1;n<11;n++){
           	 var s=n+"";
           	 if( n < 10){
           		 s="0"+s;
           	 }
           	 if(	IsSortOk(s,"","add")  ){
           		 $("#smallsort").append("<option value='"+s+"'>"+s+"</option>");
           	 }
        }
	}
	
	//新增大类判断排序
	function addBig(){
		 $("#bigsort").empty();
		 $("#bigsort").append("<option value='0'>请选择</option>");
         for(var n=1;n<7;n++){
	       	 var s="0"+n;
	       	 if(	IsSortOk(s,"0","add")  ){
	       		 $("#bigsort").append("<option value='"+s+"'>"+s+"</option>");
	       	 }
        }
	}
         
 	 
	
	 
	
	//确认添加大类
	function yes_big(value,obj) {
		if($("#bigname").val().trim() == ""){
			alert("名称不能为空");
			return false;
		}
		if($("#bigsort").val().trim() == ""){
			alert("排序不能为空");
			return false;
		}
		if(IsSortOk($("#bigsort").val().trim(),"0"),"edit"){
			$.ajax({
	            type:"post",
	            url:'<%=basePath%>storepc_CategoryManageController/addBig.do', 
	       		data:{"name":$("#bigname").val(),"sort":$("#bigsort").val().trim(),"store_id":"${pd.store_id}"},                
	            dataType:"json",
	            success: function(data){
	            	if(data.result == "1"){
 	            		window.location.reload(); //刷新当前页面
	            	}else{
	            		alert(data.message);
	            	}
	             }
	        });
		}
 	}
	
	//确认添加小类
	function yes_small(value,obj) {
		if($("#smallname").val().trim() == ""){
			alert("名称不能为空");
			return;
		}
		if($("#smallsort").val().trim() == ""){
			alert("排序不能为空");
			return;
		}
		if(IsSortOk($("#smallsort").val().trim(),"","edit")){
			$.ajax({
	            type:"post",
	            url:'<%=basePath%>storepc_CategoryManageController/addSmall.do', 
	       		data:{"name":$("#smallname").val(),"sort":$("#smallsort").val().trim(),"store_id":"${pd.store_id}","category_parent_id":$("#category_parent_id").val()} ,               
	            dataType:"json",
	            success: function(data){
	             	if(data.result == "1"){
 	            		window.location.reload(); //刷新当前页面
	            	}else{
	            		alert(data.message);
	            	}
	            }
	        });
		}
  	}
 	
	//修改
	function updateCate(type,goods_category_id){
		$(".dask").css({"display":"block"});
		$(".alert_xg").css({"display":"block"});
		$("#sort").empty();
		$.ajax({
            type:"post",
            url:'<%=basePath%>storepc_CategoryManageController/findByCateId.do', 
       		data:{"goods_category_id":goods_category_id,"store_id":"${pd.store_id}"} ,               
            dataType:"json",
            success: function(data){
            	$('.new_tc3').css('display','block');
            	$('.new_tc2').css('display','none');
        		$('.new_tc').css('display','none');
            	var pd=data.data;
            	window.goods_category_id =pd.goods_category_id;
            	window.nowsort =pd.sort;
            	$("#name").val(pd.name);
            	$("#sort").val("<option value='"+pd.sort+"'>"+pd.sort+"</option>");
             	if(type == "1"){//大类
              		$(".editsure").attr("onclick","editSure('0')");
            		$("#parent_id").empty();
            		$("#parent_id").append("<option value='0'>无</option>");
            		editdalei(pd.sort);
            	}else {//小类
             		$(".editsure").attr("onclick","editSure('1')");
            		$("#parent_id").empty();
            		$("#parent_id").append("<option value='"+pd.parent_id+"'>"+pd.parent_name+"</option>");
            		editxiaolei(pd.parent_id,pd.sort);
            	}
            	
            }
        });
 	}
	
	//修改的大类排序
	function editdalei(value){
		 $("#sort").empty();
		 $("#sort").append("<option value='"+value+"'>"+value+"</option>");
		 for(var n=1;n<7;n++){
	       	 s="0"+n;
	         if(	IsSortOk(s,"0","add")  ){
	        	 $("#sort").append("<option value='"+s+"'>"+s+"</option>");
	         }
          }
	}
	//修改的小类排序
	function editxiaolei(parent_id,value){
		 $("#sort").empty();
		 $("#sort").append("<option value='"+value+"'>"+value+"</option>");
		 for(var n=1;n<11;n++){
           	 var s=n+"";
           	 if( n < 10){
           		 s="0"+s;
           	 }
           	 if(	IsSortOk(s,"","add")  ){
           		$("#sort").append("<option value='"+s+"'>"+s+"</option>");
           	 }
         }
	}
	
	//确认修改
	function editSure(value){
		if($("#name").val().trim() == ""){
			alert("名称不能为空");
			return;
		}
		if($("#sort").val().trim() == ""){
			alert("排序不能为空");
			return;
		}
		var sort=$("#sort").val();
		var category_parent_id="0";
		if(value=="1"){
			category_parent_id=$("#parent_id").val();
		}
		if(nowsort==sort ||	IsSortOk(sort,category_parent_id,"edit")    ){
			window.location.href="<%=basePath%>storepc_CategoryManageController/editCate.do?name="+$("#name").val()+"&store_id="+"${pd.store_id}"+"&category_parent_id="+$("#parent_id").val()+"&sort="+$("#sort").val()+"&goods_category_id="+goods_category_id;
		}else{
			alert(data.message);
    		$(obj).val("");
		}
	}
	
	//删除这个大小类
	function delCate(value){
		$.ajax({
            type:"post",
            url:'<%=basePath%>storepc_CategoryManageController/delCate.do', 
       		data:{"goods_category_id":value,"store_id":"${pd.store_id}"},                
            dataType:"json",
            async:false,
            success: function(data){
            	if(data.result == "0"){
            		 alert(data.message);
            	}else{
            		window.location.reload(); //刷新当前页面
            	}
             }
        });
	}


$(".xl").click(function(){
	xiaolei()
})
$(".dl").click(function(){
	dalei()
})
$(".change").click(function(){
	xiugai()
})
$(".close").click(function(){
	guanbi()
})
$(".button_box").click(function(){
	guanbi()
})
</script>
</body>
</html>