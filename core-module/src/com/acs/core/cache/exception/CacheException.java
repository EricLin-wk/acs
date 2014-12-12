/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.cache.exception.CacheException
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
package com.acs.core.cache.exception;

/**
 * @author tw4149
 * 
 */
public class CacheException extends Exception {

	/** serialVersionUID */
	private static final long serialVersionUID = 1595689318149713245L;

	/** default constructors */
	public CacheException() {
		super();
	}

	/** default constructors */
	public CacheException(String key) {
		super(key);
	}

	/** default constructors */
	public CacheException(String key, Throwable e) {
		super(key, e);
	}
}
