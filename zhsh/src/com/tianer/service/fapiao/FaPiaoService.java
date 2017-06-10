package com.tianer.service.fapiao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/**
 * 发票申请记录服务层
 */
@Service("faPiaoService")
public class FaPiaoService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;


	/**
	 *  新增申请发票的信息记录=======================================================================
	 */
	public void savebillinfor(PageData pd)throws Exception{
		dao.save("FaPiaoMapper.savebillinfor", pd);
	}
	
	/**
	 * 修改发票申请的信息
	 */
	public void updatebillinfor(PageData pd)throws Exception{
		dao.update("FaPiaoMapper.updatebillinfor", pd);
	}
	
	/**
	 * 发票信息的详情
	 */
	public PageData findByIdbillinfor(PageData pd) throws Exception {
		return (PageData) dao.findForObject("FaPiaoMapper.findByIdbillinfor", pd);
	}
	
	
	
	
	

	/**
	 *  新增发票记录==================================================================================
	 */
	public void savebill(PageData pd)throws Exception{
		dao.save("FaPiaoMapper.savebill", pd);
	}
	
	/**
	 *  新增发票关联的订单记录
	 */
	public void savebillandid(PageData pd)throws Exception{
		dao.save("FaPiaoMapper.savebillandid", pd);
	}
	
	/**
	 * 修改发票信息
	 */
	public void updatebill(PageData pd)throws Exception{
		dao.update("FaPiaoMapper.updatebill", pd);
	}
	

	
	/**
	 * 发票记录列表(分页)
	 */
	public List<PageData> billlistPage(Page page)throws Exception{
  		return (List<PageData>)dao.findForList("FaPiaoMapper.billlistPage", page);
	}
	
	
	/**
	 * 发票记录列表（全部）
	 */
	public List<PageData> billlistAll(PageData pd)throws Exception{
  		return (List<PageData>)dao.findForList("FaPiaoMapper.billlistAll", pd);
	}
	
	
	/**
	 * 可开发票的订单记录（全部）
	 */
	public List<PageData> listOrderByBill(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("FaPiaoMapper.listOrderByBill", pd);
	}
	
	/**
	 * 发票的详情
	 */
	public PageData findByIdbill(PageData pd) throws Exception {
		return (PageData) dao.findForObject("FaPiaoMapper.findByIdbill", pd);
	}
	
	/**
	 * 发票关联订单的列表
	 */
	public List<PageData> billlistandid(PageData pd)throws Exception{
  		return (List<PageData>)dao.findForList("FaPiaoMapper.billlistandid", pd);
	}
	 
	/**
	 *修改订单开取发票状态
	 */
	public void updatebillStatusByWater(PageData pd)throws Exception{
		dao.update("FaPiaoMapper.updatebillStatusByWater", pd);
	}
	
	

	 
	 
}
