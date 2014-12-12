/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.template.service.impl.UrlTemplateServiceImpl
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
package com.acs.core.template.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.core.common.exception.CoreException;
import com.acs.core.common.utils.StringUtils;
import com.acs.core.mail.entity.Mail;
import com.acs.core.template.service.TemplateService;

/**
 * @author tw4149
 * 
 */
public class UrlTemplateServiceImpl implements TemplateService, Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 7865637072400975228L;

	/** logger */
	private final Logger logger = LoggerFactory.getLogger(getClass());
	/** encoding, default: big5 */
	private String encoding = "big5";
	/** templateMapping, for (template name, url) */
	private Map templateMapping = new HashMap();
	/** PROXY_HOST, defaut: null (none use) */
	private String proxyHost = null;
	/** PROXY_PORT, default: 8080 */
	private int proxyPort = 8080;
	/** timeOut (second) */
	private int timeout = 30;

	/** default constructors */
	public UrlTemplateServiceImpl() {
		super();
	}

	/**
	 * @param encoding
	 *           the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @param templateMapping
	 *           the templateMapping to set
	 */
	public void setTemplateMapping(Map templateMapping) {
		this.templateMapping = templateMapping;
	}

	/**
	 * @param proxyHost
	 *           the proxyHost to set
	 */
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	/**
	 * @param proxyPort
	 *           the proxyPort to set
	 */
	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	/**
	 * @param timeout
	 *           the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acs.core.template.service.TemplateService#format(java.lang.String, java.util.Map)
	 */
	public String format(String templateName, Map objs) throws CoreException {
		String result = "";
		GetMethod method = null;
		try {
			String uri = "";
			if (templateMapping.get(templateName) != null) {
				uri = (String) templateMapping.get(templateName);
			} else {
				uri = templateName;
			}
			if (objs != null) {
				Iterator it = objs.keySet().iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					if (uri.indexOf("?") != -1) {
						uri += ("&" + key + "=" + URLEncoder.encode(objs.get(key).toString(), encoding));
					} else {
						uri += ("?" + key + "=" + URLEncoder.encode(objs.get(key).toString(), encoding));
					}
				}
			}
			method = new GetMethod(uri);
			if (encoding != null) {
				method.addRequestHeader("Content-Type", "text/html; charset=" + encoding);
			}

			HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());

			if (timeout > 0) {
				client.getParams().setSoTimeout(timeout);
			}

			if (StringUtils.isNotEmpty(proxyHost)) {
				client.getHostConfiguration().setProxy(proxyHost, proxyPort);
			}
			logger.info("In:{}", uri);
			int httpRes = client.executeMethod(method);
			if (httpRes != 200) {
				throw new CoreException("errors.template.url", uri);
			}

			byte[] b = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedInputStream bis = new BufferedInputStream(method.getResponseBodyAsStream());
			int len = 0;
			while ((len = bis.read(b, 0, 1024)) != -1) {
				baos.write(b, 0, len);
			}

			baos.flush();
			logger.debug("encoding:{}", client.getParams().getContentCharset());

			if (encoding == null) {
				result = baos.toString();
			} else {
				result = baos.toString(encoding);
			}
			bis.close();
			baos.close();
			logger.debug("Out[{}]:{}", httpRes, result);

		} catch (IOException e) {
			throw new CoreException("errors.template.url", e, templateName);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acs.core.template.service.TemplateService#formatByString(java.lang.String, java.lang.String[],
	 * java.lang.Object[])
	 */
	public String formatByString(String template, String[] name, Object[] values) throws CoreException {
		throw new CoreException("errors.template.nosupport", "URL");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acs.core.template.service.TemplateService#formatByStringTemplate(java.lang.String, java.util.Map)
	 */
	public String formatByStringTemplate(String template, Map objs) throws CoreException {
		throw new CoreException("errors.template.nosupport", "URL");
	}

	public Mail formatToMail(String templateName, Map objs) throws CoreException {
		String body = format(templateName, objs);
		String subject = StringUtils.parseTitle(body);
		Mail mail = new Mail(subject, body, null, null);
		return mail;
	}

}
