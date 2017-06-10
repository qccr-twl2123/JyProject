package com.tianer.controller.zhihui.yingxiao;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

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
import com.tianer.entity.zhihui.MsgTask;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.city_file_sort.City_file_sortService;
import com.tianer.service.business.city_marketing.City_marketingService;
import com.tianer.service.business.cm_all.Cm_allService;
import com.tianer.service.business.operator_file.Operator_fileService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.sp_file.Sp_fileService;
import com.tianer.service.business.store.StoreService;

/** 
 * 
* 类名称：ZhihuiCity_marketingController   
* 类描述：   城市营销
* 创建人：魏汉文  
* 创建时间：2016年6月13日 上午9:15:49
 */
@Controller
@RequestMapping(value="/zhihuicity_marketing")
public class ZhihuiCity_marketingController extends BaseController {
	
	@Resource(name="city_marketingService")
	private City_marketingService city_marketingService;
	
 
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		//logBefore(logger, "删除City_marketing");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			city_marketingService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}

	
	/**
	 * 修改
	 * 魏汉文20160614
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		//logBefore(logger, "修改City_marketing");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String city_marketing_id=pd.getString("city_marketing_id");
		String currentPage=pd.getString("currentPage");//分页数
		String alloneid=pd.getString("alloneid");
		String alltwoid=pd.getString("alltwoid");
		String allthreeid=pd.getString("allthreeid");
		String allfourid=pd.getString("allfourid");
		String allsevenid=pd.getString("allsevenid");
//		String allfiveid=pd.getString("allfiveid");
		String allsixid=pd.getString("allsixid"); 
		
 		//设置一的商家
		String[] str1=alloneid.split("@");
		for(int i= 0 ;i<str1.length ;i++){
			PageData e=new PageData();
			e.put("cm_one_id", str1[i]);
			e.put("city_marketing_id", city_marketing_id);
			PageData e1=new PageData();
			e1=cm_allService.getRedStoreById(e);
 			//判断是否有被删除
			if(pd.getString(str1[i]+"store_redpackets_id") != null){
				e.put("store_redpackets_id", pd.getString(str1[i]+"store_redpackets_id") );
				if(e1 == null ){
					cm_allService.saveRedStore(e);
				}
			}else{
				cm_allService.deleteRedStore(e);
			}
			e=null;
			e1=null;
 		}
		
		//设置二
		String[] str2=alltwoid.split("@");
		for(int i= 0 ;i<str2.length ;i++){
			PageData e=new PageData();
			e.put("cm_two_id", str2[i]);
			e.put("check_status", pd.getString(str2[i]+"check_status"));
			cm_allService.editKaijiRed(e);
			e=null;
		}
		
		//设置三
		String[] str3=allthreeid.split("@");
		for(int i= 0 ;i<str3.length ;i++){
			PageData e=new PageData();
			e.put("cm_three_id", str3[i]);
			e.put("rule_status", pd.getString(str3[i]+"rule_status"));
			e.put("parameter", Integer.parseInt(e.getString("rule_status"))*1000+"");
			cm_allService.editfujinRed(e);
			e=null;
		}
		
 		//设置四
		String[] str4=allfourid.split("@");
		for(int i= 0 ;i<str4.length ;i++){
			PageData e=new PageData();
			e.put("cm_four_id", str4[i]);
			e.put("refresh_type", pd.getString(str4[i]+"refresh_type"));
			e.put("onerefresh_time", pd.getString(str4[i]+"onerefresh_time"));
			e.put("tworefresh_time", pd.getString(str4[i]+"tworefresh_time"));
 			cm_allService.editRedTime(e);
 			e=null;
		}
		
		//设置五
		String[] str5=allsevenid.split("@");
		for(int i= 0 ;i<str5.length ;i++){
			PageData e=new PageData();
			e.put("cm_seven_id", str5[i]);
			e.put("billing_money", pd.getString(str5[i]+"billing_money"));
			e.put("billing_time", pd.getString(str5[i]+"billing_time"));
  			cm_allService.editUpStoreFee(e);
  			//设置定时器：传入省市区的名称，以及金额
  			Date date=new Date();
			long l1=date.getTime();
			String time=e.get("billing_time").toString();
			String nowday=DateUtil.getDay();
			time=nowday+" "+time;
			Date date2=DateUtil.fomatDate1(time);
			long l2=date2.getTime();
			//System.out.println(time);
			if(l2-l1 >=0){
				MsgTask mt=new MsgTask(e.getString("province_name"),e.getString("city_name"),e.getString("area_name"),e.getString("billing_money"));
				Timer tt=new Timer();
				tt.schedule(mt, l2-l1);
			}
			e=null;
		}
		
		//设置六
		String[] str7=allsixid.split("@");
		for(int i= 0 ;i<str7.length ;i++){
			PageData e=new PageData();
			e.put("cm_six_id", str7[i]);
			e.put("clerk_commission", pd.getString(str7[i]+"clerk_commission"));
			e.put("service_provider_commission", pd.getString(str7[i]+"service_provider_commission"));
			e.put("monthly_sales", pd.getString(str7[i]+"monthly_sales"));
			e.put("send_money", pd.getString(str7[i]+"send_money"));
			e.put("allrenmai_number", pd.getString(str7[i]+"allrenmai_number"));
			e.put("jioayi_number", pd.getString(str7[i]+"jioayi_number"));
			e.put("jiaoyi_money", pd.getString(str7[i]+"jiaoyi_money"));
   			cm_allService.editTichengButie(e);
   			e=null;
		}
		
		
		//设置八
		String alleightid=pd.getString("alleightid");
		String keyword=pd.getString(alleightid+"keyword");
		if(true){
			PageData e=new PageData();
			e.put("keyword", keyword);
			e.put("cm_eight_id", alleightid);
			cm_allService.editcontent(e);
		}
		
		
  		mv.setViewName("redirect:list.do?currentPage="+currentPage);
		return mv;
	}
	
	@Resource(name="city_fileService")
	private City_fileService city_fileService;
	@Resource(name="pcdService")
	private PcdService pcdService;
	
	/**
	 * 列表
	 * 魏汉文20160613
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		//logBefore(logger, "列表City_marketing");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String content = pd.getString("content");
 			if(null != content && !"".equals(content)){
				content = content.trim();
				pd.put("content", content);
			}
 			String province_id = pd.getString("province_id");
 			if(null != province_id && !"".equals(province_id)){
				province_id = province_id.trim();
				pd.put("province_id", province_id);
			}
			String city_id = pd.getString("city_id");
			if(null != city_id && !"".equals(city_id)){
				pd.put("city_id", city_id);
			}
			String area_id = pd.getString("area_id");
			if(null != area_id && !"".equals(area_id)){
				pd.put("area_id", area_id);
			}
			String currentPage = pd.getString("currentPage");
			if(null != currentPage && !"".equals(currentPage)){
				page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
			}
			pd.put("open_status", "1");
			page.setPd(pd);
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			List<PageData>	varList = city_fileService.list(page);	//列出City_marketing列表
			this.getHC(); //调用权限
			mv.setViewName("zhihui/yingxiao/yingxiao1");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@Resource(name="sp_fileService")
	private Sp_fileService sp_fileService;
	
	@Resource(name="cm_allService")
	private Cm_allService cm_allService;
	
	@Resource(name="city_file_sortService")
	private City_file_sortService city_file_sortService;
	
	/**
	 * 去修改页面
	 * 魏汉文20160614
	 * 
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		//logBefore(logger, "去修改City_marketing页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
				String currentPage=pd.getString("currentPage");
  				String allredId=pd.getString("allredId");
				//获得当前城市一级分类
				pd.put("sort_type", "1");
				List<PageData> firstList=city_file_sortService.listAll(pd);
				mv.addObject("firstList", firstList);
				//获得营销ID
				pd = city_marketingService.findById(pd);	//根据ID读取
				if(pd == null ){
					mv.setViewName("redirect:list.do?currentPage="+currentPage);
					return mv;
				}
				//获取当前城市的收费标准
				List<PageData> feeList=city_fileService.listAllCityFee(pd);
				mv.addObject("feeList", feeList);
 				//获取营销中的商家红包
				List<PageData> oneList=cm_allService.listAllRedStore(pd);
				if(allredId != null && !allredId.equals("")){
 					//保存新增的商家红包
   					String[] str=allredId.split("@");
					for( int i=0 ; i<str.length ; i++){
							PageData e=new PageData();
							e.put("store_redpackets_id", str[i]);
							e=storeService.findByRedPackageId(e);
							e.put("cm_one_id",BaseController.getTimeID());
							oneList.add(e);
							e=null;
					}
 				}
				String alloneid="";
				for(PageData e :oneList){
					alloneid+=e.get("cm_one_id")+"@";
				}
				pd.put("alloneid", alloneid);
				mv.addObject("oneList", oneList);
				
				//获取会员获取开机红包的来源
				List<PageData> twoList=cm_allService.listAllKaijiRed(pd);
				String alltwoid="";
				for(PageData e :twoList){
					alltwoid+=e.get("cm_two_id")+"@";
				}
				pd.put("alltwoid", alltwoid);
				mv.addObject("twoList", twoList);
				
				//获取会员抢附近商家红包的规则
				List<PageData> threeList=cm_allService.listAllfujinRed(pd);
				String allthreeid="";
				for(PageData e :threeList){
					allthreeid+=e.get("cm_three_id")+"@";
				}
				pd.put("allthreeid", allthreeid);
				mv.addObject("threeList", threeList);
				
				//红包发送时间
				List<PageData> fourList=cm_allService.listAllRedTime(pd);
				String allfourid="";
				for(PageData e :fourList){
					allfourid+=e.get("cm_four_id")+"@";
				}
				pd.put("allfourid", allfourid);
				mv.addObject("fourList", fourList);
				
				//获取超出商品限度的费用
				List<PageData> sevenList=cm_allService.listAllUpStoreFee(pd);
				String allsevenid="";
				for(PageData e :sevenList){
					allsevenid+=e.get("cm_seven_id")+"@";
				}
				pd.put("allsevenid", allsevenid);
				mv.addObject("sevenList", sevenList);
 				
				//获取收费标准
				pd.put("ismoreng", "0");
 				List<PageData> list=city_file_sortService.listAll(pd);//获取所有默认的分类 
 				if(list.size() > 0){
 					mv.addObject("fee", list.get(0));
 				}
 				pd.put("ismoreng", "1");
 				List<PageData> fiveList=city_file_sortService.listAll(pd);//获取所有非默认的分类 
				mv.addObject("fiveList", fiveList);
				
				//获取提成与补贴
				List<PageData> sixList=cm_allService.listAllTichengButie(pd);
				String allsixid="";
				for(PageData e :sixList){
					allsixid+=e.get("cm_six_id")+"@";
				}
				pd.put("allsixid", allsixid);
				mv.addObject("sixList", sixList);
				
				//获取PC搜索框下关键字设置
				List<PageData> eightList=cm_allService.listAllcontent(pd);
				if(eightList.size() > 0){
					String alleightid=eightList.get(0).get("cm_eight_id")+"";
					String content =eightList.get(0).getString("keyword");
					eightList.clear();
	 				String[] str=content.split("@");
					int n=str.length;
					for(int i=0 ;  i<8 ;i++){
								PageData e=new PageData();
								if(i<=n-1){
									e.put("keyword", str[i]);
								}else{
									e.put("keyword", "");
								}
								eightList.add(e);
								e=null;
					}
	   				pd.put("alleightid", alleightid);
					mv.addObject("eightList", eightList);
				}	
				//处理分页
	 			if(currentPage != null){
	 				pd.put("currentPage", currentPage);
				}
	  			mv.setViewName("zhihui/yingxiao/yingxiao2");
				mv.addObject("msg", "edit");
				mv.addObject("pd", pd);
				
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	

	
	/**
	 * 去新增可发红包的商家页面
	 * 魏汉文20160613
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		//logBefore(logger, "去新增可发红包的商家页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String city_marketing_id=pd.getString("city_marketing_id");
			String city_file_id=pd.getString("city_file_id");
			String currentPage=pd.getString("currentPage");
			pd=city_fileService.findById(pd);
			String area_id=pd.getString("area_id");
			PageData e=new PageData();
			e.put("area_id", area_id);
			List<PageData> spList=sp_fileService.getSpListBycity(e);
			mv.addObject("spList", spList);
			mv.setViewName("zhihui/yingxiao/yingxiao3");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
			mv.addObject("city_marketing_id", city_marketing_id);
			mv.addObject("city_file_id", city_file_id);
			mv.addObject("currentPage", currentPage);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	
	
	@Resource(name="operator_fileService")
	private Operator_fileService operator_fileService;
	
	
	@Resource(name="storeService")
	private StoreService storeService;
	
	/**
	 * 通过服务商获取该服务商下的商家
	 * 魏汉文20160613
	 */
	@RequestMapping(value="/getStoreListBySp")
	@ResponseBody
	public Object getStoreListBySp() throws Exception{
		//logBefore(logger, "通过服务商获取该服务商下的商家");
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> allstore=new ArrayList<PageData>();
		String result = "01";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			allstore=storeService.getStoreList(pd);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	map.put("allstore", allstore);
  		map.put("result", result);
 		return map;
	}
	
	
	/**
	 * 通过搜索条件获取所有已发出红包的商家
	 * 魏汉文20160613
	 */
	@RequestMapping(value="/getRedPackageListStore")
	@ResponseBody
	public Object getRedPackageListStore() throws Exception{
		//logBefore(logger, "通过搜索条件获取所有已发出红包的商家");
		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "01";
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
				String content =pd.getString("content");
				String store_id=pd.getString("store_id");
				if(null != content && !"".equals(content)){
					content = content.trim();
					pd.put("content", content);
				}
				if(null != store_id && !"".equals(store_id)){
					store_id = store_id.trim();
					pd.put("store_id", store_id);
				}
				pd.put("redpackage_status", Const.kaiji_redpackage_status);
				List<PageData> redList=storeService.getRedPackageListStore(pd);
				int n= Integer.parseInt(Const.redMin);
				if(redList.size() == 0  ){
					result="00";
				}
				for(int i=0;i<redList.size() ;i++){//红包必须大于20个
					PageData e=new PageData();
					e=redList.get(i);
					int m=Integer.parseInt(e.getString("redpackage_number"));
					int m2=Integer.parseInt(e.getString("overget_number"));
					double money1=Double.parseDouble(e.getString("money"));
					double money2=Double.parseDouble(e.getString("overget_money"));
					if(m <= n || m2==m || money2==money1){
						redList.remove(i);
						i=i-1;
					}
					e=null;
				}
	    		map.put("redlist", redList);
 		}catch(Exception e){
 			logger.error(e.toString(), e);
		}
   		map.put("result", result);
 		return map;
	}

	
	/**
	 * 删除商家红包
	 */
	@RequestMapping(value="/deleteRedStore")
	@ResponseBody
	public Object deleteRedStore(){
		//logBefore(logger, "删除商家红包");
		Map<String,Object> map = new HashMap<String,Object>();
		String result="01";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			cm_allService.deleteRedStore(pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
			result="00";
		}
		map.put("result", result);
		return map;
 	}
	
	
	/**
	 * 新增城市营销收费标准 1010
	 */
	@RequestMapping(value="/addCityFileFee")
	@ResponseBody
	public Object addCityFileFee(){
		//logBefore(logger, "新增城市营销收费标准");
		Map<String,Object> map = new HashMap<String,Object>();
		String result="01";
		String message="新增成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData> feeList=city_fileService.listAllCityFee(pd);
			if(feeList.size() < 3){
				String city_file_fee_id=pd.getString("city_file_id")+this.get9ZM();
				pd.put("city_file_fee_id", city_file_fee_id);
				city_fileService.addCityFileFee(pd);
				map.put("data", city_file_fee_id);
			}else{
				message="一个购买类型最多新增3种";
				result="00";
			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
			result="00";
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	
	
	/**
	 * 删除城市营销收费标准tb_city_file_fee
	 */
	@RequestMapping(value="/deleteCityFileFee")
	@ResponseBody
	public Object deleteCityFileFee(){
		//logBefore(logger, "删除城市营销收费标准tb_city_file_fee");
		Map<String,Object> map = new HashMap<String,Object>();
		String result="01";
		String message="删除成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			city_fileService.updateCityFileFee(pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
			result="00";
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	
	
	/**
	 * 所有城市营销收费标准tb_city_file_fee
	 */
	@RequestMapping(value="/listAllCityFee")
	@ResponseBody
	public Object listAllCityFee(){
		//logBefore(logger, "所有城市营销收费标准tb_city_file_fee");
		Map<String,Object> map = new HashMap<String,Object>();
		String result="01";
		String message="获取 成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData> feeList=city_fileService.listAllCityFee(pd);
			map.put("data", feeList);
		} catch(Exception e){
			logger.error(e.toString(), e);
			result="00";
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	

	/**
	 * 城市营销收费标准详情
	 */
	@RequestMapping(value="/getCityFeeDetail")
	@ResponseBody
	public Object getCityFeeDetail(){
		//logBefore(logger, "城市营销收费标准详情");
		Map<String,Object> map = new HashMap<String,Object>();
		String result="01";
		String message="获取 成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd=city_fileService.getCityFeeDetail(pd);
			map.put("data", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
			result="00";
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	
	/* ===============================权限================================== */
	public void getHC(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("16"));
		}
 	}
	/* ===============================权限================================== */
	
}
