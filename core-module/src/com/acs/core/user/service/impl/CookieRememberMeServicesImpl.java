/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.service.impl.CookieRememberMeServicesImpl
   Module Description   :

   Date Created      : 2012/11/26
   Original Author   : tw4149
   Team              : 
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.user.service.impl;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;

import com.acs.core.common.utils.PasswordUtils;
import com.acs.core.user.entity.User;
import com.acs.core.user.entity.UserDetails;
import com.acs.core.user.service.UserService;

/**
 * @author tw4149
 * 
 */
public class CookieRememberMeServicesImpl extends AbstractRememberMeServices {
	private String path = "/";
	private String domain = "telexpress.com";
	private String encryKey = "qwe123!@#";
	@Resource
	private UserService userService;

	/** default constructor */
	public CookieRememberMeServicesImpl() {
		super();
	}

	/**
	 * @param path
	 *           the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @param domain
	 *           the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @param encryKey
	 *           the encryKey to set
	 */
	public void setEncryKey(String encryKey) {
		this.encryKey = encryKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.springframework.security.ui.rememberme.AbstractRememberMeServices#onLoginSuccess(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.Authentication)
	 */
	@Override
	protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication successfulAuthentication) {
		String username = successfulAuthentication.getName();

		logger.debug("Creating new persistent login for user " + username);

		try {
			String password = request.getParameter("j_password");
			if (StringUtils.isBlank(password)) {
				password = successfulAuthentication.getCredentials().toString();
			}
			addCookie(username, password, request, response);
			HttpSession s = null;
			// 如果不是 remote, 不存在 session create 一個, 否則沿用舊 session
			// 若是 remote 為避免 session 過多, 不允許新建 session(不需要)
			if (request.getRequestURI().startsWith(request.getContextPath() + "/remote")) {
				s = request.getSession(false);
			} else {
				s = request.getSession();
			}
			if (s != null) {
				s.setAttribute("Login", username);
			}
		} catch (DataAccessException e) {
			logger.error("Failed to save persistent token ", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.ui.rememberme.AbstractRememberMeServices#processAutoLoginCookie(java.lang.String[],
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request,
			HttpServletResponse response) throws RememberMeAuthenticationException, UsernameNotFoundException {
		if (cookieTokens.length < 2) {
			throw new InvalidCookieException("Cookie token did not contain " + 2 + " tokens, but contained '"
					+ Arrays.asList(cookieTokens) + "'");
		}

		final String username = cookieTokens[0];
		final String password = PasswordUtils.decodePassword(cookieTokens[1], encryKey);

		if (StringUtils.isBlank(username)) {
			throw new RememberMeAuthenticationException("login fail, username is blank");
		}
		User user = userService.get(username);

		if (user == null) {
			throw new RememberMeAuthenticationException("login fail, user not found");
		}

		if (!userService.validatePassword(user, password)) {
			throw new RememberMeAuthenticationException("login fail, password incorrect");
		}

		HttpSession s = null;
		if (request.getRequestURI().startsWith(request.getContextPath() + "/remote")) {
			s = request.getSession(false);
		} else {
			s = request.getSession();
		}
		if (s != null) {
			s.setAttribute("Login", username);
		}
		return user;
	}

	private void addCookie(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		setCookie(new String[] { username, PasswordUtils.encodePassword(password, encryKey) }, getTokenValiditySeconds(),
				request, response);
	}

	@Override
	protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Cancelling cookie");
		Cookie cookie = new Cookie(getCookieName(), null);
		cookie.setMaxAge(0);
		if (StringUtils.isBlank(path)) {
			cookie.setPath(StringUtils.defaultString(request.getContextPath(), "/"));
		} else {
			cookie.setPath(path);
		}
		if (StringUtils.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}

		response.addCookie(cookie);

		HttpSession s = null;
		if (request.getRequestURI().startsWith(request.getContextPath() + "/remote")) {
			s = request.getSession(false);
		} else {
			s = request.getSession();
		}
		if (s != null) {
			s.setAttribute("Login", null);
		}
	}

	@Override
	protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
		String cookieValue = encodeCookie(tokens);
		Cookie cookie = new Cookie(getCookieName(), cookieValue);
		cookie.setMaxAge(maxAge);
		if (StringUtils.isBlank(path)) {
			cookie.setPath(StringUtils.defaultString(request.getContextPath(), "/"));
		} else {
			cookie.setPath(path);
		}
		if (StringUtils.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}
}
