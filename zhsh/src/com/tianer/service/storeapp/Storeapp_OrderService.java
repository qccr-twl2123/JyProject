package com.tianer.service.storeapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/**
 * 
* 类名称：Storeapp_OrderService   
 */
@Service("storeapp_OrderService")
public class Storeapp_OrderService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
   	 
	
	/*
	* 查看订单详情-通过store_wealthhistory_id
	*/
	public PageData DetailOrderById(PageData pd)throws Exception{
  		return (PageData)dao.findForObject("Storeapp_orderMapper.DetailOrderById", pd);
	}
	
	
}
