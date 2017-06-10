package com.tianer.util;

import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.city_marketing.City_marketingService;
import com.tianer.service.business.clerk_file.Clerk_fileService;
import com.tianer.service.business.cm_all.Cm_allService;
import com.tianer.service.business.operator_file.Operator_fileService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.send_notifications.Send_notificationsService;
import com.tianer.service.business.service_performance.Service_performanceService;
import com.tianer.service.business.sp_file.Sp_fileService;
import com.tianer.service.business.store.StoreService;
import com.tianer.service.business.store_file.Store_fileService;
import com.tianer.service.business.subsidiary.SubsidiaryService;
import com.tianer.service.business.waterrecord.WaterRecordService;
import com.tianer.service.htmlwx.HtmlWxService;
import com.tianer.service.memberapp.AppCity_fileService;
import com.tianer.service.memberapp.AppFriendService;
import com.tianer.service.memberapp.AppGoodsService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppMember_redpacketsService;
import com.tianer.service.memberapp.AppMember_wealthhistoryService;
import com.tianer.service.memberapp.AppOrderService;
import com.tianer.service.memberapp.AppPcdService;
import com.tianer.service.memberapp.AppShopCarService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.memberapp.AppStore_redpacketsService;
import com.tianer.service.memberapp.AppStorepc_marketingService;
import com.tianer.service.oneyuan.OneYuanService;
import com.tianer.service.storeapp.Payapp_historyService;
import com.tianer.service.storepc.daoliu.StoreDaoLiuService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
import com.tianer.service.storepc.store_discountway.Storepc_discountwayService;
import com.tianer.service.storepc.store_marketingeffect.Storepc_marketingeffectService;
import com.tianer.service.storepc.store_marketingtype.Storepc_marketingtypeService;
import com.tianer.service.storepc.store_redpackets.Storepc_redpacketsService;
import com.tianer.service.storepc.store_scoreway.Storepc_scorewayService;
import com.tianer.service.storepc.store_wealth.Storepc_wealthService;
import com.tianer.service.storepc.store_wealthhistory.Storepc_wealthhistoryService;
import com.tianer.service.storepc.stotr.StorepcService;
import com.tianer.service.storepc.tableNumber.TablerNumberService;
import com.tianer.service.system.menu.MenuService;
import com.tianer.service.system.role.RoleService;
import com.tianer.service.system.user.UserService;
import com.tianer.service.tongyon.TYAllSortService;
import com.tianer.service.youxuan.YouXuanService;
import com.tianer.service.zhaoshang.ZhaoShangService;



/**
 * 
* 类名称：ServiceHelper   
* 类描述： 获取Spring容器中的service bean  
* 创建人：魏汉文  
* 创建时间：2016年5月24日 下午3:57:37
 */
public final class ServiceHelper {
	
	//主要方法：需要在开启项目的时候就执行一个操作Const.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
	public static Object getService(String serviceName){
		return Const.WEB_APP_CONTEXT.getBean(serviceName);
	}
	
	//--------------------获取指定service方法---------------------------------
	
	public static TYAllSortService getTYAllSortService(){
		return (TYAllSortService) getService("tYAllSortService");
	}
	
	public static HtmlWxService getHtmlWxService(){
		return (HtmlWxService) getService("htmlWxService");
	}
	
	
	public static UserService getUserService(){
		return (UserService) getService("userService");
	}
	
	public static AppFriendService getAppFriendService(){
		return (AppFriendService) getService("appFriendService");
	}
	
	public static RoleService getRoleService(){
		return (RoleService) getService("roleService");
	}
	
	public static MenuService getMenuService(){
		return (MenuService) getService("menuService");
	}
	
	public static Send_notificationsService getSend_notificationsService(){
		return (Send_notificationsService) getService("send_notificationsService");
	}
	//商家财富
		public static StorepcService getStorepcService(){
			return (StorepcService) getService("storepcService");
		}
	//商家财富
	public static Storepc_wealthhistoryService getStorepc_wealthhistoryService(){
		return (Storepc_wealthhistoryService) getService("storepc_wealthhistoryService");
	}
	//商家财富
	public static Storepc_wealthService getStorepc_wealthService(){
		return (Storepc_wealthService) getService("storepc_wealthService");
	}
	//导流
	public static ZhaoShangService getZhaoShangService(){
		return (ZhaoShangService) getService("zhaoShangService");
	}
	

	//导流
	public static StoreDaoLiuService getStoreDaoLiuService(){
		return (StoreDaoLiuService) getService("storeDaoLiuService");
	}
	
	//城市营销参数需要的服务层
	public static Cm_allService getCm_allService(){
		return (Cm_allService) getService("cm_allService");
	}
	
	//城市营销参数需要的服务层
	public static AppCity_fileService getAppCity_fileService(){
		return (AppCity_fileService) getService("appCity_fileService");
	}
 
	//服务商
	public static Sp_fileService getSp_fileService(){
		return (Sp_fileService) getService("sp_fileService");
	}
	
	//操作员
	public static Operator_fileService getOperator_fileService(){
		return (Operator_fileService) getService("operator_fileService");
	}
	
	//桌号
	public static TablerNumberService getTablerNumberService(){
		return (TablerNumberService) getService("tablerNumberService");
	}
	
	//子公司
	public static SubsidiaryService getSubsidiaryService(){
		return (SubsidiaryService) getService("subsidiaryService");
	}
	
	//服务商或者业务员的业绩
	public static Service_performanceService getService_performanceService(){
		return (Service_performanceService) getService("service_performanceService");
	}
	
	//
	public static AppPcdService getAppPcdService(){
		return (AppPcdService) getService("appPcdService");
	}
	
	//
	public static PcdService getPcdService(){
		return (PcdService) getService("pcdService");
	}
	
	//
	public static AppStore_redpacketsService getAppStore_redpacketsService(){
		return (AppStore_redpacketsService) getService("appStore_redpacketsService");
	}
 
	//城市
	public static City_fileService getCity_fileService(){
		return (City_fileService) getService("city_fileService");
	}
	
	public static Clerk_fileService getClerk_fileService(){
		return (Clerk_fileService) getService("clerk_fileService");
	}
 
	
	public static AppOrderService getAppOrderService(){
		return (AppOrderService) getService("appOrderService");
	}
	
	public static AppMemberService getAppMemberService(){
		return (AppMemberService) getService("appMemberService");
	}
	
	public static StoreService getStoreService(){
		return (StoreService) getService("storeService");
	}
	
 	
	public static AppStoreService getAppStoreService(){
		return (AppStoreService) getService("appStoreService");
	}
	
	public static AppStorepc_marketingService getAppStorepc_marketingService(){
		return (AppStorepc_marketingService) getService("appStorepc_marketingService");
	}
	
	public static Store_fileService getStore_fileService(){
		return (Store_fileService) getService("store_fileService");
	}
	
	public static Storepc_marketingeffectService getstorepcMarketingeffectService(){
		return (Storepc_marketingeffectService) getService("storepc_marketingeffectService");
	}
	
	
	public static Storepc_discountwayService getStorepc_discountwayService(){
		return (Storepc_discountwayService) getService("storepc_discountwayService");
	}
	
	
	public static Storepc_redpacketsService getStorepc_redpacketsService(){
		return (Storepc_redpacketsService) getService("storepc_redpacketsService");
	}
	
	
	public static Storepc_marketingtypeService getStorepc_marketingtypeService(){
		return (Storepc_marketingtypeService) getService("storepc_marketingtypeService");
	}
	
	
	public static Storepc_scorewayService getStorepc_scorewayService(){
		return (Storepc_scorewayService) getService("storepc_scorewayService");
	}
	
	public static AppMember_redpacketsService getAppMember_redpacketsService(){
		return (AppMember_redpacketsService) getService("appMember_redpacketsService");
	}
	
	public static AppMember_wealthhistoryService getAppMember_wealthhistoryService(){
		return (AppMember_wealthhistoryService) getService("appMember_wealthhistoryService");
	}
	
	//城市营销
	public static City_marketingService getCity_marketingService(){
		return (City_marketingService) getService("city_marketingService");
	}
	
	//获取提货卷
	public static Payapp_historyService getPayapp_historyService(){
		return (Payapp_historyService) getService("payapp_historyService");
	}
 
	
	
	//操作员的信息
	public static StoreManageService getStoreManageService(){
		return (StoreManageService) getService("storeManageService");
	}
	
	
	//商品信息
	public static AppGoodsService getAppGoodsService(){
		return (AppGoodsService) getService("appGoodsService");
	}
	
	
	//购物车
	public static AppShopCarService getShopCarService(){
		return (AppShopCarService) getService("appShopCarService");
	}
	
	//总流水信息
	public static WaterRecordService getWaterRecordService(){
		return (WaterRecordService) getService("waterRecordService");
	}
	
	//优选的的服YouXuanService层
	public static YouXuanService getYouXuanService(){
		return (YouXuanService) getService("youXuanService");
	}
	
	
	//优选的的服OneYuanService层
	public static OneYuanService getOneYuanService(){
		return (OneYuanService) getService("oneYuanService");
	}
 
 
	
	
}
