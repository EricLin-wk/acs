/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.menu.service.MenuService
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
package com.acs.core.menu.service;

import com.acs.core.common.exception.CoreException;
import com.acs.core.common.service.DomainService;
import com.acs.core.menu.entity.Menu;

/**
 * @author tw4149
 */
public interface MenuService extends DomainService<Menu> {
	// Menu Keys
	public final static String MENU_USER_STATUS = "user_status";
	public final static String MENU_DEVICE_TYPE = "device_type";

	public Menu getClone(String menuKey) throws CoreException;
}
