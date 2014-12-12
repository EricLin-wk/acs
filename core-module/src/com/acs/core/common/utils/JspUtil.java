/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.utils.JspUtil
   Module Description   :

   Date Created      : 2012/11/29
   Original Author   : tw4149
   Team              : 
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.common.utils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.acs.core.common.service.BaseManager;
import com.acs.core.menu.entity.Option;
import com.acs.core.user.entity.Group;
import com.acs.core.user.entity.Role;
import com.acs.core.user.entity.User;
import com.acs.core.user.utils.AdminHelper;

/**
 * @author tw4149
 * 
 */
public class JspUtil implements ApplicationContextAware, InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(JspUtil.class);
	private static ApplicationContext ctx;
	private static BaseManager baseManager;

	public static Date addDays(Date target, int amount) {
		if (target == null || target == null) {
			return target;
		} else {
			return DateUtils.addDays(target, amount);
		}

	}

	public static Object[] addToList(String listName, Object value) {
		List list = (List) getRequest().getAttribute(listName);
		if (list == null) {
			list = new ArrayList();
			getRequest().setAttribute(listName, list);
		}
		list.add(value);
		return list.toArray();

	}

	public static Map addToMap(String mapName, String name, Object value) {
		Map map = (Map) getRequest().getAttribute(mapName);
		if (map == null) {
			map = new HashMap();
			getRequest().setAttribute(mapName, map);
		}
		return map;
	}

	public static Boolean after(Date target, Date source) {
		if (target == null || source == null) {
			return false;
		}
		return target.after(source);
	}

	public static Object[] arrayGet(String listName) {
		List list = (List) getRequest().getAttribute(listName);
		if (list == null) {
			return null;
		}
		return list.toArray();
	}

	public static Boolean before(Date target, Date source) {
		if (target == null || source == null) {
			return false;
		}
		return target.before(source);
	}

	public static Boolean contain(String source, String regex) {
		if (source.indexOf(regex) >= 0) {
			return true;
		}
		return false;
	}

	public static Integer countByCriteria(DetachedCriteria dc) {
		return baseManager.countByCriteria(dc);
	}

	public static Long countByHql(String hql, Object[] values) {
		return baseManager.countByHql(hql, values);
	}

	public static Criterion criteria(String type, String parameter, Object value) {
		if (value == null || (value instanceof String && ((String) value).length() == 0)) {
			return null;
		}
		Criterion criterion;
		try {
			Class[] parameterTypes = new Class[] { String.class, Object.class };
			Method method = Restrictions.class.getDeclaredMethod(type, parameterTypes);
			method.setAccessible(true);
			Object[] values = new Object[] { parameter, value };
			criterion = (Criterion) method.invoke(null, values);
		} catch (Exception e) {
			throw new RuntimeException(e); // NOPMD
		}
		return criterion;
	}

	public static void criteriaAdd(Criterion criterion) {

		if (criterion != null) {
			getCriterionList().add(criterion);
		}
	}

	public static Criterion criteriaAllEq(Map nameValues) {
		if (nameValues == null) {
			return null;
		}
		return Restrictions.allEq(nameValues);
	}

	public static Criterion criteriaAnd(Criterion criterion1, Criterion criterion2) {
		if (criterion1 == null || criterion2 == null) {
			return null;
		}
		return Restrictions.and(criterion1, criterion2);
	}

	public static Criterion criteriaBetween(String parameter, Object value1, Object value2) {
		if (value1 == null || value2 == null) {
			return null;
		}
		return Restrictions.between(parameter, value1, value2);
	}

	public static void criteriaForName(String className) {
		getRequest().setAttribute("criteriaForName", className);
		getRequest().setAttribute("criterionList", null);
		getRequest().setAttribute("orderList", null);
		getRequest().setAttribute("aliasMap", null);
	}

	public static Criterion criteriaIn(String parameter, Object[] values) {
		if (values == null) {
			return null;
		}
		return Restrictions.in(parameter, values);
	}

	public static Criterion criteriaNotNull(String parameter) {
		return Restrictions.isNotNull(parameter);
	}

	public static Criterion criteriaNull(String parameter) {
		return Restrictions.isNull(parameter);
	}

	public static Criterion criteriaOr(Criterion criterion1, Criterion criterion2) {
		if (criterion1 == null || criterion2 == null) {
			return null;
		}
		return Restrictions.or(criterion1, criterion2);
	}

	public static void createAlias(String associationPath, String alias) {
		Map<String, String> aliasMap = (Map<String, String>) getRequest().getAttribute("aliasMap");
		if (aliasMap == null) {
			aliasMap = new HashMap<String, String>();
		}
		aliasMap.put(associationPath, alias);
		getRequest().setAttribute("aliasMap", aliasMap);
	}

	public static String currency(Object value) {
		if (value == null) {
			return null;
		}
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("zh", "cn"));
		nf.setMaximumFractionDigits(1);
		return nf.format(value);
	}

	public static Date currentDate() {
		return new Date();
	}

	public static Long customHqlCount(String hql, Object[] values) {
		return baseManager.customHqlCount(hql, values);
	}

	public static void delete(Object object) {
		baseManager.delete(object);
	}

	public static Object emptyToNull(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj.equals("")) {
			return null;
		} else {
			return obj;
		}
	}

	public static void initHql(String hqlInit) {
		getRequest().setAttribute("compositeHql", null);
		getRequest().setAttribute("partHqls", null);
		getRequest().setAttribute("compositeHqlValues", null);
		String hql = hqlInit;
		getRequest().setAttribute("compositeHql", hql);
	}

	public static Map<String, Object> compositeHql(String hqlPart, Object[] values, String type) {
		String hql = (String) getRequest().getAttribute("compositeHql");
		List<String> partHqls = (List<String>) getRequest().getAttribute("partHqls");
		List<Object> valueList = (List<Object>) getRequest().getAttribute("compositeHqlValues");
		boolean isNullValues = true;
		for (Object value : values) {
			if (value != null) {
				if (value instanceof String) {
					if (((String) value).length() > 0) {
						isNullValues = false;
					}

				} else {
					isNullValues = false;
				}

				break;
			}
		}
		if (!isNullValues) {
			if (partHqls == null) {
				partHqls = new ArrayList<String>();
				partHqls.add(hqlPart);
				getRequest().setAttribute("partHqls", partHqls);
				hql += " where ";
			} else {
				hql += " " + type + " ";
			}
			hql += hqlPart;
			if (valueList == null) {
				valueList = new ArrayList<Object>();
			}
			for (Object value : values) {
				valueList.add(value);
			}
		}
		Map<String, Object> pair = new HashMap<String, Object>();
		pair.put("hql", hql);
		pair.put("values", valueList);
		getRequest().setAttribute("compositeHql", hql);
		getRequest().setAttribute("compositeHqlValues", valueList);
		return pair;
	}

	public static Object[] findByCriteria(DetachedCriteria dc) {
		return baseManager.findByCriteria(dc);
	}

	public static Object[] findByCriteria(DetachedCriteria dc, int start, int end) {
		return baseManager.findByCriteria(dc, start, end);
	}

	public static String formatString(String source, Object[] args) {
		String result = null;
		if (args.length == 1) {
			result = String.format(source, args[0]);
		} else if (args.length == 2) {
			result = String.format(source, args[0], args[1]);
		} else if (args.length == 3) {
			result = String.format(source, args[0], args[1], args[2]);
		} else if (args.length == 4) {
			result = String.format(source, args[0], args[1], args[2], args[3]);
		} else if (args.length == 5) {
			result = String.format(source, args[0], args[1], args[2], args[3], args[4]);
		}
		return result;
	}

	public static Object get(String name) {
		return getRequest().getAttribute(name);
	}

	public static Object get(String _class, Serializable id) {

		try {
			return baseManager.get(Class.forName(_class), id);
		} catch (ClassNotFoundException e) {
			LOGGER.warn(e.toString());
			// e.printStackTrace();
		}
		return null;
	}

	private static String getCriteriaClassName() {
		String className = (String) getRequest().getAttribute("criteriaForName");

		if (className == null) {
			throw new RuntimeException("You have to init detachedCriteria with method:criteriaForName");// NOPMD
		}
		return className;
	}

	private static List<Criterion> getCriterionList() {
		List<Criterion> list = (List<Criterion>) getRequest().getAttribute("criterionList");
		if (list == null) {
			list = new ArrayList<Criterion>();
			getRequest().setAttribute("criterionList", list);
		}
		return list;
	}

	public static ApplicationContext getCtx() {
		return ctx;
	}

	public static Group getGroupByUsername(String username) {
		User user = (User) baseManager.get(User.class, username);
		return user.getGroup();
	}

	private static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static Collection<Role> getRolesByUsername(String username) {
		User user = (User) baseManager.get(User.class, username);
		return user.getRoles();
	}

	public static String getToken() {
		String token = getToken("cuttleToken");
		return token;
	}

	public static String getToken(String tokenName) {
		String token = null;// (String) CookieUtil.getCookieValue(tokenName, false);
		if (token == null || token.length() == 0) {
			token = UUID.randomUUID().toString();
			// CookieUtil.add(tokenName, token);
		}
		return token;
	}

	public static Boolean hasRole(String username, String roleKey) {
		User user = (User) baseManager.get(User.class, username);
		Collection<Role> roles = user.getRoles();

		boolean hasRole = false;
		for (Role role : roles) {
			if (role.getKey().equals(roleKey)) {
				hasRole = true;
				break;
			}
		}
		return hasRole;
	}

	public static Boolean isCRMManager(String username) {
		Object result[] = baseManager.queryByHql(
				"from TaskGroupUser where user.username=? and taskGroup.id like ? and taskRole=?", new Object[] { username,
						"C.%", "MANAGER" });
		if (result != null && result.length > 0) {
			return true;
		}
		return false;
	}

	public static Boolean isC02(String username) {
		Object result[] = baseManager.queryByHql("from TaskGroupUser where user.username=? and taskGroup.id =?",
				new Object[] { username, "C.02" });
		if (result != null && result.length > 0) {
			return true;
		}
		return false;
	}

	public static Object getDefaultTaskGroup(String username) {
		Object[] result = baseManager.queryByHql("from TaskGroupUser gu where gu.user.username=? and gu.defaultGroup=?",
				new Object[] { username, Boolean.TRUE });
		if (result != null && result.length > 0) {
			return result[0];
		} else {
			result = baseManager.queryByHql("from TaskGroupUser gu where gu.user.username=?", new Object[] { username });
			if (result != null && result.length > 0) {
				return result[0];
			} else {
				return null;
			}
		}
	}

	public static boolean isIn(String source, Object[] target) {
		if (target == null) {
			return false;
		}
		for (Object obj : target) {
			if (source.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	public static Boolean isManager(String username) {
		User user = (User) baseManager.get(User.class, username);
		Collection<Role> roles = user.getRoles();
		boolean isManager = false;
		for (Role role : roles) {
			if (role.getName().equals(user.getGroup().getManager().getName())) {
				isManager = true;
				break;
			}
		}
		return isManager;
	}

	public static Boolean isResubmit(String token) {

		// if (getToken().equals(token)) {
		// resetToken();
		// return false;
		// } else {
		// return true;
		// }
		return false;

	}

	public static Boolean isResubmit(String key, String token) {
		// if (getToken(key).equals(token)) {
		// resetToken(key);
		// return false;
		// } else {
		// return true;
		// }
		return false;
	}

	public static void listAdd(String listName, Object value) {
		List list = (List) getRequest().getAttribute(listName);
		if (list == null) {
			list = new ArrayList();
			getRequest().setAttribute(listName, list);
		}
		list.add(value);
	}

	public static Object[] listAll(Class _class) {
		return baseManager.list(_class);
	}

	public static List listGet(String listName) {
		List list = (List) getRequest().getAttribute(listName);
		if (list == null) {
			return null;
		}
		return list;
	}

	public static void main(String args[]) {
		// System.out.println(toWrapDate("2009/11/26 23:59:59", "yyyy/MM/dd HH:mm:ss"));
		// System.out.println(Math.)
		// String source="I am %S, and I am in %S";
		// System.out.println(formatString(source, new String[]{"Andy", "Taipei"}));
		// float value = 12335f;
		// NumberFormat nf=NumberFormat.getCurrencyInstance(new Locale("zh", "cn"));
		// System.out.println(currency(value));// NOPMD

		// System.out.println(maskEmail("jinwei.lin@gmail.com.tw"));// NOPMD
		// System.out.println(maskMobile("01234567890123"));// NOPMD
		// Float f = 50.2352323f;
		// System.out.println(currency(f));
		// System.out.println(JspUtil.replaceAll("我的最爱的小狗", "最爱", "<b>最爱</b>"));
		Calendar cal = Calendar.getInstance();
		cal.set(2011, Calendar.FEBRUARY, 15);
		Date d1 = cal.getTime();
		cal.set(2011, Calendar.FEBRUARY, 13);
		Date d2 = cal.getTime();

		System.out.println(JspUtil.subDateInHour(d1, d2));
	}

	public static String maskEmail(String input) {
		String mask = "";
		for (int i = 0; i < input.length(); i++) {
			mask += "*";
		}
		if (input.indexOf("@") > 0) {
			input = input.substring(0, input.indexOf("@") + 1) + mask.substring(input.indexOf("@") + 1, input.length());
		}
		return input;
	}

	public static String maskMobile(String input) {
		String mask = "";
		if (StringUtils.isNotEmpty(input)) {
			for (int i = 0; i < input.length(); i++) {
				mask += "*";
			}
			if (input.length() > 5) {
				input = input.substring(0, 5) + mask.substring(5, input.length());
			}
		}
		return input;
	}

	public static String maskString(String source, Integer leftLength) {
		if (source == null) {
			return null;
		}
		StringBuffer mask = new StringBuffer("");
		for (int i = leftLength; i < source.length(); i++) {
			mask.append("*");
		}
		if (leftLength > source.length()) {
			return source;
		}
		return source.substring(0, leftLength) + mask.toString();
	}

	public static Option menuOption(String key, String code) {
		String hql = "from Option where menu.key=? and code=?";
		List<Object> list = new ArrayList<Object>();
		list.add(key);
		list.add(code);
		Object[] results = baseManager.queryByHql(hql, list.toArray());
		if (results != null && results.length > 0) {

			return (Option) results[0];
		}
		return null;
	}

	public static long parseLong(String source) {
		return Long.parseLong(source);
	}

	public static void resetToken() {
		resetToken("cuttleToken");
	}

	public static void resetToken(String tokenName) {
		// CookieUtil.clear(tokenName);
		String token = null;
		token = UUID.randomUUID().toString();
		// getRequest().getSession().setAttribute(tokenName, token);
	}

	public static String rmb(Object value) {
		DecimalFormat nf = new DecimalFormat("######0.0");
		return nf.format(value);
	}

	public static void save(Object object) {
		baseManager.save(object);
	}

	public static void set(String name, Object value) {
		getRequest().setAttribute(name, value);
	}

	public static void setCtx(ApplicationContext ctx) {
		JspUtil.ctx = ctx;
	}

	public static String[] split(String source, String regex) {
		String[] splits = source.split(regex);
		return splits;
	}

	public static Integer arrayLength(Object[] array) {
		return array.length;
	}

	public static Integer listLength(List<Object> list) {
		return list.size();
	}

	public static String substring(String source, Integer start, Integer end) {
		if (end > source.length()) {
			end = source.length();
		}
		return source.substring(start, end);
	}

	public static Object[] toArray(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof Object[]) {
			return (Object[]) object;
		} else if (object instanceof Collection) {
			return ((Collection) object).toArray();
		} else {
			Object[] objs = new Object[1];
			objs[0] = object;
			return objs;
		}
	}

	public static Boolean toBoolean(String source) {
		return Boolean.valueOf(source);
	}

	public static Date toDate(String source, String format) {
		if (source == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			// throw new RuntimeException(e);
			LOGGER.warn(e.toString());
			return null;
		}
	}

	public static Integer toInteger(String source) {
		if (source.indexOf(".") >= 0) {
			return Integer.valueOf(source.substring(0, source.indexOf(".")));
		} else {
			return Integer.valueOf(source);
		}
	}

	public static Long toLong(String source) {
		if (source == null || source.equals("")) {
			LOGGER.warn("toLong source:null");
			return null;
		}
		Long result = null;
		if (NumberUtils.isNumber(source)) {
			if (source.indexOf(".") >= 0) {
				result = Long.valueOf(source.substring(0, source.indexOf(".")));
			} else {
				result = Long.valueOf(source);
			}
		} else {
			result = Long.valueOf(StringUtils.parseNumber(source));
		}
		return result;
	}

	public static BigDecimal toBigDecimal(String source) {
		return new BigDecimal(source);
	}

	public static String toString(Object o) {
		return String.valueOf(o);
	}

	public static Date toWrapDate(String source, String desFormat) {
		try {
			Date desDate = DateUtils.parseDate(source, new String[] { desFormat });
			return desDate;
		} catch (ParseException e) {
			// throw new RuntimeException(e);
			e.printStackTrace();
			LOGGER.warn(e.toString());
			return null;
		}
	}

	public static void update(Object object) {
		baseManager.update(object);
	}

	public static User user() {
		User user = (User) baseManager.get(User.class, AdminHelper.getUserDetail().getUsername());
		return user;
	}

	public static String username() {
		User user = (User) baseManager.get(User.class, AdminHelper.getUserDetail().getUsername());
		if (user != null) {
			return user.getUsername();
		}
		return null;
	}

	public void afterPropertiesSet() throws Exception {
		baseManager = (BaseManager) ctx.getBean("baseMgr");
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;

	}

	public static String replaceAll(String source, String replaceStr, String targetStr) {
		if (source == null) {
			return null;
		} else if (replaceStr == null || targetStr == null) {
			return source;
		}
		return source.replaceAll(replaceStr, targetStr);
	}

	public static Float toFloat(String source) {
		if (source == null) {
			return null;
		}
		Float _float = new Float(source);
		return _float;
	}

	public static String urlDecoding(String source, String encoding) {
		if (source == null) {
			return null;
		}
		try {
			return URLDecoder.decode(source, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return source;
	}

	public static String urlEncoding(String source, String encoding) {
		if (source == null) {
			return null;
		}
		try {
			return URLEncoder.encode(source, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return source;
	}

	public static Double toDouble(String source) {
		if (source == null) {
			return null;
		}
		Double _double = new Double(source);
		return _double;
	}

	public static Float subDateInHour(Date source, Date target) {
		if (source == null || target == null) {
			return null;
		}
		Calendar sourceC = Calendar.getInstance();
		Calendar targetC = Calendar.getInstance();
		sourceC.setTime(source);
		targetC.setTime(target);

		long daterange = sourceC.getTimeInMillis() - targetC.getTimeInMillis();
		long time = 1000 * 3600; // An hour in milliseconds
		return Float.valueOf(daterange / time);
	}

}
