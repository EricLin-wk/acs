/**
 *
 */
package com.acs.biz.device.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.acs.biz.device.entity.Device;
import com.acs.biz.device.entity.DeviceGroup;
import com.acs.biz.device.service.DeviceGroupService;
import com.acs.biz.device.service.DeviceService;
import com.acs.core.common.exception.CoreException;
import com.acs.core.common.web.AbstractAction;
import com.acs.core.menu.entity.Menu;
import com.acs.core.menu.service.MenuService;

/**
 * @author Eric
 */
public class DeviceAction extends AbstractAction {

	@Resource
	private DeviceService deviceService;
	@Resource
	private DeviceGroupService deviceGroupService;
	@Resource
	protected MenuService menuService;

	/* Page fields */
	private List<Device> objList;
	private Map<String, DeviceGroup> groupMap;
	private Device paraObj;
	private Long paraOid;
	private Menu menuDeviceType;
	private String paraDeviceType;
	private String paraSerialNum;
	private Long paraGroupId;

	@Override
	public void prepare() throws Exception {
		super.prepare();
		super.getPager().setPageRecord(20);
		if (menuDeviceType == null) {
			menuDeviceType = menuService.getClone(MenuService.MENU_DEVICE_TYPE);
		}
		groupMap = deviceGroupService.getMapOrdered();
	}

	@Override
	public void resetData() {
		objList.clear();
		paraObj = null;
	}

	public String init() {
		pager.setCurrentPage(0);
		return list();
	}

	public String list() {
		try {
			int totalSize = deviceService.listSizeByIsDelete_GroupId_DeviceType_SerialNum(false, paraGroupId, paraDeviceType,
					paraSerialNum);
			pager.setTotalSize(totalSize);
			objList = deviceService.listByIsDelete_GroupId_DeviceType_SerialNum(
					pager.getCurrentPage() * pager.getPageRecord(), pager.getPageRecord(), false, paraGroupId, paraDeviceType,
					paraSerialNum);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("错误:" + e.getMessage());
		}
		return "list";
	}

	public String add() {
		paraObj = new Device();
		return "edit";
	}

	public String view() {
		paraObj = deviceService.get(paraOid);
		if (paraObj == null) {
			addActionError("錯誤: 设备不存在. ID:" + paraOid);
			return "list";
		}

		return "view";
	}

	public String edit() {
		try {
			paraObj = deviceService.get(paraOid);
			if (paraObj == null) {
				addActionError("错误: 设备不存在. ID:" + paraOid);
				return "list";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("错误:" + e.getMessage());
		}
		return "edit";
	}

	public String save() {
		try {
			// validate
			boolean isValid = true;
			if (paraObj.getPort() < 0 || paraObj.getPort() > 65535) {
				addActionError("錯誤: 端口超出範圍(0~65535)");
				isValid = false;
			}
			boolean check = deviceService.isSerialNumUsed(paraObj.getSerialNum(), paraObj.getOid());
			if (check) {
				addActionError("錯誤: 序列号已被使用");
				isValid = false;
			}
			boolean check2 = deviceService.isDeviceNameUsed(paraObj.getDeviceName(), paraObj.getOid());
			if (check2) {
				addActionError("錯誤: 设备名称已被使用");
				isValid = false;
			}
			if (!isValid)
				return "edit";
			// copy field values for entity save
			Device entity = null;
			if (paraObj.getOid() != null) {
				entity = deviceService.get(paraObj.getOid());
				if (entity == null)
					throw new CoreException("OID:" + paraObj.getOid() + " not found");
			} else
				entity = new Device();
			BeanUtils.copyProperties(paraObj, entity, new String[] { "oid", "createUser", "createDate", "modifyUser",
					"modifyDate" });

			deviceService.save(entity);
			addActionMessage("設備" + entity.getDeviceName() + "保存成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("错误:" + e.getMessage());
			return "edit";
		}

		return list();
	}

	public String delete() {
		try {
			paraObj = deviceService.markDelete(paraOid);
			addActionMessage("设备" + paraObj.getSerialNum() + "刪除成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("错误:" + e.getMessage());
		}
		return "list";
	}

	public List<Device> getObjList() {
		return objList;
	}

	public Device getDevice() {
		return paraObj;
	}

	public Long getParaOid() {
		return paraOid;
	}

	public void setParaOid(Long paraOid) {
		this.paraOid = paraOid;
	}

	public String getParaDeviceType() {
		return paraDeviceType;
	}

	public void setParaDeviceType(String paraDeviceType) {
		this.paraDeviceType = paraDeviceType;
	}

	public String getParaSerialNum() {
		return paraSerialNum;
	}

	public void setParaSerialNum(String paraSerialNum) {
		this.paraSerialNum = paraSerialNum;
	}

	public Menu getMenuDeviceType() {
		return menuDeviceType;
	}

	public Device getParaObj() {
		return paraObj;
	}

	public void setParaObj(Device paraObj) {
		this.paraObj = paraObj;
	}

	public Map<String, DeviceGroup> getGroupMap() {
		return groupMap;
	}

	public Long getParaGroupId() {
		return paraGroupId;
	}

	public void setParaGroupId(Long paraGroupId) {
		this.paraGroupId = paraGroupId;
	}

}
