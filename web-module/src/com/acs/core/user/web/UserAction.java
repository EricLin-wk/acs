/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.web.UserAction
   Module Description   :

   Date Created      : 2012/12/17
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.entity.SimplePager;
import com.acs.core.common.exception.CoreException;
import com.acs.core.common.utils.ServerValue;
import com.acs.core.mail.entity.Mail;
import com.acs.core.mail.service.MailService;
import com.acs.core.menu.entity.Menu;
import com.acs.core.menu.service.MenuService;
import com.acs.core.template.service.TemplateService;
import com.acs.core.user.entity.Group;
import com.acs.core.user.entity.Permission;
import com.acs.core.user.entity.Role;
import com.acs.core.user.entity.User;
import com.acs.core.user.service.GroupService;
import com.acs.core.user.service.PermissionService;
import com.acs.core.user.service.RoleService;
import com.acs.core.user.service.UserService;
import com.acs.core.user.utils.AdminHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author tw4149
 */
public class UserAction extends ActionSupport implements Preparable {
	/** serialVersionUID */
	private static final long serialVersionUID = 4772155807577183575L;
	private final Logger logger = LoggerFactory.getLogger(UserAction.class);
	@Resource
	protected UserService userService;
	@Resource
	protected GroupService groupService;
	@Resource
	protected RoleService roleService;
	@Resource
	protected MenuService menuService;
	@Resource
	protected PermissionService permissionService;
	/** templateService */
	@Resource
	protected TemplateService templateService;
	/** mailService */
	@Resource
	protected MailService mailService;
	protected String usernameParam;
	protected String emailParam;
	protected String groupParam;
	protected List<String> statusParam = new ArrayList<String>();
	protected String objId;

	protected Menu userStatusMenu;
	protected List<Group> averableGroups;
	protected List<Role> managerRoles;
	protected List<Role> otherRoles;
	protected List<User> objList;
	protected User obj;
	protected String groupCode;
	protected List<Permission> permissions = new ArrayList<Permission>();
	protected List<Permission> teamPermissions = new ArrayList<Permission>();
	protected List<String> grantManageRoles = new ArrayList();
	protected List<String> grantOtherRoles = new ArrayList();
	protected List<Role> grantTeamRoles = new ArrayList();
	protected List<Role> grantRoles = new ArrayList();
	private SimplePager pager;
	private final String charSet = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKMNPQRSTUVWXYZ";
	public final static int PASSWORD_LENGTH = 8;
	protected String location;

	/**
	 * @return the groupService
	 */
	public GroupService getGroupService() {
		return groupService;
	}

	/**
	 * @param groupService the groupService to set
	 */
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	/**
	 * @return the usernameParam
	 */
	public String getUsernameParam() {
		return usernameParam;
	}

	/**
	 * @param usernameParam the usernameParam to set
	 */
	public void setUsernameParam(String usernameParam) {
		this.usernameParam = usernameParam;
	}

	/**
	 * @return the emailParam
	 */
	public String getEmailParam() {
		return emailParam;
	}

	/**
	 * @param emailParam the emailParam to set
	 */
	public void setEmailParam(String emailParam) {
		this.emailParam = emailParam;
	}

	/**
	 * @return the groupParam
	 */
	public String getGroupParam() {
		return groupParam;
	}

	/**
	 * @param groupParam the groupParam to set
	 */
	public void setGroupParam(String groupParam) {
		this.groupParam = groupParam;
	}

	/**
	 * @return the statusParam
	 */
	public List<String> getStatusParam() {
		return statusParam;
	}

	/**
	 * @param statusParam the statusParam to set
	 */
	public void setStatusParam(List<String> statusParam) {
		this.statusParam = statusParam;
	}

	/**
	 * @return the objId
	 */
	public String getObjId() {
		return objId;
	}

	/**
	 * @param objId the objId to set
	 */
	public void setObjId(String objId) {
		this.objId = objId;
	}

	/**
	 * @return the userStatusMenu
	 */
	public Menu getUserStatusMenu() {
		return userStatusMenu;
	}

	/**
	 * @param userStatusMenu the userStatusMenu to set
	 */
	public void setUserStatusMenu(Menu userStatusMenu) {
		this.userStatusMenu = userStatusMenu;
	}

	/**
	 * @return the averableGroups
	 */
	public List<Group> getAverableGroups() {
		return averableGroups;
	}

	/**
	 * @param averableGroups the averableGroups to set
	 */
	public void setAverableGroups(List<Group> averableGroups) {
		this.averableGroups = averableGroups;
	}

	/**
	 * @return the managerRoles
	 */
	public List<Role> getManagerRoles() {
		return managerRoles;
	}

	/**
	 * @param managerRoles the managerRoles to set
	 */
	public void setManagerRoles(List<Role> managerRoles) {
		this.managerRoles = managerRoles;
	}

	/**
	 * @return the otherRoles
	 */
	public List<Role> getOtherRoles() {
		return otherRoles;
	}

	/**
	 * @param otherRoles the otherRoles to set
	 */
	public void setOtherRoles(List<Role> otherRoles) {
		this.otherRoles = otherRoles;
	}

	/**
	 * @return the objList
	 */
	public List<User> getObjList() {
		return objList;
	}

	/**
	 * @param objList the objList to set
	 */
	public void setObjList(List<User> objList) {
		this.objList = objList;
	}

	/**
	 * @return the obj
	 */
	public User getObj() {
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void setObj(User obj) {
		this.obj = obj;
	}

	/**
	 * @return the groupCode
	 */
	public String getGroupCode() {
		return groupCode;
	}

	/**
	 * @param groupCode the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * @return the permissions
	 */
	public List<Permission> getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * @return the grantManageRoles
	 */
	public List<String> getGrantManageRoles() {
		return grantManageRoles;
	}

	/**
	 * @param grantManageRoles the grantManageRoles to set
	 */
	public void setGrantManageRoles(List<String> grantManageRoles) {
		this.grantManageRoles = grantManageRoles;
	}

	/**
	 * @return the grantOtherRoles
	 */
	public List<String> getGrantOtherRoles() {
		return grantOtherRoles;
	}

	/**
	 * @param grantOtherRoles the grantOtherRoles to set
	 */
	public void setGrantOtherRoles(List<String> grantOtherRoles) {
		this.grantOtherRoles = grantOtherRoles;
	}

	/**
	 * @return the grantTeamRoles
	 */
	public List<Role> getGrantTeamRoles() {
		return grantTeamRoles;
	}

	/**
	 * @param grantTeamRoles the grantTeamRoles to set
	 */
	public void setGrantTeamRoles(List<Role> grantTeamRoles) {
		this.grantTeamRoles = grantTeamRoles;
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
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the teamPermissions
	 */
	public List<Permission> getTeamPermissions() {
		return teamPermissions;
	}

	/**
	 * @param teamPermissions the teamPermissions to set
	 */
	public void setTeamPermissions(List<Permission> teamPermissions) {
		this.teamPermissions = teamPermissions;
	}

	/**
	 * @return the grantRoles
	 */
	public List<Role> getGrantRoles() {
		return grantRoles;
	}

	/**
	 * @param grantRoles the grantRoles to set
	 */
	public void setGrantRoles(List<Role> grantRoles) {
		this.grantRoles = grantRoles;
	}

	public String search() {
		CommonCriteria cri = new CommonCriteria();
		if (StringUtils.isNotEmpty(usernameParam)) {
			cri.addRlike("username", usernameParam);
		}
		if (StringUtils.isNotEmpty(groupParam)) {
			cri.addRlike("group.code", groupParam);
		}
		if (statusParam != null && statusParam.size() > 0) {
			cri.addIn("status", statusParam);
		}
		if (StringUtils.isNotEmpty(emailParam)) {
			cri.addRlike("email", emailParam);
		}
		try {
			int entitySize = userService.getListSize(cri).intValue();
			pager.setTotalSize(entitySize);

			objList = userService.getList(pager.getCurrentPage() * pager.getPageRecord(), pager.getPageRecord(), cri, null);

			logger.info("pager:{},users:{}", pager, objList);
		} catch (CoreException e) {
			addActionError(e.getMessage());
			e.printStackTrace();
		}
		return "list";
	}

	public String view() {
		if (StringUtils.isNotBlank(objId)) {
			try {
				obj = userService.get(objId);
				if (obj == null) {
					addActionError("使用者不存在, 帐号:" + objId);
					return "list";
				}
				groupCode = obj.getGroupName();
				grantManageRoles.clear();
				grantOtherRoles.clear();
				grantTeamRoles.clear();
				permissions.clear();
				grantRoles.clear();
				teamPermissions.clear();
				for (Role r : obj.getRoles()) {
					if (StringUtils.startsWith(r.getKey(), "TEAM")) {
						grantTeamRoles.add(r);
					} else {
						grantRoles.add(r);
					}
					switch (r.getType()) {
					case MANAGER:
						grantManageRoles.add(r.getName());
						break;
					case OTHER:
						grantOtherRoles.add(r.getName());
						break;
					}
				}

				List<String> keys = new ArrayList<String>(obj.getPermissions().keySet());
				Collections.sort(keys);
				for (int i = 0; i < keys.size(); i++) {
					CommonCriteria crit = new CommonCriteria();
					crit.addEq("name", keys.get(i));
					List<Permission> temp = permissionService.getList(0, -1, crit, new String[] { "key" });
					if (temp.size() > 0) {
						if (StringUtils.startsWith(keys.get(i), "ROLE_TEAM")) {
							teamPermissions.add(temp.get(0));
						} else {
							permissions.add(temp.get(0));
						}
					}
				}
			} catch (Exception e) {
				addActionError(e.getMessage());
				e.printStackTrace();
			}
			return "view";
		} else {
			addActionError("使用者不存在, 帐号:" + objId);
			return "list";
		}
	}

	public String save() {
		try {
			if (obj != null) {
				List<String> userRoleName = new ArrayList();
				Iterator<Role> it = obj.getRoles().iterator();
				logger.info(groupCode);
				while (it.hasNext()) {
					Role r = it.next();
					if (Collections.frequency(grantManageRoles, r.getName()) == 0 && Role.Type.MANAGER.equals(r.getType())) {
						// 移除權限為 Manager, 但是代碼不相同
						it.remove();
					} else if (Collections.frequency(grantOtherRoles, r.getName()) == 0 && Role.Type.OTHER.equals(r.getType())) {
						// 移除權限為 Other, 但是代碼不相同
						it.remove();
					} else if (Role.Type.GROUP.equals(r.getType()) && !r.getKey().equals(groupCode)) {
						// 移除權限為 Group, 但是部門代碼不相同
						it.remove();
					} else {
						userRoleName.add(r.getName());
					}
				}

				for (String mRole : grantManageRoles) {
					if (Collections.frequency(userRoleName, mRole) == 0) {
						logger.info("add role:{}", mRole);
						Role r = roleService.get(mRole);
						if (r != null) {
							obj.getRoles().add(r);
						}
					}
				}
				for (String oRole : grantOtherRoles) {
					if (Collections.frequency(userRoleName, oRole) == 0) {
						logger.info("add role:{}", oRole);
						Role r = roleService.get(oRole);
						if (r != null) {
							obj.getRoles().add(r);
						}
					}
				}
				logger.debug("roles:{}", obj.getRoles());
				if (obj.getModifyDate() == null) {
					if (StringUtils.isNotBlank(obj.getPassword())) {
						obj = userService.createUser(obj, obj.getPassword(), groupCode);
					} else {
						addActionError("预设密码不允许为空");
						return "edit";
					}
				} else {
					if (!groupCode.equals(obj.getGroupName())) {
						Group newGroup = groupService.get(groupCode);
						obj.setGroup(newGroup);
						obj.getRoles().add(newGroup.getRole());
					}
					obj = userService.save(obj);
				}
				objId = obj.getUsername();
			} else {
				addActionError("使用者不存在");
				return "list";
			}
		} catch (Exception e) {
			addActionError(e.getMessage());
			e.printStackTrace();
			return "edit";
		}
		view();
		return "view";
	}

	public String edit() {
		obj = userService.get(objId);
		return "edit";
	}

	public String add() {
		obj = new User("");
		return "edit";
	}

	public String delete() {
		try {
			obj = userService.get(objId);
			if (obj != null) {
				userService.delete(obj);
			} else {
				addActionError("使用者不存在, 未删除");
			}
		} catch (Exception e) {
			addActionError(e.getMessage());
			e.printStackTrace();
			return "view";
		}
		return search();
	}

	public String resetPasswd() {
		// TODO: redesign password reset
		try {
			obj = userService.get(objId);
			if (obj != null) {
				String newPasswd = userService.getRandPassword();
				userService.resetPassword(obj);
				User user = AdminHelper.getUser();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("userObj", obj);
				m.put("newPassword", newPasswd);
				m.put("serverValue", ServerValue.getInstance());
				Mail mail = templateService.formatToMail("User.resetPassword", m);
				mail.addTo(obj.getNameNative(), obj.getEmail());
				mail.addCc(user.getUsername(), user.getEmail());
				mailService.save(mail);
				addActionMessage("重设密码成功");
			}
		} catch (Exception e) {
			addActionError(e.getMessage());
			e.printStackTrace();
			return "view";
		}
		return "view";
	}

	public String list() {
		return "list";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	@Override
	public void prepare() throws Exception {
		clearErrorsAndMessages();
		if (pager == null) {
			pager = new SimplePager();
		}
		if (userStatusMenu == null) {
			userStatusMenu = menuService.getClone(MenuService.MENU_USER_STATUS);

			logger.debug("new user status:{}", userStatusMenu);
		}

		if (managerRoles == null) {
			CommonCriteria cri = new CommonCriteria();
			cri.addEq("type", Role.Type.MANAGER);
			managerRoles = roleService.getList(0, -1, cri, new String[] { "key asc" });
			// for (int i = managerRoles.size(); i > 0; i--) {
			// Role r = managerRoles.get(i - 1);
			// if (!(r.getKey().startsWith("A-") || r.getKey().startsWith("C"))) {
			// managerRoles.remove(i - 1);
			// }
			// }
		}
		if (otherRoles == null) {
			CommonCriteria cri = new CommonCriteria();
			cri.addEq("type", Role.Type.OTHER);
			otherRoles = roleService.getList(0, -1, cri, new String[] { "key asc" });
		}
		if (averableGroups == null) {
			CommonCriteria cri = new CommonCriteria();
			averableGroups = groupService.getList(0, -1, cri, new String[] { "code asc" });
		}
	}

}
