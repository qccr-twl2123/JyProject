package com.tianer.service.business.send_notifications;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("send_notificationsService")
public class Send_notificationsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("Send_notificationsMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("Send_notificationsMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("Send_notificationsMapper.edit", pd);
	}
	
	/**
	 * 审核
	 * @param pd
	 * @throws Exception
	 */
	public void toExamine(PageData pd)throws Exception{
		dao.update("Send_notificationsMapper.toExamine", pd);
	}
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Send_notificationsMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Send_notificationsMapper.listAll", pd);
	}

	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Send_notificationsMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("Send_notificationsMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	* 新增推送记录=======================================================================================
	*/
	public void saveTuisong(PageData pd)throws Exception{
		dao.save("Send_notificationsMapper.saveTuisong", pd);
	}
	

	/*
	* 批量更新推送状态
	*/
	public void deleteTuisong(String[] array)throws Exception{
		dao.update("Send_notificationsMapper.deleteTuisong", array);
	}
	
	
	/*
	 *获取推送列表
	 */
	public List<PageData> listAllTuisong(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Send_notificationsMapper.listAllTuisong", pd);
	}
	
	
}

