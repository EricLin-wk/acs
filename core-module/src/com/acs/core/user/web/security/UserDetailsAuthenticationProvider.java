/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.web.security.UserDetailsAuthenticationProvider
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
package com.acs.core.user.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.acs.core.common.exception.CoreException;
import com.acs.core.user.entity.User;
import com.acs.core.user.service.UserService;

/**
 * @author tw4149
 * 
 */
public class UserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private UserService userService;

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider#
	 * additionalAuthenticationChecks(org.springframework.security.core.userdetails.UserDetails,
	 * org.springframework.security.authentication.UsernamePasswordAuthenticationToken)
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider#retrieveUser(java.lang
	 * .String, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)
	 */
	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		String remoteIp = null;
		try {
			User user = userService.get(username);

			Object detail = authentication.getDetails();
			if (detail instanceof WebAuthenticationDetails) {
				WebAuthenticationDetails webDetail = (WebAuthenticationDetails) detail;
				remoteIp = webDetail.getRemoteAddress();
			}

			if (user == null) {
				logger.error("user not found, username:" + username + ",ip:" + remoteIp);
				throw new UsernameNotFoundException("username:" + username + ",ip:" + remoteIp);
			} else {
				if (!userService.validatePassword(user, authentication.getCredentials().toString())) {
					if (user.getErrorCount() > 5) {
						logger.error("password incorrect, username:" + username + ",ip:" + remoteIp + ",count:"
								+ user.getErrorCount());
					} else {
						logger.warn("password incorrect, username:" + username + ",ip:" + remoteIp);
					}
					throw new BadCredentialsException(messages.getMessage("error.admin.badCredentials", "帐号/密码错误"), user);
				}
			}

			return user;
		} catch (CoreException e) {
			if ("errors.account.lock".equals(e.getMessage())) {
				logger.error("user locked, username:" + username + ",ip:" + remoteIp);
				throw new LockedException(messages.getMessage(e.getMessage(), "帐号已被锁定, 请联络系统管理员"));
			} else {
				e.printStackTrace();
				logger.error("exception, username:" + username + ",ip:" + remoteIp, e);
				throw new BadCredentialsException(messages.getMessage(e.getMessage(), "帐号/密码错误"));
			}
		}
	}

}
