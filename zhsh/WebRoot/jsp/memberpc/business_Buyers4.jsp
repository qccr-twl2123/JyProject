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
    <title>订单列表</title>
	<base href="<%=basePath%>">
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <link rel="stylesheet" href="css/memberpc/library/predefine.pc.css">
    <link rel="stylesheet" href="css/memberpc/font/iconfont.css">
    <link rel="stylesheet" href="css/memberpc/shouye.css">
    <link rel="stylesheet" href="css/memberpc/dingdan.css">
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
            		 <a href="memberorderPc/orderInfoList.do?area_name=${pd.area_name}&city_name=${pd.city_name}&sl_k=1&ac_k=0&id_user=1">
		                <span>我的订单</span>
		            </a>
            		<a href="memberpc/memberInfoList.do?area_name=${pd.area_name}&city_name=${pd.city_name}&sl_k=1&ac_k=0&id_user=0">
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
                    	<option value="">--${pd.city_name}--</option>
                     </c:if>
                     <c:forEach items="${cityList }" var="var" >
                    	<option value="${var.city_id }">${var.city_name }</option>
                    </c:forEach>
                 </select>
                <select name="area" id="loc_qu" class="select_item" onchange="shaixin()">
                	 <c:if test="${!empty pd.area_name}">
                    	<option value="">--${pd.area_name}--</option>
                     </c:if>
                	 <c:forEach items="${areaList }" var="var" >
                	 	<option value="${var.area_id }" >${var.area_name }</option>
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
<!-- 订单位置 -->

<!--弹窗-->
<div class="dask">
    <div class="del_item_qr">
        <div class="dingbu">
            <p>确定要删除这条记录吗？</p>
        </div>
        <div class="del_btn_box">
            <span class="qr_button suredelete">确定</span>
            <span class="qx_button">取消</span>
        </div>
    </div>
</div>



<!--导航栏结束-->
<div class="nav_title inner_width">
    <p>我的订单</p>
</div>

<form action="memberorderPc/orderInfoList.do" name="Form" id="Form" method="post"> 
	<input type="hidden" name="order_status" value="${pd.order_status}" id="order_status"/>
	<input type="hidden" name="tihuo_status" value="${pd.tihuo_status}" id="tihuo_status"/>
	<input type="hidden" name="city_name" value="${pd.city_name}"  />
	<input type="hidden" name="area_name" value="${pd.area_name}"  />
	<div class="my_ddlist inner_width ">
	    <div class="check_button_box  pd_4">
	        <span class=" mg_r_8 ${(empty pd.tihuo_status or pd.tihuo_status eq '') == true?'qr_button':'qx_button'}"  onclick="changestatus('1','')">全部已支付订单</span>
	        <span class=" mg_r_8 ${pd.tihuo_status eq '0'?'qr_button':'qx_button' }"  onclick="changestatus('1','0')">未提货订单</span>
	        <span class=" mg_r_8 ${pd.tihuo_status eq '99'?'qr_button':'qx_button' }"  onclick="changestatus('3','99')">退货订单</span>
	    </div>
	    <div class="sp_list">
	        <ul>
	            <li class="sp_tit">
	                <span>
	                    <select name="pay_type" onchange="changepaytype()" >
	                        <option value="">全部</option>
	                        <option value="1" ${pd.pay_type eq '1'?'selected':'' }>收银订单</option>
	                        <option value="2" ${pd.pay_type eq '2'?'selected':'' }>扫一扫优惠买单订单</option>
	                        <option value="3" ${pd.pay_type eq '3'?'selected':'' }>提货券订单</option>
	                     </select>
	                </span>
	                <span>订单详情</span>
	                <span>购买人</span>
	                <span>金额</span>
	                <span>全部状态</span>
	                <span>操作</span>
	            </li>
	            
	            <c:forEach items="${orderList}" var="var" varStatus="vs">
		            <li class="sp_item">
		                <p>
		                    <span class="w_30 col_a">${var.createtime }</span>
		                    <span class="w_30">
		                        <span class="col_a">订单编号：</span>
		                        <span>${var.order_id }</span>
		                    </span>
		                    <span class="w_30">
		                        <span class="col_a">店名：</span>
		                        <span>${var.store_name}</span>
		                    </span>
		                </p>
		                <div class="sp_show">
		                    <span class="b_r_a"><img src="${var.pictrue_url}" alt=""></span>
		                    <span class="b_r_a txa_l sp_sq">
		                    	<c:forEach items="${var.goodsList}" var="goodsvar">
		                    		<b>  ${goodsvar.goods_name} x ${goodsvar.shop_number } </b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                    	</c:forEach>
		                    </span>
		                    <span class="b_r_a">${var.name }</span>
		                    <span class="b_r_a">￥<span class="col_e9">${var.sale_money}</span></span>
		                    <span class="b_r_a">  
		                    	 <c:if test="${var.order_status eq '1' }">已支付 <c:if test="${var.order_status eq '1' and  var.tihuo_status eq '0' and var.pay_type eq '3' }">--未提货</c:if></c:if>
 		                    	 <c:if test="${var.order_status eq '3' and var.tihuo_status eq '99' and  var.pay_type eq '3' }">已退货</c:if>
 		                    </span>
		                    <span class="dd_del"  ><b class="iconfont icon-shanchu" order_id="${var.order_id}"></b></span>
		                </div>
		            </li>
	            </c:forEach>
	         </ul>
	    </div>
	</div>
</form>
<div class="pagination fenye clf">
            ${page.pageStr2}
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
<script src="js/memberpc/library/jquery-1.12.4.min.js"></script>
<script src="js/memberpc/library/lib/jquery.raty.min.js"></script>
<script src="js/memberpc/library/cropper.min.js"></script>
<script src="js/memberpc/toubu.js"></script>
<script src="js/memberpc/dingdan.js"></script>
<script type="text/javascript">
$(function(){
	 //删除按钮移入事件
    $(".dd_del").mouseover(function(e){
        var ele;
        if($(e.target).tagName=="SPAN"){
            ele=$(e.target)
        }else{
            ele=$(e.target).parents(".dd_del")
        }
        ele.children("b").text("删除").attr("class","col_e9");
    })

    $(".dd_del").mouseout(function(e){
        $(e.target).children("b").attr("class","iconfont icon-shanchu").text("");
    })

  //删除按钮点击事件
    $(".dd_del b").click(function(e){
        $(".dask").show();
        var order_id=$(e.target).attr("order_id");
        $(".suredelete").attr("onclick","del('"+order_id+"')");
     });
	 
    //取消按钮
    $(".del_btn_box .qx_button").click(function(){
         $(".dask").hide();
         $(".suredelete").attr("onclick","");
    });
});




//改变支付类型（提货券/收银/优惠买单）表单提交
function changepaytype(){
	$("#Form").submit();
}
//支付状态表单提交
function changestatus(order_status,tihuo_status){
	$("#order_status").val(order_status);
	$("#tihuo_status").val(tihuo_status);
	$("#Form").submit();
}



//删除
function del(id){
	$.ajax({
		type:"post",
		url:"memberorderPc/delOrder.do",
		dataType:"json",
			data:{
					order_id:id
			},
		success:function(data){
			if(data.result == "1"){
				window.location.reload(); //刷新当前页面
			}
		}
	});
}

 


</script>
</body>
</html>