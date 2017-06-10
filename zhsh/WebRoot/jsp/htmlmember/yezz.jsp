<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
    <title>营业执照</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="css/htmlmember/labary/predefine.css">
    <script src="js/htmlmember/library/jquery-1.12.4.min.js"></script>
</head>
<style>
    ul{
        width: 50%;
    }
    ul li{
        box-sizing: border-box;
        padding: 2%;
    }
    ul li img{
        width: 100%;
    }
    .fll{
        float: left;
    }
    .flr{
        float: right;
    }
    .img_show_box{
        position: fixed;
        width: 100%;
        height:100%;
        top:0;
        z-index: 9;
        background: rgba(0,0,0,0.4);
    }
    .img_show_box img{
        width: 70%;
        border: 5px solid #fff;
        position: fixed;
        z-index: 10;
        left:0;
        right:0;
        top:0;
        bottom: 0;
        margin: auto;
    }
    .local_box{
        width:100%;
        height:40px;
        background:#ff0600;
        box-sizing:border-box;
        position: relative;
        color: #fff;
    }
    .local_cont_box{
        width:60%;
        height:100%;
        margin:0 auto;
        text-align: center;
        overflow: hidden;
    }
    .local_cont_box img{
        height: 20px;
        padding: 10px 0;
    }
    .local_cont_box span{
        vertical-align: top;
        line-height: 40px;
        font-size: 100%;
    }
    .icon_left{
        font-size: 100%;
        position: absolute;
        left: 0;
        top:0;
        vertical-align: top;
        line-height: 50px;
        padding: 0 3.6%;
        font-weight: bold;
    }
</style>
<body>
<div class="local_box guding">
    <div class="local_cont_box">
        <span class="title">营业执照</span>
    </div>
    <span class="icon_left local_l" onclick="back_url()"><img src="img/fanhui.png" alt=""></span>
</div>
<script type="text/javascript">
function back_url(){
	window.location.href="javascript:window.history.back()";
}
</script>
<!--图片按顺序  奇数放入  fll-->
            <!--偶数放入  flr-->
<div class="img_box change">
	<!-- 代码 开始 -->
     <ul class=" clf fll">  <!--左-->
    	<c:if test="${pd.image_one ne ''}">
			<li><img src="${pd.image_one}"></li>
 		</c:if>
 		<c:if test="${pd.image_three ne ''}">
			<li><img src="${pd.image_three}"></li>
 		</c:if>
 		<c:if test="${pd.image_fix ne ''}">
			<li><img src="${pd.image_fix}"></li>
 		</c:if>
     </ul>
    <ul class=" clf flr">  <!--右-->
    	<c:if test="${pd.image_two ne ''}">
			<li><img src="${pd.image_two}"></li>
 		</c:if>
 		<c:if test="${pd.image_four ne ''}">
			<li><img src="${pd.image_four}"></li>
 		</c:if>
    </ul>
</div>

</body>
<script>
    $(function(){
//        视区  高度
        var hei=$("body").height();
        var height=0;
        for (var i=0;i<2;i++){
            height=Number($($("body").children(".guding")[i]).height())+height
        }
        $(".change").height(hei-height);
        $(".change").css("overflow-y","scroll")
        //图片全屏预览
        $(" ul img").mousedown(function(){
            var url=this.url;
            var show_box=document.createElement("div");
            var show_img=document.createElement("img");
            show_img.src=this.src;
            show_box.className="img_show_box";
            $(show_box).append(show_img);
            $("body").append(show_box);
            $(".img_show_box").click(function () {
                $(".img_show_box").remove()
            })
        })
    })

</script>
</html>