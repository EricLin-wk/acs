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
	<div class="box span12">
		<div class="box-header well" data-original-title>
			<h2>
				<i class="icon-tag"></i> 设备管理-显示
			</h2>
		</div>
		<table class="table table-bordered table-striped table-condensed">
			<tr>
				<th style="width: 15%">序列号</th>
				<td>${paraObj.serialNum}</td>
			</tr>
			<tr>
				<th style="width: 15%">设备名称</th>
				<td style="text-align: left">${paraObj.deviceName}</td>
			</tr>
			<tr>
				<th style="width: 15%">设备类型</th>
				<td style="text-align: left">${menuDeviceType.options[paraObj.deviceType].name}</td>
			</tr>
			<tr>
				<th style="width: 15%">型号</th>
				<td style="text-align: left">${paraObj.model}</td>
			</tr>
			<tr>
				<th style="width: 15%">IP地址</th>
				<td style="text-align: left">${paraObj.ipAddress}</td>
			</tr>
			<tr>
				<th style="width: 15%">端口</th>
				<td style="text-align: left">${paraObj.port}</td>
			</tr>
			<tr>
				<th style="width: 15%">群组</th>
				<c:set var="groupIdStr">${paraObj.groupId}</c:set>
				<td style="text-align: left">${groupMap[groupIdStr].groupName}</td>
			</tr>		
		</table>
		<div class="row-fluid">
			<div class="span12" style="margin-bottom: 20px; margin-left: 15px">
				<a href="edit.do?paramOid=${obj.oid}" class="btn btn-primary">编辑</a>
				<a href="list.do"	class="btn btn-primary">回搜寻页</a>
			</div>
		</div>
	</div>
</div>