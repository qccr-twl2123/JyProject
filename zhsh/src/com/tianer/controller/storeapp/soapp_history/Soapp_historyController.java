package com.tianer.controller.storeapp.soapp_history;

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
import com.tianer.service.business.store_shift.Store_shiftService;
import com.tianer.service.storeapp.Soapp_historyService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
import com.tianer.service.storepc.store_wealthhistory.Storepc_wealthhistoryService;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;

/** 
* 类名称：Storeapp_shiftController
* 创建人：邢江涛  app班次汇总
* 创建时间：2016-06-30 
*/
@Controller("soapp_historyController")
@RequestMapping(value="/storeapp_shift")
public class Soapp_historyController extends BaseController{
	
	@Resource(name = "soapp_historyService")
	private Soapp_historyService historyService;
	
	@Resource(name="store_shiftService")
	private Store_shiftService store_shiftService;
	@Resource(name="storeManageService")
	private StoreManageService storeManageService;
	
	@Resource(name = "storepc_wealthhistoryService")
	private Storepc_wealthhistoryService wealthhistoryService;
	
	/**
	 * 班次汇总
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/listAll")
	@ResponseBody
	public Object listAll(){
		Map<String,Object> map = new HashMap<String,Object>();
 		List<PageData> tjList=new ArrayList<PageData>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String login_type = pd.getString("login_type");
			String login_id = pd.getString("login_id");
			if(login_type != null ){
				if(login_type.equals("1")){
					pd.put("store_id", login_id);
				}else{
					pd.put("store_operator_id", login_id);
				}
 			}
			//获取操作员
			List<PageData> soplist=storeManageService.listAllOperator(pd);
			for(PageData e : soplist){
				 PageData sumoptator=ServiceHelper.getStorepc_wealthhistoryService().BanCiHuiZongByOprator(e);
				 sumoptator.put("ordernumber", ServiceHelper.getStorepc_wealthhistoryService().countOrderNumberByOprator(e));
				 sumoptator.put("logintime", e.getString("logintime"));
				 sumoptator.put("downtime", e.getString("downtime"));
				 sumoptator.put("operator_name", e.getString("operator_name"));
				 sumoptator.put("shift_name", e.getString("shift_name"));
 				 tjList.add(sumoptator);
				 sumoptator=null;
 			}
			if(tjList.size() == 0){
				map.put("data", "");
			}else{
				PageData e =tjList.get(0);
				map.put("data", e);
			}
 		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			map.put("data","");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return map;
	}
	
	
	
	/**
	 * 班次明细
	 * storeapp_shift/listinfo.do
	 */
	@RequestMapping(value="/listinfo")
	@ResponseBody
	public Object listInfo( Page page ){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			String login_type = pd.getString("login_type");
			String login_id = pd.getString("login_id");
			if(login_type != null ){
				if(login_type.equals("1")){
					pd.put("store_id", login_id);
				}else{
					pd.put("store_operator_id", login_id);
				}
 			}
			String nowpage=pd.getString("nowpage");
			if(nowpage==null || nowpage.equals("")){
					nowpage="1";
			}
			page.setCurrentPage(Integer.parseInt(nowpage));
	 		page.setPd(pd);
			List<PageData> varList = historyService.nowlistPageAll(page);
			if(varList.size()==0){
 				map.put("data", "");
			}else{
				map.put("data", varList);
			}
			varList=null;
		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return map;
	}
	
	
	/**
	 * 操作员信息:login_type,login_id
 	 */
	@RequestMapping(value="/listoperator")
	@ResponseBody
	public Object listoperator(){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String login_type = pd.getString("login_type");
			String login_id = pd.getString("login_id");
			if(login_type.equals("1")){
				pd.put("store_id", login_id);
			}else{
				pd.put("store_operator_id", login_id);
			}
			//操作员信息
			List<PageData> operatorList = historyService.listoperator(pd);
			if(login_type.equals("2")){
				pd.put("store_shift_id", operatorList.get(0).getString("store_shift_id"));
			} 
			//班次信息
			List<PageData> shiftList = historyService.shift(pd);
 			map1.put("operatorList", operatorList);
			map1.put("shiftList", shiftList);
 		} catch(Exception e){
			result="0";
			message="获取异常";
 		}
		map.put("result", result);
		map.put("message",message);
		map.put("data",map1);
		return  map;
	}
}
