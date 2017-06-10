package com.tianer.service.storepc.liangqin.basemanage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.entity.storepc.liangqin.Operator_File;
import com.tianer.entity.storepc.liangqin.Store;
import com.tianer.entity.storepc.liangqin.Store_Bankcard;
import com.tianer.entity.storepc.liangqin.Store_File_Sort;
import com.tianer.entity.storepc.liangqin.Store_Image;
import com.tianer.util.PageData;
/**
 * 类名称: VIPService 
 * 类描述: TODO
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-17 上午9:01:47	
 * 版本号: v1.0
 */
@Service("storeManageService")
public class StoreManageService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;	
	
	public void editStoreFile(PageData pd) throws Exception{
		dao.update("LQStore_FileMapper.editStore", pd);
		dao.update("AppStoreMapper.edit", pd);
	}
	
	public void editJiuchushezhi(PageData pd) throws Exception{
		dao.update("LQStore_FileMapper.editJiuchushezhi", pd);
	}

	/**
	 * 方法名:addStoreImage
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public void editStoreImage(PageData pd) throws Exception {
		dao.save("Store_imageMapper.edit", pd);
	}
	


	/**
	 * 方法名:showStoreMessage
	 * 描述:TODO
	 *
	*/
	public PageData showStoreMessage(String store_id) throws Exception{
		return (PageData) dao.findForObject("LQStoreMapper.findAll", store_id);
	}

	/**
	 * 方法名:showBankMessage
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public PageData showBankMessage(String store_id) throws Exception {
		return (PageData) dao.findForObject("LQStore_BankcardMapper.findBankMessageByStoreId", store_id);
	}

	/**
	 * 方法名:showSortMessage
	 * 描述:TODO
	 *
	*/
	public PageData showSortMessage(String store_id) throws Exception {
		return (PageData) dao.findForObject("LQStore_File_SortMapper.findSortMessage", store_id);
	}

	/**
	 * 方法名:updateMessage
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public void updateMessage(PageData pd) throws Exception {
		dao.update("LQStore_File_SortMapper.updateMessage", pd);
	}

	/**
	 * 方法名:findStoreById
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public Store findStoreById(String store_id) throws Exception {
		return (Store) dao.findForObject("LQStoreMapper.findStoreById", store_id);
	}

	/**
	 * 方法名:updateStore
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public void updateStore(PageData  pd) throws Exception {
		dao.update("LQStoreMapper.updatePassword", pd);
	}

	/**
	 * 方法名:findOperator
	 * 描述:TODO
	 *
	*/
	public List<PageData> datalistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("Store_operatorMapper.datalistPage", page);
	}
	
	/**
	 * 方法名:findOperator
	 * 描述:TODO
	 *
	 */
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("Store_operatorMapper.listAll", pd);
	}

	/**
	 * 方法名:save
	 * 描述:TODO
	 * @throws Exception 
	 *
	 */
	public void save(PageData pd) throws Exception {
		dao.save("Store_operatorMapper.save", pd);
	}
	
	
	/**
	 * 获取密码
	 */
	public String findPassword(String str) throws Exception {
		return (String) dao.findForObject("Store_operatorMapper.findPassword", str);
	}
	/**
	 * 方法名:findOperatorById
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public PageData findOperatorById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("Store_operatorMapper.findById", pd);
	}

	/**
	 * 方法名:updateOperator
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public void updateOperator(PageData pd) throws Exception {
		dao.update("Store_operatorMapper.updateOperator", pd);
 	}
	/**
	 * 方法名:updatealldesk_no
	 * 描述:TODO
	 * @throws Exception 
	 *
	 */
	public void updatealldesk_no(PageData pd) throws Exception {
		dao.update("Store_operatorMapper.updatealldesk_no", pd);
	}
	/**
	 * 方法名:updateOperator
	 * 描述:TODO
	 * @throws Exception 
	 *
	 */
	public void updateOperatorStatus(PageData pd) throws Exception {
		dao.update("Store_operatorMapper.updateOperatorStatus", pd);
	}

	/**
	 * 方法名:delOperatorById
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public void delOperatorById(PageData pd) throws Exception {
		dao.delete("Store_operatorMapper.delete", pd);
	}
	/**
	 *	通过id查出商家信息
	 * 	刘耀耀
	 *  2016.07.13 
	 *
	*/

	public PageData findStoreById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("LQStore_FileMapper.findByStoreId", pd);
	}
	/**
	 *	通过id查出商家图片
	 * 	刘耀耀
	 *  2016.07.13 
	 * @throws Exception 
	 *
	*/

	public List<PageData> findImage(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LQStore_FileMapper.findImage", pd);
	}
	
	/**
	 *	全部操作员信息
	 *
	*/
	public List<PageData> operatorList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("Store_operatorMapper.operatorList", pd);
	}
	
	/**
	 *指定操作员或全部
 	 */
	public List<PageData> listAllOperator(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("Store_operatorMapper.listAllOperator", pd);
	}
	
}



