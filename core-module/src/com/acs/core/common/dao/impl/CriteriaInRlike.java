/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.dao.impl.CriteriaInRlike
   Module Description   :

   Date Created      : 2012/11/23
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
public class CriteriaInRlike extends CommonCriteria {
	private Map<String, Collection<Serializable>> inRlike = null;

	/** default constructor */
	public CriteriaInRlike() {
		super();
	}

	/** default constructor */
	public CriteriaInRlike(CommonCriteria cri) {
		super();
		if (cri != null) {
			this.setEq(cri.getEq());
			this.setNe(cri.getNe());
			this.setGe(cri.getGe());
			this.setLe(cri.getLe());
			this.setRlike(cri.getRlike());
			this.setIn(cri.getIn());
			this.setLike(cri.getLike());
		}
	}

	/**
	 * @return the criteria
	 */
	public CommonCriteria addInRlike(String key, Collection<Serializable> value) {
		getInRlike().put(key, value);
		return this;
	}

	/**
	 * @return the inRlike
	 */
	public Map<String, Collection<Serializable>> getInRlike() {
		if (inRlike == null) {
			inRlike = new HashMap();
		}
		return inRlike;
	}

	/**
	 * @param inRlike the inRlike to set
	 */
	public void setInRlike(Map<String, Collection<Serializable>> inRlike) {
		this.inRlike = inRlike;
	}
}
