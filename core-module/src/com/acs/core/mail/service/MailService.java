/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.mail.service.MailService
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
package com.acs.core.mail.service;

import java.util.List;
import java.util.Map;

import com.acs.core.common.exception.CoreException;
import com.acs.core.common.service.DomainService;
import com.acs.core.mail.entity.Mail;

/**
 * @author tw4149
 * 
 */
public interface MailService extends DomainService<Mail> {
	/**
	 * send mail to SMTP Server
	 * 
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param body
	 * @param inlines
	 * @param attachments
	 * @param mailFrom
	 * @throws CoreException
	 */
	public void sendMail(String to, String cc, String bcc, String subject, String body, Map inlines, Map attachments,
			String mailFrom) throws CoreException;

	/**
	 * send mail to SMTP Server
	 * 
	 * @param mail
	 * @return
	 * @throws CoreException
	 */
	public boolean sendMail(Mail mail) throws CoreException;

	/**
	 * 取得未寄送電子郵件
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws CoreException
	 */
	public List<Mail> getNoneSendMails(int firstResult, int maxResults) throws CoreException;

	/**
	 * save mail entity for new tx
	 * 
	 * @param entity
	 * @return
	 * @throws CoreException
	 */
	public Mail saveMust(Mail entity) throws CoreException;
}
