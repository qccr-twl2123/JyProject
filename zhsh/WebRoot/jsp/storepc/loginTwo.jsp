<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="IE=9" >
    <meta name="renderer" content="webkit">
    <title>班次选择</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
    <link rel="stylesheet" href="css/pcstore/hsd_banci.css">
    <link rel="stylesheet" href="css/pcstore/bootstrap.min.css">
</head>
<body onkeydown="BindEnter(event)">
    <section>
        <div class="cont">
            <h3>班次|桌号的选择</h3>
            <div class="fir">第<span class="bord">1</span>步：选择班次</div>
            <div class="fir_cont">
            		<c:forEach items="${shiftList}" var="var">
	                    <div class="ra col-lg-4 col-sm-4 col-xs-4"><input type="radio" value="all" name="bc" value="${var.store_shift_id}"><span>${var.shift_name }</span></div>
               		</c:forEach>
             </div>
            <div class="fir"> 第 <span class="bord">2</span> 步 ： 选择收银对应桌号</div>
            <div class="sec_cont">
				<c:forEach items="${deskList}" var="var">
                    <div class="check col-lg-3  col-xs-3"><input type="checkbox" name="zh" value="${var.table_name}"><span>${var.table_name}</span></div> <!--有空格时与复选框有间距-->
 				</c:forEach>
				<div class="check col-lg-3  col-xs-3" id="checkall"><input type="checkbox" ><span>选择全部</span></div>
			</div>
        </div>
        <h6>说明：桌号是指店内消费者使用的位置，收银员在登录时，根据自己收银台所对应的桌号进行勾选。老板登录时如果不选择班次和桌号，默认为全选桌号。</h6>
        <div class="checkbutton">
		    <c:if test="${pd.type eq '1'}">
				<div class="button button2"  >员工登录</div>
				<div class="button"   onclick="goLoginOne()" id="typelogin">老板直接登录</div>
			</c:if>
            <c:if test="${pd.type eq '2'}">
				<div class="button"   onclick="goLoginTwo()" id="typelogin">员工登录</div>
				<div class="button button2"   >老板直接登录</div>
			</c:if>
        </div>
    </section>
</body>
<script src="<%=basePath%>js/jquery-1.12.4.min.js"></script>
<script>
    var check=$("#checkall");
    var timecheck=$(".ra");
    var zccheck=$(".check");
    var inp=$("input");
    inp.click(function(){
        this.checked=!this.checked;
    })
    console.log(timecheck);
    check.click(function(){
        var that=$(this).children("input")[0];
        var fla=that.checked;
        if(!fla){
            $("input[name='zh']").prop("checked", true);
        }else{
            $("input[name='zh']").prop("checked", false);
        }
    })
    timecheck.click(function(){
        var that=$(this).children("input")[0];
            that.checked=!that.checked;
    })
    zccheck.click(function(){
        var that=$(this).children("input")[0];
            that.checked=!that.checked;
    })
</script>
<script type="text/javascript">
    $(function(){
         $("#cbAll").click(function(){
                $("input[name='checkbox2']").prop("checked",$(this).prop("checked"));
         });
    });
    //老板
    function goLoginOne(){
    	window.location.href="<%=basePath%>storepc/goStore.do";
    }
    //员工
    function goLoginTwo(){
    	var store_shift_id="";
    	var n=-1;
    	$("input[name='bc']").each(function(index,obj){
     		if($(obj).is(":checked")){
    			n=index;
    			store_shift_id=$(obj).val();
    		}
    	});
    	if(n == -1){
    		alert("请选择班次");
    		return;
    	}
    	var alldesk_no="";
    	var m=-1;
    	$("input[name='zh']").each(function(index,obj){
     		if($(obj).is(":checked")){
    			m=index;
    			alldesk_no+=$(obj).val()+",";
    		}
    	});
    	if(m == -1){
    		alert("请选择桌号");
    		return;
    	}
    	if("${pd.type }" == "2"){
    		window.location.href="<%=basePath%>storepc/overBanCiZhouHao.do?type=${pd.type}&store_operator_id=${pd.gologin_id}&store_id=${pd.store_id}&store_shift_id="+store_shift_id+"&alldesk_no="+alldesk_no;
    	}
     }
    
    //使用document.getElementById获取到按钮对象
	function BindEnter(event){
		var typelogin = document.getElementById("typelogin");
		if(event.keyCode == 13){
			typelogin.click();
			event.returnValue = false;
		}
	}
</script>
</html>