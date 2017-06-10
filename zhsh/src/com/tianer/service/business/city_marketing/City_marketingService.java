package com.tianer.service.business.city_marketing;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("city_marketingService")
public class City_marketingService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("City_marketingMapper.save", pd);
  	}
	
	/*
	 * 新增
	 */
	public void saveYinxiao(PageData pd)throws Exception{
 		dao.save("City_marketingMapper.saveYinxiao", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("City_marketingMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("City_marketingMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("City_marketingMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("City_marketingMapper.listAll", pd);
	}
	

	
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("City_marketingMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("City_marketingMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	 *城市营销详情的城市营销超出商品限度的费用表
	 */
	public List<PageData> getCitySevenList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("City_marketingMapper.getCitySevenList", pd);
	}
	
	
	
	/*
	* 通过省市区获取详情20160801
	*/
	public PageData getCityMarketingForPCD(PageData pd)throws Exception{
		return (PageData)dao.findForObject("City_marketingMapper.getCityMarketingForPCD", pd);
	}
	
}

