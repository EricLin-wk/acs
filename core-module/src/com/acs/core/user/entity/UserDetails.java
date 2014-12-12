/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.entity.UserDetail
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

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.acs.core.common.entity.BaseEntity;

/**
 * @author tw4149
 * 
 */
public class UserDetails extends BaseEntity implements org.springframework.security.core.userdetails.UserDetails {
	/** serialVersionUID */
	private static final long serialVersionUID = 3457805250227875352L;
	private String username;
	private String password;
	private List<GrantedAuthority> authorities;

	/** default constructor */
	private UserDetails() {
	}

	/** default constructor */
	public UserDetails(String username) {
		super();
		this.username = username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *           the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *           the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the authorities
	 */
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities
	 *           the authorities to set
	 */
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}
