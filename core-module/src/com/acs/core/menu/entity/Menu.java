/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.menu.entity.Menu
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
package com.acs.core.menu.entity;

import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.acs.core.common.entity.BaseEntity;
import com.acs.core.common.utils.StringUtils;

/**
 * @author tw4149
 */
@Entity
@Table(name = "comm_menu")
public class Menu extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -733806372297882887L;
	@Id
	@GeneratedValue(generator = "assigned")
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(name = "menu_key")
	private String key;

	@Column(name = "menu_desc", length = 60)
	private String description;

	@MapKey(name = "code")
	@OneToMany(mappedBy = "menu", orphanRemoval = true)
	@Cascade({ CascadeType.SAVE_UPDATE })
	@OrderBy(value = "sortOrder")
	@LazyCollection(LazyCollectionOption.FALSE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Map<String, Option> options;

	@Column(name = "menu_type", length = 1)
	private String type = "M";

	@Column(length = 3000)
	private String memo;

	/** default constructor */
	public Menu() {
	}

	/** default constructor */
	public Menu(String key, String description) {
		this.key = key;
		this.description = description;
	}

	/** default constructor */
	public Menu(String key, String description, String type) {
		this.key = key;
		this.description = description;
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the options
	 */
	public Map<String, Option> getOptions() {
		if (options == null) {
			options = new TreeMap<String, Option>();
		}
		return options;
	}

	public Menu addOption(String key, String value) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
			try {
				throw new RuntimeException("addOption empty key/value.");
			} catch (Exception ex) {
				LoggerFactory.getLogger(getClass()).debug("addOption empty key/value.", ex);
			}
		}
		Option opt = new Option(key, value, this, getOptions().size());
		getOptions().put(key, opt);
		return this;
	}

	public Menu addOption(String key, String value, String memo1, String memo2) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
			try {
				throw new RuntimeException("addOption empty key/value.");
			} catch (Exception ex) {
				LoggerFactory.getLogger(getClass()).debug("addOption empty key/value.", ex);
			}
		}
		Option opt = new Option(key, value, this, getOptions().size(), memo1, memo2);
		getOptions().put(key, opt);
		return this;
	}

	public Menu addOption(String key, String value, int sort, String memo1, String memo2) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
			try {
				throw new RuntimeException("addOption empty key/value.");
			} catch (Exception ex) {
				LoggerFactory.getLogger(getClass()).debug("addOption empty key/value.", ex);
			}
		}
		Option opt = new Option(key, value, this, sort, memo1, memo2);
		getOptions().put(key, opt);
		return this;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(Map<String, Option> options) {
		this.options = options;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Menu [key=" + key + ", options=" + options + "]";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Menu clone() throws CloneNotSupportedException { // NOPMD
		Menu clone = new Menu();
		BeanUtils.copyProperties(this, clone, new String[] { "modifyDate", "options" });
		for (Option opt : this.options.values()) {
			Option cloneOpt = opt.clone();
			cloneOpt.setMenu(clone);
			clone.getOptions().put(cloneOpt.getCode(), cloneOpt);
		}
		return clone;
	}
}
