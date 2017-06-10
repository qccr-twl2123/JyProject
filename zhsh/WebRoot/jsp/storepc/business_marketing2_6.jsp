<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>积分设置</title>
	 <base href="<%=basePath%>">
    <link rel="stylesheet" href="css/pcstore/bootstrap.min.css">
    <link rel="stylesheet" href="css/pcstore/hsd_integration.css">
	<script src="js/jquery-1.8.0.min.js"></script>
	<style type="text/css">
	body {
	    background: #fff;
	}
	@media screen and (max-width: 1200px) {
	  section .sec {
	    width: 100%;
	  }
	}
	.bg {
    	width: 100%;
 	}
 	section .sec .nav_tit ul .act {
	    background: #156cb1;
	}
	li :HOVER {
		cursor: pointer;
	}
 	</style>
</head>
<c:if test="${storeqx.look eq '1'}">
<body  onkeydown="BindEnter(event)">
<div class="bg">
<section>
    <div class="sec">
        <div class="nav_tit">
			<ul class="nav nav-pills nav-justified" role="tablist">
				<li role="presentation" class="chose zd"><a onclick="shaixin('1')">整店送积分设置</a></li>
				<li role="presentation" class="chose pl"><a onclick="shaixin('2')">品类送积分设置</a></li>
				<li role="presentation" class="chose dp"><a onclick="shaixin('3')">单品送积分设置</a></li>
				<li role="presentation" class="chose bs"><a onclick="shaixin('4')">交易笔数送积分设置</a></li>
				<li role="presentation" class="chose je"><a onclick="shaixin('5')">金额阶梯送积分设置</a></li>
			</ul>
		</div>
         <div class="nav_cont zd">   <!--整店送积分设置-->
			<p class="nav_dec">声明：选择该方式时，会员在商家消费买单时，按照<span>实际买单金额X整店积分率</span>的方式进行积分返还。</p>
            <div class="form-group fm1">
				<label class="lab control-label">积分率</label>
				<select class="sele onejifen">
				   <c:forEach var="num" begin="1" end="100">
					 <option value="${num}" ${pd.oneback_rate eq num?'selected':'' }>${num}%</option>
				   </c:forEach>
				</select>
			</div>
             <label class="checkbox-inline use">
                <input ${pd.change_type eq '1'?'checked':'' } type="checkbox" class="check" value="option1" id="one" onclick="showtext(this)"> 使用
            </label>
			<span class="tips">提醒：您正在切换积分设置，切换后原有设置立即失效。同时部分功能将无法使用，请谨慎选择。</span>
			<c:if test="${storeqx.add eq '1'}">
				<div class="button_box">
					<div class="anniu-m" onclick="onesave('one')">确定</div>
				</div>
			</c:if>
		</div>
		<script type="text/javascript">
    	function onesave(id){
    		if(!$("#"+id).is(":checked")){
				alert("请先勾选");
				return ;
			}
     		var backRate = $('.onejifen option:selected').val();
			var grantrule = "积分率："+backRate+"%";
 			$.ajax({
				type:"post",
				url:"storepc_scoreway/save.do",
				dataType:"json",
 				data:{
 						content:"整店送积分",
 						selected_status:"1",
 						change_type:"1",
 						oneback_rate:backRate,
 						integral_rate:backRate,
 						grantrule : grantrule,
 						store_id:"${storepd.store_id}"
 					},
				success:function(data){
					if(data.result == "01"){
						alert("设置成功");
						window.location.reload(); //刷新当前页面
					}
 				}
			});
    	}
    </script>
        <div class="nav_cont pl">   <!--品类送积分设置-->
        <p class="nav_dec">
            声明： <span>按照类别金额X类别积分率</span>的方式进行积分返还
        </p>
        <p class="nav_dec">
            例如：某烟酒专卖店将类别设置为烟、酒、茶三个类别，每个类别设置不同的积分率分别为2%，5%，15%，不同品类的积分率设置方式可有效帮助商家进行精细化营销，
          <!--  <span>修改积分后，需点击修改方可生效.</span> -->
        </p>
             <div class="form-group fm1 ">
				<c:forEach items="${varList}" var="var" varStatus="vs">
			 
					<div class="col-xs-6 cate">
						<label class=" col-lg-5 col-md-4 cool-sm-4 col-xs-5 control-label lab"  id = "twoname">${var.name}积分率</label>
						<select class="col-lg-2 col-md-3 col-xs-4 sele twojifen ${var.goods_category_id}" onchange="editgoods_category('${var.goods_category_id}')">
							<option value="0">0%</option>
							<c:forEach var="num" begin="1" end="100">
								<option value="${num}" ${num eq var.back_rate?'selected':'' }>${num}%</option>
							</c:forEach>
						</select>
 					</div>
                 
				</c:forEach> 
            </div>
            <h5 style="clear: both;">
	             <label class="checkbox-inline use">
	                    <input ${pd.change_type eq '2'?'checked':'' } type="checkbox" class="check" value="option1" id="two" onclick="showtext(this)"> 使用
	             </label>
            </h5>
          <p class="tips">提醒：您正在切换积分设置，切换后原有设置立即失效。同时部分功能将无法使用，请谨慎选择。</p>
        <c:if test="${storeqx.add eq '1'}">
        <div class="button_box">
            <div class="anniu-m" onclick="twosure('two')">确定</div>
        </div>
        </c:if>
    </div>
	<script type="text/javascript">
	    var ww_allgoods_category_id="";
    	//修改分类的积分率
		function editgoods_category(goods_category_id){
			if(ww_allgoods_category_id.indexOf(goods_category_id) == -1 ){
				ww_allgoods_category_id+=goods_category_id+"@";//积分商品分类id
 			} 
   			var back_rate = $("."+goods_category_id).val();//返积分率
 			$.ajax({
				type:"post",
				url:"storepc_CategoryManageController/editRate.do",
				async:false,
 				data:{
 						goods_category_id : goods_category_id,
 						back_rate : back_rate 
					},
				success:function(data){
					if(data.result="01"){
						//alert("修改成功");
 					}
  				}
			});  
  			
		}
		
		
		//确认使用
		function twosure(id){
 			  if(!$("#"+id).is(":checked")){
					alert("请先打勾");
					return ;
			  }
			  var max=0;
			  var min=0;
			  //var m=0;
			  //获取最大值和最小值的积分
			  $(".twojifen").each(function(index,obj){
				     if(index == 0){
				    	 min=parseFloat($(obj).val());
				     }
				     if($(obj).val() != "0"){
				    	 var jfl=parseFloat($(obj).val());
						 if(min > jfl ){
							 min=jfl;
						 }
				     }
      		  });
			  $(".twojifen").each(function(index,obj){
				     var jfl=parseFloat($(obj).val());
					 if(max < jfl ){
						 max=jfl;
					 }
  		  	  });
			  var grantrule="";
			  var n=0;
			  $(".cate").each(function(){
					  if($(this).children(".twojifen").val() != "0"){
						  n=n+1;
						  if(n<4){
							//判断包含的逗号数
	 						  grantrule+=$(this).children("#twoname").html()+$(this).children(".twojifen").val()+"%，";
						  }
 					  }
    		  });
      		  $.ajax({
				type:"post",
				url:"storepc_scoreway/save.do",
 				data:{
						content : "品类送积分",
						grantrule : "积分率："+min+"-"+max+"%",
						integral_rate : min+"%~"+max+"%",
						selected_status:"1",
 						change_type:"2",
 						allgoods_category_id : ww_allgoods_category_id,
						store_id:"${storepd.store_id}"
				},
				success:function(data){
					if(data.result="01"){
						alert("新增成功");
						window.location.reload(); //刷新当前页面
					}
 				}
			});    
		}
    </script>
        <div class="nav_cont dp"> <!--单品送积分设置-->
        <p class="nav_dec">
            注：<span>按照每个商品或服务的不同设置各自的积分率</span>
        </p>
        <p class="tips">
            例如：部分超市，精品店商品之间利润相差较大根据该方式设置后可进行深度全方位营销。
        </p>
        <p class="tips lh">
            如需设置请在 <span>商品管理>单品管理</span>中进行设置，本处设置是否启用该功能及上下值。
        </p>
             <div class="form-group fm1">
                    <label class="col-md-1 col-xs-2 control-label lab">最低</label>
					<select class="col-md-1 col-xs-2 sele minjf">
                       <option value="0">0%</option>
					   <c:forEach var="num" begin="1" end="100">
							<option value="${num}" ${pd.threemin_rate eq num?'selected':'' }>${num}%</option>
					   </c:forEach>
                    </select>
                    <label class="col-md-1 col-xs-2 control-label lab">最高</label>
                    <select class="col-md-1 col-xs-2 sele maxjf">
                        <option value="0">0%</option>
					    <c:forEach var="num" begin="1" end="100">
							<option value="${num}" ${pd.threemax_rate eq num?'selected':'' }>${num}%</option>
					    </c:forEach>
                    </select>
                <span class="col mgl">*</span ><span class="lh">本功能仅支持线上交易，请慎重选择</span>
                </div>
                   <label class="checkbox-inline use">
                    <input ${pd.change_type eq '3'?'checked':'' } type="checkbox" class="check" value="option1" id="three" onclick="showtext(this)"> 使用
                </label>
         <p class="tips">提醒：您正在切换积分设置，切换后原有设置立即失效。同时部分功能将无法使用，请谨慎选择。</p>
        <c:if test="${storeqx.add eq '1'}">
        <div class="button_box">
            <div class="anniu-m" onclick="threesave('three')">确定</div>
        </div>
        </c:if>
    </div>
	    <script type="text/javascript">
    	function threesave(id){
    		if(!$("#"+id).is(":checked")){
				alert("请先打勾");
				return ;
			} 
			var threeminRate = $('.minjf option:selected').val();
			var threemaxRate = $('.maxjf option:selected').val();
			var integral_rate=threeminRate+"%~"+threemaxRate+"%";
			//var grantrule = "最高"+threemaxRate+"%-最低"+threeminRate+"%";
			var grantrule = "积分率："+threeminRate+"-"+threemaxRate+"%";
 			$.ajax({
				type:"post",
				url:"storepc_scoreway/save.do",
 				data:{
 						content:"单品送积分",
 						selected_status:"1",
 						change_type:"3",
 						threemin_rate:threeminRate,
 						threemax_rate:threemaxRate,
 						integral_rate : integral_rate,
 						grantrule:grantrule,
 						store_id:"${storepd.store_id}"
 				},
				success:function(datas){
					alert("新增成功");
					window.location.reload(); //刷新当前页面
				}
			});
      	}
    </script>
		        <div class="nav_cont bs"> <!--交易笔数送积分设置-->
		        <p  class="nav_dec">
		            注：无论价格多少，按照<span>每笔交易固定值</span>的积分赠送
		        </p>
		            <p class="tips">
		            例如：某快餐店平均客单价均约为20元，设定为每笔消费固定返3元对客流量大，经营品种相对固定，单品之间价格差距不到的商家更为合适。
		        </p>
                <div class="form-group fm1">
                    <label class=" col-lg-2 cool-sm-2 col-xs-3 control-label lab">每单反积分</label>
					<input type="text" class="col-xs-4 sele" id="jfw"  value="${pd.fourbackintegral_integral }" style="text-align: center;    font-size: 19px;" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
                </div>
                 <label class="checkbox-inline use">
                    <input ${pd.change_type eq '4'?'checked':'' } type="checkbox" class="check" value="option1" id="four"  onclick="showtext(this)"> 使用
                </label>
		         <p class="tips">提醒：您正在切换积分设置，切换后原有设置立即失效。同时部分功能将无法使用，请谨慎选择。</p>
		        <c:if test="${storeqx.add eq '1'}">
			        <div class="button_box">
			            <div class="anniu-m" onclick="foursave('four')">确定</div>
			        </div>
		        </c:if>
    </div>
	    <script type="text/javascript">
    	function foursave(id){
    		if(!$("#"+id).is(":checked")){
				alert("请先打勾");
				return ;
			}
    		var content= $("#open").text();  
			var fourbackintegralIntegral = $('#jfw').val();
 			//var grantrule = "每单返积分"+fourbackintegralIntegral;
 			var grantrule = fourbackintegralIntegral+"分/笔";
 			if(fourbackintegralIntegral == ""){
 				alert("积分不能为空");
 				return;
 			}
  			$.ajax({
				type:"post",
				url:"storepc_scoreway/save.do",
 				data:{
 					content:"交易笔数送积分",
 					selected_status:"1",
					change_type:"4",
 					fourbackintegral_integral:fourbackintegralIntegral,
 					integral_rate :fourbackintegralIntegral,
 					grantrule:grantrule,
 					store_id:"${storepd.store_id}"
 				},
				success:function(data){
					window.location.reload(); //刷新当前页面
				}
			});
      	}
    
    </script>
        <div class="nav_cont je"> <!--金额阶梯送积分设置-->
        <p class="nav_dec">
            注： <span>根据消费金额不同，设定梯级积分赠送机制</span>
        </p>
        <p class="tips">
            例如：某品牌餐饮店，为促使客人多消费，设定为消费满100元积分5%，满300元积分10%，满1000元积分15%。
        </p>
             <div class="form-group fm1">
				<c:forEach items="${jfList}" var="jfvar" varStatus="vs">
					<div class="col-xs-4">
						<span class="col-xs-1 lab pad">满</span>
						<input type="text" class="col-xs-2 money${vs.index+1}"  id="money" value="${jfvar.money}"  style="width: 32%;"/>
						<label class="col-lg-2 col-xs-3 control-label lab pad">元送</label>
						<select class=" sele fivejifen" id="opt${vs.index+1}">
							<option value="0">0%</option>
						    <c:forEach var="num" begin="1" end="100">
								<option value="${num}" ${jfvar.rate eq num?'selected':'' }>${num}%</option>
						    </c:forEach>
						</select>
					</div>
				</c:forEach>   
            </div>
                 <label class="checkbox-inline use">
                    <input ${pd.change_type eq '5'?'checked':'' } type="checkbox" class="check" value="option1" id="five"  onclick="showtext(this)"> 使用
                </label>
         <p class="tips">提醒：您正在切换积分设置，切换后原有设置立即失效。同时部分功能将无法使用，请谨慎选择。</p>
        <c:if test="${storeqx.add eq '1'}">
	        <div class="button_box">
	            <div class="anniu-m" onclick="fivesave('five')">确定</div>
	        </div>
        </c:if>
    </div>
        <div class="sec_cont ">
        <h6 class="col mgl lh">重要声明：</h6>
        <p>
            一、积分设置是商家在会员消费完成后，赠送给会员的消费返利，目的是促进会员再次消费，从而形成有效的集客引流效果，积分1分=1元钱，会员凭积分可以九鱼网商家抵等值现金使用；（例：商家设定为整店赠送积分率为10%，会员张三消费了100元，该笔消费商家应赠送张三100×10%=10积分）
        </p>
        <p>
            二、九鱼网为商家提供五种积分赠送方式，商家可根据行业、规模、经营状况选择其中一种；根据商家选择积分赠送方式的不同，系统自动调整相对应的营销功能；
        </p>
        <p>
            三、积分赠送额将根据交易状况从商家资金帐户中自动扣除，为保证商家的正常使用，建议商家在初次使用时根据门店经营流水预存一定金额的积分额，同时请留意帐户积分余额，余额不足时请及时充值；
        </p>
        <p>
            四、会员在商家消费时使用积分支付后，与积分相对应的现金可随时从后台进行提现操作，提现在3-10个工作日到达商家指定帐户，遇节假日顺延；
        </p>
        <p>
            五、为了给商家提供更好的售后服务和增值服务，每笔交易赠送积分额的20%将另行扣除，用于会员拓展、推广和九鱼网服务体系建设；（例：如商家在一笔消费中向会员张三赠送了10积分，则另需赠送10×20%=2积分.
        </p>
        <p>
            六、以上设置的比例或积分值一经设定，商家可随时上调设定的数值，如需下调需经审核后方可生效。
        </p>
    </div>
	</div>
    <script type="text/javascript">
		function fivesave(id){
			if(!$("#"+id).is(":checked")){
				alert("请先打勾");
				return ;
			}
			 var max=0;
			 var min=0;
			  //var m=0;
			  //获取最大值和最小值的积分
			  $(".fivejifen").each(function(index,obj){
				  	if(index == 0){
				    	 min=parseFloat($(obj).val());
				     }
				     if($(obj).val() != "0"){
				    	 var jfl=parseFloat($(obj).val());
						 if(min > jfl ){
							 min=jfl;
						 }
				     }
    		  });
			  $(".fivejifen").each(function(n,obj){
				  	 var jfl=parseFloat($(obj).val());
					 if(max < jfl ){
						 max=jfl;
					 }
 		  	  });
			var grantrule2="积分率："+min+"-"+max+"%";
			var integral_rate=min+"%~"+max+"%";
			var content= $("#open").text();
			var money1 = $(".money1").val().trim();
			var money2 = $(".money2").val().trim();
			var money3 = $(".money3").val().trim();
			var opt1 = $("#opt1").val();
			var opt2 = $("#opt2").val();
			var opt3 = $("#opt3").val();
			var fiveconsumerMoney="";
			if(money1 != "" && opt1 != -1 ){
				fiveconsumerMoney+="满"+money1+"元送"+opt1+"%,";
			}
			if(money2 != "" && opt2 != -1 ){
				fiveconsumerMoney+="满"+money2+"元送"+opt2+"%,";
			}
			if(money3 != "" && opt3 != -1 ){
				fiveconsumerMoney+="满"+money3+"元送"+opt3+"%,";
			}
			
  			//alert(fiveconsumerMoney);
			$.ajax({
				type:"post",
				url:"storepc_scoreway/save.do",
 				data:{
 					content:"金额阶梯送积分",
 					grantrule : fiveconsumerMoney,
 					grantrule2 : grantrule2,
 					integral_rate : integral_rate,
 					selected_status:"1",
					change_type:"5",
 					store_id:"${storepd.store_id}"},
				success:function(data){
					window.location.reload();
				}
			});
		} 
    
    </script>
  </div>
</section>
<script src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
		function showtext(obj){
    		if($(obj).is(":checked")){
    			alert("您正在切换积分设置，切换后原有设置立即失效，同时部分功能将无法使用，请慎重选择");
 			}
     	}
		//下一步
	    function gonext(){
	        if("${shezhi}" == "0"){
	            alert("积分是必需设置，请设置");
	            return;
	        }else{
	            window.location.href='<%=basePath%>storepc/goSheZhiOne.do?store_id=${pd.store_id}';
	        }
	    }
		//使用document.getElementById获取到按钮对象
    	function BindEnter(event){
    		var gonext = document.getElementById("gonext");
    		if(event.keyCode == 13){
    			gonext.click();
    			event.returnValue = false;
    		}
    	}
		
    	//点击标题刷新页面
		function shaixin(change_type){
			 window.location.href='<%=basePath%>storepc_marketing/goIntegral.do?store_id=${storepd.store_id}&change_type='+change_type;
		}
    	
</script>
<script type="text/javascript">
		var change_type="${change_type}";
		if(change_type == "" || change_type =="1"){
			$(".zd").addClass("act");
			$(".nav_cont").hide();
			$(".zd").show();
		}else if(change_type =="2"){
			$(".pl").addClass("act");
			$(".nav_cont").hide();
			$(".pl").show();
		}else if(change_type =="3"){
			$(".dp").addClass("act");
			$(".nav_cont").hide();
			$(".dp").show();
		}else if(change_type =="4"){
			$(".bs").addClass("act");
			$(".nav_cont").hide();
			$(".bs").show();
		}else if(change_type =="5"){
			$(".je").addClass("act");
			$(".nav_cont").hide();
			$(".je").show();
		}
 </script> 
</body>
</c:if>
 </html>