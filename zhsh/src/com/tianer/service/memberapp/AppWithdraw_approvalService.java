//package com.tianer.service.app;
//
// 
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.tianer.dao.DaoSupport;
//import com.tianer.util.PageData;
//
//
//@Service("appWithdraw_approvalService")
//public class AppWithdraw_approvalService {
//
//	@Resource(name = "daoSupport")
//	private DaoSupport dao;
//	
//	/*
//	* 新增提现记录魏汉文20160704
//	*/
//	public void saveWithdraw(PageData pd)throws Exception{
//		dao.save("AppWithdraw_approvalMapper.saveWithdraw", pd);
//	}
//	
//	/*
//	*提现列表(全部)魏汉文20160706
//	*/
//	public List<PageData> listAll(PageData pd)throws Exception{
//		return (List<PageData>)dao.findForList("AppWithdraw_approvalMapper.listAll", pd);
//	}
//	 
//}
//
