/**
 *
 */
package com.acs.biz.device.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.acs.biz.device.entity.Device;
import com.acs.biz.device.service.DeviceService;
import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.exception.CoreException;
import com.acs.core.common.service.impl.DomainServiceImpl;

/**
 * @author Eric
 */
public class DeviceServiceImpl extends DomainServiceImpl<Device> implements DeviceService {

	@Resource
	private NamedParameterJdbcTemplate npJdbcTemplate;

	/**
	 * List device entities with isDelete equal false
	 *
	 * @return
	 */
	@Override
	public List<Device> listActiveDevices(int firstResult, int maxResults) {
		List<Device> list = null;
		CommonCriteria crit = new CommonCriteria();
		crit.addEq("isDelete", false);
		list = super.getList(firstResult, maxResults, crit, new String[] { "deviceName" });
		return list;
	}

	@Override
	public List<Device> listByIsDelete_GroupId_DeviceType_SerialNum(int firstResult, int maxResults, boolean isDelete,
			Long groupId, String deviceType, String serialNum) {
		List<Device> list = null;
		CommonCriteria crit = new CommonCriteria();
		crit.addEq("isDelete", isDelete);
		if (groupId != null && groupId >= 0)
			crit.addEq("groupId", groupId);
		if (StringUtils.isNotBlank(deviceType))
			crit.addEq("deviceType", deviceType);
		if (StringUtils.isNotBlank(serialNum))
			crit.addLike("serialNum", "%" + serialNum + "%");
		list = super.getList(firstResult, maxResults, crit, new String[] { "deviceName" });
		return list;
	}

	@Override
	public int listSizeByIsDelete_GroupId_DeviceType_SerialNum(boolean isDelete, Long groupId, String deviceType,
			String serialNum) {
		CommonCriteria crit = new CommonCriteria();
		crit.addEq("isDelete", isDelete);
		if (groupId != null && groupId >= 0)
			crit.addEq("groupId", groupId);
		if (StringUtils.isNotBlank(deviceType))
			crit.addEq("deviceType", deviceType);
		if (StringUtils.isNotBlank(serialNum))
			crit.addLike("serialNum", "%" + serialNum + "%");
		int size = super.getListSize(crit).intValue();
		return size;
	}

	/**
	 * Set delete flag to true for given PKey
	 *
	 * @param oid Id of record to update.
	 * @return Updated device
	 */
	@Override
	public Device markDelete(Long oid) {
		Device dev = super.get(oid);
		if (dev == null) {
			throw new CoreException("Device id " + oid + " does not exist");
		}
		dev.setDelete(true);
		dev = super.save(dev);
		return dev;
	}

	/**
	 * Clear group Id before GroupDevice gets deleted.
	 *
	 * @param groupId
	 */
	@Override
	public void clearDeviceGroup(Long groupId) {
		String sql = "update acs_device set group_id=null where group_id=:groupId";
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("groupId", groupId);
		int updRows = npJdbcTemplate.update(sql, parameter);
		logger.debug("updRows:" + updRows);
	}

	@Override
	public boolean isSerialNumUsed(String serialNum, Long oid) {
		CommonCriteria crit = new CommonCriteria();
		crit.addEq("serialNum", serialNum);
		if (oid != null)
			crit.addNe("oid", oid);
		int size = super.getListSize(crit).intValue();
		return size > 0 ? true : false;
	}

	@Override
	public boolean isDeviceNameUsed(String deviceName, Long oid) {
		CommonCriteria crit = new CommonCriteria();
		crit.addEq("deviceName", deviceName);
		if (oid != null)
			crit.addNe("oid", oid);
		int size = super.getListSize(crit).intValue();
		return size > 0 ? true : false;
	}

}
