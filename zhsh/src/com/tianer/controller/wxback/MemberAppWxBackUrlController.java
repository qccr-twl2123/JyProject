package com.tianer.controller.wxback;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.wxpay.WXPayConfig;
import com.tianer.util.wxpay.WXPayConfigImpl;
import com.tianer.util.wxpay.WXPayUtil;
 



/** 
 * 会员的App的微信的全部回调地址
 * @author Administrator
 *
 */
@Controller("memberAppWxBackUrlController")
@RequestMapping(value="/wxback_mapp")
public class MemberAppWxBackUrlController extends BaseController { 
	
	  
	 
	
}
