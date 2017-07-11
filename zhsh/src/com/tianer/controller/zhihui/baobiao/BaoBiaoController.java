package com.tianer.controller.zhihui.baobiao;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.service.baobiao.BaoBiaoService;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;


/** 
 * 
* 类名称：BaoBiaoController 
* 报表统计  
 */
@Controller
@RequestMapping(value="/zhihuiBaoBiao")
public class BaoBiaoController extends BaseController{
	
	@Resource(name="baoBiaoService")
	private BaoBiaoService baoBiaoService;
	
	  
	
	/**
	 * 	GROUP BY remittance_type,money_type
	 */
	@RequestMapping(value="/baoBiaoTypeTotol")
	public ModelAndView BaoBiaoTypeTotol(){
 		ModelAndView mv = this.getModelAndView();
 		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		PageData pd = new PageData();
 		try { 
			List<PageData> provincelist=ServiceHelper.getPcdService().listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			pd=this.getPageData();
			this.getHC57();
			if(pd.getString("monthtime") == null || pd.getString("monthtime").equals("")){
				mv.addObject("pd", pd);
				mv.setViewName("zhihui/baobiao/baobiao9");
				return mv;
			}
  			//支付宝
			double sumupalipay=0;//总收入金额
			int countupalipay=0;//收入单数
			double sumdownalipay=0;//提现总金额--支出
 			int countdownalipay=0;//支出订单数
 			//微信
			double sumupwx=0;//收入总金额
			int countupwx=0;//收入总单数
			double sumdownwx=0;//暂时没有
			int countdownwx=0;//暂时没有
			//银联
			double sumupbank=0;//暂时没有
			int countupbank=0;//暂时没有
			double sumdownbank=0;//提现转账--支出
			int countdownbank=0;//总--单数
			//九鱼平台
			double sumjiuyu=0;
			int countjiuyu=0;
			//开始统计
			List<PageData>  bbList=baoBiaoService.baoBiaoTypeTotol(pd);
			int bbn=bbList.size();
			PageData e=null;
			for (int i = 0; i < bbn; i++) {
				e=bbList.get(i);
//1-银联支付，2- 现金支付（为0的情况下），3-支付宝支付，4-微信支付，5-苹果支付,6-平台支付（九鱼平台支付），
				String remittance_type=e.getString("remittance_type");
//1-充值（商家或会员充积分）2-购买商品，3-商家购买保证金4-服务商支付保证金 5-提现 ，
//6-退款7-商家编辑优选商品费用，8-商家上架优选商品 （商家和服务商）9-导流模块收益（商家和平台不包括服务商） 
//10-商家/会员发积分红包，11-抢积分红包，12-退还积分红包13-广告费用收取（商家和服务商）
				String money_type=e.getString("money_type");
				if(remittance_type.equals("3")){
					if(money_type.equals("5")){
						sumdownalipay+=Double.parseDouble(e.getString("sumalipay_money"));
						countdownalipay+=Integer.parseInt(e.getString("typecount"));
					}else{
						sumupalipay+=Double.parseDouble(e.getString("sumalipay_money"));
						countupalipay+=Integer.parseInt(e.getString("typecount"));
					}
				}else if(remittance_type.equals("4")){
					if(money_type.equals("5")){
						sumdownwx+=Double.parseDouble(e.getString("sumwx_money"));
						countdownwx+=Integer.parseInt(e.getString("typecount"));
					}else{
						sumupwx+=Double.parseDouble(e.getString("sumwx_money"));
						countupwx+=Integer.parseInt(e.getString("typecount"));
					}
				}else if(remittance_type.equals("1")){
					if(money_type.equals("5")){
						sumdownbank+=Double.parseDouble(e.getString("sumbank_money"));
						countdownbank+=Integer.parseInt(e.getString("typecount"));
					}else{
						sumupbank+=Double.parseDouble(e.getString("sumbank_money"));
						countupbank+=Integer.parseInt(e.getString("typecount"));
					}
				}else{
					sumjiuyu+=Double.parseDouble(e.getString("sumjiuyu_money"));
					countjiuyu+=Integer.parseInt(e.getString("typecount"));
				}
 			}
			
			
			mv.addObject("bbList", bbList);
			
			//开始计算各类型的总的金额 
			double allalipay=sumupalipay+sumdownalipay;
			double allbank=sumupbank+sumdownbank;
			double allwx=sumdownwx+sumupwx;
			
			//系统平台的总收益
			double allmoney=allalipay+allbank+allwx; 
			
			mv.addObject("sumupalipay", df.format(sumupalipay));
			mv.addObject("countupalipay",  countupalipay);
			mv.addObject("sumdownalipay", df.format(sumdownalipay));
			mv.addObject("countdownalipay",  countdownalipay);
 			mv.addObject("sumupbank", df.format(sumupbank));
 			mv.addObject("countupbank",  countupbank);
			mv.addObject("sumdownbank", df.format(sumdownbank));
			mv.addObject("countdownbank",  countdownbank);
			mv.addObject("sumdownwx", df.format(sumdownwx));
			mv.addObject("countdownwx",  countdownwx);
			mv.addObject("countupwx",  countupwx);
			mv.addObject("sumupwx", df.format(sumupwx));
			mv.addObject("countjiuyu",  countjiuyu);
			mv.addObject("sumjiuyu", df.format(sumjiuyu));
			mv.addObject("allalipay", df.format(allalipay));
			mv.addObject("allwx", df.format(allwx));
			mv.addObject("allbank", df.format(allbank));
			mv.addObject("allmoney", df.format(allmoney));
			
			
			//计算平台的收入计算
			/*
			 * 规则：
			 * 微信入账：0.006
			 * 支付宝入账：0.0055
			 * 支付宝转账：每个月20000以内，超过的部分0.006
			 * 银联转账：每笔两元
			 */
			//计算总收入金额
			double jiuyuwxup=sumupwx;
 			double jiuyualipayup=sumupalipay;
 			double jiuyuallup=(new BigDecimal(jiuyuwxup+jiuyualipayup)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			//计算支付宝和微信收入扣除的服务费
			double jiuyuwxupkou=(new BigDecimal(sumupwx)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()*0.006;
			double jiuyualipayupkou=(new BigDecimal(sumupalipay)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()*0.0055 ;
			//计算支付宝支出提现金额
			double jiuyualipaydown=(-sumdownalipay);
			double jiuyubankdown=(-sumdownbank);
			mv.addObject("jiuyualipaydown", df.format(jiuyualipaydown));
 			mv.addObject("jiuyubankdown", df.format(jiuyubankdown));
 			//计算支付宝支出总扣服务费
  			double jiuyualipaydownkou=0;
			double jiuyubankdownkou=countdownbank*2;
			double alipaycha=(new BigDecimal(jiuyualipaydown+20000)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
 			if(alipaycha < 0 ){
 				jiuyualipaydownkou=(new BigDecimal((-alipaycha)*0.006)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			}
  			double downfuwukou=jiuyualipaydownkou+jiuyubankdownkou;
 			mv.addObject("downfuwukou", df.format(downfuwukou));
 			//计算支付宝总支出金额（收入扣除服务费+转账金额+转账扣除服务费）
 			double jiuyualipayalldown=jiuyualipayupkou+jiuyualipaydown+jiuyubankdown+downfuwukou;
 			mv.addObject("jiuyualipayalldown", df.format(jiuyualipayalldown));
 			//支付宝总计算
 			double jiuyualipay=jiuyualipayup-jiuyualipayalldown;
 			mv.addObject("jiuyualipay", df.format(jiuyualipay));
 			//微信总计算
 			double jiuyuwx=jiuyuwxup-jiuyuwxupkou;
 			mv.addObject("jiuyuwx", df.format(jiuyuwx));
 			//计算总的平台收益
 			double jiuyudown=downfuwukou+jiuyualipayupkou+jiuyuwxupkou+jiuyualipaydown+jiuyubankdown;
 			double alljiuyu=jiuyuallup-jiuyudown;
 			//添加扣除的服务费和系统对比
 			double notkou=alljiuyu+downfuwukou+jiuyuwxupkou+jiuyualipayupkou;
 			mv.addObject("notkou", df.format(notkou));
 			
 			mv.addObject("jiuyuwxup", df.format(jiuyuwxup));
 			mv.addObject("jiuyuwxupkou", df.format(jiuyuwxupkou));
 			mv.addObject("jiuyualipayup", df.format(jiuyualipayup));
 			mv.addObject("jiuyualipayupkou", df.format(jiuyualipayupkou));
 			mv.addObject("jiuyuallup", df.format(jiuyuallup));
 			mv.addObject("jiuyualipaydown", df.format(jiuyualipaydown));
 			mv.addObject("jiuyubankdown", df.format(jiuyubankdown));
 			mv.addObject("alljiuyu", df.format(alljiuyu));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		mv.setViewName("zhihui/baobiao/baobiao9");
		return mv;
	}
	
	
	/**
	 * 城市列表
 	 * 
	 */
	@RequestMapping(value="/citylist")
	@ResponseBody
	public Object citylist(   ){
 		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			List<PageData> citylist=ServiceHelper.getPcdService().listcity(pd);//市
			map.put("citylist", citylist);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return map;
	}
	/**
	 * 区域列表
 	 * 
	 */
	@RequestMapping(value="/arealist")
	@ResponseBody
	public Object arealist(){
 		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			List<PageData> arealist=ServiceHelper.getPcdService().listarea(pd); 
			map.put("arealist", arealist);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return map;
	}
	
	 

	/* ===============================异常待支付权限================================== */
	public void getHC57(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("57"));
		}
 	}
	/* ===============================权限================================== */
	
	
	
	
	

}
