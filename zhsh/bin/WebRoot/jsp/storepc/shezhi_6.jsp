 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<% String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>桌号</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
    <link rel="stylesheet" href="css/pcstore/hsd_tablenumber.css">
    <link rel="stylesheet" href="css/pcstore/bootstrap.min.css">
</head>
<body  onkeydown="BindEnter(event)">
<div class="dask">
    <div class="alert">
        <div class="al_head">
            <div class="close"></div>
            <div class="alert_tit">
                <span>创建桌号</span>
                <div class="one"></div>
            </div>
        </div>
        <div class="al_body">
            <form role="form ">
                <div class="form-group col-xs-6 hg">
                    <label class="hg">请输入新增加的桌号</label>
                    <input type="email" class="form-control hg"  placeholder="请填写桌号" id="table_name">
                    <p class="help-block hg">注：最少添加一个桌号</p>
                    <h6>提示：桌号信息如有修改和增加，请第一时间联系您的服务商，及时为您更换桌贴二维码。</h6>
                </div>
            </form>
        </div>
        <div class="al_foot">
            <div class="button_box">
                <div class="next clo">取消</div>
                <div class="next"  id="save">确定</div>
            </div>
        </div>
    </div>
</div>
<div class="bg">
    <header>
        <div class="head_cont">
            <img src="img/page/TableNumber.png" alt="" class="logo">
            <div class="title">•  桌号 </div>
            <div class="one"></div>
        </div>
    </header>
    <section>
        <div class="button_box">
            <div class="butt butt_next">创建桌号</div>
        </div>

        <ul class="sec_cont">
			 <c:forEach items="${varList}" var="vs">
 		         <li class="col-xs-2">
					<span class="col-xs-9">${vs.table_name }</span>
				</li>  
 	         </c:forEach>
         </ul>
    </section>
    <footer>
        <div class="font_cont">
            <div class="button_box" onclick="gonext()" id="gonext"> 
                <div class="butt">前往下一步</div>
            </div>
        </div>
    </footer>
</div>
</body>
<script src="js/jquery-1.12.4.min.js"></script>
 <script type="text/javascript">
 //下一步
		 function gonext(){
             	if("${varList}".length > 2 ){
            		window.location.href='<%=basePath%>storepc/goSheZhiOne.do?store_id=${pd.store_id}&jichushezhi=${pd.jichushezhi}';
            	}else{
            		alert("至少添加一个桌号");
            	}
             }
 //确认新增
    	 $("#save").click(function(){
     		var table_name = $("#table_name").val().trim();
    		if(table_name == "" || table_name == null){
    			alert("桌号不能为空！");
    			return ;
    		}
    		$.ajax({
				type:"post",
				url:'<%=path %>/storepc_tableNumber/save.do',
				data:{
					table_name:table_name,store_id:"${pd.store_id}" 
				},
				success:function(data){
   					if(data.result == "00"){	
						alert("新增失败，请联系管理员！");
	 				}else if(data.result == "02"){
	 					alert("桌号已存在！");
	 				}else if(data.result == "01"){
 	 					window.location.reload(); //刷新当前页面
	 				}
				}
			});
    	}); 
    	 
//使用document.getElementById获取到按钮对象
 		function BindEnter(event){
 			var gonext = document.getElementById("gonext");
 			if(event.keyCode == 13){
 				gonext.click();
 				event.returnValue = false;
 			}
 		}
     </script>
<script>
    $(".butt_next").click(function() {
        $(".dask").css({"display":"block"})
        $(".close").click(function(){
            $(".dask").css({"display":"none"})
        })
        $(".clo").click(function(){
            $(".dask").css({"display":"none"})
        })
    })
</script>
</html>