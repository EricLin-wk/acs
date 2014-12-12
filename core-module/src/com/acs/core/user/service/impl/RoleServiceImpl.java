/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.service.impl.RoleServiceImpl
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

import org.springframework.transaction.annotation.Transactional;

import com.acs.core.common.exception.CoreException;
import com.acs.core.common.service.impl.DomainServiceImpl;
import com.acs.core.user.entity.Role;
import com.acs.core.user.service.RoleService;

/**
 * @author tw4149
 * 
 */
public class RoleServiceImpl extends DomainServiceImpl<Role> implements RoleService {
	/** default constructors */
	public RoleServiceImpl() {
		super();
		setDefaultSort(new String[] { "type", "key asc" });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.admin.service.RoleService#get(java.lang.String,
	 * com.yaodian100.core.admin.entity.Role.Type)
	 */
	public Role get(String key, Role.Type type) throws CoreException {
		return this.get((type.name() + "_" + key).toUpperCase());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.common.service.impl.DomainServiceImpl#save(java.lang.Object)
	 */
	@Override
	@Transactional(readOnly = false)
	public Role save(Role entity) throws CoreException {
		entity.getMainPermission().setDescription(entity.getDescription() + " 权限");
		return super.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.common.service.impl.DomainServiceImpl#update(java.lang.Object)
	 */
	@Override
	@Transactional(readOnly = false)
	public Role update(Role entity) throws CoreException {
		entity.getMainPermission().setDescription(entity.getDescription() + " 权限");
		return super.update(entity);
	}
}
