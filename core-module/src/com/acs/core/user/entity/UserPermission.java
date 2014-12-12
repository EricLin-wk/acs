/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.entity.UserPermission
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
package com.acs.core.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;

import com.acs.core.common.utils.StringUtils;

/**
 * @author tw4149
 * 
 */
@Entity
@Table(name = "COMM_USER_PERMISSION", uniqueConstraints = { @UniqueConstraint(columnNames = { "USER_NAME",
		"PERMISSION_KEY" }) })
public class UserPermission implements GrantedAuthority {

	/** serialVersionUID */
	private static final long serialVersionUID = 7948808444125235044L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_userprmn")
	@SequenceGenerator(name = "seq_userprmn", sequenceName = "SEQ_USER_PERMISSION")
	@Column(name = "OID")
	private Long oid;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name = "USER_NAME")
	private User user;

	@Column(name = "PERMISSION_KEY", length = 50)
	private String permissionKey;

	@Enumerated
	@Column(name = "PERMISSION_TYPE", updatable = false, nullable = false)
	private Permission.Type permissionType;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	/** default constructors */
	@SuppressWarnings("unused")
	private UserPermission() {
	}

	/** default constructors */
	public UserPermission(User user, String permissionKey, Permission.Type permissionType) {
		super();
		this.user = user;
		this.permissionKey = permissionKey;
		this.permissionType = permissionType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.GrantedAuthority#getAuthority()
	 */
	public String getAuthority() {
		return permissionKey;
	}

	/**
	 * @return the permissionType
	 */
	public Permission.Type getPermissionType() {
		return permissionType;
	}

	/**
	 * @param permissionType
	 *           the permissionType to set
	 */
	public void setPermissionType(Permission.Type permissionType) {
		this.permissionType = permissionType;
	}

	/**
	 * @return the oid
	 */
	public Long getOid() {
		return oid;
	}

	/**
	 * @param oid
	 *           the oid to set
	 */
	private void setOid(Long oid) {
		this.oid = oid;
	}

	/**
	 * @return the user
	 */
	private User getUser() {
		return user;
	}

	/**
	 * @param user
	 *           the user to set
	 */
	private void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the permissionKey
	 */
	public String getPermissionKey() {
		return permissionKey;
	}

	/**
	 * @return the permissionCode
	 */
	@Transient
	public String getPermissionCode() {
		String code = "";
		if (StringUtils.isNotBlank(permissionKey) && permissionType != null) {
			code = StringUtils.removeStart(permissionKey, permissionType.name() + "_");
		}
		return code;
	}

	/**
	 * @param permissionKey
	 *           the permissionKey to set
	 */
	public void setPermissionKey(String permissionKey) {
		this.permissionKey = permissionKey;
	}

	/**
	 * @return the endDT
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDT
	 *           the endDT to set
	 */
	public void setEndDate(Date endDT) {
		this.endDate = endDT;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("permissionKey", this.permissionKey)
				.append("oid", this.oid).toString();
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		UserPermission myClass = (UserPermission) object;
		return new CompareToBuilder().append(this.permissionKey, myClass.permissionKey)
				.append(this.permissionType, myClass.permissionType).append(this.user, myClass.user).toComparison();
	}
}
