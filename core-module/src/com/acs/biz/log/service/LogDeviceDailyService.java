/**
 *
 */
package com.acs.biz.log.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.acs.biz.log.entity.LogDeviceDaily;
import com.acs.core.common.service.DomainService;

/**
 * @author Eric
 */
public interface LogDeviceDailyService extends DomainService<LogDeviceDaily> {

	/**
	 * Delete logs with RecordDates less equal to given cutOffDate
	 *
	 * @param cutOffDate
	 * @return Number of rows deleted.
	 */
	public int deleteLog(Date cutOffDate);

	/**
	 * Aggregates rows in LogDeviceHourly table by RecordDate's day and DeviceId, then insert into LogDeviceDaily table
	 *
	 * @param recordDateStart
	 * @param recordDateEnd
	 * @param deviceId
	 */
	public void aggregateLogDeviceDaily(Date recordDateStart, Date recordDateEnd, Long deviceId);

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

}
