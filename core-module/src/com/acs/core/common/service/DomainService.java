/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.service.DomainService
   Module Description   :

   Date Created      : 2012/11/16
   Original Author   : tw4149
   Team              : 
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.common.service;

import java.io.Serializable;
import java.util.List;

import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.exception.CoreException;

/**
 * @author tw4149
 * 
 */
public interface DomainService<T> {
	public T get(Serializable oid) throws CoreException;

	public T save(T entity) throws CoreException;

	public T update(T entity) throws CoreException;

	public void delete(T entity) throws CoreException;

	public List<T> getAll(int firstResult, int maxResults, String[] sortOrder) throws CoreException;

	public Number getAllSize() throws CoreException;

	public List<T> getList(int firstResult, int maxResults, CommonCriteria criteria, String[] sortOrder)
			throws CoreException;

	public Number getListSize(CommonCriteria criteria) throws CoreException;

	public T getSingle(CommonCriteria criteria, String[] sortOrder) throws CoreException;
}
