/**
 *
 */
package com.acs.biz.device.entity;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * @author Eric Stores current device status, not persisted to DB.
 */
public class DeviceStatus {

	/**
	 * hardware version
	 */
	private String hwVersion;

	/**
	 * software version
	 */
	private String swVersion;

	/**
	 * operation status
	 */
	private String operationStatus;

	/**
	 * current temperature in celsius
	 */
	private Double temperatureCelsius;

	/**
	 * current humidity
	 */
	private Double humidity;

	/**
	 * timestamp for last status update
	 */
	private Date statusDate;

	/**
	 * Socket to connected device
	 */
	private Socket socket;

	/**
	 * Output stream to connected device
	 */
	private PrintWriter socketOut;

	/**
	 * Input stream to connected device
	 */
	private BufferedReader socketIn;

	/**
	 *
	 */
	private Device device;

	public String getHwVersion() {
		return hwVersion;
	}

	public void setHwVersion(String hwVersion) {
		this.hwVersion = hwVersion;
	}

	public String getSwVersion() {
		return swVersion;
	}

	public void setSwVersion(String swVersion) {
		this.swVersion = swVersion;
	}

	public String getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}

	public Double getTemperatureCelsius() {
		return temperatureCelsius;
	}

	public void setTemperatureCelsius(Double temperatureCelsius) {
		this.temperatureCelsius = temperatureCelsius;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public PrintWriter getSocketOut() {
		return socketOut;
	}

	public void setSocketOut(PrintWriter socketOut) {
		this.socketOut = socketOut;
	}

	public BufferedReader getSocketIn() {
		return socketIn;
	}

	public void setSocketIn(BufferedReader socketIn) {
		this.socketIn = socketIn;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

}
