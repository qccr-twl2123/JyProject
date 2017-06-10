package com.tianer.controller.storepc.store_wealth;

import java.text.DecimalFormat;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.tongyongUtil.TongYong;
import com.tianer.entity.zhihui.Qx;
import com.tianer.entity.zhihui.StoreRole;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.storepc.store_bankcard.Storepc_bankcardService;
import com.tianer.service.storepc.store_wealth.Storepc_wealthService;
import com.tianer.service.storepc.store_wealthhistory.Storepc_wealthhistoryService;
import com.tianer.util.Const;
import com.tianer.util.PageData;


/** 
 * 
* 类名称：Storepc_scorewayController   
* 类描述：   积分充值
* 创建人：邢江涛  
* 创建时间：2016年6月21日 
 */
@Controller("storeStore_wealthController")
@RequestMapping(value = "/storepc_wealth")
public class Storepc_wealthController extends BaseController{
	

	
	@Resource(name = "storepc_wealthService")
	private Storepc_wealthService wealthService;
	
	@Resource(name = "storepc_wealthhistoryService")
	private Storepc_wealthhistoryService wealthhistoryService;
	
	@Resource(name = "storepc_bankcardService")
	private Storepc_bankcardService bankcardService;
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	
 	public DecimalFormat df2=TongYong.df2;
	
 	
	/**
	 * 去支付充值页面
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String jichushezhi=pd.getString("jichushezhi");
			pd=appStoreService.findById(pd);
 			String now_wealth=wealthService.sumStoreWealth(pd); 
 			pd.put("now_wealth", now_wealth);
   			if(jichushezhi != null && jichushezhi.equals("11111111100")){
  				mv.setViewName("/storepc/shezhi_9");
  				pd.put("jichushezhi", jichushezhi);
  			}else{
  				mv.setViewName("storepc/business_account");
   			}
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd",pd);
  		return mv;
		
	}
	
//	/**	
//	 * 
//	 *   新增数据进充值记录数据表
//	 */
//	@RequestMapping(value="/save")
//	public Object save(){
//		Map<String , Object> map = new HashMap<String, Object>();
//		String result = "01";
//		PageData pd = new PageData();
//		try {
//			pd = this.getPageData();
//			String id = this.getTXUID();//提现id
//			pd.put("store_wealthhistory_id", id);	//主键
//			String arrivalMoney = pd.getString("arrivalMoney");
//			arrivalMoney = arrivalMoney.substring(0, (arrivalMoney.length()-1));
//			pd.put("arrivalMoney", arrivalMoney);
//			wealthhistoryService.save(pd);
//			saveWithdraw();
//			map.put("result", result);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}
//		
//		return AppUtil.returnObject(pd, map);
//	}

	
//	/**
//	 * 商家新增提现记录魏汉文20160704
//	 */
//	@RequestMapping(value="/saveWithdraw")
//	@ResponseBody
//	public Object saveWithdraw() throws Exception{
//		logBefore(logger, "新增Withdraw_approval");
//		Map<String,Object> map = new HashMap<String,Object>();
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//		String result = "1";
//		String message="提现审批，请等待1至2个工作日";
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try{
//						PageData e=new PageData();
//						double money=Double.parseDouble(pd.getString("number"));
//							e.put("store_id", pd.getString("store_id"));
//							//获取商家的默认银行卡号
//							if(pd.getString("store_bankcard_id") != null ){
//									PageData bankpd=appStoreService.findBankById(pd);
//									pd.put("account_name", bankpd.getString("account"));
//									pd.put("withdraw_number", bankpd.getString("bank_number"));
//									pd.put("withdraw_username", bankpd.getString("bankcard_name"));
//									pd.put("account_type", "4");
//							}else{
//								map.put("result", "0");
//								map.put("message", "请选择银行卡号");
//								map.put("data", "");		
//								return AppUtil.returnObject(pd, map);
//							}
//							//获取商家的财富
//						e.put("wealth_type", "2");
//						e=appStoreService.findWealthById(e);
//						double now_wealth=Double.parseDouble(e.getString("now_wealth"));
//						double frozen_wealth=Double.parseDouble(e.getString("frozen_wealth"));
// 						if(now_wealth-frozen_wealth < money){
//							result="0";
//							message="余额不足";
//							map.put("data", "");
//						}else{
//							e=appStoreService.findById(e);
//							pd.put("phone", e.getString("registertel_phone"));
//							pd.put("province_name", e.getString("province_name"));
//							pd.put("city_name", e.getString("city_name"));
//							pd.put("area_name", e.getString("area_name"));
//							pd.put("withdraw_type", "1");
// 							appWithdraw_approvalService.saveWithdraw(pd);
//							//减少用户积分余额
//							double n=now_wealth-money;
//							e.put("now_wealth", df.format(n));
//							appStoreService.editWealthById(e);
// 						}
//	 		}catch(Exception e){
//				result="0";
//				message="系统异常";
//				map.put("data", e.toString());
//				logger.error(e.toString(), e);
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", "");		
//		return AppUtil.returnObject(pd, map);
//	}

	
	
	
	
	/* ===============================财务信息权限================================== */
	public void getHC(){
			ModelAndView mv = this.getModelAndView();
			HttpServletRequest request=this.getRequest();
			HttpSession pcsession=request.getSession();
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();	
			StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
			if(slogin != null){
				Map<String,Object> map=slogin.getMap();
				if(map !=null){
					Qx qx=new Qx();
					qx=(Qx) map.get("cw");
					pcsession.setAttribute("storeqx", qx);
					mv.addObject("qx", qx);
				}
			}
  	}
	/* ===============================权限================================== */
	
	
	
	
	
}
