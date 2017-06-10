 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
    <head>
        <title>档案管理</title>
        <meta charset="utf-8">
        <link rel="stylesheet" href="css/dangan4.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/dangan4.js"></script>
        <script src="js/bg.js"></script>
    </head>
    <body>
       <div class="dangan2_d1">
         <select class="dangan2_d1_st1">
           <option>浙江省</option>
           <option>浙江省</option>
         </select>
         <select class="dangan2_d1_st1">
           <option>杭州市</option>
           <option>杭州市</option>
         </select>
         <select class="dangan2_d1_st1">
           <option>滨江区</option>
           <option>滨江区</option>
         </select>
         <select class="dangan2_d1_st2">
           <option>不限</option>
           <option>不限</option>
         </select>
         <input type="text" placeholder="可输分类、关键字" class="dangan2_d1_ipt1"></input>
         <button class="dangan2_d1_btncx">查询</button>
         <button class="dangan2_d1_btnmb">添加模板</button>
       </div>
       <div class="dangan2_d2">
          <table  border="0" cellspacing="0" cellpadding="0" class="dangan2_d2_table">
              <tr class="tdtop">
                <td>序号</td>
                <td>省</td>
                <td>市</td>
                <td>区/县</td>
                <td>是否启用</td>
                <td></td>
              </tr>   
              <tr>
                <td>0001</td>
                <td>浙江省</td>
                <td>杭州市</td>
                <td>滨江区</td>
                <td>是</td>
                <td><span class="td_sp1">修改</span><span class="td_sp1">删除</span></td>
              </tr>
               <tr>
                <td>0002</td>
                <td>浙江省</td>
                <td>杭州市</td>
                <td>滨江区</td>
                <td>是</td>
                <td><span class="td_sp1">修改</span><span class="td_sp1">删除</span></td>
              </tr>     
               <tr>
                <td>0003</td>
                <td>浙江省</td>
                <td>杭州市</td>
                <td>滨江区</td>
                <td>否</td>
                <td><span class="td_sp1">修改</span><span class="td_sp1">删除</span></td>
              </tr>     
               <tr>
                <td>0004</td>
                <td>浙江省</td>
                <td>杭州市</td>
                <td>滨江区</td>
                <td>否</td>
                <td><span class="td_sp1">修改</span><span class="td_sp1">删除</span></td>
              </tr>     
               <tr>
                <td>0005</td>
                <td>浙江省</td>
                <td>杭州市</td>
                <td>滨江区</td>
                <td>是</td>
                <td><span class="td_sp1">修改</span><span class="td_sp1">删除</span></td>
              </tr>                   
          </table>
       </div>
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
    </body>
</html> --%>