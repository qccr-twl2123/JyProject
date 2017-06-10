package com.tianer.controller.storeapp.statistical_chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.service.storeapp.Statisticalapp_chartService;
import com.tianer.service.storepc.store_wealthhistory.Storepc_wealthhistoryService;
import com.tianer.util.AppUtil;
import com.tianer.util.PageData;



/** 
* 类名称：Storeapp_wealthhistoryController
* 创建人：邢江涛  app统计图
* 创建时间：2016-07-01 
*/
@Controller("statistical_chartController")
@RequestMapping(value="/storeapp_chart")
public class Statistical_chartController extends BaseController{
	
	
	@Resource(name="statisticalapp_chartService")
	private Statisticalapp_chartService chartService;
	
	/**
	 * 获取15天内的销售金额
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findMoneyNum")
	@ResponseBody
	public Object findMoneyNum(Page page){
		Map<String,Object> map = new HashMap<String,Object>();
 		List<String> monList=new ArrayList<String>();
		List<String> numList=new ArrayList<String>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			//获取商家id
			String store_id = pd.getString("store_id");
			pd.put("store_id", store_id);
			for (int i = 1; i < 16; i++) {
				pd.put("num", i);
				String moneyNum=chartService.findMoneyNum(pd);
				monList.add(moneyNum);
				String number=chartService.findNum(pd);
				numList.add(number);
			}
 			pd.put("monList", monList);
  			pd.put("numList", numList);
    		//交易今天笔数总和
			int ordernumber=storepc_wealthhistoryService.getCountOrder(pd);
 			//统计今天的销售额
			if(storepc_wealthhistoryService.getSumOrder(pd) != null && storepc_wealthhistoryService.getSumOrder(pd).get("money") != null){
				String ordermoney=storepc_wealthhistoryService.getSumOrder(pd).get("money").toString();
				pd.put("ordermoney", ordermoney);
			}else{
				pd.put("ordermoney", "0");
			}
  			pd.put("ordernumber", ordernumber);
 		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			logger.error(e.toString(), e);
		}
 		map.put("data", pd);
		map.put("result", result);
		map.put("message",message);
		return AppUtil.returnObject(pd, map);
	}
	@Resource(name="storepc_wealthhistoryService")
	private Storepc_wealthhistoryService storepc_wealthhistoryService;
//	
//	/**
//	 * 获取15天内的销售笔数
//	 * @param page
//	 * @return
//	 */
//	@RequestMapping(value="/findNum")
//	@ResponseBody
//	public Object findNum(Page page){
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="获取成功";
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			page.setPd(pd);
//			//获取商家id
//			String store_id = pd.getString("store_id");
//			pd.put("store_id", store_id);
//			String num1 = chartService.findNum(1);
//			String num2 = chartService.findNum(2);
//			String num3 = chartService.findNum(3);
//			String num4 = chartService.findNum(4);
//			String num5 = chartService.findNum(5);
//			String num6 = chartService.findNum(6);
//			String num7 = chartService.findNum(7);
//			String num8 = chartService.findNum(8);
//			String num9 = chartService.findNum(9);
//			String num10 = chartService.findNum(10);
//			String num11 = chartService.findNum(11);
//			String num12 = chartService.findNum(12);
//			String num13 = chartService.findNum(13);
//			String num14 = chartService.findNum(14);
//			String num15 = chartService.findNum(15);
//			if(num1 == null || num1.equals("") || num1.isEmpty()){
//				message="暂无数据";
//				map.put("data", "");
//			}
//			map.put("num1", num1);
//			map.put("num2", num2);
//			map.put("num3", num3);
//			map.put("num4", num4);
//			map.put("num5", num5);
//			map.put("num6", num6);
//			map.put("num7", num7);
//			map.put("num8", num8);
//			map.put("num9", num9);
//			map.put("num10", num10);
//			map.put("num11", num11);
//			map.put("num12", num12);
//			map.put("num13", num13);
//			map.put("num14", num14);
//			map.put("num15", num15);
//		} catch(Exception e){
//			result="0";
//			map.put("message", "获取异常");
//			logger.error(e.toString(), e);
//		}
//		map.put("result", result);
//		map.put("message",message);
//		return AppUtil.returnObject(pd, map);
//	}

}
