/**
 *
 */
package com.acs.biz.device.test;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.acs.biz.device.entity.Device;
import com.acs.biz.device.service.DeviceService;
import com.acs.core.common.utils.SpringCommonTest;

/**
 * @author Eric
 */
public class DeviceServiceTest extends SpringCommonTest {

	private static DeviceService deviceService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		configCtx();
		deviceService = (DeviceService) ctx.getBean("deviceService");
	}

	@Test
	public void listActiveDevices() {
		List<Device> result = deviceService.listActiveDevices(0, -1);
		logger.info("listActiveDevices, result size: {}", result.size());
		logger.info(result.toString());
	}

}
