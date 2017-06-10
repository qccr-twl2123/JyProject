package com.tianer.service.storeapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


/**
 * 
* 类名称：storeapp_redpacketsService   
* 类描述：   app发红包
* 创建人：邢江涛 
* 创建时间：2016年6月28日 
 */
@Service("storeapp_redpacketsService")
public class Storeapp_redpacketsService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/*
	 *显示红包的使用条件
	 */
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTSrp_userconditionMapper.listAll", pd);
	}

	/*
	 *显示用户范围
	 */
	public List<PageData> listuser(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTSrp_userconditionMapper.listuser", pd);
	}
	
	/*
	 *显示发放范围
	 */
	public List<PageData> listrange(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTSrp_userconditionMapper.listRange", pd);
	}
	
	/*
	 *红包分页列表
	 */
	public List<PageData> listPageRedpackets(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XJTSrp_userconditionMapper.listPageRedpackets", page);
	}
	/*
	 *红包列表
	 */
	public List<PageData> listRedpackets(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTSrp_userconditionMapper.listRedpackets", pd);
	}
	
	/*
	 * 红包过期数量
	 */
	public String guoqiCount(String id)throws Exception{
		return (String) dao.findForObject("XJTSrp_userconditionMapper.guoqiCount", id);
	}
	
	
	/*
	 * 红包使用数量
	 */
	public String shiyongCount(String id)throws Exception{
		return (String) dao.findForObject("XJTSrp_userconditionMapper.shiyongCount", id);
	}
	
	/*
	 * 发送红包
	 */
	public void save(PageData pd)throws Exception{
		dao.save("XJTSrp_userconditionMapper.save", pd);
	}

	/*
	 * 一度人脉（还会员是否是该商家的一度人脉）
	 */
	public PageData renamiYi(PageData pd)throws Exception{
		return (PageData) dao.findForObject("XJTStore_redpacketsMapper.renmaiYi", pd);
	}
	
	/*
	 * 二度人脉（还会员是否是该商家的二度人脉）
	 */
	public PageData renamiEr(PageData pd)throws Exception{
		return (PageData) dao.findForObject("XJTStore_redpacketsMapper.renamiEr", pd);
	}
	

	/*
	 * 是否是收藏本店的会员
	 */
	public PageData shoucang(PageData pd)throws Exception{
		return (PageData) dao.findForObject("XJTStore_redpacketsMapper.shoucang", pd);
	}
	
	/*
	 * 消费过的会员
	 */
	public List<PageData> consumptionMember(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTSrp_userconditionMapper.consumptionMember", pd);
	}
	
	/*
	 * 本店会员
	 */
	public PageData member(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTSrp_userconditionMapper.member", pd);
	}
	

	/*
	 * 所在城市
	 */
	public PageData ciyt(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTSrp_userconditionMapper.city", pd);
	}
	
	/*
	 * 所在城市
	 */
	public PageData area(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTSrp_userconditionMapper.area", pd);
	}
	
	/*
	 * 我的盟友
	 */
	public PageData myAlly(PageData pd)throws Exception{
		return (PageData) dao.findForObject("XJTStore_redpacketsMapper.myAlly", pd);
	}
	
	/*
	 * 获取该商户的积分提醒数据
	 */
	public PageData remindIntegral(PageData pd)throws Exception{
		return (PageData) dao.findForObject("XJTStore_redpacketsMapper.remindIntegral", pd);
	}
}
