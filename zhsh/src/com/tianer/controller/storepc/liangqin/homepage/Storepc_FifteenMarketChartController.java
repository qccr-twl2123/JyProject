/**
 * 
 */
package com.tianer.controller.storepc.liangqin.homepage;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.service.storepc.liangqin.homepage.FifteenMarketChartService;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;

/**
 * 类名称: Storepc_FifteenMarketChart 
 * 类描述: TODO
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-14 上午8:53:56	
 * 版本号: v1.0
 */
@Controller
@RequestMapping(value="/storepc_fifteenmarketchart")
public class Storepc_FifteenMarketChartController extends BaseController{
	@Resource(name="fifteenMarketChartService")
	private FifteenMarketChartService fifteenMarketChartService;
	
	
	
	/**
	 * 
	* 方法名称:：showChart 
	* 方法描述：
	* 创建人：魏汉文
	* 创建时间：2016年7月12日 下午4:44:26
	 */
	@RequestMapping("/showChart")
	public ModelAndView showChart() throws Exception{
		logBefore(logger, "新增Order");
		ModelAndView modelAndView = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//获取规定日期的销售额和订单数
			for(int i=1 ;i<16 ;i++){
				PageData e=new PageData();
				pd.put("n", i);
		  		e= fifteenMarketChartService.findOrderQuantity(pd);
		  		modelAndView.addObject("num"+i, e.get("number"));
				modelAndView.addObject("mon"+i, e.get("money"));
				e=null;
			}
 		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
    	modelAndView.setViewName("/storepc/business_homepage4");
		return modelAndView;
	}

}
