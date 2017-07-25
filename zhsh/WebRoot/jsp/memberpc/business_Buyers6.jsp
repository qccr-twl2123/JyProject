<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>基本信息</title>
	<base href="<%=basePath%>">
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <link rel="stylesheet" href="css/memberpc/library/predefine.pc.css">
    <link rel="stylesheet" href="css/memberpc/font/iconfont.css">
    <link rel="stylesheet" href="css/memberpc/shouye.css">
    <link rel="stylesheet" href="css/memberpc/user_inf.css">
    <link rel="stylesheet" href="js/memberpc/library/cropper.min.css">
    <script type="text/javascript">
    var base_inf={
    	    base_href:"<%=basePath%>" 
      };
    </script> 
 </head>
<body>
<!--头部-->
<header>
    <div class="top_bar">
        <div class="tp_bar_box inner_width">
            <a href="storepc/goLogin.do">
                <span>商家登陆</span>
            </a>
            <a href="storepc/goRegister.do">
                <span>商家加盟</span>
            </a>
            <c:choose>
            	<c:when test="${ !empty memberpd }">
            		 <a href="memberorderPc/orderInfoList.do?area_name=${pd.area_name}&city_name=${pd.city_name}">
		                <span>我的订单</span>
		            </a>
            		<a href="memberpc/memberInfoList.do?area_name=${pd.area_name}&city_name=${pd.city_name}">
		                <span>${memberpd.name}</span>
		            </a>
		            <a href="memberpc/loginOut.do">
		                <span>退出登录</span>
		            </a>
            	</c:when>
            	<c:otherwise>
            		 <a href="memberpc/goMemberLogin.do">
		                <span>我的订单</span>
		            </a>
            		<a href="memberpc/goMemberLogin.do">
		                <span>会员登陆</span>
		            </a>
		            <a href="memberpc/goMemberRegister.do">
		                <span>会员注册</span>
		            </a>
            	</c:otherwise>
            </c:choose>
            <a>
                <span>常见问题</span>
            </a>
            <a>
                <span class="show_ewm">手机九鱼
                    <div class="ewm_appdown">
                        <img src="img/three.png" alt="" class="sanjiao">
                        <img src="img/downmemberapp.png" alt="" class="ewm" >
                    </div>
                </span>
            </a>
        </div>
    </div>
    <div class="head_bar">
        <div class="head_bar_box inner_width">
            <span class="img_box" onclick="goindex()">
                <img src="img/logo.png" alt="">
            </span>
            <span class="sel_box">
                <select name="city" id="loc_shi" class="select_item" onchange="addsearcharea()">
                    <c:if test="${!empty pd.city_name}">
                    	<option value="">${pd.city_name}</option>
                     </c:if>
                    <c:forEach items="${cityList }" var="var" >
                    	<option value="${var.city_id }">${var.city_name }</option>
                    </c:forEach>
                 </select>
                <select name="area" id="loc_qu" class="select_item" onchange="shaixin()">
                	  <c:if test="${!empty pd.area_name }">
                    	<option value="">${pd.area_name}</option>
                      </c:if>
                	 <c:forEach items="${areaList }" var="var" >
                	 	<option value="${var.area_id }">${var.area_name }</option>
                	 </c:forEach>
                 </select>
            </span>
            <span class="inp_box">
                <input type="text" id="sj_search" placeholder="根据商家名称/地址进行检索" class="input_item" >
                <div class="search_btn" onclick="search_click()"></div>
            </span>
        </div>
    </div>
</header>
<!--导航栏-->
<nav>
    <div class="nav_box inner_width">
        <span class="all_list" onmouseover="nav_mouseover()" onmouseleave="nav_mouseleave()"><span class="alllist_tit">全部分类</span>
               <ul class="xl_list cycc">
               		<c:forEach items="${firstList}" var="var">
               			 <li onmouseover="li_mouseover(this)" onmouseout="li_mouseleave(this)" s_id="${var.city_file_sort_id}"  s_type="${var.sort_type}" s_name="${var.sort_name}" onclick="checkedByCitySort(this)" > 
			                     <p class="item_color">  
				                     <div class="iconfont  ${var.sort_name}" style="float: left"></div> 
				                    	${var.sort_name}
				                     <div class="iconfont icon-jinru1" style="float:right"></div>  
			                     </p>  
 			                       <ul class="two_list ccyc" onmouseover="two_over(this)" onmouseout="two_leave(this)">
			                           <li class='two_list_title'>${var.sort_name}</li> 
			                            <li style="overflow-x: hidden;overflow-y: scroll;" class="style_scroll">
 			                           <c:forEach items="${var.twoList}" var="var2">
 			                               <p s_id="${var2.city_file_sort_id}" s_type="${var2.sort_type}" s_name="${var2.sort_name}"  onclick="checkedByCitySort(this)">${var2.sort_name}</p>
 			                           </c:forEach>
			                            </li>
 			                       </ul>
 		                 </li> 
                		</c:forEach>
                </ul>
        </span>
        <span onclick="goindex()">
            <a >首页</a>
        </span>
        <span  onclick="checked('','6')">
            <a >最新上线</a>
        </span>
        <span onclick="checked('1','')">
            <a >首单立减</a>
        </span>
        <span onclick="checked('','1')">
            <a >智能排序</a>
        </span>
        <span onclick="checked('','3')">
            <a >人气递减</a>
        </span>
        <span onclick="checked('','2')">
            <a >距离远近</a>
        </span>
        <span onclick="checked('','5')">
            <a >销量递减</a>
        </span>
        <span onclick="checked('','4')">
            <a >积分率递减</a>
        </span>
    </div>
</nav>
<!--导航栏结束-->
<div class="nav_title inner_width">
    <p>用户信息</p>
</div>
<!--基础信息-->
<section class="inner_width ccyc" style="height: auto;padding-bottom: 7%;">
    <form action="" name="Form" id="Form"  >
        <input type="hidden" name="member_id" value=""/>
        <ul class="inf_list ccyc">
            <li>
                <span class="title">
                    <span class="name" >用户名</span>
                    <span>：</span>
                </span>
                <span>
                    <input type="text" name = "name" value="${mpd.name}">
                </span>
            </li>
            <li>
                <span class="title">
                    <span class="name">登陆账号</span>
                    <span>：</span>
                </span>
                <span>
                    ${mpd.phone}
                </span>
            </li>
            <li>
                <span class="title">
                    <span class="name">性别</span>
                    <span> ：</span>
                </span>
                <span class="danxuankaung">
                    <input type="radio" name="xb"  ${mpd.sex eq '1'?'checked':'' } value="1" /><span>男</span>
                    <input type="radio" name="xb"  ${mpd.sex eq '2'?'checked':'' } value="2" /><span>女</span>
                    <input type="radio" name="xb"   ${mpd.sex eq '0'?'checked':'' } value="0" /><span>保密</span>
                    <input name="sex" value="${mpd.sex }" id="sex" type="hidden">
                 </span>
            </li>
           <%-- <li>
                <span class="title">
                    <span class="name">生日</span>
                    <span> ：</span>
                </span>
                <span>
                    <input type="date" name="birthday" value="${mpd.birthday}"><span style="color:#aaa;">填写生日有惊喜哦~</span>
                </span>
            </li>
            <li>
                <span class="title">
                    <span class="name">电子邮箱</span>
                    <span> ：</span>
                </span>
                <span>
                    <input type="email" name="email" value="${mpd.email}">
                    <!--此处type写为email时   输入非邮箱时  无法提交-->
                    <!--故下立即验证按钮不需要-->
                    <!--<span class="yz_button">立即验证</span>-->
                </span>
            </li>
             <li>
                <span class="title">
                    <span class="name">真实姓名</span>
                    <span> ：</span>
                </span>
                <span>${mpd.real_name}</span>
            </li> --%>
        </ul>
        <ul class="img_change ccyc">
            <li >
                <img src="${mpd.image_url}" alt="" onclick="imgChange(this)" class="img-preview cp"  id="image_url">
            </li>
            <li>
                <span>点击图片修改头像</span>
            </li>
        </ul>
         <p style="text-align: center;padding: 10px 0;" class="ccyc">
            <span onclick="editMemberInfor()" class="qr_button">确认</span>
        </p>
    </form>
    <form action="" name="imgForm" id="imgForm">
    	<input name="imgbase64" value=" " id="base64url" type="hidden">
    </form>
</section>

<!--图片剪切弹窗-->
<div class="dask">
    <div class="img_chage_dask">
        <div class="dingbu">
            <h4>修改图片</h4>
            <span class="close" onclick="dask_hide()">x</span>
        </div>
        <div class="change_img_box ccyc">
            <div class="left_box">
                <img src="" alt="">
            </div>
            <div class="right_box">
                <div class="showbox mg_b_4 ccyc">
                    <div class="img-preview preview-lg"></div>
                    <div class="img-preview preview-md"></div>
                    <div class="img-preview preview-sm"></div>
                </div>
                <div class="buttonbox docs-buttons" id="dpage">
                    <div class="qr_button bg_a mg_b_4">
                        选择图片
                        <input type="file" hidden  id="inputImage" type="file" accept="image/*" multiple>
                    </div>
                    <div class="qr_button" onclick="dask_hide()" id="clipBtn" data-method="getCroppedCanvas">
                        确定上传
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<footer>
    <div class="line_one"></div>
    <div class="foot_cont inner_width">
        <span class="ewm_box">
            <img src="img/userapp.png" alt="">
            <span>用户端下载</span>
        </span>
        <span class="ewm_box">
            <img src="img/sjapp.png" alt="">
            <span>商家端app下载</span>
        </span>
        <span class="ewm_box">
            <img src="img/wechat.png" alt="">
            <span>微信公众号</span>
        </span>
        <div class="link_box">
            <span >
                <a href="storepc/goRegister.do">我要开店</a>
            </span>
            <span >
                <a href="jsp/storepc/gyjy.html" target="_blank">关于九鱼</a>
            </span>
            <span >
                <a>加入我们</a>
            </span>
            <span >
                <a>合作流程</a>
            </span>
            <span >
                <a>常见问题</a>
            </span>
        </div>
        <div class="beihao">
            <span>©2016 jiuyuvip.com [浙] ICP备16025718号-2 ,All Rights Reserved.</span>
        </div>
    </div>
 </footer>
<script src="js/jquery-1.8.0.min.js"></script>
<script src="js/jquery.form.js"></script>
<script src="js/memberpc/library/lib/jquery.raty.min.js"></script>
<script src="js/memberpc/library/cropper.min.js"></script>
 <script src="js/memberpc/toubu.js"></script>
 <script src="js/memberpc/user_base.js"></script>
<script type="text/javascript">
function editMemberInfor(){
     $("#sex").val($("input[name='xb']:checked").val());
     $("#Form").ajaxSubmit({  
	  	url : 'memberpc/editMemberInfo.do',
        type: "POST",//提交类型  
      	data:{},
      	dataType:"json",
   		success:function(data){
    		if(data.result == "1"){
   				 window.location.reload();
   			}else{
   				alert(data.message);
   			} 
		} 
				  
    });
     
}


//修改头像
function editImageUrl(imgbase64){
 	$("#imgForm").ajaxSubmit({  
	  	url : 'memberpc/editMemberImager_url.do',
        type: "POST",//提交类型  
      	data:{},
      	dataType:"json",
   		success:function(data){
   		 	$("#image_url").attr("src",data.url+"?timestamp=" + new Date().getTime() );
		} 
				  
    });
 }
</script>
</body>
</html>