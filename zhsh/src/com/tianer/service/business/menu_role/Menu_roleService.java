package com.tianer.service.business.menu_role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("menu_roleService")
public class Menu_roleService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	

	
	/*
	 * 新增菜单魏0715
	 */
	public void saveMenu(PageData e )throws Exception{
 		dao.save("Menu_roleMapper.saveMenu", e);
	}
	
	/*
	 * 新增角色权限
	 */
	public void saveRoleQx(PageData pd)throws Exception{
		dao.save("Menu_roleMapper.saveRoleQx", pd);
	}
	
	
	/*
	* 新增魏0715
	*/
	public void saveRole(PageData pd)throws Exception{
		dao.save("Menu_roleMapper.saveRole", pd);
  	}
	
	
	/*
	 * 新增魏0715
	 */
	public void saveQx(PageData e )throws Exception{
 		dao.save("Menu_roleMapper.saveQx", e);
	}
	
	/*
	* 删除角色魏0715
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("Menu_roleMapper.delete", pd);
		dao.delete("Menu_roleMapper.deleteQx", pd);
	}
 
	
	/*
	* 修改 魏0715
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("Menu_roleMapper.edit", pd);
	}
	

	
	/*
	*列表魏0715
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Menu_roleMapper.datalistPage", page);
	}
	
	/*
	*列表魏0715
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Menu_roleMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据魏0715
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Menu_roleMapper.findById", pd);
	}
	
	/*
	* 判断角色是有有重复
	*/
	public PageData findByName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Menu_roleMapper.findByName", pd);
	}
	
	
	/*
	*获取父类菜单的名称魏0713
	*/
	public List<PageData> listAllParentMenu(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Menu_roleMapper.listAllParentMenu", pd);
	}
	
	/*
	 *获取子类菜单的名称魏0713
	 */
	public List<PageData> listAllMenu(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Menu_roleMapper.listAllMenu", pd);
	}
	
	/*
	 *获取指定ID的所有权限魏0714
	 */
	public List<PageData> listAllQx(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Menu_roleMapper.listAllQx", pd);
	}
	
	/*
	 *获取所有角色
	 */
	public List<PageData> listAllRole(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Menu_roleMapper.listAllRole", pd);
	}
	
	/*
	 * 修改权限魏0714
	 */
	public void delQx(PageData pd)throws Exception{
		dao.update("Menu_roleMapper.delQx", pd);
	}
	
	/*
	 * 修改权限魏0714
	 */
	public void updateQx(PageData pd)throws Exception{
		dao.update("Menu_roleMapper.updateQx", pd);
	}
	
}

