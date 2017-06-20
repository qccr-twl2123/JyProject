package com.tianer.controller.storeapp.loginapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.service.business.store.StoreService;
import com.tianer.service.business.store_shift.Store_shiftService;
import com.tianer.service.storeapp.LoginappService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
import com.tianer.service.storepc.stotr.StorepcService;
import com.tianer.service.storepc.tableNumber.TablerNumberService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.MD5;
import com.tianer.util.PageData;
import com.tianer.util.StringUtil;


/** 
 * 
* 类名称：MemberController   
* 类描述：   登录app
* 创建人：邢江涛
* 创建时间：2016年6月30日 
 */
@Controller("loginappController")
@RequestMapping(value="/storeapp_login")
public class LoginappController extends BaseController{
	
	@Resource(name="loginappService")
	private LoginappService loginappService;

	
	@Resource(name="storepcService")
	private StorepcService storepcService;
	
	@Resource(name="storeService")
	private StoreService storeService;
	
	/**
	 * 登陆魏汉文0713
	 */
	@RequestMapping(value="/applogin")
	@ResponseBody
	public Object login(HttpServletRequest request ){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="登陆成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String password=pd.getString("password");
			//判断是商家还是操作员的账号和密码
			pd.put("password", MD5.md5(password));
			PageData store = storepcService.listNamePwdById(pd);//判断手机号和密码是否正确
			if(store == null ){
	 				store=storepcService.getOperateLogin(pd);//获取操作员的信息
					if(store != null){
						 String operator_status=store.getString("operator_status");
						 String istuisong=store.getString("istuisong");
 						 pd.put("store_id", store.getString("store_id"));
 						 pd=storepcService.findById(pd);
    					 if(operator_status.equals("1")){
	 							//判断办证金是否已经交了
	 							String biaozhun_status=pd.getString("biaozhun_status");
	  		 					if(biaozhun_status.equals("0")){
	   		 							result="0";
	  			 						message=Const.not_payStore_message;
	  		 					}else if(biaozhun_status.equals("1")){
	  		 						 pd.put("istuisong",istuisong);
	 		 						 pd.put("login_type", "2");
	 								 pd.put("store_id", store.getString("store_id"));
	 								 pd.put("login_phone", store.getString("operator_phone"));
	 								 pd.put("login_name", store.getString("operator_name"));
	 		 						 pd.put("login_id", store.getString("store_operator_id"));
	 								 pd.put("store_operator_id", store.getString("store_operator_id"));
	 								 pd.put("login_phone", store.getString("operator_phone"));
	 								 pd.put("sy", store.getString("sy_competence"));
	 								 pd.put("sp", store.getString("sp_competence"));
	 								 pd.put("hd", store.getString("hd_competence"));
	 								 pd.put("cw", store.getString("cw_competence"));
	 								 pd.put("yx", store.getString("yx_competence"));
	 								 //更新操作员的登陆时间
	 								 pd.put("login", "1");
	 								 storepcService.updateTime(pd);
	  		 					}else if(biaozhun_status.equals("2")){
	  		 						 result="0";
	 			 					 message="您的服务费已到期，请续费";
 		 					}
 						 }else{
 							 result="0";
 							 message="当前操作员未启用，请联系商家";
 						 }
  					}else{
						result="0";
						message="登录账号或密码错误";
						map.put("data", "");
					}
			}else{
					pd=storepcService.findById(store);
					pd.put("login_phone", pd.getString("registertel_phone"));
					pd.put("login_id", store.getString("store_id"));
 					//判断办证金是否已经交了
 					String biaozhun_status=pd.getString("biaozhun_status");
 					if(biaozhun_status.equals("0")){
 							result="0";
							message=Const.not_payStore_message;
 					}else if(biaozhun_status.equals("1")){
						 pd.put("login_type", "1");
 						 pd.put("store_id", store.getString("store_id"));
 						 pd.put("store_name", store.getString("store_name"));
 						 pd.put("login_name", "");
						 pd.put("store_operator_id", "");
						 pd.put("sy", "1,1,1,1");
						 pd.put("sp", "1,1,1,1");
						 pd.put("hd", "1,1,1,1");
						 pd.put("yx", "1,1,1,1");
						 pd.put("cw", "1,1,1,1");
						 pd.put("islogin", "1");
						 pd.put("login_ip", StringUtil.getIp(request));
						 storepcService.updateStatus(pd);
					}else if(biaozhun_status.equals("2")){
						 result="0";
						 message="您的服务费已到期，请去九鱼商家端续费";
					}
			}
  		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		if(pd ==null){
			pd=new PageData();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
  		return map;
	}
	
//	
//	
//	/**
//	 * 短信登陆刘耀耀
//	 */
//	@RequestMapping(value="/appSMSlogin")
//	@ResponseBody
//	public Object SMSlogin(){
//		logBefore(logger, "登陆");
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="登陆成功";
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			//判断是商家还是操作员的账号
//			PageData store = storepcService.listNamePwd(pd);//判断是否有该手机号
//			if(store == null ){
//	 				store=storepcService.getOperateLogin(pd);//获取操作员的信息
//					if(store != null){
//							 pd.put("store_id", store.getString("store_id"));
//							 pd=storepcService.findById(pd);
//							//判断办证金是否已经交了
// 							String biaozhun_status=pd.getString("biaozhun_status");
// 		 					if(biaozhun_status.equals("0")){
// 		 							result="0";
//			 						message="您的店铺没有支付服务费，请去九鱼商家端支付后使用";
// 		 					}else if(biaozhun_status.equals("1")){
//		 						 pd.put("type", "2");
//								 pd.put("store_id", store.getString("store_id"));
//								 pd.put("store_operator_id", store.getString("store_operator_id"));
//								 pd.put("sy", store.getString("sy_competence"));
//								 pd.put("sp", store.getString("sp_competence"));
//								 pd.put("hd", store.getString("hd_competence"));
//								 pd.put("cw", store.getString("cw_competence"));
//								 pd.put("yx", store.getString("yx_competence"));
//								 //更新操作员的登陆时间
//								 pd.put("login", "1");
//								 storepcService.updateTime(pd);
//		 					}else if(biaozhun_status.equals("2")){
//		 						 result="0";
//		 						 message="您的服务费已到期，请去九鱼商家端续费";
//		 					}
// 					}else{
//						result="0";
//						message="没有该手机号，清先注册！！";
//						map.put("data", "");
//					}
//			}else{
//					pd=storepcService.findById(store);
//					//判断办证金是否已经交了
// 					String biaozhun_status=pd.getString("biaozhun_status");
//  					if(biaozhun_status.equals("0")){
//  							result="0";
//	 						message="您的店铺没有支付服务费，请去九鱼商家端支付后使用";
//  					}else if(biaozhun_status.equals("1")){
// 						 pd.put("type", "1");
// 						 pd.put("store_id", store.getString("store_id"));
// 						 pd.put("store_name", store.getString("store_name"));
// 						 pd.put("store_operator_id", "");
// 						 pd.put("sy", "1,1,1,1");
// 						 pd.put("sp", "1,1,1,1");
// 						 pd.put("hd", "1,1,1,1");
// 						 pd.put("yx", "1,1,1,1");
// 						 pd.put("cw", "1,1,1,1");
// 						 storepcService.updateStatus(pd);
//  					}else if(biaozhun_status.equals("2")){
// 						 result="0";
// 						 message="您的服务费已到期，请去九鱼商家端续费";
// 					}
// 			}
//  		} catch(Exception e){
//			logger.error(e.toString(), e);
//			result="0";
//			message="系统异常";
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", pd);
//  		return map;
//	}
//	
	
	/**
	 * 换绑手机号码接口
	 */
	@RequestMapping(value="/code")
	@ResponseBody
	public Object code() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="换绑成功";
		PageData pd = new PageData();
  		try{
				pd = this.getPageData();
				String new_phone=pd.getString("new_phone");
				String login_id=pd.getString("login_id");
				if( new_phone == null || new_phone.equals("")){
					result="0";
					message="换绑手机号码不能为空";
				}else{
					if(login_id.length() == 8){
						pd.put("store_id", login_id);
						PageData pd1=storepcService.findById(pd);
						if(pd1==null){
							map.put("result", "0");
							map.put("message", "当前登录ID号无法匹配到用户，请重新登录");
							map.put("data","");
							return map;
						}
 						loginappService.editPowd(pd);
 				    }else{
 						pd.put("store_operator_id", login_id);
	 					PageData pd1=storeManageService.findOperatorById(pd);
						if(pd1==null){
							map.put("result", "0");
							map.put("message", "当前登录ID号无法匹配到用户，请重新登录");
							map.put("data","");
							return map;
						}
 						loginappService.editPowdByOprator(pd);
 				    }
 				}
 		}catch(Exception e){
			logger.error(e.toString(), e);
			message="获取失败";
			result="0";
		}
  		map.put("result", result);
 		map.put("message", message);
 		map.put("data", "");
 		return AppUtil.returnObject(pd, map);
	}

	
	
	/**
	 * 退出登陆
	 */
	@RequestMapping(value="/outLogin")
	@ResponseBody
	public Object outLogin(Page page){
//		logBefore(logger, "退出登陆"); 
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="退出成功";
		PageData pd = new PageData();
		try{
				pd = this.getPageData();
				if(pd.getString("login_type").equals("1")){
					pd.put("islogin","0");
					storepcService.updateStatus(pd);
				}else{
					pd.put("login","2");
					storepcService.updateTime(pd);
				}
   		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
  		return map;
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value="/updPass")
	@ResponseBody
	public Object updPass() throws Exception{
//		logBefore(logger, "修改密码");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String oldPassword = pd.getString("oldpassword");//输入的当前密码
			String password = pd.getString("password");//输入的新密码
 			String confirmPass = pd.getString("confirmPass");//确认密码
			String login_id = pd.getString("login_id");//登录人ID
			if(!password.equals(confirmPass)){
				result="0";
				message="两次密码输入不一致！";
				map.put("data", "");
				map.put("result", result);
				map.put("message", message);
				return map;
			}
			if(login_id != null && !login_id.equals("")){
				if(login_id.length() == 8){
 					if( oldPassword != null &&  !oldPassword.equals("")  ){
						if(MD5.md5(oldPassword).equals(storepcService.findPassword(login_id))){
							pd.put("password", MD5.md5(pd.getString("password")));
							loginappService.editPowd(pd);
	 					}else{
	 						result = "0";
	 						message="修改失败,原密码输入错误";
	 					}
					} 
	 			 }else{
 						if( oldPassword != null && !oldPassword.equals("")  ){
							if(MD5.md5(oldPassword).equals(storeManageService.findPassword(login_id))){
								pd.put("password", MD5.md5(pd.getString("password")));
								loginappService.editPowdByOprator(pd);
	 						}else{
	 							result = "0";
	 							message="修改失败,原密码输入错误";
	 						}
						} 
	 			 }
			}else{
				result = "0";
				message="修改失败，请先登录！";
			}
   		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			 e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data","");
		return map;
	}
	
	
	/**
	 * 修改用户名
	 */
	@RequestMapping(value="/updName")
	@ResponseBody
	public Object updName() throws Exception{
//		logBefore(logger, "修改用户名");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String store_id = pd.getString("store_id");
			pd.put("store_id", store_id);
			String store_name = pd.getString("store_name");
			pd.put("store_name", store_name);
			if(store_id != null && store_name != null){
				loginappService.edit(pd);
			}
		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data","");
		return AppUtil.returnObject(pd, map);
	}
	

	/**
	 * 账户信息
	 */
	@RequestMapping(value="/accountinfo")
	@ResponseBody
	public Object accountinfo(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//商户id
			String store_id = pd.getString("store_id");
			pd.put("store_id", store_id);
			pd=loginappService.account(pd);
 			if(pd == null ){
				result="0";
				message="获取失败";
				map.put("data", "");
			}
 			if(pd.getString("registertel_phone").length() == 11){
 				pd.put("registertel_phone", pd.getString("registertel_phone").substring(0, 3)+"****"+pd.getString("registertel_phone").substring(7, 11));
 			}
			map.put("data", pd);
  		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
  		return map;
	}
	
	/**
	 * 忘记密码
	 */
	@RequestMapping(value="/editPowd")
	@ResponseBody
	public Object editPowd() throws Exception{
//		logBefore(logger, " 忘记密码");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
  			String login_id=pd.getString("login_id");
			if(login_id ==null){
				result="0";
				message="ID号有误";
			}else{
				if(login_id.length() == 8){
						pd.put("store_id", login_id);
						PageData pd1=storepcService.findById(pd);
						if(pd1==null){
							map.put("result", "0");
							map.put("message", "当前登录ID号无法匹配到用户，请重新输入");
							map.put("data","");
							return map;
						}
						pd.put("password", MD5.md5(pd.getString("password")));
 						loginappService.editPowd(pd);
 				}else{
 						pd.put("store_operator_id", login_id);
	 					PageData pd1=storeManageService.findOperatorById(pd);
						if(pd1==null){
							map.put("result", "0");
							map.put("message", "当前登录ID号无法匹配到用户，请重新输入");
							map.put("data","");
							return map;
						}
						pd.put("password", MD5.md5(pd.getString("password")));
 						loginappService.editPowdByOprator(pd);
 				}
			}
 		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			 e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data","");
		return map;
	}
	
	
 
	
	/**
	 *  分享商户链接的搜索框搜索
	 */
	@RequestMapping(value="/listinfoStore_name")
	@ResponseBody
	public Object listinfoStore_name(   ){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{ 
				pd=this.getPageData();
				List<PageData> storeList=storeService.listAll(pd);
				map.put("data",storeList);
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
	 *  分享商户链接的地址
	 */
	@RequestMapping(value="/sendUrlForStore")
	@ResponseBody
	public Object sendUrlForStore(   ){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{ 
				pd=this.getPageData();
				String url="";
				map.put("data", url);
		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return AppUtil.returnObject(pd, map);
	}
	
	
	@Resource(name="store_shiftService")
	private Store_shiftService store_shiftService;

	@Resource(name = "tablerNumberService")
	private TablerNumberService tablerNumberService;
	
	
	/**
	 *  前往选择桌号的页面，数据
	 */
	@RequestMapping(value="/goAppXuanBanCiZhouHao")
	@ResponseBody
	public Object goAppXuanBanCiZhouHao(   ){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//获取当前商家的班次 
 			List<PageData> shiftList=store_shiftService.listAll(pd);
 			map1.put("shiftList", shiftList);
 			//获取当前商家的桌号
			List<PageData>	deskList = tablerNumberService.listAll(pd);//列出Store列表
			map1.put("deskList", deskList);
  		} catch(Exception e){
			result="0";
			message="系统错误";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data", map1);
		return map;
	}
	
	
	@Resource(name="storeManageService")
	private StoreManageService storeManageService;
	
	
	/**
	 *  确认选择桌号的页面，数据 store_operator_id
	 */
	@RequestMapping(value="/overBanCiZhouHao")
	@ResponseBody
	public Object overBanCiZhouHao(   ){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
//			System.out.println(pd);
			String login_type=pd.getString("login_type");//1-商家，2-员工
			//store_shift_id班次，alldesk_no桌号
			if(login_type.equals("2")){
				storeManageService.updateOperator(pd);
			}
		} catch(Exception e){
			result="0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data", map1);
		return map;
	}
	
	
	/**
	 *  是否参加pk======ispk:0-不参加，1-参加,store_id
	 *  storeapp_login/ifCanJiaPK.do
	 *  
	 */
	@RequestMapping(value="/ifCanJiaPK")
	@ResponseBody
	public Object ifCanJiaPK( ){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改成功";
		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
			storeService.edit(pd);
		} catch(Exception e){
			result="0";
			map.put("message", "修改异常");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data","");
		return map;
	}
	

	
	/**
	 *  pk的商家集合：按综合分值pk,前100名,当前商家id
	 *  storeapp_login/pkStoreList.do
	 */
	@RequestMapping(value="/pkStoreList")
	@ResponseBody
	public Object pkStoreList(){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
			pd=storeService.findById(pd);
			if(pd != null && pd.getString("ispk").equals("1")){
				List<PageData> storeList=storeService.listAllPkStore(pd);
				for(int i=0;i<storeList.size();i++){
					PageData e=new PageData();
					e=storeList.get(i);
					if(pd.getString("store_id").equals(e.getString("store_id"))){
						pd.put("nowstorepaiming", (i+1)+"");
						pd.put("nowstore_name", e.getString("store_name"));
					}
					e.put("paiming",  (i+1)+"");
				}
 				map1.put("storeList",storeList);
			}else{
				result="0";
				message="未开通战绩排行版";
			}
 			map1.put("pd",pd);
  		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data",map1);
 		return map;
	}
	
	
	/**
	 * 修改推送
	 * login_id,login_type,istuisong:1-可推送，0-不可推送
	 * zhsh/storeapp_login/editTuiSong.do
	 */
	@RequestMapping(value="/editTuiSong")
	@ResponseBody
	public Object editTuiSong() throws Exception{
//		logBefore(logger, "editTuiSong");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String login_id=pd.getString("login_id");
			if(pd.getString("login_type").equals("1")){
				pd.put("store_id", login_id);
 				storepcService.updateStatus(pd);
			}else{
				pd.put("store_operator_id", login_id);
 				storepcService.updateTime(pd);
			}
 		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
		pd=null;
		return map;
	}
	
	
	
	
	
}
