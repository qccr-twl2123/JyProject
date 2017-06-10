<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>九鱼网</title>
        <meta charset="utf-8">
         <base href="<%=basePath%>">
        <link rel="stylesheet" href="<%=basePath%>css/memberpc/business_Buyers3.css">
        <script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
        <script src="<%=basePath%>js/memberpc/business_Buyers3.js"></script>
        <link rel="stylesheet" href="<%=basePath%>css/memberpc/swiper.min.css">
        <script src="<%=basePath%>js/memberpc/swiper.min.js"></script>
          <link rel="stylesheet" href="<%=basePath%>css/ace.min.css" />
		<link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet" /> 
		<style type="text/css">
		input[type=checkbox],input[type=radio] {
			opacity: 1;
			position: inherit;
			margin-top: 19px;
		}
			.Buyers_hnav_sp1, .Buyers_hnav_sp2 {
    			 margin-top: 4px;
			}
			.sousuoparent{
				margin-left: -71px;
				display: inline-block;
				background-color: #fe2929;
				height: 40px;
			}
			.sousuoimag{
					width: 39px;
				    height: 30px;
				    display: inline-block;
				    /* margin: 0 auto; */
				    margin-left: 18px;
				    margin-top: 5px;
 			}
 			.shopred{
 				color:#fff;
 			}
 			
		</style>
    </head>
    <body>
        <div class="Buyers_body">
           <div class="right" style="width:69px;">
                <div class="right_d2">
                		<span  class="orangew" style="color: #ffffff;display: inline-block;position: absolute;left: 30px;font-size: 8px;width: 18px;height: 11px;background-color: #fe2929;top: 7px;text-align: center;border-radius: 22px;padding-bottom: 7px;">${empty shopnumber ?'0': shopnumber}</span>
	                    <a href="<%=basePath%>membershopCarPc/shopCar.do?member_id=${memberpd.member_id}">
		                    <span class="r_gw">
		                        <img src="../img/b_gw2.png">
		                    </span>
                    	</a>
                    <a href="#">
                        <span>
                            <img src="../img/b_top.png">
                        </span>
                    </a>
                 </div>
            </div> 
            <!-- 顶部导航 -->
            <div class="Buyers_hnav">
                 <span class="Buyers_hnav_sp1" >  
                	<a href="<%=basePath%>storepc/goLogin.do" > 商家登录 </a>
                 </span> 
                <span class="Buyers_hnav_sp1">
                	<a href="<%=basePath%>storepc/goRegister.do" > 商家加盟 </a>
                </span>
                <span class="Buyers_hnav_sp2"> 常见问题 </span> 
                 <c:if test="${empty memberpd}">
                	 <span class="Buyers_hnav_sp2">
                	 	<a href="<%=basePath%>memberpc/goMemberRegister.do">注册</a> 
                	 </span>
                	 <span class="Buyers_hnav_sp2">
                	 	<a href="<%=basePath%>memberpc/goMemberLogin.do" >会员登录</a>
                	 </span>
                	<!--  <span class="Buyers_hnav_sp2">
	                	 <a href="#">购物车 
	                	 <span class="orange">0</span>件 </a>
	                </span> -->
	                <span class="Buyers_hnav_sp2"> 
		                <a href="#"> 我的订单 </a>
	                 </span>
	                <span class="Buyers_hnav_sp2">
                	 	<a href="#" >我的九鱼 </a>
                	</span><!-- 个人中心 -->
                </c:if>
                <c:if test="${!empty memberpd}">
                  	<span class="Buyers_hnav_sp2">
                  		<a href="<%=basePath%>memberpc/loginOut.do" style="color:red;">退出</a>
                  	</span>
                	<span class="Buyers_hnav_sp2">
	                	<a href="<%=basePath%>membershopCarPc/shopCar.do?member_id=${memberpd.member_id}">购物车
	                	<span class="orange">${empty shopnumber ?'0': shopnumber}</span>
	                	件 </a>
	                </span> 
	                <a href="<%=basePath%>memberorderPc/orderInfoList.do?member_id=${memberpd.member_id}">
		                <span class="Buyers_hnav_sp2">
		                	我的订单
		                </span>
	                </a>
	                <span class="Buyers_hnav_sp2">
                		<a href="<%=basePath%>memberpc/memberInfoList.do?member_id=${memberpd.member_id}" >我的九鱼 </a>
                	</span><!-- 个人中心 -->
                </c:if>
              </div>
            <!-- 头部 -->
            <div class="Buyers_header">
                <span class="Buyers_header_logo">
                    <img src="<%=basePath%>img/logo.png">
                </span>
                <span class="Buyers_header_sp1">
                	<select class="Buyers_p2" name="city_name" onchange="search()" id="city_name" style="width: 92px; height: 40px;">
	                    <c:forEach items="${cityList}" var="var" >
	                    	<option value="${var.city_name }" ${pd.city_name eq var.city_name?'selected':'' }>${var.city_name }</option>
	                    </c:forEach>
                    </select>
                 </span>
                <span class="Buyers_header_sp2" style="margin-left: 37px;width: 59%;">
                    <p>
                        <input  style="height: 30px;"  type="text" class="Buyers_header_sp2_input" name="content" id="content" maxlength="40"/>
                        <span class="Buyers_header_sp2_sp sousuoparent" onclick="checked('','')" >
                        	<img src="<%=basePath%>img/b_ss.png" style="width:30px;height:30px" class="sousuoimag">
                        </span>
                    </p>
                    <!-- 关键字 -->
                    <p class="Buyers_header_p2">
                    	<c:forEach items="${keywordList}" var="infor" end="5">
                    	  <span class="" style="width: 4em">
                            	${infor}
                          </span>
                    	</c:forEach>
                      </p>
                </span>
            </div>
            <!-- 导航 -->
             <div class="Buyers_nav">
                <span class="Buyers_nav_sp2" style="color:#fff">
                    	<a href="<%=basePath%>memberpc/goMemberOne.do?member_id=${memberpd.member_id}&flag=0">首页</a>
                </span> 
            </div>
             <!-- coutent -->
            <div class="Buyers_coutent">
                <div class="Buyers_coutent_lt">
                    <div class="Buyers_coutent_lt_top">
                        <!-- 商家大图片集合 -->
                        <c:forEach items="${two }" var="var" varStatus="vs">
                        	<c:if test="${vs.index+1 eq '1' }">
                        		<img src="${var}" class="datu" id="da${vs.index+1}" style="width:430px; height:430px;"/>
                        	</c:if>
                        	<c:if test="${vs.index+1 ne '1' }">
                        		<img src="${var}" class="datu" id="da${vs.index+1}" style="width:430px; height:430px;display:none"/>
                        	</c:if>
                         </c:forEach>
                     </div>
                    <div class="Buyers_coutent_lt_mid">
                    	<!-- 商家小图片集合 -->
                    	<c:forEach items="${one}" var="var" varStatus="vs">
                        	<c:if test="${vs.index+1 eq '1' }">
                         		 <span class="Buyers_coutent_lt_mid_active hands xiaotu" onclick="tupian('${vs.index+1}')" id="xiao${vs.index+1}">
                        			<img src="${var}"  style="width:100%; height:100%; "/>
                        		 </span>
                         	</c:if>
                        	<c:if test="${vs.index+1 ne '1' }"  >
                         		<span class="hands xiaotu"  onclick="tupian('${vs.index+1}')" id="xiao${vs.index+1}">
                        			<img src="${var}"  style="width:100%; height:100%;"/>
                        		 </span>
                          	</c:if>
                         </c:forEach>
                    </div>
                    <div class="Buyers_coutent_lt_btm">
                        <span class="xin">
                            <span onclick="shoucang('${pa.store_id}')">
                            <img src="<%=basePath%>img/b_xin.png">
                           </span>收藏本店
                         </span>
                        <span class="zan" onclick="dianzan('${pa.store_id}')">
                            ${pa.zan_times}&nbsp;<span><img src="<%=basePath%>img/b_zan2.png"></span>点赞
                        </span>
                    </div>
                </div>
                <div class="Buyers_coutent_rt">
                     <div class="Buyers_coutent_rt_d1">
                         <p class="Buyers_coutent_rt_d1_p1">
                             ${pa.store_name }        <span> 积分率  ${pa.integral_rate}</span>
                         </p>
                         <p class="Buyers_coutent_rt_d1_p2">
                              			<c:if test="${pa.star eq '1' }">
				                        	<img src="<%=basePath%>img/new_star1.png"  style="width:100px">
				                        </c:if>
				                        <c:if test="${pa.star eq '2' }">
				                        	<img src="<%=basePath%>img/new_star2.png"  style="width:100px">
				                        </c:if>
				                        <c:if test="${pa.star eq '3' }">
				                        	<img src="<%=basePath%>img/new_star3.png"  style="width:100px">
				                        </c:if>
				                        <c:if test="${pa.star eq '4' }">
				                        	<img src="<%=basePath%>img/new_star4.png"  style="width:100px">
				                        </c:if>
				                        <c:if test="${pa.star eq '5' }">
				                        	<img src="<%=basePath%>img/new_star5.png"  style="width:100px">
				                        </c:if>
                           </p>
                     </div>
                    <!--  <div class="right_youhui"><a  onclick="youhuibuy()">优惠买单</a></div> -->
                     <dl class="Buyers_coutent_rt_d2">
                         <dt>商家信息</dt>
                         <dd>${pa.store_introduce}</dd>
                     </dl>
                     <div class="Buyers_coutent_rt_d3">
                      	<dd><strong>经营类别：</strong>${pa.sort_name }</dd>
                     </div>
                     <div class="Buyers_coutent_rt_d4">
                         <p>
                             <span><img src="<%=basePath%>img/b_dw2.png"></span>
                             ${pa.address}
                         </p>
                         <p style="line-height: 40px;">
                             <span><img src="<%=basePath%>img/b_dh2.png"></span>
                             	联系电话  ${pa.phone_bymemeber}
                         </p>
                     </div>
                </div>
            </div>
            <div  class="Buyers_coutent2">
                <span><img src="<%=basePath%>img/b_cx.png">优惠</span> 
            </div>
            <div class="Buyers_coutent3">
            	<c:forEach items="${marketlist}" var="var">
	            	<c:if test="${var.marketing_type eq '2'}">
	            		<p>
		                    <span>减</span> ${var.grantrule }
		                </p>
	            	</c:if>
	            	<c:if test="${var.marketing_type eq '1'}">
	            		<p>
		                    <span>增</span> ${var.grantrule }
		                </p>
	            	</c:if>
	            	<c:if test="${var.marketing_type eq '3'}">
	            		<p>
		                    <span>时</span> ${var.grantrule }
		                </p>
	            	</c:if>
	            	<c:if test="${var.marketing_type eq '4'}">
	            		<p>
		                    <span>买</span> ${var.grantrule }
		                </p>
	            	</c:if>
	            	<c:if test="${var.marketing_type eq '5'}">
	            		<p>
		                    <span>购</span> ${var.grantrule }
		                </p>
	            	</c:if>
	            	<c:if test="${var.marketing_type eq '6'}">
	            		<p>
		                    <span>积</span> ${var.grantrule }
		                </p>
	            	</c:if>
	            	<c:if test="${var.marketing_type eq '7'}">
	            		<p>
		                    <span>折</span> ${var.grantrule }
		                </p>
	            	</c:if>
            	</c:forEach>
             </div>
            <div style="text-align: center;width:100%;margin:10px 0;">
                <span style="font-size: 34px"> ${pa.store_name }</span> 
            </div>
            <c:forEach items="${three}" var="var">
                <div style="width:100%">
            			<div style="width:900px;margin:0 auto;"><img src="${var.image_url}" style="width:100%;"/></div>
            			<div style="width:900px;margin:0 auto;"><span style="font-size:18px">${var.text}</span></div>
            	</div>
            </c:forEach>
             <div  class="Buyers_coutent2">
                <span><img src="<%=basePath%>img/b_sp.png">商品</span> 
            </div>
             <!-- 优惠买单新位置 -->
            <div class="tc" id="#tc">
                	 <div class="tc_d1 hands">
                        <span class="d2_sp1" onclick="closeyouhui()">关闭</span>
                      </div>
                     <div class="tc_d2">
                        <span class="d2_sp1"> 按总金额</span>
                        <span class="tc_d2_sp_active d2_sp2"> 按类别金额</span>
                    </div>
                     <form action="" name="sortbuyForm" id="sortbuyForm" method="post">
                    <input type="hidden" name="pay_type" value="2"/>
                    <input type="hidden" name="member_id" value="${memberpd.member_id}"/>
                    <input type="hidden" name="pay_sort_type" value="2"/>
                    <input type="hidden" name="allgoodsid" value=""/>
                    <input type="hidden" name="desk_no" id="sortdesk_no" value=""/>
                    <input type="hidden" name="no_discount_money" value="0"/>
                    <input type="hidden" name="sale_money" id="sortsale_money" value="0"/>
                    <input type="hidden" name="redpackage_id" id="sortredpackage_id" value=""/>
                    <input type="hidden" name="store_redpackets_id" id="sortstore_redpackets_id" value=""/>
                    <input type="hidden" name="store_id" value="${pa.store_id}"/>
                    <input type="hidden" name="discount_content" id="sortdiscount_content" value=""/>
                    <input type="hidden" name="get_integral" id="sortget_integral" value=""/>
                     <input type="hidden" name="discount_money" id="sortdiscount_money" value=""/>
                     <input type="hidden" name="actual_money" id="sortactual_money"  value=""/>
                    <div class="tc1" >
                    	<c:forEach items="${bigsortList}" var="var">
                    		<div class="tc_d3">
	                            <span>${var.name}消费金额：</span>
	                            <span><input type="text" value="0" id="${var.goods_category_id}" onblur="leibietwo()"/></span>
	                            <span>积分率${var.back_rate}%</span>
	                            <input type="hidden"  class="goods_category_id" value="${var.goods_category_id}">
	                            <input type="hidden"  class="${var.goods_category_id}jf" value="${var.back_rate}">
	                            <input type="hidden"  class="${var.goods_category_id}zk" value="${var.zk_rate}">
	                        </div>
                    	</c:forEach>
                    	<div class="tc_d3">
                            <span>桌号：</span>
                            <span>
                             <select  class="sortdesk_no">
                             	<option value="">请选择</option>
                             	<c:forEach items="${tableNumberList}" var="var">
                              		<option value="${var.table_name}">${var.table_name}</option>
                             	</c:forEach>
                              </select>
                            </span>
                         </div>
                        <div class="tc_d3">
                            <span>优惠明细：</span>
                            <span></span>
                         </div>
                         <div class="tc_d3youhui">
                            <span></span>
                            <span class="sortyouhui"></span>
                         </div>
                        <div class="tc_d3">
                            <span></span>
                            <span>赠送积分<span class="red sortget_integral" >0</span></span>
                          </div>
                        <div class="tc_d4">
                           	 实付金额：￥<span class="red sortactual_money"> </span>
                             <span>已优惠<span class="red sortdiscount_money"></span>元</span>
                         </div>
                        <div class="tc_d5">
                            <span class="">
                                     <span><input type="checkbox" onclick="ischecked(this)">余额支付 ：</input></span>
                                     <input type="text" id="sortuser_balance" name="user_balance" onchange="isOk('1',this)" value="0"/>元
                             </span>
                            <span class="">
                                     	余额 ：${mpd.now_money } 元
                             </span>
                        </div>
                        <div class="tc_d5">
                            <span class="">
                                      <span><input type="checkbox" onclick="ischecked(this)" /> 积分支付 ：</span>
                                      <input type="text" id="sortuser_integral" name="user_integral" onchange="isOk('1',this)" value="0"/>分
                             </span>
                            <span class="">
                                     	积分余额 ：${mpd.now_integral}分
                             </span>
                        </div>
                        <div class="tc_zhifu" onclick="gopay('2')">去支付</div>
                    </div>
                    </form>
                     <!-- 按总金额 -->
                    <form action="" name="allbuyForm" id="allbuyForm" method="post">
                    <input type="hidden" name="pay_type" value="2"/>
                    <input type="hidden" name="member_id" value="${memberpd.member_id}"/>
                    <input type="hidden" name="pay_sort_type" value="1"/>
                    <input type="hidden" name="allgoodsid" value=""/>
                    <input type="hidden" name="desk_no" value=""/>
                    <input type="hidden" name="redpackage_id" id="allredpackage_id" value=""/>
                    <input type="hidden" name="store_redpackets_id" id="allstore_redpackets_id" value=""/>
                    <input type="hidden" name="store_id" value="${pa.store_id}"/>
                    <input type="hidden" name="discount_content" id="alldiscount_content" value=""/>
                    <input type="hidden" name="get_integral" id="allget_integral" value=""/>
                     <input type="hidden" name="discount_money" id="alldiscount_money" value=""/>
                     <input type="hidden" name="actual_money" id="allactual_money"  value=""/>
                    <div class="tc2">
                        <div class="tc_d3">
                            <span>消费金额：
                            <input type="text" name="sale_money"  id="allsale_money" value="0"  onblur="leibieone()"/>
                            </span> 
                            <span></span>
                        </div>
                        <div class="tc_d3">
                            <span>不优惠金额：
                            <input type="text" name="no_discount_money"  id="allno_discount_money" value="0"  onblur="leibieone()"/>
                            </span>
                            <span></span>
                        </div>
                        <div class="tc_d3">
                            <span>优惠明细：</span> <span></span>
                          </div>
                         <div class="tc_d3youhui">
                            <span></span>
                            <span class="allyouhui"></span>
                         </div>
                        <div class="tc_d3">
                            <span></span>
                            <span>赠送积分 <span class="red allget_integral">0</span></span>
                          </div>
                        <div class="tc_d4">
                           	 实付金额：￥<span class="red allactual_money"></span>
                           	  已优惠<span class="red alldiscount_money" ></span>元
                        </div>
                        <div class="tc_d5">
                            <span class="">
                            	<span><input type="checkbox" onclick="ischecked(this)" />余额支付 ：</span>
                                <input type="text" name="user_balance" id="alluser_balance" onchange="isOk('1',this)" value="0"/>元 
                             </span>
                            <span class="">余额 ：${mpd.now_money}元
                        </div>
                        <div class="tc_d5">
                            <span class="">
                                       <span><input type="checkbox" onclick="ischecked(this)" /> 积分支付 ：</span>
                                       <input type="text" name="user_integral"  id="alluser_integral" onchange="isOk('2',this)" value="0"/>分
                             </span>
                            <span class=""> 积分余额 ：${mpd.now_integral}分
                        </div>
                        <div class="tc_zhifu" onclick="gopay('1')">去支付 </div>
                    </div>
                    </form>
             </div>
            <script type="text/javascript">
	            if("${issortjf}" == "3"){
	            	$(".right_youhui a").attr("onclick","");
	            	$(".right_youhui a").css("background-color","#999");
	            } 
             </script>
            <!-- 支付界面 -->
                <div class="zhifu">
                    <div class="zhifu_d1"> 支付方式 <span>X</span></div>
                    <input type="hidden" name="pay_sort" value="" id="pay_sort"/>
                    <div class="zhifu_d2">
                        <label><input type="radio" name="zf" value="wx" checked="checked" style="margin-top:0px"/>
                        <span>微信支付</span></label>
                    </div>
                    <div class="zhifu_d2">
                        <label><input type="radio" name="zf" value="alipay_wap" style="margin-top:0px" />
                        <span>支付宝支付</span></label>
                    </div>
                   <!--  <div class="tc_d2">
                        <label><input type="radio" name="zf" value="3"></input>
                        <span>银行卡支付</span></label>
                    </div> -->
                    <span class="zhifu_yes" onclick="ok()">  确定   </span>
                </div> 
                <!-- 支付界面结束 -->
            <!--  -->
            <div class="Buyers_coutent4">
                <!-- 优惠买单位置 -->
                
                <!--  -->
                <div class="Buyers_coutent4_lt">
                    <dl>
                    <c:forEach items="${categoryList}" var="var" varStatus="vs">
                    			<dd>
		                            <span class="Buyers_coutent4_lt_active hands ${var.sort eq '1'?'Buyers_coutent4_lt_active_ok':''}"  onclick="goods('${var.goods_category_id}','${var.sort}',this)">
 									${var.name}
 									</span>
  								</dd>
 					</c:forEach>
 					</d1>
                </div>
                <div class="Buyers_coutent4_rt">
                    <div class="Buyers_coutent4_rt_d1" id="goods_id">
                    	<c:forEach items="${redList}" var="var"> 
                    		<div class="Buyers_coutent4_rt_d1_lt ${var.store_redpackets_id }" >
	                        </div> 
	                        <div class="Buyers_coutent4_rt_d1_rt ${var.store_redpackets_id }" id="content" >
	                             <p><span>${var.redpackage_content}</span></p> 
	                             <c:if test="${var.type eq '1'}">
	                              <p><span class="money${var.store_redpackets_id }" >${var.money}</span>元</p>
	                             </c:if>
	                             <c:if test="${var.type eq '2'}">
	                              <p><span class="money${var.store_redpackets_id }">${var.money}</span>折</p>
	                             </c:if>
 	                             <p><span class="shopred" style="color:black;margin-top: -60px;" onclick="getred('${var.store_redpackets_id}')">领取</span></p> 
	                        </div> 
                     	</c:forEach>
                     </div>
                </div>
            </div>
             <form action="<%=basePath%>memberpc/storeInfoList.do?store_id=${pa.store_id}" name="Form" id="Form" method="post">
            <div  class="Buyers_coutent2" style="border:none;">
                <span><img src="<%=basePath%>img/b_hy.png">评价</span>
            </div>
            <div class="Buyers_coutent5">
             <c:forEach items="${list}" var="list" varStatus="vs">
                <div class="Buyers_coutent5_d1">
                    <div class="Buyers_coutent5_d1_lt">
                        <div class="Buyers_coutent5_d1_lt_top">
                            <img src="${list.image_url }" style="width: 130px;height: 130px;display: inline-block;margin:0 auto;"/>
                        </div>
                        <div class="Buyers_coutent5_d1_lt_btm">
                           ${list.name }
                        </div>
                    </div>
                    <dl class="Buyers_coutent5_d1_rt">
                        <dt>
                            <span>  
                            	<c:if test="${list.star eq '1' }">
				                        	<img src="<%=basePath%>img/new_star1.png">
				                        	</c:if>
				                        	<c:if test="${list.star eq '2' }">
				                        	<img src="<%=basePath%>img/new_star2.png">
				                        	</c:if>
				                        	<c:if test="${list.star eq '3' }">
				                        	<img src="<%=basePath%>img/new_star3.png">
				                        	</c:if>
				                        	<c:if test="${list.star eq '4' }">
				                        	<img src="<%=basePath%>img/new_star4.png">
				                        	</c:if>
				                        	<c:if test="${list.star eq '5' }">
				                        	<img src="<%=basePath%>img/new_star5.png">
				                 </c:if>
                            </span>
                            <span style="width: 20px;"></span>   
                             <span>
								${fn:substring(list.star_time,0,18)}   
                            </span>
                        </dt>
                        <dd>
                            <span>[认真评价]</span>
                            ${list.content }<br>
                            <c:forEach items="${list.imgList}" var="imgvar">
		                        <span><img src="${imgvar.icon_url}" style="width:60px;height:60px;" /></span>
	                         </c:forEach>
	                         <br>
	                         <span>[商家回复]</span>
	                          ${list.comment_store }<br>
                        </dd>
                    </dl>
                </div>
                </c:forEach>
                <div class="Buyers_coutent5_d1">
           	          <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div> 
           	 	</div>
             </div> 
             <div></div>
             </form>
        </div>
         <!-- 分页需要的js -->
		<script type="text/javascript" src="<%=basePath%>js/jquery-1.7.2.js"></script>
 		<script type="text/javascript">window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="<%=basePath%>js/bootstrap.min.js"  type="text/javascript"></script>
		<script src="<%=basePath%>js/ace.min.js"  type="text/javascript"></script>
		<script src="<%=basePath%>js/jquery.form.js"  type="text/javascript"></script>
		<script src="<%=basePath%>js/ping/pingpp.js" type="text/javascript"></script>
        <script type="text/javascript">
	        $('.zhifu_d1 span').click(function(){
				$(this).parents('.zhifu').hide();
				$(".tc").show();
			});
        	var member_id="${memberpd.member_id}";
        	
        	//大小图片显示
        	function tupian(value){
        		$(".datu").hide();
        		$("#da"+value).show();
        		$(".xiaotu").removeClass("Buyers_coutent_lt_mid_active");
         		$("#xiao"+value).addClass("Buyers_coutent_lt_mid_active"); 
         	}
        
        	//搜索
        	function search(){
        		var city_name=$("#city_name").val();
        		var content=$("#content").val();
        		window.location.href="<%=basePath%>memberpc_shouye/listAllStore.do?content="+content+"&city_name="+city_name+"&member_id"+"${memberpd.member_id}";
        	}
         
        	//检索商品
           function goods(id,sort,obj){
        		 //变背景色
        		 $(".Buyers_coutent4_lt_active").removeClass("Buyers_coutent4_lt_active_ok");
        		 $(obj).addClass("Buyers_coutent4_lt_active_ok");
        		 
				 $.ajax({
				  url: "<%=basePath%>memberpc/goodsList.do",
				  data:{"goods_category_id":id,"sort":sort,"store_id":"${pa.store_id}","member_id":member_id},
				  type:"post",
				  dataType:"json",
 				  success:function(data){
 					  var str = $("#goods_id");
 					  str.empty();
 					  if(sort == "1"){
 						//红包的数据
 		 					if(data.length>0){
 								  	for(var i=0;i<data.length;i++){
 	  								  	str.append("<div class='Buyers_coutent4_rt_d1_lt'></div>");
 	  								  	var strdiv="<div class='Buyers_coutent4_rt_d1_rt' >";
 	  								 	strdiv+="<p><span>"+data[i].redpackage_content+"</span></p>";
  	 	  								if(data[i].type == "1"){
 	 	  									strdiv+="<p><span>"+data[i].money+"</span>元</p>";
 		  								}else if(data[i].redpackage_type == "2"){
 		  									strdiv+="<p><span>"+data[i].money+"</span>折</p>";
 		  								}
/*  	 	  							    strdiv+="<p><span class=\"shopred\"  style=\"color:black;margin-top: -60px;\" onclick=\"getred(\'"+data[i].store_redpackets_id+"\')\">领取</span></p>"; */
 	  	  								strdiv+="</div>";
 		  								str.append(strdiv);
 	 							  	}
 						  	}
 					  }else{
 						 //普通小类， 人气版的数据，今日特价的数据
 	 					  	if(data.length>0){
 							  	for(var i=0;i<data.length;i++){
 							  		 var strdiv="";
 							  		 var strdiv="<div class='Buyers_coutent4_rt_d1_lt'><img src='"+data[i].image_url+"' style='width:120px ;height:120px ;'/></div>";
 					 				 strdiv+="<div class='Buyers_coutent4_rt_d1_rt' >";
 	 							  	 strdiv+="<p><span>"+data[i].goods_name+"</span></p>";
									 strdiv+="<input hidden='hidden'  id = 'id' value='"+data[i].goods_id+"'/>";
 									 strdiv+="<p><span style='color: gray; text-decoration: line-through;'>"+data[i].market_money+"</span>/"+data[i].company+"&nbsp;&nbsp;<span style='color: red' >"+data[i].retail_money+"元</span></p>";
/* 									 strdiv+="<p><span class='shopred' id='goumai' onclick='goumai()'>购买结算</span>";
									 strdiv+="<span class=\"shopred\" id=\"joinCar\" onclick=\"joinCar(\'"+data[i].goods_id+"\')\">加入购物车</span></p>"; */
									 strdiv+="</div>";
									 str.append(strdiv);
 							  	}
 					  		}
 					  }
  				  }
			}); 
           }
           
        	//跳转去购物车的界面
           function goumai(){
           		window.location.href='<%=basePath%>membershopCarPc/shopCar.do?member_id=${memberpd.member_id}';
           }
           
           
           //添加购物车
           function joinCar(value){
        	    if("${memberpd.member_id}" == null || "${memberpd.member_id}" == ""){
        	    	alert("请前往登陆");
        	    	return;
        	    }
           		var id = value;
           		$.ajax({
				type:"post",
				url:'<%=basePath%>membershopCarPc/saveShop.do',
				dataType:"json",
 				data:{
 						goods_id:id,
  						member_id:"${memberpd.member_id}"
 					},
				success:function(data){
					if(data.result == "01"){
						alert("添加成功！");
						$(".orange").html(parseInt($(".orange").html())+1);
						$(".orangew").html(parseInt($(".orangew").html())+1);
 					}else if(data.result == "03"){
 						alert("该商品你已添加，数量新增成功");
 					}else if(data.result == "02"){
 						alert("库存不足");
 					}
 				}
			});
           }
           
           //收藏
           function shoucang(storeId){
           		$.ajax({
				type:"post",
				url:"<%=basePath%>membershopCarPc/collocation.do",
				dataType:"json",
 				data:{
 						store_id:storeId,
  						member_id:"${memberpd.member_id}"
 					},
				success:function(data){
					if(data.result == "01"){
						alert("收藏成功！");
 					}else if(data.result == "00"){
						alert("你已经收藏过了！");
					}
 				}
			});
           }
           
           //点赞
           function dianzan(storeId){
            	$.ajax({
				type:"post",
				url:"<%=basePath%>membershopCarPc/praise.do",
				dataType:"json",
 				data:{
 						store_id:storeId,
 						member_id:"${memberpd.member_id}"
 					},
				success:function(data){
					if(data.result == "01"){
						alert("点赞成功！");
 					}else if(data.result == "00"){
						alert("你已经点过赞了！");
					}
 				}
			});
           }
           
       	
   		//领取红包
    	function getred(store_redpackets_id){
    		if(member_id ==null || member_id ==""){
   				return;
   			}
   			$.ajax({
				type:"post",
				url:"<%=basePath%>app_shouye/getRedPackage.do",
				dataType:"json",
 				data:{  "store_redpackets_id": store_redpackets_id,
 						member_id:member_id,
 						"money":$(".money"+store_redpackets_id).html()},
 				success:function(data){
					if(data.result == "1"){
						alert("领取成功！");
						$("."+store_redpackets_id).remove();
					} 
 				}
   			});
   		}
   		
   		//优惠买单
   		function youhuibuy(){
   			if("${issortjf}" == "0"){
            	$(".tc1").hide();
            	$(".d2_sp2").removeClass("tc_d2_sp_active");
            	$(".tc2").show();
         		$(".d2_sp1").addClass("tc_d2_sp_active");
            }else if("${issortjf}" == "1"){
            	$(".tc1").show();
       			$(".d2_sp1").removeClass("tc_d2_sp_active");
       			$(".tc2").hide();
       			$(".d2_sp2").addClass("tc_d2_sp_active");
            }
   			$(".tc").show();  			
    	    $(".allactual_money").html("0");
			$("#allactual_money").val("0");
			$("#alldiscount_money").val("0");
			$(".alldiscount_money").html("0");
			$("#allget_integral").val("0");
			$(".allget_integral").html("+"+"0");
			$("#allredpackage_id").val("0");
			$("#allstore_redpackets_id").val("0");
			$(".allyouhui").empty();
			$("#alldiscount_content").val("");
			$(".sortactual_money").html("0");
			$("#sortactual_money").val("0");
			$("#sortdiscount_money").val("0");
			$(".sortdiscount_money").html("0");
			$("#sortget_integral").val("0");
			$(".sortget_integral").html("+"+"0");
			$("#sortredpackage_id").val("0");
			$("#sortstore_redpackets_id").val("0");
			$(".sortyouhui").empty();
			$("#sortdiscount_content").val("");
			$(".goods_category_id").each(function(n,o){
					var id=$(o).val();
					$("#"+id).val("0");
 	 			});
   		}
   		
   		//表单提交购买
   		function gopay(value){
   			if(member_id == null || member_id == ""){
   				return;
   			}
       		if(value=="1"){
       			var acmoney=$("#allactual_money").val();
       			if($("#allsale_money").val() == "" || $("#allsale_money").val() == "0"){
       				return;
       			}
       			var usermoney=$("#alluser_balance").val();
       			var userintegral=$("#alluser_integral").val();
       			var paymoney=parseFloat(acmoney)-parseFloat(usermoney)-parseFloat(userintegral);
       			if(paymoney <0){
       				paymoney="0";
        		}
       			$("#allactual_money").val(paymoney);
        	}else{
        		if($("#sortsale_money").val() == "" || $("#sortsale_money").val() == "0"){
       				return;
       			}
       			var acmoney=$("#sortactual_money").val();
       			var usermoney=$("#sortuser_balance").val();
       			var userintegral=$("#sortuser_integral").val();
       			var paymoney=parseFloat(acmoney)-parseFloat(usermoney)-parseFloat(userintegral);
       			if(paymoney <0){
       				paymoney="0";
        		}
       			$("#sortactual_money").val(paymoney);
        		}
       		$("#pay_sort").val(value);
       		$(".zhifu").show();
    	}  

   		
   		
   		//选中支付
   		function isOk(value,obj){
   			var user="";
   			if(value =="1"){//金额
   				if($(obj).prev().find("input").is(":checked")){
   					user=$(obj).val();
   					//判断金额够不够
   					var less=parseFloat("${mpd.now_money}")-parseFloat(user);
   					if(less < 0){
   						alert("余额不足");
   						$(obj).val("0");
   						return;
   					}
   				}else{
   					alert("请先勾选")
    				$(obj).val("0");
   				}
   			}else{//积分
   				if($(obj).prev().find("input").is(":checked")){
   					user=$(obj).val();
   					//判断金额够不够
   					var less=parseFloat("${mpd.now_integral}")-parseFloat(user);
   					if(less < 0){
   						alert("积分不足");
   						$(obj).val("0");
   						return;
   					}
    			}else{
   					alert("请先勾选")
    				$(obj).val("0");
   				}
   			}
   		}
    	
   		
    		//获取总金额的优惠信息
   			function leibieone(){
   				if(member_id == null || member_id == ""){
   					return;
   				}
   				var sale_money=$("#allsale_money").val();
   				var no_discount_money=$("#allno_discount_money").val();
   				if(sale_money =="" ){
   					return;
   				}
   				if(no_discount_money =="" ){
   					no_discount_money="0";
   				}
   	  			//获取营销信息
   	 			$.ajax({
   					url:"<%=basePath%>app_goods/allMoneyByOne.do",
   					type:"post",
   					dataType:"json",
   					data:{"member_id":"${memberpd.member_id}",
   						"store_id":"${pa.store_id}",
   						"paymoney":sale_money,
   						"notmoney":no_discount_money},
   					success:function(data){ 
   						var countpd=data.data.countpd;
   						var yingxiaoList=data.data.yingxiaoList;
   						if(countpd != null){
   							$(".allactual_money").html(countpd.paymoney);
   	   	 					$("#allactual_money").val(countpd.paymoney);
   	   						$("#alldiscount_money").val(countpd.reducemoney);
   	   						$(".alldiscount_money").html(countpd.reducemoney);
   	   	 					$("#allget_integral").val(countpd.zengjf);
   	   						$(".allget_integral").html("+"+countpd.zengjf);
   	   						$("#allredpackage_id").val(countpd.red_id);
   	   						$("#allstore_redpackets_id").val(countpd.zengid);
   						}
     	 				var discount_content="";
   	 					$(".allyouhui").empty();
   	  					for(var n=0; n<yingxiaoList.length ; n++){
   	  						var s1=yingxiaoList[n].content;
   	  						var s2=yingxiaoList[n].id;
   	  						var s3=yingxiaoList[n].number;
   	  						var s4=yingxiaoList[n].type;
   	   						var s=s1+"@"+s2+"@"+s3+"@"+s4;
   	  						discount_content+=s+",";
    	  					var sspan="<span class='wht11' style='color:blue;'>"+s1+"</span><span class='blue'>"+s3+"</span><br/>";
   	  						$(".allyouhui").append(sspan);
   	  					}
   	  					$("#alldiscount_content").val(discount_content );
   	 				}
   				});
   							
   			}
   	 		
   			
   			//获取类别的优惠信息
   			function leibietwo(){
   				if(member_id == null || member_id == ""){
   					return;
   				}
   				var allleibie="";
   				var xxx=0;//总金额
   				$(".goods_category_id").each(function(n,o){
   					var id=$(o).val();
   					var s2=$("#"+id).val();
   					var s1=id;
   	 				var s3=$("."+id+"jf").val();
   					var s4=$("."+id+"zk").val();
   	  				if(s2 != null && s2 != "" ){
   	 					var s=s1+"@"+s2+"@"+s3+"@"+s4;
   						allleibie+=s+",";
   						xxx+=parseFloat(s2);
   					}
   	 			});
   	  			//获取提货信息
   	 			$.ajax({
   					url:"<%=basePath%>app_goods/allMoneyByTwo.do",
   					type:"post",
   					dataType:"json",
   					data:{"allleibie":allleibie,"member_id":"${memberpd.member_id}","store_id":"${pa.store_id}"},
   					success:function(data){ 
   						var countpd=data.data.countpd;
   						var yingxiaoList=data.data.yingxiaoList;
   						if(countpd != null){
   							$("#sortsale_money").val(xxx);
   	   	 					$(".sortactual_money").html(countpd.paymoney);
   	   	 					$("#sortactual_money").val(countpd.paymoney);
   	   						$("#sortdiscount_money").val(countpd.reducemoney);
   	   						$(".sortdiscount_money").html(countpd.reducemoney);
   	   	 					$("#sortget_integral").val(countpd.zengjf);
   	   						$(".sortget_integral").html("+"+countpd.zengjf);
   	   						$("#sortredpackage_id").val(countpd.red_id);
   	   						$("#sortstore_redpackets_id").val(countpd.zengid);
   						}
     	 				var discount_content="";
   	 					$(".sortyouhui").empty();
   	  					for(var n=0; n<yingxiaoList.length ; n++){
   	  						var s1=yingxiaoList[n].content;
   	  						var s2=yingxiaoList[n].id;
   	  						var s3=yingxiaoList[n].number;
   	  						var s4=yingxiaoList[n].type;
   	   						var s=s1+"@"+s2+"@"+s3+"@"+s4;
   	  						discount_content+=s+",";
    	  					var sspan="<span class='wht11' style='color:blue;'>"+s1+"</span><span class='blue'>"+s3+"</span><br/>";
   	  						$(".sortyouhui").append(sspan);
   	  					}
   	  					$("#sortdiscount_content").val(discount_content );
   	 				}
   				});
   							
   			}	
    		
   			//隐藏优惠买单
   			function closeyouhui(){
   				$(".tc").hide();
   			}
   			//复选框
   			function ischecked(obj){
   				if(!$(obj).is(":checked")){
   					$(obj).parent().next().val("0");
   				} 
   			}
   			
   		    //开始支付
   		    function ok(){
   		    	    var sort=$("#pay_sort").val();
   		    	    var formup="";
   		    	    if(sort == "2"){
   		    	    	formup="sortbuyForm";
   		    	    }else if(sort == "1"){
   		    	    	formup="allbuyForm";
   		    	    }else{
   		    	    	return;
   		    	    }
    		        var url='<%=basePath%>/memberorderPc/orderInfoList.do?member_id='+'${memberpd.member_id}';
    		        var pay_way = $('input:radio:checked').val();//支付方式
    		        //获取charge
    		    	$("#"+formup).ajaxSubmit({  
					  	url : '<%=basePath%>membershopCarPc/thirdPartyPay.do',
				        type: "POST",//提交类型  
				      	data:{
				      		 "url":url,"pay_way":pay_way
 				      	},
				      	dataType:"json",
				   		success:function(data){ 
					   			if(data.result=="0"){
										alert(data.message);
										return;
								}
								var m=data.data;
								var charge=m.charge;
								if(charge == ""){
									alert("支付渠道未开通");
								}else{
									//支付
									pingpp.createPayment(charge, function(result, err){
									    console.log(result);
									    console.log(err.msg);
									    console.log(err.extra);
									    if (result == "success") {
									    	alert("支付成功,跳转至我的订单");
									    	window.location.href=url;
									        // 只有微信公众账号 wx_pub 支付成功的结果会在这里返回，其他的支付结果都会跳转到 extra 中对应的 URL。
									    } else if (result == "fail") {
									    	alert("支付失败fail");
									        // charge 不正确或者微信公众账号支付失败时会在此处返回
									    } else if (result == "cancel") {
									    	alert("支付取消");
									        // 微信公众账号支付取消支付
									    }
									});
								}
				   		} 
 					});
   		        	
   		        }
     			
   		 $(function(){
    			    $('.d2_sp1').click(function(){
   		                $(this).addClass('tc_d2_sp_active');
   		                $('.d2_sp2').removeClass('tc_d2_sp_active');
   		                if("${issortjf}" == "0"){
   		                	$('.tc2').show();
   		                }
    		            $('.tc1').hide();
   		            })

   		            $('.d2_sp2').click(function(){
   		                $(this).addClass('tc_d2_sp_active');
   		                $('.d2_sp1').removeClass('tc_d2_sp_active');
   		             	if("${issortjf}" == "1"){
		                	$('.tc1').show();
		                }
    		             $('.tc2').hide();
   		            });
    			    
   		            $('.r_gw').click(function(){
   		                $('.tc').show();
   		            })
   		            $('.tc_zhifu').click(function(){
   		                $(this).parents('.tc').hide();
   		            })      
   		        
   		})

   		    
   		   

   			
   			
        </script>
        
    </body>
</html> --%>