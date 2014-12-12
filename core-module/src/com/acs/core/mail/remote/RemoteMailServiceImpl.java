/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.mail.remote.RemoteMailServiceImpl
   Module Description   :

   Date Created      : 2013/8/23
   Original Author   : tw4149
   Team              : 
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.mail.remote;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.core.common.exception.CoreException;
import com.acs.core.mail.entity.Mail;
import com.acs.core.mail.service.MailService;

/**
 * @author tw4149
 * 
 */
public class RemoteMailServiceImpl implements RemoteMailService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private MailService mailService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acs.core.mail.remote.RemoteMailService#sendMail()
	 */
	@Override
	public String sendMail(int i) {
		try {
			List<Mail> mailList = mailService.getNoneSendMails(0, -1);
			if (mailList.size() > 0) {
				for (Mail mail : mailList) {
					// 信件內容不可是空 && 收件人、CC、BCC其一非空才寄信。
					if ((StringUtils.isNotBlank(mail.getTo()) || StringUtils.isNotBlank(mail.getCc()) || StringUtils
							.isNotBlank(mail.getBcc())) && StringUtils.isNotBlank(mail.getBody())) {
						mailService.sendMail(mail);
					}
				}
			}
		} catch (Exception e) {
			throw new CoreException(e.getMessage(), e);
		}
		return "success";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acs.core.mail.remote.RemoteMailService#sendTestMail(int)
	 */
	@Override
	public String sendTestMail(int i) {
		try {
			Mail testMail = new Mail("test", "tst2", "chargedar@gmail.com", null);
			mailService.sendMail(testMail);
		} catch (Exception e) {
			throw new CoreException(e.getMessage(), e);
		}
		return "success";
	}

}
