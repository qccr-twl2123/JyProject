package com.tianer.service.storepc.store_redpackets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/**
 * 红包记录
 * @author 邢江涛
 *
 */
@Service("storepc_redpacketsService")
public class Storepc_redpacketsService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	

	/***
	 * 红包分页
	 */
	public List<PageData> listPageRedAll(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XJTStore_redpacketsMapper.listPageRedAll", page);
	}
	
	
	/***
	 * 显示列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTStore_redpacketsMapper.listAll", pd);
	}
	
	/***
	 * 增红包状态为5
	 */
	public List<PageData> listAllRedPackage(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTStore_redpacketsMapper.listAllRedPackage", pd);
	}

	

	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("XJTStore_redpacketsMapper.save", pd);
	}
	
	/***
	 * 查看详情
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTStore_redpacketsMapper.findById", pd);
	}
	
	

	/**
	 * 更新商家红包的已使用数量
	 */
	public void update(PageData pd)throws Exception{
		dao.save("XJTStore_redpacketsMapper.update", pd);
	}
	
	//获取该商家下收藏过本店的会员信息集合
	public List<PageData> shoucangList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("XJTStore_redpacketsMapper.shoucangList", pd);
	}
	//获取在该商家消费过的会员信息集合
	public List<PageData> xiaofeiList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("XJTStore_redpacketsMapper.xiaofeiList", pd);
	}
	//获取在和商家处于同一城市下的会员信息集合
	public List<PageData> cityList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("XJTStore_redpacketsMapper.cityList", pd);
	}
	//获取在和商家处于同一县区下的会员信息集合
	public List<PageData> areaList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("XJTStore_redpacketsMapper.areaList", pd);
	}
	//我的盟友下的会员信息
	public List<PageData> mengyouList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("XJTStore_redpacketsMapper.mengyouList", pd);
	}
	// 本店会员的会员信息
 	public List<PageData> allmemberList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("XJTStore_redpacketsMapper.allmemberList", pd);
	}
	// 获取会员经纬度信息
	public List<PageData> hyjwList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("XJTStore_redpacketsMapper.hyjwList", pd);
	}
	// 获取商家经纬度信息
	public PageData sjjwList(PageData pd)throws Exception{
		return (PageData) dao.findForObject("XJTStore_redpacketsMapper.sjjwList", pd);
	}
	// 获取商家的一度人脉的人脉信息
	public List<PageData> renmaiYiList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("XJTStore_redpacketsMapper.renmaiYiList", pd);
	}
	// 获取商家的二度人脉的人脉信息
	public List<PageData> renmaiErList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("XJTStore_redpacketsMapper.renmaiErList", pd);
	}
	
	/**
	 *删除红包
	 */
	public void delete(PageData pd)throws Exception{
		dao.save("XJTStore_redpacketsMapper.delete", pd);
	}
	/**
	 * 查看该红包有没有被领取
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String selectNumber(PageData pd)throws Exception{
		return (String)dao.findForObject("XJTStore_redpacketsMapper.selectNumber", pd);
	}
}
