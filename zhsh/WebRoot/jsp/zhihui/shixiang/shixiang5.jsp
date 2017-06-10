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
        <link rel="stylesheet" href="css/shixiang5.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/shixiang5.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
       <div class="shixiang5_d1">
          <div class="shixiang5_d1_d1">
            今天
            <span class="shixiang5_d1_d1_sp1">X</span>
          </div>
          <div class="shixiang5_d1_d2 height200">
            <span class="shixiang5_d1_d2_sp1">
              <img src="img/txleft.png">
            </span>
            <span class="shixiang5_d1_d2_sp2">
                <img src="img/photo.png">
            </span>
          </div>
           <div class="shixiang5_d1_d2">
              <span class="shixiang5_d1_d2_sp3">
                <img src="img/txright.png">
              </span>
              <span class="shixiang5_d1_d2_sp4">
                  你好，麻烦今天帮我预定一个十人桌的酒席，谢谢！
              </span>
          </div>
          <div class="shixiang5_d1_d2 ">
            <span class="shixiang5_d1_d2_sp1">
              <img src="img/txleft.png">
            </span>
            <span class="shixiang5_d1_d2_sp5">
              好的，不用客气！
            </span>
          </div>
          <div class="shixiang5_d1_d3 ">
            <dl class="dangan2_d3">
              <dt class="dangan2_d3_sp2_2">共2页，每页5行</dt>
              <dt  class="dangan2_d3_sp2">页</dt>
              <dt>
                <select class="dangan2_d3_st1">
                  <option>1</option>
                  <option>2</option>
                  <option>3</option>
                  <option>4</option>
                  <option>5</option>
                </select>
              </dt>
              <dt  class="dangan2_d3_sp2">第</dt>
              <dt class="dangan2_dt1 leftimg4"><img src="img/right.png" width="120%" height="130%"></dt>
              <dt class="dangan2_dt1 leftimg3"><img src="img/right2.png" width="120%" height="130%"></dt>
              <dt class="dangan2_dt1 bordernone">.....</dt>
              <dt class="dangan2_dt1">4</dt>
              <dt class="dangan2_dt1">3</dt>
              <dt class="dangan2_dt1">2</dt>
              <dt class="dangan2_dt1">1</dt>
              <dt class="dangan2_dt1 leftimg2"><img src="img/left2.png" width="120%" height="130%"></dt>
              <dt class="dangan2_dt1 leftimg"><img src="img/left.png" width="120%" height="130%"></dt>
           </dl>
          </div>
       </div>
       </c:if>
    </body>
</html>