/**
 * 
 */
package com.tianer.service.storepc.liangqin.homepage;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;

/**
 * 类名称: FifteenMarketChartService 
 * 类描述: TODO
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-14 上午8:56:03	
 * 版本号: v1.0
 */
@Service("fifteenMarketChartService")
public class FifteenMarketChartService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	/**
	 * 查出15天的订单量
	 */
	public PageData findOrderQuantity(PageData pd) throws Exception{
		return (PageData)dao.findForObject("LQOrderMapper.findNDayQuantity", pd);
	}


}
