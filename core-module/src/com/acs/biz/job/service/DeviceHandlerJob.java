/**
 *
 */
package com.acs.biz.job.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.biz.device.service.DeviceHandlerHelper;

/**
 * @author Eric
 */
public class DeviceHandlerJob {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private DeviceHandlerHelper deviceHandlerHelper;

	public void retrieveDeviceStatus() {
		try {
			// logger.debug("retrieveDeviceStatus() started.");
			// retrieve status
			deviceHandlerHelper.sendCommandStatusToAllDevice();
			// set target temperature
			deviceHandlerHelper.sendCommandSetTemperatureToAllDevice();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void reconnectAllDevices() {
		try {
			// logger.debug("reconnectAllDevices() started.");
			// disconnect all device
			deviceHandlerHelper.disconnectAllDevice();
			// connect all device
			deviceHandlerHelper.connectAllActiveDevice();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
