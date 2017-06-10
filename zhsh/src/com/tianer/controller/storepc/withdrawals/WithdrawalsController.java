package com.tianer.controller.storepc.withdrawals;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.storepc.store_bankcard.Storepc_bankcardService;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;


/** 
 * 
* 类名称：Storepc_scorewayController   
* 类描述：   提现
* 创建人：邢江涛  
* 创建时间：2016年6月22日 
 */
@Controller("storeWithdrawalsController")
@RequestMapping(value = "/storepc_withdrawals")
public class WithdrawalsController extends BaseController{
 
	
	@Resource(name = "storepc_bankcardService")
	private Storepc_bankcardService bankcardService;
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	/**
	 * 提现展示
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(){
		ModelAndView mv = this.getModelAndView();
		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		PageData pd = new PageData();
		try{
					pd = this.getPageData();
  					pd=appStoreService.findById(pd);//保底积分lowest_score，提现费率withdraw_rate,tixain_money冻结金额，即未到账的提现金额,store_name商家名称
 					//获取商家货款的财富
					String allnow_wealth=ServiceHelper.getStorepc_wealthService().sumStoreWealth(pd);
 					pd.put("allnow_wealth", allnow_wealth); 
 					//银行卡信息
 					List<PageData>	bankList = bankcardService.list(pd);	
					//获取银行卡号，截取后4位，放进list做前台显示
					for (PageData pageData : bankList) {
						String kaihao = pageData.getString("bank_number");
						String kh = kaihao.substring(kaihao.length()-4, kaihao.length());
						pageData.put("kh", kh);
 					}
					mv.addObject("bankList", bankList);
					bankList=null;
					//支付宝列表
					List<PageData> alipayList = bankcardService.listAlipay(pd);
					for (PageData pageData : alipayList) {
						String alipay_number = pageData.getString("alipay_number");
						if(alipay_number.length() == 11){
							alipay_number=alipay_number.substring(0, 4)+"****"+alipay_number.substring(7, 11);
						}
						pd.put("alipay_number", alipay_number);
		  			}
					mv.addObject("alipayList", alipayList);
					alipayList=null;
					mv.addObject("pd", pd);
					pd=null;
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		mv.setViewName("storepc/business_account3");
 		return mv;
		
	}

}
