//package com.tianer.service.storepc.withdrawalsService;
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
///**
// * 提现
// * @author 邢江涛
// *
// */
//@Service("withdrawalsService")
//public class WithdrawalsService {
//	
//
//	@Resource(name = "daoSupport")
//	private DaoSupport dao;
//	
//	/**
//	 * 查询全部
//	 * @param pd
//	 * @return
//	 * @throws Exception
//	 */
//	public List<PageData> list(PageData pd) throws Exception{
//		return (List<PageData>) dao.findForList("XJTWithdrawalsMapper.list", pd);
//	}
//	
//	/**
//	 * 已提现总额orderPriceCount
//	 * @param pd
//	 * @return
//	 * @throws Exception
//	 */
//	public String tixianPriCount(PageData pd) throws Exception{
//		return (String) dao.findForObject("XJTWithdrawalsMapper.tixianPriCount", pd);
//	}
//
//	/**
//	 * 已支付订单总额
//	 * @param pd
//	 * @return
//	 * @throws Exception
//	 */
//	public String orderPriceCount(PageData pd) throws Exception{
//		return (String) dao.findForObject("XJTWithdrawalsMapper.orderPriceCount", pd);
//	}
//}
