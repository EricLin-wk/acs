/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.web.tag.CheckPermission
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
package com.acs.core.user.web.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import com.acs.core.common.utils.StringUtils;
import com.acs.core.user.utils.AdminHelper;

/**
 * @author tw4149
 * 
 */
public class CheckPermission extends ConditionalTagSupport {

	/** serialVersionUID */
	private static final long serialVersionUID = -6918447977651113517L;

	private String permissions = null;

	/** default constructor */
	public CheckPermission() {
		super();
	}

	/**
	 * @param permissions
	 *           the permissions to set
	 */
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.jstl.core.ConditionalTagSupport#condition()
	 */
	@Override
	protected boolean condition() throws JspTagException {
		if (permissions != null && permissions.indexOf(",") != -1) {
			String[] pArray = StringUtils.split(permissions, ",");
			for (int i = 0; i < pArray.length; i++) {
				if (AdminHelper.hasPermission(pArray[i])) {
					return true;
				}
			}
		} else if (permissions != null) {
			return AdminHelper.hasPermission(permissions);
		}
		return false;
	}

}
