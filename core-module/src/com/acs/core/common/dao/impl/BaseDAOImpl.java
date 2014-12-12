/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.dao.impl.BaseDAOImpl
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
package com.acs.core.common.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.acs.core.common.dao.BaseDAO;
import com.acs.core.common.entity.BaseEntity;
import com.acs.core.common.entity.CommonEntity;
import com.acs.core.user.utils.AdminHelper;

/**
 * @author tw4149
 */
public class BaseDAOImpl extends HibernateDaoSupport implements BaseDAO {

	private static Logger logger = LoggerFactory.getLogger(BaseDAOImpl.class);

	@Resource
	private JdbcTemplate jdbcTemplate;

	/*
	 * (non-Javadoc)
	 *
	 * @see net._12iam.integration.BaseDAO#get(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public Object get(Class _class, Serializable key) {
		return this.getHibernateTemplate().get(_class, key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net._12iam.integration.BaseDAO#load(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public Object load(Class _class, Serializable key) {
		return this.getHibernateTemplate().load(_class, key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net._12iam.integration.BaseDAO#save(java.lang.Object)
	 */
	@Override
	public Serializable save(Object obj) {
		if (obj instanceof BaseEntity) {
			updateCommonAttribute((BaseEntity) obj);
		} else if (obj instanceof CommonEntity) {
			updateCommonAttribute((CommonEntity) obj);
		}
		return this.getHibernateTemplate().save(obj);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net._12iam.integration.BaseDAO#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(Object obj) {
		if (obj instanceof BaseEntity) {
			updateCommonAttribute((BaseEntity) obj);
		} else if (obj instanceof CommonEntity) {
			updateCommonAttribute((CommonEntity) obj);
		}
		this.getHibernateTemplate().saveOrUpdate(obj);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net._12iam.integration.BaseDAO#update(java.lang.Object)
	 */
	@Override
	public void update(Object obj) {
		if (obj instanceof BaseEntity) {
			updateCommonAttribute((BaseEntity) obj);
		} else if (obj instanceof CommonEntity) {
			updateCommonAttribute((CommonEntity) obj);
		}

		this.getHibernateTemplate().update(obj);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net._12iam.integration.BaseDAO#delete(java.lang.Object)
	 */
	@Override
	public void delete(Object obj) {
		this.getHibernateTemplate().delete(obj);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net._12iam.integration.BaseDAO#list(java.lang.Class)
	 */
	@Override
	public Object[] list(Class _class) {
		Session session = super.currentSession();
		Criteria criteria = session.createCriteria(_class);
		return criteria.list().toArray();
	}

	@Override
	public Object[] findByCriteria(DetachedCriteria dc, int firstRecord, int maxRecord) {
		Criteria criteria = dc.getExecutableCriteria(super.currentSession());
		criteria.setFirstResult(firstRecord);
		criteria.setMaxResults(maxRecord);
		return criteria.list().toArray();
	}

	@Override
	public int countByCriteria(DetachedCriteria dc) {
		Criteria criteria = dc.getExecutableCriteria(super.currentSession());
		// CriteriaImpl impl = (CriteriaImpl) criteria;
		// Projection projection = impl.getProjection();
		criteria.setProjection(Projections.rowCount());
		criteria.setFirstResult(0);
		criteria.setMaxResults(99999999);
		Object obj = criteria.uniqueResult();
		int totalCount = ((Integer) obj).intValue();
		return totalCount;
	}

	@Override
	public Object executeHql(String hql, Map parameters) {
		Query query = super.currentSession().createQuery(hql);
		if (parameters != null) {
			query.setProperties(parameters);
		}
		return query.executeUpdate();
	}

	@Override
	public Object executeHql(String hql, Object[] parameters) {
		Query query = super.currentSession().createQuery(hql);
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				Object value = parameters[i];
				populateQuery(query, i, value);
			}
		}
		return query.executeUpdate();
	}

	private void populateQuery(Query query, int i, Object value) {
		if (value instanceof String) {
			query.setString(i, (String) value);
		} else if (value instanceof Boolean) {
			query.setBoolean(i, (Boolean) value);
		} else if (value instanceof Date) {
			query.setTimestamp(i, (Date) value);
		} else if (value instanceof Long) {
			query.setLong(i, (Long) value);
		} else if (value instanceof Short) {
			query.setShort(i, (Short) value);
		} else if (value instanceof Integer) {
			query.setInteger(i, (Integer) value);
		} else if (value instanceof Double) {
			query.setDouble(i, (Double) value);
		} else if (value instanceof Float) {
			query.setFloat(i, (Float) value);
		} else if (value instanceof Object[]) {
			query.setParameter(i, value);
		}
	}

	@Override
	public Object[] queryByHql(String hql, Object[] values) {
		Query query = super.currentSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				Object value = values[i];
				populateQuery(query, i, value);
			}
		}
		return query.list().toArray();
	}

	@Override
	public Object[] queryByHql(String hql, Object[] values, int start, int max) {
		Query query = super.currentSession().createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(max);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				Object value = values[i];
				populateQuery(query, i, value);
			}
		}
		return query.list().toArray();
	}

	@Override
	public Object[] findByCriteria(DetachedCriteria dc) {
		Criteria criteria = dc.getExecutableCriteria(super.currentSession());
		return criteria.list().toArray();
	}

	@Override
	public Long countByHql(String hql, Object[] values) {
		String hqlCount = "select count(*) from";
		if (hql.indexOf(hqlCount) < 0) {
			int indexEnd = hql.indexOf("from");
			if (indexEnd < 0) {
				indexEnd = hql.indexOf("FROM");
			}
			if (indexEnd < 0) {
				hql = hqlCount + " " + hql;
			} else {
				String sub = hql.substring(0, indexEnd + 4);
				hql = StringUtils.replace(hql, sub, hqlCount);
			}
		}
		int indexOrderStart = hql.lastIndexOf("order by");
		if (indexOrderStart < 0) {
			indexOrderStart = hql.lastIndexOf("ORDER BY");
		}
		if (indexOrderStart > 0) {
			String sub = hql.substring(indexOrderStart, hql.length());
			hql = StringUtils.replace(hql, sub, "");
		}
		Query query = super.currentSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				Object value = values[i];
				populateQuery(query, i, value);
			}
		}
		Long count = (Long) query.uniqueResult();
		return count;
	}

	private void populateStatement(PreparedStatement statement, int i, Object value) {
		try {
			if (value instanceof String) {
				statement.setString(i, (String) value);
			} else if (value instanceof Boolean) {
				statement.setBoolean(i, (Boolean) value);
			} else if (value instanceof Date) {
				throw new RuntimeException("NOT SUPPORT DATE PARAMETER!");
			} else if (value instanceof Long) {
				statement.setLong(i, (Long) value);
			} else if (value instanceof Short) {
				statement.setShort(i, (Short) value);
			} else if (value instanceof Integer) {
				statement.setInt(i, (Integer) value);
			} else if (value instanceof Double) {
				statement.setDouble(i, (Double) value);
			} else if (value instanceof Float) {
				statement.setFloat(i, (Float) value);
			}
		} catch (SQLException e) {
			logger.error("error:{}", e.toString());
			throw new RuntimeException(e); // NOPMD
		}
	}

	@Override
	public Object[] queryBySql(String sql, Object[] values) {
		Object[] result = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);

			for (int i = 0; i < values.length; i++) {
				Object obj = values[i];
				populateStatement(ps, i + 1, obj);
			}
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rmd = rs.getMetaData();
			List list = new ArrayList();
			while (rs.next()) {
				Map map = new HashMap();
				for (int i = 0; i < rmd.getColumnCount(); i++) {
					map.put(rmd.getColumnName(i + 1), rs.getString(i + 1));
				}
				list.add(map);
			}
			result = list.toArray();
		} catch (Exception e) {
			logger.error("error:{}", e.toString());
			throw new RuntimeException(e); // NOPMD
		}
		return result;
	}

	@Override
	public Object[] customHqlQuery(String hql, Object[] values) {
		Query query = super.currentSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				populateQuery(query, i, values[i]);
			}
		}
		return query.list().toArray();
	}

	@Override
	public Long customHqlCount(String hql, Object[] values) {
		Query query = super.currentSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			populateQuery(query, i, values[i]);
		}
		Long count = (Long) query.uniqueResult();
		return count;
	}

	@Override
	public Object[] customHqlQuery(String hql, Object[] values, int start, int max) {
		Query query = super.currentSession().createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(max);
		for (int i = 0; i < values.length; i++) {
			populateQuery(query, i, values[i]);
		}
		return query.list().toArray();
	}

	/**
	 * 更新 createDate, createUser, updateUser
	 *
	 * @param entity
	 */
	protected void updateCommonAttribute(BaseEntity entity) {
		UserDetails actor = AdminHelper.getUserDetail();
		if (entity.getModifyDate() == null) {
			entity.setCreateDate(Calendar.getInstance().getTime());
		}
		if (actor != null) {
			if (entity.getModifyDate() == null) {
				entity.setCreateUser(actor.getUsername());
			}
			entity.setModifyUser(actor.getUsername());
		} else {
			logger.warn("user not found. entity: {}", entity);
		}
	}

	protected void updateCommonAttribute(CommonEntity entity) {
		UserDetails actor = AdminHelper.getUserDetail();
		if (entity.getModifyDate() == null) {
			entity.setCreateDate(Calendar.getInstance().getTime());
			entity.setModifyDate(Calendar.getInstance().getTime());
		}
		if (actor != null) {
			if (entity.getCreateUser() == null) {
				entity.setCreateUser(actor.getUsername());
			}
			entity.setModifyUser(actor.getUsername());
		} else {
			logger.warn("user not found. entity: {}", entity);
		}
	}

	// public static void main(String args[]) {
	// String hql = "from Manager";
	// String hqlCount = "select count(*) from";
	// if (hql.indexOf(hqlCount) < 0) {
	// int indexEnd = hql.indexOf("from");
	// logger.debug("indexEnd:{}", indexEnd);
	// if (indexEnd < 0) {
	// indexEnd = hql.indexOf("FROM");
	// }
	// logger.debug("indexEnd:{}", indexEnd);
	// if (indexEnd < 0) {
	// hql = hqlCount + " " + hql;
	// } else {
	// String sub = hql.substring(0, indexEnd + 4);
	// logger.debug(sub);
	// logger.debug(hql);
	// hql = StringUtils.replace(hql, sub, hqlCount);
	// }
	// }
	// logger.debug(hql);
	//
	// }
	public static void main(String args[]) {
		System.out.println(Calendar.getInstance().getTimeInMillis()); // NOPMD
		System.out.println(System.currentTimeMillis()); // NOPMD
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acs.core.common.dao.BaseDAO#executeSql(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object executeSql(String sql, Object[] parameters) {
		SQLQuery sqlQuery = super.currentSession().createSQLQuery(sql);
		if (null != parameters) {
			sqlQuery.setProperties(parameters);
		}
		return sqlQuery.executeUpdate();
	}

}
