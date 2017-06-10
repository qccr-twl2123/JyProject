package com.tianer.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tianer.util.Const;
import com.tianer.util.XmlUtil;
import com.tianer.util.huanxin.api.ChatGroupAPI;
import com.tianer.util.huanxin.api.ChatMessageAPI;
import com.tianer.util.huanxin.api.ChatRoomAPI;
import com.tianer.util.huanxin.api.FileAPI;
import com.tianer.util.huanxin.api.IMUserAPI;
import com.tianer.util.huanxin.api.SendMessageAPI;
import com.tianer.util.huanxin.comm.ClientContext;
import com.tianer.util.huanxin.comm.EasemobRestAPIFactory;
/**
 * 
* 类名称：WebAppContextListener.java
* 类描述： 
* 作者单位： 
* 联系方式：
* @version 1.0
 */
public class WebAppContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
  		Const.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
//		System.out.println("========获取Spring WebApplicationContext");
//		Const.APPVALIDATION = XmlUtil.initAppValidation();
    }

}
