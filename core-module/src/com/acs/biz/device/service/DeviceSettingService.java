/**
 *
 */
package com.acs.biz.device.service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.acs.biz.device.entity.DeviceSetting;
import com.acs.core.common.service.DomainService;

/**
 * @author Eric
 */
public interface DeviceSettingService extends DomainService<DeviceSetting> {
	public static final long DEFAULT_DEVICE_ID = -1;
	public static final String DEFAULT_DAY_OF_WEEK = "*";
	public static final int DEFAULT_TIME_OF_DAY = -1;

	/**
	 * Set the default device setting for temperature and humidity
	 *
	 * @param temperature
	 * @param humidity
	 * @return
	 */
	public DeviceSetting setDefaultTemperatureHumidity(double temperature, double humidity);

	/**
	 * Get default device setting
	 *
	 * @return
	 */
	public DeviceSetting getDefaultSetting();

	/**
	 * Seve the given device setting parameters
	 *
	 * @param deviceId
	 * @param dayOfWeek
	 * @param timeOfDay
	 * @param temperature
	 * @param humidity
	 * @return
	 */
	public DeviceSetting saveDeviceSetting(Long deviceId, String dayOfWeek, int timeOfDay, double temperature,
			double humidity);

	/**
	 * Generate List of Maps with map key: timeOfDay, sun, mon, tue, wed, thur, fri, sat. To be used for json output
	 *
	 * @param deviceId
	 * @return
	 */
	public List<LinkedHashMap<String, String>> getJsonSettingListForDevice(Long deviceId);

	/**
	 * Save the given settings map for the device
	 *
	 * @param deviceId
	 * @param listSetting
	 */
	public void saveDeviceSettingFromMap(Long deviceId, List<HashMap<String, String>> listSetting);

	/**
	 * Generate List of Maps with map key: timeOfDay, sun, mon, tue, wed, thur, fri, sat. To be used for json output
	 *
	 * @param groupId
	 * @return
	 */
	public List<LinkedHashMap<String, String>> getJsonSettingListForGroup(Long groupId);

	/**
	 * Save the given setting into all devices under the specified group.
	 *
	 * @param groupId
	 * @param listSetting
	 */
	public void saveGroupSettingFromMap(Long groupId, List<HashMap<String, String>> listSetting);

	/**
	 * Get Device setting for given device Id and time.
	 *
	 * @param deviceId
	 * @param time
	 * @return
	 */
	public DeviceSetting getSettingByDeviceId_Time(Long deviceId, Date time);

}
