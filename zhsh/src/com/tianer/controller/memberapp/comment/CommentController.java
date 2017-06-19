package com.tianer.controller.memberapp.comment;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.tongyongUtil.TongYong;
import com.tianer.entity.Page;
import com.tianer.entity.html.HtmlUser;
import com.tianer.service.business.store.StoreService;
import com.tianer.service.memberapp.AppCommentService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppOrderService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.EbotongSecurity;
import com.tianer.util.FileUpload;
import com.tianer.util.PageData;

/** 
 * 类名称：CommentController
 * 创建人：刘耀耀
 * 创建时间：2016-06-20
 */
@Controller("appCommentController")
@RequestMapping(value="/app_comment")
public class CommentController extends BaseController {
	
	@Resource(name="appCommentService")
	private AppCommentService appCommentService;
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	@Resource(name="appOrderService")
	private AppOrderService appOrderService;
	
	/**
	 * 评论列表全部
	 *  魏汉文20160630
	 */
	@RequestMapping(value="/listAll")
	@ResponseBody
	public Object listAll(Page page){
 		Map<String,Object> map = new HashMap<String,Object>();
 		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		PageData _pd=new PageData();
		try{
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
					pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
					//商家ID解密
					String sk_shop=pd.getString("sk_shop");
					String store_id=EbotongSecurity.ebotongDecrypto(sk_shop.substring(4, sk_shop.length()-1));
					pd.put("store_id", store_id);
			}
 			String allstore=appStoreService.countStore();
			//获取商家ID
			pd=appStoreService.findById(pd);
			if(pd != null){
				String score=pd.getString("comment_score");
				_pd.put("score", pd.getString("comment_score"));
				pd.put("score", Double.parseDouble(score));
	 			String nstore=appStoreService.countStoreTwo(pd);
				double number=Double.parseDouble(nstore)/Double.parseDouble(allstore);
				_pd.put("percent", df.format(number));
				List<PageData> commentList=appCommentService.listAll(pd);
				for (int i = 0; i <commentList.size(); i++) {
 						//图片
						List<PageData> imgList=new ArrayList<PageData>();
						PageData e=commentList.get(i);
						//格式化号码
						String phone=e.getString("phone");
 					    if(phone != null &&  phone.length() < 11){
					    	 commentList.remove(i);
					    	 i=i-1;
					    }else{
					    	 phone=phone.substring(0, 3)+"****"+phone.substring(7, phone.length());
					    	 e.put("phone", phone);
					    	 String image_url=e.getString("commentimage_url");
								String[] array=image_url.split(",");
								for (int j = 0; j < array.length; j++) {
									PageData e1=new PageData();
									if(array[j] != ""){
										e1.put("icon_url", array[j]);
										imgList.add(e1);
									}
 									e1=null;
								}
								e.put("imgList", imgList);
					     }
 					    imgList=null;
  				}
				_pd.put("commentList", commentList);
				map.put("data", _pd);
				commentList=null;
				pd=null;
				_pd=null;
			}else{
				result="0";
				message="store_id不能为空";
				map.put("data", "");
			}
   		} catch(Exception e){
			result = "0";
			message="系统错误";
			map.put("data", "");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	/**
	 * 新增评价
	 * 魏汉文20160630
	 */
	@RequestMapping(value="/addComment")
	@ResponseBody
	public Object addComment(
			@RequestParam(value="image_url",required=false) MultipartFile[] file,
			@RequestParam  String star_number, 
			@RequestParam  String store_id, 
			@RequestParam  String member_id, 
			@RequestParam  String order_id, 
			@RequestParam  String content 
 			){
//		logBefore(logger, "新增评价");
		Map<String,Object> map = new HashMap<String,Object>();
		DecimalFormat    df   = new DecimalFormat("######0.0"); 
		String result = "1";
		String message="新增成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String image_url="";
			if(file.length != 0){
				String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
				String filePath = "/commentFile";//文件上传路径
				for(int i=0 ;i<file.length;i++){
					String cityFilename =  FileUpload.fileUp(file[i], currentPath+filePath, BaseController.getTimeID());//字符拼接，上传到服务器上
					String m_img = AppUtil.getuploadRootUrlIp()+filePath+"/"+cityFilename;
					logBefore(logger, DateUtil.getTime().toString()+":上传文件，文件地址是："+AppUtil.getuploadRootUrlIp());	
					image_url+=m_img+",";
				}
 	 		}
			pd.put("store_id", store_id);
			pd.put("content", content);
			pd.put("order_id", order_id);
			pd.put("member_id",member_id);
			pd.put("image_url", image_url);
			pd.put("star_number", star_number);
			//判断是否已经评价过当前订单
			if(appCommentService.findByIdForSave(pd) != null){
				map.put("result", "0");
				map.put("message", "评价失败，当前订单已评价");
				map.put("data", "");
				return map;
			}
  			PageData mpd=appMemberService.findById(pd);
			if(mpd != null ){
				 appCommentService.save(pd);//新增评价以及修改订单评价状态
				 TongYong.charm_numberAdd(member_id, Const.charm_number[6]);
				//修改商家的星级
				PageData startpd=appCommentService.countStart(pd);
				System.out.println(startpd);
				if(startpd != null){
 						Integer count = new Integer(String.valueOf(startpd.get("number"))); 
						if(count != 0){
 							Double sum = new Double(String.valueOf(startpd.get("star_number")));
							double n=sum/count;
							pd.put("comment_score", df.format(n));
							appStoreService.edit(pd);
						}
						  //新增商家的综合分值
						pd=appStoreService.findById(pd);
		   			    logger.info("==========================更新商家的综合分值");
		   			    double complex_score=Double.parseDouble(pd.getString("complex_score"));
		   				int n =(int)(Double.parseDouble(star_number));
		   				PageData compd=new PageData();
		   				compd.put("store_id", pd.getString("store_id"));
		   				if( n==5 ){
		   					complex_score+=Double.parseDouble(Const.complexscore[9]);
	  	   				}else if( n== 4){
	  	   					complex_score+=Double.parseDouble(Const.complexscore[10]);
		   				}else if( n== 3){
		   					complex_score+=Double.parseDouble(Const.complexscore[11]);
		   				}else if( n== 2){
		   					complex_score+=Double.parseDouble(Const.complexscore[12]);
		   				}else if( n== 1){
		   					complex_score+=Double.parseDouble(Const.complexscore[13]);
		   				}
		   			    //根据综合分值增加星级
		   				TongYong.complex_scoreAdd(store_id, TongYong.df0.format(complex_score));
 				}
			}
   		} catch(Exception e){
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
		return map;
	}
	
	
	@Resource(name="storeService")
	private StoreService storeService;
	
	@Resource(name="appOrderService")
	private AppOrderService orderService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
