<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/taglibs.jsp"%>
<%@ page import="com.teecs.core.common.utils.ServerValue"%>
<style type="text/css">
input,textarea {
	width: auto;
}
</style>
<div class="row-fluid">
	<s:if test="hasActionMessages()">
		<div class="alert alert-block">
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
					<i class="icon-tag"></i> Permission维护管理
				</h2>
			</div>
			<table class="table table-bordered table-striped table-condensed">
				<tr>
					<th style="width: 15%">类别</th>
					<s:if test="%{permission.modifyDate != null}">
						<td>${permission.type}</td>
					</s:if>
					<s:else>
						<td>OTHER<s:hidden name="permission.type" value="OTHER"/></td>
					</s:else>
				</tr>
				<tr>
					<th style="width: 15%">代码</th>
					<s:if test="%{permission.modifyDate != null}">
						<td>${permission.key}</td>
					</s:if>
					<s:else>
						<td><s:textfield name="permission.key" id="pkey" theme="simple"/></td>
					</s:else>
				</tr>
				<tr>
					<th style="width: 15%">说明</th>
					<td style="text-align: left"><s:textfield name="permission.description" theme="simple"/></td>
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