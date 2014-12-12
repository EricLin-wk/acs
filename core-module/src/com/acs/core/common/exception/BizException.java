/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.exception.BizException
   Module Description   :

   Date Created      : 2013/1/3
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
public class BizException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = -2169606438896572738L;
	/** parameter */
	private List<String> parameters = new ArrayList<String>();

	/** default constructor */
	public BizException() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getMessage() + ", parameters=" + parameters;
	}

	/**
	 * default constructor
	 * 
	 * @param i18nKey
	 */
	public BizException(String i18nKey) {
		super(i18nKey);
	}

	/**
	 * default constructor
	 * 
	 * @param i18nKey
	 * @param arg0
	 *           parameter
	 */
	public BizException(String i18nKey, String arg0) {
		super(i18nKey);
		parameters.add(arg0);
	}

	/**
	 * default constructor
	 * 
	 * @param i18nKey
	 * @param arg0
	 *           parameter
	 * @param arg1
	 *           parameter
	 */
	public BizException(String i18nKey, String arg0, String arg1) {
		super(i18nKey);
		parameters.add(arg0);
		parameters.add(arg1);
	}

	/**
	 * default constructor
	 * 
	 * @param i18nKey
	 * @param arg0
	 *           parameter
	 * @param arg1
	 *           parameter
	 * @param arg2
	 *           parameter
	 */
	public BizException(String i18nKey, String arg0, String arg1, String arg2) {
		super(i18nKey);
		parameters.add(arg0);
		parameters.add(arg1);
		parameters.add(arg2);
	}

	/**
	 * default constructor
	 * 
	 * @param i18nKey
	 * @param e
	 */
	public BizException(String i18nKey, Throwable e) {
		super(i18nKey, e);
	}

	/**
	 * default constructor
	 * 
	 * @param i18nKey
	 * @param e
	 * @param arg0
	 */
	public BizException(String i18nKey, Throwable e, String arg0) {
		super(i18nKey, e);
		parameters.add(arg0);
	}

	/**
	 * default constructor
	 * 
	 * @param i18nKey
	 * @param e
	 * @param arg0
	 *           parameter
	 * @param arg1
	 *           parameter
	 */
	public BizException(String i18nKey, Throwable e, String arg0, String arg1) {
		super(i18nKey, e);
		parameters.add(arg0);
		parameters.add(arg1);
	}

	/**
	 * default constructor
	 * 
	 * @param i18nKey
	 * @param e
	 * @param arg0
	 *           parameter
	 * @param arg1
	 *           parameter
	 * @param arg2
	 *           parameter
	 */
	public BizException(String i18nKey, Throwable e, String arg0, String arg1, String arg2) {
		super(i18nKey, e);
		parameters.add(arg0);
		parameters.add(arg1);
		parameters.add(arg2);
	}

	/**
	 * @return Returns the parameter.
	 */
	public List getParameter() {
		return this.parameters;
	}

	/**
	 * @param parameter
	 *           The parameter to set.
	 * @return exception instance
	 */
	@SuppressWarnings("unchecked")
	public BizException setParameter(List parameter) {
		this.parameters = parameter;
		return this;
	}
}
