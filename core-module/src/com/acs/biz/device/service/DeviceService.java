/**
 *
 */
package com.acs.biz.device.service;

import java.util.List;

import com.acs.biz.device.entity.Device;
import com.acs.core.common.service.DomainService;

/**
 * @author Eric
 */
public interface DeviceService extends DomainService<Device> {

	public static final String DEVICE_TYPE_AIR_CONDITION = "AC";
	public static final String DEVICE_TYPE_HUMIDIFIER = "HM";
	public static final String DEVICE_TYPE_DEHUMIDIFIER = "DH";
	public static final String DEVICE_TYPE_AIR_PURIFY = "AP";

	/**
	 * List device entities with isDelete equal false
	 *
	 * @return
	 */
	public List<Device> listActiveDevices(int firstResult, int maxResults);

	/**
	 * Set delete flag to true for given PKey
	 *
	 * @param oid Id of record to update.
	 * @return Updated device
	 */
	public Device markDelete(Long oid);

	/**
	 * Clear group Id before GroupDevice gets deleted.
	 *
	 * @param groupId
	 */
	public abstract void clearDeviceGroup(Long groupId);

	/**
	 * List device entities with matching criteria.
	 *
	 * @param firstResult
	 * @param maxResults
	 * @param isDelete Delete flag
	 * @param groupId Group Id, -1 for all groups
	 * @param deviceType Device type, blank for all
	 * @param serialNum Serial number, blank for all
	 * @return
	 */
	public List<Device> listByIsDelete_GroupId_DeviceType_SerialNum(int firstResult, int maxResults, boolean isDelete,
			Long groupId, String deviceType, String serialNum);

	/**
	 * Get record number with matching criteria.
	 *
	 * @param isDelete Delete flag
	 * @param groupId Group Id, -1 for all groups
	 * @param deviceType Device type, blank for all
	 * @param serialNum Serial number, blank for all
	 * @return
	 */
	public abstract int listSizeByIsDelete_GroupId_DeviceType_SerialNum(boolean isDelete, Long groupId,
			String deviceType, String serialNum);

	/**
	 * Check if given serial number is used, excluding records with given oid
	 *
	 * @param serialNum
	 * @param oid Oid to exclude
	 * @return
	 */
	public boolean isSerialNumUsed(String serialNum, Long oid);

	/**
	 * Check if given device name is used, excluding records with given oid
	 *
	 * @param deviceName
	 * @param oid Oid to exclude
	 * @return
	 */
	public boolean isDeviceNameUsed(String deviceName, Long oid);

}
