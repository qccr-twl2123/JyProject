package com.tianer.controller.storepc.store_operator;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sun.istack.internal.logging.Logger;
import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.entity.zhihui.StoreRole;
import com.tianer.service.business.operator_file.Operator_fileService;
import com.tianer.service.business.store_shift.Store_shiftService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
import com.tianer.service.storepc.store_operator.Storepc_operator_fileService;
import com.tianer.service.storepc.stotr.StorepcService;
import com.tianer.service.storepc.tableNumber.TablerNumberService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.FileDownload;
import com.tianer.util.FileUpload;
import com.tianer.util.MD5;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;
import com.tianer.util.SmsUtil;
import com.tianer.util.ErWerMa.NewFileUpload;
import com.tianer.util.ErWerMa.OneEr;
import com.tianer.util.huanxin.HuanXin;

/** 
 * 
* 类名称：Operator_fileController   
* 类描述：pc端操作员   
* 创建人：刘耀耀
* 创建时间：2016年7月214日 
 */
@Controller("Storepc_operator_fileController")
@RequestMapping(value="/storepcOperator_file")
public class Storepc_operatorController extends BaseController {
	
	@Resource(name="storeManageService")
	private StoreManageService storeManageService;
	
		/**
		 * 到操作员页面
		 */
		@RequestMapping(value="/findOperator")
		public ModelAndView findOperator(Page page) throws Exception{
			ModelAndView modelAndView = this.getModelAndView();
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();	
			StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
			PageData pd = this.getPageData();
 			try {
				pd=this.getPageData();
				if(slogin != null && slogin.getType().equals("2")){
					pd.put("store_operator_id", slogin.getLogin_id());
				}
 				if(pd.getString("store_id") != null && !pd.getString("store_id").equals("") ){
					page.setPd(pd);
		 			List<PageData> varList = storeManageService.datalistPage(page);
					modelAndView.addObject("list", varList);
					//获取所有班次
					List<PageData> bcList=store_shiftService.listAll(pd);
					modelAndView.addObject("bcList", bcList);
				}
				String jichushezhi=pd.getString("jichushezhi");
	  			if(jichushezhi != null && jichushezhi.equals("11110000000")){
	  				modelAndView.setViewName("/storepc/shezhi_4");
	  			}else{
	  				modelAndView.setViewName("/storepc/business_base5");
 	  			}
  			} catch (Exception e) {
				// TODO: handle exception
				//System.out.println(e.toString());
			}
 			modelAndView.addObject("pd", pd);
   			return modelAndView;
		}
		
	/**
	 * 新增操作员
	 * 魏0717
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		//logBefore(logger, "新增操作员");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			//System.out.println("新增操作员"+pd);
			String phone=pd.getString("operator_phone");
// 			String operator_name=pd.getString("operator_name");
			String password=BaseController.get6UID();
			pd.put("operator_password",MD5.md5(password)  );
			String sy_competence=pd.getString("syadd")+","+pd.getString("sydelete")+","+pd.getString("syedit")+","+pd.getString("sylook");
			String yx_competence=pd.getString("yxadd")+","+pd.getString("yxdelete")+","+pd.getString("yxedit")+","+pd.getString("yxlook");
			String sp_competence=pd.getString("spadd")+","+pd.getString("spdelete")+","+pd.getString("spedit")+","+pd.getString("splook");
			String hd_competence=pd.getString("hdadd")+","+pd.getString("hddelete")+","+pd.getString("hdedit")+","+pd.getString("hdlook");
			String cw_competence=pd.getString("cwadd")+","+pd.getString("cwdelete")+","+pd.getString("cwedit")+","+pd.getString("cwlook");
			pd.put("sy_competence", sy_competence);
			pd.put("yx_competence", yx_competence);
			pd.put("sp_competence", sp_competence);
			pd.put("hd_competence", hd_competence);
			pd.put("cw_competence", cw_competence);
			pd.put("operator_status", "0");
			//循环
			String store_operator_id="";
			String store_name="";
			if(storepcService.findById(pd) != null && storepcService.findById(pd).getString("store_name") != null){
				store_name=storepcService.findById(pd).getString("store_name");
			}
 			boolean flag=true;
			while(flag){
				store_operator_id= pd.getString("store_id")+this.get4UID();
				pd.put("store_operator_id",store_operator_id);
 				if(storeManageService.findOperatorById(pd) == null){
					flag=false;
 				} 
			}
			//----------
 			storeManageService.save(pd);
			try {
				if(phone.length() == 11){
					//注册环信
					HuanXin.regirstHx(store_operator_id, store_operator_id, store_operator_id);
					//发送给操作员短信
					//System.out.println("phone="+phone+"*****password="+password);
					SmsUtil.sendCzMm(phone, password,store_name,store_operator_id);
				}
 			} catch (Exception e) {
				// TODO: handle exception
			}
			String jichushezhi=pd.getString("jichushezhi");
			if(jichushezhi == null){
				jichushezhi="";
			}
 			mv.setViewName("redirect:findOperator.do?store_id="+pd.getString("store_id")+"&jichushezhi="+jichushezhi);
 		} catch (Exception e) {
			// TODO: handle exception
 			logger.error(e);
			//System.out.println(e.toString());
		}
 		return mv;
	}
	
	/**
	 * 
	* 方法名称:：delOperator 
	* 方法描述：删除操作员
	* 创建人：魏汉文
	* 创建时间：2016年7月16日 下午7:25:46
	 */
	@RequestMapping(value="/delOperator")
	public ModelAndView delOperator() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			if(pd.getString("store_id") != null && !pd.getString("store_id").equals("")){
				storeManageService.delOperatorById(pd );
		  		mv.setViewName("redirect:findOperator.do?store_id="+pd.getString("store_id"));
			}else{
				mv.setViewName("redirect:findOperator.do");
			}
 		} catch (Exception e) {
			// TODO: handle exception
		}
 		return mv;
	}
	
	
	/**
	 * 修改
	 * 刘耀耀
	 * 2013.07.14
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		//logBefore(logger, "修改Operator_file");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if(pd.getString("store_id") != null && !pd.getString("store_id").equals("")){
				String sy_competence=pd.getString("syadd")+","+pd.getString("sydelete")+","+pd.getString("syedit")+","+pd.getString("sylook");
				String yx_competence=pd.getString("yxadd")+","+pd.getString("yxdelete")+","+pd.getString("yxedit")+","+pd.getString("yxlook");
				String sp_competence=pd.getString("spadd")+","+pd.getString("spdelete")+","+pd.getString("spedit")+","+pd.getString("splook");
				String hd_competence=pd.getString("hdadd")+","+pd.getString("hddelete")+","+pd.getString("hdedit")+","+pd.getString("hdlook");
				String cw_competence=pd.getString("cwadd")+","+pd.getString("cwdelete")+","+pd.getString("cwedit")+","+pd.getString("cwlook");
				pd.put("sy_competence", sy_competence);
				pd.put("yx_competence", yx_competence);
				pd.put("sp_competence", sp_competence);
				pd.put("hd_competence", hd_competence);
				pd.put("cw_competence", cw_competence);
				storeManageService.updateOperator(pd);
 	 			mv.setViewName("redirect:findOperator.do?store_id="+pd.getString("store_id"));
			}else{
				mv.setViewName("redirect:findOperator.do");
			}
 		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return mv;
	}
	
	/**
	 * 
	* 方法名称:：updateOperator 
	* 方法描述：停止/启动操作员
	* 创建人：魏汉文
	* 创建时间：2016年7月16日 下午7:29:05
	 */
	@RequestMapping(value="/updateOperator")
	public ModelAndView updateOperator() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			if(pd.getString("store_id") != null && !pd.getString("store_id").equals("")){
				storeManageService.updateOperatorStatus(pd);
		 		mv.setViewName("redirect:findOperator.do?store_id="+pd.getString("store_id"));
			}else{
 		 		mv.setViewName("redirect:findOperator.do");
			}
 		} catch (Exception e) {
			// TODO: handle exception
		}
 		return mv;
	}
	
	/**
	 * 去修改页面
 	 */
	@RequestMapping(value="/goEdit")
	@ResponseBody
	public Object goEdit(){
		//logBefore(logger, "去修改pcOperator_file页面");
		Map< String , Object> map=new HashMap<>();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd = storeManageService.findOperatorById(pd);	//根据ID读取
			List<PageData> allList=new ArrayList<PageData>();
			String[] sy=pd.getString("sy_competence").split(",");
			PageData sypd=new PageData();
			sypd.put("name", "收银");
			sypd.put("type", "sy");
			sypd.put("save", sy[0]);
			sypd.put("del", sy[1]);
			sypd.put("edit", sy[2]);
			sypd.put("look", sy[3]);
  			String[] yx=pd.getString("yx_competence").split(",");
			PageData yxpd=new PageData();
			yxpd.put("name", "营销");
			yxpd.put("type", "yx");
			yxpd.put("save", yx[0]);
			yxpd.put("del", yx[1]);
			yxpd.put("edit", yx[2]);
			yxpd.put("look", yx[3]);
			String[] sp=pd.getString("sp_competence").split(",");
			PageData sppd=new PageData();
			sppd.put("name", "商品");
 			sppd.put("type", "sp");
			sppd.put("save", sp[0]);
			sppd.put("del", sp[1]);
			sppd.put("edit", sp[2]);
			sppd.put("look", sp[3]);
			String[] hd=pd.getString("hd_competence").split(",");
			PageData hdpd=new PageData();
			hdpd.put("name", "互动");
 			hdpd.put("type", "hd");
			hdpd.put("save", hd[0]);
			hdpd.put("del", hd[1]);
			hdpd.put("edit", hd[2]);
			hdpd.put("look", hd[3]);
			String[] cw=pd.getString("cw_competence").split(",");
			PageData cwpd=new PageData();
			cwpd.put("name", "财务");
 			cwpd.put("type", "cw");
			cwpd.put("save", cw[0]);
			cwpd.put("del", cw[1]);
			cwpd.put("edit", cw[2]);
			cwpd.put("look", cw[3]);
			allList.add(sypd);
			allList.add(yxpd);
			allList.add(sppd);
			allList.add(cwpd);
			allList.add(hdpd);
			map.put("allList", allList);
			map.put("data", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return map;
	}	
	
	
	//下面是班次的
	
	@Resource(name="store_shiftService")
	private Store_shiftService store_shiftService;
	
	
	/**
	 * 班次列表
	 * 刘耀耀
	 * 2013.07.14
	 */
	@RequestMapping(value="/list")
	public ModelAndView list() throws Exception{
		//logBefore(logger, "班次列表");
		ModelAndView mv = this.getModelAndView();
 		try {
			PageData pd = new PageData();
			pd = this.getPageData();
			mv.addObject("pd", pd);
			List<PageData> varList=store_shiftService.listAll(pd);
			mv.addObject("varList", varList);
			String jichushezhi=pd.getString("jichushezhi");
  			if(jichushezhi != null && jichushezhi.equals("11111000000")){
  				mv.setViewName("/storepc/shezhi_5");
  			}else{
  				mv.setViewName("storepc/business_base6");
  			}
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString());
		}
 		return mv;
	}
	
	/**
	 * 删除班次
	 * 刘耀耀
	 * 2013.07.14
	 */
	@RequestMapping(value="/delete")
	public ModelAndView delete() throws Exception{
		//logBefore(logger, "班次列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			store_shiftService.delete(pd);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("redirect:list.do?store_id="+pd.getString("store_id"));
		return mv;
	}
	
	/**
	 * 新增
	 * 刘耀耀
	 * 2013.07.14
	 */
	@RequestMapping(value="/saveShift")
	public ModelAndView saveShift() throws Exception{
 		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try {
				pd = this.getPageData();
 				pd.put("store_shift_id", BaseController.getTimeID());	//主键
				store_shiftService.save(pd);
				String jichushezhi=pd.getString("jichushezhi");
				if(jichushezhi == null){
					jichushezhi="";
				}
	 			mv.setViewName("redirect:list.do?store_id="+pd.getString("store_id")+"&jichushezhi="+jichushezhi);
  		} catch (Exception e) {
			// TODO: handle exception
		}
 		return mv;
	}
	
	@Resource(name = "tablerNumberService")
	private TablerNumberService tablerNumberService;
	
	
	/**
	 * 前往生成桌面二维码
	 */
	@RequestMapping(value="/QRCode")
	public ModelAndView QRCode() throws Exception{
		//logBefore(logger, "新增Operator_file");
		ModelAndView mv = this.getModelAndView();
		  List<PageData> varList = new ArrayList<PageData>();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//System.out.println(pd);
			if(pd.getString("store_operator_id") != null && !pd.getString("store_operator_id").equals("")){
				PageData oppd=new PageData();
				oppd=storeManageService.findOperatorById(pd);
				if(oppd != null){
					 String alldesk_no=oppd.getString("alldesk_no");
					 String[] str=alldesk_no.split(",");
					 for (int i = 0; i < str.length; i++) {
						  PageData e=new PageData();
						  e.put("desk_no", str[i]);
						  varList.add(e);
					}
	 			}
			}else{
				varList=tablerNumberService.listAll(pd);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("pd", pd);
		mv.addObject("varList", varList);
 		mv.setViewName("storepc/QRCode");
		return mv;
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	
	@Resource(name="storepcService")
	private StorepcService storepcService;
	
	/**
	 * 判断桌号:操作员ID
	 */
	@RequestMapping(value="/isdesk_no")
	@ResponseBody
	public Object isdesk_no(){
		//logBefore(logger, "判断桌号");
		Map< String , Object> map=new HashMap<>();
		String result="1";
		String message="桌号可生成";
		PageData pd = new PageData();
		try {
			   pd = this.getPageData();
			   String store_operator_id=pd.getString("store_operator_id").trim();
			   String desk_no=pd.getString("desk_no").trim();
			   String store_id=pd.getString("store_id");
			   PageData spd=storepcService.findById(pd);
			   int gg=0;
			   List<PageData> varList = storeManageService.listAll(pd);
			   for (int i = 0; i < varList.size(); i++) {
					   PageData e= varList.get(i);
					   if(e.getString("alldesk_no") != null && e.getString("alldesk_no").contains(desk_no) && !e.getString("store_operator_id").equals(store_operator_id)){
						   gg=1;
						   break;
					   }else  if(e.getString("alldesk_no") != null &&  e.getString("alldesk_no").contains(desk_no) && e.getString("store_operator_id").equals(store_operator_id)){
						   gg=2;
						   break;
					   }
			   }
			   if(gg == 1){
				   result="0";
				   message="当前桌号已有负责人存在";
			   }else if(gg == 0 || gg==2){
				   //生成桌号二维码
	 				String store_name=spd.getString("store_name");
		 			String path_url=Const.ErWeiMa;
		 			String imagename=store_id+"@"+desk_no;
		 			OneEr.print(store_id+"@"+desk_no, store_name+" "+desk_no, imagename,path_url);
		 			path_url=path_url+ "/"+imagename+".png";
//		 			是否需要将图片上传至云服务器
		 			String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
					String filePath = "/opratorZFile";//文件上传路径
					String cityFilename =  FileUpload.fileUpFile(path_url, currentPath+filePath, imagename);//字符拼接，上传到服务器上
					path_url= AppUtil.getuploadRootUrlIp()+filePath+"/"+cityFilename;
					//System.out.println(filePath);
			 		map.put("data", path_url);
		 			//------------------------------------------------------------------
			   }
  		} catch (Exception e) {
			logger.error(e.toString(), e);
		}			
		map.put("result", result);
		map.put("message", message);
		return map;
	}	
	
	
	/**
	 * 下载图片
	 */
	@RequestMapping(value="/downPic")
	@ResponseBody
	public Object downPic(HttpServletResponse response,HttpServletRequest request){
		//logBefore(logger, "下载图片");
		Map< String , Object> map=new HashMap<>();
		String result="1";
		String message="下载图片";
		PageData pd = new PageData();
		try { 
			 pd = this.getPageData();
			 FileDownload.downpicture(response,  request, pd.getString("image_url"));
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}			
		map.put("result", result);
		map.put("message", message);
		return map;
	}	
	
	
	
	
	
//	/**
//	 * 生成桌号二维码
//	 */
//	@RequestMapping(value="/savePicUrl")
//	@ResponseBody
//	public Object savePicUrl(){
//		//logBefore(logger, "判断桌号");
//		Map< String , Object> map=new HashMap<>();
//		String result="1";
//		String message="下载图片";
//		PageData pd = new PageData();
//		try { 
////			pd = this.getPageData();
//			
// 			map.put("data", path_url);
// 		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}			
//		map.put("result", result);
//		map.put("message", message);
//		return map;
//	}	
	
	
	
	
//	public static void main(String[] args) {
//		(new Storepc_operatorController()).savePicUrl();
//	}
	
	
	
	
	
	
}