package com.tianer.controller.memberpc;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.service.memberPc.PcShopCarService;
import com.tianer.service.memberPc.PcTongYongService;
import com.tianer.service.memberPc.Storepc_fileService;
import com.tianer.util.Const;
import com.tianer.util.EbotongSecurity;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.address.BaiDuUtil;

/** 
 * 类名称：PcShouYeController
 * 创建人：魏汉文(首页所需的接口)
 * 创建时间：2016-06-20
 */
@Controller("pcShouYeController")
//@RequestMapping(value="/memberpc_shouye")
public class PcShouYeController extends BaseController {
	
	@Resource(name="pcShopCarService")
	private PcShopCarService pcShopCarService;
 	
	@Resource(name="pcTongYongService")
	private PcTongYongService pcTongYongService;
	
	@Resource(name="storepc_fileService")
	private Storepc_fileService storepc_fileService;
	
	
	/**
	 * 登录成功后跳转到pc会员首页
	 */
	@RequestMapping(value="/jiuyusy")
	public ModelAndView jiuyusy(){
 		ModelAndView mv = this.getModelAndView();
 		String member_id=(String) this.getRequest().getSession().getAttribute("jylogin_id");
   		PageData pd = new PageData();
 		try { 
  			pd=this.getPageData();
   			//获取所有的市
    		List<PageData> cityList=pcTongYongService.listAllCity(pd);
   			mv.addObject("cityList",cityList);
	  		//获取当前的所在位置-精确查询api
	  		if(pd.getString("area_name") == null){
	  			pd=BaiDuUtil.getIPXY(pd);
	  		}
  			//更新会员信息
  			pd.put("member_id", member_id);
			if(pcTongYongService.detailMemberById(pd) != null ){
				//更新当前用户的位置：经度纬度，省市区
				pcTongYongService.editMember(pd);
 				//统计购物车的数量
 				this.getRequest().getSession().setAttribute("shopnumber", pcShopCarService.shopCarCount(pd));
 			}
     		//获取城市档案ID以及城市营销参数
  			PageData citypd=pcTongYongService.getCityMarketingForPCD(pd);
  			//获取当前城市的所有的广告
	  		if(citypd != null){
		        	citypd.put("pc_type", "4");
					List<PageData> advertList= pcTongYongService.allPcPictureById(citypd); 
					mv.addObject("advertList",advertList);
					//获取当前城市的关键字
					PageData keyword=pcTongYongService.listAllcontent(citypd).get(0);
	  	   			List<String> keywordList = Arrays.asList(keyword.getString("keyword").split("@"));
	  	   			mv.addObject("keywordList",keywordList);
	  	   			//获取当前地级市下的所有开通的区域
		  	   		List<PageData> areaList=pcTongYongService.listAllArea(citypd);
	   	   			mv.addObject("areaList",areaList);
	  	   			keywordList=null;
	  	   			advertList=null;
	  	   			areaList=null;
	  	   			//获得一级分类
	  	   			citypd.put("sort_parent_id", "0");
	  	   			citypd.put("sort_type", "1");
	  	   			List<PageData> firstList=pcTongYongService.listAllCitySort(citypd);
	  	   			mv.addObject("leftfirstsort",firstList);
	  	   			citypd.remove("sort_parent_id");
	  	   			citypd.remove("sort_type");
	  	   			PageData e=null;
	  	   			PageData twopd=new PageData();
	  	   			int n=firstList.size();
	  	   			for (int i = 0; i < n; i++) {
						e=firstList.get(i);
 		 				//获得二级分类
						twopd.put("city_file_id", e.getString("city_file_id"));
						twopd.put("sort_parent_id", e.getString("city_file_sort_id"));
						twopd.put("sort_type", "2");
						List<PageData> twoList=pcTongYongService.listAllCitySort(twopd);
	 	  				e.put("twoList", twoList);
	 	  				twoList=null;
	 	  				//获取二级分类下的商家 8个
	 	  				List<PageData> storeList=storepc_fileService.listAllForCitysort(e);
	 	  				for(PageData e1 : storeList){
	 	  							//所有交易笔数
 	 	  							e1.put("transaction_times",ServiceHelper.getStorepc_wealthhistoryService().getallCountOrder(e1));
			 	  					//获取营销规则1-满赠，2-满减，3-时段营销，4-买N减N，5-购买次数，6-积分收益,7-折扣
			 						List<PageData> marketlist=pcTongYongService.listAllMarketingLimitTwo(e1);
			 						e1.put("marketlist", marketlist);
			 						//对store_id进行加密
		 							e1.put("new_store_id",BaseController.get4ZMSZ()+EbotongSecurity.ebotongEncrypto(e1.getString("store_id")));
  	 	  				}
	 	  				e.put("storeList", storeList);
	 	  				storeList=null;
	  			}
	  	   		mv.addObject("firstList", firstList);
	  	   		twopd=null;
	  	   		e=null;
  				firstList=null;
  				//获取这个地区的热门商家--暂定
  				
  				
	 	    }
	  		pd.remove("member_id");
 			mv.addObject("pd", pd);
 		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
 		mv.setViewName("memberpc/business_Buyers0");
		return mv;
 	}
	
	
	/**
	 * 列表获取区
	 * memberpcListAllArea.do
	 */
	@RequestMapping(value="/memberpcListAllArea")
	@ResponseBody
	public Object MemberpcListAllArea(){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
  			List<PageData>	varList = pcTongYongService.listAllArea(pd);//列出City_file列表
  			map.put("data", varList);
			varList=null;
			pd=null;
		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	/**
	 * 获得所有商家 memberpclistAllStore
 	 * pauxu:  1-智能排序（综合分值为基数的），2-距离近到远，3-人气高到低，4-积分率从高到底，5-销售量从高到底，6-最新上线
	 * shaixuan:    1-首单立减（满减），2-折扣商品（折扣），3-满返代金卷（满赠）,
	 */
	@RequestMapping(value="/memberpclistAllStore")
	public ModelAndView MemberpcListAllStore(Page page){
 			ModelAndView mv = this.getModelAndView();
   			PageData pd = new PageData();
   			try { 
		 			pd = this.getPageData();
		 			//获取所有的市
		    		List<PageData> cityList=pcTongYongService.listAllCity(pd);
		   			mv.addObject("cityList",cityList);
		   			//获取城市档案ID以及城市营销参数
		  			PageData citypd=pcTongYongService.getCityMarketingForPCD(pd);
		   			if(citypd != null ){
						//获取当前城市的关键字
						PageData keyword=pcTongYongService.listAllcontent(citypd).get(0);
		  	   			List<String> keywordList = Arrays.asList(keyword.getString("keyword").split("@"));
		  	   			mv.addObject("keywordList",keywordList);
		  	   			//获取当前地级市下的所有开通的区域
			  	   		List<PageData> areaList=pcTongYongService.listAllArea(citypd);
		   	   			mv.addObject("areaList",areaList);
		  	   			keywordList=null;
 		  	   			areaList=null;
		  	   			//获得一级分类
		  	   			citypd.put("sort_parent_id", "0");
		  	   			citypd.put("sort_type", "1");
		  	   			List<PageData> firstList=pcTongYongService.listAllCitySort(citypd);
		  	   			mv.addObject("leftfirstsort",firstList);
		  	   			citypd.remove("sort_parent_id");
		  	   			citypd.remove("sort_type");
		  	   			PageData e=null;
		  	   			PageData twopd=new PageData();
		  	   			int n=firstList.size();
		  	   			for (int i = 0; i < n; i++) {
							e=firstList.get(i);
	 		 				//获得二级分类
							twopd.put("city_file_id", e.getString("city_file_id"));
							twopd.put("sort_parent_id", e.getString("city_file_sort_id"));
							twopd.put("sort_type", "2");
							List<PageData> twoList=pcTongYongService.listAllCitySort(twopd);
		 	  				e.put("twoList", twoList);
		 	  				twoList=null;
		  	   			}
			  	   		mv.addObject("firstList", firstList);
			  	   		twopd=null;
			  	   		e=null;
		  				firstList=null;
 		   			}
  					boolean isdingwei=true;
					if(pcTongYongService.detailMemberById(pd) != null){
 	 						//判断是否定位了
 							if(pcTongYongService.detailMemberById(pd).getString("longitude").equals("--")){
								isdingwei=false;
								pd.put("longitude", "0");
								pd.put("latitude", "0");
 							}else{
 								pd.put("longitude", pcTongYongService.detailMemberById(pd).getString("longitude"));
								pd.put("latitude",pcTongYongService.detailMemberById(pd).getString("latitude"));
							}
					}else{
						isdingwei=false;
						pd.put("longitude", "0");
						pd.put("latitude", "0");
					}
					page.setShowCount(30);
 					page.setPd(pd);
 		 			List<PageData> storeList= storepc_fileService.getStorelistPage(page); 
 		 			PageData e1=null;
 		 			int m=storeList.size();
		 			for(int i=0;i< m  ;i++){
		 					 e1=storeList.get(i);
 		 					//获取营销规则1-满赠，2-满减，3-时段营销，4-买N减N，5-购买次数，6-积分收益,7-折扣
		 					List<PageData> marketlist=pcTongYongService.listAllMarketingLimitTwo(e1);
		 					e1.put("marketlist", marketlist);
 							if(!isdingwei){
								e1.put("distance", e1.getString("distance")+"km");
							}else{
								if(Double.parseDouble(e1.getString("distance") )-Const.maxjuli > 0 ){
									e1.put("distance", Const.maxjuli+"km+");
								}else{
									e1.put("distance", e1.getString("distance")+"km");
								}
							}
 							//对store_id进行加密字段名
 							e1.put("new_store_id",BaseController.get4ZMSZ()+EbotongSecurity.ebotongEncrypto(e1.getString("store_id")));
    	  			}
 		 			mv.addObject("storeList", storeList);
		 			storeList=null;
		 			pd.remove("member_id");
					mv.addObject("pd", pd);
					pd=null;
		  			mv.setViewName("memberpc/business_Buyers2");
    		} catch (Exception e) {
 			 logger.error(e.toString(), e);
 		 	}
   			return mv;
 	}

	
	public static void main(String[] args) {
		int count=0;
		for (int i = 0; i < 10; i++) {
 			count=++count;
 		}
		System.out.println(count);
	}
}
