/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.service.impl.UserServiceImpl
   Module Description   :

   Date Created      : 2012/11/22
   Original Author   : tw4149
   Team              :
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.user.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.exception.CoreException;
import com.acs.core.common.service.impl.DomainServiceImpl;
import com.acs.core.common.utils.StringUtils;
import com.acs.core.logger.service.LogService;
import com.acs.core.mail.entity.Mail;
import com.acs.core.mail.service.MailService;
import com.acs.core.menu.service.MenuService;
import com.acs.core.template.service.TemplateService;
import com.acs.core.user.dao.UserDao;
import com.acs.core.user.entity.Group;
import com.acs.core.user.entity.Permission;
import com.acs.core.user.entity.Role;
import com.acs.core.user.entity.User;
import com.acs.core.user.entity.UserPermission;
import com.acs.core.user.service.GroupService;
import com.acs.core.user.service.RoleService;
import com.acs.core.user.service.UserService;

/**
 * @author tw4149
 */
public class UserServiceImpl extends DomainServiceImpl<User> implements UserService {

	public final static int PASSWORD_LENGTH = 8;

	/** passwordEncoder, default: PlaintextPasswordEncoder() */
	@Autowired(required = false)
	private PasswordEncoder passwordEncoder = new PlaintextPasswordEncoder();
	/** templateService */
	@Resource
	protected TemplateService templateService;
	/** mailService */
	@Resource
	protected MailService mailService;
	/** groupService */
	@Resource
	private GroupService groupService;
	/** roleService */
	@Resource
	private RoleService roleService;
	@Resource
	private LogService logService;
	@Resource
	private MenuService menuService;
	private String charSet = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKMNPQRSTUVWXYZ";
	/**
	 * 若有設定, 則搜尋出的 user.groupName 必須以此開頭
	 */
	private String companyCode = null;
	/** maxError, default: 999 */
	private int maxError = 999;

	/** default constructors */
	public UserServiceImpl() {
		super();
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @param charSet the charSet to set
	 */
	public void setCharSet(String charSet) {
		if (charSet != null) {
			this.charSet = charSet;
		}
	}

	/**
	 * @param maxError the maxError to set
	 */
	public void setMaxError(int maxError) {
		this.maxError = maxError;
	}

	/**
	 * @param passwordEncoder the passwordEncoder to set
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setUserDao(UserDao userDao) {
		setDao(userDao);
	}

	@Override
	@Transactional(readOnly = false)
	public User changePassword(User user, String newPassword, String oldPassword) throws CoreException {
		logService.save(getClass().getSimpleName(), "changePassword", user);
		if (user == null) {
			throw new CoreException("errors.account.empty");
		}
		if (!User.STATUS_ACTIVE.equals(user.getStatus())) {
			throw new CoreException("errors.account.status", user.getStatus());
		}
		if (validatePassword(user, oldPassword)) {
			user.setPassword(passwordEncoder.encodePassword(newPassword, null));
			user.setNeedChangePassword(false);
			// 直接 update 不用 resetPermission
			super.update(user);
		} else {
			throw new CoreException("errors.account.password");
		}
		return user;
	}

	@Override
	@Transactional(readOnly = false)
	public User resetPassword(User user) throws CoreException {
		String newPwd = getRandPassword();
		return resetPassword(user, newPwd, false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acs.core.user.service.UserService#resetPassword(com.acs.core.user.entity.User, boolean)
	 */
	@Override
	@Transactional(readOnly = false)
	public User resetPassword(User user, String password, boolean sendEmail) throws CoreException {

		user.setPassword(passwordEncoder.encodePassword(password, null));
		user.setNeedChangePassword(true);
		// 直接 update 不用 resetPermission
		super.update(user);
		if (sendEmail) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("userObj", user);
			m.put("newPassword", password);
			// m.put("serverValue", ServerValue.getInstance());
			Mail mail = templateService.formatToMail("User.resetPassword", m);
			mail.addTo(user.getNameNative(), user.getEmail());
			mailService.save(mail);
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acs.core.user.service.UserService#validatePassword(com.acs.core.user.entity.User, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false)
	public boolean validatePassword(User user, String password) throws CoreException {
		// aplog.info(JSONArray.fromObject(user).toString());
		boolean valid = false;
		boolean update = false;

		valid = passwordEncoder.isPasswordValid(user.getPassword(), password, null);
		logger.debug("userId:" + user.getUsername() + ",input:" + password + ",passwd:" + user.getPassword());
		if (valid && user.getErrorCount() > 0) {
			user.setErrorCount(0);
			update = true;
		} else if (!valid) {
			user.setErrorCount(user.getErrorCount() + 1);
			if (user.getErrorCount() >= maxError) {
				user.setStatus(User.STATUS_INACTIVE);
			}
			update = true;
		}

		if (update) {
			super.update(user);
		}
		return valid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acs.core.user.service.UserService#getByGroup(java.lang.String)
	 */
	@Override
	public List<User> getByGroup(String groupKey) throws CoreException {
		List<User> users = null;
		CommonCriteria cri = new CommonCriteria();
		cri.addEq("group.code", groupKey);
		return super.getList(0, -1, cri, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acs.core.user.service.UserService#getByRole(java.lang.String, com.acs.core.user.entity.Role.Type)
	 */
	@Override
	public List<User> getByRole(String roleKey, Role.Type type, String groupCode) throws CoreException {
		List<User> result = null;
		Role role = roleService.get(roleKey, type);
		if (role != null) {
			List<Object> attrs = new ArrayList<Object>();
			attrs.add(role);
			attrs.add(User.STATUS_ACTIVE);
			if (StringUtils.isBlank(groupCode)) {
				result = getDao().getQueryByList("from User u where ? in elements(u.roles) and u.status=? order by u.username",
						attrs, 0, -1);
			} else {
				attrs.add(groupCode);
				result = getDao().getQueryByList(
						"from User u where ? in elements(u.roles) and u.status=? and u.groupName =? order by u.username", attrs, 0,
						-1);
			}
		}
		return result;
	}

	@Override
	public List<User> getByRole(String roleKey, Role.Type type) throws CoreException {
		return getByRole(roleKey, type, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acs.core.user.service.UserService#createUser(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false)
	public User createUser(User entity, String password, String groupCode) throws CoreException {
		return createUser(entity, password, groupCode, false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yaodian100.core.common.service.impl.DomainServiceImpl#save(java.lang.Object)
	 */
	@Override
	@Transactional(readOnly = false)
	public User save(User entity) throws CoreException {
		if (StringUtils.isNotBlank(companyCode) && !entity.getGroupName().startsWith(companyCode)) {
			throw new CoreException("errors.user.save.companyCode", entity.getUsername(), companyCode);
		}
		logService.save(getClass().getSimpleName(), "save", entity);
		resetPermissions(entity);
		return super.save(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yaodian100.core.common.service.impl.DomainServiceImpl#update(java.lang.Object)
	 */
	@Override
	@Transactional(readOnly = false)
	public User update(User entity) throws CoreException {
		if (StringUtils.isNotBlank(companyCode) && !entity.getGroupName().startsWith(companyCode)) {
			throw new CoreException("errors.user.update.companyCode", entity.getUsername(), companyCode);
		}
		logService.save(getClass().getSimpleName(), "update", entity);
		resetPermissions(entity);
		return super.update(entity);
	}

	/**
	 * @param user
	 */
	private void resetPermissions(User user) {
		Set<String> newPermissionNames = new HashSet();

		// check roles
		for (Role role : user.getRoles()) {
			logger.debug("role:{}", role);
			if (role == null) {
				break;
			}
			for (Permission permission : role.getPermissions().values()) {
				newPermissionNames.add(permission.getName());
				if (user.getPermissions().get(permission.getName()) == null) {
					user.getPermissions().put(permission.getName(),
							new UserPermission(user, permission.getName(), permission.getType()));
				}
			}
		}

		// check group
		Role role = user.getGroup().getRole();
		for (Permission permission : role.getPermissions().values()) {
			newPermissionNames.add(permission.getName());
			if (user.getPermissions().get(permission.getName()) == null) {
				user.getPermissions().put(permission.getName(),
						new UserPermission(user, permission.getName(), permission.getType()));
			}
		}

		List<String> removeList = new ArrayList();
		for (UserPermission p : user.getPermissions().values()) {
			if (!newPermissionNames.contains(p.getPermissionKey())) {
				removeList.add(p.getPermissionKey());
			}
		}
		for (String key : removeList) {
			user.getPermissions().remove(key);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yaodian100.core.common.service.impl.DomainServiceImpl#get(java.io.Serializable)
	 */
	@Override
	public User get(Serializable oid) throws CoreException {
		User user = super.get(oid);
		if (user != null) {
			if (StringUtils.isNotBlank(companyCode) && !user.getGroupName().startsWith(companyCode)) {
				logger.error("錯誤 companyCode, username:{}, groupName:{}, companyCode:{}", new Object[] { user.getUsername(),
						user.getGroupName(), companyCode });
				return null;
			}
			Hibernate.initialize(user.getRoles());
			Hibernate.initialize(user.getPermissions());
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		UserDetails entity = null;
		try {
			entity = get(username);
		} catch (CoreException e) {
			e.printStackTrace();
			throw new RecoverableDataAccessException(e.getMessage());
		}
		if (entity == null) {
			throw new UsernameNotFoundException(username);
		}
		return entity;
	}

	@Override
	public String getRandPassword() {
		StringBuffer sb = new StringBuffer(PASSWORD_LENGTH);
		Random rand = new Random(Calendar.getInstance().getTimeInMillis());
		String clone = String.valueOf(charSet);
		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int j = rand.nextInt() % clone.length();
			j = j < 0 ? -j : j;
			sb.append(clone.charAt(j));
			StringUtils.remove(clone, clone.charAt(j));
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acs.core.user.service.UserService#getByPermission(java.lang.String,
	 * com.acs.core.user.entity.Permission.Type)
	 */

	@Override
	public List<User> getByPermission(String permissionKey, Permission.Type type, String channelCode) {
		List<User> result = null;
		if (StringUtils.isNotBlank(permissionKey)) {
			List param = new ArrayList();
			StringBuffer hql = new StringBuffer().append("from User u where u.permissions[?] is not null and u.status=?");
			param.add(type.name() + "_" + permissionKey);
			param.add(User.STATUS_ACTIVE);
			if (StringUtils.isNotBlank(channelCode)) {
				param.add(channelCode);
				hql.append(" and u.groupName = ?");
			}
			if (StringUtils.isNotBlank(companyCode)) {
				hql.append(" and u.groupName like '" + companyCode + "%'");
			}
			result = getDao().getQueryByList(hql.toString(), param, 0, -1);
		}
		return result;
	}

	@Override
	public List<User> getByPermission(String permissionKey, Permission.Type type) {
		return getByPermission(permissionKey, type, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acs.core.user.service.UserService#createUser(com.acs.core.user.entity.User, java.lang.String,
	 * java.lang.String, boolean)
	 */
	@Override
	@Transactional(readOnly = false)
	public User createUser(User entity, String password, String groupCode, boolean sendEmail) throws CoreException {
		if (StringUtils.isNotBlank(companyCode) && !groupCode.startsWith(companyCode)) {
			throw new CoreException("errors.user.create.companyCode", entity.getUsername(), groupCode, companyCode);
		}
		entity.setUsername(entity.getUsername().toLowerCase());
		logService.save(getClass().getSimpleName(), "createUser", entity);

		if (get(entity.getUsername()) != null) {
			throw new CoreException("帐号重覆", entity.getUsername());
		}

		Group group = groupService.get(groupCode);
		if (group == null) {
			throw new CoreException("群组/部门不存在,请确认", groupCode);
		}
		entity.setGroup(group);
		entity.getRoles().add(group.getRole());

		if (StringUtils.isBlank(password)) {
			password = getRandPassword();
		}
		entity.setPassword(passwordEncoder.encodePassword(password, null));
		resetPermissions(entity);
		entity = super.save(entity);

		if (sendEmail) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("userObj", entity);
			m.put("password", password);
			// m.put("serverValue", ServerValue.getInstance());
			Mail mail = templateService.formatToMail("User.create", m);
			mail.addTo(entity.getNameNative(), entity.getEmail());
			mailService.save(mail);
		}

		return entity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yaodian100.core.common.service.impl.DomainServiceImpl#getList(int, int,
	 * com.yaodian100.core.common.dao.impl.CommonCriteria, java.lang.String[])
	 */
	@Override
	public List<User> getList(int firstResult, int maxResults, CommonCriteria criteria, String[] sortOrder)
			throws CoreException {
		return this.getListByRole(firstResult, maxResults, criteria, sortOrder, null, null);
	}

	@Override
	public List<User> getListByRole(int firstResult, int maxResults, CommonCriteria criteria, String[] sortOrder,
			Role.Type roleType, String roleKey) throws CoreException {
		if (StringUtils.isNotBlank(companyCode)) {
			if (criteria == null) {
				criteria = new CommonCriteria();
			}

			if (criteria.getRlike().get("groupName") != null) {
				if (!StringUtils.startsWith(criteria.getRlike().get("groupName"), companyCode)) {
					logger.error("錯誤 companyCode, input:{}, companyCode:{}", criteria.getRlike().get("groupName"), companyCode);
					return null;
				}
			} else {
				criteria.addRlike("groupName", companyCode);
			}
		}

		if (roleType == null && roleKey == null) {
			return super.getList(firstResult, maxResults, criteria, sortOrder);
		} else {
			UserDao userDao = (UserDao) super.getDao();
			return userDao.getListPageableByRole(criteria, sortOrder, firstResult, maxResults, roleType, roleKey);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yaodian100.core.common.service.impl.DomainServiceImpl#getListSize(com.yaodian100.core.common.dao.impl.
	 * CommonCriteria)
	 */
	@Override
	public Number getListSize(CommonCriteria criteria) throws CoreException {
		return this.getListSizeByRole(criteria, null, null);

	}

	@Override
	public Number getListSizeByRole(CommonCriteria criteria, Role.Type roleType, String roleKey) throws CoreException {
		if (StringUtils.isNotBlank(companyCode)) {
			if (criteria == null) {
				criteria = new CommonCriteria();
			}

			if (criteria.getRlike().get("groupName") != null) {
				if (!StringUtils.startsWith(criteria.getRlike().get("groupName"), companyCode)) {
					logger.error("錯誤 companyCode, input:{}, companyCode:{}", criteria.getRlike().get("groupName"), companyCode);
					return null;
				}
			} else {
				criteria.addRlike("groupName", companyCode);
			}
		}

		if (roleType == null && roleKey == null) {
			return super.getListSize(criteria);
		} else {
			UserDao userDao = (UserDao) super.getDao();
			return userDao.getListSizeByRole(criteria, roleType, roleKey);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acs.core.user.service.UserService#getTeamList(java.lang.String)
	 */
	// @Override
	// public List<String> getTeamList(String userName) throws CoreException {
	// List<String> teamParams = new ArrayList<String>();
	// User user = get(userName);
	// Menu teamMenu = menuService.getClone(AppConstants.MENU_COMM_TEAM);
	// if (user != null) {
	// for (String key : teamMenu.getOptions().keySet()) {
	// if (AdminHelper.hasPermission(user, teamMenu.getOptions().get(key).getMemo1())) {
	// teamParams.add(key);
	// }
	// }
	// }
	// return teamParams;
	// }

}
