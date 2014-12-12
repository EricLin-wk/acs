/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.mail.RemoteMailService
   Module Description   :

   Date Created      : 2013/8/23
   Original Author   : tw4149
   Team              : 
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.mail.remote;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author tw4149
 * 
 */
@WebService
public interface RemoteMailService {
	@WebMethod(action = "http://remote.mail.core.teecs.com/sendMail")
	public String sendMail(int i);

	@WebMethod(action = "http://remote.mail.core.teecs.com/sendTestMail")
	public String sendTestMail(int i);
}
