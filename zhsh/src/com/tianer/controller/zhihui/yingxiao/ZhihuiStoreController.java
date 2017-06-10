package com.tianer.controller.zhihui.yingxiao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.system.Menu;
import com.tianer.entity.Page;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.store.StoreService;
import com.tianer.service.business.store_file.Store_fileService;

/** 
 * 类名称：StoreController
 * 创建人：刘耀耀
 * 创建时间：2016-06-12
 */
@Controller
@RequestMapping(value="/zhihui_store")
public class ZhihuiStoreController extends BaseController {
	
	@Resource(name="storeService")
	private StoreService storeService;
	@Resource(name="pcdService")
	private PcdService pcdService;
	
	
	@Resource(name="store_fileService")
	private Store_fileService store_fileService;
	
	
	/**
	 * 修改星级
	 * 刘耀耀
	 * 2016.06.12
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改Store");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
  		 if(pd != null){
 				//选择商品的上限
	 			String merchant_level=pd.getString("merchant_level");//星级
	 			pd=store_fileService.findById(pd);
	 			if(pd != null && !pd.equals("")){
	 				PageData sortpd=store_fileService.getStartNumberForStore(pd);
	 				if(sortpd != null && !sortpd.equals("")){
	 					String onexing_money=sortpd.getString("onexing_money");
	 	 				String twoxing_money=sortpd.getString("twoxing_money");
	 	 				String threexing_money=sortpd.getString("threexing_money");
	 	 				if(merchant_level.equals("1")){
	 	 					pd.put("goods_max", onexing_money);
	 	 				}
	 	 				if(merchant_level.equals("2")){
	 	 					pd.put("goods_max", twoxing_money);
	 	 				}
	 	 				if(merchant_level.equals("3")){
	 	 					pd.put("goods_max", threexing_money);
	 	 				}
	 	 				//更新商家的商品上限
	 	 				pd.put("merchant_level", merchant_level);
	 	  				storeService.edit(pd);
	 	  				mv.setViewName("redirect:list.do?currentPage="+pd.getString("currentPage"));
	 				}
	 			}
		}
		
 		return mv;
	}
	
	
	/**
	 * 列表
	 * 刘耀耀
	 * 2016.06.12
	 * 
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Store");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String content = pd.getString("content");
			
			if(null != content && !"".equals(content)){
				content = content.trim();
				pd.put("content", content);
			}
			//省
			String province_name = pd.getString("province_name");
			if(null != province_name && !"".equals(province_name)){
				province_name = province_name.trim();
				pd.put("province_name", province_name);
			}
			//市
			String city_name = pd.getString("city_name");
			if(null != city_name && !"".equals(city_name)){
				pd.put("city_name", city_name);
			}
			//区
			String area_name= pd.getString("area_name");
			if(null != area_name && !"".equals(area_name)){
				pd.put("area_name", area_name);
			}
			//分页
			String currentPage = pd.getString("currentPage");
			if(null != currentPage && !"".equals(currentPage)){
				page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
			}
			page.setPd(pd);
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			List<PageData>	varList = storeService.list(page);	//列出Store列表
			this.getHC(); //调用权限
			mv.setViewName("zhihui/yingxiao/yingxiao12");
			mv.addObject("provicelist", provincelist);
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
 
	
	/**
	 * 去修改页面
	 * 刘耀耀
	 * 2016.06.12
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Store页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String currentPage=pd.getString("currentPage");
			pd = storeService.findById(pd);	//根据ID读取
//			List<PageData> provincelist=pcdService.listAll(pd);//省list
//			mv.addObject("provincelist", provincelist);
			mv.setViewName("zhihui/yingxiao/yingxiao13");
			mv.addObject("msg", "edit");
			mv.addObject("currentPage", currentPage);
			mv.addObject("pd", pd);
		} catch (Exception e) {
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
			session.setAttribute("qx", map.get("22"));
		}
 	}
	/* ===============================权限================================== */
	

}
