/**
 *
 */
package com.acs.biz.log.service;

import java.util.Date;

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

}
