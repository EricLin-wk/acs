/**
 *
 */
package com.acs.biz.device.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.acs.biz.device.entity.Device;
import com.acs.biz.device.entity.DeviceSetting;
import com.acs.biz.device.service.DeviceService;
import com.acs.biz.device.service.DeviceSettingService;
import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.service.impl.DomainServiceImpl;

/**
 * @author Eric
 */
public class DeviceSettingServiceImpl extends DomainServiceImpl<DeviceSetting> implements DeviceSettingService {

	@Resource
	private NamedParameterJdbcTemplate npJdbcTemplate;
	@Resource
	private DeviceService deviceService;

	@Override
	@Transactional(readOnly = false)
	public DeviceSetting setDefaultTemperatureHumidity(double temperature, double humidity) {
		DeviceSetting setting = getDefaultSetting();
		if (setting == null) {
			setting = new DeviceSetting();
			setting.setDeviceId(DeviceSettingService.DEFAULT_DEVICE_ID);
			setting.setDayOfWeek(DeviceSettingService.DEFAULT_DAY_OF_WEEK);
			setting.setTimeOfDay(DeviceSettingService.DEFAULT_TIME_OF_DAY);
		} else if (setting.getTemperature() == temperature && setting.getHumidity() == humidity)
			return setting;
		setting.setTemperature(temperature);
		setting.setHumidity(humidity);
		return super.save(setting);
	}

	@Override
	public DeviceSetting getDefaultSetting() {
		CommonCriteria crit = new CommonCriteria();
		crit.addEq("deviceId", DeviceSettingService.DEFAULT_DEVICE_ID);
		crit.addEq("dayOfWeek", DeviceSettingService.DEFAULT_DAY_OF_WEEK);
		crit.addEq("timeOfDay", DeviceSettingService.DEFAULT_TIME_OF_DAY);
		DeviceSetting setting = super.getSingle(crit, null);
		return setting;
	}

	@Override
	@Transactional(readOnly = false)
	public DeviceSetting saveDeviceSetting(Long deviceId, String dayOfWeek, int timeOfDay, double temperature,
			double humidity) {
		CommonCriteria crit = new CommonCriteria();
		crit.addEq("deviceId", deviceId);
		crit.addEq("dayOfWeek", dayOfWeek);
		crit.addEq("timeOfDay", timeOfDay);
		DeviceSetting setting = super.getSingle(crit, null);
		if (setting == null) {
			setting = new DeviceSetting();
			setting.setDeviceId(deviceId);
			setting.setDayOfWeek(dayOfWeek);
			setting.setTimeOfDay(timeOfDay);
		} else if (setting.getTemperature() == temperature && setting.getHumidity() == humidity)
			return setting;
		setting.setTemperature(temperature);
		setting.setHumidity(humidity);
		return super.save(setting);
	}

	@Override
	public List<LinkedHashMap<String, String>> getJsonSettingListForDevice(Long deviceId) {
		// get default setting for null records
		DeviceSetting defautSetting = this.getDefaultSetting();
		List<LinkedHashMap<String, String>> timeList = new ArrayList<LinkedHashMap<String, String>>();
		for (int i = 0; i < 24; i++) {
			LinkedHashMap<String, String> dayMap = new LinkedHashMap<String, String>();
			// generate timeOfDay key
			String timeOfDay = String.format("%04d", i * 100);
			dayMap.put("timeOfDay", timeOfDay);

			// find settings for deviceId+timeOfDay
			CommonCriteria crit = new CommonCriteria();
			crit.addEq("deviceId", deviceId);
			crit.addEq("timeOfDay", i * 100);
			Map<String, DeviceSetting> settingMap = super.getDao().getMap("dayOfWeek", crit, null);

			// iterate from Sunday to Saturday
			Iterator<Calendar> itor = DateUtils.iterator(Calendar.getInstance(), DateUtils.RANGE_WEEK_SUNDAY);
			while (itor.hasNext()) {
				Calendar cal = itor.next();
				String dayOfWeek = DateFormatUtils.format(cal, "EEE", Locale.US).toLowerCase();

				DeviceSetting setting = settingMap.get(dayOfWeek);
				if (setting != null)
					dayMap.put(dayOfWeek, Math.round(setting.getTemperature()) + "°C " + Math.round(setting.getHumidity()) + "%");
				else
					dayMap.put(dayOfWeek, (int) defautSetting.getTemperature() + "°C " + (int) defautSetting.getHumidity() + "%");
			}
			timeList.add(dayMap);
		}

		return timeList;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveDeviceSettingFromMap(Long deviceId, List<HashMap<String, String>> listSetting) {
		DeviceSetting defaultSetting = this.getDefaultSetting();
		// loop list
		for (HashMap<String, String> settingMap : listSetting) {
			String timeOfDayStr = settingMap.get("timeOfDay");
			int timeOfDay = Integer.parseInt(timeOfDayStr);
			// iterate from Sunday to Saturday
			Iterator<Calendar> itor = DateUtils.iterator(Calendar.getInstance(), DateUtils.RANGE_WEEK_SUNDAY);
			while (itor.hasNext()) {
				Calendar cal = itor.next();
				String dayOfWeekStr = DateFormatUtils.format(cal, "EEE", Locale.US).toLowerCase();
				String settingStr = settingMap.get(dayOfWeekStr);
				double temperature;
				double humidity;
				if (StringUtils.isNotBlank(settingStr)) {
					String[] array = settingStr.split(" ");
					temperature = Double.parseDouble(array[0].replace("°C", ""));
					humidity = Double.parseDouble(array[1].replace("%", ""));
				} else {
					temperature = defaultSetting.getTemperature();
					humidity = defaultSetting.getHumidity();
				}
				saveDeviceSetting(deviceId, dayOfWeekStr, timeOfDay, temperature, humidity);
			}
		}
	}

	/**
	 * Return DeviceSetting for given group id. Temperature & humidity will be the average of all group devices.
	 *
	 * @param groupId
	 * @return
	 */
	private List<Map<String, Object>> getGroupSettingMap(Long groupId) {
		String sql = "SELECT s.time_of_day, s.day_of_week, avg(s.temperature) as temperature, avg(s.humidity) as humidity FROM acs_device_setting s inner join acs_device d on s.device_id=d.oid where d.group_id=:groupId group by s.time_of_day, s.day_of_week;";
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("groupId", groupId);
		return npJdbcTemplate.queryForList(sql, paramMap);
	}

	@Override
	public List<LinkedHashMap<String, String>> getJsonSettingListForGroup(Long groupId) {
		// get default setting for null records
		DeviceSetting defautSetting = this.getDefaultSetting();
		// put group settings into map
		HashMap<String, Map<String, Object>> groupMap = new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> row : this.getGroupSettingMap(groupId)) {
			String key = (int) row.get("time_of_day") + (String) row.get("day_of_week");
			groupMap.put(key, row);
		}
		List<LinkedHashMap<String, String>> timeList = new ArrayList<LinkedHashMap<String, String>>();
		for (int i = 0; i < 24; i++) {
			LinkedHashMap<String, String> dayMap = new LinkedHashMap<String, String>();
			// generate timeOfDay key
			String timeOfDay = String.format("%04d", i * 100);
			dayMap.put("timeOfDay", timeOfDay);

			// iterate from Sunday to Saturday
			Iterator<Calendar> itor = DateUtils.iterator(Calendar.getInstance(), DateUtils.RANGE_WEEK_SUNDAY);
			while (itor.hasNext()) {
				Calendar cal = itor.next();
				String dayOfWeek = DateFormatUtils.format(cal, "EEE", Locale.US).toLowerCase();

				Map<String, Object> setting = groupMap.get((i * 100) + dayOfWeek);
				if (setting != null) {
					double temperature = (double) setting.get("temperature");
					double humidity = (double) setting.get("humidity");
					dayMap.put(dayOfWeek, Math.round(temperature) + "°C " + Math.round(humidity) + "%");
				} else
					dayMap.put(dayOfWeek, (int) defautSetting.getTemperature() + "°C " + (int) defautSetting.getHumidity() + "%");
			}
			timeList.add(dayMap);
		}

		return timeList;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveGroupSettingFromMap(Long groupId, List<HashMap<String, String>> listSetting) {
		// get Device under the group
		List<Device> deviceList = deviceService.listByIsDelete_GroupId(0, -1, false, groupId);
		for (Device dev : deviceList) {
			// save to device setting
			this.saveDeviceSettingFromMap(dev.getOid(), listSetting);
		}
	}
}
