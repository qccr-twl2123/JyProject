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
    <title>招商</title>
    <base href="<%=basePath%>">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="css/zhaoshang/swiper.min.css">
    <link rel="stylesheet" href="css/zhaoshang/bootstrap.min.css">
    <link rel="stylesheet" href="css/zhaoshang/zs.css">
</head>
<body>
<div class="dask">
    <div class="alert">
    <div class="tit1">
        <h4>报名成功</h4>
        <h4>谢谢您的参与</h4>
    </div>
    <div class="tit2">
        <h4>请不要重复点击</h4>
    </div>
        <div class="button1 button">确定</div>
     </div>
</div>
<div class="swiper-container">
    <div class="swiper-wrapper">
        <div class="swiper-slide">
            <div class="image4">
                <img src="img/huawen.png" alt="" class="title4">
                <div class="cont">
                    <div class="tit">
                        <img src="img/tit4-1.png" class="img_tit" alt="">
                    </div>
                    <div class="form_cont">
                        <form class="form-horizontal dlqy" role="form">
                              <div class="form-group mg0">
                                  <div class="name">
                                  <h6><span>*</span>意向代理区域：</h6>
                                  </div>
                                <select class=" col-xs-2 all" id="province_id" onchange="addsearchfind();">
                                  <option value="" >省</option>
                                  	<c:forEach items="${provincelist}" var="var" varStatus="vs">
										<option value="${var.pcd_id}">${var.name}</option>
									</c:forEach>
                                </select>
                                 <select class="col-xs-2 all" id="city_id" onchange="addsearcharea();">
                                  <option value=""  >市</option>
                                </select>
                                 <select class="col-xs-3 all"  id="area_id">
                                  <option value="" >区/县</option>
                                </select>
                              </div>

                              <div class="form-group qylx">
                                  <div class="name">
                                      <h6><span>*</span>企业类型：</h6>
                                  </div>
                                <label class="radio-inline text company_type">
                                  <input type="radio"  name="inlineRadioOptions" value="1">大型商场
                                </label>
                                <label class="radio-inline text company_type">
                                  <input type="radio"  name="inlineRadioOptions" value="2">连锁企业
                                </label>
                                <label class="radio-inline text company_type">
                                  <input type="radio" name="inlineRadioOptions" value="3">大型市场
                                </label>
                                <label class="radio-inline text company_type">
                                  <input type="radio"  name="inlineRadioOptions" value="4">行业协会
                                </label>
                                <label class="radio-inline text company_type">
                                  <input type="radio"  name="inlineRadioOptions" value="5">当地名企
                                </label>
                                <label class="radio-inline text company_type">
                                  <input type="radio"  name="inlineRadioOptions" value="6" checked>其他
                                </label>
                              </div>
                            <div class="inp">
                                <h6><span>*</span>企业名称：</h6>
                                <input type="text" class="inf company_name">
                            </div>
                            <div class="inp">
                                <h6><span>*</span>联系人：</h6>
                                <input type="text" class="inf company_contacts">
                            </div>
                            <div class="inp">
                                <h6><span>*</span>职位：</h6>
                                <input type="text" class="inf company_position">
                            </div>
                            <div class="inp">
                                <h6><span>*</span>手机号码：</h6>
                                <input type="text" class="inf company_phone">
                            </div>
                            <div class="inp">
                                <h6><span>*</span>邮箱：</h6>
                                <input type="email" class="inf company_mailbox">
                            </div>
                        </form>
                    </div>
                 </div>
                <img src="img/huawen.png" alt="" class="title5">
                 <img src="img/butt1.png" class="butt" alt="">
            </div>
        </div>
    </div>
</div>
<script src="js/zhaoshang/jquery-1.12.4.min.js"></script>
<script src="js/zhaoshang/swiper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/ace-elements.min.js"></script>
<script src="js/ace.min.js"></script>
<!--提示框-->
<script type="text/javascript" src="js/jquery.tips.js"></script>
<script>
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        direction: 'vertical'
    });
</script>
<script>
    $(".button1").click(function(){
             window.history.back(-1);
	        //history.back(-1):直接返回当前页的上一页，数据全部消息，是个新页面
	        //history.go(-1):也是返回当前页的上一页，不过表单里的数据全部还在
    });
    
    var company_type="6";
    $(".company_type").click(function(){
    	company_type=$(this).children("input").val();
     });
     //提交
    $(".butt").click(function(){
    	//判断是否为空
    	var s=1;
    	$(".all").each(function(n,o){
    		if($(o).val() == ""){
    			s=0;
    		}
    	});
     	if(s == 0){
    		alert("带*项不能为空");
    		return;
    	}
     	var myreg = /^((13[0-9])|(15[^4,\\D])|(17[2,7-8])|(18[0,5-9]))\\d{8}$/;
     	var phone=$(".company_phone").val();
     	//判断手机号格式
     	if(phone.length != 11 && !myreg.test(phone)){
			$(".company_phone").tips({
				side:3,
	            msg:'手机号格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$(".company_phone").focus();
			return false;
		}
     	var company_mailbox= $(".company_mailbox").val();
     	//判断邮箱格式
     	if(!ismail(company_mailbox)){
			$(".company_mailbox").tips({
				side:3,
	            msg:'邮箱格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$(".company_mailbox").focus();
			return false;
		}
     	function ismail(mail){
    		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
    	}
     	//添加信息
        $.ajax({
            url:"<%=basePath%>zhaoshang/ajaxAddCompay.do",
            type: "POST",
            data: {
                "company_type" : company_type ,
                "company_name" : $(".company_name").val() ,
                "company_contacts" : $(".company_contacts").val() ,
                "company_position" : $(".company_position").val(),
                "company_phone" : phone,
                "company_mailbox" : company_mailbox,
                "area_id" : $("#area_id").val(),
                "city_id" : $("#city_id").val(),
                "province_id" : $("#province_id").val()
            } ,
            dataType:"json",
            success:function(data){
            	if(data.result == "1"){
            		  $(".dask").css({"display":"block"})
					  $(".tit1").css({"display":"block"})
					  $(".button1").css({"display":"block"})
					  $(".button2").css({"display":"block"})
            	}else{
            		  $(".dask").css({"display":"block"})
					  $(".tit1").css({"display":"none"})
					  $(".tit2").css({"display":"block"})
					  $(".button1").css({"display":"block"})
					  $(".button2").css({"display":"block"})
            	}
               
            }
          });
     });
    
  //获取城市
    function addsearchfind(){
    	var str=$("#province_id option:selected").val();//获取被选中的value值
     	$.ajax({
    		  url: '<%=path%>/zhihui_subsidiary/citylist.do',
    		  data:"province_id="+str,
    		  type:"post",
    		  dataType:"json",
    		  success:function(data){
    			  	var list=data.citylist;
    				$("#city_id option").remove();
    				$("#area_id option").remove();
    			  	if(list.length>0){
    			  		$("#city_id").append("<option value=''>市</option>");
    				  	$("#area_id").append("<option  value=''>区/县</option>");
    				  	for(var i=0;i<list.length;i++){
    				  		$("#city_id").append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
    				  	}
    			  	}
    		  },
    		  error:function(a){
    		  	alert("异常");
    		  }
    	});
    }
    	
    //获取区域
    function addsearcharea(){
    	var str=$("#city_id option:selected").val();//获取被选中的value值
    	$.ajax({
    		  url: '<%=path%>/zhihui_subsidiary/arealist.do',
    		  data:"city_id="+str,
    		  type:"post",
    		  dataType:"json",
    			  success:function(data){
    			  	var list=data.arealist;
    			  	$("#area_id option").remove();
    			  	$("#area_id").append("<option  value=''>区/县</option>");
    			  	if(list.length>0){
    				  	for(var i=0;i<list.length;i++){
    				  		$("#area_id").append("<option value='"+list[i].pcd_id+"'>"+list[i].name+"</option>");
    				  	}
    		  		}
    		  },
    		  error:function(a){
    		  alert("异常");
    		  }
    	});
    };
   
</script>
</body>
</html>