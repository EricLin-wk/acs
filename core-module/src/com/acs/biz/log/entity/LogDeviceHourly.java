/**
 *
 */
package com.acs.biz.log.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.acs.core.common.entity.BaseEntity;

/**
 * @author Eric
 */
@Entity
@Table(name = "acs_log_device_hourly", uniqueConstraints = { @UniqueConstraint(columnNames = { "record_date",
		"device_id" }) })
public class LogDeviceHourly extends BaseEntity {

	private static final long serialVersionUID = 750813631802820008L;

	/**
	 * PKey id
	 */
	@Id
	@GeneratedValue
	@Column(name = "oid", nullable = false)
	private Long oid;

	/**
	 * Recording date
	 */
	@Column(name = "record_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordDate;

	/**
	 * Device Id
	 */
	@Column(name = "device_id", nullable = false)
	private Long deviceId;

	/**
	 * Device's group Id
	 */
	@Column(name = "group_id")
	private Long groupId;

	/**
	 * Average recorded temperature
	 */
	@Column(name = "temperature", nullable = false)
	private double temperature;

	/**
	 * Average recorded humidity
	 */
	@Column(name = "humidity", nullable = false)
	private double humidity;

	/**
	 * Target temperature
	 */
	@Column(name = "target_temperature", nullable = false)
	private double targetTemperature;

	/**
	 * Target humidity
	 */
	@Column(name = "target_humidity", nullable = false)
	private double targetHumidity;

	/**
	 * Maximum recorded temperature
	 */
	@Column(name = "max_temperature", nullable = false)
	private double maxTemperature;

	/**
	 * Maximum recorded humidity
	 */
	@Column(name = "max_humidity", nullable = false)
	private double maxHumidity;

	/**
	 * Minimum recorded temperature
	 */
	@Column(name = "min_temperature", nullable = false)
	private double minTemperature;

	/**
	 * Minimum recorded humidity
	 */
	@Column(name = "min_humidity", nullable = false)
	private double minHumidity;

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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

	public double getTargetTemperature() {
		return targetTemperature;
	}

	public void setTargetTemperature(double targetTemperature) {
		this.targetTemperature = targetTemperature;
	}

	public double getTargetHumidity() {
		return targetHumidity;
	}

	public void setTargetHumidity(double targetHumidity) {
		this.targetHumidity = targetHumidity;
	}

	public double getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public double getMaxHumidity() {
		return maxHumidity;
	}

	public void setMaxHumidity(double maxHumidity) {
		this.maxHumidity = maxHumidity;
	}

	public double getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(double minTemperature) {
		this.minTemperature = minTemperature;
	}

	public double getMinHumidity() {
		return minHumidity;
	}

	public void setMinHumidity(double minHumidity) {
		this.minHumidity = minHumidity;
	}

	public Long getOid() {
		return oid;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("oid", this.oid)
				.append("recordDate", this.recordDate).append("deviceId", this.deviceId).append("groupId", groupId)
				.append("temperature", this.temperature).append("humidity", this.humidity).toString();
	}

}
