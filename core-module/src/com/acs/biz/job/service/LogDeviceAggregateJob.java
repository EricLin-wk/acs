/**
 *
 */
package com.acs.biz.job.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acs.biz.log.service.LogDeviceDailyService;
import com.acs.biz.log.service.LogDeviceHourlyService;
import com.acs.biz.log.service.LogDeviceService;

/**
 * @author Eric
 */
public class LogDeviceAggregateJob {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private LogDeviceService logDeviceService;
	@Resource
	private LogDeviceHourlyService logDeviceHourlyService;
	@Resource
	private LogDeviceDailyService logDeviceDailyService;

	private static final int DEVICE_LOG_RETAIN_DAYS = 7;
	private static final int DEVICE_LOG_HOURLY_RETAIN_DAYS = 60;
	@SuppressWarnings("unused")
	private static final int DEVICE_LOG_DAILY_RETAIN_DAYS = 365 * 5;

	/**
	 * Aggregate LogDevice data into LogDeviceHourly & LogDeviceDaily table. Delete logs that are past retaining days.
	 * Method to be run in a scheduled job once daily.
	 */
	public void aggregateLogDeviceData() {
		try {
			Calendar cal = DateUtils.truncate(Calendar.getInstance(), Calendar.DAY_OF_MONTH);
			List<Map<String, Object>> list = logDeviceService.listDistinctRecordDay_DeviceId(cal.getTime());
			// loop over available data before today
			for (Map<String, Object> row : list) {
				Date recordDate = (Date) row.get("record_date");
				Long deviceId = (Long) row.get("device_id");
				try {
					aggregateLogDeviceDataByRecordDate_DeviceId(recordDate, deviceId);
				} catch (Exception e) {
					logger.error("Error during aggregateLogDeviceDataByRecordDate_DeviceId. RecordDate:" + recordDate
							+ " DeviceId:" + deviceId + "\n" + e.getMessage(), e);
				}
			}
			// delete old logs past retain days
			deleteOldLog(cal.getTime());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	private void aggregateLogDeviceDataByRecordDate_DeviceId(final Date recordDate, final Long deviceId) {
		// check if record already exits
		Date recordDateStart = DateUtils.truncate(recordDate, Calendar.DAY_OF_MONTH);
		Date recordDateEnd = DateUtils.addMilliseconds(DateUtils.addDays(recordDateStart, 1), -1);
		if (logDeviceHourlyService.listSizeByRecordDate_DeviceId(recordDateStart, recordDateEnd, deviceId) == 0) {
			// aggregate data
			logDeviceHourlyService.aggregateLogDeviceHourly(recordDateStart, recordDateEnd, deviceId);
			logDeviceDailyService.aggregateLogDeviceDaily(recordDateStart, recordDateEnd, deviceId);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	private void deleteOldLog(final Date startDate) {
		Date logCutOff = DateUtils.addDays(startDate, DEVICE_LOG_RETAIN_DAYS * -1);
		logDeviceService.deleteLog(logCutOff);
		Date logHourlyCutOff = DateUtils.addDays(startDate, DEVICE_LOG_HOURLY_RETAIN_DAYS * -1);
		logDeviceHourlyService.deleteLog(logHourlyCutOff);
	}
}
