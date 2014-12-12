/**
 *
 */
package com.acs.biz.device.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.acs.biz.device.entity.DeviceGroup;
import com.acs.biz.device.service.DeviceGroupService;
import com.acs.biz.device.service.DeviceService;
import com.acs.core.common.dao.ObjectDao;
import com.acs.core.common.exception.CoreException;
import com.acs.core.common.service.impl.DomainServiceImpl;

/**
 * @author Eric
 */
public class DeviceGroupServiceImpl extends DomainServiceImpl<DeviceGroup> implements DeviceGroupService {

	@Resource
	private DeviceService deviceService;

	/**
	 * Get map of device groups with Pkey as map key. Ordered by "groupOrder" field.
	 *
	 * @return
	 */
	@Override
	public Map<String, DeviceGroup> getMapOrdered() {
		ObjectDao<DeviceGroup> dao = super.getDao();
		return dao.getMap("oid", null, new String[] { "groupOrder asc", "groupName asc" });
	}

	/**
	 * List rows ordered by "groupOrder" field.
	 *
	 * @return
	 */
	@Override
	public List<DeviceGroup> listAllOrdered() {
		return super.getList(0, -1, null, new String[] { "groupOrder asc", "groupName asc" });
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(DeviceGroup entity) throws CoreException {
		deviceService.clearDeviceGroup(entity.getOid());
		super.delete(entity);
	}

}
