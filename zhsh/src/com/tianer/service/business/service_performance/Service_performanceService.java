package com.tianer.service.business.service_performance;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("service_performanceService")
public class Service_performanceService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	//==============================================================================================
	
	/*
	* 新增服务商月报表
	*/
	public void savespMonth(PageData pd)throws Exception{
		dao.save("Service_performanceMapper.savespMonth", pd);
	}
	
	
	/*
	* 修改服务商月报表
	*/
	public void editspMonth(PageData pd)throws Exception{
		dao.update("Service_performanceMapper.editspMonth", pd);
	}
	
	/*
	* 通过ID获取服务商月报表详情
	*/
	public PageData findByIdspMonth(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Service_performanceMapper.findByIdspMonth", pd);
	}
 
	
	/*
	 *获取服务商月报表列表(全部)
	 */
	public List<PageData> listAllspMonth(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Service_performanceMapper.listAllspMonth", pd);
	}
	
	/*
	 *获取服务商最近一个季度的信息
	 */
	public List<PageData> getMaxMonthBySpId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Service_performanceMapper.getMaxMonthBySpId", pd);
	}
	
	
	/*
	 *商家报表统计 sp_file_id,month,type
	 */
	public String countMonthStore(PageData pd)throws Exception{
		return (String)dao.findForObject("Service_performanceMapper.countMonthStore", pd);
	}
	
	/*
	 * 会员报表统计  sp_file_id,month,type
	 */
	public String countMonthMember(PageData pd)throws Exception{
		return (String)dao.findForObject("Service_performanceMapper.countMonthMember", pd);
	}
	
	/*
	 * 流水，积分，销售，广告，提现报表统计  sp_file_id,month,money_type,user_type,type
	 */
	public PageData countAllMonthByWater(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Service_performanceMapper.countAllMonthByWater", pd);
	}
	
	
	
	/*
	* 新增业务员月报表
	*/
	public void saveClerkMonth(PageData pd)throws Exception{
		dao.save("Service_performanceMapper.saveClerkMonth", pd);
	}
	
	
	/*
	* 修改业务员月报表
	*/
	public void editClerkMonth(PageData pd)throws Exception{
		dao.update("Service_performanceMapper.editClerkMonth", pd);
	}
	
	/*
	* 通过ID获取业务员月报表详情
	*/
	public PageData findByIdClerkMonth(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Service_performanceMapper.findByIdClerkMonth", pd);
	}
	

	
	/*
	 *获取业务员月报表列表(全部)
	 */
	public List<PageData> listPageAllClerkMonth(Page  page)throws Exception{
		return (List<PageData>)dao.findForList("Service_performanceMapper.listPageAllClerkMonth", page);
	}
	
	
	//=============================================================================================
	
	
	
	
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("Service_performanceMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("Service_performanceMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("Service_performanceMapper.edit", pd);
	}
	
//	/*
//	*列表服务商业绩
//	*/
//	public List<PageData> list(Page page)throws Exception{
//		return (List<PageData>)dao.findForList("Service_performanceMapper.datalistPage", page);
//	}

	
//	/*
//	* 通过id获取数据
//	*/
//	public PageData findById(PageData pd)throws Exception{
//		return (PageData)dao.findForObject("Service_performanceMapper.findById", pd);
//	}
	
	
	

	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Service_performanceMapper.listAll", pd);
	}
 	
 	
	
	
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("Service_performanceMapper.deleteAll", ArrayDATA_IDS);
	}
 	/*
	 * 批量修改
	 */
	public void editAllIsOk(String store_id)throws Exception{
		dao.update("Service_performanceMapper.editAllIsOk", store_id);
	}
	
	
	
	/*
	 * 获取未/已激活的费用来处理
	 */
	public Integer findNeedJiHuoFeeCount(PageData pd)throws Exception{
		return (Integer)dao.findForObject("Service_performanceMapper.findNeedJiHuoFeeCount", pd);
	}
 
	
	/*
	 * 获取未 激活的费用 
	 */
	public Double getSumNotJiHuoOptrator(String operate_id)throws Exception{
		return (Double)dao.findForObject("Service_performanceMapper.getSumNotJiHuoOptrator", operate_id);
	}

	
	/*
	 *列表服务商服务费收益
	 */
	public List<PageData> datalistPageOne(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Service_performanceMapper.datalistPageOne", page);
	}
  	
	/*
	 *列表服务商服务费收益
	 */
	public List<PageData> datalistPageTwo(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Service_performanceMapper.datalistPageTwo", page);
	}
 	
	/*
	 *列表服务商积分收益
	 */
	public List<PageData> datalistPageThree(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Service_performanceMapper.datalistPageThree", page);
	}
	
	/*
	 *列表服务商广告收益
	 */
	public List<PageData> datalistPageFour(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Service_performanceMapper.datalistPageFour", page);
	}
	
	/*
	 *列表服务商提现申请
	 */
	public List<PageData> datalistPageFive(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Service_performanceMapper.datalistPageFive", page);
	}
	
	/*
	 *列表服务商交易扣点
	 */
	public List<PageData> datalistPageSix(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Service_performanceMapper.datalistPageSix", page);
	}
	
	
	
	
	
	
}

