package com.tianer.controller.fapiao;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.entity.zhihui.zhihuiLogin;
import com.tianer.service.fapiao.FaPiaoService;
import com.tianer.util.Const;
import com.tianer.util.PageData;
 ;


/** 
 * 
* 类名称：FaPiaoController   
* 类描述： 发票信息
 */
@Controller("FaPiaoController")
@RequestMapping(value = "/fapiao")
public class FaPiaoController extends BaseController{ 
	
	@Resource(name="faPiaoService")
	private FaPiaoService faPiaoService;
	
	
	/**
	 * 前往申请发票页面--商家
	 */
	@RequestMapping(value="/goAddFaPiao")
	public ModelAndView goAddFaPiao(){
		logBefore(logger, "前往申请发票页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
 			pd = this.getPageData();
 			String store_id=pd.getString("store_id");
 			//获取开取发票详情
  			pd.put("sq_id", store_id);
 			PageData fppd=faPiaoService.findByIdbillinfor(pd);
 			if(fppd == null){
 				mv.addObject("infor_ok", "0");
 				mv.addObject("fppd", new PageData());
 			}else{
 				mv.addObject("infor_ok", "1");//发票信息是否已经填写好
 				mv.addObject("fppd", fppd);
 			}
 			//获取支付服务费成功未申请发票的数据
 			pd.put("issend_bill", "0");
 			pd.put("money_type", "3");
 			pd.put("user_id", store_id);
			List<PageData> orderList=faPiaoService.listOrderByBill(pd);
			mv.addObject("order_size", orderList.size());
			mv.addObject("orderList", orderList);
 			//获取已经开取发票的列表
			List<PageData> fpList=faPiaoService.billlistAll(pd);
			mv.addObject("fp_size", fpList.size());
			mv.addObject("fpList", fpList);
			mv.setViewName("fapiao/fapiao");
 		} catch (Exception e) {
			logger.error(e.toString(), e);
		}	
		mv.addObject("pd", pd);
		return mv;
	}	
	
	
	
	
	/**
	 * 保存申请发票填写的个人信息
	 * billinfor_id,bill_tt,bill_type,nsr_type, yy_address,yy_phone,swdj_number,
	 * sj_name,sj_address,sj_phone,postcode,sq_id,sq_type,createtime
 	 */
 	@RequestMapping(value="/savebillinfor")
	@ResponseBody
	public Object savebillinfor( ){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="保存申请发票填写的个人信息";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			PageData inforpd=faPiaoService.findByIdbillinfor(pd);
			if(inforpd == null  ){
				faPiaoService.savebillinfor(pd);
			}else{
				faPiaoService.updatebillinfor(pd);
			}
 		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data",pd);
		return map;
	}
  
 	
 	/**
 	 * 最后一步，点击索取发票
 	 * bill_id,
 	 * bill_money,billinfor_id,
 	 * kd_name,kd_number,
 	 * sp_address,principal,phone,
 	 * chuli_status 
 	 */
 	@RequestMapping(value="/saveBill")
 	@ResponseBody
 	public Object saveBill(){
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
 		String message="新增发票";
 		PageData pd = new PageData();
 		try{
 			pd = this.getPageData();
 			String allorder=pd.getString("allorder");
 			String bill_id=BaseController.get8UID();
 			pd.put("bill_id", bill_id);
 			faPiaoService.savebill(pd);
 			//新增发票关联的订单记录
 			String[] orderstr=allorder.split(",");
 			for (int i = 0; i < orderstr.length; i++) {
				PageData _pd=new PageData();
				_pd.put("bill_id", bill_id);
				_pd.put("waterrecord_id", orderstr[i]);
 				faPiaoService.savebillandid(_pd);
 				//修改开取发票的状态
 				faPiaoService.updatebillStatusByWater(_pd);
 				_pd=null;
			}
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
 	 * 发票详情：bill_id
 	 */
 	@RequestMapping(value="/fapiaoDetail")
 	@ResponseBody
 	public Object fapiaoDetail(){
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
 		String message="索取发票前往第二步";
 		PageData pd = new PageData();
 		try{
 			pd = this.getPageData();
 			pd=faPiaoService.findByIdbill(pd);
 			if(pd == null){
 				result="0";
 				message="当前发票不存在";
 			}else{
 				List<PageData> billlistandid=faPiaoService.billlistandid(pd);
 				pd.put("billlistandid", billlistandid);
 			}
 			map.put("data",pd);
 		} catch(Exception e){
 			result="0";
 			map.put("message", "获取异常");
 			logger.error(e.toString(), e);
 		}
 		map.put("result", result);
 		map.put("message",message);
  		return map;
 	}
 	
 	
 	//===========================
 	
 	

	/**
	 * 修改开取发票状态
	 */
	@RequestMapping(value="/updatebill")
	public void updatebill(PrintWriter out){
		logBefore(logger, "修改开取发票状态");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			faPiaoService.updatebill(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 	}
 	
	
	
	
 	 
 	/**
	 *总后台--发票列表
	 */
	@RequestMapping(value="/fapiaolist")
	public ModelAndView fapiaolist(Page page){
		logBefore(logger, "总后台--发票列表");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(zhlogin != null && zhlogin.getMenu_role_id() != null){
				//登陆用户的角色id:0-管理员，1-服务商，2-业务员，3-操作员，4-其他
				String login_type=zhlogin.getMenu_role_id();
				pd.put("login_type", login_type);
				if(login_type.equals("1")){
					pd.put("sp_file_id", zhlogin.getLogin_id());
 				} 
			}
			String chuli_status = pd.getString("chuli_status");
			//当前页
			String currentPage = pd.getString("currentPage");
			if(null != currentPage && !"".equals(currentPage)){
				page.setCurrentPage(Integer.parseInt(currentPage));
			}
 			page.setPd(pd);
			List<PageData>	varList =faPiaoService.billlistPage(page);
			if(chuli_status.equals("0")){//待开发票
				this.getHC51();  
				mv.setViewName("zhihui/yingxiao/fapiao1");
			}else if(chuli_status.equals("1")){//待寄发票
				this.getHC52();  
				mv.setViewName("zhihui/yingxiao/fapiao2");
			}else{//已开发票
				this.getHC53();  
				mv.setViewName("zhihui/yingxiao/fapiao3");
			}
 			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	

	/* ===============================权限================================== */
	public void getHC51(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		session.setAttribute("qx", map.get("51"));
	}
	/* ===============================权限================================== */
	

	/* ===============================权限================================== */
	public void getHC52(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		session.setAttribute("qx", map.get("52"));
	}
	/* ===============================权限================================== */
	

	/* ===============================权限================================== */
	public void getHC53(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		session.setAttribute("qx", map.get("53"));
	}
	/* ===============================权限================================== */
	
	
	
	 
 	/**
	 *总后台--查看发票详情
	 */
	@RequestMapping(value="/look_detail")
	public ModelAndView look_detail(){
		logBefore(logger, "总后台--发票列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
   			List<PageData>	varList =faPiaoService.billlistandid(pd);
  			mv.setViewName("zhihui/yingxiao/fapiao1_detail");
 			mv.addObject("varList", varList);
 			pd=faPiaoService.findByIdbill(pd);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 *总后台--去填写快递信息
	 */
	@RequestMapping(value="/goAddKD")
	public ModelAndView goAddKD(){
		logBefore(logger, "总后台--去填写快递信息");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			PageData fppd=faPiaoService.findByIdbill(pd);
			mv.setViewName("zhihui/yingxiao/fapiao2_kd");
			mv.addObject("fppd", fppd);
 			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 *总后台--更改发票（快递）信息
	 */
	@RequestMapping(value="/startSend")
	public ModelAndView startSend(){
		logBefore(logger, "总后台--更改发票（快递）信息");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
  			faPiaoService.updatebill(pd);
 			mv.addObject("msg","success");
 			mv.setViewName("save_result");
 			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
