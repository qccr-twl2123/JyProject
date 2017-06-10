package com.tianer.controller.storeapp.storeapp_goods;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianer.controller.base.BaseController;
import com.tianer.service.storeapp.Storeapp_goodsService;
import com.tianer.service.storepc.store_marketing.Storepc_marketingService;
import com.tianer.service.storepc.store_marketingtype.Storepc_marketingtypeService;
import com.tianer.service.storepc.store_scoreway.Storepc_scorewayService;
import com.tianer.util.AppUtil;
import com.tianer.util.DateUtil;
import com.tianer.util.FileUpload;
import com.tianer.util.PageData;

/** 
 * 类名称：GoodsController
 * 创建人：刘耀耀
 * 创建时间：2016-06-20
 */
@Controller("Storeapp_goodsService")
@RequestMapping(value="/storeapp_goods")
public class Storeapp_goodsController extends BaseController {
	
	@Resource(name="storeapp_goodsService")
	private Storeapp_goodsService storeapp_goodsService;
	
	/**
	 * 添加商品
	 * 刘耀耀
	 * 2016.07.04
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(
			@RequestParam("image_url") MultipartFile file,
 			@RequestParam String goods_name,
			@RequestParam String market_money,
			@RequestParam String retail_money,
			@RequestParam String promotion_content,
			@RequestParam String goods_category_id,
			@RequestParam String integral_rate,
			String integral_number,
			String starttime,
			String endtime,
			@RequestParam String promotion_type,
			@RequestParam String store_id,
			@RequestParam String sort,
			@RequestParam String company
			){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="添加成功";
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
 				pd.put("goods_name",goods_name);
				pd.put("market_money", market_money);	//主键
				pd.put("retail_money", retail_money);
				pd.put("goods_category_id", goods_category_id);
 				String da = DateUtil.getDays();
				if(starttime == null || starttime.equals("")){
					pd.put("starttime", da);
				}else {
					pd.put("starttime", starttime);
				}
				if(endtime == null || endtime.equals("")){
					pd.put("endtime", "20180101");
				}else {
					pd.put("endtime", endtime);
				}
 				pd.put("promotion_type",promotion_type);
				pd.put("promotion_content", promotion_content);
				if(Storeapp_goodsController.GoodsYingXiaoFlag(promotion_type,promotion_content).get("result").equals("0")){
					return GoodsYingXiaoFlag(promotion_type,promotion_content);
				};
 				pd.put("integral_rate",integral_rate);
				pd.put("store_id", store_id);
				pd.put("sort", sort);
				pd.put("company", company);
				pd.put("integral_number", integral_number);
				pd.put("goods_id", BaseController.getTimeID());
				String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
				String filePath = "/storegoodsFile";//文件上传路径goodsFile文件夹
				if(file != null){
					String advicefilename =  FileUpload.fileUp(file, currentPath+filePath, BaseController.getTimeID());//字符拼接，上传到服务器上
					String img_url = AppUtil.getuploadRootUrlIp()+filePath+"/"+advicefilename;
 					pd.put("image_url", img_url);
				}else{
					pd.put("image_url", "");
				}
			storeapp_goodsService.save(pd);//添加商品
 		} catch(Exception e){
			result = "0";
			message="添加失败";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
		pd=null;
		return map;
	}
	
	
	
	/**
	 * 修改商品
	 */
	@RequestMapping(value="/editGoods")
	@ResponseBody
	public Object editGoods(
//			@RequestParam("image_url") MultipartFile file,
 			@RequestParam String goods_name,
			@RequestParam String market_money,
			@RequestParam String retail_money,
			@RequestParam String promotion_content,
 			@RequestParam String integral_rate,
 			String integral_number,
			String starttime,
			String endtime,
			@RequestParam String promotion_type,
			@RequestParam String store_id,
			@RequestParam String goods_id,
			@RequestParam String sort,
			@RequestParam String company,
			HttpServletRequest request
			){
//		//logBefore(logger, "编辑商品");
		//使用reques获取图片
		boolean flag = true;
		MultipartHttpServletRequest multipartRequest = null;
		try {
			multipartRequest = (MultipartHttpServletRequest)request;			
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
		}
		MultipartFile image_url = null;
		if(flag){
			image_url = multipartRequest.getFile("image_url");
		}
		//方法结束
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="编辑成功";
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
 				pd.put("goods_name",goods_name);
				pd.put("market_money", market_money);	//主键
				pd.put("retail_money", retail_money);
				pd.put("starttime", starttime);
				pd.put("endtime", endtime);
				pd.put("promotion_type", promotion_type);
				pd.put("promotion_content", promotion_content);
				if(Storeapp_goodsController.GoodsYingXiaoFlag(promotion_type,promotion_content).get("result").equals("0")){
					return GoodsYingXiaoFlag(promotion_type,promotion_content);
				};
				pd.put("integral_rate",integral_rate);
				pd.put("store_id", store_id);
				pd.put("sort", sort);
				pd.put("goods_id", goods_id);
				pd.put("company", company);
				pd.put("integral_number", integral_number);
 				String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
				String filePath = "/storegoodsFile";//文件上传路径goodsFile文件夹
				if(image_url != null){
					String advicefilename =  FileUpload.fileUp(image_url, currentPath+filePath, BaseController.getTimeID());//字符拼接，上传到服务器上
					String img_url = AppUtil.getuploadRootUrlIp()+filePath+"/"+advicefilename;
					//logBefore(logger, DateUtil.getTime().toString()+":上传文件，文件地址是："+AppUtil.getuploadRootUrlIp());	
					pd.put("image_url", img_url);
				}else{
					pd.put("image_url", "");
				}
			storeapp_goodsService.editGoods(pd);//添加商品
 		} catch(Exception e){
			result = "0";
			message="添加失败";
			map.put("data","");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
		pd=null;
		return map;
	}
	
	
	/**
	 * 商品列表
	 * 刘耀耀
	 * 2016.07.04
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list(){
//		//logBefore(logger, "商品列表");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData>  varList=storeapp_goodsService.list(pd);
			if(varList.size()==0){
				map.put("data", "");
			}else{
				map.put("data",varList);
			}
			varList=null;
		} catch(Exception e){
			result = "0";
			map.put("data", "");
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("messgae", message);
		pd=null;
		return map;
	}
	
	/**
	 * 商品删除
	 * 刘耀耀
	 * 2016.07.04
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(){
//		//logBefore(logger, "商品删除");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="删除成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(pd == null){
				result="0";
			}else{
				storeapp_goodsService.delete(pd);
			}
 		} catch(Exception e){
			result = "0";
 			message="删除失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("messgae", message);
		map.put("data", "");
		pd=null;
		return map;
	}
	
	/**
	 * 商品类别列表
	 * 刘耀耀
	 * 2016.07.04
	 */
	@RequestMapping(value="/listType")
	@ResponseBody
	public Object listType(){
//		//logBefore(logger, "商品列表");
		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData>  varList=storeapp_goodsService.listType(pd);//商品大类列表
			for (PageData p : varList) {
				PageData e=new PageData();
 				e.put("category_parent_id", p.getString("goods_category_id"));
 				e.put("store_id", pd.getString("store_id"));
 				List<PageData>  xiaoType=storeapp_goodsService.listXiaoType(e);//商品小类列表
				p.put("xiaoList", xiaoType);
				xiaoType=null;
				e=null;
			}
			map.put("data", varList);
			varList=null;
   		} catch(Exception e){
			result = "0";
			map.put("data", "");
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("messgae", message);
		pd=null;
 		return map;
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	

	@Resource(name = "storepc_scorewayService")
	private Storepc_scorewayService storepcScorewayService; 
	
	@Resource(name = "storepc_marketingService")
	private Storepc_marketingService storepcMarketingService;
	@Resource(name = "storepc_marketingtypeService")
	private Storepc_marketingtypeService storepcMarketingTypeService;
	
	/**
	 * 获取积分率
	 * 0918
	 */
	@RequestMapping(value="/getIntegral_Rate")
	@ResponseBody
	public Object getIntegral_Rate(){
 		Map<String,Object> map = new HashMap<String,Object>();
		DecimalFormat    df   = new DecimalFormat("######0.00"); 
 		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//获取商家id以及原价及商品id
			String money=pd.getString("retail_money");
			
			if(money != null && money.length()>0 &&  (money.indexOf(".") == money.length()-1)){
				money=money.substring(0,money.length()-1);
			}
			if(money == null || money.equals("")){
				money="0";
 			}
			double n=Double.parseDouble(money);
 			String integral_rate="0";
			String integral_number="0";
			//新增/修改的商品大类
			PageData goodpd=new PageData();
			goodpd=storeapp_goodsService.findByIdForWca(pd);
			//判断是否有进行积分设置
			PageData jfpd=storepcScorewayService.findById(pd);
			pd.put("change_type", "");
			if(jfpd != null ){
				String change_type=jfpd.getString("change_type");
				pd.put("change_type", change_type);
				if(change_type.equals("1")){
					integral_rate=jfpd.getString("oneback_rate");//不可修改
 				}else if(change_type.equals("2")){
 					if(goodpd != null && !goodpd.equals("")){
 						integral_rate=goodpd.getString("back_rate");//不可修改
 					}
				}else if(change_type.equals("3")){
					integral_rate=jfpd.getString("threemax_rate");//可手动修改的
				}else{
					integral_rate="0";
				}
				if(integral_rate != null && integral_rate != ""){
					integral_number=df.format(Double.parseDouble(integral_rate)/100*n);
				}
 			}else{
 				result="0";
 				message="当前未设置积分率，请先前往设置";
 			}
 			pd.put("integral_rate", integral_rate);
 			pd.put("integral_number", integral_number);
   		} catch(Exception e){
			result = "0";
 			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("messgae", message);
		map.put("data", pd);
		pd=null;
 		return map;
	}
	
	/**
	 * 判断商品的营销措施有没有问题
	 */
	public static Map<String,Object> GoodsYingXiaoFlag(String promotion_type,String promotion_content){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="没有问题";
		try {
			if(promotion_type != null && promotion_content != null){
				if( promotion_type.equals("1") ){
					double manmoney=Double.parseDouble(promotion_content.substring(promotion_content.indexOf("满")+1, promotion_content.indexOf("元")));
					double jianmoney=Double.parseDouble(promotion_content.substring(promotion_content.indexOf("减")+1, promotion_content.lastIndexOf("元")));
					if(manmoney <= 0){
						map.put("result", "0");
						map.put("message", "满金额必须大于0");
						map.put("data", "");
 						return map;
					}
					if(jianmoney <= 0){
						map.put("result", "0");
						map.put("message", "减金额必须大于0");
						map.put("data", "");
 						return map;
					}
					if(manmoney - jianmoney <= 0){
						map.put("result", "0");
						map.put("message", "满金额必须大于减金额");
						map.put("data", "");
 						return map;
					}
					}else if(promotion_type.equals("2")){
					double zhenumber=Double.parseDouble(promotion_content.substring(0, promotion_content.indexOf("折")));
					if(zhenumber <= 0){
						map.put("result", "0");
						map.put("message", "折扣必须大于0");
						map.put("data", "");
 						return map;
					}
					}else if(promotion_type.equals("3")){
						double mainumber=Double.parseDouble(promotion_content.substring(promotion_content.indexOf("买")+1, promotion_content.indexOf("件")));
					if(mainumber <= 1){
						map.put("result", "0");
						map.put("message", "件数必须大于1");
						map.put("data", "");
 						return map;
					}
					}else{
						//无营销措施
					}
			}
		} catch (Exception e) {
			// TODO: handle exception
 		}
		map.put("result", result);
		map.put("messgae", message);
 		return map;
	}
	
	
	
}
