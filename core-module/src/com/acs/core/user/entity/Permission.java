/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.entity.Permission
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
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import com.acs.core.common.entity.BaseEntity;

/**
 * @author tw4149
 */
@Entity
@Table(name = "comm_permission")
@NamedQuery(name = "searchPermissions", query = "from Permission o where o.key like :key and o.key in (:keys)")
public class Permission extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 6591542034805831810L;

	public enum Type {
		/** 系统 */
		SYSTEM("系统"),
		/** 服务 */
		SERVICE("服务"),
		/** 角色 */
		ROLE("角色"),
		/** 群组 */
		GROUP("群组"),
		/** 其他 */
		OTHER("其他"),
		/** 主管 */
		MANAGER("主管");
		String desc;

		Type(String desc) {
			this.desc = desc;
		}

		public String getCode() {
			return name();
		}

		public String getDesc() {
			return desc;
		}
	};

	@Column(name = "permission_key", length = 30, updatable = false, nullable = false)
	private String key;

	@Id
	@GeneratedValue(generator = "assigned")
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(name = "permission_name", length = 50)
	private String name;

	@Enumerated
	@Column(name = "permission_type", updatable = false, nullable = false)
	private Type type = Type.OTHER;

	@Column(name = "permission_desc", length = 200)
	private String description;

	/** default constructors */
	public Permission() {
		super();
	}

	/** default constructors */
	public Permission(String key, String description, Type type) {
		this.key = key;
		this.description = description;
		this.type = type;
		this.setName((type.name() + "_" + key).toUpperCase());// NOPMD
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the permissionKey
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param permissionKey the permissionKey to set
	 */
	public void setKey(String permissionKey) {
		this.key = permissionKey;
	}

	/**
	 * @return the key/description
	 */
	public String getKeyDesc() {
		return key + "/" + description;
	}

	/**
	 * @return the name/description
	 */
	public String getNameDesc() {
		return name + "/" + description;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		if (name == null) {
			name = (type.name() + "_" + key).toUpperCase();
		}
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("key", this.key)
				.append("type", this.type).toString();
	}
}
