/**
 *
 */
package com.acs.biz.device.service;

import java.util.List;
import java.util.Map;

import com.acs.biz.device.entity.DeviceGroup;
import com.acs.core.common.service.DomainService;

/**
 * @author Eric
 */
public interface DeviceGroupService extends DomainService<DeviceGroup> {

	/**
	 * Get map of device groups with Pkey as map key.
	 *
	 * @return
	 */
	public Map<String, DeviceGroup> getMapOrdered();

	/**
	 * List rows ordered by "groupOrder" field.
	 *
	 * @return
	 */
	public List<DeviceGroup> listAllOrdered();

}
