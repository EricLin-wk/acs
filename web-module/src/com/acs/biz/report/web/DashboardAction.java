/**
 *
 */
package com.acs.biz.report.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.acs.biz.device.entity.Device;
import com.acs.biz.device.entity.DeviceGroup;
import com.acs.biz.device.entity.DeviceStatus;
import com.acs.biz.device.service.DeviceGroupService;
import com.acs.biz.device.service.DeviceHandler;
import com.acs.biz.device.service.DeviceService;
import com.acs.biz.log.service.LogDeviceService;
import com.acs.core.common.web.AbstractAction;
import com.acs.core.menu.entity.Menu;
import com.acs.core.menu.service.MenuService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Eric
 */
public class DashboardAction extends AbstractAction {

	private static final long serialVersionUID = 1325243761483327144L;

	@Resource
	private DeviceService deviceService;
	@Resource
	private DeviceGroupService deviceGroupService;
	@Resource
	protected MenuService menuService;
	@Resource
	protected DeviceHandler deviceHandler;
	@Resource
	private LogDeviceService logDeviceService;

	/* Page fields */
	private Menu menuDeviceType;
	private LinkedHashMap<DeviceGroup, List<Device>> deviceMap;
	private Hashtable<Long, DeviceStatus> statusMap;
	private String jsonData;
	private String seriesDataTemperature;
	private String seriesDataHumidity;

	@Override
	public void prepare() throws Exception {
		super.prepare();
		if (menuDeviceType == null) {
			menuDeviceType = menuService.getClone(MenuService.MENU_DEVICE_TYPE);
		}

		if (deviceMap == null) {
			deviceMap = new LinkedHashMap<DeviceGroup, List<Device>>();
			// insert device with no group
			List<Device> devList = deviceService.listByIsDelete_GroupId(0, -1, false, null);
			if (devList.size() > 0) {
				DeviceGroup devGroup = new DeviceGroup();
				devGroup.setGroupName("无群组");
				deviceMap.put(devGroup, devList);
			}
			// insert device for each group
			for (DeviceGroup group : deviceGroupService.listAllOrdered()) {
				List<Device> devList2 = deviceService.listByIsDelete_GroupId(0, -1, false, group.getOid());
				if (devList2.size() > 0)
					deviceMap.put(group, devList2);
			}
		}
	}

	@Override
	public void resetData() {

	}

	public String list() {
		try {
			deviceHandler.rereshStatus();
			statusMap = deviceHandler.getStatusMap();
			// load graph data for past 30 min
			Calendar cal = Calendar.getInstance();
			Date endDate = cal.getTime();
			cal.add(Calendar.MINUTE, -30);
			Date startDate = cal.getTime();

			// endDate = DateUtils.parseDate("2014-12-01 00:30:00", "yyyy-MM-dd HH:mm:ss");
			// startDate = DateUtils.parseDate("2014-12-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
			TreeMap<Date, TreeMap<String, Object>> temperatureDataMap = new TreeMap<Date, TreeMap<String, Object>>();
			ArrayList<LinkedHashMap<String, String>> seriesListT = new ArrayList<LinkedHashMap<String, String>>();
			ArrayList<LinkedHashMap<String, String>> seriesListH = new ArrayList<LinkedHashMap<String, String>>();

			for (List<Device> devList : deviceMap.values()) {
				for (Device device : devList) {
					// get logs in date range
					List<Map<String, Object>> logList = logDeviceService.listByDeviceId_RecordDate(device.getOid(), startDate,
							endDate);
					for (Map<String, Object> rowMap : logList) {
						Date recordDate = (Date) rowMap.get("record_date");
						Double temperature = (Double) rowMap.get("temperature");
						Double humidity = (Double) rowMap.get("humidity");
						TreeMap<String, Object> jsonMap = temperatureDataMap.get(recordDate);
						if (jsonMap == null) {
							jsonMap = new TreeMap<String, Object>();
							jsonMap.put("recordDate", recordDate);
							temperatureDataMap.put(recordDate, jsonMap);
						}
						jsonMap.put("Dev" + device.getOid() + "T", temperature);
						jsonMap.put("Dev" + device.getOid() + "H", humidity);
					}
					// create series config
					if (logList.size() > 0) {
						LinkedHashMap<String, String> seriesMap = new LinkedHashMap<String, String>();
						seriesMap.put("dataField", "Dev" + device.getOid() + "T");
						seriesMap.put("displayText", device.getDeviceName());
						seriesMap.put("emptyPointsDisplay", "connect");
						seriesListT.add(seriesMap);

						seriesMap = new LinkedHashMap<String, String>();
						seriesMap.put("dataField", "Dev" + device.getOid() + "H");
						seriesMap.put("displayText", device.getDeviceName());
						seriesMap.put("emptyPointsDisplay", "connect");
						seriesListH.add(seriesMap);
					}
				}
			}
			Gson gson = new GsonBuilder().create();
			jsonData = gson.toJson(temperatureDataMap.values());
			seriesDataTemperature = gson.toJson(seriesListT);
			seriesDataHumidity = gson.toJson(seriesListH);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("错误:" + e.getMessage());
		}
		return "list";
	}

	public Menu getMenuDeviceType() {
		return menuDeviceType;
	}

	public LinkedHashMap<DeviceGroup, List<Device>> getDeviceMap() {
		return deviceMap;
	}

	public Hashtable<Long, DeviceStatus> getStatusMap() {
		return statusMap;
	}

	public String getJsonData() {
		return jsonData;
	}

	public String getSeriesDataTemperature() {
		return seriesDataTemperature;
	}

	public String getSeriesDataHumidity() {
		return seriesDataHumidity;
	}

}
