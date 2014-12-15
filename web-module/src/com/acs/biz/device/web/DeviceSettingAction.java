/**
 *
 */
package com.acs.biz.device.web;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.acs.biz.device.entity.Device;
import com.acs.biz.device.entity.DeviceGroup;
import com.acs.biz.device.entity.DeviceSetting;
import com.acs.biz.device.service.DeviceGroupService;
import com.acs.biz.device.service.DeviceService;
import com.acs.biz.device.service.DeviceSettingService;
import com.acs.core.common.exception.CoreException;
import com.acs.core.common.web.AbstractAction;
import com.acs.core.menu.entity.Menu;
import com.acs.core.menu.service.MenuService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @author Eric
 */
public class DeviceSettingAction extends AbstractAction {

	private static final long serialVersionUID = 1967856891696456969L;

	@Resource
	private DeviceSettingService deviceSettingService;
	@Resource
	private DeviceService deviceService;
	@Resource
	private DeviceGroupService deviceGroupService;
	@Resource
	protected MenuService menuService;

	/* Page fields */
	private Map<String, DeviceGroup> groupMap;
	private Map<String, List<Device>> deviceMap;
	private Menu menuDeviceType;
	private Long paraDeviceId;
	private Long paraGroupId;
	private String paraJsonSetting;
	private int paraTemperature;
	private int paraHumidity;
	private int paraDefaultTemperature;
	private int paraDefaultHumidity;
	private String displayTtile;

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
		resetData();
	}

	@Override
	public void resetData() {
		paraDeviceId = null;
		paraGroupId = null;
		paraJsonSetting = null;
		DeviceSetting defaultSetting = deviceSettingService.getDefaultSetting();
		paraTemperature = (int) defaultSetting.getTemperature();
		paraDefaultTemperature = (int) defaultSetting.getTemperature();
		paraHumidity = (int) defaultSetting.getHumidity();
		paraDefaultHumidity = (int) defaultSetting.getHumidity();
		displayTtile = null;
	}

	public String init() {
		resetData();
		return "list";
	}

	public String listDevice() {
		try {
			Device device = deviceService.get(paraDeviceId);
			if (device == null)
				throw new CoreException("设备不存在." + paraDeviceId);
			displayTtile = "设备名称: " + device.getDeviceName() + "&nbsp;&nbsp;  设备类型:"
					+ menuDeviceType.getOptions().get(device.getDeviceType()).getName() + "&nbsp;&nbsp; 序列号:"
					+ device.getSerialNum();

			List<LinkedHashMap<String, String>> listSetting = deviceSettingService.getJsonSettingListForDevice(paraDeviceId);
			Gson gson = new GsonBuilder().create();
			paraJsonSetting = gson.toJson(listSetting);

			paraGroupId = null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("错误:" + e.getMessage());
		}
		return "list";
	}

	public String listGroup() {
		try {
			DeviceGroup deviceGroup = deviceGroupService.get(paraGroupId);
			if (deviceGroup == null)
				throw new CoreException("设备群组不存在." + paraGroupId);
			displayTtile = "群组名称: " + deviceGroup.getGroupName();

			List<LinkedHashMap<String, String>> listSetting = deviceSettingService.getJsonSettingListForGroup(paraGroupId);
			Gson gson = new GsonBuilder().create();
			paraJsonSetting = gson.toJson(listSetting);

			paraDeviceId = null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("错误:" + e.getMessage());
		}
		return "list";
	}

	public String save() {
		try {
			if (paraDeviceId != null && paraGroupId == null) {
				// save device setting
				Device device = deviceService.get(paraDeviceId);
				if (device == null)
					throw new CoreException("设备不存在." + paraDeviceId);

				// convert Json to hashmap
				Type typeOfHashMap = new TypeToken<List<LinkedHashMap<String, String>>>() {
				}.getType();
				Gson gson = new GsonBuilder().create();
				List<HashMap<String, String>> listSetting = gson.fromJson(paraJsonSetting, typeOfHashMap);

				// save the settings
				deviceSettingService.saveDeviceSettingFromMap(paraDeviceId, listSetting);
				addActionMessage("装置" + device.getDeviceName() + "设定保存成功");
			} else if (paraGroupId != null && paraDeviceId == null) {
				// save group setting
				DeviceGroup deviceGroup = deviceGroupService.get(paraGroupId);
				if (deviceGroup == null)
					throw new CoreException("设备群组不存在." + paraGroupId);

				// convert Json to hashmap
				Type typeOfHashMap = new TypeToken<List<LinkedHashMap<String, String>>>() {
				}.getType();
				Gson gson = new GsonBuilder().create();
				List<HashMap<String, String>> listSetting = gson.fromJson(paraJsonSetting, typeOfHashMap);

				// save the settings
				deviceSettingService.saveGroupSettingFromMap(paraGroupId, listSetting);
				addActionMessage("群组" + deviceGroup.getGroupName() + "设定保存成功");
			} else {
				addActionError("发生错误");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("错误:" + e.getMessage());
		}
		return "list";
	}

	public String saveDefault() {
		try {
			deviceSettingService.setDefaultTemperatureHumidity(paraDefaultTemperature, paraDefaultHumidity);
			addActionMessage("默认设定保存成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("错误:" + e.getMessage());
		}
		return "list";
	}

	public Long getParaGroupId() {
		return paraGroupId;
	}

	public void setParaGroupId(Long paraGroupId) {
		this.paraGroupId = paraGroupId;
	}

	public Map<String, DeviceGroup> getGroupMap() {
		return groupMap;
	}

	public Long getParaDeviceId() {
		return paraDeviceId;
	}

	public void setParaDeviceId(Long paraDeviceId) {
		this.paraDeviceId = paraDeviceId;
	}

	public Map<String, List<Device>> getDeviceMap() {
		return deviceMap;
	}

	public String getParaJsonSetting() {
		return paraJsonSetting;
	}

	public void setParaJsonSetting(String paraJsonSetting) {
		this.paraJsonSetting = paraJsonSetting;
	}

	public int getParaTemperature() {
		return paraTemperature;
	}

	public void setParaTemperature(int paraTemperature) {
		this.paraTemperature = paraTemperature;
	}

	public int getParaHumidity() {
		return paraHumidity;
	}

	public void setParaHumidity(int paraHumidity) {
		this.paraHumidity = paraHumidity;
	}

	public String getDisplayTtile() {
		return displayTtile;
	}

	public int getParaDefaultTemperature() {
		return paraDefaultTemperature;
	}

	public void setParaDefaultTemperature(int paraDefaultTemperature) {
		this.paraDefaultTemperature = paraDefaultTemperature;
	}

	public int getParaDefaultHumidity() {
		return paraDefaultHumidity;
	}

	public void setParaDefaultHumidity(int paraDefaultHumidity) {
		this.paraDefaultHumidity = paraDefaultHumidity;
	}

}
