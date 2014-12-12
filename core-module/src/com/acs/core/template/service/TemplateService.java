/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.template.service.TemplateService
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
package com.acs.core.template.service;

import java.util.Map;

import com.acs.core.common.exception.CoreException;
import com.acs.core.mail.entity.Mail;

/**
 * @author tw4149
 * 
 */
public interface TemplateService {
	/**
	 * @param templateName
	 *           template name
	 * @param objs
	 *           input data
	 * @return result string
	 * @throws CoreException
	 */
	public String format(String templateName, Map objs) throws CoreException;

	/**
	 * @param template
	 * @param objs
	 * @return string
	 * @throws CoreException
	 */
	public String formatByStringTemplate(String template, Map objs) throws CoreException;

	/**
	 * @param template
	 * @param name
	 * @param values
	 * @return string
	 * @throws CoreException
	 */
	public String formatByString(String template, String[] name, Object[] values) throws CoreException;

	/**
	 * @param templateName
	 * @param objs
	 * @return
	 * @throws CoreException
	 */
	public Mail formatToMail(String templateName, Map objs) throws CoreException;
}
