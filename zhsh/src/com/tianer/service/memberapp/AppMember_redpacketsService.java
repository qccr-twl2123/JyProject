package com.tianer.service.memberapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;


@Service("appMember_redpacketsService")
public class AppMember_redpacketsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	*查出红包列表
	*刘耀耀
	*2016.06.22
	*/
	public List<PageData> list(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppMember_redpacketsMapper.list", pd);
	}
	
	/*
	 *查出会员可用红包列表
	 *魏汉文20160630
	 */
	public List<PageData> listAllById(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppMember_redpacketsMapper.listAllById", pd);
	}
	
 

	
	
	/*
	*添加红包
	*魏汉文20160701
	*/
	public void saveSendRed(PageData pd) throws Exception {
		dao.save("AppMember_redpacketsMapper.saveSendRed", pd);
 	}
	
	/*
	 * 我的可以使用的红包列表魏汉文20160630，状态可以选择
	 */
	public List<PageData> listRedId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppMember_redpacketsMapper.listRedId", pd);
	}
	
	/*
	* 删除过期红包
	*/
	public void deleteGuoqiRed(PageData pd)throws Exception{
		dao.delete("AppMember_redpacketsMapper.deleteGuoqiRed", pd);
	}

	/*
	* 修改红包的拥有者魏汉文20160701
	*/
	public void editRedPackageForId(PageData pd)throws Exception{
		dao.delete("AppMember_redpacketsMapper.editRedPackageForId", pd);
	}
	
	/*
	 *修改红包状态魏汉文20160705(过期/使用)
	 */
	public void editRedStatus(PageData pd)throws Exception{
		dao.update("AppMember_redpacketsMapper.editRedStatus", pd);
	}
	
	/*
	 *删除红包状态魏汉文20160705
	 */
	public void deleteRed(PageData pd)throws Exception{
		dao.delete("AppMember_redpacketsMapper.deleteRed", pd);
	}
	
	
	
	/*
	 * 我的可以使用的红包列表魏汉文20160630
	 */
	public  PageData  findById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("AppMember_redpacketsMapper.findById", pd);
	}

	

	/*
	 * 我的转增红包列表
	 */
	public List<PageData> listAllZZred(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppMember_redpacketsMapper.listAllZZred", pd);
	}
	
	
}

