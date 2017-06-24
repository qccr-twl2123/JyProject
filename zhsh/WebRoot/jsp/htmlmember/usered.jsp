<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
     <title>选择红包</title>
    <link rel="stylesheet" href="css/htmlmember/labary/predefine.css">
</head>
<style>
    body{
        background: #eee;
    }
    .canuse{
        width: 100%;
        text-align: center;
        position: relative;
        background: inherit;
        height:2rem;

    }
    .canuse span{
        padding: 2px 4px;
        background: #eee;
        line-height: 2.3;
        width: 7rem;
        position: absolute;
        margin: auto;
        left: 0;
        right: 0;
    }
    .canuse .lineone{
        padding-bottom: 1rem;
        border-bottom: 1px solid #999;
        margin-bottom: 1rem;
        position: absolute;
        z-index: 0;
        width: 96%;
        margin-left: 2%;
        margin-right: 2%;
    }
</style>

<!-- 可用不可用 -->
<style>
    .fs_80{ 
        font-size: 80%;
    }
       
    body{
        background: #eee;
    }
    .rp_box .col_c9{
        color: #c90000;
    }
    
    .rp_item{
        width: 100%;
        margin-bottom: 52px;
    }
    .rp_box{
        width:96%;
        margin: 1% 2%;
        background: #fff;
        border-radius: 4px;
        position: relative;
        overflow: hidden;
        text-align: center;
        height: 6rem;
        padding:0.8rem;
        box-sizing: border-box;
    }
    .rp_box_use{
        opacity: 0.8;
        width:96%;
        margin: 1% 2%;
        background: #ddd;
        border-radius: 4px;
        position: relative;
        overflow: hidden;
        text-align: center;
        height: 6rem;
        padding:0.8rem;
        box-sizing: border-box;
    }

    .ball{
        width: 8px;
        height:8px;
        border-radius: 50%;
        background: #eee;
        border:2px solid rgba(0,0,0,0.02);
        position: absolute;
    }
    .ball_l{
        left:-6px;
        top:0;
        bottom: 0;
        margin: auto;
    }
    .ball_r{
        right:-6px;
        margin: auto;
        top:0;
        bottom: 0;
    }
    .cash{
        width: 32%;
        display: inline-block;
        height:100%;
        float: left;
        line-height:4rem;
    }
    .cont{
        width: 60%;
        box-sizing:border-box;
        padding-left: 1rem;
        display: inline-block;
        text-align: left;
        height: 100%;
        float: left;
        line-height: 1.9;
    }
    .tit{
        font-size: 117.5%;
    }
    .hongbao{
        position: absolute;
        right: 0;
        top: 0;
        width: 0;
        height: 0;
        border-width: 1.4rem;
        border-style: solid;
        border-color: #e90000 #e90000 transparent transparent;
    }
    .hongbao span{
        position: absolute;
        word-break: normal;
        display: block;
        width: 26px;
        color: #fff;
        font-size: 12px;
        line-height: 0;
        top: -6px;
        left: -2px;
        font-size: 119.3%;
    }
    section{
    	width: 100%;
    	height: 100%;
    	box-sizing:border-box;
    	border-top:44px solid transparent;
    	border-bottom:44px solid transparent;
    }
</style>
<style>
	.header ,.header_l{
	    width: 100%;
	    height: 44px;
	    background: #ff0600;
	    text-align: center;
	    line-height: 44px;
	    font-size: 1.2rem;
	    font-weight: bold;
	    color: #fff;
	    position: fixed;
	}
	.header .head_tit,.header_l .head_tit{
	    display: inline-block;
	    width: 60%;
	}
	.header_l span{
	    float: left;
	}
	.header_l .leftcont{
	    width: 20%;
	}
	.header_l .leftcont .imgbox{
	    float: left;
	    padding-left: 13%;
	    padding-right: 10%;
	}
	.header_l .rightcont .imgbox{
	    float: right;
	    padding-left: 10%;
	    padding-right: 13%;
	}
	.header_l .rightcont{
	    width: 20%;
	}
</style>

<style>
.footer{
	background: #fff;
	border-top:1px solid #ccc;
	position: fixed;
	bottom: 0;
	color: #666;
}
</style>
<body>
<!-- 左 -->
<div class="header_l">
     <span class="leftcont">
        <span class="imgbox" onclick="history.back()">
            <img src="img/fanhui.png" alt="">
        </span>
     </span>
    <span class='ccyc head_tit'>选择红包</span>
</div>
<section>
		<!--span颜色须于背景色相同-->
<div class="canuse">
	<div class="lineone"></div>
    <span>可用优惠券</span>
</div>
<!-- 可用 -->
<div class="rp_item">
	<c:forEach items="${canUseList}" var="var">
		<div class="rp_box clf" onclick="sureThisRed('${var.redpackage_id}')">
	        <div class="cash col_c9">
	        	<c:if test="${var.store_redpackage_type eq '1'}"> <span>￥</span> <span style="font-size:2rem;">  ${var.redpackage_money }</span></c:if>
	        	<c:if test="${var.store_redpackage_type eq '2'}"> <span style="font-size:2rem;">  ${var.redpackage_money }</span><span>折</span> </c:if>
 	        </div>
	        <div class="cont">
	            <p> <span class="tit">${var.title_name }</span> </p>
	            <p class='fs_80'> ${var.redpackage_content }</p>
	            <p class='fs_80'> 有效期至 ${var.enddate } </p>
	        </div>
	         <c:if test="${var.isready_use eq '1'}"><div class="hongbao"><span>✔</span>  </div></c:if>
	        <div class="ball ball_l"></div>
	        <div class="ball ball_r"></div>
	    </div>
	</c:forEach>
</div>
<div class="canuse">
	<div class="lineone"></div>
    <span>不可用优惠券</span>
</div>
<!-- 不可用 -->
<div class="rp_item">
	<c:forEach items="${notUseList }" var="var">
		<div class="rp_box_use clf">
	        <div class="cash col_c9">
	        	<c:if test="${var.store_redpackage_type eq '1'}"> <span>￥</span> <span style="font-size:2rem;">  ${var.redpackage_money }</span></c:if>
	        	<c:if test="${var.store_redpackage_type eq '2'}"> <span style="font-size:2rem;">  ${var.redpackage_money }</span><span>折</span> </c:if>
 	        </div>
	        <div class="cont">
	            <p> <span class="tit">${var.title_name }</span> </p>
	            <p class='fs_80'> ${var.redpackage_content }</p>
	            <p class='fs_80'> 有效期至 ${var.enddate } </p>
	        </div>
 	        <c:if test="${var.isready_use eq '1'}"><div class="hongbao"><span>✔</span>  </div></c:if>
 	        <div class="ball ball_l"></div>
	        <div class="ball ball_r"></div>
	    </div>
	</c:forEach>
</div>
</section>
<div class="header footer">
    <span class='ccyc head_tit '  onclick="sureThisRed('')">不使用红包</span>
</div>
<script type="text/javascript">
//确认使用红包
function sureThisRed(redpackage_id){
	if("${pd.pay_type}" == "3"){
		window.location.href="html_member/goStoreGoodsOverBuy.do?sk_shop=${pd.sk_shop}&redpackage_id="+redpackage_id;
	}else{
		window.location.href="html_member/sysyhmd.do?sale_money=${pd.sale_money}&no_discount_money=${pd.no_discount_money}&isredback=1&desk_no=${empty pd.desk_no?'':pd.desk_no}&sk_shop=${pd.sk_shop}&redpackage_id="+redpackage_id;
	}
}

</script>
</body>
</html>