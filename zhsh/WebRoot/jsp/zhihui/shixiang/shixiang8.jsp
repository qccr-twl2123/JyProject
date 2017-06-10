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
        <title>事项预沟通</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/zhihui/shixiang8.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/shixiang8.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
      <div class="shixiang8_d1">
        <div class="shixiang8_d1_left">
            <div class="shixiang8_d1_left_header">
                <input class="shixiang8_d1_ipt1 " type="text" placeholder="搜索" />
                <span class="shixiang8_d1_search">
                    <img src="img/search.png" width="100%">
                </span>
                <span class="shixiang8_d1_append">
                   <img src="img/appen.png" width="100%">
                </span>
            </div>
            <dl class="shixiang8_d1_dl">
              <dd>
                 <span class="dl_tx">
                   <img src="img/txtaok.png" width="100%">
                 </span>
                 <div class="dl_text">
                   <div class="dl_text_d1">
                     <span class="dl_text_sp1">XXXX名称</span>
                     <span class="dl_text_sp2">11:26</span>
                   </div>
                   <div  class="dl_text_d2">
                     平面-设计师-小宝：
                   </div>
                 </div>
              </dd>
              <dd>
                 <span class="dl_tx">
                   <img src="img/txtaok.png" width="100%">
                 </span>
                 <div class="dl_text">
                   <div class="dl_text_d1">
                     <span class="dl_text_sp1">XXXX名称</span>
                     <span class="dl_text_sp2">11:26</span>
                   </div>
                   <div  class="dl_text_d2">
                     平面-设计师-小宝：
                   </div>
                 </div>
              </dd>
              <dd>
                 <span class="dl_tx">
                   <img src="img/txtaok.png" width="100%">
                 </span>
                 <div class="dl_text">
                   <div class="dl_text_d1">
                     <span class="dl_text_sp1">XXXX名称</span>
                     <span class="dl_text_sp2">11:26</span>
                   </div>
                   <div  class="dl_text_d2">
                     平面-设计师-小宝：
                   </div>
                 </div>
              </dd>
              <dd class="active_dd">
                 <span class="dl_tx">
                   <img src="img/txleft.png" width="100%">
                 </span>
                 <div class="dl_text">
                   <div class="dl_text_d1">
                     <span class="dl_text_sp1">XXXX名称</span>
                     <span class="dl_text_sp2">11:26</span>
                   </div>
                   <div  class="dl_text_d2">
                     平面-设计师-小宝：
                   </div>
                 </div>
              </dd>
              <dd>
                 <span class="dl_tx">
                   <img src="img/txtaok.png" width="100%">
                 </span>
                 <div class="dl_text">
                   <div class="dl_text_d1">
                     <span class="dl_text_sp1">XXXX名称</span>
                     <span class="dl_text_sp2">11:26</span>
                   </div>
                   <div  class="dl_text_d2">
                     平面-设计师-小宝：
                   </div>
                 </div>
              </dd>
              <dd>
                 <span class="dl_tx">
                   <img src="img/txtaok.png" width="100%">
                 </span>
                 <div class="dl_text">
                   <div class="dl_text_d1">
                     <span class="dl_text_sp1">XXXX名称</span>
                     <span class="dl_text_sp2">11:26</span>
                   </div>
                   <div  class="dl_text_d2">
                     平面-设计师-小宝：
                   </div>
                 </div>
              </dd>
              <dd>
                 <span class="dl_tx">
                   <img src="img/txtaok.png" width="100%">
                 </span>
                 <div class="dl_text">
                   <div class="dl_text_d1">
                     <span class="dl_text_sp1">XXXX名称</span>
                     <span class="dl_text_sp2">11:26</span>
                   </div>
                   <div  class="dl_text_d2">
                     平面-设计师-小宝：
                   </div>
                 </div>
              </dd>
            </dl>
        </div>
        <div class="shixiang8_d1_right">
            <span class="shixiang8_d1_right_sp1">夏天 <span class="shixiang8_d1_right_img"><img src="img/down.png" width="100%"></span></span>
            <div class="shixiang8_d1_right_d1">
              <span class="shixiang8_d1_right_d1_tx">
                  <img src="img/txleft.png">
              </span>
              <span class="shixiang8_d1_right_d1_content">
                  <img src="img/photo.png">
              </span>
            </div>
            <div class="shixiang8_d1_right_d2">
                <span class="shixiang8_d1_right_d1_tx1"><img src="img/txleft.png"></span>
                <span class="shixiang8_d1_right_d2_content">你好，麻烦今天帮我订一个十人桌的酒席，谢谢.</span>
            </div>
            <div class="shixiang8_d1_right_d2">
                <span class="shixiang8_d1_right_d1_tx"><img src="img/txright.png"></span>
                <span class="shixiang8_d1_right_d2_content  left_content">好的，不用客气.</span>
            </div>
        </div>
        <div class="shixiang8_d1_right_d3">
          <div class="shixiang8_d1_right_d3_tp">
              <span class="tp_1"><img src="img/xiao.png" width="100%" ></span>
              <span class="tp_1"><img src="img/wenzi.png" width="100%" ></span>
              <span class="tp_1"><img src="img/wenjian.png" width="100%" ></span>
              <span class="tp_1"><img src="img/jianqie.png" width="100%" ></span>
          </div>
          <c:if test="${qx.add eq '1'}">
          <span class="send">发送(S)</span> 
          </c:if>   
        </div>
      </div>
      
      
</c:if>
    </body>
</html>