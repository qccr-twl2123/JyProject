package com.tianer.controller.zhihui.login;
 
import java.util.HashMap;
import java.util.List;
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
import com.tianer.entity.Page;
import com.tianer.entity.zhihui.Qx;
import com.tianer.entity.zhihui.zhihuiLogin;
import com.tianer.service.business.citymanager.CityManagerService;
import com.tianer.service.business.clerk_file.Clerk_fileService;
import com.tianer.service.business.menu_role.Menu_roleService;
import com.tianer.service.business.operator_file.Operator_fileService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.sp_file.Sp_fileService;
import com.tianer.service.business.subsidiary.SubsidiaryService;
import com.tianer.service.business.system.SystemService;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.Tools;

/**
 * 
* 类名称：ZhiHuiLoginController   
* 类描述：   智慧总后台
* 创建人：魏汉文  
* 创建时间：2016年5月31日 上午11:15:14
 */
@Controller
public class ZhiHuiLoginController extends BaseController {
	
//	/**
//	 * 获取登录用户的IP
//	 * @throws Exception 
//	 */
//	public void getRemortIP(String USERNAME) throws Exception {  
//		PageData pd = new PageData();
//		HttpServletRequest request = this.getRequest();
//		String ip = "";
//		if (request.getHeader("x-forwarded-for") == null) {  
//			ip = request.getRemoteAddr();  
//	    }else{
//	    	ip = request.getHeader("x-forwarded-for");  
//	    }
//		pd.put("USERNAME", USERNAME);
//		pd.put("IP", ip);
// 	}  
	
	
	@Resource(name="subsidiaryService")
	private SubsidiaryService subsidiaryService;
	
	@Resource(name="pcdService")
	private PcdService pcdService;
	@Resource(name="systemService")
	private SystemService systemService;
	

	
//	/**
//	 * 访问登录页
//	 * @return
//	 */
//	@RequestMapping(value="/login_toLogin")
//	public ModelAndView login_toLogin()throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
// 		mv.setViewName("zhihui/admin/login");
//		mv.addObject("pd",pd);
//		return mv;
//	}
	
	
	
	@Resource(name="clerk_fileService")
	private Clerk_fileService clerk_fileService;
	@Resource(name="operator_fileService")
	private Operator_fileService operator_fileService;
	@Resource(name="sp_fileService")
	private Sp_fileService sp_fileService;
	
	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value="/login_login")
	public ModelAndView login()throws Exception{
		ModelAndView mv = this.getModelAndView();
		Map<String,Object> map=new HashMap<String,Object>(); 
		PageData pd = new PageData();
		
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码
		
		String errInfo = "";
		pd = this.getPageData();
		PageData pdm = new PageData();
		pdm = this.getPageData();
		String code = pd.getString("code");
		if(null == code || "".equals(code)){
			mv.setViewName("redirect:/");
		}else{
			String username = pd.get("loginname").toString();
			String password  = pd.get("password").toString();
			zhihuiLogin zh=new zhihuiLogin();
  			if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){ 
 				//判断用户名和密码
 				if(username.contains("FW")){//服务商
	 					pd.put("sp_file_id", username);
	 					pd.put("password", password);
	 					pd=sp_fileService.findByLogin(pd);
	 					if(pd == null){
	 						errInfo="用户和密码错误";
	 					}else{
 	 						//获得角色的所有权限
	 						List<PageData> qxList=menu_roleService.listAllQx(pd);
	 						for(PageData e : qxList ){
	 							Qx qx=new Qx();
	 							qx.setAdd(e.getString("save"));
	 							qx.setDelete(e.getString("del"));
	 							qx.setEdit(e.getString("edit"));
	 							qx.setLook(e.getString("look"));
	 							map.put(e.getString("menu_id"), qx);
	 							qx=null;
	 						}
	 						zh.setMap(map);
	 						zh.setLogin_id(username);
	 						zh.setLogin_name(pd.getString("principal"));
	 						zh.setLogin_password(password);
	 						zh.setLogin_phone(pd.getString("phone"));
	 						zh.setMenu_role_id(pd.getString("menu_role_id"));
	 						zh.setProvince_name(pd.getString("province_name"));
	 						zh.setCity_name(pd.getString("city_name"));
	 						zh.setArea_name(pd.getString("area_name"));
	 						zh.setLogin_type("1");
 	 						session.setAttribute(Const.SESSION_USER, zh);	//存入session
	 						session.removeAttribute(Const.SESSION_SECURITY_CODE);	//移除验证码
	 					}
 				}else if(username.contains("YW")){//业务员
	 						pd.put("clerk_file_id", username);
	 						pd.put("password", password);
	 						pd=clerk_fileService.findByLogin(pd);
	 						if(pd == null){
		 						errInfo="用户和密码错误";
		 					}else{
		 						//获得角色的所有权限
		 						List<PageData> qxList=menu_roleService.listAllQx(pd);
		 						for(PageData e : qxList ){
		 							Qx qx=new Qx();
		 							qx.setAdd(e.getString("save"));
		 							qx.setDelete(e.getString("del"));
		 							qx.setEdit(e.getString("edit"));
		 							qx.setLook(e.getString("look"));
		 							map.put(e.getString("menu_id"), qx);
		 							qx=null;
		 						}
		 						zh.setMap(map);
		 						zh.setLogin_id(username);
		 						zh.setLogin_name(pd.getString("clerk_name"));
		 						zh.setLogin_password(password);
		 						zh.setLogin_phone(pd.getString("phone"));
		 						zh.setMenu_role_id(pd.getString("menu_role_id"));
		 						zh.setProvince_name(pd.getString("province_name"));
		 						zh.setCity_name(pd.getString("city_name"));
		 						zh.setArea_name(pd.getString("area_name"));
		 						zh.setLogin_type("2");
		 						session.setAttribute(Const.SESSION_USER, zh);	//存入session
		 						session.removeAttribute(Const.SESSION_SECURITY_CODE);	//移除验证码
		 					}
 				}else if(username.contains("CZ")){//操作员
		 					    pd.put("operator_file_id", username);
								pd.put("password", password);
								pd=operator_fileService.findByLogin(pd);
								if(pd == null){
			 						errInfo="用户和密码错误";
			 					}else{
			 						//获得角色的所有权限
			 						List<PageData> qxList=menu_roleService.listAllQx(pd);
			 						for(PageData e : qxList ){
			 							Qx qx=new Qx();
			 							qx.setAdd(e.getString("save"));
			 							qx.setDelete(e.getString("del"));
			 							qx.setEdit(e.getString("edit"));
			 							qx.setLook(e.getString("look"));
			 							map.put(e.getString("menu_id"), qx);
			 							qx=null;
			 						}
			 						zh.setMap(map);
			 						zh.setLogin_id(username);
			 						zh.setLogin_name(pd.getString("operator_name"));
			 						zh.setLogin_password(password);
			 						zh.setLogin_phone(pd.getString("phone"));
			 						zh.setMenu_role_id(pd.getString("menu_role_id"));
			 						zh.setProvince_name(pd.getString("province_name"));
			 						zh.setCity_name(pd.getString("city_name"));
			 						zh.setArea_name(pd.getString("area_name"));
			 						zh.setLogin_type("3");
			 						session.setAttribute(Const.SESSION_USER, zh);	//存入session
			 						session.removeAttribute(Const.SESSION_SECURITY_CODE);	//移除验证码
			 					}
 				}else if(username.contains("ZGS")){
  					pd.put("subsidiary_id", username);
					pd.put("password", password);
					pd=subsidiaryService.findByLogin(pdm);
					if(pd == null){
 						errInfo="用户和密码错误";
 					}else{
 						String allprovince_name="";
 						String allcity_name="";
 						String allarea_name="";
 						List<PageData> subsidiarypcd=subsidiaryService.findSubPcdList(pd);
 						for (PageData e : subsidiarypcd) {
 							allprovince_name+=e.getString("province_name")+",";
 							allcity_name+=e.getString("city_name")+",";
 							allarea_name+=e.getString("area_name")+",";
						}
  						//获得角色的所有权限
 						List<PageData> qxList=menu_roleService.listAllQx(pd);
 						for(PageData e : qxList ){
 							Qx qx=new Qx();
 							qx.setAdd(e.getString("save"));
 							qx.setDelete(e.getString("del"));
 							qx.setEdit(e.getString("edit"));
 							qx.setLook(e.getString("look"));
 							map.put(e.getString("menu_id"), qx);
 							qx=null;
 						}
 						zh.setMap(map);
 						zh.setLogin_id(username);
 						zh.setLogin_name(pd.getString("subsidiary_name"));
 						zh.setLogin_password(password);
 						zh.setLogin_phone(pd.getString("phone"));
 						zh.setMenu_role_id(pd.getString("menu_role_id"));
 						zh.setProvince_name(allprovince_name);
 						zh.setCity_name(allcity_name);
 						zh.setArea_name(allarea_name);
 						zh.setLogin_type("5");
 						session.setAttribute(Const.SESSION_USER, zh);	//存入session
 						session.removeAttribute(Const.SESSION_SECURITY_CODE);	//移除验证码
 					}
 				}else if(username.contains("JL")){
  					pd.put("citymanager_id", username);
					pd.put("password", password);
					pd=citymanagerService.findByLogin(pd);
					if(pd == null){
 						errInfo="用户和密码错误";
 					}else{
 						String allprovince_name="";
 						String allcity_name="";
 						String allarea_name="";
 						List<PageData> subsidiarypcd=citymanagerService.findSubPcdList(pd);
 						for (PageData e : subsidiarypcd) {
 							allprovince_name+=e.getString("province_name")+",";
 							allcity_name+=e.getString("city_name")+",";
 							allarea_name+=e.getString("area_name")+",";
						}
  						//获得角色的所有权限
 						List<PageData> qxList=menu_roleService.listAllQx(pd);
 						for(PageData e : qxList ){
 							Qx qx=new Qx();
 							qx.setAdd(e.getString("save"));
 							qx.setDelete(e.getString("del"));
 							qx.setEdit(e.getString("edit"));
 							qx.setLook(e.getString("look"));
 							map.put(e.getString("menu_id"), qx);
 							qx=null;
 						}
 						zh.setMap(map);
 						zh.setLogin_id(username);
 						zh.setLogin_name(pd.getString("citymanager_name"));
 						zh.setLogin_password(password);
 						zh.setLogin_phone(pd.getString("phone"));
 						zh.setMenu_role_id(pd.getString("menu_role_id"));
 						zh.setProvince_name(allprovince_name);
 						zh.setCity_name(allcity_name);
 						zh.setArea_name(allarea_name);
 						zh.setLogin_type("4");
 						session.setAttribute(Const.SESSION_USER, zh);	//存入session
 						session.removeAttribute(Const.SESSION_SECURITY_CODE);	//移除验证码
 					}
 				}else{
 					//获取账号密码
 					PageData adminpd=new PageData();
 					adminpd.put("password", password);
 					adminpd.put("loginnumber", username);
 					adminpd=systemService.findByIdAdmin(adminpd);
   					if(adminpd != null){
   						    pd.put("menu_role_id", "99");
	 						//获得角色的所有权限
	 						List<PageData> qxList=menu_roleService.listAllQx(pd);
	 						for(PageData e : qxList ){
	 							Qx qx=new Qx();
	 							qx.setAdd(e.getString("save"));
	 							qx.setDelete(e.getString("del"));
	 							qx.setEdit(e.getString("edit"));
	 							qx.setLook(e.getString("look"));
	 							map.put(e.getString("menu_id"), qx);
	 							qx=null;
	 						}
	 						zh.setMap(map);
	 						zh.setLogin_id(username);
	 						zh.setLogin_name(username);
	 						zh.setLogin_password(password);
	 						zh.setLogin_phone("15260282340");
	 						zh.setMenu_role_id(pd.getString("menu_role_id"));
	 						zh.setProvince_name("");
	 						zh.setCity_name("");
 						    zh.setArea_name("");
 						    zh.setLogin_type("0");
	 						session.setAttribute(Const.SESSION_USER, zh);	//存入session
	 						session.removeAttribute(Const.SESSION_SECURITY_CODE);	//移除验证码
 					}else{
 						errInfo="用户和密码错误";
 					}
  				}
			}else{
				errInfo = "验证码输入有误！";
			}
			if(Tools.isEmpty(errInfo)){
				mv.setViewName("redirect:zhihui_index.do");
			}else{
				mv.addObject("errInfo", errInfo);
				mv.addObject("loginname",username);
				mv.addObject("password",password);
				mv.setViewName("zhihui/admin/login");
			}
		mv.addObject("pd",pdm);
		}
		return mv;
	}
	
	@Resource(name="menu_roleService")
	private Menu_roleService menu_roleService;
	
	/**
	 * 访问首页
	 * @return
	 */
	@RequestMapping(value="/zhihui_index")
	public ModelAndView toLogin(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();	
		Map<String,Object> map=new HashMap<String,Object>(); 
		HttpServletRequest request=this.getRequest();
 		HttpSession pcsession=request.getSession();
 		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			//获得登陆用户信息
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			zhihuiLogin zh = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);
			if (zh != null) {
 					//获取menu_id为1的权限
				   map=zh.getMap();
				   if(map != null && !map.equals("")){
					   pcsession.setAttribute("qx", map.get("1"));
					   pcsession.setAttribute(Const.SESSION_MAP, map);
					   pcsession.setAttribute("menu_id", "1");
					   pcsession.setAttribute("logininfor", zh);
				   }
 					//获取所有省
					List<PageData> provicelist=pcdService.listAll(pd);//省list
					mv.addObject("provicelist", provicelist);
			 		//获取所有子公司
					List<PageData>	varList = subsidiaryService.list(page);	//列出Subsidiary列表
					mv.addObject("varList", varList);
					mv.setViewName("zhihui/index/index");
			}else{
					mv.setViewName("zhihui/admin/login");//session失效后跳转登录页面
			}
 			 //=========
 		}catch(Exception e){
			logger.info(e.toString(), e);
		}
 		mv.addObject("pd",pd);
		return mv;
	}
	
	
	
	/**
	 * 用户注销
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	public ModelAndView logout(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(Const.SESSION_allmenuList);
		session.removeAttribute(Const.SESSION_menuList);
		session.removeAttribute(Const.SESSION_QX);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute("changeMenu");
		
		//shiro销毁登录
		Subject subject = SecurityUtils.getSubject(); 
		subject.logout();
		
		pd = this.getPageData();
		String  msg = pd.getString("msg");
		pd.put("msg", msg);
		
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.setViewName("zhihui/admin/login");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 去修改密码页面
	 */
	@RequestMapping(value="/zhihui_goEditpassword")
	public ModelAndView zhihui_goEditpassword(){
		logBefore(logger, "去修改密码页面");
		ModelAndView mv = this.getModelAndView();
  		PageData pd = new PageData();
 		try { 
			pd = this.getPageData();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}		
		mv.setViewName("zhihui/admin/editpassword");
		mv.addObject("pd",pd);
		return mv;
	}	
	
	@Resource(name="citymanagerService")
	private CityManagerService citymanagerService;
 	
 	
	/**
	 *  修改密码 
	 */
	@RequestMapping(value="/zhihui_editpassword")
	public ModelAndView zhihui_editpassword(){
		logBefore(logger, " 修改密码 ");
		ModelAndView mv = this.getModelAndView();
		//0-管理员，1-服务商，2-业务员，3-操作员，4-城市经理，5-子公司，6-其他
  		PageData pd = new PageData();
 		try { 
			pd = this.getPageData();
			String type=pd.getString("type");
			pd.put("password", pd.getString("newpassword"));
			if(type.equals("0")){
				pd.put("admin_id", pd.getString("id"));
 				systemService.editAdmin(pd);
			}else if(type.equals("1")){
				pd.put("sp_file_id",  pd.getString("id"));
				sp_fileService.edit(pd);
 			}else if(type.equals("2")){
 				pd.put("clerk_file_id", pd.getString("id"));
 				clerk_fileService.edit(pd);
 			}else if(type.equals("3")){
 				pd.put("operator_file_id", pd.getString("id"));
 				operator_fileService.edit(pd);
 			}else if(type.equals("4")){
 				pd.put("citymanager_id", pd.getString("id"));
 				citymanagerService.edit(pd);
 			}else if(type.equals("5")){
 				pd.put("subsidiary_id", pd.getString("id"));
 				subsidiaryService.edit(pd);
 			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}		
		mv.setViewName("redirect:logout.do");
		mv.addObject("pd",pd);
		return mv;
	}	
	
	
	
}
