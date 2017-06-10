<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<title>首页-概要概括</title>
	<base href="<%=basePath%>">
	<script src="js/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" href="css/pcstore/sy_gk.css">
</head>
<body style=" border: 1px solid #aaa; ">
	<c:if test="${storeqx.look eq '1'}">
		<table cellspacing="0" cellpadding="0" style="width:100%;">
			<tr class="fir_tr">
				<td style="border-bottom: 1px solid #aaa;" >
					<li style="padding-left:28px;">
						<span>累计推广效益：</span>
						<span class="col-r">${allmoney}</span>
					</li>
					<li style="padding-left:28px;">
						<span>其中一度人脉：</span>
						<span class="col-r">${firstmoney}</span>
					</li>
					<li style="padding-left:28px;">
						<span>其中二度人脉：</span>
						<span class="col-r">${twomoney}</span>
					</li>
				</td>
				<td style="border-bottom: 1px solid #aaa;">
					<li>
						<span>可用积分</span>
						<span class="col-r">${cantx}</span>
					</li>
					<li>
						<a href="storepc_wealth/list.do?store_id=${storepd.store_id}"><span class="anniu">充值</span></a>
					</li>
				</td>
				<td style="border-bottom: 1px solid #aaa;">
					<li>
						<span>可提现金额</span>
						<span class="col-r">${cantx}</span>
					</li>
					<li>
						<a href="storepc_withdrawals/list.do?store_id=${storepd.store_id}"><span class="anniu">提现</span></a>
					</li>
				</td>
				<td style="border-bottom: 1px solid #aaa;">
					<li style="font-weight:bolder;">
						<span>冻结资金</span>
						<span class="col-r">${frozen_wealth}</span>
					</li>
					<li>
						<span>提现后还未到账的</br>积分和销售金额</span>
					</li>
				</td>
				<td style="width:15%;border:1px solid #aaa;font-size:12px;    text-align: center;" rowspan="2" >
					<a target="_bank" href="../storepcOperator_file/downPic.do?image_url=https://www.jiuyuvip.com/FileSave/File/storeErFile/${storepd.store_id}.png"> 
						<img src="https://www.jiuyuvip.com/FileSave/File/storeErFile/${storepd.store_id}.png" alt="" style="max-width:180px;width:100%;">
					</a>
				</td>
			</tr>
			<tr>
				<td colspan="4"  style="text-align:left;border-bottom: 1px solid #aaa;">
					<span style="padding-left:15px;">积分方式：${scoreway}</span>
					<span style="font-size:80%;">总积分率=会员积分率+共享人脉积分率；<span style:"color:#999;">共享人脉积分率为会员积分率的20%</span></span>
				</td>
			</tr>
			</table>
				<table cellspacing="0" cellpadding="0" style="width:100%;">
					<tr>
						<td style="border-bottom: 1px solid #aaa; width:16.6666%; ">
							<span style="padding-left:15px;">
								提现费率:
							</span>
							<span class="col-r">
								${pd.withdraw_rate}%
							</span>
						</td>
						<!-- <td style="border-bottom: 1px solid #aaa;width:16.6666%;">
							<span>
								被点赞数
							</span>
							<span class="col-r">
								2
							</span>
						</td> -->
						<td style="border-bottom: 1px solid #aaa;width:16.6666%;">
							<span>
								累计交易笔数
							</span>
							<span class="col-r">
								${allordernumber}
							</span>
						</td>
						<td style="border-bottom: 1px solid #aaa;width:16.6666%;">
							<span>
								评论数量：
							</span>
							<span class="col-r">
								${comment_amount}
							</span>
						</td>
						  <td style="border-bottom: 1px solid #aaa;width:16.6666%;">
							<span>
								被收藏数：
							</span>
							<span class="col-r">
								${collect_amount}
							</span>
						</td> 
						<td style="border-bottom: 1px solid #aaa;width:16.6666%;">
							<span>
								发红包个数：
							</span>
							<span class="col-r">
								${redpackage_number}
							</span>
						</td>
					</tr>
				</table>
			
			
				<table cellspacing="0" cellpadding="0" style="width:100%;">
					<tr>
						<td>
							<span style="padding-left:15px;">今日营业额</span>
							<span class="col-r">${ordermoney}</span><span>元</span>
						</td>
						<td>
							<span>
								今日交易笔数
							</span>
							<span class="col-r">${ordernumber}</span>
							<span>笔</span>
						</td>
						<td>
							<span>今日送积分</span>
							<span class="col-r">${sumsong_jf}</span>
							<span>分</span>
						</td>
					</tr>
					<tr>
						<td>
							<span style="padding-left:15px;">今日收积分：</span>
							<span class="col-r">${sumshou_jf}</span>
							<span>分</span>
						</td>
						<td>
							<span>今日充值：</span>
							<span class="col-r">${sumcz}</span>
							<span>元</span>
						</td>
						<td>
							<span>今日提现：</span>
							<span class="col-r">${sumtx}</span>
							<span>元</span>
						</td>
					</tr>
				</table>
			<table cellspacing="0" cellpadding="0" style="width:100%;border-bottom:1px solid #999;">
				<tr>
					<td>
					<span style="padding-left:15px;">我的综合评分值：</span>
					<span class="col-r">${pd.complex_score }</span>
					<span>分</span>
					<span class="viewto col-o">什么是综合评分值？（点击查看）</span>
					</td>
				</tr>
			</table>
			<table class="biaoge">
				<tr>
					<td style="border:0;text-align: left;padding-left:15px;">
						<span>
							综合评分值是商家在系统使用过程中信誉度，交易量等综合系数的量化指标，分值越高的商家在会员APP端显示序列中排列更靠前。综合评分值的计划公式如下
						</span>
					</td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0" style="width:100%;border:1px solid #a4a4a4;" class="biaoge">
				<thead>
					<td style="width:9%;font-weight: bold;">
						交易场景
					</td>
					<td>
						交易
					</td>
					<td colspan="5">
						单笔交易返会员积分额
					</td>
					<td>
						投诉
					</td>
					<td colspan="2">
						充值
					</td>
					<td colspan="5">
						会员评价 
					</td>
				</tdead>
				<tbody >
					<tr style="">
						<td style="font-weight: bold;padding:50px 30px;">
							指标
						</td>
						<td>
							通过系统完成一笔交易
						</td>
						<td>
							<=5
						</td>
						<td>
							>5且<=10
						</td>
						<td>
							>10且<=30
						</td>
						<td>
							>30且<=100
						</td>
						<td>
							>100
						</td>
						<td>
							会员投诉且确认成立后
						</td>
						<td>
							完成首次充值且金额100元以上
						</td>
						<td>
							完成二次充值且金额在500元以上
						</td>
						<td>
							五星好评
						</td>
						<td>
							四星好评
						</td>
						<td>
							三星好评
						</td>
						<td>
							二星好评
						</td>
						<td>
							一星好评
						</td>
					</tr>
				</tbody>
				<tfoot>
					<td style="font-weight: bold;">分值</td>
					<td>+5</td>
					<td>+3</td>
					<td>+5</td>
					<td>+8</td>
					<td>+12</td>
					<td>+20</td>
					<td>-100</td>
					<td>+20</td>
					<td>+30</td>
					<td>+5</td>
					<td>+1</td>
					<td>0</td>
					<td>-10</td>
					<td>-20</td>
				</tfoot>
			</table>
		</c:if>
</body>
<script src="js/jquery-1.12.4.min.js"></script>
<script>
	var flag=0
	$(".viewto").click(function(){	
		if (flag==0) {
			flag+=1;
			$(".biaoge").css({"display": "block"})
		}else{
			flag=0
			$(".biaoge").css({"display": "none"})
		}
	})
</script>
</html>