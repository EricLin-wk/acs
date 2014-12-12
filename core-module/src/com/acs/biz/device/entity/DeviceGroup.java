/**
 *
 */
package com.acs.biz.device.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.acs.core.common.entity.BaseEntity;

/**
 * @author Eric
 */
@Entity
@Table(name = "acs_device_group")
public class DeviceGroup extends BaseEntity {

	private static final long serialVersionUID = -7471747727822019789L;

	/**
	 * PKey id
	 */
	@Id
	@GeneratedValue
	@Column(name = "oid")
	private Long oid;

	/**
	 * Device group name
	 */
	@Column(name = "group_name", length = 50)
	private String groupName;

	/**
	 * Order field
	 */
	@Column(name = "group_order")
	private int groupOrder;

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupOrder() {
		return groupOrder;
	}

	public void setGroupOrder(int groupOrder) {
		this.groupOrder = groupOrder;
	}

}
