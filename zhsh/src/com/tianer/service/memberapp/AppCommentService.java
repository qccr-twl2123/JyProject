package com.tianer.service.memberapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("appCommentService")
public class AppCommentService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	*评价列表魏汉文20160630
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppCommentMapper.listAll", pd);
	}
	
	/*
	*评价列表分页
	*/
	public List<PageData> datalistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AppCommentMapper.datalistPage", page);
	}
	
	/*
	 *评价星级以及单数魏汉文20160630
	 */
	public PageData countStart(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppCommentMapper.countStart", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AppCommentMapper.list", page);
	}
	
	

	/*
    *新增评论 魏汉文20160630
	*/
	public void save(PageData pd) throws Exception {
		dao.update("AppCommentMapper.save", pd);//修改订单的评价状态
		dao.update("AppOrderMapper.updateOrderJud", pd);//修改订单的评价状态
		
	}
	
	
	/*
	 *评价详情
	 */
	public PageData findByIdForSave(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppCommentMapper.findByIdForSave", pd);
	}
	
	
}

