/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.utils.AdminHelper
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
package com.acs.core.user.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.acs.core.common.utils.StringUtils;
import com.acs.core.user.entity.Group;
import com.acs.core.user.entity.MemberDetails;
import com.acs.core.user.entity.Permission;
import com.acs.core.user.entity.Role;
import com.acs.core.user.entity.User;
import com.acs.core.user.entity.UserDetails;
import com.acs.core.user.entity.UserPermission;

/**
 * @author tw4149
 * 
 */
public final class AdminHelper {
	private static final String KEY_ADM = "SYSTEM_ADM";

	public final static UserDetails getUserDetail() {
		UserDetails user = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Object obj = auth.getPrincipal();
			if (obj instanceof UserDetails) {
				user = (UserDetails) obj;
			}
		}
		return user;
	}

	/**
	 * 取得 Yaodian100 UserDetails 物件
	 * 
	 * @return
	 */
	public final static MemberDetails getMemberDetails() {
		MemberDetails user = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Object obj = auth.getPrincipal();
			if (obj instanceof MemberDetails) {
				user = (MemberDetails) obj;
			}
		}
		return user;
	}

	/**
	 * 取得 User 物件
	 * 
	 * @return
	 */
	public final static User getUser() {
		User user = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Object obj = auth.getPrincipal();
			if (obj instanceof User) {
				user = (User) obj;
			}
		}
		return user;
	}

	public final static boolean hasPermission(String permissions) {
		boolean result = false;
		if (permissions == null) {
			permissions = "";
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Object obj = auth.getPrincipal();
			if (obj instanceof UserDetails) {
				UserDetails user = (UserDetails) obj;
				List<String> pList = new ArrayList<String>();
				for (String p : StringUtils.split(permissions, ",")) {
					pList.add(p);
				}
				pList.add(KEY_ADM);

				for (GrantedAuthority ga : user.getAuthorities()) {
					if (pList.contains(ga.getAuthority())) {
						result = true;
						break;
					}
				}
			}
		}

		return result;
	}

	public final static boolean hasPermissions(User user, String permissions) {
		boolean result = false;
		if (permissions == null) {
			permissions = "";
		}
		List<String> pList = new ArrayList<String>();
		for (String p : StringUtils.split(permissions, ",")) {
			pList.add(p);
		}
		pList.add(KEY_ADM);

		for (GrantedAuthority ga : user.getAuthorities()) {
			if (pList.contains(ga.getAuthority())) {
				result = true;
				break;
			}
		}

		return result;
	}

	/**
	 * 判斷該 user 是否有此 permission
	 * 
	 * @param user
	 * @param permissionKey
	 *           格式應當如 ROLE_PM, GROUP_RD
	 * @return
	 */
	public final static boolean hasPermission(User user, String permissionName) {
		for (UserPermission p : user.getPermissions().values()) {
			if (permissionName.equals(p.getPermissionKey())) {
				return true;
			}
			if (KEY_ADM.equals(p.getPermissionKey())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param user
	 * @param permissionKey
	 * @param type
	 * @return
	 */
	public final static boolean hasPermission(User user, String permissionKey, Permission.Type type) {
		String permissionName = type.name() + "_" + permissionKey;
		for (UserPermission p : user.getPermissions().values()) {
			if (permissionName.equals(p.getPermissionKey())) {
				return true;
			}
			if (KEY_ADM.equals(p.getPermissionKey())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判斷該 user 是否有此 role
	 * 
	 * @param user
	 * @param roleKey
	 * @return
	 */
	public final static boolean hasRole(User user, String roleKey, Role.Type type) {
		for (Role r : user.getRoles()) {
			if (KEY_ADM.equals(r.getName())) {
				return true;
			}
			if (r.getName().equals(type.name() + "_" + roleKey)) {
				return true;
			}
		}
		return false;
	}

	public final static boolean hasRole(String roleKey, Role.Type type) {
		User user = getUser();
		for (Role r : user.getRoles()) {
			if (KEY_ADM.equals(r.getName())) {
				return true;
			}
			if (r.getName().equals(type.name() + "_" + roleKey)) {
				return true;
			}
		}
		return false;
	}

	public final static String getGroupLastName(Group group) {
		if (group != null) {
			String[] n = StringUtils.split(group.getCode(), ".");
			return n[n.length - 1];
		}
		return "";
	}

	public final static String getGroupPrefixName(Group group) {
		if (group != null) {
			String[] n = StringUtils.split(group.getCode(), ".");
			return n[0];
		}
		return "";
	}
}
