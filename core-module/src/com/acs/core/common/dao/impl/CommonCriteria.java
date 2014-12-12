/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.dao.impl.CommonCriteria
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
package com.acs.core.common.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tw4149
 * 
 */
public class CommonCriteria implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 3500331519384981676L;
	private Map<String, Serializable> eq = null;
	private Map<String, Serializable> ne = null;
	private Map<String, Serializable> ge = null;
	private Map<String, Serializable> le = null;
	private Map<String, String> rlike = null;
	private Map<String, Collection<Serializable>> in = null;
	private Map<String, String> like = null;

	/** default constructors */
	public CommonCriteria() {
	}

	/**
	 * @param eq
	 * @param ne
	 * @param ge
	 * @param le
	 * @param rlike
	 * @param in
	 * @param order
	 */
	public CommonCriteria(Map<String, Serializable> eq, Map<String, Serializable> ne, Map<String, Serializable> ge,
			Map<String, Serializable> le, Map<String, String> rlike, Map<String, Collection<Serializable>> in) {
		this.eq = eq;
		this.ne = ne;
		this.ge = ge;
		this.le = le;
		this.rlike = rlike;
		this.in = in;
	}

	/**
	 * @return the eq
	 */
	public Map<String, Serializable> getEq() {
		return eq;
	}

	/**
	 * @return the criteria
	 */
	public CommonCriteria addEq(String key, Serializable value) {
		if (eq == null) {
			eq = new HashMap();
		}
		eq.put(key, value);
		return this;
	}

	/**
	 * @param eq the eq to set
	 */
	public void setEq(Map<String, Serializable> eq) {
		this.eq = eq;
	}

	/**
	 * @return the ne
	 */
	public Map<String, Serializable> getNe() {
		return ne;
	}

	/**
	 * @return the criteria
	 */
	public CommonCriteria addNe(String key, Serializable value) {
		if (ne == null) {
			ne = new HashMap();
		}
		ne.put(key, value);
		return this;
	}

	/**
	 * @param ne the ne to set
	 */
	public void setNe(Map<String, Serializable> ne) {
		this.ne = ne;
	}

	/**
	 * @return the ge
	 */
	public Map<String, Serializable> getGe() {
		return ge;
	}

	/**
	 * @return the criteria
	 */
	public CommonCriteria addGe(String key, Serializable value) {
		if (ge == null) {
			ge = new HashMap();
		}
		ge.put(key, value);
		return this;
	}

	/**
	 * @param ge the ge to set
	 */
	public void setGe(Map<String, Serializable> ge) {
		this.ge = ge;
	}

	/**
	 * @return the le
	 */
	public Map<String, Serializable> getLe() {
		return le;
	}

	/**
	 * @return the criteria
	 */
	public CommonCriteria addLe(String key, Serializable value) {
		if (le == null) {
			le = new HashMap();
		}
		le.put(key, value);
		return this;
	}

	/**
	 * @param le the le to set
	 */
	public void setLe(Map<String, Serializable> le) {
		this.le = le;
	}

	/**
	 * @return the rlike
	 */
	public Map<String, String> getRlike() {
		return rlike;
	}

	/**
	 * @return the criteria
	 */
	public CommonCriteria addRlike(String key, String value) {
		if (rlike == null) {
			rlike = new HashMap();
		}
		rlike.put(key, value);
		return this;
	}

	/**
	 * @param rlike the rlike to set
	 */
	public void setRlike(Map<String, String> rlike) {
		this.rlike = rlike;
	}

	/**
	 * @return the in
	 */
	public Map<String, Collection<Serializable>> getIn() {
		return in;
	}

	/**
	 * @return the criteria
	 */
	public CommonCriteria addIn(String key, Collection value) {
		if (in == null) {
			in = new HashMap();
		}
		in.put(key, value);
		return this;
	}

	/**
	 * @param in the in to set
	 */
	public void setIn(Map<String, Collection<Serializable>> in) {
		this.in = in;
	}

	/**
	 * @return the like
	 */
	public Map<String, String> getLike() {
		return like;
	}

	/**
	 * @return the criteria
	 */
	public CommonCriteria addLike(String key, String value) {
		if (like == null) {
			like = new HashMap();
		}
		like.put(key, value);
		return this;
	}

	/**
	 * @param like the like to set
	 */
	public void setLike(Map<String, String> like) {
		this.like = like;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CommonCriteria [eq=" + eq + ", ge=" + ge + ", in=" + in + ", le=" + le + ", ne=" + ne + ", rlike=" + rlike
				+ ", like=" + like + "]";
	}
}
