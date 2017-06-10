package com.tianer.controller.zhihui.dangan;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.entity.zhihui.zhihuiLogin;
import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.citymanager.CityManagerService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;
import com.tianer.util.SmsUtil;
import com.tianer.util.StringUtil;

/** 
 * 
* 类名称：citymanagerController   
* 类描述：   子公司
* 创建人：魏汉文  
* 创建时间：2016年5月25日 下午4:13:48
 */
@Controller
@RequestMapping(value="/zhihui_citymanager")
public class ZhihuiCityManagerController extends BaseController {
	
	@Resource(name="citymanagerService")
	private CityManagerService citymanagerService;
	@Resource(name="pcdService")
	private PcdService pcdService;
	@Resource(name="city_fileService")
	private City_fileService city_fileService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		//logBefore(logger, "新增citymanager");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("number", "0");
			String citymanager_id=pd.getString("citymanager_id");
			String citymanager_name=pd.getString("citymanager_name");
			String phone=pd.getString("phone");
			String password=BaseController.get6UID();
 			pd.put("password", password);
 	  		citymanagerService.save(pd);
 			//新增地域
			String allpcd=pd.getString("allpcd");
			if(allpcd.contains("@")){
					List<PageData> allpcdlist=new ArrayList<PageData>();
					String[] one=allpcd.split(",");
					for (int i = 0; i < one.length; i++) {
						String[] two=one[i].split("@");
						PageData e=new PageData();
						e.put("province_id", two[0]);
						e.put("city_id", two[1]);
						e.put("area_id", two[2]);
						e.put("citymanager_id", citymanager_id);
						allpcdlist.add(e);
						PageData da = new PageData();
						//添加城市档案数据
						if(e.getString("area_id").equals("0")){
							String city_id = e.getString("city_id");
							List<PageData> list = citymanagerService.cityAll(city_id);
							//拿到所有为全市的区域
							for (int j = 0; j < list.size(); j++) {
								String area_id = list.get(j).getString("area_id");
								da.put("area_id", area_id);
								da.put("province_id", e.getString("province_id"));
								da.put("city_id", e.getString("city_id"));
								//查看是否已经注册过
								PageData pg=city_fileService.findById(da);
								if(pg != null){
									continue;
								}
								//判断该表是否有值
								List<PageData> cityList=city_fileService.listAll(pd);
								if(cityList.size() == 0){
									da.put("city_file_id", "0001");	//主键
								}else{
									String id=city_fileService.getMaxID(pd);
									String nextId=StringUtil.getNextId(id);
									da.put("city_file_id", nextId);
								}
								da.put("open_status", '0');
								citymanagerService.saveCity(da);
								cityList=null;
								pg=null;
							}
							list=null;
						}else{
							//查看是否已经注册过
							PageData pg=city_fileService.findById(e);
							if(pg == null || pg.equals("")){
								//判断该表是否有值
								List<PageData> cityList=city_fileService.listAll(pd);
								if(cityList.size() == 0){
									e.put("city_file_id", "0001");	//主键
								}else{
									String id=city_fileService.getMaxID(pd);
									String nextId=StringUtil.getNextId(id);
									e.put("city_file_id", nextId);
								}
								e.put("open_status", '0');
								citymanagerService.saveCity(e);
								cityList=null;
							}
							pg=null;
						}
						e=null;
						da=null;
	 				}
					citymanagerService.savePcd(pd,allpcdlist);
	 		}
			//发送短信
			SmsUtil.chengshijingli(phone, citymanager_name, citymanager_id, password);
		} catch (Exception e) {
			// TODO: handle exception
		}
 		mv.setViewName("redirect:list.do");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		//logBefore(logger, "删除 ");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			citymanagerService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		//logBefore(logger, "修改citymanager");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
					pd = this.getPageData();
					String citymanager_id=pd.getString("citymanager_id");
					citymanagerService.edit(pd);
					//新增地域
					String allpcd=pd.getString("allpcd");
					if(allpcd.contains("@")){
							List<PageData> allpcdlist=new ArrayList<PageData>();
							String[] one=allpcd.split(",");
							for (int i = 0; i < one.length; i++) {
								String[] two=one[i].split("@");
								PageData e=new PageData();
								e.put("province_id", two[0]);
								e.put("city_id", two[1]);
								e.put("area_id", two[2]);
								e.put("citymanager_id", citymanager_id);
								allpcdlist.add(e);
								e=null;
			 				}
							citymanagerService.savePcd(pd,allpcdlist);
							allpcdlist=null;
			 		}
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString());
		}
 		mv.setViewName("redirect:list.do?currentPage="+pd.getString("currentPage"));
		return mv;
	}
	
	/**
	 * 
	 * 城市经理列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		//logBefore(logger, "城市经理列表");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		try{
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			pd = this.getPageData();
			if(zhlogin != null && zhlogin.getMenu_role_id() != null){
				//0-管理员，1-服务商，2-业务员，3-操作员，4-城市经理，5-子公司，6-其他
				pd.put("login_cityname", zhlogin.getCity_name()); 
				pd.put("login_areaname", zhlogin.getArea_name());
				if(zhlogin.getMenu_role_id().equals("4")){
	 				pd.put("citymanager_id", zhlogin.getLogin_id());
 	 			}  
				//当前页
				String currentPage = pd.getString("currentPage");
				if(null != currentPage && !"".equals(currentPage)){
					page.setCurrentPage(Integer.parseInt(currentPage));
				}
				page.setPd(pd);
 				List<PageData>	varList = citymanagerService.list(page);	//列出citymanager列表
	 			mv.addObject("varList", varList);
				mv.setViewName("zhihui/dangan/citymanager1");
				mv.addObject("pd", pd);
			}
 			
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
 	
	/**
	 * 去新增页面
	 * 魏汉文20160531
	 * 
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		//logBefore(logger, "去新增citymanager页面");
		ModelAndView mv = this.getModelAndView();
		List<PageData> citymanagerpcd=new ArrayList<PageData>();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			citymanagerpcd.add(pd);
			mv.addObject("citymanagerpcd", citymanagerpcd);//新增一个地域
			String citymanager_posts_id=BaseController.getTimeID();
			pd.put("citymanager_posts_id", citymanager_posts_id);
			//获取当前最大值得ID
			String id=citymanagerService.getMaxId();
			if(id==null || id.equals("") ){
				id="JL0001";
				pd.put("citymanager_id", id);
			}else{
				//设置新增的ID
				id=id.substring(3);
				String nextId=StringUtil.getNextId(id);
				pd.put("citymanager_id", "JL"+nextId);
			}
 			List<PageData> provicelist=pcdService.listAll(pd);//省list
			mv.addObject("provicelist", provicelist);
			mv.setViewName("zhihui/dangan/citymanager2");
			mv.addObject("msg", "save");
		
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
 
	
	/**
	 * 去修改页面
	 * 魏汉文20160531
	 * 
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		//logBefore(logger, "去修改citymanager页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
 			List<PageData> citymanagerpcd=citymanagerService.findSubPcdList(pd);
			if(citymanagerpcd.size() == 0){
				citymanagerpcd.add(pd);
			}
			mv.addObject("citymanagerpcd", citymanagerpcd);//地域列表
 			String currentPage=pd.getString("currentPage");
			//获取该子公司信息
			pd = citymanagerService.findById(pd);	//根据ID读取
 			List<PageData> provicelist=pcdService.listAll(pd);//省list
			mv.addObject("provicelist", provicelist);
			pd.put("currentPage", currentPage);
			mv.setViewName("zhihui/dangan/citymanager2");
			mv.addObject("msg", "edit");
 			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		//logBefore(logger, "批量删除citymanager");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				citymanagerService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		//logBefore(logger, "导出citymanager到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("子公司名称");	//1
			titles.add("内部公司名称");	//2
			titles.add("子公司工商名称");	//3
			titles.add("省ID");	//4
			titles.add("市ID");	//5
			titles.add("区域/县ID");	//6
			titles.add("唯一标示ID");	//7
			titles.add("创建时间");	//8
			dataMap.put("titles", titles);
			List<PageData> varOList = citymanagerService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("citymanager_NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("HOUSE_NAME"));	//2
				vpd.put("var3", varOList.get(i).getString("citymanager_IC_NAME"));	//3
				vpd.put("var4", varOList.get(i).getString("PROVINCE_ID"));	//4
				vpd.put("var5", varOList.get(i).getString("CITY_ID"));	//5
				vpd.put("var6", varOList.get(i).getString("AREA_ID"));	//6
				vpd.put("var7", varOList.get(i).getString("citymanager_ID"));	//7
				vpd.put("var8", varOList.get(i).getString("CREATEDATE"));	//8
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/* ===============================权限================================== */
	public void getHC(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("54"));
		}
 	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
