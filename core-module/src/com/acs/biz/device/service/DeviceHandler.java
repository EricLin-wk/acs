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
import java.util.Calendar;
import java.util.concurrent.Future;

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

	@Resource
	private DeviceSettingService deviceSettingService;
	@Resource
	private LogDeviceService logDeviceService;

	private static final int CONNECTION_TIMEOUT_SEC = 30;

	private static final int COMMAND_TIMEOUT_SEC = 30;

	/**
	 * No public constructor for singleton instance
	 */
	protected DeviceHandler() {

	}

	/**
	 * Create new socket connection to the device. Existing connection will be closed.
	 *
	 * @param status
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	@Async("myExecutor")
	public Future<Boolean> connectDevice(DeviceStatus status) {
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
	 * Close the socket connection for given DeviceStatus.
	 *
	 * @param status
	 * @throws IOException
	 */
	public void disconnectDevice(DeviceStatus status) {
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

	@Async
	public Future<Boolean> sendCommandStatus(DeviceStatus status) {
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

	@Async
	public Future<Boolean> sendCommandSetTemperature(DeviceStatus status) {
		try {
			// get target temperature
			DeviceSetting setting = deviceSettingService.getSettingByDeviceId_Time(status.getDevice().getOid(), Calendar
					.getInstance().getTime());
			// TODO: update command
			String command = "SET TEMPERATURE" + status.getDevice().toStringShort() + "\tTemperature:"
					+ setting.getTemperature() + "\tHumidity:" + setting.getHumidity();
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
				if (response.startsWith("[OK] STATUS")) {
					// status response
					processResponseStatus(status, response);
				} else if (response.startsWith("[OK] SET TEMPERATURE")) {
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
		if (response == null || !response.startsWith("[OK] STATUS"))
			return;
		try {
			// TODO: parse response
			double temperature, humidity;
			String[] arg = response.split("\t");
			temperature = Double.parseDouble(arg[1].split(":")[1]);
			humidity = Double.parseDouble(arg[2].split(":")[1]);

			String hwVersion = "1.0";
			String swVersion = "0.1";

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
			logDeviceService.saveLog(status);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void processResponseSetTemperature(DeviceStatus status, String response) {
		if (response == null || !response.startsWith("[OK] SET TEMPERATURE"))
			return;
		try {
			// TODO: do something when set fail
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
