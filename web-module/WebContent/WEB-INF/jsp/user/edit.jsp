<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/taglibs.jsp"%>
<%@ page import="com.acs.core.common.utils.ServerValue"%>
<style type="text/css">
input,textarea {
	width: auto;
}
</style>
<div class="row-fluid">
	<s:if test="hasActionMessages()">
		<div class="alert alert-success">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<s:actionmessage cssStyle="list-style-type:none;" escape="false" />
		</div>
	</s:if>
	<s:if test="hasActionErrors()">
		<div class="alert alert-error">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<s:actionerror cssStyle="list-style-type:none;" escape="false" />
		</div>
	</s:if>
	<s:if test="hasFieldErrors()">
		<div class="alert alert-info">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<s:fielderror cssStyle="list-style-type:none;" escape="false" />
		</div>
	</s:if>
	<s:form action="save.do" method="post" id="formObj">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>
					<i class="icon-tag"></i> User维护管理
				</h2>
			</div>
			<s:hidden name="permissionName" id="pName"></s:hidden>
			<table class="table table-bordered table-striped table-condensed">
				<tr>
					<th style="width: 15%">* 登入代码</th>
					<s:if test="%{obj.modifyDate != null}">
						<td>${obj.username}<input type="hidden" id="username" value="${obj.username}"></input></td>
					</s:if>
					<s:else>
						<td><s:textfield name="obj.username" id="username" theme="simple"/></td>
					</s:else>
				</tr>
				<s:if test="%{obj.modifyDate == null}">
					<tr>
						<th style="width: 15%">密码</th>
						<td><s:password name="obj.password" id="password" theme="simple"/></td>
					</tr>
				</s:if>
				<tr>
					<th style="width: 15%">* 姓名</th>
					<td><s:textfield name="obj.nameNative" id="nameNative" theme="simple"/></td>
				</tr>
				<tr>
					<th style="width: 15%">* 部门/群组</th>
					<td style="text-align: left"><s:select list="averableGroups" name="groupCode" listKey="code" listValue="description" theme="simple"/></td>
				</tr>
				<tr>
					<th style="width: 15%">* 电子邮件</th>
					<td><s:textfield name="obj.email" id="email" theme="simple"/></td>
				</tr>
				<tr>
					<th style="width: 15%">电话</th>
					<td><s:textfield name="obj.phone" theme="simple"/></td>
				</tr>
				<tr>
					<th style="width: 15%">手机</th>
					<td><s:textfield name="obj.mobile" theme="simple"/></td>
				</tr>
				<tr>
					<th style="width: 15%">状态</th>
					<td><s:select name="obj.status" list="userStatusMenu.options" listKey="key" listValue="value.name" theme="simple"/></td>
				</tr>
				<tr>
					<th style="width: 15%">一般角色</th>
					<td style="text-align: left">
						<s:checkboxlist name="grantOtherRoles" list="otherRoles" listKey="name" listValue="description" template="checkboxlist2" theme="simple"/>
					</td>
				</tr>
				<tr>
					<th style="width: 15%">主管角色</th>
					<td style="text-align: left">
						<s:checkboxlist name="grantManageRoles" list="managerRoles" listKey="name" listValue="description" template="checkboxlist2" theme="simple"/>
					</td>
				</tr>
			</table>
			<div class="row-fluid">
				<div class="span12" style="margin-bottom: 20px; margin-left: 15px">
					<button type="submit" class="btn btn-primary">储存修改</button>
					<s:if test="%{obj.modifyDate != null}">
						<a href="view.do" class="btn btn-primary">回检视页</a>
					</s:if>
					<s:else>
						<a href="search.do" class="btn btn-primary">回搜寻页</a>
					</s:else>
				</div>
			</div>
		</div>
	</s:form>
</div>