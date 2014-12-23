/**
 *
 */
package com.acs.biz.log.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.acs.biz.device.entity.DeviceStatus;
import com.acs.biz.log.entity.LogDevice;
import com.acs.core.common.service.DomainService;

/**
 * @author Eric
 */
public interface LogDeviceService extends DomainService<LogDevice> {

	/**
	 * Save log based on given DeviceStatus.
	 *
	 * @param status
	 * @return
	 */
	public LogDevice saveLog(DeviceStatus status);

	/**
	 * List distinct record day (ignore time), device combinations in the table prior to given cut off date.
	 *
	 * @param cutOffDate
	 * @return List of map with keys: record_date, device_id
	 */
	public List<Map<String, Object>> listDistinctRecordDay_DeviceId(Date cutOffDate);

	/**
	 * Delete rows with RecordDate less equal to cutOffDate
	 *
	 * @param cutOffDate
	 * @return Number of rows deleted
	 */
	public int deleteLog(Date cutOffDate);

	/**
	 * List by device id and record date range.
	 *
	 * @param deviceId
	 * @param recordDateStart
	 * @param recordDateEnd
	 * @return List of map with keys: record_date, temperature, humidity. Ordered by record date in ascending order.
	 */
	public List<Map<String, Object>> listByDeviceId_RecordDate(Long deviceId, Date recordDateStart, Date recordDateEnd);

}
