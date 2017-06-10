<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>我的会员</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/sj_hy.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<style type="text/css">
	.sel_sty{
		width: 168px;
	}
	.padd_td {
	    height:20px;
	}
	</style>
</head>
<body>
    <c:if test="${storeqx.look eq '1'}">
    <form action="storepc_vip/goMyVIP.do?store_id=${storepd.store_id}" method="post" name="Form" id="Form">
	<ul>
		<li class="padd_td">
			 <span>会员来源</span>:
			 <select name="source_type" onchange="find_vip()" class="sel_sty">
					<option value="1" ${pd.source_type eq '1'?'selected':'' }>平台导入</option>
					<option value="2" ${pd.source_type eq '2'?'selected':'' }>实体店导入</option>  
					<option value="3" ${pd.source_type eq '3'?'selected':'' }>一度人脉</option>
			 </select>
 			<!--  <span class="anniu-s flr " onclick="downloadAPP('0')">全选实体店会员</span>
			 <span class="flr" style="line-height:36px;"><img src="img/jiantou.png" alt="" ></span>
			 <span class="anniu-s flr mar_l" onclick="downloadAPP('1')">推荐下载APP</span> -->
			  <c:if test="${storeqx.add eq '1'}">
				  <span class="anniu-s flr mar_l" onclick="importInfo()">导入实体店会员</span>
				 <span class="anniu-s flr mar_l" onclick="importInfoTwo()">下载模版</span>
			  </c:if>
 		</li>
		<li class="padd_td">
 			<div class="hy_num_box">
				<span>全部会员总数：</span>
				<span class="col-r">${pd.allnumber_w}</span>
			</div>
			<div class="hy_num_box">
				<span>实体店导入会员数：</span>
				<span class="col-r">${pd.allexcel}</span>
			</div>
			<div class="hy_num_box">
				<span>平台导入会员总数：</span>
				<span class="col-r">${pd.allvip}</span>
			</div>
			<div class="hy_num_box">
				<span>一度人脉会员总数：</span>
				<span class="col-r">${pd.allrenmai}</span>
			</div>
		</li>
		<li class="padd_td">
		   
			<div class="hy_num_box"><span class="cx_item">姓名</span>：<input type="text" name="name" value="${pd.name}"></div>
			<%-- <div class="hy_num_box"><span class="cx_item">性别</span>：
			<select name="sex"  class="sel_sty">
				<option value="">请选择性别</option>
				<option value="1" ${pd.sex eq '1'?'selected':'' }>男</option>
				<option value="2" ${pd.sex eq '2'?'selected':'' }>女</option>
			</select>
			</div> --%>
			<div class="hy_num_box"><span>手机号码</span>：
			<input type="text" maxlength="11" name="phone" value="${pd.phone}"></div>
			<span class="anniu-m" onclick="find_vip()">查询</span>
		</li>
		<li class="padd_td" style=" display: inline; ">
			<table cellspacing="0" cellpadding="0" style="width:100%;margin-top:15px;border:1px solid #a4a4a4;white-space: nowrap;">
				<thead>
					<td><input type="checkbox" id = "setAll" class="checkall setAll"></td>
					<td>序号</td>
					<td>姓名</td>
					<!-- <td>性别</td> -->
					<td>手机号码</td>
					<td>生日</td>
					<td>积分余额</td>
					<td>最后一次消费时间</td>
					<td>本店累计消费金额</td>
					<td>会员来源</td>
					<td>备注</td>
					<td>操作</td>
				</thead>
				<tbody>
					 <c:forEach items="${vip_list}" var="vip" varStatus="vs">
						<tr>
							<td style="text-align: left;"> 
								<c:if test="${vip.source_type eq '2'}">
									<input type="checkbox" name = "check"  value="${vip.phone}"/>
								</c:if>
							</td>
							<td>${vs.index+1}</td>
							<td><input style="width: 100px;" type="text" id="${vip.member_id}name" value="${vip.name}"/></td>
							<%-- <td> 
								<c:if test="${vip.sex eq '1'}">
								男
								</c:if>
								<c:if test="${vip.sex eq '2'}">
								女
								</c:if>
							</td> --%>
							<td>${vip.phone}</td>
							<td><input style="width: 130px;"  type="date" id="${vip.member_id}born_date" value="${vip.born_date}"/></td>
							 <td>${vip.now_money }</td>
							 <td>${fn:substring(vip.lastsale_time,0,19)} </td>
							 <td>${vip.allsalemoney }</td>
							<td> 
								<c:if test="${vip.source_type eq '1'}">
								平台导入
								</c:if>
								<c:if test="${vip.source_type eq '2'}">
								实体店导入
								</c:if>
								<c:if test="${vip.source_type eq '3'}">
								一度人脉
								</c:if>
							</td>
							<td><input  style="width: 100px;" type="text" id="${vip.member_id}remarks" value="${vip.remarks}"/></td>
							<td>
								<c:if test="${storeqx.edit eq '1'}">
									<c:if test="${vip.source_type eq '1'}">
										无权操作
									</c:if>
									<c:if test="${vip.source_type eq '2'}">
										<span class="xiugai" onclick="edit('${vip.source_type}','${vip.store_id}','${vip.member_id}')">确认修改</span>
									</c:if>
									<c:if test="${vip.source_type eq '3'}">
										无权操作
									</c:if>
								</c:if>
								<%-- <c:if test="${storeqx.delete eq '1'}">
									<span><a onclick="del('${vip.source_type}','${vip.store_id}','${vip.member_id}')">删除</a></span>
								</c:if> --%>
							</td>
						 </tr>
						</c:forEach>
				</tbody>
			</table>
		</li>
		<li class="padd_td">
			<div class="fenye clf">
				<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
			</div>
		</li>
	</ul>
	</form>
	<form  action="storepc_vip/addExcel.do" enctype="multipart/form-data" method="post" id="Form1" name="Form1">
			<input type="file" name="file" id="file" style="display: none" onchange="importExcel()">
			<input type="hidden" name="store_id" value="${storepd.store_id}"/>
			<input type="hidden" name="source_type" value="${pd.source_type}"/>
	</form>
	</c:if>
<script src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
      //复选框的全选和全不选
    	$(function() {
            $(".setAll").click(function() {
                $('input[name="check"]').attr("checked",this.checked); 
             });  
        });
    	
        	//下载Excel模板表格
			function importInfoTwo(){
				window.location.href="<%=basePath%>storepc_vip/downExcel.do";
			}
        	//导入excel
			function importInfo(){
				$("#file").click();
			}
			function importExcel(){
				$("#Form1").submit();
			}
			
			//推荐下载app
        	function downloadAPP(value){
				if(value =="0"){
					$.ajax({
            			url:"storepc_vip/sendMessageByPhone.do",
            			type:"GET",
            			dataType:"json",
            			data:{"str":"0","store_id":"${storepd.store_id}"},
            			success:function(data){
            				if(data.result == "1"){
            					alert("短信已发送成功");
            				}
            			}
            		});
				}else{
 					var str="";
	        		var n=-1;
	        		$("input[name='check']:checked").each(function(i,o){
	        			n=i;
	         			var id=$(o).val();
	         			str+=id+"@";
	        		});
	        		if(n==-1){
	        			alert("请先选择需要推荐的会员");
	        			return;
	        		}else{
	        			$.ajax({
	            			url:"storepc_vip/sendMessageByPhone.do",
	            			type:"GET",
	            			dataType:"json",
	            			data:{"str":str,"store_id":"${storepd.store_id}"},
	            			success:function(data){
	            				if(data.result == "1"){
	            					alert("短信已发送成功");
	            				}
	            			}
	            		});
	        		}
				}
          	}
        	//查询
			function find_vip(){
				$("#Form").submit();
			}
        	
        	//删除---暂定没用到
        	function del(source_type,store_id,member_id){
        		$.ajax({
        			url:"storepc_vip/deleteVip.do",
        			type:"GET",
        			dataType:"json",
        			data:{"source_type":source_type,"store_id":store_id,"member_id":member_id},
        			success:function(data){
        				$("#Form").submit();
        			}
        		});
        	}
        	
        	//修改
        	function edit(source_type,store_id,member_id){
        		var name=$("#"+member_id+"name").val();
        		var born_date=$("#"+member_id+"born_date").val();
        		var remarks=$("#"+member_id+"remarks").val();
        		$.ajax({
        			url:"storepc_vip/editVip.do",
        			type:"GET",
        			dataType:"json",
        			data:{"source_type":source_type,"store_id":store_id,"member_id":member_id,
        				"name":name,"born_date":born_date,"remarks":remarks
        			},
        			success:function(data){
        				if(data.result == "1"){
        					alert("修改成功");
        				}
         			}
        		});
        	}
        </script>
</body>

</html>