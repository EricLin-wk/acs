/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.service.UserService
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
package com.acs.core.user.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.exception.CoreException;
import com.acs.core.common.service.DomainService;
import com.acs.core.user.entity.Permission;
import com.acs.core.user.entity.Role;
import com.acs.core.user.entity.User;

/**
 * @author tw4149
 */
public interface UserService extends DomainService<User>, UserDetailsService {
	/**
	 * 變更密碼
	 *
	 * @param user
	 * @param newPassword
	 * @param oldPassword
	 * @throws CoreException errors.account.password 密码错误 errors.account.empty 帐号不存在 errors.account.status 状态不正确
	 */
	public User changePassword(User user, String newPassword, String oldPassword) throws CoreException;

	/**
	 * 重設密碼
	 *
	 * @param user
	 * @throws CoreException
	 */
	public User resetPassword(User user) throws CoreException;

	/**
	 * @param user
	 * @param sendEmail 是否寄送電子郵件
	 * @return
	 * @throws CoreException
	 */
	public User resetPassword(User user, String password, boolean sendEmail) throws CoreException;

	/**
	 * 驗證密碼
	 *
	 * @param user
	 * @param password
	 * @param targetIP
	 * @return
	 * @throws CoreException
	 */
	public boolean validatePassword(User user, String password) throws CoreException;

	/**
	 * @param entity
	 * @param password
	 * @param groupName
	 * @return
	 * @throws CoreException
	 */
	public User createUser(User entity, String password, String groupName) throws CoreException;

	public User createUser(User entity, String password, String groupName, boolean sendEmail) throws CoreException;

	/**
	 * @param roleKey
	 * @return
	 * @throws CoreException
	 */
	public List<User> getByRole(String roleKey, Role.Type type) throws CoreException;

	public List<User> getByRole(String roleKey, Role.Type type, String groupCode) throws CoreException;

	/**
	 * @param permissionKey
	 * @return
	 * @throws CoreException
	 */
	public List<User> getByPermission(String permissionKey, Permission.Type type) throws CoreException;

	public List<User> getByPermission(String permissionKey, Permission.Type type, String groupCode) throws CoreException;

	/**
	 * @param groupKey
	 * @return
	 * @throws CoreException
	 */
	public List<User> getByGroup(String groupKey) throws CoreException;

	//
	// /**
	// * 依部門和角色抓取符合的使用者。部門和角色代碼都不可以傳入空值。
	// *
	// * @param groupCode 部門代碼
	// * @param roleKey 角色代碼
	// * @param type
	// * @return
	// * @throws CoreException
	// */
	// public List<User> getByGroupAndRole(String groupCode, String roleKey, Role.Type type) throws CoreException;

	public String getRandPassword();

	public List<User> getListByRole(int firstResult, int maxResults, CommonCriteria criteria, String[] sortOrder,
			Role.Type roleType, String roleKey) throws CoreException;

	public Number getListSizeByRole(CommonCriteria criteria, Role.Type roleType, String roleKey) throws CoreException;

	// public List<String> getTeamList(String userName) throws CoreException;
}
