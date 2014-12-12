/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.cache.service.CacheService
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
package com.acs.core.cache.service;

import com.acs.core.cache.exception.CacheException;

/**
 * @author tw4149
 * 
 */
public interface CacheService<T> {
	/**
	 * Get an item from the cache
	 * 
	 * @param key
	 * @return the cached object or <tt>null</tt>
	 * @throws CacheException
	 */
	public T get(String key) throws CacheException;

	/**
	 * Add an item to the cache, nontransactionally, with failfast semantics
	 * 
	 * @param key
	 * @param value
	 * @throws CacheException
	 */
	public void put(String key, T value);

	/**
	 * Add an item to the cache
	 * 
	 * @param key
	 * @param value
	 * @throws CacheException
	 */
	public void update(String key, T value);

	/**
	 * Remove an item from the cache
	 * 
	 * @param key
	 * @throws CacheException
	 */
	public void remove(String key);

	/**
	 * Clean up
	 * 
	 * @throws CacheException
	 */
	public void destroy();

	/**
	 * @return cahce
	 */
	public Object getCache();
}
