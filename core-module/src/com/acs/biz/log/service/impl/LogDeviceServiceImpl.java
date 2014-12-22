/**
 *
 */
package com.acs.biz.log.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.acs.biz.device.entity.Device;
import com.acs.biz.device.entity.DeviceStatus;
import com.acs.biz.device.service.DeviceSettingService;
import com.acs.biz.log.entity.LogDevice;
import com.acs.biz.log.service.LogDeviceService;
import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.service.impl.DomainServiceImpl;

/**
 * @author Eric
 */
public class LogDeviceServiceImpl extends DomainServiceImpl<LogDevice> implements LogDeviceService {

	@Resource
	private DeviceSettingService deviceSettingService;
	@Resource
	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Override
	@Transactional(readOnly = false)
	public LogDevice saveLog(DeviceStatus status) {
		Device device = status.getDevice();
		// build log data based on status
		LogDevice log = new LogDevice();
		log.setRecordDate(status.getStatusDate());
		log.setDeviceId(device.getOid());
		log.setGroupId(device.getGroupId());
		log.setTemperature(status.getTemperature());
		log.setHumidity(status.getHumidity());
		log.setTargetTemperature(status.getTargetTemperature());
		log.setTargetHumidity(status.getTargetHumidity());

		log = super.save(log);
		return log;
	}

	@Override
	public List<Map<String, Object>> listDistinctRecordDay_DeviceId(Date cutOffDate) {
		String sql = "select distinct STR_TO_DATE(DATE_FORMAT(record_date, '%Y-%m-%d'), '%Y-%m-%d') as record_date, device_id from acs_log_device where record_date < :cutOffDate";
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cutOffDate", cutOffDate);
		List<Map<String, Object>> result = npJdbcTemplate.queryForList(sql, paramMap);
		return result;
	}

	@Override
	@Transactional(readOnly = false)
	public int deleteLog(Date cutOffDate) {
		CommonCriteria crit = new CommonCriteria();
		crit.addLe("recordDate", cutOffDate);
		return super.getDao().deleteByAttributes(crit);
	}

	@Override
	public List<Map<String, Object>> listByDeviceId_RecordDate(Long deviceId, Date recordDateStart, Date recordDateEnd) {
		String sql = "select record_date, temperature, humidity from acs_log_device where device_id=:deviceId and record_date between :recordDateStart and :recordDateEnd order by record_date asc";
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceId", deviceId);
		paramMap.put("recordDateStart", recordDateStart);
		paramMap.put("recordDateEnd", recordDateEnd);
		List<Map<String, Object>> result = npJdbcTemplate.queryForList(sql, paramMap);
		return result;
	}

}
