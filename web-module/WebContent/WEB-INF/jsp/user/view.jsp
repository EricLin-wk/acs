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
	<div class="box span12">
		<div class="box-header well" data-original-title>
			<h2>
				<i class="icon-tag"></i> User维护管理
			</h2>
		</div>
		<table class="table table-bordered table-striped table-condensed">
			<tr>
				<th style="width: 15%">登入代码</th>
				<td>${obj.username}</td>
			</tr>
			<tr>
				<th style="width: 15%">姓名</th>
				<td>${obj.nameNative}</td>
			</tr>
			<tr>
				<th style="width: 15%">电子邮件</th>
				<td style="text-align: left">${obj.email}</td>
			</tr>
			<tr>
				<th style="width: 15%">电话</th>
				<td style="text-align: left">${obj.phone}</td>
			</tr>
			<tr>
				<th style="width: 15%">手机</th>
				<td style="text-align: left">${obj.mobile}</td>
			</tr>
			<tr>
				<th style="width: 15%">会员状态</th>
				<td style="text-align: left">${userStatusMenu.options[obj.status].name}</td>
			</tr>
			<tr>
				<th style="width: 15%">目前角色</th>
				<td style="text-align: left">
					<table class="table table-bordered table-striped table-condensed">
						<tbody>
							<c:forEach items="${grantRoles}" var="role" varStatus="status">
								<tr>
									<td>
										${role.description}
										<c:if test="${role.type=='MANAGER'}"> 部门管理</c:if>
										<c:if test="${role.type=='GROUP'}"> 部门</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table class="table table-bordered table-striped table-condensed">
						<tbody>
							<tr>
								<th>DashBoard</th>
							</tr>
							<c:forEach items="${grantTeamRoles}" var="role" varStatus="status">
								<tr>
									<td>
										${role.description}
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<th style="width: 15%">目前权限</th>
				<td style="text-align: left">
					<table class="table table-bordered table-striped table-condensed">
						<tbody>
							<c:forEach items="${permissions}" var="permission" varStatus="rows_index">
								<tr>
									<td>${permission.description}[${permission.name}]</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<s:if test="teamPermissions.size > 0">
						<table class="table table-bordered table-striped table-condensed">
							<tbody>
								<tr>
									<th>DashBoard</th>
								</tr>
								<c:forEach items="${teamPermissions}" var="permission" varStatus="rows_index">
									<tr>
										<td>${permission.description}[${permission.name}]</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</s:if>
				</td>
			</tr>
		</table>
		<div class="row-fluid">
			<div class="span12" style="margin-bottom: 20px; margin-left: 15px">
				<a href="edit.do?objId=${obj.username}" class="btn btn-primary">修改</a>
				<a href="search.do"	class="btn btn-primary">回搜寻页</a>
				<a href="resetPasswd.do?objId=${obj.username}" class="btn btn-primary">重设密码</a>
				<a href="delete.do?objId=${obj.username}" class="btn btn-primary">删除</a>
			</div>
		</div>
	</div>
</div>