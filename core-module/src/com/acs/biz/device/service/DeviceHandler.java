/**
 *
 */
package com.acs.biz.device.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import com.acs.biz.device.entity.Device;
import com.acs.biz.device.entity.DeviceSetting;
import com.acs.biz.device.entity.DeviceStatus;
import com.acs.biz.log.service.LogDeviceService;

/**
 * @author Eric
 */
public class DeviceHandler {

	/** logger **/
	private final Logger logger = LoggerFactory.getLogger(DeviceHandler.class);

	private static final int CONNECTION_TIMEOUT_SEC = 30;

	private static final int COMMAND_TIMEOUT_SEC = 30;

	private static final int THREAD_POOL_SIZE = 10;

	private static final int THREAD_EXECUTION_TIMEOUT_SEC = 60;

	@Resource
	private DeviceService deviceService;
	@Resource
	private LogDeviceService logDeviceService;
	@Resource
	private DeviceSettingService deviceSettingService;

	private Hashtable<Long, DeviceStatus> statusMap;

	private ExecutorService executor;

	/**
	 * No public constructor for singleton instance
	 */
	protected DeviceHandler() {
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
				Future<Boolean> future = connectDevice(status);
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
	 * Create new socket connection to the device. Existing connection will be closed.
	 *
	 * @param status
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	@Async
	private Future<Boolean> connectDevice(DeviceStatus status) {
		Device device = status.getDevice();
		try {
			// close current connection if it's open
			disconnectDevice(status);
			// create new socket connection
			InetSocketAddress endpoint = new InetSocketAddress(status.getDevice().getIpAddress(), status.getDevice()
					.getPort());
			Socket socket = new Socket();
			socket.setKeepAlive(true);
			socket.setSoTimeout(COMMAND_TIMEOUT_SEC * 1000);
			socket.connect(endpoint, CONNECTION_TIMEOUT_SEC * 1000);
			// get input & output stream
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// store connection back to entity
			status.setSocket(socket);
			status.setSocketOut(socketOut);
			status.setSocketIn(socketIn);
			status.setOperationStatus(DeviceStatus.STATUS_CONNECTED);

			logger.debug(status.getDevice().toStringShort() + " connection created.");
			return new AsyncResult<Boolean>(true);
		} catch (Exception e) {
			logger.error(
					"Error connection to Device " + device.getDeviceName() + " S/N:" + device.getSerialNum() + "\n"
							+ e.getMessage(), e);
			status.setOperationStatus(DeviceStatus.STATUS_CONNECTION_ERROR);
			return new AsyncResult<Boolean>(false);
		}
	}

	/**
	 * Close connection for all connected devices.
	 */
	public synchronized void disconnectAllDevice() {
		for (DeviceStatus status : statusMap.values()) {
			disconnectDevice(status);
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
	 * Close the socket connection for given DeviceStatus.
	 *
	 * @param status
	 * @throws IOException
	 */
	private void disconnectDevice(DeviceStatus status) {
		try {
			Socket socket = status.getSocket();
			if (socket != null && !socket.isClosed()) {
				// close streams
				PrintWriter socketOut = status.getSocketOut();
				if (socketOut != null)
					socketOut.close();
				BufferedReader socketIn = status.getSocketIn();
				if (socketIn != null)
					socketIn.close();
				socket.close();
				status.setOperationStatus(DeviceStatus.STATUS_DISCONNECTED);

				logger.debug(status.getDevice().toStringShort() + " connection closed.");
			}
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
			Future<Boolean> future = sendCommandStatus(statusRef);
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

	@Async
	private Future<Boolean> sendCommandStatus(DeviceStatus status) {
		try {
			// TODO: update command
			String command = "STATUS " + status.getDevice().toStringShort();
			// write command to device
			PrintWriter pw = status.getSocketOut();
			pw.println(command);
			// get response
			processResponse(status);

			return new AsyncResult<Boolean>(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new AsyncResult<Boolean>(false);
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
			Future<Boolean> future = sendCommandSetTemperature(statusRef);
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

	@Async
	private Future<Boolean> sendCommandSetTemperature(DeviceStatus status) {
		try {
			// get target temperature
			DeviceSetting setting = deviceSettingService.getSettingByDeviceId_Time(status.getDevice().getOid(), Calendar
					.getInstance().getTime());
			// TODO: update command
			String command = "SET TEMPERATURE " + status.getDevice().toStringShort() + " Temperature:"
					+ setting.getTemperature() + " Humidity:" + setting.getHumidity();
			// write command to device
			PrintWriter pw = status.getSocketOut();
			pw.println(command);
			// get response
			processResponse(status);

			return new AsyncResult<Boolean>(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new AsyncResult<Boolean>(false);
		}
	}

	private void processResponse(DeviceStatus status) {
		try {
			BufferedReader br = status.getSocketIn();
			do {
				String response = br.readLine();
				if (response == null)
					break;
				logger.debug(status.getDevice().toStringShort() + " response: " + response);
				// parse response header
				if (response.startsWith("[echo] STATUS")) {
					// status response
					processResponseStatus(status, response);
				} else if (response.startsWith("[echo] SET")) {
					// set temperature response
					processResponseSetTemperature(status, response);
				} else {
					logger.warn(status.getDevice().toStringShort() + " Unrecongnizable response: " + response);
				}
			} while (br.ready());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void processResponseStatus(DeviceStatus status, String response) {
		if (response == null || !response.startsWith("[echo] STATUS"))
			return;
		try {
			// TODO: parse response
			String hwVersion = "1.0";
			String swVersion = "0.1";
			double temperature = 25.5;
			double humidity = 52.5;
			status.setHwVersion(hwVersion);
			status.setSwVersion(swVersion);
			status.setTemperature(temperature);
			status.setHumidity(humidity);
			status.setStatusDate(Calendar.getInstance().getTime());
			// set target temperature & humidity
			Device device = status.getDevice();
			DeviceSetting setting = deviceSettingService.getSettingByDeviceId_Time(device.getOid(), status.getStatusDate());
			status.setTargetTemperature(setting.getTemperature());
			status.setTargetHumidity(setting.getHumidity());
			// save device log
			// logDeviceService.saveLog(status);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void processResponseSetTemperature(DeviceStatus status, String response) {
		if (response == null || !response.startsWith("[echo] SET"))
			return;
		try {
			// TODO: do something when set fail
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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
