package com.tianer.service.business.cm_all;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;


@Service("cm_allService")
public class Cm_allService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void saveRedStore(PageData pd)throws Exception{
		dao.save("Cm_allMapper.saveRedStore", pd);
	}

	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("Cm_allMapper.edit", pd);
	}
	

	
	
	
	/*
	*可发红包的商家20160614
	*/
	public List<PageData> listAllRedStore(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Cm_allMapper.listAllRedStore", pd);
	}
	
	/*
	*可发红包的商家魏汉文20160614
	*/
	public  PageData  getRedStoreById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Cm_allMapper.getRedStoreById", pd);
	}
	
	/*
	* 删除可发红包的商家魏汉文20160614
	*/
	public void deleteRedStore(PageData pd)throws Exception{
		dao.delete("Cm_allMapper.deleteRedStore", pd);
	}
	
	
	
	
	/*
	 *开机红包魏汉文20160614
	 */
	public List<PageData> listAllKaijiRed(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Cm_allMapper.listAllKaijiRed", pd);
	}
	
	/*
	 * 修改开机红包魏汉文20160614
	 */
	public void editKaijiRed(PageData pd)throws Exception{
		dao.update("Cm_allMapper.editKaijiRed", pd);
	}
	
	
	
	
	/*
	 *附近红包魏汉文20160614
	 */
	public List<PageData> listAllfujinRed(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Cm_allMapper.listAllfujinRed", pd);
	}
	/*
	 * 附近红包魏汉文20160614
	 */
	public void editfujinRed(PageData pd)throws Exception{
		dao.update("Cm_allMapper.editfujinRed", pd);
	}

	
	
	
	/*
	 *红包发送时间魏汉文20160614
	 */
	public List<PageData> listAllRedTime(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Cm_allMapper.listAllRedTime", pd);
	}
	
	/*
	 * 红包发送时间魏汉文20160614
	 */ 
	public void editRedTime(PageData pd)throws Exception{
		dao.update("Cm_allMapper.editRedTime", pd);
	}

	
	
	
	/*
	 *星级收费魏汉文20160614
	 */
	public List<PageData> listAllXingFee(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Cm_allMapper.listAllXingFee", pd);
	}
	/*
	 *星级收费魏汉文20160614
	 */
	public void editXingFee(PageData pd)throws Exception{
		dao.update("Cm_allMapper.editXingFee", pd);
	}
	

	
	
	
	/*
	 *提成和补贴魏汉文20160614
	 */
	public List<PageData> listAllTichengButie(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Cm_allMapper.listAllTichengButie", pd);
	}
	/*
	 *提成和补贴魏汉文20160614
	 */
	public void editTichengButie(PageData pd)throws Exception{
		dao.update("Cm_allMapper.editTichengButie", pd);
	}

	
	
	
	
	
	/*
	 *商店超限收费魏汉文20160614
	 */
	public List<PageData> listAllUpStoreFee(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Cm_allMapper.listAllUpStoreFee", pd);
	}
	/*
	 * 商店超限收费魏汉文20160614
	 */
	public void editUpStoreFee(PageData pd)throws Exception{
		dao.update("Cm_allMapper.editUpStoreFee", pd);
	}

	
	/*
	 *关键字魏汉文20160614
	 */
	public List<PageData> listAllcontent(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Cm_allMapper.listAllcontent", pd);
	}
	/*
	 *关键字魏汉文20160614
	 */
	public void editcontent(PageData pd)throws Exception{
		dao.update("Cm_allMapper.editcontent", pd);
	}
	
	
}

