/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.menu.entity.Option
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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.BeanUtils;

/**
 * @author tw4149
 */
@Entity
@Table(name = "comm_menu_option")
public class Option implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 8718513301951162580L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_menu_option")
	@SequenceGenerator(name = "seq_menu_option", sequenceName = "SEQ_MENU_OPTION")
	@Column(name = "OPTION_OID")
	private Long oid;

	@Column(name = "option_code", length = 60)
	private String code;

	@Column(name = "option_name", length = 60)
	private String name;

	@Column(name = "option_memo1", length = 60)
	private String memo1;

	@Column(name = "option_memo2", length = 60)
	private String memo2;

	@Column(name = "sort_order")
	private int sortOrder = 0;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Menu.class)
	@JoinColumn(name = "menu_key")
	private Menu menu;

	/** default constructor */
	public Option() {
	}

	public Option(Menu menu) {
		super();
		this.menu = menu;
	}

	public Option(String code, String name, Menu menu, int sortOrder) {
		super();
		this.code = code;
		this.name = name;
		this.menu = menu;
		this.sortOrder = sortOrder;
	}

	public Option(String code, String name, Menu menu, int sortOrder, String memo1, String memo2) {
		super();
		this.code = code;
		this.name = name;
		this.menu = menu;
		this.sortOrder = sortOrder;
		this.memo1 = memo1;
		this.memo2 = memo2;
	}

	/**
	 * @return the oid
	 */
	public Long getOid() {
		return oid;
	}

	/**
	 * @param oid the oid to set
	 */
	public void setOid(Long oid) {
		this.oid = oid;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the code
	 */
	@Transient
	public String getCodeName() {
		return code + "/" + name;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the memo1
	 */
	public String getMemo1() {
		return memo1;
	}

	/**
	 * @param memo1 the memo1 to set
	 */
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}

	/**
	 * @return the memo2
	 */
	public String getMemo2() {
		return memo2;
	}

	/**
	 * @param memo2 the memo2 to set
	 */
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}

	/**
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Option [code=" + code + ", name=" + name + ", sortOrder=" + sortOrder + "]";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Option clone() throws CloneNotSupportedException {// NOPMD
		Option clone = new Option();
		BeanUtils.copyProperties(this, clone, new String[] { "menu" });
		return clone;
	}
}
