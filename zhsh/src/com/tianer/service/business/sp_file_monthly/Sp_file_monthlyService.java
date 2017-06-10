//package com.tianer.service.business.sp_file_monthly;
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
//@Service("sp_file_monthlyService")
//public class Sp_file_monthlyService {
//
//	@Resource(name = "daoSupport")
//	private DaoSupport dao;
//	
//	/*
//	* 新增
//	*/
//	public void save(PageData pd)throws Exception{
//		dao.save("Sp_file_monthlyMapper.save", pd);
//	}
//	
//	/*
//	* 删除
//	*/
//	public void delete(PageData pd)throws Exception{
//		dao.delete("Sp_file_monthlyMapper.delete", pd);
//	}
//	
//	/*
//	* 修改
//	*/
//	public void edit(PageData pd)throws Exception{
//		dao.update("Sp_file_monthlyMapper.edit", pd);
//	}
//	
//	/*
//	*列表
//	*/
//	public List<PageData> list(Page page)throws Exception{
//		return (List<PageData>)dao.findForList("Sp_file_monthlyMapper.datalistPage", page);
//	}
//	
//	/*
//	*列表(全部)
//	*/
//	public List<PageData> listAll(PageData pd)throws Exception{
//		return (List<PageData>)dao.findForList("Sp_file_monthlyMapper.listAll", pd);
//	}
//	
//	/*
//	* 通过id获取数据
//	*/
//	public PageData findById(PageData pd)throws Exception{
//		return (PageData)dao.findForObject("Sp_file_monthlyMapper.findById", pd);
//	}
//	
//	/*
//	* 批量删除
//	*/
//	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
//		dao.delete("Sp_file_monthlyMapper.deleteAll", ArrayDATA_IDS);
//	}
//	
//	/*
//	*获取服务商最近一个季度的信息
//	*魏汉文20160608
//	*
//	*/
//	public List<PageData> getMaxMonthBySpId(PageData pd)throws Exception{
//		return (List<PageData>)dao.findForList("Sp_file_monthlyMapper.getMaxMonthBySpId", pd);
//	}
//	
//	
//
//}
//
