/**
 *
 */
package com.acs.biz.device.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.acs.core.common.entity.BaseEntity;

/**
 * @author Eric
 */
@Entity
@Table(name = "acs_device_setting", uniqueConstraints = { @UniqueConstraint(columnNames = { "device_id", "day_of_week",
"time_of_day" }) })
public class DeviceSetting extends BaseEntity {

	private static final long serialVersionUID = -1627688700390401064L;

	/**
	 * PKey id
	 */
	@Id
	@GeneratedValue
	@Column(name = "oid", nullable = false)
	private Long oid;

	/**
	 * Device Id
	 */
	@Column(name = "device_id", nullable = false)
	private Long deviceId;

	/**
	 * Day of week in 3 letters, MON,TUE,etc.
	 */
	@Column(name = "day_of_week", length = 3, nullable = false)
	private String dayOfWeek;

	/**
	 * Time of day in 4 digits HHMM. 0000,0100,etc.
	 */
	@Column(name = "time_of_day", nullable = false)
	private int timeOfDay;

	/**
	 * Temperature in Celsius
	 */
	@Column(name = "temperature", nullable = false)
	private double temperature;

	/**
	 * Humidity percentage, 0~100.
	 */
	@Column(name = "humidity", nullable = false)
	private double humidity;

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(int timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public Long getOid() {
		return oid;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("oid", this.oid)
				.append("deviceId", this.deviceId).append("dayOfWeek", this.dayOfWeek).append("timeOfDay", timeOfDay)
				.append("temperature", this.temperature).append("humidity", this.humidity).toString();
	}

}
