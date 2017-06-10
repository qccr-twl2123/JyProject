package com.tianer.service.business.pay_history;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("pay_historyService")
public class Pay_historyService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
  
//	
//	//------------------------------------------------------------------------------------------------------
//	/*
//	* 新增
//	*/
//	public void save(PageData pd)throws Exception{
//		dao.save("Pay_historyMapper.save", pd);
//	}
//	
//	/*
//	* 删除
//	*/
//	public void delete(PageData pd)throws Exception{
//		dao.delete("Pay_historyMapper.delete", pd);
//	}
//
//	
//	/*
//	*列表
//	*/
//	public List<PageData> datalistPage(Page page)throws Exception{
//		return (List<PageData>)dao.findForList("Pay_historyMapper.datalistPage", page);
//	}
//	/*
//	 * 合计总金额
//	 */
//	public String dataSumMoney(Page page)throws Exception{
//		return (String)dao.findForObject("Pay_historyMapper.dataSumMoney", page);
//	}
//	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Pay_historyMapper.listAll", pd);
	}
//	
//	/*
//	* 通过id获取数据
//	*/
//	public PageData findById(PageData pd)throws Exception{
//		return (PageData)dao.findForObject("Pay_historyMapper.findById", pd);
//	}
//	
//	
//	
//	/*
//	* 修改
//	*/
//	public void edit(PageData pd)throws Exception{
//		dao.update("Pay_historyMapper.edit", pd);
//	}
//	
//	/*
//	* 批量删除
//	*/
//	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
//		dao.delete("Pay_historyMapper.deleteAll", ArrayDATA_IDS);
//	}
//	/*
//	* 批量修改
//	* 刘耀耀
//	* 2016.6.6
//	*/
//	public void editAll(String[] ArrayDATA_IDS)throws Exception{
//		dao.delete("Pay_historyMapper.editAll", ArrayDATA_IDS);
//	}
//	
//	
//	/*
//	*获取所有消费统计
//	*魏汉文20160617
//	*/
//	public List<PageData> countAllMoney(Page page)throws Exception{
//		return (List<PageData>)dao.findForList("Pay_historyMapper.countAllMoney", page);
//	}
//	
//	
//	/*
//	*获取所有个人当日最高消费统计
//	*魏汉文20160617
//	*/
//	public List<PageData> countAllMoneyByOne(Page page)throws Exception{
//		return (List<PageData>)dao.findForList("Pay_historyMapper.countAllMoneyByOne", page);
//	}
//   	
//	/*
//	 *excel导出列表(全部)
//	 */
//	public List<PageData> ExcellistAllHistory(Page page)throws Exception{
//		return (List<PageData>)dao.findForList("Pay_historyMapper.ExcellistAllHistory", page);
//	}
//	
//	
}

