package com.tianer.controller.storeapp.storeapp_redpackets;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.tongyongUtil.TongYong;
import com.tianer.entity.Page;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.storeapp.ChatRedService;
import com.tianer.service.storeapp.Storeapp_redpacketsService;
import com.tianer.service.storepc.store_redpackets.Storepc_redpacketsService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.StringUtil;

/** 
* 类名称：Storeapp_redpacketsController
* 创建人：邢江涛  app端发红包接口
* 创建时间：2016-06-28 
*/
@Controller("storeapp_redpacketsController")
@RequestMapping(value="/storeapp_redpackets")
public class Storeapp_redpacketsController extends BaseController{
	
	@Resource(name="storeapp_redpacketsService")
	private Storeapp_redpacketsService redpacketsService;
 
	@Resource(name = "ChatRedService")
	private ChatRedService chatRedService;
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
  
	
	/**
	 * 查询进入页面时需要显示的数据
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/listAll")
	@ResponseBody
	public Object listAll(Page page){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			//显示用户范围下拉框内容
			List<PageData>	varList = redpacketsService.listuser(pd);
			for(PageData e : varList){
				//用户范围id 
				List<PageData> rangeList = redpacketsService.listrange(e);
				e.put("sortList", rangeList);
				rangeList=null;
			}
			//显示使用条件的下拉框内容
			List<PageData>	conditionList = redpacketsService.listAll(pd);
			if(conditionList.size() == 0||varList.size() == 0){
				message="获取成功";
				map.put("data", "");
			}else{
				map.put("varList", varList);
				map.put("conditionList", conditionList);
				varList=null;
				conditionList=null;
			}
		} catch(Exception e){
			result="0";
			message="获取异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return map;
	}
	
	
	/**
	 * 查询红包列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/listRedpackets")
	@ResponseBody
	public Object listRedpackets(Page page){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
  			String nowpage=pd.getString("nowpage");
  			String store_id=pd.getString("store_id");
  			if(store_id == null || store_id.equals("")){
  				map.put("result", "0");
  				map.put("message","重要参数不能为空");
  				map.put("data","");
  				return map;
  			}
			if(nowpage == null || nowpage.equals("")){
				nowpage="1";
			}
			page.setCurrentPage(Integer.parseInt(nowpage));
			page.setPd(pd);
			//红包列表
			List<PageData>	varList = redpacketsService.listPageRedpackets(page);
			for (PageData pageData : varList) {
 				String id = pageData.getString("store_redpackets_id");
				//过期红包数量
				String guoqiCount = redpacketsService.guoqiCount(id);
				pageData.put("guoqiCount", guoqiCount);
				pageData.put("money",StringUtil.getMoveLastZero( pageData.getString("money")));
				//已使用红包数量
				String shiyongCount = redpacketsService.shiyongCount(id);
				pageData.put("shiyongCount", shiyongCount);
				//发送范围
				String content="";
 				if(pageData.getString("srp_opentype_id") != null && !pageData.getString("srp_opentype_id").equals("")){
						String[] str=pageData.getString("srp_opentype_id").split(",");
						if(str.length > 0){
							int n = 0;
							for(int i=0; i<str.length;i++){
								 String sp=str[i];
								 if( sp != null && !sp.equals("")){
									 n=Integer.parseInt(sp);
								 }
			 					 content+= Const.srp_opentype[n]+",";
			 				}
						}
						pageData.put("content", content);
				}
   			}
			if(varList.size() == 0||varList.size() == 0){
				message="获取成功";
				map.put("data", "");
			}else{
				map.put("data", varList);
				varList=null;
			}
		} catch(Exception e){
			result="0";
			message="获取异常";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return map;
	}
	
	@Resource(name = "storepc_redpacketsService")
	private Storepc_redpacketsService storepcRedpacketsService;
	
	/**
	 * 发送红包
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
 		DecimalFormat    df   = new DecimalFormat("######0.00");
		String result = "1";
		String message="发送成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("store_redpackets_id", "store"+BaseController.getTimeID());//红包编号
			//发放金额
			String money = pd.getString("money");
			//判断发放金额是否为空
			if(money == null || money.equals("") || !StringUtil.checkMoney(money) ||  Double.parseDouble(money) <= 0){
					map.put("result", "0");
					map.put("message", "发送失败，红包总额有误");
					map.put("data", "");
			 		return map;
			}
			pd.put("money", money);
			//商家id
			String store_id = pd.getString("store_id");
			pd.put("store_id", store_id);
 			//红包个数
			String redpackage_number = pd.getString("redpackage_number");
			//判断红包个数是否为空
			//判断红包个数是否为空
			if(redpackage_number == null || redpackage_number.equals("") || Integer.parseInt(redpackage_number) < 1 ){
					map.put("result", "0");
					map.put("message", "红包个数必须大于0");
					map.put("data", "");
			 		return AppUtil.returnObject(pd, map);
			}
 			//0-普通红包，1-开机红包，2-附近红包，3-聊天红包，4-发送的红包,5赠送红包
			pd.put("redpackage_status", Const.send_redpackage_status);
 			//发放规则
			String choice_type = pd.getString("choice_type");
			//判断是现金红包还是折扣红包
			String redpackage_type = pd.getString("redpackage_type");
			//使用条件
			String srp_usercondition_id = pd.getString("srp_usercondition_id");
			String srp_usercondition_content = pd.getString("srp_usercondition_content");
 			//判断这个红包是否满足条件
			if(redpackage_type.equals("1") && srp_usercondition_id.equals("2") ){//现金红包
 				String number=srp_usercondition_content.substring(srp_usercondition_content.indexOf("满")+1,srp_usercondition_content.indexOf("元"));
 				if(number == null || number.equals("")){
 					number="0";
 				}
 				double n=Double.parseDouble(number);//满多少元使用
 				double m=Double.parseDouble(money);//红包总共多少元
 				int x=Integer.parseInt(redpackage_number);//多少个红包
 				if(choice_type.equals("2")){
 					if(n < (m/x)*2){
 						map.put("result", "0");
 						map.put("message", "满X金额过少，至少满"+df.format(((m/x)*2))+"元");
 						map.put("data", "");
 				 		return map;
 					}
 				}else{
 					if(n < (m/x+m/x/2)*2){
 						map.put("result", "0");
 						map.put("message", "满X金额过少，至少满"+df.format(((m/x+m/x/2)*2))+"元");
 						map.put("data", "");
 				 		return map;
 					}
 				}
 			}
			long l0=DateUtil.fomatDate(DateUtil.getDay()).getTime();
			long l1=DateUtil.fomatDate( pd.getString("starttime")).getTime();
			long l2=DateUtil.fomatDate( pd.getString("endtime")).getTime();
 			if( l0>l1 || l0 > l2  ){
 				map.put("result", "0");
 				map.put("message", "红包有效时间不能小于今天");
 				map.put("data", "");
 		  		return map;
 			}
 			if( l1 > l2 ){
 				map.put("result", "0");
 				map.put("message", "结束时间不能小于开始时间");
 				map.put("data", "");
 		  		return map;
 			}
 			redpacketsService.save(pd);
 			//获取所有满足条件的会员发送推送
			TongYong.getMemberByRedSrpTuiSong(pd);
  		} catch (Exception e) {
			 result = "0";
			 message="发送信息有误";
			e.printStackTrace();
		}
 		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
 		return map;
	}
	
 	
}
