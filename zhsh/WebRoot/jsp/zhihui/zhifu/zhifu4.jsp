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
        <title>支付管理</title>
        <meta charset="utf-8">
        <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
        <link rel="stylesheet" href="css/zhihui/zhifu4.css">
        <script src="js/jquery-1.8.0.min.js"></script>
        <script src="js/zhihui/zhifu4.js"></script>
        <script src="My97DatePicker/WdatePicker.js"></script>
        <!-- 用户提现/汇款统计 -->
        <script src="https://code.highcharts.com/highcharts.js"></script>
		<script src="https://code.highcharts.com/modules/data.js"></script>
		<script src="https://code.highcharts.com/modules/drilldown.js"></script>
    </head>
    <body>
    <c:if test="${qx.look eq '1'}">
    	<form action="zhihuicount_allmoney/countList.do" name="Form" id="Form" method="post">
	       <div class="dangan2_d1">
	            <span  class="zhifu1_sp1">查询月份</span>
	            <input class="zhifu1_st1" type="text" name="startmonth" id="startmonth" value="${pd.startmonth}" placeholder="开始月份" onclick="WdatePicker({dateFmt:'yyyy-MM'})"/>
	            <select class="zhifu1_st1" name="province_id" id="province_id" onchange="addsearchfind();">
		           	<option value="">请选择省</option>
		           	<c:forEach items="${provincelist}" var="var" varStatus="vs">
						<option value="${var.pcd_id}">${var.name}</option>
					</c:forEach>
					<c:if test="${pd.province_id ne '' and  !empty pd.province_id}">
								<option value="${pd.province_id}"  selected="selected">${pd.province_name}</option>
					</c:if>
	 	         </select>
		         <select class="zhifu1_st1" name="city_id" id="city_id" onchange="addsearcharea();">
		         	 <option value="${pd.city_id}">${pd.city_name}</option>
	 	         </select>
		         <select class="zhifu1_st1" name="area_id" id="area_id">
		         	 <option value="${pd.area_id}">${pd.area_name}</option>
	 	         </select>
	 	         <input type="hidden" name="province_name" id="province_name" value="${pd.province_name}"  />
			     <input type="hidden" name="city_name" id="city_name" value="${pd.city_name}"  />
			     <input type="hidden" name="area_name" id="area_name" value="${pd.area_name}"  />
 	         </div>
	         <div class="dangan2_d1">
	           <span  class="zhifu1_sp1">用户类型</span>
	           <select class="zhifu1_st1">
	             <option value="">全部</option>
		         <option value="1" ${pd.user_type eq '1'?'selected':''}>商家</option>
		         <option value="2" ${pd.user_type eq '2'?'selected':''}>会员</option>
		         <option value="3" ${pd.user_type eq '3'?'selected':''}>服务商</option>
           		 <%-- <option value="4" ${pd.user_type eq '4'?'selected':''}>业务员</option> --%>
	           </select>
	           <input class="zhifu1_ipt1" type="text" placeholder="可输入账号/金额等查询" name="content" id="content" />
	           <span class="zhifu1_btn1" onclick="checked()">查询</span>
	       </div>
	       <div class="zhifu4_d1">
	          <dl class="zhifu4_d1_dl">
	            <dt>
	              <div id="allhkcontainer" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
	            </dt>
	            <dd>当月汇款图如上所示</dd>
	          </dl>
	          <br>
	          <dl class="zhifu4_d1_dl">
	            <dt>
	              <div id="alltxcontainer" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
	            </dt>
	            <dd>当月提现图如上所示</dd>
 	          </dl>
 	           <br>
	          <dl class="zhifu4_d1_dl">
	            <dt>
	              <div id="hkcontainer" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
	            </dt>
	            <dd>当月用户汇款金额图如上所示</dd>
 	          </dl>
 	           <br>
	          <dl class="zhifu4_d1_dl">
	            <dt>
	              <div id="txcontainer" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
	            </dt>
	            <dd>当月用户提现金额图如上所示</dd>
 	          </dl>
 	       </div>
       </form>
       </c:if>
      <script type="text/javascript">
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
 					  	$("#city_name").val("");
 					  	$("#area_name").val("");
 					  	$("#city_id").append("<option value=''>请选择市</option>");
 					  	if(list.length>0){
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
					  	$("#area_id").append("<option  value=''>请选择区</option>");
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
		}
		
      	function checked(){
      		if($("#city_id option:selected").val() != ""){
				var city_name=$("#city_id option:selected").text();
				$("#city_name").show();
				$("#city_name").val(city_name);
			}
			if($("#province_id option:selected").val() != ""){
				var province_name=$("#province_id option:selected").text();
				$("#province_name").show();
				$("#province_name").val(province_name);
			}
			if($("#area_id option:selected").val() != ""){
				var area_name=$("#area_id option:selected").text();
				$("#area_name").show();
				$("#area_name").val(area_name);
			}
      		$("#Form").submit();//提交
      	}
      	
//提现会员统计图
$(function () {
    //当月提现用户金额多到少
	    $('#txcontainer').highcharts({
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: ''
	        },
	        xAxis: {
	        	 type: 'category'
	        },
	        yAxis: {
	            title: {
	                text: '金额'
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        plotOptions: {
	            series: {
	                borderWidth: 0,
	                dataLabels: {
	                    enabled: true,
	                    format: '{point.y:.2f}'
	                }
	            }
	        },
	
	        tooltip: {
 	            pointFormat: '姓名：<span>{point.drilldown}</span><br/>金额： <b>{point.y:.2f}</b>'
	        },
	
	        series: [{
	            name: 'Brands',
	            colorByPoint: true,
	            data: [
							{
							    name: '1',
							    y: parseFloat("${pd.onetx}") ,
							    drilldown:"${pd.onetxname}"
							}, {
							    name: '2',
							    y: parseFloat("${pd.twotx}"),
							    drilldown: "${pd.twotxname}"
							}, {
							    name: '3',
							    y: parseFloat("${pd.allthreetx}"),
							    drilldown:"${pd.threetxname}"
							}, {
							    name: '4',
							    y: parseFloat("${pd.fourtx}"),
							    drilldown:"${pd.fourtxname}"
							}, {
							    name: '5',
							    y:parseFloat("${pd.fivetx}"),
							    drilldown:"${pd.fivetxname}"
							}, {
							    name: '6',
							    y: parseFloat("${pd.sixtx}"),
							    drilldown:"${pd.sixtxname}"
							}, {
							    name: '7',
							    y: parseFloat("${pd.seventx}"),
							    drilldown:"${pd.seventxname}"
							}, {
							    name: '8',
							    y:parseFloat( "${pd.eighttx}"),
							    drilldown: "${pd.eighttxname}"
							},
							{
							    name: '9',
							    y: parseFloat("${pd.aninetx}"),
							    drilldown: "${pd.aninetxname}"
							}, {
							    name: '10',
							    y: parseFloat("${pd.tentx}"),
							    drilldown: "${pd.tentxname}"
							}, {
							    name: '11',
							    y: parseFloat("${pd.eleventx}"),
							    drilldown: "${pd.eleventxname}"
							}, {
							    name: '12',
							    y: parseFloat("${pd.twelvetx}"),
							    drilldown: "${pd.twelvetxname}"
							}, {
							    name: '13',
							    y: parseFloat("${pd.thirteentx}"),
							    drilldown: "${pd.thirteentxname}"
							}, {
							    name: '14',
							    y: parseFloat("${pd.fourteentx}"),
							    drilldown:  "${pd.fourteentxname}"
							}, {
							    name: '15',
							    y: parseFloat("${pd.fifteentx}"),
							    drilldown: "${pd.fifteentxname}"
							}, {
							    name: '16',
							    y: parseFloat("${pd.sixteentx}"),
							    drilldown: "${pd.sixteentxname}"
							},
							{
							    name: '17',
							    y: parseFloat("${pd.seventeentx}"),
							    drilldown: "${pd.seventeentxname}"
							}, {
							    name: '18',
							    y: parseFloat("${pd.eighteentx}"),
							    drilldown:"${pd.eighteentxname}"
							}, {
							    name: '19',
							    y: parseFloat("${pd.nineteentx}"),
							    drilldown: "${pd.nineteentxname}"
							}, {
							    name: '20',
							    y:parseFloat( "${pd.twentytx}"),
							    drilldown: "${pd.twentytxname}"
							}, {
							    name: '21',
							    y: "${pd.twentyonetx}",
							    drilldown: "${pd.twentyonetxname}"
							}, {
							    name: '22',
							    y: parseFloat("${pd.twentytwotx}"),
							    drilldown: "${pd.twentytwotxname}"
							}, {
							    name: '23',
							    y: parseFloat("${pd.twentythreetx}"),
							    drilldown: "${pd.twentythreetxname}"
							}, {
							    name: '24',
							    y: parseFloat("${pd.twentyfourtx}"),
							    drilldown: "${pd.twentyfourtxname}"
							},
							{
							    name: '25',
							    y: parseFloat("${pd.twentyfivetx}"),
							    drilldown: "${pd.twentyfivetxname}"
							}, {
							    name: '26',
							    y: parseFloat("${pd.twentysixtx}"),
							    drilldown: "${pd.twentysixtxname}"
							}, {
							    name: '27',
							    y: parseFloat("${pd.twentyseventx}"),
							    drilldown: "${pd.twentyseventxname}"
							}, {
							    name: '28',
							    y:parseFloat( "${pd.twentyeighttx}"),
							    drilldown: ""
							}, {
							    name: '29',
							    y: parseFloat("${pd.twentyninetx}"),
							    drilldown: "${pd.twentyninetxname}"
							}, {
							    name: '30',
							    y: parseFloat("${pd.thirtytx}"),
							    drilldown: "${pd.thirtytxname}"
							}, {
							    name: '31',
							    y: parseFloat("${pd.thirtyonetx}"),
							    drilldown: "${pd.thirtyonetxname}"
							}  
													] 
	        }]
	    });
    
    //当月汇款用户金额多到少
	    $('#hkcontainer').highcharts({
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: ''
	        },
	        xAxis: {
	        	 type: 'category'
	        },
	        yAxis: {
	            title: {
	                text: '金额'
	            }
	
	        },
	        legend: {
	            enabled: false
	        },
	        plotOptions: {
	            series: {
	                borderWidth: 0,
	                dataLabels: {
	                    enabled: true,
	                    format: '{point.y:.2f}'
	                }
	            }
	        },
	
	        tooltip: {
 	            pointFormat: '姓名：<span>{point.drilldown}</span><br/>金额： <b>{point.y:.2f}</b>'
	        },
	
	        series: [{
	            name: 'Brands',
	            colorByPoint: true,
	            data: [
							{
							    name: '1',
							    y: parseFloat("${pd.onehk}") ,
							    drilldown:"${pd.onehkname}"
							}, {
							    name: '2',
							    y: parseFloat("${pd.twohk}"),
							    drilldown: "${pd.twohkname}"
							}, {
							    name: '3',
							    y: parseFloat("${pd.allthreehk}"),
							    drilldown:"${pd.threehkname}"
							}, {
							    name: '4',
							    y: parseFloat("${pd.fourhk}"),
							    drilldown:"${pd.fourhkname}"
							}, {
							    name: '5',
							    y:parseFloat("${pd.fivehk}"),
							    drilldown:"${pd.fivehkname}"
							}, {
							    name: '6',
							    y: parseFloat("${pd.sixhk}"),
							    drilldown:"${pd.sixhkname}"
							}, {
							    name: '7',
							    y: parseFloat("${pd.sevenhk}"),
							    drilldown:"${pd.sevenhkname}"
							}, {
							    name: '8',
							    y:parseFloat( "${pd.eighthk}"),
							    drilldown: "${pd.eighthkname}"
							},
							{
							    name: '9',
							    y: parseFloat("${pd.aninehk}"),
							    drilldown: "${pd.aninehkname}"
							}, {
							    name: '10',
							    y: parseFloat("${pd.tenhk}"),
							    drilldown: "${pd.tenhkname}"
							}, {
							    name: '11',
							    y: parseFloat("${pd.elevenhk}"),
							    drilldown: "${pd.elevenhkname}"
							}, {
							    name: '12',
							    y: parseFloat("${pd.twelvehk}"),
							    drilldown: "${pd.twelvehkname}"
							}, {
							    name: '13',
							    y: parseFloat("${pd.thirteenhk}"),
							    drilldown: "${pd.thirteenhkname}"
							}, {
							    name: '14',
							    y: parseFloat("${pd.fourteenhk}"),
							    drilldown:  "${pd.fourteenhkname}"
							}, {
							    name: '15',
							    y: parseFloat("${pd.fifteenhk}"),
							    drilldown: "${pd.fifteenhkname}"
							}, {
							    name: '16',
							    y: parseFloat("${pd.sixteenhk}"),
							    drilldown: "${pd.sixteenhkname}"
							},
							{
							    name: '17',
							    y: parseFloat("${pd.seventeenhk}"),
							    drilldown: "${pd.seventeenhkname}"
							}, {
							    name: '18',
							    y: parseFloat("${pd.eighteenhk}"),
							    drilldown:"${pd.eighteenhkname}"
							}, {
							    name: '19',
							    y: parseFloat("${pd.nineteenhk}"),
							    drilldown: "${pd.nineteenhkname}"
							}, {
							    name: '20',
							    y:parseFloat( "${pd.twentyhk}"),
							    drilldown: "${pd.twentyhkname}"
							}, {
							    name: '21',
							    y: "${pd.twentyonehk}",
							    drilldown: "${pd.twentyonehkname}"
							}, {
							    name: '22',
							    y: parseFloat("${pd.twentytwohk}"),
							    drilldown: "${pd.twentytwohkname}"
							}, {
							    name: '23',
							    y: parseFloat("${pd.twentythreehk}"),
							    drilldown: "${pd.twentythreehkname}"
							}, {
							    name: '24',
							    y: parseFloat("${pd.twentyfourhk}"),
							    drilldown: "${pd.twentyfourhkname}"
							},
							{
							    name: '25',
							    y: parseFloat("${pd.twentyfivehk}"),
							    drilldown: "${pd.twentyfivehkname}"
							}, {
							    name: '26',
							    y: parseFloat("${pd.twentysixhk}"),
							    drilldown: "${pd.twentysixhkname}"
							}, {
							    name: '27',
							    y: parseFloat("${pd.twentysevenhk}"),
							    drilldown: "${pd.twentysevenhkname}"
							}, {
							    name: '28',
							    y:parseFloat( "${pd.twentyeighthk}"),
							    drilldown: ""
							}, {
							    name: '29',
							    y: parseFloat("${pd.twentyninehk}"),
							    drilldown: "${pd.twentyninehkname}"
							}, {
							    name: '30',
							    y: parseFloat("${pd.thirtyhk}"),
							    drilldown: "${pd.thirtyhkname}"
							}, {
							    name: '31',
							    y: parseFloat("${pd.thirtyonehk}"),
							    drilldown: "${pd.thirtyonehkname}"
							}  
					]
	        }]
	    });
    
	  //当月提现总金额
	    $('#alltxcontainer').highcharts({
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: ''
	        },
	        xAxis: {
	        	 type: 'category'
	        },
	        yAxis: {
	            title: {
	                text: '金额'
	            }
	
	        },
	        legend: {
	            enabled: false
	        },
	        plotOptions: {
	            series: {
	                borderWidth: 0,
	                dataLabels: {
	                    enabled: true,
	                    format: '{point.y:.2f}'
	                }
	            }
	        },
	
	        tooltip: {
 	            pointFormat: '金额： <b>{point.y:.2f}</b>'
	        },
	
	        series: [{
	            name: 'Brands',
	            colorByPoint: true,
	            data: [
							{
							    name: '1',
							    y: parseFloat("${pd.allonetx}") ,
							    drilldown:""
							}, {
							    name: '2',
							    y: parseFloat("${pd.alltwotx}"),
							    drilldown: ""
							}, {
							    name: '3',
							    y: parseFloat("${pd.allthreetx}"),
							    drilldown:""
							}, {
							    name: '4',
							    y: parseFloat("${pd.allfourtx}"),
							    drilldown:""
							}, {
							    name: '5',
							    y:parseFloat("${pd.allfivetx}"),
							    drilldown:""
							}, {
							    name: '6',
							    y: parseFloat("${pd.allsixtx}"),
							    drilldown:""
							}, {
							    name: '7',
							    y: parseFloat("${pd.allseventx}"),
							    drilldown:""
							}, {
							    name: '8',
							    y:parseFloat( "${pd.alleighttx}"),
							    drilldown: ""
							},
							{
							    name: '9',
							    y: parseFloat("${pd.allninetx}"),
							    drilldown: ""
							}, {
							    name: '10',
							    y: parseFloat("${pd.alltentx}"),
							    drilldown: ""
							}, {
							    name: '11',
							    y: parseFloat("${pd.alleleventx}"),
							    drilldown: ""
							}, {
							    name: '12',
							    y: parseFloat("${pd.alltwelvetx}"),
							    drilldown: ""
							}, {
							    name: '13',
							    y: parseFloat("${pd.allthirteentx}"),
							    drilldown: ""
							}, {
							    name: '14',
							    y: parseFloat("${pd.allfourteentx}"),
							    drilldown:  ""
							}, {
							    name: '15',
							    y: parseFloat("${pd.allfifteentx}"),
							    drilldown: ""
							}, {
							    name: '16',
							    y: parseFloat("${pd.allsixteentx}"),
							    drilldown: ""
							},
							{
							    name: '17',
							    y: parseFloat("${pd.allseventeentx}"),
							    drilldown: ""
							}, {
							    name: '18',
							    y: parseFloat("${pd.alleighteentx}"),
							    drilldown:""
							}, {
							    name: '19',
							    y: parseFloat("${pd.allnineteentx}"),
							    drilldown: ""
							}, {
							    name: '20',
							    y:parseFloat( "${pd.alltwentytx}"),
							    drilldown: ""
							}, {
							    name: '21',
							    y: "${pd.alltwentyonetx}",
							    drilldown: "${pd.alltwentyonetxname}"
							}, {
							    name: '22',
							    y: parseFloat("${pd.alltwentytwotx}"),
							    drilldown: ""
							}, {
							    name: '23',
							    y: parseFloat("${pd.alltwentythreetx}"),
							    drilldown: ""
							}, {
							    name: '24',
							    y: parseFloat("${pd.alltwentyfourtx}"),
							    drilldown: ""
							},
							{
							    name: '25',
							    y: parseFloat("${pd.alltwentyfivetx}"),
							    drilldown: ""
							}, {
							    name: '26',
							    y: parseFloat("${pd.alltwentysixtx}"),
							    drilldown: ""
							}, {
							    name: '27',
							    y: parseFloat("${pd.alltwentyseventx}"),
							    drilldown: ""
							}, {
							    name: '28',
							    y:parseFloat( "${pd.alltwentyeighttx}"),
							    drilldown: ""
							}, {
							    name: '29',
							    y: parseFloat("${pd.alltwentyninetx}"),
							    drilldown: ""
							}, {
							    name: '30',
							    y: parseFloat("${pd.allthirtytx}"),
							    drilldown: ""
							}, {
							    name: '31',
							    y: parseFloat("${pd.allthirtyonetx}"),
							    drilldown: ""
							}   
	                   
				]
	        }]
	    });
	  
	  //当月总汇款金额 
	    $('#allhkcontainer').highcharts({
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: ''
	        },
	        xAxis: {
	        	 type: 'category'
	        },
	        yAxis: {
	            title: {
	                text: '金额'
	            }
	
	        },
	        legend: {
	            enabled: false
	        },
	        plotOptions: {
	            series: {
	                borderWidth: 0,
	                dataLabels: {
	                    enabled: true,
	                    format: '{point.y:.2f}'
	                }
	            }
	        },
	
	        tooltip: {
 	            pointFormat: '金额： <b>{point.y:.2f}</b>'
	        },
	
	        series: [{
	            name: 'Brands',
	            colorByPoint: true,
	            data: [
						{
						    name: '1',
						    y: parseFloat("${pd.allonehk}") ,
						    drilldown:""
						}, {
						    name: '2',
						    y: parseFloat("${pd.alltwohk}"),
						    drilldown: ""
						}, {
						    name: '3',
						    y: parseFloat("${pd.allthreehk}"),
						    drilldown:""
						}, {
						    name: '4',
						    y: parseFloat("${pd.allfourhk}"),
						    drilldown:""
						}, {
						    name: '5',
						    y:parseFloat("${pd.allfivehk}"),
						    drilldown:""
						}, {
						    name: '6',
						    y: parseFloat("${pd.allsixhk}"),
						    drilldown:""
						}, {
						    name: '7',
						    y: parseFloat("${pd.allsevenhk}"),
						    drilldown:""
						}, {
						    name: '8',
						    y:parseFloat( "${pd.alleighthk}"),
						    drilldown: ""
						},
						{
						    name: '9',
						    y: parseFloat("${pd.allninehk}"),
						    drilldown: ""
						}, {
						    name: '10',
						    y: parseFloat("${pd.alltenhk}"),
						    drilldown: ""
						}, {
						    name: '11',
						    y: parseFloat("${pd.allelevenhk}"),
						    drilldown: ""
						}, {
						    name: '12',
						    y: parseFloat("${pd.alltwelvehk}"),
						    drilldown: ""
						}, {
						    name: '13',
						    y: parseFloat("${pd.allthirteenhk}"),
						    drilldown: ""
						}, {
						    name: '14',
						    y: parseFloat("${pd.allfourteenhk}"),
						    drilldown:  ""
						}, {
						    name: '15',
						    y: parseFloat("${pd.allfifteenhk}"),
						    drilldown: ""
						}, {
						    name: '16',
						    y: parseFloat("${pd.allsixteenhk}"),
						    drilldown: ""
						},
						{
						    name: '17',
						    y: parseFloat("${pd.allseventeenhk}"),
						    drilldown: ""
						}, {
						    name: '18',
						    y: parseFloat("${pd.alleighteenhk}"),
						    drilldown:""
						}, {
						    name: '19',
						    y: parseFloat("${pd.allnineteenhk}"),
						    drilldown: ""
						}, {
						    name: '20',
						    y:parseFloat( "${pd.alltwentyhk}"),
						    drilldown: ""
						}, {
						    name: '21',
						    y: "${pd.alltwentyonehk}",
						    drilldown: "${pd.alltwentyonehkname}"
						}, {
						    name: '22',
						    y: parseFloat("${pd.alltwentytwohk}"),
						    drilldown: ""
						}, {
						    name: '23',
						    y: parseFloat("${pd.alltwentythreehk}"),
						    drilldown: ""
						}, {
						    name: '24',
						    y: parseFloat("${pd.alltwentyfourhk}"),
						    drilldown: ""
						},
						{
						    name: '25',
						    y: parseFloat("${pd.alltwentyfivehk}"),
						    drilldown: ""
						}, {
						    name: '26',
						    y: parseFloat("${pd.alltwentysixhk}"),
						    drilldown: ""
						}, {
						    name: '27',
						    y: parseFloat("${pd.alltwentysevenhk}"),
						    drilldown: ""
						}, {
						    name: '28',
						    y:parseFloat( "${pd.alltwentyeighthk}"),
						    drilldown: ""
						}, {
						    name: '29',
						    y: parseFloat("${pd.alltwentyninehk}"),
						    drilldown: ""
						}, {
						    name: '30',
						    y: parseFloat("${pd.allthirtyhk}"),
						    drilldown: ""
						}, {
						    name: '31',
						    y: parseFloat("${pd.allthirtyonehk}"),
						    drilldown: ""
						}   
					]
	        }]
	    });
});
      </script>
    </body>
</html>