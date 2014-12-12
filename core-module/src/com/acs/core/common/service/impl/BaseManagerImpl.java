/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.service.impl.BaseManagerImpl
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
package com.acs.core.common.service.impl;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.acs.core.common.dao.BaseDAO;
import com.acs.core.common.service.BaseManager;

/**
 * @author tw4149
 * 
 */
public class BaseManagerImpl implements BaseManager {

	private static Logger LOGGER = LoggerFactory.getLogger(BaseManagerImpl.class);// NOPMD
	private BaseDAO baseDAO;

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	@Transactional
	public int countByCriteria(DetachedCriteria dc) {
		return this.baseDAO.countByCriteria(dc);
	}

	@Transactional(readOnly = false)
	public void delete(Object obj) {
		this.baseDAO.delete(obj);

	}

	@Transactional(readOnly = false)
	public Object executeHql(String hql, Map parameters) {
		return this.baseDAO.executeHql(hql, parameters);
	}

	@Transactional(readOnly = false)
	public Object executeHql(String hql, Object[] parameters) {
		return this.baseDAO.executeHql(hql, parameters);
	}

	@Transactional
	public Object get(Class _class, Serializable key) {
		return this.baseDAO.get(_class, key);
	}

	@Transactional
	public Object[] list(Class _class) {
		return this.baseDAO.list(_class);
	}

	@Transactional
	public Object[] findByCriteria(DetachedCriteria dc, int firstRecord, int maxRecord) {
		return this.baseDAO.findByCriteria(dc, firstRecord, maxRecord);
	}

	@Transactional
	public Object load(Class _class, Serializable key) {
		return this.baseDAO.load(_class, key);
	}

	@Transactional(readOnly = false)
	public Serializable save(Object obj) {
		return this.baseDAO.save(obj);
	}

	@Transactional(readOnly = false)
	public void saveOrUpdate(Object obj) {
		this.baseDAO.saveOrUpdate(obj);

	}

	@Transactional(readOnly = false)
	public void update(Object obj) {
		this.baseDAO.update(obj);

	}

	@Transactional
	public Object[] findByCriteria(DetachedCriteria dc) {
		return this.baseDAO.findByCriteria(dc);
	}

	@Transactional
	public Object[] queryByHql(String queryString, Object[] values, int start, int max) {
		return this.baseDAO.queryByHql(queryString, values, start, max);
	}

	@Transactional
	public Object[] queryByHql(String queryString, Object[] values) {
		return this.baseDAO.queryByHql(queryString, values);
	}

	@Transactional
	public Long countByHql(String hql, Object[] values) {
		return this.baseDAO.countByHql(hql, values);
	}

	@Transactional
	public Long customHqlCount(String hql, Object[] values) {
		return this.baseDAO.customHqlCount(hql, values);
	}

	@Transactional
	public Object[] customHqlQuery(String hql, Object[] values) {
		return this.baseDAO.customHqlQuery(hql, values);
	}

	@Transactional
	public Object[] customHqlQuery(String hql, Object[] values, int start, int max) {
		return this.baseDAO.customHqlQuery(hql, values, start, max);
	}

	@Transactional
	public Object[] queryBySql(String sql, Object[] values) {
		return this.baseDAO.queryBySql(sql, values);
	}

}
