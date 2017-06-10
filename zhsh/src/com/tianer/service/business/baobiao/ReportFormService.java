package com.tianer.service.business.baobiao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

@Service("reportFormService")
public class ReportFormService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	*服务商保证金列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ReportFormMapper.datalistPage", page);
	}
	
	/*
	 *服务商保证金--统计当前页
	 */
	public PageData nowdatalistPage(Page page)throws Exception{
		return (PageData)dao.findForObject("ReportFormMapper.nowdatalistPage", page);
	}

	/*
	 *服务商保证金---统计所有的
	 */
	public PageData alldataSumMoney(Page page)throws Exception{
		return (PageData)dao.findForObject("ReportFormMapper.alldataSumMoney", page);
	}
	
	/*
	*商家保证金列表
	*/
	public List<PageData> listService(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ReportFormMapper.servicelistPage", page);
	}
	/*
	 *当前页的商家保证金统计
	 */
	public PageData nowsumserviceMoney(Page page)throws Exception{
		return (PageData)dao.findForObject("ReportFormMapper.nowsumserviceMoney", page);
	}
	/*
	 *所有的商家保证金统计
	 */
	public PageData allserviceSumMoney(Page page)throws Exception{
		return (PageData)dao.findForObject("ReportFormMapper.allserviceSumMoney", page);
	}

	
	/*
	*列表平台积分信息--分页
	*/
	public List<PageData> listIntegra(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ReportFormMapper.integralistPage", page);
	}
	/*
	 *统计当前页的平台总金额
	 */
	public PageData nowintegraSumMoney(Page page)throws Exception{
		return (PageData)dao.findForObject("ReportFormMapper.nowintegraSumMoney", page);
	}
	/*
	 *统计平台总金额
	 */
	public PageData allintegraSumMoney(Page page)throws Exception{
		return (PageData)dao.findForObject("ReportFormMapper.allintegraSumMoney", page);
	}

/*	
	 *统计总金额
	 
	public PageData sunMony(Page page)throws Exception{
		return (PageData)dao.findForObject("ReportFormMapper.sunMoney", page);
	}*/
	
	/*
	 *商家人脉收益的列表
	 */
	public List<PageData> datalistPageStoreRenMai(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ReportFormMapper.datalistPageStoreRenMai", page);
	}
	
	/*
	 *商家人脉收益的列表
	 */
	public List<PageData> allRenMaiJf_orderById(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ReportFormMapper.allRenMaiJf_orderById", pd);
	}
	
	/*
	 *商家经营分析报表
	 */
	public List<PageData> datalistPageStoreJJFX(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ReportFormMapper.datalistPageStoreJJFX", page);
	}
	
	/*
	 *商家时间段的线下销售收入金额
	 */
	public String timeByStartEndForXxSaleMoney(PageData pd)throws Exception{
		return (String)dao.findForObject("ReportFormMapper.timeByStartEndForXxSaleMoney", pd);
	}
	/*
	 *商家时间段的线上销售收入金额
	 */
	public String timeByStartEndForXSSaleMoney(PageData pd)throws Exception{
		return (String)dao.findForObject("ReportFormMapper.timeByStartEndForXSSaleMoney", pd);
	}
	/*
	 *商家时间段的查询收入的平均日积分
	 */
	public String timeByStartEndForIntegerSy(PageData pd)throws Exception{
		return (String)dao.findForObject("ReportFormMapper.timeByStartEndForIntegerSy", pd);
	}
	/*
	 *商家时间段的查询支出的平均日积分 
	 */
	public String timeByStartEndForIntegerZc(PageData pd)throws Exception{
		return (String)dao.findForObject("ReportFormMapper.timeByStartEndForIntegerZc", pd);
	}
	/*
	 *商家时间段的查询评论数量
	 */
	public String timeByStartEndForCommentNumber(PageData pd)throws Exception{
		return (String)dao.findForObject("ReportFormMapper.timeByStartEndForCommentNumber", pd);
	}
	/*
	 *商家时间段新增一度人脉数
	 */
	public String timeByStartEndForContactNumber(PageData pd)throws Exception{
		return (String)dao.findForObject("ReportFormMapper.timeByStartEndForContactNumber", pd);
	}
	/*
	 *商家时间段的查询充值金额
	 */
	public String timeByStartEndForCzMoney(PageData pd)throws Exception{
		return (String)dao.findForObject("ReportFormMapper.timeByStartEndForCzMoney", pd);
	}
	/*
	 *商家时间段的查询审核通过的提现金额
	 */
	public String timeByStartEndForTxOkMoney(PageData pd)throws Exception{
		return (String)dao.findForObject("ReportFormMapper.timeByStartEndForTxOkMoney", pd);
	}
	/*
	 *商家时间段的查询等待审核的提现金额
	 */
	public String timeByStartEndForReadyTxMoney(PageData pd)throws Exception{
		return (String)dao.findForObject("ReportFormMapper.timeByStartEndForReadyTxMoney", pd);
	}
	 
}
