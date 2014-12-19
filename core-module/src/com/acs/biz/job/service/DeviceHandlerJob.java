/**
 *
 */
package com.acs.biz.job.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.biz.device.service.DeviceHandler;

/**
 * @author Eric
 */
public class DeviceHandlerJob {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private DeviceHandler deviceHandler;

	public void retrieveDeviceStatus() {
		try {
			// logger.debug("retrieveDeviceStatus() started.");
			// retrieve status
			deviceHandler.sendCommandStatusToAllDevice();
			// set target temperature
			deviceHandler.sendCommandSetTemperatureToAllDevice();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void reconnectAllDevices() {
		try {
			// logger.debug("reconnectAllDevices() started.");
			// disconnect all device
			deviceHandler.disconnectAllDevice();
			// connect all device
			deviceHandler.connectAllActiveDevice();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
