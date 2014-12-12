/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.web.AbstractAction
   Module Description   :

   Date Created      : 2012/11/28
   Original Author   : tw4149
   Team              :
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.common.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.core.common.entity.SimplePager;
import com.acs.core.user.entity.User;
import com.acs.core.user.service.UserService;
import com.acs.core.user.utils.AdminHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author tw4149
 */
public abstract class AbstractAction extends ActionSupport implements Preparable {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected SimplePager pager;
	@Resource
	protected UserService userService;
	protected User user;

	/** default constructor */
	public AbstractAction() {
	}

	/**
	 * @return the pager
	 */
	public SimplePager getPager() {
		if (pager == null) {
			pager = new SimplePager();
		}
		return pager;
	}

	/**
	 * @param pager the pager to set
	 */
	public void setPager(SimplePager pager) {
		this.pager = pager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	@Override
	public void prepare() throws Exception {
		clearErrorsAndMessages();
		if (user == null) {
			try {
				user = AdminHelper.getUser();
				logger.debug("uer:{}", user);
			} catch (Exception e) {
				addActionError("Get user error!:" + e.getMessage());
				e.printStackTrace();
			}
		} else if (!user.getUsername().equals(AdminHelper.getUser().getUsername())) {
			try {
				user = userService.get(AdminHelper.getUser().getUsername());
			} catch (Exception e) {
				addActionError("Get user error!:" + e.getLocalizedMessage());
				e.printStackTrace();
			}
			resetData();
		}
		if (user.getErrorCount() > 0) {
			user.setErrorCount(0);
			userService.update(user);
		}
	}

	// 當系統發現，session 內有兩個不同的帳號時，應藉由 resetData 來 reset data。避免資料錯亂發生。
	public abstract void resetData();

}
