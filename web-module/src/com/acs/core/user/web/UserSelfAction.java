/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.web.UserSelfAction
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

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.core.common.exception.CoreException;
import com.acs.core.menu.entity.Menu;
import com.acs.core.menu.service.MenuService;
import com.acs.core.user.entity.Group;
import com.acs.core.user.entity.Role;
import com.acs.core.user.entity.User;
import com.acs.core.user.service.UserService;
import com.acs.core.user.utils.AdminHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author tw4149
 */
public class UserSelfAction extends ActionSupport implements Preparable {

	/** serialVersionUID */
	private static final long serialVersionUID = -3672091192202210793L;
	private final Logger logger = LoggerFactory.getLogger(UserSelfAction.class);
	@Resource
	private UserService userService;
	@Resource
	private MenuService menuService;
	private Menu userStatus;
	private Group group;
	private Set<Role> roles = new HashSet<Role>();
	private Set<Role> teamRoles = new HashSet<Role>();
	private String status;

	// writable
	private String nameNative;
	private String email;
	private String phone;
	private String mobile;

	private String oldPassword;
	private String newPassword;

	/** default constructor */
	public UserSelfAction() {
	}

	/**
	 * @return the userStatus
	 */
	public Menu getUserStatus() {
		return userStatus;
	}

	/**
	 * @return the nameNative
	 */
	public String getNameNative() {
		return nameNative;
	}

	/**
	 * @param nameNative the nameNative to set
	 */
	public void setNameNative(String nameNative) {
		this.nameNative = nameNative;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return AdminHelper.getUser().getUsername();
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @return the teamRoles
	 */
	public Set<Role> getTeamRoles() {
		return teamRoles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @param teamRoles the teamRoles to set
	 */
	public void setTeamRoles(Set<Role> teamRoles) {
		this.teamRoles = teamRoles;
	}

	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public User toUser() {
		User user = null;
		try {
			user = AdminHelper.getUser();
			user.setEmail(email);
			user.setMobile(mobile);
			user.setNameNative(nameNative);
			user.setPhone(phone);
		} catch (CoreException e) {
			addActionError(e.getLocalizedMessage());
			logger.error(e.getMessage(), e);
		}
		return user;
	}

	public void fromUser() {
		try {
			User user = AdminHelper.getUser();
			email = user.getEmail();
			mobile = user.getMobile();
			nameNative = user.getNameNative();
			phone = user.getPhone();

			group = user.getGroup();
			status = user.getStatus();
			Set<Role> temp = user.getRoles();
			roles.clear();
			teamRoles.clear();
			for (Role r : temp) {
				if (StringUtils.startsWith(r.getKey(), "TEAM")) {
					teamRoles.add(r);
				} else {
					roles.add(r);
				}
			}

		} catch (CoreException e) {
			addActionError(e.getLocalizedMessage());
			logger.error(e.getMessage(), e);
		}
	}

	public String view() {
		fromUser();
		return "view";
	}

	public String save() {
		try {
			User user = toUser();
			if (user != null) {
				user = userService.save(user);
			}
		} catch (CoreException e) {
			addActionError(e.getMessage());
			logger.error(e.getMessage(), e);
			return "edit";
		}
		return view();
	}

	public String prepareChangePasswd() {
		return "chgpwd";
	}

	public String changePasswd() {
		try {
			User user = AdminHelper.getUser();
			userService.changePassword(user, newPassword, oldPassword);
			addActionMessage("密码变更成功");
		} catch (CoreException e) {
			if ("errors.account.password".equals(e.getMessage())) {
				addActionError("密码错误");
			} else {
				addActionError(e.getLocalizedMessage());
			}
			logger.error(e.getMessage(), e);
			return "chgpwd";
		} catch (Exception e) {
			addActionError(e.getLocalizedMessage());
			logger.error(e.getMessage(), e);
			return "chgpwd";
		}
		return view();
	}

	public String edit() {
		fromUser();
		return "edit";
	}

	@Override
	public void prepare() throws Exception {
		clearErrorsAndMessages();
		if (userStatus == null) {
			userStatus = menuService.getClone(MenuService.MENU_USER_STATUS);
			logger.debug("new user status:{}", userStatus);
		}
	}

}
