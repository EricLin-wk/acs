/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.web.RoleAction
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.entity.SimplePager;
import com.acs.core.common.exception.CoreException;
import com.acs.core.menu.entity.Menu;
import com.acs.core.menu.service.MenuService;
import com.acs.core.user.entity.Permission;
import com.acs.core.user.entity.Role;
import com.acs.core.user.entity.User;
import com.acs.core.user.service.PermissionService;
import com.acs.core.user.service.RoleService;
import com.acs.core.user.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author tw4149
 */
public class RoleAction extends ActionSupport implements Preparable {
	/** serialVersionUID */
	private static final long serialVersionUID = 643190658632806874L;
	private final Logger logger = LoggerFactory.getLogger(RoleAction.class);
	@Resource
	private RoleService roleService;
	@Resource
	private PermissionService permissionService;
	@Resource
	private UserService userService;
	@Resource(name = "menuService")
	private MenuService menuService;
	private Role obj;
	private String permissionName;
	private String key;
	private String roleType;
	private List<Role> objList;
	private String paraId;
	private String paraKey;
	private String paraDesc;
	private List<String> paraTypes;
	private final Role.Type[] typeMenu = Role.Type.values();
	private Map<String, Permission> averablePermission;
	private Menu roleTypeMenu;
	private SimplePager pager;

	/**
	 * @return the obj
	 */
	public Role getObj() {
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void setObj(Role obj) {
		this.obj = obj;
	}

	/**
	 * @return the permissionName
	 */
	public String getPermissionName() {
		return permissionName;
	}

	/**
	 * @param permissionName the permissionName to set
	 */
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the objList
	 */
	public List<Role> getObjList() {
		return objList;
	}

	/**
	 * @param objList the objList to set
	 */
	public void setObjList(List<Role> objList) {
		this.objList = objList;
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
	 * @return the averablePermission
	 */
	public Map<String, Permission> getAverablePermission() {
		return averablePermission;
	}

	/**
	 * @param averablePermission the averablePermission to set
	 */
	public void setAverablePermission(Map<String, Permission> averablePermission) {
		this.averablePermission = averablePermission;
	}

	/**
	 * @return the roleTypeMenu
	 */
	public Menu getRoleTypeMenu() {
		return roleTypeMenu;
	}

	/**
	 * @param roleTypeMenu the roleTypeMenu to set
	 */
	public void setRoleTypeMenu(Menu roleTypeMenu) {
		this.roleTypeMenu = roleTypeMenu;
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

	/**
	 * @return the typeMenu
	 */
	public Role.Type[] getTypeMenu() {
		return typeMenu;
	}

	public String search() {
		CommonCriteria cri = new CommonCriteria();
		if (StringUtils.isNotEmpty(paraKey)) {
			paraKey = paraKey.toUpperCase();
			cri.addRlike("key", paraKey);
		}
		if (StringUtils.isNotEmpty(paraDesc)) {
			cri.addRlike("description", paraDesc);
		}
		if (paraTypes != null && paraTypes.size() > 0) {
			List types = new ArrayList();
			for (String s : paraTypes) {
				types.add(Role.Type.valueOf(s));
			}
			cri.addIn("type", types);
		}

		try {
			int entitySize = roleService.getListSize(cri).intValue();
			pager.setTotalSize(entitySize);

			objList = roleService.getList(pager.getCurrentPage() * pager.getPageRecord(), pager.getPageRecord(), cri,
					new String[] { "name asc" });

			logger.info("pager:{},objs:{}", pager, roleService);
		} catch (CoreException e) {
			addActionError(e.getMessage());
			e.printStackTrace();
		}
		return "list";
	}

	public String view() {
		try {
			if (StringUtils.isNotBlank(paraId)) {
				obj = roleService.get(paraId);
				if (obj == null) {
					addActionError("Role not found");
					return "list";
				} else {
					HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
					List users = userService.getByRole(obj.getKey(), obj.getType());
					request.setAttribute("assignUsers", users);
					logger.debug("assignUsers, size: {}", users.size());
				}
			} else {
				addActionError("Role ID not found");
				return "list";
			}
		} catch (Exception e) {
			addActionError(e.getMessage());
			return "list";
		}
		return "view";
	}

	public String add() {
		obj = new Role();
		return "edit";
	}

	public String addPermission() {
		if (obj == null) {
			addActionError("Role not found");
			return "list";
		}
		if (StringUtils.isNotBlank(permissionName)) {
			if (!obj.getPermissions().containsKey(permissionName)) {
				obj.getPermissions().put(permissionName, averablePermission.get(permissionName));
			}
		}
		return "edit";
	}

	public String removePermission() {
		if (obj == null) {
			addActionError("Role not found");
			return "list";
		}
		if (StringUtils.isNotBlank(permissionName)) {
			if (obj.getPermissions().containsKey(permissionName)) {
				Permission p = obj.getPermissions().get(permissionName);
				if ((Permission.Type.OTHER.equals(p.getType()) || Permission.Type.SERVICE.equals(p.getType())
						|| Permission.Type.SYSTEM.equals(p.getType()) || Permission.Type.ROLE.equals(p.getType()))
						&& !obj.getMainPermission().getName().equals(permissionName)) {
					obj.getPermissions().remove(permissionName);
				} else {
					addActionError("不可移除 Permission : " + permissionName);
				}
			}
		}
		return "edit";
	}

	public String save() {
		if (obj == null) {
			addActionError("Role not found");
			return "list";
		}
		if (obj.getModifyDate() == null) {
			if (StringUtils.isBlank(roleType)) {
				addActionError("请选择一种角色类型！");
				return "edit";
			}
			// 因為重新建立的 entity 才會 re-new name
			Role newRole = new Role(key.toUpperCase(), obj.getDescription(), Role.Type.valueOf(roleType));
			newRole.getPermissions().putAll(obj.getPermissions());
			obj = newRole;
		}
		try {
			roleService.save(obj);
			paraId = obj.getName();
			List<User> users = userService.getByRole(obj.getKey(), obj.getType());
			boolean hasError = false;
			List failUsers = new ArrayList();
			for (User u : users) {
				// 因為 lazy 要重新 get
				try {
					userService.save(userService.get(u.getUsername()));
				} catch (Exception e) {
					hasError = true;
					failUsers.add(u.getUsername());
				}
			}
			if (hasError) {
				addActionError("使用者重设权限失败, 帐号:" + StringUtils.join(failUsers, ","));
			}
		} catch (Exception e) {
			addActionError(e.getMessage());
			return "edit";
		}
		return view();
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
			for (Role.Type t : typeMenu) {
				paraTypes.add(t.getCode());
			}
		}
		if (averablePermission == null) {
			CommonCriteria criteria = new CommonCriteria();
			criteria.addIn("type",
					Arrays.asList(Permission.Type.OTHER, Permission.Type.SERVICE, Permission.Type.SYSTEM, Permission.Type.ROLE));
			List<Permission> pList = permissionService.getList(0, -1, criteria, new String[] { "name" });

			averablePermission = new TreeMap();
			for (Permission p : pList) {
				averablePermission.put(p.getName(), p);
			}
		}
		if (roleTypeMenu == null) {
			roleTypeMenu = new Menu();
			for (Role.Type t : typeMenu) {
				// logger.info(t.getCode() + "/" + t.getDesc());
				if (!"SYSTEM".equals(t.getCode())) {
					roleTypeMenu.addOption(t.getCode(), t.getDesc());
				}
			}
		}
		if (pager == null) {
			pager = new SimplePager();
		}
	}

}