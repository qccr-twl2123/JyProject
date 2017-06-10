//package com.tianer.service.storepc.store_image;
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
// * 班次汇总
// * @author 邢江涛
// *
// */
//@Service("storepc_imageService")
//public class Storepc_imageService {
//	
//	@Resource(name = "daoSupport")
//	private DaoSupport dao;
//	
//	
//	/*
//	*列表
//	*/
//	public List<PageData> list(Page page)throws Exception{
//		return (List<PageData>)dao.findForList("XJTStorepc_imageMapper.datalistPage", page);
//	}
//	
//	/*
//	 *列表
//	 */
//	public List<PageData> listAll(PageData e)throws Exception{
//		return (List<PageData>)dao.findForList("XJTStorepc_imageMapper.listAll", e);
//	}
//	
//	
//	/*
//	*列表明细
//	*/
//	public List<PageData> listImage(Page page)throws Exception{
//		return (List<PageData>)dao.findForList("XJTStorepc_imageMapper.imagelistPage", page);
//	}
//	
//	
//	/*
//	 *班次汇总综合sum
//	 */
//	public PageData imagelistSum(Page page)throws Exception{
//		return (PageData)dao.findForObject("XJTStorepc_imageMapper.imagelistSum", page);
//	}
//	
//	/*
//	*获取现金的总和
//	*/
//	public String listxianjin(String id)throws Exception{
//		return (String) dao.findForObject("XJTStorepc_imageMapper.type1", id);
//	}
//
//	/*
//	*获取第三方的总和
//	*/
//	public String listdisanfang(String id)throws Exception{
//		return (String) dao.findForObject("XJTStorepc_imageMapper.type2", id);
//	}
//	
//	
//	/*
//	*获取积分的总和
//	*/
//	public String listjifen(String id)throws Exception{
//		return (String) dao.findForObject("XJTStorepc_imageMapper.type3", id);
//	}
//
//}
