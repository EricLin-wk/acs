/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.entity.Group
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.acs.core.common.entity.BaseEntity;
import com.acs.core.common.utils.StringUtils;

/**
 * @author tw4149
 * 
 */
@Entity
@Table(name = "COMM_GROUP")
public class Group extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -594473934335957847L;

	public enum Type {
		OSA("O.SA"), TE("Telexpress");
		String desc;

		Type(String desc) {
			this.desc = desc;
		}
	}

	@Id
	@GeneratedValue(generator = "assigned")
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(name = "GROUP_CODE", length = 50)
	private String code;

	@Column(name = "GROUP_DESC", length = 30)
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GROUP_MANAGER")
	@NotFound(action = NotFoundAction.IGNORE)
	@Cascade({ CascadeType.ALL })
	private Role manager;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GROUP_ROLE")
	@NotFound(action = NotFoundAction.IGNORE)
	@Cascade({ CascadeType.ALL })
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_GROUP")
	@NotFound(action = NotFoundAction.IGNORE)
	private Group parentGroup;

	@Column(name = "PARENT_GROUP", insertable = false, updatable = false)
	private String parentGroupName;

	/**
	 * Y for yaodian100, S for supplier, C for CRM
	 */
	@Column(name = "GROUP_TYPE", length = 3)
	private String type = Type.TE.name();

	/** default constructors */
	public Group() {
		super();
	}

	/** default constructors */
	public Group(String code, String description) {
		super();
		this.code = code;
		this.description = description;
		this.manager = new Role(code, description, Role.Type.MANAGER);
		this.role = new Role(code, description, Role.Type.GROUP);
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *           the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	@Transient
	public Type getTypeEnum() {
		return Type.valueOf(type);
	}

	@Transient
	public void setTypeEnum(Type type) {
		if (type != null) {
			this.type = type.name();
		}
	}

	/**
	 * @return the parentGroupName
	 */
	public String getParentGroupName() {
		return parentGroupName;
	}

	/**
	 * @param parentGroupName
	 *           the parentGroupName to set
	 */
	public void setParentGroupName(String parentGroupName) {
		this.parentGroupName = parentGroupName;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *           the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	public String getCodeDesc() {
		return code + "/" + description;
	}

	/**
	 * @param description
	 *           the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the mainRole
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param mainRole
	 *           the mainRole to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the manager
	 */
	public Role getManager() {
		return manager;
	}

	/**
	 * @param manager
	 *           the manager to set
	 */
	public void setManager(Role manager) {
		this.manager = manager;
	}

	/**
	 * @return the parentGroup
	 */
	public Group getParentGroup() {
		return parentGroup;
	}

	/**
	 * @param parentGroup
	 *           the parentGroup to set
	 */
	public void setParentGroup(Group parentGroup) {
		this.parentGroup = parentGroup;
	}

	@Transient
	public String getTrimCode() {
		String trimCode = new String(code);
		if (StringUtils.isNotBlank(trimCode)) {
			do {
				if (trimCode.endsWith("-0") || trimCode.endsWith("_0")) {
					trimCode = trimCode.substring(0, trimCode.length() - 2);
				}
				if (trimCode.endsWith("-00") || trimCode.endsWith("_00")) {
					trimCode = trimCode.substring(0, trimCode.length() - 3);
				}
				if (trimCode.endsWith("-000") || trimCode.endsWith("_000")) {
					trimCode = trimCode.substring(0, trimCode.length() - 4);
				}
			} while (trimCode.endsWith("-0") || trimCode.endsWith("-00") || trimCode.endsWith("-000")
					|| trimCode.endsWith("_0") || trimCode.endsWith("_00") || trimCode.endsWith("_000"));
		}
		return trimCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Group [code=");
		builder.append(code);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}
}
