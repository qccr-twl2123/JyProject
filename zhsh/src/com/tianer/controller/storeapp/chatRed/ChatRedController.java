package com.tianer.controller.storeapp.chatRed;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.entity.zhihui.JFredTask;
import com.tianer.service.memberapp.AppFriendService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppMember_redpacketsService;
import com.tianer.service.memberapp.AppMember_wealthhistoryService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.memberapp.AppStore_redpacketsService;
import com.tianer.service.storeapp.ChatRedService;
import com.tianer.service.storeapp.Storeapp_redpacketsService;
import com.tianer.service.storepc.store_marketingtype.Storepc_marketingtypeService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;
import com.tianer.util.StringUtil;



/** 
* 类名称：ChatRedController
* 创建人：邢江涛  app端群聊发红包接口
* 创建时间：2016-07-4 
*/
@Controller("ChatRedController")
@RequestMapping(value="/storeapp_chaRed")
public class ChatRedController extends BaseController{
	
	@Resource(name="storeapp_redpacketsService")
	private Storeapp_redpacketsService redpacketsService;
	@Resource(name = "ChatRedService")
	private ChatRedService chatRedService;
	@Resource(name = "storepc_marketingtypeService")
	private Storepc_marketingtypeService storepcMarketingTypeService;
	
	
	/**
	 * 发送红包(群发)现金红包/折扣红包
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		String result = "1";
		String message="发送成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
  			//组合为红包编号
			String store_redpackets_id=this.getTimeID();
 			pd.put("store_redpackets_id", store_redpackets_id);//红包编号
			//商家id
			String store_id = pd.getString("store_id");
 			//红包类型
			String redpackage_type = pd.getString("redpackage_type");
			String redpackage_number = pd.getString("redpackage_number");
			String choice_type = pd.getString("choice_type");
			String srp_usercondition_id = pd.getString("srp_usercondition_id");
 			String srp_usercondition_content = pd.getString("reduce_content");
 			//发放金额
			String money = pd.getString("money");
			pd.put("money", money);
			//判断发放金额是否为空
			if(money == null || money.equals("")){
					map.put("result", "0");
					map.put("message", "不能为空");
					map.put("data", "");
			 		return map;
			}
			if(redpackage_type.equals("1") && srp_usercondition_id.equals("2") ){//现金红包
 				String number=srp_usercondition_content.substring(srp_usercondition_content.indexOf("满")+1,srp_usercondition_content.indexOf("元"));
 				if(number == null || number.equals("")){
 					number="0";
 				}
 				double n=Double.parseDouble(number);//满多少元使用
 				double m=Double.parseDouble(money);//红包总共多少元
 				int x=Integer.parseInt(redpackage_number);//多少个红包
 				if(choice_type.equals("2")){
 					if(n < (m/x)*2){
 						map.put("result", "0");
 						map.put("message", "满X金额过少，至少满"+df.format(((m/x)*2))+"元");
 						map.put("data", "");
 				 		return map;
 					}
 				}else{
 					if(n < (m/x+m/x/2)*2){
 						map.put("result", "0");
 						map.put("message", "满X金额过少，至少满"+df.format(((m/x+m/x/2)*2))+"元");
 						map.put("data", "");
 				 		return map;
 					}
 				}
 			}
   			//判断红包个数是否为空
			if(redpackage_number == null || redpackage_number.equals("") || Integer.parseInt(redpackage_number) < 1 ){
					map.put("result", "0");
					map.put("message", "红包个数必须大于0");
					map.put("data", "");
			 		return AppUtil.returnObject(pd, map);
			}
			//发放规则choice_type
			if(pd.getString("srp_usercondition_id") != null && !pd.getString("srp_usercondition_id").equals("")){
						//使用条件srp_usercondition_id
						if(pd.getString("srp_usercondition_id").equals("1")){
							pd.put("srp_usercondition_content", "无条件");
						}else if(pd.getString("srp_usercondition_id").equals("2")){
							if(pd.getString("reduce_content") != null && !pd.getString("reduce_content").equals("")){
								pd.put("srp_usercondition_content", pd.getString("reduce_content"));
							}else{
								pd.put("srp_usercondition_content", "满0元");
							}
			 			}else if(pd.getString("srp_usercondition_id").equals("3")){
							pd.put("srp_usercondition_content", "首单");
						}
			}
  			//有效开始时间
			pd.put("starttime", DateUtil.getDay());
			//有效结束日期
 			pd.put("endtime", pd.getString("enddate"));
  			long l0=DateUtil.fomatDate( pd.getString("starttime")).getTime();
			long l1=DateUtil.fomatDate( pd.getString("starttime")).getTime();
			long l2=DateUtil.fomatDate( pd.getString("endtime")).getTime();
 			if( l0>l1 || l0 > l2  ){
 				map.put("result", "0");
 				map.put("message", "红包有效时间不能小于今天");
 				map.put("data", "");
 		  		return map;
 			}
 			if( l1 > l2 ){
 				map.put("result", "0");
 				map.put("message", "结束时间不能小于开始时间");
 				map.put("data", "");
 		  		return map;
 			}
  			//区分是群聊红包还是单聊红包state_type(1群聊，2单聊)
  			pd.put("redpackage_status", Const.liaotian_redpackage_status);//聊天红包
 			redpacketsService.save(pd);//新增红包
  			map.put("data", store_redpackets_id);
		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			e.printStackTrace();
		}
  		map.put("result", result);
		map.put("message", message);
  		return map;
	}
	
	
	/**
	 * 立减
	 */
	@RequestMapping(value="/minus")
	@ResponseBody
	public Object minus() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="发送成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String redpackage_number = pd.getString("redpackage_number");
 			//id
			String store_redpackets_id=BaseController.getTimeID();
			pd.put("store_redpackets_id", store_redpackets_id);
			//有效结束时间
			pd.put("starttime", DateUtil.getDay());
 			//满多少
			String oneachieve_money = pd.getString("oneachieve_money");
 			//减多少
			String onereduce_monye = pd.getString("onereduce_monye");
 			//新增商家红包记录
			String reduce_content = "满"+ oneachieve_money + "元减" +onereduce_monye+"元";
			if(Double.parseDouble(oneachieve_money) <= 0  ||  Double.parseDouble(onereduce_monye) <= 0){
				map.put("result", "0");
				map.put("message", "金额必须大于0");
 				map.put("data", "");
		  		return map;
			}
			if(Double.parseDouble(oneachieve_money) <= Double.parseDouble(onereduce_monye)){
				map.put("result", "0");
				map.put("message", "满足的金额必须大于减的金额");
 				map.put("data", "");
		  		return map;
			}
 			pd.put("reduce_content", reduce_content);
			pd.put("money", onereduce_monye);
			pd.put("redpackage_number",redpackage_number);
			pd.put("srp_usercondition_id", "0");
			pd.put("srp_opentype_id", "0");
 			pd.put("redpackage_type", pd.getString("type"));
 			pd.put("choice_type", "0");
 			pd.put("redpackage_status", Const.liaotian_redpackage_status);
 			pd.put("state_type", "2");
 			redpacketsService.save(pd);//新增红包
 			map.put("data", store_redpackets_id);
		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			e.printStackTrace();
		}
 		map.put("result", result);
		map.put("message", message);
  		return map;
	}
	
	

	@Resource(name="appStoreService")
	private AppStoreService appStoreService;

	
	/**
	 * 点击发送积分（0713已测试）
	 */
	@RequestMapping(value="/integral")
	@ResponseBody
	public Object saveintegralRed() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="发送成功";
		PageData pd = new PageData();
		try {
				pd = this.getPageData();
				String store_wealthhistory_id=BaseController.getTimeID();
   				String store_id = pd.getString("store_id");//商家id
 				String content = pd.getString("content");//内容
 				String redpackage_money=pd.getString("redpackage_money"); //积分红包金额
 				String redpackage_number=pd.getString("redpackage_number");//数量  
				String redpackage_type=pd.getString("redpackage_type");  ////随机，平均 传2
 				if(redpackage_money == null || redpackage_money.equals("") || Double.parseDouble(redpackage_money) <= 0){
					map.put("result", "0");
					map.put("message", "金额必须大于0");
					map.put("data", "");
					return map;
				}
				if(redpackage_number == null || redpackage_number.equals("") || Integer.parseInt(redpackage_number) <= 0){
					map.put("result", "0");
					map.put("message", "数量必须大于0");
					map.put("data", "");
					return map;
				}
				pd.put("redpackage_number", String.valueOf(Integer.parseInt(redpackage_number)));
				PageData spd=appStoreService.findById(pd);
	 			//发放类型
 				//当前为积分的情况下的积分数量
 				double integralMoney = Double.parseDouble(redpackage_money);
				double nowMoney = Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(pd));
				//发送积分
				if(integralMoney <= 0){
					map.put("result", "0");
					map.put("message", "积分格式错误");
					map.put("data", "");
			 		return map;
				}
				if(integralMoney > nowMoney){
					map.put("result", "0");
					map.put("message", "积分不足");
					map.put("data", "");
			 		return map;
				}
 				String now_wealth =TongYong.df2.format( nowMoney - integralMoney) ;
				pd.put("now_wealth", now_wealth);
				appStoreService.editWealthById(pd);
				//新增财富历史记录
	 			pd.put("wealth_type", "1");
				pd.put("profit_type", "8");
				pd.put("number", -integralMoney+"");
				pd.put("store_id", store_id);
				if(pd.getString("store_operator_id") == null || pd.getString("store_operator_id").equals("") ){
					pd.put("store_operator_id", "jy"+store_id);
				}
				pd.put("store_operator_id",pd.getString("store_operator_id") );
				pd.put("process_status", "1");
				pd.put("pay_type", "nowpay");
				pd.put("last_wealth", String.valueOf(now_wealth));
				pd.put("store_wealthhistory_id", store_wealthhistory_id);
				pd.put("arrivalMoney", String.valueOf(-integralMoney));
				pd.put("jiaoyi_id", store_wealthhistory_id);
				pd.put("in_jiqi", "2");
				appStoreService.saveWealthhistory(pd);
				//新增积分红包记录
	 			pd.put("ms_redpackage_id", store_wealthhistory_id);
	  			pd.put("user_id",  store_id);
				pd.put("user_type",  "1");
				pd.put("content",  content);
				pd.put("redpackage_money",  integralMoney);
				pd.put("redpackage_type",  redpackage_type);
				pd.put("redpackage_number",  redpackage_number);
				member_redpacketsService.saveSendRed(pd);
				map.put("data",store_wealthhistory_id);
				//判断改商家的积分是否需要提醒
				PageData e = redpacketsService.remindIntegral(pd);
				//再发放积分后重新获取该商家的积分数量
				String jfcount = ServiceHelper.getAppStoreService().sumStoreWealth(pd);
				double jf = Double.parseDouble(jfcount);
				double jifenCount =  Double.parseDouble(e.getString("remind_integral"));
				if(jf < jifenCount){
					String phone = e.getString("registertel_phone");
					//SmsUtil.sendLessScode(phone);
				}
				//新增总流水记录
 	   			PageData waterpd=new PageData();
	   			waterpd.put("pay_status","1");
   				waterpd.put("waterrecord_id",store_wealthhistory_id);
				waterpd.put("user_id", store_id);
				waterpd.put("user_type", "1");
				waterpd.put("withdraw_rate","0");
				waterpd.put("money_type","10");
				waterpd.put("money", TongYong.df2.format(-integralMoney));
				waterpd.put("reduce_money", "0");
				waterpd.put("arrivalmoney",  -integralMoney);
				waterpd.put("nowuser_money", ServiceHelper.getAppStoreService().sumStoreWealth(pd) );
				waterpd.put("application_channel","2" );
				waterpd.put("remittance_name","发送积分红包:"+integralMoney+"积分");
				waterpd.put("remittance_number",store_id);//支付人的支付账号
				waterpd.put("remittance_type","7" );
				waterpd.put("integral_money", TongYong.df2.format(-integralMoney) );
 				waterpd.put("createtime",DateUtil.getTime());
				waterpd.put("over_time",DateUtil.getTime());
 				waterpd.put("jiaoyi_type","6");
				waterpd.put("payee_number",Const.jiuyunumber);
	 			waterpd.put("order_tn",  store_wealthhistory_id);
				waterpd.put("province_name", spd.getString("province_name"));
				waterpd.put("city_name", spd.getString("city_name"));
				waterpd.put("area_name", spd.getString("area_name"));
				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
				waterpd=null;
				//新增定时器
				JFredTask jftask=new JFredTask(store_wealthhistory_id);
				Timer tt=new Timer();
				tt.schedule(jftask, Const.jfguoqi);
   		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			e.printStackTrace();
		}
 		map.put("result", result);
		map.put("message", message);
  		return map;
	}
	@Resource(name="appMember_redpacketsService")
	private AppMember_redpacketsService member_redpacketsService;
	
	
	
	/**
	 * 去发送积分页面---可用积分（已测试）
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//商家id ,积分
 			double count = Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(pd));
 			map.put("data", TongYong.df2.format(count));
		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return AppUtil.returnObject(pd, map);
	}
	

	
	/**
	 * 帮客户注册会员 
	 * storeapp_chaRed/member.do
	 * 
	 * phone 手机号码
	 */
	@RequestMapping(value="/member")
	@ResponseBody
	public Object member() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取验证码成功";
		PageData pd = new PageData();
		String code=StringUtil.getSixRandomNum();
 		try{
			pd = this.getPageData();
			String phone=pd.getString("phone");
			if(phone == null || phone.equals("")){
				map.put("result", "0");
				map.put("message", "手机号码不能为空");
				map.put("data", "");
		 		return map;
			}
			//判断是否注册过
			pd = appMemberService.detailByPhone(pd);
			if(pd != null){
				map.put("result", "0");
				map.put("message", "当前推荐手机号已注册，请重新填写");
				map.put("data", "");
		 		return map;
			}else{
				/*
				 * 您正在注册**商家的会员，如确定是您本人在场，请将验证码1234告知工作人员
				 */
				SmsUtil.sendZcCode(phone, code);
			}
   		}catch(Exception e){
			logger.error(e.toString(), e);
			message="获取失败";
			result="0";
		}
  		map.put("result", result);
 		map.put("message", message);
 		map.put("data", code);
 		return map;
	}
	
	/**
	 * 帮客户注册会员---》》验证码正确调的接口
	 */
	@RequestMapping(value="/codeOkMember")
	@ResponseBody
	public Object codeOkMember() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="注册成功";
		PageData pd = new PageData();
   		try{
			pd = this.getPageData();
 			String phone = pd.getString("phone");
 			String password=BaseController.getMiMaNumber();
 			String recommended = pd.getString("recommended");
 			String recommended_type = pd.getString("recommended_type");
 			pd.put("name", phone.substring(0, 3)+"****"+phone.substring(7, 11));
 			PageData spd=new PageData();
			spd.put("store_id", recommended);
 			pd.put("province_name", appStoreService.findById(spd).getString("store_name"));
 			pd.put("city_name", appStoreService.findById(spd).getString("city_name"));
 			pd.put("area_name", appStoreService.findById(spd).getString("area_name"));
 			pd.put("zhuce_shebei", "2");
 			pd=TongYong.saveMember(pd,recommended, recommended_type);
  			if(pd.getString("flag").equals("false")){
 				map.put("result", "0");
 				map.put("message", pd.getString("message"));
 				map.put("data", "");
  		 		return map;
 			}
   		    //发送短信
  			SmsUtil.sendMem(phone, password,appStoreService.findById(spd).getString("store_name"));
   		}catch(Exception e){
			logger.error(e.toString(), e);
			message="注册失败"+e.toString();
			result="0";
		}
  		map.put("result", result);
 		map.put("message", message);
 		map.put("data", "");
 		return map;
	}
	
	@Resource(name="appFriendService")
	private AppFriendService appFriendService;
	
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	

	
    /**
     * 
    * 方法名称:：businessFriends 
    * 方法描述：我的好友列表
    * 创建人：邢江涛
    * storeapp_chaRed/businessFriends.do?store_id=47883919
     */
	@RequestMapping(value="/businessFriends")
	@ResponseBody
	public Object businessFriends(){
 		Map<String,Object> map = new HashMap<String,Object>();
  		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
			List<PageData> allList=TongYong.getFriendList(pd.getString("store_id"));
			map.put("data", allList);
			allList=null;
			PageData spd= appStoreService.findSpById(pd);	//列出Store列表
			map.put("spd", spd);
		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	
	/**
	 * 判断积分红包是否已经被领取
	 * 魏汉文20160702
	 */
	@RequestMapping(value="/isOverJFRed")
	@ResponseBody
	public synchronized Object isOverJFRed(){
 		Map<String,Object> map = new HashMap<String,Object>();
 		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
			map=TongYong.getJfRedstatic(pd);
		} catch(Exception e){
 			logger.error(e.toString(), e);
		}
 		return map;
	}
	
	
	@Resource(name="appMember_wealthhistoryService")
	private AppMember_wealthhistoryService appMember_wealthhistoryService;
	@Resource(name="appStore_redpacketsService")
	private AppStore_redpacketsService appStore_redpacketsService;
	
	
	
	
	/**
	 * 领取红包针对。。聊天中的=====现金折扣红包，立减红包，优惠现金红包，优惠折扣红包
	 * 魏汉文20160623
	 * 
	 * user_id
	 * store_redpackets_id
	 * money
	 * 
	 * storeapp_chaRed/getRedPackage.do
	 * 
	 */
	@RequestMapping(value="/getRedPackage")
	@ResponseBody
	public synchronized Object getRedPackage() throws Exception{
		//logBefore(logger, "领取红包");
		Map<String,Object> map = new HashMap<String,Object>();
		DecimalFormat    df   = new DecimalFormat("######0.00"); 
 		String result = "1";
		String message="领取成功";
		PageData pd = new PageData();
		try{ 
				pd = this.getPageData();
				//获取红包ID
				PageData e=new PageData();
				//判断是否已经领取过这个红包
			    pd.put("member_id", pd.getString("user_id"));
 				e=appMemberService.findRePackagedById(pd);
				if(e != null){
					map.put("result", "1");
					map.put("message", "你已领取过当前红包！");
					map.put("data", e);
			   		return map;
				}
 				e=appStore_redpacketsService.findRedById(pd);
  				if(e != null){
					//判断当前红包是否充足
					String redpackage_type=e.getString("redpackage_type");
					String choice_type=e.getString("choice_type");
					double money=Double.parseDouble(e.getString("money"));//总金钱/折扣率
					double overget_money=Double.parseDouble(e.getString("overget_money"));//已领金钱总金钱
					int redpackage_number=Integer.parseInt(e.getString("redpackage_number"));//总红包
					int overget_number=Integer.parseInt(e.getString("overget_number"));//已领红包
 					if(redpackage_number ==  overget_number  ){
						  result="1";
						  message="很抱歉，你晚了一步，红包已被领完";
					}else{
							  PageData e2=new PageData();
							  String srp_usercondition_id=e.getString("srp_usercondition_id");
							  String srp_usercondition_content=e.getString("srp_usercondition_content");
							  String redpackage_content="";
							  if(redpackage_type.equals("51")){//立减可和其他红包一起使用
								  e2.put("redpackage_type", "21");
								  e2.put("redpackage_content", e.getString("reduce_content"));
								  //获取到所有的金钱
								  e2.put("redpackage_money", money);
							  }else if(redpackage_type.equals("52")){//立减不可和其他红包一起使用
								  e2.put("redpackage_type", "22");
								  e2.put("redpackage_content", e.getString("reduce_content"));
								  e2.put("redpackage_money", money);
							  }else if(redpackage_type.equals("1")){//现金红包
										  e2.put("redpackage_type",  e.getString("srp_usercondition_id"));
		  								  if(choice_type.equals("1")){
												//获取到随机的金钱
		 									        if(redpackage_number -overget_number == 1){
		 									        	e2.put("redpackage_money", df.format(money-overget_money));
		 									        }else{
		 									        	double pjmoney=money/redpackage_number;
		 												double minpjmoney=pjmoney/2;
		 												double maxpjmoney=pjmoney/2+pjmoney;
		 												money=StringUtil.getSuiJi(minpjmoney, maxpjmoney);
		 			 								   e2.put("redpackage_money", df.format(money));
		 									        }
		 								  }else{
												//获取到平均的金钱
												  money=money/redpackage_number;
												  e2.put("redpackage_money", df.format(money));
										  }
 										if(srp_usercondition_id.equals("1")){
											redpackage_content=srp_usercondition_content+"减"+df.format(money)+"元";
 											e.put("reduce_content", redpackage_content);
										}else if(srp_usercondition_id.equals("2")){
											redpackage_content=srp_usercondition_content+"减"+df.format(money)+"元";
											srp_usercondition_id="21";
 										}else if(srp_usercondition_id.equals("3")){
											redpackage_content=srp_usercondition_content+"减"+df.format(money)+"元";
 										}
										 e.put("reduce_content", redpackage_content);
		  								 e2.put("redpackage_content", redpackage_content);
 							  }else if(redpackage_type.equals("2")){//折扣红包
		 								  	
											if(srp_usercondition_id.equals("1")){
												redpackage_content=srp_usercondition_content+"打"+df.format(money)+"折";;
 											}else if(srp_usercondition_id.equals("2")){
												redpackage_content=srp_usercondition_content+"打"+df.format(money)+"折";
 											}else if(srp_usercondition_id.equals("3")){
												redpackage_content=srp_usercondition_content+"打"+df.format(money)+"折";
 											}
											if(choice_type.equals("2")){
												srp_usercondition_id=srp_usercondition_id+"1";
											}
										  e2.put("redpackage_content", redpackage_content);
										  e2.put("redpackage_type",  srp_usercondition_id);
	 									  e2.put("redpackage_money", df.format(money));
	 									 e.put("reduce_content", redpackage_content);
  							  }else if(redpackage_type.equals("3")){//优惠现金红包
 											if(srp_usercondition_id.equals("1")){
												redpackage_content=srp_usercondition_content+"减"+df.format(money)+"元";
 											}else if(srp_usercondition_id.equals("2")){
												redpackage_content=srp_usercondition_content+"减"+df.format(money)+"元";
												srp_usercondition_id="21";
 											}else if(srp_usercondition_id.equals("3")){
												redpackage_content=srp_usercondition_content+"减"+df.format(money)+"元";
 											}
										  e2.put("redpackage_type",  srp_usercondition_id);
		 								  e2.put("redpackage_money", df.format(money));
										  e2.put("redpackage_content", redpackage_content);
										  e.put("reduce_content", redpackage_content);
							  }else if(redpackage_type.equals("4")){//优惠折扣红包
 											if(srp_usercondition_id.equals("1")){
												redpackage_content=srp_usercondition_content+"打"+df.format(money)+"折";;
 											}else if(srp_usercondition_id.equals("2")){
												redpackage_content=srp_usercondition_content+"打"+df.format(money)+"折";
 											}else if(srp_usercondition_id.equals("3")){
												redpackage_content=srp_usercondition_content+"打"+df.format(money)+"折";
 											}
											if(choice_type.equals("2")){
												srp_usercondition_id=srp_usercondition_id+"1";
											}
										  e2.put("redpackage_content", redpackage_content);
										  e2.put("redpackage_type",  srp_usercondition_id);
										  e2.put("redpackage_money", df.format(money));
										  e.put("reduce_content", redpackage_content);
							}
  						    e2.put("redpackage_id",BaseController.getTimeID());
							e2.put("member_id", pd.getString("member_id"));
							e2.put("store_redpackets_id", e.getString("store_redpackets_id"));
							e2.put("enddate", e.get("endtime").toString());
							e2.put("startdate", e.get("starttime").toString());
							e2.put("set_id", e.getString("store_id"));
							e2.put("set_type", "1");
							if(pd.getString("user_type").equals("2")){
									appMemberService.saveRedForMember(e2);//新增红包信息至会员
									ServiceHelper.getAppMemberService().updateMemberRedNumber(e2);
								     //修改红包数量以及金钱
								    e.put("overget_number", Integer.parseInt(e.getString("overget_number"))+1+"");
									e.put("overget_money",overget_money+money+"");
									appStore_redpacketsService.edit(e);
 							}else{
									result="0";
 							}
							e2=null;
  	 				}
 					map.put("data", e);
 				}else{
					map.put("data", "");
					result = "0";
					message="该红包不存在";
				}
   		}catch(Exception e){
			 result = "0";
			 message="系统错误";
			 map.put("data", "");
			 logger.error(e.toString(), e);
		}
 		map.put("result", result);
		map.put("message", message);
   		return map;
	}
	
	
	/**
	 * 推荐会员注册
	 * 魏汉文20160623
	 */
	@RequestMapping(value="/tuijianRegister")
	@ResponseBody
	public Object tuijianRegister() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="推荐等待确认中";
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
 			pd.put("phone", pd.getString("be_phone"));
 			///判断是否注册过
 			if(appMemberService.detailByPhone(pd) != null){
				map.put("result", "0");
				map.put("message", "当前推荐手机号已注册，请重新填写");
				map.put("data", "");
		 		return map;
			}
 			pd.remove("phone");
 			pd.put("store_id", pd.getString("id"));
 			if(appStoreService.findById(pd) != null){
 				if(appMemberService.findDetailTuiJian(pd) == null){
 					SmsUtil.TjFrinendSave(pd.getString("be_phone"), appStoreService.findById(pd).getString("registertel_phone"),pd.getString("id"));
 	 				//将信息存入数据库中
 	 				pd.put("type", "1");
 	 				appMemberService.saveTuiJian(pd);
 				}
 			}else{
				map.put("result", "0");
				map.put("message", "请前往登录，再推荐会员注册");
				map.put("data", "");
		 		return map;
			}
 		}catch(Exception e){
			 result = "0";
			 message="系统错误";
			logger.error(e.toString(), e);
		}
 		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
  		return map;
	}
	
	
}
