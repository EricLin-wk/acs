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

import com.acs.biz.device.service.DeviceHandlerHelper;
import com.acs.core.common.service.AppConfigService;

/**
 * @author Eric
 */
public class MyAppServletContextListener implements ServletContextListener {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DeviceHandlerHelper deviceHandlerHelper;
	@Autowired
	private AppConfigService appConfigService;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.debug("invoke deviceHandler.disconnectAllDevice()");
		deviceHandlerHelper.disconnectAllDevice();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext())
		.getAutowireCapableBeanFactory().autowireBean(this);
		final int port = Integer
				.parseInt(appConfigService.get(AppConfigService.APPCONFIG_ECHO_SERVER_BIND_PORT).getValue());
		final String address = appConfigService.get(AppConfigService.APPCONFIG_ECHO_SERVER_BIND_IP).getValue();

		Runnable run = new Runnable() {
			@Override
			public void run() {
				try {
					EchoServerTest test = new EchoServerTest();
					test.startServer(address, port);
				} catch (Exception e) {
					logger.info(e.getMessage());
				}
			}
		};
		new Thread(run).start();

		logger.debug("invoke deviceHandler.connectAllActiveDevice()");
		deviceHandlerHelper.connectAllActiveDevice();
		deviceHandlerHelper.sendCommandStatusToAllDevice();
		deviceHandlerHelper.sendCommandSetTemperatureToAllDevice();
	}
}
