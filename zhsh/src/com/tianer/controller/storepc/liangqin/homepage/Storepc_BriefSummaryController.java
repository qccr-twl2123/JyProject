/**
 * 
 */
package com.tianer.controller.storepc.liangqin.homepage;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.tongyongUtil.TongYong;
import com.tianer.service.memberapp.AppFriendService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.storepc.liangqin.homepage.BriefSummaryService;
import com.tianer.service.storepc.liangqin.homepage.CashAndPickupService;
import com.tianer.service.storepc.store_scoreway.Storepc_scorewayService;
import com.tianer.service.storepc.store_wealth.Storepc_wealthService;
import com.tianer.service.storepc.store_wealthhistory.Storepc_wealthhistoryService;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;

/**
 * 类名称: Storepc_BriefSummary 
 * 类描述: 简要概括的后台
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-13 上午9:59:38	
 * 版本号: v1.0
 */
@Controller
@RequestMapping(value="/storepc_briefsummary")
public class Storepc_BriefSummaryController extends BaseController{
	DateUtil dateUtil = new DateUtil();
	
	@Resource(name="briefSummaryService")
	private BriefSummaryService briefSummaryService;
	@Resource(name="cashAndPickupService")
	private CashAndPickupService cashAndPickupService;
	
	@Resource(name="storepc_wealthhistoryService")
	private Storepc_wealthhistoryService storepc_wealthhistoryService;
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	@Resource(name="appFriendService")
	private AppFriendService appFriendService;
	
	@Resource(name = "storepc_scorewayService")
	private Storepc_scorewayService storepcScorewayService; 
	@Resource(name = "storepc_wealthService")
	private Storepc_wealthService storepc_wealthService;
	
	
	/**
	 * 
	* 方法名称:：showMessage 
	* 方法描述：简要概括
	* 创建人：魏汉文
	* 创建时间：2016年7月12日 下午2:47:52
	 */
	@RequestMapping(value="/showMessage")   
	public ModelAndView showMessage() throws Exception {
		//logBefore(logger, "简要概括");
		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
 		try{
 			pd = this.getPageData();
			pd=appStoreService.findById(pd);
			mv.addObject("pd", pd);
			String storeId=pd.getString("store_id");
			//所有交易笔数
 			int allordernumber=storepc_wealthhistoryService.getallCountOrder(pd);
			mv.addObject("allordernumber", allordernumber);
			//积分方式
			PageData scorepd=new PageData();
			scorepd=storepcScorewayService.findById(pd);
			if(scorepd != null){
				String change_type=scorepd.getString("change_type");
				if(change_type.equals("1")){
					mv.addObject("scoreway", "整店送积分");
				}else if(change_type.equals("2")){
					mv.addObject("scoreway", "品类送积分");
				}else if(change_type.equals("3")){
					mv.addObject("scoreway", "单品送积分");
				}else if(change_type.equals("3")){
					mv.addObject("scoreway", "交易笔数送积分");
				}else if(change_type.equals("5")){
					mv.addObject("scoreway", "金额阶梯送积分");
				}
			}
 			PageData allpd=new PageData();
 			allpd.put("wealth_type", "1");
 			allpd.put("store_id", pd.getString("store_id"));
			//提现总和
 			allpd.put("profit_type", "1");
			String sumtx=storepc_wealthhistoryService.getSumMoney(allpd);
			mv.addObject("sumtx", sumtx);
			//充值总和
 			allpd.put("profit_type", "2");
			String sumcz=storepc_wealthhistoryService.getSumMoney(allpd);
			mv.addObject("sumcz", sumcz);
			PageData sumorderpd=storepc_wealthhistoryService.getSumOrder(pd);
  			if(sumorderpd != null){
  				//收会员使用积分总和
  				String sumshou_jf=sumorderpd.getString("user_integral") ;
 				mv.addObject("sumshou_jf", sumshou_jf);
 				//送会员使用积分总和
 				String sumsong_jf1=sumorderpd.getString("get_integral") ;
 				String sumsong_jf2=sumorderpd.getString("sendxitong_integral") ;
  				mv.addObject("sumsong_jf", TongYong.df2.format(Double.parseDouble(sumsong_jf1)+Double.parseDouble(sumsong_jf2)));
 				//统计今天的销售额
 				String ordermoney=sumorderpd.getString("money");
				mv.addObject("ordermoney", ordermoney);
 			}else{
 				mv.addObject("sumshou_jf", "0");
 				mv.addObject("sumsong_jf", "0");
 				mv.addObject("ordermoney", "0");
 			}
 			//交易今天笔数总和
 			int ordernumber=storepc_wealthhistoryService.getCountOrder(allpd);
			mv.addObject("ordernumber", ordernumber);
  			//商家信息（已发红包、评论数量）
			String redpackage_number = briefSummaryService.findRedPacketsByStoreId(storeId);
			mv.addObject("redpackage_number", redpackage_number);
			//评论数量
			String comment_amount = briefSummaryService.findCommentAmount(storeId);
			mv.addObject("comment_amount", comment_amount);
			//点赞次数
			String zan_amount = briefSummaryService.findZanAmount(storeId);
			mv.addObject("zan_amount", zan_amount);
			//收藏测试
			String collect_amount = briefSummaryService.findCollectTimes(storeId);
 			mv.addObject("collect_amount", collect_amount);
  			//总可提现金额总可用积分 
 			String allnow_wealth=storepc_wealthService.sumStoreWealth(pd);
			mv.addObject("cantx", allnow_wealth);
			//冻结金额
   			mv.addObject("frozen_wealth", pd.getString("tixain_money"));
 			//人脉收益
   			pd.put("onecontacts_id", pd.getString("store_id"));
	  		String firstmoney=storepc_wealthhistoryService.getContantSumMoney(pd).getString("sumonecontacts_getmoney");
	  		pd.remove("onecontacts_id");
 	  		pd.put("twocontacts_id", pd.getString("store_id"));
	  		String twomoney=storepc_wealthhistoryService.getContantSumMoney(pd).getString("sumtwocontacts_getmoney");
	  		mv.addObject("firstmoney",firstmoney);
	  		mv.addObject("twomoney",twomoney);
	  		mv.addObject("allmoney", TongYong.df2.format(Double.parseDouble(firstmoney)+Double.parseDouble(twomoney)));
       	}catch(Exception e){
			logger.error(e.toString(), e);
		}
  		mv.setViewName("storepc/business_homepage3");
		return mv;
	}
}