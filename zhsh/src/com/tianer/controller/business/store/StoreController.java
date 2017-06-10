package com.tianer.controller.business.store;

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

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.system.Menu;
import com.tianer.entity.Page;
import com.tianer.util.AppUtil;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.service.business.store.StoreService;

/** 
 * 类名称：StoreController
 * 创建人：cyr
 * 创建时间：2016-05-26
 */
@Controller
@RequestMapping(value="/store")
public class StoreController extends BaseController {
	
	@Resource(name="storeService")
	private StoreService storeService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Store");
		logger.error("啦啦");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("store_id", this.get32UUID());	//主键
//		storeService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Store");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			storeService.delete(pd);
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
		logBefore(logger, "修改Store");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		storeService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
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
			page.setPd(pd);
			List<PageData>	varList = storeService.list(page);	//列出Store列表
			this.getHC(); //调用权限
			mv.setViewName("business/store/store_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增Store页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("business/store/store_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Store页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = storeService.findById(pd);	//根据ID读取
			mv.setViewName("business/store/store_edit");
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
		logBefore(logger, "批量删除Store");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				storeService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Store到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("商店名称");	//1
			titles.add("app显示名称");	//2
			titles.add("详细地址");	//3
			titles.add("楼层");	//4
			titles.add("关键字");	//5
			titles.add("联系人姓名");	//6
			titles.add("联系人职务");	//7
			titles.add("经营品项介绍");	//8
			titles.add("会员联系号码");	//9
			titles.add("注册手机号码");	//10
			titles.add("登陆密码");	//11
			titles.add("审核状态");	//12
			titles.add("商家vip图片");	//13
			titles.add("商家状态");	//14
			titles.add("商家星级");	//15
			titles.add("经营范围");	//16
			titles.add("商家介绍");	//17
			titles.add("营业执照");	//18
			titles.add("营业许可证");	//19
			titles.add("注册时间");	//20
			titles.add("官网地址");	//21
			titles.add("本店积分率");	//22
			titles.add("推广服务积分率");	//23
			titles.add("提现费率");	//24
			titles.add("保底低分");	//25
			titles.add("积分提醒");	//26
			titles.add("排序分值");	//27
			titles.add("综合评分值");	//28
			titles.add("充值次数");	//29
			titles.add("交易次数");	//30
			titles.add("提现次数");	//31
			titles.add("累计赞次数");	//32
			titles.add("大类上限");	//33
			titles.add("小类上限");	//34
			dataMap.put("titles", titles);
			List<PageData> varOList = storeService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("STORE_NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("APP_ADDRESS"));	//2
				vpd.put("var3", varOList.get(i).getString("DETAILED_ADDRESS"));	//3
				vpd.put("var4", varOList.get(i).getString("STOREY"));	//4
				vpd.put("var5", varOList.get(i).getString("KEYWORD"));	//5
				vpd.put("var6", varOList.get(i).getString("CONTACTS_NAME"));	//6
				vpd.put("var7", varOList.get(i).getString("CONTACTS_JOB"));	//7
				vpd.put("var8", varOList.get(i).getString("MANAGEMENT"));	//8
				vpd.put("var9", varOList.get(i).getString("STORE_PHONE"));	//9
				vpd.put("var10", varOList.get(i).getString("REGISTERTEL_PHONE"));	//10
				vpd.put("var11", varOList.get(i).getString("PASSWORD"));	//11
				vpd.put("var12", varOList.get(i).getString("CHECK_STATUS"));	//12
				vpd.put("var13", varOList.get(i).getString("VIP_IMAGE"));	//13
				vpd.put("var14", varOList.get(i).getString("MERCHANT_STATUS"));	//14
				vpd.put("var15", varOList.get(i).getString("MERCHANT_LEVEL"));	//15
				vpd.put("var16", varOList.get(i).getString("SCOPE"));	//16
				vpd.put("var17", varOList.get(i).getString("INTRODUCE"));	//17
				vpd.put("var18", varOList.get(i).getString("LICENSE_IMAGE"));	//18
				vpd.put("var19", varOList.get(i).getString("PERMIT_IMAGE"));	//19
				vpd.put("var20", varOList.get(i).getString("REGISTERTIME"));	//20
				vpd.put("var21", varOList.get(i).getString("WEBSITE_ADDRESS"));	//21
				vpd.put("var22", varOList.get(i).getString("INTEGRAL_RATE"));	//22
				vpd.put("var23", varOList.get(i).getString("SERVICE_RATE"));	//23
				vpd.put("var24", varOList.get(i).getString("WITHDRAW_RATE"));	//24
				vpd.put("var25", varOList.get(i).getString("LOWEST_SCORE"));	//25
				vpd.put("var26", varOList.get(i).getString("REMIND_INTEGRAL"));	//26
				vpd.put("var27", varOList.get(i).getString("SORT_SCORE"));	//27
				vpd.put("var28", varOList.get(i).getString("COMPLEX_SCORE"));	//28
				vpd.put("var29", varOList.get(i).getString("RECHARGE_TIMES"));	//29
				vpd.put("var30", varOList.get(i).getString("TRANSACTION_TIMES"));	//30
				vpd.put("var31", varOList.get(i).getString("WITHDRAW_TIMES"));	//31
				vpd.put("var32", varOList.get(i).getString("ZAN_TIMES"));	//32
				vpd.put("var33", varOList.get(i).getString("BIGTYPE_MAX"));	//33
				vpd.put("var34", varOList.get(i).getString("SMALLTYPE_MIN"));	//34
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
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_QX);
		mv.addObject(Const.SESSION_QX,map);	//按钮权限
		List<Menu> menuList = (List)session.getAttribute(Const.SESSION_menuList);
		mv.addObject(Const.SESSION_menuList, menuList);//菜单权限
	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
