package com.tianer.service.memberPc;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/***
 * 购物车会员pc
 * @author 邢江涛
 *
 */
@Service("orService")
public class OrderPcService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	*评价列表分页
	*/
	public List<PageData> datalistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XJTOrderMapper.datalistPage", page);
	}

	/*
	* 删除我的订单
	*/
	public void delOrder(PageData pd)throws Exception{
		dao.update("XJTOrderMapper.delOrder", pd);
	}
	
	/*
	 * 订单详情
	 */
	public PageData findByOrderId(PageData pd)throws Exception{
		return (PageData) dao.findForObject("XJTOrderMapper.findByOrderId", pd);
	}
 	
	
	
}
