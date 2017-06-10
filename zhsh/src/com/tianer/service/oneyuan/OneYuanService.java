package com.tianer.service.oneyuan;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


/**
 * 一元购商品的集合
 */
@Service("oneYuanService")
public class OneYuanService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	

	/**
	 * 新增一元商品
	 */
	public void saveGoods(PageData pd)throws Exception{
		dao.save("OneYuanMapper.saveGoods", pd);
	}

	
	/**
	 * 修改一元商品
	 */
	public void editGoods(PageData pd)throws Exception{
		dao.update("OneYuanMapper.editGoods", pd);
	}
	
	/**
	 * 一元商品的列表
	 */
 	public List<PageData> datalistPageGoods(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OneYuanMapper.datalistPageGoods", page);
	}
	
	
	/**
	 * 所有一元商品的列表
	 */
 	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OneYuanMapper.listAll", pd);
  	}
	
 	/**
	 * 查询一元购商品的详情包括
	 */
	public  PageData  findByIdWithMember(PageData pd) throws Exception{
		return ( PageData ) dao.findForObject("OneYuanMapper.findByIdWithMember", pd);
	}

	
	/**
	 * 所有一元商品的所有购买会员的列表
	 */
	public List<PageData> findAllMemberBuyThisGoods(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OneYuanMapper.findAllMemberBuyThisGoods", pd);
	}
	

	/**
	 * 新增购买一元商品的记录
	 */
	public void saveMemberBuyGoods(PageData pd)throws Exception{
		dao.save("OneYuanMapper.saveMemberBuyGoods", pd);
	}
	
	/**
	 * 修改购买一元商品的记录
	 */
	public void editMemberGoods(PageData pd)throws Exception{
		dao.save("OneYuanMapper.editMemberGoods", pd);
	}
	
	/**
	 * 获取购买当前商品的记录信息
	 */
	public  PageData  findMemberOrderById(PageData pd) throws Exception{
		return ( PageData ) dao.findForObject("OneYuanMapper.findMemberOrderById", pd);
	}
 	
	
	/**
	 * 删除状态未0的订单
	 */
	public void deleteoneyuanmember(PageData pd)throws Exception{
		dao.delete("OneYuanMapper.deleteoneyuanmember", pd);
	}
	
	/**
	 * 新增一元商品的订单
	 */
	public void saveOneYuanOrder(PageData pd)throws Exception{
		dao.save("OneYuanMapper.saveOneYuanOrder", pd);
	}
	
	
	

}
