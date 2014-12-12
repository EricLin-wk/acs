/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.dao.ObjectDao
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
package com.acs.core.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.exception.CoreException;

/**
 * @author tw4149
 */
public interface ObjectDao<T> extends BasicDao<T> {
	/**
	 * Delete Objects
	 *
	 * @param objects
	 * @throws CoreException
	 */
	public void deleteBatch(Collection<T> objects) throws CoreException;

	/**
	 * Delete objects from getList
	 *
	 * @return delete size
	 * @throws CoreException
	 */
	public int deleteByAttributes(CommonCriteria criteria) throws CoreException;

	/**
	 * Delete object by primary key
	 *
	 * @param oid
	 * @throws CoreException
	 */
	public void deleteByPK(Serializable oid) throws CoreException;

	public int executeUpdate(String queryName, Map<String, Serializable> attrs) throws CoreException;

	/**
	 * @throws CoreException
	 */
	public void flush() throws CoreException;

	@SuppressWarnings("unchecked")
	public List getAttributes(String[] attributeNames, CommonCriteria criteria, String[] sortOrder) throws CoreException;

	public List<T> getAttributesPageable(String[] attributeNames, CommonCriteria criteria, String[] sortOrder,
			int startNode, int returnSize) throws CoreException;

	public List<T> getList(CommonCriteria criteria, String[] sortOrder) throws CoreException;

	public List<T> getListPageable(CommonCriteria criteria, String[] sortOrder, int startNode, int returnSize)
			throws CoreException;

	public Number getListSize(CommonCriteria criteria) throws CoreException;

	public Map<String, T> getMap(String mapKey, CommonCriteria criteria, String[] sortOrder) throws CoreException;

	/**
	 * @param queryName
	 * @param attrs
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List getNameQuery(String queryName, Map<String, Serializable> attrs, int firstResult, int maxResults)
			throws CoreException;

	public List getQueryByList(String queryString, List attrs, int firstResult, int maxResults) throws CoreException;

	public List getQueryByMap(String queryString, Map attrs, int firstResult, int maxResults) throws CoreException;

	public T getSingle(CommonCriteria criteria, String[] sortOrder) throws CoreException;

	public T getSingle(String key, Serializable value) throws CoreException;

	public List getSQLQueryByList(String queryString, List attrs, int firstResult, int maxResults) throws CoreException;

	/**
	 * Load object by primary key
	 *
	 * @param oid hibernate id
	 * @return object instance
	 * @throws CoreException
	 */
	public T load(Serializable oid) throws CoreException;

	/**
	 * Save or update object
	 *
	 * @param obj
	 * @return object instance
	 * @throws CoreException
	 */
	public T saveOrUpdate(T obj) throws CoreException;

	/**
	 * Save or update objects
	 *
	 * @param objs
	 * @throws CoreException
	 */
	// public void saveOrUpdateBatch(Collection<T> objs) throws CoreException;
}
