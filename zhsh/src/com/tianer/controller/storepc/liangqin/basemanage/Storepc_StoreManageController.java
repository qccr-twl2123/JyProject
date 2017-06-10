/**
 * 
 */
package com.tianer.controller.storepc.liangqin.basemanage;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.JSONObject;
import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.entity.storepc.liangqin.Store;
import com.tianer.entity.zhihui.Qx;
import com.tianer.entity.zhihui.StoreRole;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
import com.tianer.util.Const;
import com.tianer.util.MD5;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;

/**
 * 类名称: Storepc_StoreManageController 
 * 类描述: TODO
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-24 下午2:13:50	
 * 版本号: v1.0
 */
@Controller
@RequestMapping(value="/storepc_StoreManageController")
public class Storepc_StoreManageController extends BaseController {
	
	@Resource(name="storeManageService")
	private StoreManageService storeManageService;
	
	
	/**
	 * 
	* 方法名称:：editStore 
	* 方法描述：修改商家信息
	* 创建人：魏汉文
	* 创建时间：2016年8月17日 下午6:13:42
	 */
	@RequestMapping(value="/editStore")
	public ModelAndView editStore()throws Exception{
		ModelAndView modelAndView = this.getModelAndView();
 		PageData pd = new PageData();
  		try {
 			pd=this.getPageData();
 			String jichushezhi=pd.getString("jichushezhi");
 			String store_id=pd.getString("store_id");
 			if(store_id != null && !store_id.equals("")){
 				storeManageService.editStoreFile(pd);
 				if(jichushezhi != null && jichushezhi.equals("11000000000")){
 					modelAndView.setViewName("redirect:../storepc/goSheZhiOne.do?jichushezhi=11000000000&store_id="+pd.getString("store_id"));
 				}else{
 					modelAndView.setViewName("redirect:goInformation.do?store_id="+pd.getString("store_id"));
 				}
   			}else{
 				modelAndView.setViewName("redirect:goInformation.do");
 			}
 		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString());
		}
 		return modelAndView;
	}
	
	/**
	 * 去商家信息页面
	 * 刘耀耀
	 * 2016.07.09
	 */
	@RequestMapping(value="/goInformation")
	public ModelAndView goInformation(String id) throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd=new PageData();
			try {
				pd=this.getPageData();
				String jichushezhi=pd.getString("jichushezhi");
				this.getHC();
	  			if(pd.getString("store_id") != null && !pd.getString("store_id").equals("") ){
	  				pd=storeManageService.findStoreById(pd);
 	  			}
	  			if(jichushezhi != null && jichushezhi.equals("11000000000")){
	  				mv.setViewName("/storepc/shezhi_2");
	  			}else{
	  				mv.setViewName("/storepc/business_base");
	  			}
	  			pd.put("jichushezhi", jichushezhi);
 			} catch (Exception e) {
				// TODO: handle exception
			}
 			mv.addObject("pd", pd);
 		return mv;
	}
	
	
	
	
	

	
	/**
	 * 
	* 方法名称:：showSenior 
	* 方法描述：到高级信息
	* 创建人：魏汉文
	* 创建时间：2016年8月17日 下午6:14:45
	 */
	@RequestMapping(value="/showSenior")
	public ModelAndView showSenior(Page page) throws Exception{
		ModelAndView modelAndView = this.getModelAndView();
 		PageData pd= new PageData();
 		try {
 			pd=this.getPageData();
  	 		Subject currentUser = SecurityUtils.getSubject();  
 			Session session = currentUser.getSession();	
 			StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
 			if(slogin == null){
 				modelAndView.setViewName("redirect:../storepc/goLogin.do");
 			}else{
 				String store_id=slogin.getStore_id();
 				if(pd.getString("store_id") == null || pd.getString("store_id").equals("")){
 					pd.put("store_id",store_id );
 				}
  		 		PageData storepd = ServiceHelper.getAppStoreService().findById(pd);
  		 		PageData sb = storeManageService.showBankMessage(store_id);//银行卡信息
  		 		PageData sfs = storeManageService.showSortMessage(store_id);
  				modelAndView.addObject("sb", sb);
 				modelAndView.addObject("store", storepd);
 				modelAndView.addObject("sfs", sfs);
 				String jichushezhi=pd.getString("jichushezhi");
 				if(jichushezhi != null && jichushezhi.equals("11111111110")){
 					modelAndView.setViewName("/storepc/shezhi_10");
	  			}else{
	  				modelAndView.setViewName("/storepc/business_base3");
 	  			}
  			}
		} catch (Exception e) {
			// TODO: handle exception
		}
 		modelAndView.addObject("pd", pd);
  		return modelAndView;
	}
	
	/**
	 * 
	* 方法名称:：addRemind 
	* 方法描述：修改高级信息
	* 创建人：魏汉文
	* 创建时间：2016年8月17日 下午6:14:25
	 */
	@RequestMapping(value="/addRemind")
	public ModelAndView eidtRemind(Page page) throws Exception{
		ModelAndView modelAndView = this.getModelAndView();
		PageData pd= new PageData();
		pd=this.getPageData();
		try {
			String jichushezhi=pd.getString("jichushezhi");
			storeManageService.updateMessage(pd);
			if(jichushezhi == null ){
				jichushezhi="";
			}
			modelAndView.setViewName("redirect:showSenior.do?jichushezhi="+jichushezhi);
 		} catch (Exception e) {
				// TODO: handle exception
				//System.out.println(e.toString());
		}
 		return modelAndView;
	}
	

	
	/**
	 * 
	* 方法名称:：modPassword 
	* 方法描述：修改密码
	* 创建人：魏汉文
	* 创建时间：2016年8月17日 下午6:15:50
	 */
	@RequestMapping(value="/modPassword")
	@ResponseBody
	public Object modPassword(Page page) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
  		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
		PageData pd=new PageData();
		String result="1";
		String message="修改成功";
		try {
			pd=this.getPageData();
			String beforpassword =pd.getString("beforpassword");
			String newpassword =pd.getString("password");
			String login_type=slogin.getType();
			String login_id=slogin.getLogin_id();
			String password=slogin.getPassword();//登录密码
			if(!beforpassword.equals(password)){
				result="0";
				message="原密码有误"; 
			}else{
				if(login_type.equals("1")){//
					storeManageService.updateStore(pd);
				}else{
					pd.put("operator_password", MD5.md5(newpassword));
					pd.put("store_operator_id", login_id);
					storeManageService.updateOperator(pd);
				}
			}
 		} catch (Exception e) {
			// TODO: handle exception
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
  		return map;
	}
	/**
	 *
	* 方法名称:：showPassword 
	* 方法描述： 到修改密码
	* 创建人：魏汉文
	* 创建时间：2016年8月17日 下午6:16:42
	 */
	@RequestMapping(value="/showPassword")
	public ModelAndView showPassword(Page page) throws Exception{
		ModelAndView modelAndView = this.getModelAndView();
		Subject currentUser = SecurityUtils.getSubject();  
 		Session session = currentUser.getSession();	
 		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
 		try {
 			if(slogin != null){
  				String store_id=slogin.getStore_id();
 				Store store = storeManageService.findStoreById(store_id);
 				String password = store.getPassword();
 				modelAndView.addObject("password", password);
 				modelAndView.setViewName("/storepc/business_base4");
 			}
		} catch (Exception e) {
			// TODO: handle exception
		}
  		return modelAndView;
	}

	

	/**
	 * 去操作员汇页面
	 * 刘耀耀
	 * 2016.07.09
	 */
	@RequestMapping(value="/goOperator")
	public ModelAndView goOperator(String id) throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("/storepc/business_base5");
		return mv;
	}
	
	/**
	 * 去班商家图片页面
	 */
	@RequestMapping(value="/goImage")
	public ModelAndView goImage(String id) throws Exception{
		ModelAndView mv = this.getModelAndView();
		List<String> one=new ArrayList<String>();
		List<PageData> two=new ArrayList<PageData>();
		PageData pd=new PageData();
		try {
			    pd=this.getPageData();
			    String jichushezhi=pd.getString("jichushezhi");
			    String store_id=pd.getString("store_id");
				List<PageData> varList=storeManageService.findImage(pd);
				if(varList.size() != 0){
					pd=varList.get(0);
					if(pd.getString("address")  != null){
						String[] address=pd.getString("address").split(",");
						for(int i=0 ; i<5 ;i++){
							if(address.length-1 >= i){
								one.add(address[i]);
							}else{
								one.add("");
							}
		 				}
						mv.addObject("one", one);
					}
					if(pd.getString("address1")  != null){
						String[] address1=pd.getString("address1").split(",");
		 				for(int i=0 ; i<10 ;i++){
		 					PageData e=new PageData();
		  					if(address1.length-1 >= i){
		 						String[] address3=address1[i].split("@");
								if(address3.length == 2){
									e.put("twoimage", address3[0]);
									e.put("twotext", address3[1]);
								}else if(address3.length == 1){
									e.put("twoimage", address3[0]);
									e.put("twotext", "");
								}else{
									e.put("twoimage", "");
									e.put("twotext", "");
								}
							}else{
								e.put("twoimage","");
								e.put("twotext", "");
							}
		  					two.add(e);
						}
		 				mv.addObject("two", two);
					}
	 			}
 	  			if(jichushezhi != null && jichushezhi.equals("11100000000")){
	  				mv.setViewName("/storepc/shezhi_3");
	  			}else{
	  				mv.setViewName("/storepc/business_base2");
	  			}
 	  			pd.put("store_id", store_id);
 	  			pd.put("jichushezhi", jichushezhi);
  		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString());
		}
 		mv.addObject("pd", pd);
 		return mv;
	}
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	/**
	 * 上传图片
	* 方法名称:：addPic 
	* 方法描述：
	* 创建人：魏汉文
	* 创建时间：2016年8月6日 上午10:46:01
	 */
	@RequestMapping(value="/addPic")
	public ModelAndView addPic()throws Exception{
		ModelAndView modelAndView = this.getModelAndView();
		PageData pd=new PageData();
 		pd=this.getPageData();
 		try {
  			//缩略图
 			String[] addressall=pd.getString("address").split(",");
 			if(addressall.length >0 ){
 				PageData pctpd=new PageData();
 				pctpd.put("store_id", pd.getString("store_id"));
 			}
  			//详情图
 			String address1image=pd.getString("address1image");
 			String[] strimage=address1image.split(",");
 			int n= strimage.length;
 			//文本
  			String address1text=pd.getString("address1text");
  			String[] alltext=new String[10];
  			String[] strtext=address1text.split(",");
 			for (int i = 0; i <10; i++) {
				if(strtext.length-1 < i){
					alltext[i]="";
				}else{
					alltext[i]=strtext[i];
				}
			}
 			//聚合
 			String address1="";
 			for (int i = 0; i < n; i++) {
 				address1+=strimage[i]+"@"+alltext[i]+",";
			}
 			pd.put("address1", address1);
  			storeManageService.editStoreImage(pd);
  			String jichushezhi=pd.getString("jichushezhi");
  			if(jichushezhi != null && jichushezhi.equals("11100000000")){
  				modelAndView.setViewName("redirect:../storepc/goSheZhiOne.do?jichushezhi=11100000000&store_id="+pd.getString("store_id"));
  			}else{
  				modelAndView.setViewName("redirect:goImage.do?store_id="+pd.getString("store_id"));
  			}
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString());
		}
 		return modelAndView;
	}
	
	/**
	 * 上传图片
	 */
	@RequestMapping(value="/uploadStoreImage")
	@ResponseBody
	public Object uploadheadimage(ServletRequest request, ServletResponse response) throws Exception{
		//logBefore(logger, "上传图片");
		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="上传成功";
		try {
   			// 参数序列化
            JSONObject params = (JSONObject) JSONObject.parse(request.getParameter("params"));
             String image = params.getString("image");        //拿到字符串格式的图片
            String subPath="c:/";
            String PicName=this.getTimeID();
            //System.out.println(PicName);

            // 只允许jpg
            String header ="data:image/jpeg;base64,";
 		    // 去掉头部
		    image=image.split(",")[1];
		    //image = image.substring(header.length());
		    // 写入磁盘
		    String success = "fail";
		    BASE64Decoder decoder = new BASE64Decoder();
		    try{
		            byte[] decodedBytes = decoder.decodeBuffer(image);        //将字符串格式的image转为二进制流（biye[])的decodedBytes
		           
		            String imgFilePath =subPath+PicName;                        //指定图片要存放的位置
		            FileOutputStream out = new FileOutputStream(imgFilePath);        //新建一个文件输出器，并为它指定输出位置imgFilePath
		            out.write(decodedBytes); //利用文件输出器将二进制格式decodedBytes输出
		            out.close();                        //关闭文件输出器
		            success = "上传文件成功！";
		            //System.out.println("上传文件成功！");
		
		    }catch(Exception e){
		            success = "上传文件失败！|"+e.getMessage();
		            e.printStackTrace();
		    }
 		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString()+"上传照片============");
		}
		map.put("result", result);
 		map.put("message", message);
		return  map;
	}
	
	

	
	/* ===============================基础信息权限================================== */
	public void getHC(){
			ModelAndView mv = this.getModelAndView();
			HttpServletRequest request=this.getRequest();
			HttpSession pcsession=request.getSession();
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();	
			StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
			if(slogin != null ){
				Map<String,Object> map=slogin.getMap();
				Qx qx=new Qx();
				if(map != null){
 					qx=(Qx) map.get("cw");
					pcsession.setAttribute("storeqx", qx);
					mv.addObject("qx", qx);
				}else{
					mv.addObject("qx", qx);
				}
			}
  	}
	/* ===============================权限================================== */
}
