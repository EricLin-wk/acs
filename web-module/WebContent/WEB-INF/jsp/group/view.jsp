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
				<i class="icon-tag"></i> Group维护管理
			</h2>
		</div>
		<table class="table table-bordered table-striped table-condensed">
			<tr>
				<th style="width: 15%">代码</th>
				<td>${obj.code}</td>
			</tr>
			<tr>
				<th style="width: 15%">说明</th>
				<td style="text-align: left">${obj.description}</td>
			</tr>
			<tr>
				<th style="width: 15%">公司/部门权限</th>
				<td style="text-align: left">
					<table class="table table-bordered table-striped table-condensed">
						<thead>
							<tr>
								<th style="width: 25%">代码</th>
								<th>说明</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="obj.role.permissions" var="pobj"
								status="rows_index">
								<tr>
									<td>${pobj.value.name}</td>
									<td>${pobj.value.description}</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<th style="width: 15%">主管权限</th>
				<td style="text-align: left">
					<table class="table table-bordered table-striped table-condensed">
						<thead>
							<tr>
								<th style="width: 25%">代码</th>
								<th>说明</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="obj.manager.permissions" var="pobj"
								status="rows_index">
								<tr>
									<td>${pobj.value.name}</td>
									<td>${pobj.value.description}</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
		<div class="row-fluid">
			<div class="span12" style="margin-bottom: 20px; margin-left: 15px">
				<a href="edit.do" class="btn btn-primary">修改</a>
				<a href="search.do"	class="btn btn-primary">回搜寻页</a>
			</div>
		</div>
	</div>
</div>