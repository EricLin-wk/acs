/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.dao.impl.HibernateObjectDaoImpl
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
package com.acs.core.common.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.acs.core.common.dao.ObjectDao;
import com.acs.core.common.entity.BaseEntity;
import com.acs.core.common.exception.CoreException;

/**
 * @author tw4149
 */
public class HibernateObjectDaoImpl<T> extends HibernateDaoImpl implements ObjectDao<T> {
	private String className;
	private Class classObj;

	/**
	 *
	 */
	public HibernateObjectDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HibernateObjectDaoImpl(String className) throws ClassNotFoundException {
		super();
		this.className = className;
		this.classObj = Class.forName(className);
	}

	public HibernateObjectDaoImpl(String entityName, SessionFactory sessionFactory) throws ClassNotFoundException {
		super();
		setSessionFactory(sessionFactory);
		this.className = entityName;
		this.classObj = Class.forName(className);
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) throws ClassNotFoundException {
		this.className = className;
		classObj = Class.forName(className);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acs.core.dao.BaseDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(final Object obj) throws CoreException {
		try {
			logger.debug("object: {}", obj);
			super.delete(obj);
		} catch (HibernateException e) {
			throw new CoreException(CoreException.ERROR_DB, e);
		}
	}

	@Override
	public void deleteBatch(final Collection objects) throws CoreException {
		try {
			super.deleteBatch(objects);
		} catch (HibernateException e) {
			throw new CoreException("errors.system.db", e);
		}
	}

	@Override
	public int deleteByAttributes(final CommonCriteria cri) throws CoreException {
		return super.deleteByAttributes(classObj, cri);
	}

	@Override
	public void deleteByPK(final Serializable oid) throws CoreException {
		try {
			logger.debug("class: {}/{}", className, oid);
			Object obj = this.get(oid);
			super.delete(obj);
		} catch (HibernateException e) {
			throw new CoreException(CoreException.ERROR_DB, e);
		}
	}

	@Override
	public void flush() throws CoreException {
		try {
			super.flush();
		} catch (HibernateException e) {
			throw new CoreException(CoreException.ERROR_DB, e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acs.core.dao.BaseDao#get(java.io.Serializable)
	 */
	@Override
	public T get(final Serializable oid) throws CoreException {
		T result = null;
		try {
			result = (T) getHibernateTemplate().get(className, oid);
		} catch (HibernateException e) {
			throw new CoreException(CoreException.ERROR_DB, e);
		}
		return result;
	}

	@Override
	public List getAttributes(final String[] attributeNames, final CommonCriteria cri, final String[] sortOrder)
			throws CoreException {
		return super.getAttributes(classObj, attributeNames, cri, sortOrder);
	}

	@Override
	public List getAttributesPageable(final String[] attributeNames, final CommonCriteria cri, final String[] sortOrder,
			final int startNode, final int returnSize) throws CoreException {
		List<T> result = null;
		if (startNode == 0 && returnSize == -1) {
			result = this.getAttributes(attributeNames, cri, sortOrder);
		} else {
			result = super.getAttributesPageable(classObj, attributeNames, cri, sortOrder, startNode, returnSize);
		}
		return result;
	}

	@Override
	public List<T> getList(final CommonCriteria cri, final String[] sortOrder) throws CoreException {
		return super.getList(classObj, cri, sortOrder);
	}

	@Override
	public List<T> getListPageable(final CommonCriteria cri, final String[] sortOrder, final int startNode,
			final int returnSize) throws CoreException {
		List<T> result = null;
		if (startNode == 0 && returnSize == -1) {
			result = this.getList(classObj, cri, sortOrder);
		} else {
			result = super.getListPageable(classObj, cri, sortOrder, startNode, returnSize);
		}
		return result;
	}

	@Override
	public Number getListSize(final CommonCriteria cri) throws CoreException {
		return super.getListSize(classObj, cri);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map getMap(final String mapKey, final CommonCriteria cri, final String[] sortOrder) throws CoreException {
		return super.getMap(classObj, mapKey, cri, sortOrder);
	}

	@Override
	public T getSingle(final CommonCriteria cri, final String[] sortOrder) throws CoreException {
		List objs = getListPageable(cri, sortOrder, 0, 1);
		return (T) ((objs != null && objs.size() > 0) ? objs.get(0) : null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getSingle(final String key, final Serializable value) throws CoreException {
		Map conditions = new HashMap();
		conditions.put(key, value);
		return getSingle(new CommonCriteria(), null);
	}

	@Override
	public T load(final Serializable oid) throws CoreException {
		T result = null;
		try {
			result = (T) getHibernateTemplate().load(className, oid);
		} catch (HibernateException e) {
			throw new CoreException(CoreException.ERROR_DB, e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acs.core.dao.BaseDao#save(java.lang.Object)
	 */
	@Override
	public T save(final Object obj) throws CoreException {
		try {
			if (obj instanceof BaseEntity) {
				updateCommonAttribute((BaseEntity) obj);
			}
			getHibernateTemplate().save(obj);
		} catch (HibernateException e) {
			throw new CoreException(CoreException.ERROR_DB, e);
		}
		return (T) obj;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acs.core.dao.BaseDao#update(java.lang.Object)
	 */
	@Override
	public T update(final Object obj) throws CoreException {
		try {
			if (obj instanceof BaseEntity) {
				updateCommonAttribute((BaseEntity) obj);
			}
			getHibernateTemplate().update(obj);
		} catch (HibernateException e) {
			throw new CoreException(CoreException.ERROR_DB, e);
		}
		return (T) obj;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acs.core.dao.ObjectDao#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public T saveOrUpdate(final Object obj) throws CoreException {
		try {
			if (obj instanceof BaseEntity) {
				updateCommonAttribute((BaseEntity) obj);
			}
			getHibernateTemplate().saveOrUpdate(obj);
		} catch (HibernateException e) {
			throw new CoreException(CoreException.ERROR_DB, e);
		}
		return (T) obj;
	}

	// @Override
	// public void saveOrUpdateBatch(final Collection objs) throws CoreException {
	// try {
	// for (Object obj : objs) {
	// if (obj instanceof BaseEntity) {
	// updateCommonAttribute((BaseEntity) obj);
	// }
	// }
	// getHibernateTemplate().saveOrUpdateAll(objs);
	// } catch (HibernateException e) {
	// throw new CoreException(CoreException.ERROR_DB, e);
	// }
	// }
}
