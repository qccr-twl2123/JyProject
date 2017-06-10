<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>九鱼网</title>
        <meta charset="utf-8">
         <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
         <link rel="shortcut icon" href="<%=basePath%>favicon.ico" >
        <link rel="Bookmark" href="<%=basePath%>favicon.ico">
   		<link rel="icon" type="image/gif" href="<%=basePath%>animated_favicon1.gif" >
        <link rel="stylesheet" href="css/memberpc/business_Buyers5.css">
        <link rel="stylesheet" href="css/memberpc/swiper.min.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/memberpc/business_Buyers5.js"></script>
         <script src="js/memberpc/swiper.min.js"></script>
        <script src="js/jquery.qrcode.min.js"></script>
    </head>
    <body>
        <div class="Buyers_body">
            <!-- 顶部导航 -->
            <div class="Buyers_hnav">
                 <span class="Buyers_hnav_sp1" >  
                	<a href="storepc/goLogin.do" > 商家登录 </a>
                </span> 
                <span class="Buyers_hnav_sp1">
                	<a href="storepc/goRegister.do" > 商家加盟 </a>
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
                	<%-- <span class="Buyers_hnav_sp2">
	                	<a href="<%=basePath%>membershopCarPc/shopCar.do?member_id=${memberpd.member_id}">购物车
	                	<span class="orange">${empty shopnumber ?'0': shopnumber}</span>
	                	件 </a>
	                </span>  --%>
	                <span class="Buyers_hnav_sp2">
		                <a href="<%=basePath%>memberorderPc/orderInfoList.do">
	 		                	我的订单
	 	                </a>
	                 </span>
	                <span class="Buyers_hnav_sp2">
                		<a href="<%=basePath%>memberpc/memberInfoList.do" >我的九鱼 </a>
                	</span><!-- 个人中心 -->
                </c:if>
                
             </div>
            <!-- 导航 -->
            <div class="Buyers_nav">
                <span class="Buyers_nav_sp2">
                    	<a style="color:#fff" href="<%=basePath%>">首页</a>
                </span> 
            </div>
            <!-- coutent -->
            <div class="Buyers_coutent">
                
                <div class="Buyers_coutent ">
                    <div class="Buyers_coutent_mid1">
                        <span class="mid_active">全部商品</span>
                        
                    </div>
                    <div class="Buyers_coutent_mid2" id = "check">
                        <table width="100%"border="0"cellpadding="0"cellspacing="0">
                            <tr>
                                <td style="text-align: left;"><input type="checkbox" id = "setAll" class="setAll"/><span>全选</span></td>
                                <td>商品</td>
                                <td>单价（元）</td>
                                <td>数量</td>
                                <td>小计（元）</td>
                                <td>操作</td>
                            </tr>
                        </table>
                    </div>
                    <div class="Buyers_coutent2_d1">
                        <c:forEach items="${shopList}" var="var" varStatus="vs">
						  <input type="hidden" name="store${var.shopcart_id}" value="${var.store_id}"/>
						  <input type="hidden" name="goods${var.shopcart_id}" value="${var.goods_id}"/>
                         <div class="Buyers_coutent2_d2">
                             <div class="Buyers_coutent2_d2_sp1">
                                 <span class="Buyers_coutent2_d2_sp1_img">
                                     <input type="checkbox" name = "check"  value="${var.shopcart_id}" onclick="checkboxsure()"/>
                                     <img src="${var.image_url }" id = "img" style=" width: 80px;height: 80px"/>
                                 </span>
                                 <span class="Buyers_coutent2_d2_sp1_txt"> ${var.goods_name } </span>
                             </div>
                             <div class="Buyers_coutent2_d2_sp2" >
                                <span class="onemoney${var.shopcart_id}">${var.retail_money }</span>
                             </div>
                             <div class="Buyers_coutent2_d2_sp2_numebr">
                                 <div class="new_dd"> 
                                    <span style="border-left: 0"  onclick="changenumber('${var.shopcart_id}','-1')">-</span>
                                    <input type = "text" class="num${var.shopcart_id}" id = "num" value="${var.goods_number }" onchange="inputchange('${var.shopcart_id}',this.value)"/></span> 
                                    <span onclick="changenumber('${var.shopcart_id}','1')">+</span>
                                 </div>
                             </div>
                             <div class="Buyers_coutent2_d2_sp2" id= "money" ><span class="ownmoney${var.shopcart_id}"> ${var.money} </span></div>
                             <div class="Buyers_coutent2_d2_sp2_caozuo hands">
                                <span id ="del" onclick="del('${var.shopcart_id}')" style="color: blue;">删除</span> 
                             </div>
                         </div>
                         </c:forEach>
                    </div>
                    <div class="Buyers_coutent2_d4">
                        <div class="Buyers_coutent2_d4_lt">
                           <input type="checkbox" id = "choiseAll" class="setAll"></input> <span style="margin: 0" >全选</span>
                           <span onclick="delCoutn()">删除选中的商品</span><span>移到我的关注</span>
                           <span  onclick="delGoods()">清除下柜产品</span>
                        </div>
                        <div class="Buyers_coutent2_d4_rt" id="priceCount">
                            <span class="go_js">去结算</span>
                            <span id= "price">总价（不含运费）：¥ <span class="red" id="allpaymoney">0</span></span>
                           <span class="new_sp"><span>件商品</span> <span class="red"  id = "allcount">0</span><span>已选择</span></span> 
                        </div>
                    </div>
                </div>
                <!-- 支付界面 -->
                <div class="tc">
                    <div class="tc_d1"> 支付方式 <span onclick="closepay()">X</span></div>
                    <div class="tc_d2">
                        <label><input type="radio" name="zf" value="wx_pub_qr" checked="checked" />
                        <span>微信支付</span></label>
                    </div>
                    <div class="tc_d2">
                        <label><input type="radio" name="zf" value="alipay_pc_direct" />
                        <span>支付宝支付</span></label>
                    </div>
                   <!--  <div class="tc_d2">
                        <label><input type="radio" name="zf" value="3"></input>
                        <span>银行卡支付</span></label>
                    </div> -->
                    <div style="width:200px;height:200px;float:right;margin-right:2%;position: absolute; top: 71px;left: 235px;" id="erweimapay">
 
 	         		</div>  
                    <span class="tc_yes" onclick="ok()">  确定   </span>
                </div> 
                <!-- 支付界面结束 -->
            </div>
        </div>
         <script src="js/ping/pingpp.js" type="text/javascript"></script>
    <script type="text/javascript">
		$('.tc_d1 span').click(function(){
			$(this).parents('.tc').hide();
		});
	
	     $('.go_js').click(function(){
	    	 var s=$("input[name='check']:checked").length;
	    	 if( s != 0){
	    			$('.tc').show();
	    	 }
 	     });
 	     
    	//复选框的全选和全不选
    	$(function() {
           $(".setAll").click(function() {
                $('input[name="check"]').attr("checked",this.checked); 
                checkboxsure();
            });
            var $subBox = $("input[name='check']"); 
            $subBox.click(function(){
                $(".setAll").attr("checked",$subBox.length == $("input[name='check']:checked").length ? true : false);
            });
          });
    	
    	//循环获取数量以及金额
    	function checkboxsure(){
    		var n=0;
    		var allpaymoney=0;
    		$("input[name='check']:checked").each(function(i,o){
    			n=i+1;
    			var id=$(o).val();
    			var money=$(".ownmoney"+id).html();
    			allpaymoney+=parseFloat(money);
    		});
    		$("#allcount").html(n);
    		$("#allpaymoney").html(allpaymoney);
    	}
        
       
        
        //修改件数
       	function changenumber(id,num){
        	var changenum=parseInt(num);
        	var n=parseInt($(".num"+id).val());
        	var m=n+changenum;
        	if(m <= 1){
        		m=1;
        	}
        	$(".num"+id).val(m);
        	var onemoney=parseFloat($(".onemoney"+id).html());
         	var money=m*onemoney;
        	var ownmoney=$(".ownmoney"+id).html(money);
        	checkboxsure();
        }
        
        //文本框修改件数
        function inputchange(id,num){
        	var changenum=parseInt(num);
         	var m=changenum;
        	if(m <= 1){
        		alert("最少选择一件");
        		m=1;
        	}
        	$(".num"+id).val(m);
        	var onemoney=parseFloat($(".onemoney"+id).html());
         	var money=m*onemoney;
        	var ownmoney=$(".ownmoney"+id).html(money);
        	checkboxsure();
        }
        
        //删除(OK)
        function del(id){
       	$.ajax({
				type:"post",
				url:"membershopCarPc/delShop.do",
				dataType:"json",
				data:{
						shopcart_id:id
				},
				success:function(data){
					if(data.result == "01"){
						window.location.reload(); //刷新当前页面
					}
				}
			});
       } 
        //删除选中的所有商品(OK)
        function delCoutn(){
        	var obj = document.getElementsByName('check');
    		var del = '';
    		for ( var i = 0; i < obj.length; i++) {
				if(obj[i].checked) del += obj[i].value+',';
			}
     		if(del==''){
    			alert('你还没有选择你要删除的内容！');
    			return;
    		}
        	$.ajax({
				type:"post",
				url:"membershopCarPc/deleteAll.do",
				dataType:"json",
 				data:{
 						shopcart_id:del
 				},
				success:function(data){
					if(data.result == "01"){
						window.location.reload(); //刷新当前页面
					}else if(data.result == "00"){
						alert("删除失败");
					}
 				}
			}); 
        }
        
        //清除下柜产品
        function delGoods(){
        	
        }
        
        //支付成功 
        function ok(){
        	var url='<%=basePath%>memberorderPc/orderInfoList.do?member_id='+'${memberpd.member_id}'; 
        	var sale_money=$("#allpaymoney").html();
         	var no_discount_money="0";
        	var discount_money="0";
        	var actual_money=$("#allpaymoney").html();
        	var user_balance="0";
        	var user_integral="0";
         	var pay_type="3";//提货卷
        	var get_integral="0";
         	var member_id="${memberpd.member_id}";
        	var discount_content="";
        	var redpackage_id="";
        	var store_redpackets_id="";
         	var pay_way = $('input:radio:checked').val();//支付方式
         	var channel=pay_way;
         	var pay_sort_type="1";
         	var remark="";
        	var allgoodsid="";
         	//获取所有的购物车的商品ID
        	$("input[name='check']:checked").each(function(i,o){
        		x=i;
    			var id=$(o).val();
    			var money=$(".ownmoney"+id).html();
    			var number=$(".num"+id).val();
    			var s=id+"@"+number+"@"+money;
    			allgoodsid+=s+",";
     		});
        	
        	//获取charge
    		$.ajax({
				type:"post",
					url:"<%=basePath%>membershopCarPc/thirdPartyPay.do",
					data:{
						"url":url,
						"sale_money":sale_money,
						"no_discount_money":no_discount_money,
						"discount_money":discount_money,
						"actual_money":actual_money,
						"user_balance":user_balance,
						"user_integral":user_integral,
						"pay_type":pay_type,
						"get_integral":get_integral,
						"discount_content":discount_content,
						"redpackage_id":redpackage_id,
						"store_redpackets_id":store_redpackets_id,
						"pay_way":pay_way,
						"pay_sort_type":pay_sort_type,
						"remark":remark,
						"allgoodsid":allgoodsid,
						"member_id":member_id
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
							if(channel == "alipay_qr"){
								var credential=charge.credential;
								var alipay_qr=credential.alipay_qr;
								$("#erweimapay").empty();
								//生成二维码：商家ID以及zhuohao.生成的二维码下载，图片尺寸为5*6CM；
		 				        jQuery($("#erweimapay")).qrcode({ width: 150, height: 150,  text: alipay_qr }); 
							}else if(channel == "wx_pub_qr" ){
								var credential=charge.credential;
								var wx_pub_qr =credential.wx_pub_qr ;
								$("#erweimapay").empty();
								//生成二维码：商家ID以及zhuohao.生成的二维码下载，图片尺寸为5*6CM；
		 				        jQuery($("#erweimapay")).qrcode({ width: 150, height: 150,  text: wx_pub_qr }); 
		 				        $("#erweimapay").append("<span>微信支付二维码</span>");
 							}else if(channel == "alipay_wap" || channel == "wx_pub" || channel == "alipay_pc_direct"){
 								//支付
 								pingpp.createPayment(charge, function(result, err){
 								    console.log(result);
 								    console.log(err.msg);
 								    console.log(err.extra);
 								    if (result == "success") {
 								    	alert("支付成功,跳转至我的订单");
 								    	//window.location.href=url;
 								    	var tihuo_id = data.tihuo_id;//订单号
						   				//在支付成功的状态下跳转订单到订单详情界面
						   				window.location.href='<%=basePath%>html_member/findById.do?tihuo_id='+tihuo_id;
 								        // 只有微信公众账号 wx_pub 支付成功的结果会在这里返回，其他的支付结果都会跳转到 extra 中对应的 URL。
 								    } else if (result == "fail") {
 								    	alert("支付失败fail");
 								        // charge 不正确或者微信公众账号支付失败时会在此处返回
 								    } else if (result == "cancel") {
 								    	alert("支付取消");
 								        // 微信公众账号支付取消支付
 								    } else if(result == "01"){
 								    	window.location.href="<%=basePath%>memberpc/goMemberLogin.do";
 								    }
 								});
 							}
 						}
   					}
			});  
        	
        }
    </script>
    </body>
 </html>