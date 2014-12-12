/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.cache.remote.RemoteCacheServiceImpl
   Module Description   :

   Date Created      : 2013/7/10
   Original Author   : tw4149
   Team              : 
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.cache.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.acs.core.cache.service.CacheService;
import com.acs.core.common.exception.CoreException;
import com.acs.core.common.utils.StringUtils;

/**
 * @author tw4149
 * 
 */
public class RemoteCacheServiceImpl implements RemoteCacheService, ApplicationContextAware {
	ApplicationContext ctx = null;
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acs.core.cache.remote.RemoteCacheService#clean(java.lang.String)
	 */
	@Override
	public boolean clean(String cache) throws CoreException {
		boolean result = false;
		try {
			if (ctx != null) {
				if (StringUtils.isNotBlank(cache)) {
					CacheService cacheObj = ctx.getBean(cache, CacheService.class);
					if (cacheObj != null) {
						cacheObj.destroy();
						logger.warn("cache({}) clean", cache);
						result = true;
					} else {
						logger.warn("cache({}) not found", cache);
					}
				} else {
					String[] cacheNames = ctx.getBeanNamesForType(CacheService.class);
					for (String s : cacheNames) {
						CacheService cacheObj = ctx.getBean(s, CacheService.class);
						if (cacheObj != null) {
							cacheObj.destroy();
							logger.warn("cache({}) clean", s);
							result = true;
						} else {
							logger.warn("cache({}) not found", s);
						}
					}
				}
			} else {
				logger.warn("spring config fail, ctx is null");
			}
		} catch (Exception e) {
			throw new CoreException("clean cache false", e);
		}
		return result;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
	}
}
