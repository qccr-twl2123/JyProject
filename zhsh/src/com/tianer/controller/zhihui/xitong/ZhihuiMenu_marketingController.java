package com.tianer.controller.zhihui.xitong;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.service.storepc.store_marketing.Storepc_marketingService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.SmsUtil;

/** 
 * 类名称：ZhihuiMenu_marketingController
 * 创建人：刘耀耀.营销规则
 * 创建时间：2016-06-16
 */
@Controller
@RequestMapping(value="/zhihui_menu_marketing")
public class ZhihuiMenu_marketingController extends BaseController {
	
	
	@Resource(name = "storepc_marketingService")
	private Storepc_marketingService storepcMarketingService;
	/**
	 * 列表
	 * 刘耀耀
	 * 2016.06.16
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Menu_qx");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = storepcMarketingService.marketlist(page);	//列出Store列表
			mv.addObject("varList", varList);
			page.setPd(pd);
			this.getHC();
			mv.setViewName("zhihui/xitong/xitong7");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/golist")
	public ModelAndView golist(Page page){
		logBefore(logger, "golist");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
//			page.setPd(pd);
			mv.setViewName("zhihui/xitong/xitong8");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/selectAll")
	public ModelAndView selectAll(Page page){
		logBefore(logger, "golist");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String content = pd.getString("content");
			if(content == null || content == ""){
				mv.setViewName("redirect:golist.do");
				mv.addObject("pd", pd);
			}else{
				if(content.contains("CZ")){//操作员
					pd = storepcMarketingService.selectoperator(pd); 
					if(pd != null){
						pd.put("type", "1");
					}
 				}else if(content.contains("YW")){//业务员
					pd = storepcMarketingService.selectclerk(pd);
					if(pd != null){
						pd.put("type", "2");
					}
 				}else if(content.contains("FW")){//服务商
					pd = storepcMarketingService.selectsp(pd);
					if(pd != null){
						pd.put("type", "3");
					}
				}else if(content.length() > 0){//子公司
					pd = storepcMarketingService.selectsubsidiary(pd);
					if(pd != null){
						pd.put("type", "4");
					}
				}else{
					System.out.println("未使用id查询");
					pd.put("content", "请使用ID号查询");
				}
			}
			mv.setViewName("zhihui/xitong/xitong8");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value="/updPass")
	@ResponseBody
	public Object updPass(){
		logBefore(logger, "修改密码");
		String result = "01";
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			String id = pd.getString("id");
			String phone = pd.getString("phone");
			String pass = pd.getString("password");
			String name="";
			if(id.contains("CZ")){//操作员
				name="操作员";
				storepcMarketingService.updateOperator(pd);
			}else if(id.contains("YW")){//业务员
				name="业务员";
				storepcMarketingService.updateClerk(pd);
			}else if(id.contains("FW")){//服务商
				name="服务商";
				storepcMarketingService.updateSp(pd);
			}else if(id.length() > 0){//子公司
				name="子公司";
				storepcMarketingService.updateSubsidiary(pd);
			}
			//给用户发送短信，告知重置后密码
			SmsUtil.ChongzhiPassword(phone, id,name);
  		} catch(Exception e){
 			map.put("result", "00");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
 		return map;
	}
	
	
	/* ===============================权限================================== */
	public void getHC(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		session.setAttribute("qx", map.get("26"));
	}
	/* ===============================权限================================== */
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
