/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.service.RoleService
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

import com.acs.core.common.exception.CoreException;
import com.acs.core.common.service.DomainService;
import com.acs.core.user.entity.Role;

/**
 * @author tw4149
 * 
 */
public interface RoleService extends DomainService<Role> {
	public Role get(String key, Role.Type type) throws CoreException;
}
