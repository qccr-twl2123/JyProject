package com.tianer.listener;


import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.entity.zhihui.JFredTask;
import com.tianer.entity.zhihui.OrderShop;
import com.tianer.entity.zhihui.StoreFeeTihuoTask;
import com.tianer.entity.zhihui.TihuoTask;
import com.tianer.entity.zhihui.WxTask;
import com.tianer.entity.zhihui.YouXuanShop;
import com.tianer.entity.zhihui.YouXuanTask;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
public class ScheduleStartListener implements ServletContextListener{	
	
	@Override
	public void contextDestroyed(ServletContextEvent sce){}	
	
	@Override
	public void contextInitialized(ServletContextEvent sce){		
		try {
			System.out.print("定时任务启动开始");
 			YouXuanTimer();
 			TiHuoTimer();
 			StoreFWTimer();
 			shopcart();
 			kuncunOrder();
 			jfredchuli();
 			wx();
 			zzredpakage();
			System.out.print("定时任务启动结束");
		}catch (Exception e){
			(new TongYong()).dayinerro(e);
		}
	}
	
	
    /**
     * 设置微信的基础的access_token和jsapi_ticket
     */
	public void wx(){
 		try {
			WxTask wx=new WxTask();
			Timer tt=new Timer();
			tt.schedule(wx,1,102*60*1000);
		} catch (Exception e) {
			// TODO: handle exception
			(new TongYong()).dayinerro(e);
		}
	}
 	 
	
	/**
	 * 优选的定时
	 */
	public synchronized void YouXuanTimer(){
		PageData pd = new PageData();
		try {
			pd.put("isover", "1");// //0-刚生成，1-正在进行，99-已结束
			List<PageData> dqlist=ServiceHelper.getYouXuanService().listAllDangqi(pd);
			int length=dqlist.size();
 			PageData e=null;
 			for (int i = 0; i <length; i++) {
 				e=dqlist.get(i);
				String youxuandq_id=e.getString("youxuandq_id");
				String city_file_id=e.getString("city_file_id");
				String enddate=e.getString("enddate");
				String opentime=e.getString("opentime");
				//设置定时任务
	 			long n1=(new Date()).getTime();
	 			String kqtime=enddate+" "+opentime;
	 			long n2=DateUtil.fomatDate1(kqtime).getTime();
	 			if(n2-n1 > 0){
	 				YouXuanTask yp=new YouXuanTask(youxuandq_id,city_file_id);
					Timer tt=new Timer();
					tt.schedule(yp, n2-n1);
	 			}
	 			e=null;
 			}
 			System.out.println("----------- 优选定时任务结束 ----------------");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("优选的定时"+e.toString());
			(new TongYong()).dayinerro(e);
		}
	}
	
	/**
	 * 设置提货卷的定时器
	 */
	public synchronized void TiHuoTimer(){
 		PageData tihuopd=new PageData();
		try { 
			List<PageData> tihuoList=ServiceHelper.getAppOrderService().tihuoListByendTime(tihuopd);
			int length=tihuoList.size();
 			PageData e=null;
 			for (int i = 0; i <length; i++) {
 				e=tihuoList.get(i);
				String time=e.get("enddate").toString();
				String tihuo_id=e.getString("tihuo_id");
				//设置定时器
				long l1=DateUtil.fomatDate1(DateUtil.getTime()).getTime();
				long l2=DateUtil.fomatDate1(time).getTime();
				if(l1 < l2){
					TihuoTask th=new TihuoTask(tihuo_id);
					Timer tt=new Timer();
					tt.schedule(th, l2-l1);
				}else{
					TihuoTask th=new TihuoTask(tihuo_id);
					Timer tt=new Timer();
					tt.schedule(th, 30000);
				}
				e=null;
 			}
			System.out.println("----------- 提货券定时任务结束 ----------------");
		} catch (Exception e) {
				e.printStackTrace();
				System.out.println("设置提货卷的定时器"+e.toString());
				(new TongYong()).dayinerro(e);
		} 
	}
	/**
	 * 设置商家购买服务费的定时器
	 */
	public synchronized  void StoreFWTimer(){
 		PageData pd=new PageData();
		try { 
			List<PageData> storeList=ServiceHelper.getAppStoreService().listAllOkStore(pd);
			int length=storeList.size();
 			PageData e=null;
 			for (int i = 0; i <length; i++) {
 				e=storeList.get(i);
				String endtime=e.get("endtime").toString();
				//设置定时器
		 		long l1=DateUtil.fomatDate(DateUtil.getDay()).getTime();
				long l2=DateUtil.fomatDate(endtime).getTime();
				StoreFeeTihuoTask feeth=new StoreFeeTihuoTask(e.getString("store_id"));
				Timer tt=new Timer();
				if( l2-l1 >0){
					tt.schedule(feeth, l2-l1);
				}else{
					tt.schedule(feeth, 1000);
				}
  			}
			System.out.println("----------- 购买服务费定时任务结束 ----------------");
		} catch (Exception e) {
				e.printStackTrace();
				System.out.println("设置商家购买服务费的定时器"+e.toString());
				(new TongYong()).dayinerro(e);
		} 
	}
	
	/**
	 * 购物车的定时
	 */
	public synchronized void shopcart(){
		PageData pd = new PageData();
		try {
 			List<PageData> shpcartlist=ServiceHelper.getShopCarService().forMemberShopCart(pd);
 			int shpcartlistlength=shpcartlist.size();
 			PageData e=null;
 			for (int i = 0; i < shpcartlistlength; i++) {
 				e=shpcartlist.get(i);
 				//设置定时器 
 				long l=1000*10;
		 		YouXuanShop ys=new YouXuanShop(e.getString("member_id"),e.getString("goods_type"));
				Timer tt=new Timer();
				tt.schedule(ys, l);
				e=null;
			}
			System.out.println("----------- 购物车的定时开始 ----------------");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("设置购物车的定时"+e.toString());
			(new TongYong()).dayinerro(e);
		}
	}
	
	
	/**
	 *未支付成功为绑定订单的定时
	 */
	public synchronized void kuncunOrder(){
		PageData e = new PageData();
 		try {
 			List<PageData> kuncunOrderlist=ServiceHelper.getAppOrderService().allkuncunOrder(e);
 			int kuncunOrderlistlength=kuncunOrderlist.size();
 			for (int i = 0; i < kuncunOrderlistlength; i++) {
				e=kuncunOrderlist.get(i);
				if(ServiceHelper.getAppOrderService().findById(e) != null && ServiceHelper.getAppOrderService().findById(e).getString("order_status").equals("0")){
					//设置定时器 
	 		 		OrderShop os=new OrderShop(e.getString("order_id"));
					Timer tt=new Timer();
					tt.schedule(os, 1000);
				}else{
					ServiceHelper.getAppOrderService().deletekuncunOrder(e);
				}
				e=null;
 			}
			System.out.println("----------- 未支付成功为绑定订单的定时 ----------------");
		} catch (Exception e1) {
 			System.out.println("设置未支付成功为绑定订单的定时"+e1.toString());
			(new TongYong()).dayinerro(e1);
		}
	}
	
	
	
		/**
		 * 未过期积分红包到期处理
		 */
		public synchronized  void jfredchuli(){
			PageData e = new PageData();
		    try{
			    	//查询所有状态未过期但是时间是已过期的积分红包
		    		List<PageData> passJfRedList=ServiceHelper.getAppPcdService().allPassTimeJfRed(e);
		    		int passJfRedListlength=passJfRedList.size();
	 				for (int i = 0; i < passJfRedListlength; i++) {
	 					e=passJfRedList.get(i);
 						if(e != null){
							String ms_redpackage_id=e.getString("ms_redpackage_id");
							//判断时间是否满足处理
							Date now=new Date();
							String createtime=e.get("createtime").toString();
 							long n3=Const.jfguoqi; 
							long n1=DateUtil.fomatDate1(createtime).getTime()+n3;
							long n2=now.getTime();
							long n4=n1-n2;
							if(n4 < 0){
									n4 = 30000;
							} 
							JFredTask jf=new JFredTask(ms_redpackage_id);
		 					Timer tt=new Timer();
		 					tt.schedule(jf, n4);
		 					e=null;
						}
 	 				}
					 System.out.println("执行完成");
	    }catch(Exception e1){
	    	System.out.println("未过期积分红包到期处理"+e1.toString());
	    	(new TongYong()).dayinerro(e1);
	    }
	}
		/**
		 * 对转增红包进行处理
		 */
		public synchronized  void zzredpakage(){
			PageData pd = new PageData();
			try{
				//查询转增红包 
				List<PageData> zzRedList=ServiceHelper.getAppMember_redpacketsService().listAllZZred(pd);
				int zzRedListlength=zzRedList.size();
 				for (int i = 0; i < zzRedListlength; i++) {
 					pd=zzRedList.get(i);
 					pd.put("iszhuanzeng", "0");
 					pd.put("ishiyong", "0");
	 		 		ServiceHelper.getAppMember_redpacketsService().editRedStatus(pd);
	 		 		pd=null;
				}
				System.out.println("对转增红包进行处理====执行完成");
			}catch(Exception e){
				System.out.println("对转增红包进行处理"+e.toString());
				(new TongYong()).dayinerro(e);
			}
		}
	
	
	
	
	
	
	
 	public static void main(String[] args) {}  
 	
 	
}
