package com.tianer.entity.zhihui;

import java.util.List;
import java.util.TimerTask;

import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
 
 
/**
 * 
* 类名称：去支付的订单到期时间
* 创建人：魏汉文  
* 创建时间：2016年4月7日 上午9:50:10
 */
public class OrderShop extends TimerTask{
 
 	private String order_id;//会员ID
     

	public   OrderShop(){
		
	}

	public   OrderShop(String order_id ) {
 		this.order_id = order_id;
 	}

	
	@Override
	public void run() {
			/*
			 * 在定时器到时间的时候 
 			 */
  		 	PageData pd=new PageData();
 		 	try { 
 		 		synchronized ( this ) { 
 		 			pd.put("order_id", order_id);
 		 			if(ServiceHelper.getAppOrderService().detailkuncunOrde(pd) != null){
 		 				//删除
		 				ServiceHelper.getAppOrderService().deletekuncunOrder(pd);
		 				//详情
		 				pd=ServiceHelper.getAppOrderService().findById(pd);
	 		 			if(pd != null && (pd.getString("order_status").equals("0") || pd.getString("order_status").equals("99"))){
 	 		 				//获取商品信息
	 						List<PageData> goodsList=ServiceHelper.getAppGoodsService().getGoodsIdByOrder(pd);
	 						for(PageData e : goodsList){
	  							if(e.getString("goods_type").equals("2")){
	  								  System.out.println(pd.getString("order_id")+"优选订单到期时间修改商品====库存");
	 								  e.put("youxuangg_id", e.getString("goods_id"));
					 				  ServiceHelper.getYouXuanService().updateYouXuanGoodsBuyNumber(e);
					 				  //判断库存改状态
					 				  System.out.println(e.toString());
					 				  PageData goodsggpd=ServiceHelper.getYouXuanService().finddetailgg(e);
					 				  if(!goodsggpd.getString("needsale_number").equals("0")){
					 						PageData xpd=new PageData();
					 						xpd.put("goods_status", "4");
					 						xpd.put("youxuangoods_id", goodsggpd.getString("youxuangoods_id"));
					 						ServiceHelper.getYouXuanService().editGoods(xpd);
					 				  }
	 							}else if(e.getString("goods_type").equals("1")){
	 								  ServiceHelper.getAppGoodsService().updateGoodsBuyNumber(e);
	 							}
 	   						}
	 						//删除订单以及订单中关联的的商品
 							ServiceHelper.getAppOrderService().deleteOrderShop(pd);
	 		 			}
	  		 		}
 		 		}
  		 			
     		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    
	public static void main(String[] age){
 	}

}
