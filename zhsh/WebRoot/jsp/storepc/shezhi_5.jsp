 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<% String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>设置班次</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
    <link rel="stylesheet" href="css/pcstore/hsd_classes.css">
    <link rel="stylesheet" href="css/pcstore/bootstrap.min.css">
</head>
<body onkeydown="BindEnter(event)">
<div class="dask">
    <div class="alert">
        <div class="al_head">
            <div class="close"></div>
            <div class="alert_tit">
                <span>创建班次</span>
                <div class="one"></div>
            </div>
        </div>
        <div class="al_body">
            <form role="form " action="<%=path %>/storepcOperator_file/saveShift.do" method="post" id="Form">
				<input type="hidden" name="jichushezhi"   value="${pd.jichushezhi}"/>
				<input type="hidden" name="store_id" value="${pd.store_id}"/>
                <div class="form-group col-xs-6">
                    <label>请输入新增加班次</label>
                    <input type="email" class="form-control"  placeholder="请填写班次" name="shift_name" >
                    <p class="help-block">注：最少添加一个班次</p>
                </div>
            </form>
        </div>
        <div class="al_foot">
            <div class="button_box">
                <div class="next clo">取消</div>
                <div class="next" id="save">确定</div>
            </div>
        </div>
    </div>
</div>
<div class="bg">
    <header>
        <div class="head_cont">
            <img src="img/page/classes.png" alt="" class="logo">
            <div class="title">•  班次 </div>
            <div class="one"></div>
        </div>
    </header>
    <section>
         <div class="button_box">
            <div class="butt butt_next">创建班次</div>
         </div>
         <ul class="sec_cont">
			<c:forEach items="${varList}" var="vs">
 					<li>${vs.shift_name }</li>
	        </c:forEach>
        </ul>
    </section>
    <footer>
        <div class="font_cont">
            <div class="button_box"  onclick="gonext()" id="gonext">
                <div class="butt">前往下一步</div>
            </div>
        </div>
    </footer>
</div>
</body>
<script src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
		 //新增
    	$("#save").click(function(){
    		$("#Form").submit();
    	});
		 
		//下一步
		function gonext(){
            if("${varList}".length > 2 ){
            		window.location.href='<%=basePath%>storepc/goSheZhiOne.do?store_id=${pd.store_id}&jichushezhi=${pd.jichushezhi}';
            }else{
            		alert("至少添加一个班次");
            		return;
            }
        }
		//使用document.getElementById获取到按钮对象
		function BindEnter(event){
			var gonext = document.getElementById("gonext");
			if(event.keyCode == 13){
				gonext.click();
				event.returnValue = false;
			}
		}
		//点击新建
		$(".butt_next").click(function() {
	        $(".dask").css({"display":"block"})
	        $(".close").click(function(){
	            $(".dask").css({"display":"none"})
	        })
	        $(".clo").click(function(){
	            $(".dask").css({"display":"none"})
	        })
	    });
</script>
</html>