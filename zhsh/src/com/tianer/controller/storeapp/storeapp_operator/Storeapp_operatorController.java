package com.tianer.controller.storeapp.storeapp_operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.service.business.store_shift.Store_shiftService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.storeapp.Storeapp_operatorService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
import com.tianer.util.AppUtil;
import com.tianer.util.FileUpload;
import com.tianer.util.MD5;
import com.tianer.util.PageData;
import com.tianer.util.SmsUtil;
import com.tianer.util.huanxin.HuanXin;


/** 
 * 
* 类名称：Storeapp_operatorController   
* 类描述：  管理内部人员
* 创建人：邢江涛  
* 创建时间：2016年6月30日 
 */
@Controller("storeapp_operatorController")
@RequestMapping(value="/storeapp_operator")
public class Storeapp_operatorController extends BaseController{
	
	@Resource(name="storeapp_operatorService")
	private Storeapp_operatorService operatorService;
	
	@Resource(name="store_shiftService")
	private Store_shiftService store_shiftService;
	
	
	/**
	 * 查看内部成员 login_type login_id
	 * @param page
	 * @return
	 * storeapp_operator.listAll.do
	 * 
	 */
	@RequestMapping(value="/listAll")
	@ResponseBody
	public Object listAll( ){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
				pd = this.getPageData(); //商家id
				String login_type=pd.getString("login_type");
				String login_id=pd.getString("login_id");
				if(login_type.equals("2")){//操作员登录
					pd.put("store_operator_id", login_id);
				} 
 	 			List<PageData> varList = operatorService.listAll(pd);
	 			map1.put("varList", varList);
  				//获取当前商家的班次 
	 			if(login_type.equals("2") && varList.size()==1){
					pd.put("store_shift_id", varList.get(0).getString("store_shift_id"));
				}
	 			varList=null;
	 			List<PageData> shiftList=store_shiftService.listAll(pd);
	 			map1.put("shiftList", shiftList);
	 			shiftList=null;
		} catch(Exception e){
				result="0";
				map.put("message", "获取异常");
				logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data",map1);
		return map;
	}
	
	
	/**
	 * 删除成员
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(Page page){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="删除操作员成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(pd==null){
				result="0";
				message="删除失败[id]不能为空";
			}else{
				operatorService.delete(pd);
			}
 		} catch(Exception e){
			result="0";
			message="获取异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data","");
		return map;
	}
	
	/**
	 * 更新员工
	 * @param page
	 * @return
	 * storeapp_operator/update.do
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public Object update(Page page){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改操作员成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			storeManageService.updateOperator(pd);
 		} catch(Exception e){
			result="0";
			message="获取异常";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data","");
		return map;
	}
	
	@Resource(name="storeManageService")
	private StoreManageService storeManageService;
	
	/**
	 * 新增操作员
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="新增操作员成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
 //			//姓名
//			String operator_name = pd.getString("operator_name");
//			pd.put("operator_name", operator_name);
//			//手机号
			String operator_phone = pd.getString("operator_phone");
//			pd.put("operator_phone", operator_phone);
//			//身份证号
//			String ID_card_numbe = pd.getString("ID_card_numbe");
//			pd.put("ID_card_numbe", ID_card_numbe);
//			//状态(0-正常，1-停用)
//			String operator_status = pd.getString("operator_status");
//			pd.put("operator_status", operator_status);
//			//职务(1-操作员，2-收银员，3-管理员)
//			String operator_position = pd.getString("operator_position");
//			pd.put("operator_position", operator_position);
//			//权限  0,0,0,0
//			//所属商家
//			String store_id = pd.getString("store_id");
//			pd.put("store_id", store_id);
 //			pd.put("store_id", store_id);
			String password=BaseController.get6UID();
			pd.put("operator_password", MD5.md5(password));
			//内部人员id
			//循环
			String store_operator_id="";
			String store_name="";
			if(appStoreService.findById(pd) != null && appStoreService.findById(pd).getString("store_name") != null){
				store_name=appStoreService.findById(pd).getString("store_name");
			}
  			boolean flag=true;
			while(flag){
				store_operator_id= pd.getString("store_id")+this.get4UID();
				pd.put("store_operator_id",store_operator_id);
 				if(storeManageService.findOperatorById(pd) == null){
					flag=false;
 				} 
			}
			//----------
			operatorService.save(pd);
			try {
				//注册环信
				HuanXin.regirstHx(store_operator_id, store_operator_id, store_operator_id);
				//发送给操作员短信
 				SmsUtil.sendCzMm(operator_phone, password,store_name,store_operator_id);
			} catch (Exception e) {
				// TODO: handle exception
			}
 		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			 e.printStackTrace();
 		}
 		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
 		return map;
	}
	
	
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	/**
	 * 修改商家头像
	 * 魏汉文20160630
	 */
	@RequestMapping(value="/editImgae_url")
	@ResponseBody
	public Object editImgae_url(
			@RequestParam(value="image_url",required=false) MultipartFile file,
			@RequestParam  String store_id 
 			){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="新增成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String image_url="";
			if(file != null){
					String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
					String filePath = "/userFile";//文件上传路径
 					String cityFilename =  FileUpload.fileUp(file, currentPath+filePath,store_id);//字符拼接，上传到服务器上
				    image_url = AppUtil.getuploadRootUrlIp()+filePath+"/"+cityFilename;
    	 		}else{
   	 			result="0";
   	 			message="上传失败[file]不能为空";
   	 		}
			pd.put("store_id", store_id);
			pd.put("pictrue_url", image_url);
			appStoreService.edit(pd);
			map.put("data", image_url);
		} catch(Exception e){
			result = "0";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return AppUtil.returnObject(pd, map);
	}
	
	
	
	
	
	
	
}
