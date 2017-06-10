package com.tianer.controller.zhihui.dangan;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.tianer.util.AppUtil;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.service.business.friend.FriendService;
import com.tianer.service.business.member.MemberService;
import com.tianer.service.business.store.StoreService;

/** 
 * 类名称：FriendController
 * 创建人：cyr
 * 创建时间：2016-05-26
 */
@Controller
@RequestMapping(value="/zhihui_friend")
public class ZhihuiFriendController extends BaseController {
	
	@Resource(name="friendService")
	private FriendService friendService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		//logBefore(logger, "新增Friend");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("FRIEND_ID",BaseController.getTimeID());	//主键
		friendService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		//logBefore(logger, "删除Friend");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			friendService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		//logBefore(logger, "修改Friend");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		friendService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**
	 * 人脉联查列表
	 * 魏汉文
	 * 20160608
	 */
	@RequestMapping(value="/contactsList")
	public ModelAndView contactsList(){
		//logBefore(logger, "列表Friend");
		ModelAndView mv = this.getModelAndView();
		List<PageData> firstContacts=new ArrayList<PageData>();
		List<PageData> twoContacts=new ArrayList<PageData>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String content = pd.getString("content");
			String check_type=pd.getString("check_type");
			if(content == null || content.equals("")){
					//System.out.println("为空");
			}else{
						if(check_type.equals("1")){//商家
 							List<PageData> storeList=storeService.listAllByPhoneForLike(pd);
							if(storeList.size() >0 ){
								pd.put("id", storeList.get(0).getString("store_id"));
							}else{
								pd.put("firstnumber", firstContacts.size());
								pd.put("twonumber", twoContacts.size());
								mv.setViewName("zhihui/dangan/dangan16");
					 			mv.addObject("pd", pd);
								return mv;
							}
							storeList=null;
			  			}else{//会员
				  				pd.put("phone", content.trim());
				  				List<PageData> memberList=memberService.listAllByPhone(pd);	
				  				if(memberList.size() >0 ){
				  					pd.put("id", memberList.get(0).getString("member_id"));
				  				}else{
				  					pd.put("firstnumber", firstContacts.size());
				  					pd.put("twonumber", twoContacts.size());
				  					mv.setViewName("zhihui/dangan/dangan16");
				  		 			mv.addObject("pd", pd);
				  					return mv;
				  				}
				  				memberList=null;
			   			}
						logger.info("获取一度人脉");
						List<PageData>	oneList = friendService.contactsList(pd);	//获得一/二度人脉列表
						for(PageData e : oneList){
								if(e.getString("contacts_type").equals("1")){//商家
									    PageData msppd=new PageData();
									    msppd.put("store_id", e.getString("contacts_id"));
									    msppd=storeService.detailById(msppd);
									    msppd.put("id", msppd.getString("store_id"));
										logger.info("获取二度人脉");
										List<PageData>	twoList = friendService.contactsList(msppd);	//获得一/二度人脉列表
										List<PageData> list=new ArrayList<PageData>();
										for(PageData m: twoList){
											 	PageData msppd2=new PageData();
			 									if(m.getString("contacts_type").equals("1")){//商家
			 											msppd2.put("store_id", m.getString("contacts_id"));
			 											msppd2=storeService.detailById(msppd2);
														twoContacts.add(msppd2);
														list.add(msppd2);
												}else if(m.getString("contacts_type").equals("2")){//会员
														msppd2.put("member_id", m.getString("contacts_id"));
														msppd2=memberService.detailById(msppd2);
														twoContacts.add(msppd2);
														list.add(msppd2);
							 					}
			 									msppd2=null;
			 							}
										msppd.put("list", list);
 										firstContacts.add(msppd);
										msppd=null;
										twoList=null;
										list=null;
								}else if(e.getString("contacts_type").equals("2")){//会员
									 	PageData msppd=new PageData();
									 	msppd.put("member_id", e.getString("contacts_id"));
									 	msppd=memberService.detailById(msppd);
										if(msppd != null && !msppd.equals("")){
											msppd.put("id", msppd.getString("member_id"));
											logger.info("获取二度人脉");
											List<PageData>	twoList = friendService.contactsList(msppd);	//获得一/二度人脉列表
											List<PageData> list=new ArrayList<PageData>();
											for(PageData m: twoList){
													PageData msppd2=new PageData();
				 									if(m.getString("contacts_type").equals("1")){//商家
				 											msppd2.put("store_id", m.getString("contacts_id"));
				 											msppd2=storeService.detailById(msppd2);
															list.add(msppd2);
															twoContacts.add(msppd2);
													}else{//会员
															msppd2.put("member_id", m.getString("contacts_id"));
															msppd2=memberService.detailById(msppd2);
															twoContacts.add(msppd2);
															list.add(msppd2);
								 					}
				 									msppd2=null;
											}
											msppd.put("list", list);
											firstContacts.add(msppd);
											twoList=null;
											list=null;
										}
										msppd=null;
			 					}
						}
			}
  			this.getHC(); //调用权限
			pd.put("firstnumber", firstContacts.size());
			pd.put("twonumber", twoContacts.size());
			mv.setViewName("zhihui/dangan/dangan16");
 			mv.addObject("pd", pd);
 			mv.addObject("firstContacts", firstContacts);
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	@Resource(name="storeService")
	private StoreService storeService;
	@Resource(name="memberService")
	private MemberService memberService;
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		//logBefore(logger, "去新增Friend页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("business/friend/friend_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		//logBefore(logger, "去修改Friend页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = friendService.findById(pd);	//根据ID读取
			mv.setViewName("business/friend/friend_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		//logBefore(logger, "批量删除Friend");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				friendService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		//logBefore(logger, "导出Friend到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("邀请人ID");	//1
			titles.add("被邀请人ID");	//2
			titles.add("邀请状态");	//3
			titles.add("被邀请人的类型");	//4
			titles.add("邀请人类型");	//5
			titles.add("邀请时间");	//6
			dataMap.put("titles", titles);
			List<PageData> varOList = friendService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("INVITE_ID"));	//1
				vpd.put("var2", varOList.get(i).getString("BE_INVITE_ID"));	//2
				vpd.put("var3", varOList.get(i).getString("INVITE_STATUS"));	//3
				vpd.put("var4", varOList.get(i).getString("BE_INVITE_TYPE"));	//4
				vpd.put("var5", varOList.get(i).getString("INVITE_ID_TYPE"));	//5
				vpd.put("var6", varOList.get(i).getString("INVITE_TIME"));	//6
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/* ===============================权限================================== */
	public void getHC(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("9"));
		}
 	}
	/* ===============================权限================================== */
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
