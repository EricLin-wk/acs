/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.logger.service.LogService
   Module Description   :

   Date Created      : 2012/11/22
   Original Author   : tw4149
   Team              :
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.logger.service;

import com.acs.core.common.service.DomainService;
import com.acs.core.logger.entity.CommonLog;

/**
 * @author tw4149
 */
public interface LogService extends DomainService<CommonLog> {
	public void save(String serviceName, String method, Object obj);

	public void save(String serviceName, String method, Object obj, String actorId);
}
