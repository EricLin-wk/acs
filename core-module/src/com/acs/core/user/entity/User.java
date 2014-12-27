/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.entity.User
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author tw4149
 */
@Entity
@Table(name = "comm_user")
public class User extends UserDetails {

	/** serialVersionUID */
	private static final long serialVersionUID = -7363505940096103003L;

	public final static String STATUS_ACTIVE = "9";
	public final static String STATUS_LOCK = "1";
	public final static String STATUS_INACTIVE = "0";

	/** username */
	@Id
	@GeneratedValue(generator = "assigned")
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(name = "user_name")
	private String username;

	/** password */
	@Column(name = "user_password", length = 100, nullable = false)
	private String password;

	/** group */
	@ManyToOne
	@JoinColumn(name = "group_code")
	@NotFound(action = NotFoundAction.IGNORE)
	@LazyToOne(LazyToOneOption.FALSE)
	private Group group;

	@Column(name = "group_code", insertable = false, updatable = false)
	private String groupCode;

	/** roles */
	@ManyToMany(targetEntity = Role.class)
	@JoinTable(name = "comm_user_role", joinColumns = @JoinColumn(name = "user_name"), inverseJoinColumns = @JoinColumn(
			name = "role_name"))
	@NotFound(action = NotFoundAction.IGNORE)
	@OrderBy("name")
	private Set<Role> roles;

	@OneToMany(targetEntity = UserPermission.class, mappedBy = "user", orphanRemoval = true)
	@MapKey(name = "permissionKey")
	@Cascade({ CascadeType.SAVE_UPDATE })
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Map<String, UserPermission> permissions;

	@Column(name = "user_status", length = 1, nullable = false)
	private String status = STATUS_ACTIVE;

	@Column(name = "login_ip", length = 15)
	private String loginIP;

	@Column(name = "user_lang", length = 5)
	private String lang = "zh_TW";

	@Column(name = "error_count")
	private int errorCount = 0;

	@Column(name = "is_password_need_change")
	@Type(type = "yes_no")
	private boolean needChangePassword = false;

	@Column(name = "is_acc_not_expire")
	@Type(type = "yes_no")
	private boolean accountNonExpired = true;

	@Column(name = "is_acc_not_lock")
	@Type(type = "yes_no")
	private boolean accountNonLocked = true;

	@Column(name = "is_password_not_expire")
	@Type(type = "yes_no")
	private boolean credentialsNonExpired = true;

	@Column(name = "first_name", length = 30)
	private String nameFirst;

	@Column(name = "last_name", length = 30)
	private String nameLast;

	@Column(name = "nick_name", length = 50)
	private String nameNick;

	@Column(name = "native_name", length = 50, nullable = false)
	private String nameNative;

	@Column(name = "user_phone", length = 50)
	private String phone;

	@Column(name = "user_mobile", length = 50)
	private String mobile;

	@Column(name = "user_email", length = 50)
	private String email;

	/** default constructors */
	private User() {
		super("");
	}

	/** default constructors */
	public User(String username) {
		super(username);
		this.username = username;
	}

	/** default constructor */
	public User(String username, String nameNative, String email) {
		super(username);
		this.username = username;
		this.nameNative = nameNative;
		this.email = email;
	}

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		if (roles == null) {
			roles = new HashSet();
		}
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return the permissions
	 */
	public Map<String, UserPermission> getPermissions() {
		if (permissions == null) {
			permissions = new HashMap<String, UserPermission>();
		}
		return permissions;
	}

	/**
	 * @return the permissions
	 */
	public Map<String, UserPermission> getPermissions(Permission.Type type) {
		Map<String, UserPermission> result = new HashMap<String, UserPermission>();
		if (type == null) {
			result.putAll(getPermissions());
		} else {
			for (String k : getPermissions().keySet()) {
				UserPermission up = getPermissions().get(k);
				if (type.equals(up.getPermissionType())) {
					result.put(k, up);
				}
			}
		}
		return result;
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(Map<String, UserPermission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupCode;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupCode = groupName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the errorCount
	 */
	public int getErrorCount() {
		return errorCount;
	}

	/**
	 * @param errorCount the errorCount to set
	 */
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	/**
	 * @return the loginIP
	 */
	public String getLoginIP() {
		return loginIP;
	}

	/**
	 * @param loginIP the loginIP to set
	 */
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the nameFirst
	 */
	public String getNameFirst() {
		return nameFirst;
	}

	/**
	 * @param nameFirst the nameFirst to set
	 */
	public void setNameFirst(String nameFirst) {
		this.nameFirst = nameFirst;
	}

	/**
	 * @return the nameLast
	 */
	public String getNameLast() {
		return nameLast;
	}

	/**
	 * @param nameLast the nameLast to set
	 */
	public void setNameLast(String nameLast) {
		this.nameLast = nameLast;
	}

	/**
	 * @return the nameNative
	 */
	public String getNameNative() {
		return nameNative;
	}

	/**
	 * @param nameNative the nameNative to set
	 */
	public void setNameNative(String nameNative) {
		this.nameNative = nameNative;
	}

	public String getCodeName() {
		return username + "/" + nameNative;
	}

	/**
	 * @return the nameNick
	 */
	public String getNameNick() {
		return nameNick;
	}

	/**
	 * @param nameNick the nameNick to set
	 */
	public void setNameNick(String nameNick) {
		this.nameNick = nameNick;
	}

	/**
	 * @return the needChangePassword
	 */
	public boolean isNeedChangePassword() {
		return needChangePassword;
	}

	/**
	 * @param needChangePassword the needChangePassword to set
	 */
	public void setNeedChangePassword(boolean needChangePassword) {
		this.needChangePassword = needChangePassword;
	}

	/**
	 * @return the password
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the userid
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * @param userid the userid to set
	 */
	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.userdetails.UserDetails#getAuthorities()
	 */
	@Override
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		Iterator<UserPermission> it = getPermissions().values().iterator();
		while (it.hasNext()) {
			auths.add(it.next());
		}
		return auths;
	}

	/**
	 * @return the accountNonExpired
	 */
	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	/**
	 * @param accountNonExpired the accountNonExpired to set
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * @return the accountNonLocked
	 */
	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	/**
	 * @param accountNonLocked the accountNonLocked to set
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	/**
	 * @return the credentialsNonExpired
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * @param credentialsNonExpired the credentialsNonExpired to set
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return STATUS_ACTIVE.equals(status);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("username", this.username)
				.append("status", this.status).append("group", this.group).toString();
	}
}
