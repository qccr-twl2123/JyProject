package com.tianer.service.storeapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("storeapp_goodsService")
public class Storeapp_goodsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/*
	 * 添加商品
	 * 刘耀耀
	 * 2016.07.04
	 */
	public void save(PageData pd) throws Exception {
		dao.save("XJTStoreapp_goodsMapper.save", pd);
	}
	/*
	 * 修改商品
	 */
	public void editGoods(PageData pd) throws Exception {
		dao.save("XJTStoreapp_goodsMapper.editGoods", pd);
	}
	/*
	 * 商品列表
	 * 刘耀耀
	 * 2016.07.04
	 */
	public List<PageData> list(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("XJTStoreapp_goodsMapper.list", pd);
	}
	/*
	 * 商品删除
	 * 刘耀耀
	 * 2016.07.04
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("XJTStoreapp_goodsMapper.delete", pd);
	}
	
	/*
	 * 商品大类
	 * 刘耀耀
	 * 2016.07.12
	 */
	public List<PageData> listType(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("XJTStoreapp_goodsMapper.listType", pd);
	}
	/*
	 * 商品小类
	 * 刘耀耀
	 * 2016.07.12
	 */
	public List<PageData> listXiaoType(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("XJTStoreapp_goodsMapper.listXiaoType", pd);
	}
		/*
		 * 商品详情
		 */
		public PageData findByIdForWca(PageData pd) throws Exception {
			return (PageData) dao.findForObject("XJTStoreapp_goodsMapper.findByIdForWca", pd);
	}
}

