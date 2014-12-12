/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.service.impl.DomainServiceImpl
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
package com.acs.core.common.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.acs.core.cache.exception.CacheException;
import com.acs.core.cache.service.CacheService;
import com.acs.core.common.dao.ObjectDao;
import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.exception.CoreException;
import com.acs.core.common.service.DomainService;

/**
 * @author tw4149
 * 
 */
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class DomainServiceImpl<T> implements DomainService<T> {
	private ObjectDao<T> dao;
	/** logger */
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	/** cache */
	private CacheService<T> cache;
	/** cacheAttribute */
	private String cacheAttribute = null;
	/** defaultSort: "updateDate", "createDate" */
	private String[] defaultSort = new String[] { "modifyDate", "createDate" };

	/**
	 * @return the dao
	 */
	public ObjectDao<T> getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *           the dao to set
	 */
	public void setDao(ObjectDao dao) {
		this.dao = dao;
	}

	/**
	 * @return the defaultSort
	 */
	public String[] getDefaultSort() {
		return defaultSort;
	}

	/**
	 * @return the cache
	 */
	protected CacheService<T> getCache() {
		return cache;
	}

	/**
	 * @param cache
	 *           the cache to set
	 */
	public void setCache(CacheService<T> cache) {
		this.cache = cache;
	}

	/**
	 * @param cacheAttribute
	 *           the cacheAttribute to set
	 */
	public void setCacheAttribute(String cacheAttribute) {
		this.cacheAttribute = cacheAttribute;
	}

	/**
	 * @param defaultSort
	 *           the defaultSort to set
	 */
	public void setDefaultSort(String[] defaultSort) {
		this.defaultSort = defaultSort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.common.service.DomainService#delete(java.lang.Object)
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(T entity) throws CoreException {
		logger.debug("entity:{}", entity);
		if (cache != null) {
			try {
				cache.remove(BeanUtils.getSimpleProperty(entity, cacheAttribute));
			} catch (Exception e) {
				logger.warn("remove cache fail, Object:{}", entity);
			}
		}
		dao.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.common.service.DomainService#get(java.io.Serializable)
	 */
	@Override
	public T get(Serializable oid) throws CoreException {
		T entity = null;
		if (cache != null) {
			try {
				entity = cache.get(String.valueOf(oid));
			} catch (CacheException e) {
				entity = dao.get(oid);
				try {
					if (entity != null) {
						cache.put(BeanUtils.getSimpleProperty(entity, cacheAttribute), entity);
					}
				} catch (Exception e1) {
					logger.warn("put cache fail, Object:{}", entity);
				}
			}
		} else {
			entity = dao.get(oid);
		}
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.common.service.DomainService#getAll(int, int)
	 */
	@Override
	public List<T> getAll(int firstResult, int maxResults, String[] sortOrder) throws CoreException {
		if (sortOrder == null) {
			return dao.getListPageable(new CommonCriteria(), defaultSort, firstResult, maxResults);
		}
		return dao.getListPageable(new CommonCriteria(), sortOrder, firstResult, maxResults);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.common.service.DomainService#save(java.lang.Object)
	 */
	@Override
	@Transactional(readOnly = false)
	public T save(T entity) throws CoreException {
		if (cache != null) {
			try {
				cache.remove(BeanUtils.getSimpleProperty(entity, cacheAttribute));
			} catch (Exception e) {
				logger.warn("remove cache fail, Object:{}", entity);
			}
		}
		return dao.saveOrUpdate(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.common.service.DomainService#update(java.lang.Object)
	 */
	@Override
	@Transactional(readOnly = false)
	public T update(T entity) throws CoreException {
		if (cache != null) {
			try {
				cache.remove(BeanUtils.getSimpleProperty(entity, cacheAttribute));
			} catch (Exception e) {
				logger.warn("remove cache fail, Object:{}", entity);
			}
		}
		return dao.update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.common.service.DomainService#getAllSize()
	 */
	@Override
	public Number getAllSize() throws CoreException {
		return dao.getListSize(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.common.service.DomainService#getList(int, int,
	 * com.yaodian100.core.common.dao.impl.CommonCriteria)
	 */
	@Override
	public List<T> getList(int firstResult, int maxResults, CommonCriteria criteria, String[] sortOrder)
			throws CoreException {
		if (sortOrder == null) {
			return dao.getListPageable(criteria, defaultSort, firstResult, maxResults);
		}
		return dao.getListPageable(criteria, sortOrder, firstResult, maxResults);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.common.service.DomainService#getListSize(com.yaodian
	 * .core.common.dao.impl.CommonCriteria)
	 */
	@Override
	public Number getListSize(CommonCriteria criteria) throws CoreException {
		return dao.getListSize(criteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yaodian100.core.common.service.DomainService#getSingle(com.yaodian100.core.common.dao.impl.CommonCriteria,
	 * java.lang.String[])
	 */
	@Override
	public T getSingle(CommonCriteria criteria, String[] sortOrder) throws CoreException {
		T result = null;
		List<T> resultList = dao.getListPageable(criteria, sortOrder, 0, 1);
		if ((resultList != null) && (resultList.size() > 0)) {
			result = resultList.get(0);
		}
		return result;
	}
}
