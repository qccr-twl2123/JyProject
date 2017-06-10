package com.tianer.controller.storepc.goods;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.util.AppUtil;
import com.tianer.util.PageData;
import com.tianer.service.business.goods.GoodsService;

/** 
 * 
* 类名称：GoodsController   
* 类描述：   商家
* 创建人：魏汉文  
* 创建时间：2016年5月26日 下午3:49:30
 */
@Controller("storeGoodsController")
 public class GoodsController extends BaseController {
	
	@Resource(name="goodsService")
	private GoodsService goodsService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/store_saveGoods")
	@ResponseBody
	public Object save() throws Exception{
		logBefore(logger, "新增Goods");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "01";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("goods_id", BaseController.getTimeID());	//主键
			goodsService.save(pd);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
 		map.put("result", result);
 		return map;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/store_deleteGoods")
	@ResponseBody
	public Object delete(){
		logBefore(logger, "删除Goods");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "01";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			goodsService.delete(pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/store_editGoods")
	@ResponseBody
	public Object edit() throws Exception{
		logBefore(logger, "修改Goods");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "01";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			goodsService.edit(pd);
		} catch (Exception e) {
			// TODO: handle exception
		}
 		map.put("result", result);
 		return map;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/store_listGoods")
	@ResponseBody
	public Object list(Page page){
		logBefore(logger, "列表Goods");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "01";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = goodsService.list(page);	//列出Goods列表
 			map.put("pagedatas", varList);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		return AppUtil.returnObject(pd, map);
	}
	

	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
