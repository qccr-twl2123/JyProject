package com.tianer.service.storepc.store_redpackets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;

/**
 * 商品类别
 * @author 邢江涛
 *
 */
@Service("goodsCategoryService")
public class Goods_categoryService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/*
	* 查询商品
	*/
	public List<PageData> listgoods(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("XJTGoods_Mapper.goodsList", pd);
	}
	
	
	/*
	* 查询类别
	*/
	public List<PageData> goodsCategoryList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("XJTGoods_Mapper.goodsCategoryList", pd);
	}

}
