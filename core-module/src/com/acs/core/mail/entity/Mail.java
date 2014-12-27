/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.mail.entity.Mail
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
package com.acs.core.mail.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.MapKeyColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.acs.core.common.entity.BaseEntity;
import com.acs.core.common.utils.StringUtils;

/**
 * @author tw4149
 */
@Entity
@Table(name = "comm_mail")
public class Mail extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -6448933120663079161L;
	public static final int LEVEL_HIGH = 100;
	public static final int LEVEL_NORMAL = 200;
	public static final int LEVEL_LOW = 300;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_mail")
	@SequenceGenerator(name = "seq_mail", sequenceName = "SEQ_MAIL")
	@Column(name = "mail_oid")
	private Long oid;

	@Column(name = "mail_subject", length = 200)
	private String subject;

	@Lob
	@Column(name = "mail_body", updatable = false)
	@Basic(fetch = FetchType.LAZY)
	private String body;

	@Column(name = "mail_to")
	private String to;

	@Column(name = "mail_cc")
	private String cc;

	@Column(name = "mail_bcc")
	private String bcc;

	@Column(name = "mail_from", length = 50)
	private String from;

	/** 是否已寄送旗標 */
	@Column(name = "is_send")
	@Type(type = "yes_no")
	private boolean send = false;

	/** 重試次數, if retry 3 time, this mail will be hold. */
	@Column(name = "mail_retry")
	private int retry = 0;

	/** 優先序 */
	@Column(name = "mail_level")
	private int sort = LEVEL_NORMAL;

	/** 內崁物件 */
	@ElementCollection
	@JoinTable(name = "comm_mail_inline", joinColumns = @JoinColumn(name = "mail_oid"))
	@MapKeyColumn(name = "file_name")
	@Column(name = "file_location")
	private Map<String, String> inlines;

	/** 附件 */
	@ElementCollection
	@JoinTable(name = "comm_mail_attach", joinColumns = @JoinColumn(name = "mail_oid"))
	@MapKeyColumn(name = "content_id")
	@Column(name = "file_loc")
	private Map<String, String> attachments;

	/** 寄送時間 */
	@Column(name = "send_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendDT;

	@Column(name = "mail_func_code", length = 50)
	private String functionCode;

	/** 是否有附檔 */
	@Column(name = "has_attachment")
	@Type(type = "yes_no")
	private boolean hasAttachment = false;

	/** default constructors */
	@SuppressWarnings("unused")
	private Mail() {
	}

	/** default constructors */
	public Mail(String subject, String body, String to, String cc) {
		this.subject = subject;
		this.body = body;
		this.to = to;
		this.cc = cc;
		this.sendDT = Calendar.getInstance().getTime();
	}

	/**
	 * @return the hasAttachment
	 */
	public boolean isHasAttachment() {
		return hasAttachment;
	}

	/**
	 * @param hasAttachment the hasAttachment to set
	 */
	public void setHasAttachment(boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	/**
	 * @return the bcc
	 */
	public String getBcc() {
		return bcc;
	}

	/**
	 * @param bcc the bcc to set
	 */
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the cc
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * @param cc the cc to set
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * @return the attachments
	 */
	public Map<String, String> getAttachments() {
		if (attachments == null) {
			attachments = new HashMap<String, String>();
		}
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(Map<String, String> attachments) {
		this.attachments = attachments;
	}

	/**
	 * @return the inlines
	 */
	public Map<String, String> getInlines() {
		if (inlines == null) {
			inlines = new HashMap<String, String>();
		}
		return inlines;
	}

	/**
	 * @param inlines the inlines to set
	 */
	public void setInlines(Map<String, String> inlines) {
		this.inlines = inlines;
	}

	/**
	 * @return the oid
	 */
	public Long getOid() {
		return oid;
	}

	/**
	 * @param oid the oid to set
	 */
	public void setOid(Long oid) {
		this.oid = oid;
	}

	/**
	 * @return the retry
	 */
	public int getRetry() {
		return retry;
	}

	/**
	 * @param retry the retry to set
	 */
	public void setRetry(int retry) {
		this.retry = retry;
	}

	/**
	 * @return the send
	 */
	public boolean isSend() {
		return send;
	}

	/**
	 * @param send the send to set
	 */
	public void setSend(boolean send) {
		this.send = send;
	}

	/**
	 * @return the sendDT
	 */
	public Date getSendDT() {
		return sendDT;
	}

	/**
	 * @param sendDT the sendDT to set
	 */
	public void setSendDT(Date sendDT) {
		this.sendDT = sendDT;
	}

	/**
	 * @return the sort
	 */
	public int getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the functionCode
	 */
	public String getFunctionCode() {
		return functionCode;
	}

	/**
	 * @param functionCode the functionCode to set
	 */
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public Mail addBcc(String name, String email) {
		if (StringUtils.isNotBlank(email)) {
			if (StringUtils.isNotBlank(bcc)) {
				bcc += ",";
			} else {
				bcc = "";
			}
			if (StringUtils.isNotBlank(name)) {
				bcc += "\"" + name + "\" <" + email + ">";
			} else {
				bcc += "<" + email + ">";
			}
		}
		return this;
	}

	public Mail addTo(String name, String email) {
		if (StringUtils.isNotBlank(email)) {
			if (StringUtils.isNotBlank(to)) {
				to += ",";
			} else {
				to = "";
			}
			if (StringUtils.isNotBlank(name)) {
				to += "\"" + name + "\" <" + email + ">";
			} else {
				to += "<" + email + ">";
			}
		}
		return this;
	}

	public Mail addCc(String name, String email) {
		if (StringUtils.isNotBlank(email)) {
			if (StringUtils.isNotBlank(cc)) {
				cc += ",";
			} else {
				cc = "";
			}
			if (StringUtils.isNotBlank(name)) {
				cc += "\"" + name + "\" <" + email + ">";
			} else {
				cc += "<" + email + ">";
			}
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Mail [oid=");
		builder.append(oid);
		builder.append(", retry=");
		builder.append(retry);
		builder.append(", sendDT=");
		builder.append(sendDT);
		builder.append(", subject=");
		builder.append(subject);
		builder.append(", to=");
		builder.append(to);
		builder.append(", send=");
		builder.append(send);
		builder.append("]");
		return builder.toString();
	}
}
