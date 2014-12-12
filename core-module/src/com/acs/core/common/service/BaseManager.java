/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.service.BaseManager
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
package com.acs.core.common.service;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

/**
 * @author tw4149
 * 
 */
public interface BaseManager {
	public abstract Object get(Class _class, Serializable key);

	public abstract Object load(Class _class, Serializable key);

	public abstract Serializable save(Object obj);

	public abstract void saveOrUpdate(Object obj);

	public abstract void update(Object obj);

	public abstract void delete(Object obj);

	public abstract Object[] list(Class _class);

	public int countByCriteria(DetachedCriteria dc);

	public Object[] findByCriteria(DetachedCriteria dc, int firstRecord, int maxRecord);

	public Object[] findByCriteria(DetachedCriteria dc);

	public Object executeHql(String hql, Map parameters);

	public Object executeHql(String hql, Object[] parameters);

	public Object[] queryByHql(String queryString, Object[] values, int start, int max);

	public Object[] queryByHql(String queryString, Object[] values);

	public Long countByHql(String hql, Object[] values);

	public Object[] customHqlQuery(String hql, Object[] values, int start, int max);

	public Object[] customHqlQuery(String hql, Object[] values);

	public Long customHqlCount(String hql, Object[] values);

	public Object[] queryBySql(String sql, Object[] values);
}
