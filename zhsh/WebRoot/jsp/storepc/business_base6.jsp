<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<% String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>桌号</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/jcxx_bc.css">
	<script src="js/storepc/jquery-1.8.0.min.js"></script>
	<style type="text/css">
	.tk_box{
			display: none;
			position: fixed;
			z-index: 1;
			width: 100%;
			height: 100%;
		}
		.tk_box ul{
			width:370px;
			height: 10%;
			min-height: 76px;
			border: 1px solid#ccc;
			background: #fff;
			position: absolute;
			left: 0;
			right: 0;
			bottom: 0;
			top: 0;
			margin: auto;
			border-radius: 6px;
		}
	
	</style>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
<div class="tk_box">
<ul >
	<li style="height:50%;line-height:2.5;">
	<span class="padd_l">确定删除?</span>
	</li>
	<li style="text-align:right;height:50%;line-height:2.5;padding-right:20px;">
	<span class="anniu-m qd">确定</span>
	<span class="anniu-m qx">取消</span>
	</li>
</ul>
</div>
<!-- 遮罩 -->
<div class="dask">
<!-- 修改 -->
<form action="storepcOperator_file/saveShift.do" method="post" id="Form">
    <input type="hidden"  name="store_id"  value="${pd.store_id }" />
    <div class="alert_xg">		
        <div class="al_head">
            <div class="close"></div>
             <div class="alert_tit">
                <span >新建班次</span>
                <div class="one"></div>
            </div>
         </div>
        <ul class="al_body">
           	<li>
           		<span>班次名称：</span>
           		<input type="text" maxlength="5" placeholder="请输入新增加的班次" class="shift_name"  name="shift_name"   id = "table_name">
           	</li>
           	<li style="text-align:center; line-height:9;">
           		<span class="anniu-m go" id="save" >确定</span>
           		<span class="anniu-m return">取消</span>
           	</li>
        </ul>           
     </div>
</form>
</div>

	<ul>
		<li style="text-align:right;line-height:2.5;">
			<c:if test="${storeqx.add eq '1'}"><span class="anniu-l xinjian" style="margin-right:10%;">新建班次</span></c:if>
		</li>
		<li >
			<ul class="bc_list_box">
				  <c:forEach items="${varList }" var="vs">
						<li >${vs.shift_name } <a  style="display:none"  href="storepcOperator_file/delete.do?store_shift_id=${vs.store_shift_id}&store_id=${vs.store_id}"></a></li>
				  </c:forEach>
			</ul>
		</li>
	</ul>
	</c:if>
	<script type="text/javascript">
    	 $("#save").click(function(){
    		 if($(".shift_name").val().trim() == ""){
    			 alert("请填写班次，班次不能为空");
    			 return;
    		 }
    		$("#Form").submit();
    	});
    </script>
</body>
<script src="js/jquery-1.12.4.min.js"></script>
<script>
	function tianjia(){
		$(".dask").css({"display":"block"})
		$(".alert_xg").css({"display":"block"})
	}
	function guanbi(){
		$(".dask").css({"display":"none"})
		$(".alert_xl").css({"display":"none"})
		$(".alert_dl").css({"display":"none"})
	}
	$(".xinjian").click(function(){
		tianjia()
	})
	$(".close").click(function(){
		guanbi()
	})
	$(".anniu-m").click(function(){
		guanbi()
	})

	
	$(".bc_list_box").mouseenter(function(e){
		add_remove(e)
	});
	
	function add_remove(e){
		$(".bc_list_box li div").remove()
			var ev=e||window.event
			var elem=ev.target||ev.srcElement
			if (elem.nodeName=="LI") {
				if ($(elem)[0].childNodes.length==2) {
					var div=document.createElement("div")
					div.innerHTML="删除"
					div.className="closeto"
					div.onclick=function(){
							$(".tk_box").css({"display":"block"})
							$(".qd").click(function(){
								$(elem).children("a")[0].click();
 							})
							$(".qx").click(function(){
								$(".tk_box").css({"display":"none"})
							})
 					}
					div.onmouseout=function(e){$(e.target).remove();$(".bc_list_box li div").remove();}
					$(elem).append(div)
				}
			}			
	}
 
	 
	
</script>
</html>