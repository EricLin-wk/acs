/**
 *
 */
package com.acs.biz.device.service;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.biz.device.entity.Device;
import com.acs.biz.device.entity.DeviceStatus;
import com.acs.biz.log.service.LogDeviceService;

/**
 * @author Eric
 */
public class DeviceHandlerHelper {

	/** logger **/
	private final Logger logger = LoggerFactory.getLogger(DeviceHandlerHelper.class);

	private static final int THREAD_EXECUTION_TIMEOUT_SEC = 60;

	@Resource
	private DeviceService deviceService;
	@Resource
	private LogDeviceService logDeviceService;
	@Resource
	private DeviceSettingService deviceSettingService;
	@Resource
	private DeviceHandler deviceHandler;

	private Hashtable<Long, DeviceStatus> statusMap;

	/**
	 * No public constructor for singleton instance
	 */
	protected DeviceHandlerHelper() {
		statusMap = new Hashtable<Long, DeviceStatus>();
	}

	/**
	 * Create socket connections to all active devices
	 */
	public synchronized void connectAllActiveDevice() {
		List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>();
		Hashtable<Long, DeviceStatus> newStatusMap = new Hashtable<Long, DeviceStatus>();
		// iterate over active devices
		List<Device> devList = deviceService.listActiveDevices(0, -1);
		logger.debug(devList.toString());
		for (Device device : devList) {
			// get corresponding device status
			DeviceStatus status = statusMap.get(device.getOid());
			if (status == null) {
				status = new DeviceStatus();
			} else {
				statusMap.remove(device.getOid()); // remove from old map
			}
			status.setDevice(device);
			newStatusMap.put(device.getOid(), status);
			// create connection if not already connected
			Socket socket = status.getSocket();
			if (socket == null || socket.isClosed()) {
				// call method asynchronously
				Future<Boolean> future = deviceHandler.connectDevice(status);
				futureList.add(future);
			}
		}

		// disconnect whatever's remaining in the old statusMap
		for (DeviceStatus status : statusMap.values()) {
			deviceHandler.disconnectDevice(status);
		}
		// set statMap to new map
		statusMap = newStatusMap;

		// wait for futures to finish or terminate
		for (Future<Boolean> future : futureList) {
			try {
				Boolean result = future.get(THREAD_EXECUTION_TIMEOUT_SEC, TimeUnit.SECONDS);
				// logger.debug("thread result: " + result);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				logger.error(e.getMessage(), e);
				// time out occurred, cancel thread
				future.cancel(true);
			}
		}
	}

	/**
	 * Close connection for all connected devices.
	 */
	public synchronized void disconnectAllDevice() {
		for (DeviceStatus status : statusMap.values()) {
			deviceHandler.disconnectDevice(status);
		}
	}

	/**
	 * Reconnect to all active devices
	 */
	public synchronized void reconnectAllDevices() {
		try {
			// disconnect all device
			disconnectAllDevice();
			// connect all device
			connectAllActiveDevice();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Reconnect the given device.
	 *
	 * @param deviceId Device ID.
	 */
	public synchronized void reconnectDevice(Long deviceId) {
		Device device = deviceService.get(deviceId);
		if (device == null)
			return;
		DeviceStatus status = statusMap.get(deviceId);
		if (status != null) {
			deviceHandler.disconnectDevice(status);
			status.setDevice(device);
			deviceHandler.connectDevice(status);
		} else {
			if (device.isDelete())
				return;
			status = new DeviceStatus();
			status.setDevice(device);
			deviceHandler.connectDevice(status);
			statusMap.put(device.getOid(), status);
		}
	}

	public synchronized void disconnectDevice(Long deviceId) {
		DeviceStatus status = statusMap.get(deviceId);
		if (status != null) {
			deviceHandler.disconnectDevice(status);
		}
	}

	public synchronized void sendCommandStatusToAllDevice() {
		List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>();
		// loop through all connected devices
		for (DeviceStatus status : statusMap.values()) {
			if (status.getSocket() == null || status.getSocket().isClosed())
				continue; // skip disconnected device
			if (status.getDevice() == null || status.getDevice().isDelete())
				continue; // skip deleted device

			final DeviceStatus statusRef = status;
			// run method asynchronously
			Future<Boolean> future = deviceHandler.sendCommandStatus(statusRef);
			futureList.add(future);
		}

		// wait for futures to finish or terminate
		for (Future<Boolean> future : futureList) {
			try {
				Boolean result = future.get(THREAD_EXECUTION_TIMEOUT_SEC, TimeUnit.SECONDS);
				// logger.debug("thread result: " + result);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				logger.error(e.getMessage(), e);
				// time out occurred, cancel thread
				future.cancel(true);
			}
		}
	}

	public synchronized void sendCommandSetTemperatureToAllDevice() {
		List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>();
		// loop through all connected devices
		for (DeviceStatus status : statusMap.values()) {
			if (status.getSocket() == null || status.getSocket().isClosed())
				continue; // skip disconnected device
			if (status.getDevice() == null || status.getDevice().isDelete())
				continue; // skip deleted device

			// create new thread for task
			final DeviceStatus statusRef = status;
			// call methoud asynchronously
			Future<Boolean> future = deviceHandler.sendCommandSetTemperature(statusRef);
			futureList.add(future);
		}

		// wait for futures to finish or terminate
		for (Future<Boolean> future : futureList) {
			try {
				Boolean result = future.get(THREAD_EXECUTION_TIMEOUT_SEC, TimeUnit.SECONDS);
				// logger.debug("thread result: " + result);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				logger.error(e.getMessage(), e);
				// time out occurred, cancel thread
				future.cancel(true);
			}
		}
	}

	public void rereshStatus() {
		for (DeviceStatus status : statusMap.values()) {
			Socket socket = status.getSocket();
			if (socket == null || socket.isClosed())
				status.setOperationStatus(DeviceStatus.STATUS_DISCONNECTED);
			else if (socket.isConnected())
				status.setOperationStatus(DeviceStatus.STATUS_CONNECTED);
		}
	}

	public Hashtable<Long, DeviceStatus> getStatusMap() {
		return statusMap;
	}
}
