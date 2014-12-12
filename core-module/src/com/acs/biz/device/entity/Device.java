/**
 *
 */
package com.acs.biz.device.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.acs.core.common.entity.BaseEntity;

/**
 * @author Eric
 */
@Entity
@Table(name = "acs_device")
public class Device extends BaseEntity {

	private static final long serialVersionUID = 8211048761264453369L;

	/**
	 * PKey id
	 */
	@Id
	@GeneratedValue
	@Column(name = "oid")
	private Long oid;

	/**
	 * IP Address, or domain name
	 */
	@Column(name = "ip_address", length = 50)
	private String ipAddress;

	/**
	 * TCP port
	 */
	@Column(name = "port")
	private int port;

	/**
	 * Serial number
	 */
	@Column(name = "serial_num", length = 50)
	private String serialNum;

	/**
	 * Model name
	 */
	@Column(name = "model", length = 50)
	private String model;

	/**
	 * Device nick name
	 */
	@Column(name = "device_name", length = 50)
	private String deviceName;

	/**
	 * Device type. Air conditional, humidifier, dehumidifier etc.
	 */
	@Column(name = "device_type", length = 50)
	private String deviceType;

	/**
	 * Delete flag
	 */
	@Column(name = "is_delete", columnDefinition = "bit", length = 1)
	private boolean isDelete;

	/**
	 * Group ID
	 */
	@Column(name = "group_id")
	private Long groupId;

	public Long getOid() {
		return oid;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("oid", this.oid)
				.append("ipAddress", this.ipAddress).append("port", this.port).append("deviceName", deviceName)
				.append("serialNum", this.serialNum).append("deviceType", this.deviceType).toString();
	}

}
