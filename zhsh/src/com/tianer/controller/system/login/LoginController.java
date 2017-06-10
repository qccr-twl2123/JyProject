//package com.tianer.controller.system.login;
// 
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.tianer.controller.base.BaseController;
//import com.tianer.entity.Page;
//import com.tianer.entity.system.Menu;
//import com.tianer.entity.system.Role;
//import com.tianer.entity.system.User;
//import com.tianer.service.system.menu.MenuService;
//import com.tianer.service.system.role.RoleService;
//import com.tianer.service.system.user.UserService;
//import com.tianer.util.Const;
//import com.tianer.util.DateUtil;
//import com.tianer.util.MD5;
//import com.tianer.util.PageData;
//import com.tianer.util.RightsHelper;
//import com.tianer.util.Tools;
///*
// * 总入口
// */
//@Controller
//public class LoginController extends BaseController {
//
//	@Resource(name="userService")
//	private UserService userService;
//	@Resource(name="menuService")
//	private MenuService menuService;
//	@Resource(name="roleService")
//	private RoleService roleService;
//	
//	/**
//	 * 获取用户权限
//	 */
//	public Map<String, String> getUQX(Session session){
//		PageData pd = new PageData();
//		Map<String, String> map = new HashMap<String, String>();
//		try {
//			String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();
//			pd.put(Const.SESSION_USERNAME, USERNAME);
//			String ROLE_ID = userService.findByUId(pd).get("ROLE_ID").toString();
// 			pd.put("ROLE_ID", ROLE_ID);
// 			PageData pd2 = new PageData();
//			pd2.put(Const.SESSION_USERNAME, USERNAME);
//			pd2.put("ROLE_ID", ROLE_ID);
// 			pd = roleService.findObjectById(pd);																
// 			pd2 = roleService.findGLbyrid(pd2);
//			if(null != pd2){
//				map.put("FX_QX", pd2.get("FX_QX").toString());
//				map.put("FW_QX", pd2.get("FW_QX").toString());
//				map.put("QX1", pd2.get("QX1").toString());
//				map.put("QX2", pd2.get("QX2").toString());
//				map.put("QX3", pd2.get("QX3").toString());
//				map.put("QX4", pd2.get("QX4").toString());
// 				pd2.put("ROLE_ID", ROLE_ID);
//				pd2 = roleService.findYHbyrid(pd2);
//				map.put("C1", pd2.get("C1").toString());
//				map.put("C2", pd2.get("C2").toString());
//				map.put("C3", pd2.get("C3").toString());
//				map.put("C4", pd2.get("C4").toString());
//				map.put("Q1", pd2.get("Q1").toString());
//				map.put("Q2", pd2.get("Q2").toString());
//				map.put("Q3", pd2.get("Q3").toString());
//				map.put("Q4", pd2.get("Q4").toString());
//			}
// 			map.put("adds", pd.getString("ADD_QX"));
//			map.put("dels", pd.getString("DEL_QX"));
//			map.put("edits", pd.getString("EDIT_QX"));
//			map.put("chas", pd.getString("CHA_QX"));
// 			//System.out.println(map);
// 			this.getRemortIP(USERNAME);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}	
//		return map;
//	}
//	/* ==================================================登录过滤========================================================== */
//	
//	/* ==================================================登录过滤========================================================== */
//	
//	
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
//		userService.saveIP(pd);
//	}  
//	
//	
//	/**
//	 * 访问登录页
//	 * @return
//	 */
//	@RequestMapping(value="/login_toLogin")
//	public ModelAndView toLogin()throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
//		mv.setViewName("system/admin/login");
//		mv.addObject("pd",pd);
//		return mv;
//	}
//	
//	/**
//	 * 请求登录，验证用户
//	 */
//	@RequestMapping(value="/login_login")
//	public ModelAndView login()throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		
//		//shiro管理的session
//		Subject currentUser = SecurityUtils.getSubject();  
//		Session session = currentUser.getSession();
//		String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码
//		
//		String errInfo = "";
//		pd = this.getPageData();
//		PageData pdm = new PageData();
//		pdm = this.getPageData();
//		String code = pd.getString("code");
//		if(null == code || "".equals(code)){
//			mv.setViewName("redirect:/");
//		}else{
//			String USERNAME = pd.get("loginname").toString();
//			String PASSWORD  = pd.get("password").toString();
//			pd.put("USERNAME", USERNAME);
//			if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){
// 				String passwd = MD5.md5(PASSWORD);	//密码加密	
//				pd.put("PASSWORD", passwd);
//				pd = userService.getUserByNameAndPwd(pd);
//				//判断当前用户是否为空
//				if(pd != null){
//					pd.put("LAST_LOGIN",DateUtil.getTime().toString());
//					userService.updateLastLogin(pd);
//					User user = new User();
//					user.setUSER_ID(pd.getString("USER_ID"));
//					user.setUSERNAME(pd.getString("USERNAME"));
//					user.setPASSWORD(pd.getString("PASSWORD"));
//					user.setNAME(pd.getString("NAME"));
//					user.setRIGHTS(pd.getString("RIGHTS"));
//					user.setROLE_ID(pd.getString("ROLE_ID"));
//					user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
//					user.setIP(pd.getString("IP"));
//					user.setSTATUS(pd.getString("STATUS"));
//					session.setAttribute(Const.SESSION_USER, user);	//存入session
//					session.removeAttribute(Const.SESSION_SECURITY_CODE);	//移除验证码
//					
//					//shiro加入身份验证
//					Subject subject = SecurityUtils.getSubject(); 
//				    UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD); 
//				    try { 
//				        subject.login(token); 
//				    } catch (AuthenticationException e) { 
//				    	errInfo = "身份验证失败！";
//				    }
//				    
//				}else{
//					errInfo = "用户名或密码有误！";
//				}
//			}else{
//				errInfo = "验证码输入有误！";
//			}
//			if(Tools.isEmpty(errInfo)){
//				mv.setViewName("redirect:login_index.do");
//			}else{
//				mv.addObject("errInfo", errInfo);
//				mv.addObject("loginname",USERNAME);
//				mv.addObject("password",PASSWORD);
//				mv.setViewName("system/admin/login");
//			}
//		mv.addObject("pd",pdm);
//		}
//		return mv;
//	}
//	
//	/**
//	 * 访问系统首页
//	 */
//	@RequestMapping(value="/login_index")
//	public ModelAndView login_index(Page page){
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try{
//			
//			//shiro管理的session
//			Subject currentUser = SecurityUtils.getSubject();  
//			Session session = currentUser.getSession();
//			//获得登陆用户信息
//			User user = (User)session.getAttribute(Const.SESSION_USER);
//			if (user != null) {
//				User userr = (User)session.getAttribute(Const.SESSION_USERROL);
//				if(null == userr){
//					user = userService.getUserAndRoleById(user.getUSER_ID());
//					session.setAttribute(Const.SESSION_USERROL, user);
//				}else{
//					user = userr;
//				}
//				Role role = user.getRole();
//				String roleRights = role!=null ? role.getRIGHTS() : "";
//				//避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限限都存入session
//				session.setAttribute(Const.SESSION_ROLE_RIGHTS, roleRights); 		//将角色权限存入session
//				session.setAttribute(Const.SESSION_USERNAME, user.getUSERNAME());	//放入用户名
//				pd.put("username", user.getUSERNAME());
//				
//				//所有菜单
//				List<Menu> allmenuList = new ArrayList<Menu>();
//				if(null == session.getAttribute(Const.SESSION_allmenuList)){
//						allmenuList = menuService.listAllMenu();
//						if(Tools.notEmpty(roleRights)){
//							for(Menu menu : allmenuList){
//								//开始设置权限
//								menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
//								if(menu.isHasMenu()){
//									List<Menu> subMenuList = menu.getSubMenu();
//									for(Menu sub : subMenuList){
//										sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
//									}
//								}
//							}
//						}
//						session.setAttribute(Const.SESSION_allmenuList, allmenuList);			//菜单权限放入session中
//				}else{
//					allmenuList = (List<Menu>)session.getAttribute(Const.SESSION_allmenuList);
//				}
//				
//				//切换菜单=====
//				List<Menu> menuList = new ArrayList<Menu>();
//				if(null == session.getAttribute(Const.SESSION_menuList) || ("yes".equals(pd.getString("changeMenu")))){
//					List<Menu> menuList1 = new ArrayList<Menu>();
//					List<Menu> menuList2 = new ArrayList<Menu>();
//					
//					//拆分菜单
//					for(int i=0;i<allmenuList.size();i++){
//						Menu menu = allmenuList.get(i);
//						if("1".equals(menu.getMENU_TYPE())){
//							menuList1.add(menu);
//						}else{
//							menuList2.add(menu);
//						}
//					}
//					
//					session.removeAttribute(Const.SESSION_menuList);
//					if("2".equals(session.getAttribute("changeMenu"))){
//						session.setAttribute(Const.SESSION_menuList, menuList1);
//						session.removeAttribute("changeMenu");
//						session.setAttribute("changeMenu", "1");
//						menuList = menuList1;
//					}else{
//						session.setAttribute(Const.SESSION_menuList, menuList2);
//						session.removeAttribute("changeMenu");
//						session.setAttribute("changeMenu", "2");
//						menuList = menuList2;
//					}
//				}else{
//					menuList = (List<Menu>)session.getAttribute(Const.SESSION_menuList);
//				}
//				//切换菜单=====
//				
//				if(null == session.getAttribute(Const.SESSION_QX)){
//					session.setAttribute(Const.SESSION_QX, this.getUQX(session));	//按钮权限放到session中
//				}
//				
//				//FusionCharts 报表
////			 	String strXML = "<graph caption='前12个月订单销量柱状图' xAxisName='月份' yAxisName='值' decimalPrecision='0' formatNumberScale='0'><set name='2013-05' value='4' color='AFD8F8'/><set name='2013-04' value='0' color='AFD8F8'/><set name='2013-03' value='0' color='AFD8F8'/><set name='2013-02' value='0' color='AFD8F8'/><set name='2013-01' value='0' color='AFD8F8'/><set name='2012-01' value='0' color='AFD8F8'/><set name='2012-11' value='0' color='AFD8F8'/><set name='2012-10' value='0' color='AFD8F8'/><set name='2012-09' value='0' color='AFD8F8'/><set name='2012-08' value='0' color='AFD8F8'/><set name='2012-07' value='0' color='AFD8F8'/><set name='2012-06' value='0' color='AFD8F8'/></graph>" ;
////			 	mv.addObject("strXML", strXML);
//			 	//FusionCharts 报表
//			 	
//				mv.setViewName("system/admin/index");
// 				mv.addObject("user", user);
//				mv.addObject("menuList", menuList);
//				System.out.println("menuList的数量:"+menuList.size());
//				for(int i=0;i<menuList.size();i++){
//					Menu menu = menuList.get(i);
//					System.out.println("i:"+menu);
//				}
//			}else {
//				mv.setViewName("system/admin/login");//session失效后跳转登录页面
//			}
//			
//			
//		} catch(Exception e){
//			mv.setViewName("system/admin/login");
//			logger.error(e.getMessage(), e);
//		}
//		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
//		mv.addObject("pd",pd);
//		return mv;
//	}
//	
//	/**
//	 * 进入首页后的默认页面
//	 * @return
//	 */
//	@RequestMapping(value="/login_default")
//	public String defaultPage(){
//		return "system/admin/default";
//	}
//	
//	/**
//	 * 用户注销
//	 * @param session
//	 * @return
//	 */
//	@RequestMapping(value="/logout")
//	public ModelAndView logout(){
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		
//		//shiro管理的session
//		Subject currentUser = SecurityUtils.getSubject();  
//		Session session = currentUser.getSession();
//		
//		session.removeAttribute(Const.SESSION_USER);
//		session.removeAttribute(Const.SESSION_ROLE_RIGHTS);
//		session.removeAttribute(Const.SESSION_allmenuList);
//		session.removeAttribute(Const.SESSION_menuList);
//		session.removeAttribute(Const.SESSION_QX);
//		session.removeAttribute(Const.SESSION_userpds);
//		session.removeAttribute(Const.SESSION_USERNAME);
//		session.removeAttribute(Const.SESSION_USERROL);
//		session.removeAttribute("changeMenu");
//		
//		//shiro销毁登录
//		Subject subject = SecurityUtils.getSubject(); 
//		subject.logout();
//		
//		pd = this.getPageData();
//		String  msg = pd.getString("msg");
//		pd.put("msg", msg);
//		
//		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
//		mv.setViewName("system/admin/login");
//		mv.addObject("pd",pd);
//		return mv;
//	}
//	
//}
