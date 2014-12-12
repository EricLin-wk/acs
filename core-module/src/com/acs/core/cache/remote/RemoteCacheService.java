/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.cache.remote.RemoteCacheService
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

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.acs.core.common.exception.CoreException;

/**
 * @author tw4149
 * 
 */
@WebService
public interface RemoteCacheService {
	@WebMethod(action = "http://remote.cache.core.teecs.com/clean")
	public boolean clean(String cache) throws CoreException;
}
