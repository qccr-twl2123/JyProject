package com.tianer.controller.memberapp.tongyongUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.sun.xml.internal.ws.wsdl.writer.document.Service;
import com.tianer.controller.base.BaseController;
import com.tianer.entity.zhihui.MessageTask;
import com.tianer.entity.zhihui.TihuoTask;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.Distance;
import com.tianer.util.EbotongSecurity;
import com.tianer.util.FileUpload;
import com.tianer.util.JPushClientUtil;
import com.tianer.util.MD5;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;
import com.tianer.util.StringUtil;
import com.tianer.util.ErWerMa.OneEr;
import com.tianer.util.huanxin.HuanXin;


/** 
 * 
* 类名称：Payapp_historyController   
* 类描述：   收银记录app
* 创建人：邢江涛
* 创建时间：2016年7月4日 
 */
public class TongYong extends BaseController{
	
	//-----------------------------------------
	
	public static DecimalFormat    df3   = new DecimalFormat("######0.000"); 
	public static DecimalFormat    df2   = new DecimalFormat("######0.00"); 
	public static DecimalFormat    df1   = new DecimalFormat("######0.0"); 
	public static DecimalFormat    df0   = new DecimalFormat("######0"); 
	
	//打印错误记录
  	public void dayinerro(Exception e){
  		logger.error(e.toString(), e);
  		e=null;
  	}
	

	/**
	 * 新增会员魅力值
	 */
  	public static void charm_numberAdd(String member_id,String charm_number){
 		PageData pd = new PageData();
		try{
				 pd.put("member_id", member_id);
				 pd=ServiceHelper.getAppMemberService().findById(pd);
				 //魅力值：0-50一星会员 50-99 二星会员 100-199 三星会员 200-499  四星会员 500-999 五星会员 1000-2000 一钻会员
				int number=(int)Double.parseDouble(pd.getString("charm_number"))+Integer.parseInt(charm_number);
				PageData chpd=new PageData();
				chpd.put("member_id", member_id);
				chpd.put("charm_number", number);
				if(number >=0 && number < 50){
					chpd.put("vip_level", "1");
				}else if(number >=50 && number < 100){
					chpd.put("vip_level", "2");
				}else if(number >=100 && number < 200){
					chpd.put("vip_level", "3");
				}else if(number >=200 && number < 500){
					chpd.put("vip_level", "4");
				}else if(number >=500 && number < 1000){
					chpd.put("vip_level", "5");
				}else if(number >=1000 && number < 2000){
					chpd.put("vip_level", "6");
				}
				ServiceHelper.getAppMemberService().edit(chpd);
 		} catch(Exception e){
  			//System.out.println(e.toString()+"新增会员魅力值===========================");
  			(new TongYong()).dayinerro(e);
 		}
 	}
  	
  	
 
  	/**
  	 * 新增商家的综合分值：商家id以及增加到多少的综合分值
  	 */
  	public static void complex_scoreAdd(String store_id,String complex_score){
    		try{
    			//根据综合分值增加星级
			    PageData compd=new PageData();
	 			compd.put("store_id", store_id);
				compd.put("complex_score", complex_score);
				double number=Integer.parseInt(complex_score);
				if(number >= Const.xingcomplexscore[2]){
					compd.put("merchant_level", "3");
					compd.put("goods_max", Const.xingcomplexscoregoodsnumber[2]);
	   			}else if(number >=  Const.xingcomplexscore[1]){
	   				compd.put("merchant_level", "2");
	   				compd.put("goods_max", Const.xingcomplexscoregoodsnumber[1]);
	   			}else{
	   				compd.put("merchant_level", "1");
	   				compd.put("goods_max", Const.xingcomplexscoregoodsnumber[0]);
	   			}
				ServiceHelper.getStoreService().edit(compd);
				compd=null;
				//加综合分值结束
  		} catch(Exception e){
   			//System.out.println(e.toString()+"新增综合分值===========================");
   			(new TongYong()).dayinerro(e);
  		}
   	}
  	
  
	/**
	 * 
	* 方法名称:：YingXiao 
	* 方法描述：营销效果表
	* 创建人：魏汉文
	* 创建时间：2016年7月20日 上午10:53:00
	 */
	public static void YingXiao(PageData pd){
 		try{
			/*
		   	 * 营销效果表数据优惠内容 (content@id@number@type ,content@id@number@type.....)
		   	 * 0-红包， 1-满赠，2-满减，3-时段营销，4-买N减N，5-购买次数，6-积分，7-折扣
		   	 */
		   			String discount_content=pd.getString("discount_content");
//		   			//System.out.println("YingXiao内容：discount_content="+discount_content);
		   			if(discount_content != null && discount_content.contains(",")){
		   					String[] str=discount_content.split(",");
		   					PageData  effectpd=null;
		   					String[] str1=null;
		   					int strlength=str.length;
		   					for(int i=0; i<strlength ; i++){
 		   						  	str1=str[i].split("@");
//		   						  	//System.out .println("营销效果 0-红包， 1-满赠，2-满减，3-时段营销，4-买N减N，5-购买次数，6-积分，7-折扣=============================content@id@number@type ");
		   						  	//System.out .println("营销效果=============================str1="+str1.toString());
		   						  	String content=str1[0];
		   						  	String marketing_id=str1[1];
		   						  	String number=str1[2];
		   						  	String marketing_type=str1[3];
		   						  	effectpd=new PageData();
		   						    effectpd.put("store_id", pd.getString("store_id"));
		   						    effectpd.put("marketing_id", marketing_id);
		   						    if(marketing_type.equals("1")){
		   						    		//System.out .println("进入满赠===========================");
			   						    	effectpd=ServiceHelper.getstorepcMarketingeffectService().findById(effectpd);
			   						    	String number1=content.substring(content.indexOf("满")+1, content.indexOf("元"));
			   						    	if(effectpd== null){
			   						    			effectpd=new PageData();
			   						    		    effectpd.put("marketing_id", marketing_id);
			   						    		    effectpd.put("store_id", pd.getString("store_id"));
			   						    		    effectpd=ServiceHelper.getStorepc_marketingtypeService().findById(effectpd);
			   						    		    if(effectpd != null){
			   						    		    	effectpd.put("startdate", effectpd.get("startdate").toString());
				   						    		    effectpd.put("enddate", effectpd.get("enddate").toString());
				   						    		    effectpd.put("marketing_type", marketing_type);
				   						    		    effectpd.put("marketing_id", marketing_id);
				   						    		    effectpd.put("content", content);
	 			   						    		    effectpd.put("grant_number", number1);
				   						    		    effectpd.put("receive_number", "1");
				   						    		    ServiceHelper.getstorepcMarketingeffectService().save(effectpd);
			   						    		    }
  			   						    	}else{
			   						    		    double grant_number=Double.parseDouble(effectpd.getString("grant_number"));
			   						    		    double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
			   						    		    effectpd.put("grant_number", df2.format( grant_number+Double.parseDouble(number1) ));
			   						    		    effectpd.put("receive_number",receive_number+1);
			   						    		 ServiceHelper.getstorepcMarketingeffectService().update(effectpd);
			   						    	}
		   						    }else if(marketing_type.equals("2")){
	 			   						    	effectpd=ServiceHelper.getstorepcMarketingeffectService().findById(effectpd);
	 			   						    	String number1=content.substring(content.indexOf("满")+1, content.indexOf("元"));
	 			   						    	if(effectpd== null){
			 			   						    	effectpd=new PageData();
	 			   						    		    effectpd.put("marketing_id", marketing_id);
	 			   						    		    effectpd.put("store_id", pd.getString("store_id"));
	 			   						    		    effectpd=ServiceHelper.getStorepc_marketingtypeService().findById(effectpd);
	 			   						    		    if(effectpd != null){
		 			   						    		    effectpd.put("startdate", effectpd.get("startdate").toString());
		 			   						    		    effectpd.put("enddate", effectpd.get("enddate").toString());
		 			   						    		    effectpd.put("marketing_type", marketing_type);
		 			   						    		    effectpd.put("marketing_id", marketing_id);
		 			   						    		    effectpd.put("content", content);
				 			   						    	effectpd.put("grant_number", number1);
		 			   						    		    effectpd.put("receive_number",number.substring(1)+"" );
		 			   						    		    ServiceHelper.getstorepcMarketingeffectService().save(effectpd);
	 			   						    		    }
 	 			   						    	}else{
	 			   						    		double grant_number=Double.parseDouble(effectpd.getString("grant_number"));
			   						    		    double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
			   						    		    effectpd.put("grant_number", df2.format( grant_number+Double.parseDouble(number1) ));
			   						    		    effectpd.put("receive_number",df2.format(receive_number+Double.parseDouble(number.substring(1))));
			   						    		    ServiceHelper.getstorepcMarketingeffectService().update(effectpd);
	 			   						    	}
		   						    }else if(marketing_type.equals("3")){
	 			   						    effectpd=ServiceHelper.getstorepcMarketingeffectService().findById(effectpd);
		 			   						String number1="0";
		   						    		    if(content.contains("元")){
		   						    		    	number1=content.substring(content.indexOf("满")+1, content.indexOf("元"));
		   						    		    }
 			   						    	if(effectpd== null){
			 			   						    	effectpd=new PageData();
	 			   						    		    effectpd.put("marketing_id", marketing_id);
	 			   						    		    effectpd.put("store_id", pd.getString("store_id"));
	 			   						    		    effectpd=ServiceHelper.getStorepc_marketingtypeService().findById(effectpd);
	 			   						    		    if(effectpd != null){
		 			   						    		    effectpd.put("startdate", effectpd.get("startdate").toString());
		 			   						    		    effectpd.put("enddate", effectpd.get("enddate").toString());
		 			   						    		    effectpd.put("marketing_type", marketing_type);
		 			   						    		    effectpd.put("marketing_id", marketing_id);
		 			   						    		    effectpd.put("content", content);
			 			   						    	    effectpd.put("grant_number", number1);
		 			   						    		    effectpd.put("receive_number",number.substring(1)+"" );
		 			   						    		    ServiceHelper.getstorepcMarketingeffectService().save(effectpd);
	 			   						    		    }
	 			   						    		    
 			   						    	}else{
	 			   						    	double grant_number=Double.parseDouble(effectpd.getString("grant_number"));
			   						    		double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
			   						    		effectpd.put("grant_number", df2.format( grant_number+Double.parseDouble(number1) ));
 			   						    		effectpd.put("receive_number",df2.format(receive_number+Double.parseDouble(number.substring(1))));
 			   						    		ServiceHelper.getstorepcMarketingeffectService().update(effectpd);
 			   						    	}
		   						    }else if(marketing_type.equals("4")){
	 			   						    effectpd=ServiceHelper.getstorepcMarketingeffectService().findById(effectpd);
	 			   						    String number1=content.substring(content.indexOf("满")+1, content.indexOf("件"));
 			   						    	if(effectpd== null){
			 			   						    	effectpd=new PageData();
	 			   						    		    effectpd.put("marketing_id", marketing_id);
	 			   						    		    effectpd.put("store_id", pd.getString("store_id"));
	 			   						    		    effectpd=ServiceHelper.getStorepc_marketingtypeService().findById(effectpd);
	 			   						    		    if(effectpd != null){
		 			   						    		    effectpd.put("startdate", effectpd.get("startdate").toString());
		 			   						    		    effectpd.put("enddate", effectpd.get("enddate").toString());
		 			   						    		    effectpd.put("marketing_type", marketing_type);
		 			   						    		    effectpd.put("marketing_id", marketing_id);
		 			   						    		    effectpd.put("content", content);
				 			   						    	effectpd.put("grant_number", number1);
		 			   						    		    effectpd.put("receive_number",number.substring(1)+"" );
		 			   						    		    ServiceHelper.getstorepcMarketingeffectService().save(effectpd);
	 			   						    		    }
	 			   						    		    
 			   						    	}else{
 			   						    		double grant_number=Double.parseDouble(effectpd.getString("grant_number"));
			   						    		    double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
			   						    		    effectpd.put("grant_number", df2.format( grant_number+Double.parseDouble(number1) ));
			   						    		    effectpd.put("receive_number",df2.format(receive_number+Double.parseDouble(number.substring(1))));
			   						    		 ServiceHelper.getstorepcMarketingeffectService().update(effectpd);
 			   						    	}
		   						    }else if(marketing_type.equals("5")){
	 			   						    effectpd=ServiceHelper.getstorepcMarketingeffectService().findById(effectpd);
	 			   						    String number1="0";
	 			   						    if(content.contains("件")){
			   						    		     number1=content.substring(content.indexOf("满")+1, content.indexOf("件"));
		   						    		    }
		   						    		    if(content.contains("元")){
		   						    		    	 number1=content.substring(content.indexOf("满")+1, content.indexOf("元"));
		   						    		    }
 			   						    	if(effectpd== null){
			 			   						    	effectpd=new PageData();
	 			   						    		    effectpd.put("marketing_id", marketing_id);
	 			   						    		    effectpd.put("store_id", pd.getString("store_id"));
	 			   						    		    effectpd=ServiceHelper.getStorepc_marketingtypeService().findById(effectpd);
	 			   						    		    if(effectpd != null){
		 			   						    		    effectpd.put("startdate", effectpd.get("startdate").toString());
		 			   						    		    effectpd.put("enddate", effectpd.get("enddate").toString());
		 			   						    		    effectpd.put("marketing_type", marketing_type);
		 			   						    		    effectpd.put("marketing_id", marketing_id);
		 			   						    		    effectpd.put("content", content);
		 			   						    		    effectpd.put("grant_number", number1);
			 			   						    		effectpd.put("receive_number","1" );
			 			   						    		ServiceHelper.getstorepcMarketingeffectService().save(effectpd);
	 			   						    		    }
  			   						    	}else{
	 			   						    	double grant_number=Double.parseDouble(effectpd.getString("grant_number"));
			   						    		double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
			   						    		effectpd.put("grant_number", df2.format( grant_number+Double.parseDouble(number1) ));
 			   						    		effectpd.put("receive_number",df2.format(receive_number+1));
 			   						    		ServiceHelper.getstorepcMarketingeffectService().update(effectpd);
 			   						    	}
		   						    }else if(marketing_type.equals("6")){
	 			   						    effectpd=ServiceHelper.getstorepcMarketingeffectService().findById(effectpd);
 			   						    	if(effectpd== null){
			 			   						    	    effectpd=new PageData();
	 			   						    		        effectpd.put("marketing_id", marketing_id);
	 			   						    		        effectpd.put("store_id", pd.getString("store_id"));
		 			   						    		    effectpd.put("startdate", "2016-01-01");
		 			   						    		    effectpd.put("enddate","2020-01-01");
		 			   						    		    effectpd.put("marketing_type", marketing_type);
		 			   						    		    effectpd.put("marketing_id", marketing_id);
		 			   						    		    effectpd.put("content", content);
			 			   						    	    effectpd.put("grant_number", "");
			 			   						    	    effectpd.put("receive_number", Double.parseDouble(number.substring(1)));
			 			   						    	    ServiceHelper.getstorepcMarketingeffectService().save(effectpd);
 			   						    	}else{
			   						    		        double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
 			   						    		    effectpd.put("receive_number",df2.format(receive_number+Double.parseDouble(number.substring(1))));
 			   						    		    ServiceHelper.getstorepcMarketingeffectService().update(effectpd);
 			   						    	}
		   						    }else if(marketing_type.equals("7")){
	 			   						    effectpd=ServiceHelper.getstorepcMarketingeffectService().findById(effectpd);
 			   						    	if(effectpd== null){
			 			   						   	effectpd=new PageData();
	 			   						    		effectpd.put("marketing_id", marketing_id);
	 			   						    		effectpd.put("store_id", pd.getString("store_id"));
	 			   						    		effectpd=ServiceHelper.getStorepc_discountwayService().findById(effectpd);
	 			   						    		if(effectpd != null){
		 			   						    		effectpd.put("startdate", effectpd.get("starttime").toString());
	 			   						    		    effectpd.put("enddate", effectpd.get("endtime").toString());
	 			   						    		    effectpd.put("marketing_type", marketing_type);
	 			   						    		    effectpd.put("marketing_id", marketing_id);
	 			   						    		    effectpd.put("receive_number", Double.parseDouble(number.substring(1)));
	 			   						    		    effectpd.put("content", content);
		 			   						    	    effectpd.put("grant_number", "");
		 			   						    	    ServiceHelper.getstorepcMarketingeffectService().save(effectpd);
	 			   						    		}
  			   						    	}else{
			   						    		    	double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
 			   						    		    effectpd.put("receive_number",df2.format(receive_number+Double.parseDouble(number.substring(1))));
 			   						    		ServiceHelper.getstorepcMarketingeffectService().update(effectpd);
 			   						    	}
		   						    }else if(marketing_type.equals("0")){//红包
		   						    			//更新商家红包的使用数量
		   						    			ServiceHelper.getStorepc_redpacketsService().update(effectpd);
		   						    			//操作Marketingeffect表
		 			   						    effectpd=ServiceHelper.getstorepcMarketingeffectService().findById(effectpd);
 			   						    	   if(effectpd== null){
			 			   						    	effectpd=new PageData();
	 			   						    		    effectpd.put("marketing_id", marketing_id);
	 			   						    		    effectpd.put("store_id", pd.getString("store_id"));
	 			   						    		    effectpd=ServiceHelper.getStorepc_redpacketsService().findById(effectpd);
	 			   						    		    if(effectpd != null){
		 			   						    		    effectpd.put("startdate", effectpd.get("starttime").toString());
		 			   						    		    effectpd.put("enddate", effectpd.get("endtime").toString());
		 			   						    		    effectpd.put("marketing_type", marketing_type);
		 			   						    		    effectpd.put("marketing_id", marketing_id);
		 			   						    		    effectpd.put("content", content);
			 			   						    	    effectpd.put("grant_number",effectpd.getString("redpackage_number") );
		 			   						    		    effectpd.put("receive_number","1" );
		 			   						    		    ServiceHelper.getstorepcMarketingeffectService().save(effectpd);
	 			   						    		    }
  			   						    	  }else{
			   						    		    		double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
			   						    		    		effectpd.put("receive_number",df2.format(receive_number+1));
			   						    		    		ServiceHelper.getstorepcMarketingeffectService().update(effectpd);
 			   						    	 }
		   						    }
		   						 effectpd=null;
		   						 //System.out .println("营销效果记录处理结束===============================================");
		   					}
		   			}
			}catch(Exception e){
				//System.out.println("======================营销管理出错订单号为==============="+e.toString());
				(new TongYong()).dayinerro(e);
  			}
		}
	
	/**
	 * 对订单进行处理以及判断是否可以
	 * @param pd 订单pd
	 * @return
	 */
	public static PageData chuliOrder(PageData pd,PageData mpd,PageData spd){
		PageData returnpd=new PageData();
		String result="1";
		String message="订单没有问题";
		try {
				String pay_type=pd.getString("pay_type");//1-收银，2-优惠买单，3-提货卷 ,4-购买一元夺宝 ， 
				String discount_content=pd.getString("discount_content");//优惠项
				if(!pay_type.equals("3") && (discount_content == null || discount_content.trim().equals(""))){
					returnpd.put("result", "0");
					returnpd.put("message", "支付失败，优惠项有问题。请咨询商家");
					returnpd.put("data", pd);
			    	return returnpd;
				}
				String order_id=pd.getString("order_id");
				String order_status=pd.getString("order_status");
				String store_id=pd.getString("store_id");
 				String actual_money=pd.getString("actual_money");//支付的金额（现金或者第三方）
	 			String pay_way=pd.getString("pay_way");//支付方式
				String allgoodsid=pd.getString("allgoodsid");//所有订单里的商品
				String user_balance=pd.getString("user_balance");//使用的余额
				String user_integral=pd.getString("user_integral");//使用的积分
				String sale_money=pd.getString("sale_money");//订单金额
				String discount_money=pd.getString("discount_money"); 
				String no_discount_money=pd.getString("no_discount_money"); 
				String get_integral=pd.getString("get_integral");//使用的积分
 				if(order_status == null || order_status.equals("")){
					order_status="0";
					pd.put("order_status", order_status);
 		       	}
				if(sale_money == null || sale_money.equals("")){
					sale_money="0";
					pd.put("sale_money", sale_money);
				}
				if(get_integral == null || get_integral.equals("")){
					get_integral="0";
					pd.put("get_integral", get_integral);
					pd.put("sendxitong_integral","0");
				}else{
					pd.put("sendxitong_integral", df2.format(Double.parseDouble(get_integral)*Const.orderShouyiMoney[0]));
				}
 				if(user_integral == null || user_integral.equals("")){
					user_integral="0";
					pd.put("user_integral", user_integral);
				}
				if(user_balance == null || user_balance.equals("")){
					user_balance="0";
					pd.put("user_balance", user_balance);
				}
				if(discount_money == null || discount_money.equals("")){
					discount_money="0";
					pd.put("discount_money", discount_money);
				}
				if(no_discount_money == null || no_discount_money.equals("")){
					no_discount_money="0";
					pd.put("no_discount_money", no_discount_money);
				}
				//判断优惠金额和不优惠金额
	  			if(Double.parseDouble(sale_money)/2.00 - Double.parseDouble(no_discount_money) < 0 ){
		  				returnpd.put("result", "0");
		  				returnpd.put("message", "不优惠金额不能大于总金额的50%");
		  				returnpd.put("data", "");
						return returnpd;
	  			}
				double discount_after_money=Double.parseDouble(sale_money)-Double.parseDouble(discount_money);
				pd.put("discount_after_money", df2.format(discount_after_money));//优惠后总共需要支付的金额
				//判断金钱是否大于0
//				System.out.println("sale_money="+sale_money+"*actual_money="+actual_money+"*user_balance="+user_balance+"*user_integral="+user_integral+"*discount_money="+discount_money);
 				if(Double.parseDouble(sale_money) <= 0 || (!df2.format(Double.parseDouble(sale_money)).equals(df2.format(Double.parseDouble(actual_money)+Double.parseDouble(user_balance)+Double.parseDouble(user_integral)+Double.parseDouble(discount_money))))){
					returnpd.put("result", "0");
					returnpd.put("message", "金钱的支付有误，请注意一下");
					returnpd.put("data", pd);
			    	return returnpd;
				}
				//判断金钱是否符合
//				//System.out.println("==============================判断金钱是否符合");
				if( !StringUtil.checkMoney(sale_money) ||  !StringUtil.checkMoney(user_balance) || !StringUtil.checkMoney(user_integral) ){
					returnpd.put("result", "0");
					returnpd.put("message", "金钱格式有误/总金额不能为0");
					returnpd.put("data", pd);
			    	return returnpd;
				}
				//判断是否开通类别积分
				PageData ispd=ServiceHelper.getAppStorepc_marketingService().getJfById(pd);
				if(ispd != null && ispd.getString("change_type").equals("4") ){
					String fourbackintegral_integral=ispd.getString("fourbackintegral_integral");
					if(fourbackintegral_integral == null || fourbackintegral_integral.equals("")){
						fourbackintegral_integral="0";
					}
					//判断支付金额是否大于赠送积分
//					//System.out.println("==============================判断支付金额是否大于赠送积分");
					if(Double.parseDouble(sale_money) < Double.parseDouble(fourbackintegral_integral)*2){
						returnpd.put("result", "0");
						returnpd.put("message", "支付金额不能小于"+df2.format(Double.parseDouble(fourbackintegral_integral)*2));
						returnpd.put("data",pd);
					    return returnpd;
					}
				}
 	 		if(mpd == null){
	 				returnpd.put("result", "0");
	 				returnpd.put("message", "用户不存在，请联系管理员");
	 				returnpd.put("data", pd);
	 		    	return returnpd;
 			}
//			//System.out.println("==============================判断余额是否充足");
 			//判断余额
			double now_money=Double.parseDouble(mpd.getString("now_money"));
			double n=Double.parseDouble(user_balance);
				if(now_money < n){
					returnpd.put("result", "0");
					returnpd.put("message", "余额不足，当前余额"+now_money);
					returnpd.put("data", pd);
			    	return returnpd;
			} 
//			//System.out.println("==============================判断积分是否充足");
			//判断积分
			if(!user_integral.equals("")){
   				double now_integral=Double.parseDouble(mpd.getString("now_integral"));
 				double m=Double.parseDouble(user_integral);
				if(now_integral < m){
					returnpd.put("result", "0");
					returnpd.put("message", "积分不足，当前积分"+now_integral);
					returnpd.put("data", "");
					return returnpd;
				} 
 			}
 			//判断商家的赠送积分是否充足
 			if(TongYong.orderIsOkByStore(Double.parseDouble(get_integral), discount_after_money, pay_type, Double.parseDouble(user_integral), Double.parseDouble(user_integral), Double.parseDouble(actual_money), spd)){
				returnpd.put("result", "0");
				returnpd.put("message", spd.getString("store_name")+"积分余额不足，请等待商家充值后再购买");
				returnpd.put("data", pd);
		    	return returnpd;
			}
			//分配操作员
			pd.put("store_operator_id", "");
			pd.put("store_shift_id", "");
			String desk_no=pd.getString("desk_no");
// 			//System.out.println("==============================分配操作员按桌号"+desk_no);
			if(desk_no != null && !desk_no.trim().equals("")){
				List<PageData> varList = ServiceHelper.getStoreManageService().listAll(pd);
 				for (int i = 0; i < varList.size(); i++) {
						PageData opratorpd= varList.get(i);
 						if(opratorpd.getString("alldesk_no") != null && opratorpd.getString("alldesk_no").contains(desk_no)){
							   pd.put("store_operator_id",opratorpd.getString("store_operator_id"));
							   pd.put("store_shift_id", opratorpd.getString("store_shift_id"));
					   } 
 						opratorpd=null; 
				}
				varList=null;
			}
			if(pd.getString("store_operator_id").equals("")){
				 pd.put("store_operator_id", "jy"+store_id);
			}
  			if(pay_way == null || pay_way.equals("") || pay_way.equals("nowpay") || pay_way.equals("2")){
					pay_way="nowpay";
					pd.put("pay_way", pay_way);
					pd.put("money_pay_type", "2");
			}else{
					if(pay_way.contains("alipay")){
						pd.put("money_pay_type", "3");
					}else if(pay_way.contains("wx")){
						pd.put("money_pay_type", "4");
					} 
			}
  			
			pd.put("store_renmai_money", "0");
			pd.put("order_tn", pd.getString("order_id"));
			pd.put("channel", pay_way);
			String channel=pay_way;
			double lastpay_money=Double.parseDouble(actual_money);
			//1-收银，2-优惠买单，3-提货卷 ,4-购买一元夺宝 ， 
			if(pay_type.equals("1")){
				boolean orderflag=TongYong.historyByOrder(pd, channel, "1",pd.getString("apptype"),true);
				if(!orderflag){
					message="支付失败,请联平台";
					result="0";
				}
			}else if(pay_type.equals("2")){
	 			if(lastpay_money == 0){//新增订单
 	               	boolean orderflag=TongYong.historyByOrder(pd, channel, pay_type,"b",true);
					if(!orderflag){
							message="支付失败,请联平台";
							result="0";
					}else{
						if(pd.getString("store_operator_id").contains("jy")){
							//发送推送
							//System.out.println("推送别名=="+store_id);
					    	TongYong.sendTuiSong(store_id, order_id, "2", store_id, "1", sale_money,"");
						}else{
							//发送推送
							//System.out.println("推送别名=="+pd.getString("store_operator_id"));
					    	TongYong.sendTuiSong(pd.getString("store_operator_id"), order_id, "2", pd.getString("store_operator_id"), "11", pd.getString("sale_money"),"");
						}
 					}
				}else{//待确认
						pd.put("order_status", "0");
						pd.put("tihuo_status", "1");
						ServiceHelper.getAppOrderService().saveOrder(pd);
				}
  			}else if(pay_type.equals("3")){
  				pd.put("pay_sort_type", "1");
  				//生成一个提货卷
				boolean istihuo=true;
				while(istihuo){
							String tihuo_id=BaseController.get10UID();
							PageData e=new PageData();
							e.put("tihuo_id", tihuo_id);
							e=ServiceHelper.getPayapp_historyService().tihuoByOrderId(e);
							if(e==null){
								istihuo=false;
								pd.put("startdate", DateUtil.getTime());
								String time=DateUtil.getAfterTimeDate(DateUtil.getTime(),Const.endnumberdate);
								pd.put("enddate", time);
								//设置定时器
								long l1=DateUtil.fomatDate1(DateUtil.getTime()).getTime();
								long l2=DateUtil.fomatDate1(time).getTime();
								TihuoTask th=new TihuoTask(tihuo_id);
								Timer tt=new Timer();
								tt.schedule(th, l2-l1);
								//----------------------
								pd.put("tihuo_id", tihuo_id);
 								String id=tihuo_id.substring(0, 2)+"  "+tihuo_id.substring(2, 6)+"  "+tihuo_id.substring(6, 10);
								pd.put("id", id);
							}
							e=null;
				}
//				//System.out.println("==============================新增订单关联的商品"); 
				if(allgoodsid != null && !allgoodsid.equals("")){
					String[] allgoods=allgoodsid.split(",");
					PageData gpd=null;
					String[] goodsstr=null;
					for(int i=0 ; i< allgoods.length ; i++){
							gpd=new PageData();
							goodsstr=allgoods[i].split("@");
							gpd.put("goods_id", goodsstr[0]);
							gpd.put("shop_number", goodsstr[1]);
							gpd.put("price", goodsstr[2]);
							gpd.put("order_id", order_id);
							gpd.put("goods_type", "1");
							ServiceHelper.getAppOrderService().saveOrderGoods(gpd);
							gpd=null;
							goodsstr=null;
					}
				}
				if(lastpay_money == 0){
						//支付完成完成
						pd.put("order_status", "1");
						pd.put("tihuo_status", "0"); 
						ServiceHelper.getAppOrderService().saveOrder(pd);
						//新增会员历史记录以及总后台记录
						TongYong.saveMemberHistory(pd, mpd, spd);
  				    	//发送推送
	    				//System.out.println("推送别名=="+store_id);
	    				TongYong.sendTuiSong(store_id, order_id, "3", store_id, "1", sale_money,"");
				}else{
						pd.put("order_status", "0");//待确认
						pd.put("tihuo_status", "0"); 
						ServiceHelper.getAppOrderService().saveOrder(pd);
  				}
   			} 
			//处理订单结束
			//System.out.println("chuliOrder处理订单结束==");
 		} catch (Exception e) {
			// TODO: handle exception
 			message=e.toString();
 			result="0";
 			(new TongYong()).dayinerro(e);
		}
		returnpd.put("result", result);
		returnpd.put("message", message);
		returnpd.put("data", pd);
    	return returnpd;
	}
	
	
	
 
	/**
	 * 提货后或是支付的最后操作阶段
	 * @param pd           订单详情
	 * @param channel      nowpay-线下现金支付，alipay-支付宝支付，wx-微信支付
	 * @param comeon_type  1-收银，2-优惠买单，3-提货卷 ,4-购买一元夺宝 ， 5-优选提货券
	 * @param apptype      b-b端，c-c端，pc网页端
	 * @param pay_type     1-收银，2-优惠买单，3-提货卷 ,4-购买一元夺宝 
	 * @param iszeroorder  true-是0元订单，false-不是0元订单
	 * @return:
	 * @throws Exception
	 */
  	public synchronized static boolean historyByOrder(PageData pd,String channel,String comeon_type,String apptype,boolean iszeroorder) throws Exception{
       		boolean flag=true;
     		try{ 
	 			if(pd == null){
					return false;
				}
	 			//System.out.println("进来支付的订单"+pd.getString("order_id")+"======="+pd.toString()+"======");
// 	 			String store_renmai_money=pd.getString("store_renmai_money");
//	 			String store_id=pd.getString("store_id");
	 			String order_id=pd.getString("order_id");
	 			String in_jiqi=pd.getString("in_jiqi");
//	 			String money_pay_type=pd.getString("money_pay_type");
//	  			String redpackage_id=pd.getString("redpackage_id");//红包ID
		       	String store_redpackets_id=pd.getString("store_redpackets_id");//赠送红包ID
		       	String sale_money=pd.getString("sale_money");//消费的总金额
		       	String actual_money=pd.getString("actual_money");//支付的金额（现金或者第三方）
		       	String user_balance=pd.getString("user_balance");//使用的余额
		       	String user_integral=pd.getString("user_integral");//使用的积分
		       	String get_integral=pd.getString("get_integral");//获得的积分
		       	String discount_money=pd.getString("discount_money"); 
				String no_discount_money=pd.getString("no_discount_money"); 
				String order_status=pd.getString("order_status"); 
				if(order_status == null || order_status.equals("")){
					order_status="0";
					pd.put("order_status", order_status);
 		       	}
				if(get_integral == null || get_integral.equals("")){
					get_integral="0";
 					pd.put("get_integral", get_integral);
 				}
				if(user_integral == null || user_integral.equals("")){
					user_integral="0";
					pd.put("user_integral", user_integral);
				}
				if(user_balance == null || user_balance.equals("")){
					user_balance="0";
					pd.put("user_balance", user_balance);
				}
				if(discount_money == null || discount_money.equals("")){
					discount_money="0";
					pd.put("discount_money", discount_money);
				}
				if(no_discount_money == null || no_discount_money.equals("")){
					no_discount_money="0";
					pd.put("no_discount_money", no_discount_money);
				}
	          	PageData mpd=new PageData();
	       		mpd=ServiceHelper.getAppMemberService().findById(pd);//用户详情
	       		if(mpd == null){
	       			return false;
	       		}
		       	PageData spd=new PageData();
	       		spd=ServiceHelper.getAppStoreService().findById(pd);//商家详情
	       		if(spd == null && !comeon_type.equals("4")){
	       			return false;
	       		}
	       		//操作comeon_type类型：1-收银，2-优惠买单，3-提货卷 ,4-购买一元夺宝 ， 5-优选提货券
				if( pd.getString("store_operator_id") == null ||  pd.getString("store_operator_id").equals("") ){
					pd.put("store_operator_id", "jy"+pd.getString("store_id"));
				}
	       		//通用的PageDATA（);
				PageData moneypd=new PageData();
				double no_youhui_money=Double.parseDouble(no_discount_money);
				double acmoney=Double.parseDouble(actual_money);
				double user_jifen=Double.parseDouble(user_integral);
				double user_yue=Double.parseDouble(user_balance);
				boolean isTihuo=(!comeon_type.equals("3") && !comeon_type.equals("5") && iszeroorder);
 				if(isTihuo){//不包括提货券
					saveMemberHistory(pd, mpd, spd);//新增会员的历史记录，删除会员使用的红包
				} 
    			double membershouyi_jifen=Double.parseDouble(get_integral);
 				if(membershouyi_jifen >0 ){
     				pd=renmaiJf(pd,spd,mpd, membershouyi_jifen);//新增用户积分获赠记录,人脉积分获赠记录
   				}
 				String now_wealth=ServiceHelper.getAppStoreService().sumStoreWealth(spd);//统计商家财富
				moneypd.clear();
				if(comeon_type.equals("1")){ 
   					moneypd.put("now_wealth", df2.format(Double.parseDouble(now_wealth)+user_jifen+user_yue));//n-会员使用积分，m-会员使用余额
  					moneypd.put("store_id", spd.getString("store_id"));
 					ServiceHelper.getAppStoreService().editWealthById(moneypd);
 				}else{
   					moneypd.put("now_wealth", df2.format(Double.parseDouble(now_wealth)+user_jifen+user_yue+acmoney));//n-会员使用积分，m-会员使用余额，acmoney-结余支付金额
  					moneypd.put("store_id", spd.getString("store_id"));
 					ServiceHelper.getAppStoreService().editWealthById(moneypd);
   				}
  		   		//处理是否使用交易扣点来进行的商家
  				PageData tranpd=transaction_points(spd, pd,(user_jifen+user_yue+acmoney));
  				String orderfuwu_money=tranpd.getString("orderfuwu_money");
  				String ordersp_getmoney=tranpd.getString("ordersp_getmoney");
  				String transaction_points=tranpd.getString("transaction_points");
 		   		//===
  				moneypd.clear();
				moneypd.put("wealth_type", "1");
	   			moneypd.put("profit_type", "3");
	   			moneypd.put("arrivalMoney", df2.format(acmoney+user_jifen+user_yue));
	   			moneypd.put("number",  df2.format(acmoney+user_jifen+user_yue));
	   			moneypd.put("store_id", spd.getString("store_id"));
	   			moneypd.put("pay_type", channel);
	   			moneypd.put("store_operator_id", pd.getString("store_operator_id"));
	   			moneypd.put("process_status", "1");
	   			moneypd.put("in_jiqi", in_jiqi);
	   			moneypd.put("jiaoyi_id",order_id);
	   			moneypd.put("sp_getmoney",pd.getString("sp_getmoney"));
	   			moneypd.put("send_jf","0");
   	   			moneypd.put("user_id", mpd.getString("member_id"));
   	   			moneypd.put("orderfuwu_money",orderfuwu_money);
   	   			moneypd.put("ordersp_getmoney", ordersp_getmoney);
   	   			moneypd.put("transaction_points", transaction_points);
	   			moneypd.put("last_wealth", ServiceHelper.getAppStoreService().sumStoreWealth(spd));
	   			moneypd.put("store_wealthhistory_id",order_id);
 	   			ServiceHelper.getAppStoreService().saveWealthhistory(moneypd);
				if(store_redpackets_id != null && !store_redpackets_id.equals("")){
					 String[] str=store_redpackets_id.split(",");
 					 for (int i = 0; i < str.length; i++) {
									pd.put("store_redpackets_id", str[i]);
 									PageData redpd=ServiceHelper.getAppStore_redpacketsService().findRedById(pd);
					 				if(redpd != null){
								 					String redpackage_type="";
								 					String redpackage_content="";
													if(redpd.getString("redpackage_type").equals("1")){//现金
														redpackage_type="21";
														redpackage_content=redpd.getString("srp_usercondition_content")+"减"+redpd.getString("money")+"元";
													}else if(redpd.getString("redpackage_type").equals("2")){//折扣
														redpackage_type="22";
														redpackage_content=redpd.getString("srp_usercondition_content")+"打"+redpd.getString("money")+"折";
													}
													PageData e2=new PageData();
													e2.put("redpackage_id",BaseController.getTimeID());
													e2.put("member_id", pd.getString("member_id"));
													e2.put("redpackage_money", redpd.getString("money"));
													e2.put("redpackage_content", redpackage_content);
													e2.put("store_redpackets_id", redpd.getString("store_redpackets_id"));
													e2.put("redpackage_type", redpackage_type);
													e2.put("enddate", redpd.get("endtime").toString());
													e2.put("startdate", redpd.get("starttime").toString());
													e2.put("set_id", redpd.getString("store_id"));
													e2.put("set_type", "1");
													ServiceHelper.getAppMemberService().saveRedForMember(e2);//新增红包信息至会员
													ServiceHelper.getAppMemberService().updateMemberRedNumber(e2);
													e2=null;
					 					}
					 				 
					}
  				}
    			pd.put("channel",channel);
    			//comeon_type 1-收银，2-优惠买单，3-提货卷 ,4-购买一元夺宝 ,5-优选
   				 if(comeon_type.equals("1") || (isTihuo && acmoney == 0)){
 	    			pd.put("order_status", "1");
	    			pd.put("tihuo_status", "1");
 	  				ServiceHelper.getAppOrderService().saveOrder(pd);//新增订单信息支付金额为0或是收银订单
   				 }else if(comeon_type.equals("2")){
    				pd.put("order_status", "1");
   					pd.put("tihuo_status", "1");
    				ServiceHelper.getPayapp_historyService().editOrderStatus(pd);//更新提货状态记录
   				 }else if(comeon_type.equals("3")){
    					pd.put("order_status", "1");
   					pd.put("tihuo_status", "1");
    				ServiceHelper.getPayapp_historyService().editOrderStatus(pd);//更新提货券状态
 			   		PageData wapd=new PageData();
			   		wapd.put("waterrecord_id", order_id);
			   		wapd.put("pay_status", "1");
			   		ServiceHelper.getWaterRecordService().editWaterRecord(wapd);
			   		wapd=null;
   				 }else if(comeon_type.equals("4")){//一元购
     				pd.put("order_status", "1");
    				pd.put("tihuo_status", "1");
     				ServiceHelper.getPayapp_historyService().editOrderStatus(pd);//更新提货状态记录
    			 }else if(comeon_type.equals("5")){//优选
      				pd.put("order_status", "1");
     				pd.put("tihuo_status", "1");
      				ServiceHelper.getPayapp_historyService().editOrderStatus(pd);//更新提货状态记录
 			   		PageData wapd=new PageData();
			   		wapd.put("waterrecord_id", order_id);
			   		wapd.put("pay_status", "1");
			   		ServiceHelper.getWaterRecordService().editWaterRecord(wapd);
			   		wapd=null;
     			}

				PageData over_pd=new PageData();
				over_pd.put("member_id", mpd.getString("member_id"));
				over_pd.put("store_id", spd.getString("store_id"));
 				YingXiao(pd);//更新商家营销记录表
 	   	   		over_pd.put("salemoney", df2.format(acmoney+user_yue+user_jifen));
	   	   		ServiceHelper.getAppMemberService().updateMemberById(over_pd); //更新会员个人消费次数、消费金额信息
 	   	   		double zengcomplex_score=0;
				if( membershouyi_jifen<=5 ){
			   			zengcomplex_score=Double.parseDouble( Const.complexscore[4]);
		   		}else if(5< membershouyi_jifen  && membershouyi_jifen<= 10){
		   				zengcomplex_score=Double.parseDouble( Const.complexscore[5]);
		   		}else if(10< membershouyi_jifen  && membershouyi_jifen<= 30){
		   				zengcomplex_score=Double.parseDouble( Const.complexscore[6]);
		   		}else if(30< membershouyi_jifen  && membershouyi_jifen<= 100){
		   				zengcomplex_score=Double.parseDouble( Const.complexscore[7]);
		   		}else if(100< membershouyi_jifen){
		   				zengcomplex_score=Double.parseDouble( Const.complexscore[8]);
		   		}
	   	   		double complex_score=Double.parseDouble(spd.getString("complex_score"))+Double.parseDouble(Const.complexscore[1])+zengcomplex_score;
	   	   		complex_scoreAdd(over_pd.getString("store_id"),df0.format(complex_score));//更新商家的综合分值
  	   			over_pd.put("transaction_times", "1");
 	   			ServiceHelper.getAppStoreService().edit(over_pd);//更新商家的订单交易次数
 	  			over_pd.put("sale_money", sale_money);
	  			ServiceHelper.getAppMemberService().updateStoreVipById(over_pd);//更新会员指定商家的vip内容
 	  			charm_numberAdd(over_pd.getString("member_id"), Const.charm_number[5]); //新增会员的魅力值
   				//针对提货券
      			if(!isTihuo){ 
  					//获取商品信息
					List<PageData> goodsList=ServiceHelper.getAppGoodsService().getGoodsIdByOrder(pd);
					PageData gpd=new PageData();
					PageData goodspd=null;
					int goodsListlength=goodsList.size();
					for (int i = 0; i <goodsListlength; i++) {
						goodspd=goodsList.get(i);
						if(goodspd.getString("goods_type").equals("2")){
							gpd.put("ok_number", goodspd.getString("shop_number"));
							gpd.put("youxuangg_id", goodspd.getString("goods_id"));
							ServiceHelper.getYouXuanService().updateYouXuanGoodsBuyNumber(gpd);
						}else{
							gpd.put("ok_number", goodspd.getString("shop_number"));
							gpd.put("goods_id", goodspd.getString("goods_id"));
							ServiceHelper.getAppGoodsService().updateGoodsBuyNumber(gpd);
							ServiceHelper.getAppGoodsService().updateGoodsConsumption_number(gpd);//更新当前商品的销售数量
						}
						goodspd.put("member_id", pd.getString("member_id"));
						//清空购物车
						ServiceHelper.getShopCarService().delShop(goodspd);
						gpd.clear();
 					}
					goodspd=null;
					gpd=null;
      			}
       			if(isTihuo){//不包括提货券
      				moneypd.clear();
      				moneypd.put("pay_status","1");
      				moneypd.put("waterrecord_id", order_id);
      				moneypd.put("user_id", pd.getString("member_id"));
      				moneypd.put("user_type", "2");
      				moneypd.put("withdraw_rate","0");
      				moneypd.put("money_type","2");
      				moneypd.put("money", sale_money);
      				moneypd.put("reduce_money",discount_money);
      				moneypd.put("arrivalmoney", df2.format(Double.parseDouble(sale_money)-Double.parseDouble(discount_money)));
      				moneypd.put("nowuser_money", mpd.getString("now_money"));
      				moneypd.put("application_channel", in_jiqi ); 
   					if(pd.getString("channel").contains("alipay")){
   						moneypd.put("remittance_type","3" );
   						moneypd.put("alipay_money",actual_money );
   						moneypd.put("remittance_name",Const.payjiqi[3] );
   					}else if(pd.getString("channel").contains("wx")){
   						moneypd.put("remittance_type","4" );
   						moneypd.put("wx_money",actual_money );
   						moneypd.put("remittance_name",Const.payjiqi[4] );
   					}else if(pd.getString("channel").contains("nowpay")){
   						moneypd.put("remittance_type","2" );
   						moneypd.put("nowypay_money",actual_money );
   						if(in_jiqi.equals("1")){
   							moneypd.put("remittance_name",Const.payjiqi[0] );
   						}else if(in_jiqi.equals("4")){
   							moneypd.put("remittance_name",Const.payjiqi[6] );
   						}else if(in_jiqi.equals("2")){
   							moneypd.put("remittance_name",Const.payjiqi[2] );
   						}else if(in_jiqi.equals("5")){
   							moneypd.put("remittance_name",Const.payjiqi[9] );
   						}
   					}else if(pd.getString("channel").contains("pple")){
   						moneypd.put("remittance_type","5" );
   						moneypd.put("apple_money",actual_money );
   						moneypd.put("remittance_name",Const.payjiqi[5] );
   					}else{
   						moneypd.put("remittance_type","5" );
   						moneypd.put("bank_money",actual_money);
   						moneypd.put("remittance_name",Const.payjiqi[1] );
   					}
   					moneypd.put("integral_money",user_integral); 
   					moneypd.put("balance_money",user_balance); 
   					moneypd.put("remittance_number",mpd.getString("phone"));//支付人的支付账号
   					moneypd.put("createtime",DateUtil.getTime());
   					moneypd.put("over_time",DateUtil.getTime());
   					moneypd.put("jiaoyi_type",comeon_type);
   					moneypd.put("payee_number",spd.getString("store_id"));
   					moneypd.put("order_tn", pd.getString("order_tn"));
   					moneypd.put("province_name", mpd.getString("province_name"));
   					moneypd.put("city_name", mpd.getString("city_name"));
   					moneypd.put("area_name", mpd.getString("area_name"));
    				ServiceHelper.getWaterRecordService().saveWaterRecord(moneypd);
        		}
      			pd=null;
      			moneypd=null;
      			//System.out.println("historyByOrder处理订单结束");
  		} catch(Exception e){
 			flag=false;
 			//System.out.println(e.toString()+"historyByOrder处理订单");
 			(new TongYong()).dayinerro(e);
 			ServiceHelper.getAppPcdService().saveLog(pd.getString("order_id"), e.toString()+"第三方支付===========================","1");
		}
 		return flag;
	}
  	
  	
  	/**
  	 * 新增会员订单的历史记录
  	 * @param orderpd
  	 * @param mpd
  	 * @param spd
  	 * @param user_balance
  	 * @param user_integral
  	 * @throws Exception
  	 */
  	public synchronized static void saveMemberHistory(PageData orderpd,PageData mpd,PageData spd) throws Exception{
  		PageData moneypd=new PageData();
  		try {
  			String pay_type=orderpd.getString("pay_type");
  			double user_balance=Double.parseDouble(orderpd.getString("user_balance"));
  			double user_integral=Double.parseDouble(orderpd.getString("user_integral"));
  			double now_money=Double.parseDouble(ServiceHelper.getAppMemberService().findBalance(mpd));
  			if(user_balance >0){
  					    	//System.err.println("更新会员余额消费次数===========================");
  					    	moneypd.put("pay_way", "nowmoney");
  					    	moneypd.put("member_id", mpd.getString("member_id"));
			   				ServiceHelper.getAppMemberService().updateMemberById(moneypd);  
			   				moneypd.clear();
 			   				//会员个人余额消费历史
			   				now_money=now_money-user_balance;
							moneypd.put("member_id", mpd.getString("member_id"));
		 					moneypd.put("wealth_type", "2");
							moneypd.put("consume_type", "2");
							moneypd.put("content",  spd.getString("store_name")+"消费抵用");
							moneypd.put("number", df2.format(-user_balance));
							moneypd.put("now_money", df2.format(now_money));
							moneypd.put("jiaoyi_id", orderpd.getString("order_id"));
							moneypd.put("jiaoyi_status", "1");
							moneypd.put("member_wealthhistory_id", BaseController.getXFUID(mpd.getString("show_lookid")));
 							ServiceHelper.getAppMember_wealthhistoryService().saveWealthhistory(moneypd);
							moneypd.clear();
							//更新金钱
							moneypd.put("now_money", df2.format(now_money));
							moneypd.put("member_id", mpd.getString("member_id"));
							ServiceHelper.getAppMemberService().edit(moneypd);
 			}
  			moneypd=null;
   			moneypd=new PageData();
			double now_integral=Double.parseDouble(mpd.getString("now_integral"));
 			if(user_integral  > 0){
  				 			now_integral=now_integral-user_integral;
					 		//System.err.println("更新会员积分消费次数===========================");
					 		moneypd.put("pay_way", "integralmoney");
					 		moneypd.put("member_id",mpd.getString("member_id"));
						  	ServiceHelper.getAppMemberService().updateMemberById(moneypd); 
						  	moneypd.clear();
 							//新增个人积分消费历史
							moneypd.put("wealth_type", "1");
							moneypd.put("consume_type", "2");
							moneypd.put("member_id",mpd.getString("member_id"));
							moneypd.put("content", spd.getString("store_name")+"消费抵用");
							moneypd.put("number", df2.format(-user_integral));
							moneypd.put("now_money", df2.format(now_integral));
							moneypd.put("jiaoyi_id", orderpd.getString("order_id"));
							moneypd.put("jiaoyi_status", "1");
							moneypd.put("member_wealthhistory_id",BaseController.getXFUID(mpd.getString("show_lookid")));
							ServiceHelper.getAppMember_wealthhistoryService().saveWealthhistory(moneypd);
							moneypd.clear();
							//更新积分
		  					moneypd.put("member_id", mpd.getString("member_id"));
							moneypd.put("now_integral", df2.format(now_integral));
							ServiceHelper.getAppMemberService().edit(moneypd);
 			}
 			//红包ID
	   		if(orderpd.getString("redpackage_id") != null && !orderpd.getString("redpackage_id").equals("")){
	   					orderpd.put("isshiyong", "1");
						ServiceHelper.getAppMember_redpacketsService().editRedStatus(orderpd);
						//System.out.println("更新红包数量--》更新会员红包数量=====================================");
						ServiceHelper.getAppMemberService().updateMemberRedNumber(mpd);
			}
	   		//1-收银，2-优惠买单，3-提货卷 ,4-购买一元夺宝  
	   		if(pay_type != null && pay_type.equals("3")){
	   				PageData waterpd=new PageData();
	   				waterpd.put("pay_status","97");
	   				waterpd.put("waterrecord_id", orderpd.getString("order_id"));
					waterpd.put("user_id", mpd.getString("member_id"));
					waterpd.put("user_type", "2");
					waterpd.put("withdraw_rate","0");
					waterpd.put("money_type","2");
	 				waterpd.put("money", orderpd.getString("sale_money"));
	 				waterpd.put("reduce_money",orderpd.getString("discount_money"));
					waterpd.put("arrivalmoney",  orderpd.getString("discount_after_money"));
					waterpd.put("nowuser_money", ServiceHelper.getAppMemberService().findBalance(mpd));
					waterpd.put("application_channel", orderpd.getString("in_jiqi")); 
					if(orderpd.getString("pay_way").contains("alipay")){
						waterpd.put("remittance_type","3" );
						waterpd.put("alipay_money",orderpd.getString("actual_money") );
						waterpd.put("remittance_name",Const.payjiqi[3] );
					}else if(orderpd.getString("pay_way").contains("wx")){
						waterpd.put("remittance_type","4" );
						waterpd.put("wx_money",orderpd.getString("actual_money") );
						waterpd.put("remittance_name",Const.payjiqi[4] );
					}else if(orderpd.getString("pay_way").contains("nowpay")){
						waterpd.put("remittance_type","2" );
						waterpd.put("nowypay_money",orderpd.getString("actual_money"));
						waterpd.put("remittance_name",Const.payjiqi[10] );
					}else if(orderpd.getString("pay_way").contains("pple")){
						waterpd.put("remittance_type","5" );
						waterpd.put("apple_money",orderpd.getString("actual_money"));
						waterpd.put("remittance_name",Const.payjiqi[5] );
					}else{
						waterpd.put("remittance_type","5" );
						waterpd.put("bank_money",orderpd.getString("actual_money"));
					waterpd.put("remittance_name",Const.payjiqi[1] );
					}
					waterpd.put("integral_money",orderpd.getString("user_integral")); 
					waterpd.put("balance_money",orderpd.getString("user_balance")); 
					waterpd.put("remittance_number",mpd.getString("phone"));//支付人的支付账号
					waterpd.put("createtime",DateUtil.getTime());
					waterpd.put("over_time",DateUtil.getTime());
					waterpd.put("jiaoyi_type","3");
					waterpd.put("payee_number",orderpd.getString("store_id"));
					waterpd.put("order_tn", orderpd.getString("store_id"));
					waterpd.put("province_name", mpd.getString("province_name"));
					waterpd.put("city_name", mpd.getString("city_name"));
					waterpd.put("area_name", mpd.getString("area_name"));
					ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
					waterpd=null;
	   		}
		} catch (Exception e) {
			// TODO: handle exception
			(new TongYong()).dayinerro(e);
 			ServiceHelper.getAppPcdService().saveLog(orderpd.getString("order_id"), e.toString()+"会员历史记录","11");
		}
  	}
  	
  	
  	
  	
  	
  	
  	/**
  	 * 处理人脉积分收益
  	 * @param order_id
  	 * @param spd
  	 * @param zenjf
  	 * @return
  	 * @throws Exception 
  	 */
  	public synchronized static PageData  renmaiJf(PageData orderpd,PageData spd,PageData mpd,double zenjf) throws Exception{
    	double xtmoney=0;
  		double lessmoneyxtmoney=0;
//  	double sp_getmoney=0;
     	try {
     			PageData moneypd=new PageData();
      			//获赠积分推送通知
     			TongYong.sendTuiSong(mpd.getString("member_id"), orderpd.getString("order_id"), "7", mpd.getString("member_id"), "2", spd.getString("store_name"),df2.format(zenjf));
				//会员获赠积分记录
 				double nowmemberIntegrale=Double.parseDouble(ServiceHelper.getAppMemberService().findIntegrale(mpd));
 				nowmemberIntegrale=nowmemberIntegrale+zenjf;
  	 			moneypd.put("member_id", mpd.getString("member_id"));
				moneypd.put("now_integral", df2.format(nowmemberIntegrale));
				ServiceHelper.getAppMemberService().edit(moneypd);
				moneypd.clear();
				//会员财务记录
				moneypd.put("wealth_type", "1");
				moneypd.put("consume_type", "1");
				moneypd.put("content", spd.getString("store_name")+" 消费获赠");
				moneypd.put("number", df2.format(zenjf));
				moneypd.put("member_id", mpd.getString("member_id"));
				moneypd.put("now_money", df2.format(nowmemberIntegrale));
				moneypd.put("jiaoyi_id", orderpd.getString("order_id"));
				moneypd.put("jiaoyi_status", "1");
				moneypd.put("member_wealthhistory_id",BaseController.getXFUID(mpd.getString("show_lookid")));
				ServiceHelper.getAppMember_wealthhistoryService().saveWealthhistory(moneypd);
				moneypd=null;
	   			moneypd=new PageData();
				//商家修改财务（减赠送积分）
 		   		double now_wealth=Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(spd))-zenjf;
 		   		moneypd.put("now_wealth", df2.format(now_wealth));
 		   		moneypd.put("store_id", spd.getString("store_id"));
 		   		ServiceHelper.getAppStoreService().editWealthById(moneypd);
 		   		//处理人脉收益
 		   		xtmoney=zenjf*Const.orderShouyiMoney[0];//系统获取的积分
 		   		lessmoneyxtmoney=xtmoney;//结算最后给子公司的钱
 		   		orderpd.put("sendxitong_integral", df2.format(xtmoney));
 		   		if(xtmoney >0){
 					//减少商家返系统积分
  		   			now_wealth=now_wealth-xtmoney;
		   			moneypd.put("now_wealth", df2.format(now_wealth));
		   			moneypd.put("store_id", spd.getString("store_id"));
		   			ServiceHelper.getAppStoreService().editWealthById(moneypd);
		   			moneypd=null;
		   			moneypd=new PageData();
//		   			//新增商家财务记录（减返系统积分）
//			   		moneypd=null;
//					moneypd=new PageData();
//					moneypd.put("wealth_type", "1");
//					moneypd.put("profit_type", "11");
//					moneypd.put("arrivalMoney", df2.format(-xtmoney));
//					moneypd.put("number",   df2.format(-xtmoney));
//					moneypd.put("store_id", spd.getString("store_id"));
//					moneypd.put("pay_type", channel);
//					moneypd.put("store_operator_id", pd.getString("store_operator_id"));
//					moneypd.put("process_status", "1");
//					moneypd.put("in_jiqi", in_jiqi);
//					moneypd.put("jiaoyi_id",order_id);
//					moneypd.put("sp_money","");
//					moneypd.put("user_id", mpd.getString("member_id"));
//					moneypd.put("last_wealth", ServiceHelper.getAppStoreService().sumStoreWealth(spd));
//					moneypd.put("store_wealthhistory_id","FXT"+order_id);
//	 	   			ServiceHelper.getAppStoreService().saveWealthhistory(moneypd);
// 			   		sp_getmoney=xtmoney*Const.orderShouyiMoney[2];//服务商积分获益
// 			   		orderpd.put("sp_file_id", spd.getString("sp_file_id"));
// 			   		orderpd.put("sp_getmoney", df2.format(sp_getmoney));
// 			   		double nowmoney=0;
//			   		if( sp_getmoney >0 && ServiceHelper.getSp_fileService().findById(spd) != null){
//			   				PageData sppd=ServiceHelper.getSp_fileService().findById(spd);//商家对应的服务商
//			   				nowmoney=Double.parseDouble(sppd.getString("nowmoney"));
//	            		    nowmoney+=xtmoney*Const.orderShouyiMoney[2];
//		            	    sppd.put("nowmoney", df2.format(nowmoney));
//		            	    ServiceHelper.getSp_fileService().edit(sppd);
//		            	    lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[2];
//		            	    //服务商财务记录
//		            	    moneypd=null;
//	 	   					moneypd=new PageData();
//	 	   					moneypd.put("yewu_id", spd.getString("store_id"));//业务对象
//	 	   					moneypd.put("yewu_type", "1");//业务对象
//	 	   					moneypd.put("money",  df2.format(sp_getmoney));//金额
//	 	   					moneypd.put("money_type", "2");//1、销售提成: 2、积分收益： 3、平台奖励 
//	 	   					moneypd.put("operate_type", "1"); //1-服务商，2-业务员
//	 	   					moneypd.put("operate_id", sppd.getString("sp_file_id")); 
//	 	   					moneypd.put("isshouyi", "0");//0-收益，1-消费
//	 	   					moneypd.put("isjihuo", "1");//0-未激活，1-已激活
//	 	   					moneypd.put("now_balance", df2.format(nowmoney));//当前余额
//	 	   					moneypd.put("service_performance_id", orderpd.getString("order_id"));
//		 	 				ServiceHelper.getService_performanceService().save(moneypd);
//         	   }
         	   String recommended_type=mpd.getString("recommended_type");
        	   String recommended=mpd.getString("recommended");
        	   double onerenmaishouyi=xtmoney*Const.orderShouyiMoney[3];
          	   if(recommended_type.equals("1") && onerenmaishouyi >0){//商家的积分
          		   lessmoneyxtmoney=lessmoneyxtmoney-onerenmaishouyi;
          		    orderpd.put("onecontacts_id", recommended);
         		    orderpd.put("onecontacts_type", "1");
         		    orderpd.put("onecontacts_getmoney", df2.format(onerenmaishouyi));
         		    orderpd.put("twocontacts_id", "0");
         		    orderpd.put("twocontacts_type", "0");
         		    orderpd.put("twocontacts_getmoney", "0");
	            	if(recommended.equals(spd.getString("store_id"))){//如果收益的是当前商家则记录
	            		orderpd.put("store_renmai_money", df2.format(onerenmaishouyi));
 			    	}
//	            	//新增商家人脉收益积分
//	            	moneypd=null;
//	 	   			moneypd=new PageData();
//	 	   			moneypd.put("store_id", recommended);
// 	 	   			double _symoney=Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(moneypd))+onerenmaishouyi;
//		            moneypd.put("now_wealth", df2.format(_symoney));
//	 			   	ServiceHelper.getAppStoreService().editWealthById(moneypd);
//		 			//新增商家人脉收益积分记录
//				   	moneypd=null;
//   					moneypd=new PageData();
//   					moneypd.put("wealth_type", "1");
//   					moneypd.put("profit_type", "12");
//   					moneypd.put("arrivalMoney", df2.format(onerenmaishouyi));
//   					moneypd.put("number",   df2.format(onerenmaishouyi));
//   					moneypd.put("store_id", spd.getString("store_id"));
//   					moneypd.put("pay_type", channel);
//   					moneypd.put("store_operator_id", pd.getString("store_operator_id"));
//   					moneypd.put("process_status", "1");
//   					moneypd.put("in_jiqi", in_jiqi);
//   					moneypd.put("jiaoyi_id",order_id);
//   					moneypd.put("sp_money","");
//   					moneypd.put("user_id", mpd.getString("member_id"));
//   					moneypd.put("last_wealth", ServiceHelper.getAppStoreService().sumStoreWealth(spd));
//   					moneypd.put("store_wealthhistory_id","RM"+order_id);
//	 	   			ServiceHelper.getAppStoreService().saveWealthhistory(moneypd);
	             }else if(recommended_type.equals("2") && onerenmaishouyi >0){
	            	 		lessmoneyxtmoney=lessmoneyxtmoney-onerenmaishouyi;
 			            	 orderpd.put("onecontacts_id", recommended);
			            	 orderpd.put("onecontacts_type", "2");
			            	 orderpd.put("onecontacts_getmoney", df2.format(onerenmaishouyi));
	            		    //更新会员积分
	            		    if(onerenmaishouyi >0){
            			   	moneypd=null;
		   					moneypd=new PageData();
		   					moneypd.put("member_id", recommended);
		   					moneypd=ServiceHelper.getAppMemberService().findById(moneypd);//会员详情
            		    	double member_nowmongy=Double.parseDouble(moneypd.getString("now_integral"))+onerenmaishouyi;
	            		    double onecontact_integral=Double.parseDouble(moneypd.getString("onecontactintegral"))+onerenmaishouyi;
	            		    moneypd.put("now_integral", df2.format(member_nowmongy));
	            		    moneypd.put("onecontactintegral", df2.format(onecontact_integral));
	   						ServiceHelper.getAppMemberService().edit(moneypd);
	   					    //新增会员积分支出历史
	   						moneypd.put("wealth_type", "1");//1-积分（送/增），2-金钱（充值提现）
	   						moneypd.put("consume_type", "1");//获赠1-收益（提现，获赠），支出2-消费（充值余额/送积分）
	   						moneypd.put("content", "一度人脉消费获益");
	   						moneypd.put("number", df2.format(onerenmaishouyi));
	   						moneypd.put("now_money", df2.format(member_nowmongy));
	   						moneypd.put("jiaoyi_id", orderpd.getString("order_id"));
	   						moneypd.put("jiaoyi_status", "1");
	   						moneypd.put("member_wealthhistory_id", BaseController.getXFUID(moneypd.getString("show_lookid")));
	   						ServiceHelper.getAppMember_wealthhistoryService().saveWealthhistory(moneypd);
	   							//开始处理二度人脉
	   							recommended_type=moneypd.getString("recommended_type");
			            	    recommended=moneypd.getString("recommended");
			            	    double tworenmaishouyi=xtmoney*Const.orderShouyiMoney[4];
 	 			            	if(recommended_type.equals("1") && tworenmaishouyi>0 ){
 	 			            		 	lessmoneyxtmoney=lessmoneyxtmoney-tworenmaishouyi;
 			            	    		orderpd.put("twocontacts_id", recommended);
			            	    		orderpd.put("twocontacts_type", "1");
			            	    		orderpd.put("twocontacts_getmoney", df2.format(tworenmaishouyi));
						            	if(recommended.equals(spd.getString("store_id"))){//如果收益的是当前商家则记录
						            		orderpd.put("store_renmai_money", df2.format(tworenmaishouyi));
					 			   	 	}
//						            	//新增商家人脉收益积分
//						            	moneypd=null;
//				 	 	   				moneypd=new PageData();
//				 	 	   				moneypd.put("store_id", recommended);
//					 	 	   			double _symoney=Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(moneypd))+tworenmaishouyi;
//							            moneypd.put("now_wealth", df2.format(_symoney));
//						 			   	ServiceHelper.getAppStoreService().editWealthById(moneypd);
//						 			    //新增商家人脉收益积分记录
//									   	moneypd=null;
//					   					moneypd=new PageData();
//					   					moneypd.put("wealth_type", "1");
//					   					moneypd.put("profit_type", "12");
//					   					moneypd.put("arrivalMoney", df2.format(tworenmaishouyi));
//					   					moneypd.put("number",   df2.format(tworenmaishouyi));
//					   					moneypd.put("store_id", spd.getString("store_id"));
//					   					moneypd.put("pay_type", channel);
//					   					moneypd.put("store_operator_id", pd.getString("store_operator_id"));
//					   					moneypd.put("process_status", "1");
//					   					moneypd.put("in_jiqi", in_jiqi);
//					   					moneypd.put("jiaoyi_id",order_id);
//					   					moneypd.put("sp_money","");
//					   					moneypd.put("user_id", mpd.getString("member_id"));
//					   					moneypd.put("last_wealth", ServiceHelper.getAppStoreService().sumStoreWealth(spd));
//					   					moneypd.put("store_wealthhistory_id","RM"+order_id);
//						 	   			ServiceHelper.getAppStoreService().saveWealthhistory(moneypd);
 			            	    }else if(recommended_type.equals("2") && tworenmaishouyi >0){
 			            	    	lessmoneyxtmoney=lessmoneyxtmoney-tworenmaishouyi;
 			            	    	orderpd.put("twocontacts_id", recommended);
 			            	    	orderpd.put("twocontacts_type", "1");
 			            	    	orderpd.put("twocontacts_getmoney", df2.format(tworenmaishouyi));
					            	moneypd=null;
				   					moneypd=new PageData();
				   					moneypd.put("member_id", recommended);
				   					moneypd=ServiceHelper.getAppMemberService().findById(moneypd);//会员详情
		            		    	double twomember_nowmongy=Double.parseDouble(moneypd.getString("now_integral"))+tworenmaishouyi;
			            		    double twoonecontact_integral=Double.parseDouble(moneypd.getString("onecontactintegral"))+tworenmaishouyi;
			            		    moneypd.put("now_integral", df2.format(twomember_nowmongy));
			            		    moneypd.put("onecontactintegral", df2.format(twoonecontact_integral));
			   						ServiceHelper.getAppMemberService().edit(moneypd);
			   					    //新增会员积分支出历史
			   						moneypd.put("wealth_type", "1");//1-积分（送/增），2-金钱（充值提现）
			   						moneypd.put("consume_type", "1");//获赠1-收益（提现，获赠），支出2-消费（充值余额/送积分）
			   						moneypd.put("content", "一度人脉消费获益");
			   						moneypd.put("number", df2.format(tworenmaishouyi));
			   						moneypd.put("now_money", df2.format(twomember_nowmongy));
			   						moneypd.put("jiaoyi_id", orderpd.getString("order_id"));
			   						moneypd.put("jiaoyi_status", "1");
			   						moneypd.put("member_wealthhistory_id", BaseController.getXFUID(moneypd.getString("show_lookid")));
			   						ServiceHelper.getAppMember_wealthhistoryService().saveWealthhistory(moneypd);
			            	    }
        		    }
        	    }
        	    if(lessmoneyxtmoney > 0 && ServiceHelper.getSp_fileService().findById(spd) != null){
            	       moneypd=null;
	   				   moneypd=ServiceHelper.getSubsidiaryService().findById(ServiceHelper.getSp_fileService().findById(spd));
	   				   if(moneypd != null){
	   					 double sub_nowmoney=Double.parseDouble(moneypd.getString("nowmoney"))+lessmoneyxtmoney;
	   					 moneypd.put("nowmoney", df2.format(sub_nowmoney));
	   					 ServiceHelper.getSubsidiaryService().edit(moneypd);
	   					 orderpd.put("subsidiary_id", moneypd.getString("subsidiary_id"));
		   			   }else{
		   				orderpd.put("subsidiary_id", "");
		   			   }
	   				   orderpd.put("subsidiary_getmoney", df2.format(lessmoneyxtmoney));
         	   }
			}
 		} catch (Exception e) {
			// TODO: handle exception
			(new TongYong()).dayinerro(e);
 			ServiceHelper.getAppPcdService().saveLog(orderpd.getString("order_id"), e.toString()+"赠送积分环节出错","11");
		}
  		return orderpd;
  	}
  	
  	
  	/**
  	 * 处理商家是否使用交易扣点的方式
  	 * @param spd
  	 * @param orderpd
  	 * @return
  	 * @throws Exception
  	 */
  	public synchronized static PageData transaction_points(PageData spd,PageData orderpd,double discount_after_paymoney) throws Exception{
    	double orderfuwu_money=0;
   		double ordersp_getmoney=0;
   		PageData moneypd=new PageData();
  		try {
  			String isopen_points=spd.getString("isopen_points");
			double transaction_points=Double.parseDouble(spd.getString("transaction_points"));
			if(isopen_points.equals("1")){//开启了交易扣费
					orderfuwu_money=discount_after_paymoney*(transaction_points/100);
					//处理商家余额
					double now_wealth=Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(spd));
					now_wealth=now_wealth-orderfuwu_money;
	 				moneypd.put("now_wealth", df2.format(now_wealth));
			   		moneypd.put("store_id", spd.getString("store_id"));
			   		ServiceHelper.getAppStoreService().editWealthById(moneypd);
			   		moneypd.remove("now_wealth");
			   		moneypd.remove("store_id");
			   		//更新服务商的余额以及添加记录
  					PageData cpd=ServiceHelper.getStore_fileService().getCityForName(spd);
					if(cpd != null){
						double service_provider_commission=Double.parseDouble(cpd.getString("service_provider_commission"));
						ordersp_getmoney=orderfuwu_money*(service_provider_commission/100);//服务商获取的费用
					 	PageData sppd=ServiceHelper.getSp_fileService().findById(spd);
				 		if(sppd != null && ordersp_getmoney >0 ){
				 			String now_balance=df2.format(Double.parseDouble(sppd.getString("nowmoney"))+ordersp_getmoney);
				 			sppd.put("nowmoney", now_balance);
					 		ServiceHelper.getSp_fileService().edit(sppd);
					 		//添加一个支付记录
		 	 				PageData mmpd=new PageData();
		 	 				mmpd.put("yewu_id", spd.getString("store_id"));//业务对象
			 	 			mmpd.put("yewu_type", "1");//业务对象
		 	 				mmpd.put("money", df2.format(ordersp_getmoney));//金额
		 	 				mmpd.put("money_type", "1");//1、销售提成: 2、积分收益： 3、平台奖励 
			 	 			mmpd.put("operate_type", "1"); //1-服务商，2-业务员
		 	 				mmpd.put("operate_id", sppd.getString("sp_file_id")); 
		 	 				mmpd.put("isshouyi", "0");//0-收益，1-消费
		 	 				mmpd.put("isjihuo", "1");//0-未激活，1-已激活
		 	 				mmpd.put("now_balance", now_balance);//余额
		 	 				mmpd.put("service_performance_id", orderpd.getString("order_id"));//收益对象
		 	 				ServiceHelper.getService_performanceService().save(mmpd);
		 	 				mmpd=null;
 				 		}
					}
 			}
		} catch (Exception e) {
			// TODO: handle exception
			(new TongYong()).dayinerro(e);
			ServiceHelper.getAppPcdService().saveLog(orderpd.getString("order_id"), e.toString()+"处理商家是否使用交易扣点的方式","12");
		}
  		moneypd.put("orderfuwu_money", df2.format(orderfuwu_money));
  		moneypd.put("ordersp_getmoney", df2.format(ordersp_getmoney));
  		moneypd.put("transaction_points", spd.getString("transaction_points"));
  		return moneypd;
  	}
  	
  	
  	
 
  	
  	
  	/**
  	 * 购买的优惠买单买单信息pd,
  	 * youhui_money可优惠的金额(除去不优惠的金额)，
  	 * notyouhui_money 不优惠金额，
   	 * pay_sort_type 买单类别：1-总金额（提货券/扫一扫优惠买单），2-类别买单
   	 * appname    在哪一个端member=C端，store=B端
  	 * allgoods   购买商品拼接： 1.（商品id@数量@总金额，商品id@数量@总金额） 2.非商品购买的时候传“”空字符串
  	 * allleibie  类别购买字段拼接：拆分类别 （类别ID@金钱@积分率@折扣率）
  	 * @return
  	 * 
  	 */
  	public synchronized static Map<String,Object> youhuimaidan(PageData pd,double youhui_money,double notyouhui_money){
   		Map<String,Object> map = new HashMap<String,Object>();
  		List<PageData> yingxiaoList=new ArrayList<PageData>();//用来存储营销List
  		try {
    		String allgoods=pd.getString("allgoods");
    		String allleibie=pd.getString("allleibie");
     		boolean goodsFlag=(allgoods != null  && !allgoods.equals(""));//是否为购物车购买
    		boolean leibieFlag=(allleibie != null  && !allleibie.equals(""));//是否为分类购买
    		//goodsFlag 和 leibieFlag 都为false的时候则为按总金额买单
   			//获取商家的营销规则明细
 			map.put("yxpd", markeingAll(pd));
 			double alljifeng=0;
 			if(leibieFlag){
    				//判断是否开通类别积分
  				if(ServiceHelper.getAppStorepc_marketingService().getJfById(pd) == null || !ServiceHelper.getAppStorepc_marketingService().getJfById(pd).getString("change_type").equals("2") ){
  					map.put("message", "暂未开通该通道");
  					return map;
				}
  				map.put("sortList", ServiceHelper.getAppGoodsService().listAllBigSort(pd));
  				//拆分类别（类别ID@金钱@积分率@折扣率）
				String alllei=pd.getString("allleibie");
  				if(alllei != null && !alllei.equals("") && alllei.contains("@")){
							String[] everylei=alllei.split(",");
							int everyleilength=everylei.length;
							for(int i=0;i<everyleilength ; i++){
  								  if(everylei[i].split("@")[1] != null && !everylei[i].split("@")[1].equals("") && !everylei[i].split("@")[1].equals("null")){
									  if(everylei[i].split("@")[2] != null && !everylei[i].split("@")[2].equals("null") && !everylei[i].split("@")[2].equals("")){
										  alljifeng+= Double.parseDouble(everylei[i].split("@")[1])*Double.parseDouble(everylei[i].split("@")[2])/100;
										  youhui_money+= Double.parseDouble(everylei[i].split("@")[1]);
									  }
								  }
  							}
				}
  			   //积分（获取被选中的积分设置）
				if(alljifeng >= 0){
					PageData jfpd=new PageData();
	 				jfpd.put("content", "分类赠送积分");
	 				jfpd.put("number", "+"+df2.format( alljifeng ));
	 				jfpd.put("type", "6");
	 				jfpd.put("id", ServiceHelper.getAppStorepc_marketingService().getJfById(pd).getString("store_scoreway_id"));
					yingxiaoList.add(jfpd);
					jfpd=null;
				}
  			}
   			//1.先获取营销中的折扣设置
  			PageData typepd=new PageData();
			typepd.put("marketing_type", "7");
			typepd.put("store_id", pd.getString("store_id"));
			List<PageData> zklist=ServiceHelper.getAppStorepc_marketingService().listAllById(typepd);
			String zkcontent="";
			double zkmoney=0;
			String zkid="";
 			int zklistlength=zklist.size();
			PageData e=null;
			for (int zi = 0; zi <zklistlength; zi++) {
 					e=zklist.get(zi);
// 					String marketing_type=e.getString("marketing_type");
					String grantrule=e.getString("grantrule");
					String marketing_id=e.getString("marketing_id");
					e.put("store_discountway_id", marketing_id);
					//获取所有启用的折扣
 					PageData zkpd=ServiceHelper.getAppStorepc_marketingService().getZKById(e);
					if(zkpd != null){
							String zkgrantrule=zkpd.getString("grantrule");
							if(zkpd.getString("discount_type").equals("1")){//整店折扣
 									double n=0;
									if(zkpd.getString("onealldiscount_rate").length() == 1){
										n=1-Double.parseDouble(zkpd.getString("onealldiscount_rate"))/10.0;
									}else if(zkpd.getString("onealldiscount_rate").length() == 2){
										n=1-Double.parseDouble(zkpd.getString("onealldiscount_rate"))/100;
									}else if(zkpd.getString("onealldiscount_rate").length() == 3){
										n=1-Double.parseDouble(zkpd.getString("onealldiscount_rate"))/1000;
									}
	 								double m=n*youhui_money;
	 								if(m >= zkmoney ){
	 									zkcontent=grantrule;
	 									zkmoney=m;
 	 									zkid=marketing_id;
	 								}
							}else if(zkpd.getString("discount_type").equals("4")){//满多少折多少
								String[] str=zkgrantrule.split(",");
								int strlength=str.length;
 	 							for(int i=0; i< strlength ;i++){ 
 	  		 								double n1=Double.parseDouble(str[i].substring(str[i].indexOf("满")+1, str[i].indexOf("元")));
	  		 								double n2=0;
											if(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")).length() == 1){
												n2=1-Double.parseDouble(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")))/10.0;
											}else if(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")).length() == 2){
												n2=1-Double.parseDouble(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")))/100;
											}else if(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")).length() == 3){
												n2=1-Double.parseDouble(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")))/1000;
											}
	  		 								if(n2*youhui_money >zkmoney && youhui_money >=n1){
	  		 									zkcontent=str[i];
			 									zkmoney=n2*youhui_money;
 			 									zkid=marketing_id;
	  		 								}
 	 							}
							}else if(zkpd.getString("discount_type").equals("2") && goodsFlag ){//按类别折扣
   			 								double m=0;
   			 								if(allgoods != null && !allgoods.equals("")){
	   			 								if(allgoods.contains("@")){
		   			 								String[] goods=allgoods.split(",");
				   			 							//循环所有商品
	   			 										for(int i=0 ; i<goods.length ; i++){
	   			 												PageData e6=new PageData();
	   			 											    String[] str1=goods[i].split("@");
		   			 											if(str1.length != 3){
		 			 												continue;
		 			 											}
 	   			 												e6.put("goods_id", str1[0]);
	   			 												e6=ServiceHelper.getAppGoodsService().goodsSortById(e6);
	   			 												if(e6 != null ){
 				   			 											if(e6.getString("zk_rate").length() == 1){
				   			 												double zkrate=1-Double.parseDouble(e6.getString("zk_rate"))/10.0;
				   			 												m+=zkrate*Double.parseDouble(str1[2]);
			   			 												}else if(e6.getString("zk_rate").length() == 2){
				   			 												double zkrate=1-Double.parseDouble(e6.getString("zk_rate"))/100;
				   			 												m+=zkrate*Double.parseDouble(str1[2]);
			   			 												}else if(e6.getString("zk_rate").length() ==3){
				   			 												double zkrate=1-Double.parseDouble(e6.getString("zk_rate"))/1000;
				   			 												m+=zkrate*Double.parseDouble(str1[2]);
			   			 												}
	   			 												}
	   			 												e6=null;
		   			 										}
		   			 							}
   			 								}
											  //判断总金钱折扣是否最大
											  if(m >= zkmoney){
												zkcontent=grantrule;
			 									zkmoney=m ;
 			 									zkid=marketing_id;
											  }
							}else if(zkpd.getString("discount_type").equals("3") &&  goodsFlag){//单品设置--购物车
  											String[] goods=allgoods.split(",");
  				 	 						double yhmoney=0;
				 	 						zkcontent=grantrule;
				 	 						String danpingcontent="";
 				 	 						for(int i=0; i<goods.length ;i++){
				 	 									PageData goodspd=new PageData();
 			 											String[] str=goods[i].split("@");
 			 											if(str.length != 3){
 			 												continue;
 			 											}
			 											goodspd.put("goods_id", str[0]);
			 											double buymoney=Double.parseDouble(str[2]);
			 											double buynumber=Double.parseDouble(str[1]);
			 											goodspd=ServiceHelper.getAppGoodsService().findById(goodspd);
			 											if(goodspd != null){
			 															//对时间进行判断
			 												 			long l1=new Date().getTime();
					 					   							  	long l2=DateUtil.fomatDate1(goodspd.get("starttime").toString()).getTime();
					 					   							  	long l3=DateUtil.fomatDate1(goodspd.get("endtime").toString()).getTime();
					 					   							  	boolean flag= l2<l1 && l3>l1 ;
					 					   							  	String goods_name=goodspd.getString("goods_name");
  						 												String promotion_type=goodspd.getString("promotion_type");
							 											String promotion_content=goodspd.getString("promotion_content");
 								 										if(!promotion_type.equals("0") && flag){//0-无促销，1-满减，2-单品折扣，3-买N件减1,4-送物品
	 							 											    if(promotion_type.equals("1")){
   													  		 								if(Double.parseDouble(promotion_content.substring(promotion_content.indexOf("减")+1, promotion_content.lastIndexOf("元"))) >=zkmoney && buymoney >= Double.parseDouble(promotion_content.substring(promotion_content.indexOf("满")+1, promotion_content.indexOf("元")))){
													  		 									yhmoney+=Double.parseDouble(promotion_content.substring(promotion_content.indexOf("减")+1, promotion_content.lastIndexOf("元")));
													  		 									danpingcontent+=goods_name+promotion_content+",";
													  		 								}
								 												}else if(promotion_type.equals("2")){
 										 													double n1=0;
										 													if(promotion_content.substring(0, promotion_content.indexOf("折")).length() == 1){
										 														n1=1-Double.parseDouble(promotion_content.substring(0, promotion_content.indexOf("折")))/10.0;
										 													}else if(promotion_content.substring(0, promotion_content.indexOf("折")).length() == 2){
										 														n1=1-Double.parseDouble(promotion_content.substring(0, promotion_content.indexOf("折")))/100;
										 													}else if(promotion_content.substring(0, promotion_content.indexOf("折")).length() == 3){
										 														n1=1-Double.parseDouble(promotion_content.substring(0, promotion_content.indexOf("折")))/1000;
										 													}
											 												double n2=buymoney*n1;
										 													if(n2 >=zkmoney){
										 														yhmoney+=n2;
										 														danpingcontent+=goods_name+promotion_content+",";
													  		 								}
									 										   }else if(promotion_type.equals("3")){
 									 													if(buynumber >= Double.parseDouble(promotion_content.substring(promotion_content.indexOf("买")+1, promotion_content.indexOf("件")))){
									 														 double n2=buymoney/buynumber;
		 								 													 if(n2 >zkmoney){
		 								 														 	yhmoney+=n2;
		 								 														 	danpingcontent+=goods_name+promotion_content+",";
														  		 							 }
									 													}
								 												}else{
								 													danpingcontent+=goods_name+promotion_content+",";
								 												}
							 											}
			 											}
			 											goodspd=null;
	 										}
 				 	 						//是否进行了单品折扣
 				 	 						if(!danpingcontent.equals("")){
 				 	 							zkcontent=danpingcontent.substring(0,danpingcontent.length()-1);
 				 	 						}
				 	 						if(yhmoney >= zkmoney ){
 			 									zkmoney=yhmoney ;
 			 									zkid=marketing_id;
				 	 						}
  							   } 
					}
					e=null;
			}
			//折扣设置的折扣
			if(!zkcontent.equals("") && zkmoney>0 ){
				PageData zkpd=new PageData();
				zkpd.put("content", zkcontent);
				zkpd.put("id", zkid);
				zkpd.put("type", "7");
				zkpd.put("number", "-"+df2.format(zkmoney));
				yingxiaoList.add(zkpd);
			}
			//是否为购物车购买的--今日特价商品只是不参与折扣优惠其他照常参加
			if(goodsFlag){
				youhui_money=youhui_money+notyouhui_money;
				notyouhui_money=0;
			}
 			//2.获取其他的营销规则
			List<PageData> marketlist=ServiceHelper.getAppStorepc_marketingService().listAllById(pd);
			PageData e1=new PageData();
			PageData e2=new PageData();
			PageData e3=new PageData();
			//获得积分
			boolean isopenjf=false;
			double addjf=0;
			String desc="";
			String addjfid="";
			//优惠内容
			String zengcontent="";
			String zengid="";
			String zengtype="";
			String zengcontent2="";
			String zengid2="";
			String zengtype2="";
			String jiancontent="";
			String jiantype="";
			String jianid="";
			double reducemoney=0;
			int marketlistlength=marketlist.size();
			for (int mi = 0; mi <marketlistlength ; mi++) {
				 			e=marketlist.get(mi);
   								//1-满赠，*2-满减，3-时段营销，4-买N减N（针对商品），5-累计次数/购买金额--增,6-积分，7-折扣
 								String marketing_type=e.getString("marketing_type");
 		 						String grantrule=e.getString("grantrule");
	 							String marketing_id=e.getString("marketing_id");
  	 							if(marketing_type.equals("1")){
 				 							if(youhui_money >= Double.parseDouble(grantrule.substring(grantrule.indexOf("满")+1, grantrule.indexOf("元")))){
					 							zengcontent=grantrule;
					 							zengid=marketing_id;
					 							zengtype=marketing_type;
			 								} 
 	  							}else if(marketing_type.equals("2")){
  	  									if(grantrule.contains("折")){
  		  		 								double n2=0;
				 								if(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")).length() == 1){
				 									n2=1-Double.parseDouble(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")))/10;
				 								}else if(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")).length() == 2){
				 									n2=1-Double.parseDouble(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")))/100;
				 								}else if(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")).length() == 3){
				 									n2=1-Double.parseDouble(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")))/1000;
				 								}
		  		 								if(n2*youhui_money >reducemoney && youhui_money >= Double.parseDouble(grantrule.substring(grantrule.indexOf("满")+1, grantrule.indexOf("元")))){
		  		 									jiancontent=grantrule;
		  		 									reducemoney=n2*youhui_money;
		  		 									jiantype=marketing_type;
		  		 									jianid=marketing_id;
		  		 								}
	  									}else{
		  										if(Double.parseDouble(grantrule.substring(grantrule.indexOf("减")+1, grantrule.lastIndexOf("元"))) >reducemoney && youhui_money >= Double.parseDouble(grantrule.substring(grantrule.indexOf("满")+1, grantrule.indexOf("元")))){
		  		 									jiancontent=grantrule;
		  		 									reducemoney=Double.parseDouble(grantrule.substring(grantrule.indexOf("减")+1, grantrule.lastIndexOf("元")));
		  		 									jiantype=marketing_type;
		  		 									jianid=marketing_id;
		  		 								}
	  									}
	   							}else if(marketing_type.equals("6")){
	   								//肯定有
	   							}else if(marketing_type.equals("7")){
	   								// ("第一步先进行折扣");
	   							}else if(marketing_type.equals("3")){
	   							  PageData sdpd=new PageData();
	   							  sdpd.put("marketing_id", marketing_id);
	   							  sdpd.put("store_id", pd.getString("store_id"));
	   							  sdpd=ServiceHelper.getStorepc_marketingtypeService().findById(sdpd);
	   							  if(sdpd != null){
	   								  		  long l1=new Date().getTime();
				   							  long l2=DateUtil.fomatDate1(DateUtil.getDay()+" "+sdpd.get("starttime").toString()).getTime();
				   							  long l3=DateUtil.fomatDate1(DateUtil.getDay()+" "+sdpd.get("endtime").toString()).getTime();
				   							  if(  Double.parseDouble(sdpd.getString("threeachieve_money")) <= youhui_money &&  l1 > l2 && l1 < l3 ){
				   								  	 if(sdpd.getString("marketsmall_type").equals("1")){//折
				   								  		 	 if(sdpd.getString("threediscount_rate").length() == 1){
						   								  		 	double mm=youhui_money*(1-Double.parseDouble(sdpd.getString("threediscount_rate"))/10.0);
						   								  	         if(  mm > reducemoney){
										   								  	        jiancontent=grantrule;
										  		 									reducemoney=mm;
										  		 									jiantype=marketing_type;
										  		 									jianid=marketing_id;
						   								  	         }
				   								  		 	 }else  if(sdpd.getString("threediscount_rate").length() == 2){
						   								  		 	double mm=youhui_money*(1-Double.parseDouble(sdpd.getString("threediscount_rate"))/100);
						   								  	         if(  mm > reducemoney){
										   								  	        jiancontent=grantrule;
										  		 									reducemoney=mm;
										  		 									jiantype=marketing_type;
										  		 									jianid=marketing_id;
						   								  	         }
				   								  		 	 }else  if(sdpd.getString("threediscount_rate").length() == 3){
						   								  		 	double mm=youhui_money*(1-Double.parseDouble(sdpd.getString("threediscount_rate"))/1000);
						   								  	         if(  mm > reducemoney){
										   								  	        jiancontent=grantrule;
										  		 									reducemoney=mm;
										  		 									jiantype=marketing_type;
										  		 									jianid=marketing_id;
						   								  	         }
				   								  		 	 } 
					   								  	}else{//钱
				   								  		   double mm= Double.parseDouble(sdpd.getString("threereduce_money"));
						   								  	if( mm> reducemoney){
								   								  	        jiancontent=grantrule;
								  		 									reducemoney= Double.parseDouble(sdpd.getString("threereduce_money"));
								  		 									jiantype=marketing_type;
								  		 									jianid=marketing_id;
													  	         }
				   								  	 }
				   							  }
	   							  }
	   							sdpd=null;
	   						}else if(marketing_type.equals("4")  && goodsFlag ){//买N减N（针对商品）
	   							     PageData mnpd=new PageData();
	   								 mnpd.put("marketing_id", marketing_id);
	   								 mnpd.put("store_id", pd.getString("store_id"));
	   								 mnpd=ServiceHelper.getStorepc_marketingtypeService().findById(mnpd);
	   								 if(mnpd != null){
				   								  long l1=new Date().getTime();
	 	 			   							  long l2=DateUtil.fomatDate1(DateUtil.getDay()+" "+mnpd.get("starttime").toString()).getTime();
					   							  long l3=DateUtil.fomatDate1(DateUtil.getDay()+" "+mnpd.get("endtime").toString()).getTime();
					   							  if(  l1 > l2 && l1 < l3 ){
					   								  	 String[] goods=allgoods.split(",");
					   								  	 double m=0;
					   								  	 double buynumber=0;
					   								  	 double minmoney=0;
						   								 for(int i=0; i<goods.length ;i++){
						   									 String[] str=goods[i].split("@");//所有商品：商品id@数量@总金额 
						   									if(str.length != 3){
	 			 												continue;
	 			 											}
						   									if(mnpd.getString("goods_id").contains(str[0])){
						   										 buynumber+=Double.parseDouble(str[1]);
						   										 if(Double.parseDouble(str[2] ) / Double.parseDouble(str[1]) < minmoney){
						   											   minmoney=Double.parseDouble(str[2] ) / Double.parseDouble(str[1]);
						   										 }
						   									 }
						   									 if( Double.parseDouble(mnpd.getString("fourachieve_number")) <= buynumber){
						   										 m=minmoney;
						   									 }
		 				   								 }
						   								 if(m > reducemoney){
							   									jiancontent=grantrule;
					  		 									reducemoney= m;
					  		 									jiantype=marketing_type;
					  		 									jianid=marketing_id;
						   								 }
	 				   							  }
	   								 }
	   								mnpd=null;
	   						}else if(marketing_type.equals("5")){
			   						PageData ljpd=new PageData();
			   						ljpd.put("marketing_id", marketing_id);
			   						ljpd.put("store_id", pd.getString("store_id"));
			   						ljpd=ServiceHelper.getStorepc_marketingtypeService().findById(ljpd);
					   				if(ljpd != null){
				   								  long l1=new Date().getTime();
		 	 			   						  long l2=DateUtil.fomatDate1(DateUtil.getDay()+" "+ljpd.get("starttime").toString()).getTime();
					   							  long l3=DateUtil.fomatDate1(DateUtil.getDay()+" "+ljpd.get("endtime").toString()).getTime();
					   							  if(  l1 > l2 && l1 < l3 ){
						   								PageData orderpd=new PageData();
				 	 			   						orderpd.put("starttime", DateUtil.getDay()+" "+ljpd.get("starttime").toString());
				 	 			   						orderpd.put("endtime", DateUtil.getDay()+" "+ljpd.get("endtime").toString());
				 	 			   						orderpd.put("store_id", pd.getString("store_id"));
				 	 			   						orderpd.put("member_id", pd.getString("member_id"));
			 				   							orderpd=ServiceHelper.getAppOrderService().listhistoryNumberByStore(orderpd);
			 				   							String number=orderpd.getString("number");
			 				   							String sumsale_money="0";
				 				   						if(orderpd.get("sumsale_money") != null){
							   								sumsale_money=orderpd.get("sumsale_money").toString();
							   							};
							   							if(Integer.parseInt(number)%Integer.parseInt(ljpd.getString("fiveachieve_number")) == 0 && Double.parseDouble(sumsale_money)%Double.parseDouble(ljpd.getString("fiveachieve_money")) >= 0 && Double.parseDouble(sumsale_money)%Double.parseDouble(ljpd.getString("fiveachieve_money")) < 1){
		 				   									//判断哪个红包适合
			 				   								zengcontent2=grantrule;
						 									zengid2=marketing_id;
						 									zengtype2=marketing_type;
							   							}
							   							orderpd=null;
						   						}
					   				}
				   					 
		   						}
 	 							e=null;
		}
			marketlist=null;
			//满赠
			if(!zengcontent.equals("")){
				e1.put("content", zengcontent);
				e1.put("number", "");
				e1.put("id", zengid);
				e1.put("type", zengtype);
				yingxiaoList.add(e1);
			}
			e1=null;
			//累计次数
			PageData ldpd=new PageData();
			if(!zengcontent2.equals("")){
				ldpd.put("content", zengcontent2);
				ldpd.put("number", "");
				ldpd.put("id", zengid2);
				ldpd.put("type", zengtype2);
				yingxiaoList.add(ldpd);
			}
			ldpd=null;
			//满减
			if(!jiancontent.equals("") && reducemoney>0 ){
				e2.put("content", jiancontent);
				e2.put("id", jianid);
				e2.put("type", jiantype);
				e2.put("number", "-"+df2.format(reducemoney));
				yingxiaoList.add(e2);
			}
			e2=null;
			 int yingxiaosize=yingxiaoList.size();
 			 double useredbeforMoney=reducemoney+zkmoney;//使用红包前的总共优惠了的金额
			 //判断优惠后的金额是否已经是0
			 String redpackage_id="";
			 String redmoney="0";
			 if(useredbeforMoney < youhui_money+notyouhui_money){
				   //使用红包
					PageData redpd=getMaxStoreRedMoneyByMember(pd,youhui_money,yingxiaosize,useredbeforMoney);
		 			if(redpd != null){
		 				redpackage_id=redpd.getString("id");
		 				redmoney=redpd.getString("number");
						redpd.put("number", "-"+redmoney);
						redpd.put("type", "0");
						yingxiaoList.add(redpd);
					} 
					redpd=null;
			 }
 			//使用红包后的优惠后的实际应该支付的金额为
			double surepaymoney=youhui_money-reducemoney-Double.parseDouble(redmoney)-zkmoney+notyouhui_money;
			//总共优惠金额
			double surehuiyoumoney=reducemoney+Double.parseDouble(redmoney)+zkmoney;
 			if(!leibieFlag){
 					//获取所有启用的积分
					PageData jfpd=ServiceHelper.getAppStorepc_marketingService().getJfById(pd);
					if(jfpd != null){ 
							double redhuiyoumoney=surepaymoney-notyouhui_money;
							if(redhuiyoumoney < 0){
								redhuiyoumoney=0;
							}
 		 	 				if(jfpd.getString("change_type").equals("1")){//整店
 			 	 					if(Double.parseDouble(jfpd.getString("oneback_rate"))/100*redhuiyoumoney >= addjf){
			 	 						addjf=Double.parseDouble(jfpd.getString("oneback_rate"))/100*redhuiyoumoney;
			 	 						desc=jfpd.getString("grantrule");
			 	 					}
		 	 				}else if(jfpd.getString("change_type").equals("5")){//满多少送多少积分
		 	 							String[] str=jfpd.getString("grantrule").split(",");
		 	 							int strlength=str.length;
		 	 							for(int i=0; i<strlength ;i++){
 			  		  							if(Double.parseDouble(str[i].substring(str[i].indexOf("满")+1, str[i].indexOf("元"))) <= redhuiyoumoney){
 			  		 								if(Double.parseDouble(str[i].substring(str[i].indexOf("满")+1, str[i].indexOf("元")))*Double.parseDouble(str[i].substring(str[i].indexOf("送")+1, str[i].lastIndexOf("%")))/100.00 >= addjf ){
			  		 									addjf=Double.parseDouble(str[i].substring(str[i].indexOf("满")+1, str[i].indexOf("元")))*Double.parseDouble(str[i].substring(str[i].indexOf("送")+1, str[i].lastIndexOf("%")))/100.00;
		 					 	 						desc=jfpd.getString("grantrule");
				  		 							}
			  		  							}
		 	 							}
		 	 				}else if(jfpd.getString("change_type").equals("4")){//每单返积分
 				 	 					if(Double.parseDouble(jfpd.getString("fourbackintegral_integral")) >= addjf){
				 	 						addjf=Double.parseDouble(jfpd.getString("fourbackintegral_integral"));
				 	 						desc=jfpd.getString("grantrule");
				 	 					}
		 	 				} else if(jfpd.getString("change_type").equals("3")  &&  goodsFlag){//单品送积分
			 	 						String[] goods=allgoods.split(",");
			 	 						PageData goodspd=new PageData();
			 	 						double m=0;
			 	 						int goodslength=goods.length;
										for(int i=0; i< goodslength ;i++){
 											if(goods[i].split("@").length != 3){
	 												continue;
	 										}
											goodspd.put("goods_id", goods[i].split("@")[0]);
											goodspd=ServiceHelper.getAppGoodsService().findById(goodspd);
 											if(goodspd.getString("integral_rate") != null && !goodspd.getString("integral_rate").equals("")){
 	 											m+=Double.parseDouble( goods[i].split("@")[2] )*Double.parseDouble(goodspd.getString("integral_rate"))/100.00;
											}
 										}
										goodspd=null;
										if(m >= addjf){
											addjf=m;
											desc=jfpd.getString("grantrule");
										}
		 	 			  }else if(jfpd.getString("change_type").equals("2") && goodsFlag){
				 	 				String[] goods=allgoods.split(",");
		 	 						PageData goodspd=new PageData();
		 	 						double m=0;
		 	 						int goodslength=goods.length;
		 	 						for(int i=0; i< goodslength ;i++){
 										if(goods[i].split("@").length != 3){
												continue;
										}
										goodspd.put("goods_id", goods[i].split("@")[0]);
										goodspd=ServiceHelper.getAppGoodsService().findById(goodspd);
 										if(goodspd.getString("integral_rate") != null && !goodspd.getString("integral_rate").equals("")){
  											m+=Double.parseDouble( goods[i].split("@")[2] )*Double.parseDouble(goodspd.getString("integral_rate"))/100.00;
										}
									}
		 	 						goodspd=null;
		 	 						if(m >= addjf){
										addjf=m;
										desc=jfpd.getString("grantrule");
									}
		 	 			  }
				}
				//积分
				if(!desc.equals("")){
					e3.put("content", desc);
					e3.put("number", "+"+df2.format(addjf));
					e3.put("id", addjfid);
					e3.put("type", "6");
					yingxiaoList.add(e3);
				}
			}
 			//处理营销
 			if(youhui_money+notyouhui_money <= 0){
	  			map.put("yingxiaoList", new ArrayList<PageData>());
	  		}else{
	  			map.put("yingxiaoList", yingxiaoList);
	  		}
 			//参数集中
  			PageData countpd=new PageData();
			if(surepaymoney <= 0){
				countpd.put("paymoney", "0");//优惠后的支付金额
				countpd.put("reducemoney", df2.format(youhui_money+notyouhui_money));
			}else{
				countpd.put("paymoney", df2.format(surepaymoney));
				countpd.put("reducemoney", df2.format(surehuiyoumoney));//优惠金额=优惠的金额+红包优惠的金额+折扣的金额
			}
   			countpd.put("zengid", zengid+","+zengid2);//增红包的集合
 			countpd.put("zengjf", df2.format(addjf+alljifeng));//赠送的积分
 			countpd.put("red_id", redpackage_id);//使用红包的ID
 			countpd.put("allmoney", df2.format(youhui_money+notyouhui_money));//总金额
 			map.put("countpd", countpd);
 			countpd=null;
 			//获取个人财富
 			map.put("mpd", ServiceHelper.getAppMemberService().findWealthById(pd));
			map.put("memberInfor", ServiceHelper.getAppMemberService().findWealthById(pd));
 			//判断是否开通类别积分购买的权限
			if(ServiceHelper.getAppStorepc_marketingService().getJfById(pd) != null){
				if(ServiceHelper.getAppStorepc_marketingService().getJfById(pd).getString("change_type").equals("3") ){
					map.put("issortjf", "3");
				}else if(ServiceHelper.getAppStorepc_marketingService().getJfById(pd).getString("change_type").equals("2") ){
					map.put("issortjf", "1");
				} else{
					map.put("issortjf", "0");
				}
			}else{
				map.put("issortjf", "0");
			}
 			//商家名称
			map.put("store_name", ServiceHelper.getAppStoreService().findById(pd).getString("store_name"));
  		} catch (Exception e) {
  			(new TongYong()).dayinerro(e);
		}
  		return map;
  	}
  	
  	
  	
  	
  	
 
  	
  	/**
	 * 
	* 方法名称:：getMaxStoreRedMoneyByMember 
	* 方法描述：会员自动获取当前商家最优惠的红包
	* 
	*  youhui_money  参与优惠的总金额
	*  reducemoney   营销优惠金额
	*  yingxiaosize  参与营销的次数
	 */
	public static  PageData  getMaxStoreRedMoneyByMember(PageData pd,double youhui_money,int yingxiaosize,double reducemoney){
   		PageData _pd=new PageData();
 		try{ 
  			String id="";
 			double maxreduce=0;
 			String content="";
 			//获取当前用户在当前商家可以用的所有红包
			List<PageData>	memredList =ServiceHelper.getAppMember_redpacketsService().listAllById(pd);
			PageData e=null;
			int memberredlength=memredList.size();
			for (int redi = 0; redi < memberredlength;redi++) {
				e=memredList.get(redi);
				String redpackage_content=e.getString("redpackage_content");
// 				redpackage_type//1-无要求，21/2-满XX元 （可一起使用），3-首单，22-满XX元 （不可一起使用（减钱） ）-优惠前打折,01、21、31表示优惠后折扣（打折） 
 //				store_redpackage_type//1-现金红包，2-折扣红包，3-优惠现金红包，4-优惠折扣红包,51-立减红包（可一起使用）,52-立减红包（不可一起使用）
 //				srp_usercondition_id//0-是立减红包。1-无要求。2-满XX元使用 。3-首单 
   				if(redpackage_content !=null && !redpackage_content.equals("") && !redpackage_content.contains("null")){
   						//判断是否在改商店消费过订单	
   						if(redpackage_content.contains("首单") && ServiceHelper.getAppOrderService().countStoreMember(pd) == 0){//首单立减的红包
 								if(redpackage_content.contains("折")){
									String number=redpackage_content.substring(redpackage_content.indexOf("打")+1, redpackage_content.indexOf("折"));
									int numberlength=number.length();
									if(number.contains(".")){
										numberlength=number.substring(0, number.indexOf(".")).length();
									}
									double zzk=0;
			  						if(numberlength == 1){
			  							zzk=(1-Double.parseDouble(number)/10) ;
								    }else if(numberlength == 2){
								    	zzk=(1-Double.parseDouble(number)/100) ;
								    }else if(numberlength == 3){
								    	zzk=(1-Double.parseDouble(number)/1000) ;
								    }
									double n= zzk*youhui_money;
								if(e.getString("redpackage_type").contains("1")){
									n=zzk*(youhui_money-reducemoney);
								}
								if(n>maxreduce  ){
									maxreduce=n;
									id=e.getString("redpackage_id");
									content=redpackage_content;
								}
							}else{
								if(Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("减")+1, redpackage_content.lastIndexOf("元"))) > maxreduce  ){
									maxreduce=Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("减")+1, redpackage_content.lastIndexOf("元")));
									id=e.getString("redpackage_id");
									content=redpackage_content;
								}
							}
 						}else if(redpackage_content.contains("满") && redpackage_content.contains("件")){//满多少件的红包
							  //滤过
						}else if(redpackage_content.contains("满") && redpackage_content.contains("元")){//满多少元的红包
							double buymoney=Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("满")+1, redpackage_content.indexOf("元")));    
 						    if(youhui_money >= buymoney){
 						    	if(e.getString("redpackage_type").equals("22")){//不可一起使用的红包
 						    		if(yingxiaosize == 0){
 	 									double n=0;
 										if(redpackage_content.contains("折")){
 											String number=redpackage_content.substring(redpackage_content.indexOf("打")+1, redpackage_content.lastIndexOf("折"));
 											int numberlength=number.length();
 											if(number.contains(".")){
 												numberlength=number.substring(0, number.indexOf(".")).length();
 											}
 					  						if(numberlength == 1){
 										    	 n=(1-Double.parseDouble(number)/10)*youhui_money;
 										    }else if(numberlength == 2){
 										    	 n=(1-Double.parseDouble(number)/100)*youhui_money;
 										    }else if(numberlength == 3){
 										    	 n=(1-Double.parseDouble(number)/1000)*youhui_money;
 										    }
 					 					}else{
 											n=Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("减")+1, redpackage_content.lastIndexOf("元")));
 					 					}
 					 					if(n > maxreduce ){
 											maxreduce=n;
 											id=e.getString("redpackage_id");
 											content=redpackage_content;
 										}
 						    		}
 								}else{
 	 								if(redpackage_content.contains("折")){
 										String number=redpackage_content.substring(redpackage_content.indexOf("打")+1, redpackage_content.indexOf("折"));
 										int numberlength=number.length();
 										if(number.contains(".")){
 											numberlength=number.substring(0, number.indexOf(".")).length();
 										}
 										double zzk=0;
 						  				if(numberlength == 1){
 						  					zzk=(1-Double.parseDouble(number)/10) ;
 										}else if(numberlength == 2){
 											zzk=(1-Double.parseDouble(number)/100) ;
 										}else if(numberlength == 3){
 											zzk=(1-Double.parseDouble(number)/1000) ;
 										}
 										double n= zzk*youhui_money;
 										if(e.getString("redpackage_type").contains("1")){
 											n=zzk*(youhui_money-reducemoney);
 										}
 										if(n > maxreduce && youhui_money >= Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("满")+1, redpackage_content.indexOf("元")))){
 											maxreduce=n;
 											id=e.getString("redpackage_id");
 											content=redpackage_content;
 										}
 									}else {
 										if( Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("减")+1, redpackage_content.lastIndexOf("元")))  > maxreduce  && youhui_money >= Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("满")+1, redpackage_content.indexOf("元")))){
 											maxreduce=Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("减")+1, redpackage_content.lastIndexOf("元")));
 											id=e.getString("redpackage_id");
 											content=redpackage_content;
 										}
 						 			}
 								} 
 						    }
  						}else if(redpackage_content.contains("无条件") || redpackage_content.contains("无要求")){//无条件/无要求的红包
							if(redpackage_content.contains("折")){
 								String number=redpackage_content.substring(redpackage_content.indexOf("打")+1, redpackage_content.indexOf("折"));
								int numberlength=number.length();
								if(number.contains(".")){
									numberlength=number.substring(0, number.indexOf(".")).length();
								}
								double zzk=0;
			  					if(numberlength == 1){
			  						zzk=(1-Double.parseDouble(number)/10) ;
								}else if(numberlength == 2){
								    zzk=(1-Double.parseDouble(number)/100) ;
								}else if(numberlength == 3){
								    zzk=(1-Double.parseDouble(number)/1000) ;
								}
								double n=zzk*youhui_money;
								if(e.getString("redpackage_type").contains("1")){
									n=zzk*(youhui_money-reducemoney);
								}
								if(n>maxreduce){
									maxreduce=n;
									id=e.getString("redpackage_id");
									content=redpackage_content;
								}
 							}else{
   								if( Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("减")+1, redpackage_content.lastIndexOf("元"))) >maxreduce ){
									maxreduce= Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("减")+1, redpackage_content.lastIndexOf("元"))) ;
									id=e.getString("redpackage_id");
									content=redpackage_content;
								}
					 		}
						}
				}
				e=null;
  			}
			memredList=null;
			if(id==""){
				_pd=null;
			}else{
				_pd.put("id", id);
				_pd.put("content", content);
				_pd.put("number", df2.format(maxreduce));
			}
 		} catch(Exception e){
  			(new TongYong()).dayinerro(e);
 		}
 		return _pd;
	}
	
	/**
	 * 
	* 方法名称:：getAllStoreRedMoneyByMember 
	* 方法描述：会员获取当前所有的未过期未使用的红包列表
	* 
	* youhui_money  参与优惠的总金额
	* reducemoney   营销优惠金额
	* yingxiaosize  参与营销的次数
	* 
	* 返回数据
	* canuse_red 1-可以使用，99-不可以使用
  	 */
	public static  List<PageData>  getAllStoreRedMoneyByMember(PageData pd,double youhui_money,int yingxiaosize,double reducemoney){
		List<PageData> okredList=new ArrayList<PageData>();
  		try{ 
    		//获取当前用户在当前商家可以用的所有红包
			List<PageData>	memredList =ServiceHelper.getAppMember_redpacketsService().listAllById(pd);
			PageData e=null;
			int memberredlength=memredList.size();
			for (int redi = 0; redi < memberredlength;redi++) {
				e=memredList.get(redi);
				e.put("canuse_red", "99");
				String redpackage_content=e.getString("redpackage_content");
  				if(!redpackage_content.contains("null")){
 						if(redpackage_content.contains("首单")){//首单立减的红包
							//判断是否在改商店消费过订单
							int rednumber=ServiceHelper.getAppOrderService().countStoreMember(pd);
							if(rednumber == 0){ 
								e.put("canuse_red", "1");
							} 
 						}else if(redpackage_content.contains("满") && redpackage_content.contains("件")){//满多少件的红包
							  //滤过
						}else if(redpackage_content.contains("满") && redpackage_content.contains("元")){//满多少元的红包
							double buymoney=Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("满")+1, redpackage_content.indexOf("元")));
  							if(youhui_money >= buymoney){
   								if(e.getString("redpackage_type").equals("22")){//不可一起使用的红包
   									if( yingxiaosize == 0 ){
   										e.put("canuse_red", "1");
   									}
   								}else{
  									e.put("canuse_red", "1");
  								}
 							} 
  						}else if(redpackage_content.contains("无条件") || redpackage_content.contains("无要求")){//无条件/无要求的红包
  							e.put("canuse_red", "1");
						}
				}
  				okredList.add(e);
				e=null;
  			}
  		} catch(Exception e){
  			(new TongYong()).dayinerro(e);
 		}
 		return okredList;
	}
	
	/**
 	* 方法名称:：getRedPackageInforByID 
	* 方法描述：会员当前使用红包的信息
	* 
 	*  redpackage_id  红包ID
 	*  youhui_money   可以优惠的总金额
 	*  reducemoney    优惠金额
	 */
	public static  PageData  getRedPackageInforByID(String redpackage_id ,double youhui_money,double reducemoney){
   		PageData _pd=new PageData();
 		try{ 
 			_pd.put("type", "0");
 			_pd.put("id", redpackage_id);
 			_pd.put("redpackage_id", redpackage_id);
  			PageData e= ServiceHelper.getAppMember_redpacketsService().findById(_pd);
 			if( e == null){
 				return null;
 			}
 			_pd.remove("redpackage_id");
 			String redpackage_content=e.getString("redpackage_content");
 			_pd.put("content", redpackage_content);
  			if(redpackage_content !=null && !redpackage_content.equals("") && !redpackage_content.contains("null")){
					if(redpackage_content.contains("首单")){//首单立减的红包
						if(redpackage_content.contains("折")){
								String number=redpackage_content.substring(redpackage_content.indexOf("打")+1, redpackage_content.indexOf("折"));
								int numberlength=number.length();
								if(number.contains(".")){
									numberlength=number.substring(0, number.indexOf(".")).length();
								}
								double zzk=0;
		  						if(numberlength == 1){
		  							zzk=(1-Double.parseDouble(number)/10) ;
							    }else if(numberlength == 2){
							    	zzk=(1-Double.parseDouble(number)/100) ;
							    }else if(numberlength == 3){
							    	zzk=(1-Double.parseDouble(number)/1000) ;
							    }
								double jianmoney= zzk*youhui_money;
								if(e.getString("redpackage_type").contains("1")){//包含1表示优惠后折扣
									jianmoney=zzk*(youhui_money-reducemoney);
								}
								_pd.put("number", df2.format(jianmoney));
 						}else{
 							double jianmoney=Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("减")+1, redpackage_content.lastIndexOf("元")));
 							_pd.put("number", df2.format(jianmoney));
						}
							
					}else if(redpackage_content.contains("满") && redpackage_content.contains("件")){//满多少件的红包
						  //滤过
					}else if(redpackage_content.contains("满") && redpackage_content.contains("元")){//满多少元的红包
						double jianmoney=0;
						if(redpackage_content.contains("折")){
							String number=redpackage_content.substring(redpackage_content.indexOf("打")+1, redpackage_content.lastIndexOf("折"));
							int numberlength=number.length();
							if(number.contains(".")){
								numberlength=number.substring(0, number.indexOf(".")).length();
							}
	  						if(numberlength == 1){
	  							jianmoney=(1-Double.parseDouble(number)/10)*youhui_money;
						    }else if(numberlength == 2){
						    	jianmoney=(1-Double.parseDouble(number)/100)*youhui_money;
						    }else if(numberlength == 3){
						    	jianmoney=(1-Double.parseDouble(number)/1000)*youhui_money;
						    }
	 					}else{
	 						jianmoney=Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("减")+1, redpackage_content.lastIndexOf("元")));
	 					}
						_pd.put("number", df2.format(jianmoney));
					}else if(redpackage_content.contains("无条件") || redpackage_content.contains("无要求")){//无条件/无要求的红包
						double jianmoney=0;		
						if(redpackage_content.contains("折")){
								String number=redpackage_content.substring(redpackage_content.indexOf("打")+1, redpackage_content.indexOf("折"));
 								int numberlength=number.length();
 								if(number.contains(".")){
 									numberlength=number.substring(0, number.indexOf(".")).length();
 								}
 								double zzk=0;
 				  				if(numberlength == 1){
 				  					zzk=(1-Double.parseDouble(number)/10) ;
 				  				}else if(numberlength == 2){
 									zzk=(1-Double.parseDouble(number)/100) ;
 								}else if(numberlength == 3){
 									zzk=(1-Double.parseDouble(number)/1000) ;
 								}
 				  				jianmoney=zzk*youhui_money;
								if(e.getString("redpackage_type").contains("1")){
									jianmoney=zzk*(youhui_money-reducemoney);
								}
 						}else{
 								jianmoney= Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("减")+1, redpackage_content.lastIndexOf("元"))) ;
 				 		}
						_pd.put("number", df2.format(jianmoney));
					}else{
						return null;
					}
			}else{
				return null;
			}
			e=null;
  		} catch(Exception e){
  			(new TongYong()).dayinerro(e);
 		}
 		return _pd;
	}
	
	
	
	
	/**
	 * 
	* 方法名称:：markeingAll 
	* 方法描述：获取营销规则
	* 创建人：魏汉文
	* 创建时间：2016年7月21日 上午10:48:06
	 */
	public static PageData markeingAll(PageData pd){
				PageData yxpd=new PageData();
				String add="";
				String reduce="";
				String time="";
				String n="";
				String number="";
				String score="";
				String zk="";
 				try{
					//获取营销规则
//			 		   PageData e = new PageData();
						List<PageData> marketlist=ServiceHelper.getAppStorepc_marketingService().listAllById(pd);
						int marketlength=marketlist.size();
						PageData e2=null;
						for (int mi = 0; mi< marketlength ; mi++) {
									e2=marketlist.get(mi);
 									/*
									 * 1-满赠，2-满减，3-时段营销，4-买N减N，5-购买次数,6-积分，7折扣
									 */
//									String marketing_type=e2.getString("marketing_type");
//									String marketing_id=e2.getString("marketing_id");
//									String grantrule=e2.getString("grantrule");
									if(e2.getString("marketing_type").equals("1")){
										add+=e2.getString("grantrule")+",";
									}else if(e2.getString("marketing_type").equals("2")){
										reduce+=e2.getString("grantrule")+",";
									}else if(e2.getString("marketing_type").equals("3")){
										time+=e2.getString("grantrule")+",";
									}else if(e2.getString("marketing_type").equals("4")){
										n+=e2.getString("grantrule")+",";
									}else if(e2.getString("marketing_type").equals("5")){
										number+=e2.getString("grantrule")+",";
									}else if(e2.getString("marketing_type").equals("6")){
										score+=e2.getString("grantrule")+",";
 			 						}else if(e2.getString("marketing_type").equals("7")){
 			 							zk+=e2.getString("grantrule")+",";
   									}
									e2=null;
						}
						marketlist=null;
						if(!add.equals("")){
  							add=add.substring(0, add.length()-1);
  						}
  						if(!reduce.equals("")){
  							reduce=reduce.substring(0, reduce.length()-1);
  						}
  						if(!time.equals("")){
  							time=time.substring(0, time.length()-1);
  						}
  						if(!n.equals("")){
  							n=n.substring(0, n.length()-1);
  						}
  						if(!number.equals("")){
  							number=number.substring(0, number.length()-1);
  						}
  						if(!score.equals("")){
  							score=score.substring(0, score.length()-1);
  						}
  						if(!zk.equals("")){
  							zk=zk.substring(0, zk.length()-1);
  						}
 				}catch(Exception e){
					//System.out.println(e.toString());
					(new TongYong()).dayinerro(e);
				}
				yxpd.put("mz", add );
				yxpd.put("mj", reduce );
				yxpd.put("sdyx", time );
				yxpd.put("mnjn", n );
				yxpd.put("gmcs", number );
				yxpd.put("jfsy", score );
				yxpd.put("zk", zk );
 				return yxpd;
	}
  	
	
	/**
  	 * 判断这个会员对于这个商家是否有红包可言
  	 */
  	public static Map<String,Object> storeAndMemberByRed(PageData pd){
  		Map<String,Object> map = new HashMap<String,Object>();
  		boolean varListflay=false;
   		try{
   		    //获取当前用户的所有红包
			List<PageData>	memredList =ServiceHelper.getAppMember_redpacketsService().listAllById(pd);
			//列出红包列表--除去已拥有的红包
			pd.put("redpackage_status", Const.putong_redpackage_status);
			List<PageData>	varList = ServiceHelper.getAppStore_redpacketsService().list(pd);	
			for(int i=0 ; i<varList.size() ;i++ ){
//				//System.out.println(i);
				 PageData e=varList.get(i);
				 String store_redpackets_id=e.getString("store_redpackets_id");
				 boolean flag=false;
 				 for(int j=0;j< memredList.size() ; j++ ){
					 PageData f =new PageData();
					 f=memredList.get(j);
					 if(store_redpackets_id.equals(f.getString("store_redpackets_id"))){
						 flag=true;
						 break;
					 }
					 f=null;
				}
 				if(flag){
 					varList.remove(i);
 					--i;
 					 e=null;
					continue;
				 }else{
//					 //System.out.println("共"+varList.size()+"条数据，当前红包第"+i+"个详情"+e.toString());
					 boolean flag2=getRedByMemberIsOk(ServiceHelper.getAppMemberService().findById(pd),e);
	  				 if(!flag2){
						 varList.remove(i);
						 --i;
						 e=null;
						 continue;
	 				 } 
				 }
 			}
			memredList=null;
			if(varList.size() >0){
				varListflay=true;
 			}
			map.put("varList", varList);
   		} catch(Exception e){
  			//System.out.println(e.toString()+"判断这个会员对于这个商家是否有红包可言===========================");
  			(new TongYong()).dayinerro(e);
   		}
  		map.put("flag", varListflay);
  		return map;
  	}
  	
  	
  	
	
	
	
	
	/**
	 * 距离
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public static List<PageData> jingweidu(PageData pd) throws Exception{
			List<PageData> jiluList = new ArrayList<PageData>();//存储距离和手机号
			try {
 				PageData sjjw = ServiceHelper.getStorepc_redpacketsService().sjjwList(pd);
				if(sjjw != null){
				//判断是否定位了
						boolean isdingwei = true;
						double sjlongitude = 0.0;
						double sjlatitude = 0.0;
						double distance = 0;
						if(sjjw.getString("longitude") == null || sjjw.getString("longitude").equals("")){
							isdingwei = false;
						}else{
							sjlongitude = Double.parseDouble(sjjw.getString("longitude"));//商家经度
							sjlatitude = Double.parseDouble(sjjw.getString("latitude"));//商家纬度
						}
	 					List<PageData> hyjw = ServiceHelper.getStorepc_redpacketsService().hyjwList(pd);
						if(hyjw.size() > 0){
							for (int i = 0; i < hyjw.size(); i++) {
								PageData e = new PageData();
								e = hyjw.get(i);
								if(e != null){
										if(e.getString("longitude") != null && !e.getString("longitude").equals("")){
													double hylongitude = Double.parseDouble(e.getString("longitude"));//会员经度
													double hylatitude = Double.parseDouble(e.getString("latitude"));//会员纬度
													if(isdingwei){
															double overdistance=Distance.getResult(sjlongitude, hylongitude, sjlatitude, hylatitude);
															if(distance < overdistance){
																hyjw.remove(i);
																i=i-1;
															}else{
																	e.put("phone", hyjw.get(i).get("phone").toString());
																	e.put("member_id", hyjw.get(i).get("member_id").toString());
																	e.put("distance", overdistance+"");
																	jiluList.add(e);
																}
													}else{
														e.put("distance", "--");
													}
										}
	 								}
								}
								
						}
			}
			} catch (Exception e) {
				// TODO: handle exception
				(new TongYong()).dayinerro(e);
			}
  		return jiluList;
	}
	
	/**
	 * 判断服务商的钱是否可以激活
	 */
	public static void storeIsCityServerFeeOK(String store_id){
		PageData pd=new PageData();
 		try {
   			pd.put("isjihuo", "0");
 			pd.put("user_type", "1");
 			pd.put("user_id", store_id);
 			//判断当前的商家以及服务商之间是否还有为激活的费用
  			if(ServiceHelper.getService_performanceService().findNeedJiHuoFeeCount(pd) > 0){
  				pd.put("store_id", store_id);
  				pd=ServiceHelper.getAppStoreService().findById(pd);
  	 			if(pd != null){
  					//统计当前商家的一度二度人脉的数量
  					String allcontacts=ServiceHelper.getAppStoreService().countNowUserContacts(store_id);
  		 			//统计当前商家的消费订单笔数
  					String allordersnumber=ServiceHelper.getAppStoreService().countNowStoreOrders(store_id);
  		 			//统计当前商家的总交易金额
  					String allorderssum=ServiceHelper.getAppStoreService().sumNowStoreOrders(store_id);
  					//获取当前城市营销的提成与补贴
  	 				if(ServiceHelper.getCity_marketingService().getCityMarketingForPCD(pd) != null){
  	 					if(ServiceHelper.getCm_allService().listAllTichengButie(ServiceHelper.getCity_marketingService().getCityMarketingForPCD(pd)).size() > 0){
  	 						PageData citypd=ServiceHelper.getCm_allService().listAllTichengButie(ServiceHelper.getCity_marketingService().getCityMarketingForPCD(pd)).get(0);
  	 						//获取人脉数，订单数，以及总销售额
  	 						String allrenmai_number=citypd.getString("allrenmai_number");
  	 						String jioayi_number=citypd.getString("jioayi_number");
  	 						String jiaoyi_money=citypd.getString("jiaoyi_money");
  	 						if(Double.parseDouble(allcontacts) > Double.parseDouble(allrenmai_number) && Double.parseDouble(allordersnumber) > Double.parseDouble(jioayi_number) && Double.parseDouble(allorderssum) > Double.parseDouble(jiaoyi_money)  ){
  	 							ServiceHelper.getService_performanceService().editAllIsOk(store_id);
  	 						}
   	 					}
  	  				}
  	 			}
 			}
   			pd=null;
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString());
			(new TongYong()).dayinerro(e);
		}
	}
	
	public static void main(String[] args) {
		String s="1.22";
		if(s.contains(".")){
			String number=s.substring(0,s.indexOf("."));
			System.out.println(number.length());
		}
		
		
	}
  	
	
	
	/**
	 * 
	 * 方法名称:：getRedByMemberIsOk 
	 * 方法描述：判断当前会员是否满足领取红包条件
	 * 创建人：魏汉文
	 * 创建时间：2016年7月21日 上午10:48:06
	 */
	public static boolean getRedByMemberIsOk(PageData mpd,PageData redpd){
		boolean flag=false;
		PageData pd=new PageData();
		try {
			if(mpd == null){
				return true;
			}
			//用户范围,发放范围
			String member_id=mpd.getString("member_id");
			String store_id=redpd.getString("store_id");
			pd.put("member_id", member_id);
			pd.put("store_id", store_id);
   			String srp_opentype_id = redpd.getString("srp_opentype_id");
 			String[] str = srp_opentype_id.split(",");
 			String nowstr="";
 			String newstr="";
  			for (int i = 0; i < str.length; i++) {
  				nowstr=nowstr+str[i];
				//一度人脉
  				List<PageData> renmaiYiList = ServiceHelper.getStorepc_redpacketsService().renmaiYiList(pd);//商家一度人脉集合
				if(str[i].equals("1")){
					if(renmaiYiList.size() > 0){
						for (PageData e : renmaiYiList) {
							if(e.getString("contacts_id").equals(member_id)){
								newstr=newstr+"1";
 							}
 						}
					} 
				}else if(str[i].equals("2")){//二度人脉
					if(renmaiYiList.size() > 0){
						for (int j = 0; j < renmaiYiList.size(); j++) {
							String memberId = renmaiYiList.get(j).getString("contacts_id");
							PageData twomempd=new PageData();
							twomempd.put("member_id", memberId);
							//根据一度人脉的人脉id查询二度人脉的人脉信息
							List<PageData> renmaiErList = ServiceHelper.getStorepc_redpacketsService().renmaiErList(twomempd);
							if(renmaiErList.size() > 0 ){
								for (PageData e : renmaiErList) {
									if(e.getString("contacts_id").equals(member_id)){
										newstr=newstr+"2";
		 							}
		 						}
							}
							twomempd=null;
						}
					}
				}else if(str[i].equals("3")){//收藏本 店会员
					List<PageData> shoucangList = ServiceHelper.getStorepc_redpacketsService().shoucangList(pd);
					if(shoucangList.size() > 0){
						for (PageData e : shoucangList) {
							if(e.getString("member_id").equals(member_id)){
								newstr=newstr+"3";
 							}
 						}
					}
					shoucangList=null;
				}else if(str[i].equals("4")){//500米以内
					//获取经度纬度与所有商家距离进行比较
 					double  longitude1=0;//用户经度
					double latitude1=0;//用户纬度
					//判断是否定位了
					boolean isdingwei=true;
					if(mpd.getString("longitude") == null || mpd.getString("latitude").equals("")){
						isdingwei=false;
					}else{
						longitude1=Double.parseDouble(mpd.getString("longitude"));
			 			latitude1=Double.parseDouble(mpd.getString("latitude"));
					}
					//经纬度
					double longitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("longitude"));//商家经度
					double latitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("latitude"));//商家纬度
					if(isdingwei){
							double overdistance=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
							if(500 >= overdistance){
								newstr=newstr+"4";
							} 
					} 
				}else if(str[i].equals("5")){//1000米以内
					//获取经度纬度与所有商家距离进行比较
 					double  longitude1=0;//用户经度
					double latitude1=0;//用户纬度
					//判断是否定位了
					boolean isdingwei=true;
					if(mpd.getString("longitude") == null || mpd.getString("latitude").equals("")){
						isdingwei=false;
					}else{
						longitude1=Double.parseDouble(mpd.getString("longitude"));
			 			latitude1=Double.parseDouble(mpd.getString("latitude"));
					}
					//经纬度
					double longitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("longitude"));//商家经度
					double latitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("latitude"));//商家纬度
					if(isdingwei){
							double overdistance=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
							if(1000 >= overdistance){
								newstr=newstr+"5";
							} 
					} 
 				}else if(str[i].equals("6")){//消费过得会员
					List<PageData> xiaofeiList = ServiceHelper.getStorepc_redpacketsService().xiaofeiList(pd);
					if(xiaofeiList.size() > 0){
						newstr=newstr+"6";
					}
					xiaofeiList=null;
				}else if(str[i].equals("7")){//2000米以内
					//获取经度纬度与所有商家距离进行比较
 					double  longitude1=0;//用户经度
					double latitude1=0;//用户纬度
					//判断是否定位了
					boolean isdingwei=true;
					if(mpd.getString("longitude") == null || mpd.getString("latitude").equals("")){
						isdingwei=false;
					}else{
						longitude1=Double.parseDouble(mpd.getString("longitude"));
			 			latitude1=Double.parseDouble(mpd.getString("latitude"));
					}
					//经纬度
					double longitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("longitude"));//商家经度
					double latitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("latitude"));//商家纬度
					if(isdingwei){
							double overdistance=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
							if(2000 >= overdistance){
								newstr=newstr+"7";
							} 
					} 
				}else if(str[i].equals("8")){//5000米以内
					//获取经度纬度与所有商家距离进行比较
 					double  longitude1=0;//用户经度
					double latitude1=0;//用户纬度
					//判断是否定位了
					boolean isdingwei=true;
					if(mpd.getString("longitude") == null || mpd.getString("latitude").equals("")){
						isdingwei=false;
					}else{
						longitude1=Double.parseDouble(mpd.getString("longitude"));
			 			latitude1=Double.parseDouble(mpd.getString("latitude"));
					}
					//经纬度
					double longitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("longitude"));//商家经度
					double latitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("latitude"));//商家纬度
					if(isdingwei){
							double overdistance=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
							if(5000 >= overdistance){
								newstr=newstr+"8";
							} 
					} 
				}else if(str[i].equals("9")){//所在县市
					 if(mpd.getString("area_name").trim().equals(ServiceHelper.getAppStoreService().findById(pd).getString("area_name").trim())){
						 newstr=newstr+"9";
					 }
				}else if(str[i].equals("10")){//所在城市
					if(mpd.getString("city_name").equals(ServiceHelper.getAppStoreService().findById(pd).getString("city_name"))){
						 newstr=newstr+"10";
					 }
				}else if(str[i].equals("11")){//我的盟友的会员
					String mystore_id=pd.getString("mystore_id");
					PageData storepd=new PageData();
						if(!mystore_id.equals("")){
								storepd.put("store_id", mystore_id);
								List<PageData> oneUnionList = ServiceHelper.getStorepc_redpacketsService().renmaiYiList(storepd);//一度人脉集合
								if(oneUnionList.size() > 0){
									for (int j = 0; j < oneUnionList.size(); j++) {
											if(oneUnionList.get(j).getString("contacts_id").equals(member_id)){
												 newstr=newstr+"11";
											}else{
												if(oneUnionList.get(j).getString("contacts_id") != null && !oneUnionList.get(j).getString("contacts_id").equals("")){
	 												storepd.put("member_id", oneUnionList.get(j).getString("contacts_id"));
													//根据一度人脉的人脉id查询二度人脉的人脉信息
													List<PageData> renmaiErList = ServiceHelper.getStorepc_redpacketsService().renmaiErList(storepd);
													if(renmaiErList.size() > 0 ){
														for (int k = 0; k < renmaiErList.size(); k++) {
															if(renmaiErList.get(k).getString("contacts_id").equals(member_id)){
																newstr=newstr+"11";
															}
						 								}
													}
													renmaiErList=null;
												}
											}
 											
			 						}
								}
								oneUnionList=null;
						}
						storepd=null;
				}else if(str[i].equals("12")){//本店会员
					List<PageData> memberList = ServiceHelper.getStorepc_redpacketsService().allmemberList(pd);
					if(memberList.size() > 0){
						newstr=newstr+"12";
					}
					memberList=null;
				}
			}
  			if(nowstr.equals(newstr)){
  				flag=true;
  			}
		} catch (Exception e) {
			// TODO: handle exception
			(new TongYong()).dayinerro(e);
			return false;
		}
		return flag;
	}
	
	
	/**
	 * 
	 * 方法名称:：getRedByMemberIsOk 
	 * 方法描述：判断满足领取红包条件的所有会员进行推送
	 * 创建人：魏汉文
	 * 创建时间：2016年7月21日 上午10:48:06
	 */
	public static boolean getMemberByRedSrpTuiSong(PageData redpd){
		boolean flag=true;
		PageData pd=new PageData();
		try {
			//用户范围,发放范围
			pd.put("store_id", redpd.getString("store_id"));
    		String srp_opentype_id = redpd.getString("srp_opentype_id");
    		String store_redpackets_id = redpd.getString("store_redpackets_id");
 			String[] str = srp_opentype_id.split(",");
 			String memberstr="";
 			String tuisongmemberstr="";
   			for (int i = 0; i < str.length; i++) {
 				//一度人脉
  				List<PageData> renmaiYiList = ServiceHelper.getStorepc_redpacketsService().renmaiYiList(pd);//商家一度人脉集合
				if(str[i].equals("1")){
					String s="";
					if(memberstr == "" ){
						for (PageData e : renmaiYiList) {
							memberstr+=e.getString("member_id")+",";;
						}
					}else{
						for (PageData e : renmaiYiList) {
							if(memberstr.contains(e.getString("member_id"))){
								s+=e.getString("member_id")+",";
							}
						}
						memberstr=s;
					}
 				}else if(str[i].equals("2")){//二度人脉
					if(renmaiYiList.size() > 0){
						for (int j = 0; j < renmaiYiList.size(); j++) {
							String memberId = renmaiYiList.get(j).getString("contacts_id");
							PageData twomempd=new PageData();
							twomempd.put("member_id", memberId);
							//根据一度人脉的人脉id查询二度人脉的人脉信息
							List<PageData> renmaiErList = ServiceHelper.getStorepc_redpacketsService().renmaiErList(twomempd);
							if(renmaiErList.size() > 0 ){
								String s="";
								if(memberstr == "" ){
									for (PageData e : renmaiErList) {
										memberstr+=e.getString("member_id")+",";
									}
								}else{
									for (PageData e : renmaiErList) {
										if(memberstr.contains(e.getString("member_id"))){
											s+=e.getString("member_id")+",";
										}
									}
									memberstr=s;
								}
 							}
							twomempd=null;
						}
					}
				}else if(str[i].equals("3")){//收藏本 店会员
					List<PageData> shoucangList = ServiceHelper.getStorepc_redpacketsService().shoucangList(pd);
					if(shoucangList.size() > 0){
						String s="";
						if(memberstr == "" ){
							for (PageData e : shoucangList) {
								memberstr+=e.getString("member_id")+",";
 							}
						}else{
							for (PageData e : shoucangList) {
								if(memberstr.contains(e.getString("member_id"))){
									s+=e.getString("member_id")+",";
								}
							}
							memberstr=s;
						}
						//推送操作
						String s1="";
						if(tuisongmemberstr == "" ){
							for (PageData e : shoucangList) {
								tuisongmemberstr+=e.getString("member_id")+",";
 							}
						}else{
							for (PageData e : shoucangList) {
								if(tuisongmemberstr.contains(e.getString("member_id"))){
									s1+=e.getString("member_id")+",";
								}
							}
							tuisongmemberstr=s1;
						}
						//========
					}
					shoucangList=null;
				}else if(str[i].equals("4")){//500米以内
					//获取经度纬度与所有商家距离进行比较
 					double  longitude1=0;//用户经度
					double latitude1=0;//用户纬度
					String s="";
					if(memberstr == "" ){
						for(PageData mpd:ServiceHelper.getAppMemberService().listAllMember(pd)){
							//判断是否定位了
							boolean isdingwei=true;
							if(mpd.getString("longitude") == null || mpd.getString("latitude").equals("")){
								isdingwei=false;
							}else{
								longitude1=Double.parseDouble(mpd.getString("longitude"));
					 			latitude1=Double.parseDouble(mpd.getString("latitude"));
							}
							//经纬度
							double longitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("longitude"));//商家经度
							double latitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("latitude"));//商家纬度
							if(isdingwei){
									double overdistance=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
									if(500 >= overdistance){
										String member_id=mpd.getString("member_id");
										memberstr+=member_id+",";
									} 
	 						} 
						}
					}else{
 						for(PageData mpd:ServiceHelper.getAppMemberService().listAllMember(pd)){
							//判断是否定位了
							boolean isdingwei=true;
							if(mpd.getString("longitude") == null || mpd.getString("latitude").equals("")){
								isdingwei=false;
							}else{
								longitude1=Double.parseDouble(mpd.getString("longitude"));
					 			latitude1=Double.parseDouble(mpd.getString("latitude"));
							}
							//经纬度
							double longitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("longitude"));//商家经度
							double latitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("latitude"));//商家纬度
							if(isdingwei){
									double overdistance=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
									if(500 >= overdistance){
										String member_id=mpd.getString("member_id");
										if(memberstr.contains(member_id)){
											s+=member_id+",";
										}
										
									} 
	 						} 
						}
 						memberstr=s;
 					}
					 
 				}else if(str[i].equals("5")){//1000米以内
					//获取经度纬度与所有商家距离进行比较
 					double  longitude1=0;//用户经度
					double latitude1=0;//用户纬度
					String s="";
					if(memberstr == "" ){
						for(PageData mpd:ServiceHelper.getAppMemberService().listAllMember(pd)){
							//判断是否定位了
							boolean isdingwei=true;
							if(mpd.getString("longitude") == null || mpd.getString("latitude").equals("")){
								isdingwei=false;
							}else{
								longitude1=Double.parseDouble(mpd.getString("longitude"));
					 			latitude1=Double.parseDouble(mpd.getString("latitude"));
							}
							//经纬度
							double longitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("longitude"));//商家经度
							double latitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("latitude"));//商家纬度
							if(isdingwei){
									double overdistance=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
									if(1000 >= overdistance){
										String member_id=mpd.getString("member_id");
										memberstr+=member_id+",";
									} 
	 						} 
						}
					}else{

						for(PageData mpd:ServiceHelper.getAppMemberService().listAllMember(pd)){
							//判断是否定位了
							boolean isdingwei=true;
							if(mpd.getString("longitude") == null || mpd.getString("latitude").equals("")){
								isdingwei=false;
							}else{
								longitude1=Double.parseDouble(mpd.getString("longitude"));
					 			latitude1=Double.parseDouble(mpd.getString("latitude"));
							}
							//经纬度
							double longitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("longitude"));//商家经度
							double latitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("latitude"));//商家纬度
							if(isdingwei){
									double overdistance=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
									if(1000 >= overdistance){
										String member_id=mpd.getString("member_id");
										if(memberstr.contains(member_id)){
											s+=member_id+",";
										}
 									} 
	 						} 
						}
						memberstr=s;
 					}
					
 				}else if(str[i].equals("6")){//消费过得会员
					List<PageData> xiaofeiList = ServiceHelper.getStorepc_redpacketsService().xiaofeiList(pd);
					if(xiaofeiList.size() > 0){
 							 	String s="";
								if(memberstr == "" ){
									for (PageData e : xiaofeiList) {
										memberstr+=e.getString("member_id")+",";
									}
								}else{
									for (PageData e : xiaofeiList) {
										if(memberstr.contains(e.getString("member_id"))){
											s+=e.getString("member_id")+",";
										}
									}
									memberstr=s;
								}
								//推送操作
								String s1="";
								if(tuisongmemberstr == "" ){
									for (PageData e : xiaofeiList) {
										tuisongmemberstr+=e.getString("member_id")+",";
									}
								}else{
									for (PageData e : xiaofeiList) {
										if(tuisongmemberstr.contains(e.getString("member_id"))){
											s1+=e.getString("member_id")+",";
										}
									}
									tuisongmemberstr=s1;
								}
								//========
 					}
					xiaofeiList=null;
				}else if(str[i].equals("7")){//2000米以内
					//获取经度纬度与所有商家距离进行比较
 					double  longitude1=0;//用户经度
					double latitude1=0;//用户纬度
					String s="";
					if(memberstr == "" ){
						for(PageData mpd:ServiceHelper.getAppMemberService().listAllMember(pd)){
							//判断是否定位了
							boolean isdingwei=true;
							if(mpd.getString("longitude") == null || mpd.getString("latitude").equals("")){
								isdingwei=false;
							}else{
								longitude1=Double.parseDouble(mpd.getString("longitude"));
					 			latitude1=Double.parseDouble(mpd.getString("latitude"));
							}
							//经纬度
							double longitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("longitude"));//商家经度
							double latitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("latitude"));//商家纬度
							if(isdingwei){
									double overdistance=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
									if(2000 >= overdistance){
										String member_id=mpd.getString("member_id");
										memberstr+=member_id+",";
									} 
	 						} 
						}
					}else{
 						for(PageData mpd:ServiceHelper.getAppMemberService().listAllMember(pd)){
							//判断是否定位了
							boolean isdingwei=true;
							if(mpd.getString("longitude") == null || mpd.getString("latitude").equals("")){
								isdingwei=false;
							}else{
								longitude1=Double.parseDouble(mpd.getString("longitude"));
					 			latitude1=Double.parseDouble(mpd.getString("latitude"));
							}
							//经纬度
							double longitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("longitude"));//商家经度
							double latitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("latitude"));//商家纬度
							if(isdingwei){
									double overdistance=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
									if(2000 >= overdistance){
										String member_id=mpd.getString("member_id");
										if(memberstr.contains(member_id)){
											s+=member_id+",";
										}
 									} 
	 						} 
						}
 						memberstr=s;
 					}
				}else if(str[i].equals("8")){//5000米以内
					//获取经度纬度与所有商家距离进行比较
 					double  longitude1=0;//用户经度
					double latitude1=0;//用户纬度
					String s="";
					if(memberstr == "" ){
						for(PageData mpd:ServiceHelper.getAppMemberService().listAllMember(pd)){
							//判断是否定位了
							boolean isdingwei=true;
							if(mpd.getString("longitude") == null || mpd.getString("latitude").equals("")){
								isdingwei=false;
							}else{
								longitude1=Double.parseDouble(mpd.getString("longitude"));
					 			latitude1=Double.parseDouble(mpd.getString("latitude"));
							}
							//经纬度
							double longitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("longitude"));//商家经度
							double latitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("latitude"));//商家纬度
							if(isdingwei){
									double overdistance=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
									if(5000 >= overdistance){
										String member_id=mpd.getString("member_id");
										memberstr+=member_id+",";
									} 
	 						} 
						}
					}else{
 						for(PageData mpd:ServiceHelper.getAppMemberService().listAllMember(pd)){
							//判断是否定位了
							boolean isdingwei=true;
							if(mpd.getString("longitude") == null || mpd.getString("latitude").equals("")){
								isdingwei=false;
							}else{
								longitude1=Double.parseDouble(mpd.getString("longitude"));
					 			latitude1=Double.parseDouble(mpd.getString("latitude"));
							}
							//经纬度
							double longitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("longitude"));//商家经度
							double latitude2=Double.parseDouble(ServiceHelper.getAppStoreService().findById(pd).getString("latitude"));//商家纬度
							if(isdingwei){
									double overdistance=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
									if(5000 >= overdistance){
										String member_id=mpd.getString("member_id");
										if(memberstr.contains(member_id)){
											s+=member_id+",";
										}
										
									} 
	 						} 
						}
 						memberstr=s;
 					}
				}else if(str[i].equals("9")){//所在县市
					String s="";
					if(memberstr == "" ){
						for(PageData mpd:ServiceHelper.getAppMemberService().listAllMember(pd)){
							memberstr+=mpd.getString("member_id")+",";
 						}
					}else{
						for(PageData mpd:ServiceHelper.getAppMemberService().listAllMember(pd)){
							if(mpd.getString("area_name").trim().equals(ServiceHelper.getAppStoreService().findById(pd).getString("area_name").trim())){
								 s+=mpd.getString("member_id")+",";
							 }
						}
						memberstr=s;
					}
 				}else if(str[i].equals("10")){//所在城市
 					String s="";
					if(memberstr == "" ){
						for(PageData mpd:ServiceHelper.getAppMemberService().listAllMember(pd)){
							memberstr+=mpd.getString("member_id")+",";
 						}
					}else{
						for(PageData mpd:ServiceHelper.getAppMemberService().listAllMember(pd)){
							if(mpd.getString("city_name").trim().equals(ServiceHelper.getAppStoreService().findById(pd).getString("area_name").trim())){
								 s+=mpd.getString("member_id")+",";
							 }
						}
						memberstr=s;
					}
				}else if(str[i].equals("11")){//我的盟友的会员
					String mystore_id=pd.getString("mystore_id");
					PageData storepd=new PageData();
						if(!mystore_id.equals("")){
								storepd.put("store_id", mystore_id);
								List<PageData> oneUnionList = ServiceHelper.getStorepc_redpacketsService().renmaiYiList(storepd);//一度人脉集合
								if(oneUnionList.size() > 0){
									for (int j = 0; j < oneUnionList.size(); j++) {
										String s="";
										if(memberstr == "" ){
											memberstr+=oneUnionList.get(j).getString("member_id");
											if(oneUnionList.get(j).getString("contacts_id") != null && !oneUnionList.get(j).getString("contacts_id").equals("")){
 												storepd.put("member_id", oneUnionList.get(j).getString("contacts_id"));
												//根据一度人脉的人脉id查询二度人脉的人脉信息
												List<PageData> renmaiErList = ServiceHelper.getStorepc_redpacketsService().renmaiErList(storepd);
												if(renmaiErList.size() > 0 ){
													for (int k = 0; k < renmaiErList.size(); k++) {
														memberstr+=oneUnionList.get(j).getString("member_id")+",";
 					 								}
												}
												renmaiErList=null;
											}
										}else{
 											if(memberstr.contains(oneUnionList.get(j).getString("member_id"))){
												 s+=oneUnionList.get(j).getString("member_id")+",";
											}else{
												if(oneUnionList.get(j).getString("contacts_id") != null && !oneUnionList.get(j).getString("contacts_id").equals("")){
	 												storepd.put("member_id", oneUnionList.get(j).getString("contacts_id"));
													//根据一度人脉的人脉id查询二度人脉的人脉信息
													List<PageData> renmaiErList = ServiceHelper.getStorepc_redpacketsService().renmaiErList(storepd);
													if(renmaiErList.size() > 0 ){
														for (int k = 0; k < renmaiErList.size(); k++) {
															if(memberstr.contains(renmaiErList.get(j).getString("member_id"))){
																 s+=oneUnionList.get(j).getString("member_id")+",";
															}
						 								}
													}
													renmaiErList=null;
												}
											}
 											memberstr=s;
										}
 			 						}
								}
								oneUnionList=null;
						}
						storepd=null;
				}else if(str[i].equals("12")){//本店会员
					List<PageData> memberList = ServiceHelper.getStorepc_redpacketsService().allmemberList(pd);
					if(memberList.size() > 0){
						String s="";
						if(memberstr == "" ){
							for (PageData e : memberList) {
								memberstr+=e.getString("member_id")+",";
							}
						}else{
							for (PageData e : memberList) {
								if(memberstr.contains(e.getString("member_id"))){
									s+=e.getString("member_id")+",";
								}
							}
							memberstr=s;
						}
						//推送操作
						String s1="";
						if(tuisongmemberstr == "" ){
							for (PageData e : memberList) {
								tuisongmemberstr+=e.getString("member_id")+",";
							}
						}else{
							for (PageData e : memberList) {
								if(tuisongmemberstr.contains(e.getString("member_id"))){
									s1+=e.getString("member_id")+",";
								}
							}
							tuisongmemberstr=s1;
						}
						//========
					}
					memberList=null;
				}
			}
   			String store_id=ServiceHelper.getAppStoreService().findById(pd).getString("store_id");
   			String store_name=ServiceHelper.getAppStoreService().findById(pd).getString("store_name");
//   			List<String> list = new ArrayList<String>();
//   		String[] ss=memberstr.split(",");
//    		for (int k = 0; k < ss.length; k++) {
//   				String member_id=ss[k];
//   				if(!list.contains(member_id)){
//   					list.add(member_id);
//    			}
//			}
//   		for (String member_id : list) {
//			//满足会员的等待进行操作
//   				
//  		}
   			List<String> list = new ArrayList<String>();
   			String[] tuisongstr=tuisongmemberstr.split(",");
   			for (int k = 0; k < tuisongstr.length; k++) {
				String member_id=tuisongstr[k];
				if(!list.contains(member_id)){
					list.add(member_id);
				}
   			}
   			for (String member_id : list) {
				//开始推送
   				PageData _pd=new PageData();
   				_pd.put("member_id", member_id);
   				_pd=ServiceHelper.getAppMemberService().findById(_pd);
      			if(_pd != null && _pd.getString("istuisong").equals("1")){
     				sendTuiSong( _pd.getString("member_id"), store_id, "8", _pd.getString("member_id"),"2", store_name, store_redpackets_id);
     			}
  			}
		} catch (Exception e) {
			// TODO: handle exception
			(new TongYong()).dayinerro(e);
			return false;
		}
		return flag;
	}
	
	
	/**
	 * 导流商家--最近的12位商家
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public static List<PageData> daoliuList(PageData pd) throws Exception{
		 List<PageData> daoLiuStoreList=new ArrayList<PageData>();
		try {
			List<PageData> storeList=ServiceHelper.getAppStoreService().listJlMinMoreStore(ServiceHelper.getAppStoreService().findById(pd));
 			int n=storeList.size();
 			PageData daoliupd=null;
 			for (int i = 0; i < n ; i++) {
				daoliupd=storeList.get(i);
				daoliupd.put("daoliurecord_id", BaseController.get4Integer());
				daoliupd.put("zhu_store_id", pd.getString("store_id"));
				daoliupd.put("ci_store_id", daoliupd.getString("store_id"));
				daoliupd.put("sk_shop",BaseController.get4ZMSZ()+EbotongSecurity.ebotongEncrypto(daoliupd.getString("store_id")));
 	 	 		if(ServiceHelper.getStoreService().findById(daoliupd) != null){
	 	 			daoliupd.put("sort_name", "经营类别："+ServiceHelper.getStoreService().findById(daoliupd).getString("sort_name"));
	 	 			daoliupd.put("store_name", ServiceHelper.getStoreService().findById(daoliupd).getString("store_name"));
	 	 			daoliupd.put("comment_score", ServiceHelper.getStoreService().findById(daoliupd).getString("comment_score"));
	 	 			daoliupd.put("pictrue_url", ServiceHelper.getStoreService().findById(daoliupd).getString("pictrue_url"));
	 	 			daoliupd.put("transaction_times", ServiceHelper.getStoreService().findById(daoliupd).get("transaction_times").toString());
  	 	 			//获取营销规则
	 	 			List<PageData> marketlist=ServiceHelper.getAppStorepc_marketingService().listAllMarketing(daoliupd);
	 	 			if(marketlist.size() > 0){
	 	 				daoliupd.put("marketlist", marketlist);
		 	 			daoLiuStoreList.add(daoliupd);
	 	 			}
  	 	 	   }
 	 	 		daoliupd=null;
			}
		} catch (Exception e) {
 			(new TongYong()).dayinerro(e);
		}
		 return daoLiuStoreList;
	}
	
	
	
//	/**
//	 * 导流列表后台取
//	 * @param pd
//	 * @return
//	 * @throws Exception
//	 */
//	public static List<PageData> daoliuList(PageData pd) throws Exception{
//			 List<PageData> daoLiuStoreList=new ArrayList<PageData>();
//			 try {
//				//获取优选推荐商家列表:商家头像
// 	 				List<PageData> getStoreList=ServiceHelper.getStoreDaoLiuService().listAllStoreByStoreId(pd);
//	 				for (PageData daoliupd : getStoreList) {
//	 					PageData spd=new PageData();
//	 					spd.put("store_id", daoliupd.getString("ci_store_id"));
//	 					//判断当前商家的余额是否满足200，不满足则不显示
//	 					double nowmoney=Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(spd));
//	 					if(nowmoney >= Const.minWealthShow){
//	 						spd=ServiceHelper.getStoreService().findById(spd);
//	 	 					if(spd != null){
//	 	 						daoliupd.put("sort_name", "经营类别："+spd.getString("sort_name"));
//	 	 						daoliupd.put("store_name", spd.getString("store_name"));
//	 	 						daoliupd.put("comment_score", spd.getString("comment_score"));
//	 	 						daoliupd.put("pictrue_url", spd.getString("pictrue_url"));
//	 	 						daoliupd.put("transaction_times", spd.get("transaction_times").toString());
//  	 	 						//获取营销规则
//	 	 						List<PageData> marketlist=ServiceHelper.getAppStorepc_marketingService().listAllMarketing(spd);
//	 	 						daoliupd.put("marketlist", marketlist);
//	 	 						daoLiuStoreList.add(daoliupd);
//	 	 						spd=null;
//	 	 	 				}
//	 					} 
//	  	 			}
//  			} catch (Exception e) {
//				// TODO: handle exception
//				//System.out.println("导流列表"+e.toString());
//				(new TongYong()).dayinerro(e);
// 			}
//			 return daoLiuStoreList;
//	}
	
	
//	/**
//	 * 导流点击扣费方法
//	 */
//	public synchronized static PageData DaoliuClickFee(PageData  pd){
//   		try {
//			//导流专属
//  			String member_id=pd.getString("member_id");
//			String daoliurecord_id=pd.getString("daoliurecord_id");
//			//判断是否从导流进入商家详情
// 			if(member_id != null && !member_id.equals("") && daoliurecord_id != null && daoliurecord_id.length() >=1){
// 				int click_number=ServiceHelper.getStoreDaoLiuService().countNowDayClickNumber(pd);
//  				PageData dlpd=ServiceHelper.getStoreDaoLiuService().daoliuByHzStoreDetail(pd);
//				if(dlpd != null){
//					String zhu_store_id=dlpd.getString("zhu_store_id");
//					String ci_store_id=dlpd.getString("ci_store_id");
//					String click_fee=dlpd.getString("click_fee");
//					pd.put("store_id", ci_store_id);
//					//当点击次数为0的时候进行操作
// 					if(click_number == 0){
//						PageData _pd=new PageData();
// 						//扣除被点击商家的金额
//						_pd.put("money", click_fee); 
//						_pd.put("store_id", ci_store_id); 
//						ServiceHelper.getAppStoreService().editJFWealthById(_pd);
//						//-----新增广告主商家积分
//						_pd=null;
//						_pd=new PageData();
//						String store_fee=df2.format(Double.parseDouble(click_fee)*0.8);
//						_pd.put("money", "-"+store_fee); 
//						_pd.put("store_id", zhu_store_id); 
//						ServiceHelper.getAppStoreService().editJFWealthById(_pd);
//						//-----系统获取的积分
//						String xitong_fee=df2.format(Double.parseDouble(click_fee)-Double.parseDouble(store_fee));
//						//-----记录导流记录
//						String  jiaoyi_id="DL"+BaseController.get32UUID();
//						PageData clickpd=new PageData();
//						clickpd.put("jiaoyi_id",jiaoyi_id);
//						clickpd.put("daoliurecord_id", dlpd.get("daoliurecord_id")+"");
//						clickpd.put("all_fee", click_fee);
//						clickpd.put("store_fee", store_fee);
//						clickpd.put("xitong_fee", xitong_fee);
//						clickpd.put("member_id", member_id);
//						ServiceHelper.getStoreDaoLiuService().saveMemberClick(clickpd);
//						clickpd=null;
//// 						PageData waterpd=new PageData();
////	    				waterpd.put("pay_status","1");
////	    	   			waterpd.put("waterrecord_id",jiaoyi_id);
////	   					waterpd.put("user_id", ci_store_id);
////	   					waterpd.put("user_type", "1");
////	    				waterpd.put("withdraw_rate","0");
////	    				waterpd.put("money_type","9");
////	   	 				waterpd.put("money", click_fee);
////	   	 				waterpd.put("reduce_money","0");
////	   					waterpd.put("arrivalmoney",  click_fee);
////		   				waterpd.put("nowuser_money",ServiceHelper.getAppStoreService().sumStoreWealth(pd));
////	   					waterpd.put("application_channel", "3");
////	   					waterpd.put("remittance_name",Const.payjiqi[4] );
////	   					waterpd.put("remittance_type","2" );
////	   					waterpd.put("nowpay_money",  click_fee );
////	   					waterpd.put("remittance_number",ServiceHelper.getAppStoreService().findById(pd).getString("registertel_phone"));//支付人的支付账号
////	    				waterpd.put("createtime",DateUtil.getTime());
////	   					waterpd.put("over_time",DateUtil.getTime());
////	   	  				waterpd.put("jiaoyi_type","0");
////	   					waterpd.put("payee_number",Const.jiuyunumber);
////	    	 			waterpd.put("order_tn", jiaoyi_id);
////	   					waterpd.put("province_name", ServiceHelper.getAppStoreService().findById(pd).getString("province_name"));
////	   					waterpd.put("city_name", ServiceHelper.getAppStoreService().findById(pd).getString("city_name"));
////	   					waterpd.put("area_name", ServiceHelper.getAppStoreService().findById(pd).getString("area_name"));
////	    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
////	    				waterpd=null; 
//					}
// 					
//				}
//				dlpd=null;
//			}
//  		} catch (Exception e) {
// 			//System.out.println("导流位置出错"+e.toString());
// 			(new TongYong()).dayinerro(e);
// 		}
//  		return pd;
//	}
//	
	
	/**
	 * 提货券d-----订单退货
	 */
	public synchronized static PageData TiHuoReturnOrder(PageData pd,String return_type){
		PageData ispd=new PageData();
 		String result = "1";
		String message="退货成功";
 		try{ 
   			if(ServiceHelper.getAppOrderService().tihuoByOrderId(pd) != null && ServiceHelper.getAppOrderService().tihuoByOrderId(pd).getString("order_status") != null && ServiceHelper.getAppOrderService().tihuoByOrderId(pd).getString("order_status").equals("1") && ServiceHelper.getAppOrderService().tihuoByOrderId(pd).getString("tihuo_status") != null && ServiceHelper.getAppOrderService().tihuoByOrderId(pd).getString("tihuo_status").equals("0")){
  					//未提货	
  					System.out.println("退货:"+pd.toString()+"======time="+new Date().getTime());
  					pd=ServiceHelper.getAppOrderService().tihuoByOrderId(pd);//订单详情
 					String order_id=pd.getString("order_id");	
 					//更改提货单状态,订单状态
			   		pd.put("order_status", "3");
			   		pd.put("tihuo_status", "99");
			   		ServiceHelper.getAppOrderService().editStatus(pd);
			   		//更新退货状态退款记录
		   			PageData wapd=new PageData();
		   			wapd.put("waterrecord_id", order_id);
		   			wapd.put("pay_status", "98");
		   			ServiceHelper.getWaterRecordService().editWaterRecord(wapd);
		   			wapd=null;
	   		 		String member_id=pd.getString("payer_id");
	 		 		pd.put("member_id", member_id);
	 		 		String user_balance=pd.getString("user_balance");
	  		 		String user_integral=pd.getString("user_integral");
	 		 		String actual_money=pd.getString("actual_money");
   		 			if(ServiceHelper.getAppMemberService().findById(pd) != null){
			 				//个人余额消费历史
			 				double _money=Double.parseDouble(user_balance )+Double.parseDouble( actual_money);
			 				double integral=Double.parseDouble(user_integral);
			 				PageData moneypd=new PageData();
			   				if(_money>0){
			   					//更新金钱
								moneypd.put("member_id", member_id);
								moneypd.put("now_money", df2.format(Double.parseDouble(ServiceHelper.getAppMemberService().findWealthById(pd).getString("now_money"))+_money));
								ServiceHelper.getAppMemberService().edit(moneypd);
								//新增个人积分消费历史
			 					moneypd.put("wealth_type", "2");
								moneypd.put("consume_type", "1");
								moneypd.put("jiaoyi_id", order_id);
								moneypd.put("content", "未提货退余额");
								moneypd.put("number", df2.format(_money));
								moneypd.put("in_jiqi", pd.getString("in_jiqi"));
								moneypd.put("now_money",ServiceHelper.getAppMemberService().findWealthById(pd).getString("now_money"));
								moneypd.put("jiaoyi_status", "1");
		   						moneypd.put("member_wealthhistory_id", BaseController.getXFUID(ServiceHelper.getAppMemberService().findById(pd).getString("show_lookid")));
								ServiceHelper.getAppMember_wealthhistoryService().saveWealthhistory(moneypd);
			   				}
			   				moneypd.clear();
							if(integral >0){
								//更新积分
			  					 moneypd.put("member_id", member_id);
		 						 moneypd.put("now_integral", df2.format(Double.parseDouble(ServiceHelper.getAppMemberService().findWealthById(pd).getString("now_integral"))+integral));
		 						 ServiceHelper.getAppMemberService().edit(moneypd);
							     //新增个人积分消费历史
								  moneypd.put("wealth_type", "1");
								  moneypd.put("consume_type", "1");
								  moneypd.put("jiaoyi_id", order_id);
								  moneypd.put("content",  "未提货退积分");
								  moneypd.put("number", df2.format(integral));
								  moneypd.put("in_jiqi", pd.getString("in_jiqi"));
								  moneypd.put("now_money", ServiceHelper.getAppMemberService().findWealthById(pd).getString("now_integral"));
								  moneypd.put("jiaoyi_status", "1");
			   					  moneypd.put("member_wealthhistory_id", BaseController.getXFUID(ServiceHelper.getAppMemberService().findById(pd).getString("show_lookid")));
								  ServiceHelper.getAppMember_wealthhistoryService().saveWealthhistory(moneypd);
							}
		 			   		//红包ID
					   		if(pd.getString("redpackage_id") != null && !pd.getString("redpackage_id").equals("")){
					   					pd.put("isshiyong", "0");
					   					ServiceHelper.getAppMember_redpacketsService().editRedStatus(pd);
					   					ServiceHelper.getAppMemberService().updateMemberRedNumber(pd);
					   		}
					   	    //获取商品信息-退货将商品退回原处
							List<PageData> goodsList=ServiceHelper.getAppGoodsService().getGoodsIdByOrder(pd);
							for(PageData goodspd : goodsList){
	  								PageData gpd=new PageData();
	  								if(goodspd.getString("goods_type").equals("2")){
		  								  //判断库存改状态
		  				 				  PageData goodsggpd=ServiceHelper.getYouXuanService().finddetailgg(goodspd);
		  				 				  if(goodsggpd != null ){
		  				 					  PageData xpd=new PageData();
		  				 					  String dqendtime=goodsggpd.getString("goodsggpd");
		  				 					  long l1=DateUtil.fomatDate1(dqendtime).getTime();
		  				 					  long l2=(new Date()).getTime();
		  				 					  if(l2 > l1){
		  				 						xpd.put("goods_status", "99");
		  				 					  }else{
		  				 						xpd.put("goods_status", "4");
		  				 					  }
		  				 					  xpd.put("youxuangoods_id", goodsggpd.getString("youxuangoods_id"));
		  				 					  ServiceHelper.getYouXuanService().editGoods(xpd);
		  				 					  xpd=null;
		  				 				  } 
		  				 				 gpd.put("goods_number", goodspd.getString("shop_number"));
  									 gpd.put("youxuangg_id", goodspd.getString("goods_id"));
  									 ServiceHelper.getYouXuanService().updateYouXuanGoodsBuyNumber(gpd);
	  								}else{
	  									gpd.put("goods_number", goodspd.getString("shop_number"));
	  									gpd.put("goods_id", goodspd.getString("goods_id"));
	  									ServiceHelper.getAppGoodsService().updateGoodsBuyNumber(gpd);
	  								}
	  								gpd=null;
	  		 					}
		 				}
		 				if(return_type.equals("0")){//到期退款
		 		 			TongYong.sendTuiSong(member_id, order_id, "10", member_id, "2", "", "");
		 		 		}else{
		 		 			TongYong.sendTuiSong(member_id, order_id, "10", member_id, "2", "", "");
		 		 		}
   			}else{
 				result="0";
 				message="当前提货券不存在";
 			}
  		} catch(Exception e){
 			result="0";
			message="系统异常";
			//System.out.println("退货======"+e.toString());
			(new TongYong()).dayinerro(e);
 		}
		ispd.put("result", result);
		ispd.put("message", message);
		ispd.put("data", "");
  		return ispd;
	}
	
	

	/**
	 * 判断当前号码是否可以发送验证码
	 * type:1-有次数限制，2-无限制
	 */
  	public static PageData Okmessage(String phone,String type){
 		PageData pd = new PageData();
 		pd.put("result", "1");
		pd.put("message", "请稍等，短信正在发送中...");
		pd.put("data", "");
		try{ 
			if(type.equals("1")){
				//获取电话获取验证码次数map---判断是否超出次数
				if(Const.numberphone.get(phone) == null){
					Const.numberphone.put(phone, 1);
				}else{
					int number=Const.numberphone.get(phone);
					if(number >= Const.phone_sendmessage_number){
						pd.put("result", "0");
						pd.put("message", "当天的短信次数已用尽，请明天再尝试");
						pd.put("data", "");
					}
				}
			}
  			//获取电话集合--包含
			if(Const.xzphone.contains(phone)){
				pd.put("result", "0");
				pd.put("message", "操作过于频繁，请稍后再尝试一下");
				pd.put("data", "");
			}else{
				Const.xzphone.add(phone);
				MessageTask messageTask=new MessageTask(phone);
				Timer tt=new Timer();
				tt.schedule(messageTask,54*1000);//定时54秒
			}
// 		   if(!StringUtil.isChinaPhoneLegal(phone)){
//				pd.put("result", "0");
//				pd.put("message", "手机格式有误");
//				pd.put("data", "");
// 			}
		} catch(Exception e){
  			//System.out.println(e.toString()+"判断当前号码是否可以发送验证码========================");
  			(new TongYong()).dayinerro(e);
		}
		return pd;
 	}
	
   	 
  	
  	
  	/**
  	 * 统计服务商月销售
  	 */
  	public static void spMonth_number(int number){//0-当月，-1.......
		 PageData pd=new PageData();
 		 try{
				 Calendar c = Calendar.getInstance();
			     c.add(Calendar.MONTH, number);
			     String prevmonth=new SimpleDateFormat("yyyy-MM").format(c.getTime());
		         //System.out.println("统计月是："+prevmonth);
//		         pd.put("month", prevmonth);
		    	 //获取所有服务商
		         pd.put("sp_status", "1");
		         List<PageData> allsplist=ServiceHelper.getSp_fileService().listAllSp(pd);
		         for (PageData sppd : allsplist) {
		        	 PageData monthpd1=new PageData();
		        	 //获取当前month的商家人数：type1当月，2之前的月份包括当月
		        	sppd.put("month", prevmonth);
		        	sppd.put("type", "1"); 
		        	String actual_user_number=ServiceHelper.getService_performanceService().countMonthStore(sppd);
		        	String add_member=ServiceHelper.getService_performanceService().countMonthMember(sppd);
		        	//流水，积分
		        	sppd.put("money_type", "2");
		        	sppd.put("user_type", "2");
		        	PageData moneypd=ServiceHelper.getService_performanceService().countAllMonthByWater(sppd);
		        	String actual_water="";
		        	String add_jifen="";
		        	String add_koudian="";
		        	if(moneypd != null){
		        		actual_water= moneypd.get("summoney").toString();
		        		add_jifen= moneypd.get("sumjfsp_getmoney").toString();
		        		add_koudian= moneypd.get("sumordersp_getmoney").toString();
		        	}
		        	//销售（服务费）
		        	sppd.put("money_type", "3");
		        	sppd.put("user_type", "1");
 		        	moneypd.clear();
		        	moneypd=ServiceHelper.getService_performanceService().countAllMonthByWater(sppd);
		        	String add_xiaoshou="";
		        	if(moneypd != null){
		        		add_xiaoshou= moneypd.get("sumqitasp_getmoney").toString();
		        	}
		        	// 优选编辑费 
		        	sppd.put("money_type", "7");
		        	sppd.put("user_type", "1");
		        	moneypd.clear();
		        	moneypd=ServiceHelper.getService_performanceService().countAllMonthByWater(sppd);
		        	String add_guanggao1="";
		        	if(moneypd != null){
		        		add_guanggao1= moneypd.get("summoney").toString();
		        	}
		        	// 优选上架费  
		        	sppd.put("money_type", "8");
		        	sppd.put("user_type", "1");
		        	moneypd.clear();
		        	moneypd=ServiceHelper.getService_performanceService().countAllMonthByWater(sppd);
		        	String add_guanggao2="";
		        	if(moneypd != null){
		        		add_guanggao2= moneypd.get("summoney").toString();
		        	}
		        	//广告 编辑费 
		        	sppd.put("money_type", "13");
		        	sppd.put("user_type", "1");
		        	moneypd.clear();
		        	moneypd=ServiceHelper.getService_performanceService().countAllMonthByWater(sppd);
		        	String add_guanggao3="";
		        	if(moneypd != null){
		        		add_guanggao3= moneypd.get("summoney").toString();
		        	}
		        	//月销售收益费 
		        	sppd.put("money_type", "19");
		        	sppd.put("user_type", "3");
		        	moneypd.clear();
		        	moneypd=ServiceHelper.getService_performanceService().countAllMonthByWater(sppd);
		        	String add_guanggao4="";
		        	if(moneypd != null){
		        		add_guanggao4= moneypd.get("summoney").toString();
		        	}
  		        	sppd.remove("month");
		        	sppd.remove("type");
		        	sppd.remove("money_type");
		        	sppd.remove("user_type");
		        	//开始累计
		        	sppd.put("month", prevmonth);
		        	sppd.put("type", "2"); 
		        	String leiji_store=ServiceHelper.getService_performanceService().countMonthStore(sppd);
		        	String leiji_member=ServiceHelper.getService_performanceService().countMonthMember(sppd);
		        	//流水，积分
		        	sppd.put("money_type", "2");
		        	sppd.put("user_type", "2");
		        	moneypd.clear();
		        	moneypd=ServiceHelper.getService_performanceService().countAllMonthByWater(sppd);
		        	String leiji_water="";
		        	String leiji_jifen="";
		        	String leiji_koudian="";
		        	if(moneypd != null){
		        		leiji_water= moneypd.get("summoney").toString();
		        		leiji_jifen= moneypd.get("sumjfsp_getmoney").toString();
		        		leiji_koudian= moneypd.get("sumordersp_getmoney").toString();
		        	}
		        	//销售（服务费）
		        	sppd.put("money_type", "3");
		        	sppd.put("user_type", "1");
		        	moneypd.clear();
		        	moneypd=ServiceHelper.getService_performanceService().countAllMonthByWater(sppd);
		        	String leiji_xiaoshou="";
		        	if(moneypd != null){
		        		leiji_xiaoshou= moneypd.get("sumqitasp_getmoney").toString();
		        	}
		        	// 优选编辑费 
		        	sppd.put("money_type", "7");
		        	sppd.put("user_type", "1");
		        	moneypd.clear();
		        	moneypd=ServiceHelper.getService_performanceService().countAllMonthByWater(sppd);
		        	String leiji_guanggao1="";
		        	if(moneypd != null){
		        		leiji_guanggao1= moneypd.get("summoney").toString();
		        	}
		        	// 优选上架费 
		        	sppd.put("money_type", "8");
		        	sppd.put("user_type", "1");
		        	moneypd.clear();
		        	moneypd=ServiceHelper.getService_performanceService().countAllMonthByWater(sppd);
		        	String leiji_guanggao2="";
		        	if(moneypd != null){
		        		leiji_guanggao2= moneypd.get("summoney").toString();
		        	}
		        	//广告 编辑费 
		        	sppd.put("money_type", "13");
		        	sppd.put("user_type", "1");
		        	moneypd.clear();
		        	moneypd=ServiceHelper.getService_performanceService().countAllMonthByWater(sppd);
		        	String leiji_guanggao3="";
		        	if(moneypd != null){
		        		leiji_guanggao3= moneypd.get("summoney").toString();
		        	}
		        	//广告 编辑费 
		        	sppd.put("money_type", "19");
		        	sppd.put("user_type", "3");
		        	moneypd.clear();
		        	moneypd=ServiceHelper.getService_performanceService().countAllMonthByWater(sppd);
		        	String leiji_guanggao4="";
		        	if(moneypd != null){
		        		leiji_guanggao4= moneypd.get("summoney").toString();
		        	}
		        	 //提现
		        	sppd.put("money_type", "3");
		        	sppd.put("user_type", "3");
		        	moneypd.clear();
		        	moneypd=ServiceHelper.getService_performanceService().countAllMonthByWater(sppd);
		        	String add_tixiannumber="";
		        	String leiji_tixianmoney="";
		        	if(moneypd != null){
		        		add_tixiannumber= moneypd.get("countnumber").toString();//新增提现次数
		        		leiji_tixianmoney= moneypd.get("summoney").toString();//累计提现金额
		        	}
 		        	sppd.remove("month");
		        	sppd.remove("type");
		        	sppd.remove("money_type");
		        	sppd.remove("user_type");
		        	//当月的余额
		        	String lastday_money=ServiceHelper.getSp_fileService().findById(sppd).getString("nowmoney");
		        	if(leiji_water == null || leiji_water.equals("")){
		        		leiji_water="0";
	        		 }
		        	if(leiji_store == null || leiji_store.equals("")){
		        		leiji_store="0";
	        		 }
		        	if(add_member == null || add_member.equals("")){
		        		add_member="0";
	        		}if(leiji_member == null || leiji_member.equals("")){
	        			leiji_member="0";
		        	}
	        		if(add_xiaoshou == null || add_xiaoshou.equals("")){
	        			add_xiaoshou="0";
	        		 }
	        		if(leiji_xiaoshou == null || leiji_xiaoshou.equals("")){
	        			leiji_xiaoshou="0";
	        		 }
		        	if(add_jifen == null || add_jifen.equals("")){
		        		add_jifen="0";
	        		 }
		        	if(leiji_jifen == null || leiji_jifen.equals("")){
		        		leiji_jifen="0";
		        	}
 		        	if(add_guanggao1 == null || add_guanggao1.equals("")){
		        		add_guanggao1="0";
		        	}
 		        	if(add_guanggao2 == null || add_guanggao2.equals("")){
 		        		add_guanggao2="0";
 		        	}
 		        	if(add_guanggao3 == null || add_guanggao3.equals("")){
 		        		add_guanggao3="0";
 		        	}
 		        	if(add_guanggao4 == null || add_guanggao4.equals("")){
 		        		add_guanggao4="0";
 		        	}
		        	if(leiji_guanggao1 == null || leiji_guanggao1.equals("")){
		        		leiji_guanggao1="0";
		        	}
		        	if(leiji_guanggao2 == null || leiji_guanggao2.equals("")){
		        		leiji_guanggao2="0";
		        	}
		        	if(leiji_guanggao3 == null || leiji_guanggao3.equals("")){
		        		leiji_guanggao3="0";
		        	}
		        	if(leiji_guanggao4 == null || leiji_guanggao4.equals("")){
		        		leiji_guanggao4="0";
		        	}
		        	if(add_tixiannumber == null || add_tixiannumber.equals("")){
		        		add_tixiannumber="0";
		        	}
		        	if(leiji_tixianmoney == null || leiji_tixianmoney.equals("")){
		        		leiji_tixianmoney="0";
		        	}
		        	if(lastday_money == null || lastday_money.equals("")){
		        		lastday_money="0";
		        	}
		        	if(actual_water == null || actual_water.equals("")){
		        		actual_water="0";
		        	}
		        	if(actual_user_number == null || actual_user_number.equals("")){
		        		actual_user_number="0";
		        	}
		        	if(add_koudian == null || add_koudian.equals("")){
		        		add_koudian="0";
		        	}
		        	if(leiji_koudian == null || leiji_koudian.equals("")){
		        		leiji_koudian="0";
		        	}
		        	//判断当前月份是否有规定计划
		        	 sppd.put("month", prevmonth);
		        	 PageData monthpd2=ServiceHelper.getService_performanceService().findByIdspMonth(sppd);
		        	 monthpd1.put("sp_file_id", sppd.getString("sp_file_id"));
		        	 monthpd1.put("month", prevmonth);
		        	 monthpd1.put("leiji_water", leiji_water);
		        	 monthpd1.put("leiji_store", leiji_store);
		        	 monthpd1.put("add_member", add_member);
		        	 monthpd1.put("leiji_member", leiji_member);
		        	 monthpd1.put("add_xiaoshou", add_xiaoshou);
		        	 monthpd1.put("leiji_xiaoshou", leiji_xiaoshou);
		        	 monthpd1.put("add_jifen", add_jifen);
		        	 monthpd1.put("leiji_jifen", leiji_jifen);
		        	 String add_guanggao=df2.format(Double.parseDouble(add_guanggao1)+Double.parseDouble(add_guanggao2)+Double.parseDouble(add_guanggao3)+Double.parseDouble(add_guanggao4));
		        	 monthpd1.put("add_guanggao", add_guanggao);
		        	 String leiji_guanggao=df2.format(Double.parseDouble(leiji_guanggao1)+Double.parseDouble(leiji_guanggao2)+Double.parseDouble(leiji_guanggao3)+Double.parseDouble(leiji_guanggao4));
		        	 monthpd1.put("leiji_guanggao", leiji_guanggao);
		        	 monthpd1.put("add_tixiannumber", add_tixiannumber);
		        	 monthpd1.put("leiji_tixianmoney", leiji_tixianmoney);
		        	 monthpd1.put("lastday_money", lastday_money);
		        	 monthpd1.put("actual_water", actual_water);
		        	 monthpd1.put("actual_user_number", actual_user_number);
		        	 monthpd1.put("add_koudian", add_koudian);
		        	 monthpd1.put("leiji_koudian", leiji_koudian);
		        	 monthpd1.put("isqualified", "2");
		        	 if(monthpd2 == null){
		        		 monthpd1.put("sp_file_monthly_id", BaseController.getTimeID());
		        		 monthpd1.put("user_number_indicator", actual_user_number);
		        		 monthpd1.put("completion_rate", "100");
		        		 monthpd1.put("flow_indicators", actual_water);
		        		 monthpd1.put("water_completion_rate", "100");
		        		 ServiceHelper.getService_performanceService().savespMonth(monthpd1);
		        	 }else{
		        		 monthpd1.put("sp_file_monthly_id", monthpd2.getString("sp_file_monthly_id"));
		        		 String user_number_indicator=monthpd2.getString("user_number_indicator");//商家指标数
		        		 if(user_number_indicator == null || user_number_indicator.equals("")){
		        			user_number_indicator=actual_user_number;
		        		 }
		        		 String flow_indicators=monthpd2.getString("flow_indicators");//流水
		        		if(flow_indicators == null || flow_indicators.equals("")){
		        			flow_indicators=actual_water;
		        		 }
		        		if(Double.parseDouble(user_number_indicator) == 0){
		        			monthpd1.put("completion_rate", "100");
		        		}else{
		        			double n1=Double.parseDouble(actual_user_number)/Double.parseDouble(user_number_indicator);
 			        		String completion_rate=df2.format(n1*100 );
 			        		monthpd1.put("completion_rate", completion_rate);
 		        		}
		        		if(Double.parseDouble(user_number_indicator) == 0){
		        			monthpd1.put("water_completion_rate", "100");
		        		}else{
 			        		double n2=Double.parseDouble(actual_water)/Double.parseDouble(flow_indicators);
 			        		String water_completion_rate=df2.format( n2*100 );
 			        		monthpd1.put("water_completion_rate", water_completion_rate);
		        		}
 		        		ServiceHelper.getService_performanceService().editspMonth(monthpd1);
		        	 }
				 }
		 }catch(Exception e){
			 //System.out.println("每月一号开始服务商的月统计定时器"+e.toString());
			 (new TongYong()).dayinerro(e);
		 }
		 //System.out.println("服务商上个月的月统计完成");
	 }
  	
  	
  	

  	/**
  	 * 统计业务员月销售
  	 */
  	public static void clerkMonth_number(int number){//0-当月，-1.......
		 PageData pd=new PageData();
 		 try{
				 Calendar c = Calendar.getInstance();
			     c.add(Calendar.MONTH, number);
			     String prevmonth=new SimpleDateFormat("yyyy-MM").format(c.getTime());
  		    	 //获取所有业务员
 		         List<PageData> allsplist=ServiceHelper.getClerk_fileService().listAll(pd);
		         for (PageData clerkpd : allsplist) {
 		        	 //获取当前month的商家人数：type1当月，2之前的月份包括当月
		        	 clerkpd.put("month", prevmonth);
		        	 clerkpd.put("type", "1"); 
		        	 String add_store=ServiceHelper.getService_performanceService().countMonthStore(clerkpd);
		        	 String add_member=ServiceHelper.getService_performanceService().countMonthMember(clerkpd);
		        	//流水 
		        	 clerkpd.put("money_type", "2");
		        	 clerkpd.put("user_type", "2");
		        	PageData moneypd=ServiceHelper.getService_performanceService().countAllMonthByWater(clerkpd);
		        	String add_water="";
  		        	if(moneypd != null){
  		        		add_water= moneypd.get("summoney").toString();
 		        	}
  		        	clerkpd.remove("month");
  		        	clerkpd.remove("type");
  		        	clerkpd.remove("money_type");
  		        	clerkpd.remove("user_type");
		        	//开始累计
  		        	clerkpd.put("month", prevmonth);
  		        	clerkpd.put("type", "2"); 
		        	String leiji_store=ServiceHelper.getService_performanceService().countMonthStore(clerkpd);
		        	String leiji_member=ServiceHelper.getService_performanceService().countMonthMember(clerkpd);
		        	//流水 
		        	clerkpd.put("money_type", "2");
		        	clerkpd.put("user_type", "2");
		        	moneypd.clear();
		        	moneypd=ServiceHelper.getService_performanceService().countAllMonthByWater(clerkpd);
		        	String leiji_water="";
 		        	if(moneypd != null){
		        		leiji_water= moneypd.get("summoney").toString();
 		        	}
 		        	//处理数据
 		        	if(add_water == null || add_water.equals("")){
 		        		add_water="0";
 		        	}
  		        	if(leiji_water == null || leiji_water.equals("")){
		        		leiji_water="0";
	        		}
  		        	if(add_store == null || add_store.equals("")){
  		        		add_store="0";
  		        	}
		        	if(leiji_store == null || leiji_store.equals("")){
		        		leiji_store="0";
	        		 }
		        	if(add_member == null || add_member.equals("")){
		        		add_member="0";
	        		}if(leiji_member == null || leiji_member.equals("")){
	        			leiji_member="0";
		        	}
	        		clerkpd.remove("month");
  		        	clerkpd.remove("type");
  		        	clerkpd.remove("money_type");
  		        	clerkpd.remove("user_type");
 		        	 //判断当前月份是否有规定计划
  		        	 PageData monthpd1=new PageData();
		        	 monthpd1.put("clerk_file_id", clerkpd.getString("clerk_file_id"));
		        	 monthpd1.put("month", prevmonth);
		        	 monthpd1.put("add_water", add_water);
		        	 monthpd1.put("leiji_water", leiji_water);
		        	 monthpd1.put("add_store", add_store);
		        	 monthpd1.put("leiji_store", leiji_store);
		        	 monthpd1.put("add_member", add_member);
		        	 monthpd1.put("leiji_member", leiji_member);
		        	 //==================
	        		 clerkpd.put("month", prevmonth);
		        	 PageData monthpd2=ServiceHelper.getService_performanceService().findByIdClerkMonth(clerkpd);
  		        	 if(monthpd2 == null){
  		        		 monthpd1.put("clerk_file_monthly_id", BaseController.getTimeID());
 		        		 ServiceHelper.getService_performanceService().saveClerkMonth(monthpd1);
		        	 }else{
		        		 monthpd1.put("clerk_file_monthly_id", monthpd2.getString("clerk_file_monthly_id"));
  		        		 ServiceHelper.getService_performanceService().editClerkMonth(monthpd1);
		        	 }
				 }
		 }catch(Exception e){
 			 (new TongYong()).dayinerro(e);
		 }
 	 }
  	
  	
  	
  	
  	/**
  	 * 发送推送内容
  	 * @param content:内容
  	 * @param alias:别名
  	 * @param order_id：如果是订单则是订单ID、提货券ID
     * @param msg_type：1-服务商推送(爆品)，2-商家扫一扫优惠买单，3-商家提货券推送，
     * 						4-红包未使用提醒推送，5-红包快到期提醒
     *   6-提货券提醒推送,7-积分奖励推送,8-商家发送红包，9-会员索要商家红包，10-提货券到期提醒
  	 * oprator_id:接收人的ID
  	 * oprator_type:接收人的类型：1-商家，11-商家操作员，2-会员，3-服务商
  	 * other_text：顺带信息
  	 * other_text2：顺带信息
   	 */
  	public static void sendTuiSong(String alias,String order_id,
  										String msg_type,String oprator_id,
  											String oprator_type,String other_text,String other_text2){
  		PageData pd=new PageData();
  		String title="";
  		String jiaoyi_id=order_id;
  		String content="";
  		String zhu_type="";//1-爆品，2-订单（提货券/非提货券），3-红包，4-积分
  		String other="";
  		boolean flag=true;
   		try {
	  			if(msg_type.equals("1")){
	  				title="爆屏来袭";
	  				content=other_text;
	  				zhu_type="1";
	  			}else if(msg_type.equals("2")){
	  				title="优惠买单";
	  				content="您收到一笔“优惠买单("+order_id+")”金额：￥"+other_text+"元。";
	  				zhu_type="2";
	  			}else if(msg_type.equals("3")){
	  				title="提货券";
	  				content="您有新九鱼网订单（"+order_id+"），请及时查看！";
	  				zhu_type="2";
	  			}else if(msg_type.equals("4")){
	  				title="红包即将到期";
	  				content="你有"+other_text+"个优惠红包，赶紧去使用吧！";//（3天未使用时推送）
	  				zhu_type="3";
	   			}else if(msg_type.equals("5")){
	  				title="红包即将到期";
	   				content="您有"+other_text+"个优惠红包1天后到期，赶紧去使用吧！";//（1天后到期）
	   				zhu_type="3";
	  			}else if(msg_type.equals("6")){
	  				title="提货券即将到期";
	   				content="您有"+other_text+"张提货券，请于"+other_text2+"前到门店取货！";//（（下单后第四天推送）
	   				zhu_type="2";
	  			}else if(msg_type.equals("7")){
	  				title="获赠积分";
	   				content="恭喜你在"+other_text+"消费获赠积分"+other_text2;// 消费获赠积分
	   				zhu_type="4";
	   				if(Double.parseDouble(other_text2) <= 0){
	   					flag=false;
	   				}
	  			}else if(msg_type.equals("8")){
	  				title="领取红包";
	   				content="你有一个新的红包，请前往领取"; 
	   				zhu_type="3";
	   				jiaoyi_id=other_text2;
//	   				String store_redpackets_id=other_text2;
//	   				String store_name=other_text;
	  			}else if(msg_type.equals("9")){
	  				title="红包索要";
	   				content="有会员请你发红包，现在就发~"; 
	   				zhu_type="3";
	   				pd.put("member_id", order_id);
	 				pd=ServiceHelper.getAppPcdService().getpcdBymember(pd);
	 				if(pd != null){
	 					other=pd.getString("name");
	 				}
	 				pd=null;pd=new PageData();
//	   				String member_id=order_id;
	  			}else if(msg_type.equals("10")){
	  				title="提货券到期";
	   				content="您有一张提货券到期未使用，已退款到钱包余额";// 消费获赠积分
	   				zhu_type="2";
	  			}
	  			//推送
	  			if(oprator_type.equals("1")){
	 				pd.put("store_id", oprator_id);
	 				pd=ServiceHelper.getAppPcdService().getpcdBystore(pd);
	  			}else if(oprator_type.equals("11")){
	 				pd.put("store_operator_id", oprator_id);
	 				pd=ServiceHelper.getAppPcdService().getpcdBystoreoprator(pd);
	   			}else if(oprator_type.equals("2")){
	 				pd.put("member_id", oprator_id);
	 				pd=ServiceHelper.getAppPcdService().getpcdBymember(pd);
	   			}else if(oprator_type.equals("3")){
	 				pd.put("sp_file_id", oprator_id);
	 				pd=ServiceHelper.getAppPcdService().getpcdBysp(pd);
	   			}
  			    //判断当前用户是否存在
				if(pd == null){
					content="用户不存在"+content;
					pd=new PageData();
				}
  				String istuisong=pd.getString("istuisong");
 				pd.put("title", title);
				pd.put("content", content);
				pd.put("zhu_type", zhu_type);
				pd.put("tuisong_type", msg_type);
				pd.put("oprator_id", oprator_id );
				pd.put("jiaoyi_id", jiaoyi_id );
				ServiceHelper.getSend_notificationsService().saveTuisong(pd);
//				//System.out.println("新增推送记录成功");
				try {
					if(flag && istuisong != null && istuisong.equals("1")){
						if(oprator_type.equals("1") || oprator_type.equals("11")){
		 					JPushClientUtil.storepushMessage(title, content, alias,msg_type,order_id,other);
		 				}else{
		 					JPushClientUtil.memberpushMessage(title, content, alias,msg_type,order_id,other);
		 				}
					}
				} catch (Exception e) {
					// TODO: handle exception
 				}
				System.out.println("推送结束.......");
   		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString());
			(new TongYong()).dayinerro(e);
		}
   	}
  	
  	
  	/**
  	 * 判断是否在收银/支付
  	 */
  	public static PageData isReadyBuy(String member_id){
			PageData pd=new PageData();
			pd.put("member_id", "1");
			pd.put("result", "1");
			pd.put("message", "可以支付");
			pd.put("data", "");
   			try { 
				//获取当前会员最近一笔订单
   				
			} catch (Exception e) {
				// TODO: handle exception
				//System.out.println(e.toString());
				(new TongYong()).dayinerro(e);
			}
   			return pd;
  	}
  	
  	/**
  	 * 获取当前用户的好友列表
  	 */
  	public static List<PageData> getFriendList(String user_id){
   		List<PageData>	allList=new ArrayList<PageData>();
   		try { 
   			//当本人是邀请人时
			PageData friendpd=new PageData();
			friendpd.put("invite_id", user_id);
			friendpd.put("friend_status", "2");
			List<PageData>	be_invite_List = ServiceHelper.getAppFriendService().myFriendList(friendpd);	//获得所有被邀请好友的列表
			int belength=be_invite_List.size();
			PageData e=null;
			PageData adde=null;
			for( int i=0;i<belength;i++ ){
				adde=new PageData();
				e=be_invite_List.get(i);
				if(e != null){
  					if(e.getString("be_invite_type").equals("2")){//会员
 						  if(e.getString("be_member_id") != null && !e.getString("be_member_id").equals("")){
 							 if(e.getString("be_member_name") != null && e.getString("be_member_name").length()==11){
 								e.put("be_member_name", e.getString("be_member_name").substring(0, 3)+"****"+e.getString("be_member_name").substring(7, 11));
 							 }
 							adde.put("name", e.getString("be_member_name"));
 							adde.put("image_url", e.getString("be_image_url"));
 							adde.put("id", e.getString("be_member_id"));
 							adde.put("type", "1");
 							allList.add(adde);
 							adde=null;
 							continue;
 						  } 
 	 				 }else if(e.getString("be_invite_type").equals("1")){//商家
  	 					if( e.getString("be_store_id") != null && ! e.getString("be_store_id").equals("")){
  	 						adde.put("name",e.getString("be_store_name"));
  	 						adde.put("image_url", e.getString("be_pictrue_url"));
  	 						adde.put("id",  e.getString("be_store_id"));
  	 						adde.put("type", "1");
							allList.add(adde);
							adde=null;
							continue;
						  } 
	 				 }else{
	 					continue;
	 				 }
 				}
  			}
			//当本人是被邀请人时
			friendpd=null;
			friendpd=new PageData();
			friendpd.put("be_invite_id", user_id);
			friendpd.put("friend_status", "2");
			List<PageData>	invite_List = ServiceHelper.getAppFriendService().myFriendList(friendpd);	//获得所有被邀请好友的列表
			int inlength=invite_List.size();
			for( int i=0;i<inlength;i++ ){
				adde=new PageData();
				e=invite_List.get(i);
				if(e != null){
//					String invite_type=e.getString("invite_type");
// 					String member_id=e.getString("member_id");
//					String member_name=e.getString("member_name");
//					String image_url=e.getString("image_url");
//					String store_id=e.getString("store_id");
//					String store_name=e.getString("store_name");
//					String pictrue_url=e.getString("pictrue_url");
  					if(e.getString("invite_type").equals("2")){//会员
 						  if(e.getString("member_id") != null && !e.getString("member_id").equals("")){
  							 if(e.getString("member_name") != null && e.getString("member_name").length()==11){
  								e.put("member_name", e.getString("member_name").substring(0, 3)+"****"+e.getString("member_name").substring(7, 11));
							 } 
  							adde.put("name",e.getString("member_name"));
  							adde.put("image_url", e.getString("image_url"));
  							adde.put("id", e.getString("member_id"));
  							adde.put("type", "2");
 							allList.add(adde);
 							adde=null;
 							continue;
 						  } 
 	 				 }else if(e.getString("invite_type").equals("1")){//商家
 	 					if(e.getString("store_id") != null && !e.getString("store_id").equals("")){
 	 						adde.put("name", e.getString("store_name"));
 	 						adde.put("image_url", e.getString("pictrue_url"));
 	 						adde.put("id", e.getString("store_id"));
 	 						adde.put("type", "1");
							allList.add(adde);
							adde=null;
	 						continue;
						  } 
 	 				 } 
 				}else{
 					continue;
 				}
 			}
   		} catch (Exception e) {
  			// TODO: handle exception
   			(new TongYong()).dayinerro(e);
  		}
   		return allList;
  	}
  	
  	
  	

	/**
	 * 登录账号进行设置
	 * 
	 */
  	public static PageData LoginNumber(String loginnumber,String type){
 		PageData pd = new PageData();
 		String result="1";
 		String message="登录成功，正在跳转";
 		try{ 
			//获取电话集合--包含
			if(Const.xzloginnumber.contains(loginnumber)){
				result="99";
				message="登录失败已"+Const.limit_loginerrornumber+"次，请输入图形码";
 			}else{
				//获取电话获取验证码次数map---判断是否超出次数
				if(Const.clicklogin_number.get(loginnumber) == null){
					Const.clicklogin_number.put(loginnumber, 1);
					message="剩余登录次数："+(Const.limit_loginerrornumber-1);
 				}else{
					int number=Const.clicklogin_number.get(loginnumber);
 					if(Const.limit_loginerrornumber-number <= 0){
						result="98";
						message="登录失败已"+Const.limit_loginerrornumber+"次，请输入图形码";
 						Const.xzloginnumber.add(loginnumber);
//						//设置定时器
// 						LoginNumberTask loginTask=new LoginNumberTask(loginnumber);
// 						Timer tt=new Timer();
// 						tt.schedule(loginTask,Const.limit_logintime*60*1000);//定时54秒
 					}else{
 						message="剩余登录次数："+(Const.limit_loginerrornumber-number);
 						Const.clicklogin_number.put(loginnumber, number+1);
 					}
				} 
			}
  		} catch(Exception e){
  			//System.out.println(e.toString()+"判断当前号码是否可以发送验证码========================");
  			(new TongYong()).dayinerro(e);
  			result="0";
  			message="系统错误";
		}
		pd.put("result", result);
		pd.put("message", message);
		return pd;
 	}
  	
  	/**
  	 * 清除登录账号
  	 * @param loginnumber
  	 */
  	public static void clearLoginNumber(String loginnumber){
  		try {
  			Const.clicklogin_number.remove(loginnumber);
			//获取电话集合--移除当前的电话
 			for(int i=0;i<Const.xzloginnumber.size();i++){  
	            if(Const.xzloginnumber.get(i).equals(loginnumber)){  
	            	Const.xzloginnumber.remove(i);  
	            }  
	        } 
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
  	}
  	
  	
  	
  	
  	
  	/**
  	 * 通过订单获取商品列表
  	 * @param loginnumber
  	 */
  	public static PageData getGoodsListByOrder(PageData pd){
   		try {
	  				if(pd == null){
	  					return new PageData();
	  				}
 					List<PageData> goodsList=ServiceHelper.getTYAllSortService().listAllGoodsByOrder(pd);
					double shenmoney=0;//节省金额
					int goods_number=0;//总购买数量
					for(PageData e1 : goodsList){
	 				String goods_type=e1.getString("goods_type");//商品类型
	 				String shop_number=e1.getString("shop_number");//购买数量
	 				goods_number+=Integer.parseInt(shop_number);
	 				PageData goodspd=new PageData();
	 				if(goods_type.equals("1")){
	 					goodspd.put("goods_id", e1.getString("goods_id"));
 	 					goodspd=ServiceHelper.getTYAllSortService().findByIdByGoods(goodspd);
	 					if(goodspd != null){
	 						e1.put("goods_name", goodspd.getString("goods_name"));
	 						e1.put("sale_money", goodspd.getString("retail_money"));
	 						e1.put("image_url", goodspd.getString("image_url"));
	 						e1.put("company", goodspd.getString("company"));
	 						e1.put("gf_salemoney", "");
	 	 					e1.put("th_address", "");
	 						e1.put("brand_name", "");
	 						e1.put("gg_miaosu", "");
	 						e1.put("shen_money","");
	 					}else{
	 						e1.put("goods_name", "商品不存在");
	 						e1.put("sale_money", "");
	 						e1.put("image_url", "");
	 						e1.put("company", "");
	 						e1.put("gf_salemoney", "");
	 	 					e1.put("th_address", "");
	 						e1.put("brand_name", "");
	 						e1.put("gg_miaosu", "");
	 						e1.put("shen_money","");
	 					}
	  					goodspd=null;
	 				}else if(goods_type.equals("2")){
	 						goodspd.put("youxuangg_id", e1.getString("goods_id"));
 	 	 					goodspd=ServiceHelper.getTYAllSortService().finddetailgg(goodspd);
	 	 					if(goodspd != null){
	 	 						e1.put("goods_name", goodspd.getString("goods_name"));
	 	 						e1.put("image_url", goodspd.getString("gg_imageurl"));
	 	 						e1.put("sale_money", goodspd.getString("sale_money"));
	 	 						e1.put("company", goodspd.getString("goods_dw"));
	 	 						e1.put("gf_salemoney", goodspd.getString("gf_salemoney"));
		 	 					e1.put("th_address", goodspd.getString("th_address"));
	 	 						e1.put("brand_name", goodspd.getString("brand_name"));
	 	 						e1.put("gg_miaosu", goodspd.getString("gg_miaosu"));
	 	 						shenmoney+=Double.parseDouble( goodspd.getString("gf_salemoney"))- Double.parseDouble(goodspd.getString("sale_money"));
	 	 					}else{
		 						e1.put("goods_name", "商品不存在");
		 						e1.put("sale_money", "");
		 						e1.put("image_url", "");
		 						e1.put("company", "");
		 						e1.put("gf_salemoney", "");
		 	 					e1.put("th_address", "");
	 	 						e1.put("brand_name", "");
	 	 						e1.put("gg_miaosu", "");
	 	 						e1.put("shen_money","");
		 					}
	 	  					goodspd=null;
	 				}else if(goods_type.equals("3")){
	 					goodspd.put("oneyuangoods_id", e1.getString("goods_id"));
 	 					goodspd=ServiceHelper.getTYAllSortService().findByIdWithMember(goodspd);
	 					if(goodspd != null){
	 						e1.put("goods_name", goodspd.getString("oneyuangoods_name"));
	 						e1.put("image_url", goodspd.getString("oneyuangoodsimage_url"));
	 						e1.put("sale_money", goodspd.getString("allpay_money"));
	 						e1.put("company", "");
	 						e1.put("gf_salemoney", "");
	 	 					e1.put("th_address", "");
	 						e1.put("brand_name", "");
	 						e1.put("gg_miaosu", "");
	 						e1.put("shen_money","");
	 					}else{
	 						e1.put("goods_name", "商品不存在");
	 						e1.put("sale_money", "");
	 						e1.put("image_url", "");
	 						e1.put("company", "");
	 						e1.put("gf_salemoney", "");
	 	 					e1.put("th_address", "");
	 						e1.put("brand_name", "");
	 						e1.put("gg_miaosu", "");
	 						e1.put("shen_money","");
	 					}
	  					goodspd=null;
	 				}
	 			}
				pd.put("shen_money", df2.format(shenmoney));//已省金额
	 			pd.put("goods_number", goods_number);//数量
 				pd.put("goodsList", goodsList);
	 			pd.put("store_name", ServiceHelper.getTYAllSortService().findByIdByStore(pd).getString("store_name"));
	 			pd.put("pictrue_url", ServiceHelper.getTYAllSortService().findByIdByStore(pd).getString("pictrue_url"));
	 			pd.put("phone_bymemeber", ServiceHelper.getTYAllSortService().findByIdByStore(pd).getString("phone_bymemeber"));
	 			pd.put("address", ServiceHelper.getTYAllSortService().findByIdByStore(pd).getString("address"));
	 			//付款方式
	 			pd.put("member_id", pd.getString("payer_id"));
 				List<PageData> fukaungList =new ArrayList<PageData>();
 				PageData fspd=new PageData();
  				if(pd.getString("channel").contains("wx")){
 					fspd.put("fsname", "微信支付");
 					fspd.put("fsnumber", pd.getString("actual_money"));
 					fukaungList.add(fspd);
 				}else if(pd.getString("channel").contains("alipay")){
 					fspd.put("fsname", "支付宝支付");
 					fspd.put("fsnumber", pd.getString("actual_money"));
 					fukaungList.add(fspd);
 				}else if(pd.getString("channel").contains("nowpay") && Double.parseDouble(pd.getString("actual_money")) >0){
 					fspd.put("fsname", "当面收银");
 					fspd.put("fsnumber", pd.getString("actual_money"));
 					fukaungList.add(fspd);
 				}
  				fspd=null;
 				fspd=new PageData();
 				if(pd.getString("user_balance") != null && !pd.getString("user_balance").trim().equals("") &&  Double.parseDouble(pd.getString("user_balance") ) > 0){
 					fspd.put("fsname", "余额支付");
 					fspd.put("fsnumber", pd.getString("user_balance"));
 					fukaungList.add(fspd);
 				}
 				fspd=null;
 				fspd=new PageData();
 				if(pd.getString("user_integral") != null && !pd.getString("user_integral").trim().equals("") && Double.parseDouble(pd.getString("user_integral") ) > 0){
 					fspd.put("fsname", "积分支付");
 					fspd.put("fsnumber", pd.getString("user_integral"));
 					fukaungList.add(fspd);
 				}
 				pd.put("fukaungList", fukaungList);
 				//优惠项
 	 			List<PageData> discountListone =new ArrayList<PageData>();
 	 			String discount_content=pd.getString("discount_content");
 	 			if(discount_content != null && discount_content.contains(",")){
 	 					String[] str=discount_content.split(",");
 	 					 PageData  dispd=null;
	   					for(int i=0;i<str.length ; i++){
	   						dispd=new PageData();
	   					    if(str[i].contains("@")){
	   					    	String[] str1=str[i].split("@");
			   					dispd.put("content", str1[0]);
			   					dispd.put("number", str1[2]);
			   					dispd.put("all", str1[0]+str1[2]);
			   					discountListone.add(dispd);
	   					    }
 		   					dispd=null;
	 	 			 }
 	 			}	
 	 			pd.put("discountList", discountListone);
 	 			discountListone=null;
     	} catch (Exception e) {
			// TODO: handle exception
     		new TongYong().dayinerro(e);
		}
  		return pd;
  	}
  	
  	
  	/**
  	 * 注册会员
  	 */
  	public static PageData saveMember(PageData pd ,String recommended,String recommended_type){
  		try {
 			String phone = pd.getString("phone");
 			if(phone == null || phone.equals("")){
 				phone=BaseController.get11UID();
 				pd.put("phone", phone);
 			}
 			String member_id=BaseController.getMemberID(phone);
 			pd.put("member_id", member_id);
   			pd.put("show_lookid", BaseController.getShowKookId() );
 			while (ServiceHelper.getAppMemberService().detailByShowLookId(pd) > 0 ) {
  				pd.put("show_lookid",BaseController.getShowKookId());
 			}	
   			if(ServiceHelper.getAppMemberService().detailByPhone(pd) == null){
  				//会员id
      	  		pd.put("member_id",member_id);
   	  			pd.put("charm_number", Const.charm_number[1]); 
	 			pd.put("vip_level", "1"); 
	 			//头像处理
	 			if(pd.getString("image_url") == null || pd.getString("image_url").equals("")){
	 				pd.put("image_url", "https://www.jiuyuvip.com/FileSave/File/userFile/moren.png"); 
	 			}else if(!pd.getString("image_url").contains("jiuyu")){
	 				if(FileUpload.getImageByUrl( pd.getString("image_url"), AppUtil.getuploadRootUrl()+"/userFile", member_id).equals("success")){
	 					pd.put("image_url",  AppUtil.getuploadRootUrlIp()+"/userFile/"+member_id+".png"); 
	 				}else{
	 					pd.put("image_url", "https://www.jiuyuvip.com/FileSave/File/userFile/moren.png"); 
	 				}
	 			}
 	 			//处理推荐人关系
  	 			if(recommended == null || recommended.equals("") || recommended.equals("0") || recommended_type == null || recommended_type.equals("0")){
  	 	 				pd.put("recommended", "0");
  	 	 				pd.put("recommended_type", "0");
  	 			}else{
  	 					pd.put("recommended", recommended);
  	 					pd.put("recommended_type", recommended_type);
  	 					PageData e=new PageData();
  						//添加人脉信息表
  		 				e.put("contacts_id",member_id);
  						e.put("contacts_type","2");
  						e.put("contacts_parent_id",recommended);
  						e.put("contacts_parent_type",recommended_type);
  						ServiceHelper.getAppMemberService().saveContact(e);
  						e.clear();
  						e.put("friend_status", "2");
  						e.put("invite_id",recommended);
  						e.put("invite_type", recommended_type);
  						e.put("be_invite_id", member_id);
  						e.put("be_invite_type", "2");
  						ServiceHelper.getAppFriendService().saveFriend(e);
  						e.clear();
  						if(recommended_type.equals("1")){//商家
   							 //添加为本店会员
							e.put("store_id", recommended);
 							if(ServiceHelper.getAppStoreService().findVipImage(e) != null){
								e= ServiceHelper.getAppStoreService().findVipImage(e);
								e.put("member_id", member_id);
								ServiceHelper.getAppMemberService().addStoreVip(e);
							}
 						}else{
	  						//魅力值：0-50一星会员 50-99 二星会员 100-199 三星会员 200-499  四星会员 500-999 五星会员 1000-2000 一钻会员
	  						TongYong.charm_numberAdd(recommended, Const.charm_number[3]);
	  					}
  						e=null;
   	  			}
  	 			pd.put("password", MD5.md5(pd.getString("password")));//密码转Md5
   	 	 		ServiceHelper.getAppMemberService().save(pd);
   	 	 		try {
  	 	 			//注册环信
  	 				HuanXin.regirstHx(member_id, member_id, pd.getString("show_lookid"));
  	  			} catch (Exception e) {
  	 				// TODO: handle exception
  	 				//system.out.println(e.toString()+"环信出错");
  	  				new TongYong().dayinerro(e);
   	 			}
  			}else{
  				pd.put("flag", "false");
  				pd.put("message", "当前账号已注册，请前往登录");
	  			return pd;
   			}
 		} catch (Exception e) {
			// TODO: handle exception
 			new TongYong().dayinerro(e);
 		}
 		pd.put("flag", "true");
 		return pd;
   	}
  	
 	/**
  	 * 订单是否ok针对商家来说余额充足？
  	 * 
  	 */
  	public static boolean orderIsOkByStore(double zengjifen,double discount_after_money,String pay_type,double user_integral,double user_balance,double actual_money,PageData spd){
  		try { 
  				//商家收益的总金额
  			    double n=user_balance+user_integral;
  			    if(pay_type.equals("2") || pay_type.equals("3")){//是优惠买单支付或是提货卷支付钱（第三方支付）也是入账的
					n=n+actual_money;
				}
  			    n=Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(spd))+n;
  			   
 				//支出的总金额
 				double storeintegral=zengjifen+zengjifen*Const.orderShouyiMoney[0];//赠送积分+赠送系统积分
				String isopen_points=spd.getString("isopen_points");
				double transaction_points=Double.parseDouble(spd.getString("transaction_points"));
				if(isopen_points.equals("1")){//开启了交易扣费
					storeintegral=storeintegral+discount_after_money*transaction_points/100;
				}
				 
				if(n-storeintegral  < 0){
 			    	return true;
				}
 		} catch (Exception e) {
			// TODO: handle exception
 			new TongYong().dayinerro(e);
 			return true;
  		}
  		return false;
   	}
  	
  	
  	/**
  	 * 优选确认订单的接口
  	 * @param guanlian_id 关联订单的ID
  	 * @param order_tn 流水编号
  	 */
  	public static void youxuanOkOrder(String guanlian_id,String order_tn ){
   		try {
  				//优选提货券
     			System.err.println("优选提货券===========================guanlian_id="+guanlian_id);
     			PageData glpd=new PageData();
    			glpd.put("guanlian_id", guanlian_id);
    			glpd.put("status", "0");//0-未处理，1-已处理
    			glpd=ServiceHelper.getAppOrderService().getguanlianById(glpd);
    			if(glpd != null){
    				String[] allbeguanlian_id=glpd.getString("beguanlian_id").split(",");
	       			PageData orderpd=null;
					PageData mpd=null;
					PageData waterpd=null;
					PageData moneypd=null;
    				for (int i = 0; i < allbeguanlian_id.length; i++) {
    					orderpd=new PageData();
    					orderpd.put("order_id", allbeguanlian_id[i]);
    					orderpd.put("order_status", "1");
     					orderpd.put("order_tn", order_tn);
     					ServiceHelper.getAppOrderService().editStatus(orderpd);
		   				//订单详情
		   				orderpd=ServiceHelper.getAppOrderService().findById(orderpd);
		   				if(orderpd != null){
 			   				//用户详情
	 			            mpd=new PageData();
	 			            orderpd.put("member_id", orderpd.getString("payer_id"));
	 			       		mpd=ServiceHelper.getAppMemberService().findById(orderpd);
	 			       		//扣减积分
	 		    			if(Double.parseDouble(orderpd.getString("user_integral")) > 0){
 	 		    				moneypd=new PageData();
	 					 		moneypd.put("pay_way", "integralmoney");
	 					 		moneypd.put("member_id",mpd.getString("member_id"));
	 						  	ServiceHelper.getAppMemberService().updateMemberById(moneypd); 
	 						  	moneypd.clear();
	 							//新增个人积分消费历史
	 							moneypd.put("wealth_type", "1");
	 							moneypd.put("consume_type", "2");
	 							moneypd.put("member_id",mpd.getString("member_id"));
	 							moneypd.put("content", ServiceHelper.getAppStoreService().findById(orderpd).getString("store_name")+"消费抵用");
	 							moneypd.put("number", "-"+orderpd.getString("user_integral"));
	 							moneypd.put("now_money", df2.format(Double.parseDouble(mpd.getString("now_integral"))-Double.parseDouble(orderpd.getString("user_integral"))));
	 							moneypd.put("jiaoyi_id", orderpd.getString("order_id"));
	 							moneypd.put("jiaoyi_status", "1");
		   						moneypd.put("member_wealthhistory_id", BaseController.getXFUID(mpd.getString("show_lookid")));
 	 							ServiceHelper.getAppMember_wealthhistoryService().saveWealthhistory(moneypd);
	 							moneypd.clear();
	 							//更新积分
	 		  					moneypd.put("member_id", mpd.getString("member_id"));
	 							moneypd.put("now_integral", df2.format(Double.parseDouble(mpd.getString("now_integral"))-Double.parseDouble(orderpd.getString("user_integral"))));
	 							ServiceHelper.getAppMemberService().edit(moneypd);
	 		    			}
			 			   	waterpd=new PageData();
	 	    				waterpd.put("pay_status","97");
	 	    	   			waterpd.put("waterrecord_id", allbeguanlian_id[i]);
	 	   					waterpd.put("user_id", orderpd.getString("payer_id"));
	 	   					waterpd.put("user_type", "2");
	 	    				waterpd.put("withdraw_rate","0");
	 	   					waterpd.put("money_type","2");
	 	   	 				waterpd.put("money", orderpd.getString("sale_money"));
	 	   	 				waterpd.put("reduce_money",orderpd.getString("discount_money"));
	 	   					waterpd.put("arrivalmoney", df2.format(Double.parseDouble(orderpd.getString("sale_money"))-Double.parseDouble(orderpd.getString("discount_money"))));
	 	   					waterpd.put("nowuser_money", mpd.getString("now_money"));
	 	    				waterpd.put("application_channel", orderpd.getString("in_jiqi")); 
	 	   					if(orderpd.getString("channel").contains("alipay")){
	 	   						waterpd.put("remittance_type","3" );
	 	    	 				waterpd.put("alipay_money",orderpd.getString("actual_money") );
	 	    	 				waterpd.put("remittance_name",Const.payjiqi[3] );
	 	   					}else if(orderpd.getString("channel").contains("wx")){
	 	   						waterpd.put("remittance_type","4" );
	 	   						waterpd.put("wx_money",orderpd.getString("actual_money") );
	 	   						waterpd.put("remittance_name",Const.payjiqi[4] );
	 	   					}else if(orderpd.getString("channel").contains("nowpay")){
	 	   						waterpd.put("remittance_type","2" );
	 	   						waterpd.put("nowypay_money",orderpd.getString("actual_money") );
	 	   						if(orderpd.getString("in_jiqi").equals("1")){
	 	   							waterpd.put("remittance_name",Const.payjiqi[0] );
	 	   						}else if(orderpd.getString("in_jiqi").equals("4")){
	 	   							waterpd.put("remittance_name",Const.payjiqi[6] );
	 	   						}else if(orderpd.getString("in_jiqi").equals("2")){
	 	   							waterpd.put("remittance_name",Const.payjiqi[2] );
	 	   						}
	 	   					}else if(orderpd.getString("channel").contains("pple")){
	 	   						waterpd.put("remittance_type","5" );
	 	   						waterpd.put("apple_money",orderpd.getString("actual_money") );
	 	   						waterpd.put("remittance_name",Const.payjiqi[5] );
	 	   					}else{
	 	   						waterpd.put("remittance_type","5" );
	 	   						waterpd.put("bank_money",orderpd.getString("actual_money"));
	 	    					waterpd.put("remittance_name",Const.payjiqi[1] );
	 	   					}
		 	   				waterpd.put("integral_money",orderpd.getString("user_integral")); 
		 	   				waterpd.put("balance_money",orderpd.getString("user_balance")); 
	 	   					waterpd.put("remittance_number",mpd.getString("phone"));//支付人的支付账号
	 	    				waterpd.put("createtime",DateUtil.getTime());
	 	   					waterpd.put("over_time",DateUtil.getTime());
	 	   					waterpd.put("jiaoyi_type","5");
	 	    				waterpd.put("payee_number",orderpd.getString("store_id"));
	 	     	 			waterpd.put("order_tn", orderpd.getString("order_tn"));
	 	   					waterpd.put("province_name", mpd.getString("province_name"));
	 	   					waterpd.put("city_name", mpd.getString("city_name"));
	 	   					waterpd.put("area_name", mpd.getString("area_name"));
	 	    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
	 	    				waterpd=null;
		 	 				//获取商品信息
	 						List<PageData> goodsList=ServiceHelper.getAppGoodsService().getGoodsIdByOrder(orderpd);
	 						for(PageData goodspd : goodsList){
	 							goodspd.put("member_id", mpd.getString("member_id"));
	 							//清空购物车
	 							ServiceHelper.getShopCarService().delShop(goodspd);
		 	 				}
	 						//发送推送
						    TongYong.sendTuiSong(orderpd.getString("store_id"), orderpd.getString("order_id"), "3", orderpd.getString("store_id"), "1", orderpd.getString("sale_money"),"");
		   				}
				}
    			
     		}
 		} catch (Exception e) {
			// TODO: handle exception
			new TongYong().dayinerro(e);
		}
  	}
  	
  	
  	/**
  	 * 领取积分红包
  	 * @param pd user_id,user_type,ms_redpackage_id
  	 * @return
  	 */
  	public static  Map<String,Object>  getJfRedstatic(PageData pd){
  		Map<String,Object> map = new HashMap<String,Object>();
  		String result="1";
  		String message="领取成功！"; 
  		try {
  			BaseController.lock.lock();
  			String user_id=pd.getString("user_id");//领取人ID
			String user_type=pd.getString("user_type");//领取人类型
 			//判断是否已经领取了该积分红包
  			if(ServiceHelper.getAppMemberService().findJfRedHistoryById(pd) != null ){
				result="1";
				message="你已领取过当前红包";
				map.put("data", ServiceHelper.getAppMemberService().findJfRedHistoryById(pd));
 			}else{
    				if(ServiceHelper.getAppMemberService().findJfRedById(pd) != null && ServiceHelper.getAppMemberService().findJfRedById(pd).getString("isguoqi").equals("0")){
    						PageData redjfpd=ServiceHelper.getAppMemberService().findJfRedById(pd);	
    						String name="";
  							PageData mmmpd=new PageData();
  					        if(redjfpd.getString("user_type").equals("1")){
  					        	mmmpd.put("store_id", redjfpd.getString("user_id"));
    					        name=ServiceHelper.getAppStoreService().findById(mmmpd).getString("store_name");
  					        }else if(redjfpd.equals("2")){
  					        	mmmpd.put("member_id", redjfpd.getString("user_id"));
    					        name=ServiceHelper.getAppMemberService().findById(mmmpd).getString("name");
  					        }
  					        mmmpd=null;
  					        mmmpd=new PageData();
  					        if(user_type.equals("1")){
  					        	mmmpd.put("store_id", user_id);
  					        	mmmpd=ServiceHelper.getAppStoreService().findById(mmmpd);
  					        }else{
  					        	mmmpd.put("member_id", user_id);
  					        	mmmpd=ServiceHelper.getAppMemberService().findById(mmmpd);
  					        }
   					        String ms_redpackage_id=redjfpd.getString("ms_redpackage_id");
 							double redpackage_money=Double.parseDouble(redjfpd.getString("redpackage_money"));//总金钱
							double overget_money=Double.parseDouble(redjfpd.getString("overget_money"));//已领金钱
							int redpackage_number=Integer.parseInt(redjfpd.getString("redpackage_number"));//总红包
							int overget_number=Integer.parseInt(redjfpd.getString("overget_number"));//已领红包个数
							double getmoney=redpackage_money-overget_money;//剩余的钱
							if( getmoney > 0){
 										if(redpackage_number-overget_number > 1 ){
//											if(redjfpd.getString("redpackage_type").equals(Const.redtwo_type)){//平均
//												getmoney=redpackage_money/redpackage_number;
//						 					} else if(redjfpd.getString("redpackage_type").equals(Const.redone_type)){//随机
//												double lessmoney=redpackage_money-overget_money;
//												int lessnumber=redpackage_number-overget_number;
//												//获取平均值
//												double pjmoney=lessmoney/lessnumber;
//												double minpjmoney=pjmoney/2;
//												double maxpjmoney=pjmoney/2+pjmoney;
//												double suijimoney=StringUtil.getSuiJi(minpjmoney, maxpjmoney);
//												getmoney=suijimoney;
//						 					}
 											getmoney=redpackage_money/redpackage_number;
							 		    } 
 										if(user_type.equals("1")){//商家
 						 					double now_wealth=Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(mmmpd))+getmoney;
						 					mmmpd.put("now_wealth",  TongYong.df2.format(now_wealth));
											ServiceHelper.getAppStoreService().editWealthById(mmmpd);
											//新增财富历史记录
											String store_wealthhistory_id="LQ"+BaseController.getTimeID();
											PageData sweathpd=new PageData();
											sweathpd.put("store_wealthhistory_id",store_wealthhistory_id );
											sweathpd.put("wealth_type", "1");
											sweathpd.put("profit_type", "6");
											sweathpd.put("number", getmoney+"");
											sweathpd.put("store_id", user_id);
											if(redjfpd.getString("store_operator_id") == null){
												redjfpd.put("store_operator_id", "jy"+user_id);
											}
											sweathpd.put("store_operator_id",redjfpd.getString("store_operator_id")  );
											sweathpd.put("process_status", "1");
											sweathpd.put("pay_type", "nowpay");
											sweathpd.put("last_wealth", TongYong.df2.format(now_wealth));
											sweathpd.put("jiaoyi_id",ms_redpackage_id);
											ServiceHelper.getAppStoreService().saveWealthhistory(sweathpd);
		 									sweathpd=null;
			 		 						//新增总后台提现充值/记录
		 			 			   			PageData waterpd=new PageData();
		 				    				waterpd.put("pay_status","1");
		 				    	   			waterpd.put("waterrecord_id",store_wealthhistory_id);
		 				   					waterpd.put("user_id", user_id);
		 				   					waterpd.put("user_type", "1");
		 				    				waterpd.put("withdraw_rate","0");
		 				   					waterpd.put("money_type","11");
		 				   	 				waterpd.put("money", TongYong.df2.format(getmoney));
		 				   	 				waterpd.put("reduce_money", "0");
		 				   					waterpd.put("arrivalmoney",  TongYong.df2.format(getmoney));
		 				   					waterpd.put("nowuser_money",  ServiceHelper.getAppStoreService().sumStoreWealth(mmmpd) );
		 				   					waterpd.put("application_channel", "2" );
		 				    				waterpd.put("remittance_name", "抢积分红包" );
		 				    				waterpd.put("remittance_type","7" );
		 			   						waterpd.put("integral_money", TongYong.df2.format(getmoney) );
		 				   					waterpd.put("remittance_number",user_id);//支付人的支付账号
		 				    				waterpd.put("createtime",DateUtil.getTime());
		 				   					waterpd.put("over_time",DateUtil.getTime());
		 				   	  				waterpd.put("jiaoyi_type","0");
		 				   					waterpd.put("payee_number",user_id);
		 					    	 		waterpd.put("order_tn", store_wealthhistory_id);
		 				   					waterpd.put("province_name", mmmpd.getString("province_name"));
		 				   					waterpd.put("city_name", mmmpd.getString("city_name"));
		 				   					waterpd.put("area_name", mmmpd.getString("area_name"));
		 				    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
		 				    				waterpd=null;
		 							}else if(user_type.equals("2")){
 										double now_integral=Double.parseDouble(mmmpd.getString("now_integral"))+getmoney;
										mmmpd.put("now_integral", TongYong.df2.format(now_integral));
										ServiceHelper.getAppMemberService().edit(mmmpd);
										//新增财富历史记录
										PageData moneypd=new PageData();
										moneypd.put("member_id", user_id);
				 						moneypd.put("wealth_type", "1");
										moneypd.put("consume_type", "1");
										if(name.length() == 11){
											name=name.substring(0, 3)+"****"+name.substring(7, 11);
										}
										moneypd.put("content","领取"+name+" 积分红包");
										moneypd.put("number", TongYong.df2.format(getmoney));
										moneypd.put("now_money", TongYong.df2.format(now_integral));
										moneypd.put("jiaoyi_id", ms_redpackage_id);
										moneypd.put("in_jiqi", "1");
										moneypd.put("jiaoyi_status", "1");
				   						moneypd.put("member_wealthhistory_id", BaseController.getXFUID(mmmpd.getString("show_lookid")));
										ServiceHelper.getAppMember_wealthhistoryService().saveWealthhistory(moneypd);//新增个人消费历史
										moneypd=null;
										String member_wealthhistory_id=ServiceHelper.getAppMember_wealthhistoryService().getMaxMemberWealthId(mmmpd);
										//新增总后台提现充值/记录
	 			 			   			PageData waterpd=new PageData();
	 				    				waterpd.put("pay_status","1");
	 				    	   			waterpd.put("waterrecord_id",member_wealthhistory_id);
	 				   					waterpd.put("user_id", user_id);
	 				   					waterpd.put("user_type", "2");
	 				    				waterpd.put("withdraw_rate","0");
	 				   					waterpd.put("money_type","11");
	 				   	 				waterpd.put("money", TongYong.df2.format(getmoney));
	 				   	 				waterpd.put("reduce_money", "0");
	 				   					waterpd.put("arrivalmoney",  TongYong.df2.format(getmoney));
	 				   					waterpd.put("nowuser_money",  TongYong.df2.format(now_integral) );
	 				   					waterpd.put("application_channel", "2" );
	 				    				waterpd.put("remittance_name", "抢积分红包" );
	 				    				waterpd.put("remittance_type","7" );
	 			   						waterpd.put("integral_money", TongYong.df2.format(getmoney) );
	 				   					waterpd.put("remittance_number",user_id);//支付人的支付账号
	 				    				waterpd.put("createtime",DateUtil.getTime());
	 				   					waterpd.put("over_time",DateUtil.getTime());
	 				   	  				waterpd.put("jiaoyi_type","6");
	 				   					waterpd.put("payee_number",user_id);
	 					    	 		waterpd.put("order_tn", member_wealthhistory_id);
	 				   					waterpd.put("province_name", mmmpd.getString("province_name"));
	 				   					waterpd.put("city_name", mmmpd.getString("city_name"));
	 				   					waterpd.put("area_name", mmmpd.getString("area_name"));
	 				    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
	 				    				waterpd=null;
		 							
		 							 }
 									PageData redpd=new PageData();
									//新增积分红包历史记录
									redpd.put("user_id", user_id);
									redpd.put("overget_money", TongYong.df2.format(getmoney));
									redpd.put("user_type",user_type);
									redpd.put("ms_redpackage_id",ms_redpackage_id);
									ServiceHelper.getAppMemberService().saveJfMrRed(redpd);
									redpd=null;
									//更新红包记录
									if(overget_number+1 == redpackage_number){
										redjfpd.put("isguoqi", "1");
									}
									redjfpd.put("overget_number",overget_number+1);
									redjfpd.put("overget_money", TongYong.df2.format( overget_money+getmoney));
									ServiceHelper.getAppMemberService().editJfRed(redjfpd);
  							}else{
									result="0";
									message="手慢了，红包派完了";
 				 			}
							map.put("data", pd);
						    mmmpd=null;
				}else{
 						map.put("data", "");
						result="0";
						message="当前积分红包派发，已结束";
 				}
 			} 
 		} catch (Exception e) {
			// TODO: handle exception
			new TongYong().dayinerro(e);
		}finally{
			BaseController.lock.unlock();
		}
  		map.put("result", result);
  		map.put("message", message);
  		return map;
  	}
  	
  	/**
  	 * 新增商家
  	 * @param pd
  	 * @return
  	 */
  	public static PageData saveStore(PageData pd){
  		try {
  			String store_id="";//商家id 8位数字
			boolean flag=true;
 			while(flag){
				store_id=BaseController.get8UID();//商家id 8位数字
  				if(ServiceHelper.getStorepcService().getStoreId(store_id) != null && !ServiceHelper.getStorepcService().getStoreId(store_id).equals("")){
 					continue;
				}else{
					flag=false;
  				}
 			}
 			if(pd.getString("registertel_phone") == null || pd.getString("registertel_phone").equals("")){
 				pd.put("registertel_phone", pd.getString("phone"));
 			}else{
 				if(!pd.getString("registertel_phone").equals(pd.getString("phone"))){
 					pd.put("registertel_phone", pd.getString("phone"));
 				}
 			}
 			pd.put("store_id", store_id);	//注册商家主键
 			pd.put("only_store_id", BaseController.getOnlyStoreID(store_id));
  			pd.put("store_file_id", store_id);	//注册商家档案主键
			pd.put("store_vip_id",(int) (Math.random()*4+1));
			if(pd.getString("store_name") != null && !pd.getString("store_name").equals("")){
				if(ServiceHelper.getSp_fileService().findById(pd) != null){
					pd.put("sp_file_id", ServiceHelper.getSp_fileService().findById(pd).getString("sp_file_id"));//服务商ID
	 				String sp_phone=ServiceHelper.getSp_fileService().findById(pd).getString("phone");
					String principal=ServiceHelper.getSp_fileService().findById(pd).getString("principal");
					SmsUtil.sendStoreSp(sp_phone, principal, pd.getString("store_name"));
				}
				ServiceHelper.getStorepcService().save(pd);
				//发送短信
 				SmsUtil.sendZhuCeStore(pd.getString("registertel_phone"));
			}
			//生成二维码图片
			String imagename=store_id;
			String tuiguangUrl="https://www.jiuyuvip.com/html_member/goRegister.do?recommended="+store_id+"&recommended_type=1&recommended_phone="+store_id;
 			OneEr.printStore(tuiguangUrl, pd.getString("store_name") , imagename,Const.ErWeiMa);
 			String path_url=Const.ErWeiMa+ "/"+imagename+".png";
//	 		是否需要将图片上传至云服务器
 			String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
			String filePath = "/storeErFile";//文件上传路径
			String cityFilename =  FileUpload.fileUpFile(path_url, currentPath+filePath, imagename);//字符拼接，上传到服务器上
//			String path_url2= AppUtil.getuploadRootUrlIp()+filePath+"/"+cityFilename;
			//删除本地商家号
	 		FileUpload.deleteFile(path_url);
		} catch (Exception e) {
			// TODO: handle exception
			new TongYong().dayinerro(e);
		}
  		return pd;
  	}
  	
  	
  	
	
  	/**
  	 * YouHuiMaiDanByTwoForMember  6月16更新的代码专属C端以及公众号
  	 * 
  	 * 购买的优惠买单买单信息  pd,
  	 * youhui_money可优惠的金额(除去不优惠的金额)，
  	 * notyouhui_money 不优惠金额，
     * allgoods   购买商品拼接： 1.（商品id@数量@总金额，商品id@数量@总金额） 2.非商品购买的时候传“”空字符串
   	 * 
  	 */
  	public synchronized static Map<String,Object> YouHuiMaiDanByTwoForMember(PageData pd,double youhui_money,double notyouhui_money){
   		Map<String,Object> map = new HashMap<String,Object>();
  		List<PageData> yingxiaoList=new ArrayList<PageData>();//用来存储营销List
  		try {
    		String allgoods=pd.getString("allgoods");
    		boolean goodsFlag=(allgoods != null  && !allgoods.equals(""));//是否为购物车购买
  			double alljifeng=0;
   			//1.先获取营销中的折扣设置
  			PageData typepd=new PageData();
			typepd.put("marketing_type", "7");
			typepd.put("store_id", pd.getString("store_id"));
			List<PageData> zklist=ServiceHelper.getAppStorepc_marketingService().listAllById(typepd);
			String zkcontent="";
			double zkmoney=0;
			String zkid="";
 			int zklistlength=zklist.size();
			PageData e=null;
			for (int zi = 0; zi <zklistlength; zi++) {
 					e=zklist.get(zi);
 					String grantrule=e.getString("grantrule");
					String marketing_id=e.getString("marketing_id");
					e.put("store_discountway_id", marketing_id);
					//获取所有启用的折扣
 					PageData zkpd=ServiceHelper.getAppStorepc_marketingService().getZKById(e);
					if(zkpd != null){
							String zkgrantrule=zkpd.getString("grantrule");
							if(zkpd.getString("discount_type").equals("1")){//整店折扣
 									double n=0;
									if(zkpd.getString("onealldiscount_rate").length() == 1){
										n=1-Double.parseDouble(zkpd.getString("onealldiscount_rate"))/10.0;
									}else if(zkpd.getString("onealldiscount_rate").length() == 2){
										n=1-Double.parseDouble(zkpd.getString("onealldiscount_rate"))/100;
									}else if(zkpd.getString("onealldiscount_rate").length() == 3){
										n=1-Double.parseDouble(zkpd.getString("onealldiscount_rate"))/1000;
									}
	 								double m=n*youhui_money;
	 								if(m >= zkmoney ){
	 									zkcontent=grantrule;
	 									zkmoney=m;
 	 									zkid=marketing_id;
	 								}
							}else if(zkpd.getString("discount_type").equals("4")){//满多少折多少
								String[] str=zkgrantrule.split(",");
								int strlength=str.length;
 	 							for(int i=0; i< strlength ;i++){ 
 	  		 								double n1=Double.parseDouble(str[i].substring(str[i].indexOf("满")+1, str[i].indexOf("元")));
	  		 								double n2=0;
											if(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")).length() == 1){
												n2=1-Double.parseDouble(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")))/10.0;
											}else if(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")).length() == 2){
												n2=1-Double.parseDouble(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")))/100;
											}else if(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")).length() == 3){
												n2=1-Double.parseDouble(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")))/1000;
											}
	  		 								if(n2*youhui_money >zkmoney && youhui_money >=n1){
	  		 									zkcontent=str[i];
			 									zkmoney=n2*youhui_money;
 			 									zkid=marketing_id;
	  		 								}
 	 							}
							}else if(zkpd.getString("discount_type").equals("2") && goodsFlag  ){//按类别折扣
   			 								double m=0;
   			 								if(allgoods != null && !allgoods.equals("")){
	   			 								if(allgoods.contains("@")){
		   			 								String[] goods=allgoods.split(",");
				   			 							//循环所有商品
	   			 										for(int i=0 ; i<goods.length ; i++){
	   			 												PageData e6=new PageData();
	   			 											    String[] str1=goods[i].split("@");
		   			 											if(str1.length != 3){
		 			 												continue;
		 			 											}
 	   			 												e6.put("goods_id", str1[0]);
	   			 												e6=ServiceHelper.getAppGoodsService().goodsSortById(e6);
	   			 												if(e6 != null ){
 				   			 											if(e6.getString("zk_rate").length() == 1){
				   			 												double zkrate=1-Double.parseDouble(e6.getString("zk_rate"))/10.0;
				   			 												m+=zkrate*Double.parseDouble(str1[2]);
			   			 												}else if(e6.getString("zk_rate").length() == 2){
				   			 												double zkrate=1-Double.parseDouble(e6.getString("zk_rate"))/100;
				   			 												m+=zkrate*Double.parseDouble(str1[2]);
			   			 												}else if(e6.getString("zk_rate").length() ==3){
				   			 												double zkrate=1-Double.parseDouble(e6.getString("zk_rate"))/1000;
				   			 												m+=zkrate*Double.parseDouble(str1[2]);
			   			 												}
	   			 												}
	   			 												e6=null;
		   			 										}
		   			 							}
   			 								}
											  //判断总金钱折扣是否最大
											  if(m >= zkmoney){
												zkcontent=grantrule;
			 									zkmoney=m ;
 			 									zkid=marketing_id;
											  }
							}else if(zkpd.getString("discount_type").equals("3") && goodsFlag){//单品设置--购物车
  											String[] goods=allgoods.split(",");
  				 	 						double yhmoney=0;
				 	 						zkcontent=grantrule;
				 	 						String danpingcontent="";
 				 	 						for(int i=0; i<goods.length ;i++){
				 	 									PageData goodspd=new PageData();
 			 											String[] str=goods[i].split("@");
 			 											if(str.length != 3){
 			 												continue;
 			 											}
			 											goodspd.put("goods_id", str[0]);
			 											double buymoney=Double.parseDouble(str[2]);
			 											double buynumber=Double.parseDouble(str[1]);
			 											goodspd=ServiceHelper.getAppGoodsService().findById(goodspd);
			 											if(goodspd != null){
			 															//对时间进行判断
			 												 			long l1=new Date().getTime();
					 					   							  	long l2=DateUtil.fomatDate1(goodspd.get("starttime").toString()).getTime();
					 					   							  	long l3=DateUtil.fomatDate1(goodspd.get("endtime").toString()).getTime();
					 					   							  	boolean flag= l2<l1 && l3>l1 ;
					 					   							  	String goods_name=goodspd.getString("goods_name");
  						 												String promotion_type=goodspd.getString("promotion_type");
							 											String promotion_content=goodspd.getString("promotion_content");
 								 										if(!promotion_type.equals("0") && flag){//0-无促销，1-满减，2-单品折扣，3-买N件减1,4-送物品
	 							 											    if(promotion_type.equals("1")){
   													  		 								if(Double.parseDouble(promotion_content.substring(promotion_content.indexOf("减")+1, promotion_content.lastIndexOf("元"))) >=zkmoney && buymoney >= Double.parseDouble(promotion_content.substring(promotion_content.indexOf("满")+1, promotion_content.indexOf("元")))){
													  		 									yhmoney+=Double.parseDouble(promotion_content.substring(promotion_content.indexOf("减")+1, promotion_content.lastIndexOf("元")));
													  		 									danpingcontent+=goods_name+promotion_content+",";
													  		 								}
								 												}else if(promotion_type.equals("2")){
 										 													double n1=0;
										 													if(promotion_content.substring(0, promotion_content.indexOf("折")).length() == 1){
										 														n1=1-Double.parseDouble(promotion_content.substring(0, promotion_content.indexOf("折")))/10.0;
										 													}else if(promotion_content.substring(0, promotion_content.indexOf("折")).length() == 2){
										 														n1=1-Double.parseDouble(promotion_content.substring(0, promotion_content.indexOf("折")))/100;
										 													}else if(promotion_content.substring(0, promotion_content.indexOf("折")).length() == 3){
										 														n1=1-Double.parseDouble(promotion_content.substring(0, promotion_content.indexOf("折")))/1000;
										 													}
											 												double n2=buymoney*n1;
										 													if(n2 >=zkmoney){
										 														yhmoney+=n2;
										 														danpingcontent+=goods_name+promotion_content+",";
													  		 								}
									 										   }else if(promotion_type.equals("3")){
 									 													if(buynumber >= Double.parseDouble(promotion_content.substring(promotion_content.indexOf("买")+1, promotion_content.indexOf("件")))){
									 														 double n2=buymoney/buynumber;
		 								 													 if(n2 >zkmoney){
		 								 														 	yhmoney+=n2;
		 								 														 	danpingcontent+=goods_name+promotion_content+",";
														  		 							 }
									 													}
								 												}else{
								 													danpingcontent+=goods_name+promotion_content+",";
								 												}
							 											}
			 											}
			 											goodspd=null;
	 										}
 				 	 						//是否进行了单品折扣
 				 	 						if(!danpingcontent.equals("")){
 				 	 							zkcontent=danpingcontent.substring(0,danpingcontent.length()-1);
 				 	 						}
				 	 						if(yhmoney >= zkmoney ){
 			 									zkmoney=yhmoney ;
 			 									zkid=marketing_id;
				 	 						}
  							   } 
					}
					e=null;
			}
			//折扣设置的折扣
			if(!zkcontent.equals("") && zkmoney>0 ){
				PageData zkpd=new PageData();
				zkpd.put("content", zkcontent);
				zkpd.put("id", zkid);
				zkpd.put("type", "7");
				zkpd.put("name", "折扣");
				zkpd.put("number", "-"+df2.format(zkmoney));
				yingxiaoList.add(zkpd);
			}
			//是否为购物车购买的--今日特价商品只是不参与折扣优惠其他照常参加
			if(goodsFlag){
				youhui_money=youhui_money+notyouhui_money;
				notyouhui_money=0;
			}
 			//2.获取其他的营销规则
			List<PageData> marketlist=ServiceHelper.getAppStorepc_marketingService().listAllById(pd);
			PageData e1=new PageData();
			PageData e2=new PageData();
  			//优惠内容
			String zengcontent="";
 			String zengid="";
			String zengtype="";
			String zengcontent2="";
			String zengid2="";
 			String zengtype2="";
			String jiancontent="";
			String jiantype="";
			String jianid="";
			String jianname="";
			double reducemoney=0;
			int marketlistlength=marketlist.size();
			for (int mi = 0; mi <marketlistlength ; mi++) {
 	 				e=marketlist.get(mi);
					/*
					 * *1-满赠，*2-满减，3-时段营销，4-买N减N（针对商品），5-累计次数/购买金额--增,6-积分，7-折扣
					 */
					String marketing_type=e.getString("marketing_type");
					String grantrule=e.getString("grantrule");
					String marketing_id=e.getString("marketing_id");
					if(marketing_type.equals("1")){
		 				if(youhui_money >= Double.parseDouble(grantrule.substring(grantrule.indexOf("满")+1, grantrule.indexOf("元")))){
		 						zengcontent=grantrule;
		 						zengid=marketing_id;
		 						zengtype=marketing_type;
  						} 
					}else if(marketing_type.equals("2")){
						if(grantrule.contains("折")){
		 					double n2=0;
		 					if(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")).length() == 1){
	 							n2=1-Double.parseDouble(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")))/10;
	 						}else if(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")).length() == 2){
	 							n2=1-Double.parseDouble(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")))/100;
	 						}else if(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")).length() == 3){
	 							n2=1-Double.parseDouble(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")))/1000;
	 						}
		 					if(n2*youhui_money >reducemoney && youhui_money >= Double.parseDouble(grantrule.substring(grantrule.indexOf("满")+1, grantrule.indexOf("元")))){
		 						jiancontent=grantrule;
		 						reducemoney=n2*youhui_money;
		 						jiantype=marketing_type;
		 						jianid=marketing_id;
		 						jianname="立减";
		 					}
						}else{
							if(Double.parseDouble(grantrule.substring(grantrule.indexOf("减")+1, grantrule.lastIndexOf("元"))) >reducemoney && youhui_money >= Double.parseDouble(grantrule.substring(grantrule.indexOf("满")+1, grantrule.indexOf("元")))){
		 						jiancontent=grantrule;
		 						reducemoney=Double.parseDouble(grantrule.substring(grantrule.indexOf("减")+1, grantrule.lastIndexOf("元")));
		 						jiantype=marketing_type;
		 						jianid=marketing_id;
		 						jianname="立减";
		 					}
						}
					}else if(marketing_type.equals("6") ){
						//必须开启
					}else if(marketing_type.equals("7")){
						//("第一步先进行折扣");
					}else if(marketing_type.equals("3")){
						 PageData sdpd=new PageData();
						 sdpd.put("marketing_id", marketing_id);
						 sdpd.put("store_id", pd.getString("store_id"));
						 sdpd=ServiceHelper.getStorepc_marketingtypeService().findById(sdpd);
						 if(sdpd != null){
 								long l1=new Date().getTime();
		   						long l2=DateUtil.fomatDate1(DateUtil.getDay()+" "+sdpd.get("starttime").toString()).getTime();
		   						long l3=DateUtil.fomatDate1(DateUtil.getDay()+" "+sdpd.get("endtime").toString()).getTime();
		   						if(  Double.parseDouble(sdpd.getString("threeachieve_money")) <= youhui_money &&  l1 > l2 && l1 < l3 ){
		   							if(sdpd.getString("marketsmall_type").equals("1")){//折
		   								if(sdpd.getString("threediscount_rate").length() == 1){
		   									double mm=youhui_money*(1-Double.parseDouble(sdpd.getString("threediscount_rate"))/10.0);
								  	        if(  mm > reducemoney){
 	   								  	        jiancontent=grantrule;
	  		 									reducemoney=mm;
	  		 									jiantype=marketing_type;
	  		 									jianid=marketing_id;
	  		 									jianname="时段";
 								  	        }
		   								}else  if(sdpd.getString("threediscount_rate").length() == 2){
    								  		 double mm=youhui_money*(1-Double.parseDouble(sdpd.getString("threediscount_rate"))/100);
   								  	         if(  mm > reducemoney){
 		   								  	        jiancontent=grantrule;
		  		 									reducemoney=mm;
		  		 									jiantype=marketing_type;
		  		 									jianid=marketing_id;
		  		 									jianname="时段";
    								  	         }
		   								}else  if(sdpd.getString("threediscount_rate").length() == 3){
    								  		 double mm=youhui_money*(1-Double.parseDouble(sdpd.getString("threediscount_rate"))/1000);
   								  	         if(  mm > reducemoney){
				   								  	jiancontent=grantrule;
				   								  	reducemoney=mm;
				  		 							jiantype=marketing_type;
				  		 							jianid=marketing_id;
				  		 							jianname="时段";
   								  	         }
							  		 	 
		   								} 
			   					}else{
 							  		  double mm= Double.parseDouble(sdpd.getString("threereduce_money"));
	   								  if( mm> reducemoney){
  								  	        jiancontent=grantrule;
		 									reducemoney= Double.parseDouble(sdpd.getString("threereduce_money"));
		 									jiantype=marketing_type;
		 									jianid=marketing_id;
		 									jianname="时段";
 	   								  }
  			   					}
		   					}
 						 }
						sdpd=null;
					}else if(marketing_type.equals("4")  && goodsFlag  ){//买N减N（针对商品）
						     PageData mnpd=new PageData();
							 mnpd.put("marketing_id", marketing_id);
							 mnpd.put("store_id", pd.getString("store_id"));
							 mnpd=ServiceHelper.getStorepc_marketingtypeService().findById(mnpd);
							 if(mnpd != null){
	   								  long l1=new Date().getTime();
			   						  long l2=DateUtil.fomatDate1(DateUtil.getDay()+" "+mnpd.get("starttime").toString()).getTime();
		   							  long l3=DateUtil.fomatDate1(DateUtil.getDay()+" "+mnpd.get("endtime").toString()).getTime();
		   							  if(  l1 > l2 && l1 < l3 ){
		   								  	 String[] goods=allgoods.split(",");
		   								  	 double m=0;
		   								  	 double buynumber=0;
		   								  	 double minmoney=0;
			   								 for(int i=0; i<goods.length ;i++){
			   									 String[] str=goods[i].split("@");//所有商品：商品id@数量@总金额 
			   									if(str.length != 3){
		 												continue;
		 											}
			   									if(mnpd.getString("goods_id").contains(str[0])){
			   										 buynumber+=Double.parseDouble(str[1]);
			   										 if(Double.parseDouble(str[2] ) / Double.parseDouble(str[1]) < minmoney){
			   											   minmoney=Double.parseDouble(str[2] ) / Double.parseDouble(str[1]);
			   										 }
			   									 }
			   									 if( Double.parseDouble(mnpd.getString("fourachieve_number")) <= buynumber){
			   										 m=minmoney;
			   									 }
				   								 }
			   								 if(m > reducemoney){
				   									jiancontent=grantrule;
		  		 									reducemoney= m;
		  		 									jiantype=marketing_type;
		  		 									jianid=marketing_id;
		  		 									jianname="立减";
			   								 }
			   							  }
							 }
							mnpd=null;
					}else if(marketing_type.equals("5")){
   						PageData ljpd=new PageData();
   						ljpd.put("marketing_id", marketing_id);
   						ljpd.put("store_id", pd.getString("store_id"));
   						ljpd=ServiceHelper.getStorepc_marketingtypeService().findById(ljpd);
		   				if(ljpd != null){
		   					  long l1=new Date().getTime();
		   					  long l2=DateUtil.fomatDate1(DateUtil.getDay()+" "+ljpd.get("starttime").toString()).getTime();
 							  long l3=DateUtil.fomatDate1(DateUtil.getDay()+" "+ljpd.get("endtime").toString()).getTime();
 							  if(  l1 > l2 && l1 < l3 ){
	   								PageData orderpd=new PageData();
			   						orderpd.put("starttime", DateUtil.getDay()+" "+ljpd.get("starttime").toString());
			   						orderpd.put("endtime", DateUtil.getDay()+" "+ljpd.get("endtime").toString());
			   						orderpd.put("store_id", pd.getString("store_id"));
			   						orderpd.put("member_id", pd.getString("member_id"));
		   							orderpd=ServiceHelper.getAppOrderService().listhistoryNumberByStore(orderpd);
		   							String number=orderpd.getString("number");
		   							String sumsale_money="0";
			   						if(orderpd.get("sumsale_money") != null){
		   								sumsale_money=orderpd.get("sumsale_money").toString();
		   							};
		   							if(Integer.parseInt(number)%Integer.parseInt(ljpd.getString("fiveachieve_number")) == 0 && Double.parseDouble(sumsale_money)%Double.parseDouble(ljpd.getString("fiveachieve_money")) >= 0 && Double.parseDouble(sumsale_money)%Double.parseDouble(ljpd.getString("fiveachieve_money")) < 1){
		   									//判断哪个红包适合
		   								zengcontent2=grantrule;
	 									zengid2=marketing_id;
	 									zengtype2=marketing_type;
		   							}
		   							orderpd=null;
	   						}
 		   				}
 					}
					e=null;
 			}
			marketlist=null;
			//满赠
			if(!zengcontent.equals("")){
				e1.put("name", "满赠");e1.put("content", zengcontent);e1.put("number", "");e1.put("id", zengid);e1.put("type", zengtype);
				yingxiaoList.add(e1);
			}
			e1=null;
			//累计次数
			PageData ldpd=new PageData();
			if(!zengcontent2.equals("")){
				ldpd.put("name", "累积");ldpd.put("content", zengcontent2);ldpd.put("number", "");ldpd.put("id", zengid2);ldpd.put("type", zengtype2);
				yingxiaoList.add(ldpd);
			}
			ldpd=null;
			//满减
			if(!jiancontent.equals("") && reducemoney>0 ){
				e2.put("name", jianname);e2.put("content", jiancontent);e2.put("id", jianid);e2.put("type", jiantype);e2.put("number", "-"+df2.format(reducemoney));
				yingxiaoList.add(e2);
			}
			 e2=null;
			 String discount_content="";
			 int yingxiaosize=yingxiaoList.size();
			 //判断优惠后的金额是否已经是0
			 String redpackage_id=(pd.getString("redpackage_id") == null?"":pd.getString("redpackage_id"));
			 String redmoney="0";
			 List<PageData> canUseRedList=getAllStoreRedMoneyByMember(pd, notyouhui_money, yingxiaosize, reducemoney);
			 double useredbeforMoney=reducemoney+zkmoney;//使用红包前的总共优惠了的金额
			 PageData canUsePd=null;
			 String redMessage="暂无可使用红包";
			 if(useredbeforMoney < youhui_money+notyouhui_money){
				 	/**
				 	 * 1先判断有red_id表示使用某个红包，2:如果没有则获取可使用的红包列表
				 	 */
				    if(redpackage_id.equals("")){
				    	canUseRedList=getAllStoreRedMoneyByMember(pd, notyouhui_money, yingxiaosize, reducemoney);
				    	canUsePd=new PageData();
				    	if(canUseRedList.size() >0 ){
				    		redMessage="有可用红包";
				    	}
  				    }else{
				    	canUsePd=getRedPackageInforByID(redpackage_id, youhui_money, reducemoney);
				    	if(canUsePd == null){
				    		canUsePd=new PageData();
				    	}else{
				    		redMessage=canUsePd.getString("content");
				    		discount_content=canUsePd.getString("content")+"@"+canUsePd.getString("id")+"@"+canUsePd.getString("number")+"@"+canUsePd.getString("type")+",";
				    	}
				    }
 			 }
			 map.put("canUseRedList", canUseRedList);//可以使用的红包集合
			 map.put("canUsePd", canUsePd);
			 map.put("redMessage", redMessage);//显示红包的信息
 			//使用红包后的优惠后的实际应该支付的金额为
			double surepaymoney=youhui_money-reducemoney-Double.parseDouble(redmoney)-zkmoney+notyouhui_money;
			//总共优惠金额
			double surehuiyoumoney=reducemoney+Double.parseDouble(redmoney)+zkmoney;
			//获得积分
 			double addjf=0;
 			//获取所有启用的积分
			PageData jfpd=ServiceHelper.getAppStorepc_marketingService().getJfById(pd);
 			PageData e3=new PageData();
			if(jfpd != null){
				double redhuiyoumoney=surepaymoney-notyouhui_money;
				if(redhuiyoumoney < 0){
					redhuiyoumoney=0;
				}
	 	 		if(jfpd.getString("change_type").equals("1")){//整店
	 	 			if(Double.parseDouble(jfpd.getString("oneback_rate"))/100*redhuiyoumoney >= addjf){
 	 					addjf=Double.parseDouble(jfpd.getString("oneback_rate"))/100*redhuiyoumoney;
  	 				}
	 	 		}else if(jfpd.getString("change_type").equals("5")){//满多少送多少积分
	 					String[] str=jfpd.getString("grantrule").split(",");
	 					int strlength=str.length;
	 					for(int i=0; i<strlength ;i++){
	  		  				if(Double.parseDouble(str[i].substring(str[i].indexOf("满")+1, str[i].indexOf("元"))) <= redhuiyoumoney){
  		 					if(Double.parseDouble(str[i].substring(str[i].indexOf("满")+1, str[i].indexOf("元")))*Double.parseDouble(str[i].substring(str[i].indexOf("送")+1, str[i].lastIndexOf("%")))/100.00 >= addjf ){
  		 						addjf=Double.parseDouble(str[i].substring(str[i].indexOf("满")+1, str[i].indexOf("元")))*Double.parseDouble(str[i].substring(str[i].indexOf("送")+1, str[i].lastIndexOf("%")))/100.00;
 	  		 				}
  		  				}
	 					 }
	 			}else if(jfpd.getString("change_type").equals("4")){//每单返积分
		 	 		if(Double.parseDouble(jfpd.getString("fourbackintegral_integral")) >= addjf){
	 	 				addjf=Double.parseDouble(jfpd.getString("fourbackintegral_integral"));
 	 	 			}
	 			}else if(jfpd.getString("change_type").equals("3")  && goodsFlag){//单品送积分
 	 				String[] goods=allgoods.split(",");
 	 				PageData goodspd=new PageData();
 	 				double m=0;
 	 				int goodslength=goods.length;
					for(int i=0; i< goodslength ;i++){
						if(goods[i].split("@").length != 3){
							continue;
						}
						goodspd.put("goods_id", goods[i].split("@")[0]);
						goodspd=ServiceHelper.getAppGoodsService().findById(goodspd);
						if(goodspd.getString("integral_rate") != null && !goodspd.getString("integral_rate").equals("")){
								m+=Double.parseDouble( goods[i].split("@")[2] )*Double.parseDouble(goodspd.getString("integral_rate"))/100.00;
						}
					}
					goodspd=null;
					if(m >= addjf){
						addjf=m;
 					}
	 			}else if(jfpd.getString("change_type").equals("2") && goodsFlag){//类别送积分
	 	 			String[] goods=allgoods.split(",");
	 				PageData goodspd=new PageData();
	 				double m=0;
	 				int goodslength=goods.length;
	 				for(int i=0; i< goodslength ;i++){
	 					if(goods[i].split("@").length != 3){
							continue;
						}
						goodspd.put("goods_id", goods[i].split("@")[0]);
						goodspd=ServiceHelper.getAppGoodsService().findById(goodspd);
							if(goodspd.getString("integral_rate") != null && !goodspd.getString("integral_rate").equals("")){
								m+=Double.parseDouble( goods[i].split("@")[2] )*Double.parseDouble(goodspd.getString("integral_rate"))/100.00;
						}
					}
	 				goodspd=null;
	 				if(m >= addjf){
	 					addjf=m;
 					}
	 			}else{
	 				  //无
	 			}
	 	 		//积分
	 	 		e3.put("content", jfpd.getString("grantrule")); e3.put("number", "+"+df2.format(addjf)); e3.put("id", jfpd.getString("store_scoreway_id")); e3.put("type", "6");
	 	 		e3.put("name", "积分");
	 	 		yingxiaoList.add(e3);
 			}
   			//处理营销
			if(youhui_money+notyouhui_money <= 0){
 	  			map.put("yingxiaoList", new ArrayList<PageData>());
 	  		}else{
 	  			map.put("yingxiaoList", yingxiaoList);
 	  		}
 			for (PageData dispd : yingxiaoList) {
 				discount_content+=dispd.getString("content")+"@"+dispd.getString("id")+"@"+dispd.getString("number")+"@"+dispd.getString("type")+",";
			}
    		PageData countpd=new PageData();//总统计消费金额以及使用红包情况
   			countpd.put("store_redpackets_id", zengid2+","+zengid);//增红包,id的集合
 			countpd.put("zengjf", df2.format(addjf+alljifeng));//赠送的积分
 			countpd.put("redpackage_id", redpackage_id);//使用红包的ID
  			countpd.put("allmoney", df2.format(youhui_money+notyouhui_money));//总金额
  			countpd.put("notmoney", df2.format(notyouhui_money));//不优惠金额
 			if(surepaymoney <= 0){
				countpd.put("paymoney", "0");//优惠后的支付金额
				countpd.put("reducemoney", df2.format(youhui_money+notyouhui_money));//优惠金额=优惠的金额+红包优惠的金额+折扣的金额
			}else{
				countpd.put("paymoney", df2.format(surepaymoney));
				countpd.put("reducemoney", df2.format(surehuiyoumoney));
			}
 			countpd.put("discount_content", discount_content);//优惠内容
 			map.put("countpd", countpd);
 			countpd=null;
 			//获取个人财富
 			map.put("memberInfor", ServiceHelper.getAppMemberService().findWealthById(pd));
			//判断是否开通类别积分购买的权限：0-总金额/分类买单都没开通，1-开通了总金额买单，3-开通了类别买单
 			if(ServiceHelper.getAppStorepc_marketingService().getJfById(pd) != null){
 				if( ServiceHelper.getAppStorepc_marketingService().getJfById(pd).getString("change_type").equals("3") ){
 					map.put("issortjf", "3");
 				}else if( ServiceHelper.getAppStorepc_marketingService().getJfById(pd).getString("change_type").equals("2") ){
 					map.put("issortjf", "1");
 				} else{
 					map.put("issortjf", "0");
 				}
 			}
 			//商家名称
 			map.put("store_name", ServiceHelper.getAppStoreService().findById(pd).getString("store_name"));
 			//获取商家的营销规则明细
 			map.put("yxpd", markeingAll(pd));
  		} catch (Exception e) {
			// TODO: handle exception
 			(new TongYong()).dayinerro(e);
 			return null;
		}
  		return map;
  	}
  	
  	

  	/**
  	 * YouHuiMaiDanByTwoForStoreMaiDan
  	 * 购买的优惠买单买单信息pd,
  	 * youhui_money可优惠的金额(除去不优惠的金额)，
  	 * notyouhui_money 不优惠金额，
   	 * pay_sort_type 买单类别：1-总金额，2-类别买单
     * allleibie  类别购买字段拼接：拆分类别 （类别ID@金钱@积分率@折扣率）
  	 * @return
  	 * 
  	 */
  	public synchronized static Map<String,Object> YouHuiMaiDanByTwoForStoreMaiDan(PageData pd,double youhui_money,double notyouhui_money){
   		Map<String,Object> map = new HashMap<String,Object>();
  		List<PageData> yingxiaoList=new ArrayList<PageData>();//用来存储营销List
  		try {
 			String allleibie=pd.getString("allleibie");
 			boolean leibieFlag=(allleibie != null  && !allleibie.equals(""));//是否为分类购买
   			double alljifeng=0;
  			if(leibieFlag){//充值总支付金额
 	  			if(ServiceHelper.getAppStorepc_marketingService().getJfById(pd) == null || !ServiceHelper.getAppStorepc_marketingService().getJfById(pd).getString("change_type").equals("2") ){
	  					map.put("message", "暂未开通该通道");
	  					return map;
  				}
  				PageData ispd=ServiceHelper.getAppStorepc_marketingService().getJfById(pd);
	  				map.put("sortList", ServiceHelper.getAppGoodsService().listAllBigSort(pd));//类别集合
	  				if(leibieFlag  && allleibie.contains("@")){
  							String[] everylei=allleibie.split(",");
  							int everyleilength=everylei.length;
  							for(int i=0;i<everyleilength ; i++){
	  								  if(everylei[i].split("@")[1] != null && !everylei[i].split("@")[1].equals("") && !everylei[i].split("@")[1].equals("null")){
  									  if(everylei[i].split("@")[2] != null && !everylei[i].split("@")[2].equals("null") && !everylei[i].split("@")[2].equals("")){
  										  alljifeng+= Double.parseDouble(everylei[i].split("@")[1])*Double.parseDouble(everylei[i].split("@")[2])/100;
  										  youhui_money+= Double.parseDouble(everylei[i].split("@")[1]);
  									  }
  								  }
	  							}
  				}
	  			//积分（获取被选中的积分设置）
  				if(alljifeng >= 0){
  					PageData jfpd=new PageData(); jfpd.put("content", "分类赠送积分"); jfpd.put("number", "+"+df2.format( alljifeng ));
  	 				jfpd.put("type", "6"); jfpd.put("id", ispd.getString("store_scoreway_id"));
  					yingxiaoList.add(jfpd);
  					jfpd=null;
  				}
   			}
   			//1.先获取营销中的折扣设置
  			PageData typepd=new PageData();
			typepd.put("marketing_type", "7");
			typepd.put("store_id", pd.getString("store_id"));
			List<PageData> zklist=ServiceHelper.getAppStorepc_marketingService().listAllById(typepd);
			String zkcontent="";
			double zkmoney=0;
			String zkid="";
 			int zklistlength=zklist.size();
			PageData e=null;
			for (int zi = 0; zi <zklistlength; zi++) {
 					e=zklist.get(zi);
 					String grantrule=e.getString("grantrule");
					String marketing_id=e.getString("marketing_id");
					e.put("store_discountway_id", marketing_id);
					//获取所有启用的折扣
 					PageData zkpd=ServiceHelper.getAppStorepc_marketingService().getZKById(e);
					if(zkpd != null){
							String zkgrantrule=zkpd.getString("grantrule");
							if(zkpd.getString("discount_type").equals("1")){//整店折扣
 									double n=0;
									if(zkpd.getString("onealldiscount_rate").length() == 1){
										n=1-Double.parseDouble(zkpd.getString("onealldiscount_rate"))/10.0;
									}else if(zkpd.getString("onealldiscount_rate").length() == 2){
										n=1-Double.parseDouble(zkpd.getString("onealldiscount_rate"))/100;
									}else if(zkpd.getString("onealldiscount_rate").length() == 3){
										n=1-Double.parseDouble(zkpd.getString("onealldiscount_rate"))/1000;
									}
	 								double m=n*youhui_money;
	 								if(m >= zkmoney ){
	 									zkcontent=grantrule;
	 									zkmoney=m;
 	 									zkid=marketing_id;
	 								}
							}else if(zkpd.getString("discount_type").equals("4")){//满多少折多少
								String[] str=zkgrantrule.split(",");
								int strlength=str.length;
 	 							for(int i=0; i< strlength ;i++){ 
 	  		 								double n1=Double.parseDouble(str[i].substring(str[i].indexOf("满")+1, str[i].indexOf("元")));
	  		 								double n2=0;
											if(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")).length() == 1){
												n2=1-Double.parseDouble(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")))/10.0;
											}else if(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")).length() == 2){
												n2=1-Double.parseDouble(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")))/100;
											}else if(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")).length() == 3){
												n2=1-Double.parseDouble(str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折")))/1000;
											}
	  		 								if(n2*youhui_money >zkmoney && youhui_money >=n1){
	  		 									zkcontent=str[i];
			 									zkmoney=n2*youhui_money;
 			 									zkid=marketing_id;
	  		 								}
 	 							}
							}else if(zkpd.getString("discount_type").equals("2") && leibieFlag ){//按类别折扣
   			 					double m=0;
 	 							if(allleibie != null && allleibie.contains("@")){
 		  							String[] everylei=allleibie.split(",");
 		  							int everyleilength=everylei.length;
  		  							for(int i=0;i<everyleilength ; i++){
 		  								if(everylei[i].split("@")[1] != null && !everylei[i].split("@")[1].equals("") && !everylei[i].split("@")[1].equals("null")){
		  									  if(everylei[i].split("@")[3] != null && !everylei[i].split("@")[3].equals("null") && !everylei[i].split("@")[3].equals("")){
		  										  if(everylei[i].split("@")[3].length() == 2){
		  											  m+= Double.parseDouble(everylei[i].split("@")[1])*Double.parseDouble(everylei[i].split("@")[3])/100;
		  										  }else{
		  											  m+= Double.parseDouble(everylei[i].split("@")[1])*Double.parseDouble(everylei[i].split("@")[3])/10;
		  										  }
		  										  
 		  									  }
		  								}
  	 	  							}
 	 							}
 								//判断总金钱折扣是否最大
								 if(m >= zkmoney){
									zkcontent=grantrule;
			 						zkmoney=m ;
 			 						zkid=marketing_id;
								}
							}else{ 
								//单品设置
							} 
					}
					e=null;
			}
			//折扣设置的折扣
			if(!zkcontent.equals("") && zkmoney>0 ){
				PageData zkpd=new PageData();
				zkpd.put("content", zkcontent);
				zkpd.put("id", zkid);
				zkpd.put("type", "7");
				zkpd.put("number", "-"+df2.format(zkmoney));
				yingxiaoList.add(zkpd);
			}
 			//2.获取其他的营销规则
			List<PageData> marketlist=ServiceHelper.getAppStorepc_marketingService().listAllById(pd);
			PageData e1=new PageData();
			PageData e2=new PageData();
			PageData e3=new PageData();
			//获得积分
 			double addjf=0;
			String desc="";
			String addjfid="";
			//优惠内容
			String zengcontent="";
			String zengid="";
			String zengtype="";
			String zengcontent2="";
			String zengid2="";
			String zengtype2="";
			String jiancontent="";
			String jiantype="";
			String jianid="";
			double reducemoney=0;
			int marketlistlength=marketlist.size();
			for (int mi = 0; mi <marketlistlength ; mi++) {
				 			e=marketlist.get(mi);
   								/*
								 * *1-满赠，*2-满减，3-时段营销，4-买N减N（针对商品），5-累计次数/购买金额--增,6-积分，7-折扣
								 */
								String marketing_type=e.getString("marketing_type");
//								String change_type=e.getString("change_type");
		 						String grantrule=e.getString("grantrule");
	 							String marketing_id=e.getString("marketing_id");
  	 							if(marketing_type.equals("1")){
 				 							if(youhui_money >= Double.parseDouble(grantrule.substring(grantrule.indexOf("满")+1, grantrule.indexOf("元")))){
					 							zengcontent=grantrule;
					 							zengid=marketing_id;
					 							zengtype=marketing_type;
			 								} 
 	  							}else if(marketing_type.equals("2")){
 	  								
 	  								
	  									if(grantrule.contains("折")){
  		  		 								double n2=0;
				 								if(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")).length() == 1){
				 									n2=1-Double.parseDouble(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")))/10;
				 								}else if(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")).length() == 2){
				 									n2=1-Double.parseDouble(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")))/100;
				 								}else if(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")).length() == 3){
				 									n2=1-Double.parseDouble(grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折")))/1000;
				 								}
		  		 								if(n2*youhui_money >reducemoney && youhui_money >= Double.parseDouble(grantrule.substring(grantrule.indexOf("满")+1, grantrule.indexOf("元")))){
		  		 									jiancontent=grantrule;
		  		 									reducemoney=n2*youhui_money;
		  		 									jiantype=marketing_type;
		  		 									jianid=marketing_id;
		  		 								}
	  									}else{
		  										if(Double.parseDouble(grantrule.substring(grantrule.indexOf("减")+1, grantrule.lastIndexOf("元"))) >reducemoney && youhui_money >= Double.parseDouble(grantrule.substring(grantrule.indexOf("满")+1, grantrule.indexOf("元")))){
		  		 									jiancontent=grantrule;
		  		 									reducemoney=Double.parseDouble(grantrule.substring(grantrule.indexOf("减")+1, grantrule.lastIndexOf("元")));
		  		 									jiantype=marketing_type;
		  		 									jianid=marketing_id;
		  		 								}
	  									}
	   							}else if(marketing_type.equals("6")){
	   								//肯定有
 	   							}else if(marketing_type.equals("7")){
	   								//("第一步先进行折扣");
	   							}else if(marketing_type.equals("3")){
	   							  PageData sdpd=new PageData();
	   							  sdpd.put("marketing_id", marketing_id);
	   							  sdpd.put("store_id", pd.getString("store_id"));
	   							  sdpd=ServiceHelper.getStorepc_marketingtypeService().findById(sdpd);
	   							  if(sdpd != null){
	   								  		  long l1=new Date().getTime();
				   							  long l2=DateUtil.fomatDate1(DateUtil.getDay()+" "+sdpd.get("starttime").toString()).getTime();
				   							  long l3=DateUtil.fomatDate1(DateUtil.getDay()+" "+sdpd.get("endtime").toString()).getTime();
				   							  if(  Double.parseDouble(sdpd.getString("threeachieve_money")) <= youhui_money &&  l1 > l2 && l1 < l3 ){
				   								  	 if(sdpd.getString("marketsmall_type").equals("1")){//折
				   								  		 	 if(sdpd.getString("threediscount_rate").length() == 1){
						   								  		 	double mm=youhui_money*(1-Double.parseDouble(sdpd.getString("threediscount_rate"))/10.0);
						   								  	         if(  mm > reducemoney){
										   								  	        jiancontent=grantrule;
										  		 									reducemoney=mm;
										  		 									jiantype=marketing_type;
										  		 									jianid=marketing_id;
						   								  	         }
				   								  		 	 }else  if(sdpd.getString("threediscount_rate").length() == 2){
						   								  		 	double mm=youhui_money*(1-Double.parseDouble(sdpd.getString("threediscount_rate"))/100);
						   								  	         if(  mm > reducemoney){
										   								  	        jiancontent=grantrule;
										  		 									reducemoney=mm;
										  		 									jiantype=marketing_type;
										  		 									jianid=marketing_id;
						   								  	         }
				   								  		 	 }else  if(sdpd.getString("threediscount_rate").length() == 3){
						   								  		 	double mm=youhui_money*(1-Double.parseDouble(sdpd.getString("threediscount_rate"))/1000);
						   								  	         if(  mm > reducemoney){
										   								  	        jiancontent=grantrule;
										  		 									reducemoney=mm;
										  		 									jiantype=marketing_type;
										  		 									jianid=marketing_id;
						   								  	         }
				   								  		 	 } 
					   								  	}else{//钱
				   								  		   double mm= Double.parseDouble(sdpd.getString("threereduce_money"));
						   								  	if( mm> reducemoney){
								   								  	        jiancontent=grantrule;
								  		 									reducemoney= Double.parseDouble(sdpd.getString("threereduce_money"));
								  		 									jiantype=marketing_type;
								  		 									jianid=marketing_id;
													  	         }
				   								  	 }
				   							  }
	   							  }
	   							sdpd=null;
 	   						} else if(marketing_type.equals("5")){
			   						PageData ljpd=new PageData();
			   						ljpd.put("marketing_id", marketing_id);
			   						ljpd.put("store_id", pd.getString("store_id"));
			   						ljpd=ServiceHelper.getStorepc_marketingtypeService().findById(ljpd);
					   				if(ljpd != null){
				   								  long l1=new Date().getTime();
		 	 			   						  long l2=DateUtil.fomatDate1(DateUtil.getDay()+" "+ljpd.get("starttime").toString()).getTime();
					   							  long l3=DateUtil.fomatDate1(DateUtil.getDay()+" "+ljpd.get("endtime").toString()).getTime();
					   							  if(  l1 > l2 && l1 < l3 ){
						   								PageData orderpd=new PageData();
				 	 			   						orderpd.put("starttime", DateUtil.getDay()+" "+ljpd.get("starttime").toString());
				 	 			   						orderpd.put("endtime", DateUtil.getDay()+" "+ljpd.get("endtime").toString());
				 	 			   						orderpd.put("store_id", pd.getString("store_id"));
				 	 			   						orderpd.put("member_id", pd.getString("member_id"));
			 				   							orderpd=ServiceHelper.getAppOrderService().listhistoryNumberByStore(orderpd);
			 				   							String number=orderpd.getString("number");
			 				   							String sumsale_money="0";
				 				   						if(orderpd.get("sumsale_money") != null){
							   								sumsale_money=orderpd.get("sumsale_money").toString();
							   							};
							   							if(Integer.parseInt(number)%Integer.parseInt(ljpd.getString("fiveachieve_number")) == 0 && Double.parseDouble(sumsale_money)%Double.parseDouble(ljpd.getString("fiveachieve_money")) >= 0 && Double.parseDouble(sumsale_money)%Double.parseDouble(ljpd.getString("fiveachieve_money")) < 1){
		 				   									//判断哪个红包适合
			 				   								zengcontent2=grantrule;
						 									zengid2=marketing_id;
						 									zengtype2=marketing_type;
							   							}
							   							orderpd=null;
						   						}
					   				}
				   					 
		   						}
 	 							e=null;
		}
			marketlist=null;
			//满赠
			if(!zengcontent.equals("")){
				e1.put("content", zengcontent);
				e1.put("number", "");
				e1.put("id", zengid);
				e1.put("type", zengtype);
				yingxiaoList.add(e1);
			}
			e1=null;
			//累计次数
			PageData ldpd=new PageData();
			if(!zengcontent2.equals("")){
				ldpd.put("content", zengcontent2);
				ldpd.put("number", "");
				ldpd.put("id", zengid2);
				ldpd.put("type", zengtype2);
				yingxiaoList.add(ldpd);
			}
			ldpd=null;
			//满减
			if(!jiancontent.equals("") && reducemoney>0 ){
				e2.put("content", jiancontent);
				e2.put("id", jianid);
				e2.put("type", jiantype);
				e2.put("number", "-"+df2.format(reducemoney));
				yingxiaoList.add(e2);
			}
			e2=null;
			 int yingxiaosize=yingxiaoList.size();
 			 double useredbeforMoney=reducemoney+zkmoney;//使用红包前的总共优惠了的金额
			 //判断优惠后的金额是否已经是0
			 String redpackage_id="";
			 String redmoney="0";
			 if(useredbeforMoney < youhui_money+notyouhui_money){
				   //使用红包
					PageData redpd=getMaxStoreRedMoneyByMember(pd,youhui_money,yingxiaosize,useredbeforMoney);
		 			if(redpd != null){
		 				redpackage_id=redpd.getString("id");
		 				redmoney=redpd.getString("number");
						redpd.put("number", "-"+redmoney);
						redpd.put("type", "0");
						yingxiaoList.add(redpd);
					} 
					redpd=null;
			 }
 			//使用红包后的优惠后的实际应该支付的金额为
			double surepaymoney=youhui_money-reducemoney-Double.parseDouble(redmoney)-zkmoney+notyouhui_money;
			//总共优惠金额
			double surehuiyoumoney=reducemoney+Double.parseDouble(redmoney)+zkmoney;
 			if(!leibieFlag){
					double redhuiyoumoney=surepaymoney-notyouhui_money;
					if(redhuiyoumoney < 0){
						redhuiyoumoney=0;
					}
					e=new PageData();
					e.put("store_scoreway_id", addjfid);
					e.put("store_id", pd.getString("store_id"));
					//获取所有启用的积分
					PageData jfpd=ServiceHelper.getAppStorepc_marketingService().getJfById(e);
					if(jfpd != null){ 
  		 	 				if(jfpd.getString("change_type").equals("1")){//整店
 			 	 					if(Double.parseDouble(jfpd.getString("oneback_rate"))/100*redhuiyoumoney >= addjf){
			 	 						addjf=Double.parseDouble(jfpd.getString("oneback_rate"))/100*redhuiyoumoney;
			 	 						desc=jfpd.getString("grantrule");
			 	 					}
		 	 				}else if(jfpd.getString("change_type").equals("5")){//满多少送多少积分
		 	 							String[] str=jfpd.getString("grantrule").split(",");
		 	 							int strlength=str.length;
		 	 							for(int i=0; i<strlength ;i++){
 			  		  							if(Double.parseDouble(str[i].substring(str[i].indexOf("满")+1, str[i].indexOf("元"))) <= redhuiyoumoney){
  			  		 								if(Double.parseDouble(str[i].substring(str[i].indexOf("满")+1, str[i].indexOf("元")))*Double.parseDouble(str[i].substring(str[i].indexOf("送")+1, str[i].lastIndexOf("%")))/100.00 >= addjf ){
			  		 									addjf=Double.parseDouble(str[i].substring(str[i].indexOf("满")+1, str[i].indexOf("元")))*Double.parseDouble(str[i].substring(str[i].indexOf("送")+1, str[i].lastIndexOf("%")))/100.00;
		 					 	 						desc=jfpd.getString("grantrule");
				  		 							}
			  		  							}
		 	 							}
		 	 				}else if(jfpd.getString("change_type").equals("4")){//每单返积分
 				 	 					if(Double.parseDouble(jfpd.getString("fourbackintegral_integral")) >= addjf){
				 	 						addjf=Double.parseDouble(jfpd.getString("fourbackintegral_integral"));
				 	 						desc=jfpd.getString("grantrule");
				 	 					}
		 	 				}else{ 
		 	 					//无
		 	 				}
				}
				//积分
				if(!desc.equals("")){
					e3.put("content", desc);e3.put("number", "+"+df2.format(addjf));e3.put("id", addjfid);e3.put("type", "6");
					yingxiaoList.add(e3);
			 	}
 			}
 			//优惠集合处理
 			if(youhui_money+notyouhui_money <= 0){
 	  			map.put("yingxiaoList", new ArrayList<PageData>());
 	  		}else{
 	  			map.put("yingxiaoList", yingxiaoList);
 	  		}
  			PageData countpd=new PageData();
  			countpd.put("zengid", zengid+","+zengid2);//增红包的集合
//  		countpd.put("store_redpackets_id", zengid+","+zengid2);//增红包的集合
 			countpd.put("zengjf", df2.format(addjf+alljifeng));//赠送的积分
 			countpd.put("red_id", redpackage_id);//使用红包的ID
//   		countpd.put("redpackage_id", redpackage_id);//使用红包的ID
  			countpd.put("allmoney", df2.format(youhui_money+notyouhui_money));//总金额
 			countpd.put("notmoney", df2.format(youhui_money+notyouhui_money));//不优惠金额
 			if(surepaymoney <= 0){
				countpd.put("paymoney", "0");//优惠后的支付金额
				countpd.put("reducemoney", df2.format(youhui_money+notyouhui_money));
			}else{
				countpd.put("paymoney", df2.format(surepaymoney));
				countpd.put("reducemoney", df2.format(surehuiyoumoney));//优惠金额=优惠的金额+红包优惠的金额+折扣的金额
			}
 			map.put("countpd", countpd);
 			countpd=null;
 			//获取个人财富
 			map.put("mpd", ServiceHelper.getAppMemberService().findWealthById(pd));
			map.put("memberInfor", ServiceHelper.getAppMemberService().findWealthById(pd));
  			//判断是否开通类别积分购买的权限
 			if(ServiceHelper.getAppStorepc_marketingService().getJfById(pd) != null){
 				if( ServiceHelper.getAppStorepc_marketingService().getJfById(pd).getString("change_type").equals("3") ){
 					map.put("issortjf", "3");
 				}else if( ServiceHelper.getAppStorepc_marketingService().getJfById(pd).getString("change_type").equals("2") ){
 					map.put("issortjf", "1");
 				} else{
 					map.put("issortjf", "0");
 				}
 			}
 			//商家名称
 			map.put("store_name", ServiceHelper.getAppStoreService().findById(pd).getString("store_name"));
     		//获取商家的营销规则明细
 			map.put("yxpd", markeingAll(pd));
 		} catch (Exception e) {
  			(new TongYong()).dayinerro(e);
		}
  		return map;
  	}
  	
  	
  	
  	
  	
  	
 

}
