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
		<div class="alert alert-block" >
			<button type="button" class="close" data-dismiss="alert">×</button>
			<s:actionmessage cssStyle="list-style-type:none;" escape="false"/>
		</div>
	</s:if>
	<s:if test="hasActionErrors()">
		<div class="alert alert-error">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<s:actionerror cssStyle="list-style-type:none;" escape="false"/>
		</div>
	</s:if>
	<s:if test="hasFieldErrors()">
		<div class="alert alert-info">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<s:fielderror cssStyle="list-style-type:none;" escape="false"/>
		</div>
	</s:if>

	<s:form action="search.do" method="post" id="searchForm">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>
					<i class="icon-tag"></i> Permission维护管理
				</h2>
				<div class="box-icon">
					<a href="add.do" class="btn btn-primary" style="border-bottom: 10px;width: 35px">新增</a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
				
			</div>
	
			<div class="box-content">
				<div class="row-fluid">
					<div class="span1">代码</div>
					<div class="span2">
						<input name="paraKey" type="text" id="paraKey" value="${paraKey}">
					</div>
					<div class="span1">说明</div>
					<div class="span2">
						<input name="paraDesc" type="text" id="paraDesc" value="${paraDesc}">
					</div>
				</div>
				<div class="row-fluid">
					<div class="span1">类别</div>
					<div class="span6">						
						<s:checkboxlist list="typeMenu" name="paraTypes" listKey="code" listValue="desc" template="checkboxlist2" theme="simple"></s:checkboxlist>
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
<s:if test="permissionList.size > 0">
	<table class="table table-bordered table-striped table-condensed">
		<thead>
			<tr>
				<th>#</th>
				<th>类别</th>
				<th>代码</th>
				<th>说明</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${permissionList}" var="permission" varStatus="rows_index">
				<tr>
					<td><fmt:formatNumber pattern="0000" value="${rows_index.count + pager.currentPage*20}" /></td>
					<td class="center"><a href="view.do?paraId=${permission.name}">${permission.type.code}</a></td>
					<td class="center"><a href="view.do?paraId=${permission.name}">${permission.key}</a></td>
					<td class="center">${permission.description}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<teecs:pageutil link="search.do" pagerObj="${pager}" />
</s:if>