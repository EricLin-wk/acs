/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.entity.Role
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

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import com.acs.core.common.entity.BaseEntity;

/**
 * @author tw4149
 */
@Entity
@Table(name = "comm_role")
public class Role extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8435790963679708952L;

	public enum Type {
		/** 系统 */
		SYSTEM("系统"),
		/** 其他 */
		OTHER("其他"),
		/** 群组 */
		GROUP("群组"),
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

	public enum Default {
		/** 前台会员 */
		MEMBER("前台会员"),
		/** 一般使用者 */
		USR("一般使用者");
		String desc;

		Default(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}
	}

	@Column(name = "ROLE_KEY", length = 30)
	private String key;

	@Id
	@GeneratedValue(generator = "assigned")
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(name = "role_name", length = 50)
	private String name;

	@Column(name = "role_desc", length = 30)
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "main_permission_key")
	@Cascade({ CascadeType.ALL })
	private Permission mainPermission;

	/** type */
	@Enumerated
	@Column(name = "role_type", updatable = false, nullable = false)
	private Type type = Type.OTHER;

	@ManyToMany(cascade = { javax.persistence.CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "comm_user_role_permission", joinColumns = @JoinColumn(name = "role_name"),
			inverseJoinColumns = @JoinColumn(name = "permission_name"))
	@MapKey(name = "name")
	private Map<String, Permission> permissions;

	/** default constructors */
	public Role() {
		super();
	}

	/** default constructors */
	public Role(String key, String description, Role.Type type) {
		this.key = key;
		this.type = type;
		this.description = description;
		this.setName((type.name() + "_" + key).toUpperCase());
		switch (type) {
		case GROUP:
			this.mainPermission = new Permission(key, description, Permission.Type.GROUP);
		break;
		case MANAGER:
			this.mainPermission = new Permission(key, description, Permission.Type.MANAGER);
		break;
		default:
			this.mainPermission = new Permission(key, description, Permission.Type.ROLE);
		}
		this.getPermissions().put(mainPermission.getName(), mainPermission);
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
	private void setName(String name) {
		this.name = name;
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
	@SuppressWarnings("unused")
	private void setType(Type type) {
		this.type = type;
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
	 * @return the permissions
	 */
	public Map<String, Permission> getPermissions() {
		if (permissions == null) {
			permissions = new HashMap<String, Permission>();
		}
		return permissions;
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(Map<String, Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the key/description
	 */
	public String getKeyDesc() {
		return key + "/" + description;
	}

	/**
	 * @param key the key to set
	 */
	@SuppressWarnings("unused")
	private void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the mainPermission
	 */
	public Permission getMainPermission() {
		return mainPermission;
	}

	/**
	 * @param mainPermission the mainPermission to set
	 */
	public void setMainPermission(Permission mainPermission) {
		this.mainPermission = mainPermission;
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
