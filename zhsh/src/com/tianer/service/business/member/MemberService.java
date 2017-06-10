package com.tianer.service.business.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("memberService")
public class MemberService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("MemberMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("MemberMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("MemberMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("MemberMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MemberMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MemberMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("MemberMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	* 通过id获取数据魏汉文20160608（人脉查找）
	*/
	public PageData detailById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MemberMapper.detailById", pd);
	}
	
	/*
	 *通过手机号码获取会员信息
	 *魏汉文20160608
	 */
	public List<PageData> listAllByPhone(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MemberMapper.listAllByPhone", pd);
	}
	
	
	/*
	* 通过id获取商家详情数据
	*/
	public PageData storeFindById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MemberMapper.storeFindById", pd);
	}
	
	/*
	 * 通过id获取营销类型
	 */
	public List<PageData> marketingById(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MemberMapper.marketingById", pd);
	}
	
	
}

