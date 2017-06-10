/**
 * 
 */
package com.tianer.controller.storepc.liangqin.shopmanage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.entity.storepc.liangqin.Goods;
import com.tianer.entity.storepc.liangqin.GoodsAndCate;
import com.tianer.entity.zhihui.Qx;
import com.tianer.entity.zhihui.StoreRole;
import com.tianer.service.business.city_marketing.City_marketingService;
import com.tianer.service.business.cm_all.Cm_allService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
import com.tianer.service.storepc.liangqin.shopmanage.CategoryManageService;
import com.tianer.service.storepc.store_marketing.Storepc_marketingService;
import com.tianer.service.storepc.store_marketingtype.Storepc_marketingtypeService;
import com.tianer.service.storepc.store_scoreway.Storepc_scorewayService;
import com.tianer.service.storepc.stotr.StorepcService;
import com.tianer.util.Const;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;

/**
 * 类名称: Storepc_CategoryManageController 
 * 类描述: 类别管理控制层 
 * 公司: 天尔西安研发中心
 * 魏汉文
 * 创建时间:
 * 2016-6-21 上午11:43:08 
 * 版本号: v1.0
 */
@Controller
@RequestMapping(value = "/storepc_CategoryManageController")
public class Storepc_CategoryManageController extends BaseController {

	@Resource(name = "/categoryManageService")
	private CategoryManageService categoryManageService;
	
	/*
	 * OK
	 */
	@RequestMapping(value = "/delCate")
	@ResponseBody
	public Object delCate() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		PageData pd=new PageData();
		String result="1";
		String message="删除成功";
		pd=this.getPageData();
		try {
			//先判断是否还存在商品
			int n=categoryManageService.selectCateXiaoGoods(pd);
			if(n == 0){
				categoryManageService.deleteCate(pd);
			}else{
				result="0";
				message="请先删除商品";
			}
 		} catch (Exception e) {
			// TODO: handle exception
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}

	
	/*
	 * OK
	 */
	@RequestMapping(value = "/editCate")
	public ModelAndView editCate() throws Exception {
		ModelAndView modelAndView = this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		categoryManageService.editCate(pd);
		modelAndView.setViewName("redirect:showShop.do?store_id="+pd.getString("store_id"));
		return modelAndView;
	}

	/*
	 * OK
	 */
	@RequestMapping(value = "/showShop")
	public ModelAndView showShop(Page page) throws Exception {
				ModelAndView modelAndView = this.getModelAndView();
				List<PageData> allList =new ArrayList<PageData>();
				//shiro管理的session
				Subject currentUser = SecurityUtils.getSubject();  
				Session session = currentUser.getSession();	
				StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
				if(slogin != null){
						this.getHC();
						String store_id=slogin.getStore_id();
						PageData pd = this.getPageData();
						try{
							pd=this.getPageData();
							if(pd.getString("store_id") == null || pd.getString("store_id").equals("")){
								pd.put("store_id", store_id);
							}
					 		page.setPd(pd);
					   		List<PageData> big_list = categoryManageService.findAllBig(pd);//获取商家所有的大类---用来新建小类
					  		List<PageData> small_list = categoryManageService.findAllSmalllistPage(page);//获取商家所有的小类--分页十条
					  		for(PageData e : big_list){
					  				List<PageData> sonList =new ArrayList<PageData>();
 					   				for(PageData e1 : small_list){
					  						if(e.getString("goods_category_id").equals(e1.getString("category_parent_id"))){
 					   							sonList.add(e1);
 					  						}
					  				}
 	 					   			Collections.sort(sonList,new Comparator<PageData>(){  
	 				 		            @Override
	 									public int compare(PageData  arg0, PageData arg1) {  
	 				 		            	String n=arg0.getString("sort");
	 				 		            	if(n.length() == 1){
	 				 		            		n="0"+n;
	 				 		            	}
	 				 		            	String m=arg1.getString("sort");
	 				 		            	if(m.length() == 1){
	 				 		            		m="0"+m;
	 				 		            	}
 	 				 		                return n .compareTo(m);  
	 				 		            }  
	 				 		        });  
	 					   			e.put("sonList", sonList);
//	 					   			for(PageData s : sonList){
//	 					   				//System.out.println(s);
//	 					   			}
						   			allList.add(e);
						   			sonList=null;
   					   		}
 					  		modelAndView.addObject("allList", allList);
					  		allList=null;
							modelAndView.addObject("big_list", big_list);
					  		big_list=null;
							modelAndView.addObject("pd", pd);
							pd=null;
						}catch(Exception e){
							//System.out.println(e.toString());
							logger.error(e.toString(), e);
						}
				}
  		 		modelAndView.setViewName("/storepc/business_shop1");
				return modelAndView;
	}
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;

	/**
	 * 
	* 方法名称:：addBig OK
	* 方法描述：新增大类
	* 创建人：魏汉文
	* 创建时间：2016年7月16日 上午9:09:36
	 */
	@RequestMapping(value = "/addBig")
	@ResponseBody
	public Object addBig(Page page) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
 		PageData pd = new PageData();
 		String result="1";
 		String message="新增成功";
		pd = this.getPageData();
		PageData e=appStoreService.findById(pd);
 		int m=Integer.parseInt( e.getString("bigtype_max") );
 		//获取商家所有的大类---用来新建小类
		int n=Integer.parseInt( categoryManageService.countBig(pd));
		//System.out.println(m+"&"+n);
		if(m <=  n){
			result="0";
			message="大类已达上限,上限数量为"+m;
		}else{
			pd.put("category_parent_id", "0");
			pd.put("goods_category_id",BaseController.getTimeID());
	 		categoryManageService.addBig(pd);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	/**
	 * 
	* 方法名称:：sortOk OK
	* 方法描述：新增大类/小类
	* 创建人：魏汉文
	* 创建时间：2016年7月16日 上午9:09:36
	 */
	@RequestMapping(value = "/sortOk")
	@ResponseBody
	public Object sortOk(   ) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
 		PageData pd = new PageData();
 		String result="1";
 		String message="可以新增";
		pd = this.getPageData();
 		try {
 			 pd=categoryManageService.findCaBySort(pd);
			 if(pd != null){
				 result="0";
				 message="当前排序已存在请重新选择";
			 }
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString());
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	/**
	 * 
	* 方法名称:：editRate 
	* 方法描述：修改积分率
	* 创建人：魏汉文
	* 创建时间：2016年7月18日 上午11:37:28
	 */
	@RequestMapping(value = "/editRate")
	@ResponseBody
	public Object editRate() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		PageData pd = new PageData();
		String result="1";
		String message="修改成功";
		pd = this.getPageData();
		 try{
			 categoryManageService.editRate(pd);
		 }catch(Exception e){
			 logger.error(e.toString());
		 }
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
 
	/**
	 * 
	* 方法名称:：addSmall OK
	* 方法描述：添加小类
	* 创建人：魏汉文
	* 创建时间：2016年7月16日 上午9:13:50
	 */
	@RequestMapping(value = "/addSmall")
	@ResponseBody
	public Object addSmall() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		PageData pd = new PageData();
		String result="1";
 		String message="新增成功";
		pd = this.getPageData();
		
		PageData e=appStoreService.findById(pd);
 		int m=Integer.parseInt( e.getString("smalltype_max") );
		int n=Integer.parseInt( categoryManageService.countSmall(pd));
		if(m == n ){
			result="0";
			message="大小已达上限,上限数量为"+m;
		}else{
			pd.put("goods_category_id", BaseController.getTimeID());
	 		categoryManageService.addSmall(pd);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	/**
	 * 
	 * 方法名称:：findByCateId OK
	 * 方法描述：查看详情
	 * 创建人：魏汉文
	 * 创建时间：2016年7月16日 上午9:13:50
	 */
	@RequestMapping(value = "/findByCateId")
	@ResponseBody
	public Object findByCateId() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		PageData pd = new PageData();
		String result="1";
		String message="新增成功";
		pd = this.getPageData();
		try {
			List<PageData> big_list = categoryManageService.findAllBig(pd);//获取商家所有的大类---用来新建小类
			if(pd.getString("goods_category_id") != null && !pd.getString("goods_category_id").equals("")){
				pd=categoryManageService.findByCateId(pd);
			}
	 		map.put("result", result);
			map.put("message", message);
			map.put("data", pd);
			pd=null;
			map.put("big_list", big_list);
			big_list=null;
		} catch (Exception e) {
			// TODO: handle exception
		}
 		return map;
	}
	

	
	@Resource(name = "storepc_scorewayService")
	private Storepc_scorewayService storepcScorewayService; 
	
	@Resource(name = "storepc_marketingService")
	private Storepc_marketingService storepcMarketingService;
	@Resource(name = "storepc_marketingtypeService")
	private Storepc_marketingtypeService storepcMarketingTypeService;
	@Resource(name="storepcService")
	private StorepcService storepcService;
	@Resource(name="storeManageService")
	private StoreManageService storeManageService;
	
	
	
	/**
	 * 
	* 方法名称:：showShop2 
	* 方法描述：商品管理
	* 创建人：魏汉文
	* 创建时间：2016年8月13日 上午10:48:48
	 */
	@RequestMapping(value = "/showShop2")
	public ModelAndView showShop2(Page page) throws Exception {
						ModelAndView modelAndView = this.getModelAndView();
						HttpServletRequest request=this.getRequest();
						HttpSession pcsession=request.getSession();
						//shiro管理的session
						Subject currentUser = SecurityUtils.getSubject();  
						Session session = currentUser.getSession();	
						Object obj=session.getAttribute(Const.SESSION_STORE_USER);
  						PageData pd = new PageData();
 						try {
 							pd=this.getPageData();
 							//System.out.println("======"+pd+"==========");
							if(obj != null){
			 					StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
				 				if(slogin != null){
				 					String store_id=slogin.getStore_id();
				 					String type=slogin.getType();
				 					String login_id=slogin.getLogin_id();
					 				if(pd.getString("store_id") == null || pd.getString("store_id").equals("")){
									    pd.put("store_id", store_id);
					 				}
					 				pd.put("login_id", login_id);
					 				pd.put("type", type);
				 				}
 							}else{
 								Qx qx=new Qx();
 								String type=pd.getString("type");
 								String login_id=pd.getString("login_id");
 								if(type.equals("1")){
 									 qx.setAdd("1");
									 qx.setDelete("1");
									 qx.setEdit("1");
									 qx.setLook("1");
									 pcsession.setAttribute("storeqx", qx);
 								}else if(type.equals("2")){
 									 PageData store=new PageData();
 									 store.put("store_operator_id", login_id);
 									 store=storeManageService.findOperatorById(store);
 									 if(store != null){
 										 String[] syStr=store.getString("sp_competence").split(",");
 										 qx.setAdd(syStr[0]);
 										 qx.setDelete(syStr[1]);
 										 qx.setEdit(syStr[2]);
 										 qx.setLook(syStr[3]);
 										 pcsession.setAttribute("storeqx", qx);
 									 }
 									store=null;
  								}
 								qx=null;
 							}
							if(pd.getString("store_id") != null && !pd.getString("store_id").equals("")){
		 							//判断是否有进行积分设置
									PageData jfpd=storepcScorewayService.findById(pd);
									if(jfpd == null ){
					 					modelAndView.addObject("jfstatus", "0");
									}else{
										modelAndView.addObject("jfstatus", "1");
										modelAndView.addObject("jfpd", jfpd);
										jfpd=null;
									}
									PageData yxpd=new PageData();
									//判断营销是否是否有开通满减营销
									yxpd.put("marketing_type", "1");
									yxpd.put("store_id", pd.getString("store_id"));
									yxpd=storepcMarketingService.findYxByOpenForOne(yxpd);
									if(yxpd == null){
										modelAndView.addObject("mjstatus", "0");
									}else{
										modelAndView.addObject("mjstatus", "1");
									}
									yxpd=null;
									//判断营销是否是否有开通买N减N营销
									yxpd=new PageData();
									yxpd.put("marketing_type", "4");
									yxpd.put("store_id", pd.getString("store_id"));
									yxpd=storepcMarketingService.findYxByOpenForOne(yxpd);
									if(yxpd != null){
 										yxpd=storepcMarketingTypeService.findById(yxpd);
										if(yxpd != null){
											String allgoods=yxpd.getString("goods_id");
											modelAndView.addObject("allyxgoodsid", allgoods);
 										}
 					  				}else{
					 					modelAndView.addObject("allyxgoodsid", "0");
					 				}
									yxpd=null;
									this.getHC();
									pd.put("paixu_w", "2");
					 		 		page.setPd(pd);
							 		List<PageData> good_list = categoryManageService.findEach(page);//商品列表
							 		List<PageData> big_list = categoryManageService.findAllBig(pd);//大类列表
							 		String goodsnumber=categoryManageService.countGoods(pd);
							 		String goods_max=appStoreService.findById(pd).getString("goods_max");
									modelAndView.addObject("big_list", big_list);
 							 		modelAndView.addObject("good_list", good_list);
 							 		modelAndView.addObject("pd", pd);
							 		modelAndView.addObject("goodsnumber", goodsnumber);
							 		modelAndView.addObject("goods_max", goods_max);
							 		good_list=null;
							 		big_list=null;
							 		//获取城市的收款信息
 							 		PageData citypd=city_marketingService.getCityMarketingForPCD(storepcService.findById(pd));
							 		if(cm_allService.listAllUpStoreFee(citypd).size()>0){
							 			modelAndView.addObject("citypd", cm_allService.listAllUpStoreFee(citypd).get(0));
							 		}
  							}
						} catch (Exception e) {
							// TODO: handle exception
							//System.out.println(e.toString());
						}
		 				modelAndView.setViewName("/storepc/business_shop2");
		 				pd=null;
		return modelAndView;
	}
	
	@Resource(name="city_marketingService")
	private City_marketingService city_marketingService;
	@Resource(name="cm_allService")
	private Cm_allService cm_allService;
	
	
	
	/**
	 * 
	 * 方法名:addGoods 描述:添加商品
	 * 
	 */
	@RequestMapping(value = "/addGoods")
	public ModelAndView addGoods( ) throws Exception {
				 ModelAndView modelAndView = this.getModelAndView();
 				 PageData pd=new PageData();
 				 try {
					 pd=this.getPageData();
					 String store_id=pd.getString("store_id");
				 	 if(store_id != null && !store_id.equals("")){
				 		if(pd.getString("starttime").equals("")){
				 			pd.remove("starttime");
				 		}
				 		if(pd.getString("endtime").equals("")){
				 			pd.remove("endtime");
				 		}
				 		pd.put("goods_id", BaseController.getTimeID());
						categoryManageService.addGoods(pd);
					 	 modelAndView.setViewName("redirect:showShop2.do?store_id="+store_id);
				 	 }
		 		} catch (Exception e) {
					// TODO: handle exception
					//System.out.println(e.toString());
		 			logger.error(e.toString(), e);
				}
 		return modelAndView;
	}
	

	/**
	 * 
	 * 方法名:editGoods 描述:修改商品
	 * 
	 */
	@RequestMapping(value = "/editGoods")
	public ModelAndView editGoods( ) throws Exception {
		 ModelAndView modelAndView = this.getModelAndView();
  		 PageData pd=new PageData();
		 try {
			 	 pd=this.getPageData();
			 	 String store_id=pd.getString("store_id");
			 	 if(store_id != null && !store_id.equals("")){
			 		 categoryManageService.editGoods(pd);
				 	 modelAndView.setViewName("redirect:showShop2.do?store_id="+store_id);
			 	 }
   		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		 pd=null;
		return modelAndView;
	}
	
	/**
	 * 删除商品
	 */
	@RequestMapping(value = "/deleteGoods")
	public ModelAndView deleteGoods() throws Exception {
		ModelAndView modelAndView = this.getModelAndView();
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			String store_id=pd.getString("store_id");
			if(store_id != null && !store_id.equals("")){
				categoryManageService.deleteGoods(pd);
				modelAndView.setViewName("redirect:showShop2.do?store_id="+store_id);
			}
 		} catch (Exception e) {
			// TODO: handle exception
		}
 		return modelAndView;
	}
	
	
	
	
	/**
	 * 
	 * 方法名称:：getSmallForBig OK
	 * 方法描述：通过大类获取小类集合
	 * 创建人：魏汉文
	 * 创建时间：2016年7月16日 上午9:13:50
	 */
	@RequestMapping(value = "/getSmallForBig")
	@ResponseBody
	public Object getSmallForBig() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		PageData pd = new PageData();
		String result="1";
		String message="新增成功";
		pd = this.getPageData();
		if(pd.getString("store_id") == null || pd.getString("store_id").equals("") || pd.getString("category_parent_id") == null || pd.getString("category_parent_id").equals("") ){
			result="0";
			message="缺少必要因素";
		}else{
			List<PageData> smallList=categoryManageService.findCateListById(pd);
			map.put("data", smallList);
			smallList=null;
		}
  		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	

	/**
	 * 
	 * 方法名称:：findGoodsById OK
	 * 方法描述：查看商品详情
	 * 创建人：魏汉文
	 * 创建时间：2016年7月16日 上午9:13:50
	 */
	@RequestMapping(value = "/findGoodsById")
	@ResponseBody
	public Object findGoodsById() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		PageData pd = new PageData();
		String result="1";
		String message="新增成功";
		pd = this.getPageData();
		if(pd.getString("goods_id") == null || pd.getString("goods_id").equals("") ){
			result="0";
			message="缺少必要因素";
		}else{
			pd=categoryManageService.findGoodsById(pd);
			map.put("data", pd);
		}
  		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	
	
	//修改库存量用到
	@RequestMapping(value="/findAjaxGoods")
	public void findAjaxGoods(@RequestParam("goods_name") String goods_name,HttpServletResponse  response,HttpServletRequest  request) throws Exception{
 		response.setContentType("text/html;charset=UTF-8");
 		PageData  pd= categoryManageService.findSmallByName(goods_name);
  		String jsonString = JSON.toJSONString(pd,SerializerFeature.PrettyFormat);
		response.getWriter().write(jsonString);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	
	//修改库存
	@RequestMapping(value="/modStockNum")
	public ModelAndView modStockNum(Page page) throws Exception{
			ModelAndView mv = this.getModelAndView();
				//shiro管理的session
				Subject currentUser = SecurityUtils.getSubject();  
				Session session = currentUser.getSession();	
				StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
				String store_id=slogin.getStore_id();
				String goods_id = this.getRequest().getParameter("goods_id");
				String stock_number = this.getRequest().getParameter("stock_number");
				Goods gd = new Goods();
				gd.setStock_number(stock_number);
				gd.setGoods_id(goods_id);
				categoryManageService.updateStockNum(gd);
				mv.setViewName("redirect:showShop2.do?store_id="+store_id);
		return mv;
	}
	
	//以上都是已修改----------------------------------------
	
	
	
	
	@RequestMapping(value = "/findGoods")
	public ModelAndView findGoods(Page page) throws Exception {
		ModelAndView modelAndView = this.getModelAndView();
//		String goods_name = this.getRequest().getParameter("goods_name");
//		Goods goods = categoryManageService.findSmallByName(goods_name);
//		modelAndView.addObject("good", goods);
//		modelAndView.setViewName("/storepc/business_shop2_1");
		return modelAndView;
	}

	/**
	 * 
	* 方法名称:：showShop3 
	* 方法描述：排行版
	* 创建人：魏汉文
	* 创建时间：2016年7月19日 下午1:21:28
	 */
	@RequestMapping(value = "/showShop3")
	public ModelAndView showShop3(Page page) throws Exception {
		ModelAndView modelAndView = this.getModelAndView();
  		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
		PageData pd = new PageData();
		try {
			pd=this.getPageData();
			if(slogin != null ){
				String store_id=slogin.getStore_id();
				if(pd.getString("store_id") == null || pd.getString("store_id").equals("")){
					pd.put("store_id", store_id);
				}
 				page.setPd(pd);
		 		List<PageData> good_list = categoryManageService.findEach(page);
		 		modelAndView.addObject("good_list", good_list);
		 		modelAndView.addObject("pd", pd);
		 		good_list=null;
			}
	 		modelAndView.setViewName("/storepc/business_shop3");
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString());
		}
 		return modelAndView;
	}
	
	/**
	 * 
	* 方法名称:：showShop4
	* 方法描述：人气版
	 */
	@RequestMapping(value = "/showShop4")
	public ModelAndView showShop4(Page page) throws Exception {
		ModelAndView modelAndView = this.getModelAndView();
  		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
		PageData pd = new PageData();
		try {
			pd=this.getPageData();
			if(slogin != null ){
				String store_id=slogin.getStore_id();
				if(pd.getString("store_id") == null || pd.getString("store_id").equals("")){
					pd.put("store_id", store_id);
				}
 				page.setPd(pd);
		 		List<PageData> good_list = categoryManageService.renqiList(page);
		 		modelAndView.addObject("good_list", good_list);
		 		modelAndView.addObject("pd", pd);
		 		good_list=null;
			}
	 		modelAndView.setViewName("/storepc/business_shop4");
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString());
		}
 		return modelAndView;
	}
	
	/**
	 * 
	* 方法名称:：showShop5
	* 方法描述：今日特价
	 */
	@RequestMapping(value = "/showShop5")
	public ModelAndView showShop5(Page page) throws Exception {
		ModelAndView modelAndView = this.getModelAndView();
  		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
		PageData pd = new PageData();
		try {
			pd=this.getPageData();
			if(slogin != null ){
				String store_id=slogin.getStore_id();
				if(pd.getString("store_id") == null || pd.getString("store_id").equals("")){
					pd.put("store_id", store_id);
				}
 				page.setPd(pd);
		 		List<PageData> good_list = categoryManageService.tejiaList(page);
		 		modelAndView.addObject("good_list", good_list);
		 		modelAndView.addObject("pd", pd);
		 		good_list=null;
			}
	 		modelAndView.setViewName("/storepc/business_shop5");
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString());
		}
 		return modelAndView;
	}
	
	/**
	 * 
	* 方法名称:：showShop4
	* 方法描述：人气版
	 */
	@RequestMapping(value = "/delRenqi")
	public ModelAndView delRenqi() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
		String store_id=slogin.getStore_id();
		try {
			pd = this.getPageData();
			categoryManageService.delRenqi(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("redirect:showShop4.do?store_id="+store_id);
		return mv;
	}
	
	
	/**
	 * 
	* 方法名称:：showShop4
	* 方法描述：人气版
	 */
	@RequestMapping(value = "/delTejia")
	public ModelAndView delTejia() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
		String store_id=slogin.getStore_id();
		try {
			pd = this.getPageData();
			categoryManageService.delTejia(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("redirect:showShop5.do?store_id="+store_id);
		return mv;
	}
 
 
	/**
	 * 以下均为排序
	 * 销售排序
	 */
	@RequestMapping(value = "/sortbyconsume")
	public ModelAndView sortbyconsume(Page page) throws Exception {
		ModelAndView modelAndView = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
 		PageData pd = new PageData();
		try {
			pd=this.getPageData();
			if(slogin != null){
				String store_id=slogin.getStore_id();
				if(pd.getString("store_id") == null || pd.getString("store_id").equals("")){
				    pd.put("store_id", store_id);
				}
				page.setPd(pd);
				List<PageData> good_list = categoryManageService.findSortByConlistPage(page);
				modelAndView.addObject("good_list", good_list);
				good_list=null;
				modelAndView.setViewName("/storepc/business_shop3");
			}
 		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString());
		}
 		return modelAndView;
	}
	
	/**
	 * 
	 * 销售排序
	 */
	@RequestMapping(value = "/sortbystock")
	public ModelAndView sortbystock(Page page) throws Exception {
		ModelAndView modelAndView = this.getModelAndView();
		try {
			List<PageData> good_list = categoryManageService.findSortByStolistPage(page);
			modelAndView.addObject("good_list", good_list);
			good_list=null;
			modelAndView.setViewName("/storepc/business_shop3");
		} catch (Exception e) {
			// TODO: handle exception
		}
 		return modelAndView;
	}

	@RequestMapping(value = "/sortbysort")
	public ModelAndView sortbysort(Page page) throws Exception {
		ModelAndView modelAndView = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
		PageData pd = new PageData();
		try {
			pd=this.getPageData();
			if(slogin != null){
				String store_id=slogin.getStore_id();
				if(pd.getString("store_id") == null || pd.getString("store_id").equals("")){
				    pd.put("store_id", store_id);
				}
				page.setPd(pd);
				List<PageData> good_list = categoryManageService.findSortBySorlistPage(page);
				modelAndView.addObject("good_list", good_list);
				good_list=null;
				modelAndView.setViewName("/storepc/business_shop3");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
  		return modelAndView;
	}
	
	/**
	 * 按时间查询
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findGoodsByTime")
	public ModelAndView findGoodsByTime(Page page) throws Exception {
		ModelAndView modelAndView = this.getModelAndView();
		PageData pd = this.getPageData();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
 		try {
			StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
			if(slogin != null){
				pd=this.getPageData();
				String store_id=slogin.getStore_id();
				if(pd.getString("store_id") == null || pd.getString("store_id").equals("")){
				    pd.put("store_id", store_id);
				}
				String starttime=pd.getString("start");
				String endtime=pd.getString("end");
				pd.put("starttime", starttime);
				pd.put("endtime", endtime);
	 			page.setPd(pd);
				List<PageData> good_list = categoryManageService.findGoodsByTimelistPage(page);
				modelAndView.addObject("good_list", good_list);
				good_list=null;
				modelAndView.setViewName("/storepc/business_shop3");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
  		return modelAndView;
	}

	/*
	 * 导出
	 */
	@RequestMapping(value="/toExcel")
	@ResponseBody
	public ModelAndView toExcel(Page page){
		ModelAndView mv = this.getModelAndView();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
		String store_id=slogin.getStore_id();
		PageData pd = new PageData();
		pd.put("store_id", store_id);
		List<String> titles = new ArrayList<String>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<PageData> varList = new ArrayList<PageData>();		
 		titles.add("商品ID");
		titles.add("大类");
		titles.add("小类");
		titles.add("商品名称");
		titles.add("市场价");
		titles.add("零售价");
		titles.add("积分率");
		titles.add("销售数量");
		titles.add("库存量");		
		titles.add("商品状态");
 		dataMap.put("titles", titles); //导出设置标题
 		titles=null;
		try {
 			List<GoodsAndCate> listMater = categoryManageService.excelListAll(pd);  //要导出的数据
			for (int i = 0; i < listMater.size(); i++) {
				PageData vpd = new PageData();
				vpd.put("var1", listMater.get(i).getParent_id());
				vpd.put("var2", listMater.get(i).getParent_name());
				vpd.put("var3", listMater.get(i).getSon_name());
				vpd.put("var4", listMater.get(i).getGoods_name());
				vpd.put("var5", listMater.get(i).getMarket_money());
				vpd.put("var6", listMater.get(i).getRetail_money());
				vpd.put("var7", listMater.get(i).getIntegral_rate());
				vpd.put("var8", listMater.get(i).getConsumption_number());
				vpd.put("var9", listMater.get(i).getStock_number());
				vpd.put("var10", listMater.get(i).getGoods_status());
				varList.add(vpd); 
			}
			dataMap.put("varList", varList);     //设置每行的信息
			varList=null;
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 
	* 方法描述：加入今日特价
	 */
	@RequestMapping(value = "/savetejia")
	@ResponseBody
	public Object savetejia() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		PageData pd = new PageData();
		String result="1";
 		String message="新增成功";
		pd = this.getPageData();
		try {
			String goods_ids = pd.getString("goods_ids");
			if(goods_ids != null && !goods_ids.equals("")){
				String listGoods_ids[] = goods_ids.split(",");
				for (int i = 0; i < listGoods_ids.length; i++) {
					//获取今日特价的数据总数
					String countTj = categoryManageService.countTejia(pd);
					double countTejia = Double.parseDouble(countTj);
					if(countTejia < 10){
						PageData pg = new PageData();
						pd.put("goods_id", listGoods_ids[i]);
 						pg = categoryManageService.findTejia(pd);
						if(pg == null){
							pd.put("goods_tj_id", BaseController.getTimeID());
							categoryManageService.savetejia(pd);
						}
						pg=null;
					}else {
						result="2";
						message="最多添加10条，还可以添加"+(int)(10-countTejia);
					}
				}
			}
		} catch (Exception e) {
			result="0";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	/**
	 * 
	* 方法描述：加入人气榜
	 */
	@RequestMapping(value = "/saverenqi")
	@ResponseBody
	public Object saverenqi() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		PageData pd = new PageData();
		String result="1";
 		String message="新增成功";
		pd = this.getPageData();
		
		try {
			String goods_ids = pd.getString("goods_ids");
			if(goods_ids != null && !goods_ids.equals("")){
				String listGoods_ids[] = goods_ids.split(",");
				for (int i = 0; i < listGoods_ids.length; i++) {
					//获取今日特价的数据总数
					String countRq = categoryManageService.countRenqi(pd);
					double countRenqi = Double.parseDouble(countRq);
					if(countRenqi < 10){
						pd.put("goods_id", listGoods_ids[i]);
						PageData pg = new PageData();
						pg = categoryManageService.findRenqi(pd);
						if(pg == null){
							pd.put("goods_tj_id", BaseController.getTimeID());
							categoryManageService.saverenqi(pd);
						}
						pg=null;
					}else {
						result="2";
						message="最多添加10条，还可以添加"+(int)(10-countRenqi);
					}
					
				}
			}
			
		} catch (Exception e) {
			result="0";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	@RequestMapping(value="/ExportExcel")
	@ResponseBody
	public ModelAndView ExportExcel(Page page, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		try {
			pd=this.getPageData();
			ExcelTest et = new ExcelTest();
			List list = categoryManageService.findAllEach(page);
			et.exportExcel(list, response, request);
			mv.setViewName("/storepc/business_success");
		} catch (Exception e) {
			// TODO: handle exception
		}
 		return mv;
	}

	

	
	/*
	 *互动沟通1
	 *刘耀耀
	 *2016.07.09
	 *
	 */
	
	@RequestMapping(value="/goweixin1")
	public ModelAndView goweixin1(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("/storepc/business_weixin1");
		return mv;
	}
	
	/**
	 *互动沟通2
	 *刘耀耀
	 *2016.07.09
	 *
	 */
 	@RequestMapping(value="/goweixin2")
	public ModelAndView goweixin2() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
 		try {
			pd=this.getPageData();
//			PageData allpd=new PageData();
//			List<PageData> varList=appStoreService.contactStoreList(pd);
//			for(PageData e : varList){
//	 			mv.addObject(e.getString("id"), e.getString("id"));
// 			}
//			varList=null;
//			varList=appStoreService.contactStoreForOpratorList(pd);
//			for(PageData e : varList){
//				allpd.put(e.getString("id"), e.getString("name"));
// 			}
//			varList=null;
//			varList=appMemberService.contactMemberList(pd);
//			for(PageData e : varList){
//				allpd.put(e.getString("id"), e.getString("name"));
// 			}
//	 		mv.addObject("allpd", allpd);
//	 		allpd=null;
			pd=appStoreService.findById(pd);
			mv.addObject("pd", pd);
			pd=null;
			mv.setViewName("/storepc/business_weixin2");
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString());
		}
 		return mv;
	}
	
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	
 
 	
	/*
	 *互动沟通3
	 *刘耀耀
	 *2016.07.09
	 *
	 */
	
	@RequestMapping(value="/goweixin3")
	public ModelAndView goweixin3(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		try{
			pd=this.getPageData();
 			this.getHCHd();
		}catch(Exception e){
			
			logger.error(e.toString());
		}
 		mv.setViewName("/storepc/business_weixin3");
		return mv;
	}
	
	
	/* ===============================商品权限================================== */
	public void getHC(){
			ModelAndView mv = this.getModelAndView();
			HttpServletRequest request=this.getRequest();
			HttpSession pcsession=request.getSession();
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();	
			StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
 			try {
				Qx qx=new Qx();
				if(slogin != null){
					//System.out.println("getHC权限======="+slogin.toString());
					Map<String,Object> map=slogin.getMap();
					//System.out.println("map====="+map.toString());
 					qx=(Qx) map.get("sp");
					pcsession.setAttribute("storeqx", qx);
 				}
  			} catch (Exception e) {
				// TODO: handle exception
			}
			
	}
	/* ===============================权限================================== */
	
	
	/* ===============================互动权限================================== */
	public void getHCHd(){
			ModelAndView mv = this.getModelAndView();
			HttpServletRequest request=this.getRequest();
			HttpSession pcsession=request.getSession();
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();	
			StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
			try {
				Qx qx=new Qx();
				if(slogin != null){
					Map<String,Object> map=slogin.getMap();
 					qx=(Qx) map.get("hd");
					pcsession.setAttribute("storeqx", qx);
  				}
  			} catch (Exception e) {
				// TODO: handle exception
			}
			
	}
	/* ===============================权限================================== */
	
	
	
	
	
	
	
}