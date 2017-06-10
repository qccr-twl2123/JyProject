package com.tianer.controller.zhaoshang;

 import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

 import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.tongyon.TYAllSortService;
import com.tianer.service.zhaoshang.ZhaoShangService;
import com.tianer.util.Const;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;
import com.tianer.util.SmsUtil;
  
/** 
 * 
* 招商类
* 创建人：魏汉文  
* 创建时间：2016年5月25日 下午4:45:36
 */
@Controller
@RequestMapping(value="/zhaoshang")
public class ZhaoShangController extends BaseController {
	
	@Resource(name="zhaoShangService")
	private ZhaoShangService zhaoShangService;
	 
	@Resource(name="city_fileService")
	private City_fileService city_fileService;
	
	@Resource(name="pcdService")
	private PcdService pcdService;
	
 	
	//--------------------------------------公司招商-----------------------------------------------
	/**
	 * 去招商页面
	 * 
	 * zhaoshang/goZhaoshangOne.do
	 */
	@RequestMapping(value="/goZhaoshangOne")
	public ModelAndView goZhaoshangOne(){
		logBefore(logger, "去招商页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<PageData> provincelist=pcdService.listAll(pd); 
			mv.addObject("provincelist", provincelist);
  			mv.setViewName("zhaoshang/goaddcompany");
 			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	 
	/**
	 * 查看当前的企业是否已经提交：所有
	 * localhost/zhsh/zhaoshang/ajaxAddCompay.do
	 */
	@RequestMapping(value="/ajaxAddCompay")
	@ResponseBody
	public Object ajaxAddCompay( ){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="Ok";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			System.out.println(pd.toString());
  			if(zhaoShangService.findByIdCompay(pd) != null){
 				result="0";
 				message="当前企业已经提交过，请拨打企业联系电话";
 			}else{
 				zhaoShangService.saveCompay(pd);
 			}
 		} catch(Exception e){
			result="0";
			message="获取异常";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return map;
	}
	
	//==================================领头羊========================================

	@Resource(name="tYAllSortService")
	private TYAllSortService tYAllSortService;
	
	
	/**
	 * 前往招商首页
	 * zhaoshang/goZhaoShangHd.do
	 */
	@RequestMapping(value="/goZhaoShangHd")
	public ModelAndView GoZhaoShangHd(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			 pd = this.getPageData();
			 List<PageData> ltyList=tYAllSortService.listAllLTYZhaoShangInfor(pd);
			 mv.addObject("ltyList", ltyList);
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("zhaoshang/ltyhd");
		return mv;
	}
	
	
	/**
	 * zhaoshang/saveZhaoShangInfor.do
	 *  新增领头羊活动报名信息
	 *  姓名、联系电话、预计到达人数、预计到达时间、建议留言
	 *  name phone  yjarrive_number yjarrive_time remark
	 */
	@RequestMapping(value="/saveZhaoShangInfor")
	@ResponseBody
	public Object SaveZhaoShangInfor(){
 		Map<String,Object> map = new HashMap<String,Object>();
     	String result="1";
  		String message="新增成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(tYAllSortService.detailLTYZhaoShangInforByPhone(pd) == null){
				String only_id=BaseController.get8UID();//参加编号
				pd.put("only_id",only_id );
				tYAllSortService.saveLTYZhaoShangInfor(pd);
				//发送短信
				SmsUtil.LTYSendMessage(pd.getString("phone"), pd.getString("name"), pd.getString("yjarrive_time"), pd.getString("yjarrive_number"));
 			}else{
				result="0";
				message="你已成功报名过，欢迎你的到来";
			}
       	} catch(Exception e){
     		result="0";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	
	
	
	/**
	 * zhaoshang/detailZhaoShangInfor.do
	 *   查询领头羊活动报名信息
	 *  phone 报名电话
	 */
	@RequestMapping(value="/detailZhaoShangInfor")
	@ResponseBody
	public Object DetailZhaoShangInfor(){
 		Map<String,Object> map = new HashMap<String,Object>();
     	String result="1";
  		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			if(tYAllSortService.detailLTYZhaoShangInforByPhone(pd)== null){
				result="0";
				message="当前号码还未报名，请先前往报名";
			}
			map.put("data", tYAllSortService.detailLTYZhaoShangInforByPhone(pd));
       	} catch(Exception e){
     		result="0";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	
	/**
	 * zhaoshang/listAllLTYZhaoShangInfor.do
	 *   所有的领头羊活动报名信息
 	 */
	@RequestMapping(value="/listAllLTYZhaoShangInfor")
	@ResponseBody
	public Object ListAllLTYZhaoShangInfor(){
 		Map<String,Object> map = new HashMap<String,Object>();
     	String result="1";
  		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			 List<PageData> ltyList=tYAllSortService.listAllLTYZhaoShangInfor(pd);
			 map.put("data", ltyList);
        } catch(Exception e){
     		result="0";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	
	
	
	
}
