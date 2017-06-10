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
        <title>营销管理</title>
        <meta charset="utf-8">
         <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/zhihui/yingxiao3.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/yingxiao3.js"></script>
        <style type="text/css">
        	input[type=checkbox]{
			    opacity: 1;
			    position: initial;
 			}
        </style>
    </head>
    <body>
    	<c:if test="${qx.look eq '1'}">
        <div class="dangan2_d1">
         <span  class="zhifu1_sp1">所属服务商</span>
         <select class="zhifu1_st1" name="sp_file_id" id="sp_file_id" onchange="getStore(this.value)">
           <option value="">全部</option>
           <c:forEach items="${spList}" var="var"> 
           		<option value="${var.sp_file_id}">${var.team_name}</option>
           </c:forEach>
          </select>
         <span  class="zhifu1_sp1">商家名称</span>
         <select class="zhifu1_st1" name="store_id" id="store_id">
           <option value="">全部</option>
          </select>
        </div>
       <div class="dangan2_d1">
          <span  class="zhifu1_sp1">搜索商家</span>
          <input class="zhifu1_ipt1" type="text" placeholder="可输入商家名称/负责人名称等查询" name="content" id="content"/>
          <span class="zhifu1_btn1" onclick="searchStore()">查询</span>
       </div>
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
          	<thead>
              <tr >
                <td><input type="checkbox" class="dangan14_ipt1 dangan14iptclick" name ="check" value="0"/> </td>
                <td>商家名称</td>
                <td>红包</td>
                <td>红包数量</td>
                <td>发送范围</td>
              </tr>
            </thead>
            <tbody id="tbodyall">

            </tbody>
          </table>
       </div>
       
        <div class="dangan2_d1">
        <c:if test="${qx.add eq '1'}">
          <span class="zhifu1_btn2 mgleft40" onclick="saveStore()">添加</span>
          </c:if>
          <span class="zhifu1_btn2">退出</span>
          <p style="color: red;margin-left: 430px;margin-right: 430px;">添加的商家必须是本城市（区县一级）</p>
       </div>
       </c:if>
       <script type="text/javascript">
       var city_marketing_id="${city_marketing_id}";//城市营销参数ID
       var city_file_id="${city_file_id}";//城市档案ID
       var currentPage="${currentPage}";//当前页
       
       	//查询
       function searchStore(){
     	   var store_id=$("#store_id option:selected").val();//获取被选中的商家值
     	   var content=$("#content").val();//获取被选中的商家值
     	   if(store_id == "" && content == ""){
     		   alert("查询前，请先填写商家信息！");
     		   return;
     	   }
       	   //使用ajax从后台获取数据
    	   $.ajax({
				  url: 'zhihuicity_marketing/getRedPackageListStore.do',
				  data:{"store_id":store_id,"content":content},
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  $("#tbodyall").empty();
					  if(data.result == '01'){
						  var list=data.redlist;
						  	$("#tbodyall").empty();
	  					  	if(list.length>0){
							  	for(var i=0;i<list.length;i++){
	 						  		var str="<tr> "+
								               " <td><input type='checkbox' class='dangan14_ipt1' value='"+list[i].store_redpackets_id+"' id='ids' name ='check'/></td>"+
								               " <td>"+list[i].store_name+"</td>"+
								               " <td>"+list[i].money+"元红包</td>"+
								               " <td>"+list[i].redpackage_number+"个</td>"+
								               " <td>"+list[i].srp_opentype_name+"</td>"+
								              "</tr>";
								    $("#tbodyall").append(str); 
							  	}
						  	} 
  					  }
 				  }
			});
       		
       }
       	
       	
       	//获取商家
       	function getStore(value){
       		$.ajax({
				  url: 'zhihuicity_marketing/getStoreListBySp.do',
				  data:{"sp_file_id":value},
				  type:"post",
				  dataType:"json",
				  success:function(data){
					  	var list=data.allstore;
					  	$("#store_id").empty();
					  	$("#store_id").append("<option value=''>请选择商家</option>");
					  	if(list.length>0){
						  	for(var i=0;i<list.length;i++){
						  		$("#store_id").append("<option value='"+list[i].store_id+"'>"+list[i].store_name+"</option>");
						  	}
					  	} 
				  }
			});
       		
       	}
       	
       	var allredId="";
       	//添加
       	function saveStore(){
       		var obj = document.getElementsByName('check');
    		var fw = '';
    		for ( var i = 0; i < obj.length; i++) {
				if(obj[i].checked) fw += obj[i].value+',';
			}
     		if(fw==''){
    			alert('你还没有选择任何内容！');
    			return;
    		}
       		//获取所有被选中的
       		var checkedObj = $('input:checkbox[id="ids"]:checked'); //获取当前checked的value值 如果选中多个则循环
       		checkedObj.each(function() { 
       			var m= this.value; 
       			allredId +=m+"@"; 
       		});
       		window.location.href='zhihuicity_marketing/goEdit.do?&city_file_id='+city_file_id+"&currentPage="+currentPage+"&allredId="+allredId;       		
       	}
       	
       	
       </script>
    </body>
</html>