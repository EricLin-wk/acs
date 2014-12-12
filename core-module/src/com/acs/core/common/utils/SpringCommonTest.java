/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.utils.SpringCommonTest
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
package com.acs.core.common.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.acs.core.user.entity.User;

/**
 * @author tw4149
 */
@Ignore
public class SpringCommonTest {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private static SessionFactory sessionFactory;
	protected static ClassPathXmlApplicationContext ctx = null;

	public static void configCtx() {
		try {
			ctx = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml", "applicationContext-*.xml" });
			String username = System.getenv("USERNAME");
			if (StringUtils.isBlank(username)) {
				username = System.getenv("USER");
			}
			if (StringUtils.isBlank(username)) {
				username = "test00";
			}
			if (ctx != null && ctx.getBean("sessionFactory") != null) {
				sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
			}
			User tester = new User(username);
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(tester, "password"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Before
	public void setUpMethod() throws Exception {
		if (sessionFactory != null) {
			Session session = sessionFactory.openSession();
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}
	}

	@After
	public void tearDownMethod() throws Exception {
		if (sessionFactory != null) {
			SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
			SessionFactoryUtils.closeSession(sessionHolder.getSession());
		}
	}
}
