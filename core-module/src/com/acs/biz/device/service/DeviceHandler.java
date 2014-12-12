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
import java.util.Hashtable;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.biz.device.entity.Device;
import com.acs.biz.device.entity.DeviceStatus;

/**
 * @author Eric
 */
public class DeviceHandler {

	/** logger **/
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final int CONNECTION_TIMEOUT = 60;

	private static final int COMMAND_TIMEOUT = 60;

	@Resource
	private DeviceService deviceService;

	private static Hashtable<String, DeviceStatus> statusMap = new Hashtable<String, DeviceStatus>();

	/**
	 * Create socket connections to all active devices
	 */
	public void connectActiveDevices() {
		List<Device> devList = deviceService.listActiveDevices(0, -1);
		for (Device device : devList) {
			// get corresponding device status
			DeviceStatus status = statusMap.get(device.getSerialNum());
			if (status == null) {
				status = new DeviceStatus();
			}
			status.setDevice(device);
			// create connection if not already connected
			Socket socket = status.getSocket();
			if (socket == null || socket.isClosed()) {
				try {
					createSocketConnection(status);
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * Close connection for all connected devices.
	 */
	public void disconnectActiveDevices() {
		for (DeviceStatus status : statusMap.values()) {
			try {
				closeSocketConnection(status);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
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
	private void createSocketConnection(DeviceStatus status) throws UnknownHostException, IOException {
		// close current connection if it's open
		closeSocketConnection(status);
		// create new socket connection
		InetSocketAddress endpoint = new InetSocketAddress(status.getDevice().getIpAddress(), status.getDevice().getPort());
		Socket socket = new Socket();
		socket.setKeepAlive(true);
		socket.setSoTimeout(COMMAND_TIMEOUT);
		socket.connect(endpoint, CONNECTION_TIMEOUT);
		// get input & output stream
		PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// store connection back to entity
		status.setSocket(socket);
		status.setSocketOut(socketOut);
		status.setSocketIn(socketIn);
	}

	/**
	 * Close the socket connection for given DeviceStatus.
	 *
	 * @param status
	 * @throws IOException
	 */
	private void closeSocketConnection(DeviceStatus status) throws IOException {
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
		}
	}
}
