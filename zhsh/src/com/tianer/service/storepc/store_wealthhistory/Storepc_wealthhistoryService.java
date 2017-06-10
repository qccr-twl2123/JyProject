package com.tianer.service.storepc.store_wealthhistory;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/**
 * 充值记录（商家财富记录表）
 * @author 邢江涛
 *
 */
@Service("storepc_wealthhistoryService")
public class Storepc_wealthhistoryService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;


	/*
	 * 充值
	 */
	public void save(PageData pd)throws Exception{
		dao.save("XJTStoreWealthhistoryMapper.save", pd);
	}


	/*
	 *充值提现记录
	 */
	public List<PageData> listPageinfo(Page page)throws Exception{
				//清空充值失败的订单
				dao.delete("XJTStoreWealthhistoryMapper.deleteZeroStoreWealthCZ", page);
		return (List<PageData>)dao.findForList("XJTStoreWealthhistoryMapper.listPageinfo", page);
	}
	

	/*
	 *列表
	 */
	public List<PageData> listAll(PageData pd)throws Exception{
  		return (List<PageData>)dao.findForList("XJTStoreWealthhistoryMapper.listAll", pd);
	}
	
	
	/**
	 *测试用的
	 */
	public Integer findByIdCS(PageData pd)throws Exception{
		return (Integer)dao.findForObject("XJTStoreWealthhistoryMapper.findByIdCS", pd);
	}
	/**
	 *删除当前数据
	 */
	public void deleteThis(PageData pd)throws Exception{
		dao.save("XJTStoreWealthhistoryMapper.deleteThis", pd);
	}
	
	/**
	 *清空充值失败的订单
	 */
	public void deleteZeroStoreWealthCZ(PageData pd)throws Exception{
 		dao.delete("XJTStoreWealthhistoryMapper.deleteZeroStoreWealthCZ", null);
	}
	
	/**
	 *清空失败的订单
	 */
	public void deleteZeroStoreWealthAll(PageData pd)throws Exception{
 		dao.delete("XJTStoreWealthhistoryMapper.deleteZeroStoreWealthAll", null);
	}
	
	/*
	*列表
	*/
	public List<PageData> datalistPage(Page page)throws Exception{
		//删除无效订单
		dao.delete("XJTStoreWealthhistoryMapper.deleteZeroStoreWealth", null);
 		return (List<PageData>)dao.findForList("XJTStoreWealthhistoryMapper.datalistPage", page);
	}
	
	/*
	*列表
	*/
	public List<PageData> listThq(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XJTStoreWealthhistoryMapper.listThqlistPage", page);
	}
	/*
	*获取所有的财富流水
	*/
	public List<PageData> weallistPage(Page page)throws Exception{
		//清空充值失败的订单
		dao.delete("XJTStoreWealthhistoryMapper.deleteZeroStoreWealthCZ", page);
		return (List<PageData>)dao.findForList("XJTStoreWealthhistoryMapper.weallistPage", page);
	}
	
	/*
	 *流水记录详情
	 */
	public PageData liuShuiDetailById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTStoreWealthhistoryMapper.liuShuiDetailById", pd);
	}
	
	/*
	 *统计当前页的总和
	 */
	public PageData sumNowPageWeath(Page page)throws Exception{
		return (PageData)dao.findForObject("XJTStoreWealthhistoryMapper.sumNowPageWeath", page);
	}
	/*
	 *统计所有的消费金额
	 */
	public PageData sumAllPageWeath(Page page)throws Exception{
		return (PageData)dao.findForObject("XJTStoreWealthhistoryMapper.sumAllPageWeath", page);
	}

 	
	/*
	 *获取制定操作员的班次汇总
	 */
	public PageData BanCiHuiZongByOprator(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTStoreWealthhistoryMapper.BanCiHuiZongByOprator", pd);
	}
 	/*
	 * 获取当前操作员的完成订单单数
	 */
	public String countOrderNumberByOprator(PageData  pd) throws Exception {
		return (String) dao.findForObject("XJTStoreWealthhistoryMapper.countOrderNumberByOprator", pd);
	}
	
//	/*
//	 * 总和
//	 */
//	public String summoney(Page  page) throws Exception {
//		return (String) dao.findForObject("XJTStoreWealthhistoryMapper.summoney", page);
//	}
//	
//	/*
//	 * 第三方总和
//	 */
//	public String sumDiSan(PageData pd) throws Exception {
//		return (String) dao.findForObject("XJTStoreWealthhistoryMapper.sumDiSan", pd);
//	}
	
	/**
	 * 编辑备注
	 */
	public void updRemark(PageData pd)throws Exception{
		dao.save("XJTStoreWealthhistoryMapper.updRemark", pd);
 	}
 
	
	
	/*
	 *获取今天商家财务统计的数据
	 *魏汉文 
	 */
	public String getSumMoney(PageData pd) throws Exception {
		return (String) dao.findForObject("XJTStoreWealthhistoryMapper.getSumMoney", pd);
	}
	
	
	/*
	 *获取今天商家营业额
	 *魏汉文 
	 */
	public PageData getSumOrder(PageData pd) throws Exception {
		return (PageData) dao.findForObject("XJTStoreWealthhistoryMapper.getSumOrder", pd);
	}
	
	
	/*
	 * 获取一度人脉/二度人脉的收益
	 *魏汉文 
	 */
	public PageData getContantSumMoney(PageData pd) throws Exception {
		return (PageData) dao.findForObject("XJTStoreWealthhistoryMapper.getContantSumMoney", pd);
	}
	
	/*
	 * 获取一度人脉/二度人脉的收益（用于凌晨统计）
	 *魏汉文 
	 */
	public PageData getContantSumMoneyTwo(PageData pd) throws Exception {
		return (PageData) dao.findForObject("XJTStoreWealthhistoryMapper.getContantSumMoneyTwo", pd);
	}
	/*
	 *获取一度人脉/二度人脉的订单
	 */
	public List<PageData> listallContantOrder(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTStoreWealthhistoryMapper.listallContantOrder", pd);
	}
 	/*
	 *获取今天商家交易订单数
	 *魏汉文 
	 */
	public int getCountOrder(PageData pd) throws Exception {
		return (int) dao.findForObject("XJTStoreWealthhistoryMapper.getCountOrder", pd);
	}
	
	/*
	 *获取今天商家交易订单数
	 *魏汉文 
	 */
	public int getallCountOrder(PageData pd) throws Exception {
		return (int) dao.findForObject("XJTStoreWealthhistoryMapper.getallCountOrder", pd);
	}
	
	/*
	 *获取登录密码
	 */
	public String getPass(PageData pd) throws Exception {
		return (String) dao.findForObject("XJTStoreWealthhistoryMapper.getPass", pd);
	}
	
	/*
	 *获取所有的的提货券
	 */
	public List<PageData> orderlistPage(Page page)throws Exception{
  		return (List<PageData>)dao.findForList("XJTStoreWealthhistoryMapper.orderlistPage", page);
	}

}
