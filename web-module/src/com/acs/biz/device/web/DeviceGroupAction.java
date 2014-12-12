/**
 *
 */
package com.acs.biz.device.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.acs.biz.device.entity.DeviceGroup;
import com.acs.biz.device.service.DeviceGroupService;
import com.acs.core.common.exception.CoreException;
import com.acs.core.common.web.AbstractAction;

/**
 * @author Eric
 */
public class DeviceGroupAction extends AbstractAction {

	@Resource
	private DeviceGroupService deviceGroupService;

	/* Page fields */
	private List<DeviceGroup> objList;
	private DeviceGroup paraObj;
	private Long paraOid;

	@Override
	public void prepare() throws Exception {
		super.prepare();
	}

	@Override
	public void resetData() {
		paraObj = null;
	}

	public String list() {
		try {
			objList = deviceGroupService.listAllOrdered();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("错误:" + e.getMessage());
		}
		return "list";
	}

	public String add() {
		paraObj = new DeviceGroup();
		return "edit";
	}

	public String view() {
		paraObj = deviceGroupService.get(paraOid);
		if (paraObj == null) {
			addActionError("錯誤: 设备群组不存在. ID:" + paraOid);
			return "list";
		}

		return "view";
	}

	public String edit() {
		try {
			paraObj = deviceGroupService.get(paraOid);
			if (paraObj == null) {
				addActionError("错误: 设备群组不存在. ID:" + paraOid);
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
			boolean check = deviceGroupService.isGroupNameUsed(paraObj.getGroupName(), paraObj.getOid());
			if (check) {
				addActionError("错误: 群组名称已被使用");
				isValid = false;
			}
			if (!isValid)
				return "edit";
			// copy field values for entity save
			DeviceGroup entity = null;
			if (paraObj.getOid() != null) {
				entity = deviceGroupService.get(paraObj.getOid());
				if (entity == null)
					throw new CoreException("OID:" + paraObj.getOid() + " not found");
			} else
				entity = new DeviceGroup();
			BeanUtils.copyProperties(paraObj, entity, new String[] { "oid", "createUser", "createDate", "modifyUser",
			"modifyDate" });

			deviceGroupService.save(entity);
			addActionMessage("設備群组" + entity.getGroupName() + "保存成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("错误:" + e.getMessage());
			return "edit";
		}

		return list();
	}

	public String delete() {
		try {
			paraObj = deviceGroupService.get(paraOid);
			if (paraObj == null) {
				addActionError("错误: 设备群组不存在. ID:" + paraOid);
				return "list";
			}
			deviceGroupService.delete(paraObj);
			addActionMessage("设备群组" + paraObj.getGroupName() + "刪除成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("错误:" + e.getMessage());
		}
		return list();
	}

	public DeviceGroup getParaObj() {
		return paraObj;
	}

	public void setParaObj(DeviceGroup paraObj) {
		this.paraObj = paraObj;
	}

	public Long getParaOid() {
		return paraOid;
	}

	public void setParaOid(Long paraOid) {
		this.paraOid = paraOid;
	}

	public List<DeviceGroup> getObjList() {
		return objList;
	}

}
