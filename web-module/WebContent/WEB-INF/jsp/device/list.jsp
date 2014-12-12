<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/taglibs.jsp"%>
<style type="text/css">
input,textarea  {
	width:auto;
}
</style>
<script type="text/javascript">
	function search(){
		document.searchForm.submit();
	}
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
	<s:form action="list.do" method="post" id="searchForm">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>
					<i class="icon-tag"></i> 设备管理
				</h2>
				<div class="box-icon">
					<a href="add.do" class="btn btn-primary" style="border-bottom: 10px;width: 65px">添加设备</a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
				
			</div>
	
			<div class="box-content">
				<div class="row-fluid">
					<div class="span1">设备群组</div>
					<div class="span2">
						<s:select id="paraGroupId" name="paraGroupId" list="groupMap" headerKey="-1" headerValue="全部" listKey="key" listValue="value.groupName" theme="simple"/>						
					</div>
					<div class="span1">设备类型</div>
					<div class="span2">
						<s:select id="paraDeviceType" name="paraDeviceType" list="menuDeviceType.options" headerKey="" headerValue="全部" listKey="key" listValue="value.name" theme="simple"/>						
					</div>
					<div class="span1">序列号</div>
					<div class="span4">						
						<input name="paraSerialNum" type="text" id="paraSerialNum" value="${paraSerialNum}">
					</div>					
				</div>
				<div class="row-fluid">
					<div class="span1">
						<button type="submit" class="btn btn-primary" onClick="search();">搜索</button>
					</div>
				</div>
			</div>
		</div>
	</s:form>
</div>
<s:if test="objList.size > 0">
	<table class="table table-bordered table-striped table-condensed">
		<thead>
			<tr>
				<th>#</th>
				<th>序列号</th>
				<th>设备名称</th>
				<th>设备类型</th>
				<th>型号</th>
				<th>IP地址</th>
				<th>设备群组</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${objList}" var="obj" varStatus="rows_index">
				<tr>
					<td><fmt:formatNumber pattern="0" value="${rows_index.count + pager.currentPage*20}" /></td>
					<td class="center"><a href="view.do?paraOid=${obj.oid}">${obj.serialNum}</a></td>
					<td class="center">${obj.deviceName}</td>
					<td class="center">${menuDeviceType.options[obj.deviceType].name}</td>
					<td class="center">${obj.model}</td>
					<td class="center">${obj.ipAddress}:${obj.port}</td>
					<td class="center"><c:set var="groupIdStr">${obj.groupId}</c:set>${groupMap[groupIdStr].groupName}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<teecs:pageutil link="list.do" pagerObj="${pager}" />
</s:if>