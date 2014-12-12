/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.exception.CoreException
   Module Description   :

   Date Created      : 2012/11/16
   Original Author   : tw4149
   Team              : 
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.common.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tw4149
 * 
 */
public class CoreException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = -1948199891470653444L;
	private List<String> parameters = new ArrayList<String>();
	public static final String ERROR_DB = "errors.system.db";
	/** ERROR_NOTSUPPORT */
	public static final String ERROR_NOTSUPPORT = "errors.nosupport";

	public CoreException() {
		super();
	}

	@Override
	public String toString() {
		return this.getMessage() + ",parameters=" + parameters;
	}

	public CoreException(String i18nKey) {
		super(i18nKey);
	}

	public CoreException(String i18nKey, Throwable e) {
		super(i18nKey, e);
	}

	public CoreException(String i18nKey, Throwable e, String arg0) {
		super(i18nKey, e);
		parameters.add(arg0);
	}

	public CoreException(String i18nKey, Throwable e, String arg0, String arg1) {
		super(i18nKey, e);
		parameters.add(arg0);
		parameters.add(arg1);
	}

	public CoreException(String i18nKey, Throwable e, String arg0, String arg1, String arg2) {
		super(i18nKey, e);
		parameters.add(arg0);
		parameters.add(arg1);
		parameters.add(arg2);
	}

	public CoreException(String i18nKey, String arg0) {
		super(i18nKey);
		parameters.add(arg0);
	}

	public CoreException(String i18nKey, String arg0, String arg1) {
		super(i18nKey);
		parameters.add(arg0);
		parameters.add(arg1);
	}

	public CoreException(String i18nKey, String arg0, String arg1, String arg2) {
		super(i18nKey);
		parameters.add(arg0);
		parameters.add(arg1);
		parameters.add(arg2);
	}

	/**
	 * @return the parameters
	 */
	public List getParameter() {
		return this.parameters;
	}

	@SuppressWarnings("unchecked")
	public CoreException setParameter(List parameter) {
		this.parameters = parameter;
		return this;
	}
}
