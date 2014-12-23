/**
 *
 */
package com.acs.biz.report.web;

import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.acs.biz.device.entity.Device;
import com.acs.biz.device.entity.DeviceGroup;
import com.acs.biz.device.service.DeviceGroupService;
import com.acs.biz.device.service.DeviceService;
import com.acs.biz.log.service.LogDeviceHourlyService;
import com.acs.core.common.web.AbstractAction;
import com.acs.core.menu.entity.Menu;
import com.acs.core.menu.service.MenuService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Eric
 */
public class DeviceHourlyReportAction extends AbstractAction {

	private static final long serialVersionUID = -4113068488518488248L;

	@Resource
	private DeviceService deviceService;
	@Resource
	private DeviceGroupService deviceGroupService;
	@Resource
	private MenuService menuService;
	@Resource
	private LogDeviceHourlyService logDeviceHourlyService;

	/* Page fields */
	private Map<String, DeviceGroup> groupMap;
	private Map<String, List<Device>> deviceMap;
	private Menu menuDeviceType;
	private ByteArrayInputStream inputStream;
	private Date recordDateStart;
	private Date recordDateEnd;
	private Device deviceObj;

	private Long paraDeviceId;

	@Override
	public void prepare() throws Exception {
		super.prepare();
		groupMap = deviceGroupService.getMapOrdered();
		deviceMap = new LinkedHashMap<String, List<Device>>();
		List<Device> noGroupList = deviceService.listByIsDelete_GroupId(0, -1, false, null);
		deviceMap.put("无群组", noGroupList);
		for (DeviceGroup group : groupMap.values()) {
			List<Device> list = deviceService.listByIsDelete_GroupId(0, -1, false, group.getOid());
			deviceMap.put(group.getGroupName(), list);
		}
		if (menuDeviceType == null) {
			menuDeviceType = menuService.getClone(MenuService.MENU_DEVICE_TYPE);
		}
	}

	@Override
	public void resetData() {
		paraDeviceId = null;

	}

	public String init() {
		resetData();
		return "list";
	}

	public String list() {
		setRecordDate();
		deviceObj = deviceService.get(paraDeviceId);
		if (deviceObj == null)
			addActionError("错误: 装置不存在. " + paraDeviceId);

		return "list";
	}

	public String jsonData() {
		setRecordDate();
		List<Map<String, Object>> result = logDeviceHourlyService.listByDeviceId_RecordDate(paraDeviceId, recordDateStart,
				recordDateEnd);
		Gson gson = new GsonBuilder().create();
		String jsonStr = gson.toJson(result);

		inputStream = new ByteArrayInputStream(jsonStr.getBytes());
		return "jsonData";
	}

	private void setRecordDate() {
		Calendar cal = Calendar.getInstance();
		recordDateEnd = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, -60);
		recordDateStart = cal.getTime();
	}

	public Long getParaDeviceId() {
		return paraDeviceId;
	}

	public void setParaDeviceId(Long paraDeviceId) {
		this.paraDeviceId = paraDeviceId;
	}

	public Map<String, DeviceGroup> getGroupMap() {
		return groupMap;
	}

	public Map<String, List<Device>> getDeviceMap() {
		return deviceMap;
	}

	public Menu getMenuDeviceType() {
		return menuDeviceType;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public Date getRecordDateStart() {
		return recordDateStart;
	}

	public Date getRecordDateEnd() {
		return recordDateEnd;
	}

	public Device getDeviceObj() {
		return deviceObj;
	}

}
