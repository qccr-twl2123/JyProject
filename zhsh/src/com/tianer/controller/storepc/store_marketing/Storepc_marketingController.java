package com.tianer.controller.storepc.store_marketing;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.entity.Page;
import com.tianer.entity.zhihui.Qx;
import com.tianer.entity.zhihui.StoreRole;
import com.tianer.service.storepc.liangqin.shopmanage.CategoryManageService;
import com.tianer.service.storepc.store_discountway.Storepc_discountwayService;
import com.tianer.service.storepc.store_marketing.Storepc_marketingService;
import com.tianer.service.storepc.store_marketingeffect.Storepc_marketingeffectService;
import com.tianer.service.storepc.store_marketingtype.Storepc_marketingtypeService;
import com.tianer.service.storepc.store_redpackets.Storepc_redpacketsService;
import com.tianer.service.storepc.store_scoreway.Storepc_scorewayService;
import com.tianer.service.storepc.store_way.Storepc_wayService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;


/** 
 * 
* 类名称：Storepc_scorewayController   
* 类描述：   管理我的营销（营销记录）
* 创建人：邢江涛  
* 创建时间：2016年6月13日 下午2:59:35
 */
@Controller("storeStorepc_marketingController")
@RequestMapping(value="/storepc_marketing")
public class Storepc_marketingController extends BaseController{
	
	@Resource(name = "storepc_marketingService")
	private Storepc_marketingService storepcMarketingService;
	@Resource(name = "storepc_marketingeffectService")
	private Storepc_marketingeffectService storepcMarketingeffectService;
	@Resource(name = "storepc_wayService")
	private Storepc_wayService storepc_wayService;
	@Resource(name = "storepc_discountwayService")
	private Storepc_discountwayService storepc_discountwayService;
	@Resource(name = "storepc_scorewayService")
	private Storepc_scorewayService storepc_scorewayService;
	@Resource(name = "storepc_marketingtypeService")
	private Storepc_marketingtypeService storepc_marketingtypeService;
	
 	public DecimalFormat df2=TongYong.df2;

	/**
	 * 前往我的营销列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		ModelAndView mv = this.getModelAndView();
		//logBefore(logger, "列表storepc_marketing");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = storepcMarketingService.list(page);	//列出Store列表
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		mv.setViewName("storepc/business_marketing17");
		return mv;
	}
	
	/**
	 * 删除我的营销
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(){
		//logBefore(logger, "删除我的营销");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "01";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			String marketing_type=pd.getString("marketing_type");
			String marketing_id=pd.getString("marketing_id");
			//1-满赠，2-满减，3-时段营销，4-买N减N，5-购买次数， 6-积分设置，7折扣设置。
			if(marketing_type.equals("1")){
				pd.put("store_marketingtype_id", marketing_id);
				storepc_marketingtypeService.delete(pd);
			}else if(marketing_type.equals("2")){
				pd.put("store_marketingtype_id", marketing_id);
				storepc_marketingtypeService.delete(pd);
			}else if(marketing_type.equals("3")){
				pd.put("store_marketingtype_id", marketing_id);
				storepc_marketingtypeService.delete(pd);
			}else if(marketing_type.equals("4")){
				pd.put("store_marketingtype_id", marketing_id);
				storepc_marketingtypeService.delete(pd);
			}else if(marketing_type.equals("5")){
				pd.put("store_marketingtype_id", marketing_id);
				storepc_marketingtypeService.delete(pd);
			}else if(marketing_type.equals("6")){
				pd.put("store_scoreway_id", marketing_id);
				storepc_scorewayService.deleteById(pd);
			}else if(marketing_type.equals("7")){
				pd.put("store_discountway_id", marketing_id);
				storepc_discountwayService.deleteById(pd);
			}
			storepcMarketingService.delete(pd);//删除我的营销
		} catch(Exception e){
			result="00";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
 		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	@ResponseBody
	public Object edit() throws Exception{
		//logBefore(logger, "删除我的营销");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "01";
		PageData pd = new PageData();
		try{
					 pd = this.getPageData();
					 //获取当日启动数据的总数
					 int count = (int) storepcMarketingService.count(pd);
					 String status = pd.getString("open_status");
					 //判断总数小于5或者点击关闭时进入修改状态
					if(count < 5 || status.equals("0")){
							//启动时间
							pd.put("daytime", DateUtil.getDays());
							storepcMarketingService.edit(pd);
							String stauts = pd.getString("open_status");
							String grantrule = pd.getString("grantrule");
							String marketing_type = pd.getString("marketing_type");
							//启动时添加数据进记录表
							if(stauts.equals("1")){
								pd.put("store_marketingeffect_id", BaseController.getTimeID());
								pd.put("marketing_type",marketing_type );
								pd.put("content", grantrule);
								storepcMarketingeffectService.save(pd);
							}
					}else {
							String info = "已发布5种营销方案";
							map.put("message", info);
							result="00";
			 		}
		}catch(Exception e){
			result="00";
			logger.error(e.toString(), e);
		}
 		map.put("result", result);
		return map;
	}
	
	
	

	/**
	 * 去消费支付方式页面
	 * 魏汉文0717
	 */
	@RequestMapping(value="/goZhifu")
	public ModelAndView goZhifu(){
		ModelAndView mv = this.getModelAndView();
		//logBefore(logger, "去消费级积分支付页面");
		PageData pd = new PageData();
		try {
			pd=this.getPageData();
			String jichushezhi=pd.getString("jichushezhi");
			String store_id=pd.getString("store_id");
			pd=storepc_wayService.findById(pd);
			if(pd != null){
				String ty = pd.getString("way_type");
				if(ty != null && !ty.equals("")){
					String[] type=ty.split(",");
					pd.put("type1","0");
					pd.put("type2", "0");
					if(type[0].equals("1")){
						pd.put("type1", "1");
					}
					if(type[1].equals("2")){
						pd.put("type2", "1");
					}
					if(pd != null){
						pd.put("zfb", "0");
						pd.put("wx", "0");
						pd.put("pos","0");
						pd.put("apple", "0");
						String[] status=pd.getString("way_status").split(",");
						for(int i=0;i<status.length ;i++){
								if(status[i].equals("1")){
									pd.put("zfb", "1");
								}else if(status[i].equals("2")){
									pd.put("wx", "1");
								}else if(status[i].equals("3")){
									pd.put("pos","1");
								}else if(status[i].equals("4")){
									pd.put("apple", "1");
								}
						}
 					}
				}
				this.getHC();
			}else{
				pd=new PageData();
			}
			pd.put("store_id", store_id);
   			if(jichushezhi != null && jichushezhi.equals("11111110000")){
  				mv.setViewName("/storepc/shezhi_7");
  				pd.put("jichushezhi", jichushezhi);
  			}else{
  				mv.setViewName("storepc/business_marketing1");
  			}
   		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.toString() );
		}
		mv.addObject("pd",pd);
 		return mv;
	}
	
	
	@Resource(name = "storepc_scorewayService")
	private Storepc_scorewayService storepcScorewayService; 
	@Resource(name = "/categoryManageService")
	private CategoryManageService categoryManageService;
	
	
	/**
	 * 去级积分方式页面
	 * 魏汉文0718
	 */
	@RequestMapping(value="/goIntegral")
	public ModelAndView goIntegral(){
		ModelAndView mv = this.getModelAndView();
		List<PageData> jfList=new ArrayList<PageData>();
		//logBefore(logger, "去级积分方式页面");
 		PageData pd = new PageData();
 		try{
				pd = this.getPageData();
 				String jichushezhi=pd.getString("jichushezhi");
 				String store_id=pd.getString("store_id");
 				String change_type=pd.getString("change_type");
 				if(change_type == null){
 					change_type="1";
 				}
   				pd.remove("change_type");
	  			pd=storepcScorewayService.findById(pd);
	  			if(pd == null){
	             	pd=new PageData();
	             	mv.addObject("shezhi","0");
	            }else{
	            	mv.addObject("shezhi","1");
	            	if(change_type == "1"){
	            		change_type=pd.getString("change_type");
	            	}
 	            }
	  			mv.addObject("change_type",change_type);
	  			 pd.put("store_id", store_id);
  	  			//----1-总设置，2-分类，3-单品，4-交易笔数，5-阶梯
  				if(change_type.equals("2")){
 					List<PageData>	varList = categoryManageService.findAllBig(pd);
 					mv.addObject("varList", varList);
					pd=storepcScorewayService.findById(pd);
					if(pd==null){
						pd=new PageData();
					}
//					mv.setViewName("storepc/business_marketing3");
  				}else if(change_type.equals("3")){
					pd=storepcScorewayService.findById(pd);
					if(pd==null){
						pd=new PageData();
					}
//					mv.setViewName("storepc/business_marketing4");
				}else if(change_type.equals("4")){
					pd=storepcScorewayService.findById(pd);
					if(pd==null){
						pd=new PageData();
					}
//					mv.setViewName("storepc/business_marketing5");
				}else if(change_type.equals("5")){
					pd.put("change_type", "5");
					pd=storepcScorewayService.findById(pd);
		 			String[] content=null;
					if(pd != null){
						content=pd.getString("grantrule").split(",");
					}else{
						pd=new PageData();
					}
		 			for(int i=0;i<3; i++){
						PageData e=new PageData();
						if(content !=  null && content.length-1 >= i){
							int n1=content[i].indexOf("满");
							int n2=content[i].indexOf("元");
							int n3=content[i].indexOf("送");
							int n4=content[i].indexOf("%");
							e.put("money", content[i].subSequence(n1+1, n2));
							e.put("rate", content[i].subSequence(n3+1, n4));
						}else{
							e.put("money", "");
							e.put("rate", "0");
						}
		 				jfList.add(e);
					}
//					mv.setViewName("storepc/business_marketing6");
				}else{
//					mv.setViewName("storepc/business_marketing2");
				} 
	            if(jichushezhi != null && jichushezhi.equals("11111111000")){
	            	mv.setViewName("storepc/shezhi_8");
	            	mv.addObject("jichushezhi",jichushezhi);
	            }else{
	            	mv.setViewName("storepc/business_marketing2_6");
	            }
	            pd.put("store_id", store_id);
	     		mv.addObject("pd",pd);
	   			mv.addObject("jfList", jfList);
	  			jfList=null;
	  			pd=null;
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		return mv;
	}
	
	
	@Resource(name = "storepc_discountwayService") 
	private Storepc_discountwayService storepcDiscountwayService;
	
	/**
	 * 去折扣设置页面
 	 */
	@RequestMapping(value="/goDiscount")
	public ModelAndView goDiscount(){
		ModelAndView mv = this.getModelAndView();
		//logBefore(logger, "去折扣设置页面");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String store_id=pd.getString("store_id");
			String type=pd.getString("type");
			String discount_type=pd.getString("discount_type");
			pd=storepcDiscountwayService.findById(pd);
			if(type.equals("0") ){
   				if(pd != null){
  					discount_type=pd.getString("discount_type");
  				}else{
  					discount_type="1";
  				}
  			} 
    		if(pd == null){
  				pd=new PageData();
  				pd.put("store_id", store_id);
    		}
  			if(discount_type.equals("2")){
				//大类折扣显示列表
				List<PageData>	varList = categoryManageService.findAllBig(pd);
				mv.addObject("varList", varList);
				varList=null;
//				mv.setViewName("redirect:../storepc_discountway/list.do?store_id="+pd.getString("store_id"));
			}else if(discount_type.equals("3")){
				//件数折扣
//				mv.setViewName("redirect:../storepc_discountway/goNumberDiscount.do?store_id="+pd.getString("store_id"));
			}else if(discount_type.equals("4")){
				//金额阶梯
				List<PageData> zkList=new ArrayList<PageData>();
				String[] content=null;
				if(pd != null && pd.getString("grantrule") != null && pd.getString("grantrule").contains(",") ){ 
					content=pd.getString("grantrule").split(",");
				}
	 			for(int i=0;i<3; i++){
					PageData e=new PageData();
					if(content !=  null && content.length-1 >= i){
						int n1=content[i].indexOf("满");
						int n2=content[i].indexOf("元");
						int n3=content[i].indexOf("打");
						int n4=content[i].indexOf("折");
						e.put("money", content[i].subSequence(n1+1, n2));
						e.put("rate", content[i].subSequence(n3+1, n4));
					}else{
						e.put("money", "");
						e.put("rate", "");
					}
					zkList.add(e);
					e=null;
				}
					mv.addObject("zkList", zkList);
				zkList=null;
//				mv.setViewName("redirect:../storepc_discountway/goAmountLadderDiscount.do?store_id="+pd.getString("store_id"));
			}  
   			mv.addObject("discount_type", discount_type);
 			mv.setViewName("storepc/business_marketing8_11");
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd",pd);
 		return mv;
	}
	
	@Resource(name = "storepc_marketingtypeService")
	private Storepc_marketingtypeService storepcMarketingTypeService;
	
	@Resource(name = "storepc_redpacketsService")
	private Storepc_redpacketsService storepcRedpacketsService;
	
	
	/**
	 * 满赠页面
	 */
	@RequestMapping(value="/goGive")
	public ModelAndView goGive(){
		ModelAndView mv = this.getModelAndView();
		//logBefore(logger, "去级积分方式页面");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("marketing_type","1");
			//获取已经发行的列表
			List<PageData> mzList=storepcMarketingTypeService.listAllByType(pd);
			mv.addObject("mzList",mzList);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd",pd);
		mv.setViewName("storepc/business_marketing12");
		return mv;
	}
	
	
	/**
	 * 去级积分方式页面
	 * 刘耀耀
	 * 2016.07.09
	 */
	@RequestMapping(value="/goAccumulate")
	public ModelAndView goAccumulate(){
		ModelAndView mv = this.getModelAndView();
		//logBefore(logger, "去级积分方式页面");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd=storepcScorewayService.findById(pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd",pd);
		mv.setViewName("storepc/business_marketing16");
		return mv;
	}
	
	 /**
	 *
	 *	修改过期红包状态 
	 * 	刘耀耀
	 * 	2016.07.19
	 */
	@RequestMapping(value="/editTime")
	@ResponseBody
	public Object editTime(Page page){
		//logBefore(logger, "修改状态");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改成功";
		PageData pd = new PageData();
		try{
			SimpleDateFormat splt=new SimpleDateFormat("yyyy-MM-dd");
			String data=splt.format(new Date());//当天时间
			pd.put("data",data);
			String store_marketingtype_id=pd.getString("store_marketingtype_id");
			pd.put("store_marketing_id",store_marketingtype_id);
			storepcMarketingService.editTime(pd);
		} catch(Exception e){
			result="0";
			message="修改异常";
			logger.error(e.toString(), e);
		}
		map.put("data", "");
		map.put("result", result);
		map.put("message",message);
		return map;
	}
	
	/**
	 * 修改排序
	 */
	@RequestMapping(value="/updSort")
	@ResponseBody
	public Object updSort(){
		//logBefore(logger, "修改排序");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
 		PageData pd = new PageData();
 		try{
			pd = this.getPageData();
			PageData e =storepcMarketingService.findBySort(pd);
			if(e != null){
				result="2";
			}else {
				storepcMarketingService.updateSort(pd);
			}
		} catch(Exception e){
			result="0";
			logger.error(e.toString(), e);
		}
 		map.put("data", "");
		map.put("result", result);
		return map;
	}
 
	/* ===============================营销权限================================== */
	public void getHC(){
			ModelAndView mv = this.getModelAndView();
			HttpServletRequest request=this.getRequest();
			HttpSession pcsession=request.getSession();
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();	
			StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
 			Qx qx=new Qx();
			if(slogin != null){
				Map<String,Object> map=slogin.getMap();
				qx=(Qx) map.get("yx");
				pcsession.setAttribute("storeqx", qx);
 			}
			mv.addObject("qx", qx);
	}
	/* ===============================权限================================== */

}
