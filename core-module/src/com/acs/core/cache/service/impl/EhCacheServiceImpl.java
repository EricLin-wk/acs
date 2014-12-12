/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.cache.service.impl.EnCacheServiceImpl
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
package com.acs.core.cache.service.impl;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.core.cache.exception.CacheException;
import com.acs.core.cache.service.CacheService;

/**
 * @author tw4149
 * 
 */
public class EhCacheServiceImpl implements CacheService {
	/** logger */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/** cache */
	private Cache cache;

	/** default constructors */
	public EhCacheServiceImpl(String name) {
		CacheManager manager = CacheManager.getInstance();
		cache = manager.getCache(name);
		if (cache == null) {
			manager.addCache(name);
			cache = manager.getCache(name);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.cache.service.CacheService#destroy()
	 */
	public void destroy() {
		cache.removeAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.cache.service.CacheService#get(java.lang.String)
	 */
	public Object get(String key) throws CacheException {
		Element element = cache.get(key);
		if (element == null) {
			logger.debug("key: {} need refresh, cache:{}", key, cache.getName());
			throw new CacheException("warns.cache.refresh");
		}
		return element.getObjectValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.cache.service.CacheService#getCache()
	 */
	public Object getCache() {
		return cache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.cache.service.CacheService#put(java.lang.String, java.lang.Object)
	 */
	public void put(String key, Object value) {
		cache.put(new Element(key, value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.cache.service.CacheService#remove(java.lang.String)
	 */
	public void remove(String key) {
		cache.remove(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.cache.service.CacheService#update(java.lang.String, java.lang.Object)
	 */
	public void update(String key, Object value) {
		cache.put(new Element(key, value));
	}
}
