/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.dao.UserDao
   Module Description   :

   Date Created      : 2012/11/23
   Original Author   : tw4149
   Team              : 
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.user.dao;

import java.util.List;

import com.acs.core.common.dao.ObjectDao;
import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.exception.CoreException;
import com.acs.core.user.entity.Role;
import com.acs.core.user.entity.User;

/**
 * @author tw4149
 * 
 */
public interface UserDao extends ObjectDao<User> {
	public List<User> getByRole(Role role) throws CoreException;

	public List<User> getListPageableByRole(final CommonCriteria cri, final String[] sortOrder, final int startNode,
			final int returnSize, Role.Type roleType, String roleKey) throws CoreException;

	public Number getListSizeByRole(CommonCriteria cri, Role.Type roleType, String roleKey) throws CoreException;
}
