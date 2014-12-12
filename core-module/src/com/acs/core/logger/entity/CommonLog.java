/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.logger.entity.CommonLog
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
package com.acs.core.logger.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author tw4149
 */
@Entity
@Table(name = "COMM_LOG_COMMON")
public class CommonLog implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -7817437965784149396L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_commonlog")
	@SequenceGenerator(name = "seq_commonlog", sequenceName = "SEQ_LOG_COMMON")
	@Column(name = "OID")
	private Long oid;

	@Column(name = "SERVICE_NAME", length = 200)
	private String serviceName;

	@Column(name = "SERVICE_METHOD", length = 100)
	private String serviceMethod;

	@Lob
	@Column(name = "LOG_MESSAGE")
	private String message;

	@Column(name = "CREATE_USER", length = 30)
	private String createUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate;

	/** default constructor */
	public CommonLog() {
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
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the serviceMethod
	 */
	public String getServiceMethod() {
		return serviceMethod;
	}

	/**
	 * @param serviceMethod the serviceMethod to set
	 */
	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
