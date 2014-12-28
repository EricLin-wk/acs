/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.service.AppConfigService
   Module Description   :

   Date Created      : 2013/3/5
   Original Author   : tw4149
   Team              :
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.common.service;

import com.acs.core.common.entity.AppConfig;

/**
 * @author tw4149
 */
public interface AppConfigService extends DomainService<AppConfig> {

	public static final String APPCONFIG_ECHO_SERVER_BIND_IP = "ECHO_SERVER_BIND_IP";
	public static final String APPCONFIG_ECHO_SERVER_BIND_PORT = "ECHO_SERVER_BIND_PORT";

}
