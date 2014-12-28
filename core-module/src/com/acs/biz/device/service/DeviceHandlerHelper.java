/**
 *
 */
package com.acs.biz.device.service;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

	private static final int THREAD_POOL_SIZE = 10;

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

	private ExecutorService executor;

	/**
	 * No public constructor for singleton instance
	 */
	protected DeviceHandlerHelper() {
		statusMap = new Hashtable<Long, DeviceStatus>();
		executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}

	/**
	 * Create socket connections to all active devices
	 */
	public synchronized void connectAllActiveDevice() {
		List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>();
		// iterate over active devices
		List<Device> devList = deviceService.listActiveDevices(0, -1);
		logger.debug(devList.toString());
		for (Device device : devList) {
			// get corresponding device status
			final DeviceStatus status;
			DeviceStatus tmp = statusMap.get(device.getOid());
			if (tmp == null) {
				status = new DeviceStatus();
				statusMap.put(device.getOid(), status);
			} else
				status = tmp;
			status.setDevice(device);
			// create connection if not already connected
			Socket socket = status.getSocket();
			if (socket == null || socket.isClosed()) {
				// call method asynchronously
				Future<Boolean> future = deviceHandler.connectDevice(status);
				futureList.add(future);
			}
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
