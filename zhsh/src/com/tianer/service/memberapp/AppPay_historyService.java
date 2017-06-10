//package com.tianer.service.app;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.tianer.dao.DaoSupport;
//import com.tianer.entity.Page;
//import com.tianer.util.PageData;
//
//
//@Service("appPay_historyService")
//public class AppPay_historyService {
//
//	@Resource(name = "daoSupport")
//	private DaoSupport dao;
//	
//	/*
//	* 删除
//	*/
//	public void deleteStatusZero(PageData pd)throws Exception{
//		dao.delete("AppPay_historyMapper.deleteStatusZero", pd);
//	}
//	
//	
//	/*
//	* 新增支付记录魏汉文20160705
//	*/
//	public void savePayhistory(PageData pd)throws Exception{
//		dao.save("AppPay_historyMapper.savePayhistory", pd);
//	}
//	
//	/*
//	* 通过id获取数据魏汉文20160705
//	*/
//	public PageData findById(PageData pd)throws Exception{
//		return (PageData)dao.findForObject("AppPay_historyMapper.findById", pd);
//	}
//	
//	/*
//	* 修改魏汉文20160705
//	*/
//	public void editPayhistory(PageData pd)throws Exception{
//		dao.update("AppPay_historyMapper.editPayhistory", pd);
//	}
//	
//}
//
