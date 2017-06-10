package com.tianer.entity.zhihui;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
 
/**
 * 
* 类名称：优选购物车
* 创建人：魏汉文  
* 创建时间：2016年4月7日 上午9:50:10
 */
public class YouXuanShop extends TimerTask{
 
 	private String member_id;//会员ID
 	private String goods_type;//商品ID
    

	public   YouXuanShop(){
		
	}

	public   YouXuanShop(String member_id,String goods_type) {
 		this.member_id = member_id;
 		this.goods_type = goods_type;
	}

	
	@Override
	public void run() {
			/*
			 * 在定时器到时间的时候 
			 * 1.获取当前购物车的最近时间
			 */
  		 	PageData pd=new PageData();
 		 	try { 
 		 		synchronized ( this ) { 
  		 		 long n1=(new Date()).getTime();
		 		 pd.put("member_id", member_id);
		 		 pd.put("goods_type", goods_type);
		 		 if(ServiceHelper.getShopCarService().getproximityBuyTime(pd) != null){
		 			 long n2=(long) ServiceHelper.getShopCarService().getproximityBuyTime(pd).get("starttimestamp");
 		 			 if(n1-n2 <0){
		 				n2=n1;
		 			 }
			 		 if(goods_type.equals("1")){
			 			if(n1-n2 >= (long) (Double.parseDouble(Const.goods_times)*60*60*1000)){//过期，将商品购买数量返还
 				 					//获取所有的商品
 					 			 List<PageData> goodsList= ServiceHelper.getShopCarService().MyShopCarList(pd);
 					 			 if(goodsList.size() >0){
 					 				 System.out.println("处理购物车的过期=====修改商品");
 					 				 for(PageData e : goodsList){
 	 					 				  ServiceHelper.getAppGoodsService().updateGoodsBuyNumber(e);
 	 					 			 }
 	 					 			 //清空购物车
 	 					 			 ServiceHelper.getShopCarService().delShop(pd);
 					 			 }
 					 	   }else{
				 			 	//设置定时器 
						 		YouXuanShop ys=new YouXuanShop(member_id,goods_type);
								Timer tt=new Timer();
								tt.schedule(ys, n1-n2);
				 		 }
			 		 }else if(goods_type.equals("2")){
   			 			 if(n1-n2 >= (long) (Double.parseDouble(Const.youxuangoods_times)*60*60*1000)){//过期，将商品返还
 	 	   			 				//获取所有的商品
						 			 List<PageData> goodsList= ServiceHelper.getShopCarService().MyShopCarList(pd);
						 			 if(goodsList.size() >0){
 						 				 for(PageData e : goodsList){
 						 					  System.out.println(e.getString("goods_id")+"处理购物车的过期=====修改商品");
	 						 				  e.put("youxuangg_id", e.getString("goods_id"));
	 						 				  ServiceHelper.getYouXuanService().updateYouXuanGoodsBuyNumber(e);
	 						 				  //判断库存改状态
	 						 				  PageData goodsggpd=ServiceHelper.getYouXuanService().finddetailgg(e);
	 						 				  if(ServiceHelper.getYouXuanService().lesssale_number(goodsggpd) != 0){
	 						 						PageData xpd=new PageData();
	 						 						xpd.put("goods_status", "4");
	 						 						xpd.put("youxuangoods_id", goodsggpd.getString("youxuangoods_id"));
	 						 						ServiceHelper.getYouXuanService().editGoods(xpd);
	 						 				  }
	 						 			 }
	 						 			 //清空购物车
	 						 			 ServiceHelper.getShopCarService().delShop(pd);
						 			}
 						 	 }else{
					 		    //设置定时器 
 						 		YouXuanShop ys=new YouXuanShop(member_id,goods_type);
								Timer tt=new Timer();
								tt.schedule(ys, n1-n2);
					 	 }
			 		 }
		 		 }
 		 		}
     		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    
	public static void main(String[] age){
		String n="003";
		System.out.println(Integer.parseInt(n)+1);
	}

}
