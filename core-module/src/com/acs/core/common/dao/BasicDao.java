/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.dao.BasicDao
   Module Description   :

   Date Created      : 2012/11/26
   Original Author   : tw4149
   Team              : 
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.common.dao;

import java.io.Serializable;

import com.acs.core.common.exception.CoreException;

/**
 * @author tw4149
 * 
 */
public interface BasicDao<T> {
	/**
	 * Save object
	 * 
	 * @param obj
	 * @return object instance
	 * @throws CoreException
	 */
	public T save(T obj) throws CoreException;

	/**
	 * Update object
	 * 
	 * @param obj
	 * @return object instance
	 * @throws CoreException
	 */
	public T update(T obj) throws CoreException;

	/**
	 * Delete object
	 * 
	 * @param obj
	 * @throws CoreException
	 */
	public void delete(T obj) throws CoreException;

	/**
	 * Get object by primary key
	 * 
	 * @param oid
	 *           hibernate id
	 * @return object instance
	 * @throws CoreException
	 */
	public T get(Serializable oid) throws CoreException;
}
