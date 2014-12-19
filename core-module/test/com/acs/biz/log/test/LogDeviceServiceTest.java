package com.acs.biz.log.test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import com.acs.biz.device.entity.Device;
import com.acs.biz.device.entity.DeviceStatus;
import com.acs.biz.device.service.DeviceService;
import com.acs.biz.log.service.LogDeviceService;
import com.acs.core.common.utils.SpringCommonTest;

public class LogDeviceServiceTest extends SpringCommonTest {

	private static LogDeviceService logDeviceService;
	private static DeviceService deviceService;
	private static SessionFactory sessionFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		configCtx();
		logDeviceService = (LogDeviceService) ctx.getBean("logDeviceService");
		deviceService = (DeviceService) ctx.getBean("deviceService");
		sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
	}

	@Test
	public void testHQL() {
		String hql = "select 1 from LogDeviceHourly where EXISTS(SELECT 1 FROM LogDeviceHourly WHERE deviceId=3)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List list = query.list();
		logger.debug("list:" + list);

	}

	@Test
	public void generateTestData() throws ParseException {
		Random rand = new Random(System.currentTimeMillis());
		double temperature = 20;
		double humidity = 50;
		int intervalMinute = 5;

		// generate for all active devices
		List<Device> devList = deviceService.listActiveDevices(0, -1);
		for (Device device : devList) {
			Calendar startCal = Calendar.getInstance();
			startCal.setTime(DateUtils.parseDate("2014-12-01 00:00", "yyyy-MM-dd HH:mm"));
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(DateUtils.parseDate("2014-12-07 23:59", "yyyy-MM-dd HH:mm"));

			// iterate over interval time
			while (startCal.compareTo(endCal) < 0) {
				// randomize temperature & humidity
				if (rand.nextDouble() > 0.5)
					temperature += rand.nextDouble();
				else
					temperature -= rand.nextDouble();
				if (rand.nextDouble() > 0.5)
					humidity += (1 + rand.nextInt(2));
				else
					humidity -= (1 + rand.nextInt(2));

				DeviceStatus status = new DeviceStatus();
				status.setDevice(device);
				status.setStatusDate(startCal.getTime());
				status.setTemperature(temperature);
				status.setHumidity(humidity);
				// save to log
				logDeviceService.saveLog(status);

				startCal.add(Calendar.MINUTE, intervalMinute);
			}
		}
	}

}