/**
 *
 */
package com.acs.biz.device.test;

import org.junit.BeforeClass;
import org.junit.Test;

import com.acs.biz.device.service.DeviceHandler;
import com.acs.core.common.utils.SpringCommonTest;

/**
 * @author Eric
 */
public class DeviceHandlerTest extends SpringCommonTest {

	private static DeviceHandler deviceHandler;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		configCtx();
		deviceHandler = (DeviceHandler) ctx.getBean("deviceHandler");
	}

	@Test
	public void connectActiveDevices() {
		deviceHandler.connectAllActiveDevice();
	}

	@Test
	public void testEchoServer() throws InterruptedException {
		deviceHandler.connectAllActiveDevice();
		logger.debug("connectAllActiveDevice() finish");
		// Thread.sleep(5000);

		// get status
		deviceHandler.sendCommandStatusToAllDevice();
		logger.debug("sendCommandStatusToAllDevice() finish");
		// Thread.sleep(5000);

		// set temp
		deviceHandler.sendCommandSetTemperatureToAllDevice();
		logger.debug("sendCommandSetTemperatureToAllDevice() finish");
		// Thread.sleep(5000);

		// get status
		deviceHandler.sendCommandStatusToAllDevice();
		logger.debug("sendCommandStatusToAllDevice() finish");
		// Thread.sleep(5000);

		// set temp
		deviceHandler.sendCommandSetTemperatureToAllDevice();
		logger.debug("sendCommandSetTemperatureToAllDevice() finish");
		// Thread.sleep(5000);

		deviceHandler.disconnectAllDevice();
	}

	@Test
	public void testReconnectEchoServer() throws InterruptedException {
		deviceHandler.connectAllActiveDevice();
		logger.debug("connectAllActiveDevice() finish");
		// Thread.sleep(5000);

		deviceHandler.disconnectAllDevice();
		logger.debug("disconnectAllDevice() finish");

		deviceHandler.connectAllActiveDevice();
		logger.debug("connectAllActiveDevice() finish");

		// get status
		deviceHandler.sendCommandStatusToAllDevice();
		logger.debug("sendCommandStatusToAllDevice() finish");
		// Thread.sleep(5000);

		// set temp
		deviceHandler.sendCommandSetTemperatureToAllDevice();
		logger.debug("sendCommandSetTemperatureToAllDevice() finish");
		// Thread.sleep(5000);

		deviceHandler.disconnectAllDevice();

	}

}
