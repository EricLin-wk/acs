/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.service.impl.PermissionServiceImpl
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
package com.acs.core.user.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.acs.core.common.exception.CoreException;
import com.acs.core.common.service.impl.DomainServiceImpl;
import com.acs.core.user.entity.Permission;
import com.acs.core.user.service.PermissionService;

/**
 * @author tw4149
 * 
 */
public class PermissionServiceImpl extends DomainServiceImpl<Permission> implements PermissionService {

	/** default constructors */
	public PermissionServiceImpl() {
		super();
		setDefaultSort(new String[] { "type", "key" });
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Permission entity) throws CoreException {
		if (!Permission.Type.OTHER.equals(entity.getType())) {
			throw new CoreException("errors.admin.permission.access.deny", entity.getKey(), entity.getType().toString());
		}
		super.delete(entity);
	}

}
