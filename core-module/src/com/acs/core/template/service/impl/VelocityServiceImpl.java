/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.template.service.impl.VelocityServiceImpl
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

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.core.common.exception.CoreException;
import com.acs.core.common.utils.ServerValue;
import com.acs.core.common.utils.StringUtils;
import com.acs.core.mail.entity.Mail;
import com.acs.core.template.service.TemplateService;

/**
 * @author tw4149
 * 
 */
public class VelocityServiceImpl implements TemplateService {

	/** logger */
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private String pathPrefix = "velocity/";

	/** default constructors */
	public VelocityServiceImpl() {
		try {
			Properties properties = System.getProperties();

			if (properties.get("resource.loader") == null) {
				properties.put("resource.loader", "classpath");
			}
			if (properties.get("classpath.resource.loader.class") == null) {
				properties.put("classpath.resource.loader.class",
						"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			}
			if (properties.get("input.encoding") == null) {
				properties.put("input.encoding", "UTF-8");
			}
			if (properties.get("output.encoding") == null) {
				properties.put("output.encoding", "UTF-8");
			}
			if (properties.get("runtime.log.logsystem.class") == null) {
				properties.put("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
			}
			Velocity.init(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** default constructors */
	public VelocityServiceImpl(Properties properties) {
		try {
			if (properties.get("resource.loader") == null) {
				properties.put("resource.loader", "classpath");
			}
			if (properties.get("classpath.resource.loader.class") == null) {
				properties.put("classpath.resource.loader.class",
						"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			}
			if (properties.get("input.encoding") == null) {
				properties.put("input.encoding", "UTF-8");
			}
			if (properties.get("output.encoding") == null) {
				properties.put("output.encoding", "UTF-8");
			}
			if (properties.get("runtime.log.logsystem.class") == null) {
				properties.put("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
			}
			Velocity.init(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param pathPrefix
	 */
	public void setPathPrefix(String pathPrefix) {
		this.pathPrefix = pathPrefix;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acs.core.template.service.TemplateService#format(java.lang.String, java.util.Map)
	 */
	public String format(String templateName, Map objs) throws CoreException {
		String result = "";
		VelocityContext context = new VelocityContext();
		if (objs != null) {
			Iterator it = objs.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				context.put(key, objs.get(key));
			}
		}
		if (context.get("now") == null) {
			context.put("now", Calendar.getInstance().getTime());
		}
		if (context.get("format") == null) {
			context.put("format", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		}
		if (context.get("numberTool") == null) {
			context.put("numberTool", new NumberTool());
		}
		if (context.get("dateTool") == null) {
			context.put("dateTool", new DateTool());
		}
		if (context.get("serverValue") == null) {
			context.put("serverValue", ServerValue.getInstance());
		}

		try {
			StringWriter writer = new StringWriter();
			Template template = null;

			template = Velocity.getTemplate(pathPrefix + templateName + ".vm");
			if (template == null) {
				throw new CoreException("errors.template.empty", templateName);
			}
			template.merge(context, writer);
			writer.flush();
			result = writer.toString();
			writer.close();
		} catch (ResourceNotFoundException e) {
			throw new CoreException("errors.template.empty", e, templateName);
		} catch (ParseErrorException e) {
			throw new CoreException("errors.template", e);
		} catch (Exception e) {
			throw new CoreException("errors.template", e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acs.core.template.service.TemplateService#formatByString(java.lang.String, java.lang.String[],
	 * java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public String formatByString(String template, String[] name, Object[] values) throws CoreException {
		Map m = new HashMap();
		if (name != null && values != null) {
			if (name.length == values.length) {
				for (int i = 0; i < values.length; i++) {
					m.put(name[i], values[i]);
					logger.debug("put key:" + name[i] + ",value:" + values[i]);
				}
			} else {
				throw new CoreException("errors.template.input", String.valueOf(name.length), String.valueOf(values.length));
			}
		}
		return formatByStringTemplate(template, m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acs.core.template.service.TemplateService#formatByStringTemplate(java.lang.String, java.util.Map)
	 */
	public String formatByStringTemplate(String template, Map objs) throws CoreException {
		String result = "";
		try {
			logger.debug("template:{},input:{}", template, objs);
			if (template == null) {
				return result;
			}

			VelocityContext context = new VelocityContext();
			if (objs != null) {
				Iterator it = objs.keySet().iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					context.put(key, objs.get(key));
				}
			}
			if (context.get("now") == null) {
				context.put("now", Calendar.getInstance().getTime());
			}
			if (context.get("format") == null) {
				context.put("format", new SimpleDateFormat());
			}
			if (context.get("numberTool") == null) {
				context.put("numberTool", new NumberTool());
			}
			if (context.get("dateTool") == null) {
				context.put("dateTool", new DateTool());
			}

			StringWriter writer = new StringWriter();
			Velocity.evaluate(context, writer, "customize", template);
			writer.flush();
			result = writer.toString();
			writer.close();
		} catch (ResourceNotFoundException e) {
			throw new CoreException("errors.template.empty", e);
		} catch (ParseErrorException e) {
			throw new CoreException("errors.template", e);
		} catch (Exception e) {
			throw new CoreException("errors.template", e);
		}
		return result;
	}

	public Mail formatToMail(String templateName, Map objs) throws CoreException {
		String body = format(templateName, objs);
		String subject = StringUtils.parseTitle(body);
		Mail mail = new Mail(subject, body, null, null);
		return mail;
	}

}
