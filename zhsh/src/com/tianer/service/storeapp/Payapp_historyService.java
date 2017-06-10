package com.tianer.service.storeapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


/** 
 * 
* 类名称：Payapp_historyService   
* 类描述：   收银记录app
* 创建人：邢江涛
* 创建时间：2016年7月4日 
 */
@Service("payapp_historyService")
public class Payapp_historyService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	

	
	/**
	 * 添加数据进订单表(按总金额)
	 */
	public void saveOrder(PageData pd)throws Exception{
		dao.save("LQOrderMapper.add", pd);
	}
	
	/**
	 * 添加数据进支付表
	 */
	public void save(PageData pd)throws Exception{
		dao.save("LQPay_HistoryMapper.save", pd);
	}
	

	
	/*
	 * 收银记录魏汉文20160706
	 */
	public List<PageData> confirmedHistorylistPage(Page page) throws Exception{
		return (List<PageData>)dao.findForList("XJTPayapp_historyMapper.confirmedHistorylistPage", page);
	}
	
	
	/*
	 * 订单的历史收银详情 魏汉文20160706
	 */
	public PageData confirmedFindById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTPayapp_historyMapper.confirmedFindById", pd);
	}
	
	/*
	 * 会员按类别购买的类别集合魏汉文20160706
	 */
	public List<PageData> orderSortList(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("XJTPayapp_historyMapper.orderSortList", pd);
	}
	
	
	/*
	 * 更新订单状态魏汉文20160706
	 */
	public void editOrderStatus(PageData pd)throws Exception{
		dao.save("XJTPayapp_historyMapper.editOrderStatus", pd);
	}
	
	/**
	 * 订单删除
	 */
	public void deleteOrderw(PageData pd) throws Exception {
		dao.delete("XJTPayapp_historyMapper.deleteOrderw", pd);
	}
	
	/*
	 * 获取该订单的订单状态
	 */
	public String orderStatus(String id)throws Exception{
		return (String)dao.findForObject("XJTPayapp_historyMapper.orderStatus", id);
	}
	
	/*
	 * 验证提货券
	 */
	public PageData tihuoByOrderId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTPayapp_historyMapper.tihuoByOrderId", pd);
	}
	
	
	
	/*
	 * 会员按类别购买的类别集合魏汉文20160706
	 */
	public List<PageData> listAllGoodsByOrder(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("XJTPayapp_historyMapper.listAllGoodsByOrder", pd);
	}

}
