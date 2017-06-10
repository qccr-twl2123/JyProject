package com.tianer.service.storeapp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;



/**
 * 
* 类名称：Statisticalapp_chartService   
* 类描述：   app统计图
* 创建人：邢江涛 
* 创建时间：2016年7月01日 
 */
@Service("statisticalapp_chartService")
public class Statisticalapp_chartService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 查出15天的订单金额统计
	 */
	public String findMoneyNum(PageData pd) throws Exception{
		return (String) dao.findForObject("XJTStatisticalapp_chartMapper.findMoneyNum", pd);
	}

	
	/**
	 * 查出15天的订单销售笔数
	 */
	public String findNum(PageData pd) throws Exception{
		return (String) dao.findForObject("XJTStatisticalapp_chartMapper.findNum", pd);
	}

}
