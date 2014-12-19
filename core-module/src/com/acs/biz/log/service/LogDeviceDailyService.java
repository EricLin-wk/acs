/**
 *
 */
package com.acs.biz.log.service;

import java.util.Date;

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

}
