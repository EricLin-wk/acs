/**
 *
 */
package com.acs.biz.log.service.impl;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.acs.biz.log.entity.LogDeviceHourly;
import com.acs.biz.log.service.LogDeviceHourlyService;
import com.acs.biz.log.service.LogDeviceService;
import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.service.impl.DomainServiceImpl;

/**
 * @author Eric
 */
public class LogDeviceHourlyServiceImpl extends DomainServiceImpl<LogDeviceHourly> implements LogDeviceHourlyService {

	@Resource
	private LogDeviceService logDeviceService;
	@Resource
	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Override
	@Transactional(readOnly = false)
	public void aggregateLogDeviceHourly(Date recordDateStart, Date recordDateEnd, Long deviceId) {
		// aggregate from LogDevice table and insert into LogDeviceHourly table
		String sql = "insert into acs_log_device_hourly (record_date, device_id, group_id, temperature, humidity, "
				+ "target_temperature, target_humidity, max_temperature, max_humidity, min_temperature, min_humidity, "
				+ "add_user, mod_user, add_date, mod_date) "
				+ "select DATE_FORMAT(a.record_date, '%Y-%m-%d %H:00:00') as record_date, a.device_id, max(a.group_id) as group_id, "
				+ "avg(a.temperature) as temperature, avg(a.humidity) as humidity, avg(a.target_temperature) as target_temperature, "
				+ "avg(a.target_humidity) as target_humidity, max(a.temperature) as max_temperature, max(a.humidity) as max_humidity, "
				+ "min(a.temperature) as min_temperature, min(a.humidity) as min_humidity, 'sys' as add_user, 'sys' as mod_user, "
				+ "NOW() as add_date, NOW() as mod_date from acs_log_device as a "
				+ "where a.record_date between :recordDateStart and :recordDateEnd and a.device_id = :deviceId "
				+ "group by DATE_FORMAT(a.record_date, '%Y-%m-%d %H:00:00'), a.device_id";
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("recordDateStart", recordDateStart);
		paramMap.put("recordDateEnd", recordDateEnd);
		paramMap.put("deviceId", deviceId);
		npJdbcTemplate.update(sql, paramMap);
	}

	@Override
	@Transactional(readOnly = false)
	public int deleteLog(Date cutOffDate) {
		CommonCriteria crit = new CommonCriteria();
		crit.addLe("recordDate", cutOffDate);
		return super.getDao().deleteByAttributes(crit);
	}

	@Override
	public int listSizeByRecordDate_DeviceId(Date recordDateStart, Date recordDateEnd, Long deviceId) {
		CommonCriteria crit = new CommonCriteria();
		crit.addGe("recordDate", recordDateStart);
		crit.addLe("recordDate", recordDateEnd);
		crit.addEq("deviceId", deviceId);
		return super.getListSize(crit).intValue();
	}

}
