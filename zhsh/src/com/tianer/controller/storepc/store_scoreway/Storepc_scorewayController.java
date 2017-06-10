package com.tianer.controller.storepc.store_scoreway;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.storepc.liangqin.shopmanage.CategoryManageService;
import com.tianer.service.storepc.store_marketing.Storepc_marketingService;
import com.tianer.service.storepc.store_scoreway.Storepc_scorewayService;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;

/** 
 * 
* 类名称：Storepc_scorewayController   
* 类描述：   积分方式
* 创建人：邢江涛  
* 创建时间：2016年6月12日 下午3:46:35
 */
@Controller("storeStorepc_scorewayController")
@RequestMapping(value = "/storepc_scoreway")
public class Storepc_scorewayController extends BaseController {
	
	@Resource(name = "storepc_scorewayService")
	private Storepc_scorewayService storepcScorewayService; 
	
	@Resource(name = "storepc_marketingService")
	private Storepc_marketingService storepcMarketingService;
	
	@Resource(name = "/categoryManageService")
	private CategoryManageService categoryManageService;
	

	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "01";
		PageData pd = new PageData();
	    DecimalFormat    df   = new DecimalFormat("######0.00"); 
		try {
			pd = this.getPageData();
			//判断是否为积分的分类营销,不是营销的吧所有积分率为0
			if(!pd.getString("change_type").equals("2")){
				PageData e=new PageData();
				e.put("store_id", pd.getString("store_id"));
				e.put("back_rate", "0");
				categoryManageService.updateJfRate(pd);
			}
			String minjf="0";
			//修改本店的会员积分率和推广积分率
			PageData llpd=new PageData();
			String integral_rate=pd.getString("integral_rate");
			if(pd.getString("change_type").equals("1")){//整店送积分
					 minjf=integral_rate;//整店的
					 String service_rate=df.format(Double.parseDouble(integral_rate)/5 );
					 llpd.put("service_rate", service_rate+"%");
					 llpd.put("integral_rate", integral_rate+"%");
					 llpd.put("store_id", pd.getString("store_id"));
					 appStoreService.edit(llpd);
			}else if(pd.getString("change_type").equals("2")){//分类送积分
					 String[] one=integral_rate.split("~");
					 String onejf=df.format(Double.parseDouble(one[0].replaceAll("%", ""))/5);
					 String twojf=df.format(Double.parseDouble(one[1].replaceAll("%", ""))/5);
 					 String service_rate=onejf+"%~"+twojf+"%";
 					 llpd.put("service_rate", service_rate);
					 llpd.put("integral_rate", integral_rate);
					 llpd.put("store_id", pd.getString("store_id"));
					 appStoreService.edit(llpd);
			}else if(pd.getString("change_type").equals("3")){//单品送积分
					String[] one=integral_rate.split("~");
					String onejf=df.format(Double.parseDouble(one[0].replaceAll("%", ""))/5);
					minjf=one[0].replaceAll("%", "");//最低的
					String twojf=df.format(Double.parseDouble(one[1].replaceAll("%", ""))/5);
					String service_rate=onejf+"%~"+twojf+"%";
					 llpd.put("service_rate", service_rate);
					 llpd.put("integral_rate", integral_rate);
					 llpd.put("store_id", pd.getString("store_id"));
					 appStoreService.edit(llpd);
			}else if(pd.getString("change_type").equals("4")){//每单返N积分
					 String service_rate=df.format(Double.parseDouble(integral_rate)/5 );
					 llpd.put("service_rate", service_rate+"分/笔");
					 llpd.put("integral_rate", integral_rate+"分/笔");
					 llpd.put("store_id", pd.getString("store_id"));
					 appStoreService.edit(llpd);
			}else if(pd.getString("change_type").equals("5")){
					String[] one=integral_rate.split("~");
					String onejf=df.format(Double.parseDouble(one[0].replaceAll("%", ""))/5);
					String twojf=df.format(Double.parseDouble(one[1].replaceAll("%", ""))/5);
					String service_rate=onejf+"%~"+twojf+"%";
					 llpd.put("service_rate", service_rate);
					 llpd.put("integral_rate", integral_rate);
					 llpd.put("store_id", pd.getString("store_id"));
					 appStoreService.edit(llpd);
			}
			String store_scoreway_id=BaseController.getTimeID();
			pd.put("store_scoreway_id",store_scoreway_id);//主键
 			storepcScorewayService.save(pd);
 			if(pd.getString("change_type").equals("5")){
 				pd.put("grantrule", pd.getString("grantrule2"));
 			}
			//2、我的营销数据表新增
 			pd.put("marketing_type", "6");
   			pd.put("store_marketing_id", BaseController.getTimeID());	
			pd.put("marketing_id", store_scoreway_id);
			pd.put("starttime", DateUtil.getDay());
			pd.put("endtime", "2020-01-01");
			//先删除后新增
 			storepcMarketingService.deleteByType(pd);
 			String grantrule=pd.getString("grantrule");
			storepcMarketingService.savescore(pd);
			//重新规划商品积分值
			try {
				if(minjf == null || minjf.equals("")){
					minjf="0";
				}
				if(pd.getString("change_type").equals("2")){//分类积分
					System.out.println(pd.getString("allgoods_category_id"));
					String[] allgoods_category_idstr=pd.getString("allgoods_category_id").trim().split("@");
					if(pd.getString("allgoods_category_id").contains("@")){
						for (int i = 0; i < allgoods_category_idstr.length; i++) {
							PageData goodspd=new PageData();
							goodspd.put("ww_goods_category_id", allgoods_category_idstr[i]);
							//获取所有的商品
							List<PageData> good_list = categoryManageService.findEachAllGoods(pd);//商品列表
							for(PageData e : good_list){
								String fljf= e.getString("back_rate");
								if(fljf == null || fljf.equals("")){
									fljf="0";
								}
								String retail_money=e.getString("retail_money");
								if(retail_money == null || retail_money.equals("")){
									retail_money="0";
								}
 	 							String integral_number=df.format(Double.parseDouble(retail_money)*Double.parseDouble(fljf)/100);
								goodspd.put("goods_id", e.getString("goods_id"));
								goodspd.put("integral_number", integral_number);
								goodspd.put("integral_rate", fljf);
								categoryManageService.editGoods(goodspd);
	 						}
							goodspd=null;
						}
					}
 				}else{
 					//获取所有的商品
					List<PageData> good_list = categoryManageService.findEachAllGoods(pd);//商品列表
					for(PageData e : good_list){
						String retail_money=e.getString("retail_money");
						if(retail_money == null || retail_money.equals("")){
							retail_money="0";
						}
						PageData goodspd=new PageData();
						String integral_number=df.format(Double.parseDouble(retail_money)*Double.parseDouble(minjf)/100);
						goodspd.put("goods_id", e.getString("goods_id"));
						goodspd.put("integral_number", integral_number);
						goodspd.put("integral_rate", minjf);
						categoryManageService.editGoods(goodspd);
						goodspd=null;
					}
				}
  			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
			}
		} catch (Exception e) {
			result="0";
			e.printStackTrace();
		}
		map.put("result", result);
		return map;
		
	}
	
//	
//	/**
//	 * 去分类积分列表,获取小类的积分
//	 */
//	@RequestMapping(value="/list")
//	@ResponseBody
//	public ModelAndView list(){
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//  			List<PageData>	varList = categoryManageService.findAllBig(pd);
//			mv.addObject("varList", varList);
//			String jichushezhi=pd.getString("jichushezhi");
//			pd=storepcScorewayService.findById(pd);
//			pd.put("jichushezhi", jichushezhi);
//			mv.addObject("pd",pd);
//			mv.setViewName("storepc/business_marketing3");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
// 		return mv;
//	}
// 
//	
//	
//	
//	/**
//	 * 前往单品积分
//	 */
//	@RequestMapping(value="/goSingleproductIntegral")
//	public ModelAndView goSingleproductIntegral(){
//		ModelAndView mv = this.getModelAndView();
//		//logBefore(logger, "单品积分");
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			String jichushezhi=pd.getString("jichushezhi");
//			pd=storepcScorewayService.findById(pd);
//			if(pd==null){
//				pd=new PageData();
//			}
// 			pd.put("jichushezhi", jichushezhi);
//			pd.put("jichushezhi", jichushezhi);
//			mv.addObject("pd", pd);
//			mv.setViewName("storepc/business_marketing4");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		
//		return mv;
//	}
//	/**
//	 * 交易笔数
//	 */
//	@RequestMapping(value="/gotransaction")
//	public ModelAndView gotransaction(){
//		ModelAndView mv = this.getModelAndView();
//		//logBefore(logger, "交易笔数积分");
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			String jichushezhi=pd.getString("jichushezhi");
//			pd=storepcScorewayService.findById(pd);
//			if(pd==null){
//				pd=new PageData();
//			}
// 			pd.put("jichushezhi", jichushezhi);
//			mv.addObject("pd", pd);
//			mv.setViewName("storepc/business_marketing5");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		
//		return mv;
//	}
//	
//	/**
//	 * 金额阶梯
//	 */
//	@RequestMapping(value="/goAmountLadder")
//	public ModelAndView goAmountLadder(){
//		ModelAndView mv = this.getModelAndView();
//		//logBefore(logger, "金额阶梯积分");
//		List<PageData> jfList=new ArrayList<PageData>();
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			String jichushezhi=pd.getString("jichushezhi");
//			pd.put("change_type", "5");
//			pd=storepcScorewayService.findById(pd);
// 			String[] content=null;
//			if(pd != null){
//				
//				content=pd.getString("grantrule").split(",");
//			}else{
//				pd=new PageData();
//			}
//			pd.put("jichushezhi", jichushezhi);
// 			for(int i=0;i<3; i++){
//				PageData e=new PageData();
//				if(content !=  null && content.length-1 >= i){
//					int n1=content[i].indexOf("满");
//					int n2=content[i].indexOf("元");
//					int n3=content[i].indexOf("送");
//					int n4=content[i].indexOf("%");
//					e.put("money", content[i].subSequence(n1+1, n2));
//					e.put("rate", content[i].subSequence(n3+1, n4));
//				}else{
//					e.put("money", "");
//					e.put("rate", "0");
//				}
// 				jfList.add(e);
//			}
//			mv.addObject("pd", pd);
//			mv.addObject("jfList", jfList);
//			mv.setViewName("storepc/business_marketing6");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		
//		return mv;
//	}

}
