package com.tianer.service.memberapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("appGoodsService")
public class AppGoodsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 *所有类别列表魏汉文20160630
	 */
	public List<PageData> listAllSort(PageData  pd)throws Exception{
		return (List<PageData>)dao.findForList("AppGoodsMapper.listAllSort", pd);
	}
	
	/*
	 *所有类别列表魏汉文20160630
	 */
	public List<PageData> listAllBigSort(PageData  pd)throws Exception{
		return (List<PageData>)dao.findForList("AppGoodsMapper.listAllBigSort", pd);
	}
	
	/*
	 *所有指定商品类别列表魏汉文20160630
	 */
	public List<PageData> listAllGoodsBySortId(PageData  pd)throws Exception{
		return (List<PageData>)dao.findForList("AppGoodsMapper.listAllGoodsBySortId", pd);
	}

	
	/*
	 *今日特价列表魏汉文20160630
	 */
	public List<PageData> getYingXiaoGoods(PageData  pd)throws Exception{
		return (List<PageData>)dao.findForList("AppGoodsMapper.getYingXiaoGoods", pd);
	}
	
	
	/*
	 *人气版10条列表魏汉文20160630
	 */
	public List<PageData> getRenQiGoods(PageData  pd)throws Exception{
		return (List<PageData>)dao.findForList("AppGoodsMapper.getRenQiGoods", pd);
	}
	
	
	/*
	 *获取订单的所有商品魏汉文20160630
	 */
	public List<PageData> listAllGoodsByOrder(PageData  pd)throws Exception{
		return (List<PageData>)dao.findForList("AppGoodsMapper.listAllGoodsByOrder", pd);
	}

	/*
	 *会员按类别购买的类别集合魏汉文20160706
	 */
	public PageData goodsSortById(PageData  pd)throws Exception{
		return (PageData)dao.findForObject("AppGoodsMapper.goodsSortById", pd);
	}
	
	
	/*
	 *查看详情
	 */
	public  PageData  findById(PageData  pd)throws Exception{
		return ( PageData )dao.findForObject("AppGoodsMapper.findById", pd);
	}
	
	
	
	/*
	 *购物车到期更新购买数量 
	 */
	public  PageData  updateGoodsBuyNumber(PageData  pd)throws Exception{
		return ( PageData )dao.findForObject("AppGoodsMapper.updateGoodsBuyNumber", pd);
	}
	
	/*
	 *获取已购买的商品的ID
	 */
	public List<PageData> getGoodsIdByOrder(PageData  pd)throws Exception{
		return (List<PageData>)dao.findForList("AppGoodsMapper.getGoodsIdByOrder", pd);
	}
}

