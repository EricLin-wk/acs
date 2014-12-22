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

	public static final String STATUS_CONNECTED = "已连线";
	public static final String STATUS_DISCONNECTED = "未连线";
	public static final String STATUS_CONNECTION_ERROR = "连线错误";

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
	private Double temperature;

	/**
	 * current humidity
	 */
	private Double humidity;

	/**
	 * target temperature in celsius
	 */
	private Double targetTemperature;

	/**
	 * target humidity
	 */
	private Double targetHumidity;

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

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperatureCelsius) {
		this.temperature = temperatureCelsius;
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

	public Double getTargetTemperature() {
		return targetTemperature;
	}

	public void setTargetTemperature(Double targetTemperature) {
		this.targetTemperature = targetTemperature;
	}

	public Double getTargetHumidity() {
		return targetHumidity;
	}

	public void setTargetHumidity(Double targetHumidity) {
		this.targetHumidity = targetHumidity;
	}

	public String getStatusConnected() {
		return STATUS_CONNECTED;
	}

	public String getStatusDisconnected() {
		return STATUS_DISCONNECTED;
	}

	public String getStatusConnectionError() {
		return STATUS_CONNECTION_ERROR;
	}

}
