/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.web.PermissionAction
   Module Description   :

   Date Created      : 2012/12/14
   Original Author   : tw4149
   Team              :
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.user.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.entity.SimplePager;
import com.acs.core.common.exception.CoreException;
import com.acs.core.user.entity.Permission;
import com.acs.core.user.service.PermissionService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author tw4149
 */
public class PermissionAction extends ActionSupport implements Preparable {

	/** serialVersionUID */
	private static final long serialVersionUID = -6944016521544553286L;
	private final Logger logger = LoggerFactory.getLogger(PermissionAction.class);
	@Resource
	private PermissionService permissionService;

	private Permission permission;

	private List<Permission> permissionList;

	private String paraId;
	private String paraKey;
	private String paraDesc;
	private List<String> paraTypes;
	private final Permission.Type[] typeMenu = Permission.Type.values();
	private SimplePager pager;

	/**
	 * @return the permission
	 */
	public Permission getPermission() {
		return permission;
	}

	/**
	 * @param permission the permission to set
	 */
	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	/**
	 * @return the paraId
	 */
	public String getParaId() {
		return paraId;
	}

	/**
	 * @param paraId the paraId to set
	 */
	public void setParaId(String paraId) {
		this.paraId = paraId;
	}

	/**
	 * @return the paraKey
	 */
	public String getParaKey() {
		return paraKey;
	}

	/**
	 * @param paraKey the paraKey to set
	 */
	public void setParaKey(String paraKey) {
		this.paraKey = paraKey;
	}

	/**
	 * @return the paraDesc
	 */
	public String getParaDesc() {
		return paraDesc;
	}

	/**
	 * @param paraDesc the paraDesc to set
	 */
	public void setParaDesc(String paraDesc) {
		this.paraDesc = paraDesc;
	}

	/**
	 * @return the paraTypes
	 */
	public List<String> getParaTypes() {
		return paraTypes;
	}

	/**
	 * @param paraTypes the paraTypes to set
	 */
	public void setParaTypes(List<String> paraTypes) {
		this.paraTypes = paraTypes;
	}

	/**
	 * @return the permissionList
	 */
	public List<Permission> getPermissionList() {
		return permissionList;
	}

	/**
	 * @return the typeMenu
	 */
	public Permission.Type[] getTypeMenu() {
		return typeMenu;
	}

	/**
	 * @return the pager
	 */
	public SimplePager getPager() {
		return pager;
	}

	/**
	 * @param pager the pager to set
	 */
	public void setPager(SimplePager pager) {
		this.pager = pager;
	}

	public String search() {
		CommonCriteria cri = new CommonCriteria();
		if (StringUtils.isNotEmpty(paraKey)) {
			cri.addRlike("key", paraKey);
		}
		if (StringUtils.isNotEmpty(paraDesc)) {
			cri.addRlike("description", paraDesc);
		}
		if (paraTypes != null && paraTypes.size() > 0) {
			List types = new ArrayList();
			for (String s : paraTypes) {
				types.add(Permission.Type.valueOf(s));
			}
			cri.addIn("type", types);
		}

		try {
			int entitySize = permissionService.getListSize(cri).intValue();
			pager.setTotalSize(entitySize);

			permissionList = permissionService.getList(pager.getCurrentPage() * pager.getPageRecord(), pager.getPageRecord(),
					cri, null);

			logger.info("pager:{},permissions:{}", pager, permissionList);
		} catch (CoreException e) {
			addActionError(e.getMessage());
			e.printStackTrace();
		}
		return "list";
	}

	public String view() {
		try {
			if (StringUtils.isNotBlank(paraId)) {
				permission = permissionService.get(paraId);
				if (permission == null) {
					addActionError("Permission not found");
					return "list";
				}
			} else {
				addActionError("Permission ID not found");
				return "list";
			}
		} catch (Exception e) {
			addActionError(e.getMessage());
			return "list";
		}
		return "view";
	}

	public String add() {
		permission = new Permission();
		return "edit";
	}

	public String save() {
		if (permission == null) {
			addActionError("Permission not found");
			return "list";
		}

		if (permission.getModifyDate() == null) {
			// 因為重新建立的 entity 才會 re-new name
			permission = new Permission(permission.getKey().toUpperCase(), permission.getDescription(), permission.getType());
		}
		try {
			permissionService.save(permission);
		} catch (Exception e) {
			addActionError(e.getMessage());
			return "edit";
		}
		return "view";
	}

	public String edit() {
		return "edit";
	}

	public String list() {
		return "list";
	}

	@Override
	public void prepare() throws Exception {
		clearErrorsAndMessages();
		if (paraTypes == null) {
			paraTypes = new ArrayList();
			for (Permission.Type t : typeMenu) {
				paraTypes.add(t.getCode());
			}
		}
		if (pager == null) {
			pager = new SimplePager();
		}
	}

}
