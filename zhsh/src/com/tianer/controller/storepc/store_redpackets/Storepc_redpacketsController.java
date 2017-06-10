package com.tianer.controller.storepc.store_redpackets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.tongyongUtil.TongYong;
import com.tianer.entity.Page;
import com.tianer.service.storeapp.ChatRedService;
import com.tianer.service.storepc.store_marketing.Storepc_marketingService;
import com.tianer.service.storepc.store_redpackets.Srp_userconditionService;
import com.tianer.service.storepc.store_redpackets.Storepc_redpacketsService;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.StringUtil;


/** 
 * 
* 类名称：Storepc_scorewayController   
* 类描述：   红包记录
* 创建人：邢江涛  
* 创建时间：2016年6月13日 
 */
@Controller("storeStorepc_redpacketsController")
@RequestMapping(value="/storepc_redpackets")
public class Storepc_redpacketsController extends BaseController{

	
	@Resource(name = "storepc_redpacketsService")
	private Storepc_redpacketsService storepcRedpacketsService;
	@Resource(name = "srp_userconditionService")
	private Srp_userconditionService srpUserconditionService;

	@Resource(name = "storepc_marketingService")
	private Storepc_marketingService storepcMarketingService;
	@Resource(name = "ChatRedService")
	private ChatRedService chatRedService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
 			pd = this.getPageData();
 			List<PageData>	varList = storepcRedpacketsService.listAll(pd);	//列出Store列表
			for(PageData e : varList){
				String content="";
				if(e.getString("srp_opentype_id") != null){
					String[] str=e.getString("srp_opentype_id").split(",");
					if(str.length > 0 ){
						int n = 0;
						for(int i=0; i<str.length;i++){
							if(i < 2){
								 String sp=str[i];
								 if( sp != null && !sp.equals("")){
									 n=Integer.parseInt(sp);
								 }
			 					 content+= Const.srp_opentype[n]+",";
							}
 		 				}
						content=content.substring(0, content.length()-1);
					}
				}
 				e.put("content", content);
 			}
			List<PageData> srpList = srpUserconditionService.listAll();//获取使用条件
			mv.addObject("srpList", srpList);
			List<PageData> unionList=new ArrayList<PageData>();
			List<PageData> mengyouList = storepcRedpacketsService.mengyouList(pd);
			if(mengyouList.size() > 0){
				for (int j = 0; j < mengyouList.size(); j++) {
						PageData e=new PageData();
						String onestore_id = mengyouList.get(j).getString("one");
						String onestore_name = mengyouList.get(j).getString("onestore_name");
						String twostore_id = mengyouList.get(j).getString("two");
						String twostore_name = mengyouList.get(j).getString("twostore_name");
 						if(onestore_id.equals(pd.getString("store_id"))){
							e.put("store_id", onestore_id);
							e.put("store_name", onestore_name);
							unionList.add(e);
						}else{
							e.put("store_id", twostore_id);
							e.put("store_name", twostore_name);
							unionList.add(e);
						}
 						e=null;
					}
			}
			mv.addObject("unionList", unionList);
 			mv.addObject("varList", varList);
 			String jichushezhi=pd.getString("jichushezhi");
			if(jichushezhi != null && jichushezhi.equals("11111111111")){
				mv.setViewName("/storepc/shezhi_11");
  			}else{
  				mv.setViewName("storepc/business_marketing7");
   			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		mv.addObject("pd",pd);
 		return mv;
	}
	
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save() throws Exception{
 		Map<String , Object> map = new HashMap<String, Object>();
 		String result = "01";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//判断发放金额是否为空
			String money=pd.getString("money");
			if(money == null || money.equals("") || !StringUtil.checkMoney(money) ||  Double.parseDouble(money) <= 0){
					map.put("result", "0");
					map.put("message", "发送金额有误");
					map.put("data", "");
			 		return map;
			}
			//红包个数
			String redpackage_number = pd.getString("redpackage_number");
			//判断红包个数是否为空
			if(redpackage_number == null || redpackage_number.equals("") ||  Integer.parseInt(redpackage_number) <= 0){
					map.put("result", "0");
					map.put("message", "红包个数不能为空/或为0");
					map.put("data", "");
			 		return map;
			}
			long l1=DateUtil.fomatDate( pd.getString("starttime")).getTime();
			long l2=DateUtil.fomatDate( pd.getString("endtime")).getTime();
 			if(l1 > l2 ){
 				map.put("result", "0");
 				map.put("message", "红包结束时间不能小于今天");
 				map.put("data", "");
 		  		return map;
 			}
			String store_redpackets_id="store"+BaseController.getTimeID();
			//组合为红包编号
			pd.put("store_redpackets_id", store_redpackets_id );	//主键
			//0-普通红包，1-开机红包，2-附近红包，3-聊天红包，4-发送的红包,5赠送红包
			pd.put("redpackage_status", Const.send_redpackage_status);
			//判断是现金红包还是折扣红包
			//使用条件
			String srp_usercondition_id = pd.getString("srp_usercondition_id");
			if(srp_usercondition_id.equals("1")){
				pd.put("srp_usercondition_content", pd.getString("tiaojiantext"));
			}else if(srp_usercondition_id.equals("2")){
				pd.put("srp_usercondition_content", "满"+pd.getString("usermoney")+"元使用");
			}else if(srp_usercondition_id.equals("3")){
				pd.put("srp_usercondition_content",pd.getString("tiaojiantext"));
			}
			pd.put("srp_usercondition_id", srp_usercondition_id);
 			//新增到本表数据库
			storepcRedpacketsService.save(pd);
			//获取所有满足条件的会员发送推送
			TongYong.getMemberByRedSrpTuiSong(pd);
 		} catch (Exception e) {
			logger.error(e.toString(), e);
			result="0";
		}
		map.put("result", result);
 		return map;
 	}
	
 
	
	/**
	 * 删除红包
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/deleteRed")
	@ResponseBody
	public Object deletebank(Page page){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="删除成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			String count = storepcRedpacketsService.selectNumber(pd);
			if(count != null && !count.equals("")){
				double num = Double.parseDouble(count);
				if(num == 0){
					storepcRedpacketsService.delete(pd);
				}else{
					result="2";
				}
			}else{
				result="2";
			}
			
		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data","");
		return map;
	}
	
}
