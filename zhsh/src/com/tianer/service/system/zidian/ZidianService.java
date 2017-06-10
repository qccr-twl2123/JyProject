package com.tianer.service.system.zidian;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

@Service("zidianService")
public class ZidianService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	//新增
	public void save(PageData pd)throws Exception{
		dao.save("ZidianMapper.save", pd);
	}
	
	//修改
	public void edit(PageData pd)throws Exception{
		dao.update("ZidianMapper.edit", pd);
	}
	
	//通过id获取数据
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ZidianMapper.findById", pd);
	}
	
	//查询总数
	public PageData findCount(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ZidianMapper.findCount", pd);
	}
	
	//查询某编码
	public PageData findBmCount(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ZidianMapper.findBmCount", pd);
	}
	
	//列出同一父类id下的数据
	public List<PageData> zidianlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ZidianMapper.zidianlistPage", page);
		
	}
	
	//删除
	public void delete(PageData pd) throws Exception {
		dao.delete("ZidianMapper.delete", pd);
		
	}

	/**
	 * 
	 * <p>description: 根据父级名称获取列表信息</p>
	 * 
	 * @param      pd
	 *					父级名称
	 *
	 * @return     该父级下列表
	 *
	 * @author     旭东
	 *
	 * @date       2015-8-27 下午5:08:00
	 */
	public List<PageData> getListByParentName(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ZidianMapper.getListByParentName", pd);
	}
}
