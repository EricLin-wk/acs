/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.menu.service.impl.MenuServiceImpl
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
package com.acs.core.menu.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.core.common.exception.CoreException;
import com.acs.core.common.service.impl.DomainServiceImpl;
import com.acs.core.menu.entity.Menu;
import com.acs.core.menu.service.MenuService;

/**
 * @author tw4149
 */
public class MenuServiceImpl extends DomainServiceImpl<Menu> implements MenuService {
	private static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Override
	public Menu getClone(String menuKey) throws CoreException {
		Menu menu = get(menuKey);
		if (menu != null) {
			try {
				menu = menu.clone();
			} catch (CloneNotSupportedException e) {
				logger.error(e.getMessage(), e);
				throw new CoreException(e.getMessage());
			}
		}

		return menu;
	}
}
