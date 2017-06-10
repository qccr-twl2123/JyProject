package com.tianer.service.business.withdraw_approval;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("withdraw_approvalService")
public class Withdraw_approvalService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
//	
//	/*
//	* 新增
//	*/
//	public void save(PageData pd)throws Exception{
//		dao.save("Withdraw_approvalMapper.save", pd);
//	}
//	
//	/*
//	* 删除
//	*/
//	public void delete(PageData pd)throws Exception{
//		dao.delete("Withdraw_approvalMapper.delete", pd);
//	}
//	
//	/*
//	* 修改
//	*/
//	public void edit(PageData pd)throws Exception{
//		dao.update("Withdraw_approvalMapper.edit", pd);
// 	}
//	
//	/*
//	*列表
//	*/
//	public List<PageData> datalistPage(Page page)throws Exception{
//		return (List<PageData>)dao.findForList("Withdraw_approvalMapper.datalistPage", page);
//	}
//	
//	/*
//	 *统计总金额
//	 */
//	public String sunMoney(Page page)throws Exception{
//		return (String)dao.findForObject("Withdraw_approvalMapper.sunMoney", page);
//	}
//	
//	/*
//	 *统计应到账总金额
//	 */
//	public String sunMoneyTwo(Page page)throws Exception{
//		return (String)dao.findForObject("Withdraw_approvalMapper.sunMoneyTwo", page);
//	}
//	
//	/*
//	*列表(全部)
//	*/
//	public List<PageData> listAll(PageData pd)throws Exception{
//		return (List<PageData>)dao.findForList("Withdraw_approvalMapper.listAll", pd);
//	}
	/*
	 *列表(全部)
	 */
	public List<PageData> listAllZiDian(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Withdraw_approvalMapper.listAllZiDian", pd);
	}
//	
//	/*
//	 *列表    导出excle
//	 */
//	public List<PageData> listAllForExcel(PageData pd)throws Exception{
//		return (List<PageData>)dao.findForList("Withdraw_approvalMapper.listAllForExcel", pd);
//	}
//	
//	/*
//	* 通过id获取数据
//	*/
//	public PageData findById(PageData pd)throws Exception{
//		return (PageData)dao.findForObject("Withdraw_approvalMapper.findById", pd);
//	}
//	
//	/*
//	* 批量删除
//	*/
//	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
//		dao.delete("Withdraw_approvalMapper.deleteAll", ArrayDATA_IDS);
//	}
////	/*
////	* 批量通过
////	* 刘耀耀
////	* 2016.6.6
////	*/
////	public void editAll(String[] ArrayDATA_IDS)throws Exception{
////		dao.update("Withdraw_approvalMapper.editAll", ArrayDATA_IDS);
////	}
//	
//	/*
//	* 批量修改状态
//	* xjt
//	* 2016.9.24
//	*/
//	public void updateAll(PageData pd)throws Exception{
//		dao.update("Withdraw_approvalMapper.updateAll", pd);
//	}
//	
//	/*
//	 *修改状态以及操作员元
//	 */
//	public void updateForId(PageData pd)throws Exception{
//		dao.update("Withdraw_approvalMapper.updateForId", pd);
//	}
//	
//	/*
//	*获取所有提现统计
//	*魏汉文20160617
//	*/
//	public List<PageData> countAllMoney(Page page)throws Exception{
//		return (List<PageData>)dao.findForList("Withdraw_approvalMapper.countAllMoney", page);
//	}
//	
//	
//	/*
//	*获取所有个人当日最高提现统计
//	*魏汉文20160617
//	*/
//	public List<PageData> countAllMoneyByOne(Page page)throws Exception{
//		return (List<PageData>)dao.findForList("Withdraw_approvalMapper.countAllMoneyByOne", page);
//	}
//	
//	/*
//	* 新增提现记录魏汉文20160704
//	*/
//	public void saveWithdraw(PageData pd)throws Exception{
//		dao.save("Withdraw_approvalMapper.saveWithdraw", pd);
//	}
//	
//
//	
}

