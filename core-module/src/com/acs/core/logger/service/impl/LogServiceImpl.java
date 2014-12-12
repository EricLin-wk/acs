/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.logger.service.impl.LogServiceImpl
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
package com.acs.core.logger.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONSerializer;

import org.apache.commons.betwixt.io.BeanWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acs.core.common.exception.CoreException;
import com.acs.core.common.service.impl.DomainServiceImpl;
import com.acs.core.logger.entity.CommonLog;
import com.acs.core.logger.service.LogService;
import com.acs.core.user.utils.AdminHelper;

/**
 * @author tw4149
 */
public class LogServiceImpl extends DomainServiceImpl<CommonLog> implements LogService {
	@Resource
	private JdbcTemplate jdbcTemplate;
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private Map<String, String> sqlMap = new HashMap();
	private Map<String, String> attrsMap = new HashMap();
	private String defaultInsert = "insert into COMM_LOG_COMMON (OID, SERVICE_NAME, SERVICE_METHOD, CREATE_USER, LOG_MESSAGE) values (nextval('SEQ_LOG_COMMON'),?,?,?,?)";
	private String outputFormat = "json";

	/** default constructor */
	public LogServiceImpl() {
	}

	/**
	 * @param outputFormat the outputFormat to set
	 */
	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

	/**
	 * @param sqlMap the sqlMap to set
	 */
	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	/**
	 * @param attrsMap the attrsMap to set
	 */
	public void setAttrsMap(Map<String, String> attrsMap) {
		this.attrsMap = attrsMap;
	}

	/**
	 * @param sQLINS the sQL_INS to set
	 */
	public void setDefaultInsert(String sQLINS) {
		defaultInsert = sQLINS;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.logger.service.LogService#save(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */

	@Override
	public void save(String serviceName, String method, Object msg) {
		try {
			UserDetails user = AdminHelper.getUserDetail();
			if (user != null)
				this.save(serviceName, method, msg, user.getUsername());
			else
				this.save(serviceName, method, msg, null);
		} catch (Throwable e) {
			logger.error("log fail, service:{}, method:{}, obj:{}", new Object[] { serviceName, method, msg });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yaodian100.core.logger.service.LogService#save(java.lang.String, java.lang.String, java.lang.Object,
	 * java.lang.String)
	 */

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(String serviceName, String method, Object obj, String actorId) {
		try {
			CommonLog log = new CommonLog();
			log.setServiceName(serviceName);
			log.setServiceMethod(method);
			log.setCreateUser(actorId);
			if ("json".equals(outputFormat)) {
				log.setMessage(toJSON(obj));
			} else {
				log.setMessage(toXML(obj));
			}
			super.save(log);
		} catch (Throwable e) {
			logger.error("log fail, service:{}, method:{}, obj:{}", new Object[] { serviceName, method, obj, e });
		}
	}

	private String toXML(Object entity) throws CoreException {
		String xml = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BeanWriter beanWriter = new BeanWriter(baos);
			beanWriter.enablePrettyPrint();
			beanWriter.write(entity);
			xml = baos.toString();
			beanWriter.flush();
			baos.flush();
			beanWriter.close();
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CoreException("to xml fail", e);
		}
		return xml;
	}

	private String toJSON(Object entity) throws CoreException {
		String json = null;
		try {
			json = JSONSerializer.toJSON(entity).toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CoreException("to xml fail", e);
		}
		return json;
	}
}
