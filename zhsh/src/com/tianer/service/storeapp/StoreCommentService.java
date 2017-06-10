package com.tianer.service.storeapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/**
 * 
* 类名称：CommentService   
* 类描述：  商家评论
* 创建人：邢江涛 
* 创建时间：2016年7月4日 
 */
@Service("storeCommService")
public class StoreCommentService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	
	/**
	 * 商家回复
	 */
	public void updComment(PageData pd)throws Exception{
		dao.update("AppCommentMapper.updComment", pd);
	}
	
	/*
	 *  订单信息详情和评价详情
	 */
	public PageData findById(PageData pd) throws Exception {
		return  (PageData) dao.findForObject("AppCommentMapper.findById", pd);
	}
	
	
	/*
	 *  商家评论列表
	 */
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AppCommentMapper.listAll", pd);
	}
	
	
	/*
	 *  商家评论列表
	 */
	public List<PageData> datalistPage(Page  page) throws Exception {
		return (List<PageData>) dao.findForList("AppCommentMapper.datalistPage", page);
	}
	
}
