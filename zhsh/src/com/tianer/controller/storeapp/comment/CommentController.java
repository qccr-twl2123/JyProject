package com.tianer.controller.storeapp.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.service.memberapp.AppGoodsService;
import com.tianer.service.storeapp.StoreCommentService;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;

/** 
* 类名称：ChatRedController
* 创建人：邢江涛  商家评论
* 创建时间：2016-07-4 
*/
@Controller("CommentController")
@RequestMapping(value="/storeapp_comment")
public class CommentController extends BaseController{
	

	@Resource(name="storeCommService")
	private StoreCommentService storeCommentService;
	
	
	/**
	 * 商家评论,page,store_id
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list(Page page){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String currentPage=pd.getString("page");
			if(currentPage == null ||currentPage.equals("") || currentPage.equals("0")){
				currentPage="1";
			}
			page.setCurrentPage(Integer.parseInt(currentPage));
			page.setPd(pd);
			List<PageData> varList = storeCommentService.datalistPage(page);
			if(varList.size() > 0){
				map.put("data", varList);
			}else {
				map.put("data", "");
			}
			varList=null;
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
	 * 商家评论,store_id
	 * @param  
	 * @return
	 */
	@RequestMapping(value="/listTwo")
	@ResponseBody
	public Object listTwo(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
  			List<PageData> varList = storeCommentService.listAll(pd);
			if(varList.size() > 0){
				map.put("data", varList);
			}else {
				map.put("data", "");
			}
			varList=null;
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
	 * 商家回复评论
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/updComment")
	@ResponseBody
	public Object updComment(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="回复成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("comment_store_status", "1");
			storeCommentService.updComment(pd);
		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data","");
		return map;
	}
	
	
	@Resource(name="appGoodsService")
	private AppGoodsService appGoodsService;
	
	/**
	 * 商家评论
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findById")
	@ResponseBody
	public Object findById(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd = storeCommentService.findById(pd);
			if(pd != null){
				//设置图片集合
				String[] imgstr=pd.getString("image_url").split(",");
				List<PageData> imgList =new ArrayList<PageData>();
				for (int i = 0; i < imgstr.length; i++) {
					 PageData imgpd=new PageData();
					 imgpd.put("image", imgstr[i]);
					 imgList.add(imgpd);
					 imgpd=null;
				}
				pd.put("imgList", imgList);
	 			//获取商品信息
				List<PageData> goodsList=appGoodsService.listAllGoodsByOrder(pd);
				for(PageData e1 : goodsList){
					String goods_type=e1.getString("goods_type");
					PageData goodspd=new PageData();
					if(goods_type.equals("1")){
						goodspd.put("goods_id", e1.getString("goods_id"));
						goodspd=appGoodsService.findById(goodspd);
						if(goodspd != null){
							e1.put("goods_name", goodspd.getString("goods_name"));
							e1.put("image_url", goodspd.getString("image_url"));
							e1.put("company", goodspd.getString("company"));
						}else{
	 						e1.put("goods_name", "商品不存在");
	 						e1.put("image_url", "");
 						e1.put("company", "");
 						 
 				     }
	 					goodspd=null;
					}else if(goods_type.equals("2")){
						goodspd.put("youxuangoods_id", e1.getString("goods_id"));
						goodspd=ServiceHelper.getYouXuanService().findByIdGoods(goodspd);
						if(goodspd != null){
							e1.put("goods_name", goodspd.getString("goods_name"));
							e1.put("image_url", goodspd.getString("image_url"));
							e1.put("company", goodspd.getString("goods_dw"));
						}else{
	 						e1.put("goods_name", "商品不存在");
	 						e1.put("image_url", "");
 						e1.put("company", "");
 						 
 				     }
	 					goodspd=null;
					}else if(goods_type.equals("3")){
						goodspd.put("youxuangoods_id", e1.getString("goods_id"));
						goodspd=ServiceHelper.getOneYuanService().findByIdWithMember(goodspd);
						if(goodspd != null){
							e1.put("goods_name", goodspd.getString("oneyuangoods_name"));
							e1.put("image_url", goodspd.getString("oneyuangoodsimage_url"));
							e1.put("company", "");
						}else{
	 						e1.put("goods_name", "商品不存在");
	 						e1.put("image_url", "");
	 						e1.put("company", "");
 						 
						}
	 					goodspd=null;
					}
				}
				int n=0;
				for(PageData e : goodsList){
					n+=Integer.parseInt(e.getString("shop_number"));
				}
				pd.put("goodsjianshu", n);
				pd.put("goodsList", goodsList);
	 			if(pd != null && !pd.equals("")){
					map.put("data", pd);
				}else {
					map.put("data", "");
				}
			}else{
				map.put("data", "");
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
	
}
