/**
 *
 */
package com.acs.biz.device.test;

import org.junit.BeforeClass;
import org.junit.Test;

import com.acs.biz.device.service.DeviceHandlerHelper;
import com.acs.core.common.utils.SpringCommonTest;

/**
 * @author Eric
 */
public class DeviceHandlerTest extends SpringCommonTest {

	private static DeviceHandlerHelper deviceHandlerHelper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		configCtx();
		deviceHandlerHelper = (DeviceHandlerHelper) ctx.getBean("deviceHandlerHelper");
	}

	@Test
	public void connectActiveDevices() {
		deviceHandlerHelper.connectAllActiveDevice();
	}

	@Test
	public void testEchoServer() throws InterruptedException {
		deviceHandlerHelper.connectAllActiveDevice();
		logger.debug("connectAllActiveDevice() finish");
		// Thread.sleep(5000);

		// get status
		deviceHandlerHelper.sendCommandStatusToAllDevice();
		logger.debug("sendCommandStatusToAllDevice() finish");
		// Thread.sleep(5000);

		// set temp
		deviceHandlerHelper.sendCommandSetTemperatureToAllDevice();
		logger.debug("sendCommandSetTemperatureToAllDevice() finish");
		// Thread.sleep(5000);

		// get status
		deviceHandlerHelper.sendCommandStatusToAllDevice();
		logger.debug("sendCommandStatusToAllDevice() finish");
		// Thread.sleep(5000);

		// set temp
		deviceHandlerHelper.sendCommandSetTemperatureToAllDevice();
		logger.debug("sendCommandSetTemperatureToAllDevice() finish");
		// Thread.sleep(5000);

		deviceHandlerHelper.disconnectAllDevice();
	}

	@Test
	public void testReconnectEchoServer() throws InterruptedException {
		deviceHandlerHelper.connectAllActiveDevice();
		logger.debug("connectAllActiveDevice() finish");
		// Thread.sleep(5000);

		// deviceHandlerHelper.disconnectAllDevice();
		// logger.debug("disconnectAllDevice() finish");
		//
		// deviceHandlerHelper.connectAllActiveDevice();
		// logger.debug("connectAllActiveDevice() finish");
		//
		// // get status
		// deviceHandlerHelper.sendCommandStatusToAllDevice();
		// logger.debug("sendCommandStatusToAllDevice() finish");
		// // Thread.sleep(5000);
		//
		// // set temp
		// deviceHandlerHelper.sendCommandSetTemperatureToAllDevice();
		// logger.debug("sendCommandSetTemperatureToAllDevice() finish");
		// // Thread.sleep(5000);
		//
		deviceHandlerHelper.disconnectAllDevice();

	}

}
