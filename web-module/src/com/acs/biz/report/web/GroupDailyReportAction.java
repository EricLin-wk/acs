/**
 *
 */
package com.acs.biz.report.web;

import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.acs.biz.device.entity.DeviceGroup;
import com.acs.biz.device.service.DeviceGroupService;
import com.acs.biz.log.service.LogDeviceDailyService;
import com.acs.core.common.web.AbstractAction;
import com.acs.core.menu.entity.Menu;
import com.acs.core.menu.service.MenuService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Eric
 */
public class GroupDailyReportAction extends AbstractAction {

	private static final long serialVersionUID = -4113068488518488248L;

	@Resource
	private DeviceGroupService deviceGroupService;
	@Resource
	private MenuService menuService;
	@Resource
	private LogDeviceDailyService logDeviceDailyService;

	/* Page fields */
	private Map<String, DeviceGroup> groupMap;
	private Menu menuDeviceType;
	private ByteArrayInputStream inputStream;
	private Date recordDateStart;
	private Date recordDateEnd;
	private DeviceGroup groupObj;

	private Long paraGroupId;

	@Override
	public void prepare() throws Exception {
		super.prepare();
		groupMap = deviceGroupService.getMapOrdered();
		if (menuDeviceType == null) {
			menuDeviceType = menuService.getClone(MenuService.MENU_DEVICE_TYPE);
		}
	}

	@Override
	public void resetData() {
		paraGroupId = null;

	}

	public String init() {
		resetData();
		setRecordDate();
		return "list";
	}

	public String list() {
		setRecordDate();
		if (paraGroupId == -1) {
			groupObj = new DeviceGroup();
			groupObj.setGroupName("无群组设备");
		} else
			groupObj = deviceGroupService.get(paraGroupId);
		if (groupObj == null)
			addActionError("错误: 设备群组不存在. " + paraGroupId);

		return "list";
	}

	public String jsonData() {
		setRecordDate();
		if (paraGroupId == null) {
			inputStream = new ByteArrayInputStream(new byte[0]);
			return "jsonData";
		}
		Long groupId = paraGroupId;
		if (groupId == -1L) // -1 is equivalent to no groups
			groupId = null;
		List<Map<String, Object>> result = logDeviceDailyService.listGroupByGroupId_RecordDate(groupId, recordDateStart,
				recordDateEnd);
		Gson gson = new GsonBuilder().create();
		String jsonStr = gson.toJson(result);

		inputStream = new ByteArrayInputStream(jsonStr.getBytes());
		return "jsonData";
	}

	private void setRecordDate() {
		Calendar cal = Calendar.getInstance();
		recordDateEnd = cal.getTime();
		cal.add(Calendar.YEAR, -5);
		recordDateStart = cal.getTime();
	}

	public Map<String, DeviceGroup> getGroupMap() {
		return groupMap;
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

	public Long getParaGroupId() {
		return paraGroupId;
	}

	public void setParaGroupId(Long paraGroupId) {
		this.paraGroupId = paraGroupId;
	}

	public DeviceGroup getGroupObj() {
		return groupObj;
	}

}
