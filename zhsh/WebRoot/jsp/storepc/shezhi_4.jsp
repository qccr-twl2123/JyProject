<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
    <title>新建操作员</title>
	<base href="<%=basePath%>">
	<link rel="shortcut icon" href="<%=basePath%>store_favicon.ico" >
     <link rel="Bookmark" href="<%=basePath%>store_favicon.ico">
     <link rel="icon" type="image/gif" href="<%=basePath%>store_animated_favicon1.gif" >
    <link rel="stylesheet" href="css/pcstore/bootstrap.min.css">
    <link rel="stylesheet" href="css/pcstore/hsd_operator.css">
    <style type="text/css">
    .button_box:HOVER {
		cursor: pointer;
	}
    </style>
</head>
<body onkeydown="BindEnter(event)">
<div class="dask">
    <div class="alert">
        <div class="al_head">
            <div class="close"></div>
            <div class="alert_tit">
                <span>新建操作员</span>
                <div class="one"></div>
            </div>
        </div>
        <div class="al_body">
            <form class="form-horizontal" role="form" action="storepcOperator_file/save.do" name="addForm" id="addForm">
                <input type="hidden" name="jichushezhi"   value="${pd.jichushezhi}"/>
				<input type="hidden" name="store_id" value="${pd.store_id}"/>
				<div class="form-group">
                    <label  class="col-xs-2 control-label justify">姓名：</label>
                    <div class="col-xs-2">
                        <input type="text" class="form-control"  placeholder="姓名" name="operator_name">
                    </div>
                </div>
                <div class="form-group">
                    <label  class="col-xs-2 control-label justify">手机号码：</label>
                    <div class="col-xs-3">
                        <input type="text" class="form-control"  placeholder="手机号码" name="operator_phone" maxlength="11" onkeyup="value=value.replace(/[^\d]/g,'')"  onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
                    </div>
                </div>
                <div class="form-group">
                    <label  class="col-xs-2 control-label justify">角色：</label>
                    <div class="col-xs-2">
                        <select class="form-control"  name="operator_position">
                            <option value="1">员工</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label  class="col-xs-2 control-label justify">班次：</label>
                    <div class="col-xs-2">
                        <select class="form-control" name="store_shift_id">
                            <c:forEach items="${bcList}" var="var" varStatus="vs">
								<option value="${var.store_shift_id }">${var.shift_name}</option>
	                   		</c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label  class="col-xs-2 control-label justify">权限管理：</label>
                    <div class="col-xs-10">
                    <div class="lab_cont col-xs-10 ">
                        <span class="col-xs-2 padd_t">收银</span>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="sylook" type="hidden" value="0">
							<span class="col-xs-11">查看</span>
                        </label>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="syadd" type="hidden" value="0">
							<span class="col-xs-11">增加</span>
                        </label>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="sydelete" type="hidden" value="0">
							<span class="col-xs-11">删除</span>
                        </label>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="syedit" type="hidden" value="0">
							<span class="col-xs-11">修改</span>
                        </label>
                    </div>
                    <div class="lab_cont col-xs-10">
                        <span class="col-xs-2 padd_t">营销</span>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)" >
							<input name="yxlook" type="hidden" value="0">
							<span class="col-xs-11">查看</span>
                        </label>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="yxadd" type="hidden" value="0">
							<span class="col-xs-11">增加</span>
                        </label>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="yxdelete" type="hidden" value="0">
							<span class="col-xs-11">删除</span>
                        </label>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="yxedit" type="hidden" value="0">
							<span class="col-xs-11">修改</span>
                        </label>
                    </div>
                    <div class="lab_cont col-xs-10">
                        <span class="col-xs-2 padd_t">互动</span>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="hdlook" type="hidden" value="0">
							<span class="col-xs-11">查看</span>
                        </label>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="hdadd" type="hidden" value="0">
							<span class="col-xs-11">增加</span>
                        </label>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="hddelete" type="hidden" value="0">
							<span class="col-xs-11">删除</span>
                        </label>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="hdedit" type="hidden" value="0">
							<span class="col-xs-11">修改</span>
                        </label>
                    </div>
                    <div class="lab_cont col-xs-10">
                        <span class="col-xs-2 padd_t">商品</span>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="splook" type="hidden" value="0">
							<span class="col-xs-11">查看</span>
                        </label>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="spadd" type="hidden" value="0">
							<span class="col-xs-11">增加</span>
                        </label>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="spdelete" type="hidden" value="0">
							<span class="col-xs-11">删除</span>
                        </label>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="spedit" type="hidden" value="0">
							<span class="col-xs-11">修改</span>
                        </label>
                    </div>
                    <div class="lab_cont col-xs-10">
                        <span class="col-xs-2 padd_t">财务</span>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="cwlook" type="hidden" value="0">
							<span class="col-xs-11">查看</span>
                        </label>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="cwadd" type="hidden" value="0">
							<span class="col-xs-11">增加</span>
                        </label>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="cwdelete" type="hidden" value="0">
							<span class="col-xs-11">删除</span>
                        </label>
                        <label class="checkbox-inline col-xs-2">
                            <input type="checkbox" class="check" value="option1"  onclick="changeStatus(this)">
							<input name="cwedit" type="hidden" value="0">
							<span class="col-xs-11">修改</span>
                        </label>
                    </div>
                    </div>
                    </div>
            </form>
        </div>
        <div class="al_foot">
            <div class="button_box" onclick="AddOpratore()">
                <div class="butt next">确认新增</div>
            </div>
        </div>
    </div>
</div>
<div class="bg">
<header>
    <div class="head_cont">
        <img src="img/page/operator/operator.png" alt="" class="logo">
        <div class="title">•  操作员 </div>
        <div class="one"></div>
    </div>
</header>
<section>
    <div class="sec_cont">
        <div class="button_box">
            <div class="butt butt_next">新建操作员</div>
        </div>
    </div>
    <div class="tab_box">
        <div class="table-responsive tab">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <td>ID号</td>
                        <td>手机号</td>
                        <td>姓名</td>
                        <td>服务</td>
                        <td>状态</td>
                        <td>上班时间</td>
                        <td>创建时间</td>
                    </tr>
                </thead>
                <tbody>
					<c:forEach items="${list}" var="l">
						<tr>
							<td>${l.store_operator_id}</td>
							<td>${l.operator_phone}</td>
							<td>${l.operator_name}</td>
	 						<td>
								<c:if test="${l.operator_position eq '1'}">员工</c:if>
								<c:if test="${l.operator_position eq '2'}">老板</c:if>
							</td>
							<td>
								<c:if test="${l.operator_status eq '1'}">正常</c:if>
								<c:if test="${l.operator_status eq '0'}">禁用</c:if>
							</td>
 							<td>	
								${fn:substring(l.logintime,0,19)}
							</td>
							<td>	
								${fn:substring(l.createdate,0,19)}
							</td>
	 					</tr>
					</c:forEach> 
                </tbody>
            </table>
        </div>
    </div>
</section>
<footer>
    <div class="font_cont">
        <div class="button_box" onclick="gonext()" id="gonext">
            <div class="butt">稍后添加，前往下一步</div>
        </div>
    </div>
</footer>
</div>
<script src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
        //前往下一步
		function gonext(){
			window.location.href="<%=basePath%>storepc/goSheZhiOne.do?jichushezhi=${pd.jichushezhi}&store_id=${pd.store_id}";
		}
		
		 //确认操作员新增
	    function AddOpratore(){
	    	$("#addForm").submit();
	    }
		
	  //使用document.getElementById获取到按钮对象
		function BindEnter(event){
			var gonext = document.getElementById("gonext");
			if(event.keyCode == 13){
				gonext.click();
				event.returnValue = false;
			}
		}
	    
</script>
<script>
    $(".butt_next").click(function() {
        $(".dask").css({"display":"block"})
        $(".close").click(function(){
            $(".dask").css({"display":"none"})
        })
    });
	
	
</script>
</body>
</html>