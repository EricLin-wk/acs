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
					<i class="icon-tag"></i> Role维护管理
				</h2>
			</div>
			<s:hidden name="permissionName" id="pName"></s:hidden>
			<table class="table table-bordered table-striped table-condensed">
				<tr>
					<th style="width: 15%">类别</th>
					<s:if test="%{obj.modifyDate != null}">
						<td>${obj.type}</td>
					</s:if>
					<s:else>
						<td><s:select label="类型" name="roleType" list="roleTypeMenu.options" listKey="key" listValue="value.name"  headerKey="" headerValue="== 选择类型 ==" theme="simple"/></td>
					</s:else>
				</tr>
				<tr>
					<th style="width: 15%">代码</th>
					<s:if test="%{obj.modifyDate != null}">
						<td>${obj.key}</td>
					</s:if>
					<s:else>
						<td><s:textfield name="key" id="pkey" theme="simple"/></td>
					</s:else>
				</tr>
				<tr>
					<th style="width: 15%">说明</th>
					<td style="text-align: left"><s:textfield name="obj.description" theme="simple"/></td>
				</tr>
				<tr>
					<th style="width: 15%">权限</th>
					<td style="text-align: left">
						<s:select list="averablePermission" listKey="value.name" listValue="value.nameDesc" id="selPermission" theme="simple"></s:select> 
						<input type="button" value="增加" onclick="$('#pName').val($('#selPermission').val());$('#formObj').attr('action','addPermission.do');$('#formObj').submit();"></input>
						<table class="table table-bordered table-striped table-condensed">
							<thead>
								<tr>
									<th style="width: 25%">动作</th>
									<th>代码</th>
									<th>说明</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="obj.permissions" var="pobj"
									status="rows_index">
									<tr>
										<td><button type="button" class="btn btn-primary" onclick="$('#pName').val('${pobj.value.name}');$('#formObj').attr('action','removePermission.do');$('#formObj').submit();">删除</button></td>
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