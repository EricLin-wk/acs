/**
 *
 */
package com.acs.biz.device.test;

import java.util.List;

import org.junit.Assert;
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
		for (Device device : result) {
			Assert.assertFalse(device.isDelete());
		}
		logger.info(result.toString());
	}

	@Test
	public void listByIsDelete_GroupId_DeviceType_SerialNum() {
		boolean isDelete = false;
		Long groupId = 1L;
		String deviceType = DeviceService.DEVICE_TYPE_AIR_CONDITION;
		String serialNum = "9999-0";
		List<Device> result = deviceService.listByIsDelete_GroupId_DeviceType_SerialNum(0, -1, isDelete, groupId,
				deviceType, serialNum);
		logger.info("listByIsDelete_GroupId_DeviceType_SerialNum, result size: {}", result.size());
		for (Device device : result) {
			Assert.assertEquals(isDelete, device.isDelete());
			if (groupId != null)
				Assert.assertEquals(groupId, device.getGroupId());
			if (deviceType != null)
				Assert.assertEquals(deviceType, device.getDeviceType());
			if (serialNum != null)
				Assert.assertEquals(serialNum, device.getSerialNum());
		}
	}

	@Test
	public void listSizeByIsDelete_GroupId_DeviceType_SerialNum() {
		boolean isDelete = false;
		Long groupId = 1L;
		String deviceType = DeviceService.DEVICE_TYPE_AIR_CONDITION;
		String serialNum = "9999-0";
		List<Device> result = deviceService.listByIsDelete_GroupId_DeviceType_SerialNum(0, -1, isDelete, groupId,
				deviceType, serialNum);
		int resultSize = deviceService.listSizeByIsDelete_GroupId_DeviceType_SerialNum(isDelete, groupId, deviceType,
				serialNum);
		logger.info("listSizeByIsDelete_GroupId_DeviceType_SerialNum, result : {}", resultSize);
		Assert.assertEquals(result.size(), resultSize);

	}

	@Test
	public void listByIsDelete_GroupId() {
		boolean isDelete = false;
		Long groupId = null;
		List<Device> result = deviceService.listByIsDelete_GroupId(0, -1, isDelete, groupId);
		logger.info("listByIsDelete_GroupId, result size: {}", result.size());
		logger.info(result.toString());
		for (Device device : result) {
			Assert.assertEquals(isDelete, device.isDelete());
			Assert.assertEquals(groupId, device.getGroupId());
		}
	}

}
