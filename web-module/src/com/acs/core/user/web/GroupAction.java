/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.user.web.GroupAction
   Module Description   :

   Date Created      : 2012/12/13
   Original Author   : tw4149
   Team              :
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.user.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.core.common.dao.impl.CommonCriteria;
import com.acs.core.common.entity.SimplePager;
import com.acs.core.common.exception.CoreException;
import com.acs.core.user.entity.Group;
import com.acs.core.user.service.GroupService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author tw4149
 */
public class GroupAction extends ActionSupport implements Preparable {

	/** serialVersionUID */
	private static final long serialVersionUID = -5293864452010875016L;
	private final Logger logger = LoggerFactory.getLogger(GroupAction.class);
	@Resource
	private GroupService groupService;

	private Group obj;

	private List<Group> objList;

	private String paraId;
	private String paraKey;
	private String paraDesc;

	private List<String> paraTypes;
	private Map<String, String> typeMenu;
	private SimplePager pager;

	/**
	 * @return the obj
	 */
	public Group getObj() {
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void setObj(Group obj) {
		this.obj = obj;
	}

	/**
	 * @return the objList
	 */
	public List<Group> getObjList() {
		return objList;
	}

	/**
	 * @param objList the objList to set
	 */
	public void setObjList(List<Group> objList) {
		this.objList = objList;
	}

	/**
	 * @return the paraId
	 */
	public String getParaId() {
		return paraId;
	}

	/**
	 * @param paraId the paraId to set
	 */
	public void setParaId(String paraId) {
		this.paraId = paraId;
	}

	/**
	 * @return the paraKey
	 */
	public String getParaKey() {
		return paraKey;
	}

	/**
	 * @param paraKey the paraKey to set
	 */
	public void setParaKey(String paraKey) {
		this.paraKey = paraKey;
	}

	/**
	 * @return the paraDesc
	 */
	public String getParaDesc() {
		return paraDesc;
	}

	/**
	 * @param paraDesc the paraDesc to set
	 */
	public void setParaDesc(String paraDesc) {
		this.paraDesc = paraDesc;
	}

	/**
	 * @return the paraTypes
	 */
	public List<String> getParaTypes() {
		return paraTypes;
	}

	/**
	 * @param paraTypes the paraTypes to set
	 */
	public void setParaTypes(List<String> paraTypes) {
		this.paraTypes = paraTypes;
	}

	/**
	 * @return the typeMenu
	 */
	public Map<String, String> getTypeMenu() {
		return typeMenu;
	}

	/**
	 * @param typeMenu the typeMenu to set
	 */
	public void setTypeMenu(Map<String, String> typeMenu) {
		this.typeMenu = typeMenu;
	}

	/**
	 * @return the pager
	 */
	public SimplePager getPager() {
		return pager;
	}

	/**
	 * @param pager the pager to set
	 */
	public void setPager(SimplePager pager) {
		this.pager = pager;
	}

	public String list() {
		return "list";
	}

	public String search() {
		CommonCriteria cri = new CommonCriteria();
		if (StringUtils.isNotEmpty(paraKey)) {
			cri.addRlike("code", paraKey);
		}
		if (StringUtils.isNotEmpty(paraDesc)) {
			cri.addRlike("description", paraDesc);
		}
		if (paraTypes != null && paraTypes.size() > 0) {
			cri.addIn("type", paraTypes);
		}

		try {
			int entitySize = groupService.getListSize(cri).intValue();
			pager.setTotalSize(entitySize);

			objList = groupService.getList(pager.getCurrentPage() * pager.getPageRecord(), pager.getPageRecord(), cri,
					new String[] { "code asc" });

			logger.debug("pager:{},groups:{}", pager, objList);
		} catch (CoreException e) {
			addActionError(e.getMessage());
			e.printStackTrace();
		}
		return "list";
	}

	public String view() {
		try {
			if (StringUtils.isNotBlank(paraId)) {
				obj = groupService.get(paraId);
				if (obj == null) {
					addActionError("Group not found");
					return "list";
				}
			} else {
				addActionError("Group ID not found");
				return "list";
			}
		} catch (Exception e) {
			addActionError(e.getMessage());
			return "list";
		}
		return "view";
	}

	public String add() {
		obj = new Group();
		return "edit";
	}

	public String save() {
		if (obj == null) {
			addActionError("Group not found");
			return "list";
		}

		if (obj.getModifyDate() == null) {
			// 因為重新建立的 entity 才會 re-new name
			obj = new Group(obj.getCode().toUpperCase(), obj.getDescription());
		}
		try {
			groupService.save(obj);
		} catch (Exception e) {
			addActionError(e.getMessage());
			return "edit";
		}
		return "view";
	}

	public String edit() {
		return "edit";
	}

	@Override
	public void prepare() throws Exception {
		clearErrorsAndMessages();
		if (typeMenu == null) {
			typeMenu = new HashMap<String, String>();
			typeMenu.put("TE", "Telexpress");
			typeMenu.put("OSA", "O.SA");
			typeMenu.put("HTC", "HTC");
		}

		if (paraTypes == null) {
			paraTypes = Arrays.asList("TE", "OSA", "HTC");
		}
		if (pager == null) {
			pager = new SimplePager();
		}
	}

}
