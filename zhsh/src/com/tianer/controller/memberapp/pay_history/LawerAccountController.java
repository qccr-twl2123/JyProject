
//代码用区，不在项目内
//package com.tianer.controller.app.lawer;
//
//
//import java.math.BigDecimal;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Timer;
//
//import javax.annotation.Resource;
//
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.pingplusplus.model.Transfer;
//import com.tianer.controller.base.BaseController;
//import com.tianer.entity.Page;
//import com.tianer.service.lawer.LawerAccountService;
//import com.tianer.util.AppUtil;
//import com.tianer.util.Const;
//import com.tianer.util.DateUtil;
//import com.tianer.util.PageData;
//import com.tianer.util.PingppUtil;
//import com.tianer.util.quartz.TransferTask;
//
//
///**
// * app账户管理
// * @author zy
// * 时间 ：2016年6月12日18:17:00
// */
//@Controller("appLawerAccountController")
//@RequestMapping(value="/app_laweraccount")
//public class LawerAccountController extends BaseController{
//	@Resource(name="lawerAccountService")
//	private LawerAccountService lawerAccountService;
//	
//	/**
//	 * app账户余额查询
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/getLawerBal")
//	@ResponseBody
//	public Object getLawerBal() throws Exception{
//		logBefore(logger, "账户余额查询getLawerBal");
//		Map<String,Object> map = new HashMap<String,Object>();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		String lawer_id = pd.getString("lawer_id");
//		if ("".equals(lawer_id) || lawer_id == null) {
//			logBefore(logger, "APP传入的lawer_id为空");
//			map.put("result", "0");
//			map.put("message", "用户id不能为空");
//			return AppUtil.returnObject(pd, map);
//		}
//		try {
//			logBefore(logger,"账户余额查询");
//			PageData _pd = lawerAccountService.getLawerBal(pd);
//			if (_pd != null) {
//				map.put("result", "1");
//				map.put("message", "账户余额查询成功");
//				map.put("data", _pd);
//			}else {
//				map.put("result", "0");
//				map.put("message", "暂无数据");
//			}
//		} catch (Exception e) {
//			logAfter(logger, "数据查询失败");
// 			e.printStackTrace();
// 			map.put("result", "0");
//			map.put("message", "账户余额查询失败");
//		}
//		logEnd(logger);
//		return AppUtil.returnObject(pd, map);
//	}
//
//	/**
//	 * app账单查询
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/listLawerBalLog")
//	@ResponseBody
//	public Object listLawerBalLog() throws Exception{
//		logBefore(logger, "账单查询listLawerBalLog");
//		Map<String,Object> map = new HashMap<String,Object>();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		
//		
//		String lawer_id = pd.getString("lawer_id");
//		String begin_date = pd.getString("begin_date");
//		String end_date = pd.getString("end_date");
//		
//		/** 检查参数 begin ******/
//		if ("".equals(lawer_id) || lawer_id == null) {
//			logBefore(logger, "APP传入的lawer_id为空");
//			map.put("result", "0");
//			map.put("message", "用户id不能为空");
//			return AppUtil.returnObject(pd, map);
//		}
//		
//		if ("".equals(begin_date) || begin_date == null) {
//			logBefore(logger, "APP传入的begin_date为空");
//			map.put("result", "0");
//			map.put("message", "begin_date不能为空");
//			return AppUtil.returnObject(pd, map);
//		}
//		
//		if ("".equals(end_date) || end_date == null) {
//			logBefore(logger, "APP传入的end_date为空");
//			map.put("result", "0");
//			map.put("message", "end_date不能为空");
//			return AppUtil.returnObject(pd, map);
//		}
//		/** 检查参数 end *******/
//
//		
//		
//		try {
//			/**
//					4	最近三个月	
//					3	最近一月	
//					2	最近一周	
//					1	今天	
//				
//				String begin_date ="";
//				if("1".equals(search_type)){
//					begin_date =DateUtil.getBeforeDate(Calendar.DAY_OF_MONTH, 0);
//				}else if("2".equals(search_type)){
//					begin_date =DateUtil.getBeforeDate(Calendar.WEEK_OF_MONTH, -1);
//				}else if("3".equals(search_type)){
//					begin_date =DateUtil.getBeforeDate(Calendar.MONTH, -1);
//				}else if("4".equals(search_type)){
//					begin_date =DateUtil.getBeforeDate(Calendar.MONTH, -3);
//				}else{
//					begin_date =DateUtil.getBeforeDate(Calendar.DAY_OF_MONTH, 0);
//				}
//			 */
//			pd.put("begin_date",begin_date + " 00:00:00");
//			pd.put("end_date",end_date + " 23:59:59");
//			
//		//	page.setPd(pd);
//			
//			logBefore(logger,"查询账单");
//			List<PageData> varList = lawerAccountService.listLawerBalLog(pd);
//			
//			logAfter(logger, "查询总订单数和总金额");
//			PageData _data = lawerAccountService.getOrderCountAndAmountCount(pd);
//			
//			map.putAll(_data);
//			
//			if (varList.size() > 0) {
//				map.put("result", "1");
//				map.put("message", "账单查询成功");
//				map.put("data", varList);
//			}else {
//				map.put("result", "0");
//				map.put("message", "暂无数据");
//			}
//		} catch (Exception e) {
//			logAfter(logger, "查询数据失败");
// 			e.printStackTrace();
// 			map.put("result", "0");
//			map.put("message", "账单查询失败");
//		}
//		logEnd(logger);
//		return AppUtil.returnObject(pd, map);
//	}
//	
//	
//	
//	/**
//	 * 律师提现
//	 * @return
//	 * 
//	 * 
//	 * 提现规则：
//	 * 	 提现时间：每月15号9:00-22:00
//	 *	 可提现收入：本月10号00:00前所有收入
//	 *	 当日提现限额：15000元
//	 *	 到账时间：3个工作日内
//	 *	 提现次数：当日最多3次
// 	 */
//	@RequestMapping(value = "/lawerWithdraw")
//	@ResponseBody
//	public Object lawerWithdraw(){
//		logBefore(logger, "律师提现app_laweraccount.lawerWithdraw");
//		Map<String,Object> map = new HashMap<String,Object>();
//		PageData pd = new PageData();
//		if (DateUtil.getAfterDayWeek("0") != 2) {
//			map.put("result", "0");
//			map.put("message", "每周二开放提现通道");
//			return AppUtil.returnObject(pd, map);
//		}
//		
//		pd = this.getPageData();
//		
//		String 	withdraw_lawerid =pd.getString("lawer_id");
//		String 	withdraw_amount =pd.getString("withdraw_amount");
//		String 	withdraw_type =pd.getString("withdraw_type");
////		String 	withdraw_toaccount =pd.getString("withdraw_toaccount");
//		String card_number = pd.getString("bank_no");
//		String user_name = pd.getString("lawer_name");
//		String open_bank_code = pd.getString("open_bank_code");
//		String prov = pd.getString("prov");
//		String city = pd.getString("city");
//		
//		/** 检查参数 begin ******/ 
//		if ("".equals(withdraw_lawerid) || withdraw_lawerid == null) {
// 			map.put("result", "0");
//			map.put("message", "提现的律师id(withdraw_lawerid)不能为空");
//			return AppUtil.returnObject(pd, map);
//		}
//		if ("".equals(withdraw_type) || withdraw_type == null) {
//			map.put("result", "0");
//			map.put("message", "提现的类型(withdraw_type)不能为空");
//			return AppUtil.returnObject(pd, map);
//		}
//		if ("".equals(withdraw_amount) || withdraw_amount == null) {
//			map.put("result", "0");
//			map.put("message", "提现的金额(withdraw_amount)不能为空");
//			return AppUtil.returnObject(pd, map);
//		}
//		//银行卡
//		if ("1".equals(withdraw_type)) {
//			if ("".equals(open_bank_code) || open_bank_code == null) {
//				map.put("result", "0");
//				map.put("message", "银行编号(open_bank_code)不能为空");
//				return AppUtil.returnObject(pd, map);
//			}
//			if ("".equals(card_number) || card_number == null) {
//				map.put("result", "0");
//				map.put("message", "银行卡号(card_number)不能为空");
//				return AppUtil.returnObject(pd, map);
//			}
//		}
//		/** 检查参数 End   ******/
//		
//		try {
//			//当日提现限额：15000元
//			// 提现次数：当日最多3次
//			pd.put("withdraw_createtime", DateUtil.getDay());
//			logAfter(logger, "查询当日提现次数和提现金额");
//			PageData lawerTodayWithdrawInfoData = lawerAccountService.getLawerTodayWithdrawInfo(pd);
//			Long countToday  = (Long)lawerTodayWithdrawInfoData.get("count");
//			BigDecimal amountToday = (BigDecimal)lawerTodayWithdrawInfoData.get("amount");
//			if(countToday >= Const.ONE_DAY_WITHDRAW_TIMES_LIMIT){
//				map.put("result", "0");
//				map.put("message", "当日最多提现" + Const.ONE_DAY_WITHDRAW_TIMES_LIMIT + "次");
//				return AppUtil.returnObject(pd, map);
//			}
//			if(amountToday.doubleValue() >= Const.ONE_DAY_WITHDRAW_AMOUNT_LIMIT){
//				map.put("result", "0");
//				map.put("message", "当日提现总金额不得大于" + Const.ONE_DAY_WITHDRAW_AMOUNT_LIMIT);
//				return AppUtil.returnObject(pd, map);
//			}
//			
//			BigDecimal amount = new BigDecimal(withdraw_amount);
//			if(amount.doubleValue() < 0){
//				map.put("result", "0");
//				map.put("message", "提现的金额(withdraw_amount)必须大于0");
//				return AppUtil.returnObject(pd, map);
//			}
//			
//			if(amount.doubleValue() > Const.ONE_DAY_WITHDRAW_AMOUNT_LIMIT){
//				map.put("result", "0");
//				map.put("message", "当日提现总金额不得大于" + Const.ONE_DAY_WITHDRAW_AMOUNT_LIMIT);
//				return AppUtil.returnObject(pd, map);
//			}
//			
//			pd.put("lawer_id", withdraw_lawerid);
//			logAfter(logger, "根据律师id获取账户信息");
//			PageData balData = lawerAccountService.getLawerBal(pd);
//			BigDecimal lawerBal = (BigDecimal) balData.get("bal");
//			
//			if (lawerBal.compareTo(amount) < 0) {
//				map.put("result", "0");
//				map.put("message", "余额不足");
//				return AppUtil.returnObject(pd, map);
//			}
//			
//			//单位是分
//			int total_fee = amount.multiply(new BigDecimal("100")).intValue();
//			//银行卡
//			if ("1".equals(withdraw_type)) {
//				Transfer transfer = null;
//		        Map<String, Object> extra = new HashMap<String, Object>();
//	            extra.put("card_number", card_number);// 收款人银行卡号或者存折号
//	            extra.put("user_name", user_name);// 收款人姓名
//	            extra.put("open_bank_code", open_bank_code);// 开户银行编号
//			    extra.put("prov", prov);// 省份
//			    extra.put("city", city);// 城市
//				transfer = PingppUtil.transfer("", total_fee, "unionpay", extra);
//				
//				if (transfer == null) {
//					map.put("result", 0);
//					map.put("message", "提现申请失败");
//					return AppUtil.returnObject(pd, map);
//				}else {
//					pd.put("withdraw_createtime", DateUtil.getTime());
//					pd.put("withdraw_lawerid", withdraw_lawerid);
//					pd.put("withdraw_toaccount", card_number);
//					pd.put("withdraw_transfersid", transfer.getId());
//					logAfter(logger, "插入提现申请的数据");
//					lawerAccountService.addWithdrawalApplication(pd);
//					
//					logAfter(logger,"律师申请提现成功，修改账户余额");
//					pd.put("bal", lawerBal.subtract(amount));
//					pd.put("lawer_id", withdraw_lawerid);
//					pd.put("update_time", DateUtil.getTime());
//					lawerAccountService.updateLawerBal(pd);
//					map.put("transfer", transfer.toString());
//					
//					Timer t = new Timer(); // 建立Timer对象
//					Calendar calendar = Calendar.getInstance();
//					calendar.add(Calendar.SECOND, 310);
//					TransferTask task = new TransferTask(transfer.getId());
//					t.schedule(task, calendar.getTime());//在该时间启动线程
//				}
//				map.put("result", "1");
//				map.put("message", "提现申请成功");
//			}else {
//				map.put("result", "0");
//				map.put("message", "目前仅支持提现到银行卡，其他渠道暂未开通");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			map.put("result", "0");
//			map.put("message", "提现申请失败");
// 		}
// 		 
//		logEnd(logger);
//		return AppUtil.returnObject(pd, map);
//	}
//	
//	public void TransferRetrieve() {
//		
//	}
//	
//	
//	/**
//	 * 处理提现申请
//	 * @return
//	 */
//	@RequestMapping(value="/handleWithdrawalApplication")
//	@ResponseBody
//	public Object handleWithdrawalApplication(){
//		logBefore(logger, "处理提现申请app_laweraccount.handleWithdrawalApplication");
//		Map<String,Object> map = new HashMap<String,Object>();
//		PageData pd = new PageData();
////		pd = this.getPageData();
////		
////		String withdraw_id = pd.getString("withdraw_id");
////		
////		
////		try {
////			
////			logAfter(logger, "1.查询提现申请信息");
////			PageData applicationData = lawerAccountService.getWithdrawalApplication(pd);
////			System.out.println(applicationData);
////			
////			if(applicationData.get("withdraw_ishandle").toString().equals("1")){
////				map.put("result", "0");
////				map.put("message", "此次申请已处理");
////				return AppUtil.returnObject(pd, map);
////			}
////			
////			String lawer_id = applicationData.get("withdraw_lawerid").toString();
////			pd.put("lawer_id", lawer_id);
////			logAfter(logger, "2.查询律师账户信息");
////			PageData lawerAccountData = lawerAccountService.getAccountByLawerId(pd);
////			System.out.println(lawerAccountData);
////			
////			
////			BigDecimal amount = new BigDecimal(applicationData.get("withdraw_amount").toString());//提现金额
//// 			BigDecimal _bal =  new BigDecimal(lawerAccountData.get("bal").toString());//账户余额
//// 			
//// 			if( _bal.compareTo(amount) < 0){
//// 				map.put("result", "0");
//// 				map.put("message", "账户余额不足");
////  				return AppUtil.returnObject(pd, map);
//// 			}
////			
//// 			_bal = _bal.subtract(amount);
////			
////			logAfter(logger,"3.账户余额修改");
////			pd.put("bal", _bal);
////			pd.put("update_time", DateUtil.getTime());
////			lawerAccountService.updateLawerBal(pd);
////			
////			String pay_method =applicationData.get("withdraw_type").toString();
////			
////			pd.put("trade_type", 1); 
////			pd.put("pay_method", pay_method.equals("1")?"2":"1");
////			pd.put("lawer_id", lawer_id);
////			pd.put("order_id", "");
////			pd.put("amount",  amount.multiply(new BigDecimal(-1)));
////			pd.put("bal", _bal);
////			pd.put("create_time", DateUtil.getTime());
////			pd.put("update_time",  DateUtil.getTime());
////			logAfter(logger, "4.添加一条账户收支明细");
////			lawerAccountService.addLawerAccountLog(pd);
////			
////			
////			logAfter(logger,"5.更新提现申请表的处理标志");
////			pd.put("withdraw_handletime", DateUtil.getTime());
////			lawerAccountService.updateisHandle(pd);
////			
////			
////			
////			map.put("result", "1");
////			map.put("message", "成功");
////			
////			
////		} catch (Exception e) {
////			
////			logAfter(logger,"处理提现出现错误");
////			
////			e.printStackTrace();
////			map.put("result", "0");
////			map.put("message", "处理提现出现错误");
//// 		}
////  		
//		logEnd(logger);
//		return AppUtil.returnObject(pd, map);
//	}
//	
//	/**
//	 * 获取提现记录
//	 * @return
//	 */
//	@RequestMapping(value="/getWithdrawApplicationPage")
//	@ResponseBody
//	public Object getWithdrawApplicationPage(Page page){
//		logBefore(logger, "获取提现记录app_laweraccount.getWithdrawApplicationPage");
//		Map<String,Object> map = new HashMap<String,Object>();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		
//		String withdraw_ishandle = pd.getString("withdraw_ishandle");
//		String begintime = pd.getString("begintime");
//		String endtime = pd.getString("endtime");
//
//  		
// 		try {
// 			System.out.println(pd);
// 			
// 			pd.put("withdraw_ishandle", withdraw_ishandle);
// 			
// 			page.setPd(pd);
// 			
// 			page.setShowCount(1000);
// 			
// 			logAfter(logger, "查询提现申请");
//			List<PageData> varList = lawerAccountService.getWithdrawApplicationPage(page);
//			map.put("data", varList);
//			map.put("result","1");
//			map.put("message", "获取数据成功");
// 		} catch (Exception e) {
// 			logAfter(logger, "获取数据失败");
// 			e.printStackTrace();
// 			map.put("result","0");
//			map.put("message", "获取数据失败");
//		}
//		
//		logEnd(logger);
//		return map ;
//	}
//	
//	
//	
//	
//	/**
//	 * 获取绑定的银行卡
//	 * @return
//	 */
//	@RequestMapping(value="/getLawerBankCard")
//	@ResponseBody
//	public Object getLawerBankCard(){
//		logBefore(logger, " 获取绑定的银行卡app_laweraccount.getLawerBankCard");
//		Map<String,Object> map = new HashMap<String,Object>();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		
//		
//		logEnd(logger);
//		return AppUtil.returnObject(pd, map);
//	}
//	
//	@InitBinder
//	public void initBinder(WebDataBinder binder){
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
//	}
//}
