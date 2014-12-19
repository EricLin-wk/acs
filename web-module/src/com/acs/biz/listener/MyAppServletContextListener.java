/**
 *
 */
package com.acs.biz.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.acs.biz.device.service.DeviceHandler;

/**
 * @author Eric
 */
public class MyAppServletContextListener implements ServletContextListener {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DeviceHandler deviceHandler;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.debug("invoke deviceHandler.disconnectAllDevice()");
		deviceHandler.disconnectAllDevice();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext())
		.getAutowireCapableBeanFactory().autowireBean(this);

		logger.debug("invoke deviceHandler.connectAllActiveDevice()");
		deviceHandler.connectAllActiveDevice();
	}

}
