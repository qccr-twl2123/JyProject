package com.tianer.controller.memberpc;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.tongyongUtil.TongYong;
import com.tianer.entity.Page;
import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.pc_advert.Pc_advertService;
import com.tianer.service.business.store.StoreService;
import com.tianer.service.memberPc.PcTongYongService;
import com.tianer.service.memberPc.Storepc_fileService;
import com.tianer.service.memberapp.AppFriendService;
import com.tianer.service.memberapp.AppGoodsService;
import com.tianer.service.memberapp.AppMember_redpacketsService;
import com.tianer.service.memberapp.AppStore_redpacketsService;
import com.tianer.service.memberapp.AppStorepc_marketingService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
import com.tianer.service.storepc.tableNumber.TablerNumberService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.EbotongSecurity;
import com.tianer.util.ImageBase64Test;
import com.tianer.util.MD5;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;
import com.tianer.util.StringUtil;
import com.tianer.util.Tools;


	/** 
	 * 
	* 类名称：MemberPcController   
	* 创建人：刘耀耀  
	* 创建时间：2016年6月22日 
	 */
@Controller("memberPcController")
@RequestMapping(value = "/memberpc")
public class MemberPcController extends BaseController{
 
	 
	 
	@Resource(name="pc_advertService")
	private Pc_advertService pc_advertService;
	
	
	//----------------------------------修改密码如下步骤----------------------------------------------------
	
	/**
	 * 前往忘记密码页面1.
	 * memberpc/gfp2.do
	 * 
	 */
	@RequestMapping(value="/gfp2")
	public ModelAndView GoForgetPassword(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			 
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("memberpc/forgetpassword1");
		return mv;
 	}
	
	/**
	 * 忘记密码开始步数,验证登录账号,只能post提交
	 * memberpc/yZLN.do
	 */
 	@RequestMapping(value="/yZLN")
	@ResponseBody
	public Object YanZhengLoginNumber(String jphone_y,String txcd){
		Map<String,Object> map = new HashMap<String,Object>();
		String result="1";
		String message="";
		PageData pd = new PageData();
		try{
 			String sessiontxcd=(String) this.getRequest().getSession().getAttribute("validateCode");  //获取缓存验证码
 			boolean flag=(Tools.notEmpty(sessiontxcd) && sessiontxcd.equalsIgnoreCase(txcd));//判断验证码
			if(!flag){
				result="0";
				message="图形码错误";
			}else{
				pd.put("phone", jphone_y);
 				if(pcTongYongService.detailMemberById(pd) != null){
	  				this.getRequest().getSession().setAttribute("fgpphone", jphone_y);
	  				this.getRequest().getSession().setAttribute("jmfgpphone", jphone_y.substring(0, 4)+"****"+jphone_y.substring(7,11));
	  				this.getRequest().getSession().removeAttribute("validateCode");//移除图形验证码
 				}else{
	  				result="0";
	  				message="当前手机未注册，请先前往注册";
	  			}
			}
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		 map.put("result", result);
		 map.put("message", message);
		return map;
 	}
 	
 	/**
	 * 前往忘记密码页面2.必须是post请求提交
	 * memberpc/gFP2.do
	 * 
	 */
	@RequestMapping(value="/gFP2")
	public ModelAndView GoForgetPassword2(){
		ModelAndView mv = this.getModelAndView();
 		try{
    		this.getRequest().getSession().setAttribute("password_token", MD5.md5("$%.qwer"+String.valueOf(new Date().getTime())));
   			this.getRequest().getSession().setMaxInactiveInterval(10*60);//代表也是10分钟
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		mv.setViewName("memberpc/forgetpassword2");
		return mv;
 	}
	
	
	/**
	 * 忘记密码获取验证码--只能post提交
	 * memberpc//gFpMc.do?
	 * 
	 * phone
 	 */
 	@RequestMapping(value="/gFpMc")
	@ResponseBody
	public String GetForgerPasswordMessageCode(String password_token){
 		String result="1";
 		String code=StringUtil.getSixRandomNum();
		try{
 			String fgpphone=(String) this.getRequest().getSession().getAttribute("fgpphone");
  			String time_token=(String) this.getRequest().getSession().getAttribute("password_token");
   			if(time_token.equals(password_token)){//token一致则是在同一个页面
  				 //发送验证码
  				SmsUtil.sendXgCode(fgpphone, code);
  				 try {
  		  			PageData _pd=new PageData();
  		  			_pd.put("phone", fgpphone);
  		  			_pd.put("code", code);
  		  			_pd.put("content", "pc忘记密码获取验证码");
  		  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
  		  			_pd=null;
  				} catch (Exception e) {
  					// TODO: handle exception
  				}
  			    //将验证码放到session里
  	 		    SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_MEMBER_FORGETPASSWORDCODE , code);
  			}else{
   				result="0";
   			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		return result;
 	}
 	
 	
 	/**
	 * 验证短信验证码
	 * memberpc/yZmC.do
 	 */
 	@RequestMapping(value="/yZmC")
	@ResponseBody
	public String YanZhengMessageCode(String mscode){
 		String result="1";
  		try{
 			String sessioncode= (String) SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_MEMBER_FORGETPASSWORDCODE);
			if(!mscode.equals(sessioncode)){
				result="0";
			}else{
				SecurityUtils.getSubject().getSession().removeAttribute(Const.SESSION_MEMBER_FORGETPASSWORDCODE);//移除短信验证码
  			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
  		return result;
 	}
 	
 	/**
	 * 前往修改密码
	 * memberpc/guP3.do
	 * 
	 */
	@RequestMapping(value="/guP3")
	public ModelAndView GoUpdatePassword3(){
		ModelAndView mv = this.getModelAndView();
 		try{
 			
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		mv.setViewName("memberpc/forgetpassword3");
		return mv;
 	}
 	
 	
 	/**
	 * 确认修改密码,----只能post提交
	 * memberpc/uMp.do
 	 */
 	@RequestMapping(value="/uMp")
 	public ModelAndView UpdateMemberPassword(String newpassword){
 		ModelAndView mv = this.getModelAndView();
  		PageData pd=new PageData();
 		try{
 			if(this.getRequest().getSession().getAttribute("fgpphone") == null){
 				mv.setViewName("memberpc/forgetpassword1");
 			}else{
 				pd.put("phone", (String) this.getRequest().getSession().getAttribute("fgpphone"));
  	  			pd.put("member_id",  pcTongYongService.detailMemberById(pd).getString("member_id"));
  	  			pd.put("password", MD5.md5(newpassword));
 	 			pcTongYongService.editMember(pd);
 				this.getRequest().getSession().removeAttribute("fgpphone");//移除当前账号绑定的手机号码
 				mv.setViewName("memberpc/forgetpassword4");
 			}
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
  		return mv;
 	}
 	
 	
  //----------------------------------修改密码如上步骤----------------------------------------------------
 	
 	
 	
 	
 	
 	
	
 	
 
	
	/**
	 * 到会员注册页面
	 */
	@RequestMapping(value="/goMemberRegister")
	public ModelAndView goMemberRegister(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("pc_type", "3");
			List<PageData> pcadvert=pc_advertService.listAll(pd);
			if(pcadvert.size() > 0){
				mv.addObject("pcadvert",pcadvert.get(0));
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("memberpc/business_register");
		return mv;
		
	}

//	/**
//	 * 到买家首页2
//	 */
//	@RequestMapping(value="/goMemberTwo")
//	public ModelAndView goMemberTwo(){
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.setViewName("memberpc/business_Buyers2");
//		return mv;
//		
//	}
//	
//	/**
//	 * 到买家首页3
//	 */
//	@RequestMapping(value="/goMemberThree")
//	public ModelAndView goMemberThree(){
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.setViewName("memberpc/business_Buyers3");
//		return mv;
//		
//	}
//	/**
//	 * 到商品管理
//	 */
//	@RequestMapping(value="/goOrder")
//	public ModelAndView goOrder(){
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.setViewName("memberpc/business_Buyers4");
//		return mv;
//		
//	}
//	/**
//	 * 到结算
//	 */
//	@RequestMapping(value="/goClearing")
//	public ModelAndView goClearing(){
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.setViewName("memberpc/business_Buyers5");
//		return mv;
//		
////	}
//	
//	/**
//	 * 到结算
//	 */
//	@RequestMapping(value="/goPersonal")
//	public ModelAndView goPersonal(){
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.setViewName("memberpc/business_Buyers6");
//		return mv;
//		
//	}
//	
//	/**
//	 * 到完成结算
//	 */
//	@RequestMapping(value="/goFinish")
//	public ModelAndView goFinish(){
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.setViewName("memberpc/business_Buyers7");
//		return mv;
//		
//	}
//	
	
//	/**
//	 * 判断会员是否已经登录
//	 */
//	@RequestMapping(value="/isMember")
//	@ResponseBody
//	public Object isMember(){
// 		Map<String,Object> map = new HashMap<String,Object>();
//    	String result="0";
//  		String message="当前账号不存在";
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			String phone=pd.getString("phone");
//			if(Const.xzloginnumber.contains(phone)){
//				map.put("open_txcode", "1");
// 			}else{
// 				map.put("open_txcode", "0");
// 			}
//       	}catch(Exception e){
//			logger.error(e.toString(), e);
//			result="0";
//			message="系统错误";
//		}
//		map.put("result", result);
//		map.put("message", message);
// 		return map;
//	}
//	
	
	
	/**
	 * 到会员登录页面
	 */
	@RequestMapping(value="/goMemberLogin")
	public ModelAndView goMemberLogin(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("pc_type", "2");
			List<PageData> pcadvert=pc_advertService.listAll(pd);
			if(pcadvert.size() > 0 ){
				mv.addObject("pcadvert",pcadvert.get(0));
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("memberpc/business_signin");
		return mv;
 	}
	
	
	
	
 	/**
	 * 登录验证手机号和密码
	 * memberpc/pcLogin.do
	 * 
	 * phone 登录账号
	 * loginpassword 登录密码
	 * code 图形验证码
	 * 
	 */
	@RequestMapping(value="/pcLogin")
	@ResponseBody
	public Object pcLogin(HttpServletRequest request){
 		Map<String,Object> map = new HashMap<String,Object>();
  		Session session=SecurityUtils.getSubject().getSession();
    	String result="1";
 		String message="登录成功，正在跳转...";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String phone = pd.getString("phone");
			String loginpassword = pd.getString("loginpassword");
			String code = pd.getString("code");
			String validate = (String) request.getSession().getAttribute("validateCode");  //获取缓存验证码
			boolean flag=(Tools.notEmpty(validate) && validate.equalsIgnoreCase(code));//判断验证码
			//判断点击次数
			int number=0;
			if(Const.xzloginnumber.contains(phone)){
				if(flag){
					result="1";
 				}else{
					result="0";
					message="图形码错误";
					number=Const.clicklogin_number.get(phone);
				}
 			}
  			if(result.equals("1")){ 
  						pd.put("password", MD5.md5(loginpassword)); 
 						if(pcTongYongService.detailMemberById(pd) != null){
							map.put("message", "登录成功");
 							session.setAttribute("jylogin_id", pcTongYongService.detailMemberById(pd).getString("member_id"));
							request.getSession().setAttribute("memberpd", pcTongYongService.detailMemberById(pd));
							//清除当前账号的登录信息
							TongYong.clearLoginNumber(phone);
							//移除验证码
							request.getSession().removeAttribute("validateCode");
						}else{
							result="0";
  							request.getSession().setAttribute("jylogin_id", "");
  							message="用户名/密码错误";
  							
   							//更新登录次数
    						if(Const.clicklogin_number.get(phone) == null){
 								number=1;
  			 				}else{
  			 					number=Const.clicklogin_number.get(phone)+1;
  							} 
 							Const.clicklogin_number.put(phone, number);
 							if(number == Const.limit_loginerrornumber){
 								Const.xzloginnumber.add(phone);
 								map.put("changetxcode", "1");
 							}
						}
						request.getSession().removeAttribute("validateCode");
			} 
  			//显示不显示图形码
 			if(number >= Const.limit_loginerrornumber){
 					map.put("open_txcode", "1");
			}else{
					map.put("open_txcode", "0");
			}
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
 		return map;
	}
	
//	@Resource(name="appCity_fileService")
//	private AppCity_fileService appCity_fileService;
//	
//
//	
//	/**
//	 * （获取省市区）
//	 */
//	@RequestMapping(value="/tiaozhuan")
//	public ModelAndView tiaozhuan(){
//		//logBefore(logger, "（获取省市区）");
//		ModelAndView mv = this.getModelAndView();
//		mv.setViewName("memberpc/tiaozhuan");
//		return mv;
//	}
	
//	/**
//	 * 登录成功后跳转到买家首页
//	 */
//	@RequestMapping(value="/goMemberOne")
//	public ModelAndView goMemberOne(){
//		//logBefore(logger, "登录成功跳转到商家中心页面");
//		ModelAndView mv = this.getModelAndView();
// 		HttpServletRequest request=this.getRequest();
//		HttpSession pcsession=request.getSession();
// 		try { 
//   			pcsession.setAttribute("shopnumber", "0");
//  			if(pcsession.getAttribute("jylogin_id") == null || pcsession.getAttribute("jylogin_id").equals("")){
//  				pcsession.setAttribute("jylogin_id", "");
//  			}
//  			pcsession.setAttribute("shopnumber", "0");
//  			mv.addObject("flag", "0");
//    	} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}
// 		mv.setViewName("memberpc/business_Buyers0");
//		return mv;
// 	}
//	
//	/**
//	 * 测试获取经纬度
//	 * @param out
//	 */
//	@RequestMapping(value="/getjingweidu")
//	public void getjingweidu(){
//		//logBefore(logger, "测试获取经纬度");
//		PageData pd = new PageData();
//		try{  
//		        Socket s = new Socket("api.map.baidu.com",80);    
//		        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));    
//		        OutputStream  out= s.getOutputStream();   
//		        //https://api.map.baidu.com/highacciploc/v1?qcip=&qterm=pc&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&coord=bd09ll
//		        StringBuffer sb = new StringBuffer("GET /highacciploc/v1?qcip=&qterm=pc&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&coord=bd09ll HTTP/1.1\r\n");    
//		        sb.append("User-Agent: Java/1.6.0_20\r\n");    
//		        sb.append("Host: api.map.baidu.com:80\r\n");    
//		        sb.append("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n");    
//		        sb.append("Connection: Close\r\n");    
//		        sb.append("\r\n");    
//		        out.write(sb.toString().getBytes());    
//		        String jsonText ="";  
//		        String tmp = "";    
//		        while((tmp = br.readLine())!=null){    
////		            //System.out.println(tmp); 
//		            if(tmp.contains("location")){
//		            	jsonText=tmp; 
//		            }
// 		        }    
//  		        //System.out.println(jsonText); 
//  		        JSONObject json = new JSONObject(jsonText);
//  		        int resultstatus=(int) ((JSONObject) json.get("result")).get("error");
//		        if(resultstatus == 161){
//			    	  double lng=(double) ((JSONObject) json.get("content")).getJSONObject("location").get("lng");
//				      double lat=(double) ((JSONObject) json.get("content")).getJSONObject("location").get("lat");
//				      //System.out.println("经度："+lng);  
//					  //System.out.println("维度："+lat);  
//					  pd.put("lng", lng+"");
//					  pd.put("lat", lat+"");
//		        }
//		        out.close();    
//		        br.close();    
//        } catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//	}
//	
	
	/**
	 * 用户注销
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/loginOut")
	public ModelAndView loginOut(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
			pd = this.getPageData();
			this.getRequest().getSession().removeAttribute("memberpd");
 			SecurityUtils.getSubject().getSession().removeAttribute("jylogin_id");
			SecurityUtils.getSubject().logout();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
  		mv.setViewName("redirect:goMemberLogin.do");
		mv.addObject("pd",pd);
		return mv;
	}
 	
	
 	
	/**
	 *注册界面的时候 获取推荐人的手机号并判断是否注册
	 *memberpc/TuijianPhone.do
	 *
	 *phone 当前注册人的手机号码
	 *
	 */
	@RequestMapping(value="/TuijianPhone")
	@ResponseBody
	public Object TuijianPhone(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result="01";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
  			if(pcTongYongService.detailMemberById(pd) != null){
				result="00";
			}else{
  				List<PageData> varList=pcTongYongService.listAllTuiJian(pd);
				for(PageData e : varList){
					 PageData mspd=new PageData();
					 if(e.getString("type").equals("1")){
						 mspd.put("store_id", e.getString("id"));
 						 e.put("phone", storepc_fileService.findById(mspd).getString("store_name"));
					 }else{
						 mspd.put("member_id", e.getString("id"));
 						 e.put("phone", pcTongYongService.detailMemberById(mspd).getString("phone"));
					 }
					 mspd=null;
				 }
				 map.put("varList", varList);
				 varList=null;
			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		 map.put("result", result);
		return map;
 	}
	
	
	@Resource(name="appFriendService")
	private AppFriendService appFriendService;
	
	/**
	 * 注册会员
	 * memberpc/register.do
	 * 
	 * phonecode 手机验证码
	 * password  密码
	 * phone     推进人ID@推荐人类型
	 * be_phone 注册手机号码
	 */
	@RequestMapping(value="/register")
	@ResponseBody
	public Object register(HttpServletRequest request) throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="注册成功";
 		PageData pd = new PageData();
 		String member_id="";
		try {
			pd = this.getPageData();
			String phonecode=pd.getString("phonecode");//手机验证码
			String sessioncode=(String) SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_MEMBER_ZHUCECODE);
	 		if(!phonecode.equals(sessioncode)){
	 			map.put("result", "0");
	 			map.put("message", "验证码错误");
	 			map.put("data", member_id);
	 	 		return map;
	 		}
   	 		String[] recom=pd.getString("phone").split("@");//电话@类型@id
   	 		String phone=pd.getString("be_phone");//手机号码
  	 		pd.put("phone", phone);
	 		pd.put("name", phone.substring(0, 3)+"****"+phone.substring(7, 11));
	 		pd.put("zhuce_shebei", "4");
 	 		pd=TongYong.saveMember(pd,recom[2], recom[1]);
  			if(pd.getString("flag").equals("false")){
 				map.put("result", "0");
 				map.put("message", pd.getString("message"));
 				map.put("data", "");
  		 		return map;
 			}
  			member_id=pd.getString("member_id");
  			//移除注册的验证码
			SecurityUtils.getSubject().getSession().removeAttribute(Const.SESSION_MEMBER_ZHUCECODE);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
			message="系统错误";
			result="0";
		}
 		map.put("result", result);
		map.put("message", message);
		map.put("data", member_id);
 		return map;
	}
	
	/**
	 * 获取验证码，注册会员
	 * memberpc/getZhuCeCode.do
	 * 
	 * phone 注册手机号码
	 * tuxingcode 图形验证码
	 * 
	 */
	@RequestMapping(value="/getZhuCeCode")
	@ResponseBody
	public Object getZhuCeCode(HttpServletRequest request) throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "01";
		String message="验证码已发往至你手机，请注意查收";
		PageData pd = new PageData();
 		String code=StringUtil.getSixRandomNum();
		try {
				pd = this.getPageData();
				String phone=pd.getString("phone");//注册手机号
	   			String tuxingcode=pd.getString("tuxingcode");//图形验证码
	   			String validate = (String)request.getSession().getAttribute("validateCode");  //获取缓存验证码
				//验证是否可以发送验证码---
	  			PageData issend=TongYong.Okmessage(phone,"1");
	  			if(issend.get("result").equals("0")){
	  				map.put("result", "0");
			 		map.put("message",issend.getString("message"));
			 		map.put("data", "");
			 		return map;
	  			}
   	 		    if(pcTongYongService.detailMemberById(pd) == null ){
 				    //进行图形验证验证toLowerCase()不区分大小写
					if(tuxingcode == null ||!tuxingcode.equalsIgnoreCase(validate)){
							//获取电话集合--移除当前的电话
			 			for(int i=0;i<Const.xzphone.size();i++){  
				            if(Const.xzphone.get(i).equals(phone)){  
				            	Const.xzphone.remove(i);  
				            }  
				        }
						map.put("result", "0");
				 		map.put("message", "图形验证码不能为空/错误");
				 		map.put("data", "");
				  		return map;
					}else{
						SecurityUtils.getSubject().getSession().removeAttribute("validateCode");
 					}
					//图形码验证结束
				    SmsUtil.sendZcCode(phone, code);
 				    //将验证码放到session里
		   		    SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_MEMBER_ZHUCECODE , code);
			   
			 	}else{
			 		map.put("result", "0");
			 		map.put("message","当前会员已注册，请前往登录");
			 		map.put("data", "");
			 		return map;
			 	}
  		} catch (Exception e) {
			// TODO: handle exception
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	/*
	 * 获取IP
	 */
	public static String getIp(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
 		}
 		 //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		 if(ipAddress!=null && ipAddress.length()>15){
		 //"***.***.***.***".length() = 15
		 if(ipAddress.indexOf(",")>0){
		 ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
		 }
		 }
		return ipAddress;
	}
	
	/**
	 * 修改会员信息
	 * memberpc/editMemberInfo.do
	 * 
	 * name   姓名
	 * sex    性别
 	 * email  邮箱
	 * birthday  生日
	 */
	@RequestMapping(value="/editMemberInfo")
	@ResponseBody
	public Object EditMemberInfo(){
 		String result = "1";
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{
			pd=this.getPageData();
			Session session=SecurityUtils.getSubject().getSession();
	  		String member_id=(String)session.getAttribute("jylogin_id");
			if(member_id == null || member_id.equals("")){
				result="0";
			}else{
				pd.put("member_id", member_id);
 				pcTongYongService.editMember(pd);
			}
 		} catch(Exception e){
			result="0";
			logger.error(e.toString(), e);
		}
 		map.put("result", result);
		return map;
 	}
	
	/**
	 * 修改会员头像
	 * memberpc/editMemberImager_url.do
	 * 
	 * imgbase64    头像的base64位为空则不处理
 	 */
	@RequestMapping(value="/editMemberImager_url")
	@ResponseBody
	public Object EditMemberImager_url(String  imgbase64){
 		String result = "1";
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd=new PageData();
 		try{
 			Session session=SecurityUtils.getSubject().getSession();
	  		String member_id=(String)session.getAttribute("jylogin_id");
			if(member_id == null || member_id.equals("")){
				result="0";
			}else{
  				//处理图片base64===================================
				imgbase64=imgbase64.substring(imgbase64.indexOf(",")+1, imgbase64.length());
				System.out.println(imgbase64);
				String path="/userFile/"+member_id+".png";
				String imgFilePath=AppUtil.getuploadRootUrl()+path;
				ImageBase64Test.GenerateImage(imgbase64, imgFilePath);
  				//=================================================
				pd.put("member_id", member_id);
				pd.put("image_url", AppUtil.getuploadRootUrlIp()+path);
				pcTongYongService.editMember(pd);
				map.put("url", AppUtil.getuploadRootUrlIp()+path);
 			}
 		} catch(Exception e){
			result="0";
			logger.error(e.toString(), e);
		}
 		map.put("result", result);
		return map;
 	}
	
	/**
	 * 本会员信息
	 * memberpc/memberInfoList.do
	 * 
	 * city_name 城市
	 * area_name 区
	 */
	@RequestMapping(value="/memberInfoList")
	public ModelAndView memberInfoList(){
		ModelAndView mv = this.getModelAndView();
		Session session=SecurityUtils.getSubject().getSession();
  		String member_id=(String)session.getAttribute("jylogin_id");
		PageData pd=new PageData();
 		try{
			pd = this.getPageData();
			//获取所有的市
    		List<PageData> cityList=pcTongYongService.listAllCity(pd);
   			mv.addObject("cityList",cityList);
   			//获取城市档案ID以及城市营销参数
  			PageData citypd=pcTongYongService.getCityMarketingForPCD(pd);
  			if(citypd != null ){
  				//获取当前地级市下的所有开通的区域
	  	   		List<PageData> areaList=pcTongYongService.listAllArea(citypd);
   	   			mv.addObject("areaList",areaList);
   	   			areaList=null;
   	   			//获得一级分类
  	   			citypd.put("sort_parent_id", "0");
  	   			citypd.put("sort_type", "1");
  	   			List<PageData> firstList=pcTongYongService.listAllCitySort(citypd);
   	   			citypd.remove("sort_parent_id");
  	   			citypd.remove("sort_type");
  	   			PageData e=null;
  	   			PageData twopd=new PageData();
  	   			int n=firstList.size();
  	   			for (int i = 0; i < n; i++) {
					e=firstList.get(i);
		 			//获得二级分类
					twopd.put("city_file_id", e.getString("city_file_id"));
					twopd.put("sort_parent_id", e.getString("city_file_sort_id"));
					twopd.put("sort_type", "2");
					List<PageData> twoList=pcTongYongService.listAllCitySort(twopd);
 	  				e.put("twoList", twoList);
 	  				twoList=null;
  	   			}
  	   			mv.addObject("firstList",firstList);
  			}
			if(member_id == null || member_id.equals("")){
				mv.setViewName("redirect:goMemberLogin.do");
			}else{
				pd.put("member_id", member_id);
 				mv.addObject("mpd", pcTongYongService.detailMemberById(pd));
				mv.setViewName("memberpc/business_Buyers6");
			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		pd.remove("member_id");
 		mv.addObject("pd", pd); 
 		return mv;
 	}
	
	@Resource(name="storeService")
	private StoreService storeService;
	
	
	@Resource(name="city_fileService")
	private City_fileService city_fileService;
	@Resource(name="storeManageService")
	private StoreManageService storeManageService;
	
	@Resource(name="appGoodsService")
	private AppGoodsService appGoodsService;
	
	
	@Resource(name="appStore_redpacketsService")
	private AppStore_redpacketsService store_redpacketsService;
 
	@Resource(name="appMember_redpacketsService")
	private AppMember_redpacketsService appMember_redpacketsService;
	@Resource(name = "tablerNumberService")
	private TablerNumberService tablerNumberService;
	@Resource(name="appStorepc_marketingService")
	private AppStorepc_marketingService appStorepc_marketingService;
	
//	/**
//	 * 重定向商家详情
//	 */
//	@RequestMapping(value="/tzall")
//	public ModelAndView tzall(){
//		ModelAndView mv = this.getModelAndView();
//  		try{
//   			mv.setViewName("forward:storeInfoList.do");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
// 		return mv;
// 	}
	
	@Resource(name="pcTongYongService")
	private PcTongYongService pcTongYongService;

	@Resource(name="storepc_fileService")
	private Storepc_fileService storepc_fileService;
	
	/**
	 * 商家详情信息
	 * memberpc/storeInfoList.do? 
	 * 
	 * sk  商家ID的加密
	 * city_name 城市
	 * area_name 区
	 * 
	 */
	@RequestMapping(value="/storeInfoList")
	public ModelAndView storeInfoList(Page page){
		ModelAndView mv = this.getModelAndView();
  		Session session=SecurityUtils.getSubject().getSession();
  		String member_id=(String)session.getAttribute("jylogin_id");
		PageData pd = new PageData();
  		try{
			pd = this.getPageData();
			//获取所有的市
    		List<PageData> cityList=pcTongYongService.listAllCity(pd);
   			mv.addObject("cityList",cityList);
   			//获取城市档案ID以及城市营销参数
  			PageData citypd=pcTongYongService.getCityMarketingForPCD(pd);
  			if(citypd != null ){
  				//获取当前地级市下的所有开通的区域
	  	   		List<PageData> areaList=pcTongYongService.listAllArea(citypd);
   	   			mv.addObject("areaList",areaList);
   	   			areaList=null;
   	   			//获得一级分类
  	   			citypd.put("sort_parent_id", "0");
  	   			citypd.put("sort_type", "1");
  	   			List<PageData> firstList=pcTongYongService.listAllCitySort(citypd);
   	   			citypd.remove("sort_parent_id");
  	   			citypd.remove("sort_type");
  	   			PageData e=null;
  	   			PageData twopd=new PageData();
  	   			int n=firstList.size();
  	   			for (int i = 0; i < n; i++) {
					e=firstList.get(i);
		 			//获得二级分类
					twopd.put("city_file_id", e.getString("city_file_id"));
					twopd.put("sort_parent_id", e.getString("city_file_sort_id"));
					twopd.put("sort_type", "2");
					List<PageData> twoList=pcTongYongService.listAllCitySort(twopd);
 	  				e.put("twoList", twoList);
 	  				twoList=null;
  	   			}
  	   			mv.addObject("firstList",firstList);
  			}
			String sk=pd.getString("sk");
			String store_id=EbotongSecurity.ebotongDecrypto(sk.substring(4, sk.length()-1));
			pd.put("member_id", member_id);
			pd.put("store_id", store_id);
			//判断是否开通类别积分购买的权限
			PageData storejfpd=pcTongYongService.getJfByIdByStore(pd);
			mv.addObject("storejfpd", storejfpd);
   			//获取商家的大小图片
 			List<PageData> imageList=pcTongYongService.findImageByStore(pd);
			if(imageList.size() != 0){
				PageData imgpd=new PageData();
				imgpd=imageList.get(0);
				String[] address=imgpd.getString("address").split(",");
				List<String> smallList=new ArrayList<String>();
				for(int i=0 ; i<address.length ;i++){
 					if(!address[i].equals("img/base_tp.png") && !address[i].equals("")){
 						smallList.add(address[i]);
					}
				}
				mv.addObject("smallList", smallList);
 				//获取商家详情图片
				List<PageData> bigList=new ArrayList<PageData>();
				String address1=imgpd.getString("address1");
				if(address1 != null && !address1.equals("")){
					String[] addre1=address1.split(",");
					for(int i=0 ; i<addre1.length ;i++){
						PageData adpd=new PageData();
						String[] str=addre1[i].split("@");
  						adpd.put("image_url", str[0]);
						if(str.length == 2){
							adpd.put("text", str[1].trim());
						}else{
							adpd.put("text", "");
						}
						bigList.add(adpd);
						adpd=null;
  					}
					for (int j = 0; j < bigList.size(); j++) {
						if(bigList.get(j).getString("image_url").equals("img/base_tp.png") || bigList.get(j).getString("image_url").equals("")){
							String text=bigList.get(j).getString("text");
							if(j>0 && !text.equals("")){
								PageData jpd=bigList.get(j-1);
								String text2=jpd.getString("text").trim();
								text2=text2+"<br>"+text;
								jpd.put("text", text2);
  								//替换集合的指定
								bigList.set(j-1, jpd);
								jpd=null;
							}
							bigList.remove(j);
							j=j-1;
						}
					}
					mv.addObject("bigList", bigList);
 				}
			}
 			//获取商家评价
			page.setPd(pd);
			List<PageData> commentList = pcTongYongService.datalistPageByComment(page);
			for(PageData e : commentList){
				if(e.getString("name") != null && e.getString("name").length() == 11){
					e.put("name", e.getString("name").substring(0, 3)+"****"+e.getString("name").substring(7, 11));
				}
				//图片
				List<PageData> imgList=new ArrayList<PageData>();
				String image_url=e.getString("commentimage_url");
				String[] array=image_url.split(",");
				for (int j = 0; j < array.length; j++) {
						PageData e1=new PageData();
						if(!array[j].trim().equals("")){
							e1.put("icon_url", array[j]);
							imgList.add(e1);
						}
 						e1=null;
				}
				e.put("imgList", imgList);
				imgList=null;
			}
			mv.addObject("commentList", commentList);
			commentList=null;
 			//获取营销规则
			List<PageData> marketlist=pcTongYongService.listAllMarketing(pd);
   			mv.addObject("marketlist", marketlist); 
    		//获取商家信息
 			mv.addObject("storepd", storepc_fileService.findById(pd));
 			PageData  collectpd=pcTongYongService.getCollectionById(pd);
			if(collectpd == null){
				mv.addObject("iscollect", "0");
			}else{
				mv.addObject("iscollect", "1");
			}
    	} catch(Exception e){
			logger.error(e.toString(), e);
 		}
 		pd.remove("member_id");
 		mv.addObject("pd", pd); 
  		mv.setViewName("memberpc/business_Buyers3");
		return mv;
 	}
	
	/**
  	 *	  收藏/取消收藏
  	 *	memberpc/iscolloctByMS.do
  	 *
  	 *  sk 加密的商家ID
  	 *
	 */
	@RequestMapping(value="/iscolloctByMS")
	@ResponseBody
	public Object iscolloctByMS() {
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="收藏成功";
		PageData pd = new PageData();
 		try {
			pd=this.getPageData();	
			Session session=SecurityUtils.getSubject().getSession();
	  		String member_id=(String)session.getAttribute("jylogin_id");
			String sk=pd.getString("sk");
			String store_id=EbotongSecurity.ebotongDecrypto(sk.substring(4, sk.length()-1));
			pd.put("member_id", member_id);
			pd.put("store_id", store_id);
			//判断是否有当前会员
 			if(pcTongYongService.detailMemberById(pd) == null){
				map.put("result", "0");
				map.put("message", "请前往登陆");
				map.put("data", "");
				return map;
			}
  			if(pcTongYongService.getCollectionById(pd) != null ){
  					pcTongYongService.deleteCollect(pd);
  					pcTongYongService.editCollectNumber(pd);
  					message="取消收藏成功";
  					map.put("data", "0");
 			}else{
 					pd.put("collect_id", BaseController.get11UID());
 					pcTongYongService.saveCollect(pd);
 					pcTongYongService.editCollectNumber(pd);
	 				message="收藏成功";
	 				map.put("data", "1");
			}
 		} catch (Exception e) {
			result="0";
 			message="系统失败";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	
	
//	/**
//	 * 商品
//	 */
//	@RequestMapping(value="/goodsList")
//	@ResponseBody
//	public List<PageData> goodsList(Page page){
//		PageData pd = new PageData();
//		DecimalFormat   df   = new DecimalFormat("######0.00"); 
//		List<PageData> allredList =new ArrayList<PageData>();//用来存储营销List
//		List<PageData> goodsList  =null;
//		try{
//			pd = this.getPageData();
//			String sort=pd.getString("sort");
//			String store_id=pd.getString("store_id");
//			String goods_category_id=pd.getString("goods_category_id");
//			if(sort.equals("1")){
//						//获取当前用户的所有红包
//						List<PageData>	memredList =appMember_redpacketsService.listAllById(pd);
//						//列出红包列表--除去已拥有的红包
//						pd.put("redpackage_status", Const.putong_redpackage_status);
//						pd.put("redpackage_type", Const.redpackage_typeone);
//						List<PageData>	redList = store_redpacketsService.list(pd);	
//						for(int i=0 ; i<redList.size() ;i++ ){
//							PageData e=new PageData();
//							e=redList.get(i);
//							 String store_redpackets_id=e.getString("store_redpackets_id");
//							 for(int j=0;j< memredList.size() ; j++ ){
//								 PageData f =new PageData();
//								 f=memredList.get(j);
//								 if(store_redpackets_id.equals(f.getString("store_redpackets_id"))){
//									 redList.remove(i);
//									 i=i-1;
//									 break;
//								 }
//							 }
//							 e=null;
//						}
//						if(redList.size()==0){
//							//System.out.println("当前商家红包不存在");
//						}else{
//							 for(PageData e : redList){
//								 		PageData redpd=new PageData();
//									    String choice_type=e.getString("choice_type");
//									    String redpackage_type=e.getString("redpackage_type");
//									    double money=Double.parseDouble(e.getString("money"));//总金钱
//										double overget_money=Double.parseDouble(e.getString("overget_money"));//已领金钱
//										int redpackage_number=Integer.parseInt(e.getString("redpackage_number"));//总红包
//										int overget_number=Integer.parseInt(e.getString("overget_number"));//已领红包个数
//										if(redpackage_number-overget_number==1){
//													redpd.put("money", df.format(money-overget_money ));
//													redpd.put("redpackage_content", e.getString("name"));
//													redpd.put("store_redpackets_id", e.getString("store_redpackets_id"));
//													redpd.put("redpackage_type", e.getString("type"));
//													redpd.put("type",redpackage_type);
//													allredList.add(redpd);
//										}else{
//											if(redpackage_type.equals("1")){
//													if(choice_type.equals(Const.redtwo_type)){//平均
//															redpd.put("money", df.format(money/redpackage_number));
//															redpd.put("redpackage_content", e.getString("name"));
//															redpd.put("store_redpackets_id", e.getString("store_redpackets_id"));
//															redpd.put("redpackage_type", e.getString("type"));
//															redpd.put("type",redpackage_type);
//															allredList.add(redpd);
//														}else if(choice_type.equals(Const.redone_type)){//随机
//															double lessmoney=money-overget_money;
//															int lessnumber=redpackage_number-overget_number;
//															//获取平均值
//															double pjmoney=lessmoney/lessnumber;
//															double minpjmoney=pjmoney/2;
//															double maxpjmoney=pjmoney/2+pjmoney;
//															double suijimoney=StringUtil.getSuiJi(minpjmoney, maxpjmoney);
//															redpd.put("money", df.format(suijimoney));
//															redpd.put("redpackage_content", e.getString("name"));
//															redpd.put("store_redpackets_id", e.getString("store_redpackets_id"));
//															redpd.put("redpackage_type", e.getString("type"));
//															redpd.put("type",redpackage_type);
//															allredList.add(redpd);
//													}
//											}else if(redpackage_type.equals("2")){
//												//折扣红包
//												redpd.put("money", money);
//												redpd.put("redpackage_content", e.getString("name"));
//												redpd.put("store_redpackets_id", e.getString("store_redpackets_id"));
//												redpd.put("redpackage_type", e.getString("type"));
//												redpd.put("type",redpackage_type);
//												allredList.add(redpd);
//											}	
// 									}
//									redpd=null;
//								 }
//			  			}
//					goodsList=allredList;
//			}else if(sort.equals("2")){
//				goodsList = appGoodsService.getYingXiaoGoods(pd); 
//			}else if(sort.equals("3")){
//				goodsList = appGoodsService.getRenQiGoods(pd); 
//			}else{
//				goodsList = goodsService.listgoods(pd);
//			}
// 		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return goodsList;
//	}
//	
	

}
