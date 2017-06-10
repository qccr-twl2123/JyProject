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
        <link rel="stylesheet" href="css/zhihui/xitong2.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/xitong2.js"></script>
        <script type="text/javascript" src="js/jquery.form.js"></script>
        <style type="text/css">
	        .xitong2_d1_d1_sp3{
			    width: 10%;
			    height: 30px;
			    cursor: pointer;
			}
			.xitong2_d1_d1_sp3 span{
			    width: 30px;
			    height: 30px;
			    text-align: center;
			    line-height: 30px;
			    color: #5dc9ba;
			    border:1px solid #5dc9ba;
			    border-radius: 50%;
			    display: none;
			}
        </style>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    <form action="zhihui_menu_role/${msg}.do" id="Form" name="Form" method="post">
      <div class="yingxiao8_d1">
         <input type="hidden" name="menu_role_id" value="${pd.menu_role_id }">
        <input type="hidden"  name="currentPage" value="${currentPage }">
      </div> 
      <div class="yingxiao8_d1">
        <span class="yingxiao8_d1_sp1">角色名称</span>
        <input class="yingxiao8_d1_ipt1" name="menu_role_name" id="menu_role_name" value="${pd.menu_role_name }" type="text"/>
        <c:if test="${pd.role_type ne '0'}">
	         <span class="yingxiao8_d1_sp1">服务类型</span>
	        <select class="yingxiao8_d1_ipt1"  name="role_type">
	        	<option value="1" ${pd.role_type eq '1'?'selected':'' }>总部</option>
	        	<option value="2" ${pd.role_type eq '2'?'selected':'' }>服务商</option>
	        </select>
         </c:if>
        </div> 
       <div class="xitong2_d1" id="changeQx">
                 <c:forEach items="${allList}" var="var" >
                 	<div class="new_xitong2">
                 		<div class="xitong2_d1_st1">
	                 		<span class="xitong2_d1_st1_sp1">
		                         <span class="xitong2_d1_st1_sp1_img">
		                           <img src="img/new_down.png" width="100%" height="100%" class="new_tb">
		                         </span>
		                         <span class="xitong2_d1_st1_sp1_text">${var.menu_parent_name}</span>
		                     </span>
		                     <span class="xitong2_d1_st1_sp2">
		                        <label>
		                          <input class="xitong2_ipt1" type="checkbox"  />
		                          <span class="new_jc_ck1">全部</span>
		                        </label>
		                      </span>
		                       <span class="xitong2_d1_st1_sp2">
		                        <label>
		                          <input class="xitong2_ipt1" type="checkbox" />
		                          <span class="new_jc_ck2">全部</span>
		                        </label>
		                      </span>
		                       <span class="xitong2_d1_st1_sp2">
		                        <label>
		                          <input class="xitong2_ipt1" type="checkbox" />
		                          <span class="new_jc_ck3">全部</span>
		                        </label>
		                      </span>
		                       <span class="xitong2_d1_st1_sp2">
		                        <label>
		                          <input class="xitong2_ipt1" type="checkbox" />
		                          <span class="new_jc_ck4">全部</span>
		                        </label>
		                      </span>
		                </div>
		                 <!-- 0.0. -->
                  		<div class="xitong2_no">
	                     <c:forEach items="${var.sonList}" var="sonvar" >
	                     		 <div class="xitong2_d1_st2">
		                           <span class="xitong2_d1_st1_sp1 xitong2_d1_st2_sp2">
		                                <span class="xitong2_d1_st1_sp1_text ">${sonvar.menu_name}</span>
		                            </span>
		                            <span class="xitong2_d1_st1_sp2">
		                              <label>
		                                <input class="xitong2_ipt1" type="checkbox" ${sonvar.look eq '1'?'checked':''}/>
		                                <input  type="hidden" class="X" name="${sonvar.menu_id}look" value="${sonvar.look}" />
		                                <span class="new_ck1">查</span>
		                              </label>
		                            </span>
		                            <c:if test="${qx.edit eq '1'}">
		                             <span class="xitong2_d1_st1_sp2">
		                              <label>
		                                <input class="xitong2_ipt1" type="checkbox" ${sonvar.edit eq '1'?'checked':''}/>
		                                <input  type="hidden" class="X"  name="${sonvar.menu_id}edit" value="${sonvar.edit}" />
		                                <span class="new_ck2">修</span>
		                              </label>
		                            </span>
		                            </c:if>
		                            <c:if test="${qx.delete eq '1'}">
		                             <span class="xitong2_d1_st1_sp2">
		                              <label>
		                                <input class="xitong2_ipt1" type="checkbox" ${sonvar.del eq '1'?'checked':''}/>
		                                <input type="hidden" class="X"  name="${sonvar.menu_id}del" value="${sonvar.del}" />
		                                <span class="new_ck3">删</span>
		                              </label>
		                            </span>
		                            </c:if>
		                            <c:if test="${qx.add eq '1'}">
		                             <span class="xitong2_d1_st1_sp2">
		                              <label>
		                                <input class="xitong2_ipt1" type="checkbox"  ${sonvar.save eq '1'?'checked':''} />
		                                <input  type="hidden" class="X"  name="${sonvar.menu_id}save" value="${sonvar.save}" />
		                                <span class="new_ck4">增</span>
		                              </label>
		                            </span>
		                            </c:if>
		                        </div> 
	                     </c:forEach>
	                  </div>
	             </div> 
             </c:forEach>
       </div>
       
      <!-- 确认后的效果 -->
      <div class="xitong2_d2" id="sureQx" style="display:none">
	           <div class="xitong2_d3">
	              <div class="xitong2_d1_d1">
	                 <span class="xitong2_d1_d1_sp1">基础档案</span>
	                 <span class="xitong2_d1_d1_sp2">全部</span>
	                 <span class="xitong2_d1_d1_sp2">全部</span>
	                 <span class="xitong2_d1_d1_sp2">全部</span>
	                 <span class="xitong2_d1_d1_sp2">全部</span>
	              </div>
	          </div>
	          <div class="xitong2_d3">
	              <div class="xitong2_d1_d1">
	                 <span class="xitong2_d1_d1_sp4">子公司档案</span>
	                 <span class="xitong2_d1_d1_sp2">可读</span>
	                 <span class="xitong2_d1_d1_sp2">修改</span>
	                 <span class="xitong2_d1_d1_sp2">删除</span>
	                 <span class="xitong2_d1_d1_sp2">上传</span>
	                 <span class="xitong2_d1_d1_sp3">
	                    <span >X</span>
	                 </span>
	              </div>
	          </div> 
	         
      </div>
      <c:if test="${qx.add eq '1'}">
	      <div class="yingxiao8_d1" id="sure">
	          <span class="yingxiao8_yes" onclick="saveRole()">确定</span>
	      </div> 
      </c:if>
      <div class="yingxiao8_d1 shuaxin" style="display:none;">
		          <span class="yingxiao8_yes" onclick="sureShuaXin()">确定</span>
		      </div> 
     </form>
     </c:if>
     <script type="text/javascript">
     //刷新
       function sureShuaXin(){
    	   window.location.href='<%=basePath%>zhihui_menu_role/list.do?menu_id=23';  
       }
    	//确认
    	function saveRole(){
    	 	var name = $("#menu_role_name").val().trim();
    		if( name == ""){
    			alert("角色名称不能为空");
    			return;
    		}
    		$.ajax({
					type:"post",
	                url:'<%=path%>/zhihui_menu_role/isname.do', 
	                data:{
	                	menu_role_name:name
 	                },
	                success: function(data){
	                	if(data.result == "00"){
							alert("角色名称已存在！");
							return ;
	 					}else{
	 						$("#Form").ajaxSubmit({  
			    		  	url : '<%=basePath%>zhihui_menu_role/saveRole.do',
			    	        type: "POST",//提交类型  
			    	      	dataType:"json",
			    	   		success:function(data){
			    	   			$("#changeQx").hide();
			    	   			$("#sure").remove();
			    	   			$("#sureQx").show();
			    	   			$("#sureQx").empty();
			    	   			var parent=data.data;
			    				for(var i=0 ; i<parent.length ; i++){
			     					$("#sureQx").append("<div class='xitong2_d3'> <div class='xitong2_d1_d1'> <span class='xitong2_d1_d1_sp1'>"+parent[i].menu_parent_name+"</span> <span class='xitong2_d1_d1_sp2'></span> <span class='xitong2_d1_d1_sp2'></span> <span class='xitong2_d1_d1_sp2'></span> <span class='xitong2_d1_d1_sp2'></span> </div> </div>");
			    					var son=parent[i].sortAll;
			    					for(var j=0 ; j<son.length ; j++){
			    						var str="<div class='xitong2_d3'> <div class='xitong2_d1_d1'>";
			    						str+="<span class='xitong2_d1_d1_sp4'>"+son[j].menu_name+"</span>";
			    						if(son[j].save == "1"){
			    							str+="<span class='xitong2_d1_d1_sp2'>增</span>";
			    						}
			    						if(son[j].del == "1"){
			    							str+="<span class='xitong2_d1_d1_sp2'>删</span>";
			    						}
			    						if(son[j].edit == "1"){
			    							str+="<span class='xitong2_d1_d1_sp2'>改</span>";
			    						}
			    						if(son[j].look == "1"){
			    							str+="<span class='xitong2_d1_d1_sp2'>查</span>";
			    						}
			    						str+="<span class=\"xitong2_d1_d1_sp2\"><span><a  onclick=\"del(\'"+son[j].qx_id+"\',this)\"  style='color:blue;cursor: pointer;'>X</a></span></span></div></div>";
			    						$("#sureQx").append(str);
			    					}
			    				}
			    	   			 
			    			}
			    		});
	 					
	 					}
	                }
	 			});
    		   $(".shuaxin").show();
    		   $("#sure").hide();
      	}
    	
    	//删除
    	function del(value,obj){
    		 $.ajax({
                    type:"post",
                    url:'<%=basePath%>/zhihui_menu_role/delQx.do', 
               		data:{"qx_id":value},           
                    dataType:"json",
                    success: function(data){
          					if(data.result == "1"){
          						$(obj).parents('.xitong2_d3').remove();
          					}
                    }
                });
    	}
    	
    	
    </script>
    </body>
 </html>