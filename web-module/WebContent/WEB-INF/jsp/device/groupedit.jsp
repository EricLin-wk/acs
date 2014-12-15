<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/taglibs.jsp"%>
<%@ page import="com.acs.core.common.utils.ServerValue"%>
<style>
#formObj label.error  {
	width: auto;
	display: inline;
	color: #B94A48;
}
#formObj input.error  {
	border-color: #B94A48;
}
</style>
<script>
$().ready(function() {
	$("#formObj").validate({
		rules: {
			"paraObj.groupName": "required",
			"paraObj.groupOrder": {required: true, digits: true}
		}
	});
});
</script>
<div class="row-fluid">
	<s:if test="hasActionMessages()">
		<div class="alert alert-success" >
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong><s:actionmessage cssStyle="list-style-type:none;" escape="false"/></strong>
		</div>
	</s:if>
	<s:if test="hasActionErrors()">
		<div class="alert alert-error">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong><s:actionerror cssStyle="list-style-type:none;" escape="false"/></strong>
		</div>
	</s:if>
	<s:if test="hasFieldErrors()">
		<div class="alert alert-info">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong><s:fielderror cssStyle="list-style-type:none;" escape="false"/></strong>
		</div>
	</s:if>
	<s:form action="save.do" method="post" id="formObj">
	<div class="box span12">
		<div class="box-header well" data-original-title>
			<h2>
				<i class="icon-tag"></i> 设备群组管理-
				<s:if test="%{paraObj.oid != null}">
					编辑<s:hidden id="paraObj.oid" value="%{paraObj.oid}"/>
				</s:if>
				<s:else>添加</s:else>
			</h2>
		</div>
		<table class="table table-bordered table-striped table-condensed">
			<tr>
				<th style="width: 15%">群组名称*</th>
				<td style="text-align: left"><s:textfield id="paraObj.groupName" name="paraObj.groupName" theme="simple"/></td>
			</tr>
			<tr>
				<th style="width: 15%">排序代码*</th>
				<td style="text-align: left"><s:textfield id="paraObj.groupOrder" name="paraObj.groupOrder" theme="simple"/></td>
			</tr>				
		</table>
		<div class="row-fluid">
			<div class="span12" style="margin-bottom: 20px; margin-left: 15px">
				<button type="submit" class="btn btn-primary">保存</button>
					<s:if test="%{paraObj.oid != null}">						
						<a href="view.do?paraOid=${paraObj.oid}" class="btn btn-primary">回检视页</a>
					</s:if>
					<s:else>
						<a href="list.do" class="btn btn-primary">回搜索页</a>
					</s:else>
			</div>
		</div>
	</div>
	</s:form>
</div>