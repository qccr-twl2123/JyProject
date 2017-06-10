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
		<script type="text/javascript" src="<%=basePath%>js/htmlmember/jquery.min.js"></script>
		<link rel="stylesheet" href="<%=basePath%>yyg/css/bootstrap.min.css">
		<style type="text/css">
		img{
          width: 100%;
          outline-width:0px;  
  		  vertical-align:top;
        }
		.shangbu{
		   width:100%;height:312px;display: block;
 		}
		.zhongjian{
			width:100%;
			margin:0 auto;
    	}
   		.zhongjian li{
   		    display: block;
		    width: 47%;
		    margin: 1% 0;
		    margin-left: 1.5%;
		    float: left;
		    height: 252px;
		    background-color: #fff;
		    border: 1.8px solid #E80707;
		    border-radius: 9px;
    	}
   		.zhongjian li .one{
   			    border-top-left-radius:9px;              
				border-top-right-radius:9px; 
				width:100%;
				height:70%;           
   		}
    	.zhongjian li .three{
   			    display: block;
			    height: 30%;
			    border-bottom-right-radius:9px;     
				border-bottom-left-radius:9px;  
				    padding: 6px 5px;     
    	}
    	.zhongjian li .two{
   			    display: block;
   			    width:100%;
			    height: 40%;
			    font-size: 17px;
			    text-align: left; 
			    overflow: hidden;
			    white-space: nowrap;
			    text-overflow: ellipsis;         
   		}
    	.zhongjian li .three .oneleft{
    			display: block;
    			width:65%;
    			float:left;
 			    height: 60%;
     	}
    	.zhongjian li .three .oneright{
    		    display: block;
			    width: 34%;
			    height: 43%;
 			    float: right;
 			    text-align: center;
			    margin-top: 5%;
			    margin-right: 1%;
			    border-radius: 3px;
			    background-color: #FB0A0A;
			    padding-top: 3px;
			    color: #fff;
    	}
    	.zhongjian li .three .oneleft .oneleftonespan{
   			    display: inline-block;
   			    width:100%;
			    font-size: 13px;
			    color:#BB0808;
    	}
    	.zhongjian li .three .oneleft .onelefttwospan{
   			    display: inline-block;
   			    width:100%;
			    height: 46%;
    	}
 		.footerdi {
		        position: fixed;
			    bottom: 0;
			    z-index: 100;
			    width: 100%;
			    border-top: 1px solid #b3b3b3;
			    padding: 5px 0;
			    background: #DDB036;
			    height: 12%;
			    opacity: 0.9;
		}
 		.footerdi div{
		    display: inline-block;
		    width: 41%;
		    height: 64px;
 		    margin-top: 4%;
		    margin-left: 6%;
		    margin-bottom: 2%;	
 		}
 		.footerdi .footone{
		    display: inline-block;
		    width: 100%;
     		border-radius: 30px;
		}
		.footerdi .foottwo{
		    display: inline-block;
		    width: 100%;
     		border-radius: 30px;	
		}
		.xfbz{
			position: fixed;
		    bottom: 12%;
		    z-index: 100;
		    width: 49px;
		    height: 49px;
		    left: 85%;
		}
 		</style>
  </head>
<body style="background-image: url('../../yyg/yyg2.png');">
<c:if test="${pd.type eq '2' }">
	<nav class="top">
 		<a href="javascript:window.history.back();"><b class="back-arrow fl"></b></a>
		<div style="text-align:center;line-height:40px;color:#fff">一元夺宝</div>
	 </nav>
</c:if>
<script type="text/javascript">
 			if("${pd.type}" == "1"){
 				$("body").css("padding-top","0");
 			}
 		
</script>
<img alt="" src="../../yyg/yyg.png" class="shangbu">
<div class="zhongjian">
 	 <ul>
 	 	<li>
 	 		<img  src="../../yyg/yyg.png"  class="one">
  	 		<div class="three">
  	 		    <span class="two">一元夺宝。212121111111111111。。。</span>
 	 			<span class="oneleft">
 	 				<span class="oneleftonespan">界桥进度65%</span><br/>
 	 				<span class="onelefttwospan">
	 	 				<div class="progress-bar progress-bar-info progress-bar-striped active" style="width: 65%;"> </div>
 	 				</span>
 	 			</span>
 	 			<span class="oneright">马上抢</span>
 	 		</div>
 	 	</li>
  	 	<li>
 	 		<img  src="../../yyg/yyg.png"  class="one">
  	 		<div class="three">
  	 		    <span class="two">一元夺宝。212121111111111111。。。</span>
 	 			<span class="oneleft">
 	 				<span class="oneleftonespan">界桥进度15%</span><br/>
 	 				<span class="onelefttwospan">
 	 					<div class="progress-bar progress-bar-danger progress-bar-striped active" style="width: 15%;"></div>
 	 				</span>
 	 			</span>
 	 			<span class="oneright">马上抢</span>
 	 		</div>
 	 	</li>
 	 	<li>
 	 		<img  src="../../yyg/yyg.png"  class="one">
  	 		<div class="three">
  	 		    <span class="two">一元夺宝。212121111111111111。。。</span>
 	 			<span class="oneleft">
 	 				<span class="oneleftonespan">界桥进度25%</span><br/>
 	 				<span class="onelefttwospan">
 	 					<div class="progress-bar progress-bar-success progress-bar-striped active" style="width: 25%;"></div>
 	 				</span>
 	 			</span>
 	 			<span class="oneright">马上抢</span>
 	 		</div>
 	 	</li>
 	 	<li>
 	 		<img  src="../../yyg/yyg.png"  class="one">
  	 		<div class="three">
  	 		    <span class="two">一元夺宝。212121111111111111。。。</span>
 	 			<span class="oneleft">
 	 				<span class="oneleftonespan">界桥进度35%</span><br/>
 	 				<span class="onelefttwospan">
 	 					<div class="progress-bar progress-bar-warning progress-bar-striped active" style="width: 35%;"></div>
 	 				</span>
 	 			</span>
 	 			<span class="oneright">马上抢</span>
 	 		</div>
 	 	</li>
  	 </ul>
</div>
<div style="width:100%;clear: both;height: 75px;"></div>
<div class="footerdi">
		<div><img  src="../../yyg/dbgz.png"  class="footone"></div><!--  -->
		<div><img  src="../../yyg/wddbjl.png"  class="foottwo"></div><!-- 我的夺宝记录 -->
</div>
<!-- 置顶 -->
<a href="#"><div class="xfbz"> <img  src="../../yyg/xfbz.png" ></div></a>
</body>
</html>
