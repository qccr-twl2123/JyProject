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
        <title>优选推送</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/zhihui/shixiang3.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <style type="text/css">
        .yingxiao8_d1 {
		    width: 79%;
		    height: 60px;
		    margin-left: 19%;
		}
		.shixiang3_ipt {
		    width: 33%;
		    height: 100px;
		    border: 1px solid #8f8f8f;
		    border-radius: 5px;
		    margin-left: 20%;
		    padding-left: 2%;
		}
        </style>
     </head>
    <body>
	    <c:if test="${qx.look eq '1'}">
 	      <form action="<%=basePath%>youxuan/sure_tuisong.do" name="Form" id="Form" method="post">
	    	  <input  type="hidden" name="currentPage"   value="${pd.currentPage}" />
	    	  <input  type="hidden" name="youxuangoods_id"   value="${pd.youxuangoods_id}" />
	    	  <input  type="hidden" name="tuisong_number"   value="1" />
		      <div class="yingxiao8_d1">
		          <span class="yingxiao8_d1_sp1">用户区域</span>
		          <select class="yingxiao8_d1_st1 st2" name="province_name" id="province_name" >
	 			  	<option value="${cpd.province_name}">${cpd.province_name}</option>
	 	           </select>
		          <select class="yingxiao8_d1_st1 st2" id="city_name" name="city_name" >
		              <option value="${cpd.city_name}">${cpd.city_name}</option>
		           </select>
		          <select class="yingxiao8_d1_st1 st2" id="area_name" name="area_name">
		              <option value="${cpd.area_name}">${cpd.area_name}</option>
		           </select>
		      </div>
		      <div class="yingxiao8_d1">
		          <span class="yingxiao8_d1_sp1">来源商家</span>
		          <input  type="text"    class="yingxiao8_d1_st1"  value="${cpd.store_name }" disabled="disabled" />
		      </div>
		      <div class="yingxiao8_d1">
		          <span class="yingxiao8_d1_sp1">爆屏名称</span>
		          <input  type="text"    class="yingxiao8_d1_st1"  value="${cpd.goods_name }" disabled="disabled" />
		      </div>
		      <div class="yingxiao8_d1">
		          <span class="yingxiao8_d1_sp1">爆屏格</span>
		          <input  type="text"   class="yingxiao8_d1_st1"  value="${cpd.bp_salemoney }"  disabled="disabled"/>
		      </div>
		      <div class="yingxiao8_d1">
		          <span class="yingxiao8_d1_sp1">爆屏折扣率</span>
		          <input  type="text"    class="yingxiao8_d1_st1"  value="${cpd.goods_zkrate }"  disabled="disabled"/>%
		      </div>
	 	      <input  type="text" name="content"  class="shixiang3_ipt" placeholder="输入推送内容" />
		      <c:if test="${qx.add eq '1'}">
		 	      <div class="yingxiao8_d1">
			          <span class="yingxiao8_yes" onclick="search()">开始推送会员</span>
			      </div> 
		      </c:if>
	       </form>
        </c:if>
    </body>
	<script type="text/javascript">
 		//检索
		function search(){
 			$("#Form").submit();
		}
 	</script>
</html>