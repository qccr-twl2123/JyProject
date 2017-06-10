/**
 * 
 */
package com.tianer.service.storepc.liangqin.homepage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.entity.storepc.liangqin.Store;
import com.tianer.entity.storepc.liangqin.Store_Union;
import com.tianer.util.PageData;

/**
 * 类名称: MyLeagueService 
 * 类描述: TODO
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-14 上午9:45:17	
 * 版本号: v1.0
 */
@Service("myLeagueService")
public class MyLeagueService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	/**
	 * 查询关联的联盟信息
	 */

	public List<Store_Union> findStoreUnionById(Page page) throws Exception{
		 Store store = (Store) dao.findForObject("LQStoreMapper.findStoreUnionlistPage", page);
		 List<Store_Union> list = store.getStore_UnionList();
		 return list;
	}

	/**
	 * 删除联盟中的成员
	 */
	public void deleteStoreUnion(String fk_store_id) throws Exception{
		dao.update("LQStore_UnionMapper.deleteMember", fk_store_id);
	}
	

	
	/**
	 * 新建联盟
	 */
	public void newStoreUnion(Store_Union su) throws Exception{
		dao.save("LQStore_UnionMapper.newStoreUnion", su);
	}
	


	/**
	 * 方法名:findDistinctName
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public List<Store_Union> findDistinctName(Page page) throws Exception {
		return (List<Store_Union>) dao.findForList("LQStore_UnionMapper.findNamelistPage", page);
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * 方法名称:：findByLikeContent 
	 * 方法描述：通过content获取商家信息数据魏汉文0713
	 * 创建人：魏汉文
	 * 创建时间：2016年7月12日 下午7:50:58
	 */
	public List<PageData> findByLikeContent(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LQStore_UnionMapper.findByLikeContent", pd);
	}
	/**
	 * 
	* 方法名称:：unionlistPageForLeader 
	* 方法描述：获取我是盟主时所有的联信息信息魏汉文0712 
	* 创建人：魏汉文
	* 创建时间：2016年7月12日 下午7:50:58
	 */
	public List<PageData> unionlistPageForLeader(Page page) throws Exception {
		return (List<PageData>) dao.findForList("LQStore_UnionMapper.unionlistPageForLeader", page);
	}
	
	
	/**
	 * 
	* 方法名称:：unionForLeader 
	* 方法描述：获取我是盟主联信息魏汉文0712
	* 创建人：魏汉文
	* 创建时间：2016年7月12日 下午7:51:41
	 */
	public List<PageData> unionForLeader(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LQStore_UnionMapper.unionForLeader", pd);
	}
	
	
	/**
	 * 
	 * 方法名称:：unionlistPageForStore 
	 * 方法描述：获取我是盟友时所有的联信息信息魏汉文0712 
	 * 创建人：魏汉文
	 * 创建时间：2016年7月12日 下午7:50:58
	 */
	public List<PageData> unionlistPageForStore(Page page) throws Exception {
		return (List<PageData>) dao.findForList("LQStore_UnionMapper.unionlistPageForStore", page);
	}
	
	
	/**
	 * 
	 * 方法名称:：unionForStore 
	 * 方法描述：获取我是盟友联信息魏汉文0712
	 * 创建人：魏汉文
	 * 创建时间：2016年7月12日 下午7:51:41
	 */
	public List<PageData> unionForStore(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LQStore_UnionMapper.unionForStore", pd);
	}
	
	/**
	 * 
	* 方法名称:：delUnionByStore 
	* 方法描述： 删除联盟中的成员
	* 创建人：魏汉文
	* 创建时间：2016年7月12日 下午8:36:45
	 */
	public void delUnionByStore(PageData pd) throws Exception{
		dao.delete("LQStore_UnionMapper.delUnionByStore", pd);
	}
	
	/**
	 * 
	 * 方法名称:：delUnionByLeader 
	 * 方法描述： 删除联盟
	 * 创建人：魏汉文
	 * 创建时间：2016年7月12日 下午8:36:45
	 */
	public void delUnionByLeader(PageData pd) throws Exception{
		dao.delete("LQStore_UnionMapper.delUnionByLeader", pd);
		dao.delete("LQStore_UnionMapper.delUnionByStore", pd);
	}
	
	
	
	/**
	 * 新增联盟魏汉文0713
	 */
	public void saveLeaderUnion(PageData pd) throws Exception{
		dao.save("LQStore_UnionMapper.saveLeaderUnion", pd);
	}
	
	/**
	 * 新增盟友魏汉文0713
	 */
	public void saveStoreUnion(PageData pd) throws Exception{
		dao.save("LQStore_UnionMapper.saveStoreUnion", pd);
	}
	

}
