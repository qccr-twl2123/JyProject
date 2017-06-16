/**
 * 
 */
package com.tianer.controller.storepc.homepage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.tongyongUtil.TongYong;
import com.tianer.entity.Page;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.storepc.liangqin.homepage.VIPService;
import com.tianer.service.storepc.stotr.StorepcService;
import com.tianer.util.AppUtil;
import com.tianer.util.DateUtil;
import com.tianer.util.FileUpload;
import com.tianer.util.ObjectExcelOperate;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;

/**
 * 类名称: Storepc_VIPController 
 * 类描述: TODO
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-17 上午8:53:20	
 * 版本号: v1.0
 * @param <Pagedata>
 */
@Controller
@RequestMapping(value="/storepc_vip")
public class Storepc_VIPController extends BaseController{
	
	@Resource(name="vipService")
	private VIPService vipService;
	 
	/**
	 * 
	 * 方法名称:：deleteVip 
	 * 方法描述：删除vip
	 * 创建人：魏汉文
	 * 创建时间：2016年7月12日 下午8:34:18
	 */
	@RequestMapping(value="/deleteVip")
	@ResponseBody
	public Object deleteVip() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="删除成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if(pd.getString("source_type").equals("2")){
				vipService.deleteVIPOne(pd);
			}else if(pd.getString("source_type").equals("1")){
				vipService.deleteVIPTwo(pd);
			}else if(pd.getString("source_type").equals("3")){
//				vipService.deleteVIPTwo(pd);
			}
 		} catch (Exception e) {
			result = "0";
			message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
 		return AppUtil.returnObject(pd, map);
	}
	

	
	
	/**
	 * 
	 * 方法名称:：editVip 
	 * 方法描述：修改vip
	 * 创建人：魏汉文
	 * 创建时间：2016年7月12日 下午8:34:18
	 */
	@RequestMapping(value="/editVip")
	@ResponseBody
	public Object editVip() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if(pd.getString("source_type").equals("1")){
//				vipService.editMemberOne(pd);
			}else if(pd.getString("source_type").equals("2")){
				vipService.editMemberByStore(pd);
			}else if(pd.getString("source_type").equals("3")){
//				vipService.editMemberOne(pd);
			}
		} catch (Exception e) {
			result = "0";
			message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 
	* 方法名称:：goMyVIP 
	* 方法描述：去我的会员页面(默认显示线上会员)
	* 创建人：魏汉文
	* 创建时间：2016年7月13日 上午11:51:00
	 */
	@RequestMapping(value="/goMyVIP")
	public ModelAndView goMyVIP(Page page) throws Exception{
		ModelAndView mv = new ModelAndView();
		List<PageData> vip_list=new ArrayList<PageData>();
		DecimalFormat    df   = new DecimalFormat("######0"); 
		PageData pd = this.getPageData();
 		try{
			pd = this.getPageData();
			page.setPd(pd);
			String allvip=df.format( Double.parseDouble(vipService.allVipImageSum(page) ));
			pd.put("allvip", allvip);
			String allexcel=df.format( Double.parseDouble(vipService.findExcelVIPSum(page) ));
			pd.put("allexcel", allexcel);
 			String allrenmai=df.format( Double.parseDouble(vipService.renmaiYiSum(page) ));
 			pd.put("allrenmai", allrenmai);
 			String allnumber=df.format(Double.parseDouble(allvip)+Double.parseDouble(allexcel)+Double.parseDouble(allrenmai));
			pd.put("allnumber_w", allnumber);
			if(pd.getString("source_type") == null || pd.getString("source_type").equals("") || pd.getString("source_type").equals("1")){
 				vip_list=vipService.allVipImagelistPage(page);//平台会员（线上）
				for(PageData e : vip_list){
					e.put("source_type", "1");
					if(e.getString("phone") != null && !e.getString("phone").equals("") && e.getString("phone").length() == 11){
						e.put("phone", e.getString("phone").substring(0, 3)+"****"+e.getString("phone").substring(7, 11));
					}
					if(e.getString("name") != null && !e.getString("name").equals("") && e.getString("name").length() == 11){
						e.put("name", e.getString("name").substring(0, 3)+"****"+e.getString("name").substring(7, 11));
					}
				}
			}else if(pd.getString("source_type").equals("2")){
 				vip_list=vipService.findExcelVIPlistPage(page);//商家会员（线下）
				for(PageData e : vip_list){
					e.put("source_type", "2");
					e.put("now_integral", "0");
					if(e.getString("phone") != null && !e.getString("phone").equals("") && e.getString("phone").length() == 11){
						e.put("phone", e.getString("phone").substring(0, 3)+"****"+e.getString("phone").substring(7, 11));
					}
					if(e.getString("name") != null && !e.getString("name").equals("") && e.getString("name").length() == 11){
						e.put("name", e.getString("name").substring(0, 3)+"****"+e.getString("name").substring(7, 11));
					}
				}
			}else if(pd.getString("source_type").equals("3")){//一度人脉
 				vip_list = vipService.renmaiYiListlistPage(page);//一度人脉集合
				for(PageData e : vip_list){
					e.put("source_type", "3");
					if(e.getString("phone") != null && !e.getString("phone").equals("") && e.getString("phone").length() == 11){
						e.put("phone", e.getString("phone").substring(0, 3)+"****"+e.getString("phone").substring(7, 11));
					}
					if(e.getString("name") != null && !e.getString("name").equals("") && e.getString("name").length() == 11){
						e.put("name", e.getString("name").substring(0, 3)+"****"+e.getString("name").substring(7, 11));
					}
				}
			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
  		mv.setViewName("/storepc/business_homepage8");
		mv.addObject("vip_list", vip_list);
		vip_list=null;
		mv.addObject("pd", pd);
		return mv;
	}
	
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	
	/**
	 * 
	* 方法名称:：addExcel 
	* 方法描述：导入我的会员
	* 创建人：魏汉文
	* 创建时间：2016年7月13日 上午11:04:52
	 */
	@RequestMapping(value="/addExcel")
	public ModelAndView addExcel(
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request,
			@RequestParam String store_id,
			String source_type
		) throws Exception{
		ModelAndView mv = this.getModelAndView();
// 		logBefore(logger, "导入我的会员excel");
		// 文件保存路径  
 		String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
		String filePath = "/memberExcel";//文件上传路径
		try {				
 				String name = FileUpload.fileUpExcel(file, currentPath+filePath, file.getOriginalFilename());  //字符拼接，上传到服务器上
// 				List<Object> result = ObjectExcelRead.readExcel(currentPath+filePath, name, 1, 0, 0);
//				int allexcel=result.size(); 
//				for (int i = 0; i <allexcel ; i++) {
 				 List<PageData> result = ObjectExcelOperate.getData(currentPath+filePath, name, 1);
 			     int rowLength = result.size();
 			     for(int i=0;i<rowLength;i++) {
					    PageData excelpd=new PageData();
					    excelpd =result.get(i);
 					    excelpd.put("store_id", store_id);
					    excelpd.put("phone", excelpd.getString("var2"));
					    //判断是否为会员
					    excelpd.put("province_name", ServiceHelper.getAppStoreService().findById(excelpd).getString("store_name"));
					    excelpd.put("city_name", ServiceHelper.getAppStoreService().findById(excelpd).getString("city_name"));
					    excelpd.put("area_name", ServiceHelper.getAppStoreService().findById(excelpd).getString("area_name"));
					    excelpd.put("password", "123456");
					    excelpd.put("zhuce_shebei", "5");
   					    if( TongYong.saveMember(excelpd,store_id, "1").getString("flag").equals("true")){
					    	//发送短信
					    	SmsUtil.saveMemberByStore(excelpd.getString("var2"),ServiceHelper.getAppStoreService().findById(excelpd).getString("store_name")  );
					    	continue;
					    }
 						if(excelpd.getString("var1").equals("男")){
							excelpd.put("var1", "1");
						}else if(excelpd.getString("var1").equals("女")){
							excelpd.put("var1", "2");
						}else{
							excelpd.put("var1", "0");
						}
 						try {
							if(excelpd.getString("var3") != null && !excelpd.getString("var3").trim().equals("")){
								excelpd.put("born_date", excelpd.getString("var3").trim());//时间
							}else{
								excelpd.put("born_date", DateUtil.getDay()); 
								excelpd.put("var3", DateUtil.getDay()); 
							}
						} catch (Exception e) {
							// TODO: handle exception
							excelpd.put("born_date", DateUtil.getDay()); 
							excelpd.put("var3", DateUtil.getDay()); 
						}
 						//判断是否已经导入
						PageData vippd=vipService.findExcelVIPFindById(excelpd);
						if(vippd == null){
 							vipService.insetList(excelpd);
						}else{
							vippd.put("sex", excelpd.getString("var1"));
							vippd.put("name", excelpd.getString("var0"));
							vippd.put("born_date", excelpd.getString("var3").trim());
							vipService.editMemberByStore(vippd);
						}
						excelpd=null;
  				}
				mv.setViewName("redirect:goMyVIP.do?store_id="+store_id+"&source_type="+source_type);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}		
 		return mv;
	}
	
	
	/**
	 * 
	* 方法名称:：downExcel 
	* 方法描述：下载excel表格
	* 创建人：魏汉文
	* 创建时间：2016年8月12日 下午3:25:03
	 */
	@RequestMapping(value="/downExcel")
	public ModelAndView downExcel(){
		logBefore(logger, "下载excel表格");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("姓名");	//1
			titles.add("性别");	//2
			titles.add("手机号码");	//3
			titles.add("生日");	//4
//			titles.add("积分余额");	//4
//			titles.add("最后一次消费时间");	//5
//			titles.add("本店累计消费金额");	//6
			titles.add("备注");	//7
			dataMap.put("titles", titles);
 			List<PageData> varList = new ArrayList<PageData>();
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
 	
	@Resource(name="storepcService")
	private StorepcService storepcService;
	/**
	 * 
	 * 推荐会员注册。发送短信
	 */
	@RequestMapping(value="/sendMessageByPhone")
	@ResponseBody
	public Object sendMessageByPhone() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="推荐成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
//			String store_id=pd.getString("store_id");
			PageData e=storepcService.findById(pd);
			String str=pd.getString("str");
			if(str.equals("0")){
				List<PageData> vip_list=vipService.findExcelVIPallList(pd);//商家会员（线下）
				for(PageData e1 : vip_list){
					String phone=e1.getString("phone");
					//判断是否注册过
					PageData ispd=new PageData();
					ispd.put("phone", phone);
					ispd=appMemberService.detailByPhone(ispd);
					if(ispd == null){
						    SmsUtil.TjFrinendSave(phone, e.getString("store_id"),e.getString("store_name"));
						   //将信息存入数据库中
			 				pd.put("type", "1");
			 				pd.put("id", pd.getString("store_id"));
			 				pd.put("be_phone",phone);
			 				appMemberService.saveTuiJian(pd);
					}
				}
			}else{
				 String[] phoneStr=str.split("@");
				 for(int i = 0 ; i<phoneStr.length ; i++){
					    String be_phone=phoneStr[i];
					   //判断是否注册过
						PageData ispd=new PageData();
						ispd.put("phone", be_phone);
						ispd=appMemberService.detailByPhone(ispd);
						if(ispd == null){
							    SmsUtil.TjFrinendSave(be_phone, e.getString("store_id"),e.getString("store_name"));
							   //将信息存入数据库中
				 				pd.put("type", "1");
				 				pd.put("id", pd.getString("store_id"));
				 				pd.put("be_phone",be_phone);
				 				appMemberService.saveTuiJian(pd);
						}
	 			 }
			}
 		} catch (Exception e) {
			result = "0";
			message="推荐成功";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		return AppUtil.returnObject(pd, map);
	}
	
}
