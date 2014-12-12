/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.menu.web.MenuAction
   Module Description   :

   Date Created      : 2012/11/28
   Original Author   : tw4149
   Team              :
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.menu.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.core.cache.exception.CacheException;
import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.entity.SimplePager;
import com.acs.core.common.exception.CoreException;
import com.acs.core.common.web.AbstractAction;
import com.acs.core.menu.entity.Menu;
import com.acs.core.menu.entity.Option;
import com.acs.core.menu.service.MenuService;

/**
 * @author tw4149
 */
public class MenuAction extends AbstractAction {

	/** serialVersionUID */
	private static final long serialVersionUID = -6699872898937085062L;
	private static final Logger logger = LoggerFactory.getLogger(MenuAction.class);
	@Resource
	protected MenuService menuService;
	private SimplePager pager;
	private List<Menu> menus;
	private String menuKeyParam;
	private Map<String, Option> options = new HashMap<String, Option>();
	private Menu menu;
	private List<Option> optionCodes;
	/* 頁面參數 */
	private String menuKey;
	private String menuDesc;
	private String menuType;
	private String menuMemo;
	private String saveOrdUpdate;
	private String[] optionId;
	private String[] optionCode;
	private String[] optionMemoOne;
	private String[] optionMemoTwo;
	private String[] optionName;
	private String[] optionSortOrder;
	private String[] optionDelete;
	private String editType;
	private CommonCriteria crit;
	private String deleteMenuKey;
	// Display objects
	private Map<String, Object> jsonResponse;

	@Override
	public void prepare() throws Exception {
		super.prepare();
		if (pager == null) {
			pager = new SimplePager();
			pager.setPageRecord(5);
		}
	}

	public String list() {
		try {
			CommonCriteria crit = new CommonCriteria();
			if (StringUtils.isNotBlank(menuKeyParam)) {
				crit.addRlike("key", menuKeyParam.trim());
			}
			if (pager == null) {
				pager = new SimplePager();
			}
			int entitySize = menuService.getListSize(crit).intValue();
			pager.setTotalSize(entitySize);
			menus = menuService.getList(pager.getCurrentPage() * pager.getPageRecord(), pager.getPageRecord(), crit, null);
		} catch (CoreException e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
		}
		return "list";
	}

	public String add() throws CacheException {
		menu = new Menu();
		menuKey = "";
		menuDesc = "";
		menuType = "";
		menuMemo = "";
		options = null;
		optionCodes = null;
		return "edit";
	}

	/* 編輯 */
	public String edit() {
		try {
			options = new HashMap<String, Option>();
			if (StringUtils.isNotBlank(menuKey)) {
				menu = null;
				menu = menuService.get(menuKey);
				options = menu.getOptions();
				optionCodes = new ArrayList<Option>();
				for (Option arrayTemplate : options.values()) {
					optionCodes.add(arrayTemplate);
				}
			}
		} catch (CoreException e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
		}
		return "edit";
	}

	/* 新增,删除,修改 */
	public String save() {
		try {
			if (null != optionDelete) { // delete
				Menu tmpMmenu = new Menu();
				tmpMmenu = menuService.get(menuKey);
				options = tmpMmenu.getOptions();
				for (int i = 0; i < optionDelete.length; i++) {
					tmpMmenu.getOptions().remove(optionDelete[i]);
					menuService.save(tmpMmenu);
				}
				optionDelete = null;
			} else {
				Menu tmpMmenu = new Menu();
				Map<String, Option> tmpOptions = new HashMap<String, Option>();
				if (StringUtils.isNotBlank(menuKey) && saveOrdUpdate.equals("update")) {
					tmpMmenu = menuService.get(menuKey);
					tmpMmenu.setKey(menuKey);
					tmpMmenu.setDescription(menuDesc);
					tmpMmenu.setType(menuType);
					tmpMmenu.setMemo(menuMemo);
					tmpOptions = tmpMmenu.getOptions();
				} else {
					tmpMmenu = new Menu();
				}
				for (int i = 0; i < optionCode.length; i++) {
					if (!optionId[i].equals("")) {
						for (Option option : tmpOptions.values()) {
							if (!optionId[i].equals("")) {
								if (option.getOid().equals(Long.valueOf(optionId[i]))) {
									option.setCode(optionCode[i]);
									option.setMemo1(optionMemoOne[i]);
									option.setMemo2(optionMemoTwo[i]);
									option.setName(optionName[i]);
									option.setSortOrder(Integer.parseInt(optionSortOrder[i]));
									tmpOptions.put(optionCode[i], option);
								}
							}
						}
						tmpMmenu.setOptions(tmpOptions);
						tmpMmenu = menuService.save(tmpMmenu);
					} else {// save
						if (StringUtils.isNotBlank(optionCode[i])) {
							if (StringUtils.isNotBlank(menuKey) && saveOrdUpdate.equals("update")) {
								if (StringUtils.isNotBlank(optionSortOrder[i])) {
									tmpMmenu.addOption(optionCode[i], optionName[i], Integer.parseInt(optionSortOrder[i]),
											optionMemoOne[i], optionMemoTwo[i]);
								} else {
									tmpMmenu.addOption(optionCode[i], optionName[i], optionMemoOne[i], optionMemoTwo[i]);
								}
								menuService.save(tmpMmenu);
							} else {// for first insert
								tmpMmenu = new Menu();
								tmpMmenu.setKey(menuKey);
								tmpMmenu.setDescription(menuDesc);
								tmpMmenu.setType(menuType);
								tmpMmenu.setMemo(menuMemo);
								if (StringUtils.isNotBlank(optionSortOrder[i])) {
									tmpMmenu.addOption(optionCode[i], optionName[i], Integer.parseInt(optionSortOrder[i]),
											optionMemoOne[i], optionMemoTwo[i]);
								} else {
									tmpMmenu.addOption(optionCode[i], optionName[i], optionMemoOne[i], optionMemoTwo[i]);
								}
								menuService.save(tmpMmenu);
								setSaveOrdUpdate("update");
								setMenuKey(menuKey);
							}
						}
					}
				}
				options = null;
				menu = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
		}
		return edit();
	}

	public String delete() {
		if (StringUtils.isNotBlank(deleteMenuKey)) {
			if (menuService.get(deleteMenuKey) != null) {
				menuService.delete(menuService.get(deleteMenuKey));
			} else {
				addActionError("查无此选单请确认后再试一次，menuKey:" + deleteMenuKey);
			}
		}
		return list();
	}

	public String autoCompleteMenuKey() throws CoreException {
		jsonResponse = new TreeMap<String, Object>();
		List<Menu> menuTemp = menuService.getList(0, -1, null, new String[] { "key asc" });
		List<String> keyArray = new ArrayList<String>();
		for (Menu menu : menuTemp) {
			keyArray.add(menu.getKey());
		}
		jsonResponse.put("key", keyArray);
		return "jsonResponse";
	}

	@Override
	public SimplePager getPager() {
		return pager;
	}

	@Override
	public void setPager(SimplePager pager) {
		this.pager = pager;
	}

	/**
	 * @return the menus
	 */
	public List<Menu> getMenus() {
		return menus;
	}

	/**
	 * @param menus the menus to set
	 */
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	/**
	 * @return the menuKeyParam
	 */
	public String getMenuKeyParam() {
		return menuKeyParam;
	}

	/**
	 * @param menuKeyParam the menuKeyParam to set
	 */
	public void setMenuKeyParam(String menuKeyParam) {
		this.menuKeyParam = menuKeyParam;
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

	/**
	 * @return the optionCodes
	 */
	public List<Option> getOptionCodes() {
		return optionCodes;
	}

	/**
	 * @param optionCodes the optionCodes to set
	 */
	public void setOptionCodes(List<Option> optionCodes) {
		this.optionCodes = optionCodes;
	}

	/**
	 * @return the options
	 */
	public Map<String, Option> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(Map<String, Option> options) {
		this.options = options;
	}

	/**
	 * @return the menuKey
	 */
	public String getMenuKey() {
		return menuKey;
	}

	/**
	 * @param menuKey the menuKey to set
	 */
	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	/**
	 * @return the menuDesc
	 */
	public String getMenuDesc() {
		return menuDesc;
	}

	/**
	 * @param menuDesc the menuDesc to set
	 */
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	/**
	 * @return the menuType
	 */
	public String getMenuType() {
		return menuType;
	}

	/**
	 * @param menuType the menuType to set
	 */
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	/**
	 * @return the menuMemo
	 */
	public String getMenuMemo() {
		return menuMemo;
	}

	/**
	 * @param menuMemo the menuMemo to set
	 */
	public void setMenuMemo(String menuMemo) {
		this.menuMemo = menuMemo;
	}

	/**
	 * @return the saveOrdUpdate
	 */
	public String getSaveOrdUpdate() {
		return saveOrdUpdate;
	}

	/**
	 * @param saveOrdUpdate the saveOrdUpdate to set
	 */
	public void setSaveOrdUpdate(String saveOrdUpdate) {
		this.saveOrdUpdate = saveOrdUpdate;
	}

	/**
	 * @return the optionId
	 */
	public String[] getOptionId() {
		return optionId;
	}

	/**
	 * @param optionId the optionId to set
	 */
	public void setOptionId(String[] optionId) {
		this.optionId = optionId;
	}

	/**
	 * @return the optionCode
	 */
	public String[] getOptionCode() {
		return optionCode;
	}

	/**
	 * @param optionCode the optionCode to set
	 */
	public void setOptionCode(String[] optionCode) {
		this.optionCode = optionCode;
	}

	/**
	 * @return the optionMemoOne
	 */
	public String[] getOptionMemoOne() {
		return optionMemoOne;
	}

	/**
	 * @param optionMemoOne the optionMemoOne to set
	 */
	public void setOptionMemoOne(String[] optionMemoOne) {
		this.optionMemoOne = optionMemoOne;
	}

	/**
	 * @return the optionMemoTwo
	 */
	public String[] getOptionMemoTwo() {
		return optionMemoTwo;
	}

	/**
	 * @param optionMemoTwo the optionMemoTwo to set
	 */
	public void setOptionMemoTwo(String[] optionMemoTwo) {
		this.optionMemoTwo = optionMemoTwo;
	}

	/**
	 * @return the optionName
	 */
	public String[] getOptionName() {
		return optionName;
	}

	/**
	 * @param optionName the optionName to set
	 */
	public void setOptionName(String[] optionName) {
		this.optionName = optionName;
	}

	/**
	 * @return the optionSortOrder
	 */
	public String[] getOptionSortOrder() {
		return optionSortOrder;
	}

	/**
	 * @param optionSortOrder the optionSortOrder to set
	 */
	public void setOptionSortOrder(String[] optionSortOrder) {
		this.optionSortOrder = optionSortOrder;
	}

	/**
	 * @return the optionDelete
	 */
	public String[] getOptionDelete() {
		return optionDelete;
	}

	/**
	 * @param optionDelete the optionDelete to set
	 */
	public void setOptionDelete(String[] optionDelete) {
		this.optionDelete = optionDelete;
	}

	/**
	 * @return the editType
	 */
	public String getEditType() {
		return editType;
	}

	/**
	 * @param editType the editType to set
	 */
	public void setEditType(String editType) {
		this.editType = editType;
	}

	/**
	 * @return the crit
	 */
	public CommonCriteria getCrit() {
		return crit;
	}

	/**
	 * @param crit the crit to set
	 */
	public void setCrit(CommonCriteria crit) {
		this.crit = crit;
	}

	/**
	 * @return the jsonResponse
	 */
	public Map<String, Object> getJsonResponse() {
		return jsonResponse;
	}

	/**
	 * @param jsonResponse the jsonResponse to set
	 */
	public void setJsonResponse(Map<String, Object> jsonResponse) {
		this.jsonResponse = jsonResponse;
	}

	/**
	 * @return the deleteMenuKey
	 */
	public String getDeleteMenuKey() {
		return deleteMenuKey;
	}

	/**
	 * @param deleteMenuKey the deleteMenuKey to set
	 */
	public void setDeleteMenuKey(String deleteMenuKey) {
		this.deleteMenuKey = deleteMenuKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acs.core.common.web.AbstractAction#resetData()
	 */
	@Override
	public void resetData() {
		// TODO Auto-generated method stub

	}

}
