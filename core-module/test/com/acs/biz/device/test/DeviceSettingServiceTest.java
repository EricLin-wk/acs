/**
 *
 */
package com.acs.biz.device.test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.acs.biz.device.entity.DeviceSetting;
import com.acs.biz.device.service.DeviceSettingService;
import com.acs.core.common.utils.SpringCommonTest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @author Eric
 */
public class DeviceSettingServiceTest extends SpringCommonTest {

	private static DeviceSettingService deviceSettingService;

	private static final double DELTA = 1e-10;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		configCtx();
		deviceSettingService = (DeviceSettingService) ctx.getBean("deviceSettingService");
	}

	@Test
	public void setDefaultTemperatureHumidity() {
		double temperature = 24;
		double humidity = 55;
		deviceSettingService.setDefaultTemperatureHumidity(temperature, humidity);

		DeviceSetting setting = deviceSettingService.getDefaultSetting();
		Assert.assertEquals(temperature, setting.getTemperature(), DELTA);
		Assert.assertEquals(humidity, setting.getHumidity(), DELTA);
	}

	@Test
	public void testToJson() {
		List<LinkedHashMap<String, String>> timeList = new ArrayList<LinkedHashMap<String, String>>();
		Random ran = new Random(System.currentTimeMillis());
		for (int i = 0; i < 24; i++) {
			LinkedHashMap<String, String> dayMap = new LinkedHashMap<String, String>();

			String timeOfDay = String.format("%04d", i * 100);
			dayMap.put("timeOfDay", timeOfDay);
			int temp = ran.nextInt(35);
			int humid = ran.nextInt(100);
			dayMap.put("sun", temp + "°C " + humid + "%");
			dayMap.put("mon", temp + "°C " + humid + "%");
			dayMap.put("tue", temp + "°C " + humid + "%");
			temp = ran.nextInt(35);
			humid = ran.nextInt(100);
			dayMap.put("wed", temp + "°C " + humid + "%");
			dayMap.put("thu", temp + "°C " + humid + "%");
			dayMap.put("fri", temp + "°C " + humid + "%");
			dayMap.put("sat", temp + "°C " + humid + "%");

			timeList.add(dayMap);
		}
		logger.debug("timeList:" + timeList);
		Gson gson = new GsonBuilder().create();
		logger.debug("json:" + gson.toJson(timeList));
	}

	@Test
	public void testFromJson() {
		String json = "[{'timeOfDay':'0000','sun':'22°C 85%','mon':'22°C 85%','tue':'22°C 85%','wed':'9°C 7%','thu':'9°C 7%','fri':'9°C 7%','sat':'9°C 7%','uid':'0000'},{'timeOfDay':'0100','sun':'4°C 78%','mon':'4°C 78%','tue':'4°C 78%','wed':'7°C 99%','thu':'7°C 99%','fri':'7°C 99%','sat':'7°C 99%','uid':'0100'},{'timeOfDay':'0200','sun':'1°C 21%','mon':'1°C 21%','tue':'1°C 21%','wed':'31°C 35%','thu':'31°C 35%','fri':'31°C 35%','sat':'31°C 35%','uid':'0200'},{'timeOfDay':'0300','sun':'33°C 11%','mon':'33°C 11%','tue':'33°C 11%','wed':'28°C 4%','thu':'28°C 4%','fri':'28°C 4%','sat':'28°C 4%','uid':'0300'},{'timeOfDay':'0400','sun':'30°C 41%','mon':'30°C 41%','tue':'30°C 41%','wed':'17°C 89%','thu':'17°C 89%','fri':'17°C 89%','sat':'17°C 89%','uid':'0400'},{'timeOfDay':'0500','sun':'32°C 74%','mon':'32°C 74%','tue':'32°C 74%','wed':'11°C 44%','thu':'11°C 44%','fri':'11°C 44%','sat':'11°C 44%','uid':'0500'},{'timeOfDay':'0600','sun':'8°C 94%','mon':'8°C 94%','tue':'8°C 94%','wed':'27°C 92%','thu':'27°C 92%','fri':'27°C 92%','sat':'27°C 92%','uid':'0600'},{'timeOfDay':'0700','sun':'1°C 49%','mon':'1°C 49%','tue':'1°C 49%','wed':'25°C 89%','thu':'25°C 89%','fri':'25°C 89%','sat':'25°C 89%','uid':'0700'},{'timeOfDay':'0800','sun':'11°C 78%','mon':'11°C 78%','tue':'11°C 78%','wed':'3°C 76%','thu':'3°C 76%','fri':'3°C 76%','sat':'3°C 76%','uid':'0800'},{'timeOfDay':'0900','sun':'20°C 23%','mon':'20°C 23%','tue':'20°C 23%','wed':'15°C 51%','thu':'15°C 51%','fri':'15°C 51%','sat':'15°C 51%','uid':'0900'},{'timeOfDay':'1000','sun':'24°C 58%','mon':'24°C 58%','tue':'24°C 58%','wed':'29°C 89%','thu':'29°C 89%','fri':'29°C 89%','sat':'29°C 89%','uid':'1000'},{'timeOfDay':'1100','sun':'29°C 17%','mon':'29°C 17%','tue':'29°C 17%','wed':'23°C 11%','thu':'23°C 11%','fri':'23°C 11%','sat':'23°C 11%','uid':'1100'},{'timeOfDay':'1200','sun':'15°C 46%','mon':'15°C 46%','tue':'15°C 46%','wed':'2°C 20%','thu':'2°C 20%','fri':'2°C 20%','sat':'2°C 20%','uid':'1200'},{'timeOfDay':'1300','sun':'24°C 27%','mon':'24°C 27%','tue':'24°C 27%','wed':'28°C 3%','thu':'28°C 3%','fri':'28°C 3%','sat':'28°C 3%','uid':'1300'},{'timeOfDay':'1400','sun':'26°C 94%','mon':'26°C 94%','tue':'26°C 94%','wed':'19°C 26%','thu':'19°C 26%','fri':'19°C 26%','sat':'19°C 26%','uid':'1400'},{'timeOfDay':'1500','sun':'2°C 17%','mon':'2°C 17%','tue':'2°C 17%','wed':'13°C 81%','thu':'13°C 81%','fri':'13°C 81%','sat':'13°C 81%','uid':'1500'},{'timeOfDay':'1600','sun':'6°C 49%','mon':'6°C 49%','tue':'6°C 49%','wed':'34°C 66%','thu':'34°C 66%','fri':'34°C 66%','sat':'34°C 66%','uid':'1600'},{'timeOfDay':'1700','sun':'11°C 48%','mon':'11°C 48%','tue':'11°C 48%','wed':'13°C 49%','thu':'13°C 49%','fri':'13°C 49%','sat':'13°C 49%','uid':'1700'},{'timeOfDay':'1800','sun':'0°C 5%','mon':'0°C 5%','tue':'0°C 5%','wed':'12°C 77%','thu':'12°C 77%','fri':'12°C 77%','sat':'12°C 77%','uid':'1800'},{'timeOfDay':'1900','sun':'27°C 51%','mon':'27°C 51%','tue':'27°C 51%','wed':'16°C 62%','thu':'16°C 62%','fri':'16°C 62%','sat':'16°C 62%','uid':'1900'},{'timeOfDay':'2000','sun':'24°C 75%','mon':'24°C 75%','tue':'24°C 75%','wed':'13°C 9%','thu':'13°C 9%','fri':'13°C 9%','sat':'13°C 9%','uid':'2000'},{'timeOfDay':'2100','sun':'34°C 93%','mon':'34°C 93%','tue':'34°C 93%','wed':'19°C 73%','thu':'19°C 73%','fri':'19°C 73%','sat':'19°C 73%','uid':'2100'},{'timeOfDay':'2200','sun':'27°C 98%','mon':'27°C 98%','tue':'27°C 98%','wed':'16°C 72%','thu':'16°C 72%','fri':'16°C 72%','sat':'16°C 72%','uid':'2200'},{'timeOfDay':'2300','sun':'33°C 87%','mon':'33°C 87%','tue':'33°C 87%','wed':'19°C 31%','thu':'19°C 31%','fri':'19°C 31%','sat':'19°C 31%','uid':'2300'}]";
		Type typeOfHashMap = new TypeToken<List<LinkedHashMap<String, String>>>() {
		}.getType();
		Gson gson = new GsonBuilder().create();
		List<HashMap<String, String>> timeList = gson.fromJson(json, typeOfHashMap);
		logger.debug("timeList:" + timeList);
	}

	@Test
	public void getJsonSettingListForDevice() {
		Long deviceId = (long) 1;
		List<LinkedHashMap<String, String>> list = deviceSettingService.getJsonSettingListForDevice(deviceId);
		logger.debug("list:" + list);
	}

	@Test
	public void getJsonSettingListForGroup() {
		Long groupId = (long) 2;
		List<LinkedHashMap<String, String>> list = deviceSettingService.getJsonSettingListForGroup(groupId);
		logger.debug("list:" + list);
	}
}
