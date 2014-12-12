/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.entity.BaseEntity
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
package com.acs.core.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author tw4149
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -4882210405882397313L;
	@Column(name = "ADD_USER", length = 30, updatable = false)
	private String createUser;

	@Column(name = "ADD_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "MOD_USER", length = 30)
	private String modifyUser;

	@Version
	@Column(name = "MOD_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;

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

	/**
	 * @return the modifyUser
	 */
	public String getModifyUser() {
		return modifyUser;
	}

	/**
	 * @param modifyUser the modifyUser to set
	 */
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	/**
	 * @return the modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate the modifyDate to set
	 */
	@SuppressWarnings("unused")
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseEntity [createUser=" + createUser + ", createDate=" + createDate + ", modifyUser=" + modifyUser
				+ ", modifyDate=" + modifyDate + "]";
	}
}
