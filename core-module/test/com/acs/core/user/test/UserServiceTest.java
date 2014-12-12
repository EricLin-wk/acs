/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : prepare.PrepareService
   Module Description   :

   Date Created      : 2012/12/17
   Original Author   : tw4149
   Team              :
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.user.test;

import org.junit.BeforeClass;
import org.junit.Test;

import com.acs.core.common.utils.SpringCommonTest;
import com.acs.core.user.entity.User;
import com.acs.core.user.service.UserService;

/**
 * @author tw4149
 */
public class UserServiceTest extends SpringCommonTest {
	private static UserService userService;

	private static final String MAIN_GROUP_ID = "GRP000";
	private static final String ROLE_USER = "USER";
	private static final String ROLE_HR = "HR";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		configCtx();
		userService = (UserService) ctx.getBean("userService");
	}

	@Test
	public void deleteUser() {
		User user = userService.get("admin");
		if (user != null) {
			user.getPermissions().clear();
			userService.delete(user);
		}
	}

	@Test
	public void resetPassword() {
		User user = userService.get("admin");
		if (user != null) {
			userService.resetPassword(user, "password", false);
		}
	}

}
