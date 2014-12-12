/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.service.impl.GroupServiceImpl
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
import com.acs.core.user.entity.Group;
import com.acs.core.user.service.GroupService;

/**
 * @author tw4149
 * 
 */
public class GroupServiceImpl extends DomainServiceImpl<Group> implements GroupService {
	/** default constructors */
	public GroupServiceImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.common.service.impl.DomainServiceImpl#save(java.lang.Object)
	 */
	@Override
	@Transactional(readOnly = false)
	public Group save(Group entity) throws CoreException {
		entity.getManager().setDescription(entity.getDescription() + " 部门主管");
		entity.getManager().getMainPermission().setDescription(entity.getDescription() + " 部门主管权限");
		entity.getRole().setDescription(entity.getDescription() + " 部门成员");
		entity.getRole().getMainPermission().setDescription(entity.getDescription() + " 部门成员权限");
		return super.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.common.service.impl.DomainServiceImpl#update(java.lang.Object)
	 */
	@Override
	@Transactional(readOnly = false)
	public Group update(Group entity) throws CoreException {
		entity.getManager().setDescription(entity.getDescription() + " 部门主管");
		entity.getManager().getMainPermission().setDescription(entity.getDescription() + " 部门主管权限");
		entity.getRole().setDescription(entity.getDescription() + " 部门成员");
		entity.getRole().getMainPermission().setDescription(entity.getDescription() + " 部门成员权限");
		return super.update(entity);
	}
}
