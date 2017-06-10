package com.tianer.controller.storeapp.storeapp_member_vip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.tianer.service.storeapp.LyyMember_vipcardService;
import com.tianer.service.storepc.liangqin.homepage.VIPService;
import com.tianer.util.PageData;

/** 
 * 类名称：Member_vipcardController
 * 创建人：刘耀耀
 * 创建时间：2016-07-05
 */
@Controller("Lyymember_vipcardController")
@RequestMapping(value="/storeapp_member_vipcard")
public class Storeapp_member_vipcardController extends BaseController {
	
	@Resource(name="lyymember_vipcardService")
	private LyyMember_vipcardService member_vipcardService;
	@Resource(name="vipService")
	private VIPService vipService;

	/**
	 * 排序列表
	 * 刘耀耀
	 * 2016.07.05
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list(Page page){
		//logBefore(logger, "排序列表");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String type=pd.getString("type");
			pd.put("type", type);
			String nowpage=pd.getString("nowpage");
			if(nowpage == null || nowpage.equals("")){
				nowpage="1";
			}
			page.setCurrentPage(Integer.parseInt(nowpage));
			page.setPd(pd);
			List<PageData> varList=member_vipcardService.listPageByvipmember(page);
			if(varList.size() > 0){
				for (PageData pageData : varList) {
					pageData.put("source_type","1");
					String name=pageData.getString("name");
					String phone=pageData.getString("phone");
					if(name.length()==11){
						pageData.put("name", name.substring(0, 3)+"****"+name.substring(7, 11));
					}
					if(phone.length()==11){
						pageData.put("phone", phone.substring(0, 3)+"****"+phone.substring(7, 11));
					}
 				}
				map.put("data", varList);
			}else if(varList.size()==0){
				map.put("data", "");
			}
 		} catch(Exception e){
			result = "0";
			map.put("data", "");
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("messgae", message);
		return map;
	}
	
	
	
	/**
	 * 会员查询：source_type来源1-平台会员（线上），2-商家会员（线下），3-商家一度人脉，,content模糊查询,pagenumber页数,store_id商家id
	 */
	@RequestMapping(value="/searchList")
	@ResponseBody
	public Object searchList(Page page){
		//logBefore(logger, " 会员查询");
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> vip_list=new ArrayList<PageData>();
 		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
 			pd = this.getPageData();
 			String pagenumber=pd.getString("pagenumber");
 			if(pagenumber == null || pagenumber.equals("")){
 				pagenumber="1";
 			}
 			page.setCurrentPage(Integer.parseInt(pagenumber));
			page.setPd(pd);
			if(pd.getString("source_type") == null || pd.getString("source_type").equals("") || pd.getString("source_type").equals("1")){
 				vip_list=vipService.allVipImagelistPage(page);//平台会员（线上）
				for(PageData e : vip_list){
					e.put("source_type", "1");
					String name=e.getString("name");
					String phone=e.getString("phone");
					if(name.length()==11){
						e.put("name", name.substring(0, 3)+"****"+name.substring(7, 11));
					}
					if(phone.length()==11){
						e.put("phone", phone.substring(0, 3)+"****"+phone.substring(7, 11));
					}
				}
			}else if(pd.getString("source_type").equals("2")){
 				vip_list=vipService.findExcelVIPlistPage(page);//商家会员（线下）
				for(PageData e : vip_list){
					e.put("source_type", "2");
					e.put("now_integral", "0");
					String name=e.getString("name");
					String phone=e.getString("phone");
					if(name.length()==11){
						e.put("name", name.substring(0, 3)+"****"+name.substring(7, 11));
					}
					if(phone.length()==11){
						e.put("phone", phone.substring(0, 3)+"****"+phone.substring(7, 11));
					}
				}
			}else if(pd.getString("source_type").equals("3")){//一度人脉
 				vip_list = vipService.renmaiYiListlistPage(page);//一度人脉集合
				for(PageData e : vip_list){
					e.put("source_type", "3");
					String name=e.getString("name");
					String phone=e.getString("phone");
					if(name.length()==11){
						e.put("name", name.substring(0, 3)+"****"+name.substring(7, 11));
					}
					if(phone.length()==11){
						e.put("phone", phone.substring(0, 3)+"****"+phone.substring(7, 11));
					}
				}
			}
 		} catch(Exception e){
			result = "0";
			map.put("data", "");
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("messgae", message);
		map.put("data", vip_list);
		return map;
	}
	
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
