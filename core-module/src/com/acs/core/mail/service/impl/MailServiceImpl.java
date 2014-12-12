/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.mail.service.impl.MailServiceImpl
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
package com.acs.core.mail.service.impl;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.exception.CoreException;
import com.acs.core.common.service.impl.DomainServiceImpl;
import com.acs.core.common.utils.MailUtils;
import com.acs.core.mail.entity.Mail;
import com.acs.core.mail.service.MailService;

/**
 * @author tw4149
 * 
 */
public class MailServiceImpl extends DomainServiceImpl<Mail> implements MailService {

	public static final String HEADER_CONTENT_ID = "Content-ID";
	/** email mime type */
	public final static String MIME_TYPE = "text/html;charset=utf8";

	/** spring mailSender */
	private JavaMailSender mailSender;

	/** mail from */
	private String from = "\"telexpress\" <system@telexpress.com>";

	/** mail reply */
	private String reply = null;

	/** default constructors */
	public MailServiceImpl() {
	}

	/**
	 * @param from
	 *           the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @param reply
	 *           the reply to set
	 */
	public void setReply(String reply) {
		this.reply = reply;
	}

	/**
	 * @param mailSender
	 *           the mailSender to set
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.common.service.impl.DomainServiceImpl#save(java.lang.Object)
	 */
	@Override
	@Transactional(readOnly = false)
	public Mail save(Mail entity) throws CoreException {
		if (!entity.getAttachments().isEmpty()) {
			entity.setHasAttachment(true);
		}
		if (!entity.getInlines().isEmpty()) {
			entity.setHasAttachment(true);
		}
		return super.save(entity);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Mail saveMust(Mail entity) throws CoreException {
		if (!entity.getAttachments().isEmpty()) {
			entity.setHasAttachment(true);
		}
		if (!entity.getInlines().isEmpty()) {
			entity.setHasAttachment(true);
		}
		return super.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.mail.service.MailService#sendMail(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.util.Map, java.util.Map, java.lang.String)
	 */
	public void sendMail(String to, String cc, String bcc, String subject, String body, Map inlines, Map attachments,
			String mailFrom) throws CoreException {
		try {
			MimeMessage msg = mailSender.createMimeMessage();

			if (StringUtils.isNotEmpty(to)) {
				if (to.indexOf(",") != -1) {
					String[] addrs = StringUtils.split(to, ",");
					for (int i = 0; i < addrs.length; i++) {
						if (StringUtils.isNotEmpty(addrs[i])) {
							msg.addRecipient(MimeMessage.RecipientType.TO, MailUtils.getAddress(addrs[i]));
						}
					}
				} else if (to.indexOf(";") != -1) {
					String[] addrs = StringUtils.split(to, ";");
					for (int i = 0; i < addrs.length; i++) {
						if (StringUtils.isNotEmpty(addrs[i])) {
							msg.addRecipient(MimeMessage.RecipientType.TO, MailUtils.getAddress(addrs[i]));
						}
					}
				} else {
					msg.addRecipient(MimeMessage.RecipientType.TO, MailUtils.getAddress(to));
				}
			}
			if (StringUtils.isNotEmpty(cc)) {
				if (cc.indexOf(",") != -1) {
					String[] addrs = StringUtils.split(cc, ",");
					for (int i = 0; i < addrs.length; i++) {
						if (StringUtils.isNotEmpty(addrs[i])) {
							msg.addRecipient(MimeMessage.RecipientType.CC, MailUtils.getAddress(addrs[i]));
						}
					}
				} else if (cc.indexOf(";") != -1) {
					String[] addrs = StringUtils.split(cc, ";");
					for (int i = 0; i < addrs.length; i++) {
						if (StringUtils.isNotEmpty(addrs[i])) {
							msg.addRecipient(MimeMessage.RecipientType.CC, MailUtils.getAddress(addrs[i]));
						}
					}
				} else {
					msg.addRecipient(MimeMessage.RecipientType.CC, MailUtils.getAddress(cc));
				}
			}
			if (StringUtils.isNotEmpty(bcc)) {
				if (bcc.indexOf(",") != -1) {
					String[] addrs = StringUtils.split(bcc, ",");
					for (int i = 0; i < addrs.length; i++) {
						if (StringUtils.isNotEmpty(addrs[i])) {
							msg.addRecipient(MimeMessage.RecipientType.BCC, MailUtils.getAddress(addrs[i]));
						}
					}
				} else if (bcc.indexOf(";") != -1) {
					String[] addrs = StringUtils.split(bcc, ";");
					for (int i = 0; i < addrs.length; i++) {
						if (StringUtils.isNotEmpty(addrs[i])) {
							msg.addRecipient(MimeMessage.RecipientType.BCC, MailUtils.getAddress(addrs[i]));
						}
					}
				} else {
					msg.addRecipient(MimeMessage.RecipientType.BCC, MailUtils.getAddress(bcc));
				}
			}

			if (StringUtils.isNotEmpty(mailFrom)) {
				msg.setFrom(MailUtils.getAddress(mailFrom));
			} else {
				msg.setFrom(MailUtils.getAddress(from));
			}

			if (StringUtils.isNotEmpty(reply)) {
				msg.setReplyTo(new Address[] { MailUtils.getAddress(reply) });
			}

			if (StringUtils.isNotEmpty(subject)) {
				msg.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
			} else {
				msg.setSubject(" ");
			}

			MimeMultipart mimeMultipart = new MimeMultipart();
			MimeBodyPart mimeBodyPart = null;

			Iterator it = null;
			// attach inline files
			if (inlines != null) {
				it = inlines.keySet().iterator();
				while (it.hasNext()) {
					String contentid = (String) it.next();
					mimeBodyPart = new MimeBodyPart();
					String str = inlines.get(contentid).toString();
					if (str.indexOf("://") != -1) {
						URL url = new URL(str);
						mimeBodyPart.setDataHandler(new DataHandler(url));
					} else {
						FileDataSource fds = new FileDataSource(str);
						mimeBodyPart.setDataHandler(new DataHandler(fds));
					}

					mimeBodyPart.setHeader(HEADER_CONTENT_ID, "<" + contentid + ">");
					mimeBodyPart.setFileName(contentid);
					mimeBodyPart.setContentID(contentid);
					mimeBodyPart.setDisposition(MimeBodyPart.INLINE);
					logger.debug("add inline id:{}, content:{}", contentid, str);

					mimeMultipart.addBodyPart(mimeBodyPart);
				}
			}

			// attach files
			if (attachments != null) {
				it = attachments.keySet().iterator();
				while (it.hasNext()) {
					String filename = (String) it.next();
					mimeBodyPart = new MimeBodyPart();
					String str = attachments.get(filename).toString();
					if (str.indexOf("://") != -1) {
						URL url = new URL(str);
						mimeBodyPart.setDataHandler(new DataHandler(url));
					} else {
						FileDataSource fds = new FileDataSource(str);
						mimeBodyPart.setDataHandler(new DataHandler(fds));
					}

					// String encodFilename = MimeUtility.encodeText(filename, "big5", "B");
					// mimeBodyPart.setFileName(encodFilename);
					mimeBodyPart.setFileName(filename);
					mimeBodyPart.setDisposition(MimeBodyPart.ATTACHMENT);
					logger.debug("add attachment name:{}, content:{}", filename, str);
					mimeMultipart.addBodyPart(mimeBodyPart);
				}
			}

			mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(body, MIME_TYPE);
			mimeMultipart.addBodyPart(mimeBodyPart);

			msg.setContent(mimeMultipart);
			mailSender.send(msg);
		} catch (Exception e) {
			throw new CoreException("errors.system.mail", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.mail.service.MailService#sendMail(com.yaodian100.core.mail .entity.Mail)
	 */
	@Transactional(readOnly = false)
	public boolean sendMail(Mail mail) throws CoreException {
		boolean result = false;
		mail.setRetry(mail.getRetry() + 1);
		try {
			if (!mail.isSend()) {
				sendMail(mail.getTo(), mail.getCc(), mail.getBcc(), mail.getSubject(), mail.getBody(), mail.getInlines(),
						mail.getAttachments(), mail.getFrom());
				mail.setSend(true);
				logger.info("send mail oid:{}, subject:{}, to:{}",
						new Object[] { mail.getOid(), mail.getSubject(), mail.getTo() });
			}
			result = true;
		} catch (Exception e) {
			logger.error("errors.system.mail", e);
		}
		save(mail);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.mail.service.MailService#getNoneSendMails(int, int)
	 */
	public List<Mail> getNoneSendMails(int firstResult, int maxResults) throws CoreException {
		CommonCriteria cri = new CommonCriteria();
		// 排序條件為 1. 優先序, 2. 建立時間, 3. oid
		String[] sortOrder = new String[] { "sort", "oid" };

		// 寄送時間小於現在
		Date now = Calendar.getInstance().getTime();
		cri.addLe("sendDT", now);

		// 寄送旗標為 false
		cri.addEq("send", Boolean.FALSE);

		cri.addLe("retry", 6);
		List<Mail> mails = getDao().getListPageable(cri, sortOrder, firstResult, maxResults);
		for (Mail m : mails) {
			Hibernate.initialize(m.getInlines());
			Hibernate.initialize(m.getAttachments());
		}
		logger.info("Get none send mail, size: {}", mails.size());
		return mails;
	}

}
