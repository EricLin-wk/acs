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
					<i class="icon-tag"></i> 设备群组管理
				</h2>
				<div class="box-icon">
					<a href="add.do" class="btn btn-primary" style="border-bottom: 10px;width: 65px">添加群组</a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
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
				<th>群组名称</th>
				<th>排序代码</th>				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${objList}" var="obj" varStatus="rows_index">
				<tr>
					<td><fmt:formatNumber pattern="0" value="${rows_index.count + pager.currentPage*20}" /></td>
					<td class="center"><a href="view.do?paraOid=${obj.oid}">${obj.groupName}</a></td>
					<td class="center">${obj.groupOrder}</td>					
				</tr>
			</c:forEach>
		</tbody>
	</table>	
</s:if>