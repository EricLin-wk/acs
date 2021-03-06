/**
 *
 */
package com.acs.biz.log.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.acs.biz.log.entity.LogDeviceHourly;
import com.acs.core.common.service.DomainService;

/**
 * @author Eric
 */
public interface LogDeviceHourlyService extends DomainService<LogDeviceHourly> {

	/**
	 * Get list size for given RecordDate range and DeviceId
	 *
	 * @param recordDateStart
	 * @param recordDateEnd
	 * @param deviceId
	 * @return
	 */
	public int listSizeByRecordDate_DeviceId(Date recordDateStart, Date recordDateEnd, Long deviceId);

	/**
	 * Get list size for given RecordDate range
	 *
	 * @param recordDateStart
	 * @param recordDateEnd
	 * @return
	 */
	public int listSizeByRecordDate(Date recordDateStart, Date recordDateEnd);

	/**
	 * Delete logs with RecordDates less equal to given cutOffDate
	 *
	 * @param cutOffDate
	 * @return Number of rows deleted.
	 */
	public int deleteLog(Date cutOffDate);

	/**
	 * Aggregates rows in LogDevice table by RecordDate's hour and DeviceId, then insert into LogDeviceHourly table
	 *
	 * @param recordDateStart
	 * @param recordDateEnd
	 * @param deviceId
	 */
	public void aggregateLogDeviceHourly(Date recordDateStart, Date recordDateEnd, Long deviceId);

	/**
	 * List by device id and record date range.
	 *
	 * @param deviceId
	 * @param recordDateStart
	 * @param recordDateEnd
	 * @return List of map with keys: record_date, temperature, humidity. Ordered by record date in ascending order.
	 */
	public List<Map<String, Object>> listByDeviceId_RecordDate(Long deviceId, Date recordDateStart, Date recordDateEnd);

	/**
	 * List by group id and record date range. Grouped by recordDate and groupId.
	 *
	 * @param groupId
	 * @param recordDateStart
	 * @param recordDateEnd
	 * @return
	 */
	public List<Map<String, Object>> listGroupByGroupId_RecordDate(Long groupId, Date recordDateStart, Date recordDateEnd);

	/**
	 * Aggregates rows in LogDevice table by RecordDate's hour and DeviceId, then insert into LogDeviceHourly table
	 *
	 * @param recordDateStart
	 * @param recordDateEnd
	 */
	public void aggregateLogDeviceHourly(Date recordDateStart, Date recordDateEnd);

}
